package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

public class ExamineDefeatActivity extends Activity {
    private TextView mTitlebarbackssName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine_defeat);
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamineDefeatActivity.this.finish();
            }
        });
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssName.setText("审核未通过");
        findViewById(R.id.company_l).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.company_r).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamineDefeatActivity.this,EnterpriseApplyActivity.class);
                intent.putExtra("state","0");
                startActivity(intent);
                finish();
            }
        });
    }
}
