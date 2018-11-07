package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.LikeOrRemindEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.LikeOrRemindPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.MessageAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

public class LikeOrRemindActivity extends Activity {
    private TextView mTitlebarbackssName;
    private RecyclerView mList;
    private String mType;
    private LikeOrRemindPresenter mLikeOrRemindPresenter = new LikeOrRemindPresenter();
    private String mUser_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_or_remind);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        mType = getIntent().getStringExtra("type");
        if("4".equals(mType)){
            mTitlebarbackssName.setText("收到的赞");
        }else if("5".equals(mType)){
            mTitlebarbackssName.setText("收到的提醒");
        }
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mLikeOrRemindPresenter.getLikeOrRemindList(mUser_id, mType, new OnResultListener() {
            private List<LikeOrRemindEntity.ListBean> mList;
            private MessageAdapter mMessageAdapter;

            @Override
            public void onSuccess(Object... value) {
                LikeOrRemindEntity entity = (LikeOrRemindEntity) value[0];
                if(entity.getCode()==200){
                    if(entity.getList()!=null&&entity.getList().size()>0){
                        mList = entity.getList();
                        mMessageAdapter = new MessageAdapter(mList);
                        LikeOrRemindActivity.this.mList.setAdapter(mMessageAdapter);
                        mMessageAdapter.setOnLikeOrRemindClickListener(new MessageAdapter.OnLikeOrRemindClickListener() {
                            @Override
                            public void onClick(int pos) {
                                LikeOrRemindEntity.ListBean listBean = mList.get(pos);
                                Log.e("DH_MSG_ID",listBean.getItem_id());
                                if("1295".equals(listBean.getItem_id())){
                                    if("1".equals(listBean.getIs_staff())){
                                        Intent intent = new Intent(LikeOrRemindActivity.this,CompanyDataActivity.class);
                                        intent.putExtra("url_type","2");
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(LikeOrRemindActivity.this,CompanyDataActivity.class);
                                        intent.putExtra("url_type","3");
                                        intent.putExtra("parent_id",listBean.getUser_id());
                                        startActivity(intent);
                                    }
                                }else{
                                    if("4".equals(mType)){
                                        Intent intent = new Intent(LikeOrRemindActivity.this, CompanyCourseActivity.class);
                                        intent.putExtra("id",listBean.getItem_id());
                                        if(listBean.getIs_staff()==1){
                                            intent.putExtra("status",2);
                                        }else{
                                            intent.putExtra("status",1);
                                        }
                                        startActivity(intent);
                                    }else if("5".equals(mType)){
                                        Intent intent = null;
                                        if("4".equals(listBean.getItem_type())){
                                            intent = new Intent(LikeOrRemindActivity.this, SumUpActivity.class);
                                        }else{
                                            intent = new Intent(LikeOrRemindActivity.this, NewVideoInfoActivity.class);
                                        }
                                        intent.putExtra("id",listBean.getItem_id());
                                        intent.putExtra("type",listBean.getItem_type());
                                        startActivity(intent);
                                    }
                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH",error);
            }
        });

    }
}
