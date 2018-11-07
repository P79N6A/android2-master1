package com.wh.wang.scroopclassproject.newproject.utils;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wh.wang.scroopclassproject.base.MyApplication;

/**
 * Created by chdh on 2017/9/26.
 */

public class MainSpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = DisplayUtil.dip2px(MyApplication.mApplication,10);
        }else if(parent.getChildAdapterPosition(view)==layoutManager.getItemCount()-1){
            outRect.right = DisplayUtil.dip2px(MyApplication.mApplication,10);
        }
        outRect.left = mSpace;
    }

    public MainSpaceItemDecoration(int space) {
        this.mSpace = space;
    }
}
