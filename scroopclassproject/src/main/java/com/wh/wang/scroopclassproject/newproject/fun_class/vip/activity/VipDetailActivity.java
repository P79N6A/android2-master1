package com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EventDetailsPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventInfoActivity;
import com.wh.wang.scroopclassproject.newproject.view.OldPriceTextView;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;

public class VipDetailActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mActionCouponTv;
    private TextView mActionBuy;

    private WebView mVipWeb;
    private String mEventId;
    private WebSettings mSettings;
    private EventDetailsPresenter mEventDetailsPresenter = new EventDetailsPresenter();
    private String mUserId;
    private String mLoginRandStr;
    private LinearLayout mMenu;
    private EventDetailEntity mEventDetailEntity;
    private EventDetailEntity.InfoBean mEventInfo;
    private OldPriceTextView mOldPrice;
    private String mKnowStatus;
//    private TextView mXzOriginalPrice;
//    private TextView mXzCouponPrice;

    private TextView mDiscountsPrice;
    private String mActionStatus;


    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_vip_detail;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void initView() {
        mActionCouponTv = (LinearLayout) findViewById(R.id.action_coupon_tv);
        mActionBuy = (TextView) findViewById(R.id.action_buy);
        mVipWeb = (WebView) findViewById(R.id.vip_web);
        mMenu = (LinearLayout) findViewById(R.id.menu);
        mOldPrice = (OldPriceTextView) findViewById(R.id.old_price);
//        mXzOriginalPrice = (TextView) findViewById(R.id.xz_original_price);
//        mXzCouponPrice = (TextView) findViewById(R.id.xz_coupon_price);
        mDiscountsPrice = (TextView) findViewById(R.id.discounts_price);
        mEventId = getIntent().getStringExtra("event_id") == null ? "" : getIntent().getStringExtra("event_id");
        mKnowStatus = getIntent().getStringExtra("know_status");
        mActionStatus = getIntent().getStringExtra("action_status");

        setWebview(mEventId);
    }



    @Override
    public void initDatas() {
        if("1407".equals(mEventId)){
            mTitleC.setText("新知会员");
        }else{
            mTitleC.setText("行动会员");
        }

    }

    @Override
    public void initListener() {
        mActionBuy.setOnClickListener(this);
//        mXzOriginalPrice.setOnClickListener(this);
//        mXzCouponPrice.setOnClickListener(this);
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initOther() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
        getVipInfo();
    }


    private void getVipInfo() {
        mEventDetailsPresenter.getEventDetails(mEventId, mUserId, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                mEventDetailEntity = (EventDetailEntity) value[0];
                if (mEventDetailEntity.getInfo() != null) {
                    mMenu.setVisibility(View.VISIBLE);
                    mEventInfo = mEventDetailEntity.getInfo();
                    setEventInfo(mEventInfo);
                } else {
                    mMenu.setVisibility(View.GONE);
                    Toast.makeText(MyApplication.mApplication, "数据请求异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_WHAT", error);
                Toast.makeText(MyApplication.mApplication, "数据请求异常", Toast.LENGTH_SHORT).show();
                mMenu.setVisibility(View.GONE);
            }
        });
    }

    private void setEventInfo(EventDetailEntity.InfoBean eventInfo) {
        mActionBuy.setVisibility(View.VISIBLE);
        mActionCouponTv.setVisibility(View.VISIBLE);
        if ("1407".equals(mEventId)) {
            if ("1".equals(mKnowStatus)) {
                mActionBuy.setText("立即续费");
            }

            mDiscountsPrice.setText("899元/年");

            if ("1".equals(mKnowStatus)) {
                if ("1".equals(mActionStatus)) {
                    mActionBuy.setText("为好友开通");
                    mDiscountsPrice.setText(eventInfo.getPrice()+"/年");
                    mOldPrice.setVisibility(View.GONE);
                } else {
                    mActionBuy.setText("立即续费");
                    mDiscountsPrice.setText("续费价："+eventInfo.getPrice_two()+"/年");
                    mOldPrice.setText("原价："+eventInfo.getPrice()+"元/年");
                    mOldPrice.setVisibility(View.VISIBLE);
                }

//                mXzOriginalPrice.setVisibility(View.GONE);
//                mXzCouponPrice.setVisibility(View.GONE);

            } else {
                if ("1".equals(mActionStatus)) {
                    mActionBuy.setText("为好友开通");
                    mDiscountsPrice.setText(eventInfo.getPrice()+"/年");
                    mOldPrice.setVisibility(View.GONE);
//                    mXzOriginalPrice.setVisibility(View.GONE);
//                    mXzCouponPrice.setVisibility(View.GONE);
//                    mActionBuy.setVisibility(View.VISIBLE);
//                    mActionCouponTv.setVisibility(View.VISIBLE);
                } else {
//                    mXzOriginalPrice.setVisibility(View.VISIBLE);
//                    mXzCouponPrice.setVisibility(View.VISIBLE);
//                    mActionBuy.setVisibility(View.GONE);
//                    mActionCouponTv.setVisibility(View.GONE);
                }

            }

//            mDiscountsPrice.setText("优惠价699");
            setWebview("http://www.shaoziketang.com/wapshaozi/vip_new_know.html");
        } else {
            if ("1".equals(mActionStatus)) {
                mActionBuy.setText("立即续费");
                mDiscountsPrice.setText("续费价："+eventInfo.getPrice_two()+"/年");
                mOldPrice.setText("原价："+eventInfo.getPrice()+"元/年");
                mOldPrice.setVisibility(View.VISIBLE);
            }else{

                if("1".equals(mKnowStatus)){
                    mActionBuy.setText("升级为行动会员");
                    mDiscountsPrice.setText("升级差价："+eventInfo.getPrice_two()+"元/年");
                    mOldPrice.setText("原价："+eventInfo.getPrice()+"元/年");
                    mOldPrice.setVisibility(View.VISIBLE);
                }else{
                    mDiscountsPrice.setText(eventInfo.getPrice()+"/年");
                    mOldPrice.setVisibility(View.GONE);
                }
            }


//            mXzOriginalPrice.setVisibility(View.GONE);
//            mXzCouponPrice.setVisibility(View.GONE);
            mActionBuy.setVisibility(View.VISIBLE);
            mActionCouponTv.setVisibility(View.VISIBLE);
            setWebview("http://www.shaoziketang.com/wapshaozi/vip_be_born.html");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle;
        switch (v.getId()) {
            case R.id.action_buy:
            case R.id.xz_original_price:
                intent = new Intent(this, NewEventInfoActivity.class);
                bundle = new Bundle();
                bundle.putString("title", mEventInfo.getTitle());
                bundle.putString("event_id", mEventId);
                bundle.putString("real_price", mEventInfo.getReal_price());
                bundle.putString("vip_price", mEventInfo.getVip_price());
                bundle.putString("price", mEventInfo.getPrice());
                bundle.putString("is_vip", mEventInfo.getIs_vip() + "");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case R.id.xz_coupon_price:
                intent = new Intent(this, BargainVipWebActivity.class);
                bundle = new Bundle();
                bundle.putString("title", mEventInfo.getTitle());
                bundle.putString("event_id", mEventId);
                bundle.putString("real_price", mEventInfo.getReal_price());
                bundle.putString("vip_price", mEventInfo.getVip_price());
                bundle.putString("price", mEventInfo.getPrice());
                bundle.putString("is_vip", mEventInfo.getIs_vip() + "");
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                break;
        }
    }

    private void setWebview(String url) {
        //设置支持javaScript
        mSettings = mVipWeb.getSettings();
        //设置支持javaScript
        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        //设置文字大小
        mSettings.setTextSize(WebSettings.TextSize.SMALLER);
        mSettings.setTextZoom(100);
        mVipWeb.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mVipWeb.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        if (mVipWeb != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mVipWeb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mVipWeb);
            }

            mVipWeb.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mVipWeb.getSettings().setJavaScriptEnabled(false);
            mVipWeb.clearHistory();
            mVipWeb.clearView();
            mVipWeb.removeAllViews();
            mVipWeb.destroy();

        }
        super.onDestroy();
    }

}
