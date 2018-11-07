package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.alipay.PayResult;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.OrderBean;
import com.wh.wang.scroopclassproject.bean.TipsBean;
import com.wh.wang.scroopclassproject.newproject.eventmodel.EventMoreEntity;
import com.wh.wang.scroopclassproject.newproject.eventmodel.FinishTaskEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.SelectCouponActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventApplyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ProvinceEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EventApplyInfoPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.EventMoreAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.JsonFileReader;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.newproject.view.PopPay;
import com.wh.wang.scroopclassproject.newproject.view.PopTipVip;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

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
import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity.COUPON_RESULT;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.SDK_PAY_FLAG;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;

public class NewEventInfoActivity extends Activity implements View.OnClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private EditText mName;
    private EditText mTel;
    private EditText mEmail;
    private TextView mArea;
    private EditText mBrand;
    private EditText mStation;
    private EditText mAddress;
    private ImageView mNumAdd;
    private TextView mNum;
    private LinearLayout mApplyInfos;
    private RecyclerView mApplyList;
    private EditText mExpect;
    private RelativeLayout mAddressLoop;
    private ImageView mKeyCancel;
    private ImageView mKeyOk;
    private LoopView mProvinceLoop;
    private LoopView mCityLoop;
    private LoopView mDistrictLoop;
    private TextView mEventSubmit;
//    private ProgressBar mProgress;
    private TextView mManyApply;
    private PopTipVip popTipVip;


    private int mPhoneHight;

    private String mUser_id;

    //地区相关
    private List<String> mProvinceList = new ArrayList<>();
    private List<String> mCityList = new ArrayList<>();
    private List<String> mDistrictList = new ArrayList<>();
    private List<ProvinceEntity> mProvinceEntityList;
    private List<ProvinceEntity.CityBean> mCityBeanEntityList;

    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();
    private EventApplyInfoPresenter mEventApplyInfoPresenter = new EventApplyInfoPresenter();
//    private EventDetailEntity.InfoBean infoBean;
    private String mEvent_id;
    private double mEventPrice;

    //更多设置
    private List<EventMoreEntity> mMoreList = new ArrayList<>();
    private EventMoreAdapter mEventMoreAdapter;

    private String mEventTicketId = "";
    private PayEntity.InfoBean.WechatBean mWechat;
    private String mAlipay;
    private String mPhoneNum;


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
                        Intent intent = new Intent(NewEventInfoActivity.this, ResultActivity.class);
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
                                PayTask alipay = new PayTask(NewEventInfoActivity.this);
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
                    IWXAPI wxApi = WXAPIFactory.createWXAPI(NewEventInfoActivity.this, null);
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
    private double mSumPrice;
    private String mOriginalPrice;
    private String mReal_price;
    private String mVip_price;
    private String mIs_vip;
    private String mTitle;
    private String mIsPayCourseOrAction;
    private String strtips;
    private String strtips1;
    //    private String mOld_price;
    //    private DialogUtils mDialogUtils;
