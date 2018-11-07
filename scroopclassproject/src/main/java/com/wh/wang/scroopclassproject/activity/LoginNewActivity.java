package com.wh.wang.scroopclassproject.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v13.app.ActivityCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.LoginBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/***
 * 登录页面
 */
public class LoginNewActivity extends Activity {

    private Context context;
    private TimeCount time;
    private TimeBtnCount btntime;
    private int isType = 2;
    private String content;
    private String clickTxt;
    private EditText et_login_username;
    private TextView tv_send_msg;
    private EditText et_login_password_yzm;
    private EditText et_login_password_ma;
    private TextView tv_msg_tip;
    private TextView tv_forgetpassword_tip;
    private RelativeLayout login_new_rlayout;
    private TextView login_new_worning_tv;
    private TextView login_error;
    private TextView login_chang_login;
    private ImageView login_new_close;
    private String username;
    private TextView login_new_tv;
    private ImageView login_new_iv;
    private ImageView login_weixin;
    private View login_view;
    private AlertDialog dialog;
    private String imei;
    private static final int REQUEST_PHONE_STATE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_new);
        time = new TimeCount(60000, 1000);
        btntime = new TimeBtnCount(3000, 1000);
        context = this;
        //Android6.0需要动态获取权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
        } else {
            imei = StringUtils.getIMEI(this);
        }

        Constants.wx_api = WXAPIFactory.createWXAPI(LoginNewActivity.this, Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
        initView();
        initData();
    }


    /**
     * 加个获取权限的监听
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PHONE_STATE && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imei = StringUtils.getIMEI(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (Constants.loginCode == 0) {
            String weichat_code = Constants.weichat_code;
            SharedPreferenceUtil.putStringValueByKey(context, "openid", weichat_code);
            startWeichatLoginHttp(weichat_code, imei);
            Constants.loginCode = -1;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    //微信登陆
    private void startWeichatLoginHttp(String code, String imei) {
        Log.e("whwh", "imei==" + imei);
        HttpUserManager.getInstance().weiChatLogin(code, imei, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                if(StringUtils.isNotEmpty(data.toString())){
                    Message obtain = Message.obtain();
                    obtain.obj = data;
                    mHandler.sendMessage(obtain);
                    LoadingUtils.getInstance().hideNetLoading();
                }else{
                    Toast.makeText(MyApplication.mApplication, "服务器返回数据为空", Toast.LENGTH_SHORT).show();
                }
                
            }

            @Override
            public void onFailure(IOException e) {
                LoadingUtils.getInstance().hideNetLoading();
            }
        }, LoginBean.class);
    }

    /**
     * 初始化布局
     */
    protected void initView() {

        login_new_close = (ImageView) findViewById(R.id.login_new_close);//取消登录
        et_login_username = (EditText) findViewById(R.id.et_login_username); //手机号
        tv_send_msg = (TextView) findViewById(R.id.tv_send_msg); //发送验证码
        login_view = findViewById(R.id.login_view);
        et_login_password_yzm = (EditText) findViewById(R.id.et_login_password_yzm);//输入验证码
        et_login_password_ma = (EditText) findViewById(R.id.et_login_password_ma);//输入密码

        tv_msg_tip = (TextView) findViewById(R.id.tv_msg_tip); //验证码提示
        tv_forgetpassword_tip = (TextView) findViewById(R.id.tv_forgetpassword_tip); //忘记密码提示

        login_new_rlayout = (RelativeLayout) findViewById(R.id.login_new_rlayout);//登录
        login_new_rlayout.setBackgroundResource(R.drawable.button_pass_selector);
        login_new_tv = (TextView) findViewById(R.id.login_new_tv);//正常登录
        login_new_tv.setVisibility(View.VISIBLE);
        login_new_iv = (ImageView) findViewById(R.id.login_new_iv);//错误登录
        login_new_iv.setVisibility(View.GONE);

        login_new_worning_tv = (TextView) findViewById(R.id.login_new_worning_tv); //用户协议

        login_error = (TextView) findViewById(R.id.login_error);//登录错误提示

        login_chang_login = (TextView) findViewById(R.id.login_chang_login);//切换验证码登录或者密码登录
        login_weixin = (ImageView) findViewById(R.id.login_weixin);

        //login_btn_msg.setBackgroundColor(Color.parseColor("#ffffffff"));
        showText(isType);

        tv_forgetpassword_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_send_msg.setVisibility(View.VISIBLE);
                login_view.setVisibility(View.VISIBLE);
                tv_msg_tip.setVisibility(View.VISIBLE);
                et_login_password_yzm.setVisibility(View.VISIBLE);
                et_login_password_ma.setVisibility(View.GONE);
                login_error.setVisibility(View.GONE);
                tv_forgetpassword_tip.setVisibility(View.GONE);
                isType = 2;
                showText(isType);
            }
        });

        login_chang_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isType == 1) {
                    tv_send_msg.setVisibility(View.VISIBLE);
                    login_view.setVisibility(View.VISIBLE);
                    tv_msg_tip.setVisibility(View.VISIBLE);
                    tv_forgetpassword_tip.setVisibility(View.GONE);
                    login_error.setVisibility(View.GONE);
                    showText(isType);
                    et_login_password_yzm.setVisibility(View.VISIBLE);
                    et_login_password_ma.setVisibility(View.GONE);
                    isType = 2;
                } else if (isType == 2) {
                    tv_send_msg.setVisibility(View.GONE);
                    login_view.setVisibility(View.GONE);
                    tv_msg_tip.setVisibility(View.GONE);
                    login_error.setVisibility(View.GONE);
                    tv_forgetpassword_tip.setVisibility(View.VISIBLE);
                    showText(isType);
                    et_login_password_yzm.setVisibility(View.GONE);
                    et_login_password_ma.setVisibility(View.VISIBLE);
                    isType = 1;
                }
            }
        });

        login_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constants.wx_api.isWXAppInstalled()) {
                    BToast.showText(context, "没有安装没微信!");
                } else {
                    //发起登录请求
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    Constants.wx_api.sendReq(req);
                }
            }
        });

    }

    /**
     * 处理用户协议
     */
    private void showText(int isType) {

        content = "登录代表您已经同意《用户服务协议》";
        clickTxt = "《用户服务协议》";
        login_new_worning_tv.setText(content);
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
        login_new_worning_tv.setMovementMethod(LinkMovementMethod.getInstance());
        login_new_worning_tv.setText(spannable);
        login_new_worning_tv.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    private class TextClick extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            Intent intent = new Intent(LoginNewActivity.this, LoginwebActivity.class);
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
            //login_msg_code.setBackgroundColor(Color.parseColor("#B6B6D8"));
            tv_send_msg.setTextColor(Color.parseColor("#aeb2bf"));
            tv_send_msg.setClickable(false);
            tv_send_msg.setText("重发(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            tv_send_msg.setTextColor(Color.parseColor("#85BC44"));
            tv_send_msg.setText("重新获取");
            tv_send_msg.setClickable(true);
            //tv_send_msg.setBackgroundColor(Color.parseColor("#4EB84A"));
        }
    }


    //登录按钮倒计时
    class TimeBtnCount extends CountDownTimer {

        public TimeBtnCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            login_new_rlayout.setBackgroundResource(R.drawable.button_pass_error_selector);
            login_new_tv.setVisibility(View.GONE);
            login_new_iv.setVisibility(View.VISIBLE);
            login_error.setVisibility(View.VISIBLE);
            login_new_worning_tv.setVisibility(View.GONE);
        }

        @Override
        public void onFinish() {
            login_new_rlayout.setBackgroundResource(R.drawable.button_pass_selector);
            login_new_tv.setVisibility(View.VISIBLE);
            login_new_iv.setVisibility(View.GONE);
            login_error.setVisibility(View.GONE);
            login_new_worning_tv.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 初始化数据
     */
    protected void initData() {
        tv_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_login_username.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    errMsg("请输入手机号!");
                    BToast.showText(LoginNewActivity.this, "请输入手机号!");
                    return;
                }

                if (!StringUtils.isPhoneNum(username)) {
                    errMsg("请输入正确的手机号!");
                    BToast.showText(LoginNewActivity.this, "请输入正确的手机号!");
                    return;
                }

                time.start();
                getCodeFromNet(username);
            }
        });


        login_new_rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetUtil.isNetworkAvailable(LoginNewActivity.this)) {
                    Toast.makeText(context, R.string.network_connection_is_failed, Toast.LENGTH_SHORT).show();
                    return;
                }

                username = et_login_username.getText().toString().trim();
                //验证码验证
                if (isType == 1) {
                    String password = et_login_password_ma.getText().toString().trim(); //密码验证
                    if (TextUtils.isEmpty(username)) {
                        errMsg("请输入手机号!");
                        return;
                    }

                    if (!StringUtils.isPhoneNum(username)) {
                        errMsg("请输入正确的手机号!");
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        errMsg("请输入密码!");
                        return;
                    }
                    LoadingUtils.getInstance().showNetLoading(LoginNewActivity.this);
                    startLoginHttp(username, password, imei);


                } else if (isType == 2) {

                    String passwordMsg = et_login_password_yzm.getText().toString().trim(); //验证码验证
                    if (TextUtils.isEmpty(username)) {
                        errMsg("请输入手机号!");
                        return;
                    }

                    if (!StringUtils.isPhoneNum(username)) {
                        errMsg("请输入正确的手机号!");
                        return;
                    }

                    if (TextUtils.isEmpty(passwordMsg)) {
                        errMsg("请输入验证码!");
                        return;
                    }
                    KeyBoardUtils.hideKeyboard(LoginNewActivity.this);
                    LoadingUtils.getInstance().showNetLoading(LoginNewActivity.this);
                    startLoginHttp(username, passwordMsg, imei);
                }
            }
        });


        login_new_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.将保存在sp中的数据清除
                SharedPreferenceUtil.putStringValueByKey(LoginNewActivity.this, "username", "");
                SharedPreferenceUtil.putStringValueByKey(LoginNewActivity.this, "password", "");
                finish();
            }
        });
    }

    /**
     * 错误提示信息
     */
    private void errMsg(String info) {
        btntime.start();
        //et_login_username.setText("");
        //et_login_password.setText("");
        login_new_worning_tv.setVisibility(View.GONE);
        login_error.setVisibility(View.VISIBLE);
        login_error.setText(info);
    }


    /**
     * 发送 验证码
     */
    private void getCodeFromNet(String mobile) {
        RequestParams params = new RequestParams(Constants.loginCodeUrl);
        params.addQueryStringParameter("mobile", mobile);
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
                    Log.e("whwh", "infoMsg==" + infoMsg);
                    if (statusMsg == 1) {
                        ToastUtils.showToast(LoginNewActivity.this, "验证码发送成功!");
                    } else {
                        //ToastUtils.showToast(LoginNewActivity.this, infoMsg);
                        BToast.showText(LoginNewActivity.this, infoMsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "SuperiorViewPager---联网失败---" + ex.getMessage());
                login_new_worning_tv.setVisibility(View.GONE);
                login_error.setVisibility(View.VISIBLE);
                login_error.setText("网络异常");
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
        HttpUserManager.getInstance().login(isType, username, passwords, imei, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
                LoadingUtils.getInstance().hideNetLoading();
            }

            @Override
            public void onFailure(IOException e) {
                LoadingUtils.getInstance().hideNetLoading();

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
                    //ToastUtils.showToast(LoginNewActivity.this, "登录成功!");
                    SharedPreferenceUtil.putStringValueByKey(context, "username", username); //用户名
                    JSONObject infoObj = new JSONObject(obj.toString()).getJSONObject("info");
                    String login_area = infoObj.optString("area"); //地区
                    SharedPreferenceUtil.putStringValueByKey(context, "area", login_area);
                    String login_avatar = infoObj.optString("avatar"); //头像
                    SharedPreferenceUtil.putStringValueByKey(context, "avatar", login_avatar);
                    Log.e("whwh", "login_avatar==" + login_avatar); //头像
                    String login_brand = infoObj.optString("brand"); //品牌
                    SharedPreferenceUtil.putStringValueByKey(context, "brand", login_brand);
                    String login_is_blocked = infoObj.optString("is_blocked"); //没用
                    SharedPreferenceUtil.putStringValueByKey(context, "is_blocked", login_is_blocked);
                    String login_is_vip = infoObj.optString("is_vip"); //vip
                    SharedPreferenceUtil.putStringValueByKey(context, "is_vip", login_is_vip);
                    String login_job = infoObj.optString("job"); //不用
                    SharedPreferenceUtil.putStringValueByKey(context, "job", login_job);
                    String login_mobile = infoObj.optString("mobile"); //手机号
                    Log.e("whwh", "login_mobile==" + login_mobile); //手机号
                    SharedPreferenceUtil.putStringValueByKey(context, "mobile", login_mobile);
                    String login_nickname = infoObj.optString("nickname"); //昵称
                    Log.e("whwh", "login_nickname==" + login_nickname); //昵称
                    SharedPreferenceUtil.putStringValueByKey(context, "nickname", login_nickname);

                    String login_openid = infoObj.optString("openid"); //微信id
                    Log.e("whwh", "login_openid==" + login_openid); //昵称
                    SharedPreferenceUtil.putStringValueByKey(context, "openid", login_openid);

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
                    //finish();
                    if (StringUtils.isNotEmpty(login_user_id)) {
                        Set<String> tags = new HashSet<String>();
                        tags.add(login_user_id);//设置tag
                        //上下文、别名【Sting行】、标签【Set型】、回调
                        JPushInterface.setAliasAndTags(LoginNewActivity.this, login_user_id, tags
                                , new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Set<String> set) {
                                        Log.e("DH_JPushInterface", i + "  " + s);
                                    }
                                });
                    }
                    if (StringUtils.isNotEmpty(login_mobile)) {
                        //判断是否为新用户
//                        new GiftBagPresenter().getGiftStatus(login_user_id, new OnResultListener() {
//                            @Override
//                            public void onSuccess(Object... value) {
//                                if (!isFinishing()) {
//                                    LoadingUtils.getInstance().hideNetLoading();
//                                }
//
//                                GiftBagStatusEntity giftBagStatusEntity = (GiftBagStatusEntity) value[0];
//                                String gift_bag = giftBagStatusEntity.getCode()==1?"1":"0";
//                                SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication,"gift_bag",gift_bag);
//                                if (giftBagStatusEntity.getCode()==1) { //code 为新用户
//                                    Intent intent = new Intent(LoginNewActivity.this, GiftBagActivity.class);
//                                    startActivity(intent);
//                                }
//                                LoginNewActivity.this.finish();
//                            }
//
//                            @Override
//                            public void onFault(String error) {
//                                SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication,"gift_bag","0");
//                                LoadingUtils.getInstance().hideNetLoading();
//                                LoginNewActivity.this.finish();
//                            }
//                        });
                        LoginNewActivity.this.finish();

                    } else {
                        Intent intent2 = new Intent(context, BindWPActivity.class);
                        startActivity(intent2);
                        LoginNewActivity.this.finish();
                    }


                } else {

                    //TODO WH
                    String info = jsonObject.getString("info");
                    dialog = new AlertDialog.Builder(new ContextThemeWrapper(LoginNewActivity.this, R.style.DialogStyle)).create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setView(LayoutInflater.from(LoginNewActivity.this).inflate(R.layout.login_dialog, null));
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


//    private Dialog mVIPDig;
//
//    private void showNewDig() {
//        View dView = LayoutInflater.from(this).inflate(R.layout.dig_vip_main, null, false);
//        if (mVIPDig == null) {
//            mVIPDig = new Dialog(this, R.style.MyDialog);
//        }
//        mVIPDig.setContentView(dView);
//
//        dView.findViewById(R.id.vip_bt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginNewActivity.this, NewEventDetailsActivity.class);
//                intent.putExtra("event_id", "997");
//                startActivity(intent);
//                mVIPDig.dismiss();
//            }
//        });
//
//        dView.findViewById(R.id.vip_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mVIPDig.dismiss();
//            }
//        });
//        mVIPDig.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//
//        mVIPDig.show();
//
//    }

    //极光服务器设置别名是否成功的回调
    private final TagAliasCallback tagAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tagSet) {
            switch (code) {
                case 0:
                    Log.e("whwh", "设置别名成功");
                    break;
                default:
                    Log.e("whwh", "设置别名失败");
                    break;
            }
        }
    };
}