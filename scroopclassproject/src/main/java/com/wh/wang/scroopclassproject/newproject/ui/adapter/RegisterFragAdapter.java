package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chdh on 2018/1/17.
 */

public class RegisterFragAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public RegisterFragAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList!=null?mFragmentList.size():0;
    }
}
