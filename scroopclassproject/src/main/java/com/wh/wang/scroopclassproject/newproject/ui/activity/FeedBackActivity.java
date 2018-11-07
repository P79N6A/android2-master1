package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.FeedBackEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.FeedBackPresenter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

public class FeedBackActivity extends Activity implements TextWatcher, View.OnClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private TextView mTitlebarbackssAction;
    private EditText mFeedbackContent;
    private TextView mFeedbackNum;
    private String mUserid;
    private FeedBackPresenter mFeedBackPresenter = new FeedBackPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserid = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
    }

    private void initView() {
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssAction = (TextView) findViewById(R.id.titlebarbackss_action);
        mFeedbackContent = (EditText) findViewById(R.id.feedback_content);
        mFeedbackNum = (TextView) findViewById(R.id.feedback_num);

        mFeedbackContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
    }

    private void initData() {
        mTitlebarbackssName.setText("意见反馈");
        mTitlebarbackssAction.setText("提交");
    }

    private void initListener() {
        mFeedbackContent.addTextChangedListener(this);
        mTitlebarbackssAction.setOnClickListener(this);
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence!=null&&charSequence.length()>0){
            mFeedbackNum.setText(charSequence.length()+"");
        }else{
            mFeedbackNum.setText("0");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //TODO
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titlebarbackss_action:
                if(mUserid!=null&&!"".equals(mUserid)){
                    String content = mFeedbackContent.getText().toString();
                    if (StringUtils.isEmpty(content)&&StringUtils.isEmpty(content.trim())) {
                        Toast.makeText(this, "请输入反馈内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mFeedBackPresenter.feedBack(mUserid, content, new OnResultListener() {
                        @Override
                        public void onSuccess(Object... value) {
                            FeedBackEntity entity = (FeedBackEntity) value[0];
                            if(entity.getCode()==200){
                                Toast.makeText(FeedBackActivity.this, entity.getMsg()==null?"发送成功":entity.getMsg(), Toast.LENGTH_SHORT).show();
                                mFeedbackContent.setText("");
                            }else{
                                Toast.makeText(FeedBackActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFault(String error) {

                        }
                    });
                }else{
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }
}
