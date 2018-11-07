package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity.VipListActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CustomerEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CustomerPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EventDetailsPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.view.OldPriceTextView;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;


import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isShareInform;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;

public class NewEventDetailsActivity extends Activity implements View.OnClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private ImageView mTitlebarbackssShare;
    private TextView mEventDetailTitle;
    private TextView mEventDetailHost;
    private TextView mEventDetailDate;
    private TextView mEventDetailPlace;
    private TextView mVipPrice;
    private OldPriceTextView mOldPrice;
    private TextView mVipImme;
    private WebView mEventDetailWb;
    private TextView mApply;
    private TextView mVipAisle;
    private ScrollView mEventScroll;
    private EventDetailsPresenter mEventDetailsPresenter = new EventDetailsPresenter();
    private CustomerPresenter mCustomerPresenter = new CustomerPresenter();
    private String mUserId;
    private String mEventId;
    private LinearLayout mEventDetailBottom;
    private WebSettings mSettings;
    private String mEventRandStr;
    private String mLoginRandStr;
    private LinearLayout mEventDetailDateRel;
    private LinearLayout mEventDetailAddressRel;
    private ImageView mPhone;
    private TextView mKefu;

    private String mSub_price;
    private double mSubPrice = 0;

    private double mPayPrice = 0;
    private DialogUtils mDialogUtils;
    private CustomerEntity.InfoBean mCustomerInfo;
    private String mIsplay;
    private String mWechat_number;
    private String mCustomerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_details);
        initView();
        initIntent();
        initOther();
        initBaseData();
        initListener();
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssShare = (ImageView) findViewById(R.id.titlebarbackss_share);
        mEventDetailTitle = (TextView) findViewById(R.id.event_detail_title);
        mEventDetailHost = (TextView) findViewById(R.id.event_detail_host);
        mEventDetailDate = (TextView) findViewById(R.id.event_detail_date);
        mEventDetailPlace = (TextView) findViewById(R.id.event_detail_place);
        mVipPrice = (TextView) findViewById(R.id.event_detail_vip_money);
        mOldPrice = (OldPriceTextView) findViewById(R.id.old_price);
        mEventDetailWb = (WebView) findViewById(R.id.event_detail_wb);
        mApply = (TextView) findViewById(R.id.apply);
        mVipImme = (TextView) findViewById(R.id.vip_imme);
        mVipAisle = (TextView) findViewById(R.id.vip_aisle);
        mEventDetailDateRel = (LinearLayout) findViewById(R.id.event_detail_date_rel);
        mEventDetailAddressRel = (LinearLayout) findViewById(R.id.event_detail_address_rel);
        mPhone = (ImageView) findViewById(R.id.phone);
        mEventDetailBottom = (LinearLayout) findViewById(R.id.event_detail_bottom);
        mEventScroll = (ScrollView) findViewById(R.id.event_scroll);
        mKefu = (TextView) findViewById(R.id.kefu);


    }


    private void initIntent() {
        mEventId = getIntent().getStringExtra("event_id") == null ? "" : getIntent().getStringExtra("event_id");
//        mTemporaryId = mEventId;
        setWebview(mEventId);
    }

    private void initOther() {
        MobclickAgent.onEvent(this, "eventdes");
        Constants.wx_api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
    }


    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mTitlebarbackssShare.setOnClickListener(this);
        mApply.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mKefu.setOnClickListener(this);
    }

    private void initBaseData() {
        mTitlebarbackssName.setText("报名详情");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
        getEventDetails();
    }

    private EventDetailEntity mEventDetailEntity;
    private EventDetailEntity.InfoBean mEventInfo;

    public void getEventDetails() {
        if (StringUtils.isNotEmpty(mUserId)) {
            mCustomerPresenter.getCustomerInfo(mUserId, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    CustomerEntity customerEntity = (CustomerEntity) value[0];
                    mCustomerInfo = customerEntity.getInfo();
                    if (mCustomerInfo!=null) {
                        mIsplay = mCustomerInfo.getIsplay();
                    }
                }

                @Override
                public void onFault(String error) {

                }
            });
        }


        mEventDetailsPresenter.getEventDetails(mEventId, mUserId, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                mEventDetailEntity = (EventDetailEntity) value[0];
                if (mEventDetailEntity.getInfo() != null) {
                    mEventDetailBottom.setVisibility(View.VISIBLE);
                    mEventInfo = mEventDetailEntity.getInfo();
                    setEventInfo(mEventInfo);
                } else {
                    mEventDetailBottom.setVisibility(View.GONE);
                    Toast.makeText(NewEventDetailsActivity.this, "数据请求异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_WHAT", error);
                Toast.makeText(NewEventDetailsActivity.this, "数据请求异常", Toast.LENGTH_SHORT).show();
                mEventDetailBottom.setVisibility(View.GONE);
            }
        });
    }

    private void setEventInfo(EventDetailEntity.InfoBean infoBean) {
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "is_vip", infoBean.getIs_vip() + "");
        mEventDetailTitle.setText(infoBean.getTitle());
        if (infoBean.getSpsonsor() == null || "".equals(infoBean.getSpsonsor())) {
            mEventDetailHost.setVisibility(View.GONE);
        } else {
            mEventDetailHost.setText("主办方:  " + infoBean.getSpsonsor());
        }

        double vip_price = 0;
        double price = 0;
        double real_price = 0;
        try {
            vip_price = Double.parseDouble(infoBean.getVip_price());
            price = Double.parseDouble(infoBean.getPrice());
            real_price = Double.parseDouble(infoBean.getReal_price());
        } catch (Exception e) {
            Toast.makeText(this, "数据出错", Toast.LENGTH_SHORT).show();
            return;
        }
        mSub_price = infoBean.getSub_price();
        try {
            mSubPrice = Double.parseDouble(mSub_price);
        } catch (Exception e) {
            mSubPrice = 0;
        }
        if ("1".equals(infoBean.getIsshow())) {
            if (infoBean.getStart_shijian() == null || "".equals(infoBean.getStart_shijian())) {
                mEventDetailDateRel.setVisibility(View.GONE);
            } else {
                mEventDetailDate.setText("时  间:  " + infoBean.getStart_shijian());
            }
            if (infoBean.getAddress() == null || "".equals(infoBean.getAddress())) {
                mEventDetailAddressRel.setVisibility(View.GONE);
            } else {
                mEventDetailPlace.setText("地  址:  " + infoBean.getAddress());
            }
        } else {
            mEventDetailDateRel.setVisibility(View.GONE);
            mEventDetailAddressRel.setVisibility(View.GONE);
        }
        /**
         * 判别会员非会员UI
         */
        if ("997".equals(mEventId)) { //是否为会员报名页  ： 老版本997为会员详情页，新版本中997为行动会员 1407为新知会员 此判断可忽略直接else
            mApply.setVisibility(View.GONE);
            mVipAisle.setVisibility(View.VISIBLE);
            double showPrice = 0;
            if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                mOldPrice.setVisibility(View.VISIBLE);
                showPrice = vip_price;
            } else {
                showPrice = price;
            }
            mPayPrice = real_price;
            if (showPrice == 0) {
                mOldPrice.setVisibility(View.GONE);
                mVipImme.setVisibility(View.GONE);
                if (real_price == 0) {
                    mVipPrice.setText("价\u3000格: 免费");
                } else {
                    mVipPrice.setText("价\u3000格: " + real_price + "元");
                }
            } else {
                if (showPrice > real_price) {
                    mOldPrice.setCutLine(true);
                    mOldPrice.setText("原\u3000价: " + price + "元");
                    if (real_price == 0) {
                        mVipPrice.setText("优惠价: 免费");
                    } else {
                        if (infoBean.getIs_vip() == 1 || (!"1".equals(infoBean.getBao()) && "1".equals(infoBean.getGo_on()))) {
                            mVipPrice.setText("续费价: " + real_price + "元");

                        } else {
                            mVipPrice.setText("优惠价: " + real_price + "元");
                        }

                    }
                    mVipImme.setVisibility(View.VISIBLE);

                } else {
                    mOldPrice.setVisibility(View.GONE);
                    mVipImme.setVisibility(View.GONE);
                    if (real_price == 0) {
                        mVipPrice.setText("价\u3000格: 免费");
                    } else {
                        mVipPrice.setText("价\u3000格: " + real_price + "元");
                    }
                }
            }
            mVipAisle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentEvent(true);
                }
            });
            mVipImme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentEvent(true);
                }
            });
        } else {
            mApply.setVisibility(View.VISIBLE);
            if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                mVipAisle.setVisibility(View.GONE);
                mOldPrice.setCutLine(true);
            } else {
                mVipImme.setVisibility(View.VISIBLE);
                mVipAisle.setVisibility(View.VISIBLE);
                mOldPrice.setCutLine(false);
                mVipAisle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentEvent(false);
                    }
                });
                mVipImme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentEvent(false);
                    }
                });
            }
            if (price == 0) {
                mOldPrice.setVisibility(View.GONE);
                mVipPrice.setText("免费");
                mPayPrice = price;
            } else {
                if (vip_price == 0) {
                    mOldPrice.setVisibility(View.VISIBLE);
                    mOldPrice.setText("普通价: " + price + "元");
                    mVipPrice.setText("会员价: 免费");
                } else {
                    if (vip_price == price) {
                        mOldPrice.setVisibility(View.GONE);
                        mVipPrice.setText("价格: " + vip_price + "元");
                    } else {
                        mOldPrice.setVisibility(View.VISIBLE);
                        mOldPrice.setText("普通价: " + price + "元");
                        mVipPrice.setText("会员价: " + vip_price + "元");
                    }
                }

                mPayPrice = vip_price;
            }
        }

        /**
         * 判别报名状态
         */
        saveApplyState(infoBean);
        if ("1".equals(infoBean.getBao())) {
            if (!"997".equals(mEventId)) {
                mApply.setText("已报名,查看详情");
                if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                    mApply.setBackgroundResource(R.drawable.shape_sg_main_oval_bg);
                } else {
                    mApply.setBackgroundResource(R.drawable.apply_share_bg);
                }
                mApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewEventDetailsActivity.this, ResultActivity.class);
                        intent.putExtra("order_no", mEventId);
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    }
                });
            } else {
                mVipImme.setVisibility(View.GONE);
                mVipAisle.setText("为好友购买");
                mVipAisle.setBackgroundResource(R.drawable.apply_share_bg);
//                mVipAisle.setOnClickListener(null);
            }
        } else {
            if ("997".equals(mEventId)) {
                if ("1".equals(infoBean.getGo_on())) {
                    mVipAisle.setText("立即续费");
                } else {
                    mVipAisle.setText("开通会员");
                }
            }

            if ("1".equals(infoBean.getIsbao())) { //是否可以报名
                if ("1".equals(mEventDetailEntity.getStatus())) { //支付状态
                    if ("1".equals(infoBean.getIsrepeat())) { //是否可重复报名
                        if ("1".equals(infoBean.getIsvip())) { //是否是Vip可报名 否则 都可以报名
                            if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                                mApply.setText("立即报名");
                                mApply.setOnClickListener(NewEventDetailsActivity.this);
                                if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                                    mApply.setBackgroundResource(R.drawable.shape_sg_main_oval_bg);
                                } else {
                                    mApply.setBackgroundResource(R.drawable.apply_share_bg);
                                }
//                            mApply.setBackgroundColor(Color.parseColor("#8DC63F"));
                            } else {
                                mApply.setText("非会员无法报名");
                                mApply.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
//                            mApply.setBackgroundColor(Color.parseColor("#8DC63F"));
                                mApply.setOnClickListener(null);
                            }
                        } else {
                            mApply.setText("立即报名");
                            mApply.setOnClickListener(NewEventDetailsActivity.this);
//                        mApply.setBackgroundColor(Color.parseColor("#8DC63F"));
                            if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                                mApply.setBackgroundResource(R.drawable.shape_sg_main_oval_bg);
                            } else {
                                mApply.setBackgroundResource(R.drawable.apply_share_bg);
                            }
                        }
                    } else {
                        mApply.setText("已报名,查看详情");
                        if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                            mApply.setBackgroundResource(R.drawable.shape_sg_main_oval_bg);
                        } else {
                            mApply.setBackgroundResource(R.drawable.apply_share_bg);
                        }
