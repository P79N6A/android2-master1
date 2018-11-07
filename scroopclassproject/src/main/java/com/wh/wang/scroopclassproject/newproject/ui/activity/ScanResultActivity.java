package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.scan_resule_frag.FailFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.scan_resule_frag.OldResultFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.scan_resule_frag.SignedFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.scan_resule_frag.SuccessFragment;

public class ScanResultActivity extends BaseActivity implements View.OnClickListener {
    public static final int OLD = 0; //旧界面
    public static final int SUCCESS = 2; //签到成功
    public static final int SIGNED = 1; //签到过
    public static final int FAIL = -1; //失效或过期
    public static final int NO_LIMIT = -2; //失效或过期
    private int mType;
    private FragmentManager mFragmentManager;
    private String mName;
    private String mTel;
    private String mTitle;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_scan_result;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mType = getIntent().getIntExtra("type", FAIL);
        if (mType!=FAIL) {
//            getIntent().getStringExtra("id")
            mName = getIntent().getStringExtra("name");
            mTel = getIntent().getStringExtra("tel");
            if(mType!=SIGNED){
                mTitle = getIntent().getStringExtra("title");
            }
        }
    }

    @Override
    public void initView() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();


        if(mType == FAIL){
            FailFragment failFragment = new FailFragment();
            fragmentTransaction.add(R.id.result,failFragment);
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("name",mName);
            bundle.putString("tel",mTel);
            bundle.putString("title",mTitle);

            if(mType == OLD){
                OldResultFragment oldResultFragment = new OldResultFragment();
                oldResultFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.result,oldResultFragment);
            }else if(mType == SUCCESS){
                SuccessFragment successFragment = new SuccessFragment();
                successFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.result,successFragment);
            }else if(mType == SIGNED){
                SignedFragment signedFragment = new SignedFragment();
                signedFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.result,signedFragment);
            }
        }

        fragmentTransaction.commit();
    }

    @Override
    public void initDatas() {
        mTitleC.setText("签到结果");
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initOther() {

    }

    @Override
    public void onClick(View v) {

    }
}
