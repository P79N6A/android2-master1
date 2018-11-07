package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.wh.wang.scroopclassproject.bean.MessageBean;
import com.wh.wang.scroopclassproject.bean.TipsBean;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.SelectCouponActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.UserInfoAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopPay;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseType;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_REQUEST;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.SDK_PAY_FLAG;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

public class OrderActivity extends Activity implements View.OnClickListener {

    /* 订单信息确认*/
    private RelativeLayout titlebarbackss;
    private ImageView titlebarbackssImageViewback;
    private TextView titlebarbackssName;
    private TextView titlebarbackssAction;
    private LinearLayout llVipTitle;
    private TextView tvProductway;
    private TextView tvProductexplain;
    private LinearLayout llUserList;
    private TextView tvCheckway;
    private RecyclerView rvPersonlist;
    private TextView tvTipproduct;
    private LinearLayout payInfo;
    private RelativeLayout payWeichat;
    private ImageView weichatIcon;
    private ImageView weichatSelect;
    private RelativeLayout payAlipay;
    private ImageView alipayIcon;
    private ImageView alipaySelect;
    private RelativeLayout coupon;
    private ImageView couponIcon;
    private TextView couponInfo;
    private ImageView couponArrow;
    private TextView tvTotalprice;
    private TextView tvComit;
    private String isPayCourse;
    private String money;
    private HashMap<String, String> ordMap;
    private String productName;
    private List<ContactEntity.InfoBean> infoList = new ArrayList<>();
    private UserInfoAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    private PayEntity.InfoBean.WechatBean mWechat;
    private String mAlipay;
    private int[] mPaySelect = {R.drawable.pay_unselect, R.drawable.pay_select};
    public static final int COUPON_RESULT = 1001;
    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
    private int mApplyType;
    private CheckEventEntity checkEventEntity;

