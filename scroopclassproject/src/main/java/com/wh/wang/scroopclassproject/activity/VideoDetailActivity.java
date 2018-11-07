package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.alipay.OrderInfoUtil2_0;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.bean.CourseInfo;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.bean.ReadInfo;
import com.wh.wang.scroopclassproject.bean.SearchInfo;
import com.wh.wang.scroopclassproject.bean.SuperiorInfo;
import com.wh.wang.scroopclassproject.bean.VedioDetailInfo;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.views.CenterDialog;
import com.wh.wang.scroopclassproject.views.MyJCVideoPlayerStandard;
import com.wh.wang.scroopclassproject.wxapi.MD5;
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

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * 视频详情
 */
public class VideoDetailActivity extends AppCompatActivity implements CenterDialog.OnCenterItemClickListener {

    private MyJCVideoPlayerStandard myJCVideoPlayerStandard;
    private Intent intent;
    private Context mContext;
    private RelativeLayout rl_detail_detail;
    private TextView video_detail_tv_detail;
    private RelativeLayout rl_detail_cap;
    private TextView video_detail_tv_cap;
    private RelativeLayout detail_content_detail;
    private TextView tv_content_detail_title;
    private TextView tv_content_detail_money;
    private WebView video_webview_detail;
    private RelativeLayout detail_content_cap;
    private ListView detail_content_cap_list;
    private String url;
    private WebSettings webSettings;
    private List<VedioDetailInfo.InfoBean> infobeanLists;
    private List<VedioDetailInfo.DirBean> dirbeanLists;
    private List<VedioDetailInfo.RelBean> relbeansLists;
    private Context context;
    private int index;

    private CenterDialog centerDialog;

    //微信支付
    PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    Map<String, String> resultunifiedorder;
    StringBuffer sb;

    //支付宝支付
    public static final String APPID = "2017082208319834";
    public static final String PID = "2088021970397651";
    public static final String TARGET_ID = "";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCAI0vhmgTsAEHuJrWP1oxLCkQhl7HsqjLOTNDREdQB5c5GRXnMKTYEqU+UTKkB9X37Cq4eLi8gwxOKh8AygsNMWVncH7PQ6k5e5LXlLfVQetoBb3vNaNWVZslcrcwiG3D3l2NBsfiE7bvtqLMWDILPZUM/r6jjzuk+8n+PjAfY4/ivee3JV6CchqHdxk8iOzrgz1byab//NY8Aa8gx8FPVpNACmjQumeabcME8dNeCiEoKdMKLhqpw7UpViEPVLjHxoj3VbXE5Va1v8nZZ3E9P99ETGlzdhmVjJW0hdG0he7EfFhPFIdArFd1P+HVRu9SPCHhuiQ+dsg71zbC4x7+PAgMBAAECggEAArq9vwx81lobDWudqnazflW1pdGJZLUrZ+GTASEUFwZCIpUQRx0JnYfNvibz4qrMCDDuDI6HSnddTzIdEfZfGp08oMe8GNMk4DiW907HTvOO+UDarkFxa0FJQMQi+QcEnQGIh7m2xX4a5rEVP378ulY53H1tZ4zcww5/URt2f1Q0F+CEONymAjXS6giKftXvKnAdzNIa8H3SLAbzz3oXlBL2Of3DEJllICf604qmefMr2vLDaDxKAejA4aag2oU7r5mHCleaNQN+vbK8mZrTrC/h3pe1nFO8roh4dQLA1gN+XvGNqW+AkqUfsIqrvvvrYQK+yMQhKyGr3zRkrrgSQQKBgQDCFoafaYav9Ao5EpSN0zYS77yEnnicrg7Luq/yqjwH1ebpQHPHMtYhljac9Po0QpY0kHM3IhEAHnBkA4VIviPqRbQ1lJwSWL0oombESjZIZ8Snpg37ysk/fZ2x9gGCsJEuD1oKvc3p8yXuDBPKfoiLsge/TApGOA5D9oIxD2Lp+QKBgQCpAzMaxlmVLUjbP+y08/v8mHLeoD7vfn2QUf9ZHnYe21VRrZ0QIFtK+I3zOImr8SgInjO5zr3fd2Quf5R+LiFZH5qo6z6QpN4/of8rw3eUqHcAoNbLbXLgh22Caq1CUsf3PrGnp8GPRNRduNMCi2BUbdniTT0l9OPK1is+inKXxwKBgGLqCAIGTdYukWc0yPDey5grBfCwstdm/bNmmXUavS1hHiWR62+BwQ+s9nhXgYxQwyxW0eEvQ2rEGJNgF/VSsgsbKxSDgGb5Qq4K1kUDbVbJS7gFMOs6FgZ9hnjD31Ezm0nWGRWQVfj9Kj9/eDai9gP732bm4fWXa+3HG+LU7JxBAoGBAKeI6GuuGrbpQMM1RqrJAAyQAa31Iu28cTNSVNA+hdBoktZ4RqzpphVW9T8X2EB8uLZyO8v/x+9wJfqBtOyvRZdW4LR5HHe9NiHVr1/uTucpZ21UAhd80tcG5+LTZBPOgzHvD6OlgxVFoU2WHHT1O/P1QHo3Hlo1umgijJNeIeGZAoGALrJm2pMOvidnymLeQtFeo9UUDkjsRxse9RWOvz6sZUlqYUB8k2wqkhu+QeOw5U8cONhZ3uyrQknIdlRvNyO62HDqqnZNBSu8lTkrlfp//Ln8Ogqs8ynN9LRH727Ygxzk0ZIYyMEQIpZYKex56CVdYrrPW4hwS/7cVM9Ez0ju6X4=";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    //视频遮掩层
    private Button video_detial_zhao;

