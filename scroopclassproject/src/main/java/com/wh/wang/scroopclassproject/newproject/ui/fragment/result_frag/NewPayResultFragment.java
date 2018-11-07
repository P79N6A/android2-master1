package com.wh.wang.scroopclassproject.newproject.ui.fragment.result_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CourseSuccessInfoPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;


public class NewPayResultFragment extends Fragment implements  View.OnClickListener {
    private int mPage = 0;
    private final int mPageRow = 10;
    private CourseSuccessInfoPresenter mCourseSuccessInfoPresenter = new CourseSuccessInfoPresenter();
    private String mUser_id;
    private String mLoginRandStr;
    private String mName;
    private String mOrder_no;
    private RelativeLayout mSinglePay;
    private TextView mSingleBt;
    private String mCourse_type;


    public NewPayResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_pay_result, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mSingleBt.setOnClickListener(this);
    }

    private void initData() {
        mName = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.getApplication(),"nickname","");
        Bundle b = getArguments();
        mOrder_no = b.getString("order_no");
        mCourse_type = b.getString("course_type");
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "login_rand_str", "");
    }

    @Override
    public void onResume() {
        super.onResume();
        getSuccessInfo();
    }

    int mNum = 0;
    private String mCourseId;

    private void getSuccessInfo() {
        mCourseSuccessInfoPresenter.getSuccessInfo(mOrder_no, mPage+"", mPageRow+"", mUser_id, mLoginRandStr, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                CourseResultEntity entity = (CourseResultEntity) value[0];
                if(entity.getStatus()==1){
                    CourseResultEntity.InfoBean entityInfo = entity.getInfo();
                    if(entityInfo!=null){
                        mCourseId = entityInfo.getProduct_id();
                    }
                }else{
                    Log.e("DH_SUCCESS_INFO",entity.getStatus()+"");
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    private void initView(View view) {
        mSinglePay = (RelativeLayout) view.findViewById(R.id.single_pay);
        mSingleBt = (TextView) view.findViewById(R.id.single_bt);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.single_bt:
                if("4".equals(mCourse_type)){
                    intent = new Intent(getActivity(), SumUpActivity.class);
                }else{
                    intent = new Intent(getActivity(), NewVideoInfoActivity.class);
                }
                intent.putExtra("id",mCourseId);
                intent.putExtra("type",mCourse_type);
                startActivity(intent);
                break;
        }
    }
}
