package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.callback.OnRegisterNextClickListener;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.RegisterFragAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag.PersonInfoFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag.ShopInfoFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag.SubmitResultFragment;
import com.wh.wang.scroopclassproject.newproject.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseApplyActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener,OnRegisterNextClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private ImageView mProgressTitle;
    private NoScrollViewPager mRegisterContent;
    private TextView mLookOther;

    private int[] progress_id = {R.drawable.step_1_bg,R.drawable.step_2_bg,R.drawable.step_3_bg};
    private LinearLayout mExamine;
    private RelativeLayout mRegister;

    private ShopInfoFragment mShopInfoFragment = new ShopInfoFragment();
    private PersonInfoFragment mPersonInfoFragment = new PersonInfoFragment();
    private SubmitResultFragment mSubmitResultFragment= new SubmitResultFragment();
    private List<Fragment> mFragmentList = new ArrayList<>();

    //0：注册  1：审核
    private String mState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_apply);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mRegisterContent = (NoScrollViewPager) findViewById(R.id.register_content);
        mLookOther = (TextView) findViewById(R.id.look_other);

        mProgressTitle = (ImageView) findViewById(R.id.progress_title);
        mExamine = (LinearLayout) findViewById(R.id.examine);
        mRegister = (RelativeLayout) findViewById(R.id.register);
    }


    private void initData() {
        mState = getIntent().getStringExtra("state");
        if(mState==null||"".equals(mState)||"0".equals(mState)){
            mTitlebarbackssName.setText("填写资料");
            mExamine.setVisibility(View.GONE);
            mRegister.setVisibility(View.VISIBLE);
            iniFrags();
        }else if("1".equals(mState)){
            mTitlebarbackssName.setText("审核中");
            mExamine.setVisibility(View.VISIBLE);
            mRegister.setVisibility(View.GONE);
        }
    }

    private void iniFrags() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        mFragmentList.add(mShopInfoFragment);
        mFragmentList.add(mPersonInfoFragment);
        mFragmentList.add(mSubmitResultFragment);
        mRegisterContent.setAdapter(new RegisterFragAdapter(supportFragmentManager,mFragmentList));
    }

    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mRegisterContent.addOnPageChangeListener(this);
        mLookOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebarbackss_imageViewback:
                if(mCurrentPos==1){
                    registerProgress(0);
                }else{
                    finish();
                }
                break;
            case R.id.look_other:
                Intent intent = new Intent();
                // 设置结果，并进行传送
                intent.putExtra("page",0);
                setResult(Constant.SEARCH_RES, intent);
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if(mCurrentPos==1){
            registerProgress(0);
        }else{
            super.onBackPressed();
        }
    }

    private int mCurrentPos = 0;
    @Override
    public void OnNextClick(int pos) {
        registerProgress(pos);
    }

    public void registerProgress(int pos){
        if(pos==-1){
            finish();
            return;
        }
        if(pos==2){
            mTitlebarbackssName.setText("提交成功");
        }else{
            mTitlebarbackssName.setText("填写资料");
        }
        mCurrentPos = pos;
        mRegisterContent.setCurrentItem(pos);
        mProgressTitle.setImageResource(progress_id[pos]);
    }
}
