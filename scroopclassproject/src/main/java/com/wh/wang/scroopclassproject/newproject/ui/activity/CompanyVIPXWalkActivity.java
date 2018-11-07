package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.apply_xwalk_frag.CompanyApplyXWalkFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.apply_xwalk_frag.VIPApplyXWalkFragment;

public class CompanyVIPXWalkActivity extends FragmentActivity {
    private FragmentManager mSupportFragmentManager;
    private Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_vip_xwalk);
        mSupportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        String flag = getIntent().getStringExtra("flag");
        if("0".equals(flag)){
            mFragment = new VIPApplyXWalkFragment();
        }else if("1".equals(flag)){
            String state = getIntent().getStringExtra("state");
            mFragment = new CompanyApplyXWalkFragment();
            Bundle b = new Bundle();
            b.putString("state",state);
            mFragment.setArguments(b);
        }
        fragmentTransaction.add(R.id.frag,mFragment);
        fragmentTransaction.commit();
    }
}
