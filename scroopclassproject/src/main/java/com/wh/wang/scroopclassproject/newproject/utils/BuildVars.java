/*
 * This is the source code of Emm for Android v. 1.3.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2014.
 */

package com.wh.wang.scroopclassproject.newproject.utils;

public class BuildVars {
    public static boolean DEBUG_VERSION = true;
    public static int APP_ID = 2458;
    public static String APP_HASH = "5bce48dc7d331e62c955669eb7233217";
    public static String HOCKEY_APP_HASH = "bfe4ad0dd2c941c3b3ce0453a0c6aa63";
    public static String GCM_SENDER_ID = "760348033672";
    public static String SEND_LOGS_EMAIL = "email@gmail.com";


    public static String BuildInfo(int ver){
        switch (ver){
            case 11:
                return "Android 3.0 Honeycomb";
            case 12:
                return "Android 3.1 Honeycomb";
            case 13:
                return "Android 3.2 Honeycomb";
            case 14:
                return "Android 4.0 - 4.0.2 Ice Cream Sandwich";
            case 15:
                return "Android 4.0.3 - 4.0.4 Ice Cream Sandwich";
            case 16:
                return "Android 4.1 Jelly Bean";
            case 17:
                return "Android 4.2 Jelly Bean";
            case 18:
                return "Android 4.3 Jelly Bean";
            case 19:
                return "Android 4.4 KitKat";
            case 20:
                return "Android 4.4W";
            case 21:
                return "Android 5.0 Lollipop";
            case 22:
                return "Android 5.1 Lollipop";
            case 23:
                return "Android 6.0 Marshmallow";
            case 24:
                return "Android 7.0 Nougat";
            case 25:
                return "Android 7.1 Nougat";
            case 26:
                return "Android 8.0 Oreo";
            case 27:
                return "Android 8.1 Oreo";
            case 28:
                return "Android 9.0 Pie";
        }
        return "版本低于3.0" + ver;
    }
}
