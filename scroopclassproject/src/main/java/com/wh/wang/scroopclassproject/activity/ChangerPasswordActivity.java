package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.ChangePassBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 更改密码页面
 */

public class ChangerPasswordActivity extends Activity {

    private EditText etChangeNewpass;
    private EditText etChangeNewrepass;
    private Button changeBtn;
    private String newPassword;
    private String newRepassword;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.changer_password, "修改密码");
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
        MobclickAgent.onPause(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {

        etChangeNewpass = (EditText) findViewById(R.id.et_change_newpass);
        etChangeNewrepass = (EditText) findViewById(R.id.et_change_newrepass);
        changeBtn = (Button) findViewById(R.id.change_btn);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = etChangeNewpass.getText().toString().trim();
                newRepassword = etChangeNewrepass.getText().toString().trim();
                String user_id = SharedPreferenceUtil.getStringFromSharedPreference(ChangerPasswordActivity.this, "user_id", "");

                if (TextUtils.isEmpty(newPassword)) {
                    BToast.showText(ChangerPasswordActivity.this, "请输入新密码!");
                    return;
                }

                if (TextUtils.isEmpty(newRepassword)) {
                    BToast.showText(ChangerPasswordActivity.this, "请输入确认的新密码!");
                    return;
                }
                if (!newPassword.equals(newRepassword)) {
                    BToast.showText(ChangerPasswordActivity.this, "确认密码不一致,请重新输入!");
                    return;
                }
                startChangePasswordHttp(newPassword, newRepassword, user_id);
            }
        });
    }

    //网络请求数据
    private void startChangePasswordHttp(String newPassword, String newRepassword, String user_id) {
        HttpUserManager.getInstance().post_changePassword(newPassword, newRepassword, user_id,
                new GetDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        Message obtain = Message.obtain();
                        obtain.obj = data;
                        mHandler.sendMessage(obtain);
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }, ChangePassBean.class);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                Long changeCode = jsonObject.optLong("code");
                String changeMsg = jsonObject.optString("msg");
                if (changeCode == 200) {
                    BToast.showText(ChangerPasswordActivity.this, changeMsg, true);
                    SharedPreferenceUtil.putStringValueByKey(context, "password", newPassword);
                    finish();
                } else {
                    BToast.showText(ChangerPasswordActivity.this, changeMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
