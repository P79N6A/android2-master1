package com.wh.wang.scroopclassproject.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.wh.wang.scroopclassproject.bean.AddPersonBean;
import com.wh.wang.scroopclassproject.bean.EventFromInfo;
import com.wh.wang.scroopclassproject.bean.EventGetMoneyBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ProvinceEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.OrderPayPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.ResultActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.JsonFileReader;
import com.wh.wang.scroopclassproject.newproject.view.PopPay;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.LogUtil;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.views.CenterDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;
import static com.wh.wang.scroopclassproject.utils.Constants.batch_join;

public class EventFromActivity extends Activity implements CenterDialog.OnCenterItemClickListener, View.OnClickListener {

    private EventDetailEntity.InfoBean infoBean;
    private Intent intent;
    private EventFromInfo eventFromInfo;
    private EditText event_from_intex;
    private EditText event_from_phone;
    private EditText event_from_email;
    private TextView event_from_area;
    private EditText event_from_pai;
    private EditText event_from_work;
    private EditText event_from_address;
    private EditText event_from_idea;
    private Button event_submit;
    private String nameInput;
    private String telInput;
    private String emailInput;
    private String areaInput;
    private String brandInput;
    private String positionInput;
    private String addressInput;
    private String remarkInput;
    private CenterDialog centerDialog;

    private ImageView event_from_person_add;
    private TextView event_from_person_tv_nums;
    private List<AddPersonBean> addpersonLists;
    private RelativeLayout event_from_rlayout_person_title;
    private LinearLayout event_from_llayout_person;
    private View childView;
    private LayoutInflater inflater;
    private int mark = 0;
    private int counts = 1;

    /**
     * 地址键盘
     */
    private RelativeLayout mAddressLoop;
    private ImageView mKeyCancel;
    private ImageView mKeyOk;
    private LoopView mProvinceLoop;
    private LoopView mCityLoop;
    private LoopView mDistrictLoop;
    private List<ProvinceEntity> mProvinceEntityList;
    private List<ProvinceEntity.CityBean> mCityBeanEntityList;
    private List<String> mProvinceList = new ArrayList<>();
    private List<String> mCityList = new ArrayList<>();
    private List<String> mDistrictList = new ArrayList<>();
    private int mPhoneHight;

    private RelativeLayout mPiaoquan;
    private RelativeLayout mPiaoquan1;
    private RelativeLayout mPiaoquan2;
    private TextView mPq1Title;
    private TextView mPq1Info;
    private TextView mPq1Price;
    private TextView mPq2Title;
    private TextView mPq2Info;
    private TextView mPq2Price;



    /**
     * 检查报名
     */
    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
    private OrderPayPresenter mOrderPayPresenter = new OrderPayPresenter();

    //微信支付
//    PayReq req;
//    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    Map<String, String> resultunifiedorder;
    StringBuffer sb;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String user_id;

    private int payFlags = 0;

