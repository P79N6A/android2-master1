package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyStateEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CompanyStatePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CompanyCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.GlideRoundTransform;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CompanyInfoActivity extends Activity implements View.OnClickListener, DialogUtils.OnShareInviteClickListener, OnRefreshListener {
    private View LoadingView;
    private TextView mMember;
    private ImageView mBack;
    private RecyclerView mCompanyCourseList;
    private RoundedImageView mCompanyPerAvatar;
    private TextView mCompanyPerPos;
    private TextView mCompanyPerName;
    private TextView mCompanyName;
    private TextView mCompanyPerNum;
    private RelativeLayout mNoCourse;
    private ImageView mLikeTop1;
    private ImageView mLikeTop2;
    private TextView mLikeTop3;
    private TextView mLikeTop4;
    private TextView mLikeTop5;
    private TextView mLookAll;
//    private ProgressBar mLoading;
    private TextView mNoCourseTv;

    private ImageView mDataIcon;
    private RelativeLayout mStudyNum;
    private TextView mCompanySum;
    private TextView mDataInfo;
    private ImageView mManager;
    private TextView mTotalTime;
    private RelativeLayout mCompanyData;
    private RelativeLayout mLearnTime;
    private RelativeLayout mSwipeHead;
    private SwipeToLoadLayout mFragSwipe;


    private View[] mViews;
    private CompanyCourseAdapter mCompanyCourseAdapter;
    private CompanyStateEntity.InfoBean mCompanyInfo;
    private LinearLayout mLikeTopTwo;

    //0: 老板  1：管理员  2：普通成员
    private int mCurrentStatus = 0;
    private int mHasData = 0;
    private String mUser_id;

    private String mParent_id;
    private String mName;

    private CompanyStatePresenter mCompanyStatePresenter = new CompanyStatePresenter();
    private String mWuli_id;

    //判断当前是否为管理员跳转自己的主页
    private String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        EventBus.getDefault().register(this);

        FrameLayout rootView = (FrameLayout) findViewById(android.R.id.content);
        LoadingView = LoadingUtils.VideoActivityLoading(rootView);
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
//        mUser_id = "42980";//TODO
        mStatus = getIntent().getStringExtra("status");
        if(mStatus==null){
            mStatus = "0";
        }
        initView();
        initSize();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mMember = (TextView) findViewById(R.id.member);
        mCompanyCourseList = (RecyclerView) findViewById(R.id.company_course_list);
        mCompanyCourseList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        mCompanyCourseList.setHasFixedSize(true);
        mCompanyCourseList.setNestedScrollingEnabled(false);

        mCompanyPerAvatar = (RoundedImageView) findViewById(R.id.company_per_avatar);
        mCompanyPerPos = (TextView) findViewById(R.id.company_per_pos);
        mCompanyPerName = (TextView) findViewById(R.id.company_per_name);
        mCompanyName = (TextView) findViewById(R.id.company_name);
        mCompanyPerNum = (TextView) findViewById(R.id.company_per_num);
//        mLoading = (ProgressBar) findViewById(R.id.loading);
//        mLoading.setVisibility(View.VISIBLE);
        mCompanyData = (RelativeLayout) findViewById(R.id.company_data);
        mLearnTime = (RelativeLayout) findViewById(R.id.learn_time);
        mFragSwipe = (SwipeToLoadLayout) findViewById(R.id.frag_swipe);
        mSwipeHead = (RelativeLayout) findViewById(R.id.swipe_head);
        mSwipeHead.setBackgroundColor(Color.parseColor("#4894f2"));
        mNoCourseTv = (TextView) findViewById(R.id.no_course_tv);
        mNoCourse = (RelativeLayout) findViewById(R.id.no_course);
        mLikeTop1 = (ImageView) findViewById(R.id.like_top_1);
        mLikeTop2 = (ImageView) findViewById(R.id.like_top_2);
        mLikeTop3 = (TextView) findViewById(R.id.like_top_3);
        mLikeTop4 = (TextView) findViewById(R.id.like_top_4);
        mLikeTop5 = (TextView) findViewById(R.id.like_top_5);
        mLookAll = (TextView) findViewById(R.id.look_all);
        mLikeTopTwo = (LinearLayout) findViewById(R.id.like_top_two);
        mTotalTime = (TextView) findViewById(R.id.total_time);

        mViews = new View[]{mLikeTop1, mLikeTop2, mLikeTop3, mLikeTop4, mLikeTop5};

        mDataIcon = (ImageView) findViewById(R.id.data_icon);
        mStudyNum = (RelativeLayout) findViewById(R.id.study_num);
        mCompanySum = (TextView) findViewById(R.id.company_sum);
        mDataInfo = (TextView) findViewById(R.id.data_info);
        mManager = (ImageView) findViewById(R.id.manager);

    }

    private void initData(CompanyStateEntity entity) {
        mCompanyInfo = entity.getInfo();
        if (mCompanyInfo != null) {
            if("0".equals(mCompanyInfo.getParent_id())&&"0".equals(mStatus)){
                mCurrentStatus = 0; //老板
                mParent_id = mUser_id;
                mDataIcon.setVisibility(View.VISIBLE);
                mStudyNum.setVisibility(View.GONE);
                mDataInfo.setText("数据报表");
                mMember.setVisibility(View.VISIBLE);
                mManager.setVisibility(View.VISIBLE);
                mTotalTime.setText("企业学习总时长");
                mCompanyData.setOnClickListener(this);
                mLearnTime.setOnClickListener(this);
            }else{
                mParent_id = mCompanyInfo.getParent_id();
                if("0".equals(mCompanyInfo.getIfadmin())||"1".equals(mStatus)){
                    mCurrentStatus = 2; //普通
                    mNoCourseTv.setText("暂无分享课程");
                    mDataIcon.setVisibility(View.GONE);
                    mStudyNum.setVisibility(View.VISIBLE);
                    mCompanySum.setText(mCompanyInfo.getStudy_number());
                    mDataInfo.setText("已学完课程");
                    mMember.setVisibility(View.GONE);
                    mManager.setVisibility(View.GONE);
                    mTotalTime.setText("学习总时长");
                    mCompanyData.setOnClickListener(null);
                    mLearnTime.setOnClickListener(null);
                }else if("1".equals(mCompanyInfo.getIfadmin())){// //TODO
                    mCurrentStatus = 1; //管理员
                    mDataIcon.setVisibility(View.VISIBLE);
                    mStudyNum.setVisibility(View.GONE);
                    mMember.setVisibility(View.VISIBLE);
                    mManager.setVisibility(View.VISIBLE);
                    mDataInfo.setText("数据报表");
                    mTotalTime.setText("企业学习总时长");
                    mCompanyData.setOnClickListener(this);
                    mLearnTime.setOnClickListener(this);
                }
            }
            if("1".equals(mStatus)){
                mManager.setVisibility(View.GONE);
            }
            mWuli_id = mCompanyInfo.getWuli_id();
            if("0".equals(mCompanyInfo.getYuannumber())||mCompanyInfo.getYuannumber()==null||"".equals(mCompanyInfo.getYuannumber())){
                mMember.setText("邀请成员");
            }else{
                mMember.setText("成员");
            }
            if(mCompanyInfo.getAvator()!=null&&!"".equals(mCompanyInfo.getAvator())){
                Glide.with(MyApplication.mApplication).load(mCompanyInfo.getAvator()).error(R.drawable.qiye_zwpic).centerCrop().into(mCompanyPerAvatar);
            }else{
                mCompanyPerAvatar.setImageResource(R.drawable.qiye_zwpic);
            }
            mName = mCompanyInfo.getName();
            mCompanyPerPos.setText(mCompanyInfo.getPosition());
            mCompanyPerName.setText(mName);
            mCompanyName.setText(mCompanyInfo.getCompany_name());
            mCompanyPerNum.setText(mCompanyInfo.getStudy_time());
            if ((mCompanyInfo.getShare_video() != null && mCompanyInfo.getShare_video().size() > 0)) {
                mHasData = 1;
                mCompanyCourseList.setVisibility(View.VISIBLE);
                mNoCourse.setVisibility(View.GONE);
                mCompanyCourseAdapter = new CompanyCourseAdapter(mCompanyInfo.getShare_video(),mCurrentStatus,this);
                mCompanyCourseList.setAdapter(mCompanyCourseAdapter);
                mCompanyCourseAdapter.setOnCompanyCourseClickListener(new CompanyCourseAdapter.OnCompanyCourseClickListener() {
                    @Override
                    public void onCourseClick(String id,int pos) {
                        Intent intent = new Intent(CompanyInfoActivity.this, CompanyCourseActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("status",mCurrentStatus);
                        startActivity(intent);
                    }
                });
            } else {
                mHasData = 0;
                mCompanyCourseList.setVisibility(View.GONE);
                mNoCourse.setVisibility(View.VISIBLE);
                final List<CompanyStateEntity.InfoBean.VResBean> vResBeen = mCompanyInfo.getV_res();
                for (int i = 0; i < ((vResBeen.size() > 5) ? 5 : vResBeen.size()); i++) {
                    final CompanyStateEntity.InfoBean.VResBean v_res = vResBeen.get(i);
                    if (i < 2) {
                        ImageView img = (ImageView) mViews[i];
                        Glide.with(MyApplication.mApplication)
                                .load(v_res.getImg())
                                .transform(new GlideRoundTransform(MyApplication.mApplication, DisplayUtil.dip2px(MyApplication.mApplication, 4)))
                                .into(img);
                    } else {
                        TextView tv = (TextView) mViews[i];
                        tv.setText((i + 1) + "." + v_res.getTitle());
                    }
                    mViews[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CompanyInfoActivity.this, NewVideoInfoActivity.class);
                            intent.putExtra("id", v_res.getId());
                            intent.putExtra("type", v_res.getType());
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    private void initListener() {
        mMember.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mLookAll.setOnClickListener(this);
        mFragSwipe.setOnRefreshListener(this);
        mCompanyPerAvatar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reconnection(ReconnectionEntity reconnectionEntity){
        getCompanyInfo();
    }

    private void getCompanyInfo() {
        mCompanyStatePresenter.getCompanyState(mUser_id,mStatus, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingView.setVisibility(View.GONE);
//                mLoading.setVisibility(View.GONE);
                CompanyStateEntity entity = (CompanyStateEntity) value[0];
                if(entity.getCode()==200){
                    initData(entity);
                }else{
                    Toast.makeText(CompanyInfoActivity.this, entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_COMPANY_STATE",error);
//                mLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.member:
                if(mCompanyInfo.getYuannumber()!=null){
                    if("0".equals(mCompanyInfo.getYuannumber())){
                        DialogUtils dialogUtils = new DialogUtils(this);
                        dialogUtils.setOnShareInviteClickListener(this);
                        dialogUtils.showInviteDialog(mUser_id);
                    }else{
                        intent = new Intent(this, CompanyMemberActivity.class);
                        intent.putExtra("status",mCurrentStatus);
                        intent.putExtra("parent_id",mParent_id);
                        intent.putExtra("parent_name",mName);
                        startActivity(intent);
                    }
                }

                break;
            case R.id.back:
                finish();
                break;
            case R.id.look_all:
                intent = new Intent();
                // 设置结果，并进行传送
                Log.e("DH_WULI","mWuli_id:"+mWuli_id);
                intent.putExtra("id", (mWuli_id==null||"".equals(mWuli_id))?"29":mWuli_id);
                CompanyInfoActivity.this.setResult(Constant.SEARCH_RES, intent);
                finish();
                break;
            case R.id.company_data:
                intent = new Intent(this,CompanyDataActivity.class);
                intent.putExtra("url_type","0");
                intent.putExtra("parent_id",mParent_id);
                startActivity(intent);
                break;
            case R.id.learn_time:
                intent = new Intent(this,CompanyDataActivity.class);
                intent.putExtra("url_type","1");
                intent.putExtra("parent_id",mParent_id);
                startActivity(intent);
                break;
            case R.id.company_per_avatar:
                if(mCurrentStatus!=2){
                    intent = new Intent(this,CompanyInfoActivity.class);
                    intent.putExtra("status","1");
                    startActivity(intent);
                }
                break;
        }
    }

    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        setViewWH(d.widthPixels);
    }

    private double rate = 9 / (5.0);

    private void setViewWH(int widthPixels) {
        double h = ((widthPixels - DisplayUtil.dip2px(MyApplication.mApplication, 38)) / 2) / rate;
        ViewGroup.LayoutParams layoutParams = mLikeTopTwo.getLayoutParams();
        layoutParams.height = (int) h;
        mLikeTopTwo.setLayoutParams(layoutParams);
    }


    @Override
    public void onInviteClick() {
        ShareUtil.weiChat(Constants.wx_api, 7, this,
                "http://www.shaoziketang.com/wapshaozi/enterprise.html?qid=" + mParent_id,
                mName+"邀请您加入勺子课堂企业号",
                "好知识抱团学习才有趣，赶快点击加入吧！");
    }

    @Override
    public void onRefresh() {
        getCompanyInfo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.mApplication, "刷新成功", Toast.LENGTH_SHORT).show();
                mFragSwipe.setRefreshing(false);
            }
        },1500);
    }
}
