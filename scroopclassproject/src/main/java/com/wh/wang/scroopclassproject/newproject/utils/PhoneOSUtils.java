package com.wh.wang.scroopclassproject.newproject.utils;

import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by teitsuyoshi on 2018/6/11.
 */

public class PhoneOSUtils {
    public static final String MIUI = "xiaomi";
    public static final String Flyme = "meizu";
    public static final String OTHER = "other";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static String phoneVersion(){
        if(isMIUI()){
            return MIUI;
        }else if(isFlyme()){
            return Flyme;
        }else{
            return OTHER;
        }
    }

    /**
     * 判断手机是否是小米
     */
    public static boolean isMIUI() {
        final Properties prop = new Properties();
        return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
    }

    /**
     * 判断手机是否是魅族
     *
     * @return
     */
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null || TextUtils.equals("Meizu", Build.MANUFACTURER);
        } catch (final Exception e) {
            return TextUtils.equals("Meizu", Build.MANUFACTURER);
        }
    }
}