    //TODO WHWHW
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(EventFromActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //// eventUpdatePay String userid, String id, double price, String batch_join, int num,
//                        requestAliPayZhifuState(2, outTradeNo);

                        BToast.showText(EventFromActivity.this, "支付成功", true);
                        Constants.eventFlag = 200;
                        Intent intent = new Intent(EventFromActivity.this, ResultActivity.class);
                        intent.putExtra("type",String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no",String.valueOf(temporaryEventNo));
                        startActivity(intent);
//                        isPayCourseOrAction = -1;
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(EventFromActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String eventTitle;
    private double eventPrice;
    private String eventTicketId;
    private AlertDialog dialog;
    private double sumMoney;
    private String outTradeNo;
    private String out_trade_no;
    private String event_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.event_from, "报名详情");
        //微信支付
        sb = new StringBuffer();
//        req = new PayReq();
//        msgApi.registerApp(Constants.APP_ID);

        initView();
        initData();
        initAddress();
        initListener();
    }

    private void initListener() {
        event_from_area.setOnClickListener(this);
        mKeyOk.setOnClickListener(this);
        mKeyCancel.setOnClickListener(this);
        mAddressLoop.setOnClickListener(this);
        mPiaoquan1.setOnClickListener(this);
        mPiaoquan2.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.weixinFlag == 0) {
            Constants.weixinFlag = 10;
            BToast.showText(EventFromActivity.this, "支付成功", true);
            Constants.eventFlag = 200;
            finish();
//            requestAliPayZhifuState(1, out_trade_no);
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {

        event_from_intex = (EditText) findViewById(R.id.event_from_intex); //联系人
        event_from_phone = (EditText) findViewById(R.id.event_from_phone);//手机号
        event_from_email = (EditText) findViewById(R.id.event_from_email);//邮箱
        event_from_area = (TextView) findViewById(R.id.event_from_area);//区域
        event_from_pai = (EditText) findViewById(R.id.event_from_pai);//品牌
        event_from_work = (EditText) findViewById(R.id.event_from_work);//工作
        event_from_address = (EditText) findViewById(R.id.event_from_address);//详细地址
        event_from_person_add = (ImageView) findViewById(R.id.event_from_person_add);
        event_from_person_tv_nums = (TextView) findViewById(R.id.event_from_person_tv_nums);
        event_from_rlayout_person_title = (RelativeLayout) findViewById(R.id.event_from_rlayout_person_title);
        event_from_rlayout_person_title.setVisibility(View.GONE);
        event_from_llayout_person = (LinearLayout) findViewById(R.id.event_from_llayout_person);
        event_from_llayout_person.setVisibility(View.GONE);

        addpersonLists = new ArrayList<AddPersonBean>();// 保存View的实例
        // 布局选择器
        inflater = LayoutInflater.from(getApplicationContext());
        childView = inflater.inflate(R.layout.add_person_view, null);

        event_from_idea = (EditText) findViewById(R.id.event_from_idea);//建议
        event_submit = (Button) findViewById(R.id.event_submit);//提交
        centerDialog = new CenterDialog(EventFromActivity.this, R.layout.dialog_layout, new int[]{R.id.dialog_ll_cancel, R.id.dialog_ll_sure});
        centerDialog.setOnCenterItemClickListener(EventFromActivity.this);


        mAddressLoop = (RelativeLayout) findViewById(R.id.address_loop);
        mKeyCancel = (ImageView) findViewById(R.id.key_cancel);
        mKeyOk = (ImageView) findViewById(R.id.key_ok);
        mProvinceLoop = (LoopView) findViewById(R.id.province_loop);
        mCityLoop = (LoopView) findViewById(R.id.city_loop);
        mDistrictLoop = (LoopView) findViewById(R.id.district_loop);

        mPiaoquan = (RelativeLayout) findViewById(R.id.piaoquan);
        mPiaoquan1 = (RelativeLayout) findViewById(R.id.piaoquan_1);
        mPiaoquan2 = (RelativeLayout) findViewById(R.id.piaoquan_2);

        mPq1Title = (TextView) findViewById(R.id.pq_1_title);
        mPq1Info = (TextView) findViewById(R.id.pq_1_info);
        mPq1Price = (TextView) findViewById(R.id.pq_1_price);
        mPq2Title = (TextView) findViewById(R.id.pq_2_title);
        mPq2Info = (TextView) findViewById(R.id.pq_2_info);
        mPq2Price = (TextView) findViewById(R.id.pq_2_price);

    }
    private void showAddDialog(final String event_id){
        final Dialog d = new Dialog(this,R.style.MyDialog);
        d.setContentView(R.layout.event_dialog_new);
        final EditText name = (EditText) d.findViewById(R.id.name);
        final EditText tel = (EditText) d.findViewById(R.id.tel);
        TextView addUser = (TextView) d.findViewById(R.id.add_user);
        StringUtils.setEditTextInhibitInputSpace(name);
        StringUtils.setEditTextInhibitInputSpace(tel);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dialogName = name.getText().toString();
                final String dialogTel = tel.getText().toString();
                if (TextUtils.isEmpty(dialogName)) {
                    BToast.showText(EventFromActivity.this, "输入姓名不能为空");
                    return;
                }

                if (TextUtils.isEmpty(dialogTel)) {
                    BToast.showText(EventFromActivity.this, "输入手机号不能为空!");
                    return;
                }

                if (!StringUtils.isPhoneNum(dialogTel)) {
                    BToast.showText(EventFromActivity.this, "请输入正确的手机号!");
                    return;
                }
                /**
                 * TODO 添加更多检查
                 */
                mCheckEventPresenter.checkMoreEvent(dialogTel, event_id, "",user_id, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        CheckEventEntity entity = (CheckEventEntity) value[0];
                        Log.e("DH_CHECK_MORE","success"+entity.getStatus());
                        if(entity.getStatus()==1||"1".equals(entity.getStatus())){
                            mark++;
                            counts++;
                            event_from_person_tv_nums.setText(counts + "");
                            event_from_rlayout_person_title.setVisibility(View.VISIBLE);
                            event_from_llayout_person.setVisibility(View.VISIBLE);

                            // 新增布局必须每次获取一下布局，否则不能作区分
                            childView = inflater.inflate(R.layout.add_person_view, null);
                            event_from_llayout_person.addView(childView, mark - 1);
                            saveViewInstance(childView, mark - 1, dialogName, dialogTel, entity.getInfo());// 实例化View
                            d.dismiss();
                        }else{
                            Toast.makeText(EventFromActivity.this, entity.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFault(String error) {
                        Toast.makeText(EventFromActivity.this,"添加异常", Toast.LENGTH_SHORT).show();
                        Log.e("DH_CHECK_MORE",error);
                    }
                });
            }
        });
        d.show();
    }
    private void showAlertDialog(final String event_id) {

        dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogStyle)).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setView(LayoutInflater.from(this).inflate(R.layout.event_dialog, null));
        dialog.show();
        dialog.getWindow().setContentView(R.layout.event_dialog);
        final EditText event_dialog_name = (EditText) dialog.findViewById(R.id.event_dialog_name);
        StringUtils.setEditTextInhibitInputSpace(event_dialog_name);
        final EditText event_dialog_tel = (EditText) dialog.findViewById(R.id.event_dialog_tel);
        StringUtils.setEditTextInhibitInputSpace(event_dialog_tel);
        RelativeLayout btnPositive = (RelativeLayout) dialog.findViewById(R.id.event_dialog_rlayout_bottom);
        ImageView btnNegative = (ImageView) dialog.findViewById(R.id.event_dialog_close);

        btnPositive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String dialogName = event_dialog_name.getText().toString().trim();
                final String dialogTel = event_dialog_tel.getText().toString().trim();
                if (TextUtils.isEmpty(dialogName)) {
                    BToast.showText(EventFromActivity.this, "输入姓名不能为空");
                    return;
                }

                if (TextUtils.isEmpty(dialogTel)) {
                    BToast.showText(EventFromActivity.this, "输入手机号不能为空!");
                    return;
                }

                if (!StringUtils.isPhoneNum(dialogTel)) {
                    BToast.showText(EventFromActivity.this, "请输入正确的手机号!");
                    return;
                }

