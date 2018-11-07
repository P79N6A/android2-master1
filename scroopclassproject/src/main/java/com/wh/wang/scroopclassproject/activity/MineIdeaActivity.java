package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.MineIdeaBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MineIdeaActivity extends Activity {

    private EditText feedback_content_edit;
    final int maxNum = 500;
    private TextView tv_sums_now;
    private TextView title_action;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.mine_idea, "意见反馈");
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        title_action = (TextView) findViewById(R.id.titlebarbackss_action);
        title_action.setText("提交");

        feedback_content_edit = (EditText) findViewById(R.id.feedback_content_edit);
        tv_sums_now = (TextView) findViewById(R.id.tv_sums_now);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
    }

    /**
     * 监听事件
     */
    private void initListener() {

        //设置已经输出的字数
        feedback_content_edit.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_sums_now.setText("" + s.length());
                selectionStart = feedback_content_edit.getSelectionStart();
                selectionEnd = feedback_content_edit.getSelectionEnd();
                if (temp.length() > maxNum) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    feedback_content_edit.setText(s);
                    feedback_content_edit.setSelection(tempSelection);
                }
            }
        });

        //提交数据
        title_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = feedback_content_edit.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    //ToastUtils.showToast(MineIdeaActivity.this, "内容不能为空!");
                    BToast.showText(MineIdeaActivity.this, "内容不能为空");
                    return;
                }
                sendMsgHttp(userid, content);
            }
        });
    }

    /**
     * 网络请求
     */
    private void sendMsgHttp(String userid, String content) {
        HttpUserManager.getInstance().updateIdea(userid, content, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, MineIdeaBean.class);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(MineIdeaActivity.this, msgStrs, true);
                    finish();
                } else {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(MineIdeaActivity.this, msgStrs);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