//    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_info);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initSize();
        initAddress();
        initListener();
    }

    private void initView() {
        mManyApply = (TextView) findViewById(R.id.many_apply);
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mName = (EditText) findViewById(R.id.name);
        mTel = (EditText) findViewById(R.id.tel);
        mEmail = (EditText) findViewById(R.id.email);
        mArea = (TextView) findViewById(R.id.area);
        mBrand = (EditText) findViewById(R.id.brand);
        mStation = (EditText) findViewById(R.id.station);
        mAddress = (EditText) findViewById(R.id.address);
        mNumAdd = (ImageView) findViewById(R.id.num_add);
        mNum = (TextView) findViewById(R.id.num);
        mApplyInfos = (LinearLayout) findViewById(R.id.apply_infos);
        mApplyList = (RecyclerView) findViewById(R.id.apply_list);
        mExpect = (EditText) findViewById(R.id.expect);
        mAddressLoop = (RelativeLayout) findViewById(R.id.address_loop);
        mKeyCancel = (ImageView) findViewById(R.id.key_cancel);
        mKeyOk = (ImageView) findViewById(R.id.key_ok);
        mProvinceLoop = (LoopView) findViewById(R.id.province_loop);
        mCityLoop = (LoopView) findViewById(R.id.city_loop);
        mDistrictLoop = (LoopView) findViewById(R.id.district_loop);
        mEventSubmit = (TextView) findViewById(R.id.event_submit);
//        mProgress = (ProgressBar) findViewById(R.id.progress);

        mApplyList.setLayoutManager(new LinearLayoutManager(this));
//        mApplyList.setAnimation(new DefaultItemAnimator());
    }

    private void initData() {
//        type = getIntent().getStringExtra("type");
//        infoBean = (EventDetailEntity.InfoBean) getIntent().getSerializableExtra("eventDetailBean");
        mPhoneNum = SharedPreferenceUtil.getStringFromSharedPreference(NewEventInfoActivity.this,"mobile","");
        mTitle = getIntent().getStringExtra("title");
        mEvent_id = getIntent().getStringExtra("event_id");
        mReal_price = getIntent().getStringExtra("real_price");
        mVip_price = getIntent().getStringExtra("vip_price");
        mOriginalPrice = getIntent().getStringExtra("price");
        mIs_vip = getIntent().getStringExtra("is_vip");
//        mOld_price = getIntent().getStringExtra("old_price");
        mIsPayCourseOrAction = getIntent().getStringExtra("isPayCourseOrAction");
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
//        mEvent_id = infoBean.getId() + "";
//        mOriginalPrice = infoBean.getPrice();
//        mEventTicketId = infoBean.getTicket_id();
        mTitlebarbackssName.setText("个人资料");
        if ("997".equals(mEvent_id)) {
            mEventPrice = Double.parseDouble(mReal_price);
        } else {
            if ("1".equals(mIs_vip)) {
                mEventPrice = Double.parseDouble(mVip_price);
            } else {
                mEventPrice = Double.parseDouble(mOriginalPrice);
            }
        }

        mEventApplyInfoPresenter.getEventApplyInfo(mEvent_id, mUser_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                EventApplyEntity entity = (EventApplyEntity) value[0];
                setDataForUI(entity);
                Gson gson = new Gson();
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_EVENT_INFO", error);
            }
        });
    }

    private void setDataForUI(EventApplyEntity entity) {
        if (StringUtils.isNotEmpty(entity.getName())) {
            mName.setText(entity.getName());
        }
        if (StringUtils.isNotEmpty(entity.getTel())) {
            mTel.setText(entity.getTel());
        }
//        if (StringUtils.isNotEmpty(entity.getEmail())) {
//            mEmail.setText(entity.getEmail());
//        }
        if (StringUtils.isNotEmpty(entity.getArea())) {
            mArea.setText(entity.getArea());
        }
        if (StringUtils.isNotEmpty(entity.getBrand())) {
            mBrand.setText(entity.getBrand());
        }
        if (StringUtils.isNotEmpty(entity.getPosition())) {
            mStation.setText(entity.getPosition());
        }
        if (StringUtils.isNotEmpty(entity.getAddress())) {
            mAddress.setText(entity.getAddress());
        }
        if (StringUtils.isNotEmpty(entity.getRemark())) {
            mExpect.setText(entity.getRemark());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mArea.setOnClickListener(this);
        mKeyOk.setOnClickListener(this);
        mKeyCancel.setOnClickListener(this);
        mAddressLoop.setOnClickListener(this);
        mNumAdd.setOnClickListener(this);
        mEventSubmit.setOnClickListener(this);
        mManyApply.setOnClickListener(this);
    }

    //TODO
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area:
                startKeyStoreAni();
                break;
            case R.id.key_ok:
                setAddressInfo();
                startKeyStoreAni();
                break;
            case R.id.key_cancel:
            case R.id.address_loop:
                startKeyStoreAni();
                break;
            case R.id.num_add:
//                showAddDialog(mEvent_id);
                break;
            case R.id.event_submit:

                checkApply();
                break;
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
            case R.id.many_apply:
                Intent intent = new Intent(this,SelectApplyActivity.class);
                intent.putExtra("id",mEvent_id);
                intent.putExtra("vip",mIs_vip);
                intent.putExtra("title",mTitle);
                intent.putExtra("apply_type",1);
//                intent.putExtra("original_price",infoBean.getOld_price());
                intent.putExtra("original_price",mOriginalPrice);
                startActivity(intent);
                break;
        }
    }

    //报名检查
    private void checkApply() {

        if (!StringUtils.isNotEmpty(mName)) {
            BToast.showText(NewEventInfoActivity.this, "请输入用户名!");
            return;
        }
        if (!StringUtils.isNotEmpty(mTel)) {
            BToast.showText(NewEventInfoActivity.this, "请输入手机号!");
            return;
        }
        if (!StringUtils.isPhoneNum(mTel.getText().toString())) {
            BToast.showText(NewEventInfoActivity.this, "请输入正确的手机号!");
            return;
        }
        if (!StringUtils.isNotEmpty(mArea)) {
            BToast.showText(NewEventInfoActivity.this, "请输入所在区域!");
            return;
        }
        if (!StringUtils.isNotEmpty(mBrand)) {
            BToast.showText(NewEventInfoActivity.this, "请输入品牌!");
            return;
        }
        if (!StringUtils.isNotEmpty(mStation)) {
            BToast.showText(NewEventInfoActivity.this, "请输入您的职位!");
            return;
        }
//        if(mDialogUtils==null){
//            mDialogUtils = new DialogUtils(this);
//        }
//        mDialogUtils.showLoading();
//        LoadingUtils.getInstance().showNetLoading(this);
//        Toast.makeText(MyApplication.mApplication, "正在获取订单信息", Toast.LENGTH_SHORT).show();
        mSumPrice = 0;
        for (int i = 0; i < mMoreList.size(); i++) {
            mSumPrice += Double.parseDouble(mMoreList.get(i).getPrice());
        }
        mSumPrice += mEventPrice;


      if (isPayCourseOrAction==4){
          if (mPhoneNum.equals(mTel.getText().toString())){
              if (isPayCourseOrAction==1){
                  Intent intent = new Intent(NewEventInfoActivity.this,OrderActivity.class);
                  intent.putExtra("ordMap",(Serializable) getParamMap());
                  startActivity(intent);
                  NewEventInfoActivity.this.finish();
              }else if (isPayCourseOrAction==4){
                  Intent intent = new Intent(NewEventInfoActivity.this,OrderActivity.class);
                  intent.putExtra("ordMap",(Serializable) getParamMap());
                  startActivity(intent);
              }

          }else {

              final View pop = View.inflate(NewEventInfoActivity.this, R.layout.pop_tipvip, null);
              TextView phoneNum = (TextView) pop.findViewById(R.id.tv_phoneNum);
              TextView back = (TextView) pop.findViewById(R.id.tv_back);
              TextView con = (TextView) pop.findViewById(R.id.tv_continue);
              final TextView tips = (TextView)pop.findViewById(R.id.tv_tips);
              final TextView tips1 = (TextView) pop.findViewById(R.id.tv_tips1);
              phoneNum.setText("当前登录绑定账号："+mPhoneNum);
              NetUtil.getRetrofit(Api.class).getTips("Event","getAuditInformation","1",Api.timeStr,Api.sign).enqueue(new Callback<TipsBean>() {
                  @Override
                  public void onResponse(Call<TipsBean> call, Response<TipsBean> response) {
                      if ("200".equals(response.body().getCode())){
                          tips.setText(response.body().getInfo());
                          tips1.setText(response.body().getMessage());
                      }
                  }

                  @Override
                  public void onFailure(Call<TipsBean> call, Throwable t) {

                  }
              });
              final PopupWindow popupWindow = new PopupWindow(pop, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
              popupWindow.setFocusable(true);
              popupWindow.setOutsideTouchable(true);
              popupWindow.setBackgroundDrawable(new BitmapDrawable());
              popupWindow.setAnimationStyle(R.style.popwin_anim_style);
              popupWindow.showAtLocation(findViewById(R.id.activity_new_event_info), Gravity.BOTTOM, 0, 0);

              //背景变暗
              WindowManager.LayoutParams lp = getWindow().getAttributes();
              lp.alpha = 0.4f;
              getWindow().setAttributes(lp);
              //设置popupWindow消失时的监听
              popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                  //在dismiss中恢复透明度
                  public void onDismiss() {
                      WindowManager.LayoutParams lp = getWindow().getAttributes();
                      lp.alpha = 1f;
                      getWindow().setAttributes(lp);
                  }
              });
              back.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      popupWindow.dismiss();
                  }
              });
              con.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (isPayCourseOrAction==1){
                          Intent intent = new Intent(NewEventInfoActivity.this,OrderActivity.class);
                          intent.putExtra("ordMap",(Serializable) getParamMap());
                          startActivity(intent);
                          NewEventInfoActivity.this.finish();
                      }else if (isPayCourseOrAction==4){
                          Intent intent = new Intent(NewEventInfoActivity.this,OrderActivity.class);
                          intent.putExtra("ordMap",(Serializable) getParamMap());
                          startActivity(intent);
                      }
                  }
              });
          }
      }else {
          if (isPayCourseOrAction==1){
              Intent intent = new Intent(NewEventInfoActivity.this,OrderActivity.class);
              intent.putExtra("ordMap",(Serializable) getParamMap());
              startActivity(intent);
              NewEventInfoActivity.this.finish();
          }else if (isPayCourseOrAction==4){
              Intent intent = new Intent(NewEventInfoActivity.this,OrderActivity.class);
              intent.putExtra("ordMap",(Serializable) getParamMap());
              startActivity(intent);
          }

      }

    }

    public void startKeyStoreAni() {
        ObjectAnimator animator;
        if (mAddressLoop.getVisibility() == View.GONE) {
            mAddressLoop.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(mAddressLoop, "translationY", mPhoneHight, 0);
            animator.start();//开始动画
        } else {
            mAddressLoop.setVisibility(View.GONE);
            animator = ObjectAnimator.ofFloat(mAddressLoop, "translationY", mPhoneHight - DisplayUtil.dip2px(MyApplication.getApplication(), 216), mPhoneHight);
            animator.start();//开始动画
        }
    }

    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneHight = d.heightPixels;
    }

    private void setAddressInfo() {
        String pro = mProvinceList.get(mProvinceLoop.getSelectedItem());
        String city = mCityList.get(mCityLoop.getSelectedItem());
        String dis = mDistrictList.get(mDistrictLoop.getSelectedItem());
        if ("北京市".equals(pro) || "上海市".equals(pro) || "重庆市".equals(pro) || "天津市".equals(pro) || "香港".equals(pro) || "澳门".equals(pro) || "台湾省".equals(pro)) {
            mArea.setText(city + " ");
        } else {
            mArea.setText(pro + " " + city);
        }
    }

    private void initAddress() {
        mProvinceLoop.setNotLoop();
        mCityLoop.setNotLoop();
        mDistrictLoop.setNotLoop();

        mProvinceLoop.setTextSize(13);
        mCityLoop.setTextSize(13);
        mDistrictLoop.setTextSize(13);
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(this, "province_data.json");
        //  解析json数据
        if (province_data_json != null && province_data_json.length() > 0) {
            Gson gson = new Gson();
            mProvinceEntityList = gson.fromJson(province_data_json, new TypeToken<List<ProvinceEntity>>() {
            }.getType());

            mProvinceLoop.setItems(getProvince());
            mProvinceLoop.setOuterTextColor(getResources().getColor(R.color.white_color_aplha));
            mProvinceLoop.setCenterTextColor(getResources().getColor(R.color.black));

            mCityLoop.setItems(getCity(0));
            mCityLoop.setOuterTextColor(getResources().getColor(R.color.white_color_aplha));
            mCityLoop.setCenterTextColor(getResources().getColor(R.color.black));

            mDistrictLoop.setItems(getDistrict(0));
            mDistrictLoop.setOuterTextColor(getResources().getColor(R.color.white_color_aplha));
            mDistrictLoop.setCenterTextColor(getResources().getColor(R.color.black));

            mProvinceLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mCityLoop.setItems(getCity(index));
                    mCityLoop.setCurrentPosition(0);
                    mDistrictLoop.setItems(getDistrict(0));
                    mDistrictLoop.setCurrentPosition(0);
                }
            });

            mCityLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mDistrictLoop.setItems(getDistrict(index));
                    mDistrictLoop.setCurrentPosition(0);
                }
            });

            mDistrictLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {

                }
            });
        }

    }

    public List<String> getProvince() {
        for (int i = 0; i < mProvinceEntityList.size(); i++) {
            mProvinceList.add(mProvinceEntityList.get(i).getName());
        }
        return mProvinceList;
    }

    public List<String> getCity(int index) {
        mCityList.clear();
        mCityBeanEntityList = mProvinceEntityList.get(index).getCity();
        for (int i = 0; i < mCityBeanEntityList.size(); i++) {
            mCityList.add(mCityBeanEntityList.get(i).getName());
        }
        return mCityList;
    }

    public List<String> getDistrict(int index) {
        mDistrictList.clear();
        for (int i = 0; i < mCityBeanEntityList.get(index).getArea().size(); i++) {
            mDistrictList.add(mCityBeanEntityList.get(index).getArea().get(i));
        }
        return mDistrictList;
    }

