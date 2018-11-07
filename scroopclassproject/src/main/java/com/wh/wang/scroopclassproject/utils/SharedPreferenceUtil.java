package com.wh.wang.scroopclassproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String USER_ALIAS = "USER_ALIAS";
    public static final String USER_ID = "USER_ID";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_PHONE = "USER_PHONE";

    private static SharedPreferences preferences;

    private static synchronized SharedPreferences getSharedPreferences(
            Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("infodatas", 0);
        }
        return preferences;
    }

    public static void putStringValueByKey(Context context, String key, String value) {
        try {
            getSharedPreferences(context).edit().putString(key, value).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStringFromSharedPreference(Context context, String key, String defaultValue) {
        SharedPreferences shared = getSharedPreferences(context);
        if (shared != null) {
            return shared.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public static void putLongValueByKey(Context context, String key, long value) {
        try {
            getSharedPreferences(context).edit().putLong(key, value).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getLongFromSharedPreference(Context context, String key, long defaultValue) {
        SharedPreferences shared = getSharedPreferences(context);
        if (shared != null) {
            return shared.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    public static void putIntValueByKey(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).commit();
    }

    public static int getIntValueByKey(Context context, String key, int defaultValue) {
        SharedPreferences shared = getSharedPreferences(context);
        if (shared != null) {
            return shared.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    public static void putFloatValueByKey(Context context, String key, float value) {
        getSharedPreferences(context).edit().putFloat(key, value).commit();
    }

    public static float getFloatValueByKey(Context context, String key, int defaultValue) {
        SharedPreferences shared = getSharedPreferences(context);
        if (shared != null) {
            return shared.getFloat(key, defaultValue);
        }
        return defaultValue;
    }

    public static void putBooleanByKey(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).commit();
    }

    public static boolean getBooleanByKey(Context context, String key,boolean defaultValue) {
        SharedPreferences shared = getSharedPreferences(context);
        if (shared != null) {
            return shared.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    public static  void clearData(String key,Context context){
        getSharedPreferences(context).edit().remove(key).commit();
    }
    public static void clearInfo(Context context){
        getSharedPreferences(context).edit().clear().commit();
    }
}