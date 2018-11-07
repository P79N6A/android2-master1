package com.wh.wang.scroopclassproject.newproject.ui.fragment.apply_xwalk_frag;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.FinishTaskEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.SelectCouponActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopPay;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_REQUEST;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_RESULT;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.SDK_PAY_FLAG;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

/**
 * A simple {@link Fragment} subclass.
 */
public class VIPApplyXWalkFragment extends Fragment {
//    private XWalkView mVipXwalk;
    private WebView mVipWebview;

    private View mMc;
    private String head = "www";
    private String VIP_URL = "http://"+head+".shaoziketang.com/wapshaozi/member_center.html";
    private String BACK = "http://"+head+".shaoziketang.com/wapshaozi/personal.html";
    private String PAY = "http://"+head+".shaoziketang.com/wapshaozi/www.shaoziketang.com";
    private String mUserId;
    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
//    private DialogUtils mDialogUtils;
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    private PayEntity.InfoBean.WechatBean mWechat;
    private String mAlipay;
    private String mMobile;
    private String mNickName;
    private WebSettings mSettings;

    public VIPApplyXWalkFragment() {
    }
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
//                        Intent intent = new Intent(getContext(), ResultActivity.class);
//                        intent.putExtra("type", String.valueOf(isPayCourseOrAction));
//                        intent.putExtra("order_no", temporaryEventNo);
//                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(MyApplication.mApplication, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case WAY_ALIPAY:
                    final String orderInfo = mAlipay;
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(getActivity());
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
                    break;
                case WAY_WEICHAT:
                    PayEntity.InfoBean.WechatBean wechat = mWechat;
                    IWXAPI wxApi = WXAPIFactory.createWXAPI(getContext(), null);
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
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vipapply_xwalk, container, false);
        mVipWebview = (WebView) view.findViewById(R.id.vip_webview);

//        mVipXwalk = (XWalkView) view.findViewById(R.id.vip_xwalk);
        mMc = (View) view.findViewById(R.id.mc);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        mNickName = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
