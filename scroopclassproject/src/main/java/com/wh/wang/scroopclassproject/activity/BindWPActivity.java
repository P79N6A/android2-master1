package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.LoginBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

public class BindWPActivity extends Activity {

    private EditText et_bindwp_nike;
    private EditText et_bindwp_phone;
    private TextView tv_bindwp_phone;
    private EditText et_bindwp_yzm;
    private RelativeLayout rlayout_bindwp_sure;
    private TextView rlayout_bindwp_error;
    private Context context;
    private TimeCount time;
    private TimeBtnCount btntime;
    private ImageView iv_bindwp_sure;
    private TextView tv_bindwp_sure;
    private String nickname;
    private String user_id;
    private String rand_str;
    private AlertDialog dialog;
    private String bindwp_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.bind_wp, "绑定手机");
        time = new TimeCount(60000, 1000);
        btntime = new TimeBtnCount(3000, 1000);
        context = this;
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void finish() {
        super.finish();
        MobclickAgent.onPause(this);
        String mobile = SharedPreferenceUtil.getStringFromSharedPreference(this, "mobile", "");
        if(StringUtils.isEmpty(mobile)){
            Log.e("DH_CLEAR","user_id : "+SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", ""));
            SharedPreferenceUtil.clearInfo(this);
            Log.e("DH_CLEAR","user_id"+SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", ""));
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        et_bindwp_nike = (EditText) findViewById(R.id.et_bindwp_nike);//获取昵称
        et_bindwp_phone = (EditText) findViewById(R.id.et_bindwp_phone);//获取手机号
        tv_bindwp_phone = (TextView) findViewById(R.id.tv_bindwp_phone);//手机验证码发送
        et_bindwp_yzm = (EditText) findViewById(R.id.et_bindwp_yzm);//获取验证码
        rlayout_bindwp_sure = (RelativeLayout) findViewById(R.id.rlayout_bindwp_sure);//确定绑定
        iv_bindwp_sure = (ImageView) findViewById(R.id.iv_bindwp_sure);//确定绑定
        tv_bindwp_sure = (TextView) findViewById(R.id.tv_bindwp_sure);//确定绑定
        rlayout_bindwp_error = (TextView) findViewById(R.id.rlayout_bindwp_error);//错误提示
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //昵称
        nickname = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
        et_bindwp_nike.setText(nickname);

        tv_bindwp_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_bindwp_phone.getText().toString().trim();
                if (StringUtils.isNotEmpty(phone)) {
                    time.start();
                    getCodeFromNet(phone);
                } else {
                    BToast.showText(BindWPActivity.this, "手机号不能为空!");
                }
            }
        });

        rlayout_bindwp_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bindwp_nike = et_bindwp_nike.getText().toString().trim();
                bindwp_phone = et_bindwp_phone.getText().toString().trim();
                String bindwp_yzm = et_bindwp_yzm.getText().toString().trim(); //密码验证
                if (TextUtils.isEmpty(bindwp_phone)) {
                    errMsg("请输入手机号!");
                    return;
                }

                if (!StringUtils.isPhoneNum(bindwp_phone)) {
                    errMsg("请输入正确的手机号!");
                    return;
                }

                if (TextUtils.isEmpty(bindwp_yzm)) {
                    errMsg("请输入验证码!");
                    return;
                }
                KeyBoardUtils.hideKeyboard(BindWPActivity.this);
                startBindWPHttp(bindwp_nike, bindwp_phone, bindwp_yzm);
            }
        });

    }

    private void startBindWPHttp(String bindwp_nike, String bindwp_phone, String bindwp_yzm) {
        HttpUserManager.getInstance().bindWP(bindwp_nike, bindwp_phone, bindwp_yzm, user_id, rand_str, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
                Log.e("whwh", "onSuccess===data==" + data.toString());
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, LoginBean.class);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int loginCode = jsonObject.optInt("status");
                Log.e("whwh", "loginCode=" + loginCode);
                if (loginCode == 1) {
                    BToast.showText(BindWPActivity.this, "绑定成功!");
                    SharedPreferenceUtil.putStringValueByKey(context, "mobile", bindwp_phone);
//                    new GiftBagPresenter().getGiftStatus(user_id, new OnResultListener() {
//                        @Override
//                        public void onSuccess(Object... value) {
//                            GiftBagStatusEntity giftBagStatusEntity = (GiftBagStatusEntity) value[0];
//                            String gift_bag = giftBagStatusEntity.getCode()==1?"1":"0";
//                            SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication,"gift_bag",gift_bag);
//                            if (giftBagStatusEntity.getCode()==1) { //code 为新用户
//                                Intent intent = new Intent(BindWPActivity.this, GiftBagActivity.class);
//                                startActivity(intent);
//                            }
//                            finish();
//                        }
//
//                        @Override
//                        public void onFault(String error) {
//                           finish();
//                        }
//                    });
                    finish();
                } else {
                    //TODO WH
                    String info = jsonObject.getString("info");
                    dialog = new AlertDialog.Builder(new ContextThemeWrapper(BindWPActivity.this, R.style.DialogStyle)).create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setView(LayoutInflater.from(BindWPActivity.this).inflate(R.layout.login_dialog, null));
                    dialog.show();
                    dialog.getWindow().setContentView(R.layout.login_dialog);
                    TextView tv_login_dialog_tip = (TextView) dialog.findViewById(R.id.tv_login_dialog_tip);
                    tv_login_dialog_tip.setText(info);
                    RelativeLayout rlayout_login_dialog = (RelativeLayout) dialog.findViewById(R.id.rlayout_login_dialog);

                    rlayout_login_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 错误提示信息
     */
    private void errMsg(String info) {
        btntime.start();
        rlayout_bindwp_error.setVisibility(View.VISIBLE);
        rlayout_bindwp_error.setText(info);
    }


    /**
     * 发送 验证码
     */
    private void getCodeFromNet(String mobile) {
        RequestParams params = new RequestParams(Constants.bindCodeUrl);
        params.addQueryStringParameter("mobile", mobile);
        params.addQueryStringParameter("unbind", "0");
        String timeStr = StringUtils.getTimestamp();
        params.addQueryStringParameter("shijian", timeStr);
        params.addQueryStringParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));
        Log.e("whwh", "params==" + params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonCodeObj = new JSONObject(result);
                    int statusMsg = jsonCodeObj.optInt("status");
                    String infoMsg = jsonCodeObj.optString("info");
                    if (statusMsg == 1) {
                        ToastUtils.showToast(BindWPActivity.this, "验证码发送成功!");
                    } else {
                        ToastUtils.showToast(BindWPActivity.this, infoMsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getCodeFromNet---联网失败---" + ex.getMessage());
                rlayout_bindwp_error.setVisibility(View.VISIBLE);
                rlayout_bindwp_error.setText("网络异常");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getCodeFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getCodeFromNet---onFinished---");
            }
        });
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //login_msg_code.setBackgroundColor(Color.parseColor("#B6B6D8"));
            tv_bindwp_phone.setTextColor(Color.parseColor("#8dc63f"));
            tv_bindwp_phone.setClickable(false);
            tv_bindwp_phone.setText("重发(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            tv_bindwp_phone.setTextColor(Color.parseColor("#85BC44"));
            tv_bindwp_phone.setText("重新获取");
            tv_bindwp_phone.setClickable(true);
        }
    }


    //登录按钮倒计时
    class TimeBtnCount extends CountDownTimer {

        public TimeBtnCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            rlayout_bindwp_sure.setBackgroundResource(R.drawable.button_pass_error_selector);
            iv_bindwp_sure.setVisibility(View.VISIBLE);
            rlayout_bindwp_error.setVisibility(View.VISIBLE);
            tv_bindwp_sure.setVisibility(View.GONE);
        }

        @Override
        public void onFinish() {
            rlayout_bindwp_sure.setBackgroundResource(R.drawable.button_pass_selector);
            iv_bindwp_sure.setVisibility(View.GONE);
            rlayout_bindwp_error.setVisibility(View.GONE);
            tv_bindwp_sure.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            SharedPreferenceUtil.putStringValueByKey(context, "username", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "area", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "avatar", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "brand", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "is_vip", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "mobile", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "nickname", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "openid", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "password", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "position", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "user_id", "");
//            SharedPreferenceUtil.putStringValueByKey(context, "login_rand_str", "");
//        }

        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onStop() {
        super.onStop();

    }
}
