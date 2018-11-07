package com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.activity.TableActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MyStudyPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag.AlreadyBuyFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag.CollectFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag.DownloadFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag.StudyRecordFragment;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils.getStatusBarHeight;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyStudyFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private FrameLayout mScrollBar;
    private int mPhoneW;
    private int mPhoneH;
    private TextView mReadTvAlreadyBuy;
    private TextView mReadTvStudyHistory;
    private TextView mReadTvCollect;
    private TextView mReadTvDownload;
    private TextView[] mTitleTvs;
    private ImageView mMyStudySchedule;
    private String mMobile;
    private String mUserId;
    private RoundedImageView mMyStudyAvatar;
    private TextView mMyStudyName;
    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPager mMyStudyVp;
    private FragmentManager supportFragmentManager;
    private MyStudyPresenter mMyStudyPresenter = new MyStudyPresenter();
    private AlreadyBuyFragment mAlreadyBuyFragment;
    private CollectFragment mCollectFragment;
    private StudyRecordFragment mStudyRecordFragment;
    private AppBarLayout mAppBar;
    private TextView mMyStudyLogin;


    public MyStudyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_study, container, false);
        initView(view);
        initFrag();
        initSize();
        initListener();
        return view;
    }

    private void initFrag() {
        supportFragmentManager = getActivity().getSupportFragmentManager();
        mAlreadyBuyFragment = new AlreadyBuyFragment();
        mCollectFragment = new CollectFragment();
        mStudyRecordFragment = new StudyRecordFragment();
        mFragments.add(mAlreadyBuyFragment);
        mFragments.add(mStudyRecordFragment);
        mFragments.add(mCollectFragment);
        mFragments.add(new DownloadFragment());
        mMyStudyVp.setAdapter(new BoutiqueVPAdapter(supportFragmentManager, mFragments));
        mMyStudyVp.setOffscreenPageLimit(4);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyStudyVp.setCurrentItem(location);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        Log.e("DH_USERID", mUserId);
        if (StringUtils.isNotEmpty(mUserId) && StringUtils.isNotEmpty(mMobile)) {
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "avatar", "");
            String name = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
            mMyStudyLogin.setOnClickListener(null);
            mMyStudyLogin.setVisibility(View.GONE);
            Glide.with(MyApplication.mApplication).load(avatar).centerCrop().into(mMyStudyAvatar);
            mMyStudyName.setText(name);
        } else {
            mMyStudyLogin.setOnClickListener(this);
            mMyStudyLogin.setVisibility(View.VISIBLE);
            mMyStudyAvatar.setImageBitmap(null);
            mMyStudyName.setText("点击登陆");
        }
    }

    private void initListener() {
        mReadTvAlreadyBuy.setOnClickListener(this);
        mReadTvStudyHistory.setOnClickListener(this);
        mReadTvCollect.setOnClickListener(this);
        mReadTvDownload.setOnClickListener(this);
        mMyStudyVp.addOnPageChangeListener(this);
        mMyStudySchedule.setOnClickListener(this);
    }

    private void initView(View view) {
        mScrollBar = (FrameLayout) view.findViewById(R.id.scroll_bar);
        mReadTvStudyHistory = (TextView) view.findViewById(R.id.read_tv_study_history);
        mReadTvCollect = (TextView) view.findViewById(R.id.read_tv_collect);
        mReadTvDownload = (TextView) view.findViewById(R.id.read_tv_download);
        mMyStudyAvatar = (RoundedImageView) view.findViewById(R.id.my_study_avatar);
        mMyStudyName = (TextView) view.findViewById(R.id.my_study_name);
        mMyStudyVp = (ViewPager) view.findViewById(R.id.my_study_vp);
        mMyStudySchedule = (ImageView) view.findViewById(R.id.my_study_schedule);
        mAppBar = (AppBarLayout) view.findViewById(R.id.app_bar);
        mReadTvAlreadyBuy = (TextView) view.findViewById(R.id.read_tv_already_buy);
        mMyStudyLogin = (TextView) view.findViewById(R.id.my_study_login);

        mTitleTvs = new TextView[]{mReadTvAlreadyBuy,mReadTvStudyHistory, mReadTvCollect, mReadTvDownload};
        view.findViewById(R.id.content).setPadding(0, getStatusBarHeight(getContext()), 0, 0);
    }


    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneW = d.widthPixels;
        mPhoneH = d.heightPixels;
        ViewGroup.LayoutParams layoutParams = mScrollBar.getLayoutParams();
        layoutParams.width = mPhoneW / 4;
        mScrollBar.setLayoutParams(layoutParams);
    }

    private int startX = 0;

    private void selectInfoAni(final int item) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mPhoneW / 4 * item, 0, 0);
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
        mScrollBar.startAnimation(translateAnimation);
        startX = mPhoneW / 4 * item;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.read_tv_already_buy:
                mMyStudyVp.setCurrentItem(0);
                break;
            case R.id.read_tv_study_history:
                mMyStudyVp.setCurrentItem(1);
                break;
            case R.id.read_tv_collect:
                mMyStudyVp.setCurrentItem(2);
                break;
            case R.id.read_tv_download:
                mMyStudyVp.setCurrentItem(3);
                break;

            case R.id.my_study_login:
                intent = new Intent(getContext(), LoginNewActivity.class);
                startActivity(intent);
                break;
            case R.id.my_study_schedule:
                intent = new Intent(getContext(), TableActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        location = position;
    }

    @Override
    public void onPageSelected(int position) {
        selectInfoAni(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //主页跳转过来时fragment的切换
    private int location;
    public void setLocation(int myStudy_location) {
        location = myStudy_location;
        if(mMyStudyVp!=null){
            mMyStudyVp.setCurrentItem(location);
        }
    }
}
