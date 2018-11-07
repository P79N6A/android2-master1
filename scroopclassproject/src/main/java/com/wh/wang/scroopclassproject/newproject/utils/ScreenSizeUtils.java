package com.wh.wang.scroopclassproject.newproject.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ScreenSizeUtils {

    public static int getPhoneW(Activity activity){
        DisplayMetrics d = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(d);
        activity = null;
        return d.widthPixels;
    }
}
