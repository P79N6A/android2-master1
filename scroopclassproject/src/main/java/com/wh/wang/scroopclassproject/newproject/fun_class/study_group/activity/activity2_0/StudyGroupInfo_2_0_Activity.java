package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.SelectCouponActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.OnJoinStudyGroupClickListener;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.OnShareClickListener;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0.SGRemarkAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.BannerNotStartedFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.BannerProgressFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.BannerFinishFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.NoJoinChallengeFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.NoJoinFreeFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.SGJoinedFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGJoinEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRemarkBean;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRemarkEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity.VipListActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CommentPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.SaveBitmapAsync;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseType;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_REQUEST;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_RESULT;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.SDK_PAY_FLAG;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

public class StudyGroupInfo_2_0_Activity extends BaseActivity implements OnResultListener, BannerNotStartedFragment.OnActionStartClickListener,
        OnJoinStudyGroupClickListener, OnLoadMoreListener, SGRemarkAdapter.OnRemarkItemClickListener,SGJoinedFragment.OnFinishCouponClickListener ,
        OnShareClickListener {
    private ImageView mCourseBg;
    private FrameLayout mStudyGroupHead;
    private FrameLayout mStudyGroupContent;
//    private RelativeLayout mFoot;

    private SwipeToLoadLayout mSwipeLayout;
    private FragmentManager mSupportFragmentManager;
    private BannerNotStartedFragment mBannerNotStartedFragment;

    private float mRate = 6 / 9f;
    private NoJoinChallengeFragment mNoJoinChallengeFragment;
    private NoJoinFreeFragment mNoJoinFreeFragment;

    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mVid;
    private String mPid;
    private String mUserId;
    private String mTel;
    private String mJoinStatus = "2";
    private SGDetail2_0Entity.InfoBeanX mInfo;
    private long mPublishTime;
    private long mNowTime;
    private BannerProgressFragment mBannerProgressFragment;
    private String mIsBaoMing;
    private SGJoinedFragment mSGJoinedFragment;
    private BannerFinishFragment mBannerFinishFragment;
    private Dialog mPayDialog;
    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    private PayEntity.InfoBean.WechatBean mWechat;
    private String mAlipay;
    private String mNickName;
    private TextView mRuleIcon;




    private RelativeLayout mRemarkContent;
    private ImageView mYuanArrow;
    private TextView mRule;
    private RecyclerView mRemarkList;
    private SGRemarkAdapter mSGRemarkAdapter;
    private List<SGRemarkBean> mSGRemarkBeanList = new ArrayList<>();
    private boolean mIsPay = false;
    private CommentPresenter mCommentPresenter = new CommentPresenter();
    private EmptyRecycleAdapter mEmptyRemarkAdapter;
    private Call<SGDetail2_0Entity> mSGDetail2_0;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_study_group_info_2_0;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mVid = getIntent().getStringExtra("vid");
        mPid = getIntent().getStringExtra("pid");
        mJoinStatus = getIntent().getStringExtra("type");
        mIsBaoMing = getIntent().getStringExtra("is_bao");
        Log.e("DH_DETAIL", "vid:" + mVid + "  pid:" + mPid + "  type:" + mJoinStatus);
//        mVid = "1";
//        mPid = "26";
//        mJoinStatus = "0";
//        mIsBaoMing = "0";
    }

    @Override
    public void initView() {
        mCourseBg = (ImageView) findViewById(R.id.course_bg);
        mStudyGroupHead = (FrameLayout) findViewById(R.id.study_group_head);
        mStudyGroupContent = (FrameLayout) findViewById(R.id.study_group_content);
        mSwipeLayout = (SwipeToLoadLayout) findViewById(R.id.swipe_layout);
        ViewGroup.LayoutParams layoutParams = mStudyGroupHead.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getScreenWidth(this) * mRate);
        mStudyGroupHead.setLayoutParams(layoutParams);
        mSwipeLayout.setLoadMoreEnabled(false);


        mRemarkContent = (RelativeLayout) findViewById(R.id.remark_content);
        mYuanArrow = (ImageView) findViewById(R.id.yuan_arrow);
        mRule = (TextView) findViewById(R.id.rule);
        mRemarkList = (RecyclerView) findViewById(R.id.remark_list);
        mRemarkList.setLayoutManager(new LinearLayoutManager(this));
        mRemarkList.setHasFixedSize(true);
        mRemarkList.setNestedScrollingEnabled(false);
        mSupportFragmentManager = getSupportFragmentManager();
        mRuleIcon = (TextView) findViewById(R.id.rule_icon);
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        mTitleC.setText("学习小组");
        if ("1".equals(mJoinStatus)) {
            mRuleIcon.setText("挑战班规则");
        }else{
            mRuleIcon.setText("自学班规则");
        }
