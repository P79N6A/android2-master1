package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.UpdateBean;
import com.wh.wang.scroopclassproject.bean.UserInfoBean;
import com.wh.wang.scroopclassproject.bean.YZMBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.DataCleanManager;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.SureAndCancelDialog;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.utils.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static com.wh.wang.scroopclassproject.utils.Constants.loginCode;


/**
 * 设置页面
 */
public class SettingActivity extends Activity implements View.OnClickListener {

    private Context context;
    private RelativeLayout set_ralayout_password;
    private RelativeLayout set_ralayout_clear;
    private RelativeLayout set_ralayout_version;
    private RelativeLayout set_ralayout_about;
    private Button change_btn;
    private TextView clear_size;
    private String userid;
    private String mVersion_code;
    private TextView set_tv_version_tip;
    private AlertDialog dialog;
    private AlertDialog dialog2;
    private RelativeLayout set_ralayout_phone;
    private TextView set_tv_phone_des;
    private TextView set_tv_phone_bind;
    private RelativeLayout set_ralayout_weichat;
    private TextView set_tv_weichat_des;
    private TextView set_tv_weichat_bind;
    private String mobile;
    private String openid;
    private String nickname;
    private String rand_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(SettingActivity.this, R.layout.setting, "设置详情");
        context = this;
        //将当前的activity添加到ActivityManager中
        //ActivityManager.getInstance().add(this);
        init();
        initView();
        initData();
    }

    /**
     * 初始化
     */
    private void init() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        getInfoFromNet(userid);
        mobile = SharedPreferenceUtil.getStringFromSharedPreference(context, "mobile", "");
        openid = SharedPreferenceUtil.getStringFromSharedPreference(context, "openid", "");
        nickname = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
        rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
    }

    private void getInfoFromNet(String userid) {
        HttpUserManager.getInstance().post_getInfos(userid, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                cHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, UserInfoBean.class);
    }

    /**
     * 初始化布局
     */
    protected void initView() {
        //绑定手机
        set_ralayout_phone = (RelativeLayout) findViewById(R.id.set_ralayout_phone); //绑定手机父类
        set_tv_phone_des = (TextView) findViewById(R.id.set_tv_phone_des);//手机des
        set_tv_phone_bind = (TextView) findViewById(R.id.set_tv_phone_bind);

        //绑定微信
        set_ralayout_weichat = (RelativeLayout) findViewById(R.id.set_ralayout_weichat);
        set_tv_weichat_des = (TextView) findViewById(R.id.set_tv_weichat_des);
        set_tv_weichat_bind = (TextView) findViewById(R.id.set_tv_weichat_bind);

        //TODO WHWH
        if (StringUtils.isNotEmpty(userid)) {

            //关于绑定和解绑手机方面的
            set_tv_phone_des.setVisibility(View.VISIBLE);
            set_tv_phone_des.setText(StringUtils.settingphone(mobile));
            set_tv_phone_bind.setVisibility(View.VISIBLE);
            set_tv_phone_bind.setText("已绑定");

            //关于绑定和解绑微信方面的
            if (StringUtils.isNotEmpty(openid)) {
                set_tv_weichat_des.setVisibility(View.VISIBLE);
                set_tv_weichat_des.setText("ID: " + StringUtils.settingphone(nickname));
                set_tv_weichat_bind.setVisibility(View.VISIBLE);
                set_tv_weichat_bind.setText("已绑定");
            } else {
                set_tv_weichat_des.setVisibility(View.GONE);
                //set_tv_weichat_des.setText(StringUtils.settingphone(nickname));
                set_tv_weichat_bind.setVisibility(View.GONE);
                //set_tv_weichat_bind.setText("已绑定");
            }

        } else {
            set_tv_phone_des.setVisibility(View.GONE);
            set_tv_phone_bind.setVisibility(View.GONE);

            set_tv_weichat_des.setVisibility(View.GONE);
            set_tv_weichat_bind.setVisibility(View.GONE);
        }

        set_ralayout_password = (RelativeLayout) findViewById(R.id.set_ralayout_password);
        set_ralayout_clear = (RelativeLayout) findViewById(R.id.set_ralayout_clear);
        set_ralayout_version = (RelativeLayout) findViewById(R.id.set_ralayout_version);
        set_tv_version_tip = (TextView) findViewById(R.id.set_tv_version_tip);

        clear_size = (TextView) findViewById(R.id.clear_size);
        set_ralayout_about = (RelativeLayout) findViewById(R.id.set_ralayout_about);
        change_btn = (Button) findViewById(R.id.change_btn);
        if (StringUtils.isNotEmpty(userid)) {
            change_btn.setVisibility(View.VISIBLE);
        } else {
            change_btn.setVisibility(View.GONE);
        }

        set_ralayout_phone.setOnClickListener(this);
        set_ralayout_weichat.setOnClickListener(this);
        set_ralayout_password.setOnClickListener(this);
        set_ralayout_clear.setOnClickListener(this);
        set_ralayout_version.setOnClickListener(this);
        set_ralayout_about.setOnClickListener(this);
        change_btn.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        mobile = SharedPreferenceUtil.getStringFromSharedPreference(context, "mobile", "");
        rand_str = SharedPreferenceUtil.getStringFromSharedPreference(context, "login_rand_str", "");
        openid = SharedPreferenceUtil.getStringFromSharedPreference(context, "openid", "");
        nickname = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
        if (StringUtils.isNotEmpty(userid)) {
            change_btn.setVisibility(View.VISIBLE);
        }

        if (StringUtils.isNotEmpty(userid)) {
            set_tv_phone_des.setVisibility(View.VISIBLE);
            set_tv_phone_des.setText(StringUtils.settingphone(mobile));
            set_tv_phone_bind.setVisibility(View.VISIBLE);
            set_tv_phone_bind.setText("已绑定");

            //关于绑定和解绑微信方面的
            if (StringUtils.isNotEmpty(openid)) {
                set_tv_weichat_des.setVisibility(View.VISIBLE);
                set_tv_weichat_des.setText("ID: " + StringUtils.settingphone(nickname));
                set_tv_weichat_bind.setVisibility(View.VISIBLE);
                set_tv_weichat_bind.setText("已绑定");
            } else {
                set_tv_weichat_des.setVisibility(View.GONE);
                set_tv_weichat_bind.setVisibility(View.GONE);
            }

        } else {
            set_tv_phone_des.setVisibility(View.GONE);
            set_tv_phone_bind.setVisibility(View.GONE);
            set_tv_weichat_des.setVisibility(View.GONE);
            set_tv_weichat_bind.setVisibility(View.GONE);
        }

        if (loginCode == 0) {
            String weichat_code = Constants.weichat_code;
            SharedPreferenceUtil.putStringValueByKey(context, "openid", weichat_code);
            startWeichatBindHttp(weichat_code, userid, rand_str);
            loginCode = -1;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void startWeichatBindHttp(String weichat_code, String user_id, String rand_str) {
        HttpUserManager.getInstance().weiChatBind(weichat_code, user_id, rand_str, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                wHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, YZMBean.class);

    }


    /**
     * 获取微信解绑绑定
     */
    private void setWeiChatUnbindNet(String user_id, String rand_str) {
        HttpUserManager.getInstance().weiChatUnBind(user_id, rand_str, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                uHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, YZMBean.class);

    }

    /**
     * 初始化数据
     */
    protected void initData() {

        //TODO WHWH
        //设置需要清理缓存的大小
        try {
            String sizes = DataCleanManager.getTotalCacheSize(this);
            clear_size.setText(sizes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.set_ralayout_phone:
                if (StringUtils.isNotEmpty(userid)) {
                    Intent intent2 = new Intent(context, UnBindPhoneActivity.class);
                    startActivity(intent2);
                } else {
                    Intent intent2 = new Intent(context, LoginNewActivity.class);
                    startActivity(intent2);
                }

                break;

            case R.id.set_ralayout_weichat:
                if (StringUtils.isNotEmpty(userid)) {
                    openid = SharedPreferenceUtil.getStringFromSharedPreference(context, "openid", "");

                    ////解绑
                    if (StringUtils.isNotEmpty(openid)) {
                        new SureAndCancelDialog(this, "解绑后该微信账号相关记录会被清空!", "取消", true, "确定解绑",
                                new SureAndCancelDialog.onSCClickListener() {
                                    @Override
                                    public void onOkClick() {
                                        setWeiChatUnbindNet(userid, rand_str);
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                }).show();

                    } else {
                        //绑定
                        //发起登录请求
                        Log.e("whwh", "走的是绑定流程!");
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
                } else {
                    Intent intent2 = new Intent(context, LoginNewActivity.class);
                    startActivity(intent2);
                }

                break;

            case R.id.set_ralayout_password:
                if (StringUtils.isNotEmpty(userid)) {
                    Intent intent2 = new Intent(context, ChangerPasswordActivity.class);
                    startActivity(intent2);
                } else {
                    Intent intent2 = new Intent(context, LoginNewActivity.class);
                    startActivity(intent2);
                }

                break;
            case R.id.set_ralayout_clear:
                DataCleanManager.clearAllCache(this);
                clear_size.setText("0K");
                ToastUtils.showToast(this, "清理完成!");
                break;

            case R.id.set_ralayout_version:
                checkUpdate();

                break;
            case R.id.set_ralayout_about:
                Intent intent4 = new Intent(context, AboutActivity.class);
                startActivity(intent4);
                break;

            case R.id.change_btn:
                SharedPreferenceUtil.clearInfo(context);

                Intent intent5 = new Intent(context, LoginNewActivity.class);
                startActivity(intent5);
                finish();
                break;
        }
    }

    private void checkUpdate() {
        HttpUserManager.getInstance().updatess(new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {
                Log.e("DH_VERSION",e.toString());
            }
        }, UpdateBean.class);
    }

    private Handler cHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "wHandler-->obj --->" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int changeInfoCode = jsonObject.optInt("code");
                Log.e("whwh", "changeInfoCode=" + changeInfoCode);
                if (changeInfoCode == 1) {
                    JSONObject infoObj = new JSONObject(obj.toString()).getJSONObject("info");
                    String set_area = infoObj.optString("area"); //地区
                    SharedPreferenceUtil.putStringValueByKey(context, "area", set_area);
                    String set_avator = infoObj.optString("avator"); //头像
                    SharedPreferenceUtil.putStringValueByKey(context, "avatar", set_avator);
                    String set_brand = infoObj.optString("brand"); //品牌
                    SharedPreferenceUtil.putStringValueByKey(context, "brand", set_brand);
                    String set_email = infoObj.optString("email"); //电子邮箱
                    SharedPreferenceUtil.putStringValueByKey(context, "email", set_email);
                    String set_id = infoObj.optString("id"); //user_id
                    SharedPreferenceUtil.putStringValueByKey(context, "user_id", set_id);
                    String set_ios_integral = infoObj.optString("ios_integral"); //只是ios使用
                    String set_mensum = infoObj.optString("mensum"); //门店数量
                    SharedPreferenceUtil.putStringValueByKey(context, "mensum", set_mensum);
                    String set_mess_num = infoObj.optString("mess_num"); //消息数量
                    SharedPreferenceUtil.putStringValueByKey(context, "mess_num", set_mess_num);
                    String set_mobile = infoObj.optString("mobile"); //手机号
                    SharedPreferenceUtil.putStringValueByKey(context, "mobile", set_mobile);
                    String set_nickname = infoObj.optString("nickname"); //昵称
                    SharedPreferenceUtil.putStringValueByKey(context, "nickname", set_nickname);
                    String set_openid = infoObj.optString("openid"); //微信ID
                    SharedPreferenceUtil.putStringValueByKey(context, "openid", set_openid);
                    String set_position = infoObj.optString("position"); //职位
                    SharedPreferenceUtil.putStringValueByKey(context, "position", set_position);
                    String set_rand_str = infoObj.optString("rand_str"); //唯一登录
                    SharedPreferenceUtil.putStringValueByKey(context, "login_rand_str", set_rand_str);
                    String set_senior = infoObj.optString("senior"); //性别
                    SharedPreferenceUtil.putStringValueByKey(context, "senior", set_senior);
                } else {
                    //TODO WH
                    String msgStrs = jsonObject.getString("msg");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private Handler wHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "wHandler-->obj --->" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int loginCode = jsonObject.optInt("status");
                Log.e("whwh", "loginCode=" + loginCode);
                if (loginCode == 1) {
                    //TODO WH
                    BToast.showText(SettingActivity.this, "绑定成功");
                    JSONObject infoObj = new JSONObject(obj.toString()).getJSONObject("info");
                    String set_nickname = infoObj.optString("nickname"); //昵称
                    Log.e("whwh", "set_openid==" + set_nickname); //昵称
                    SharedPreferenceUtil.putStringValueByKey(context, "nickname", set_nickname);

                    String set_openid = infoObj.optString("openid"); //微信id
                    Log.e("whwh", "set_openid==" + set_openid);
                    SharedPreferenceUtil.putStringValueByKey(context, "openid", set_openid);

                    set_tv_weichat_des.setVisibility(View.VISIBLE);
                    set_tv_weichat_des.setText("ID: " + StringUtils.settingphone(nickname));
                    set_tv_weichat_bind.setVisibility(View.VISIBLE);
                    set_tv_weichat_bind.setText("已绑定");

                } else {
                    //TODO WH
                    String info = jsonObject.getString("info");
                    dialog = new AlertDialog.Builder(new ContextThemeWrapper(SettingActivity.this, R.style.DialogStyle)).create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setView(LayoutInflater.from(SettingActivity.this).inflate(R.layout.login_dialog, null));
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

    private Handler uHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "uHandler-->obj --->" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int loginCode = jsonObject.optInt("status");
                Log.e("whwh", "loginCode=" + loginCode);
                if (loginCode == 1) {
                    //TODO WH
                    BToast.showText(SettingActivity.this, "解绑成功");
                    SharedPreferenceUtil.putStringValueByKey(context, "openid", "");
                    set_tv_weichat_des.setVisibility(View.GONE);
                    set_tv_weichat_bind.setVisibility(View.GONE);

                } else {
                    //TODO WH
                    String info = jsonObject.getString("info");
                    dialog = new AlertDialog.Builder(new ContextThemeWrapper(SettingActivity.this, R.style.DialogStyle)).create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setView(LayoutInflater.from(SettingActivity.this).inflate(R.layout.login_dialog, null));
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


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("DH_VER_JSON", "json:" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                mVersion_code = jsonObject.getString("version");
                int serverVersion = Integer.parseInt(mVersion_code);
                int localVersion = 1;
                try {
                    localVersion = context.getPackageManager().getPackageInfo("com.wh.wang.scroopclassproject", 0).versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if (serverVersion > localVersion)
                    showAlertDialog();
                else
                    BToast.showText(SettingActivity.this, "已是最新版本,无需更新!");
//                String mVersion_apk = jsonObject.getString("apk");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    /*
     * 与本地版本比较判断是否需要更新
     */
    protected void isUpdate() {

    }

    private void showAlertDialog() {
        dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogStyle)).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setView(LayoutInflater.from(this).inflate(R.layout.version_dialog, null));
        dialog.show();
        dialog.getWindow().setContentView(R.layout.version_dialog);
        Button btn_version_result = (Button) dialog.findViewById(R.id.btn_version_result);
        btn_version_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdPath = Environment.getExternalStorageDirectory() + "/";
                String mSavePath = sdPath + "jikedownload";
                File dir = new File(mSavePath);
                if (dir.exists()) {
                    dialog.dismiss();
                    new UpdateManager(context).checkUpdate();
                }
            }
        });

        TextView btn_version_question = (TextView) dialog.findViewById(R.id.btn_version_question);
        btn_version_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(context, QuestionActivity.class);
                startActivity(intent6);
            }
        });
    }
}
