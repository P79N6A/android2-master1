package com.wh.wang.scroopclassproject.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.CommentAdapter;
import com.wh.wang.scroopclassproject.adapter.VideoDownAdapter;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.CommentResultBena;
import com.wh.wang.scroopclassproject.bean.DownVideoBean;
import com.wh.wang.scroopclassproject.bean.ReplyBean;
import com.wh.wang.scroopclassproject.bean.ReplyResultBean;
import com.wh.wang.scroopclassproject.bean.VedioDetailInfo;
import com.wh.wang.scroopclassproject.bean.VideoLoginBean;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyMessageActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.ResultActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.WindowUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopManyPay;
import com.wh.wang.scroopclassproject.newproject.view.PopPay;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.FileUtils;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.NoScrollListView;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.views.CenterDialog;
import com.wh.wang.scroopclassproject.views.MultiStateView;
import com.wh.wang.scroopclassproject.views.MyJCVideoPlayerStandard;
import com.wh.wang.scroopclassproject.wxapi.MD5;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;
import com.wh.wang.scroopclassproject.wxapi.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseType;
import static com.wh.wang.scroopclassproject.newproject.Constant.isShareInform;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity.manager;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

//import static com.wh.wang.scroopclassproject.activity.MainActivity.manager;


/**
 * 视频详情
 */
public class VideoInfosOldActivity extends AppCompatActivity implements CenterDialog.OnCenterItemClickListener, View.OnClickListener {

    private MyJCVideoPlayerStandard myJCVideoPlayerStandard;
    private Intent intent;
    private Context mContext;
    private RelativeLayout rl_detail_detail;
    private TextView video_detail_tv_detail;
    private RelativeLayout rl_detail_cap;
    private TextView video_detail_tv_cap;
    private RelativeLayout detail_content_detail;
    private TextView tv_content_detail_title;
    private TextView tv_content_detail_good;
    private TextView tv_content_detail_look;
    private TextView detail_content_fitperson_content;
    private ImageView detail_conten_iv_teache_logo;
    private TextView detail_conten_tv_teache_name;
    private TextView detail_conten_tv_teache_des;
    private WebView video_webview_detail;
    private RelativeLayout detail_content_cap;
    private ListView detail_content_cap_list;
    private String url;
    private WebSettings webSettings;
    private List<VedioDetailInfo.InfoBean> infobeanLists;
    private List<VedioDetailInfo.DirBean> dirbeanLists;
    private List<VedioDetailInfo.DirBean> downbeanLists;
    private List<VedioDetailInfo.RelBean> relbeansLists;
    private List<VedioDetailInfo.CommentBean> commentLists;
    private List<ReplyBean> replyLists;
    private Context context;
    private int index;
    private int positionPro = 0;
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    //    private CenterDialog centerDialog;
    private boolean isFirstDialog = false;
    private boolean isNowPay = false;
    Timer timer0ne = new Timer();
    Timer timerTwo = new Timer();


    //微信支付
//    final
    Map<String, String> resultunifiedorder;
    StringBuffer sb;

    //微信分享
    private IWXAPI api;

    //支付宝支付

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    //视频遮掩层
    private Button video_detial_zhao;
    private RelativeLayout video_detail_ll_buy;
    private TextView video_detail_tv_buy;
    private RelativeLayout video_detail_ll_vip;
    private TextView video_detail_tv_vip;
    private String couseId;
    private String user_id;
    private double payPrice = 0.000; //视频价格 根据VIP和非VIP
    private int payStatus = 0; //视频价格 是否支付
    private String info_collect_status;
    private int info_is_vip;
    private String info_new_price;
    private String info_pay_status;
    private String info_rand_str;
    private String info_vip_id;
    private String info_title; //课程标题
    private String dir_video_file_title;
    private String dir_veido_url;
    private String login_rand_str;
    public static int videosizes = 0;
    public static int videoindex = 0;
    private MultiStateView mStateView;
    private String out_trade_no;
    private String outTradeNo;
    private View detail_view_one;
    private View detail_view_two;
    private View detail_view_three;
    private TextView video_detail_tv_talk;
    private RelativeLayout rl_detail_talk;
    private ScrollView detail_content_detail_sv;
    private LinearLayout detail_content_comment;
    private RelativeLayout rlayout_comment_share;
    private NoScrollListView commentList;
    private LinearLayout commentLinear;
    private EditText commentEdit;
    private Button commentButton;
    private LinearLayout llayout_video_detail_bottom_one;
    private CommentAdapter commentadapter;
    //倒计时是否结束
    private boolean mIsAllow = false;

    private String commentPos; //评论和回复的索引
    private int fatherpos;  //父类的position
    private int childpos;  //父类下的position

    private int[] imgs;                    //图片资源ID数组
    private boolean isReply;            //是否是回复
    private String comment = "";        //记录对话框中的内容
    private String course_info_id;
    private RelativeLayout video_detail_titil_collect;
    private TextView video_tv_collect;
    private RelativeLayout video_detail_titil_download;
    private RelativeLayout video_rlayout_download;
    private ImageView video_iv_download_title;
    private RelativeLayout detail_content_download;
    private ListView detail_content_download_list;
    private LinearLayout video_llayout_title;
    private RelativeLayout detail_content_download_des;
    private RelativeLayout rlayout_have_download;
    private TextView tv_download_des_size;
    private TextView tv_have_download_nums;
    private TextView tv_all_download;
    private final String FILEPATH = FileHelper.getFileDefaultPath();
    private ImageView video_share_iv;
    private Dialog shareDialog;
    private String type;
    private TextView mVideoTvDownload;

    //倒计时布局
    private RelativeLayout mCountDown;
    private ImageView mBack;
    private TextView mPublicTimeTV;
    private TextView mResidueTimeDay;
    private TextView mResidueTimeHour;
    private TextView mResidueTimeMinute;
    private TextView mResidueTimeSecond;

