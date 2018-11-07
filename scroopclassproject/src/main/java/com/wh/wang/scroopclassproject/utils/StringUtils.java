package com.wh.wang.scroopclassproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 */
public abstract class StringUtils {

    private static DecimalFormat fmt = new DecimalFormat("##,###,###,##0.00");

    /* 地球半径*/
    private static final double EARTH_RADIUS = 6378137.0;

    private StringUtils() {
        throw new InstantiationError("工具类无法实例化");
    }

    /**
     * 判断字符串是否为空，即为null或""
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }


    /**
     * 判断字符串是否为空，即为null或""或长度为0
     */
    public static boolean isEmptyBankZero(String str) {
        return ((str == null) || (str.length() == 0) || (str.equals("")));
    }

    /**
     * 判断字符串是否为空，即为null或""或长度为0
     */
    public static boolean isNotEmptyBankZero(String str) {
        return (!(isEmptyBankZero(str)));
    }


    /**
     * 判断字符串是否不为空，即不为null且不为""
     */
    public static boolean isNotEmpty(String str) {
        return (!(isEmpty(str)));
    }

    public static boolean isNotEmpty(TextView tv) {
        if(tv!=null&&tv.getText()!=null&&!(isEmpty(tv.getText().toString()))){
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(EditText ed) {
        if(ed!=null&&ed.getText()!=null&&!(isEmpty(ed.getText().toString()))){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空白，即为null或为" "
     */
    public static boolean isBlank(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    /**
     * 判断字符串是否不为空白，即不为null且不为" "
     */
    public static boolean isNotBlank(String str) {
        return (!(isBlank(str)));
    }

    /**
     * 字符串转为int
     */
    public static int StringToInt(String str) {
        int result = 0;
        if (null == str) {
            return 0;
        }
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将double按默认格式格式化为字符串
     */
    public static String formateStr(Double str) {
        return fmt.format(str);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据两个经纬度获取距离  米
     */
    public static double gpsDistance(double lat_a, double lng_a, double lat_b,
                                     double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 获取根目录路径
     */
    public static String getSDPath() {
        boolean hasSDCard = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        // 如果有sd卡，则返回sd卡的目录
        if (hasSDCard) {
            return Environment.getExternalStorageDirectory().getPath() + "/";
        } else
            // 如果没有sd卡，则返回存储目录
            return Environment.getDownloadCacheDirectory().getPath() + "/";
    }


    /**
     * 基本判断手机号格式
     *
     * @param phoneNum
     * @return
     */
    public static boolean checkPhoneNum(String phoneNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0-9])|(166)|(198)|(199))\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 验证是否是有效手机号
     * 条件：
     * 以+86开头或者是11位有效手机号
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、182、198
     * 联通：130、131、132、152、155、156、185、186、166
     * 电信：133、153、180、189、（1349卫通）、177、199
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNum(String mobiles) {
        Pattern p2 = Pattern
                .compile("^(\\+?86)\\d{11}$");
        Matcher m = p2.matcher(mobiles);

        if (mobiles.length() == 11) {
            return checkPhoneNum(mobiles);

        } else if (mobiles.length() > 11 && m.matches()) {
            String mobile = mobiles.substring(3);
            return checkPhoneNum(mobile);
        }
        return false;
    }


    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    /**
     * 获取时间戳
     */
    public static String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
        return str;
    }


    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     *
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */

    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }


    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static String getAppInfo(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return  versionName;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingphone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }


    /***
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /***
     * 删除文件夹
     *
     * @param
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