//                setGetMoneyFromNet(dialogName, dialogTel, "4", event_id);
                /**
                 * TODO 添加更多检查
                 */
                mCheckEventPresenter.checkMoreEvent(dialogTel, event_id, "",user_id, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        CheckEventEntity entity = (CheckEventEntity) value[0];
                        Log.e("DH_CHECK_MORE","success"+entity.getStatus());
                        if(entity.getStatus()==1||"1".equals(entity.getStatus())){
                            mark++;
                            counts++;
                            event_from_person_tv_nums.setText(counts + "");
                            event_from_rlayout_person_title.setVisibility(View.VISIBLE);
                            event_from_llayout_person.setVisibility(View.VISIBLE);

                            // 新增布局必须每次获取一下布局，否则不能作区分
                            childView = inflater.inflate(R.layout.add_person_view, null);
                            event_from_llayout_person.addView(childView, mark - 1);
                            saveViewInstance(childView, mark - 1, dialogName, dialogTel, entity.getInfo());// 实例化View
                            dialog.dismiss();
                        }else{
                            Toast.makeText(EventFromActivity.this, entity.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFault(String error) {
                        Toast.makeText(EventFromActivity.this,"添加异常", Toast.LENGTH_SHORT).show();
                        Log.e("DH_CHECK_MORE",error);
                    }
                });

            }

        });

        btnNegative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

    private void setGetMoneyFromNet(String dialogName, String event_dialog_tel, String src, String event_id) {
        HttpUserManager.getInstance().getMoney(dialogName, event_dialog_tel, src, event_id, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler2.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, EventGetMoneyBean.class);
    }

    private Handler mHandler2 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Object obj = msg.obj;
