package com.wh.wang.scroopclassproject.newproject.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;

import com.aliyun.clientinforeport.util.AppUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hkq325800 on 2017/4/14.
 */

public class PermissionUtils {
    /**
     * Build.MANUFACTURER
     */
    private static final String MANUFACTURER_HUAWEI = "huawei";//华为
    private static final String MANUFACTURER_MEIZU = "meizu";//魅族
    private static final String MANUFACTURER_XIAOMI = "xiaomi";//小米
    private static final String MANUFACTURER_SONY = "sony";//索尼
    private static final String MANUFACTURER_OPPO = "oppo";
    private static final String MANUFACTURER_LG = "lg";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "letv";//乐视
    private static final String MANUFACTURER_ZTE = "zte";//中兴
    private static final String MANUFACTURER_YULONG = "yulong";//酷派
    private static final String MANUFACTURER_LENOVO = "lenovo";//联想
    /**
     * 此函数可以自己定义
     * @param activity
     */

    public static void GoToSetting(Activity activity){
        String packageName = AppUtils.getPackageName(activity);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (Build.MANUFACTURER.toLowerCase()){
            case MANUFACTURER_HUAWEI:
//                intent.putExtra("packageName", packageName);
                intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                activity.startActivity(intent);
                break;
            case MANUFACTURER_MEIZU:
                intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra("packageName", packageName);
                activity.startActivity(intent);
                break;
            case MANUFACTURER_XIAOMI:
                intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                String rom = getMiuiVersion();
                if ("V6".equals(rom) || "V7".equals(rom)) {
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                    activity.startActivity(intent);

                } else if ("V8".equals(rom) || "V9".equals(rom)) {
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                    activity.startActivity(intent);

                } else {
                    ApplicationInfo(activity);
                }
                break;
            case MANUFACTURER_SONY:
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
                activity.startActivity(intent);
                break;
            case MANUFACTURER_OPPO:
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity"));
                activity.startActivity(intent);
                break;
            case MANUFACTURER_LG :
                intent.setAction("android.intent.action.MAIN");
                intent.putExtra("packageName", packageName);
                ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
                intent.setComponent(comp);
                activity.startActivity(intent);
                break;
            case MANUFACTURER_LETV:
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
                activity.startActivity(intent);
                break;
            default:
                ApplicationInfo(activity);
                Log.e("goToSetting", "目前暂不支持此系统");
                break;
        }
    }

    /**
     * 应用信息界面
     * @param activity
     */
    public static void ApplicationInfo(Activity activity){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }

    /**
     * 系统设置界面
     * @param activity
     */
    public static void SystemConfig(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }


    /**
     * 获取 MIUI 版本号
     */
    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
}
