package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.AljDetailsEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.AljPresenter;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import static com.wh.wang.scroopclassproject.newproject.Constant.isFree7Vip;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;

public class CasebookInviteActivity extends Activity implements View.OnClickListener {
    private TextView mTitlebarbackssName;
    private TextView mInvite;
    private ImageView mTitlebarbackssShare;
    private String user_status;
    private String mUser_id;
    private String mEvent_id;
    private ImageView mBg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casebook);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mBg = (ImageView) findViewById(R.id.bg);
        mInvite = (TextView) findViewById(R.id.invite);
        mTitlebarbackssName.setText("分享得双重好礼");
        initListener();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        if(Constant.SHARE_STATUE){
//            System.out.println("CasebookInviteActivity.onStart");
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mEvent_id = getIntent().getStringExtra("event_id");
//        mEvent_id = "1363";
        Log.e("DH_ALJ_ID","user_id"+mUser_id);
        new AljPresenter().getAljDetails(mUser_id, mEvent_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                AljDetailsEntity aljDetailsEntity = (AljDetailsEntity) value[0];
                if (aljDetailsEntity.getInfo()!=null) {
                    AljDetailsEntity.InfoBean info = aljDetailsEntity.getInfo();
                    user_status = info.getUser_status();
                    if("0".equals(user_status)||StringUtils.isEmpty(mUser_id)){
                        mInvite.setText("分享抢书");
                    }else{
                        mInvite.setText("已分享，查看进度");
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ALJ_DETAILS",error);
            }
        });
    }

    private void initListener() {
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.titlebarbackss_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isNotEmpty(mUser_id)){
                    showShareDig();
                }else{
                    Intent intent = new Intent(CasebookInviteActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }

            }
        });
        mInvite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.invite:
                Intent intent = null;

                if(StringUtils.isNotEmpty(mUser_id)){
                    if("0".equals(user_status)){
                        showShareDig();
                    }else{
                        intent = new Intent(this,CasebookInfoActivity.class);
                        intent.putExtra("event_id",mEvent_id);
                        startActivity(intent);
                    }
                }else{
                    intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                }


                break;
        }
    }

    private Dialog mShareDialog;

    private void showShareDig() {
        //分享
        mShareDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        //初始化控件
        RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
        RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
        RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);

        share_rlayout_py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Constant.SHARE_STATUE = true;

                mShareDialog.dismiss();
                isFree7Vip = true;
                temporaryEventNo = mEvent_id;
                ShareUtil.weiChat(Constants.wx_api, 7,
                        CasebookInviteActivity.this, "http://www.shaoziketang.com/pcshaozi/invite_index.html?parent_id="+mUser_id,
                        getString(R.string.alj_share_title), getString(R.string.alj_share_desc),R.drawable.wx_xct_st);
            }
        });

        share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Constant.SHARE_STATUE = true;

                mShareDialog.dismiss();
                isFree7Vip = true;
                temporaryEventNo = mEvent_id;
                ShareUtil.weiChat(Constants.wx_api, 8,
                        CasebookInviteActivity.this, "http://www.shaoziketang.com/pcshaozi/invite_index.html?parent_id="+mUser_id,
                        getString(R.string.alj_share_title), getString(R.string.alj_share_desc),R.drawable.wx_xct_st);
            }
        });

        share_llayout_concel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
            }
        });

        //将布局设置给Dialog
        mShareDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mShareDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mShareDialog.show();//显示对话框
    }
}
