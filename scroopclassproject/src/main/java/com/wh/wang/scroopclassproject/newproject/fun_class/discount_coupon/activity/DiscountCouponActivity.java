package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.fragment.CouponListFragment;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class DiscountCouponActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView mUnused;
    private TextView mUsed;
    private TextView mPastDue;
    private FrameLayout mMoveLine;
    private ViewPager mCouponVp;
    private TextView[] mTitleTvs;
    private int mPhoneW;

    private CouponListFragment mUnuseFragment = new CouponListFragment();
    private CouponListFragment mUsedFragment = new CouponListFragment();
    private CouponListFragment mPassDueFragment = new CouponListFragment();
    private List<Fragment> mCouponFrags = new ArrayList<>();
    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_discount_coupon;
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
        mUnused = (TextView) findViewById(R.id.unused);
        mUsed = (TextView) findViewById(R.id.used);
        mPastDue = (TextView) findViewById(R.id.past_due);
        mMoveLine = (FrameLayout) findViewById(R.id.move_line);
        mCouponVp = (ViewPager) findViewById(R.id.coupon_vp);
        mCouponVp.setOffscreenPageLimit(3);
        mTitleTvs = new TextView[]{mUnused,mUsed,mPastDue};

        mTitleR.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {
        mTitleC.setText("优惠券");
        mPhoneW = ScreenUtils.getScreenWidth(this);
        ViewGroup.LayoutParams layoutParams = mMoveLine.getLayoutParams();
        layoutParams.width = mPhoneW/itemCount;
        mMoveLine.setLayoutParams(layoutParams);
        Bundle unUseBundle = new Bundle();
        unUseBundle.putString("status","1");
        mUnuseFragment.setArguments(unUseBundle);
        mCouponFrags.add(mUnuseFragment);

        Bundle usedBundle = new Bundle();
        usedBundle.putString("status","2");
        mUsedFragment.setArguments(usedBundle);
        mCouponFrags.add(mUsedFragment);

        Bundle passDueBundle = new Bundle();
        passDueBundle.putString("status","3");
        mPassDueFragment.setArguments(passDueBundle);
        mCouponFrags.add(mPassDueFragment);

        mCouponVp.setAdapter(new BoutiqueVPAdapter(getSupportFragmentManager(),mCouponFrags));
    }

    @Override
    public void initListener() {
        mUnused.setOnClickListener(this);
        mUsed.setOnClickListener(this);
        mPastDue.setOnClickListener(this);
        mCouponVp.addOnPageChangeListener(this);
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscountCouponActivity.this,CouponRuleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initOther() {

    }

    private int startX = 0;
    private int itemCount = 3;
    private void selectInfoAni(final int item) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mPhoneW / itemCount * item, 0, 0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTitleTvs[item].setTextColor(getResources().getColor(R.color.t_select));
                for (int i = 0; i < mTitleTvs.length; i++) {
                    if (i != item) {
                        mTitleTvs[i].setTextColor(getResources().getColor(R.color.t_unselect));
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mMoveLine.startAnimation(translateAnimation);
        startX = mPhoneW / itemCount * item;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unused:
                mCouponVp.setCurrentItem(0);
                break;
            case R.id.used:
                mCouponVp.setCurrentItem(1);
                break;
            case R.id.past_due:
                mCouponVp.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectInfoAni(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
