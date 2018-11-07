package com.wh.wang.scroopclassproject.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

public class AboutActivity extends Activity {
    private TextView mAboutVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.about, "关于我们");
        mAboutVersion = (TextView) findViewById(R.id.about_version);
        mAboutVersion.setText("当前版本 V"+StringUtils.getAppInfo(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
