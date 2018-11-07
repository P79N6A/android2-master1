package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ContactMap;
import com.wh.wang.scroopclassproject.newproject.eventmodel.EventContactEntity;
import com.wh.wang.scroopclassproject.newproject.eventmodel.FinishTaskEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.ContactPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.ApplyContactAdapter;
import com.wh.wang.scroopclassproject.utils.LogUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SelectApplyActivity extends Activity implements ApplyContactAdapter.OnDelClickListener, View.OnClickListener {
    private TextView mTitlebarbackssName;
    private RelativeLayout mWayCompany;
    private RelativeLayout mApplyContent;
    private RecyclerView mApplyList;
    private TextView mApplyNum;
    private String mId;
    private ApplyContactAdapter mApplyContactAdapter;
    private ContactPresenter mContactPresenter = new ContactPresenter();
    private TextView mApply;
    private String mUser_id;
    private String mLoginRandStr;
    private int mStatusCode;
    private List<ContactEntity.InfoBean> mCompanyContactList;
    private String mOriginal_price;
    private Serializable mTitle;
    private String mVip;

    //0: 视频  1：报名帖
    private int mAppyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_apply);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
//        mUser_id = "34478";
//        mLoginRandStr = "5a703449ac152";
        String isVideo = "";
        if(mAppyType==0){
            isVideo = "1";
            mTitlebarbackssName.setText(R.string.select_pay);
            mApply.setText("确认购买");
        }else{
            mTitlebarbackssName.setText(R.string.select_apply);
            mApply.setText("确认报名");
        }
