package com.wh.wang.scroopclassproject.newproject.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wh.wang.scroopclassproject.base.MyApplication;

/**
 * Created by Administrator on 2017/12/15.
 */

public class PunchSpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;
    public PunchSpacesItemDecoration(int space) {
        this.space = DisplayUtil.dip2px(MyApplication.mApplication,space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildAdapterPosition(view)==0){
            outRect.left = space;
        }
//        if(parent.getChildAdapterPosition(view)==parent.getChildCount()-1){
//            outRect.right = space;
//        }
    }
}