    private OrderPayPresenter mOrdersPayPresenter = new OrderPayPresenter();


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
                        Intent intent = new Intent(OrderActivity.this, ResultActivity.class);
                        intent.putExtra("type", String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no", temporaryEventNo);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MyApplication.mApplication, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    temporaryEventNo = "";
                    isPayCourseOrAction = -1;
                    isPayCourseType = "";
                    break;
                }
                case WAY_ALIPAY:
                    final String orderInfo = mAlipay;
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(OrderActivity.this);
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
                    IWXAPI wxApi = WXAPIFactory.createWXAPI(OrderActivity.this, null);
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
    private String mEvent_id;
    private String mUser_id;
    private int payWay = 3;
    private String title;
    private String flag;
    private TextView tvprice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initData();
        initListener();
        initTitle();
        setData();
    }


    private void initTitle() {
        titlebarbackssName.setText("确认报名信息");
    }


    /*初始化控件*/
    private void initView() {

        titlebarbackss = (RelativeLayout) findViewById(R.id.titlebarbackss);
        titlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        titlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        titlebarbackssAction = (TextView) findViewById(R.id.titlebarbackss_action);
        llVipTitle = (LinearLayout) findViewById(R.id.ll_vip_title);
        tvProductway = (TextView) findViewById(R.id.tv_productway);
        TextPaint tp = tvProductway.getPaint();
        tp.setFakeBoldText(true);
        tvProductexplain = (TextView) findViewById(R.id.tv_productexplain);
        llUserList = (LinearLayout) findViewById(R.id.ll_user_list);
        tvCheckway = (TextView) findViewById(R.id.tv_checkway);
        rvPersonlist = (RecyclerView) findViewById(R.id.rv_personlist);
        tvTipproduct = (TextView) findViewById(R.id.tv_tipproduct);
        payInfo = (LinearLayout) findViewById(R.id.pay_info);
        payWeichat = (RelativeLayout) findViewById(R.id.pay_weichat);
        weichatIcon = (ImageView) findViewById(R.id.weichat_icon);
        weichatSelect = (ImageView) findViewById(R.id.weichat_select);
        payAlipay = (RelativeLayout) findViewById(R.id.pay_alipay);
        alipayIcon = (ImageView) findViewById(R.id.alipay_icon);
        alipaySelect = (ImageView) findViewById(R.id.alipay_select);
        coupon = (RelativeLayout) findViewById(R.id.coupon);
        couponIcon = (ImageView) findViewById(R.id.coupon_icon);
        couponInfo = (TextView) findViewById(R.id.coupon_info);
        couponArrow = (ImageView) findViewById(R.id.coupon_arrow);
        tvTotalprice = (TextView) findViewById(R.id.tv_totalprice);
        tvComit = (TextView) findViewById(R.id.tv_comit);
        tvprice = (TextView) findViewById(R.id.tv_totalpricetext);
        //解决嵌套卡顿问题
        linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvPersonlist.setLayoutManager(linearLayoutManager);
    }

    /* 拿数据*/
    private void initData() {
        EventBus.getDefault().register(this);

        ordMap = (HashMap<String, String>) getIntent().getSerializableExtra("ordMap");
        mEvent_id = getIntent().getStringExtra("id");
        flag = getIntent().getStringExtra("flag");
        String strTitle = getIntent().getStringExtra("className");
        String price = getIntent().getStringExtra("price");
        if ("0".equals(flag)) {
            isPayCourseOrAction = 0;
            tvProductway.setText("课程名称");
            Drawable drawable = getResources().getDrawable(R.drawable.icpon_xianxiake);
            tvProductway.setCompoundDrawablesWithIntrinsicBounds(drawable,
                    null, null, null);
            tvProductway.setCompoundDrawablePadding(4);
            tvProductway.setTextColor(Color.parseColor("#86B93F"));
            TextPaint tp1 = tvProductway.getPaint();
            tp1.setFakeBoldText(false);
            tvProductway.setVisibility(View.VISIBLE);
            tvProductexplain.setText(strTitle);
            TextPaint tp = tvProductexplain.getPaint();
            tp.setFakeBoldText(true);
            llUserList.setVisibility(View.GONE);
            tvprice.setText("￥" + price);
        }

        if (ordMap != null) {
            mEvent_id = ordMap.get("event_id");
            mUser_id = ordMap.get("user_id");
            isPayCourse = isPayCourseOrAction + "";
            if (mEvent_id!=null){
                join();
            }
            ContactEntity.InfoBean infoBean = new ContactEntity.InfoBean();
            infoBean.setName(ordMap.get("name"));
            infoBean.setPhone(ordMap.get("tel"));
            infoList.add(infoBean);
            mAdapter = new UserInfoAdapter(OrderActivity.this, infoList,"0");
            rvPersonlist.setAdapter(mAdapter);

        } else {
            mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
            infoList = (List<ContactEntity.InfoBean>) getIntent().getSerializableExtra("apply_list");

            if ("997".equals(mEvent_id)||"1407".equals(mEvent_id)){
                NetUtil.getRetrofit(Api.class).getTips("Event","getAuditInformation","2",Api.timeStr,Api.sign).enqueue(new Callback<TipsBean>() {
                    @Override
                    public void onResponse(Call<TipsBean> call, Response<TipsBean> response) {
                        if ("200".equals(response.body().getCode())){
                            tvTipproduct.setText(response.body().getInfo());
                        }
                    }

                    @Override
                    public void onFailure(Call<TipsBean> call, Throwable t) {

                    }
                });
            }else {
                NetUtil.getRetrofit(Api.class).getTips("Event","getAuditInformation","3",Api.timeStr,Api.sign).enqueue(new Callback<TipsBean>() {
                    @Override
                    public void onResponse(Call<TipsBean> call, Response<TipsBean> response) {
                        if ("200".equals(response.body().getCode())){
                            tvTipproduct.setText(response.body().getInfo());
                        }
                    }

                    @Override
                    public void onFailure(Call<TipsBean> call, Throwable t) {

                    }
                });
            }
            ordMap = getCheckParamMap();
            isPayCourse = isPayCourseOrAction + "";
            join();
            mAdapter = new UserInfoAdapter(OrderActivity.this, infoList,"1");
            rvPersonlist.setAdapter(mAdapter);

        }

    }

    /*点击事件设立*/
    private void initListener() {
        tvComit.setOnClickListener(this);
        payWeichat.setOnClickListener(this);
        payAlipay.setOnClickListener(this);
        coupon.setOnClickListener(this);
        titlebarbackssImageViewback.setOnClickListener(this);
    }

    private void setData() {

    }

    private PopPay mPopPay;
    private String mCouponId = "";
    private String mCouponCode = "";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comit:
                if ("0".equals(flag)) {
//                    ToastUtils.showToast(this, "aaa");
                    sumPay(ordMap, "");

                } else {
                    if (infoList.size() != 1) {
//                        ToastUtils.showToast(this, "bbb");
                        sumPay(getOrderMap(1), getBatchJoin());
                    } else {
//                        ToastUtils.showToast(this, "ccc");
                        pay();
                    }

                }
                break;
            case R.id.pay_weichat:
                payWay = 3;
                changePayWay(payWay);
                break;
            case R.id.pay_alipay:
                payWay = 2;
                changePayWay(payWay);
                break;
            case R.id.coupon:
                SelectCouponClick();
                break;
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
        }

    }

    /*拉起支付*/
    private void pay() {
        LoadingUtils.getInstance().showNetLoading(OrderActivity.this);


        mOrderPayPresenter.orderPay("1", mEvent_id, mUser_id, mCouponId, "0", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                PayEntity entity = (PayEntity) value[0];
                String fee = entity.getFee();
                LoadingUtils.getInstance().hideNetLoading();
                temporaryEventNo = mEvent_id;
                if (!"0".equals(fee)) {
                    if (entity.getStatus() != 0) {
                        mWechat = entity.getInfo().getWechat();
                        mAlipay = entity.getInfo().getAlipay();

                        if (payWay == WAY_WEICHAT) {
                            Toast.makeText(OrderActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
//                            finish();
                        } else if (payWay == WAY_ALIPAY) {
                            Toast.makeText(OrderActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                        }
                        mHandler.sendEmptyMessage(payWay);
                        if (mPopPay != null && mPopPay.isShowing()) {
                            mPopPay.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(OrderActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    intent = new Intent(OrderActivity.this, ResultActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("order_no", mEvent_id);
                    startActivity(intent);
                    isPayCourseOrAction = -1;
                    temporaryEventNo = "";
                    finish();
                }
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_PAY_WECHET", error);

            }
        });
    }

    private void sumPay(Map<String, String> map, String str) {

        mOrdersPayPresenter.orderPay(map, str, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                PayEntity entity = (PayEntity) value[0];


                String fee = entity.getFee();
                LoadingUtils.getInstance().hideNetLoading();
                if (!"0".equals(fee)) {
                    if (entity.getStatus() != 0) {
//                        if (mApplyType == 0) {
//                            temporaryEventNo = entity.getInfo().getOrder_no();
//                        } else {
////                            temporaryEventNo = mId;
//                        }
                        mWechat = entity.getInfo().getWechat();
                        mAlipay = entity.getInfo().getAlipay();
                        if (payWay == WAY_WEICHAT) {
                            Toast.makeText(OrderActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
//                            finish();
                        } else if (payWay == WAY_ALIPAY) {
                            Toast.makeText(OrderActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                        }
                        mHandler.sendEmptyMessage(payWay);


                    }
                } else {
                    Intent intent = new Intent(OrderActivity.this, ResultActivity.class);
                    intent.putExtra("type", mApplyType + "");
                    intent.putExtra("order_no", mUser_id);
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

    public void changePayWay(int payWay) {
        weichatSelect.setImageResource(mPaySelect[payWay == WAY_WEICHAT ? 1 : 0]);
        alipaySelect.setImageResource(mPaySelect[payWay == WAY_ALIPAY ? 1 : 0]);
    }

    public void SelectCouponClick() {
        Intent intent = new Intent(OrderActivity.this, SelectCouponActivity.class);
        intent.putExtra("course_price", money);
        intent.putExtra("coupon_id", mCouponId);
        intent.putExtra("id", mEvent_id);
        intent.putExtra("category", "2");
        startActivityForResult(intent, COUPON_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COUPON_REQUEST && resultCode == COUPON_RESULT) {
            mCouponId = data.getStringExtra("coupon_id");
            String coupon_price = data.getStringExtra("price");
            mCouponCode = data.getStringExtra("coupon_code");
            if (!"".equals(coupon_price)){
                tvprice.setText("￥" + (Double.parseDouble(money) - Double.parseDouble(coupon_price)));
                couponInfo.setText("-"+coupon_price+"元");
                couponInfo.setTextColor(Color.parseColor("#FFAC1C"));
            }else {
                tvprice.setText("￥"+Double.parseDouble(money));
                couponInfo.setText("不使用优惠券");
                couponInfo.setTextColor(Color.parseColor("#333333"));

            }
        }
    }

    private HashMap<String, String> getCheckParamMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("event_id", mEvent_id);
        map.put("name", "");
        map.put("user_id", mUser_id);
        map.put("ticket_id", "");
        map.put("coupon_code", "");

        String batch_join = getBatchJoin();
        map.put("batch_join", batch_join == null ? "" : batch_join);
        map.put("tel", "");
        map.put("brand", "");
        map.put("email", "");
        map.put("area", "");
        map.put("position", "");
        map.put("company", "1");
        return map;
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
//            Toast.makeText(MyApplication.mApplication, "参数异常", Toast.LENGTH_SHORT).show();
            return null;
        }
        map.put("goodsType", goodsType);
        map.put("id", mEvent_id);
        map.put("user_id", mUser_id);
        map.put("coupon_code", "");
        map.put("coupon_cut", mCouponId);
        map.put("pay_type", "4");
        map.put("company", "1");
        return map;
    }

    public String getBatchJoin() {
        StringBuffer batch_join = new StringBuffer();
        for (int i = 0; i < infoList.size(); i++) {
            ContactEntity.InfoBean infoBean = infoList.get(i);
            batch_join.append(infoBean.getPhone() + "=" + infoBean.getName());
            if (i != infoList.size() - 1) {
                batch_join.append("&");
            }
        }
        return batch_join.toString();
    }

    /*报名流程*/
    private void join() {
        /**
         * TODO 新报名流程
         */
        mCheckEventPresenter.checkEvent(ordMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                CheckEventEntity entity = (CheckEventEntity) value[0];
                Gson gson = new Gson();
                money = entity.getMoney();
                if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
                    checkEventEntity = entity;
                    if (isPayCourse.equals("1")) {
                        tvProductway.setText("课程名称");
                        Drawable drawable = getResources().getDrawable(R.drawable.icpon_xianxiake);
                        tvProductway.setCompoundDrawablesWithIntrinsicBounds(drawable,
                                null, null, null);
                        tvProductway.setCompoundDrawablePadding(4);
                        tvProductway.setTextColor(Color.parseColor("#86B93F"));
                        TextPaint tp1 = tvProductway.getPaint();
                        tp1.setFakeBoldText(false);
                        tvProductway.setVisibility(View.VISIBLE);
                        tvCheckway.setText("请核对报名者信息");
                        tvTipproduct.setText("请仔细核对手机号，勺子课堂登陆账号为上方手机号!");
                        TextPaint tp = tvProductexplain.getPaint();
                        tp.setFakeBoldText(true);
                        tvProductexplain.setText(checkEventEntity.getTitle());
                        tvprice.setText("￥" + checkEventEntity.getMoney());
                    } else if (mEvent_id.equals("1407")) {

                        tvProductway.setText("勺子课堂新知会员");
                        TextPaint tp1 = tvProductway.getPaint();
                        tp1.setFakeBoldText(false);
                        TextPaint tp = tvProductexplain.getPaint();
                        tp.setFakeBoldText(true);
                        tvProductway.setVisibility(View.VISIBLE);

                        tvProductexplain.setText(checkEventEntity.getTitle());
                        tvCheckway.setText("请核对开通会员信息");
                        tvTipproduct.setText("开通会员账号为上方手机号，仅限本人享受会员权益");
                        tvprice.setText("￥" + checkEventEntity.getMoney());
                    } else if (mEvent_id.equals("997")) {

                        tvProductway.setText("勺子课堂行动会员");
                        TextPaint tp1 = tvProductway.getPaint();
                        tp1.setFakeBoldText(false);
                        TextPaint tp = tvProductexplain.getPaint();
                        tp.setFakeBoldText(true);
                        tvProductway.setVisibility(View.VISIBLE);
                        tvProductexplain.setText(checkEventEntity.getTitle());
                        tvCheckway.setText("请核对开通会员信息");
                        tvTipproduct.setText("开通会员账号为上方手机号，仅限本人享受会员权益");
                        tvprice.setText("￥" + checkEventEntity.getMoney());

                    } else {
                        tvProductway.setText("课程名称");
                        Drawable drawable = getResources().getDrawable(R.drawable.icpon_xianxiake);
                        tvProductway.setCompoundDrawablesWithIntrinsicBounds(drawable,
                                null, null, null);
                        tvProductway.setCompoundDrawablePadding(4);
                        tvProductway.setTextColor(Color.parseColor("#86B93F"));
                        TextPaint tp1 = tvProductway.getPaint();
                        tp1.setFakeBoldText(false);
                        tvProductway.setVisibility(View.VISIBLE);

                        tvCheckway.setText("请核对报名者信息");
                        tvTipproduct.setText("请仔细核对手机号，勺子课堂登陆账号为上方手机号!");
                        TextPaint tp = tvProductexplain.getPaint();
                        tp.setFakeBoldText(true);
                        tvProductexplain.setText(checkEventEntity.getTitle());
                        tvprice.setText("￥" + checkEventEntity.getMoney());

                    }
                    LoadingUtils.getInstance().hideNetLoading();

                } else {
                    tvprice.setText("￥0");
                    tvComit.setClickable(false);
                    tvComit.setSelected(true);
                    tvCheckway.setText("请核对开通会员信息");
                    tvProductway.setText("勺子课堂新知会员");
                    tvProductway.setVisibility(View.VISIBLE);
                    tvProductexplain.setText("加入勺子课堂新知会员，享受更多特权！");
                    tvTipproduct.setText("开通会员账号为上方手机号，仅限本人享受会员权益");
                    LoadingUtils.getInstance().hideNetLoading();
                    Toast.makeText(OrderActivity.this, entity.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_EVENT", error);
                LoadingUtils.getInstance().hideNetLoading();
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageBean messageBean){
        if (messageBean.getMes().equals("mes")){
//            ToastUtils.showToast(this,"终于结束了！！！！");
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);

    }
}


