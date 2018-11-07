package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.LoginBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

/***
 * 登录页面
 */
public class LoginActivity extends Activity {

    private EditText et_login_name;
    private EditText et_login_passwords;
    private EditText et_login_pass_msg;
    private Button login_btn;
    private TextView login_tv_forget_pass;
    private ImageView login_close;
    private Context context;
    private String username;
    private String password;
    private String passwordMsg;
    private Button login_btn_msg;
    private Button login_btn_pass;
    private RelativeLayout rlayout_login_pass;
    private LinearLayout llayout_login_msg;
    private TimeCount time;
    private Button login_msg_code;
    private int isType = 1;
    private TextView login_worning_tv;
    private String content;
    private String clickTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        time = new TimeCount(60000, 1000);
        context = this;
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    protected void initView() {
        login_btn_msg = (Button) findViewById(R.id.login_btn_msg); //验证码按钮
        login_btn_msg.setBackgroundColor(Color.parseColor("#ffffffff"));

        login_btn_pass = (Button) findViewById(R.id.login_btn_pass); //密码登录按钮
        login_btn_pass.setBackgroundColor(Color.parseColor("#B6B6D8"));

        et_login_name = (EditText) findViewById(R.id.et_login_name); //手机号（通用）

        rlayout_login_pass = (RelativeLayout) findViewById(R.id.rlayout_login_pass);//密码验证 密码
        et_login_passwords = (EditText) findViewById(R.id.et_login_passwords); //密码

        llayout_login_msg = (LinearLayout) findViewById(R.id.llayout_login_msg);//短信验证 密码
        et_login_pass_msg = (EditText) findViewById(R.id.et_login_pass_msg); //验证码
        login_worning_tv = (TextView) findViewById(R.id.login_worning_tv);
        showText(isType);
        login_btn = (Button) findViewById(R.id.login_btn);  //登录
        login_tv_forget_pass = (TextView) findViewById(R.id.login_tv_forget_pass); //忘记密码
        login_close = (ImageView) findViewById(R.id.login_close); //取消登录
        login_msg_code = (Button) findViewById(R.id.login_msg_code); //验证码倒计时

        login_tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn_msg.setBackgroundColor(Color.parseColor("#ffffffff"));
                login_btn_msg.setTextColor(Color.parseColor("#85BC44"));
                login_btn_pass.setBackgroundColor(Color.parseColor("#B6B6D8"));
                login_btn_pass.setTextColor(Color.parseColor("#ffffffff"));
                llayout_login_msg.setVisibility(View.VISIBLE);
                rlayout_login_pass.setVisibility(View.GONE);
                isType = 1;
                showText(isType);
            }
        });

        login_btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn_msg.setBackgroundColor(Color.parseColor("#ffffffff"));
                login_btn_msg.setTextColor(Color.parseColor("#85BC44"));
                login_btn_pass.setBackgroundColor(Color.parseColor("#B6B6D8"));
                login_btn_pass.setTextColor(Color.parseColor("#ffffffff"));
                llayout_login_msg.setVisibility(View.VISIBLE);
                rlayout_login_pass.setVisibility(View.GONE);
                isType = 1;
                showText(isType);
            }
        });

        login_btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn_pass.setBackgroundColor(Color.parseColor("#ffffffff"));
                login_btn_pass.setTextColor(Color.parseColor("#85BC44"));
                login_btn_msg.setBackgroundColor(Color.parseColor("#B6B6D8"));
                login_btn_msg.setTextColor(Color.parseColor("#ffffffff"));
                llayout_login_msg.setVisibility(View.GONE);
                rlayout_login_pass.setVisibility(View.VISIBLE);
                isType = 2;
                showText(isType);
            }
        });

    }

    private void showText(int isType) {
        if (isType == 1) {
            content = "温馨提示: 未注册勺子课堂的手机号,登录时将自动注册,且代表您已经同意《用户服务协议》";
            clickTxt = "《用户服务协议》";

        } else if (isType == 2) {
            content = "温馨提示: 登录代表您已经同意《用户服务协议》";
            clickTxt = "《用户服务协议》";
        }

        login_worning_tv.setText(content);
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        int startIndex = content.indexOf(clickTxt);
        int endIndex = startIndex + clickTxt.length();
        //文字变色
        spannable.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //文字点击
        spannable.setSpan(new TextClick(), startIndex, endIndex
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //一定要记得设置，不然点击不生效
        login_worning_tv.setMovementMethod(LinkMovementMethod.getInstance());
        login_worning_tv.setText(spannable);
        login_worning_tv.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    private class TextClick extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            Intent intent = new Intent(LoginActivity.this, LoginwebActivity.class);
            startActivity(intent);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.main_color));
        }
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            login_msg_code.setBackgroundColor(Color.parseColor("#B6B6D8"));
            login_msg_code.setClickable(false);
            login_msg_code.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            login_msg_code.setText("重新获取验证码");
            login_msg_code.setClickable(true);
            login_msg_code.setBackgroundColor(Color.parseColor("#4EB84A"));
        }
    }


    /**
     * 初始化数据
     */
    protected void initData() {

        login_msg_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast(LoginActivity.this, "点击了!");
                username = et_login_name.getText().toString().trim();
                if (StringUtils.isNotEmpty(username)) {
                    ToastUtils.showToast(LoginActivity.this, "点击username==" + username);
                    time.start();
                    getCodeFromNet(username);
                } else {
                    ToastUtils.showToast(LoginActivity.this, "手机号不能为空!");
                }
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_login_name.getText().toString().trim();
                //验证码验证
                if (isType == 1) {
                    passwordMsg = et_login_pass_msg.getText().toString().trim(); //验证码验证
                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(context, "请输入用户名!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(passwordMsg)) {
                        Toast.makeText(context, "请输入验证码!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //TODO WHWH
                    startLoginHttp(username, passwordMsg, "imei");

                } else if (isType == 2) {
                    password = et_login_passwords.getText().toString().trim(); //密码验证
                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(context, "请输入用户名!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(context, "请输入验证码!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startLoginHttp(username, password, "imei");
                }
            }
        });


        login_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.将保存在sp中的数据清除
                SharedPreferenceUtil.putStringValueByKey(LoginActivity.this, "username", "");
                SharedPreferenceUtil.putStringValueByKey(LoginActivity.this, "password", "");
                Intent intent2 = new Intent(context, NewMainActivity.class);
                intent2.putExtra("flag", 100);
                startActivity(intent2);
                finish();
            }
        });
    }


    /**
     * 发送 验证码
     */
    private void getCodeFromNet(String mobile) {
        RequestParams params = new RequestParams(Constants.loginCodeUrl);
        params.addQueryStringParameter("mobile", mobile);
        Log.e("whwh", "params==" + params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonCodeObj = new JSONObject(result);
                    int statusMsg = jsonCodeObj.optInt("status");
                    String infoMsg = jsonCodeObj.optString("info");
                    if (statusMsg == 1) {
                        ToastUtils.showToast(LoginActivity.this, "验证码发送成功!");
                    } else {
                        ToastUtils.showToast(LoginActivity.this, infoMsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "SuperiorViewPager---联网失败---" + ex.getMessage());
                ToastUtils.showToast(LoginActivity.this, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "SuperiorViewPager---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "SuperiorViewPager---onFinished---");
            }
        });
    }


    /**
     * 登录的网络请求
     */
    private void startLoginHttp(String username, String passwords, String imei) {
        HttpUserManager.getInstance().login(isType, username, passwords, "imei", new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
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
                    ToastUtils.showToast(LoginActivity.this, "登录成功!");
                    SharedPreferenceUtil.putStringValueByKey(context, "username", username); //用户名
                    JSONObject infoObj = new JSONObject(obj.toString()).getJSONObject("info");

                    String login_area = infoObj.optString("area"); //地区
                    SharedPreferenceUtil.putStringValueByKey(context, "area", login_area);

                    String login_avatar = infoObj.optString("avatar"); //头像
                    SharedPreferenceUtil.putStringValueByKey(context, "avatar", login_avatar);

                    String login_brand = infoObj.optString("brand"); //品牌
                    SharedPreferenceUtil.putStringValueByKey(context, "brand", login_brand);

                    String login_is_blocked = infoObj.optString("is_blocked"); //没用
                    SharedPreferenceUtil.putStringValueByKey(context, "is_blocked", login_is_blocked);
                    String login_is_vip = infoObj.optString("is_vip"); //vip
                    SharedPreferenceUtil.putStringValueByKey(context, "is_vip", login_is_vip);
                    String login_job = infoObj.optString("job"); //不用
                    SharedPreferenceUtil.putStringValueByKey(context, "job", login_job);
                    String login_mobile = infoObj.optString("mobile"); //手机号
                    SharedPreferenceUtil.putStringValueByKey(context, "mobile", login_mobile);
                    String login_nickname = infoObj.optString("nickname"); //昵称
                    SharedPreferenceUtil.putStringValueByKey(context, "nickname", login_nickname);
                    String login_password = infoObj.optString("password"); //密码
                    SharedPreferenceUtil.putStringValueByKey(context, "password", login_password);
                    String login_position = infoObj.optString("position");  //职位
                    SharedPreferenceUtil.putStringValueByKey(context, "position", login_position);
                    String login_token = infoObj.optString("token");//不用
                    SharedPreferenceUtil.putStringValueByKey(context, "token", login_token);
                    String login_user_id = infoObj.optString("user_id");
                    Log.e("whwh", "login_user_id==" + login_user_id); //用户id
                    SharedPreferenceUtil.putStringValueByKey(context, "user_id", login_user_id);

                    String login_rand_str = infoObj.optString("rand_str");
                    SharedPreferenceUtil.putStringValueByKey(context, "login_rand_str", login_rand_str);
                    Log.e("whwh", "login_rand_str==" + login_rand_str); //用户设备唯一

                    Intent intent2 = new Intent(context, NewMainActivity.class);
                    intent2.putExtra("flag", 200);
                    startActivity(intent2);
                    finish();

                } else {
                    et_login_name.setText("");
                    et_login_passwords.setText("");
                    String info = jsonObject.getString("info");
                    ToastUtils.showToast((Activity) context, info);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}