    private long mPublicTime = 0; //开课时间
    private long mNowTime = 0; //服务器时间
    private long mResidueTime = 0;// 剩余时间
    private CountDownTimer mCountDownTimer;
    private String mIs_buy_company = "0";
    private String mIs_company = "0";
    private String mTotal;
    private String mYunumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_detail_alter);
        mContext = this;
        WindowUtils.getInstance().fullScreen(this);
        MobclickAgent.onEvent(mContext, "coursedes");
        //微信支付
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
        sb = new StringBuffer();
        Constants.wx_api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
        intView();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化布局
     */
    private void intView() {

        mStateView = (MultiStateView) findViewById(R.id.video_detail_stateview);
        myJCVideoPlayerStandard = (MyJCVideoPlayerStandard) findViewById(R.id.jc_video_detail);
//        myJCVideoPlayerStandard.activity = VideoInfosOldActivity.this;

        //-------------------------------------详情标题-------------------------------------------
        rl_detail_detail = (RelativeLayout) findViewById(R.id.rl_detail_detail); //"详情" 标题栏
        video_detail_tv_detail = (TextView) findViewById(R.id.video_detail_tv_detail); //"详情"
        video_detail_tv_detail.setTextColor(this.getResources().getColor(R.color.vedio_cap_press));
        detail_view_one = findViewById(R.id.detail_view_one);
        detail_view_one.setVisibility(View.VISIBLE);
        //-----------------------------------------------------------------------------------------
        //video_detail_tv_collect = (TextView) findViewById(R.id.video_detail_tv_collect);//收藏 字
        video_detail_ll_buy = (RelativeLayout) findViewById(R.id.video_detail_ll_buy); //购买课程
        video_detail_tv_buy = (TextView) findViewById(R.id.video_detail_tv_buy); //购买课程
        video_detail_tv_buy = (TextView) findViewById(R.id.video_detail_tv_buy); //购买课程

        video_detail_ll_vip = (RelativeLayout) findViewById(R.id.video_detail_ll_vip); //开通VIP
        video_detail_tv_vip = (TextView) findViewById(R.id.video_detail_tv_vip); //购买课程

        //-------------------------------------目录标题-------------------------------------------
        rl_detail_cap = (RelativeLayout) findViewById(R.id.rl_detail_cap); //"目录" 标题栏
        video_detail_tv_cap = (TextView) findViewById(R.id.video_detail_tv_cap); //"目录"
        video_detail_tv_cap.setTextColor(this.getResources().getColor(R.color.vedio_cap));
        detail_view_two = findViewById(R.id.detail_view_two);
        detail_view_two.setVisibility(View.INVISIBLE);


        //-------------------------------------评论标题-------------------------------------------
        rl_detail_talk = (RelativeLayout) findViewById(R.id.rl_detail_talk);//评论标题栏
        video_detail_tv_talk = (TextView) findViewById(R.id.video_detail_tv_talk);//评论
        video_detail_tv_talk.setTextColor(this.getResources().getColor(R.color.vedio_cap));
        detail_view_three = findViewById(R.id.detail_view_three);
        detail_view_three.setVisibility(View.INVISIBLE);


        //-------------------------------------课程详情-----------------------------------------
        detail_content_detail_sv = (ScrollView) findViewById(R.id.detail_content_detail_sv);
        detail_content_detail = (RelativeLayout) findViewById(R.id.detail_content_detail);  //"详情" 内容
        tv_content_detail_title = (TextView) findViewById(R.id.tv_content_detail_title);  //详情 标题
        tv_content_detail_good = (TextView) findViewById(R.id.tv_content_detail_good);  //课程好评度
        tv_content_detail_look = (TextView) findViewById(R.id.tv_content_detail_look);  //课程浏览人数
        //tv_content_detail_old = (TextView) findViewById(tv_content_detail_old);   //"详情"以前的价格
        //tv_content_detail_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        detail_content_fitperson_content = (TextView) findViewById(R.id.detail_content_fitperson_content);//课程适合人群
        detail_conten_iv_teache_logo = (ImageView) findViewById(R.id.detail_conten_iv_teache_logo);//课程讲师的头像
        detail_conten_tv_teache_name = (TextView) findViewById(R.id.detail_conten_tv_teache_name);//课程讲师的名字
        detail_conten_tv_teache_des = (TextView) findViewById(R.id.detail_conten_tv_teache_des);//课程讲师的描述
        video_webview_detail = (WebView) findViewById(R.id.video_webview_detail);  //"详情"的H5

        video_detail_titil_collect = (RelativeLayout) findViewById(R.id.video_detail_titil_collect);
        video_tv_collect = (TextView) findViewById(R.id.video_tv_collect);
        //分享
        video_share_iv = (ImageView) findViewById(R.id.video_share_iv);

        video_detail_titil_download = (RelativeLayout) findViewById(R.id.video_detail_titil_download);

        video_detial_zhao = (Button) findViewById(R.id.video_detial_zhao); //视频屏幕遮掩层

        detail_content_cap = (RelativeLayout) findViewById(R.id.detail_content_cap);   //"目录" 内容
        detail_content_cap.setVisibility(View.GONE);
        detail_content_cap_list = (ListView) findViewById(R.id.detail_content_cap_list);  //"目录" 章节
        detail_content_cap_list.setSelection(0);

        //-------------------------------------评论详情-----------------------------------------
        detail_content_comment = (LinearLayout) findViewById(R.id.detail_content_comment); //评论父类
        rlayout_comment_share = (RelativeLayout) findViewById(R.id.rlayout_comment_share); //分享点击框
        commentList = (NoScrollListView) findViewById(R.id.commentList);//分享列表
        commentLinear = (LinearLayout) findViewById(R.id.commentLinear);//输入框父类
        commentEdit = (EditText) findViewById(R.id.commentEdit);//输入框
        commentButton = (Button) findViewById(R.id.commentButton);//发送分享

        llayout_video_detail_bottom_one = (LinearLayout) findViewById(R.id.llayout_video_detail_bottom_one);

        //-------------------------------------下载详情-----------------------------------------
        video_llayout_title = (LinearLayout) findViewById(R.id.video_llayout_title);
        video_rlayout_download = (RelativeLayout) findViewById(R.id.video_rlayout_download); //下载目录
        video_iv_download_title = (ImageView) findViewById(R.id.video_iv_download_title); //取消显示下载目录
        detail_content_download = (RelativeLayout) findViewById(R.id.detail_content_download); //下载内容的父类
        detail_content_download_list = (ListView) findViewById(R.id.detail_content_download_list); //下载的listview

        detail_content_download_des = (RelativeLayout) findViewById(R.id.detail_content_download_des);
        tv_all_download = (TextView) findViewById(R.id.tv_all_download);

        rlayout_have_download = (RelativeLayout) findViewById(R.id.rlayout_have_download);
        tv_download_des_size = (TextView) findViewById(R.id.tv_download_des_size);
        tv_download_des_size.setText("可用存储空间" + FileUtils.getSDAvailableSize(VideoInfosOldActivity.this));

        tv_have_download_nums = (TextView) findViewById(R.id.tv_have_download_nums);

        mVideoTvDownload = (TextView) findViewById(R.id.video_tv_download);

        //倒计时
        mCountDown = (RelativeLayout) findViewById(R.id.count_down);
        mBack = (ImageView) findViewById(R.id.count_down_back);
        mPublicTimeTV = (TextView) findViewById(R.id.public_time);
        mResidueTimeDay = (TextView) findViewById(R.id.residue_time_day);
        mResidueTimeHour = (TextView) findViewById(R.id.residue_time_hour);
        mResidueTimeMinute = (TextView) findViewById(R.id.residue_time_minute);
        mResidueTimeSecond = (TextView) findViewById(R.id.residue_time_second);

        mBack.setOnClickListener(this);
//        centerDialog = new CenterDialog(VideoInfosActivity.this, R.layout.dialog_layout, new int[]{R.id.dialog_ll_cancel, R.id.dialog_ll_sure});
//        centerDialog.setOnCenterItemClickListener(VideoInfosActivity.this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        intent = getIntent();
//        index = intent.getIntExtra("index", 0);
//
//        Log.e("whwh", "index==" + index);
//        if (index == 1) {
//            JXKBean.ElaborateCourseBean.CourseDetailBean jxDetailBean = (JXKBean.ElaborateCourseBean.CourseDetailBean) getIntent().getSerializableExtra("jxBean");
//            couseId = jxDetailBean.getId();
//        } else if (index == 11) {
//            SuperiorBean.ThreecourseBean zxbean = (SuperiorBean.ThreecourseBean) getIntent().getSerializableExtra("zxbean");
//            couseId = zxbean.getId();
//            //课程详情的标题和价格
//        } else if (index == 2) {
//            XTKBean.SystemCourseBean.CourseDetailBean xtDetailBean = (XTKBean.SystemCourseBean.CourseDetailBean) getIntent().getSerializableExtra("xtBean");
//            couseId = xtDetailBean.getId();
//            //课程详情的标题和价格
//        } else if (index == 22) {
//            SuperiorBean.FreeCourseBean mfbean = (SuperiorBean.FreeCourseBean) getIntent().getSerializableExtra("mfbean");
//            couseId = mfbean.getId();
//            //课程详情的标题和价格
//        } else if (index == 23) {
//            SuperiorBean.TypicalCourseBean jdbean = (SuperiorBean.TypicalCourseBean) getIntent().getSerializableExtra("jdbean");
//            couseId = jdbean.getId();
//            //课程详情的标题和价格
//        } else if (index == 24) {
//            SuperiorBean.ReadVideoBean zsbean = (SuperiorBean.ReadVideoBean) getIntent().getSerializableExtra("zsbean");
//            couseId = zsbean.getId();
//            //课程详情的标题和价格
//        } else if (index == 3) {
//            MineInfo.CollectBean collectBean = (MineInfo.CollectBean) getIntent().getSerializableExtra("collectBean");
//            couseId = collectBean.getId();
//            //课程详情的标题和价格
//        } else if (index == 4) {
//            MineInfo.OrderBean orderBean = (MineInfo.OrderBean) getIntent().getSerializableExtra("orderBean");
//            couseId = orderBean.getId();
//            //课程详情的标题和价格
//        } else if (index == 5) {
//            MineInfo.StudyBean studyBean = (MineInfo.StudyBean) getIntent().getSerializableExtra("studyBean");
//            couseId = studyBean.getId();
//            //课程详情的标题和价格
//        } else if (index == 6) {
//            MineInfo.StudyBean finishBean = (MineInfo.StudyBean) getIntent().getSerializableExtra("finishBean");
//            couseId = finishBean.getId();
//            //课程详情的标题和价格
//        } else if (index == 7) {
//            JXKBean.ScrollBean bannerBean = (JXKBean.ScrollBean) getIntent().getSerializableExtra("bannerBean");
//            couseId = bannerBean.getProduct_id();
//            //课程详情的标题和价格
//        } else if (index == 8) {
//            MFKBean.FreeCourseBean mfBean = (MFKBean.FreeCourseBean) getIntent().getSerializableExtra("mfBean");
//            couseId = mfBean.getId();
//        } else if (index == 9) {
////            SearchInfo.VideoBean videoBean = (SearchInfo.VideoBean) getIntent().getSerializableExtra("videoBean");
//            couseId = getIntent().getStringExtra("id");
//        } else if (index == 10) {
//            MineThirdBean.ListBean listBean = (MineThirdBean.ListBean) getIntent().getSerializableExtra("listBean");
//            couseId = listBean.getItem_id();
//        } else if (index == 12) {
//            CourseBean.InfoBean.ScrollBean scBean = (CourseBean.InfoBean.ScrollBean) getIntent().getSerializableExtra("scBean");
//            couseId = scBean.getCourseId();
//        } else if (index == 13) {
//            CourseBean.InfoBean.CourseListBean kcBean = (CourseBean.InfoBean.CourseListBean) getIntent().getSerializableExtra("kcBean");
//            couseId = kcBean.getId();
//        } else if (index == 99) {
//            couseId = intent.getStringExtra("web_id");
//        } else {
//            couseId = intent.getStringExtra("courseDetailId");
//        }
        couseId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type") == null ? "1" : getIntent().getStringExtra("type");
        login_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
        setWebviewData(couseId);  //设置H5页面
    }

    /**
     * 根据id 获取视频网址
     */
    private void getCourseDetailDataFromNet(String user_id, String id) {

        mStateView.setViewState(MultiStateView.ViewState.LOADING);
        //联网
        //视频内容
        RequestParams params = new RequestParams(Constants.VideoDetailUrl);
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("id", id);
        params.addBodyParameter("type", type);
        params.addBodyParameter("idfa", Constant.IMEI);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //主线程
                processVedioDetailData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mStateView.setViewState(MultiStateView.ViewState.ERROR);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }

    /**
     * 视频详情数据解析
     */
    private void processVedioDetailData(String result) {
        infobeanLists = parseJsonInfo(result);
        dirbeanLists = parseJsonDir(result);
        downbeanLists = parseJsonDown(result);
        relbeansLists = parseJsonRel(result);
        commentLists = parseJsonComment(result);
        mStateView.setViewState(MultiStateView.ViewState.CONTENT);
        setVedioDetailData();
        initListener();
    }

    /**
     * 设置视频详情数据
     */
    private void setVedioDetailData() {
        //TODO 视频播放
        //收藏的状态
        info_collect_status = infobeanLists.get(0).getCollect_status();
        course_info_id = infobeanLists.get(0).getId();
        String info_img_url = infobeanLists.get(0).getImg();
        //是不是VIP
        info_is_vip = infobeanLists.get(0).getIs_vip();
        //最新视频价格
        info_new_price = infobeanLists.get(0).getNew_price();
        //购买的状态
        info_pay_status = infobeanLists.get(0).getPay_status();
        payStatus = Integer.parseInt(info_pay_status);///支付状态
        //设备是不是唯一登录
        info_rand_str = infobeanLists.get(0).getRand_str();
        //注册会员 id
        info_vip_id = infobeanLists.get(0).getVip_id();
        String info_vip_price = infobeanLists.get(0).getVip_price(); //VIP价格
        info_title = infobeanLists.get(0).getTitle();
        tv_content_detail_title.setText(info_title);
        tv_content_detail_good.setText("好评度 " + infobeanLists.get(0).getGood());
        tv_content_detail_look.setText(infobeanLists.get(0).getLearn() + "人浏览");
        //tv_content_detail_old.setText("¥ " + infobeanLists.get(0).getOld_price());
        detail_content_fitperson_content.setText(infobeanLists.get(0).getStudent());
//        ImageLoader.getInstance().displayImage(infobeanLists.get(0).getHead(), detail_conten_iv_teache_logo, MyDisplayOptions.getOptions());
        Glide.with(MyApplication.mApplication).load(infobeanLists.get(0).getHead()).centerCrop().into(detail_conten_iv_teache_logo);
        detail_conten_tv_teache_name.setText(infobeanLists.get(0).getName());
        detail_conten_tv_teache_des.setText(infobeanLists.get(0).getDuan());

        dir_veido_url = dirbeanLists.get(0).getUrl();
        if (StringUtils.isEmpty(dir_veido_url)) {
            dir_veido_url = dirbeanLists.get(1).getUrl();
        }
        dir_video_file_title = dirbeanLists.get(0).getVideo_file_title();


        //--------------------------------------页面价格处理-----------------------------------------
        payPrice = Double.parseDouble(info_new_price);
        if (payPrice == 0.00) {
            video_detail_ll_buy.setClickable(false);
            video_detail_tv_buy.setText("免费学习");
            llayout_video_detail_bottom_one.setVisibility(View.GONE);
        //TODO
        } else {
            if (info_is_vip == 0) {
                if (payStatus == 1) {
                    video_detail_ll_buy.setClickable(true);
                    video_detail_tv_buy.setText("为好友购买");
                } else {
                    video_detail_ll_buy.setClickable(true);
                    video_detail_tv_buy.setText("购买: " + "  ¥" + info_new_price);
                }
            } else {
                video_detail_ll_buy.setClickable(true);
                video_detail_tv_buy.setText("为好友购买");
            }
        }

        //StringUtils.isEmpty(dir_veido_url)
        //--------------------------------------视频处理---------------------------------------------
        LinkedHashMap<String, String> maps = new LinkedHashMap<>();
        for (int i = 0; i < dirbeanLists.size(); i++) {
            if (!dirbeanLists.get(i).getUrl().equals("null") && dirbeanLists.get(i).getUrl().length() > 0) {
                maps.put(i + "", dirbeanLists.get(i).getUrl());
            }
        }

        videosizes = maps.size();
        myJCVideoPlayerStandard.setUp(maps, 0
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, ""); //info_title
        Picasso.with(this)
                .load(info_img_url)
                .into(myJCVideoPlayerStandard.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        Log.e("info_img_url", info_img_url);
        video_detial_zhao.setVisibility(View.GONE);

        if (timer0ne == null) {
            timer0ne = new Timer();
        }
        timer0ne.schedule(new TimerTask() {
            public void run() {
                if (StringUtils.isNotEmpty(dirbeanLists.get(0).getUrl())) {
                    sendVideoProgress(dirbeanLists.get(0));
                } else {
                    sendVideoProgress(dirbeanLists.get(1));
                }
            }
        }, 0, 60000);
        //myJCVideoPlayerStandard.startVideo();
        Drawable down = getResources().getDrawable(R.drawable.video_download);
        down.setBounds(0, 0, DisplayUtil.dip2px(MyApplication.mApplication, 14), DisplayUtil.dip2px(MyApplication.mApplication, 14));
        mVideoTvDownload.setCompoundDrawables(down, null, null, null);

        //--------------------------------------收藏处理---------------------------------------------
        if (StringUtils.isNotEmpty(infobeanLists.get(0).getCollect_status())) {
            Log.e("whwhhw", "收藏收藏");
            //video_detail_ib_collect.setBackgroundResource(R.drawable.sc_press);
            //video_detail_tv_collect.setText("已收藏");
            Drawable weather = getResources().getDrawable(R.drawable.video_collect_press);
            weather.setBounds(0, 0, DisplayUtil.dip2px(MyApplication.mApplication, 14), DisplayUtil.dip2px(MyApplication.mApplication, 14));
            video_tv_collect.setCompoundDrawables(weather, null, null, null);

        } else {
            Log.e("whwhhw", "没有收藏--没有收藏");
            Drawable weather = getResources().getDrawable(R.drawable.video_collect_nor);
            weather.setBounds(0, 0, DisplayUtil.dip2px(MyApplication.mApplication, 14), DisplayUtil.dip2px(MyApplication.mApplication, 14));
            video_tv_collect.setCompoundDrawables(weather, null, null, null);
            //video_detail_tv_collect.setText("未收藏");
        }

        //-----------------------------------------vip处理------------------------------------------
        if (info_is_vip == 1) {
            video_detail_tv_vip.setText("会员已免费");
            video_detail_ll_vip.setVisibility(View.GONE);
        } else {
            video_detail_tv_vip.setText("开通会员");
            video_detail_ll_vip.setVisibility(View.VISIBLE);
        }


        //-----------------------------------视频详情和目录处理---------------------------------------
        //setCapData();
        //设置监听事件
        rl_detail_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video_detail_tv_detail.setTextColor(getResources().getColor(R.color.vedio_cap_press));
                detail_content_detail_sv.setVisibility(View.VISIBLE);
                video_webview_detail.setVisibility(View.VISIBLE);
                detail_view_one.setVisibility(View.VISIBLE);
                video_detail_tv_cap.setTextColor(getResources().getColor(R.color.vedio_cap));
                detail_view_two.setVisibility(View.INVISIBLE);
                detail_content_cap.setVisibility(View.GONE);
                video_detail_tv_talk.setTextColor(getResources().getColor(R.color.vedio_cap));
                detail_view_three.setVisibility(View.INVISIBLE);
                detail_content_comment.setVisibility(View.GONE);
                if (payPrice != 0.00) {
                    llayout_video_detail_bottom_one.setVisibility(View.VISIBLE);
                }
                commentLinear.setVisibility(View.GONE);
            }
        });


        rl_detail_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCapData();
            }
        });

        rl_detail_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCommentData();
            }
        });

        ClickListener listener = new ClickListener();
        rlayout_comment_share.setOnClickListener(listener);
        commentButton.setOnClickListener(listener);

        video_detail_titil_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsAllow) {
                    Toast.makeText(mContext, "公开课尚未开始，无法缓存哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isNotEmpty(user_id) && StringUtils.isNotEmpty(login_rand_str)) {
                    if (payPrice == 0.00) {
                        setShowDownloadData();
                    } else if (payStatus == 1) {
                        setShowDownloadData();
                    } else if (info_is_vip == 1) {
                        setShowDownloadData();
                    } else {
//                        centerDialog.show();
//                        TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
//                        TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
//                        TextView detail_dialog_worning = (TextView) centerDialog.findViewById(R.id.detail_dialog_worning);
//                        detail_dialog_worning.setText("未购买或非会员不能下载视频课程");
//                        detail_dialog_price.setText("¥ " + payPrice);
//                        detail_dialog_title.setText(info_title);
                        showManyPayPop();
                    }


                } else {
                    Intent intent = new Intent(VideoInfosOldActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }
            }
        });

        video_rlayout_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConcelDownloadData();
            }
        });

        rlayout_have_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoInfosOldActivity.this, DownManagerActivity.class);
                startActivity(intent);
            }
        });

        //downbeanLists
        tv_all_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < downbeanLists.size(); i++) {
                    String video_id = downbeanLists.get(i).getVideo_id();
                    int fatherId = Integer.parseInt(video_id);
                    String id = downbeanLists.get(i).getId();
                    int childId = Integer.parseInt(id);
                    File downloadFile = new File(FILEPATH + "/" + "." + childId + ".mp4");
                    DataKeeper dataKeeper = new DataKeeper(VideoInfosOldActivity.this);
                    if (!downloadFile.exists()) {
                        if (!dataKeeper.isHave(fatherId, childId)) {
                            DownVideoBean downVideoBean = new DownVideoBean();

                            downVideoBean.setFatherTitle(downbeanLists.get(i).getVideo_title());
                            downVideoBean.setFatherImg(downbeanLists.get(i).getUrl());

                            String chileTitle = downbeanLists.get(i).getVideo_title();
                            downVideoBean.setChildTitle(chileTitle);
                            //String chileUrl = "http://shaozi-video.oss-cn-beijing.aliyuncs.com/" + downbeanLists.get(downPos).getDownload_url();
                            String chileUrl = "http://u.wimg.cc/" + downbeanLists.get(i).getDownload_url();
                            downVideoBean.setChildUrl(chileUrl);
                            downVideoBean.setChildStart(0);
                            downVideoBean.setChildFinished(0);
                            downVideoBean.setChildPrograss(0);
                            //infobeanLists.get(0).getTitle(), infobeanLists.get(0).getImg()
                            manager.addTask(fatherId, infobeanLists.get(0).getTitle(),
                                    infobeanLists.get(0).getImg(), childId, chileUrl, chileTitle);
                        }
                    }
                    //setNetStuat(childId, 0, userId);
                }
                ToastUtils.showToast(VideoInfosOldActivity.this, "已缓存");
            }
        });


        /**
         * 判断公开课倒计时
         */
        Log.e("DH_TIME", infobeanLists.get(0).getPublish_shijian() + "  " + infobeanLists.get(0).getNow_time());
        if (infobeanLists.get(0).getPublish_shijian() == null || "".equals(infobeanLists.get(0).getPublish_shijian())
                || infobeanLists.get(0).getNow_time() == null || "".equals(infobeanLists.get(0).getNow_time())) {
            mIsAllow = true;
            mCountDown.setVisibility(View.GONE);
        } else {
            if (infobeanLists.get(0).getPublish_shijian() != null && !"".equals(infobeanLists.get(0).getPublish_shijian())) {
                try {
                    mPublicTime = Long.parseLong(infobeanLists.get(0).getPublish_shijian());
                } catch (Exception e) {
                    mPublicTime = 0;
                }
            }
            if (infobeanLists.get(0).getNow_time() != null && !"".equals(infobeanLists.get(0).getNow_time())) {
                try {
                    mNowTime = Long.parseLong(infobeanLists.get(0).getNow_time());
                } catch (Exception e) {
                    mNowTime = 0;
                }
            }
            mResidueTime = mPublicTime - mNowTime;
            Log.e("DH_TIME", mResidueTime + "");
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
    }

    /**
     * 倒计时
     */
    private long DAT = 24 * 60 * 60;
    private long HOUR = 60 * 60;
    private long MINUTE = 60;

    private void residueCountDown(long residueTime) {
//        mResidueTimeDay
        if (mCountDownTimer == null) {
            starCountDown(residueTime);
            mCountDownTimer = new CountDownTimer(residueTime * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    starCountDown(l / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(mContext, "公开课开始了！", Toast.LENGTH_SHORT).show();
                    mIsAllow = true;
                    mCountDown.setVisibility(View.GONE);
                }
            };
            mCountDownTimer.start();
        }
    }

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

    public int videoProgress(int position) {
        this.positionPro = position;
        return position;
    }

    public void share() {
        if (StringUtils.isNotEmpty(user_id)) {
            shareDialog = new Dialog(VideoInfosOldActivity.this, R.style.ActionSheetDialogStyle);
            //填充对话框的布局
            View inflate = LayoutInflater.from(VideoInfosOldActivity.this).inflate(R.layout.share_dialog, null);
            //初始化控件
            RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
            RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
            RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
            RelativeLayout shareRlayoutCompany = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_company);
            Log.e("DH_mIs_company", mIs_company+"  "+mIs_buy_company);
            if ("0".equals(mIs_company)) {
                shareRlayoutCompany.setVisibility(View.GONE);//TODO
            } else if ("1".equals(mIs_company)) {
                shareRlayoutCompany.setVisibility(View.VISIBLE);//TODO
            }
            share_rlayout_py.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareDialog.dismiss();
                    //msgApi
                    isShareInform = true;
                    isPayCourseOrAction = 2;
                    temporaryEventNo = couseId;
                    ShareUtil.weiChat(Constants.wx_api, 7, VideoInfosOldActivity.this,
                            infobeanLists.get(0).getShare_url() + "?helper=" + user_id,
                            infobeanLists.get(0).getTitle(),
                            infobeanLists.get(0).getSub_title());
                }
            });

            share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareDialog.dismiss();
                    isShareInform = true;
                    isPayCourseOrAction = 2;
                    temporaryEventNo = couseId;
                    ShareUtil.weiChat(Constants.wx_api, 8, VideoInfosOldActivity.this,
                            infobeanLists.get(0).getShare_url() + "?helper=" + user_id,
                            infobeanLists.get(0).getTitle(),
                            infobeanLists.get(0).getSub_title());
                }
            });
            shareRlayoutCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("DH_mYunumber","mYunumber:"+mYunumber);
                    if(payPrice == 0.00){
                        if("1".equals(mIs_buy_company)){
                            Intent intent = new Intent(VideoInfosOldActivity.this, CompanyMessageActivity.class);
                            intent.putExtra("id", couseId);
                            intent.putExtra("type", "1");
                            intent.putExtra("courseOrAction", "2");
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(VideoInfosOldActivity.this, CompanyMessageActivity.class);
                            intent.putExtra("id", couseId);
                            intent.putExtra("type", "2");
                            intent.putExtra("courseOrAction", "2");
                            startActivity(intent);
                        }
                    }else{
                        if("1".equals(mIs_buy_company)){
                            Intent intent = new Intent(VideoInfosOldActivity.this, CompanyMessageActivity.class);
                            intent.putExtra("id", couseId);
                            intent.putExtra("type", "1");
                            intent.putExtra("courseOrAction", "2");
                            startActivity(intent);
                        }else{
                            double num = 0;
                            if(mYunumber!=null&&!"".equals(mYunumber)){
                                try {
                                    num = Double.parseDouble(mYunumber);
                                }catch (Exception e){
                                    num = 0;
                                }
                            }
                            if(num>0){//TODO 多人购买剩余人数
                                Intent intent = new Intent(VideoInfosOldActivity.this, CompanyMessageActivity.class);
                                intent.putExtra("id", couseId);
                                intent.putExtra("type", "2");
                                intent.putExtra("courseOrAction", "2");
                                startActivity(intent);
                            }else{
                                Toast.makeText(mContext, "请先“为好友购买”后再分享至企业号", Toast.LENGTH_SHORT).show();
                                showManyPayPop();
                            }

                        }
                    }


                    shareDialog.dismiss();
                }
            });
            share_llayout_concel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    //TODO
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.count_down_back:
                finish();
                break;
        }
    }

    /**
     * 事件点击监听器
     */
    private final class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.commentButton:    //发表评论按钮
                    if (isEditEmply()) {        //判断用户是否输入内容
                        if (isReply) {
                            replyComment();
                        } else {
                            publishComment();
                        }
                        if (payPrice != 0.00) {
                            llayout_video_detail_bottom_one.setVisibility(View.VISIBLE);
                        }
                        commentLinear.setVisibility(View.GONE);
                        onFocusChange(false);
                    }
                    break;

                case R.id.rlayout_comment_share:        //底部评论按钮
                    isReply = false;
                    commentLinear.setVisibility(View.VISIBLE);
                    llayout_video_detail_bottom_one.setVisibility(View.GONE);
                    onFocusChange(true);
                    break;

            }
        }
    }

    /**
     * 发表评论
     */
    private void publishComment() {

        HttpUserManager.getInstance().addComment(course_info_id, comment, "1", user_id, "0", "", new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                commentHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, CommentResultBena.class);
    }

    private Handler commentHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(VideoInfosOldActivity.this, msgStrs);
                    JSONObject commentObj = new JSONObject(obj.toString()).getJSONObject("info");
                    String commentResult_re_name = commentObj.optString("re_name");
                    String commentResult_parentid = commentObj.optString("parentid");
                    String commentResult_nickname = commentObj.optString("nickname");
                    String commentResult_shijian = commentObj.optString("shijian");
                    String commentResult_id = commentObj.optString("id");
                    String commentResult_user_id = commentObj.optString("user_id");
                    String commentResult_content = commentObj.optString("content");
                    String commentResult_avator = commentObj.optString("avator");
                    String commentResult_up_user = commentObj.optString("up_user");

                    VedioDetailInfo.CommentBean cbean =

                            new VedioDetailInfo.CommentBean(commentResult_avator, commentResult_content,
                                    commentResult_id, commentResult_nickname, commentResult_parentid,
                                    commentResult_re_name, commentResult_shijian, commentResult_up_user, commentResult_user_id);

                    commentLists.add(0, cbean);
                    commentadapter.notifyDataSetChanged();
                    commentList.invalidate();

                } else {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(VideoInfosOldActivity.this, msgStrs);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 回复评论
     */
    private void replyComment() {
        String parentid = commentLists.get(fatherpos).getParentid();
        String Nickname = commentLists.get(fatherpos).getNickname();
        Log.e("whwh", "parentid==" + parentid + "::::" + "re_name==" + Nickname);
        HttpUserManager.getInstance().addComment(course_info_id, comment, "1", user_id, parentid, Nickname, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                replyHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, ReplyResultBean.class);
    }

    private Handler replyHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(VideoInfosOldActivity.this, msgStrs);
                    JSONObject commentObj = new JSONObject(obj.toString()).getJSONObject("info");
                    String replyResult_re_name = commentObj.optString("re_name");
                    String replyResult_parentid = commentObj.optString("parentid");
                    String replyResult_nickname = commentObj.optString("nickname");
                    String replyResult_shijian = commentObj.optString("shijian");
                    String replyResult_id = commentObj.optString("id");
                    String replyResult_user_id = commentObj.optString("user_id");
                    String replyResult_content = commentObj.optString("content");
                    String replyResult_avator = commentObj.optString("avator");
                    String replyResult_up_user = commentObj.optString("up_user");

                    ReplyBean bean = new ReplyBean();
                    bean.setRe_name(replyResult_re_name);
                    bean.setParentid(replyResult_parentid);
                    bean.setNickname(replyResult_nickname);
                    bean.setShijian(replyResult_shijian);
                    bean.setId(replyResult_id);
                    bean.setUser_id(replyResult_user_id);
                    bean.setContent(replyResult_content);
                    bean.setAvator(replyResult_avator);
                    bean.setUp_user(replyResult_up_user);

                    commentadapter.getReplyComment(bean, fatherpos);
                    commentadapter.notifyDataSetChanged();

                } else {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(VideoInfosOldActivity.this, msgStrs);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = commentEdit.getText().toString().trim();
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        commentEdit.setText("");
        return true;
    }

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                }
            }
        }, 100);
    }

    public void progress(int currentPosition) {

        if (currentPosition >= 180000) {
            if (StringUtils.isNotEmpty(user_id) && StringUtils.isNotEmpty(login_rand_str)) {

                Log.e("whwh", "3分钟时间到了,已经登录了");
                if (payPrice == 0.00) {
                    //JCMediaManager.instance().mediaPlayer.start();
                    //myJCVideoPlayerStandard.onStatePlaying();
                    Log.e("whwh", "3分钟时间到了,免费的视频");
                } else if (info_is_vip == 1) {
                    //JCMediaManager.instance().mediaPlayer.start();
                    //myJCVideoPlayerStandard.onStatePlaying();
                    Log.e("whwh", "3分钟时间到了,我是VIP");

                } else {
                    if (payStatus == 1) { //支付成功 可看
                        //JCMediaManager.instance().mediaPlayer.start();
                        //myJCVideoPlayerStandard.onStatePlaying();
                        Log.e("whwh", "3分钟时间到了,我买了这个课程");
                    } else {
                        if (!isNowPay) {
                            Log.e("whwh", "3分钟时间到了,我还没有购买课程");
                            JCMediaManager.instance().mediaPlayer.pause();
                            myJCVideoPlayerStandard.onStatePause();
                            if (!isFirstDialog) {
//                                centerDialog.show();
//                                TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
//                                TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
//                                detail_dialog_price.setText("¥ " + payPrice);
//                                detail_dialog_title.setText(info_title);
                                showManyPayPop();
                                isFirstDialog = true;
                            }
                        }
                    }
                }

            } else {
                JCMediaManager.instance().mediaPlayer.pause();
                myJCVideoPlayerStandard.onStatePause();
                Intent intent = new Intent(VideoInfosOldActivity.this, LoginNewActivity.class);
                startActivity(intent);
            }
        }
    }

    private void setCapData() {

        video_detail_tv_detail.setTextColor(getResources().getColor(R.color.vedio_cap));
        detail_content_detail_sv.setVisibility(View.GONE);
        detail_view_one.setVisibility(View.INVISIBLE);
        video_detail_tv_cap.setTextColor(getResources().getColor(R.color.vedio_cap_press));
        detail_view_two.setVisibility(View.VISIBLE);
        detail_content_cap.setVisibility(View.VISIBLE);
        video_detail_tv_talk.setTextColor(getResources().getColor(R.color.vedio_cap));
        detail_view_three.setVisibility(View.INVISIBLE);
        detail_content_comment.setVisibility(View.GONE);
        if (payPrice != 0.00) {
            llayout_video_detail_bottom_one.setVisibility(View.VISIBLE);
        }
        commentLinear.setVisibility(View.GONE);

        //设置适配器
        VideoPlayAdapter videoAdapter = new VideoPlayAdapter(mContext);
        detail_content_cap_list.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
    }

    private void setShowDownloadData() {
        video_llayout_title.setVisibility(View.GONE);
        video_rlayout_download.setVisibility(View.VISIBLE);

        detail_content_detail_sv.setVisibility(View.GONE);
        detail_content_cap.setVisibility(View.GONE);

        detail_content_comment.setVisibility(View.GONE);
        llayout_video_detail_bottom_one.setVisibility(View.GONE);
        commentLinear.setVisibility(View.GONE);
        detail_content_download.setVisibility(View.VISIBLE);
        detail_content_download_des.setVisibility(View.VISIBLE);

        //tv_have_download_nums
        DataKeeper dataKeeper = new DataKeeper(this);
        int fatherId = Integer.parseInt(infobeanLists.get(0).getId());
        ArrayList<SQLDownLoadInfo> downLoadingList = dataKeeper.getUserDownLoadInfoByFatherId(fatherId);
        tv_have_download_nums.setText(downLoadingList.size() + "");


        //设置适配器
        VideoDownAdapter downAdapter = new VideoDownAdapter(mContext, downbeanLists, infobeanLists.get(0).getTitle(), infobeanLists.get(0).getImg(), user_id);
        detail_content_download_list.setAdapter(downAdapter);
        downAdapter.notifyDataSetChanged();
    }

    private void setConcelDownloadData() {
        video_llayout_title.setVisibility(View.VISIBLE);
        video_rlayout_download.setVisibility(View.GONE);

        detail_content_detail_sv.setVisibility(View.VISIBLE);
        detail_content_cap.setVisibility(View.GONE);

        detail_content_comment.setVisibility(View.GONE);
        if (payPrice != 0.00) {
            llayout_video_detail_bottom_one.setVisibility(View.VISIBLE);
        }
        commentLinear.setVisibility(View.GONE);
        detail_content_download.setVisibility(View.GONE);
        detail_content_download_des.setVisibility(View.GONE);
    }


    /**
     * 论坛
     */
    private void setCommentData() {

        video_detail_tv_detail.setTextColor(getResources().getColor(R.color.vedio_cap));
        detail_content_detail_sv.setVisibility(View.GONE);
        detail_view_one.setVisibility(View.INVISIBLE);

        video_detail_tv_cap.setTextColor(getResources().getColor(R.color.vedio_cap));
        detail_view_two.setVisibility(View.INVISIBLE);
        detail_content_cap.setVisibility(View.GONE);

        video_detail_tv_talk.setTextColor(getResources().getColor(R.color.vedio_cap_press));
        detail_view_three.setVisibility(View.VISIBLE);
        detail_content_comment.setVisibility(View.VISIBLE);
        //llayout_video_detail_bottom_one.setVisibility(View.GONE);
        //commentLinear.setVisibility(View.VISIBLE);
        commentadapter = new CommentAdapter(this, commentLists, R.layout.comment_item, chandler, course_info_id, user_id);
        commentList.setAdapter(commentadapter);
    }

    @SuppressLint("HandlerLeak")
    private Handler chandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                isReply = true;
                commentPos = (String) msg.obj;
                fatherpos = Integer.parseInt(commentPos.substring(0, commentPos.lastIndexOf("|")));
                childpos = Integer.parseInt(commentPos.substring(commentPos.lastIndexOf("|") + 1, commentPos.length()));

                Log.e("whwh", "fatherpos==" + fatherpos);
                Log.e("whwh", "childpos==" + childpos);

                commentLinear.setVisibility(View.VISIBLE);
                llayout_video_detail_bottom_one.setVisibility(View.GONE);
                onFocusChange(true);
            }
        }
    };


    //TODO 监听事件
    private void initListener() {
        //收藏功能
        video_detail_titil_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(user_id) && StringUtils.isNotEmpty(login_rand_str)) {
                    Log.e("whwh", "info_rand_str==++" + info_rand_str);
                    Log.e("whwh", "login_rand_str==++" + login_rand_str);
                    Log.e("whwh", "user_id==++" + user_id);
                    if (StringUtils.isNotEmpty(infobeanLists.get(0).getCollect_status())) {
                        setLoveDataFromNet(couseId, user_id, "1");
                    } else {
                        setLoveDataFromNet(couseId, user_id, "1");
                    }

                } else {
                    Intent intent = new Intent(VideoInfosOldActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }
            }
        });

        //分享功能
        video_share_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog = new Dialog(VideoInfosOldActivity.this, R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                View inflate = LayoutInflater.from(VideoInfosOldActivity.this).inflate(R.layout.share_dialog, null);
                //初始化控件
                RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
                RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
                RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
                share_rlayout_py.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                        //msgApi
                        ShareUtil.weiChat(Constants.wx_api, 7, VideoInfosOldActivity.this,
                                infobeanLists.get(0).getShare_url() + "?helper=" + user_id,
                                infobeanLists.get(0).getTitle(),
                                infobeanLists.get(0).getSub_title());
                    }
                });

                share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                        ShareUtil.weiChat(Constants.wx_api, 8, VideoInfosOldActivity.this,
                                infobeanLists.get(0).getShare_url() + "?helper=" + user_id,
                                infobeanLists.get(0).getTitle(),
                                infobeanLists.get(0).getSub_title());
                    }
                });

                share_llayout_concel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

            }
        });


        //支付功能
        video_detail_ll_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(user_id) && StringUtils.isNotEmpty(login_rand_str)) {
                    if (payPrice == 0.00) {
                        ToastUtils.showToast(VideoInfosOldActivity.this, "免费课程,无需付费!");
                    }
//                    else if (payStatus == 1) {
//                        ToastUtils.showToast(VideoInfosActivity.this, "已经付费了!");
//                    } else if (info_is_vip == 1) {
//                        ToastUtils.showToast(VideoInfosActivity.this, "VIP会员,免费学习!");
//                    }
                    else {
//                        centerDialog.show();
//                        TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
//                        TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
//                        detail_dialog_price.setText("¥ " + payPrice);
//                        detail_dialog_title.setText(info_title);
                        showManyPayPop();
                    }

                } else {
                    Intent intent = new Intent(VideoInfosOldActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }
            }
        });

        video_detail_ll_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast(VideoInfosOldActivity.this,"aaa");
                if (StringUtils.isNotEmpty(user_id) && StringUtils.isNotEmpty(login_rand_str)) {
                    if (info_is_vip != 1) {
//                        video_detail_tv_vip.setText("多人购买");
                        VedioDetailInfo.InfoBean vedioDetailInfo = infobeanLists.get(0);
                        Intent intent = new Intent(VideoInfosOldActivity.this, NewEventDetailsActivity.class);
                        intent.putExtra("event_id", vedioDetailInfo.getVip_id());
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(VideoInfosOldActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }
            }
        });

        //屏幕按钮点击
        //TODO WHWH
        video_detial_zhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(user_id) && StringUtils.isNotEmpty(login_rand_str)) {
                    Log.e("whwh", "info_rand_str==++" + info_rand_str);
                    Log.e("whwh", "login_rand_str==++" + login_rand_str);
                    Log.e("whwh", "user_id==++" + user_id);
//                    centerDialog.show();
//                    TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
//                    TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
//                    detail_dialog_price.setText("¥ " + payPrice);
//                    detail_dialog_title.setText(info_title);
                    showManyPayPop();
                } else {
                    Intent intent = new Intent(VideoInfosOldActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    /**
     * 老版支付监听
     *
     * @param dialog
     * @param view
     */
    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
//        switch (view.getId()) {
//            case R.id.dialog_ll_cancel:
//                //TODO WH
//                aliPay();
//                break;
//
//            case R.id.dialog_ll_sure:
//                weichatPay();
//                break;
//            default:
//                break;
//        }
    }


    //TODO WHWHW
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(VideoInfosOldActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        isNowPay = true;
                        video_detail_tv_buy.setText("为好友购买");
                        Intent intent = new Intent(VideoInfosOldActivity.this, ResultActivity.class);
                        intent.putExtra("type", String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no", temporaryEventNo);
                        intent.putExtra("course_type", String.valueOf(isPayCourseType));
                        startActivity(intent);
//                        if(mIsAllow){
//                            myJCVideoPlayerStandard.startVideo();
//                        }
//                        requestAliPayZhifuState(2);

                    } else {
                        Toast.makeText(VideoInfosOldActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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

    /***
     * 支付宝支付
     */
    //TODO WHW
    private void aliPay() {
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            finish();
//                        }
//                    }).show();
//            return;
//        }
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0); //payPrice
//
//        outTradeNo = OrderInfoUtil2_0.getOutTradeNo();
//        Log.e("zhifubao", "outTradeNo=outTradeNo=outTradeNo=" + outTradeNo);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, payPrice, info_title + "视频课程费用", info_title, outTradeNo, rsa2);
//
//        //添加数据
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;

        mOrderPayPresenter.orderPay(mPayMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                PayEntity entity = (PayEntity) value[0];
                final String orderInfo = entity.getInfo().getAlipay();
                temporaryEventNo = entity.getInfo().getOrder_no();
                Log.e("DH_PAY_ALIPAY", "success " + temporaryEventNo);
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(VideoInfosOldActivity.this);
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

            @Override
            public void onFault(String error) {
                Log.e("DH_PAY_ALIPAY", error);
            }
        });
    }


    /**
     * 微信支付
     */
    private void weichatPay() {
//        genPayReq();
        mOrderPayPresenter.orderPay(mPayMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_PAY_WECHET", "success");
                PayEntity entity = (PayEntity) value[0];
                temporaryEventNo = entity.getInfo().getOrder_no();
                PayEntity.InfoBean.WechatBean wechat = entity.getInfo().getWechat();
                IWXAPI wxApi = WXAPIFactory.createWXAPI(VideoInfosOldActivity.this, null);
                wxApi.registerApp(Constants.APP_ID);
                PayReq req = new PayReq();
                req.appId = wechat.getAppid();
                req.nonceStr = wechat.getNoncestr();
                req.packageValue = wechat.getPackageX();
                req.prepayId = wechat.getPrepayid();
                req.partnerId = wechat.getPartnerid();
                req.timeStamp = wechat.getTimestamp();
                req.sign = wechat.getSign();
                boolean b = wxApi.sendReq(req);
                Log.e("DH_PAY_WECHET", req.checkArgs() + "  " + b);
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_PAY_WECHET", error);
            }
        });
    }

    private void genPayReq() {

//        req.appId = Constants.APP_ID;
//        req.partnerId = Constants.MCH_ID;
//        req.prepayId = resultunifiedorder.get("prepay_id");
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = genNonceStr();
//        req.timeStamp = String.valueOf(genTimeStamp());
//
//
//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//        req.sign = genAppSign(signParams);
//        sb.append("sign\n" + req.sign + "\n\n");
//        //show.setText(sb.toString());
//        Log.e("orion", signParams.toString());

    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    //生成订单
    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            // dialog = ProgressDialog.show(VideoDetailActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            // if (dialog != null) {
            //   dialog.dismiss();
            //}
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            // show.setText(sb.toString());

            resultunifiedorder = result;

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();
            Log.e("orion", entity);

            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("orion", content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }
    }

    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID)); //APPID
            packageParams.add(new BasicNameValuePair("body", info_title));//内容信息
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            Log.e("weixin", "nonce_str==" + nonceStr);
            packageParams.add(new BasicNameValuePair("notify_url", "http://php.shaoziketang.com/callback/weChat"));
            //"201709130818195886348"
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            Log.e("weixin", "genOutTradNo==" + genOutTradNo());
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "106.120.116.198"));
            BigDecimal totalFeeBig = new BigDecimal(payPrice);
            int totalFee = totalFeeBig.multiply(new BigDecimal(100)).intValue();
            //String.valueOf(totalFee)
            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(totalFee)));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));


            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            Log.e("weixin", "sign==" + sign);

            String xmlstring = toXml(packageParams);
            Log.e("whwh", xmlstring);
            return new String(xmlstring.toString().getBytes(), "ISO-8859-1");

        } catch (Exception e) {
            Log.e("whwh", "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");

            if (params.get(i).getName().equals("out_trade_no")) {
                out_trade_no = params.get(i).getValue();
                Log.e("weixin", "out_trade_no==" + out_trade_no);
            }
        }
        sb.append("</xml>");

        Log.e("orion", sb.toString());
        return sb.toString();
    }


    //支付支付支付成功后
    private void requestAliPayZhifuState(int num) {
        RequestParams params = new RequestParams(Constants.paylUrl);
        params.addBodyParameter("status", "buy");
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("id", couseId);
        params.addBodyParameter("money", payPrice + "");
        params.addBodyParameter("order_type", "3");
        params.addBodyParameter("order_app", "4");
        String timeStr = StringUtils.getTimestamp();
        params.addBodyParameter("shijian", timeStr);
        params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));

        if (num == 2) {
            params.addBodyParameter("order_wz", "2");
            params.addBodyParameter("order_true", outTradeNo);
        } else if (num == 1) {
            params.addBodyParameter("order_wz", "1");
            params.addBodyParameter("order_true", out_trade_no);
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "requestAliPayZhifuState---联网成功---result===" + result);
                dealPayResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "requestAliPayZhifuState---联网失败---" + ex.getMessage());
                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "requestAliPayZhifuState---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "requestAliPayZhifuState---onFinished---");
            }
        });
    }

    private void dealPayResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.optInt("code");
            String msg = jsonObject.optString("msg");
            if (code == 200) {
                ToastUtils.showToast(this, msg);
                video_detail_tv_buy.setText("为好友购买");
                video_detail_ll_buy.setClickable(true);

            } else {
                ToastUtils.showToast(this, msg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 收藏
     */
    private void setLoveDataFromNet(String ids, String user_id, String type) {
        //联网
        //视频内容
        RequestParams params = new RequestParams(Constants.loveUrl);
        params.addBodyParameter("id", ids);
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("type", type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dealResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void dealResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int err = jsonObject.optInt("err");
            int number = jsonObject.optInt("number");
            if (err == 0) {
                // ToastUtils.showToast(this, "成功收藏" + number + "条");
                ToastUtils.showToast(this, "收藏成功!");
                //video_detail_ib_collect.setBackgroundResource(R.drawable.sc_press);
                Drawable weather = getResources().getDrawable(R.drawable.video_collect_press);
                weather.setBounds(0, 0, DisplayUtil.dip2px(MyApplication.mApplication, 14), DisplayUtil.dip2px(MyApplication.mApplication, 14));
                video_tv_collect.setCompoundDrawables(weather, null, null, null);
                // video_detail_tv_collect.setText("已收藏");
            } else if (err == -1) {
                ToastUtils.showToast(this, "取消收藏!");
                //video_detail_ib_collect.setBackgroundResource(R.drawable.sc_normal);
                //video_detail_tv_collect.setText("未收藏");
                Drawable weather = getResources().getDrawable(R.drawable.video_collect_nor);
                weather.setBounds(0, 0, DisplayUtil.dip2px(MyApplication.mApplication, 14), DisplayUtil.dip2px(MyApplication.mApplication, 14));
                video_tv_collect.setCompoundDrawables(weather, null, null, null);
            } else {
                ToastUtils.showToast(this, "收藏失败!");
                //video_detail_ib_collect.setBackgroundResource(R.drawable.sc_normal);
                Drawable weather = getResources().getDrawable(R.drawable.video_collect_nor);
                weather.setBounds(0, 0, DisplayUtil.dip2px(MyApplication.mApplication, 14), DisplayUtil.dip2px(MyApplication.mApplication, 14));
                video_tv_collect.setCompoundDrawables(weather, null, null, null);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载H5页面
     */
    private void setWebviewData(String id) {
        //url = "http://121.40.248.175/szkadmin/?c=welcome&m=appdetail&type=1&id=" + id;
        url = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=1&id=" + id;
        //设置支持javaScript
        webSettings = video_webview_detail.getSettings();
        //设置支持javaScript
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //设置文字大小
        webSettings.setTextSize(WebSettings.TextSize.SMALLER);
        webSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        video_webview_detail.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //pb_loading.setVisibility(View.GONE);
            }
        });

        video_webview_detail.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重写onKeyDown，当浏览网页，WebView可以后退时执行后退操作。
        if (keyCode == KeyEvent.KEYCODE_BACK && video_webview_detail.canGoBack()) {
            video_webview_detail.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JCVideoPlayer.releaseAllVideos();
        Constants.weixinFlag = 10;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
        login_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
        getCourseDetailDataFromNet(user_id, couseId);  //网络请求 加载视频详情信息
        if (Constants.weixinFlag == 0) {
            //TODO WHWH
            isNowPay = true;
            if (mIsAllow) {
                myJCVideoPlayerStandard.startVideo();
            }
//            requestAliPayZhifuState(1);
            video_detail_tv_buy.setText("为好友购买");
            video_detail_ll_buy.setClickable(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();

        //判断控件是否显示
        if (commentLinear.getVisibility() == View.VISIBLE) {
            commentLinear.setVisibility(View.GONE);
            if (payPrice != 0.00) {
                llayout_video_detail_bottom_one.setVisibility(View.VISIBLE);
            }
        }
    }


    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.e("whwhwh", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.e("whwhwh", "ON_CLICK_START_ERROR");
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.e("whwhwh", "ON_CLICK_START_AUTO_COMPLETE");
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.e("whwhwh", "ON_CLICK_PAUSE");
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.e("whwhwh", "ON_CLICK_RESUME");
                    isFirstDialog = false;
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.e("whwh", "ON_SEEK_POSITION");
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    //TODO  要做读取下一个处理
                    Log.e("whwhwh", "ON_AUTO_COMPLETE");
                    if (videoindex < videosizes) {
                        videoindex++;
                    }
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.e("whwhwh", "ON_ENTER_FULLSCREEN");
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.e("whwhwh", "ON_QUIT_FULLSCREEN");
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.e("whwhwh", "ON_ENTER_TINYSCREEN");
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.e("whwhwh", "ON_QUIT_TINYSCREEN");
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.e("whwhwh", "ON_TOUCH_SCREEN_SEEK_VOLUME");
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.e("whwhwh", "ON_TOUCH_SCREEN_SEEK_POSITION");
                    break;
                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.e("whwhwh", "ON_CLICK_START_THUMB");
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.e("whwhwh", "ON_CLICK_BLANK");
                    isFirstDialog = false;
                    break;
                default:
                    Log.e("whwhwh", "unknow");
                    break;
            }
        }
    }


    class VideoPlayAdapter extends BaseAdapter {

        private Context context;
        private int TypeOne = 0;//注意这个不同布局的类型起始值必须从0开始
        private int TypeTwo = 1;

        public VideoPlayAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return dirbeanLists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            String dirUrl = dirbeanLists.get(position).getUrl();
            if (dirUrl.equals("null") || dirUrl.equals("")) {
                return TypeOne;
            } else {
                return TypeTwo;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TypeOneViewHolder typeOneViewHolder = null;
            TypeTwoViewHolder typeTwoViewHolder = null;
            int Type = getItemViewType(position);
            if (convertView == null) {
                if (Type == TypeOne) {
                    convertView = getLayoutInflater().inflate(R.layout.video_detail_item_one, parent, false);
                    typeOneViewHolder = new TypeOneViewHolder();
                    typeOneViewHolder.video_item_one_tv = (TextView) convertView.findViewById(R.id.video_item_one_tv);
                    typeOneViewHolder.video_detail_item_rl_one = (RelativeLayout) convertView.findViewById(R.id.video_detail_item_rl_one);
                    convertView.setTag(typeOneViewHolder);

                } else if (Type == TypeTwo) {
                    convertView = getLayoutInflater().inflate(R.layout.video_detail_item_two, parent, false);
                    typeTwoViewHolder = new TypeTwoViewHolder();
                    typeTwoViewHolder.video_item_two_tv = (TextView) convertView.findViewById(R.id.video_item_two_tv);
                    typeTwoViewHolder.video_detail_item_rl_two = (RelativeLayout) convertView.findViewById(R.id.video_detail_item_rl_two);
                    typeTwoViewHolder.video_item_two_rlayout = (ImageView) convertView.findViewById(R.id.video_item_two_rlayout);
//                    typeTwoViewHolder.video_item_two_iv = (ImageView) convertView.findViewById(R.id.video_item_two_iv);

                    convertView.setTag(typeTwoViewHolder);
                }
            } else {

                if (Type == TypeOne) {
                    typeOneViewHolder = (TypeOneViewHolder) convertView.getTag();
                } else if (Type == TypeTwo) {
                    typeTwoViewHolder = (TypeTwoViewHolder) convertView.getTag();
                }
            }

            //设置数据
            if (Type == TypeOne) {
                typeOneViewHolder.video_item_one_tv.setText(dirbeanLists.get(position).getVideo_title());

            } else if (Type == TypeTwo) {

                typeTwoViewHolder.video_item_two_tv.setText(dirbeanLists.get(position).getVideo_title());
                Log.e("DH_DIR", dirbeanLists.get(position).getVideo_file_title());
                final int pos = position;
                final TypeTwoViewHolder finalTypeTwoViewHolder = typeTwoViewHolder;
                typeTwoViewHolder.video_detail_item_rl_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finalTypeTwoViewHolder.video_detail_item_rl_two.setBackgroundColor(getResources().getColor(R.color.vedio_cap_press));
                        //finalTypeTwoViewHolder.video_item_two_rlayout.setBackgroundResource(R.drawable.greenyuandian);
                        //finalTypeTwoViewHolder.video_item_two_iv.setVisibility(View.VISIBLE);
                        if (mIsAllow) {
                            final VedioDetailInfo.DirBean dirbean = dirbeanLists.get(pos);
                            String info_imgUrl = infobeanLists.get(0).getImg();
                            String videoUrl = dirbean.getUrl();
                            String Video_file_title = dirbean.getVideo_file_title();
                            //TODO WHWH
                            videoindex = pos;
                            myJCVideoPlayerStandard.setUp(videoUrl
                                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, ""); //

                            Picasso.with(context)
                                    .load(info_imgUrl)
                                    .into(myJCVideoPlayerStandard.thumbImageView);
                            JCVideoPlayer.setJcUserAction(new MyUserActionStandard());

                            myJCVideoPlayerStandard.startVideo();
                            timer0ne.cancel();
                            if (timerTwo == null) {
                                timerTwo = new Timer();
                            }
                            timerTwo.schedule(new TimerTask() {
                                public void run() {
                                    Log.e("whwh", "timerTwo===定时器走了!");
                                    sendVideoProgress(dirbean);
                                }
                            }, 0, 60000);
                        } else {
                            Toast.makeText(context, "公开课尚未开始", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
            return convertView;
        }

        private class TypeOneViewHolder {
            private TextView video_item_one_tv;
            private RelativeLayout video_detail_item_rl_one;
        }

        private class TypeTwoViewHolder {
            private TextView video_item_two_tv;
            private RelativeLayout video_detail_item_rl_two;
            private ImageView video_item_two_rlayout;
//            private ImageView video_item_two_iv;
        }

    }

    private void sendVideoProgress(VedioDetailInfo.DirBean dirbean) {
        int second = Math.round((int) positionPro / 1000);
        Log.e("DH_SEND_PROGRESS", "Video_id:" + dirbean.getVideo_id() + "    user_id:" + user_id + "   dirbean.getId:" + dirbean.getId() + "  second:" + second + "  Length:" + dirbean.getLength());
        HttpUserManager.getInstance().VideoProgress("",dirbean.getVideo_id(), user_id, dirbean.getId(), second + "", dirbean.getLength() + "", new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Log.e("whwh", "Object data===>" + data.toString());
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, VideoLoginBean.class);
    }

    private List<VedioDetailInfo.InfoBean> parseJsonInfo(String json) {
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray infoArray = jsonObject.optJSONArray("info");
            Log.e("whwh", "infoArray===>" + infoArray.toString());
            infobeanLists = new ArrayList<>();
            if (infoArray != null && infoArray.length() > 0) {
                for (int i = 0; i < infoArray.length(); i++) {
                    JSONObject infoObj = infoArray.optJSONObject(i);
                    if (infoObj != null) {
                        String info_collect_status = infoObj.optString("collect_status");
                        String info_sub_title = infoObj.optString("sub_title");
                        String info_duan = infoObj.optString("duan"); //讲师描述
                        String info_good = infoObj.optString("good"); //好评率
                        String info_head = infoObj.optString("head"); //讲师头像
                        String info_id = infoObj.optString("id");
                        String info_img = infoObj.optString("img");
                        String info_ios_integral = infoObj.optString("ios_integral");
                        String info_is_login = infoObj.optString("is_login");//ios 单点登录
                        int info_is_vip = infoObj.optInt("is_vip");
                        String info_learn = infoObj.optString("learn"); //有多少人学习该课程
                        String info_name = infoObj.optString("name");//讲师名字
                        String info_new_price = infoObj.optString("new_price");
                        String info_old_price = infoObj.optString("old_price");
                        String info_pay_status = infoObj.optString("pay_status");
                        String info_rand_str = infoObj.optString("rand_str");
                        String info_relative_course = infoObj.optString("relative_course");
                        String info_student = infoObj.optString("student"); //适合的学员
                        String info_title = infoObj.optString("title");
                        String info_vip_id = infoObj.optString("vip_id");
                        String info_vip_price = infoObj.optString("vip_price");
                        String info_share_url = infoObj.optString("share_url");
                        String info_publish_shijian = infoObj.optString("publish_shijian");
                        String info_now_time = infoObj.optString("now_time");
                        mIs_buy_company = infoObj.optString("is_buy_company");
                        mIs_company = infoObj.optString("is_company");
                        mTotal = infoObj.optString("total");
                        mYunumber = infoObj.optString("yunumber");

                        VedioDetailInfo.InfoBean infoBean = new VedioDetailInfo.InfoBean();
                        infoBean.setCollect_status(info_collect_status);
                        infoBean.setSub_title(info_sub_title);
                        infoBean.setDuan(info_duan);
                        infoBean.setGood(info_good);
                        infoBean.setHead(info_head);
                        infoBean.setId(info_id);
                        infoBean.setImg(info_img);
                        infoBean.setIos_integral(info_ios_integral);
                        infoBean.setIs_vip(info_is_vip);
                        infoBean.setLearn(info_learn);
                        infoBean.setName(info_name);
                        infoBean.setNew_price(info_new_price);
                        infoBean.setOld_price(info_old_price);
                        infoBean.setPay_status(info_pay_status);
                        infoBean.setRand_str(info_rand_str);
                        infoBean.setRelative_course(info_relative_course);
                        infoBean.setStudent(info_student);
                        infoBean.setTitle(info_title);
                        infoBean.setVip_id(info_vip_id);
                        infoBean.setVip_price(info_vip_price);
                        infoBean.setShare_url(info_share_url);
                        infoBean.setPublish_shijian(info_publish_shijian);
                        infoBean.setNow_time(info_now_time);
                        infoBean.setIs_buy_company(mIs_buy_company);
                        infoBean.setIs_company(mIs_company);
                        infoBean.setTotal(mTotal);
                        infoBean.setYunumber(mYunumber);
                        infobeanLists.add(infoBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return infobeanLists;
    }


    private List<VedioDetailInfo.CommentBean> parseJsonComment(String json) {
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray commentArray = jsonObject.optJSONArray("comment");
            Log.e("whwh", "commentArray===>" + commentArray.toString());
            commentLists = new ArrayList<>();
            if (commentArray != null && commentArray.length() > 0) {

                for (int i = 0; i < commentArray.length(); i++) {
                    JSONObject commentObj = commentArray.optJSONObject(i);
                    if (commentObj != null) {
                        String comment_avator = commentObj.optString("avator");
                        JSONArray replyArray = commentObj.optJSONArray("child");
                        Log.e("whwh", "replyArray==" + replyArray.toString());
                        replyLists = new ArrayList<>();
                        if (replyArray != null && replyArray.length() > 0) {
                            for (int j = 0; j < replyArray.length(); j++) {
                                JSONObject replyObj = replyArray.optJSONObject(j);
                                if (replyObj != null) {
                                    String reply_avator = replyObj.optString("avator");
                                    String reply_content = replyObj.optString("content");
                                    String reply_id = replyObj.optString("id");
                                    String reply_nickname = replyObj.optString("nickname");
                                    String reply_parentid = replyObj.optString("parentid");
                                    String reply_re_name = replyObj.optString("re_name");
                                    String reply_shijian = replyObj.optString("shijian");
                                    String reply_up_user = replyObj.optString("up_user");
                                    String reply_user_id = replyObj.optString("user_id");

                                    ReplyBean replyBean = new ReplyBean();
                                    replyBean.setAvator(reply_avator);
                                    replyBean.setContent(reply_content);
                                    replyBean.setId(reply_id);
                                    replyBean.setNickname(reply_nickname);
                                    replyBean.setParentid(reply_parentid);
                                    replyBean.setRe_name(reply_re_name);
                                    replyBean.setShijian(reply_shijian);
                                    replyBean.setUp_user(reply_up_user);
                                    replyBean.setUser_id(reply_user_id);
                                    replyLists.add(replyBean);
                                }
                            }
                        }

                        String comment_content = commentObj.optString("content");
                        String comment_id = commentObj.optString("id");
                        String comment_nickname = commentObj.optString("nickname");
                        String comment_parentid = commentObj.optString("parentid");
                        String comment_re_name = commentObj.optString("re_name");
                        String comment_shijian = commentObj.optString("shijian");
                        String comment_up_user = commentObj.optString("up_user");
                        String comment_user_id = commentObj.optString("user_id");

                        VedioDetailInfo.CommentBean commentBean =

                                new VedioDetailInfo.CommentBean(comment_avator, comment_content,
                                        comment_id, comment_nickname, comment_parentid,
                                        comment_re_name, comment_shijian, comment_up_user,
                                        comment_user_id, replyLists);

                        commentLists.add(commentBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commentLists;
    }


    private List<VedioDetailInfo.DirBean> parseJsonDir(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray dirArray = jsonObject.optJSONArray("dir");
            dirbeanLists = new ArrayList<>();
            if (dirArray != null && dirArray.length() > 0) {
                for (int i = 0; i < dirArray.length(); i++) {
                    JSONObject dirObj = dirArray.optJSONObject(i);
                    if (dirArray != null) {
                        String dir_id = dirObj.optString("id");
                        String dir_video_id = dirObj.optString("video_id");
                        String dir_video_title = dirObj.optString("video_title");
                        String dir_create_time = dirObj.optString("create_time");
                        String dir_url = dirObj.optString("url");
                        String dir_video_sort = dirObj.optString("video_sort");
                        String dir_video_file_title = dirObj.optString("video_file_title");
                        Integer dir_length = dirObj.optInt("length");
                        String dir_download_url = dirObj.optString("download_url");

                        VedioDetailInfo.DirBean dirBean = new VedioDetailInfo.DirBean();
                        dirBean.setId(dir_id);
                        dirBean.setVideo_id(dir_video_id);
                        dirBean.setVideo_title(dir_video_title);
                        dirBean.setCreate_time(dir_create_time);
                        dirBean.setUrl(dir_url);
                        dirBean.setVideo_sort(dir_video_sort);
                        dirBean.setVideo_file_title(dir_video_file_title);
                        dirBean.setLength(dir_length);
                        dirBean.setDownload_url(dir_download_url);
                        dirbeanLists.add(dirBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dirbeanLists;
    }


    private List<VedioDetailInfo.DirBean> parseJsonDown(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray dirArray = jsonObject.optJSONArray("dir");
            downbeanLists = new ArrayList<>();
            if (dirArray != null && dirArray.length() > 0) {
                for (int i = 0; i < dirArray.length(); i++) {
                    JSONObject dirObj = dirArray.optJSONObject(i);
                    if (dirArray != null) {
                        String dir_id = dirObj.optString("id");
                        String dir_video_id = dirObj.optString("video_id");
                        String dir_video_title = dirObj.optString("video_title");
                        String dir_create_time = dirObj.optString("create_time");
                        String dir_url = dirObj.optString("url");
                        String dir_video_sort = dirObj.optString("video_sort");
                        String dir_video_file_title = dirObj.optString("video_file_title");
                        Integer dir_length = dirObj.optInt("length");
                        String dir_download_url = dirObj.optString("download_url");
                        Log.e("whwh", "dir_download_url==" + dir_download_url);

                        if (!dir_download_url.equals("null") && dir_download_url.length() > 0) {
                            Log.e("whwh", "dir_download_url==isNotEmptyBankZero" + dir_download_url);
                            VedioDetailInfo.DirBean downBean = new VedioDetailInfo.DirBean();
                            downBean.setId(dir_id);
                            downBean.setVideo_id(dir_video_id);
                            downBean.setVideo_title(dir_video_title);
                            downBean.setCreate_time(dir_create_time);
                            downBean.setUrl(dir_url);
                            downBean.setVideo_sort(dir_video_sort);
                            downBean.setVideo_file_title(dir_video_file_title);
                            downBean.setLength(dir_length);
                            downBean.setDownload_url(dir_download_url);
                            downbeanLists.add(downBean);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return downbeanLists;
    }

    private List<VedioDetailInfo.RelBean> parseJsonRel(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray relArray = jsonObject.optJSONArray("rel");
            relbeansLists = new ArrayList<>();
            if (relArray != null && relArray.length() > 0) {
                for (int i = 0; i < relArray.length(); i++) {
                    JSONObject relObj = relArray.optJSONObject(i);
                    if (relArray != null) {
                        String rel_id = relObj.optString("id");
                        String rel_img = relObj.optString("img");
                        String rel_name = relObj.optString("name");
                        String rel_title = relObj.optString("title");
                        String rel_total_learn_nums = relObj.optString("total_learn_nums");
                        String rel_video_id = relObj.optString("video_id");

                        VedioDetailInfo.RelBean relBean = new VedioDetailInfo.RelBean();
                        relBean.setId(rel_id);
                        relBean.setImg(rel_img);
                        relBean.setName(rel_name);
                        relBean.setTitle(rel_title);
                        relBean.setTotal_learn_nums(rel_total_learn_nums);
                        relBean.setVideo_id(rel_video_id);
                        relbeansLists.add(relBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return relbeansLists;
    }

    private PopPay mPopPay;

    public void showPayPop() {
        mPopPay = new PopPay(this, info_title, payPrice + "");
        mPopPay.showAtLocation(findViewById(R.id.video_infos_activity), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
                if (payWay == WAY_WEICHAT) {
                    Toast.makeText(VideoInfosOldActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                    weichatPay();
                } else if (payWay == WAY_ALIPAY) {
                    Toast.makeText(VideoInfosOldActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                    aliPay();
                }
            }

            @Override
            public void onSelectCouponClick() {

            }
        });
    }

    private PopManyPay mPopManyPay;
    private Map<String, String> mPayMap = new HashMap<>();

    public void showManyPayPop() {
        mPopManyPay = new PopManyPay(this, info_title, payPrice, info_is_vip == 1 || payStatus == 1 ? 1 : 0);
        mPopManyPay.showAtLocation(findViewById(R.id.video_infos_activity), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        mPopManyPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mPopManyPay.setOnPayClickListener(new PopManyPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay, int payNum, int extraNum, int payForMeState, int payType) {
//                SharedPreferenceUtil.putIntValueByKey(MyApplication.mApplication,"extraNum",extraNum);
                isPayCourseOrAction = 0;
                isPayCourseType = type;
                Log.e("DH_PAY_PARAM", "extraNum:" + extraNum + (" payType:" + payType + " " + (payType == 0 ? "单人" : "多人  payForMeState:" + payForMeState + " " + (payForMeState == 0 ? "不为自己购买" : "为自己购买"))));
                mPayMap.put("goodsType", "3");
                mPayMap.put("id", course_info_id);
                mPayMap.put("user_id", user_id);
                mPayMap.put("coupon", "");
                mPayMap.put("pay_type", "4");
                mPayMap.put("buy_self", payForMeState + "");
                mPayMap.put("num", extraNum + "");
                mPayMap.put("multi_buy", payType + "");
                if (payWay == WAY_WEICHAT) {
                    Toast.makeText(VideoInfosOldActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                    weichatPay();
                } else if (payWay == WAY_ALIPAY) {
                    Toast.makeText(VideoInfosOldActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                    aliPay();
                }
                if (mPopManyPay != null && mPopManyPay.isShowing()) {
                    mPopManyPay.dismiss();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (timer0ne != null) {
            timer0ne.cancel();
            timer0ne = null;
        }
        if (timerTwo != null) {
            timerTwo.cancel();
            timerTwo = null;
        }
        if (mPopPay != null && mPopPay.isShowing()) {
            mPopPay.dismiss();
        }
    }


}
