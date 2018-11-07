package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CommentEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CommentPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CommitRemarkActivity extends BaseActivity implements OnResultListener {
    private EditText mRemark;
    private TextView mCommit;
    private CommentPresenter mCommentPresenter = new CommentPresenter();
    private String mUserId;
    private Map<String,String> mMap = new HashMap<>();
    private String mVid;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_commit_remark;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mVid = getIntent().getStringExtra("vid");
    }

    @Override
    public void initView() {
        mRemark = (EditText) findViewById(R.id.remark);
        mCommit = (TextView) findViewById(R.id.commit);

    }

    @Override
    public void initDatas() {
        mTitleC.setText("提交心得");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(this,"user_id","");
    }

    @Override
    public void initListener() {
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemark.getText()==null|| mRemark.getText().toString()==null||StringUtils.isEmpty(mRemark.getText().toString().trim())) {
                    Toast.makeText(MyApplication.mApplication, "请填写心得", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoadingUtils.getInstance().showNetLoading(CommitRemarkActivity.this);
                String content = mRemark.getText().toString();
                mMap.put("id",mVid);
                mMap.put("content",content);
                mMap.put("type","1");
                mMap.put("user_id",mUserId);
                mMap.put("parentid","0");
                mMap.put("re_name","");
                Log.e("DH_COMMIT","vid:"+mVid);
                mCommentPresenter.addComment(mMap,CommitRemarkActivity.this);
            }
        });
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initOther() {

    }

    @Override
    public void onSuccess(Object... value) {
        LoadingUtils.getInstance().hideNetLoading();
        CommentEntity commentEntity = (CommentEntity) value[0];
        Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFault(String error) {
        LoadingUtils.getInstance().hideNetLoading();
        Toast.makeText(mContext, "提交异常:"+error, Toast.LENGTH_SHORT).show();
    }
}
