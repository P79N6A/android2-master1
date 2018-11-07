package com.wh.wang.scroopclassproject.newproject.ui.myinterface;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by Administrator on 2017/12/14.
 */

public class H5Interface {
    private Activity activity;

    public H5Interface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void clickActivity(){

    }
}
