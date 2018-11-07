package com.wh.wang.scroopclassproject.newproject.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.wh.wang.scroopclassproject.newproject.utils.PhoneOSUtils.isFlyme;
import static com.wh.wang.scroopclassproject.newproject.utils.PhoneOSUtils.isMIUI;

/**
 * 判别状态栏透明时文字黑白问题
 * Created by teitsuyoshi on 2018/6/11.
 */

public class StatusBarUtils {

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        if(context==null){
            return 32; //NULL 返回默认高度
        }
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置当前手机状态栏黑白字
     * @param useDart 是否使用黑色
     * @param activity 适配的activity
     */
    public static void setStatusTextColor(boolean useDart, Activity activity) {
        if (isFlyme()) { //魅族
            processFlyme(activity, useDart);
        } else if (isMIUI()) { //小米
            processMIUI(activity, useDart);
        } else { //其他手机
            if (useDart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
            }
        }
    }

    /**
     * 适配魅族
     * @param activity
     * @param darkmode
     */
    private static void processFlyme(Activity activity, boolean darkmode) {
        Window window = activity.getWindow();
        if(window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (darkmode) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception var8) {
                Log.w("StatusBarUtils", "setStatusBarDarkIcon: failed");
            }
        }
    }

    /**
     * 适配小米
     * @param activity
     * @param darkmode
     */
    private static void processMIUI(Activity activity, boolean darkmode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 即基于 Android 6.0 ，开发版 7.7.13 及以后版本
            compatHighMIUI(activity, darkmode);
        } else {
            compatLowMIUI(activity, darkmode);
        }
    }

    /**
     * 适配高版本(即基于 Android 6.0 ，开发版 7.7.13 及以后版本)小米
     * 参考 https://dev.mi.com/console/doc/detail?pId=1159
     * @param activity
     * @param darkmode
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void compatHighMIUI(Activity activity, boolean darkmode) {
        View decorView = activity.getWindow().getDecorView();
        if (darkmode) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            int flag = decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(flag);
        }
    }

    /**
     * 适配低版本小米
     * @param activity
     * @param darkmode
     */
    private static void compatLowMIUI(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
