package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.OnShareClickListener;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0.BindZFBActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0.CommitRemarkActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0.SGRankActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0.SGPunchCard2_0Adapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.PunchSpacesItemDecoration;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SGJoinedFragment extends Fragment implements View.OnClickListener {
    private SGDetail2_0Entity.InfoBeanX mInfoBean;
    private RoundedImageView mAvatarImg;
    private TextView mName;
    private TextView mDayNum;
    private RecyclerView mPunchCardList;
    private TextView mCourseInform;
    private RelativeLayout mCourseInfo;
    //    private RecyclerView mRemarkList;
    private FrameLayout mQr;
    private TextView mQrTv;


    private TextView mBonusInfo;
    private TextView mAlreadyPunch;
    //    private ImageView mYuanArrow;
//    private TextView mRule;
    private TextView mPunchHint;
    private RelativeLayout mFinish;
    private TextView mFinishTitle;
    private TextView mFinishHint;
    private TextView mFuxi;
    private TextView mAward;
    private ImageView mBonusIcon;
    private TextView mBonusPrice;


    private TextView mCourseTitle;
    private TextView mCourseCase;
    private TextView mCommitRemark;
    private String mUserId;
    private String mNickname;
    private String mAvatar;
    private String mVid;
    //    private SGRemarkAdapter mSGRemarkAdapter;
    private String mStatus;

    //    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mPid;
    private String mType;
    private String mIs_complete;
    private int mIsStart;
    //    private List<SGRemarkBean> mSGRemarkBeanList = new ArrayList<>();

    public SGJoinedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            mInfoBean = (SGDetail2_0Entity.InfoBeanX) getArguments().getSerializable("info");
            mIsStart = getArguments().getInt("isStart");
            mPid = getArguments().getString("pid");
            mType = getArguments().getString("type");
            mUserId = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "user_id", "");
            mNickname = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "nickname", "");
            mAvatar = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "avatar", "");
            mVid = getArguments().getString("vid");
        }
        View view = inflater.inflate(R.layout.fragment_sg_joined, container, false);
        mAvatarImg = (RoundedImageView) view.findViewById(R.id.avatar);
        mName = (TextView) view.findViewById(R.id.name);
        mDayNum = (TextView) view.findViewById(R.id.day_num);
        mQr = (FrameLayout) view.findViewById(R.id.qr);
        mPunchCardList = (RecyclerView) view.findViewById(R.id.punch_card_list);
        mCourseInform = (TextView) view.findViewById(R.id.course_inform);
        mCourseTitle = (TextView) view.findViewById(R.id.course_title);
        mCourseCase = (TextView) view.findViewById(R.id.course_case);
        mCommitRemark = (TextView) view.findViewById(R.id.commit_remark);
        mCourseInfo = (RelativeLayout) view.findViewById(R.id.course_info);
//        mRemarkList = (RecyclerView) view.findViewById(R.id.remark_list);
        mBonusInfo = (TextView) view.findViewById(R.id.bonus_info);
        mAlreadyPunch = (TextView) view.findViewById(R.id.already_punch);
        mPunchHint = (TextView) view.findViewById(R.id.punch_hint);
        mFinish = (RelativeLayout) view.findViewById(R.id.finish);
        mFinishTitle = (TextView) view.findViewById(R.id.finish_title);
        mFinishHint = (TextView) view.findViewById(R.id.finish_hint);
        mQrTv = (TextView) view.findViewById(R.id.qr_tv);
        mFuxi = (TextView) view.findViewById(R.id.fuxi);
        mAward = (TextView) view.findViewById(R.id.award);
        mBonusIcon = (ImageView) view.findViewById(R.id.bonus_icon);
        mBonusPrice = (TextView) view.findViewById(R.id.bonus_price);
