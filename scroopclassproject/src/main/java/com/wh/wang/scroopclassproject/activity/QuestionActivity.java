package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

public class QuestionActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.activity_question, "帮助");
        context = this;
    }
}
