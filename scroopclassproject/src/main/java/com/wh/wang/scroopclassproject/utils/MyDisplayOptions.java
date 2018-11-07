package com.wh.wang.scroopclassproject.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MyDisplayOptions {
    private MyDisplayOptions() {
    }

    public static DisplayImageOptions getOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.showImageForEmptyUri(R.drawable.nav_bg)
                //.showImageOnFail(R.drawable.nav_bg)
                .resetViewBeforeLoading(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(10))
                .build();
        return options;
    }
}
