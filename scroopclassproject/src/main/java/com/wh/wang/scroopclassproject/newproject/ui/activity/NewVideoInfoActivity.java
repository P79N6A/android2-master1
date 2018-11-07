package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.DownManagerActivity;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.VideoLoginBean;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.DownLoadService;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.SelectCouponActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity.VipListActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.VideoFinishEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.DownLoadPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.SumUpInfoPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SumUpDownLoadAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.video_info_frag.VideoCatalogueFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.video_info_frag.VideoCommentFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.video_info_frag.VideoDetailsFragment;
import com.wh.wang.scroopclassproject.newproject.utils.PermissionUtils;
import com.wh.wang.scroopclassproject.newproject.utils.SaveBitmapAsync;
import com.wh.wang.scroopclassproject.newproject.utils.WindowUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopNewManyPay;
import com.wh.wang.scroopclassproject.newproject.view.SZSurfaceView;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.FileUtils;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.LogUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseType;
import static com.wh.wang.scroopclassproject.newproject.Constant.isShareInform;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity.manager;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;
import static com.wh.wang.scroopclassproject.newproject.view.SZSurfaceView.FULL_SCREEN;

public class  NewVideoInfoActivity extends FragmentActivity implements View.OnClickListener, SZSurfaceView.OnProcessClickListener,
        SZSurfaceView.OnShareClickListener, VideoDetailsFragment.OnCacheClickListener, SumUpDownLoadAdapter.OnDownloadClickListener,
        VideoCommentFragment.OnCommentKeyboardClickListener, VideoCatalogueFragment.OnCatalogueVideoClickListener {
    private View LoadingView;

    private RelativeLayout mVideoDetail;
    private TextView mDetail;
    private TextView mCatalogue;
    private TextView mComment;
    private FrameLayout mLine;
    private ViewPager mVideoContent;
    private SZSurfaceView mSzSurfaceView;
    private RelativeLayout mCountDown;
    private ImageView mCountDownBack;
    private TextView mResidueTimeDay;
    private TextView mResidueTimeHour;
    private TextView mResidueTimeMinute;
    private TextView mResidueTimeSecond;
    private TextView mPublicTimeTV;
    private LinearLayout mVideoBuyMenu;
    private TextView mVideoBuyCourse;
    private TextView mVideoBuyVip;
    private RelativeLayout mCache;
    private ImageView mCacheVideoCancel;
    private TextView mCacheVideoFreeSpace;
    private TextView mCacheVideoAll;
    private TextView mTvHaveDownloadNums;
    private RelativeLayout mCheckCacheVideo;
    private ImageView mLearnCard;

    private long mPublicTime = 0; //开课时间
    private long mNowTime = 0; //服务器时间
    private long mResidueTime = 0;// 剩余时间
    private CountDownTimer mCountDownTimer;

    private int mPhoneW;
    private int mPhoneH;
    private TextView[] mTitleTvs;
    private FragmentManager supportFragmentManager;
    private VideoDetailsFragment mDetailsFragment = new VideoDetailsFragment();
    private VideoCatalogueFragment mCatalogueFragment = new VideoCatalogueFragment();
    private VideoCommentFragment mCommentFragment;
    private List<Fragment> mFragments = new ArrayList<>();
    private String mCourseId;
    private String mType;
    private String mLogin_rand_str;
    private String mUserId;
    private SumUpInfoPresenter mSumUpInfoPresenter;

    private SumUpEntity.InfoBean mInfoBean;
    private String mPayPrice;
    private String mIs_company;
    private String mIs_buy_company;
    private String mYunumber;
    private String mPerson;
    private String mRandStr;
    private int mIs_vip;
    private String mPay_status;
    private String mCourseTitle;
    private View mMc;
    private RecyclerView mCacheVideoList;

    private List<SumUpEntity.DirBean> mVideoDir;

    private boolean mIsAllow;
    private Map<String, String> mPayMap = new HashMap<>();
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();

    private static final int SDK_PAY_FLAG = 1;
    private boolean mIsShowCache;
    private SumUpDownLoadAdapter mSumUpDownLoadAdapter;
    private DownLoadPresenter mDownLoadPresenter = new DownLoadPresenter();
    private DataKeeper mDataKeeper;

    private String cateID = "";

    private String mCurrentLearnCard;

    public static final int COUPON_REQUEST = 1000;
    public static final int COUPON_RESULT = 1001;
    private SumUpEntity.DirBean mCurrentDir;
    private String mIsCoupon;
    private String mOld_price;

    //学习小组参数
    private int mFrom;  // 1小组
    private String mPid = "";  //小组id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_video_info);
        WindowUtils.getInstance().fullScreen( this);
        FrameLayout rootView = (FrameLayout) findViewById(android.R.id.content);
        LoadingView = LoadingUtils.VideoActivityLoading(rootView);
        EventBus.getDefault().register(this);
        MobclickAgent.onEvent(this, "coursedes");
        mFrom = getIntent().getIntExtra("from", 0);
        mPid = getIntent().getStringExtra("pid")==null?"":getIntent().getStringExtra("pid");

        initView();
        initData();
        initFrag();
        initSize();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        mLogin_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        getVideoInfo();
    }


    /**
     * 重连接
     * @param reconnectionEntity
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reconnection(ReconnectionEntity reconnectionEntity){
        getVideoInfo();
    }

    private void initView() {
        mVideoDetail = (RelativeLayout) findViewById(R.id.video_detail);
        mDetail = (TextView) findViewById(R.id.detail);
        mCatalogue = (TextView) findViewById(R.id.catalogue);
        mComment = (TextView) findViewById(R.id.comment);
        mLine = (FrameLayout) findViewById(R.id.line);
        mVideoContent = (ViewPager) findViewById(R.id.video_content);
        mSzSurfaceView = (SZSurfaceView) findViewById(R.id.sz_surfaceView);
        mCountDown = (RelativeLayout) findViewById(R.id.count_down);
        mCountDownBack = (ImageView) findViewById(R.id.count_down_back);
        mResidueTimeDay = (TextView) findViewById(R.id.residue_time_day);
        mResidueTimeHour = (TextView) findViewById(R.id.residue_time_hour);
        mResidueTimeMinute = (TextView) findViewById(R.id.residue_time_minute);
        mResidueTimeSecond = (TextView) findViewById(R.id.residue_time_second);
        mPublicTimeTV = (TextView) findViewById(R.id.public_time);
        mVideoBuyMenu = (LinearLayout) findViewById(R.id.video_buy_menu);
        mVideoBuyCourse = (TextView) findViewById(R.id.video_buy_course);
        mVideoBuyVip = (TextView) findViewById(R.id.video_buy_vip);
        mMc = (View) findViewById(R.id.mc);
        mCache = (RelativeLayout) findViewById(R.id.cache);
        mCacheVideoCancel = (ImageView) findViewById(R.id.cache_video_cancel);
        mCacheVideoList = (RecyclerView) findViewById(R.id.cache_video_list);
        mCacheVideoFreeSpace = (TextView) findViewById(R.id.cache_video_free_space);
        mCacheVideoAll = (TextView) findViewById(R.id.cache_video_all);
        mTvHaveDownloadNums = (TextView) findViewById(R.id.tv_have_download_nums);
        mCheckCacheVideo = (RelativeLayout) findViewById(R.id.check_cache_video);
        mLearnCard = (ImageView) findViewById(R.id.learn_card);

        mSzSurfaceView.setActivity(this);

        //学习小组 隐藏心得
        if (mFrom==1) {
            mComment.setVisibility(View.GONE);
            mTitleTvs = new TextView[]{mDetail, mCatalogue};
            mCutNum = 2;
        }else{
            mComment.setVisibility(View.VISIBLE);
            mTitleTvs = new TextView[]{mDetail, mCatalogue, mComment};
            mCutNum = 3;
        }

        mCacheVideoList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        mDataKeeper = new DataKeeper(this);
        mCourseId = getIntent().getStringExtra("id");

//        mCourseId = "1414";
        mType = getIntent().getStringExtra("type") == null ? "1" : getIntent().getStringExtra("type");
        mCacheVideoFreeSpace.setText("可用存储空间" + FileUtils.getSDAvailableSize(MyApplication.mApplication));
    }


    private void initFrag() {
        supportFragmentManager = getSupportFragmentManager();
        mFragments.add(mDetailsFragment);
        mFragments.add(mCatalogueFragment);
        if (mFrom==0) {
            mCommentFragment = new VideoCommentFragment();
            mFragments.add(mCommentFragment);
        }

        mVideoContent.setAdapter(new BoutiqueVPAdapter(supportFragmentManager, mFragments));
        mVideoContent.setOffscreenPageLimit(mTitleTvs.length);
    }

    private int mCutNum = 3; //title滑动个数
    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneW = d.widthPixels;
        mPhoneH = d.heightPixels;
        ViewGroup.LayoutParams layoutParams = mLine.getLayoutParams();
        layoutParams.width = mPhoneW / mCutNum;
        mLine.setLayoutParams(layoutParams);
    }

    //切换动画
    private int startX = 0;
    private void selectInfoAni(final int item) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mPhoneW / mCutNum * item, 0, 0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTitleTvs[item].setTextColor(getResources().getColor(R.color.vedio_cap_press));
                for (int i = 0; i < mTitleTvs.length; i++) {
                    if (i != item) {
                        mTitleTvs[i].setTextColor(getResources().getColor(R.color.vedio_cap));
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mLine.startAnimation(translateAnimation);
        startX = mPhoneW / mCutNum * item;
    }

    public static int SHARE_FLAG = -1; //0 微信  1 朋友圈  2 本地
    private Dialog mFinishDig;
    private Dialog mPermissionDig;

    //    private Bitmap mDigBitmap;
    private void initListener() {
        //视频下一个的切换监听
        mSzSurfaceView.setOnNextVideoClickListener(new SZSurfaceView.OnNextVideoClickListener() {
            @Override
            public void onNextClick(SumUpEntity.DirBean dirBean,int index) {

                if(mCurrentDir!=null){
                    Log.e("DH_SEND_PROGRESS","Finish id:"+mCurrentDir.getId()+"  ");
                    mCurrentDir.setPlaytime(0);
                }
                if (mCatalogueFragment != null) {
                    mCurrentDir = dirBean;
//                        if(mVideoDir!=null){
//                            for (int i = 0; i < mVideoDir.size(); i++) {
//                                if(mVideoDir.get(i).getId().equals(cateList.get(index))){
//                                    mCurrentDir = mVideoDir.get(i);
//                                }
//                            }
//                        }
                    //刷新目录列表
                    mCatalogueFragment.notifyCate(dirBean.getId());

                }

            }

            //视频播放结束的监听
            @Override
            public void onFinishClick(SumUpEntity.DirBean dirBean,int index) {

                mSumUpInfoPresenter.showFinishDig(mUserId, mCourseId,dirBean.getId(), new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        VideoFinishEntity entity = (VideoFinishEntity) value[0];
                        if (StringUtils.isEmpty(entity.getUrl())) {
                            mSzSurfaceView.start();
                            return;
                        }
                        mSzSurfaceView.pause();
                        if (FULL_SCREEN == 1) {
                            FULL_SCREEN = 0;
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        Log.e("DH_URL", entity.getUrl());
                        showFinishDig(entity.getUrl());

                    }

                    @Override
                    public void onFault(String error) {
                        mSzSurfaceView.start();
                    }
                });
            }
        });
        mSzSurfaceView.setOnBackClickListener(new SZSurfaceView.OnBackClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }
        });
        //视频进度监听
        mSzSurfaceView.setOnProcessClickListener(this);
        mSzSurfaceView.setOnShareClickListener(this);
        mDetail.setOnClickListener(this);
        mCatalogue.setOnClickListener(this);
        mComment.setOnClickListener(this);
        mCacheVideoCancel.setOnClickListener(this);
        mCacheVideoAll.setOnClickListener(this);
        mCheckCacheVideo.setOnClickListener(this);
        mCountDownBack.setOnClickListener(this);
        mVideoContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 2) {
                    KeyBoardUtils.hideKeyboard(NewVideoInfoActivity.this);
                    //从心得切换时隐藏键盘
                    if(mCommentFragment!=null)
                        mCommentFragment.hideInput();
                    if (!"0.00".equals(mPayPrice)&&mFrom==0) {
                        mVideoBuyMenu.setVisibility(View.VISIBLE);
                    }
                }
                selectInfoAni(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // 原学习小组 视频结束的成就卡弹窗
    private void showFinishDig(String url) {
//        if(!isLearnCard){
//            mDigBitmap=null;
//        }
        mFinishDig = new Dialog(NewVideoInfoActivity.this, R.style.MyDialog);
        View fView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_video_finish, null, false);
        final ImageView imageView = (ImageView) fView.findViewById(R.id.finish_img);
        final Bitmap[] bmp = {null};
        fView.findViewById(R.id.share_pyq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bmp[0] == null) {
                    return;
                }
                ShareUtil.wechatShareImg(Constants.wx_api, bmp[0], 1, NewVideoInfoActivity.this, false);
            }
        });
        fView.findViewById(R.id.share_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bmp[0] == null) {
                    return;
                }
                ShareUtil.wechatShareImg(Constants.wx_api, bmp[0], 0, NewVideoInfoActivity.this, false);

            }
        });
        fView.findViewById(R.id.save_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bmp[0] == null) {
                    return;
                }
                if (ContextCompat.checkSelfPermission(NewVideoInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewVideoInfoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    new SaveBitmapAsync(NewVideoInfoActivity.this, "achievement_" + System.currentTimeMillis() + ".jpg", false)
                            .execute(bmp[0]);
                }

            }
        });
        if (bmp[0] != null) {
            imageView.setImageBitmap(bmp[0]);
        } else {
            Glide.with(MyApplication.mApplication).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (bmp[0] == null) {
                        imageView.setImageBitmap(resource);
                        bmp[0] = resource;
                    }
                }
            });
        }

        mFinishDig.setContentView(fView);
        mFinishDig.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

//                if (!isLearnCard) {
//
//                }
                if (bmp[0] != null) {
//                    bmp[0].recycle();
                    bmp[0] = null;
                }
                System.gc();
                mSzSurfaceView.start();
            }
        });
        mFinishDig.show();
    }

    private void showPermissionDig() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewVideoInfoActivity.this);
        builder.setTitle(R.string.perssion_title)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MyApplication.mApplication, R.string.save_defeated, Toast.LENGTH_SHORT).show();
                        if (mPermissionDig != null) {
                            mPermissionDig.dismiss();
                        }
                    }
                })
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO 去设置开启
                        PermissionUtils.GoToSetting(NewVideoInfoActivity.this);
                    }
                });
        mPermissionDig = builder.create();
        mPermissionDig.setCanceledOnTouchOutside(false);
        mPermissionDig.setCancelable(false);
        mPermissionDig.show();
    }

    //TODO
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail:
                mVideoContent.setCurrentItem(0);
                break;
            case R.id.catalogue:
                mVideoContent.setCurrentItem(1);
                break;
            case R.id.comment:
                mVideoContent.setCurrentItem(2);
                break;
            case R.id.video_buy_vip:
//                Log.e("DH_VIP_ID",mInfoBean.getVip_id()+"  ");
                isPayCourseOrAction = 4;
                Intent intent = new Intent(NewVideoInfoActivity.this, VipListActivity.class);
//                intent.putExtra("event_id", mInfoBean.getVip_id() + "");
                startActivity(intent);
                break;
            case R.id.video_buy_course:
                Log.e("DH_VIDEO_RAND", mRandStr + "  " + mLogin_rand_str);
                if (StringUtils.isNotEmpty(mUserId) && mRandStr.equals(mLogin_rand_str)) {
                    mPayMap.put("goodsType", "3");
                    mPayMap.put("id", mCourseId);
                    mPayMap.put("user_id", mUserId);
                    mPayMap.put("coupon_code", "");
                    mPayMap.put("coupon_cut", mCouponId);
                    mPayMap.put("pay_type", "4");
                    mPayMap.put("buy_self", "1");
                    mPayMap.put("num", "0");
                    mPayMap.put("multi_buy", "0");
                    Intent intent1 = new Intent(NewVideoInfoActivity.this,OrderActivity.class);
                    intent1.putExtra("ordMap",(Serializable)mPayMap);
                    intent1.putExtra("flag","0");
                    intent1.putExtra("className",mInfoBean.getTitle());
                    intent1.putExtra("price",mInfoBean.getNew_price());

                    startActivity(intent1);
//                    showNewManyPayPop();
                } else {
                    intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cache_video_cancel:
                startCacheAni();
                break;
            case R.id.cache_video_all:
                if(mFrom==1){//学习小组课程
                    Toast.makeText(MyApplication.mApplication, "当前课程无法缓存", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mDownLoadList != null && mDownLoadList.size() > 0) {
                    for (int i = 0; i < mDownLoadList.size(); i++) {
                        downLoadVideo(mDownLoadList.get(i));
                    }
                    Toast.makeText(MyApplication.mApplication, "成功添加,正在缓存..", Toast.LENGTH_SHORT).show();
                    //获取缓存数
                    int fatherId = Integer.parseInt(mCourseId);
                    ArrayList<SQLDownLoadInfo> downLoadingList = mDataKeeper.getUserDownLoadInfoByFatherId(fatherId);
                    mTvHaveDownloadNums.setText(downLoadingList.size() + "");
                    if (mDownLoadList == null) {
                        mSumUpDownLoadAdapter = new SumUpDownLoadAdapter(mDownLoadList, this);
                        mCacheVideoList.setAdapter(mSumUpDownLoadAdapter);
                    } else {
                        mSumUpDownLoadAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.check_cache_video:
                intent = new Intent(this, DownManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.count_down_back:
                finish();
                break;
        }
    }

    /**
     * 添加缓存
     */
    private void downLoadVideo(SumUpEntity.DirBean bean) {
        String video_id = bean.getVideo_id();
        int fatherId = Integer.parseInt(video_id);
        String fatherTitle = mInfoBean.getTitle();
        String fatherImg = mInfoBean.getImg();
        String id = bean.getId();
        int childId = Integer.parseInt(id);
        String chileTitle = bean.getVideo_title();
        File downloadFile = new File(FileHelper.getFileDefaultPath() + "/" + "." + childId + ".mp4");
        if (!downloadFile.exists()) {
            if (mDataKeeper == null) {
                mDataKeeper = new DataKeeper(this);
            }
            if (!mDataKeeper.isHave(fatherId, childId)) {
                String chileUrl = "http://u.wimg.cc/" + bean.getDownload_url();
                if(manager==null)
                    manager = DownLoadService.getDownLoadManager();
                manager.addTask(fatherId, fatherTitle, fatherImg, childId, chileUrl, chileTitle);
//                mDownLoadPresenter.downLoadVideo(id, "0", mUserId, new OnResultListener() {
//                    @Override
//                    public void onSuccess(Object... value) {
//
//                    }
//
//                    @Override
//                    public void onFault(String error) {
//
//                    }
//                });
            }
        }
    }


    /**
     * 多人购买弹窗
     */
    private PopNewManyPay mPopNewManyPay;

    public void showNewManyPayPop() {
        if (mIs_vip == 1 || "1".equals(mPay_status) ? true : false) {
            intentManyPay();
        } else {
            mPopNewManyPay = new PopNewManyPay(this, mCourseTitle, Double.parseDouble(mPayPrice), mIs_vip == 1 || "1".equals(mPay_status) ? 1 : 0);
            mPopNewManyPay.showAtLocation(findViewById(R.id.activity_new_video_info), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            mMc.setVisibility(View.VISIBLE);
            mPopNewManyPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mCouponId = "";
                    mMc.setVisibility(View.GONE);
                }
            });
            mPopNewManyPay.setOnManyPayCliclListener(new PopNewManyPay.OnManyPayCliclListener() {
                @Override
                public void onManyPayClick() {
                    intentManyPay();
                    if (mPopNewManyPay != null && mPopNewManyPay.isShowing()) {
                        mPopNewManyPay.dismiss();
                    }
                }
            });
            mPopNewManyPay.setOnPayClickListener(new PopNewManyPay.OnPayClickListener() {
                @Override
                public void onPayClick(int payWay) {
                    isPayCourseOrAction = 0;
                    isPayCourseType = mType;
                    mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
                    if (StringUtils.isEmpty(mUserId)) {
                        Intent intent = new Intent(NewVideoInfoActivity.this,LoginNewActivity.class);
                        startActivity(intent);
                        return;
                    }
                    mPayMap.put("goodsType", "3");
                    mPayMap.put("id", mCourseId);
                    mPayMap.put("user_id", mUserId);
                    mPayMap.put("coupon_code", "");
                    mPayMap.put("coupon_cut", mCouponId);
                    mPayMap.put("pay_type", "4");
                    mPayMap.put("buy_self", "1");
                    mPayMap.put("num", "0");
                    mPayMap.put("multi_buy", "0");
//                    Intent intent = new Intent(NewVideoInfoActivity.this,OrderActivity.class);
//                    intent.putExtra("ordMap",(Serializable)mPayMap);
//                    startActivity(intent);
                    if (payWay == WAY_WEICHAT) {
                        Toast.makeText(MyApplication.mApplication, "正在前往微信..", Toast.LENGTH_SHORT).show();
                        weichatPay();
                    } else if (payWay == WAY_ALIPAY) {
                        Toast.makeText(MyApplication.mApplication, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                        aliPay();
                    }
                    if (mPopNewManyPay != null && mPopNewManyPay.isShowing()) {
                        mPopNewManyPay.dismiss();
                    }
                }

                @Override
                public void onSelectCouponClick() {
                    Intent intent = new Intent(NewVideoInfoActivity.this, SelectCouponActivity.class);
                    intent.putExtra("course_price",mPayPrice);
                    intent.putExtra("coupon_id",mCouponId);
                    intent.putExtra("id",mCourseId);
                    intent.putExtra("category","1");
                    startActivityForResult(intent,COUPON_REQUEST);
                }
            });
        }
    }


    /**
     *优惠券带参返回的信息
     */
    private String mCouponId = "";
    private String mCouponCode = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==COUPON_REQUEST&&resultCode==COUPON_RESULT){
            mCouponId = data.getStringExtra("coupon_id");
            String coupon_price = data.getStringExtra("price");
            mCouponCode = data.getStringExtra("coupon_code");
            if(mPopNewManyPay!=null&&mPopNewManyPay.isShowing()){
                mPopNewManyPay.setCouponInfo(coupon_price,mCouponId);
            }
        }
    }

    /**
     * 多人购买
     */
    private void intentManyPay() {
        Intent intent = new Intent(this, SelectApplyActivity.class);
        intent.putExtra("id", mCourseId);
        intent.putExtra("title", mCourseTitle);
        intent.putExtra("original_price", mOld_price);
        intent.putExtra("vip", mIs_vip);
        startActivity(intent);
    }

    /**
     * 获取视频详情的信息
     */
    private void getVideoInfo() {
        if (mSumUpInfoPresenter == null) {
            mSumUpInfoPresenter = new SumUpInfoPresenter();
        }

        String imei = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"imei","");
        Log.e("DH_VIDEO", mUserId + "  " + mCourseId + "  " + mType + "  " + imei);
        mSumUpInfoPresenter.getSumUpInfo(mUserId, mCourseId, mType, imei, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingView.setVisibility(View.GONE);
                SumUpEntity entity = (SumUpEntity) value[0];
                mInfoBean = entity.getInfo().get(0);
                mIsCoupon = entity.getIsCoupon();
                mPerson = mInfoBean.getPerson();
                mYunumber = mInfoBean.getYunumber();
                mIs_buy_company = mInfoBean.getIs_buy_company();
                mIs_company = mInfoBean.getIs_company();
                mPayPrice = mInfoBean.getNew_price();
                mOld_price = mInfoBean.getOld_price();
                mRandStr = mInfoBean.getRand_str();
                mIs_vip = mInfoBean.getIs_vip();
                //更新本地会员状态
                SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "is_vip", mIs_vip + "");
                mPay_status = mInfoBean.getPay_status();
                mCourseTitle = mInfoBean.getTitle();
                mVideoDir = entity.getDir();
                cateID = mInfoBean.getCate_id();
