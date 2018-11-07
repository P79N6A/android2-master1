package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

public class ApplyResultActivity extends Activity implements View.OnClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private TextView mEnterFlock;
    private String mCourseId;
    private String mType;
    private TextView mExamineCourse;
    private String mTitle;
    private String mSubTitle;
    private String mShareUrl;
    private String mUser_id;
    private Dialog mShareDig;
    private String mVideo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_result);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mEnterFlock = (TextView) findViewById(R.id.enter_flock);
        mExamineCourse = (TextView) findViewById(R.id.examine_course);

        mTitlebarbackssName.setText("报名成功");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
    }

    private void initData() {
        mCourseId = getIntent().getStringExtra("id");
        mType = getIntent().getStringExtra("type");
        mTitle = getIntent().getStringExtra("title");
        mSubTitle = getIntent().getStringExtra("sub_title");
        mShareUrl = getIntent().getStringExtra("share_url");
        mVideo_id = getIntent().getStringExtra("video_id");
        if(StringUtils.isNotEmpty(mVideo_id)){
            mExamineCourse.setVisibility(View.VISIBLE);
        }else{
            mExamineCourse.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mEnterFlock.setOnClickListener(this);
        mExamineCourse.setOnClickListener(this);
    }

    private TextView mShare;
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
            case R.id.enter_flock:
                mShareDig = new Dialog(this, R.style.MyDialog);
                View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_share_course,null,false);
                mShareDig.setContentView(dView);
                mShare = (TextView) dView.findViewById(R.id.share);
                mShare.setOnClickListener(this);
                mShareDig.show();
                break;
            case R.id.share:
                Constant.isPublicCourseShare = true;
                ShareUtil.weiChat(Constants.wx_api, 8, ApplyResultActivity.this,
                        mShareUrl+ "?helper=" + mUser_id,
                        mTitle,
                        mSubTitle);
                if(mShareDig!=null&&mShareDig.isShowing()){
                    mShareDig.dismiss();
                }
                break;
            case R.id.examine_course:
                intent = new Intent(this,NewVideoInfoActivity.class);
                Log.e("DH_mVideo_id","mVideo_id:"+mVideo_id+"  mCourseId:"+mCourseId);
                intent.putExtra("id",mVideo_id);
                intent.putExtra("type",mType);
                startActivity(intent);
                break;
        }
    }
}