    private RelativeLayout video_detail_ll_collect;
    private ImageButton video_detail_ib_collect;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_detail);
        mContext = this;
        ActionBar LocalActionBar = getSupportActionBar();
        if (LocalActionBar == null)
            return;
        View LocalView = getLayoutInflater().inflate(R.layout.actionbar_layout, null);
        LocalActionBar.setCustomView(LocalView);
        LocalActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        LocalActionBar.setDisplayShowCustomEnabled(true);
        LocalActionBar.setDisplayHomeAsUpEnabled(true);
        LocalActionBar.setDisplayShowHomeEnabled(true);
        ActionBar.LayoutParams LocalLayoutParams = new ActionBar.LayoutParams(-2, -2);
        LocalLayoutParams.gravity = (0x1 | 0xFFFFFFF8 & LocalLayoutParams.gravity);
        LocalActionBar.setCustomView(LocalView, LocalLayoutParams);
        ((TextView) LocalActionBar.getCustomView().findViewById(R.id.title)).setText(getTitle());
        //微信支付
        req = new PayReq();
        sb = new StringBuffer();
        msgApi.registerApp(Constants.APP_ID);
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

        myJCVideoPlayerStandard = (MyJCVideoPlayerStandard) findViewById(R.id.jc_video_detail);
        rl_detail_detail = (RelativeLayout) findViewById(R.id.rl_detail_detail); //"详情" 标题栏
        video_detail_tv_detail = (TextView) findViewById(R.id.video_detail_tv_detail); //"详情"
        video_detail_tv_detail.setTextColor(this.getResources().getColor(R.color.main_color));

        //video_detail_ll_collect = (RelativeLayout) findViewById(R.id.video_detail_ll_collect); //收藏 父控件
        //video_detail_ib_collect = (ImageButton) findViewById(R.id.video_detail_ib_collect); //收藏 图
        //video_detail_tv_collect = (TextView) findViewById(R.id.video_detail_tv_collect);//收藏 字
        video_detail_ll_buy = (RelativeLayout) findViewById(R.id.video_detail_ll_buy); //购买课程
        video_detail_tv_buy = (TextView) findViewById(R.id.video_detail_tv_buy); //购买课程
        video_detail_tv_buy = (TextView) findViewById(R.id.video_detail_tv_buy); //购买课程

        video_detail_ll_vip = (RelativeLayout) findViewById(R.id.video_detail_ll_vip); //开通VIP
        video_detail_tv_vip = (TextView) findViewById(R.id.video_detail_tv_vip); //购买课程


        rl_detail_cap = (RelativeLayout) findViewById(R.id.rl_detail_cap); //"目录" 标题栏
        video_detail_tv_cap = (TextView) findViewById(R.id.video_detail_tv_cap); //"目录"
        video_detail_tv_cap.setTextColor(this.getResources().getColor(R.color.black));
        detail_content_detail = (RelativeLayout) findViewById(R.id.detail_content_detail);  //"详情" 内容
        tv_content_detail_title = (TextView) findViewById(R.id.tv_content_detail_title);  //详情 标题
        //tv_content_detail_money = (TextView) findViewById(R.id.tv_content_detail_money);   //"详情"的价格
        video_webview_detail = (WebView) findViewById(R.id.video_webview_detail);  //"详情"的H5

        video_detial_zhao = (Button) findViewById(R.id.video_detial_zhao); //视频屏幕遮掩层

        detail_content_cap = (RelativeLayout) findViewById(R.id.detail_content_cap);   //"目录" 内容
        detail_content_cap.setVisibility(View.GONE);
        detail_content_cap_list = (ListView) findViewById(R.id.detail_content_cap_list);  //"目录" 章节
        detail_content_cap_list.setSelection(0);

        centerDialog = new CenterDialog(VideoDetailActivity.this, R.layout.dialog_layout, new int[]{R.id.dialog_ll_cancel, R.id.dialog_ll_sure});
        centerDialog.setOnCenterItemClickListener(VideoDetailActivity.this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        intent = getIntent();
        index = intent.getIntExtra("index", 0);
        Log.e("whwh", "index==" + index);
        if (index == 1) {
            CourseInfo courseBean = (CourseInfo) getIntent().getSerializableExtra("courseBean");
            couseId = courseBean.getId();
        } else if (index == 2) {
            SuperiorInfo.SuperiorBean tempBean = (SuperiorInfo.SuperiorBean) getIntent().getSerializableExtra("tempbean");
            couseId = tempBean.getId();
            //课程详情的标题和价格
        } else if (index == 3) {
            MineInfo.CollectBean collectBean = (MineInfo.CollectBean) getIntent().getSerializableExtra("collectBean");
            couseId = collectBean.getId();
            //课程详情的标题和价格
        } else if (index == 4) {
            MineInfo.OrderBean orderBean = (MineInfo.OrderBean) getIntent().getSerializableExtra("orderBean");
            couseId = orderBean.getId();
            //课程详情的标题和价格
        } else if (index == 5) {
            MineInfo.StudyBean studyBean = (MineInfo.StudyBean) getIntent().getSerializableExtra("studyBean");
            couseId = studyBean.getId();
            //课程详情的标题和价格
        } else if (index == 6) {

            MineInfo.StudyBean finishBean = (MineInfo.StudyBean) getIntent().getSerializableExtra("finishBean");
            couseId = finishBean.getId();
            //课程详情的标题和价格
        } else if (index == 7) {
            SuperiorInfo.BannerBean bannerBean = (SuperiorInfo.BannerBean) getIntent().getSerializableExtra("bannerBean");
            couseId = bannerBean.getProduct_id();
            //课程详情的标题和价格
        } else if (index == 8) {
            ReadInfo.BannerBean readbannerBean = (ReadInfo.BannerBean) getIntent().getSerializableExtra("readbannerBean");
            couseId = readbannerBean.getProduct_id();
        } else if (index == 9) {
            SearchInfo.VideoBean videoBean = (SearchInfo.VideoBean) getIntent().getSerializableExtra("videoBean");
            couseId = videoBean.getId();
        }
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        login_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
        getCourseDetailDataFromNet(user_id, couseId);  //网络请求 加载视频详情信息
        setWebviewData(couseId);  //设置H5页面
    }

    /**
     * 根据id 获取视频网址
     */
    private void getCourseDetailDataFromNet(String user_id, String id) {
        //联网
        //视频内容
        RequestParams params = new RequestParams(Constants.VideoDetailUrl);
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getCourseDetailDataFromNet---联网成功---result===" + result);
                //主线程
                processVedioDetailData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getCourseDetailDataFromNet---联网失败---" + ex.getMessage());
                ToastUtils.showToast(VideoDetailActivity.this, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getCourseDetailDataFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getCourseDetailDataFromNet---onFinished---");
            }
        });

    }

    /**
     * 视频详情数据解析
     */
    private void processVedioDetailData(String result) {
        infobeanLists = parseJsonInfo(result);
        dirbeanLists = parseJsonDir(result);
        relbeansLists = parseJsonRel(result);
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
        Log.e("whwh", "11111----info_collect_status===" + info_collect_status);
        String info_id = infobeanLists.get(0).getId();
        String info_img_url = infobeanLists.get(0).getImg();
        Log.e("whwh", "info_img_url===" + info_img_url);
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
        //tv_content_detail_title.setVisibility(View.GONE);
        dir_veido_url = dirbeanLists.get(0).getUrl();
        if (StringUtils.isEmpty(dir_veido_url)) {
            dir_veido_url = dirbeanLists.get(1).getUrl();
        }
        Log.e("whwh", "dir_veido_url===" + dir_veido_url);
        dir_video_file_title = dirbeanLists.get(0).getVideo_file_title();


        //--------------------------------------页面价格处理-----------------------------------------
        payPrice = Double.parseDouble(info_new_price);
        //微信生成预订单
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
        if (info_is_vip == 0) {
            if (payPrice == 0.00) {
                Log.e("whwh", "非会员,免费");
                tv_content_detail_money.setText("免费");
                //tv_content_detail_money.setVisibility(View.GONE);
                video_detail_ll_buy.setClickable(false);
                video_detail_tv_buy.setText("免费学习");
            } else {
                Log.e("whwh", "非会员,花钱");
                tv_content_detail_money.setText("¥  " + info_new_price);
                //tv_content_detail_money.setVisibility(View.GONE);
                if (payStatus == 1) {
                    Log.e("whwh", "非会员,已经购买了");
                    video_detail_ll_buy.setClickable(false);
                    video_detail_tv_buy.setText("已经购买");
                } else {
                    Log.e("whwh", "非会员,没有购买了");
                    video_detail_ll_buy.setClickable(true);
                    video_detail_tv_buy.setText("立即购买");
                }
            }
        } else {
            if (payPrice == 0.00) {
                Log.e("whwh", "会员,免费");
                tv_content_detail_money.setText("免费");
                //tv_content_detail_money.setVisibility(View.GONE);
                video_detail_ll_buy.setClickable(false);
                video_detail_tv_buy.setText("免费学习");
            } else {
                Log.e("whwh", "会员,花钱");
                tv_content_detail_money.setText("免费");
                //tv_content_detail_money.setVisibility(View.GONE);
                video_detail_ll_buy.setClickable(false);
                video_detail_tv_buy.setText("免费学习");
            }
        }

        //--------------------------------------视频处理---------------------------------------------
        myJCVideoPlayerStandard.setUp(dir_veido_url
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, dir_video_file_title);

        Picasso.with(this)
                .load(info_img_url)
                .into(myJCVideoPlayerStandard.thumbImageView);
        //JCVideoPlayer.setJcUserAction(new MyUserActionStandard());

        if (payPrice == 0.00) {
            video_detial_zhao.setVisibility(View.GONE); //价格为0 直接可以看

        } else if (info_is_vip == 1) {

            video_detial_zhao.setVisibility(View.GONE); //如果为VIP时直接可以看

        } else {

            if (payStatus == 1) { //支付成功 可看

                video_detial_zhao.setVisibility(View.GONE);
            } else {

                video_detial_zhao.setVisibility(View.VISIBLE);
            }
        }
        //--------------------------------------收藏处理---------------------------------------------
        if (StringUtils.isNotEmpty(infobeanLists.get(0).getCollect_status())) {
            Log.e("whwhhw", "收藏收藏");
            video_detail_ib_collect.setBackgroundResource(R.drawable.sc_press);
            //video_detail_tv_collect.setText("已收藏");
        } else {
            Log.e("whwhhw", "没有收藏--没有收藏");
            video_detail_ib_collect.setBackgroundResource(R.drawable.sc_normal);
            //video_detail_tv_collect.setText("未收藏");
        }

        //-----------------------------------------vip处理------------------------------------------
        if (info_is_vip == 1) {
            video_detail_ll_vip.setClickable(false);
            video_detail_tv_vip.setText("已是会员");
        } else {
            video_detail_ll_vip.setClickable(true);
            video_detail_tv_vip.setText("开通会员");
        }

        //setCapData();

        //-----------------------------------视频详情和目录处理---------------------------------------

        //设置监听事件
        rl_detail_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video_detail_tv_detail.setTextColor(getResources().getColor(R.color.main_color));
                video_detail_tv_cap.setTextColor(getResources().getColor(R.color.black));
                detail_content_detail.setVisibility(View.VISIBLE);
                tv_content_detail_title.setVisibility(View.VISIBLE);
                tv_content_detail_money.setVisibility(View.VISIBLE);
                video_webview_detail.setVisibility(View.VISIBLE);
                detail_content_cap.setVisibility(View.GONE);
            }
        });


        rl_detail_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCapData();
            }
        });
    }

    private void setCapData() {

        video_detail_tv_detail.setTextColor(getResources().getColor(R.color.black));
        video_detail_tv_cap.setTextColor(getResources().getColor(R.color.main_color));
        detail_content_detail.setVisibility(View.GONE);
        detail_content_cap.setVisibility(View.VISIBLE);

        //设置适配器
        VideoPlayAdapter videoAdapter = new VideoPlayAdapter(mContext);
        detail_content_cap_list.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
    }


    //TODO 监听事件
    private void initListener() {

        //video_detail_ll_collect video_detail_ib_collect video_detail_tv_collect
        //video_detail_ll_buy video_detail_ll_vip


        //收藏功能
        video_detail_ib_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNotEmpty(user_id) && info_rand_str.equals(login_rand_str)
                        && StringUtils.isNotEmpty(info_rand_str) && StringUtils.isNotEmpty(login_rand_str)) {
                    Log.e("whwh", "info_rand_str==++" + info_rand_str);
                    Log.e("whwh", "login_rand_str==++" + login_rand_str);
                    Log.e("whwh", "user_id==++" + user_id);
                    if (StringUtils.isNotEmpty(infobeanLists.get(0).getCollect_status())) {
                        setLoveDataFromNet(couseId, user_id, "1");
                    } else {
                        setLoveDataFromNet(couseId, user_id, "1");
                    }

                } else {
                    Intent intent = new Intent(VideoDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        //支付功能
        video_detail_ll_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(user_id) && info_rand_str.equals(login_rand_str)
                        && StringUtils.isNotEmpty(info_rand_str) && StringUtils.isNotEmpty(login_rand_str)) {
                    Log.e("whwh", "info_rand_str==++" + info_rand_str);
                    Log.e("whwh", "login_rand_str==++" + login_rand_str);
                    Log.e("whwh", "user_id==++" + user_id);
                    if (payPrice == 0.00) {
                        ToastUtils.showToast(VideoDetailActivity.this, "免费课程,无需付费!");
                    } else if (payStatus == 1) {
                        ToastUtils.showToast(VideoDetailActivity.this, "已经付费了!");
                    } else if (info_is_vip == 1) {
                        ToastUtils.showToast(VideoDetailActivity.this, "VIP会员,免费学习!");
                    } else {
                        centerDialog.show();
                        TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
                        TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
                        detail_dialog_price.setText("¥ " + payPrice);
                        detail_dialog_title.setText(info_title);
                    }


                } else {
                    Intent intent = new Intent(VideoDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        video_detail_ll_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(user_id) && info_rand_str.equals(login_rand_str)
                        && StringUtils.isNotEmpty(info_rand_str) && StringUtils.isNotEmpty(login_rand_str)) {
                    Log.e("whwh", "info_rand_str==++" + info_rand_str);
                    Log.e("whwh", "login_rand_str==++" + login_rand_str);
                    Log.e("whwh", "user_id==++" + user_id);
                    if (info_is_vip == 1) {
                        video_detail_ll_vip.setClickable(false);
                        video_detail_tv_vip.setText("已是会员");
                    } else {
                        video_detail_ll_vip.setClickable(true);
                        video_detail_tv_vip.setText("开通会员");
                        VedioDetailInfo.InfoBean vedioDetailInfo = infobeanLists.get(0);
                        Intent intent = new Intent(VideoDetailActivity.this, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",vedioDetailInfo.getId());
                        startActivity(intent);

                    }
                } else {
                    Intent intent = new Intent(VideoDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        //屏幕按钮点击
        //TODO WHWH
        video_detial_zhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(user_id) && info_rand_str.equals(login_rand_str)
                        && StringUtils.isNotEmpty(info_rand_str) && StringUtils.isNotEmpty(login_rand_str)) {
                    Log.e("whwh", "info_rand_str==++" + info_rand_str);
                    Log.e("whwh", "login_rand_str==++" + login_rand_str);
                    Log.e("whwh", "user_id==++" + user_id);
                    centerDialog.show();
                    TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
                    TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
                    detail_dialog_price.setText("¥ " + payPrice);
                    detail_dialog_title.setText(info_title);
                } else {
                    Intent intent = new Intent(VideoDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.dialog_ll_cancel:
                //TODO WH
                aliPay();
                break;

            case R.id.dialog_ll_sure:
                weichatPay();
                break;
            default:
                break;
        }
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
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(VideoDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //TODO WHWH
                        video_detial_zhao.setVisibility(View.GONE);
                        requestAliPayZhifuState(2);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(VideoDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
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
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0); //payPrice
        String outTradeNo = OrderInfoUtil2_0.getOutTradeNo();
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, payPrice, info_title + "视频课程费用", info_title, outTradeNo, rsa2);
        //添加数据
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(VideoDetailActivity.this);
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


    /**
     * 微信支付
     */
    private void weichatPay() {
        genPayReq();
        msgApi.sendReq(req);
    }

    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
        sb.append("sign\n" + req.sign + "\n\n");
        //show.setText(sb.toString());
        Log.e("orion", signParams.toString());

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
            packageParams.add(new BasicNameValuePair("notify_url", "http://php.shaoziketang.com/callback/weChat"));
            //"201709130818195886348"
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "106.120.116.198"));
            BigDecimal totalFeeBig = new BigDecimal(payPrice);
            int totalFee = totalFeeBig.multiply(new BigDecimal(100)).intValue();
            //String.valueOf(totalFee)
            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(totalFee)));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));


            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));


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
        if (num == 2) {
            params.addBodyParameter("order_wz", "2");
        } else if (num == 1) {
            params.addBodyParameter("order_wz", "1");
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
                video_detail_tv_buy.setText("购买成功");
                video_detail_ll_buy.setClickable(false);

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
                Log.e("whwh", "setLoveDataFromNet---联网成功---result===" + result);
                dealResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "setLoveDataFromNet---联网失败---" + ex.getMessage());
                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "setLoveDataFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "setLoveDataFromNet---onFinished---");
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
                video_detail_ib_collect.setBackgroundResource(R.drawable.sc_press);
                // video_detail_tv_collect.setText("已收藏");
            } else if (err == -1) {
                ToastUtils.showToast(this, "取消收藏!");
                video_detail_ib_collect.setBackgroundResource(R.drawable.sc_normal);
                //video_detail_tv_collect.setText("未收藏");
            } else {
                ToastUtils.showToast(this, "收藏失败!");
                video_detail_ib_collect.setBackgroundResource(R.drawable.sc_normal);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载H5页面
     */
    private void setWebviewData(String id) {
        url = "http://121.40.248.175/szkadmin/?c=welcome&m=appdetail&type=1&id=" + id;
        //设置支持javaScript
        webSettings = video_webview_detail.getSettings();
        //设置支持javaScript
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //设置文字大小
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setTextZoom(120);
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
        JCVideoPlayer.releaseAllVideos();
        Constants.weixinFlag = 10;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("whwh", "Constants.weixinFlag" + Constants.weixinFlag);
        if (Constants.weixinFlag == 0) {
            //TODO WHWH
            video_detial_zhao.setVisibility(View.GONE);
            requestAliPayZhifuState(1);
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
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
            if (StringUtils.isEmpty(dirUrl)) {
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
                typeTwoViewHolder.video_item_two_tv.setText(dirbeanLists.get(position).getVideo_file_title());

                final int pos = position;
                typeTwoViewHolder.video_detail_item_rl_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VedioDetailInfo.DirBean dirbean = dirbeanLists.get(pos);
                        String info_imgUrl = infobeanLists.get(0).getImg();
                        String videoUrl = dirbean.getUrl();
                        String Video_file_title = dirbean.getVideo_file_title();
                        //TODO WHWH

                        myJCVideoPlayerStandard.setUp(videoUrl
                                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, Video_file_title);

                        Picasso.with(context)
                                .load(info_imgUrl)
                                .into(myJCVideoPlayerStandard.thumbImageView);

                        // JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
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
        }

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
                        String info_id = infoObj.optString("id");
                        String info_img = infoObj.optString("img");
                        String info_ios_integral = infoObj.optString("ios_integral");
                        int info_is_vip = infoObj.optInt("is_vip");
                        String info_learn = infoObj.optString("learn");
                        String info_name = infoObj.optString("name");
                        String info_new_price = infoObj.optString("new_price");
                        String info_old_price = infoObj.optString("old_price");
                        String info_pay_status = infoObj.optString("pay_status");
                        String info_rand_str = infoObj.optString("rand_str");
                        String info_relative_course = infoObj.optString("relative_course");
                        String info_title = infoObj.optString("title");
                        String info_vip_id = infoObj.optString("vip_id");
                        String info_vip_price = infoObj.optString("vip_price");

                        VedioDetailInfo.InfoBean infoBean = new VedioDetailInfo.InfoBean();
                        infoBean.setCollect_status(info_collect_status);
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
                        infoBean.setTitle(info_title);
                        infoBean.setVip_id(info_vip_id);
                        infoBean.setVip_price(info_vip_price);
                        infobeanLists.add(infoBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return infobeanLists;
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
                        String dir_create_time = dirObj.optString("create_time");
                        String dir_id = dirObj.optString("id");
                        String dir_url = dirObj.optString("url");
                        String dir_video_file_title = dirObj.optString("video_file_title");
                        String dir_video_id = dirObj.optString("video_id");
                        String dir_video_sort = dirObj.optString("video_sort");
                        String dir_video_title = dirObj.optString("video_title");

                        VedioDetailInfo.DirBean dirBean = new VedioDetailInfo.DirBean();
                        dirBean.setCreate_time(dir_create_time);
                        dirBean.setId(dir_id);
                        dirBean.setUrl(dir_url);
                        dirBean.setVideo_file_title(dir_video_file_title);
                        dirBean.setVideo_id(dir_video_id);
                        dirBean.setVideo_sort(dir_video_sort);
                        dirBean.setVideo_title(dir_video_title);
                        dirbeanLists.add(dirBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dirbeanLists;
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
}
