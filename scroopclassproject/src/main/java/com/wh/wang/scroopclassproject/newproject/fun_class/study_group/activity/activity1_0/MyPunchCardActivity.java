package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0.StudyGroupInfo_2_0_Activity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0.MyStudyGroupAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.MyStudyGroupEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

public class MyPunchCardActivity extends BaseActivity implements View.OnClickListener, MyStudyGroupAdapter.OnStudyGroupItemClickListener {
    private TextView mPunchingCardTv;
    private RecyclerView mPunchingList;
    private TextView mPunchCardFinishTv;
    private RecyclerView mPunchedList;
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mUserId;
    private LinearLayout mNoData;


    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_my_punch_card;
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
        mNoData = (LinearLayout) findViewById(R.id.no_data);
        mPunchingCardTv = (TextView) findViewById(R.id.punching_card_tv);
        mPunchingList = (RecyclerView) findViewById(R.id.punching_list);
        mPunchingList.setLayoutManager(new LinearLayoutManager(this));
        mPunchingList.setHasFixedSize(true);
        mPunchingList.setNestedScrollingEnabled(false);
        mPunchCardFinishTv = (TextView) findViewById(R.id.punch_card_finish_tv);
        mPunchedList = (RecyclerView) findViewById(R.id.punched_list);
        mPunchedList.setLayoutManager(new LinearLayoutManager(this));
        mPunchedList.setHasFixedSize(true);
        mPunchedList.setNestedScrollingEnabled(false);
    }

    @Override
    public void initDatas() {
        mTitleC.setText("我的学习小组");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        accessNet();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        if(StringUtils.isEmpty(mUserId)){
            finish();
        }
    }

    private void accessNet() {
        mStudyGroupPresenter.getMyStudyGroup(mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                MyStudyGroupEntity myStudyGroupEntity = (MyStudyGroupEntity) value[0];
                if("1".equals(myStudyGroupEntity.getStatus())){
                    if((myStudyGroupEntity.getMyclass()==null||myStudyGroupEntity.getMyclass().size()==0)&&
                            (myStudyGroupEntity.getMyfinishclass()==null||myStudyGroupEntity.getMyfinishclass().size()==0)){
                        mPunchingList.setVisibility(View.GONE);
                        mPunchingCardTv.setVisibility(View.GONE);
                        mPunchedList.setVisibility(View.GONE);
                        mPunchCardFinishTv.setVisibility(View.GONE);
                        mNoData.setVisibility(View.VISIBLE);
                        return;
                    }
                    mNoData.setVisibility(View.GONE);
                    if (myStudyGroupEntity.getMyclass() != null && myStudyGroupEntity.getMyclass().size() > 0) { //打卡中
                        mPunchingList.setVisibility(View.VISIBLE);
                        mPunchingCardTv.setVisibility(View.VISIBLE);
                        MyStudyGroupAdapter myStudyGroupAdapter = new MyStudyGroupAdapter(MyPunchCardActivity.this, myStudyGroupEntity.getMyclass());
                        mPunchingList.setAdapter(myStudyGroupAdapter);
                        myStudyGroupAdapter.setOnStudyGroupItemClickListener(MyPunchCardActivity.this);
                    }else{
                        mPunchingList.setVisibility(View.GONE);
                        mPunchingCardTv.setVisibility(View.GONE);
                    }

                    if (myStudyGroupEntity.getMyfinishclass() != null && myStudyGroupEntity.getMyfinishclass().size() > 0) { //已结束
                        mPunchedList.setVisibility(View.VISIBLE);
                        mPunchCardFinishTv.setVisibility(View.VISIBLE);
                        MyStudyGroupAdapter myStudyGroupAdapter = new MyStudyGroupAdapter(MyPunchCardActivity.this, myStudyGroupEntity.getMyfinishclass());
                        mPunchedList.setAdapter(myStudyGroupAdapter);
                        myStudyGroupAdapter.setOnStudyGroupItemClickListener(MyPunchCardActivity.this);
                    }else{
                        mPunchedList.setVisibility(View.GONE);
                        mPunchCardFinishTv.setVisibility(View.GONE);
                    }

                }else{
                    Toast.makeText(MyApplication.mApplication, myStudyGroupEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR","My Study Group:"+error);
            }
        });
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(this);
    }

    @Override
    public void initOther() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_l:
                finish();
                break;
        }
    }

    @Override
    public void onSGItemClick(String pid, String vid,String type) {

        if (StringUtils.isNotEmpty(mUserId)) {
            Intent intent = new Intent(this,StudyGroupInfo_2_0_Activity.class);
            intent.putExtra("pid",pid);
            intent.putExtra("vid",vid);
            intent.putExtra("isbao","1");
            intent.putExtra("type",type);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginNewActivity.class);
            startActivity(intent);
        }

    }
}
