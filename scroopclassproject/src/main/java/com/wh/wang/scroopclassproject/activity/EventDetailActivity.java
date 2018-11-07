package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.EventDetailInfos;
import com.wh.wang.scroopclassproject.bean.JXKBean;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.bean.MineThirdBean;
import com.wh.wang.scroopclassproject.bean.ReadInfo;
import com.wh.wang.scroopclassproject.bean.VedioDetailInfo;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.views.MultiStateView;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;


/**
 * 报名详情页面
 * wang
 */
public class EventDetailActivity extends Activity {

    private Context context;
    private TextView event_detail_title;
    private TextView event_detail_host;
    private TextView event_detail_date;
    private TextView event_detail_place;
    private TextView event_detail_money;
    private WebView event_detail_webview;
    private ProgressBar event_detail_pb_loading;
    private RelativeLayout event_detail_rl_tel;
    private RelativeLayout event_detail_rl_have;
    private Intent intent;
    private EventDetailInfos.InfoBean eventDetailBean;
    private String url;
    private WebSettings webSettings;
    private TextView right_now_have;
    private String user_id;
    private int eindex;
    private String login_rand_str;
    private String eventRandStrs;
    private MultiStateView mStateView;
    private ImageView titlebarbackss_share;
    private Dialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.event_detail, "报名详情");
        context = this;
        //eventdes
        MobclickAgent.onEvent(context, "eventdes");
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        Constants.wx_api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
        initView();
        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (Constants.eventFlag == 200) {
            right_now_have.setText("已报名");
            event_detail_rl_have.setClickable(false);
            event_detail_rl_have.setBackgroundColor(Color.parseColor("#555555"));
            Constants.eventFlag = 0;
        } else if (Constants.eventFlag == 400) {
            right_now_have.setText("立即报名");
            event_detail_rl_have.setClickable(true);
            event_detail_rl_have.setBackgroundColor(Color.parseColor("#85BC44"));
            Constants.eventFlag = 0;
        }

        user_id = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        login_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
        //eventRandStrs = eventDetailBean.getRand_str();
        if (eventDetailBean != null) {
            eventRandStrs = eventDetailBean.getRand_str();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mStateView = (MultiStateView) findViewById(R.id.event_detail_stateview);
        titlebarbackss_share = (ImageView) findViewById(R.id.titlebarbackss_share);
        event_detail_title = (TextView) findViewById(R.id.event_detail_title);
        event_detail_host = (TextView) findViewById(R.id.event_detail_host);
        event_detail_date = (TextView) findViewById(R.id.event_detail_date);
        event_detail_place = (TextView) findViewById(R.id.event_detail_place);
        event_detail_money = (TextView) findViewById(R.id.event_detail_money);
        event_detail_webview = (WebView) findViewById(R.id.event_detail_webview);
        event_detail_pb_loading = (ProgressBar) findViewById(R.id.event_detail_pb_loading);
        event_detail_rl_tel = (RelativeLayout) findViewById(R.id.event_detail_rl_tel);
        event_detail_rl_have = (RelativeLayout) findViewById(R.id.event_detail_rl_have);
        right_now_have = (TextView) findViewById(R.id.right_now_have);

        event_detail_rl_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                        .parse("tel:" + "400-900-3650"));
                startActivity(intentNO);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        login_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
        intent = getIntent();
        eindex = intent.getIntExtra("eindex", 0);
        if (eindex == 1) {
            //精品课程的报名
            JXKBean.EventsBean eventBean = (JXKBean.EventsBean) getIntent().getSerializableExtra("eventBean");
            String event_id = eventBean.getId();
            getDataFromNet(event_id, user_id);
        } else if (eindex == 2) {
            VedioDetailInfo.InfoBean vedioDetailInfo = (VedioDetailInfo.InfoBean) getIntent().getSerializableExtra("vedioDetailInfo");
            String vip_id = vedioDetailInfo.getVip_id();
            getDataFromNet(vip_id, user_id);
        } else if (eindex == 3) {
            JXKBean.ScrollBean bannerBean = (JXKBean.ScrollBean) getIntent().getSerializableExtra("bannerBean");
            String banner_id = bannerBean.getProduct_id();
            getDataFromNet(banner_id, user_id);
        } else if (eindex == 4) {
            ReadInfo.BannerBean readbannerBean = (ReadInfo.BannerBean) getIntent().getSerializableExtra("readbannerBean");
            String banner_id = readbannerBean.getProduct_id();
            getDataFromNet(banner_id, user_id);
        } else if (eindex == 5) {
            MineInfo.OrderBean orderBean = (MineInfo.OrderBean) getIntent().getSerializableExtra("orderBean");
            String order_id = orderBean.getId();
            getDataFromNet(order_id, user_id);
        } else if (eindex == 6) {
            MineThirdBean.ListBean listBean = (MineThirdBean.ListBean) getIntent().getSerializableExtra("listBean");
            String order_id = listBean.getItem_id();
            getDataFromNet(order_id, user_id);
        } else if (eindex == 99) {
            String web_id = intent.getStringExtra("web_id");
            getDataFromNet(web_id, user_id);
        }else {
            String order_id = intent.getStringExtra("signUpDetailId");
            Log.e("DH_VIP_ID",order_id);
            getDataFromNet(order_id, user_id);
        }
    }

    private void getDataFromNet(String event_id, String user_id) {

        mStateView.setViewState(MultiStateView.ViewState.LOADING);
        //联网
        //视频内容
        RequestParams params = new RequestParams(Constants.eventDetailUrl);
        params.addBodyParameter("id", event_id);
        params.addBodyParameter("user_id", user_id);
        String timeStr = StringUtils.getTimestamp();
        params.addBodyParameter("shijian", timeStr);
        params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "EventDetailActivity---联网成功---result===" + result);
                //主线程
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "EventDetailActivity---联网失败---" + ex.getMessage());
                mStateView.setViewState(MultiStateView.ViewState.ERROR);
                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "EventDetailActivity---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "EventDetailActivity---onFinished---");
            }
        });

    }

    //处理数据
    private void processData(String result) {
        eventDetailBean = parseJsonInfo(result);
        mStateView.setViewState(MultiStateView.ViewState.CONTENT);
        setData();
    }

    /**
     * 设置数据
     */
    private void setData() {
        event_detail_title.setText(eventDetailBean.getTitle());
        event_detail_host.setText("主办方:  " + eventDetailBean.getSpsonsor());
        if (eventDetailBean.getIsshow() == 1) {
            event_detail_date.setText("时间:  " + eventDetailBean.getStart_shijian());
            event_detail_place.setText("地址: " + eventDetailBean.getAddress());
        } else if (eventDetailBean.getIsshow() == 0) {
            event_detail_date.setVisibility(View.GONE);
            event_detail_place.setVisibility(View.GONE);
        }
        if (eventDetailBean.getIsvip() == 1) {
            double vipPrice = eventDetailBean.getVip_price();
            if (vipPrice == 0.0) {
                event_detail_money.setText("票价:   免费");
            } else {
                event_detail_money.setText("会员票价:   ¥" + vipPrice);
            }

        } else {
            double normalPrice = eventDetailBean.getPrice();
            if (normalPrice == 0.0) {
                event_detail_money.setText("票价:   免费");
            } else {
                event_detail_money.setText("票价:   ¥" + normalPrice);
            }
        }

        eventRandStrs = eventDetailBean.getRand_str();
        Log.e("whwh", "eventRandStrs==" + eventRandStrs); //用户设备唯一

        setWebview(eventDetailBean.getId() + "");
        setIsJoinIn();
    }

    private void setIsJoinIn() {

        //分享
        titlebarbackss_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog = new Dialog(EventDetailActivity.this, R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                View inflate = LayoutInflater.from(EventDetailActivity.this).inflate(R.layout.share_dialog, null);
                //初始化控件
                RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
                RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
                RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
                share_rlayout_py.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                        //msgApi
                        ShareUtil.weiChat(Constants.wx_api, 7,
                                EventDetailActivity.this, eventDetailBean.getShare_url()+"?helper="+user_id,
                                eventDetailBean.getTitle(), eventDetailBean.getStart_shijian());
                    }
                });

                share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                        ShareUtil.weiChat(Constants.wx_api, 8,
                                EventDetailActivity.this, eventDetailBean.getShare_url()+"?helper="+user_id,
                                eventDetailBean.getTitle(), eventDetailBean.getStart_shijian());
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

        int isbao = eventDetailBean.getIsbao();
        if (isbao == 1){ //可以报名
            int status = eventDetailBean.getStatus(); //1 已支付,2未支付,3支付中,4已退款,0未报名
            Log.e("whwh", "status===>" + status);
            if (status == 1) {
                right_now_have.setText("已报名");
                event_detail_rl_have.setClickable(false);
                event_detail_rl_have.setBackgroundColor(Color.parseColor("#555555"));
//                int isRepeat = eventDetailBean.getIsrepeat();
//                if (isRepeat == 1) {
//                    //ok
//                    int event_power = eventDetailBean.getEvent_power();
//                    if (event_power == 0) {
//                        //ok
//                        right_now_have.setText("立即报名");
//                        setListener();
//                    } else {
//                        //目前ok
//                        right_now_have.setText("立即报名");
//                        setListener();
//                    }
//
//                } else {
//                    right_now_have.setText("报名已结束");
//                }

            } else {
                Log.e("whwh", "2222");
                int event_power = eventDetailBean.getEvent_power();
                if (event_power == 0) {
                    //ok
                    right_now_have.setText("立即报名");
                    setListener();
                } else {
                    //目前ok
                    right_now_have.setText("立即报名");
                    setListener();
                }
            }

        } else if (isbao == 0){ //不可以报名
            right_now_have.setText("报名已结束");
        }

    }


    //报名点击
    private void setListener() {
        event_detail_rl_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(user_id)) {

                    //报名订单
                    Intent intent = new Intent(EventDetailActivity.this, EventFromActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("eventDetailBean", (Serializable) eventDetailBean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(EventDetailActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setWebview(String event_id) {
        //url = "http://121.40.248.175/szkadmin/?c=welcome&m=appdetail&type=3&id=" + event_id;
        url = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=3&id=" + event_id;
        //设置支持javaScript
        webSettings = event_detail_webview.getSettings();
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
        event_detail_webview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                event_detail_pb_loading.setVisibility(View.GONE);
            }
        });

        event_detail_webview.loadUrl(url);
    }

    private EventDetailInfos.InfoBean parseJsonInfo(String json) {
        try {

            JSONObject jsonObject = new JSONObject(json);
            eventDetailBean = new EventDetailInfos.InfoBean();
            int event_detail_status = jsonObject.optInt("status");//
            Log.e("whwh", "event_status=" + event_detail_status);
            JSONObject eventDetailObj = new JSONObject(json).getJSONObject("info");
            String event_detail_address = eventDetailObj.optString("address");
            int event_event_power = eventDetailObj.optInt("event_power");
            int event_detail_id = eventDetailObj.optInt("id");//
            int event_detail_isbao = eventDetailObj.optInt("isbao");
            int event_detail_isrepeat = eventDetailObj.optInt("isrepeat");
            int event_detail_isshow = eventDetailObj.optInt("isshow");
            int event_detail_islogin = eventDetailObj.optInt("is_login");
            int event_detail_isvip = eventDetailObj.optInt("is_vip");
            double event_detail_price = eventDetailObj.optDouble("price");
            String event_detail_spsonsor = eventDetailObj.optString("spsonsor");
            String event_detail_start_shijian = eventDetailObj.optString("start_shijian");
            String event_detail_ticket_id = eventDetailObj.optString("ticket_id");
            String event_detail_title = eventDetailObj.optString("title");
            double event_detail_vip_price = eventDetailObj.optDouble("vip_price");
            String event_detail_rand_str = eventDetailObj.optString("rand_str");
            String event_detail_share_url = eventDetailObj.optString("share_url");
            String event_detail_ios_integral = eventDetailObj.optString("ios_integral");
            eventDetailBean.setStatus(event_detail_status);
            eventDetailBean.setAddress(event_detail_address);
            eventDetailBean.setEvent_power(event_event_power);
            eventDetailBean.setId(event_detail_id);
            eventDetailBean.setIsbao(event_detail_isbao);
            eventDetailBean.setIsrepeat(event_detail_isrepeat);
            eventDetailBean.setIsshow(event_detail_isshow);
            eventDetailBean.setIs_login(event_detail_islogin);
            eventDetailBean.setIsvip(event_detail_isvip);
            eventDetailBean.setPrice(event_detail_price);
            eventDetailBean.setSpsonsor(event_detail_spsonsor);
            eventDetailBean.setStart_shijian(event_detail_start_shijian);
            eventDetailBean.setTicket_id(event_detail_ticket_id);
            eventDetailBean.setTitle(event_detail_title);
            eventDetailBean.setVip_price(event_detail_vip_price);
            eventDetailBean.setRand_str(event_detail_rand_str);
            eventDetailBean.setShare_url(event_detail_share_url);
            eventDetailBean.setIos_integral(event_detail_ios_integral);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventDetailBean;
    }
}
