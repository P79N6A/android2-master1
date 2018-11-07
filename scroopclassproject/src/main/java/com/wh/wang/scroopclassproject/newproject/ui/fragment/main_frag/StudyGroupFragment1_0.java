package com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0.MyPunchCardActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0.StudyGroupInfoActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0.SGHomeCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGHomeEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

import static com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils.getStatusBarHeight;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudyGroupFragment1_0 extends Fragment implements View.OnClickListener, SGHomeCourseAdapter.OnJoinItemClickListener {

    private RecyclerView mSgHomeCourseList;
//    private TextView mTitle;
    private View mStatusTitle;

    private SGHomeCourseAdapter mSGHomeCourseAdapter;
    private StudyGroupPresenter mStudyGroupPresenter;
    private String mUserId;
    private ImageView mMyStudyGroup;
    public StudyGroupFragment1_0() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study_group_1_0, container, false);
//        mTitle = (TextView) view.findViewById(R.id.home_title);
        mMyStudyGroup = (ImageView) view.findViewById(R.id.my_study_group);
//        mActionRule1 = (TextView) findViewById(R.id.action_rule_1);
        mSgHomeCourseList = (RecyclerView) view.findViewById(R.id.sg_home_course_list);
        mSgHomeCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
        mStatusTitle = (View) view.findViewById(R.id.status_title);
        mStatusTitle.post(new Runnable() {
            @Override
            public void run() {
                int statusBarHeight = getStatusBarHeight(getContext());
                mStatusTitle.measure(0,0);
                ViewGroup.LayoutParams layoutParams = mStatusTitle.getLayoutParams();
                layoutParams.height = mStatusTitle.getHeight()+statusBarHeight;
                mStatusTitle.setLayoutParams(layoutParams);
                mStatusTitle.setPadding(0, statusBarHeight, 0, 0);
            }
        });
        initListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        accessNet();
    }

    private void initListener() {
        mMyStudyGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_study_group:
                Intent intent;
                if(StringUtils.isEmpty(mUserId)){
                    intent = new Intent(getContext(),LoginNewActivity.class);
                }else{
                    intent = new Intent(getContext(),MyPunchCardActivity.class);
                }
                startActivity(intent);

                break;
        }
    }


    public void accessNet(){
        Log.e("DH_SG","uid:"+mUserId);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        if(mStudyGroupPresenter==null)
            mStudyGroupPresenter = new StudyGroupPresenter();
        mStudyGroupPresenter.getSGHomeInfo(mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                SGHomeEntity sgHomeEntity = (SGHomeEntity) value[0];
                if("200".equals(sgHomeEntity.getCode())){
                    if (sgHomeEntity.getInfo()!=null) {
//                        mTitle.setText(sgHomeEntity.getInfo().getTitle());
                        List<SGHomeEntity.InfoBean.ListBean> listBeans = sgHomeEntity.getInfo().getList();

                        mSGHomeCourseAdapter = new SGHomeCourseAdapter(getContext(), listBeans);
                        mSGHomeCourseAdapter.setOnJoinItemClickListener(StudyGroupFragment1_0.this);
                        mSgHomeCourseList.setAdapter(mSGHomeCourseAdapter);
                    }else{
                        Log.e("DH_ERROR","sgHomeEntity.getInfo() is NULL");
                    }
                }else{
                    Toast.makeText(MyApplication.mApplication, sgHomeEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR","SG_HOME:  "+error);
            }
        });
    }

    @Override
    public void onJoinClick(String pid, String vid, String bao) {
        Intent intent;
        if(StringUtils.isEmpty(mUserId)){
            intent = new Intent(getContext(),LoginNewActivity.class);
        }else{
            intent = new Intent(getContext(),StudyGroupInfoActivity.class);
            intent.putExtra("isbao",bao);
            intent.putExtra("pid",pid);
            intent.putExtra("vid",vid);
        }
        startActivity(intent);
    }
}
