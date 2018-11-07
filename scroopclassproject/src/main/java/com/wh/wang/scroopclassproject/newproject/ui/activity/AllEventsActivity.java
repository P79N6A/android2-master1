package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MonthEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.NewMorePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.AllEventAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.NewIntentUtils;

public class AllEventsActivity extends Activity implements View.OnClickListener, AllEventAdapter.OnEventItemClickListener, OnRefreshListener {
    private TextView mTitlebarbackssName;
    private SwipeToLoadLayout mBrowseTitleSwipe;
    private RecyclerView mSwipeTarget;
    private AllEventAdapter mAllEventAdapter;
    private String mMonth;
    private String mMonthFlag;
    private NewMorePresenter mNewMorePresenter = new NewMorePresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        initIntentData();
        initView();
        getNetData();
        initListener();

    }

    private void initListener() {
        mBrowseTitleSwipe.setOnRefreshListener(this);
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(this);
    }

    private void initIntentData() {
        mMonth = getIntent().getStringExtra("month");
        mMonthFlag = getIntent().getStringExtra("month_flag");
    }

    private void initView() {
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mBrowseTitleSwipe = (SwipeToLoadLayout) findViewById(R.id.browse_title_swipe);
        mSwipeTarget = (RecyclerView) findViewById(R.id.swipe_target);
        mTitlebarbackssName.setText(mMonth+"月线下课");
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
        }
    }


    private void getNetData() {
        mNewMorePresenter.getMonthEvents(mMonthFlag, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                MonthEventEntity monthEventEntity = (MonthEventEntity) value[0];
                mAllEventAdapter = new AllEventAdapter(AllEventsActivity.this,monthEventEntity.getMonth_list());
                mSwipeTarget.setAdapter(mAllEventAdapter);
                mAllEventAdapter.setOnEventItemClickListener(AllEventsActivity.this);
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR","Month event:"+error);
            }
        });
    }



    @Override
    public void onEventItemClick(String sel_type,String id, String type) {
        new NewIntentUtils().IntentActivity(sel_type,type,id,this);
    }


    private Handler mOnEventHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    getNetData();
                    mBrowseTitleSwipe.setRefreshing(false);
                    break;
            }
        }
    };
    @Override
    public void onRefresh() {
        mOnEventHandler.sendEmptyMessageAtTime(0,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOnEventHandler.removeCallbacksAndMessages(null);
    }
}
