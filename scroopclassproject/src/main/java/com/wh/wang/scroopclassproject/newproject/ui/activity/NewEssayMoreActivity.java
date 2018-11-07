package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayLabelEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MoreOrAllCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.EssayMoreLabelAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.essay_more_frag.EssayMoreFragment;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;

import java.util.ArrayList;
import java.util.List;

public class NewEssayMoreActivity extends BaseActivity implements EssayMoreLabelAdapter.OnEssayLabelClickListener, ViewPager.OnPageChangeListener {

    private MoreOrAllCoursePresenter mMoreOrAllCoursePresenter = new MoreOrAllCoursePresenter();
    private RecyclerView mLabelList;
    private EssayMoreLabelAdapter mEssayMoreLabelAdapter;

    private int mMaxItem = 4;

    private ViewPager mEssayList;

    private List<Fragment> mEssayFrags = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_new_essay_more;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void initView() {
        mLabelList = (RecyclerView) findViewById(R.id.label_list);
        mEssayList = (ViewPager) findViewById(R.id.essay_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLabelList.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void initDatas() {
        mTitleC.setText("餐饮好文");
        LoadingUtils.getInstance().showNetLoading(this);
        mMoreOrAllCoursePresenter.getMoreEssayLabel(new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                List<EssayLabelEntity> labelEntityList = (List<EssayLabelEntity>) value[0];
                if(labelEntityList==null||labelEntityList.size()==0){
                    LoadingUtils.getInstance().hideNetLoading();
                    return;
                }

                mEssayList.setOffscreenPageLimit(labelEntityList.size());
                for (int i = 0; i < labelEntityList.size(); i++) {
                    EssayMoreFragment essayMoreFragment = new EssayMoreFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type",labelEntityList.get(i).getType());
                    essayMoreFragment.setArguments(bundle);
                    mEssayFrags.add(essayMoreFragment);
                }
                mEssayList.setAdapter(new BoutiqueVPAdapter(getSupportFragmentManager(),mEssayFrags));

                labelEntityList.get(0).setCheck(true);
                mEssayMoreLabelAdapter = new EssayMoreLabelAdapter(NewEssayMoreActivity.this, labelEntityList);
                mEssayMoreLabelAdapter.setOnEssayLabelClickListener(NewEssayMoreActivity.this);
                mLabelList.setAdapter(mEssayMoreLabelAdapter);


            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_MORE_LABEL",error);
            }
        });
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEssayList.addOnPageChangeListener(this);
    }

    @Override
    public void initOther() {

    }

//    private int startX = 0;
//    private void selectInfoAni(final int item) {
//        mEssayMoreLabelAdapter.notifyPos(item);
//        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mPhoneW / mLabelNum * item, 0, 0);
//        translateAnimation.setDuration(200);
//        translateAnimation.setFillAfter(true);
//        mLine.startAnimation(translateAnimation);
//        startX = mPhoneW / mLabelNum * item;
//    }

    @Override
    public void onEssayLabelClick(int pos, String type) {
        mEssayList.setCurrentItem(pos);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    private int mCurrentPos = 0; //获取
    @Override
    public void onPageSelected(int position) {
//        selectInfoAni(position);
        int firstPos = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int lastPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        if(position<firstPos||position>lastPos){
            mLabelList.smoothScrollToPosition(position);
        }
        mEssayMoreLabelAdapter.notifyPos(position);
//        if(position>=mMaxItem||(mCurrentPos>=mMaxItem&&position<=mCurrentPos-mMaxItem)){
//            //当前位置大于最大数量 跳转处于半展示或不展示状态 需要移动到指定位置
//            mLabelList.smoothScrollToPosition(position);
//        }
//        mCurrentPos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