//                cateID = "5738";

                mSzSurfaceView.setBreakpoint(mInfoBean.getCate_player());
                mDetailsFragment.setDetailsInfo(mInfoBean, mVideoDir);
                //定位小结
                if (cateID == null || "".equals(cateID) || "-1".equals(cateID)) {
                    if (mVideoDir != null) {
                        if (mVideoDir.get(0).getUrl() != null && !"".equals(mVideoDir.get(0).getUrl()) && !"null".equals(mVideoDir.get(0).getUrl())) {
                            cateID = mVideoDir.get(0).getId();
                        } else {
                            cateID = mVideoDir.get(1).getId();
                        }
                    }
                } else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mVideoContent.setCurrentItem(1);
                        }
                    },300);
                }
                mCatalogueFragment.initCatalogue(mVideoDir, cateID, mCourseId, mInfoBean.getRand_str(), entity.getExam());
                if (mCommentFragment!=null) {
                    mCommentFragment.initComment(entity.getComment(), mCourseId);
                }

                /**
                 * 购买状态
                 */
                if (!"0.00".equals(mPayPrice)) {
                    mVideoBuyCourse.setOnClickListener(NewVideoInfoActivity.this);
                    if(mFrom==0){
                        mVideoBuyMenu.setVisibility(View.VISIBLE);
                    }else{
                        mVideoBuyMenu.setVisibility(View.GONE);
                    }

                    Log.e("DH_VIP_STATE", "vip:" + mIs_vip);
                    if (mIs_vip != 1) {
                        mVideoBuyVip.setVisibility(View.VISIBLE);
                        mVideoBuyVip.setOnClickListener(NewVideoInfoActivity.this);
                        if ("1".equals(mPay_status)) {
                            mVideoBuyCourse.setText("为好友购买");
                            mVideoBuyCourse.setVisibility(View.GONE);
                        } else {
                            mVideoBuyCourse.setText("购买:  ¥" + mPayPrice);
                        }
                    } else {
                        mVideoBuyVip.setVisibility(View.GONE);
                        mVideoBuyCourse.setText("为好友购买");
                        mVideoBuyCourse.setVisibility(View.GONE);
                        mVideoBuyVip.setOnClickListener(null);
                    }

                } else {
                    mVideoBuyMenu.setVisibility(View.GONE);
                }

                countDown();
                if (maps == null) {
                    if (!isFinishing()) {
                        initVideo();
                    }

                }
                initCache();
                if (!mRandStr.equals(mLogin_rand_str)) {
                    SharedPreferenceUtil.clearInfo(NewVideoInfoActivity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewVideoInfoActivity.this);
                    builder.setMessage(R.string.again_login_title)
                            .setNegativeButton(R.string.cancel, null)
                            .setPositiveButton(R.string.again_login, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(NewVideoInfoActivity.this, LoginNewActivity.class);
                                    startActivity(intent);
                                }
                            });
                    Dialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }

                if ("1".equals(mInfoBean.getEx_click())) {
                    mLearnCard.setVisibility(View.VISIBLE);
                    mLearnCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mSzSurfaceView.isPlaying()){
                                mSzSurfaceView.pause();
                            }
                            showFinishDig(mInfoBean.getStudy_achieve_url());
                        }
                    });
                }

            }

            @Override
            public void onFault(String error) {
                Log.e("DH_VIDEO_INFO", error);
                Toast.makeText(MyApplication.mApplication, R.string.data_error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 初始化缓存
     */
    List<SumUpEntity.DirBean> mDownLoadList = new ArrayList<>();

    private void initCache() {
        if (mVideoDir != null && mVideoDir.size() > 0) {
            if (mDownLoadList != null && mDownLoadList.size() > 0) {
                mDownLoadList.clear();
            }
            for (int i = 0; i < mVideoDir.size(); i++) {
                SumUpEntity.DirBean dirBean = mVideoDir.get(i);
                if (dirBean.getUrl() != null && !"null".equals(dirBean.getUrl()) && !"".equals(dirBean.getUrl().trim())) {
                    mDownLoadList.add(dirBean);
                }
            }
        }
        mSumUpDownLoadAdapter = new SumUpDownLoadAdapter(mDownLoadList, this);
        mCacheVideoList.setAdapter(mSumUpDownLoadAdapter);
        mSumUpDownLoadAdapter.setOnDownloadClickListener(this);
        //获取缓存数
        int fatherId = Integer.parseInt(mCourseId);
        ArrayList<SQLDownLoadInfo> downLoadingList = mDataKeeper.getUserDownLoadInfoByFatherId(fatherId);
        mTvHaveDownloadNums.setText(downLoadingList.size() + "");
    }

    /**
     * 缓存单个
     *
     * @param pos
     * @param bean
     */
    @Override
    public void onDownloadClick(int pos, SumUpEntity.DirBean bean) {
        downLoadVideo(bean);
        int fatherId = Integer.parseInt(mCourseId);
        ArrayList<SQLDownLoadInfo> downLoadingList = mDataKeeper.getUserDownLoadInfoByFatherId(fatherId);
        mTvHaveDownloadNums.setText(downLoadingList.size() + "");
    }

    /**
     * 初始化视频信息
     */
    private List<String> cateList = new ArrayList<>();
    LinkedHashMap<String, SumUpEntity.DirBean> maps;

    private void initVideo() {
        maps = new LinkedHashMap<>();
        mSzSurfaceView.setVideoImg(mInfoBean.getImg());

        if (mVideoDir != null) {
            int index = 0;
            int catalogue_index = 0;//服务器获取目录索引
            int cate_index = 0;//提取url之后cate的索引
            for (int i = 0; i < mVideoDir.size(); i++) {
                if (mVideoDir.get(i).getUrl() != null && !"null".equals(mVideoDir.get(i).getUrl()) && mVideoDir.get(i).getUrl().length() > 0) {
                    maps.put(index + "", mVideoDir.get(i));
                    cateList.add(mVideoDir.get(i).getId());
                    if (cateID != null && !"".equals(cateID) && !"-1".equals(cateID) && cateID.equals(mVideoDir.get(i).getId())) {
                        cate_index = index;
                    }
                    index++;
                }
                if (cateID != null && !"".equals(cateID) && !"-1".equals(cateID) && cateID.equals(mVideoDir.get(i).getId())) {
                    catalogue_index = i;
                }
            }
            Log.e("DH_CATE", "cate_id:" + cateID + "  catalogue_index:" + catalogue_index + "  cate_index:" + cate_index);
            if (cateID != null && !"".equals(cateID) && !"-1".equals(cateID)) {
                mCurrentDir = mVideoDir.get(catalogue_index);
//                startSendProgress(mCurrentDir);
            } else {
                if (StringUtils.isNotEmpty(mVideoDir.get(0).getUrl()) && !"null".equals(mVideoDir.get(0).getUrl())) {
                    mCurrentDir = mVideoDir.get(0);
//                    startSendProgress(mCurrentDir);
                } else {
                    mCurrentDir = mVideoDir.get(1);
//                    startSendProgress(mCurrentDir);
                }
            }
            mSzSurfaceView.setVideoUrl(maps, cate_index);

        }
    }


    //计时器上传观看进度
    Timer timer = new Timer();

    public void startSendProgress(SumUpEntity.DirBean dirbean) {
        clearTimer();
        if(dirbean==null){
            return;
        }
        timer = new Timer();
        timer.schedule(new ControllerTimerTask(dirbean), 1000, 30000);
    }

    private void clearTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * 打开关闭缓存弹窗
     */
    @Override
    public void onCacheClick() {
        if (mIsAllow) {

            if (StringUtils.isNotEmpty(mUserId) && mRandStr.equals(mLogin_rand_str)) {
                if (!"0.00".equals(mPayPrice)) {
                    if (!"1".equals(mPay_status)) {
                        if (mIs_vip != 1) {
                            Toast.makeText(MyApplication.mApplication, "请先购买课程", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                startCacheAni();
            } else {
                Intent intent = new Intent(this, LoginNewActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(MyApplication.mApplication, R.string.open_class_no_cache, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 评论键盘隐藏显示
     */
    @Override
    public void onKeyboardClick() {
        mVideoBuyMenu.setVisibility(View.GONE);
    }

    public class ControllerTimerTask extends TimerTask {
        private SumUpEntity.DirBean dirbean;

        public ControllerTimerTask(SumUpEntity.DirBean dirbean) {
            this.dirbean = dirbean;
        }

        @Override
        public void run() {
            sendVideoProgress(dirbean);
        }
    }
    /**
     * 发送视频进度
     */
    private void sendVideoProgress(SumUpEntity.DirBean dirbean) {
        int second = Math.round(mSzSurfaceView.getCurrentTime() / 1000);
        dirbean.setPlaytime(second);
        Log.e("DH_SEND_PROGRESS", "Video_id:" + dirbean.getVideo_id() + "    user_id:" + mUserId + "   dirbean.getId:" + dirbean.getId() + "  second:" + second + "  Length:" + dirbean.getLength());
        HttpUserManager.getInstance().VideoProgress(mPid,dirbean.getVideo_id(), mUserId, dirbean.getId(), second + "", dirbean.getLength() + "", new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, VideoLoginBean.class);
    }

    /**
     * 判断公开课倒计时
     */
    private void countDown() {
        if (mInfoBean.getPublish_shijian() == null || "".equals(mInfoBean.getPublish_shijian())
                || mInfoBean.getNow_time() == null || "".equals(mInfoBean.getNow_time())) {
            mIsAllow = true;
            mCountDown.setVisibility(View.GONE);

        } else {
            if (mInfoBean.getPublish_shijian() != null && !"".equals(mInfoBean.getPublish_shijian())) {
                try {
                    mPublicTime = Long.parseLong(mInfoBean.getPublish_shijian());
                } catch (Exception e) {
                    mPublicTime = 0;
                }
            }
            if (mInfoBean.getNow_time() != null && !"".equals(mInfoBean.getNow_time())) {
                try {
                    mNowTime = Long.parseLong(mInfoBean.getNow_time());
                } catch (Exception e) {
                    mNowTime = 0;
                }
            }
            mResidueTime = mPublicTime - mNowTime;
            if (mResidueTime <= 0) {
                mIsAllow = true;
                mCountDown.setVisibility(View.GONE);
            } else {
                mIsAllow = false;
                mCountDown.setVisibility(View.VISIBLE);
                mCountDown.setOnClickListener(this);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
                String public_time = sdf.format(new Date(mPublicTime * 1000));
                mPublicTimeTV.setText(public_time + "开课");
                residueCountDown(mResidueTime);
            }
        }
        mSzSurfaceView.setVisibility(View.VISIBLE);
    }

    /**
     * 倒计时
     */
    private long DAT = 24 * 60 * 60;
    private long HOUR = 60 * 60;
    private long MINUTE = 60;

    private void residueCountDown(long residueTime) {
        if (mCountDownTimer == null) {
            starCountDown(residueTime);
            mCountDownTimer = new CountDownTimer(residueTime * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    starCountDown(l / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MyApplication.mApplication, "课程开始了！", Toast.LENGTH_SHORT).show();
                    mIsAllow = true;
                    mCountDown.setVisibility(View.GONE);
                }
            };
            mCountDownTimer.start();
        }
    }

    /**
     * 时间更新
     *
     * @param time
     */
    private void starCountDown(long time) {
        long day = time / DAT;
        long hour = time % DAT / HOUR;
        long minute = time % DAT % HOUR / MINUTE;
        long second = time % DAT % HOUR % MINUTE;
        mResidueTimeDay.setText(day + "");
        mResidueTimeHour.setText(hour + "");
        mResidueTimeMinute.setText(minute + "");
        mResidueTimeSecond.setText(second + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        sendVideoProgress(mCurrentDir);
        EventBus.getDefault().unregister(this);
        if (mFinishDig != null && mFinishDig.isShowing()) {
            mFinishDig.dismiss();
        }
        clearTimer();
        mSzSurfaceView.stop();
        mSzSurfaceView.destroy();
        if (mHandler!=null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onBackPressed() {
        /**
         * 返回全屏处理
         */
        if (FULL_SCREEN == 1) {
            FULL_SCREEN = 0;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }

    /**
     * 全屏配置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
            FULL_SCREEN = 0;
            //显示状态栏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mVideoDetail.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            //设置view的布局，宽高之类
            ViewGroup.LayoutParams surfaceViewLayoutParams = mVideoDetail.getLayoutParams();
            surfaceViewLayoutParams.height = (int) (getWight(this) * 9.0f / 16);
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
            FULL_SCREEN = 1;
            //隐藏状态栏
            KeyBoardUtils.hideKeyboard(NewVideoInfoActivity.this);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mVideoDetail.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
            //设置view的布局，宽高
            ViewGroup.LayoutParams surfaceViewLayoutParams = mVideoDetail.getLayoutParams();
            surfaceViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }

    public static int getWight(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * 视频进度控制
     *
     * @return false 暂停播放  true 允许播放
     */
    @Override
    public boolean processClick() {
        if (StringUtils.isNotEmpty(mUserId) && mRandStr.equals(mLogin_rand_str)) {
            if (!"0.00".equals(mPayPrice)) {
                if (mIs_vip != 1) {
                    if (!"1".equals(mPay_status)) {
                        if (SZSurfaceView.FULL_SCREEN == 1) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        Toast.makeText(MyApplication.mApplication, "请付费后学习完整内容", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        } else {
            if (SZSurfaceView.FULL_SCREEN == 1) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            Intent intent = new Intent(this, LoginNewActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    @Override
    public void updateProcess() {
        sendVideoProgress(mCurrentDir);
    }

    @Override
    public void startProcessClick() {
        startSendProgress(mCurrentDir);
        Log.e("DH_SEND_PROGRESS","startProcessClick");
    }

    /**
     * 分享
     */
    private Dialog mShareDialog;

    @Override
    public void onShareClick() {
        if (StringUtils.isNotEmpty(mUserId)) {

            mShareDialog = new Dialog(NewVideoInfoActivity.this, R.style.ActionSheetDialogStyle);
            //填充对话框的布局
            View inflate = LayoutInflater.from(NewVideoInfoActivity.this).inflate(R.layout.share_dialog, null);
            //初始化控件
            RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
            RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
            RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
            RelativeLayout shareRlayoutCompany = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_company);
            Log.e("DH_mIs_company", mInfoBean.getShare_url() + "?helper=" + mUserId);
            if ("0".equals(mIs_company)) {
                shareRlayoutCompany.setVisibility(View.GONE);
            } else if ("1".equals(mIs_company)) {
                shareRlayoutCompany.setVisibility(View.VISIBLE);
            }
            share_rlayout_py.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShareDialog.dismiss();
                    //msgApi
                    isShareInform = true;
                    isPayCourseOrAction = 2;
                    temporaryEventNo = mCourseId;
                    ShareUtil.weiChat(Constants.wx_api, 7, NewVideoInfoActivity.this,
                            mInfoBean.getShare_url() + "?helper=" + mUserId,
                            "已有"+mInfoBean.getLearn()+"位餐饮人学过《"+mInfoBean.getTitle()+"》，快来加入我们",
                            mInfoBean.getSub_title());
                }
            });

            share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mShareDialog.dismiss();
                    isShareInform = true;
                    isPayCourseOrAction = 2;
                    temporaryEventNo = mCourseId;
                    ShareUtil.weiChat(Constants.wx_api, 8, NewVideoInfoActivity.this,
                            mInfoBean.getShare_url() + "?helper=" + mUserId,
                            "已有"+mInfoBean.getLearn()+"位餐饮人学过《"+mInfoBean.getTitle()+"》，快来加入我们",
                            mInfoBean.getSub_title());
                }
            });
            shareRlayoutCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("0.00".equals(mPayPrice)) {
                        if ("1".equals(mIs_buy_company)) {
                            if (mPerson != null && !"0".equals(mPerson)) {
                                Intent intent = new Intent(NewVideoInfoActivity.this, CompanyMessageActivity.class);
                                intent.putExtra("id", mCourseId);
                                intent.putExtra("type", "1");
                                intent.putExtra("courseOrAction", "2");
                                startActivity(intent);
                            } else {
                                Toast.makeText(MyApplication.mApplication, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if (mPerson != null && !"0".equals(mPerson)) {
                                Intent intent = new Intent(NewVideoInfoActivity.this, CompanyMessageActivity.class);
                                intent.putExtra("id", mCourseId);
                                intent.putExtra("type", "2");
                                intent.putExtra("courseOrAction", "2");
                                startActivity(intent);
                            } else {
                                Toast.makeText(MyApplication.mApplication, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        Log.e("DH_IS_BUY", mIs_buy_company);
                        if ("1".equals(mIs_buy_company)) {
                            if (mPerson != null && !"0".equals(mPerson)) {
                                Intent intent = new Intent(NewVideoInfoActivity.this, CompanyMessageActivity.class);
                                intent.putExtra("id", mCourseId);
                                intent.putExtra("type", "1");
                                intent.putExtra("courseOrAction", "2");
                                startActivity(intent);
                            } else {
                                Toast.makeText(MyApplication.mApplication, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            double num = 0;
                            if (mYunumber != null && !"".equals(mYunumber)) {
                                try {
                                    num = Double.parseDouble(mYunumber);
                                } catch (Exception e) {
                                    num = 0;
                                }
                            }
                            if (num > 0) {
                                if (mPerson != null && !"0".equals(mPerson)) {
                                    Intent intent = new Intent(NewVideoInfoActivity.this, CompanyMessageActivity.class);
                                    intent.putExtra("id", mCourseId);
                                    intent.putExtra("type", "2");
                                    intent.putExtra("courseOrAction", "2");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MyApplication.mApplication, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(MyApplication.mApplication, "请先“为好友购买”后再分享至企业号", Toast.LENGTH_SHORT).show();
//                                showNewManyPayPop();
                            }

                        }
                    }

                    mShareDialog.dismiss();
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
        } else {
            Intent intent = new Intent(this, LoginNewActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 微信支付
     */
    private void weichatPay() {
//        mOrderPayPresenter.orderPayJson(mPayMap, "", new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//
//            }
//
//            @Override
//            public void onFault(String error) {
//
//            }
//        });
        Log.e("vvvvvv",mPayMap.toString());
        mOrderPayPresenter.orderPay(mPayMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_PAY_WECHET", "success");
                PayEntity entity = (PayEntity) value[0];
                if( entity.getInfo()!=null){
                    temporaryEventNo = entity.getInfo().getOrder_no();
                    PayEntity.InfoBean.WechatBean wechat = entity.getInfo().getWechat();
                    IWXAPI wxApi = WXAPIFactory.createWXAPI(NewVideoInfoActivity.this, null);
                    wxApi.registerApp(Constants.APP_ID);
                    PayReq req = new PayReq();
                    req.appId = wechat.getAppid();
                    req.nonceStr = wechat.getNoncestr();
                    req.packageValue = wechat.getPackageX();
                    req.prepayId = wechat.getPrepayid();
                    req.partnerId = wechat.getPartnerid();
                    req.timeStamp = wechat.getTimestamp();
                    req.sign = wechat.getSign();
                    Log.e("DH_ORDER","appid:"+wechat.getAppid()+"  str:"+wechat.getNoncestr()+"  packe:"+wechat.getPackageX()+"  pre_id:"+wechat.getPrepayid()+"  p_id:"+wechat.getPartnerid()+
                    "  sign:L"+ wechat.getSign()+"  time:"+wechat.getTimestamp());
                    wxApi.sendReq(req);
                }else{
                    Toast.makeText(MyApplication.mApplication, "数据异常", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFault(String error) {
                Log.e("DH_PAY_WECHET", error);
            }
        });
    }

    private void aliPay() {
//        mOrderPayPresenter.orderPayJson(mPayMap, "", new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//
//            }
//
//            @Override
//            public void onFault(String error) {
//
//            }
//        });
        mOrderPayPresenter.orderPay(mPayMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                PayEntity entity = (PayEntity) value[0];
                if(entity.getInfo()!=null){
                    final String orderInfo = entity.getInfo().getAlipay();
                    temporaryEventNo = entity.getInfo().getOrder_no();
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(NewVideoInfoActivity.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }else{
                    Toast.makeText(MyApplication.mApplication, "数据异常", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFault(String error) {
                Log.e("DH_PAY_ALIPAY", error);
            }
        });
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MyApplication.mApplication, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewVideoInfoActivity.this, ResultActivity.class);
                        intent.putExtra("type", String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no", temporaryEventNo);
                        intent.putExtra("course_type", String.valueOf(isPayCourseType));
                        startActivity(intent);
                    } else {
                        Toast.makeText(MyApplication.mApplication, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    temporaryEventNo = "";
                    isPayCourseOrAction = -1;
                    isPayCourseType = "";
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * 缓存弹窗动画
     */
    public void startCacheAni() {
        Animation ani;
        if (!mIsShowCache) {
            mIsShowCache = true;
            mCache.setVisibility(View.VISIBLE);
            mCache.setOnClickListener(this);
            ani = AnimationUtils.loadAnimation(MyApplication.mApplication, R.anim.actionsheet_dialog_in);
            ani.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    mVideoInfo.setVisibility(View.GONE);
//                    mVideoBuyMenu.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            mIsShowCache = false;
            mCache.setVisibility(View.GONE);
//            mVideoInfo.setVisibility(View.VISIBLE);
//            if (!"0.00".equals(mPayPrice)) {
//                mVideoBuyMenu.setVisibility(View.VISIBLE);
//            }
            ani = AnimationUtils.loadAnimation(MyApplication.mApplication, R.anim.actionsheet_dialog_out);
            ani.setAnimationListener(null);
        }

        mCache.startAnimation(ani);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mSzSurfaceView.savePlayerState();
//        mSzSurfaceView.pause();
//        if (mPopNewManyPay != null && mPopNewManyPay.isShowing()) {
//            mPopNewManyPay.dismiss();
//        }
    }

    /**
     * 目录点击监听
     *
     * @param dirBean
     */
    @Override
    public void OnCatalogueVideoClick(SumUpEntity.DirBean dirBean) {
        if (mIsAllow) {
            if("0".equals(dirBean.getCanshow())){
                Toast.makeText(MyApplication.mApplication, "该小节未到上课时间", Toast.LENGTH_SHORT).show();
                return;
            }
            /**
             * 重置url和计时器 视频断点 TODO
             */
            mSzSurfaceView.pause();
            if(mCurrentDir!=null&&!mCurrentDir.getId().equals(dirBean.getId())){
                Log.e("DH_SEND_PROGRESS","id:"+dirBean.getId()+"   play_time:"+dirBean.getPlaytime());
                mSzSurfaceView.setBreakpoint(dirBean.getPlaytime());
            }
            mCurrentDir = dirBean;
            mSzSurfaceView.setVideoUrl(dirBean);
            mSzSurfaceView.setVideoImg(mInfoBean.getImg());
            mSzSurfaceView.start();

        } else {
            Toast.makeText(MyApplication.mApplication, "此课尚未开始哦!", Toast.LENGTH_SHORT).show();
        }
    }


    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    /**
     * 权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限请求成功的操作
                } else {
                    showPermissionDig();
                }
                return;
            }

            // case其他权限结果。。
        }
    }
}
