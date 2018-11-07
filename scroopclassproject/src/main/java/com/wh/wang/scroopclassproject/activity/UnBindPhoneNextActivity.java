package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.YZMBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.Constants;
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

public class UnBindPhoneNextActivity extends Activity {

    private TimeCount time;
    private TimeBtnCount btntime;
    private EditText et_unbind_phone_next_phone;
    private TextView tv_unbind_phone_next;
    private EditText et_unbind_phone_next_yzm;
    private RelativeLayout rlayout_unbind_phone_next_sure;
    private TextView tv_unbind_phone_next_sure;
    private ImageView iv_unbind_phone_next_sure;
    private TextView tv_unbind_phone_next_error;
    private String user_id;
    private String rand_str;
    private AlertDialog dialog;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(UnBindPhoneNextActivity.this, R.layout.unbind_phone_next, "更换手机");
        time = new TimeCount(60000, 1000);
        btntime = new TimeBtnCount(3000, 1000);
        initView();
        initData();
        initListener();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        et_unbind_phone_next_phone = (EditText) findViewById(R.id.et_unbind_phone_next_phone); //输入手机号
        tv_unbind_phone_next = (TextView) findViewById(R.id.tv_unbind_phone_next);//发送验证码
        et_unbind_phone_next_yzm = (EditText) findViewById(R.id.et_unbind_phone_next_yzm);//输入验证码
        rlayout_unbind_phone_next_sure = (RelativeLayout) findViewById(R.id.rlayout_unbind_phone_next_sure); //确认绑定
        tv_unbind_phone_next_sure = (TextView) findViewById(R.id.tv_unbind_phone_next_sure);//确认绑定
        iv_unbind_phone_next_sure = (ImageView) findViewById(R.id.iv_unbind_phone_next_sure);//错误
        tv_unbind_phone_next_error = (TextView) findViewById(R.id.tv_unbind_phone_next_error);//错误

    }

    /**
     * 初始化数据
     */
    private void initData() {
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(UnBindPhoneNextActivity.this, "user_id", "");
        rand_str = SharedPreferenceUtil.getStringFromSharedPreference(UnBindPhoneNextActivity.this, "login_rand_str", "");
    }


    /**
     * 初始化监听事件
     */
    private void initListener() {

        tv_unbind_phone_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_unbind_phone_next_phone.getText().toString().trim();
                if (StringUtils.isNotEmpty(phone)) {
                    time.start();
                    getCodeFromNet(phone);
                } else {
                    errMsg("手机号不能为空!");
                }
            }
        });

        rlayout_unbind_phone_next_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_unbind_phone_next_phone.getText().toString().trim();
                String yzm = et_unbind_phone_next_yzm.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    errMsg("请输入手机号!");
                    return;
                }

                if (!StringUtils.isPhoneNum(phone)) {
                    errMsg("请输入正确的手机号!");
                    return;
                }

                if (TextUtils.isEmpty(yzm)) {
                    errMsg("请输入密码!");
                    return;
                }
                unBindPhoneNextHttp("", phone, yzm);
            }
        });

    }

    private void unBindPhoneNextHttp(String nick, String phone, String yzm) {
        HttpUserManager.getInstance().bindWP(nick, phone, yzm, user_id, rand_str, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, YZMBean.class);
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
                    SharedPreferenceUtil.putStringValueByKey(UnBindPhoneNextActivity.this, "mobile", phone);
                    Intent intent = new Intent(UnBindPhoneNextActivity.this, SettingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    //TODO WH
                    String info = jsonObject.getString("info");
                    dialog = new AlertDialog.Builder(new ContextThemeWrapper(UnBindPhoneNextActivity.this, R.style.DialogStyle)).create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setView(LayoutInflater.from(UnBindPhoneNextActivity.this).inflate(R.layout.login_dialog, null));
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
                        ToastUtils.showToast(UnBindPhoneNextActivity.this, "验证码发送成功!");
                    } else {
                        ToastUtils.showToast(UnBindPhoneNextActivity.this, infoMsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getCodeFromNet---联网失败---" + ex.getMessage());
                tv_unbind_phone_next_error.setVisibility(View.VISIBLE);
                tv_unbind_phone_next_error.setText("网络异常");
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


    private void errMsg(String info) {
        btntime.start();
        tv_unbind_phone_next_error.setVisibility(View.VISIBLE);
        tv_unbind_phone_next_error.setText(info);
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //login_msg_code.setBackgroundColor(Color.parseColor("#B6B6D8"));
            tv_unbind_phone_next.setTextColor(Color.parseColor("#aeb2bf"));
            tv_unbind_phone_next.setClickable(false);
            tv_unbind_phone_next.setText("重发(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            tv_unbind_phone_next.setTextColor(Color.parseColor("#85BC44"));
            tv_unbind_phone_next.setText("重新获取");
            tv_unbind_phone_next.setClickable(true);
        }
    }

    //登录按钮倒计时
    class TimeBtnCount extends CountDownTimer {

        public TimeBtnCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            rlayout_unbind_phone_next_sure.setBackgroundResource(R.drawable.button_pass_error_selector);
            tv_unbind_phone_next_sure.setVisibility(View.GONE);
            iv_unbind_phone_next_sure.setVisibility(View.VISIBLE);
            tv_unbind_phone_next_error.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFinish() {
            rlayout_unbind_phone_next_sure.setBackgroundResource(R.drawable.button_pass_selector);
            tv_unbind_phone_next_sure.setVisibility(View.VISIBLE);
            iv_unbind_phone_next_sure.setVisibility(View.GONE);
            tv_unbind_phone_next_error.setVisibility(View.GONE);

        }
    }
}
