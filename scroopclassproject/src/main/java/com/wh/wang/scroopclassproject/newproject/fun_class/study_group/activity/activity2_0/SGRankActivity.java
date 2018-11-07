package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.SGRankFragment;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;

import java.util.ArrayList;
import java.util.List;

public class SGRankActivity extends BaseActivity {
    private ViewPager mRankVp;
    private ImageView mTitleL;
    private TextView mToday;
    private TextView mSOrF;
    private FrameLayout mLine;
    private TextView[] mTitleTvs;
    private LinearLayout mSelectLl;


    private String mPid;
    private String mVid;
    private SGRankFragment mTodayRank = new SGRankFragment();
    private SGRankFragment mSOrFRank = new SGRankFragment();

    private List<Fragment> mRankList = new ArrayList<>();
    private String mComplete;

    private int mMoveWidth;
    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_sg_rank;
    }

    @Override
    protected boolean isUseTitle() {
        return false;
    }

    @Override
    public void initIntent() {
        mPid = getIntent().getStringExtra("pid");
        mVid = getIntent().getStringExtra("vid");
        mComplete = getIntent().getStringExtra("complete");
    }

    @Override
    public void initView() {

        mRankVp = (ViewPager) findViewById(R.id.rank_vp);

        mTitleL = (ImageView) findViewById(R.id.title_l);
        mToday = (TextView) findViewById(R.id.today);
        mSOrF = (TextView) findViewById(R.id.sOrF);
        mLine = (FrameLayout) findViewById(R.id.line);
        mSelectLl = (LinearLayout) findViewById(R.id.select_ll);
        mTitleTvs = new TextView[]{mToday,mSOrF};
        if (!"0".equals(mComplete)) {
            mToday.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
        }else{

            mLine.setVisibility(View.VISIBLE);
            mToday.setVisibility(View.VISIBLE);
            mSelectLl.post(new Runnable() {
                @Override
                public void run() {
                    mMoveWidth = mSelectLl.getMeasuredWidth();
                    ViewGroup.LayoutParams layoutParams = mLine.getLayoutParams();
                    layoutParams.width = mMoveWidth/2;
                    mLine.setLayoutParams(layoutParams);
                }
            });
        }

    }

    @Override
    public void initDatas() {
        if ("0".equals(mComplete)) {
            Bundle today = new Bundle();
            today.putString("pid",mPid);
            today.putString("vid",mVid);
            today.putString("type","1");
            mTodayRank.setArguments(today);
            mRankList.add(mTodayRank);
        }


        Bundle SOrF = new Bundle();
        SOrF.putString("pid",mPid);
        SOrF.putString("vid",mVid);
        SOrF.putString("type","2");
        mSOrFRank.setArguments(SOrF);
        mRankList.add(mSOrFRank);

        mRankVp.setAdapter(new BoutiqueVPAdapter(getSupportFragmentManager(),mRankList));
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRankVp.setCurrentItem(0);
            }
        });
        mSOrF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRankVp.setCurrentItem(1);
            }
        });

        mRankVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
    }

    @Override
    public void initOther() {

    }


    private int startX = 0;
    private int itemCount = 2;
    private void selectInfoAni(final int item) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mMoveWidth / itemCount * item, 0, 0);
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
        mLine.startAnimation(translateAnimation);
        startX = mMoveWidth / itemCount * item;
    }
}