//            Log.e("whwh", "obj===>" + obj.toString());
//            try {
//                JSONObject jsonObject = new JSONObject(obj.toString());
//                int loginCode = jsonObject.optInt("code");
//                Log.e("whwh", "loginCode=" + loginCode);
//                if (loginCode == 200) {
//                    String msgStrs = jsonObject.optString("msg");
//                    JSONObject infoObj = new JSONObject(obj.toString()).getJSONObject("info");
//                    Double price = infoObj.getDouble("price");
//                    String user_name = infoObj.getString("user_name");
//                    String mobile = infoObj.getString("mobile");
//                    Log.e("whwh", "price==" + price + "user_name==" + user_name);
//                    //TODO WH
//                    mark++;
//                    counts++;
//                    event_from_person_tv_nums.setText(counts + "");
//                    event_from_rlayout_person_title.setVisibility(View.VISIBLE);
//                    event_from_llayout_person.setVisibility(View.VISIBLE);
//
//                    // 新增布局必须每次获取一下布局，否则不能作区分
//                    childView = inflater.inflate(R.layout.add_person_view, null);
//                    event_from_llayout_person.addView(childView, mark - 1);
//                    saveViewInstance(childView, mark - 1, user_name, mobile, price);// 实例化View
//                    dialog.dismiss();
//                } else {
//                    String msgStrs = jsonObject.optString("msg");
//                    BToast.showText(EventFromActivity.this, msgStrs);
//                    dialog.dismiss();
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    };

    //childView,indexs,dialogName,dialogTel
    private void saveViewInstance(View childView, int indexs, String dialogName, String dialogTel, String price) {
        AddPersonBean addBean = new AddPersonBean();
        addBean.setId(indexs);

        RelativeLayout add_person_rlayout = (RelativeLayout) childView.findViewById(R.id.add_person_rlayout);
        LinearLayout add_person_llayout = (LinearLayout) childView.findViewById(R.id.add_person_llayout);

        TextView add_person_tv_name = (TextView) childView.findViewById(R.id.add_person_tv_name);
        add_person_tv_name.setText(dialogName);
        TextView add_person_tv_tel = (TextView) childView.findViewById(R.id.add_person_tv_tel);
        add_person_tv_tel.setText(dialogTel);

        ImageView add_person_iv_delete_tag = (ImageView) childView.findViewById(R.id.add_person_iv_delete_tag);
        TextView add_person_tv_money = (TextView) childView.findViewById(R.id.add_person_tv_money);
        add_person_tv_money.setText(price + "元");

        //设置监听事件
        //设置监听事件
        DeletePersonListener delListen = new DeletePersonListener();
        delListen.setIndex(indexs);
        add_person_iv_delete_tag.setOnClickListener(delListen);

        addBean.setDelListen(delListen);

        addBean.setAdd_person_rlayout(add_person_rlayout);
        addBean.setAdd_person_llayout(add_person_llayout);
        addBean.setAdd_person_tv_name(add_person_tv_name);
        addBean.setAdd_person_tv_tel(add_person_tv_tel);
        addBean.setAdd_person_iv_delete_tag(add_person_iv_delete_tag);
        addBean.setAdd_person_tv_money(add_person_tv_money);

        addpersonLists.add(addBean);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.event_from_area:
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
            case R.id.piaoquan_1:
                changePQ(0,1);
                break;
            case R.id.piaoquan_2:
                changePQ(1,0);
                break;
        }
    }


    public class DeletePersonListener implements View.OnClickListener {

        private int indexs;

        public DeletePersonListener() {

        }

        public void setIndex(int index) {
            this.indexs = index;
        }

        @Override
        public void onClick(View v) {

            if (event_from_llayout_person.getChildCount() > 0) {
                // 删除动态生成View的最后一条
                //event_from_llayout_person.removeViewAt(event_from_llayout_person.getChildCount() - 1);

                if (event_from_llayout_person.getChildCount() > indexs) {
                    event_from_llayout_person.removeViewAt(indexs);
                    Log.e("whwh", "indexs=" + indexs + "removeViewAt" + event_from_llayout_person.getChildCount());
                }

                if (addpersonLists.size() > 0) {
                    addpersonLists.remove(indexs);
                }

                int start = indexs;
                for (int i = start; i < event_from_llayout_person.getChildCount(); i++) {

                    AddPersonBean addBean = addpersonLists.get(i);
                    addBean.setId(i);
                    DeletePersonListener dellisten = addBean.getDelListen();
                    dellisten.setIndex(i);
                }

                mark--;
                counts--;

                event_from_person_tv_nums.setText(counts + "");

                if (counts == 1) {
                    event_from_rlayout_person_title.setVisibility(View.GONE);
                }
            }
        }
    }


    /**
     * 初始化数据
     */
    private void initData() {
        intent = getIntent();
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        infoBean = (EventDetailEntity.InfoBean) getIntent().getSerializableExtra("eventDetailBean");
        event_id = infoBean.getId()+"";
        eventTitle = infoBean.getTitle();

        if("997".equals(event_id)){
            eventPrice = Double.parseDouble(infoBean.getReal_price());
        }else{
            if ("1".equals(infoBean.getIsvip())) {
                eventPrice = Double.parseDouble(infoBean.getVip_price());
            } else {
                eventPrice = Double.parseDouble(infoBean.getPrice());
            }
        }

        eventTicketId = infoBean.getTicket_id();
        event_from_person_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog(event_id);
            }
        });

        getDataFromNet(event_id, user_id);
    }

    private void getDataFromNet(String event_id, String user_id) {
        //联网
        //视频内容
        RequestParams params = new RequestParams(Constants.eventFromlUrl);
        params.addBodyParameter("id", event_id);
        params.addBodyParameter("user_id", user_id);
        String timeStr = StringUtils.getTimestamp();
        params.addBodyParameter("shijian", timeStr);
        params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getDataFromNet---联网成功---result===" + result);
                //主线程
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getDataFromNet---联网失败---" + ex.getMessage());
                ToastUtils.showToast(EventFromActivity.this, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getDataFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getDataFromNet---onFinished---");
            }
        });

    }

    //处理数据
    private void processData(String result) {
        eventFromInfo = parseJsonInfo(result);
        setData();
    }

    /**
     * 设置数据
     */
    private void setData() {
        //event_from_intex,event_from_phone,event_from_email,event_from_area,event_from_pai,event_from_work,
        // event_from_address,event_from_idea,event_from_lessmoney
        String name = eventFromInfo.getName();
        if (StringUtils.isNotEmpty(name)) {
            event_from_intex.setText(name);
        }
        String tel = eventFromInfo.getTel();
        if (StringUtils.isNotEmpty(tel)) {
            event_from_phone.setText(tel);
        }
        String email = eventFromInfo.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            event_from_email.setText(email);
        }
        String area = eventFromInfo.getArea();
        if (StringUtils.isNotEmpty(area)) {
            event_from_area.setText(area);
        }
        String brand = eventFromInfo.getBrand();
        if (StringUtils.isNotEmpty(brand)) {
            event_from_pai.setText(brand);
        }
        String position = eventFromInfo.getPosition();
        if (StringUtils.isNotEmpty(position)) {
            event_from_work.setText(position);
        }
        String address = eventFromInfo.getAddress();
        if (StringUtils.isNotEmpty(address)&&!"null".equals(address)) {
            event_from_address.setText(address);
        }
        String remark = eventFromInfo.getRemark();
        if (StringUtils.isNotEmpty(remark)&&!"null".equals(remark)) {
            event_from_idea.setText(remark);
        }

        setListener();
    }

    //event_from_intex,event_from_phone,event_from_email,event_from_area,event_from_pai,event_from_work,
    // event_from_address,event_from_idea,event_from_lessmoney
    private PayEntity.InfoBean.WechatBean mWechat;
    private String mAlipay;

    private void setListener() {
        //nameInput,telInput,emailInput,areaInput,brandInput,positionInput,addressInput,remarkInput,lessmoneyInput

        event_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameInput = event_from_intex.getText().toString().trim();
                telInput = event_from_phone.getText().toString().trim();
                emailInput = event_from_email.getText().toString().trim();
                areaInput = event_from_area.getText().toString().trim();
                brandInput = event_from_pai.getText().toString().trim();
                positionInput = event_from_work.getText().toString().trim();
                addressInput = event_from_address.getText().toString().trim();
                remarkInput = event_from_idea.getText().toString().trim();
                SharedPreferenceUtil.getStringFromSharedPreference(EventFromActivity.this,"mobile","");

                StringBuffer sBuffer = new StringBuffer();
                Constants.batch_join = "";

                List<Double> moneyList = new ArrayList<Double>();

                //getAdd_person_tv_tel
                if (addpersonLists.size() > 0 && addpersonLists != null) {
                    for (int i = 0; i < addpersonLists.size(); i++) {
                        AddPersonBean bean = addpersonLists.get(i);
                        sBuffer.append(bean.getAdd_person_tv_tel().getText().toString())
                                .append("=")
                                .append(bean.getAdd_person_tv_name().getText().toString()).append("&");
                        Constants.batch_join = sBuffer.toString();
                        //str.substring(0, str.indexOf("#"))
                        String tvMoney = bean.getAdd_person_tv_money().getText().toString();
                        double getMoney = Double.valueOf(tvMoney.substring(0, tvMoney.indexOf("元")));
                        moneyList.add(getMoney);

                    }
                    batch_join.trim();

                }

                Log.e("whwh", "batch_join==" + batch_join);

                sumMoney = 0.0;
                for (int i = 0; i < moneyList.size(); i++) {
                    sumMoney += moneyList.get(i);
                }

                Log.e("whwh", "submitData==>money" + sumMoney);

                Log.e("whwh", "submitData==>batch_join" + batch_join);
                //BToast.showText(EventFromActivity.this, "batch_join==" + batch_join);
                if (TextUtils.isEmpty(nameInput)) {
                    BToast.showText(EventFromActivity.this, "请输入用户名!");
                    return;
                }

                if (TextUtils.isEmpty(telInput)) {
                    BToast.showText(EventFromActivity.this, "请输入手机号!");
                    return;
                }

                if (!StringUtils.isPhoneNum(telInput)) {
                    BToast.showText(EventFromActivity.this, "请输入正确的手机号!");
                    return;
                }


                if (TextUtils.isEmpty(emailInput)) {
                    BToast.showText(EventFromActivity.this, "请输入邮箱!");
                    return;
                }

                if (TextUtils.isEmpty(areaInput)) {
                    BToast.showText(EventFromActivity.this, "请输入所在区域!");
                    return;
                }

                if (TextUtils.isEmpty(brandInput)) {
                    BToast.showText(EventFromActivity.this, "请输入品牌!");
                    return;
                }

                if (TextUtils.isEmpty(positionInput)) {
                    BToast.showText(EventFromActivity.this, "请输入您的职位!");
                    return;
                }

                //nameInput,telInput,emailInput,areaInput,brandInput,positionInput,addressInput,remarkInput,lessmoneyInput
//                submitData(batch_join, nameInput, telInput, emailInput, areaInput, brandInput, positionInput, addressInput, remarkInput);
                /**
                 * TODO 新报名流程
                 */
                mCheckEventPresenter.checkEvent(getParamMap(), new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        Log.e("DH_EVENT","success");
                        CheckEventEntity entity = (CheckEventEntity) value[0];

                        if(entity.getStatus()==1||"1".equals(entity.getStatus())){
//                            startPay();

                            mOrderPayPresenter.orderPay("1", event_id, user_id, "","0", new OnResultListener() {

                                @Override
                                public void onSuccess(Object... value) {
                                    Log.e("DH_PAY_WECHET","success");
                                    PayEntity entity = (PayEntity) value[0];
//                                    PayEntity.InfoBean.WechatBean wechat = entity.getInfo().getWechat();
                                    String fee = entity.getFee();

                                    if(!"0".equals(fee)){
                                        temporaryEventNo = entity.getInfo().getOrder_no();
                                        mWechat = entity.getInfo().getWechat();
                                        mAlipay = entity.getInfo().getAlipay();
                                        showPayPop();
                                    }else{
                                        Toast.makeText(EventFromActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EventFromActivity.this, ResultActivity.class);
                                        intent.putExtra("type","1");
                                        intent.putExtra("order_no",String.valueOf(temporaryEventNo));
                                        startActivity(intent);
//                                        isPayCourseOrAction = -1;
                                        temporaryEventNo = "";
                                        finish();
                                    }

                                }

                                @Override
                                public void onFault(String error) {
                                    Log.e("DH_PAY_WECHET",error);
                                }
                            });
                        }else{
                            Toast.makeText(EventFromActivity.this, entity.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFault(String error) {
                        Log.e("DH_EVENT",error);
                    }
                });
            }

            private void submitData(String batch_join, String nameInput, String telInput, String emailInput, String areaInput, String brandInput, String positionInput, String addressInput,
                                    String remarkInput) {
                //联网
                //视频内容
                RequestParams params = new RequestParams(Constants.eventSubmitUrl);
                params.addBodyParameter("id", infoBean.getId() + "");
                params.addBodyParameter("user_id", user_id);
                params.addBodyParameter("batch_join", batch_join);
                params.addBodyParameter("ticket_id", "");
                params.addBodyParameter("coupon_code", "");
                params.addBodyParameter("brand", brandInput);
                params.addBodyParameter("email", emailInput);
                params.addBodyParameter("area", areaInput);
                params.addBodyParameter("position", positionInput);
                params.addBodyParameter("remark", remarkInput);
                params.addBodyParameter("address", addressInput);
                params.addBodyParameter("tel", telInput);
                params.addBodyParameter("name", nameInput);
                params.addBodyParameter("ticket_id", eventTicketId);
                params.addBodyParameter("coupon_code", "");
                params.addBodyParameter("order_app", "4");
                params.addBodyParameter("order_app", "4");
                String timeStr = StringUtils.getTimestamp();
                params.addBodyParameter("shijian", timeStr);
                params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("whwh", "submitData--submitData---联网成功---result===" + result);
                        //主线程
                        processSubmitData(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("whwh", "submitData---联网失败---" + ex.getMessage());
                        ToastUtils.showToast(EventFromActivity.this, "联网失败!");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.e("whwh", "submitData---onCancelled---" + cex.getMessage());
                    }

                    @Override
                    public void onFinished() {
                        Log.e("whwh", "submitData---onFinished---");
                    }
                });
            }
        });

    }

    /**
     * 报名成功后发起支付
     */
    private void startPay() {
        centerDialog.show();
        TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
        TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
        TextView detail_dialog_worning = (TextView) centerDialog.findViewById(R.id.detail_dialog_worning);
        detail_dialog_worning.setVisibility(View.INVISIBLE);
        detail_dialog_price.setText("¥ " + (eventPrice + sumMoney));
        detail_dialog_title.setText(eventTitle);
    }

    private Map<String,String> getParamMap() {
        Map<String,String> map = new HashMap<>();
        map.put("event_id",event_id);
        map.put("name",nameInput);
        map.put("user_id",user_id);
        map.put("ticket_id",eventTicketId);
        map.put("coupon_code","");
        map.put("batch_join",Constants.batch_join);
        map.put("tel",telInput);
        map.put("brand",brandInput);
        map.put("email",emailInput);
        map.put("area",areaInput);
        map.put("position",positionInput);
        return map;
    }


    /**
     * 提交数据
     */
    private void processSubmitData(String result) {
        try {
            ArrayList list = new ArrayList();
            Gson gson = new Gson();
            list = gson.fromJson(result, ArrayList.class);
            String data = gson.toJson(list.get(0));
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.optInt("code");
            String msg = jsonObject.optString("msg");
            if (code == 200) {
                ToastUtils.showToast(EventFromActivity.this, msg);
                centerDialog.show();
                TextView detail_dialog_price = (TextView) centerDialog.findViewById(R.id.detail_dialog_price);
                TextView detail_dialog_title = (TextView) centerDialog.findViewById(R.id.detail_dialog_title);
                TextView detail_dialog_worning = (TextView) centerDialog.findViewById(R.id.detail_dialog_worning);
                detail_dialog_worning.setVisibility(View.INVISIBLE);
                detail_dialog_price.setText("¥ " + (eventPrice + sumMoney));
                detail_dialog_title.setText(eventTitle);

            } else {
                ToastUtils.showToast(EventFromActivity.this, msg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnCenterItemClick(CenterDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.dialog_ll_cancel:
                aliPay();
                break;

            case R.id.dialog_ll_sure:
                weichatPay();
                break;
            default:
                break;
        }
    }


    private void aliPay() {
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            finish();
//                        }
//                    }).show();
//            return;
//        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0); //payPrice
//
//        outTradeNo = OrderInfoUtil2_0.getOutTradeNo();
//        Log.e("zhifubao", "outTradeNo=outTradeNo=outTradeNo=" + outTradeNo);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, payPrice, info_title + "视频课程费用", info_title, outTradeNo, rsa2);
//
//        //添加数据
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(EventFromActivity.this);
                Map<String, String> result = alipay.payV2(mAlipay, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
//        mOrderPayPresenter.orderPay("1", event_id, user_id, "", new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//                Log.e("DH_PAY_ALIPAY","success");
//                PayEntity entity = (PayEntity) value[0];
//                String fee = entity.getFee();
//                if(!"0".equals(fee)){
//                    final String orderInfo = entity.getInfo().getAlipay();
//                    temporaryOrderNo = entity.getInfo().getOrder_no();
//                    Runnable payRunnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            PayTask alipay = new PayTask(EventFromActivity.this);
//                            Map<String, String> result = alipay.payV2(orderInfo, true);
//                            Log.i("msp", result.toString());
//                            Message msg = new Message();
//                            msg.what = SDK_PAY_FLAG;
//                            msg.obj = result;
//                            mHandler.sendMessage(msg);
//                        }
//                    };
//
//                    Thread payThread = new Thread(payRunnable);
//                    payThread.start();
//                }else{
//                    Intent intent = new Intent(EventFromActivity.this, ResultActivity.class);
//                    intent.putExtra("type","1");
//                    intent.putExtra("order_no",String.valueOf(temporaryEventNo));
//                    startActivity(intent);
//                    isPayCourseOrAction = -1;
//                    temporaryEventNo = "";
//                    finish();
//                }
//
//            }
//
//            @Override
//            public void onFault(String error) {
//                Log.e("DH_PAY_ALIPAY",error);
//            }
//        });
    }


    /**
     * 支付宝支付后上传到服务器
     * // eventUpdatePay String userid, String id, double price, String batch_join, int num,
     */

//    private void requestAliPayZhifuState(int num, String tradeNo) {
//
//        Log.e("whwh", "requestAliPayZhifuState==>money" + (infoBean.getPrice() + sumMoney));
//        Log.e("whwh", "requestAliPayZhifuState==>batch_join" + batch_join);
//        HttpUserManager.getInstance().eventUpdatePay(user_id, infoBean.getId() + "", infoBean.getPrice() + sumMoney, batch_join, num, tradeNo, new GetDataListener() {
//
//            @Override
//            public void onSuccess(Object data) {
//                Message obtain = Message.obtain();
//                obtain.obj = data;
//                mHandler3.sendMessage(obtain);
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//
//            }
//        }, ChangePassBean.class);
//
//    }

    private Handler mHandler3 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                String msgStr = jsonObject.optString("msg");
                if (code == 200) {
                    BToast.showText(EventFromActivity.this, msgStr, true);
                    Constants.eventFlag = 200;
                    finish();
                } else {
                    BToast.showText(EventFromActivity.this, msgStr);
                    Constants.eventFlag = 400;
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private EventFromInfo parseJsonInfo(String json) {
        try {

            JSONObject jsonObject = new JSONObject(json);
            eventFromInfo = new EventFromInfo();
            String event_from_address = jsonObject.optString("address");
            String event_from_area = jsonObject.optString("area");
            String event_from_brand = jsonObject.optString("brand"); //品牌
            String event_from_email = jsonObject.optString("email");
            String event_from_name = jsonObject.optString("name");
            String event_from_position = jsonObject.optString("position");
            String event_from_remark = jsonObject.optString("remark");//评论
            String event_from_tel = jsonObject.optString("tel");

            eventFromInfo.setAddress(event_from_address);
            eventFromInfo.setArea(event_from_area);
            eventFromInfo.setBrand(event_from_brand);
            eventFromInfo.setEmail(event_from_email);
            eventFromInfo.setName(event_from_name);
            eventFromInfo.setPosition(event_from_position);
            eventFromInfo.setRemark(event_from_remark);
            eventFromInfo.setTel(event_from_tel);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventFromInfo;
    }

    /**
     * 微信支付
     */
    private void weichatPay() {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(EventFromActivity.this, null);
        wxApi.registerApp(Constants.APP_ID);
        PayReq req = new PayReq();
        req.appId = mWechat.getAppid();
        req.nonceStr = mWechat.getNoncestr();
        req.packageValue = mWechat.getPackageX();
        req.prepayId = mWechat.getPrepayid();
        req.partnerId = mWechat.getPartnerid();
        req.timeStamp = mWechat.getTimestamp();
        req.sign = mWechat.getSign();
        boolean b = wxApi.sendReq(req);
        finish();
//        mOrderPayPresenter.orderPay("1", event_id, user_id, "", new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//                Log.e("DH_PAY_WECHET","success");
//                PayEntity entity = (PayEntity) value[0];
//                PayEntity.InfoBean.WechatBean wechat = entity.getInfo().getWechat();
//                String fee = entity.getFee();
//
//                if(!"0".equals(fee)){
//                    temporaryOrderNo = entity.getInfo().getOrder_no();
//                    IWXAPI wxApi = WXAPIFactory.createWXAPI(EventFromActivity.this, null);
//                    wxApi.registerApp(Constants.APP_ID);
//                    PayReq req = new PayReq();
//                    req.appId = wechat.getAppid();
//                    req.nonceStr = wechat.getNoncestr();
//                    req.packageValue = wechat.getPackageX();
//                    req.prepayId = wechat.getPrepayid();
//                    req.partnerId = wechat.getPartnerid();
//                    req.timeStamp = wechat.getTimestamp();
//                    req.sign = wechat.getSign();
//                    boolean b = wxApi.sendReq(req);
//                    finish();
//                }else{
//                    Intent intent = new Intent(EventFromActivity.this, ResultActivity.class);
//                    intent.putExtra("type","1");
//                    intent.putExtra("order_no",String.valueOf(temporaryEventNo));
//                    startActivity(intent);
//                    isPayCourseOrAction = -1;
//                    temporaryEventNo = "";
//                    finish();
//                }
//
//            }
//
//            @Override
//            public void onFault(String error) {
//                Log.e("DH_PAY_WECHET",error);
//            }
//        });
    }

//    private void genPayReq() {
////
////        req.appId = Constants.APP_ID;
////        req.partnerId = Constants.MCH_ID;
////        req.prepayId = resultunifiedorder.get("prepay_id");
////        req.packageValue = "Sign=WXPay";
////        req.nonceStr = genNonceStr();
////        req.timeStamp = String.valueOf(genTimeStamp());
////
////
////        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
////        signParams.add(new BasicNameValuePair("appid", req.appId));
////        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
////        signParams.add(new BasicNameValuePair("package", req.packageValue));
////        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
////        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
////        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
////
////        req.sign = genAppSign(signParams);
////        sb.append("sign\n" + req.sign + "\n\n");
////        Log.e("orion", signParams.toString());
//    }
//
//    private String genAppSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.API_KEY);
//
//        this.sb.append("sign str\n" + sb.toString() + "\n\n");
//        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", appSign);
//        return appSign;
//    }
//
//    //生成订单
//    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
//
//        private ProgressDialog dialog;
//
//
//        @Override
//        protected void onPreExecute() {
//            // dialog = ProgressDialog.show(VideoDetailActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
//        }
//
//        @Override
//        protected void onPostExecute(Map<String, String> result) {
//            // if (dialog != null) {
//            //   dialog.dismiss();
//            //}
//            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
//
//            resultunifiedorder = result;
//
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected Map<String, String> doInBackground(Void... params) {
//
//            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            String entity = genProductArgs();
//
//            Log.e("orion", entity);
//
//            byte[] buf = Util.httpPost(url, entity);
//
//            String content = new String(buf);
//            Log.e("orion", content);
//            Map<String, String> xml = decodeXml(content);
//
//            return xml;
//        }
//    }
//
//    private String genProductArgs() {
//        StringBuffer xml = new StringBuffer();
//
//        try {
//            String nonceStr = genNonceStr();
//            xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID)); //APPID
//            packageParams.add(new BasicNameValuePair("body", eventTitle));//内容信息
//            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
//            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//            packageParams.add(new BasicNameValuePair("notify_url", "http://php.shaoziketang.com/callback/weChat"));
//            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
//            packageParams.add(new BasicNameValuePair("spbill_create_ip", "106.120.116.198"));
//            BigDecimal totalFeeBig = new BigDecimal(eventPrice);
//            int totalFee = totalFeeBig.multiply(new BigDecimal(100)).intValue();
//            //String.valueOf(totalFee)
//            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(totalFee)));
//            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//            String sign = genPackageSign(packageParams);
//            packageParams.add(new BasicNameValuePair("sign", sign));
//
//
//            String xmlstring = toXml(packageParams);
//
//            return new String(xmlstring.toString().getBytes(), "ISO-8859-1");
//
//        } catch (Exception e) {
//            Log.e("whwh", "genProductArgs fail, ex = " + e.getMessage());
//            return null;
//        }
//    }
//
//    public Map<String, String> decodeXml(String content) {
//
//        try {
//            Map<String, String> xml = new HashMap<String, String>();
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(new StringReader(content));
//            int event = parser.getEventType();
//            while (event != XmlPullParser.END_DOCUMENT) {
//
//                String nodeName = parser.getName();
//                switch (event) {
//                    case XmlPullParser.START_DOCUMENT:
//
//                        break;
//                    case XmlPullParser.START_TAG:
//
//                        if ("xml".equals(nodeName) == false) {
//                            //实例化student对象
//                            xml.put(nodeName, parser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                }
//                event = parser.next();
//            }
//
//            return xml;
//        } catch (Exception e) {
//            Log.e("orion", e.toString());
//        }
//        return null;
//
//    }
//
//    private String genNonceStr() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    private long genTimeStamp() {
//        return System.currentTimeMillis() / 1000;
//    }
//
//
//    private String genOutTradNo() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    /**
//     * 生成签名
//     */
//
//    private String genPackageSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.API_KEY);
//
//
//        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", packageSign);
//        return packageSign;
//    }
//
//    private String toXml(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<xml>");
//        for (int i = 0; i < params.size(); i++) {
//            sb.append("<" + params.get(i).getName() + ">");
//
//
//            sb.append(params.get(i).getValue());
//            sb.append("</" + params.get(i).getName() + ">");
//
//            if (params.get(i).getName().equals("out_trade_no")) {
//                out_trade_no = params.get(i).getValue();
//                Log.e("weixin", "out_trade_no==" + out_trade_no);
//            }
//        }
//        sb.append("</xml>");
//
//        Log.e("orion", sb.toString());
//        return sb.toString();
//    }

    public void startKeyStoreAni(){
        ObjectAnimator animator;
        if(mAddressLoop.getVisibility()==View.GONE){
            mAddressLoop.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(mAddressLoop, "translationY",mPhoneHight,0);
            animator.start();//开始动画
        }else{
            mAddressLoop.setVisibility(View.GONE);
            animator = ObjectAnimator.ofFloat(mAddressLoop, "translationY",mPhoneHight- DisplayUtil.dip2px(MyApplication.getApplication(),216),mPhoneHight);
            animator.start();//开始动画
        }
    }

    private void setAddressInfo() {
        String pro = mProvinceList.get(mProvinceLoop.getSelectedItem());
        String city = mCityList.get(mCityLoop.getSelectedItem());
        String dis = mDistrictList.get(mDistrictLoop.getSelectedItem());
        if("北京市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)||"天津市".equals(pro)||"香港".equals(pro)||"澳门".equals(pro)||"台湾省".equals(pro)){
            event_from_area.setText(city+" "+dis);
        }else{
            event_from_area.setText(pro+" "+city);
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
        if(province_data_json!=null&&province_data_json.length()>0){
            Gson gson = new Gson();
            mProvinceEntityList = gson.fromJson(province_data_json,new TypeToken<List<ProvinceEntity>>() {}.getType());

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

    public List<String> getProvince(){
        for (int i = 0; i < mProvinceEntityList.size(); i++) {
            mProvinceList.add(mProvinceEntityList.get(i).getName());
        }
        return mProvinceList;
    }

    public List<String> getCity(int index){
        mCityList.clear();
        mCityBeanEntityList = mProvinceEntityList.get(index).getCity();
        for (int i = 0; i < mCityBeanEntityList.size(); i++) {
            mCityList.add(mCityBeanEntityList.get(i).getName());
        }
        return mCityList;
    }

    public List<String> getDistrict(int index){
        mDistrictList.clear();
        for (int i = 0; i < mCityBeanEntityList.get(index).getArea().size(); i++) {
            mDistrictList.add(mCityBeanEntityList.get(index).getArea().get(i));
        }
        return mDistrictList;
    }

    private int []PQBackground = {R.drawable.piaoquan_select,R.drawable.piaoquan_unselect};
    private int []PQColorPrice = {R.color.pq_price_1,R.color.pq_price_2};
    private int []PQColorTitle = {R.color.pq_price_1,R.color.black};
    private int []PQColorInfo = {R.color.pq_price_1,R.color.pq_info};
    public void changePQ(int p1,int p2){
        mPiaoquan1.setBackgroundDrawable(getResources().getDrawable(PQBackground[p1]));
        mPiaoquan2.setBackgroundDrawable(getResources().getDrawable(PQBackground[p2]));
        mPq1Title.setTextColor(getResources().getColor(PQColorTitle[p1]));
        mPq2Title.setTextColor(getResources().getColor(PQColorTitle[p2]));
        mPq1Info.setTextColor(getResources().getColor(PQColorInfo[p1]));
        mPq2Info.setTextColor(getResources().getColor(PQColorInfo[p2]));
        mPq1Price.setTextColor(getResources().getColor(PQColorPrice[p1]));
        mPq2Price.setTextColor(getResources().getColor(PQColorPrice[p2]));
    }
    private PopPay mPopPay;
    public void showPayPop() {
        mPopPay = new PopPay(this,eventTitle,(eventPrice + sumMoney)+"");
        mPopPay.showAtLocation(findViewById(R.id.event_from_activity), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        mPopPay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mPopPay.setOnPayClickListener(new PopPay.OnPayClickListener() {
            @Override
            public void onPayClick(int payWay) {
//                isPayCourseOrAction = 1;
                temporaryEventNo = event_id;
                if(payWay==WAY_WEICHAT){
                    Toast.makeText(EventFromActivity.this, "正在前往微信..", Toast.LENGTH_SHORT).show();
                    weichatPay();
                }else if(payWay ==WAY_ALIPAY){
                    Toast.makeText(EventFromActivity.this, "正在前往支付宝..", Toast.LENGTH_SHORT).show();
                    aliPay();
                }
                if(mPopPay!=null&&mPopPay.isShowing()){
                    mPopPay.dismiss();
                }
            }

            @Override
            public void onSelectCouponClick() {

            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        if(mPopPay!=null&&mPopPay.isShowing()){
            mPopPay.dismiss();
        }
    }
}
