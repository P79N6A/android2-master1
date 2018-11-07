package com.wh.wang.scroopclassproject.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class JDToast extends Toast {

    private static String lastMsg = null;
    private static Handler mHandler;

    public JDToast(Context context) {
        super(context);
    }

    public static void showText(Context context, String msg) {
        showText(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showText(Context context, String msg, int length) {
        Log.i("showText", msg + "");
        if (TextUtils.isEmpty(msg) || lastMsg != null || context == null) {
            return;
        }

        lastMsg = msg;

        Toast toast = makeText(context, msg, length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        if (mHandler == null) {
            mHandler = new Handler();
        }

        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 2000);
    }

    /**
     * 屏蔽2秒之内重复显示的Toast提示
     *
     * @param context
     * @param msg
     */
    public static void showShortText(Context context, String msg) {
        Log.i("showText", msg != null ? msg : "msg==null");
        if (TextUtils.isEmpty(msg) || lastMsg != null) {
            return;
        }

        lastMsg = msg;
        makeText(context, msg, Toast.LENGTH_SHORT).show();

        if (mHandler == null) {
            mHandler = new Handler();
        }

        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, 2000);
    }

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lastMsg = null;
        }
    };

}
