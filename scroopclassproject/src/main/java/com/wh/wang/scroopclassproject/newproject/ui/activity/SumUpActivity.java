package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.DownManagerActivity;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CollectEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.UpdateProgressEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CollectPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.DownLoadPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.SumUpInfoPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.UpdateProgressPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SumUpDownLoadAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.WindowUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopManyPay;
import com.wh.wang.scroopclassproject.newproject.view.PopNewManyPay;
import com.wh.wang.scroopclassproject.newproject.view.PopPay;
import com.wh.wang.scroopclassproject.newproject.view.SZSurfaceView;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.FileUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseType;
import static com.wh.wang.scroopclassproject.newproject.Constant.isShareInform;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity.manager;
import static com.wh.wang.scroopclassproject.newproject.view.SZSurfaceView.FULL_SCREEN;

public class SumUpActivity extends AppCompatActivity implements View.OnClickListener, SumUpDownLoadAdapter.OnDownloadClickListener, SZSurfaceView.OnProcessClickListener, SZSurfaceView.OnShareClickListener {
    private FrameLayout mCacheVideo;
    private RelativeLayout mCache;
    private ScrollView mVideoInfo;
    private ImageView mCacheVideoCancel;
    private FrameLayout mCollectVideo;
    private ImageView mSumUpCollect;
    //    private SumUpJCVideoPlayerStandard mSumUpVideo;
    private SZSurfaceView mSumUpVideo;
    private TextView mPayCourse;
    private TextView mSumUpTitle;
    private TextView mPlayNum;
    private TextView mSumUpFitpersonContent;
    private RoundedImageView mSumUpTeacherAvatar;
    private TextView mSumUpTeacherName;
    private TextView mSumUpTeacherInfo;
    private WebView mSumUpDetailWebview;
    private TextView mOpenVip;
    private RecyclerView mCacheVideoList;
    private LinearLayout mSumUpBottomTitle;
    private RelativeLayout mCheckCacheVideo;
    private TextView mCacheVideoFreeSpace;
    private TextView mTvHaveDownloadNums;
    private TextView mCacheVideoAll;
    private ImageView mLearnAllCourseImg;
    private TextView mLearnAllCourseName;
    private TextView mLearnAllCoursePrice;
    private RelativeLayout mLearnAll;
    private TextView mLearnAllCourseTitle;
    private View mMc;


    //当前视频播放地址
    private String mPlayVideoUrl;
    private SumUpEntity.DirBean mPlayBean;

    //当前视频下载地址
    private String mDownloadVideoUrl;
    //是否显示缓存界面
    private boolean isShowCache = false;
    //当前 收藏状态
    private boolean isCollect = false;
    //当前课程价格
    private String mCoursePrice = "0.00";
    //当前用户会员状态
    private String mVIPState = "0";
    //当前视频唯一登陆标识
    private String mVideoRandStr = "";
    //当前用户购买状态 1：已支付  2：未支付 3：支付中 4：已退款
    private String mPayState = "2";
    //vip_id
    private String mVIPId = "";
    //当前支付方式 2:支付宝  3：微信
    private int mPayWay = 2;
    //弹窗仅一次
    private boolean isShowPayDig = false;

    //Handler状态 1:支付宝结果回调  2：发起支付宝支付  3：发起微信支付
    public static final int SDK_PAY_FLAG = 1;
    public static final int WAY_ALIPAY = 2;
    public static final int WAY_WEICHAT = 3;
    public static final int WAY_TRANSFER = 4;

    public int videosizes;
    public int videoindex;

    //获取视频信息接口
    private SumUpInfoPresenter mSumUpInfoPresenter = new SumUpInfoPresenter();
    //更新视频进度接口
    private UpdateProgressPresenter mUpdateProgressPresenter = new UpdateProgressPresenter();
    //收藏接口
    private CollectPresenter mCollectPresenter = new CollectPresenter();
    //支付订单
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    //下载接口
    private DownLoadPresenter mDownLoadPresenter = new DownLoadPresenter();

    private String mCourse_id;
    private String mUser_id;
    //登录唯一标识
    private String mLoginRandStr;
    private int positionPro = 0;
    //    private CenterDialog mPayDig;
//    private TextView mDigPrice;
//    private TextView mDigTitle;
//    private TextView mDigWorn;
    private String mCourseTitle;
    private Timer mProgressTimer;
    //小结基本信息
    private SumUpEntity.InfoBean mInfoBean;