//        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");

    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyGroupInfo_2_0_Activity.this, CommitRemarkActivity.class);
                intent.putExtra("vid", mVid);
                startActivity(intent);
            }
        });
        mSwipeLayout.setOnLoadMoreListener(this);

        mRuleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRuleDig(mJoinStatus);
            }
        });
    }

    @Override
    public void initOther() {
        mSGRemarkAdapter = new SGRemarkAdapter(StudyGroupInfo_2_0_Activity.this, mSGRemarkBeanList, "0", "");
        mEmptyRemarkAdapter = new EmptyRecycleAdapter(mSGRemarkAdapter, R.layout.layout_sg_remark_empty);
        mRemarkList.setAdapter(mEmptyRemarkAdapter);
        mSGRemarkAdapter.setOnRemarkItemClickListener(StudyGroupInfo_2_0_Activity.this);
    }

//    private boolean mIsCancelLogin;
    @Override
    protected void onResume() {
        super.onResume();
        mTel = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        mNickName = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        if (StringUtils.isEmpty(mUserId)) {
            Toast.makeText(MyApplication.mApplication, "您已退出,需要重新登陆", Toast.LENGTH_SHORT).show();
//            if(!mIsCancelLogin){
//                mIsCancelLogin = true;
//                Intent intent = new Intent(this, LoginNewActivity.class);
//                startActivity(intent);
//            }else{
//
//            }
            finish();
            return;
        }
        if (mPayStatus == 1) {//支付完成需要做加入操作
            mPayStatus = 0;
            joinSG();
        } else {
            if (!mIsPay) {
                accessNet();
            }

        }

    }

    public int NO_START = 1000;
    public int START = 1001;
    private int mStartStatus;

    private String CHALLENGE_NO_JOIN = "1";
    private String FREE_NO_JOIN = "2";

    public void accessNet() {
        if (mSupportFragmentManager == null)
            return;
        LoadingUtils.getInstance().showNetLoading(this);
        mSGDetail2_0 = mStudyGroupPresenter.getSGDetail2_0(mUserId, mPid, mVid, mJoinStatus, this);
//        mStudyGroupPresenter.getSGDetail2_0Json(mUserId, mPid, mVid, mJoinStatus, new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//                LoadingUtils.getInstance().hideNetLoading();
//            }
//
//            @Override
//            public void onFault(String error) {
//                LoadingUtils.getInstance().hideNetLoading();
//            }
//        });

    }

    @Override
    public void onSuccess(Object... value) {
//        if (mStudyGroupHead.getChildCount() > 0) {
//            mStudyGroupHead.removeAllViews();
//        }
//        if(mStudyGroupContent.getChildCount()>0){
//            mStudyGroupContent.removeAllViews();
//        }
        if(mStudyGroupHead.getChildCount()>0){
            mStudyGroupHead.removeAllViews();
        }
        if(mStudyGroupContent.getChildCount()>0){
            mStudyGroupContent.removeAllViews();
        }
        LoadingUtils.getInstance().hideNetLoading();

        SGDetail2_0Entity sgDetail2_0Entity = (SGDetail2_0Entity) value[0];
        if (sgDetail2_0Entity.getInfo() != null) {
            mRuleIcon.setVisibility(View.GONE);
            FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
            mInfo = sgDetail2_0Entity.getInfo();
            if (mInfo.getInfo() != null) {

                SGDetail2_0Entity.InfoBeanX.InfoBean info = mInfo.getInfo();
                mQrUrl = info.getImg_qr();
                if (mTempBao==1) {
                    mTempBao=0;
                    showQrDig(mQrUrl);
                }
                Glide.with(this)
                        .load(info.getImg())
                        .centerCrop()
                        .bitmapTransform(new BlurTransformation(this, 12, 2))
                        .into(mCourseBg);
                try {
                    if (info.getPublist_shijian() != null && StringUtils.isNotEmpty(info.getPublist_shijian())) {
                        mPublishTime = Long.parseLong(info.getPublist_shijian());
                    }

                    if (info.getNow_time() != null && StringUtils.isNotEmpty(info.getNow_time())) {
                        mNowTime = Long.parseLong(info.getNow_time());
                    }

                } catch (Exception e) {
                    mPublishTime = 24 * 60 * 60 * 3;
                    mNowTime = 0;
                    Toast.makeText(MyApplication.mApplication, "数据时间格式异常", Toast.LENGTH_SHORT).show();
                }
//                mPublishTime = 10;
//                mNowTime = 0;

                if ((mPublishTime > mNowTime)) {//TODO
                    mStartStatus = NO_START;
                    if (mBannerNotStartedFragment != null && mBannerNotStartedFragment.isAdded()) {
                        fragmentTransaction.remove(mBannerNotStartedFragment);
                        mBannerNotStartedFragment = null;
                    }
                    mBannerNotStartedFragment = new BannerNotStartedFragment();
                    Bundle noStart = new Bundle();
                    noStart.putLong("public_time", mPublishTime);
                    noStart.putLong("now_time", mNowTime);
                    mBannerNotStartedFragment.setArguments(noStart);
                    fragmentTransaction.add(R.id.study_group_head, mBannerNotStartedFragment);

                } else {
                    mStartStatus = START;
                    if (mBannerNotStartedFragment != null && mBannerNotStartedFragment.isAdded()) {
                        fragmentTransaction.remove(mBannerNotStartedFragment);
                        mBannerNotStartedFragment = null;
                    }
                    if (mBannerProgressFragment != null && mBannerProgressFragment.isAdded()) {
                        fragmentTransaction.remove(mBannerProgressFragment);
                        mBannerProgressFragment = null;
                    }
                    if (mBannerFinishFragment != null && mBannerFinishFragment.isAdded()) {
                        fragmentTransaction.remove(mBannerFinishFragment);
                        mBannerFinishFragment = null;
                    }

                    if(!"0".equals(mInfo.getIs_complete())){
                        mBannerFinishFragment = new BannerFinishFragment();
                        Bundle finish = new Bundle();
                        finish.putString("complete", mInfo.getIs_complete() + "");
                        finish.putString("status", mInfo.getStatus()+"");
                        finish.putSerializable("achieve", mInfo.getAchieveCard());
                        mBannerFinishFragment.setArguments(finish);
                        fragmentTransaction.add(R.id.study_group_head, mBannerFinishFragment);
                    }else{
                        if ("2".equals(mInfo.getStatus())) {
                            mBannerFinishFragment = new BannerFinishFragment();
                            Bundle finish = new Bundle();
                            finish.putString("complete", mInfo.getIs_complete() + "");
                            finish.putString("status", mInfo.getStatus()+"");
                            finish.putSerializable("achieve", mInfo.getAchieveCard());
                            mBannerFinishFragment.setArguments(finish);
                            fragmentTransaction.add(R.id.study_group_head, mBannerFinishFragment);
                        } else {
                            mBannerProgressFragment = new BannerProgressFragment();
                            Bundle start = new Bundle();
                            start.putString("status", mInfo.getStatus());
                            mBannerProgressFragment.setArguments(start);
                            fragmentTransaction.add(R.id.study_group_head, mBannerProgressFragment);
                        }
                    }


                }

                if ("0".equals(mIsBaoMing)) {
                    mRemarkContent.setVisibility(View.GONE);
                    if (CHALLENGE_NO_JOIN.equals(mJoinStatus)) {

                        if (mNoJoinChallengeFragment != null && mNoJoinChallengeFragment.isAdded()) {
                            fragmentTransaction.remove(mNoJoinChallengeFragment);
                            mNoJoinChallengeFragment = null;
                        }
                        mNoJoinChallengeFragment = new NoJoinChallengeFragment();
                        Bundle noStartChallenge = new Bundle();
                        noStartChallenge.putString("title", info.getTitle());
                        noStartChallenge.putString("vid", mVid);
                        noStartChallenge.putString("num", mInfo.getUser_count().getCount());
                        noStartChallenge.putSerializable("scholarship", mInfo.getScholarship());
                        noStartChallenge.putSerializable("teacher", mInfo.getTeacher());
                        mNoJoinChallengeFragment.setArguments(noStartChallenge);
                        fragmentTransaction.add(R.id.study_group_content, mNoJoinChallengeFragment);

                    } else if (FREE_NO_JOIN.equals(mJoinStatus)) {

                        if (mNoJoinFreeFragment != null && mNoJoinFreeFragment.isAdded()) {
                            fragmentTransaction.remove(mNoJoinFreeFragment);
                            mNoJoinFreeFragment = null;
                        }
                        mNoJoinFreeFragment = new NoJoinFreeFragment();
                        Bundle noStartFree = new Bundle();
                        noStartFree.putString("title", info.getTitle());
                        noStartFree.putString("vid", mVid);
                        noStartFree.putSerializable("userCount", mInfo.getUser_count());
                        noStartFree.putSerializable("teacher", mInfo.getTeacher());
                        noStartFree.putSerializable("maxnum", mInfo.getMaxnum());
                        mNoJoinFreeFragment.setArguments(noStartFree);
                        fragmentTransaction.add(R.id.study_group_content, mNoJoinFreeFragment);
                    }
                    if (mStartStatus == START) {
                        showActionEndDig();
                    }
                } else {
                    mRuleIcon.setVisibility(View.VISIBLE);
                    String vip = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "is_vip", "0");
                    getRemarkList();
                    if ("1".equals(mInfo.getPopup())) {
                        String url = "";
                        if ("1".equals(vip)) {
                            url = info.getImg_con_vip();
                        } else {
                            url = info.getImg_con();
                        }
                        showCoupon(url);
                    }
                    mSwipeLayout.setLoadMoreEnabled(true);
                    if (mNoJoinChallengeFragment != null && mNoJoinChallengeFragment.isAdded()) {
                        fragmentTransaction.remove(mNoJoinChallengeFragment);
                        mNoJoinChallengeFragment = null;
                    }
                    if (mNoJoinFreeFragment != null && mNoJoinFreeFragment.isAdded()) {
                        fragmentTransaction.remove(mNoJoinFreeFragment);
                        mNoJoinFreeFragment = null;
                    }
                    if (mSGJoinedFragment != null && mSGJoinedFragment.isAdded()) {
                        fragmentTransaction.remove(mSGJoinedFragment);
                        mSGJoinedFragment = null;
                    }
                    mTitleC.setText(info.getTitle());
                    mSGJoinedFragment = new SGJoinedFragment();
                    Bundle start = new Bundle();
                    start.putSerializable("info", mInfo);
                    start.putString("type", mJoinStatus);
                    start.putString("vid", mVid);
                    start.putString("pid", mPid);
                    start.putInt("isStart",mStartStatus);
                    mSGJoinedFragment.setArguments(start);
                    fragmentTransaction.add(R.id.study_group_content, mSGJoinedFragment);
                    mRemarkContent.setVisibility(View.VISIBLE);
                    if ("2".equals(mInfo.getStatus())||!"0".equals(mInfo.getIs_complete())) {
                        mYuanArrow.setVisibility(View.VISIBLE);
                        mRule.setVisibility(View.VISIBLE);
                    } else {
                        mYuanArrow.setVisibility(View.GONE);
                        mRule.setVisibility(View.GONE);
                    }
                }
                try {
                    fragmentTransaction.commit();
                }catch (Exception e){
                    accessNet();
                }


            }


        }
        mIsPay = false;

    }

    @Override
    public void onFault(String error) {
        LoadingUtils.getInstance().hideNetLoading();
        Log.e("DH_ERROR_SG_DETAIL", error);
        mIsPay = false;
    }

    //倒计时结束
    @Override
    public void onActionStartClick() {
        if (!"0".equals(mIsBaoMing)) {
//            if (CHALLENGE_NO_JOIN.equals(mJoinStatus)) {
//
//            }else if(FREE_NO_JOIN.equals(mJoinStatus)){
//
//            }
            //重新加载刷新
//            Intent intent = new Intent(this,StudyGroupInfo_2_0_Activity.class);
//            startActivity(intent);
//            finish();
            accessNet();
        } else {
            showActionEndDig();
        }
    }


    //加入小组
    private String mMoney;

    @Override
    public void onJoinSGClick() {
        if("1".equals(mInfo.getIslimit())){
            Toast.makeText(MyApplication.mApplication, "您的小组报名已达上限", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("1".equals(mJoinStatus)) {
            LoadingUtils.getInstance().showNetLoading(this);
            mCheckEventPresenter.checkEvent(getParamMap(), new OnResultListener() {


                @Override
                public void onSuccess(Object... value) {
                    CheckEventEntity entity = (CheckEventEntity) value[0];

                    if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
                        mMoney = entity.getMoney();
                        LoadingUtils.getInstance().hideNetLoading();
                        String vip = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "is_vip", "0");
                        if("1".equals(vip)||"2".equals(vip)){
                            showPayDig(mMoney);
                        }else{
                            showPayInfoDig();
                        }
                    } else {
                        LoadingUtils.getInstance().hideNetLoading();
                        Toast.makeText(StudyGroupInfo_2_0_Activity.this, entity.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_EVENT", error);
                    LoadingUtils.getInstance().hideNetLoading();
                }
            });

        } else {
            joinSG();
        }


    }

    private int mTempBao = 0;
    private void joinSG() {
        LoadingUtils.getInstance().showNetLoading(this);
        mStudyGroupPresenter.joinStudyGroup2_0(mUserId, mPid, mVid, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                SGJoinEntity sgJoinEntity = (SGJoinEntity) value[0];
                if ("1".equals(sgJoinEntity.getInfo())) {
                    mIsBaoMing = "1";
//                if(mIsWXPay!=1)
                    mTempBao = 1;
                    accessNet();
                }else{
                    Toast.makeText(MyApplication.mApplication, "此活动已报满，请选择其他小组", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_JOIN", error);
                Toast.makeText(MyApplication.mApplication, "数据异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private Handler mSwipeHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case REMARK_LOAD_MORE:
//
//                    break;
//            }
//        }
//    };


    public static final int REMARK_LOAD_MORE = 3000;

    @Override
    public void onLoadMore() {
        mHandler.sendEmptyMessageDelayed(REMARK_LOAD_MORE, 1500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSGDetail2_0!=null) {
            mSGDetail2_0.cancel();
        }

        EventBus.getDefault().unregister(this);
        if (mQrImgBitmap != null) {
            mQrImgBitmap = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private int mPayStatus = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventJoinSG(SGDetail2_0Entity sgDetail2_0Entity) {
        mPayStatus = 1;
    }


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            PayEntity entity = null;
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")

                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MyApplication.mApplication, "支付成功", Toast.LENGTH_SHORT).show();
                        joinSG();
//                        mPayStatus = 1;
                    } else {
                        Toast.makeText(MyApplication.mApplication, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    temporaryEventNo = "";
                    isPayCourseOrAction = -1;
                    isPayCourseType = "";
                    mIsPay = false;
                    break;
                }
                case WAY_ALIPAY:
                    mIsPay = true;
                    final String orderInfo = mAlipay;
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(StudyGroupInfo_2_0_Activity.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Log.i("msp", result.toString());
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();

                    break;
                case WAY_WEICHAT:
                    mIsPay = true;
                    PayEntity.InfoBean.WechatBean wechat = mWechat;
                    IWXAPI wxApi = WXAPIFactory.createWXAPI(StudyGroupInfo_2_0_Activity.this, null);
                    wxApi.registerApp(Constants.APP_ID);
                    PayReq req = new PayReq();
                    req.appId = wechat.getAppid();
                    req.nonceStr = wechat.getNoncestr();
                    req.packageValue = wechat.getPackageX();
                    req.prepayId = wechat.getPrepayid();
                    req.partnerId = wechat.getPartnerid();
                    req.timeStamp = wechat.getTimestamp();
                    req.sign = wechat.getSign();
                    wxApi.sendReq(req);

                    break;
                case REMARK_LOAD_MORE:
                    mSwipeLayout.setLoadingMore(false);
                    mPage++;
                    getRemarkList();
                    break;
                default:
                    break;
            }
        }
    };


    public static int PAY_TYPE = WAY_WEICHAT; // 3微信 2支付宝
    private String mCouponId = "";
    private TextView mPayCoursePrice;
    private TextView mCouponInfo;
    public void showPayDig(final String money) {
        mPayDialog = new Dialog(StudyGroupInfo_2_0_Activity.this, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(StudyGroupInfo_2_0_Activity.this).inflate(R.layout.dig_sg_pay, null);
        //将布局设置给Dialog
        mPayDialog.setContentView(inflate);

        TextView payCourseTitle = (TextView) mPayDialog.findViewById(R.id.pay_course_title);
        mPayCoursePrice = (TextView) mPayDialog.findViewById(R.id.pay_course_price);
        RelativeLayout coupon = (RelativeLayout) mPayDialog.findViewById(R.id.coupon);
        mCouponInfo = (TextView) mPayDialog.findViewById(R.id.coupon_info);
        RelativeLayout payWeichat = (RelativeLayout) mPayDialog.findViewById(R.id.pay_weichat);
        final ImageView weichatSelect = (ImageView) mPayDialog.findViewById(R.id.weichat_select);
        final RelativeLayout payAlipay = (RelativeLayout) mPayDialog.findViewById(R.id.pay_alipay);
        final ImageView alipaySelect = (ImageView) mPayDialog.findViewById(R.id.alipay_select);
        TextView reminder = (TextView) mPayDialog.findViewById(R.id.reminder);
        TextView payCancel = (TextView) mPayDialog.findViewById(R.id.pay_cancel);
        TextView payOk = (TextView) mPayDialog.findViewById(R.id.pay_ok);
        String vip = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "is_vip", "0");
        coupon.setVisibility(View.GONE);
//        if ("1".equals(vip)||"2".equals(vip)) {
//            coupon.setVisibility(View.GONE);
//        }else{
//            coupon.setVisibility(View.VISIBLE);
//        }
        payCourseTitle.setText(mInfo.getInfo().getTitle() + "");
        mPayCoursePrice.setText(money + "元");
        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyGroupInfo_2_0_Activity.this, SelectCouponActivity.class);
                intent.putExtra("course_price",money);
                intent.putExtra("coupon_id",mCouponId);
                intent.putExtra("id",mVid);
                intent.putExtra("category","2");
                startActivityForResult(intent,COUPON_REQUEST);
            }
        });
        payWeichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAY_TYPE = WAY_WEICHAT;
                weichatSelect.setImageResource(R.drawable.pay_select);
                alipaySelect.setImageResource(R.drawable.pay_unselect);
            }
        });

        payAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAY_TYPE = WAY_ALIPAY;
                weichatSelect.setImageResource(R.drawable.pay_unselect);
                alipaySelect.setImageResource(R.drawable.pay_select);
            }
        });

        payCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayDialog.dismiss();
            }
        });
        payOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingUtils.getInstance().showNetLoading(StudyGroupInfo_2_0_Activity.this);
                Log.e("DH_OP", "goodType: 1   id:" + mInfo.getInfo().getEvent() + "  user_id:" + mUserId + "  coupon:   company:0");
                mOrderPayPresenter.orderPay("1", mInfo.getInfo().getEvent(), mUserId, mCouponId, "0", new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        PayEntity entity = (PayEntity) value[0];
                        String fee = entity.getFee();
                        LoadingUtils.getInstance().hideNetLoading();
                        Log.e("DH_FEE", "fee:" + fee);
                        if (!"0".equals(fee)) {
                            if (entity.getStatus() != 0) {
//                                temporaryEventNo = entity.getInfo().getOrder_no();
                                mWechat = entity.getInfo().getWechat();
                                mAlipay = entity.getInfo().getAlipay();

                                if (PAY_TYPE == WAY_WEICHAT) {
                                    Toast.makeText(StudyGroupInfo_2_0_Activity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                                } else if (PAY_TYPE == WAY_ALIPAY) {
                                    Toast.makeText(StudyGroupInfo_2_0_Activity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                                }
                                isPayCourseOrAction = 3;
                                mHandler.sendEmptyMessage(PAY_TYPE);

                            }
                        } else {
//                            Toast.makeText(StudyGroupInfo_2_0_Activity.this, "报名成功", Toast.LENGTH_SHORT).show();
//                            Intent intent;
//                            intent = new Intent(StudyGroupInfo_2_0_Activity.this, ResultActivity.class);
//                            intent.putExtra("type", "1");
//                            intent.putExtra("order_no", mEvent_id);
//                            startActivity(intent);
//                            isPayCourseOrAction = -1;
//                            temporaryEventNo = "";
//                            finish();
                            Toast.makeText(MyApplication.mApplication, "支付成功", Toast.LENGTH_SHORT).show();
                            joinSG();

                        }

                        if (mPayDialog != null && mPayDialog.isShowing()) {
                            mPayDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFault(String error) {
                        LoadingUtils.getInstance().hideNetLoading();
                        Log.e("DH_PAY_WECHET", error);

                    }
                });
            }
        });

        //获取当前Activity所在的窗体
        Window dialogWindow = mPayDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mPayDialog.show();//显示对话框
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==COUPON_REQUEST&&resultCode==COUPON_RESULT){
            mCouponId = data.getStringExtra("coupon_id");
            String coupon_price = data.getStringExtra("price");
            if(mPayDialog!=null&&mPayDialog.isShowing()){
                int price = Integer.parseInt(coupon_price);
                int o_price = Integer.parseInt(mMoney);
                if(price!=0){
                    mCouponInfo.setText("-"+coupon_price+"元");
                }else{
                    mCouponInfo.setText("请选择优惠券");
                }
                mPayCoursePrice.setText((o_price-price)+"元");

            }
        }
    }


    public void showActionEndDig() {
        Dialog endDig;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.punch_card_finish)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        endDig = builder.create();
        endDig.setCancelable(false);
        endDig.setCanceledOnTouchOutside(false);
        endDig.show();
    }


    private Dialog mCouponDig;

    public void showCoupon(String url) {

        if (mCouponDig == null) {
            mCouponDig = new Dialog(this, R.style.MyDialog);
        }
        View dView = LayoutInflater.from(this).inflate(R.layout.dig_coupon, null, false);
        mCouponDig.setContentView(dView);

        ImageView coupon = (ImageView) dView.findViewById(R.id.coupon_img);
        ImageView close = (ImageView) dView.findViewById(R.id.close);
        if (StringUtils.isNotEmpty(url)) {
            Glide.with(MyApplication.mApplication).load(url).centerCrop().into(coupon);
        }
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCouponDig.dismiss();
//            }
//        });
        mCouponDig.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mStudyGroupPresenter.showCoupun(mUserId, mPid, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {

                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCouponDig.dismiss();
            }
        });
        mCouponDig.show();
    }


    private Map<String, String> getParamMap() {
        Map<String, String> map = new HashMap<>();
        map.put("event_id", mInfo.getInfo().getEvent());
        map.put("name", mNickName);
        map.put("user_id", mUserId);
        map.put("list_id", mPid);
        map.put("list_video_id", mVid);
        map.put("ticket_id", "");
        map.put("coupon_code", "");
        map.put("coupon_cut", "");
        map.put("batch_join", "");
        map.put("tel", mTel);
        map.put("brand", "");
        map.put("email", "");
        map.put("area", "");
        map.put("position", "");
        return map;
    }


    private int mPage = 0;

    public void getRemarkList() {
        LoadingUtils.getInstance().showNetLoading(this);
//        mVid = "1556";
//        mPid = "26";
        mStudyGroupPresenter.getSGRemark(mUserId, mPid, mVid, mJoinStatus, String.valueOf(mPage), "10", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                mSwipeLayout.setLoadMoreEnabled(true);
                LoadingUtils.getInstance().hideNetLoading();
                SGRemarkEntity sgRemarkEntity = (SGRemarkEntity) value[0];
                if (sgRemarkEntity.getInfo() != null && sgRemarkEntity.getInfo().size() > 0) {
                    if (mPage == 0) {
                        mSGRemarkBeanList.clear();
                    }

                    mSGRemarkBeanList.addAll(sgRemarkEntity.getInfo());
                    mSGRemarkAdapter = new SGRemarkAdapter(StudyGroupInfo_2_0_Activity.this, mSGRemarkBeanList, "0", "");
                    mEmptyRemarkAdapter = new EmptyRecycleAdapter(mSGRemarkAdapter, R.layout.layout_sg_remark_empty);
                    mRemarkList.setAdapter(mEmptyRemarkAdapter);
                    mSGRemarkAdapter.setOnRemarkItemClickListener(StudyGroupInfo_2_0_Activity.this);
//                    if (mSGRemarkAdapter != null) {
//                        mSGRemarkAdapter.notifyDataSetChanged();
//                    } else {
//                        mSGRemarkAdapter = new SGRemarkAdapter(StudyGroupInfo_2_0_Activity.this, mSGRemarkBeanList, "0","");
//                        mRemarkList.setAdapter(mSGRemarkAdapter);
//                        mSGRemarkAdapter.setOnRemarkItemClickListener(StudyGroupInfo_2_0_Activity.this);
//                    }
                } else {
                    if (mPage != 0)
                        Toast.makeText(MyApplication.mApplication, "没有更多心得了", Toast.LENGTH_SHORT).show();
                    else
                        mSwipeLayout.setLoadMoreEnabled(false);
                }

            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_ERROR_SG_REMARK", error);
            }
        });
    }

    @Override
    public void onRemarkClick(String id) {
        Intent intent = new Intent(this, SGRemarkDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("vid", mVid);
        startActivity(intent);
    }

    @Override
    public void onDelClick(String id, final int pos) {
        mCommentPresenter.deleteCommentJson(id, mVid, "1", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                if (mSGRemarkBeanList != null && mSGRemarkBeanList.size() > 0) {
                    mSGRemarkBeanList.remove(pos);

                    if (mEmptyRemarkAdapter != null) {
                        mEmptyRemarkAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Toast.makeText(mContext, "删除异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Dialog mRuleDig;

    private void showRuleDig(String type) {
        mRuleDig = new Dialog(this, R.style.MyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dig_sg_rule, null, false);
        mRuleDig.setContentView(view);
        ImageView ruleImg = (ImageView) mRuleDig.findViewById(R.id.rule_img);
        if ("1".equals(type)) {
            ruleImg.setImageResource(R.drawable.tiaozhanbanguize);
        } else {
            ruleImg.setImageResource(R.drawable.zixuebanguize);
        }
        ruleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRuleDig.dismiss();
            }
        });
        Window dialogWindow = mRuleDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(MyApplication.mApplication, 290);
        lp.height = DisplayUtil.dip2px(MyApplication.mApplication, 430);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        mRuleDig.show();
    }


    /**
     *  针对fragment在back后保存完状态调用commit 跑出异常Can not perform this action after onSaveInstanceState解决办法
     *  通过反射手段在onSaveInstanceState 方法里调用 FragmentManagerImpl noteStateNotSaved方法将 mStateSaved 变量置为false
     */
    private Method noteStateNotSavedMethod;
    private Object fragmentMgr;
    private String[] activityClassName = {"Activity", "FragmentActivity"};

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ex) {
        }
    }

    private Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public void onFinishCouponClick() {
        String vip = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "is_vip", "0");
        if ("1".equals(mInfo.getPopup())) {
            String url = "";
            if ("1".equals(vip)) {
                url = mInfo.getInfo().getImg_con_vip();
            } else {
                url = mInfo.getInfo().getImg_con();
            }
            showCoupon(url);
        }
    }

    @Override
    public void onQrClick() {
        showQrDig(mQrUrl);
    }



    private Dialog mPayInfo;
    public void showPayInfoDig() {
//        Log.e("Qr",url);
        mPayInfo = new Dialog(this, R.style.MyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dig_sg_vip_pay, null, false);
        mPayInfo.setContentView(view);

        TextView price = (TextView) mPayInfo.findViewById(R.id.price);
        ImageView close = (ImageView) mPayInfo.findViewById(R.id.close);
        TextView zhijiePay = (TextView) mPayInfo.findViewById(R.id.zhijie_pay);
        TextView vipPay = (TextView) mPayInfo.findViewById(R.id.vip_pay);
        zhijiePay.setText("直接报名\n"+mMoney+"元");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayInfo.dismiss();
            }
        });
        price.setText("报名费"+mMoney+"元");
        zhijiePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayInfo.dismiss();
                showPayDig(mMoney);
            }
        });
        vipPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayInfo.dismiss();
                Intent intent = new Intent(StudyGroupInfo_2_0_Activity.this, VipListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Window dialogWindow = mPayInfo.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(MyApplication.mApplication, 283);
        lp.height = DisplayUtil.dip2px(MyApplication.mApplication, 402);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        mPayInfo.show();

    }




    private Dialog mQrDig;
    private SaveBitmapAsync mSaveBitmapAsync;
    private String mQrFileName;
    private String mQrUrl;
    private Bitmap mQrImgBitmap;


    public void showQrDig(String url) {
        Log.e("Qr","url:"+url);
        mQrDig = new Dialog(this, R.style.MyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dig_sg_qr, null, false);
        mQrDig.setContentView(view);
        ImageView close = (ImageView) mQrDig.findViewById(R.id.close);
        final ImageView qr = (ImageView) mQrDig.findViewById(R.id.qr);
        if (mQrImgBitmap == null) {
            Glide.with(MyApplication.mApplication).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    qr.setImageBitmap(resource);
                    mQrImgBitmap = resource;
                }
            });
        } else {
            qr.setImageBitmap(mQrImgBitmap);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQrDig.dismiss();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mQrFileName != null || mSaveBitmapAsync != null) {
                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
                    File currentFile = new File(file, mQrFileName);
                    if (currentFile != null && currentFile.exists()) {
                        BToast.showText(StudyGroupInfo_2_0_Activity.this, "图片已存在");
                        return true;
                    }

                }
                mQrFileName = "qr_" + System.currentTimeMillis() + ".jpg";
                mSaveBitmapAsync = new SaveBitmapAsync(StudyGroupInfo_2_0_Activity.this,
                        mQrFileName,
                        false,
                        new SaveBitmapAsync.OnSaveImgClickListener() {
                            @Override
                            public void onSaveSuccessClick() {
                                BToast.showText(StudyGroupInfo_2_0_Activity.this, "保存成功");
                            }
                        });

                mSaveBitmapAsync.execute(mQrImgBitmap);
                return true;
            }
        });
        Window dialogWindow = mQrDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(MyApplication.mApplication, 283);
        lp.height = DisplayUtil.dip2px(MyApplication.mApplication, 402);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        mQrDig.show();

    }



    private Dialog mShareDialog;
    private String mShareTitle;
    private String mShareDes;
    private String mShareUrl = "";

    private void showShareDig() {
        //分享
        mShareDialog = new Dialog(StudyGroupInfo_2_0_Activity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(StudyGroupInfo_2_0_Activity.this).inflate(R.layout.share_dialog, null);
        //初始化控件
        RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
        RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
        RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);

        if("1".equals(mJoinStatus)){
            mShareTitle = "我正在参加勺子课堂学习挑战，邀你加入，一起瓜分"+mInfo.getScholarship().getBonus_rang()+"元！";
            mShareDes = "学霸挑战，超强监督，不学就出局";
            mShareUrl = "http://www.shaoziketang.com/wapshaozi/study_group_new_join.html?&id="+mPid+"&vid="+mVid+"&event="+mInfo.getInfo().getEvent();
        }else{
            mShareTitle = "我已加入勺子课堂自学班，邀请你一起打卡学习！";
            mShareDes = "精品好课限时免费，更有学习礼包等你来拿";
            mShareUrl = "http://www.shaoziketang.com/wapshaozi/study_clock_in.html?&id="+mPid+"&vid="+mVid+"&maxnum="+mInfo.getMaxnum();
        }
        share_rlayout_py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                ShareUtil.weiChat(Constants.wx_api, 7,
                        StudyGroupInfo_2_0_Activity.this, mShareUrl,
                        mShareTitle, mShareDes);
            }
        });

        share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                ShareUtil.weiChat(Constants.wx_api, 8,
                        StudyGroupInfo_2_0_Activity.this, mShareUrl,
                        mShareTitle, mShareDes);
            }
        });

        share_llayout_concel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
            }
        });

        //将布局设置给Dialog
        mShareDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mShareDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mShareDialog.show();//显示对话框
    }

    @Override
    public void onShareClick() {
        showShareDig();
    }


    @Override
    public void onBackPressed() {
        if (!isFinishing()) {
            super.onBackPressed();
        }
    }
}
