package com.wh.wang.scroopclassproject.newproject.callback;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chdh on 2018/3/5.
 */

public interface OnWorkClickListener {
    void onWorkItemClick(int pos,String id);
    void onWorkLikeClick(int pos, String id, int like, ImageView likeImg, TextView textView,int flag);
    void onWorkRemarkClick(int pos,String id);
}
