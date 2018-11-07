package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.InviteEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.InvitePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.InviteListAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

public class InviteActivity extends Activity {
    private TextView mTitlebarbackssName;
    private RecyclerView mInviteList;
    private InvitePresenter mInvitePresenter = new InvitePresenter();
    private String mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssName.setText(getResources().getString(R.string.my_invite));
        mInviteList = (RecyclerView) findViewById(R.id.invite_list);
        mInviteList.setLayoutManager(new LinearLayoutManager(this));
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
//        mUserId = "88888";
        mInvitePresenter.getInviteList(mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                InviteEntity inviteEntity = (InviteEntity) value[0];
                if(inviteEntity.getStatus()==1){
                    mInviteList.setAdapter(new InviteListAdapter(inviteEntity.getInfo().getList()));
                }else{
                    Toast.makeText(MyApplication.mApplication, inviteEntity.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_INVITE_ERROR",error);
            }
        });


        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
