package com.wh.wang.scroopclassproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.wh.wang.scroopclassproject.base.MyApplication;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PermissionUtils {

    public static void per(Activity context) {
        if (Build.VERSION.SDK_INT >= 23) {
            //检查系统是否已经授予了指定权限
            int checkCallPhonePermission1 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
//            int checkCallPhonePermission4 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//            int checkCallPhonePermission5 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//            int checkCallPhonePermission6 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR);
//            int checkCallPhonePermission7 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
            if (checkCallPhonePermission1 != PackageManager.PERMISSION_GRANTED ||
                    checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED||
                    checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED
//                    checkCallPhonePermission4 != PackageManager.PERMISSION_GRANTED ||
//                    checkCallPhonePermission5 != PackageManager.PERMISSION_GRANTED ||
//                    checkCallPhonePermission6 != PackageManager.PERMISSION_GRANTED ||
//                    checkCallPhonePermission7 != PackageManager.PERMISSION_GRANTED
                    ) {
                //申请权限
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.READ_CALENDAR,
//                        Manifest.permission.WRITE_CALENDAR
                }, 110);
            }
        }
    }

    public static void getPhoneStatePermission(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            //检查系统是否已经授予了指定权限
            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
            if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 110);
            }
        }
    }
}