//                    mApply.setBackgroundColor(Color.parseColor("#555555"));
                        mApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(NewEventDetailsActivity.this, ResultActivity.class);
                                intent.putExtra("order_no", mEventId);
                                intent.putExtra("type", "1");
                                startActivity(intent);
                            }
                        });
                    }
                } else {
                    if ("1".equals(infoBean.getEvent_power())) {
                        if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                            mApply.setText("立即报名");
                            mApply.setOnClickListener(NewEventDetailsActivity.this);
//                        mApply.setBackgroundColor(Color.parseColor("#8DC63F"));
                            if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                                mApply.setBackgroundResource(R.drawable.shape_sg_main_oval_bg);
                            } else {
                                mApply.setBackgroundResource(R.drawable.apply_share_bg);
                            }
                        } else {
                            mApply.setText("非会员无法报名");
                            mApply.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
//                        mApply.setBackgroundColor(Color.parseColor("#555555"));
                            mApply.setOnClickListener(null);
                        }
                    } else {
                        mApply.setText("立即报名");
                        mApply.setOnClickListener(NewEventDetailsActivity.this);
//                    mApply.setBackgroundColor(Color.parseColor("#8DC63F"));
                        if (infoBean.getIs_vip() == 1) { //当前用户是否为vip
                            mApply.setBackgroundResource(R.drawable.shape_sg_main_oval_bg);
                        } else {
                            mApply.setBackgroundResource(R.drawable.apply_share_bg);
                        }
                    }
                }
            } else {
                mApply.setText("报名结束");
                mApply.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
//            mApply.setBackgroundColor(Color.parseColor("#555555"));
                mApply.setOnClickListener(null);
            }
        }

        mEventRandStr = infoBean.getRand_str();
    }

    private void saveApplyState(EventDetailEntity.InfoBean infoBean) {
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "isBao", infoBean.getIsbao());
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "isRepeat", infoBean.getIsrepeat());
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "isVip", infoBean.getIsvip());
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "applyPayStatus", mEventDetailEntity.getStatus());
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "eventPower", infoBean.getEvent_power());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebarbackss_imageViewback:
//                backInfo();
                finish();
                break;
            case R.id.titlebarbackss_share:
                if (StringUtils.isNotEmpty(mUserId)) {
                    showShareDig();
                } else {
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.apply:
                isPayCourseOrAction = 1;
                IntentEvent(true);
                break;
            case R.id.phone:
                Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                        .parse("tel:" + "400-900-3650"));
                startActivity(intentNO);