//        mYuanArrow = (ImageView) view.findViewById(R.id.yuan_arrow);
//        mRule = (TextView) view.findViewById(R.id.rule);
//        mRemarkList.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRemarkList.setHasFixedSize(true);
//        mRemarkList.setNestedScrollingEnabled(false);

        mPunchCardList = (RecyclerView) view.findViewById(R.id.punch_card_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPunchCardList.addItemDecoration(new PunchSpacesItemDecoration(15));
        mPunchCardList.setLayoutManager(linearLayoutManager);

        initData();
        initListener();
        String qr_ani = SharedPreferenceUtil.getStringFromSharedPreference(getContext(),"qr_ani","0");
        if ("0".equals(qr_ani)) {
            aniQr();
        }

        return view;
    }

    private void aniQr() {
        mQrTv.post(new Runnable() {
            @Override
            public void run() {
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f,1.1f,0.6f,1.1f,mQrTv.getWidth()/2,mQrTv.getHeight()/2);
                scaleAnimation.setDuration(600);
                scaleAnimation.setRepeatCount(ValueAnimator.INFINITE);
                scaleAnimation.setRepeatMode(ValueAnimator.REVERSE);
                mQrTv.startAnimation(scaleAnimation);

            }
        });
    }

    private void initListener() {
        mCommitRemark.setOnClickListener(this);
        mCourseInfo.setOnClickListener(this);
        mBonusInfo.setOnClickListener(this);
        mQr.setOnClickListener(this);
        mAward.setOnClickListener(this);
        mFuxi.setOnClickListener(this);
    }

    private void initData() {
//        getRemarkList();
        Glide.with(MyApplication.mApplication).load(mAvatar).centerCrop().into(mAvatarImg);
        mName.setText(mNickname);

        if (mInfoBean != null && mInfoBean.getInfo() != null) {

            mDayNum.setText("本课已打卡" + mInfoBean.getPunch_card() + "天");
            mCourseCase.setText("共" + mInfoBean.getCate_title_num().getCate_num() + "小节");
            if (StringUtils.isNotEmpty(mInfoBean.getCate_title_num().getCate_title())) {
                mCourseTitle.setText(mInfoBean.getCate_title_num().getCate_title());
            } else {
                mCourseTitle.setText(mInfoBean.getInfo().getTitle());
            }
//            if ("3".equals(mInfoBean.getNotice())) {
//                mCourseCase.setText(mInfoBean.getCate_title_num().getCate_num());
//                mCourseTitle.setText(mInfoBean.getCate_title_num().getCate_title());
//            }else{
//
//            }


            switch (mInfoBean.getNotice()==null?"":mInfoBean.getNotice()) {
                case "0":
                    mCourseInform.setText("即将学习");
                    break;
                case "1":
                    mCourseInform.setText("今日学习");
                    break;
                case "2":
                    mCourseInform.setText("明日预告");
                    break;
                case "3":
                    mCourseInform.setText("复习课程");
                    break;
            }

            mStatus = mInfoBean.getStatus();
            mIs_complete = mInfoBean.getIs_complete();
            if("1".equals(mIs_complete)){
                mPunchHint.setVisibility(View.GONE);
                if("1".equals(mType)){
                    mCommitRemark.setText("点击领取"+mInfoBean.getCashback()+"元奖学金");
                }else{
                    mCommitRemark.setText("查看我的奖励");
                }
//                mCommitRemark.setVisibility(View.GONE);
//                mPunchHint.setVisibility(View.GONE);
//                mAlreadyPunch.setVisibility(View.GONE);
//                mFinish.setVisibility(View.VISIBLE);
//                mAward.setText("领取奖励");
//                mFinishTitle.setText("恭喜您，已完成全部打卡");
//                if ("1".equals(mType)) {
//                    mFinishHint.setText(getBonusText(mInfoBean.getCashback()));
//                }else{
//                    mFinishHint.setText("恭喜您，获得优惠券");
//                }
            }else if("2".equals(mIs_complete)){
                mPunchHint.setVisibility(View.GONE);
                mCommitRemark.setText("报名下一场");
//                mCommitRemark.setVisibility(View.GONE);
//                mPunchHint.setVisibility(View.GONE);
//                mAlreadyPunch.setVisibility(View.GONE);
//                mFinish.setVisibility(View.VISIBLE);
//                mAward.setText("报名下一场");
//                mFinishTitle.setText("很遗憾，未完成打卡任务");
//                mFinishHint.setText("请再接再厉，期待您的再次参与");
            }else{
//                mPunchHint.setVisibility(View.VISIBLE);
//                mCommitRemark.setText("提交心得");
//                mFinish.setVisibility(View.GONE);
                if ("2".equals(mStatus)) {
                    mCommitRemark.setVisibility(View.GONE);
                    mPunchHint.setVisibility(View.GONE);
                    mAlreadyPunch.setVisibility(View.VISIBLE);
                } else {
                    mCommitRemark.setVisibility(View.VISIBLE);
                    mPunchHint.setVisibility(View.VISIBLE);
                    mAlreadyPunch.setVisibility(View.GONE);
                }
            }

            if (mIsStart==1000) {
                mBonusInfo.setText("邀请好友");
                mBonusIcon.setVisibility(View.GONE);
                mBonusPrice.setVisibility(View.GONE);
            }else{
                mBonusInfo.setText("今日已学习：" + mInfoBean.getPunch()+"人");
                if ("1".equals(mType)) {
                    mBonusIcon.setVisibility(View.VISIBLE);
                    mBonusPrice.setVisibility(View.VISIBLE);
                    mBonusPrice.setText("奖学金：" + (mInfoBean.getRealbonus() == null ? "0" : mInfoBean.getRealbonus()) + "元");
                } else {
                    mBonusIcon.setVisibility(View.GONE);
                    mBonusPrice.setVisibility(View.GONE);
                }
            }


        }

        if (mInfoBean != null) {
            if (mInfoBean.getCard_status() != null) {
                mPunchCardList.setAdapter(new SGPunchCard2_0Adapter(getContext(), mInfoBean.getCard_status()));
            }

        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.commit_remark:
                if ("0".equals(mIs_complete)) {
                    if ("1".equals(mStatus) || "2".equals(mStatus)) {
                        intent = new Intent(getContext(), CommitRemarkActivity.class);
                        intent.putExtra("vid", mVid);
                        startActivity(intent);
                    } else {
                        showNotRemark();
                    }
                }else if("1".equals(mIs_complete)){
                    if("1".equals(mType)){
                        intent = new Intent(getContext(), BindZFBActivity.class);
                        intent.putExtra("pid",mPid);
                        intent.putExtra("vid",mVid);
                        intent.putExtra("event",mInfoBean.getInfo().getEvent());
                        startActivity(intent);
                    }else{
                        if (mOnFinishCouponClickListener!=null) {
                            mOnFinishCouponClickListener.onFinishCouponClick();
                        }
                    }
                }else{
                    getActivity().finish();
                }


                break;
            case R.id.course_info:
            case R.id.fuxi:
                intent = new Intent(getContext(), NewVideoInfoActivity.class);
                intent.putExtra("id", mVid);
//                int from = "1".equals(mIs_complete)||"2".equals(mIs_complete)?0:1;
                intent.putExtra("from", 1);
                intent.putExtra("pid", mPid);
                startActivity(intent);
                break;
            case R.id.qr:
                if ("0".equals(SharedPreferenceUtil.getStringFromSharedPreference(getContext(),"qr_ani","0"))) {
                    SharedPreferenceUtil.putStringValueByKey(getContext(),"qr_ani","1");
                    mQrTv.clearAnimation();
                }

                if (mOnFinishCouponClickListener!=null) {
                    mOnFinishCouponClickListener.onQrClick();
                }

                break;
            case R.id.bonus_info:
                if (mIsStart==1000) {
                    if (mOnShareClickListener!=null) {
                        mOnShareClickListener.onShareClick();
                    }

                }else{
                    intent = new Intent(getContext(), SGRankActivity.class);
                    intent.putExtra("pid", mPid);
                    intent.putExtra("vid", mVid);
                    intent.putExtra("complete", mInfoBean.getIs_complete() + "");
                    startActivity(intent);
                }

                break;
            case R.id.award:
                if ("2".equals(mIs_complete)) {
                    getActivity().finish();
                }else if("1".equals(mIs_complete)){
                    if("1".equals(mType)){
                        intent = new Intent(getContext(), BindZFBActivity.class);
                        intent.putExtra("pid",mPid);
                        intent.putExtra("vid",mVid);
                        intent.putExtra("event",mInfoBean.getInfo().getEvent());
                        startActivity(intent);
                    }else{
                        if (mOnFinishCouponClickListener!=null) {
                            mOnFinishCouponClickListener.onFinishCouponClick();
                        }
                    }
                }else{
                    Toast.makeText(MyApplication.mApplication, "状态异常", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnFinishCouponClickListener){
            mOnFinishCouponClickListener = (OnFinishCouponClickListener) activity;
        }
        if (activity instanceof OnShareClickListener) {
            mOnShareClickListener = (OnShareClickListener) activity;
        }
    }

    private OnFinishCouponClickListener mOnFinishCouponClickListener;
    private OnShareClickListener mOnShareClickListener;

    public interface OnFinishCouponClickListener{
        void onFinishCouponClick();
        void onQrClick();
    }

    private Dialog mRemarkDig;

    private void showNotRemark() {
        mRemarkDig = new Dialog(getContext(), R.style.MyDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dig_sg_not_remark, null, false);
        mRemarkDig.setContentView(view);
        TextView sure = (TextView) mRemarkDig.findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemarkDig.dismiss();
            }
        });
        Window dialogWindow = mRemarkDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(MyApplication.mApplication, 283);
        lp.height = DisplayUtil.dip2px(MyApplication.mApplication, 120);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        mRemarkDig.show();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private Spannable getBonusText(String bonus) {

        Spannable sp = new SpannableString("恭喜您，获得 "+bonus+" 元奖学金");
        sp.setSpan(new AbsoluteSizeSpan(20,true),7,7+bonus.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#86B93F")),7,7+bonus.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return sp;
    }
//    @Override
//    public void onRemarkClick(String id) {
//        Intent intent = new Intent(getContext(), SGRemarkDetailActivity.class);
//        startActivity(intent);
//    }


//    private int mPage = 0;
//    public void getRemarkList(){
//        LoadingUtils.getInstance().showNetLoading(getContext());
//        mStudyGroupPresenter.getSGRemark(mUserId,mPid,mVid,mType,String.valueOf(mPage),"10",this);
//    }

//    @Override
//    public void onSuccess(Object... value) {
//        LoadingUtils.getInstance().hideNetLoading();
//        SGRemarkEntity sgRemarkEntity = (SGRemarkEntity) value[0];
//        if (sgRemarkEntity.getInfo()!=null&&sgRemarkEntity.getInfo().size()>0) {
//            if(mPage==0){
//                mSGRemarkBeanList.clear();
//            }
//
//            mSGRemarkBeanList.addAll(sgRemarkEntity.getInfo());
//            if (mSGRemarkAdapter!=null) {
//                mSGRemarkAdapter.notifyDataSetChanged();
//            }else{
//                mSGRemarkAdapter = new SGRemarkAdapter(getContext(), mSGRemarkBeanList, "0");
//                mRemarkList.setAdapter(mSGRemarkAdapter);
//                mSGRemarkAdapter.setOnRemarkItemClickListener(this);
//            }
//        }else{
//            Toast.makeText(MyApplication.mApplication, "没有更多心得了", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onFault(String error) {
//        LoadingUtils.getInstance().hideNetLoading();
//        Log.e("DH_ERROR_SG_REMARK",error);
//    }
}
