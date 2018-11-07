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
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyPriceEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CompanyPricePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.EnterpriseApplyActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.TransferAccountsActivity;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopCompanyPay;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.SDK_PAY_FLAG;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_TRANSFER;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

public class CompanyApplyXWalkFragment extends Fragment {
    //    private XWalkView mCompanyXwalk;
    private WebView mCompanyWebview;

    private View mMc;
    private String head = "www";
    private String mUserId;
    private String COMPANY_URL = "http://" + head + ".shaoziketang.com/wapshaozi/enterprise_transition.html";
    private String COMPANY_PAY = "http://" + head + ".shaoziketang.com/wapshaozi/www.shaoziketang.com";
    private String BACK = "http://" + head + ".shaoziketang.com/wapshaozi/personal.html";


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
                        Toast.makeText(MyApplication.mApplication, "购买成功", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(MyApplication.mApplication, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    isPayCourseOrAction = -1;
                    break;
                }
                case WAY_ALIPAY:
                    entity = (PayEntity) msg.obj;
                    if (entity != null) {
                        final String orderInfo = entity.getInfo().getAlipay();
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
                    }
                    break;
                case WAY_WEICHAT:
                    entity = (PayEntity) msg.obj;
                    if (entity != null) {
                        PayEntity.InfoBean.WechatBean wechat = entity.getInfo().getWechat();
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
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private CompanyPricePresenter mCompanyPricePresenter = new CompanyPricePresenter();
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    //    private DialogUtils mDialogUtils;
    private String mCompayState;
    private WebSettings mSettings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_apply_xwalk, container, false);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mCompanyWebview = (WebView) view.findViewById(R.id.company_webview);
//        mCompanyXwalk = (XWalkView) view.findViewById(R.id.company_xwalk);
        mMc = (View) view.findViewById(R.id.mc);
        mCompayState = getArguments().getString("state");
        setWebview();
        return view;
    }

    private void setWebview() {
        if (StringUtils.isNotEmpty(mUserId)) {

            String url = COMPANY_URL + "?qid=" + mUserId;
            //设置支持javaScript
            mSettings = mCompanyWebview.getSettings();
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
            mCompanyWebview.setWebViewClient(new WebViewClient() {
                //当加载页面完成的时候回调
                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Override
                // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.e("DH_WEB_URL", "url:" + url);
                    if (url.equals(BACK)) {
                        getActivity().finish();
                    }
                    if (url.equals(COMPANY_PAY)) {
                        if ("0".equals(mCompayState)) {
                            Intent intent = new Intent(getContext(), EnterpriseApplyActivity.class);
                            intent.putExtra("state", "0");
                            startActivity(intent);
                            getActivity().finish();
                        } else if ("1".equals(mCompayState)) {
                            LoadingUtils.getInstance().showNetLoading(getContext());

                            startShowPay();
                        }
                    }
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
            mCompanyWebview.loadUrl(url);
        }
    }

//    private void setXWalk() {
//        if (mUserId != null && !"".equals(mUserId)) {
//            mCompanyXwalk.loadUrl(COMPANY_URL + "?qid=" + mUserId);
//            mCompanyXwalk.setResourceClient(new XWalkResourceClient(mCompanyXwalk) {
//                @Override
//                public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
//
//                }
//            });
//        }
//    }

    private void startShowPay() {

        mCompanyPricePresenter.getCompanyPrice(mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                CompanyPriceEntity entity = (CompanyPriceEntity) value[0];
                LoadingUtils.getInstance().hideNetLoading();
                if (entity.getStatus() == 1) {
                    showCompanyPayPop(entity.getInfo());
                }
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
            }
        });
    }

    private PopCompanyPay mPopCompanyPay;
    private int mPayWay = 0;

    public void showCompanyPayPop(CompanyPriceEntity.InfoBean bean) {
        mPopCompanyPay = new PopCompanyPay(getContext(), bean);
        mPopCompanyPay.showAtLocation(getActivity().findViewById(R.id.frag), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mMc.setVisibility(View.VISIBLE);
        mPopCompanyPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mMc.setVisibility(View.GONE);
            }
        });
        mPopCompanyPay.setOnPayClickListener(new PopCompanyPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay, int payNum, String taoId) {
                isPayCourseOrAction = 1000;
                mPayWay = payWay;
                if (payWay == WAY_WEICHAT) {
                    startPay(payNum, taoId);
                } else if (payWay == WAY_ALIPAY) {
                    startPay(payNum, taoId);
                } else if (payWay == WAY_TRANSFER) {
                    Intent intent = new Intent(getContext(), TransferAccountsActivity.class);
                    startActivity(intent);
                }

                if (mPopCompanyPay != null && mPopCompanyPay.isShowing()) {
                    mPopCompanyPay.dismiss();
                }
            }
        });
    }

    private void startPay(int num, String id) {
        LoadingUtils.getInstance().showNetLoading(getContext());
        Map<String, String> mPayMap = new HashMap<>();
        mPayMap.put("goodsType", "8");
        mPayMap.put("id", id);
        mPayMap.put("user_id", mUserId);
        mPayMap.put("coupon", "");
        mPayMap.put("pay_type", "4");
        mPayMap.put("buy_self", "");
        mPayMap.put("num", num + "");
        mPayMap.put("multi_buy", "");
        mPayMap.put("tao_id", id);
        mOrderPayPresenter.orderPay(mPayMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
                PayEntity entity = (PayEntity) value[0];
//                temporaryEventNo = entity.getInfo().getOrder_no();
                Message msg = mHandler.obtainMessage();
                msg.what = mPayWay;
                msg.obj = entity;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_PAY_ALIPAY", error);
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
//        if (mCompanyXwalk != null) {
//            mCompanyXwalk.pauseTimers();
//            mCompanyXwalk.onHide();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mCompanyXwalk != null) {
//            mCompanyXwalk.resumeTimers();
//            mCompanyXwalk.onShow();
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThis(FinishTaskEntity finishTaskEntity) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mCompanyXwalk != null) {
//            mCompanyXwalk.onDestroy();
//        }
        if( mCompanyWebview!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mCompanyWebview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mCompanyWebview);
            }

            mCompanyWebview.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mCompanyWebview.getSettings().setJavaScriptEnabled(false);
            mCompanyWebview.clearHistory();
            mCompanyWebview.clearView();
            mCompanyWebview.removeAllViews();
            mCompanyWebview.destroy();

        }
        EventBus.getDefault().unregister(this);
    }
}
