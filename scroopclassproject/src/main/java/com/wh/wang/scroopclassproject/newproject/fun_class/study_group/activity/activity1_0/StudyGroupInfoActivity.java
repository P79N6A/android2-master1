package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.BannerFinishFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.BannerNotStartedFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.BannerProgressFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.JoinedFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0.NoJoinFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetailEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;


public class StudyGroupInfoActivity extends BaseActivity implements View.OnClickListener, NoJoinFragment.OnJoinGroupClickListener,
        BannerNotStartedFragment.OnActionStartClickListener,JoinedFragment.OnPunchCardClickListener {

    private FragmentManager mSupportFragmentManager;
    private ImageView mCourseBg;
    private float rate = 9 / 5f;
    //    private String mState;
    private NoJoinFragment mNoJoinFragment;
    private StudyGroupPresenter mStudyGroupPresenter;
    private String mPid;
    private String mVid;
    private String mUserId;
    //当前是否报名
    private String mIsbao;
    private BannerNotStartedFragment mBannerNotStartedFragment;
    private JoinedFragment mJoinedFragment;
    private String mIsStart;
    private String mComplete;
    private SGDetailEntity.InfoBeanX mInfo;
    private FrameLayout mStudyGroupHead;
    private FrameLayout mStudyGroupContent;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_study_group_info;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mPid = getIntent().getStringExtra("pid");
        mVid = getIntent().getStringExtra("vid");
        mIsbao = getIntent().getStringExtra("isbao");
        Log.e("DH_REC","pid:"+mPid+"  vid"+mVid+"  isbao"+mIsbao);
        mStudyGroupHead = (FrameLayout) findViewById(R.id.study_group_head);
        mStudyGroupContent = (FrameLayout) findViewById(R.id.study_group_content);
//        mUserId = "1";
//        mVid = "946";
//        mPid = "21";
    }

    @Override
    public void initView() {
        mCourseBg = (ImageView) findViewById(R.id.course_bg);
        ViewGroup.LayoutParams layoutParams = mCourseBg.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getScreenWidth(this) / rate);
        mCourseBg.setLayoutParams(layoutParams);

        mSupportFragmentManager = getSupportFragmentManager();

    }

    @Override
    public void initDatas() {

    }
    private boolean mIsCancelLogin;
    @Override
    protected void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        if (StringUtils.isEmpty(mUserId)) {
            Toast.makeText(MyApplication.mApplication, "您已退出,需要重新登陆", Toast.LENGTH_SHORT).show();
            if(!mIsCancelLogin){
                mIsCancelLogin = true;
                Intent intent = new Intent(this, LoginNewActivity.class);
                startActivity(intent);
            }else{
                finish();
            }

            return;
        }
        if(mStudyGroupHead.getChildCount()>0){
            mStudyGroupHead.removeAllViews();
        }
        if(mStudyGroupContent.getChildCount()>0){
            mStudyGroupContent.removeAllViews();
        }
        accessToNet();
    }

    private void accessToNet() {
        LoadingUtils.getInstance().showNetLoading(this);
        if (mStudyGroupPresenter == null)
            mStudyGroupPresenter = new StudyGroupPresenter();
        Log.e("DH_SG_DETAIL", "pid:" + mPid + "  vid:" + mVid);
        mStudyGroupPresenter.getSGDetail(mUserId, mPid, mVid, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
                SGDetailEntity sgDetailEntity = (SGDetailEntity) value[0];
                if ("200".equals(sgDetailEntity.getCode())) {
                    mInfo = sgDetailEntity.getInfo();
                    mTitleC.setText(mInfo.getInfo().getTitle());
                    mComplete = sgDetailEntity.getInfo().getIs_complete();
                    SGDetailEntity.InfoBeanX.InfoBean info = sgDetailEntity.getInfo().getInfo();

                    String vip = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"is_vip","0");
                    if("1".equals(mInfo.getPopup())){
                        String url  = "";
                        if("1".equals(vip)){
                            url = info.getImg_con_vip();
                        }else{
                            url = info.getImg_con();
                        }
                        showCoupon(url);
                    }
                    long publish_time = Long.parseLong(info.getPublist_shijian());
                    long now_time = Long.parseLong(info.getNow_time());
                    Glide.with(MyApplication.mApplication).load(info.getImg()).centerCrop().into(mCourseBg);
                    FragmentTransaction transaction = mSupportFragmentManager.beginTransaction();
                    //判断活动是否开始
                    if (now_time >= publish_time) { //已经开始
                        //判断是否报名
                        if (!"1".equals(mIsbao)) {

                            LoadingUtils.getInstance().hideNetLoading();
                            showActionEndDig();
                            //结束标志
                            if(mNoJoinFragment==null)
                                mNoJoinFragment = new NoJoinFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("isEnd", "1");
                            bundle.putString("vid",mVid);
                            bundle.putString("pid",mPid);
                            bundle.putSerializable("avatars",sgDetailEntity.getInfo().getUser_count());
                            bundle.putSerializable("teacher",sgDetailEntity.getInfo().getTeacher());
                            mNoJoinFragment.setArguments(bundle);
                            transaction.add(R.id.study_group_content, mNoJoinFragment);
                        } else {
                            mJoinedFragment = new JoinedFragment();
                            Bundle joinBundle = new Bundle();
                            mIsStart = "1";
                            joinBundle.putString("isStart",mIsStart);
                            joinBundle.putString("vid",mVid);
                            joinBundle.putString("pid",mPid);
                            joinBundle.putString("complete",mComplete);
                            joinBundle.putSerializable("info",sgDetailEntity.getInfo());

                            mJoinedFragment.setArguments(joinBundle);
                            transaction.add(R.id.study_group_content, mJoinedFragment);

                            if("0".equals(sgDetailEntity.getInfo().getIs_complete())){
                                BannerProgressFragment bannerProgressFragment = new BannerProgressFragment();
                                Bundle bannerBundle = new Bundle();
                                bannerBundle.putString("status",sgDetailEntity.getInfo().getStatus());
                                bannerProgressFragment.setArguments(bannerBundle);
                                transaction.add(R.id.study_group_head,bannerProgressFragment );
                            }else{
                                BannerFinishFragment bannerFinishFragment = new BannerFinishFragment();
                                Bundle bannerBundle = new Bundle();
                                bannerBundle.putString("complete",sgDetailEntity.getInfo().getIs_complete());
                                bannerBundle.putString("pid",mPid);
                                String url  = "";
                                if("1".equals(vip)){
                                    url = info.getImg_con_vip();
                                }else{
                                    url = info.getImg_con();
                                }
                                bannerBundle.putString("url",url);
                                bannerFinishFragment.setArguments(bannerBundle);
                                transaction.add(R.id.study_group_head,bannerFinishFragment);
                            }

                        }

                    } else {
                        mBannerNotStartedFragment = new BannerNotStartedFragment();
                        Bundle bannerBundle = new Bundle();
                        bannerBundle.putString("start_time", info.getStart_shijian());
                        bannerBundle.putLong("public_time", publish_time);
                        bannerBundle.putLong("now_time", now_time);
                        mBannerNotStartedFragment.setArguments(bannerBundle);
                        transaction.add(R.id.study_group_head, mBannerNotStartedFragment);

                        //判断是否报名
                        Bundle joinBundle = new Bundle();
                        if ("1".equals(mIsbao)) {
                            mJoinedFragment = new JoinedFragment();
                            joinBundle.putString("isStart",mIsStart);
                            joinBundle.putString("vid",mVid);
                            joinBundle.putString("pid",mPid);
                            joinBundle.putString("complete",mComplete);
                            joinBundle.putSerializable("info",sgDetailEntity.getInfo());

                            mJoinedFragment.setArguments(joinBundle);
                            transaction.add(R.id.study_group_content, mJoinedFragment);
                        } else {
                            mNoJoinFragment = new NoJoinFragment();
                            joinBundle.putString("isEnd", "0");
                            joinBundle.putString("vid",mVid);
                            joinBundle.putString("pid",mPid);
                            joinBundle.putSerializable("avatars",sgDetailEntity.getInfo().getUser_count());
                            joinBundle.putSerializable("teacher",sgDetailEntity.getInfo().getTeacher());
                            mNoJoinFragment.setArguments(joinBundle);
                            transaction.add(R.id.study_group_content, mNoJoinFragment);
                        }

                    }

                    transaction.commit();
                } else {
                    Toast.makeText(MyApplication.mApplication, sgDetailEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_ERROR", "SGDetail:" + error);
            }
        });
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(this);
    }

    @Override
    public void initOther() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_l:
                finish();
                break;
        }
    }

    //点击加入小组
    @Override
    public void onJoinGroupClick() {
        if (!"1".equals(mIsbao) && mNoJoinFragment != null) {
            mIsbao = "1";

            FragmentTransaction transaction = mSupportFragmentManager.beginTransaction();
            transaction.remove(mNoJoinFragment);
            mJoinedFragment = new JoinedFragment();
            Bundle bundle = new Bundle();
            bundle.putString("isStart",mIsStart);
            bundle.putString("vid",mVid);
            bundle.putString("pid",mPid);
            bundle.putSerializable("info",mInfo);
            mJoinedFragment.setArguments(bundle);
            transaction.add(R.id.study_group_content, mJoinedFragment);
            transaction.commit();
        }

    }

    //活动开始
    @Override
    public void onActionStartClick() {
        if ("1".equals(mIsbao) && mBannerNotStartedFragment != null) {
            mIsStart = "1";
            FragmentTransaction transaction = mSupportFragmentManager.beginTransaction();
            transaction.remove(mBannerNotStartedFragment);
            transaction.add(R.id.study_group_head, new BannerProgressFragment());
            transaction.commit();

            if(mJoinedFragment!=null){
                mJoinedFragment.changeStatus(mIsStart);
            }
        } else {
            showActionEndDig();
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

    @Override
    public void onPunchCardClick(String id) {
        Intent intent = new Intent(this, NewVideoInfoActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("type","1");
        startActivity(intent);
    }

    private Dialog mCouponDig;
    public void showCoupon(String url){

        if(mCouponDig==null){
            mCouponDig = new Dialog(this,R.style.MyDialog) ;
        }
        View dView = LayoutInflater.from(this).inflate(R.layout.dig_coupon, null, false);
        mCouponDig.setContentView(dView);

        ImageView coupon = (ImageView) dView.findViewById(R.id.coupon_img);
        ImageView close = (ImageView) dView.findViewById(R.id.close);
        if(StringUtils.isNotEmpty(url)){
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
}
