package com.wh.wang.scroopclassproject.newproject.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/12/15.
 */

public class LinearSpacesItemDecoration  extends RecyclerView.ItemDecoration{
    private int space;
    private int mFlag = 0;
    public LinearSpacesItemDecoration(int space,int flag) {
        this.space = space;
        mFlag = flag;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        if(mFlag==1){
            if(parent.getChildAdapterPosition(view)==0||parent.getChildAdapterPosition(view)==5||parent.getChildAdapterPosition(view)==6){
                outRect.bottom = 0;
            }
        }else if(mFlag==2){
            if(parent.getChildAdapterPosition(view)==0){
                outRect.bottom = 0;
            }
        }
    }
}
