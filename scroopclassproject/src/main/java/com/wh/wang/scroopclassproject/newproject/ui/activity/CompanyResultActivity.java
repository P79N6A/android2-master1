package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyPriceEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CompanyPricePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.TryOutPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopCompanyPay;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import java.util.HashMap;
import java.util.Map;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.SDK_PAY_FLAG;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_TRANSFER;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

public class CompanyResultActivity extends Activity implements View.OnClickListener, DialogUtils.OnShareInviteClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private TextView mCompanyL;
    private TextView mCompanyR;
    private TextView mResultTv;
    private ImageView mSuccessIcon;
    private CompanyPricePresenter mCompanyPricePresenter = new CompanyPricePresenter();
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    private TryOutPresenter mTryOutPresenter = new TryOutPresenter();
    private int TYPE = 0;

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
                        Intent intent = new Intent(CompanyResultActivity.this,CompanyResultActivity.class);
                        intent.putExtra("result_type","1");
                        startActivity(intent);
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
                                PayTask alipay = new PayTask(CompanyResultActivity.this);
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
                        IWXAPI wxApi = WXAPIFactory.createWXAPI(CompanyResultActivity.this, null);
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
    private String mUser_id;
    private DialogUtils mDialogUtils;
    private String mNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_pay_result);
        initView();
        initData();
        initListener();
    }

    private void initData() {

        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mNickname = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
        String type = getIntent().getStringExtra("result_type");
        if("0".equals(type)){
            TYPE = 0;
            mTitlebarbackssName.setText("认证成功");
            mResultTv.setText("恭喜您，认证成功");
            mCompanyL.setBackgroundResource(R.drawable.shape_free_bg);
            mCompanyL.setText("先试试看");
            mCompanyL.setTextColor(Color.parseColor("#556378"));
            mCompanyR.setBackgroundResource(R.drawable.imm_vip_shape);
            mCompanyR.setText("购买付费版");
            mSuccessIcon.setImageResource(R.drawable.success);
        }else{
            TYPE = 1;
            mTitlebarbackssName.setText("支付成功");
            mResultTv.setText("支付成功");
            mCompanyL.setBackgroundResource(R.drawable.apply_share_bg);
            mCompanyL.setText("邀请好友");
            mCompanyL.setTextColor(Color.parseColor("#ffffff"));
            mCompanyR.setBackgroundResource(R.drawable.register_next_bg);
            mCompanyR.setText("进入我的企业");
        }
    }

    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mCompanyL.setOnClickListener(this);
        mCompanyR.setOnClickListener(this);
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mCompanyL = (TextView) findViewById(R.id.company_l);
        mCompanyR = (TextView) findViewById(R.id.company_r);
        mResultTv = (TextView) findViewById(R.id.result_tv);
        mSuccessIcon = (ImageView) findViewById(R.id.success_icon);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
            case R.id.company_l:
                if(TYPE==0){
                    mTryOutPresenter.tryOut(mUser_id, new OnResultListener() {
                        @Override
                        public void onSuccess(Object... value) {
                            Intent intent = new Intent(CompanyResultActivity.this,CompanyInfoActivity.class);
                            startActivityForResult(intent, Constant.SEARCH_REQ);
                            finish();
                        }

                        @Override
                        public void onFault(String error) {

                        }
                    });
                }else{
                    mDialogUtils = new DialogUtils(this);
                    mDialogUtils.showInviteDialog(mUser_id);
                    mDialogUtils.setOnShareInviteClickListener(this);
                }
                break;
            case R.id.company_r:
                if(TYPE==0){
                    mCompanyPricePresenter.getCompanyPrice(mUser_id,new OnResultListener() {
                        @Override
                        public void onSuccess(Object... value) {
                            CompanyPriceEntity entity = (CompanyPriceEntity) value[0];
                            if(entity.getStatus()==1){
                                showCompanyPayPop(entity.getInfo());
                            }
                        }

                        @Override
                        public void onFault(String error) {

                        }
                    });
                }else{
                    Intent intent = new Intent(this,CompanyInfoActivity.class);
                    startActivityForResult(intent, Constant.SEARCH_REQ);
                }
                break;
        }
    }

    private PopCompanyPay mPopCompanyPay;
    private int mPayWay = 0;
    public void showCompanyPayPop(CompanyPriceEntity.InfoBean bean) {
        mPopCompanyPay = new PopCompanyPay(this,bean);
        mPopCompanyPay.showAtLocation(findViewById(R.id.activity_company_pay_result), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        mPopCompanyPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mPopCompanyPay.setOnPayClickListener(new PopCompanyPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay, int payNum, String taoId) {
                isPayCourseOrAction = 2;
                mPayWay = payWay;
                if(payWay==WAY_WEICHAT){
                    startPay(payNum,taoId);
                }else if(payWay==WAY_ALIPAY){
                    startPay(payNum,taoId);
                }else if(payWay==WAY_TRANSFER){
                    Intent intent = new Intent(CompanyResultActivity.this,TransferAccountsActivity.class);
                    startActivity(intent);
                }

                if(mPopCompanyPay!=null&&mPopCompanyPay.isShowing()){
                    mPopCompanyPay.dismiss();
                }
            }
        });
    }

    private void startPay(int num,String id) {
        Map<String,String> mPayMap = new HashMap<>();
        mPayMap.put("goodsType","8");
        mPayMap.put("id",id);
        mPayMap.put("user_id",mUser_id);
        mPayMap.put("coupon","");
        mPayMap.put("pay_type","4");
        mPayMap.put("buy_self","");
        mPayMap.put("num",num+"");
        mPayMap.put("multi_buy","");
        mPayMap.put("tao_id",id);
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

    @Override
    public void onInviteClick() {
        ShareUtil.weiChat(Constants.wx_api, 7, this,
                "http://test.shaoziketang.com/wapshaozi/enterprise.html?qid=" + mUser_id,
                mNickname+"邀请您加入勺子课堂企业号",
                "好知识抱团学习才有趣，赶快点击加入吧！");
    }
}