//        mContactPresenter.getCompanyContactJson(mUser_id, mLoginRandStr, mId, isVideo, new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//
//            }
//
//            @Override
//            public void onFault(String error) {
//
//            }
//        });
        mContactPresenter.getCompanyContact(mUser_id, mLoginRandStr,mId,isVideo, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                ContactEntity entity = (ContactEntity) value[0];
                if(entity.getStatus()==1){
                    mStatusCode = entity.getStatusCode();
                    if(entity.getStatusCode()==0){
                        mWayCompany.setVisibility(View.GONE);
                        mWayCompany.setOnClickListener(null);
                    }else{
                        mWayCompany.setVisibility(View.VISIBLE);
                        mWayCompany.setOnClickListener(SelectApplyActivity.this);
                    }
                    mCompanyContactList = entity.getInfo();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_COMPANY_CONTACT",error);
            }
        });
    }

    private void initView() {
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mWayCompany = (RelativeLayout) findViewById(R.id.way_company);
        mApplyContent = (RelativeLayout) findViewById(R.id.apply_content);
        mApplyList = (RecyclerView) findViewById(R.id.apply_list);
        mApplyNum = (TextView) findViewById(R.id.apply_num);
        mApply = (TextView) findViewById(R.id.apply);

        setApplyNumTv(0);
        mApplyList.setLayoutManager(new LinearLayoutManager(this));
        mApplyContactAdapter = new ApplyContactAdapter(mScreenList);
        mApplyList.setAdapter(mApplyContactAdapter);
        mApplyContactAdapter.setOnDelClickListener(this);
    }
    private void initData() {
        mId = getIntent().getStringExtra("id");
        mOriginal_price = getIntent().getStringExtra("original_price");
        mTitle = getIntent().getStringExtra("title");
        mVip = getIntent().getStringExtra("vip");
        mAppyType = getIntent().getIntExtra("apply_type",0);
    }

    private void initListener() {
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.way_contacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectApplyActivity.this,ContactActivity.class);
                ContactMap contactMap = new ContactMap(mInfoBeanMap);
                intent.putExtra("type","0");
                intent.putExtra("contact_map",contactMap);
                intent.putExtra("id",mId);
                intent.putExtra("apply_type",mAppyType);
                startActivity(intent);
            }
        });
        mWayCompany.setOnClickListener(this);
    }

    public void setApplyNumTv(int num){
        String content = "报名人数："+num+"人";
        if(mAppyType==0){
            content = "购买人数："+num+"人";
        }
        int f_index = content.indexOf("：");
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#fd733d")), f_index+1,content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mApplyNum.setText(spannableString);
    }
    
    //筛选集合
    private List<ContactEntity.InfoBean> mScreenList = new ArrayList<>();

    private Map<String,ContactEntity.InfoBean> mInfoBeanMap = new LinkedHashMap<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addContect(EventContactEntity eventContactEntity){
        List<ContactEntity.InfoBean> list = eventContactEntity.getInfoBeanList();
        Log.e("DH_CONTACT_SUB","执行 list"+list.size());
        for (int i = 0; i < list.size(); i++) {
            mInfoBeanMap.put(list.get(i).getPhone(),list.get(i));
        }
        if(mInfoBeanMap.size()>0){
            if(mScreenList.size()>0){
                mScreenList.clear();
            }
            transformContact(mInfoBeanMap);
        }
        if(mScreenList!=null&&mScreenList.size()>0){
            mApplyContent.setVisibility(View.VISIBLE);
            if(mInfoBeanMap.size()>=2){
                mApply.setBackgroundResource(R.drawable.apply_share_bg);
                mApply.setOnClickListener(this);
            }else{
                mApply.setBackgroundResource(R.drawable.apply_share_no_bg);
                mApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SelectApplyActivity.this, "多人报名,不能少于2人", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            setApplyNumTv(mInfoBeanMap.size());
        }else{
            mApplyContent.setVisibility(View.GONE);
        }
        if(mApplyContactAdapter==null){
            mApplyContactAdapter = new ApplyContactAdapter(mScreenList);
            mApplyList.setAdapter(mApplyContactAdapter);
            mApplyContactAdapter.setOnDelClickListener(this);
        }else{
            mApplyContactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    private void transformContact(Map<String, ContactEntity.InfoBean> infoBeanMap) {
        Iterator<Map.Entry<String, ContactEntity.InfoBean>> iterator = infoBeanMap.entrySet().iterator();
        while (iterator.hasNext()){
            mScreenList.add(0,iterator.next().getValue());
        }
    }

    @Override
    public void onDelClick(ContactEntity.InfoBean bean) {
        if(mInfoBeanMap!=null){
            mInfoBeanMap.remove(bean.getPhone());
            setApplyNumTv(mInfoBeanMap.size());
            if(mInfoBeanMap.size()>=2){
                mApply.setBackgroundResource(R.drawable.apply_share_bg);
                mApply.setOnClickListener(this);
            }else{
                if(mInfoBeanMap.size()==0){
                    mApplyContent.setVisibility(View.GONE);
                }
                mApply.setBackgroundResource(R.drawable.apply_share_no_bg);
                mApply.setOnClickListener(null);
            }

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.apply:
                intent = new Intent(this,OrderActivity.class);
                intent.putExtra("id",mId);
                Log.e("mmmm",mId);
                intent.putExtra("vip",mVip);
                Log.e("mmmm",mVip);

                intent.putExtra("original_price",mOriginal_price);
                Log.e("mmmm",mOriginal_price);

                intent.putExtra("title",mTitle);
                Log.e("mmmm",mTitle.toString());

                intent.putExtra("apply_list", (Serializable) mScreenList);
                Gson gson = new Gson();

                Log.e("mmmm",gson.toJson(mScreenList));

                intent.putExtra("apply_type",mAppyType);
                Log.e("mmmm",mAppyType+"");
                intent.putExtra("flag","2");

                startActivity(intent);
                break;
            case R.id.way_company:
                if(mStatusCode==2){
                    intent = new Intent(SelectApplyActivity.this,ContactActivity.class);
                    ContactMap contactMap = new ContactMap(mInfoBeanMap);
                    intent.putExtra("type","1");
                    intent.putExtra("contact_map",contactMap);
                    intent.putExtra("company_list", (Serializable) mCompanyContactList);
                    intent.putExtra("id",mId);
                    intent.putExtra("apply_type",mAppyType);
                    startActivity(intent);
                }else{
                    Toast.makeText(MyApplication.mApplication, "请先购买企业号", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishTask(FinishTaskEntity entity){
        if(this!=null){
            finish();
        }
    }
}