//        mDialogUtils = new DialogUtils(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mVipXwalk != null) {
//            mVipXwalk.resumeTimers();
//            mVipXwalk.onShow();
//        }
        setWebview();
    }

    private void setWebview() {
        if (StringUtils.isNotEmpty(mUserId)) {

            String url = VIP_URL+"?qid="+mUserId;
            //设置支持javaScript
            mSettings = mVipWebview.getSettings();
            //设置支持javaScript
            mSettings.setJavaScriptEnabled(true);
            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不缓存，只从网络加载数据
            //设置双击变大变小
            mSettings.setUseWideViewPort(true);
            //增加缩放按钮
            mSettings.setBuiltInZoomControls(true);
            mSettings.setSupportZoom(true);
            //设置文字大小
            mSettings.setTextSize(WebSettings.TextSize.SMALLER);
            mSettings.setTextZoom(100);
            //不让从当前网页跳转到系统的浏览器中
            mVipWebview.setWebViewClient(new WebViewClient() {
                //当加载页面完成的时候回调
                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Override
                // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if(url.equals(BACK)){
                        getActivity().finish();
                    }
                    if(url.equals(PAY)){
                        LoadingUtils.getInstance().showNetLoading(getContext());
                        getOrderInfo();
                    }
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
            mVipWebview.loadUrl(url);
        }
    }

//    private void setXWalk() {
//        if (mUserId != null && !"".equals(mUserId)) {

//            mVipXwalk.loadUrl(VIP_URL+"?qid="+mUserId);
//            mVipXwalk.setResourceClient(new XWalkResourceClient(mVipXwalk) {
//                @Override
//                public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
//                    Log.e("DH_WEB_URL","url:"+url);
//                    if(url.equals(BACK)){
//                        getActivity().finish();
//                    }
//                    if(url.equals(PAY)){
//                        LoadingUtils.getInstance().showNetLoading(getContext());
//                        getOrderInfo();
//                    }
//                    return true;
//                }
//            });
//        }
//    }

    private String mTitle;
    public void getOrderInfo(){
        mCheckEventPresenter.checkEvent(getCheckParamMap(), new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_EVENT", "success");
                LoadingUtils.getInstance().hideNetLoading();
                CheckEventEntity entity = (CheckEventEntity) value[0];
                if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
                    mTitle = entity.getTitle();

                    showPayPop(entity.getMoney());
                } else {
                    Toast.makeText(MyApplication.mApplication, entity.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_EVENT", error);
                LoadingUtils.getInstance().hideNetLoading();
            }
        });
    }

    private void startPay(final int payWay) {
        LoadingUtils.getInstance().showNetLoading(getContext());
        Map<String, String> orderMap = getOrderMap();
        if(orderMap!=null){
            mOrderPayPresenter.orderPay(orderMap,"", new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    LoadingUtils.getInstance().hideNetLoading();
                    Log.e("DH_PAY_WECHET", "success");
                    PayEntity entity = (PayEntity) value[0];
                    String fee = entity.getFee();
                    LoadingUtils.getInstance().hideNetLoading();
                    if (!"0".equals(fee)) {
                        if(entity.getStatus()!=0){
//                            temporaryEventNo = "997";
                            mWechat = entity.getInfo().getWechat();
                            mAlipay = entity.getInfo().getAlipay();

                            if (payWay == WAY_WEICHAT) {
                                Toast.makeText(MyApplication.mApplication, "正在前往微信..", Toast.LENGTH_SHORT).show();
                            } else if (payWay == WAY_ALIPAY) {
                                Toast.makeText(MyApplication.mApplication, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                            }
                            mHandler.sendEmptyMessage(payWay);
                            if (mPopPay != null && mPopPay.isShowing()) {
                                mPopPay.dismiss();
                            }
                        }
                    } else {
                        LoadingUtils.getInstance().hideNetLoading();
                        Toast.makeText(MyApplication.mApplication, "购买成功", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
//                        Intent intent = new Intent(getContext(), ResultActivity.class);
//                        intent.putExtra("type", "1");
//                        intent.putExtra("order_no", "997");
//                        startActivity(intent);
                    }
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_PAY_WECHET", error);
                    LoadingUtils.getInstance().hideNetLoading();
                }
            });
        }
    }

    public Map<String,String> getOrderMap(){
        Log.e("DH_ID",mUserId);
        Map<String,String> map = new HashMap<>();
        map.put("goodsType","1");
        map.put("id","997");
        map.put("user_id",mUserId);
        map.put("coupon","");
        map.put("pay_type","4");
        map.put("company","1");
        return map;
    }

    private Map<String, String> getCheckParamMap() {
        Map<String, String> map = new HashMap<>();
        map.put("event_id", "997");
        map.put("name","");
        map.put("user_id", mUserId);
        map.put("ticket_id", "");
        map.put("coupon_code", "");
        map.put("coupon_cut", mCouponId);

        String batch_join = mMobile+"="+mNickName;
        Log.e("DH_EVENT_INFO", batch_join+" ");
        map.put("batch_join", batch_join==null?"":batch_join);
        map.put("tel", "");
        map.put("brand","");
        map.put("email", "");
        map.put("area", "");
        map.put("position", "");
        map.put("company", "1");
        return map;
    }

    private PopPay mPopPay;
    private String mCouponId = "";
    private String mCouponCode = "";

    public void showPayPop(final String money) {
        mPopPay = new PopPay(getContext(), mTitle, money+ "");
        mPopPay.showAtLocation(getActivity().findViewById(R.id.frag), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mMc.setVisibility(View.VISIBLE);
        mPopPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mCouponId = "";
                mMc.setVisibility(View.GONE);
            }
        });
        mPopPay.setOnPayClickListener(new PopPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay) {
                isPayCourseOrAction = 1;
                startPay(payWay);

            }

            @Override
            public void onSelectCouponClick() {
                Intent intent = new Intent(getContext(), SelectCouponActivity.class);
                intent.putExtra("course_price",money);
                intent.putExtra("coupon_id",mCouponId);
                intent.putExtra("id","997");
                intent.putExtra("category","2");
                startActivityForResult(intent,COUPON_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==COUPON_REQUEST&&resultCode==COUPON_RESULT){
            mCouponId = data.getStringExtra("coupon_id");
            String coupon_price = data.getStringExtra("price");
            mCouponCode = data.getStringExtra("coupon_code");
            if(mPopPay!=null&&mPopPay.isShowing()){
                mPopPay.setCouponInfo(coupon_price,mCouponId);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (mVipXwalk != null) {
//            mVipXwalk.pauseTimers();
//            mVipXwalk.onHide();
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThis(FinishTaskEntity finishTaskEntity){
        getActivity().finish();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mVipXwalk != null) {
//            mVipXwalk.onDestroy();
//        }

        if( mVipWebview!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mVipWebview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mVipWebview);
            }

            mVipWebview.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mVipWebview.getSettings().setJavaScriptEnabled(false);
            mVipWebview.clearHistory();
            mVipWebview.clearView();
            mVipWebview.removeAllViews();
            mVipWebview.destroy();

        }
        EventBus.getDefault().unregister(this);
    }
}
