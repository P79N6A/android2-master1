package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;

public class CouponRuleActivity extends BaseActivity {


    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_coupon_rule;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initDatas() {
        mTitleC.setText("优惠券");
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                        .parse("tel:" + "400-900-3650"));
                startActivity(intentNO);
            }
        });
    }

    @Override
    public void initOther() {

    }
}
