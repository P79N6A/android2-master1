package com.wh.wang.scroopclassproject.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wh.wang.scroopclassproject.base.BaseFragment;

import java.util.List;

/**
 * Created by ying on 2016/1/29.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> list;

	public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}

	// 得到数据
	@Override
	public Fragment getItem(int position) {

		return list.get(position);
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

}
