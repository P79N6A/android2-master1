package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ContactMap;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.contact_frag.CompanyContactFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.contact_frag.MobileContactFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends FragmentActivity {
    private TextView mTitlebarbackssName;
    private FrameLayout mContact;
    private TextView mTitlebarbackssAction;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mContact = (FrameLayout) findViewById(R.id.contact);
        mTitlebarbackssAction = (TextView) findViewById(R.id.titlebarbackss_action);
        mTitlebarbackssName.setText("添加联系人");
    }


    private void initData() {
        String type = getIntent().getStringExtra("type");
        String id = getIntent().getStringExtra("id");
        int apply_type = getIntent().getIntExtra("apply_type",0); //联系人类型 0 手机   1  企业号
        ContactMap map = (ContactMap) getIntent().getSerializableExtra("contact_map");
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if("0".equals(type)){
            mTitlebarbackssAction.setVisibility(View.GONE);
            Bundle b = new Bundle();
            b.putString("id",id);
            b.putSerializable("contact_map",map);
            b.putInt("apply_type",apply_type);
            MobileContactFragment mobileContactFragment = new MobileContactFragment();
            mobileContactFragment.setArguments(b);
            fragmentTransaction.add(R.id.contact,mobileContactFragment);
        }else if("1".equals(type)){
            mTitlebarbackssAction.setVisibility(View.VISIBLE);
            mTitlebarbackssAction.setText("完成");
            List<ContactEntity.InfoBean> infoBeanList = (ArrayList<ContactEntity.InfoBean>) getIntent().getSerializableExtra("company_list");
            Bundle b = new Bundle();
            b.putString("id",id);
            b.putSerializable("contact_map",map);
            b.putSerializable("company_list", (Serializable) infoBeanList);
            final CompanyContactFragment companyContactFragment = new CompanyContactFragment();
            companyContactFragment.setArguments(b);
            fragmentTransaction.add(R.id.contact,companyContactFragment);
            mTitlebarbackssAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    companyContactFragment.finish();
                }
            });
        }
        fragmentTransaction.commit();
    }

    private void initListener() {
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
