package com.wh.wang.scroopclassproject.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * --------------------------------------------
 * auther :  Lvfq
 * 2016/10/30 1:41
 * description ：
 * -------------------------------------------
 **/
public class ViewHolderr {
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}