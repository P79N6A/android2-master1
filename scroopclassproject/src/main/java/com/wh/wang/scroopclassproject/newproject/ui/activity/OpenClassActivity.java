package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventOpenEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EventDetailsPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EventOpenPresenter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

public class OpenClassActivity extends Activity implements View.OnClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private ImageView mTitlebarbackssShare;
    private TextView mTitle;
    private TextView mVip;
    private TextView mStudy;
    private String mCourseId;
    private String mType;
    private String mCourseTitle;
    private WebView mOpenClassWebview;
    private WebSettings mWebSetting;
    private String mSubTitle;
    private String mShareUrl;
    private EventDetailsPresenter mEventDetailsPresenter = new EventDetailsPresenter();
    private EventOpenPresenter mEventOpenEntity = new EventOpenPresenter();
    private String mUser_id;
    private String mVideo_id;
    private String mLoginRandStr;
    private String mRandStr;
    private ImageView mPhone;
    private int OC_State = 0; //公开课状态 0没有报名 1 进答疑群
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_class);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssShare = (ImageView) findViewById(R.id.titlebarbackss_share);
        mTitle = (TextView) findViewById(R.id.title);
        mVip = (TextView) findViewById(R.id.vip);
        mStudy = (TextView) findViewById(R.id.study);
        mOpenClassWebview = (WebView) findViewById(R.id.open_class_webview);
        mPhone = (ImageView) findViewById(R.id.phone);
    }


    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mStudy.setOnClickListener(this);
        mVip.setOnClickListener(this);
        mPhone.setOnClickListener(this);
    }

    private void initData() {
        mTitlebarbackssName.setText("勺子公开课");
        mCourseId = getIntent().getStringExtra("id")==null?"":getIntent().getStringExtra("id");
        mType = getIntent().getStringExtra("type")==null?"":getIntent().getStringExtra("type");
//        mCourseId = "1288";
        setWebView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(this, "login_rand_str", "");
        if(StringUtils.isNotEmpty(mUser_id)){
            mEventDetailsPresenter.getEventDetails(mCourseId, mUser_id, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    EventDetailEntity entity = (EventDetailEntity) value[0];
                    EventDetailEntity.InfoBean infoBean = entity.getInfo();
                    mCourseTitle = infoBean.getTitle();
                    mRandStr = infoBean.getRand_str();
                    mTitle.setText(mCourseTitle);
                    mSubTitle = infoBean.getStart_shijian();
                    mShareUrl = infoBean.getShare_url();
                    mVideo_id = infoBean.getVideo_id();
                    clearShareInfo();
                    SharedPreferenceUtil.putStringValueByKey(MyApplication.getApplication(),"shaozi_code",infoBean.getCode_hao()==null?"":infoBean.getCode_hao()); //暗号
                    SharedPreferenceUtil.putStringValueByKey(MyApplication.getApplication(),"shaozi_img",infoBean.getShaozi_img()==null?"":infoBean.getShaozi_img()); //头像
                    SharedPreferenceUtil.putStringValueByKey(MyApplication.getApplication(),"shaozi_name",infoBean.getShaozi_name()==null?"":infoBean.getShaozi_name()); //微信号
                    Log.e("DH_OPEN_CLASS",infoBean.getIsbao()+"  "+entity.getStatus()+"  "+infoBean.getIsrepeat());
                    mStudy.setVisibility(View.VISIBLE);
                    if(infoBean.getIs_vip()==1){
                        mVip.setVisibility(View.GONE);
                    }else{
                        mVip.setVisibility(View.VISIBLE);
                    }
                    try{
                        mSubPrice = Double.parseDouble(infoBean.getSub_price());
                    }catch (Exception e){
                        mSubPrice = 0;
                    }
                    if("1".equals(infoBean.getIsbao())){ //是否可以报名
                        if("1".equals(entity.getStatus())){ //支付状态
                            if("1".equals(infoBean.getIsrepeat())){ //是否可重复报名
                                if("1".equals(infoBean.getIsvip())){ //是否是Vip可报名 否则 都可以报名
                                    if(infoBean.getIs_vip()==1){ //当前用户是否为vip
                                        mStudy.setText("免费报名");
                                        mStudy.setOnClickListener(OpenClassActivity.this);
                                        mStudy.setBackgroundResource(R.drawable.apply_share_bg);
//                                        mStudy.setBackgroundColor(Color.parseColor("#8DC63F"));
                                    }else{
                                        mStudy.setText("非会员无法报名");
//                                        mStudy.setBackgroundColor(Color.parseColor("#555555"));
                                        mStudy.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
                                        mStudy.setOnClickListener(null);
                                    }
                                }else{
                                    mStudy.setText("立即报名");
                                    mStudy.setOnClickListener(OpenClassActivity.this);
                                    mStudy.setBackgroundResource(R.drawable.apply_share_bg);
//                                    mStudy.setBackgroundColor(Color.parseColor("#8DC63F"));
                                }
                            }else{
                                OC_State = 1;
                                already("进答疑群");
                            }
                        }else{
                            if("1".equals(infoBean.getEvent_power())){
                                if(infoBean.getIs_vip()==1){ //当前用户是否为vip
                                    mStudy.setText("免费报名");
                                    mStudy.setOnClickListener(OpenClassActivity.this);
                                    mStudy.setBackgroundResource(R.drawable.apply_share_bg);
//                                    mStudy.setBackgroundColor(Color.parseColor("#8DC63F"));
                                }else{
                                    mStudy.setText("非会员无法报名");
//                                    mStudy.setBackgroundColor(Color.parseColor("#555555"));
                                    mStudy.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
                                    mStudy.setOnClickListener(null);
                                }
                            }else{
                                mStudy.setText("免费报名");
                                mStudy.setOnClickListener(OpenClassActivity.this);
//                                mStudy.setBackgroundColor(Color.parseColor("#8DC63F"));
                                mStudy.setBackgroundResource(R.drawable.apply_share_bg);
                            }
                        }
                    }else{
                        mStudy.setText("报名结束");
//                        mStudy.setBackgroundColor(Color.parseColor("#555555"));
                        mStudy.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
                        mStudy.setOnClickListener(null);
                    }

                }

                @Override
                public void onFault(String error) {
                    Toast.makeText(OpenClassActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mVip.setVisibility(View.VISIBLE);
            mStudy.setVisibility(View.VISIBLE);
        }

    }

    private void clearShareInfo() {
        SharedPreferenceUtil.clearData("shaozi_code",MyApplication.mApplication);
        SharedPreferenceUtil.clearData("shaozi_img",MyApplication.mApplication);
        SharedPreferenceUtil.clearData("shaozi_name",MyApplication.mApplication);
    }

    private void already(String title) {
        mStudy.setText(title);
        mStudy.setBackgroundResource(R.drawable.apply_share_bg);
//        mStudy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = null;
//                if(StringUtils.isNotEmpty(mUser_id)&&mLoginRandStr.equals(mRandStr)){
//                    intoGroup();
//                }else{
//                    intent = new Intent(OpenClassActivity.this, LoginNewActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
            case R.id.study:
                if(StringUtils.isNotEmpty(mUser_id)&&mLoginRandStr.equals(mRandStr)){
                    if(mShareUrl!=null&&!"".equals(mShareUrl)){
                        if(OC_State==1){
                            intoGroup();
                        }else{
                            mEventOpenEntity.eventOpen(mCourseId, mUser_id, new OnResultListener() {
                                @Override
                                public void onSuccess(Object... value) {
                                    EventOpenEntity entity = (EventOpenEntity) value[0];
                                    if(entity.getCode()==200){
                                        intoGroup();
                                    }
                                    Toast.makeText(OpenClassActivity.this, entity.getMsg()+"", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFault(String error) {
                                    Log.e("DH_OPEN_EVENT",error);
                                }
                            });
                        }
                    }else{
                        Toast.makeText(OpenClassActivity.this, "正在网络加载，请稍等", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.phone:
                Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                        .parse("tel:" + "400-900-3650"));
                startActivity(intentNO);
                break;
            case R.id.vip:
                if(mSubPrice>0){
                    showPriceSpread();
                }else{
                    intent = new Intent(this,NewEventDetailsActivity.class);
                    intent.putExtra("event_id","997");
                    startActivity(intent);
                }

                break;
        }
    }

    private void intoGroup(){
        Intent intent = new Intent(OpenClassActivity.this,ApplyResultActivity.class);
        intent.putExtra("id",mCourseId);
        intent.putExtra("type",mType);
        intent.putExtra("title",mCourseTitle);
        intent.putExtra("sub_title",mSubTitle);
        intent.putExtra("share_url",mShareUrl);
        intent.putExtra("video_id",mVideo_id);
        startActivity(intent);
    }

    private void setWebView() {
        String webUrl ="http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=3&id=" + mCourseId;
        mWebSetting = mOpenClassWebview.getSettings();
        //设置支持javaScript
        mWebSetting.setJavaScriptEnabled(true);
        //增加缩放按钮
        mWebSetting.setBuiltInZoomControls(true);
        mWebSetting.setSupportZoom(true);
        //设置文字大小
        mWebSetting.setTextSize(WebSettings.TextSize.SMALLER);
        mWebSetting.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        mOpenClassWebview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //pb_loading.setVisibility(View.GONE);
            }
        });

        mOpenClassWebview.loadUrl(webUrl);
    }

    private Dialog mPriceSpreadDialog;
    private TextView mPrice;
    private ImageView mVipBt;
    private double mSubPrice;
    public void showPriceSpread(){
        mPriceSpreadDialog = new Dialog(this, R.style.MyDialog);
        View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_price_spread,null,false);
        mPriceSpreadDialog.setContentView(dView);
        mPrice = (TextView) dView.findViewById(R.id.price);
        mVipBt = (ImageView) dView.findViewById(R.id.vip_bt);
        if(mSubPrice>0){
            mPrice.setText(mSubPrice+"元");
        }
        mVipBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenClassActivity.this,NewEventDetailsActivity.class);
                intent.putExtra("event_id","997");
                startActivity(intent);
                if(mPriceSpreadDialog!=null&&mPriceSpreadDialog.isShowing()){
                    mPriceSpreadDialog.dismiss();
                }
            }
        });
        mPriceSpreadDialog.show();
    }
}