//    private void showAddDialog(final String event_id) {
//        final Dialog d = new Dialog(this, R.style.MyDialog);
//        d.setContentView(R.layout.event_dialog_new);
//        final EditText name = (EditText) d.findViewById(R.id.name);
//        final EditText tel = (EditText) d.findViewById(R.id.tel);
//        TextView addUser = (TextView) d.findViewById(R.id.add_user);
//        StringUtils.setEditTextInhibitInputSpace(name);
//        StringUtils.setEditTextInhibitInputSpace(tel);
//        addUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String dialogName = name.getText() != null ? name.getText().toString() : "";
//                final String dialogTel = tel.getText() != null ? tel.getText().toString() : "";
//                if (TextUtils.isEmpty(dialogName)) {
//                    BToast.showText(NewEventInfoActivity.this, "输入姓名不能为空");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(dialogTel)) {
//                    BToast.showText(NewEventInfoActivity.this, "输入手机号不能为空!");
//                    return;
//                }
//
//                if (!StringUtils.isPhoneNum(dialogTel)) {
//                    BToast.showText(NewEventInfoActivity.this, "请输入正确的手机号!");
//                    return;
//                }
//                /**
//                 * TODO 添加更多检查
//                 */
//                mCheckEventPresenter.checkMoreEvent(dialogTel, event_id, "", mUser_id, new OnResultListener() {
//
//                    @Override
//                    public void onSuccess(Object... value) {
//                        CheckEventEntity entity = (CheckEventEntity) value[0];
//                        Log.e("DH_CHECK_MORE", "success" + entity.getStatus());
//                        if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
//                            EventMoreEntity eventMoreEntity = new EventMoreEntity();
//                            eventMoreEntity.setName(dialogName);
//                            eventMoreEntity.setTel(dialogTel);
//                            eventMoreEntity.setPrice(entity.getInfo());
//                            if (mMoreList == null) {
//                                mMoreList = new ArrayList<>();
//                            }
//                            mApplyInfos.setVisibility(View.VISIBLE);
//                            mMoreList.add(eventMoreEntity);
//                            mNum.setText((mMoreList.size() + 1) + "");
//                            if (mEventMoreAdapter == null) {
//                                mEventMoreAdapter = new EventMoreAdapter(mMoreList);
//                                mApplyList.setAdapter(mEventMoreAdapter);
//                                mEventMoreAdapter.setOnEventMoreDeleteClickListener(new EventMoreAdapter.OnEventMoreDeleteClickListener() {
//                                    @Override
//                                    public void onMoreDeleteClick(int pos) {
//                                        if (mMoreList != null && mMoreList.size() > 0) {
//                                            mMoreList.remove(pos);
//                                            mNum.setText((mMoreList.size() + 1) + "");
//                                            if (mMoreList.size() == 0) {
//                                                mApplyInfos.setVisibility(View.GONE);
//                                            }
//                                            mEventMoreAdapter.notifyDataSetChanged();
//                                        }
//                                    }
//                                });
//                            } else {
//                                mEventMoreAdapter.notifyDataSetChanged();
//                            }
//                            d.dismiss();
//                        } else {
//                            Toast.makeText(NewEventInfoActivity.this, entity.getInfo(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFault(String error) {
//                        Toast.makeText(NewEventInfoActivity.this, "添加异常", Toast.LENGTH_SHORT).show();
//                        Log.e("DH_CHECK_MORE", error);
//                    }
//                });
//            }
//        });
//        d.show();
//    }

    private PopPay mPopPay;
    private String mCouponId = "";
    private String mCouponCode = "";
    public void showPayPop(final String money) {
        mPopPay = new PopPay(this, mTitle, money+"");
        mPopPay.showAtLocation(findViewById(R.id.activity_new_event_info), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
            public void onPayClick(final int payWay) {
                temporaryEventNo = mEvent_id;
                LoadingUtils.getInstance().showNetLoading(NewEventInfoActivity.this);
                mOrderPayPresenter.orderPay("1", mEvent_id, mUser_id, mCouponId,"0", new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        PayEntity entity = (PayEntity) value[0];
                        String fee = entity.getFee();
                        LoadingUtils.getInstance().hideNetLoading();
                        if (!"0".equals(fee)) {
                            Gson gson = new Gson();
                            if(entity.getStatus()!=0){
//                                temporaryEventNo = entity.getInfo().getOrder_no();
                                mWechat = entity.getInfo().getWechat();
                                mAlipay = entity.getInfo().getAlipay();

                                if (payWay == WAY_WEICHAT) {
                                    Toast.makeText(NewEventInfoActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (payWay == WAY_ALIPAY) {
                                    Toast.makeText(NewEventInfoActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                                }
                                mHandler.sendEmptyMessage(payWay);
                                if (mPopPay != null && mPopPay.isShowing()) {
                                    mPopPay.dismiss();
                                }
                            }
                        } else {
                            Toast.makeText(NewEventInfoActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
                            Intent intent;
                            intent = new Intent(NewEventInfoActivity.this, ResultActivity.class);
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

            @Override
            public void onSelectCouponClick() {
                Intent intent = new Intent(NewEventInfoActivity.this, SelectCouponActivity.class);
                intent.putExtra("course_price",money);
                intent.putExtra("coupon_id",mCouponId);
                intent.putExtra("id",mEvent_id);
                intent.putExtra("category","2");
                startActivityForResult(intent,COUPON_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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


    private Map<String, String> getParamMap() {
        Map<String, String> map = new HashMap<>();
        map.put("event_id", mEvent_id);
        map.put("name", mName.getText().toString());
        map.put("user_id", mUser_id);
        map.put("ticket_id", mEventTicketId);
        map.put("coupon_code", "");
        map.put("coupon_cut", "");

        StringBuffer batch_join = new StringBuffer();
        for (int i = 0; i < mMoreList.size(); i++) {
            EventMoreEntity moreEntity = mMoreList.get(i);
            batch_join.append(moreEntity.getTel()+"="+moreEntity.getName());
            if(i!=mMoreList.size()-1){
                batch_join.append("&");
            }
        }
        map.put("batch_join", batch_join.toString()==null?"":batch_join.toString());
        map.put("tel", mTel.getText().toString());
        map.put("brand", mBrand.getText().toString());
        map.put("email", mEmail.getText().toString());
        map.put("area", mArea.getText().toString());
        map.put("position", mStation.getText().toString());

        return map;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishTask(FinishTaskEntity entity){
        if(this!=null){
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mHandler!=null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
