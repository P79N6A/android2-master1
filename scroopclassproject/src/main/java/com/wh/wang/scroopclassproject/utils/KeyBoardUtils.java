package com.wh.wang.scroopclassproject.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by wang on 2017/12/21.
 * <p>
 * 打开或关闭软键盘
 */

public class KeyBoardUtils {
    private static InputMethodManager inputManager;
    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null) {
                if (inputManager == null) {
                    inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                }
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void showKeyboard(Activity activity) {
        if(activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE){
            if (inputManager == null) {
                inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            inputManager.toggleSoftInput(0,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}
