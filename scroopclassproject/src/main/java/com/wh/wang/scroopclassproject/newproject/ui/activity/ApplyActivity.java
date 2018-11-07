
package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
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
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.ApplyAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopPay;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_REQUEST;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_RESULT;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.SDK_PAY_FLAG;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

public class ApplyActivity extends Activity {
    private TextView mTitlebarbackssName;
    private RecyclerView mApplyList;
    private TextView mApplyNum;
    private TextView mSubInfo;
    private ImageView mSubCancel;
    private RelativeLayout mSubHint;
    private TextView mOPrice;
    private TextView mVPrice;
//    private DialogUtils mDialogUtils;

    private String mOriginal_price;
    private List<ContactEntity.InfoBean> mApply_list;
    private ApplyAdapter mApplyAdapter;
    private String mOrderID;

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
                        Intent intent = new Intent(ApplyActivity.this, ResultActivity.class);
                        intent.putExtra("type", String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no", temporaryEventNo);
                        startActivity(intent);
                        finish();
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
                            PayTask alipay = new PayTask(ApplyActivity.this);
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
                    IWXAPI wxApi = WXAPIFactory.createWXAPI(ApplyActivity.this, null);
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
    private double mSumPrice = 0;
    private String mId = "";
    private String mTitle;
    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    private PayEntity.InfoBean.WechatBean mWechat;
    private String mAlipay;
    private String mUser_id;
    private String mVip;
    //视频还是报名帖
    private int mApplyType;  //0 视频 1 活动
    private TextView mFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    private double sub_price = 0;
    private boolean hasVPrice = false;

    private void initData() {
        double o_price = 0;
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mOriginal_price = getIntent().getStringExtra("original_price");
        mId = getIntent().getStringExtra("id");
        mTitle = getIntent().getStringExtra("title");
        mVip = getIntent().getStringExtra("vip");
        mApplyType = getIntent().getIntExtra("apply_type", 0);
        mFinish.setText("去支付");
        mTitlebarbackssName.setText("确认支付");
//        if (mApplyType == 0) {
//            mFinish.setText("购买");
//            mTitlebarbackssName.setText("确认支付");
//        } else {
//            mFinish.setText("报名");
//            mTitlebarbackssName.setText("确认支付");
//        }
        try {
            o_price = Double.parseDouble(mOriginal_price);
        } catch (Exception e) {
            o_price = 0;
        }
        mApply_list = (List<ContactEntity.InfoBean>) getIntent().getSerializableExtra("apply_list");
        for (int i = 0; i < mApply_list.size(); i++) {
            if (o_price > Double.parseDouble(mApply_list.get(i).getPrice())) {
                hasVPrice = true;
                break;
            }
        }
        if (!hasVPrice) {
            mOPrice.setVisibility(View.GONE);
            mVPrice.setText("价格");
        }

        mApplyAdapter = new ApplyAdapter(mApply_list, o_price, hasVPrice);
        mApplyList.setAdapter(mApplyAdapter);
        if (mApply_list != null && mApply_list.size() > 0) {
            setApplyNumTv(mApply_list.size());
            for (int i = 0; i < mApply_list.size(); i++) {
                double v_price = 0;
                try {
                    v_price = Double.parseDouble(mApply_list.get(i).getPrice());
                } catch (Exception e) {
                    v_price = o_price;
                }
                if (o_price > v_price) {
                    sub_price += (o_price - v_price);
                }
            }
        }
        if (sub_price > 0) {
            mSubHint.setVisibility(View.VISIBLE);
            if ("1".equals(mVip)) {
                mSubInfo.setText("会员多人报名享受折扣价,此次为您节省: " + sub_price + "元");
            } else {
                mSubInfo.setText("多人报名享受折扣价,此次为您节省: " + sub_price + "元");
            }
        }
    }

    private void initView() {
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mApplyList = (RecyclerView) findViewById(R.id.apply_list);
        mApplyNum = (TextView) findViewById(R.id.apply_num);
        mSubInfo = (TextView) findViewById(R.id.sub_info);
        mSubHint = (RelativeLayout) findViewById(R.id.sub_hint);
        mOPrice = (TextView) findViewById(R.id.o_price);
        mVPrice = (TextView) findViewById(R.id.v_price);
        mFinish = (TextView) findViewById(R.id.finish);

        mApplyList.setLayoutManager(new LinearLayoutManager(this));
        mFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mSumPrice = 0;
                for (int i = 0; i < mApply_list.size(); i++) {
                    double price = 0;
                    try {
                        price = Double.parseDouble(mApply_list.get(i).getPrice());
                    } catch (Exception e) {
                        price = 0;
                    }
                    mSumPrice = addPrice(mSumPrice, price);
                }

                if (mApplyType == 0) {
                    showPayPop(String.valueOf(mSumPrice));
                } else {
                    Toast.makeText(ApplyActivity.this, "正在获取订单信息..", Toast.LENGTH_SHORT).show();
                    LoadingUtils.getInstance().showNetLoading(ApplyActivity.this);
                    getOrderInfo();
                }
//                Intent intent = new Intent(ApplyActivity.this, ResultActivity.class);
//                intent.putExtra("type",String.valueOf(1));
//                intent.putExtra("order_no",String.valueOf(mEvent_id));
//                startActivity(intent);
            }
        });
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.sub_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubHint.setVisibility(View.GONE);
            }
        });
    }


    private PopPay mPopPay;

    public void showPayPop(final String price) {

        mPopPay = new PopPay(this, mTitle, price);
        mPopPay.showAtLocation(findViewById(R.id.activity_apply), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        mPopPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mCouponId = "";
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mPopPay.setOnPayClickListener(new PopPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay) {
                isPayCourseOrAction = mApplyType;
                LoadingUtils.getInstance().showNetLoading(ApplyActivity.this);
                startPay(payWay);
            }

            @Override
            public void onSelectCouponClick() {
                Intent intent = new Intent(ApplyActivity.this, SelectCouponActivity.class);
                intent.putExtra("course_price", price);
                intent.putExtra("coupon_id", mCouponId);
                intent.putExtra("id", mId);
                intent.putExtra("category", mApplyType == 0 ? "1" : "2");
                startActivityForResult(intent, COUPON_REQUEST);
            }
        });
    }

    private String mCouponId = "";
    private String mCouponCode = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COUPON_REQUEST && resultCode == COUPON_RESULT) {
            mCouponId = data.getStringExtra("coupon_id");
            String coupon_price = data.getStringExtra("price");
            mCouponCode = data.getStringExtra("coupon_code");

            if (mPopPay != null && mPopPay.isShowing()) {
                mPopPay.setCouponInfo(coupon_price,mCouponId);
            }

        }
    }

    public void setApplyNumTv(int mCurrentNum) {
        String content = "报名人数：" + mCurrentNum + "人";
        if (mApplyType == 0) {
            content = "购买人数：" + mCurrentNum + "人";
        }
        int f_index = content.indexOf("：");
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fd733d")), f_index + 1, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mApplyNum.setText(spannableString);
    }

    public void getOrderInfo() {
        mCheckEventPresenter.checkEvent(getCheckParamMap(), new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_EVENT", "success");
                CheckEventEntity entity = (CheckEventEntity) value[0];
                Gson gson = new Gson();
                Log.e("nnnnnnn",gson.toJson(entity));
                LoadingUtils.getInstance().hideNetLoading();
                if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
                    showPayPop(String.valueOf(mSumPrice));
                } else {
                    Toast.makeText(ApplyActivity.this, entity.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_EVENT", error);
                LoadingUtils.getInstance().hideNetLoading();
            }
        });
    }

    public Map<String, String> getOrderMap(int applyType) {
        Map<String, String> map = new HashMap<>();
        String goodsType;
        if (applyType == 0) {
            goodsType = "3";
            map.put("isVideo", "1");
        } else if (applyType == 1) {
            goodsType = "1";
        } else {
            Toast.makeText(MyApplication.mApplication, "参数异常", Toast.LENGTH_SHORT).show();
            return null;
        }
        map.put("goodsType", goodsType);
        map.put("id", mId);
        map.put("user_id", mUser_id);
        map.put("coupon_code", "");
        map.put("coupon_cut", mCouponId);
        map.put("pay_type", "4");
        map.put("company", "1");
        return map;
    }

    private void startPay(final int payWay) {
        Map<String, String> orderMap = getOrderMap(mApplyType);
        if (orderMap != null) {
            String batch_join = "";
            if (mApplyType == 0) {
                //用于视频购买，batch_join参数放在form表单
                batch_join = getBatchJoin();
                Log.e("DDDDDDDDDD",batch_join);
            }
//            Iterator<Map.Entry<String, String>> iterator = orderMap.entrySet().iterator();
//            while (iterator.hasNext()){
//                Map.Entry<String, String> next = iterator.next();
//                Log.e("DH_ORDER_PAY","key:"+next.getKey()+"   value:"+next.getValue());
//            }

//            mOrderPayPresenter.orderPayJson(orderMap,batch_join, new OnResultListener() {
//                @Override
//                public void onSuccess(Object... value) {
//
//                }
//
//                @Override
//                public void onFault(String error) {
//
//                }
//            });
            Log.e("DDDDDDDDDD",orderMap.toString());


            mOrderPayPresenter.orderPay(orderMap, batch_join, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    Log.e("DH_PAY_WECHET", "success");
                    PayEntity entity = (PayEntity) value[0];
                    Gson gson = new Gson();
                    Log.e("nnnnnnn",gson.toJson(entity));

                    String fee = entity.getFee();
                    LoadingUtils.getInstance().hideNetLoading();
                    if (!"0".equals(fee)) {
                        if (entity.getStatus() != 0) {
                            if (mApplyType == 0) {
                                temporaryEventNo = entity.getInfo().getOrder_no();
                            } else {
                                temporaryEventNo = mId;
                            }
                            mWechat = entity.getInfo().getWechat();
                            mAlipay = entity.getInfo().getAlipay();
                            if (payWay == WAY_WEICHAT) {
                                Toast.makeText(ApplyActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (payWay == WAY_ALIPAY) {
                                Toast.makeText(ApplyActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                            }
                            mHandler.sendEmptyMessage(payWay);
                            if (mPopPay != null && mPopPay.isShowing()) {
                                mPopPay.dismiss();
                            }

                        }
                    } else {
                        Intent intent = new Intent(ApplyActivity.this, ResultActivity.class);
                        intent.putExtra("type", mApplyType + "");
                        intent.putExtra("order_no", mId);
                        startActivity(intent);
                        finish();
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

    private Map<String, String> getCheckParamMap() {
        Map<String, String> map = new HashMap<>();
        map.put("event_id", mId);
        map.put("name", "");
        map.put("user_id", mUser_id);
        map.put("ticket_id", "");
        map.put("coupon_code", "");

        String batch_join = getBatchJoin();
        Log.e("DH_EVENT_INFO", batch_join + " ");
        map.put("batch_join", batch_join == null ? "" : batch_join);
        map.put("tel", "");
        map.put("brand", "");
        map.put("email", "");
        map.put("area", "");
        map.put("position", "");
        map.put("company", "1");
        return map;
    }

    public String getBatchJoin() {
        StringBuffer batch_join = new StringBuffer();
        for (int i = 0; i < mApply_list.size(); i++) {
            ContactEntity.InfoBean infoBean = mApply_list.get(i);
            batch_join.append(infoBean.getPhone() + "=" + infoBean.getName());
            if (i != mApply_list.size() - 1) {
                batch_join.append("&");
            }
        }
        return batch_join.toString();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishTask(FinishTaskEntity entity) {
        if (this != null) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public Double addPrice(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }
}
