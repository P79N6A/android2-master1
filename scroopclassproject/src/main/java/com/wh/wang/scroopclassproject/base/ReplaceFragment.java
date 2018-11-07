package com.wh.wang.scroopclassproject.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by wang on 2017/8/14.
 */

@SuppressLint("ValidFragment")
public class ReplaceFragment extends Fragment {

    private BasePager currPager;
    private ArrayList<BasePager> mBasePagers;
    private int position;

    public ReplaceFragment() {

    }

    public ReplaceFragment(BasePager basePager) {

        this.currPager = basePager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BasePager basePager2 = currPager;
        if (basePager2 != null) {
            return currPager.rootView;
        }
        return null;
    }
}
