package com.wh.wang.scroopclassproject.newproject.ui.fragment.examine_frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.callback.OnRegisterNextClickListener;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.RegisterFragAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag.PersonInfoFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag.ShopInfoFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag.SubmitResultFragment;
import com.wh.wang.scroopclassproject.newproject.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener,OnRegisterNextClickListener {
    private ImageView mProgressTitle;
    private NoScrollViewPager mRegisterContent;
    private int[] progress_id = {R.drawable.step_1_bg,R.drawable.step_2_bg,R.drawable.step_3_bg};
    private ShopInfoFragment mShopInfoFragment = new ShopInfoFragment();
    private PersonInfoFragment mPersonInfoFragment = new PersonInfoFragment();
    private SubmitResultFragment mSubmitResultFragment= new SubmitResultFragment();
    private List<Fragment> mFragmentList = new ArrayList<>();
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        iniFrags();
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        mRegisterContent = (NoScrollViewPager) view.findViewById(R.id.register_content);
        mProgressTitle = (ImageView) view.findViewById(R.id.progress_title);
    }



    private void iniFrags() {
        FragmentManager supportFragmentManager = getChildFragmentManager();
        mFragmentList.add(mShopInfoFragment);
        mFragmentList.add(mPersonInfoFragment);
        mFragmentList.add(mSubmitResultFragment);
        mRegisterContent.setAdapter(new RegisterFragAdapter(supportFragmentManager,mFragmentList));
    }

    private void initData() {
    }

    private void initListener() {
        mRegisterContent.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.titlebarbackss_imageViewback:
//                if(mCurrentPos==1){
//                    registerProgress(0);
//                }else{
//                    finish();
//                }
//                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

//    @Override
//    public void onBackPressed() {
//        if(mCurrentPos==1){
//            registerProgress(0);
//        }else{
//            super.onBackPressed();
//        }
//    }

    private int mCurrentPos = 0;
    @Override
    public void OnNextClick(int pos) {
        registerProgress(pos);
    }

    public void registerProgress(int pos){
        if(pos==-1){
            getActivity().finish();
            return;
        }
        mCurrentPos = pos;
        mRegisterContent.setCurrentItem(pos);
        mProgressTitle.setImageResource(progress_id[pos]);
//        for (int i = 0; i < mProgressTvs.length; i++) {
//            if(i<pos){
//                mProgressTvs[i].setText("✔");
//                mProgressTvs[i].setBackgroundResource(R.drawable.progress_bg_shape);
//            }else if(i>pos){
//                mProgressTvs[i].setText("");
//                mProgressTvs[i].setBackgroundResource(R.drawable.no_progress_bg_shape);
//            }else{
//                mProgressTvs[i].setText((pos+1)+"");
//                mProgressTvs[i].setBackgroundResource(R.drawable.progress_bg_shape);
//            }
//        }
//
//        for (int i = 0; i < mLines.length; i++) {
//            if(i<pos){
//                mLines[i].setBackgroundColor(Color.parseColor("#ffffff"));
//            }else{
//                mLines[i].setBackgroundColor(Color.parseColor("#66ffffff"));
//            }
//        }
    }
}
