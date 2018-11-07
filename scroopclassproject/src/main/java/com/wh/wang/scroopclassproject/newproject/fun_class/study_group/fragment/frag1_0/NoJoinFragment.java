package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetailEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGJoinEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoJoinFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener
        , ActionRuleFragment.OnRuleHeightClickListener, IntroduceFragment.OnWebViewHeightClickListener {

    private LinearLayout mJoinHead;
    private TextView mIntroduce;
    private TextView mRule;
    private FrameLayout mCutLine;
    private TextView mJoinGroup;
    private TextView[] mTitleTvs;
    private int[] mTitlePos;
    private int mStartX;
    private boolean isMeasured;
    private ViewPager mIntroduceVp;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private IntroduceFragment mIntroduceFragment = new IntroduceFragment();
    ;
    private ActionRuleFragment mActionRuleFragment = new ActionRuleFragment();
    private String mVid;
    private SGDetailEntity.InfoBeanX.UserCountBean mUserCount;
    private SGDetailEntity.InfoBeanX.TeacherBean mTeacherBean;
    private List<String> mAvators;
    private TextView mJoinNum;

    private StudyGroupPresenter mStudyGroupPresenter;
    private String mPid;
    private String mUserId;
    private String mIsEnd;

    public NoJoinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
            mIsEnd = bundle.getString("isEnd", "0");
            mVid = bundle.getString("vid", "");
            mPid = bundle.getString("pid", "");
            mUserCount = (SGDetailEntity.InfoBeanX.UserCountBean) bundle.getSerializable("avatars");
            mTeacherBean = (SGDetailEntity.InfoBeanX.TeacherBean) bundle.getSerializable("teacher");
            if ("0".equals(mIsEnd)) {
                LoadingUtils.getInstance().showNetLoading(getContext());
            }
        }


        View view = inflater.inflate(R.layout.fragment_no_join, container, false);
        mJoinHead = (LinearLayout) view.findViewById(R.id.join_head);
        mIntroduce = (TextView) view.findViewById(R.id.introduce);
        mRule = (TextView) view.findViewById(R.id.rule);
        mCutLine = (FrameLayout) view.findViewById(R.id.cut_line);
        mTitleTvs = new TextView[]{mIntroduce, mRule};
        mJoinGroup = (TextView) view.findViewById(R.id.join_group);
        mIntroduceVp = (ViewPager) view.findViewById(R.id.introduce_vp);
        mJoinNum = (TextView) view.findViewById(R.id.join_num);

        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!isMeasured) {
                    //获取标题位置
                    int[] coursePos = new int[2];
                    int[] rulePos = new int[2];
                    mIntroduce.getLocationInWindow(coursePos);
                    mRule.getLocationInWindow(rulePos);
                    mTitlePos = new int[]{0, rulePos[0] - coursePos[0]};
                    mStartX = mTitlePos[0];
                    isMeasured = true;

                }
                return true;
            }
        });

        initData();

        initListener();
        return view;
    }

    private void initListener() {
        mIntroduce.setOnClickListener(this);
        mRule.setOnClickListener(this);
        mIntroduceVp.addOnPageChangeListener(this);
        mIntroduceFragment.setOnWebViewHeightClickListener(this);
        mActionRuleFragment.setOnRuleHeightClickListener(this);
        if("0".equals(mIsEnd)){
            mJoinGroup.setOnClickListener(this);
        }else{
            mJoinGroup.setOnClickListener(null);
        }

    }

    private void initData() {
        if (mUserCount != null) {
            mAvators = mUserCount.getAvator();
            mJoinNum.setText((mUserCount.getCount()==null?"0":mUserCount.getCount()) + "人已报名");
        }

        initHead(6);

        Bundle bundle = new Bundle();
        bundle.putString("vid", mVid);
        bundle.putSerializable("teacher", mTeacherBean);
        mIntroduceFragment.setArguments(bundle);
        mFragmentList.add(mIntroduceFragment);
        mFragmentList.add(mActionRuleFragment);
        mIntroduceVp.setAdapter(new BoutiqueVPAdapter(getChildFragmentManager(), mFragmentList));

    }

    private void initHead(int count) {
        for (int i = 0; i < count; i++) {
            RoundedImageView head = new RoundedImageView(getContext());
            head.setOval(true);
            head.setScaleType(ImageView.ScaleType.CENTER_CROP);
            head.setImageResource(R.drawable.rank_default);
            if (mAvators != null && mAvators.size() > 0) {
                if (mAvators.size() - 1 >= i) {
                    Glide.with(getContext()).load(mAvators.get(i)).centerCrop().into(head);
                }
            }
            mJoinHead.addView(head);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.width = DisplayUtil.dip2px(getContext(), 32);
            layoutParams.height = DisplayUtil.dip2px(getContext(), 32);
            if (i > 0) {
                layoutParams.setMargins(DisplayUtil.dip2px(MyApplication.mApplication, 10), 0, 0, 0);
            }
            head.setLayoutParams(layoutParams);

        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        ViewGroup.LayoutParams layoutParams = mCutLine.getLayoutParams();
        layoutParams.width = mIntroduce.getWidth();
        mCutLine.setLayoutParams(layoutParams);

    }


    private void selectInfoAni(final int pos) {
        TranslateAnimation translateAnimation = new TranslateAnimation(mStartX, mTitlePos[pos], 0, 0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTitleTvs[pos].setTextColor(getResources().getColor(R.color.main_color));
                for (int i = 0; i < mTitleTvs.length; i++) {
                    if (i != pos) {
                        mTitleTvs[i].setTextColor(getResources().getColor(R.color.originator));
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mCutLine.startAnimation(translateAnimation);
        mStartX = mTitlePos[pos];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.introduce:
                mIntroduceVp.setCurrentItem(0);
                break;
            case R.id.rule:
                mIntroduceVp.setCurrentItem(1);
                break;
            case R.id.join_group:
                LoadingUtils.getInstance().showNetLoading(getContext());
                if (mStudyGroupPresenter == null)
                    mStudyGroupPresenter = new StudyGroupPresenter();
                mStudyGroupPresenter.joinStudyGroup(mUserId, mPid, mVid, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        //TODO 网络访问中的临界点
                        LoadingUtils.getInstance().hideNetLoading();
                        SGJoinEntity sgJoinEntity = (SGJoinEntity) value[0];
                        if("200".equals(sgJoinEntity.getCode())){
                            if (mOnJoinGroupClickListener != null) {
                                mOnJoinGroupClickListener.onJoinGroupClick();
                            }
                        }
                        Toast.makeText(MyApplication.mApplication, sgJoinEntity.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFault(String error) {
                        LoadingUtils.getInstance().hideNetLoading();
                        Log.e("DH_ERROR","SG_JOINED:"+error);
                    }
                });
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup.LayoutParams layoutParams = mIntroduceVp.getLayoutParams();
        if (position == 0) {
            layoutParams.height = mWebHeight;
        } else {
            layoutParams.height = mRuleHeight;
        }
        mIntroduceVp.setLayoutParams(layoutParams);
        selectInfoAni(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private int mWebHeight = 0;
    private int mRuleHeight = 0;

    @Override
    public void onWebViewHeightClick(int h) {
        mWebHeight = h;// DisplayUtil.dip2px(getContext(),h);
        Log.e("DH_H", "web:" + h);
        ViewGroup.LayoutParams layoutParams = mIntroduceVp.getLayoutParams();
        layoutParams.height = mWebHeight;
        mIntroduceVp.setLayoutParams(layoutParams);
        LoadingUtils.getInstance().hideNetLoading();
    }

    @Override
    public void onRuleHeightClick(int h) {
        mRuleHeight = h;//DisplayUtil.dip2px(getContext(),h);
        Log.e("DH_H", "rule:" + h);
    }


    private OnJoinGroupClickListener mOnJoinGroupClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnJoinGroupClickListener) {
            mOnJoinGroupClickListener = (OnJoinGroupClickListener) activity;
        }
    }

    public interface OnJoinGroupClickListener {
        void onJoinGroupClick();
    }
}
