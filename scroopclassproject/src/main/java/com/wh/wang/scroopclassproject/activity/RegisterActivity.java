package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.registerBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpSysManager;
import com.wh.wang.scroopclassproject.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/***
 * 注册页面
 */
public class RegisterActivity extends Activity {

    private EditText et_register_name;
    private EditText et_register_passwords;
    private EditText et_register_repasswords;
    private Button register_btn;
    private TextView register_no_resinge;
    private ImageView register_close;
    private Context context;
    private String username;
    private String password;
    private String repassword;
    private String registeruserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        context = this;
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        et_register_name = (EditText) findViewById(R.id.et_register_name);
        et_register_passwords = (EditText) findViewById(R.id.et_register_passwords);
        et_register_repasswords = (EditText) findViewById(R.id.et_register_repasswords);

        register_btn = (Button) findViewById(R.id.register_btn);
        register_no_resinge = (TextView) findViewById(R.id.register_no_resinge);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_register_name.getText().toString().trim();
                password = et_register_passwords.getText().toString().trim();
                repassword = et_register_repasswords.getText().toString().trim();

                //String username = "15575163724";
                //String password = "123456";
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();
                    et_register_name.setText("");
                    return;
                } else if (username.length() != 11) {
                    Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    et_register_name.setText("");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                    et_register_passwords.setText("");
                    return;
                } else if (password.length() < 6 || password.length() > 18) {
                    Toast.makeText(context, "请输入6至18的密码!", Toast.LENGTH_SHORT).show();
                    et_register_passwords.setText("");
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(context, "确认密码不一致,请重新输入!", Toast.LENGTH_SHORT).show();
                    et_register_repasswords.setText("");
                    return;
                }
                startregisterHttp(username, password, repassword);
            }
        });

        register_no_resinge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 登录的网络请求
     */
    private void startregisterHttp(String username, String password, String repassword) {
        HttpSysManager.getInstance().get_register(username, password, repassword, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Log.e("whwh", "data-->data==" + data);
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, registerBean.class);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "走了");
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                Long registerCode = jsonObject.optLong("code");
                String registerMsg = jsonObject.optString("msg");
                if (registerCode == 200) {
                    ToastUtils.showToast(RegisterActivity.this, registerMsg);
                    registeruserid = jsonObject.optString("user_id");
                    Intent i = new Intent();
                    i.putExtra("username", username);
                    i.putExtra("password", password);
                    i.putExtra("registeruserid", registeruserid);
                    setResult(1, i);
                    finish();
                } else {
                    ToastUtils.showToast(RegisterActivity.this, registerMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
