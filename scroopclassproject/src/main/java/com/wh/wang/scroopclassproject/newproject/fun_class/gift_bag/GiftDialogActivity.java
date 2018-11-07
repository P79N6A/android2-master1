package com.wh.wang.scroopclassproject.newproject.fun_class.gift_bag;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;

public class GiftDialogActivity extends BaseActivity {
    private TextView mGet;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_gift_dialog;
    }

    @Override
    protected boolean isUseTitle() {
        return false;
    }

    @Override
    public void initIntent() {
        mGet = (TextView) findViewById(R.id.get);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initDatas() {
        mGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiftDialogActivity.this,GiftBagActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initOther() {

    }
}