    private List<SumUpEntity.DirBean> mDownLoadList = new ArrayList<>();
    private SumUpDownLoadAdapter mSumUpDownLoadAdapter;

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
                        mPayState = "1";
                        Intent intent = new Intent(SumUpActivity.this, ResultActivity.class);
                        intent.putExtra("type", String.valueOf(isPayCourseOrAction));
                        intent.putExtra("course_type", String.valueOf(isPayCourseType));
                        intent.putExtra("order_no", temporaryEventNo);
                        startActivity(intent);
                        mPayCourse.setText("为好友购买");
                    } else {
                        Toast.makeText(MyApplication.mApplication, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    temporaryEventNo = "";
                    isPayCourseOrAction = -1;
                    isPayCourseType = "";
                    break;
                }
                case WAY_ALIPAY:
                    entity = (PayEntity) msg.obj;
                    if (entity != null) {
                        final String orderInfo = entity.getInfo().getAlipay();
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(SumUpActivity.this);
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
                    }
                    break;
                case WAY_WEICHAT:
                    entity = (PayEntity) msg.obj;
                    if (entity != null) {
                        PayEntity.InfoBean.WechatBean wechat = entity.getInfo().getWechat();
                        IWXAPI wxApi = WXAPIFactory.createWXAPI(SumUpActivity.this, null);
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
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private String mType = "4";
    private String mAllCourseId;
    private String mAllCourseType;
    private WebSettings mWebSetting;
    private String mIs_company;
    private String mIs_buy_company;
    private String mYunumber;
    private String mPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_up);
        WindowUtils.getInstance().fullScreen(this);
        MobclickAgent.onEvent(mContext, "coursedes");
        initView();
        initIntent();
//        initJCVideo();
        iniOther();
        initRecycle();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
        getNetData();
    }


    private void initIntent() {
        mCourse_id = getIntent().getStringExtra("id");
        mType = getIntent().getStringExtra("type");
//        mCourse_id = "1295";
        if (mType == null || "".equals(mType)) {
            mType = "4";
        }
    }


    private void initJCVideo() {
//        mSumUpVideo.activity = this;
    }

    private void initView() {
        mCacheVideo = (FrameLayout) findViewById(R.id.cache_video);
        mCache = (RelativeLayout) findViewById(R.id.cache);
        mVideoInfo = (ScrollView) findViewById(R.id.video_info);
        mCacheVideoCancel = (ImageView) findViewById(R.id.cache_video_cancel);
        mCollectVideo = (FrameLayout) findViewById(R.id.collect_video);
        mSumUpCollect = (ImageView) findViewById(R.id.sum_up_collect);
//        mSumUpVideo = (SumUpJCVideoPlayerStandard) findViewById(R.id.sum_up_video);
        mSumUpVideo = (SZSurfaceView) findViewById(R.id.sum_up_video);
        mSumUpVideo.setActivity(this);
        mPayCourse = (TextView) findViewById(R.id.pay_course);
        mSumUpTitle = (TextView) findViewById(R.id.sum_up_title);
        mPlayNum = (TextView) findViewById(R.id.play_num);
        mSumUpFitpersonContent = (TextView) findViewById(R.id.sum_up_fitperson_content);
        mSumUpTeacherAvatar = (RoundedImageView) findViewById(R.id.sum_up_teacher_avatar);
        mSumUpTeacherName = (TextView) findViewById(R.id.sum_up_teacher_name);
        mSumUpTeacherInfo = (TextView) findViewById(R.id.sum_up_teacher_info);
        mSumUpDetailWebview = (WebView) findViewById(R.id.sum_up_detail_webview);
        mOpenVip = (TextView) findViewById(R.id.open_vip);
        mCacheVideoList = (RecyclerView) findViewById(R.id.cache_video_list);
        mSumUpBottomTitle = (LinearLayout) findViewById(R.id.sum_up_bottom_title);
        mCheckCacheVideo = (RelativeLayout) findViewById(R.id.check_cache_video);
        mCacheVideoFreeSpace = (TextView) findViewById(R.id.cache_video_free_space);
        mTvHaveDownloadNums = (TextView) findViewById(R.id.tv_have_download_nums);
        mCacheVideoAll = (TextView) findViewById(R.id.cache_video_all);
        mLearnAllCourseImg = (ImageView) findViewById(R.id.learn_all_course_img);
        mLearnAllCourseName = (TextView) findViewById(R.id.learn_all_course_name);
        mLearnAllCoursePrice = (TextView) findViewById(R.id.learn_all_course_price);
        mLearnAll = (RelativeLayout) findViewById(R.id.learn_all);
        mLearnAllCourseTitle = (TextView) findViewById(R.id.learn_all_course_title);
        mMc = (View) findViewById(R.id.mc);

        TextPaint tp = mLearnAllCourseTitle.getPaint();
        tp.setFakeBoldText(true);

//        mPayDig = new CenterDialog(this, R.layout.dialog_layout, new int[]{R.id.dialog_ll_cancel, R.id.dialog_ll_sure});
//        mPayDig.setOnCenterItemClickListener(this);
    }

    private void iniOther() {
        mCacheVideoFreeSpace.setText("可用存储空间" + FileUtils.getSDAvailableSize(MyApplication.mApplication));
    }

    private void initRecycle() {
        mCacheVideoList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        mSumUpDownLoadAdapter = new SumUpDownLoadAdapter(mDownLoadList,this);
        mCacheVideoList.setAdapter(mSumUpDownLoadAdapter);
    }

    private void initListener() {
        mCacheVideo.setOnClickListener(this);
        mCacheVideoCancel.setOnClickListener(this);
        mCollectVideo.setOnClickListener(this);
        mPayCourse.setOnClickListener(this);
        mSumUpDownLoadAdapter.setOnDownloadClickListener(this);
        mCheckCacheVideo.setOnClickListener(this);
        mCacheVideoAll.setOnClickListener(this);
        mLearnAll.setOnClickListener(this);

        mSumUpVideo.setOnBackClickListener(new SZSurfaceView.OnBackClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }
        });
        mSumUpVideo.setOnProcessClickListener(this);
        mSumUpVideo.setOnShareClickListener(this);
    }

    /**
     * TODO
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cache_video:
                if (mUser_id != null && !"".equals(mUser_id) && mLoginRandStr != null && !"".equals(mLoginRandStr) &&
                        mLoginRandStr.trim().equals(mVideoRandStr.trim())) {
                    if (!"0.00".equals(mCoursePrice)) {
                        if (!"1".equals(mVIPState)) {
                            if (!"1".equals(mPayState)) {
                                Toast.makeText(this, "请先购买该小结", Toast.LENGTH_SHORT).show();
                                showNewManyPayPop();
                                return;
                            }
                        }
                    }
                    startCacheAni();
                } else {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cache_video_cancel:
                startCacheAni();
                break;
            case R.id.collect_video:
                if (StringUtils.isNotEmpty(mUser_id)) {
                    changeCollectState();
                } else {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.pay_course:
                if (mUser_id != null && !"".equals(mUser_id) && mLoginRandStr != null && !"".equals(mLoginRandStr) &&
                        mLoginRandStr.trim().equals(mVideoRandStr.trim())) {
//                    mPayDig.show();
//                    mDigPrice = (TextView) mPayDig.findViewById(R.id.detail_dialog_price);
//                    mDigTitle = (TextView) mPayDig.findViewById(R.id.detail_dialog_title);
//                    mDigWorn = (TextView) mPayDig.findViewById(R.id.detail_dialog_worning);
//                    mDigPrice.setText("¥"+mCoursePrice);
//                    mDigTitle.setText(mCourseTitle);
                    showNewManyPayPop();
                } else {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.open_vip:
                checkOpenVip();
                break;
            case R.id.check_cache_video:
                Intent intent = new Intent(this, DownManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.cache_video_all:
                if (mDownLoadList != null && mDownLoadList.size() > 0) {
                    Toast.makeText(this, "已将" + mDownLoadList.size() + "个视频添加到缓存目录", Toast.LENGTH_SHORT).show();
                    if (mTvHaveDownloadNums.getText() != null) {
                        int num = Integer.parseInt(mTvHaveDownloadNums.getText().toString());
                        mTvHaveDownloadNums.setText(num + mDownLoadList.size() + "");
                    }
                    for (int i = 0; i < mDownLoadList.size(); i++) {
                        downLoadVideo(mDownLoadList.get(i));
                    }
                }
                break;
            case R.id.learn_all:
                intent = new Intent(this, NewVideoInfoActivity.class);
                intent.putExtra("id", mAllCourseId);
                intent.putExtra("type", mAllCourseType);
                startActivity(intent);
                break;
        }
    }

    private void checkOpenVip() {
        if (mUser_id != null && !"".equals(mUser_id) && mLoginRandStr != null && !"".equals(mLoginRandStr) &&
                mLoginRandStr.trim().equals(mVideoRandStr.trim())) {
            Intent intent = new Intent(this, NewEventDetailsActivity.class);
            intent.putExtra("event_id", mVIPId);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginNewActivity.class);
            startActivity(intent);
        }
    }

    private void changeCollectState() {
        if (isCollect) {
            mSumUpCollect.setImageResource(R.drawable.video_collect_nor);
            isCollect = false;
        } else {
            mSumUpCollect.setImageResource(R.drawable.video_collect_press);
            isCollect = true;
        }
        mCollectPresenter.changeCollect(mCourse_id, mUser_id, "1", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_COLLECT", "Success");
                CollectEntity entity = (CollectEntity) value[0];
                if (entity.getErr() == 0) {
                    Toast.makeText(SumUpActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else if (entity.getErr() == -1) {
                    Toast.makeText(SumUpActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SumUpActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    if (isCollect) {
                        mSumUpCollect.setImageResource(R.drawable.video_collect_nor);
                        isCollect = false;
                    } else {
                        mSumUpCollect.setImageResource(R.drawable.video_collect_press);
                        isCollect = true;
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_COLLECT", error);
            }
        });
    }

    public void startCacheAni() {
        Animation ani;
        if (!isShowCache) {
            isShowCache = true;
            mCache.setVisibility(View.VISIBLE);
            ani = AnimationUtils.loadAnimation(MyApplication.mApplication, R.anim.actionsheet_dialog_in);
            ani.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mVideoInfo.setVisibility(View.GONE);
                    mSumUpBottomTitle.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            isShowCache = false;
            mCache.setVisibility(View.GONE);
            mVideoInfo.setVisibility(View.VISIBLE);
            if (!"0.00".equals(mCoursePrice)) {
                mSumUpBottomTitle.setVisibility(View.VISIBLE);
            }
            ani = AnimationUtils.loadAnimation(MyApplication.mApplication, R.anim.actionsheet_dialog_out);
            ani.setAnimationListener(null);
        }

        mCache.startAnimation(ani);
    }

    /**
     * 进度回调
     *
     */

    @Override
    public boolean processClick() {
        if (StringUtils.isNotEmpty(mUser_id) && StringUtils.isNotEmpty(mLoginRandStr)) {
            if(!"0.00".equals(mCoursePrice)){
                if(!"1".equals(mVIPState)){
                    if(!"1".equals(mPayState)){
                        if(SZSurfaceView.FULL_SCREEN==1){
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        Toast.makeText(this, "请付费后学习完整内容", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        } else {
            if(SZSurfaceView.FULL_SCREEN==1){
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

    }

    @Override
    public void startProcessClick() {

    }

//    public void progress(int currentPosition) {
//        this.positionPro = currentPosition;
//        if (currentPosition >= 180000) {
//            if (mUser_id != null && !"".equals(mUser_id) && mLoginRandStr != null) {
//                if (!"1".equals(mVIPState) && !isShowPayDig) {
//                    if (!"0.00".equals(mCoursePrice)) {
//                        if (!"1".equals(mPayState)) {
//                            JCMediaManager.instance().mediaPlayer.pause();
//                            mSumUpVideo.onStatePause();
//                            showManyPayPop();
//                            isShowPayDig = true;
//                        }
//                    }
//                }
//            } else {
//                JCMediaManager.instance().mediaPlayer.pause();
//                mSumUpVideo.onStatePause();
//                Intent intent = new Intent(this, LoginNewActivity.class);
//                startActivity(intent);
//            }
//        }
//    }

    private void sendVideoProgress(SumUpEntity.DirBean dirBean) {
        int second = Math.round((int) mSumUpVideo.getCurrentTime()  / 1000);
        Map<String, String> map = new HashMap<>();
        map.put("video_id", dirBean.getVideo_id() == null ? "" : dirBean.getVideo_id());
        map.put("user_id", mUser_id == null ? "" : mUser_id);
        map.put("video_file_id", dirBean.getId() == null ? "" : dirBean.getId());
        map.put("player_time", second + "");
        map.put("video_duration", dirBean.getLength() == null ? "" : dirBean.getLength());
        map.put("app", "4");
        map.put("shijian", Api.timeStr);
        map.put("sign", Api.sign);
        mUpdateProgressPresenter.updateProgress(map, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_PROGRESS", "Success");
                UpdateProgressEntity entity = (UpdateProgressEntity) value[0];
                if (entity.getInfo() != null) {
                    if (mLoginRandStr == null || "".equals(mLoginRandStr) ||
                            !mLoginRandStr.trim().equals(entity.getInfo().getRand_str().trim())) {
                        Intent intent = new Intent(SumUpActivity.this, LoginNewActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_PROGRESS", error);
            }
        });
    }


    private void getNetData() {
        mSumUpInfoPresenter.getSumUpInfo(mUser_id, mCourse_id, mType, Constant.IMEI, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                SumUpEntity entity = (SumUpEntity) value[0];
                //获取视频列表，初始化视频控件 小结只播放一个视频
                if (entity.getDir() != null && entity.getDir().size() > 0) {
//                    LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();
//                    int pos = 0;
                    for (int i = 0; i < entity.getDir().size(); i++) {
                        if (entity.getDir().get(i).getUrl() != null && !"".equals(entity.getDir().get(i).getUrl())) {
//                            map.put(pos+"",entity.getDir().get(i).getUrl());
//                            pos++;
                            mPlayBean = entity.getDir().get(i);
                            if (mDownLoadList.size() > 0) {
                                mDownLoadList.clear();
                            }
                            mDownLoadList.add(mPlayBean);
                            if (mPlayBean != null && mPlayBean.getUrl() != null) {
                                if (mSumUpDownLoadAdapter != null) {
                                    mSumUpDownLoadAdapter.notifyDataSetChanged();
                                } else {
                                    mSumUpDownLoadAdapter = new SumUpDownLoadAdapter(mDownLoadList,SumUpActivity.this);
                                    mCacheVideoList.setAdapter(mSumUpDownLoadAdapter);
                                }
                            }
                            mPlayVideoUrl = entity.getDir().get(i).getUrl();
//                            mPlayVideoUrl = "http://video.shaoziketang.com/20171023jiameng.mp4";//https://video.shaoziketang.com/201712261148.mp4?OSSAccessKeyId=LTAITJNMFGO3ZU7e&Expires=1516245443&Signature=SgXE1zqN6Vu88DnQgToNkzM%2BLlA%3D
                            mSumUpVideo.setVideoUrl(mPlayVideoUrl,true);

//                            videosizes = 1;
//                            mSumUpVideo.setUp(mPlayVideoUrl,
//                                    JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                            break;
                        }
                    }
                }
                if (entity.getInfo() != null && entity.getInfo().size() > 0 && entity.getInfo().get(0) != null) {
                    mInfoBean = entity.getInfo().get(0);
                    setDataToUI(mInfoBean);
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_SUMUP", error);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
//        JCVideoPlayer.releaseAllVideos();
//        if (mProgressTimer != null) {
//            mProgressTimer.cancel();
//        }
    }

    //TODO
    private void setDataToUI(SumUpEntity.InfoBean infoBean) {
        mPerson = infoBean.getPerson();
        mSumUpVideo.setVideoImg(infoBean.getImg());
//        Glide.with(this)
//                .load(infoBean.getImg())
//                .centerCrop()
//                .into(mSumUpVideo.thumbImageView);
//        Log.e("DH_URl", mPlayBean.getUrl());
//        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        mIs_company = infoBean.getIs_company();
        mIs_buy_company = infoBean.getIs_buy_company();
        //开启定时器每60秒向服务器传送播放进度
        mProgressTimer = new Timer();
        mProgressTimer.schedule(new TimerTask() {
            public void run() {
                if (StringUtils.isNotEmpty(mPlayBean.getUrl())) {
                    sendVideoProgress(mPlayBean);
                }
            }
        }, 0, 60000);

        if ("".equals(infoBean.getCollect_status())) {
            isCollect = false;
            mSumUpCollect.setImageResource(R.drawable.video_collect_nor);
        } else {
            isCollect = true;
            mSumUpCollect.setImageResource(R.drawable.video_collect_press);
        }
        mYunumber = infoBean.getYunumber();
        mVideoRandStr = infoBean.getRand_str();
        mCoursePrice = infoBean.getNew_price();
        mCourseTitle = infoBean.getTitle();
        mPayState = infoBean.getPay_status();
        mVIPId = infoBean.getVip_id() + "";
        mVIPState = infoBean.getIs_vip() + "";
        if ("0.00".equals(mCoursePrice)) {
            mPayCourse.setOnClickListener(null);
            if ("1".equals(mVIPState)) {
                mPayCourse.setText("会员免费学习");
                mOpenVip.setVisibility(View.GONE);
                mOpenVip.setOnClickListener(null);
            } else {
                mPayCourse.setText("免费学习");
                mOpenVip.setVisibility(View.VISIBLE);
                mOpenVip.setText("开通会员");
                mOpenVip.setOnClickListener(this);
            }
            mSumUpBottomTitle.setVisibility(View.GONE);
        } else {
            mSumUpBottomTitle.setVisibility(View.VISIBLE);
            if ("1".equals(mVIPState)) {
                mOpenVip.setVisibility(View.GONE);
                mOpenVip.setText("会员免费学习");
                mPayCourse.setText("为好友购买");
                mOpenVip.setOnClickListener(null);
            } else {
                mOpenVip.setVisibility(View.VISIBLE);
                if ("1".equals(mPayState)) {
                    mPayCourse.setText("为好友购买");
                } else {
                    mPayCourse.setText("购买 ¥:" + mCoursePrice);
                }
                mOpenVip.setText("开通会员");
                mOpenVip.setOnClickListener(this);
                mPayCourse.setOnClickListener(this);
            }

        }

        mSumUpTitle.setText(infoBean.getTitle());
        mPlayNum.setText("播放" + infoBean.getLearn() + "次");
        mSumUpFitpersonContent.setText(infoBean.getStudent());
        mSumUpTeacherName.setText(infoBean.getName());
        mSumUpTeacherInfo.setText(infoBean.getDuan());
        if (infoBean.getHead() != null && !"".equals(infoBean.getHead())) {
            Glide.with(MyApplication.mApplication).load(infoBean.getHead()).centerCrop().into(mSumUpTeacherAvatar);
        }
        //获取缓存数
        DataKeeper dataKeeper = new DataKeeper(this);
        int fatherId = Integer.parseInt(infoBean.getId());
        ArrayList<SQLDownLoadInfo> downLoadingList = dataKeeper.getUserDownLoadInfoByFatherId(fatherId);
        mTvHaveDownloadNums.setText(downLoadingList.size() + "");
        if (infoBean.getItem() != null) {
            mLearnAll.setVisibility(View.VISIBLE);
            SumUpEntity.InfoBean.ItemBean item = infoBean.getItem();
            mAllCourseId = item.getId();
            mAllCourseType = item.getType();
            Glide.with(MyApplication.mApplication).load(item.getImg()).centerCrop().placeholder(R.drawable.ivnull)
                    .error(R.drawable.ivnull).into(mLearnAllCourseImg);
            mLearnAllCourseName.setText(item.getTitle());
            if ("1".equals(mVIPState)) {
                mLearnAllCoursePrice.setText("会员免费观看");
            } else {
                if ("0.00".equals(item.getNew_price())) {
                    mLearnAllCoursePrice.setText("免费观看");
                } else {
                    mLearnAllCoursePrice.setText("¥:" + item.getNew_price());
                }
            }
        } else {
            mLearnAll.setVisibility(View.GONE);
        }

        setWebView();
    }

    private void setWebView() {
        String webUrl = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=1&id=" + mCourse_id;
        mWebSetting = mSumUpDetailWebview.getSettings();
        //设置支持javaScript
        mWebSetting.setJavaScriptEnabled(true);
        //增加缩放按钮
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setSupportZoom(true);
        //设置文字大小
        mWebSetting.setTextSize(WebSettings.TextSize.SMALLER);
        mWebSetting.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        mSumUpDetailWebview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //pb_loading.setVisibility(View.GONE);
            }
        });

        mSumUpDetailWebview.loadUrl(webUrl);
    }

    private void startPay() {
        mOrderPayPresenter.orderPay(mPayMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                PayEntity entity = (PayEntity) value[0];
                temporaryEventNo = entity.getInfo().getOrder_no();
                Message msg = mHandler.obtainMessage();
                msg.what = mPayWay;
                msg.obj = entity;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_PAY_ALIPAY", error);
            }
        });
    }

    /**
     * 下载管理
     *
     * @param pos
     * @param bean
     */
    @Override
    public void onDownloadClick(int pos, SumUpEntity.DirBean bean) {
        if (mTvHaveDownloadNums.getText() != null) {
            int num = Integer.parseInt(mTvHaveDownloadNums.getText().toString());
            mTvHaveDownloadNums.setText(num + 1 + "");
        }
        downLoadVideo(bean);
    }

    private void downLoadVideo(SumUpEntity.DirBean bean) {
        String video_id = bean.getVideo_id();
        int fatherId = Integer.parseInt(video_id);
        String fatharTitle = mInfoBean.getTitle();
        String fatherImg = mInfoBean.getImg();
        String id = bean.getId();
        int childId = Integer.parseInt(id);
        String chileTitle = bean.getVideo_title();
        String chileUrl = "http://u.wimg.cc/" + bean.getDownload_url();
        manager.addTask(fatherId, fatharTitle, fatherImg, childId, chileUrl, chileTitle);
//        mDownLoadPresenter.downLoadVideo(id, "0", mUser_id, new OnResultListener() {
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
    }

    private Dialog shareDialog;

    public void share() {
        if (StringUtils.isNotEmpty(mUser_id)) {
            shareDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
            //填充对话框的布局
            View inflate = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
            //初始化控件
            RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
            RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
            RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
            RelativeLayout shareRlayoutCompany = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_company);

            if ("1".equals(mIs_company)) {
                shareRlayoutCompany.setVisibility(View.VISIBLE);//TODO
            } else {
                shareRlayoutCompany.setVisibility(View.GONE);//TODO
            }
            share_rlayout_py.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareDialog.dismiss();
                    //msgApi
                    isShareInform = true;
                    isPayCourseOrAction = 2;
                    temporaryEventNo = mCourse_id;
                    ShareUtil.weiChat(Constants.wx_api, 7, SumUpActivity.this,
                            mInfoBean.getShare_url() + "?helper=" + mUser_id,
                            mInfoBean.getTitle(),
                            mInfoBean.getSub_title());
                }
            });

            share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareDialog.dismiss();

                    isShareInform = true;
                    isPayCourseOrAction = 2;
                    temporaryEventNo = mCourse_id;
                    ShareUtil.weiChat(Constants.wx_api, 8, SumUpActivity.this,
                            mInfoBean.getShare_url() + "?helper=" + mUser_id,
                            mInfoBean.getTitle(),
                            mInfoBean.getSub_title());
                }
            });

            shareRlayoutCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("0.00".equals(mCoursePrice)) {
                        if ("1".equals(mIs_buy_company)) {
                            if(mPerson!=null&&!"0".equals(mPerson)&&!"".equals(mPerson)){
                                Intent intent = new Intent(SumUpActivity.this, CompanyMessageActivity.class);
                                intent.putExtra("id", mCourse_id);
                                intent.putExtra("type", "1");
                                intent.putExtra("courseOrAction", "2");
                                startActivity(intent);
                            }else{
                                Toast.makeText(mContext, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if(mPerson!=null&&!"0".equals(mPerson)&&!"".equals(mPerson)){
                                Intent intent = new Intent(SumUpActivity.this, CompanyMessageActivity.class);
                                intent.putExtra("id", mCourse_id);
                                intent.putExtra("type", "2");
                                intent.putExtra("courseOrAction", "2");
                                startActivity(intent);
                            }else{
                                Toast.makeText(mContext, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if ("1".equals(mIs_buy_company)) {
                            if(mPerson!=null&&!"0".equals(mPerson)&&!"".equals(mPerson)){
                                Intent intent = new Intent(SumUpActivity.this, CompanyMessageActivity.class);
                                intent.putExtra("id", mCourse_id);
                                intent.putExtra("type", "1");
                                intent.putExtra("courseOrAction", "2");
                                startActivity(intent);
                            }else{
                                Toast.makeText(mContext, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            double num = 0;
                            if (mYunumber != null && !"".equals(mYunumber)) {
                                Log.e("DH_mYunumber", mYunumber);
                                try {
                                    num = Double.parseDouble(mYunumber);
                                } catch (Exception e) {
                                    num = 0;
                                }
                            }
                            if (num > 0) {//TODO 多人购买剩余人数
                                if(mPerson!=null&&!"0".equals(mPerson)&&!"".equals(mPerson)){
                                    Intent intent = new Intent(SumUpActivity.this, CompanyMessageActivity.class);
                                    intent.putExtra("id", mCourse_id);
                                    intent.putExtra("type", "2");
                                    intent.putExtra("courseOrAction", "2");
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(mContext, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(SumUpActivity.this, "请先“为好友购买”后再分享至企业号", Toast.LENGTH_SHORT).show();
                                showNewManyPayPop();
                            }

                        }
                    }
                    shareDialog.dismiss();
                }
            });
            //将布局设置给Dialog
            shareDialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = shareDialog.getWindow();
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
            shareDialog.show();//显示对话框
        } else {
            Intent intent = new Intent(this, LoginNewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onShareClick() {
        share();
    }

    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    isShowPayDig = false;
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    //TODO  要做读取下一个处理
                    if (videoindex < videosizes) {
                        videoindex++;
                    }
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    break;
                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    isShowPayDig = false;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return;
//        }
        if (FULL_SCREEN==1) {
            FULL_SCREEN = 0;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }

    private PopPay mPopPay;

    public void showPayPop() {
        mPopPay = new PopPay(this, mCourseTitle, mCoursePrice);
        mPopPay.showAtLocation(findViewById(R.id.sum_up_activity), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        mPopPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mPopPay.setOnPayClickListener(new PopPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay) {
                mPayWay = payWay;
                if (payWay == WAY_WEICHAT) {
                    Toast.makeText(SumUpActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                } else if (payWay == WAY_ALIPAY) {
                    Toast.makeText(SumUpActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                }
                startPay();
            }

            @Override
            public void onSelectCouponClick() {

            }
        });
    }

    private PopManyPay mPopManyPay;
    private Map<String, String> mPayMap = new HashMap<>();

    public void showManyPayPop() {
        mPopManyPay = new PopManyPay(this, mCourseTitle, Double.parseDouble(mCoursePrice), "1".equals(mVIPState) || "1".equals(mPayState) ? 1 : 0);
        mPopManyPay.showAtLocation(findViewById(R.id.sum_up_activity), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.4f;
//        getWindow().setAttributes(params);
        mMc.setVisibility(View.VISIBLE);
        mPopManyPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
                mMc.setVisibility(View.GONE);
            }
        });
        mPopManyPay.setOnPayClickListener(new PopManyPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay, int payNum, int extraNum, int payForMeState, int payType) {
                Log.e("DH_PAY_PARAM", "extraNum:" + extraNum + (" payType:" + payType + " " + (payType == 0 ? "单人" : "多人  payForMeState:" + payForMeState + " " + (payForMeState == 0 ? "不为自己购买" : "为自己购买"))));
                isPayCourseOrAction = 0;
                isPayCourseType = "4";
                mPayMap.put("goodsType", "3");
                mPayMap.put("id", mCourse_id);
                mPayMap.put("user_id", mUser_id);
                mPayMap.put("coupon", "");
                mPayMap.put("pay_type", "4");
                mPayMap.put("buy_self", payForMeState + "");
                mPayMap.put("num", extraNum + "");
                mPayMap.put("multi_buy", payType + "");
                mPayWay = payWay;
                if (payWay == WAY_WEICHAT) {
                    Toast.makeText(SumUpActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                } else if (payWay == WAY_ALIPAY) {
                    Toast.makeText(SumUpActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                }
                startPay();
                if (mPopManyPay != null && mPopManyPay.isShowing()) {
                    mPopManyPay.dismiss();
                }
            }
        });
    }

    private PopNewManyPay mPopNewManyPay;
    public void showNewManyPayPop(){
        if("1".equals(mVIPState)|| "1".equals(mPayState) ? true : false){
            intentManyPay();
        }else{
            mPopNewManyPay = new PopNewManyPay(this, mCourseTitle, Double.parseDouble(mCoursePrice),0);
            mPopNewManyPay.showAtLocation(findViewById(R.id.sum_up_activity), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            mMc.setVisibility(View.VISIBLE);
            mPopNewManyPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
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
                    isPayCourseType = "4";
                    mPayMap.put("goodsType", "3");
                    mPayMap.put("id", mCourse_id);
                    mPayMap.put("user_id", mUser_id);
                    mPayMap.put("coupon", "");
                    mPayMap.put("pay_type", "4");
                    mPayMap.put("buy_self","1");
                    mPayMap.put("num", "0");
                    mPayMap.put("multi_buy","0");
                    mPayWay = payWay;
                    if (payWay == WAY_WEICHAT) {
                        Toast.makeText(SumUpActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                    } else if (payWay == WAY_ALIPAY) {
                        Toast.makeText(SumUpActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                    }
                    startPay();
                    if (mPopNewManyPay != null && mPopNewManyPay.isShowing()) {
                        mPopNewManyPay.dismiss();
                    }
                }

                @Override
                public void onSelectCouponClick() {

                }
            });
        }
    }

    private void intentManyPay() {
        Intent intent = new Intent(this, SelectApplyActivity.class);
        intent.putExtra("id",mCourse_id);
        intent.putExtra("title",mCourseTitle);
        intent.putExtra("original_price",mCoursePrice);
        intent.putExtra("vip",Integer.parseInt(mVIPState));
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
            FULL_SCREEN = 0;
            //显示状态栏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mSumUpVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            //设置view的布局，宽高之类
            ViewGroup.LayoutParams surfaceViewLayoutParams = mSumUpVideo.getLayoutParams();
            surfaceViewLayoutParams.height = (int) (getWight() * 9.0f / 16);
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
            FULL_SCREEN = 1;
            //隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mSumUpVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
            //设置view的布局，宽高
            ViewGroup.LayoutParams surfaceViewLayoutParams = mSumUpVideo.getLayoutParams();
            surfaceViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

        }
    }

    public int getWight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSumUpVideo.savePlayerState();
        if (mPopPay != null && mPopPay.isShowing()) {
            mPopPay.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressTimer != null) {
            mProgressTimer.cancel();
            mProgressTimer = null;
        }
        mSumUpVideo.stop();
        mSumUpVideo.destroy();
    }
}