//                new SaveBitmapAsync().execute(captureWebView(mEventDetailWb));
//                new SaveBitmapAsync().execute(getBitmapByView(mEventScroll));
                break;
            case R.id.kefu:
                if (StringUtils.isNotEmpty(mUserId)) {
                    if (StringUtils.isNotEmpty(mIsplay)) {
                        if("2".equals(mIsplay)){
                            showKefuDig();
                        }else{
                            intentNO = new Intent(Intent.ACTION_DIAL, Uri
                                    .parse("tel:" + "400-900-3650"));
                            startActivity(intentNO);
                        }
                    }else{
                        Toast.makeText(MyApplication.mApplication, "正在获取客服信息", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

//    private void backInfo() {
//        if (mEventId.equals(mTemporaryId)) {
//            finish();
//        }else{
//            mEventId = mTemporaryId;
//            setWebview(mTemporaryId);
//            getEventDetails();
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        backInfo();
//    }

    private void setWebview(String event_id) {
        //url = "http://121.40.248.175/szkadmin/?c=welcome&m=appdetail&type=3&id=" + event_id;
        Log.e("DH_EVENT_ID", event_id + "");
        String url = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=3&id=" + event_id;
        //设置支持javaScript
        mSettings = mEventDetailWb.getSettings();
        //设置支持javaScript
        mSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        mSettings.setUseWideViewPort(true);
        //增加缩放按钮
        mSettings.setBuiltInZoomControls(true);
        mSettings.setSupportZoom(true);
        //设置文字大小
        mSettings.setTextSize(WebSettings.TextSize.SMALLER);

        mSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        mEventDetailWb.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mEventDetailWb.loadUrl(url);

    }


    public void IntentEvent(boolean isVipEvent) {
        if (StringUtils.isNotEmpty(mUserId) && mLoginRandStr.equals(mEventRandStr)) {
            //报名订单
            if (isVipEvent) {
                Intent intent = new Intent(this, NewEventInfoActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putSerializable("eventDetailBean", mEventInfo);
                bundle.putString("title", mEventInfo.getTitle());
                bundle.putString("event_id", mEventId);
                bundle.putString("real_price", mEventInfo.getReal_price());
                bundle.putString("vip_price", mEventInfo.getVip_price());
                bundle.putString("price", mEventInfo.getPrice());
                bundle.putString("is_vip", mEventInfo.getIs_vip() + "");
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                if (mSubPrice > 0) {
                    showPriceSpread();
                } else {
                    isPayCourseOrAction = 4;
                    Log.e("TAG","bbbbbb"+isPayCourseOrAction );

                    Intent intent = new Intent(NewEventDetailsActivity.this, VipListActivity.class);
//                    intent.putExtra("event_id", mEventId);
                    startActivity(intent);
//                    mEventId = "997";
//                    setWebview(mEventId);
//                    getEventDetails();
                }

            }
        } else {
            Intent intent = new Intent(this, LoginNewActivity.class);
            startActivity(intent);
        }
    }

    private Dialog mShareDialog;

    private void showShareDig() {
        //分享
        mShareDialog = new Dialog(NewEventDetailsActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(NewEventDetailsActivity.this).inflate(R.layout.share_dialog, null);
        //初始化控件
        RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
        RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
        RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
        share_rlayout_py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                //msgApi
                isShareInform = true;
                isPayCourseOrAction = 1;
                temporaryEventNo = mEventId;
                ShareUtil.weiChat(Constants.wx_api, 7,
                        NewEventDetailsActivity.this, mEventInfo.getShare_url() + "?helper=" + mUserId,
                        "邀你一起报名《" + mEventInfo.getTitle() + "》,这个课程真不错", mEventInfo.getStart_shijian());
            }
        });

        share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                isShareInform = true;
                isPayCourseOrAction = 1;
                temporaryEventNo = mEventId;
                ShareUtil.weiChat(Constants.wx_api, 8,
                        NewEventDetailsActivity.this, mEventInfo.getShare_url() + "?helper=" + mUserId,
                        "邀你一起报名《" + mEventInfo.getTitle() + "》,这个课程真不错", mEventInfo.getStart_shijian());
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

    private TextView mPrice;
    private ImageView mVipBt;

    private Dialog mPriceSpreadDialog;

    public void showPriceSpread() {
        mPriceSpreadDialog = new Dialog(this, R.style.MyDialog);
        View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_price_spread, null, false);
        mPriceSpreadDialog.setContentView(dView);
        mPrice = (TextView) dView.findViewById(R.id.price);
        mVipBt = (ImageView) dView.findViewById(R.id.vip_bt);
        if (mSubPrice > 0) {
            mPrice.setText(mSubPrice + "元");
        }
        mVipBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewEventDetailsActivity.this, VipListActivity.class);
//                intent.putExtra("event_id", "997");
                startActivity(intent);
//                mEventId = "997";
//                setWebview(mEventId);
//                getEventDetails();
                if (mPriceSpreadDialog != null && mPriceSpreadDialog.isShowing()) {
                    mPriceSpreadDialog.dismiss();
                }
            }
        });
        mPriceSpreadDialog.show();
    }


    @Override
    protected void onDestroy() {
        if (mEventDetailWb != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mEventDetailWb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mEventDetailWb);
            }

            mEventDetailWb.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mEventDetailWb.getSettings().setJavaScriptEnabled(false);
            mEventDetailWb.clearHistory();
            mEventDetailWb.clearView();
            mEventDetailWb.removeAllViews();
            mEventDetailWb.destroy();

        }
        super.onDestroy();

    }


    private Dialog mKeFuDialog;

    private void showKefuDig() {
        if (mDialogUtils==null) {
            mDialogUtils = new DialogUtils(this);
        }

        //分享
        mKeFuDialog = new Dialog(NewEventDetailsActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(NewEventDetailsActivity.this).inflate(R.layout.dig_detail_service, null);
        //将布局设置给Dialog
        mKeFuDialog.setContentView(inflate);
        TextView title = (TextView) mKeFuDialog.findViewById(R.id.title);
        TextView hint = (TextView) mKeFuDialog.findViewById(R.id.hint);
        RoundedImageView avatar = (RoundedImageView) mKeFuDialog.findViewById(R.id.avatar);
        TextView name = (TextView) mKeFuDialog.findViewById(R.id.name);
        TextView wx = (TextView) mKeFuDialog.findViewById(R.id.wx);
        TextView time = (TextView) mKeFuDialog.findViewById(R.id.time);
        TextView des = (TextView) mKeFuDialog.findViewById(R.id.des);
        TextView call = (TextView) mKeFuDialog.findViewById(R.id.call);
        TextView copy = (TextView) mKeFuDialog.findViewById(R.id.copy);

        if (mCustomerInfo!=null) {
            String per = "会员用户";
            if("1".equals(mCustomerInfo.getIsvip())||"2".equals(mCustomerInfo.getIsvip())){
                per = "行动会员";
            }else if("4".equals(mCustomerInfo.getIsvip())){
                per = "新知会员";
            }
            title.setText("尊贵的"+per+"，"+mCustomerInfo.getUser_name());
            hint.setText("您的专属课程顾问"+mCustomerInfo.getName()+"将为您服务");
            Glide.with(this).load(mCustomerInfo.getAvator()).into(avatar);
            name.setText("专属顾问："+mCustomerInfo.getName());
            wx.setText("微信号："+mCustomerInfo.getWechat_number());
            time.setText("工作时间："+mCustomerInfo.getWorking_interval());
            mWechat_number = mCustomerInfo.getWechat_number();
            mCustomerPhone = mCustomerInfo.getPhone();
        }
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。 TODO
                cm.setText(mWechat_number);
                Toast.makeText(MyApplication.mApplication, "复制成功，即将打开微信", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    Toast.makeText(MyApplication.mApplication, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeFuDialog != null && mKeFuDialog.isShowing()) {
                    mKeFuDialog.dismiss();
                }
                mDialogUtils.showServiceTel(mCustomerPhone, 0, mWechat_number, mCustomerInfo.getQr_code(), new DialogUtils.OnTelResultClickListener() {
                    @Override
                    public void onTelResultClick(String state) {
                        mCustomerPresenter.telResult(mUserId,mCustomerInfo.getCustomer_id(),state);
                    }
                });
            }
        });

        //获取当前Activity所在的窗体
        Window dialogWindow = mKeFuDialog.getWindow();
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
        mKeFuDialog.show();//显示对话框
    }
}
