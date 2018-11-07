package com.wh.wang.scroopclassproject.base;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

    }
    
}
