package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.PersonRankFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.TotalRankFragment;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class StudyGroupRankActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private RelativeLayout mRankTitle;
    private TextView mRankOfDay;
    private TextView mRankOfAll;
    private FrameLayout mMoveLine;
    private ViewPager mRankContent;
    private LinearLayout mRankFragTitle;


    private TextView[] mTitleTvs;
    private int mPhoneW;
    private RoundedImageView mPerRankAvatar;

    private PersonRankFragment mPersonFragment = new PersonRankFragment();
    private TotalRankFragment mTotalFragment = new TotalRankFragment();

    private List<Fragment> mRankFrags = new ArrayList<>();
    private ImageView mTitleL;
    private RelativeLayout mRankMenu;

    private String mVid;
    private String mPid;
    private String mStatus;
    private String mComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group_rank);
        initView();

        initData();

        initListener();
    }

    public void initView() {
        mRankTitle = (RelativeLayout) findViewById(R.id.rank_title);
        mRankOfDay = (TextView) findViewById(R.id.rank_of_day);
        mRankOfAll = (TextView) findViewById(R.id.rank_of_all);
        mMoveLine = (FrameLayout) findViewById(R.id.move_line);
        mRankContent = (ViewPager) findViewById(R.id.rank_content);
        mTitleL = (ImageView) findViewById(R.id.title_l);
        mRankFragTitle = (LinearLayout) findViewById(R.id.rank_frag_title);

        mRankMenu = (RelativeLayout) findViewById(R.id.rank_menu);

        mTitleTvs = new TextView[]{mRankOfDay,mRankOfAll};
    }

    public void initData() {
        Intent intent = getIntent();
        mVid = intent.getStringExtra("vid");
        mPid = intent.getStringExtra("pid");
        mComplete = intent.getStringExtra("complete");

        mPhoneW = ScreenUtils.getScreenWidth(this);
        ViewGroup.LayoutParams layoutParams = mMoveLine.getLayoutParams();
        layoutParams.width = mPhoneW/2;
        mMoveLine.setLayoutParams(layoutParams);


        if("0".equals(mComplete)){
            mRankMenu.setVisibility(View.VISIBLE);
            Bundle perBundle = new Bundle();
            perBundle.putString("vid",mVid);
            perBundle.putString("pid",mPid);
//            perBundle.putString("type","0");
            mPersonFragment.setArguments(perBundle);
            mRankFrags.add(mPersonFragment);
        }else{
            mRankMenu.setVisibility(View.GONE);
        }

        Bundle totalBundle = new Bundle();
        totalBundle.putString("vid",mVid);
        totalBundle.putString("pid",mPid);
//        totalBundle.putString("type","2");
        mTotalFragment.setArguments(totalBundle);
        mRankFrags.add(mTotalFragment);

        mRankContent.setAdapter(new BoutiqueVPAdapter(getSupportFragmentManager(),mRankFrags));

    }

    public void initListener() {
        mRankContent.addOnPageChangeListener(this);
        mRankOfDay.setOnClickListener(this);
        mRankOfAll.setOnClickListener(this);
        mTitleL.setOnClickListener(this);
    }

    private int startX = 0;

    private void selectInfoAni(final int item) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mPhoneW / 2 * item, 0, 0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTitleTvs[item].setTextColor(Color.parseColor("#FFFFFF"));
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
        startX = mPhoneW / 2 * item;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rank_of_day:
                mRankContent.setCurrentItem(0);
                break;
            case R.id.rank_of_all:
                mRankContent.setCurrentItem(1);
                break;
            case R.id.title_l:
                finish();
                break;
        }
    }
}
