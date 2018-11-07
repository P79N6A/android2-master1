package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0.SGHomeCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGHomeEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

public class StudyGroupHomeActivity extends BaseActivity implements View.OnClickListener, SGHomeCourseAdapter.OnJoinItemClickListener {
    private RecyclerView mSgHomeCourseList;
//    private TextView mTitle;

    private SGHomeCourseAdapter mSGHomeCourseAdapter;
    private StudyGroupPresenter mStudyGroupPresenter;
    private String mUserId;
    private TextView mMyStudyGroup;
//    private TextView mActionRule1;



    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_study_group;
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
//        mTitle = (TextView) findViewById(R.id.home_title);
        mMyStudyGroup = (TextView) findViewById(R.id.my_study_group);
//        mActionRule1 = (TextView) findViewById(R.id.action_rule_1);
        mSgHomeCourseList = (RecyclerView) findViewById(R.id.sg_home_course_list);
        mSgHomeCourseList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initDatas() {
        mTitleC.setText(R.string.study_group_title);

//        mActionRule1.setText("1. 挑选一个合适的学习小组，加入后参与打卡学习\n" +
//                "2. 每名用户最多可以同时加入3个学习小组\n" +
//                "3. 报名成功后根据开课时间进行学习，未开课期间无法观看视频课程\n" +
//                "4. 每日需依次完成各步骤：“观看视频”—“提交心得”，才算当日打卡成功\n" +
//                "5. 学习期间需连续学完每天系统下发的视频课程（1-3节/日）\n" +
//                "6. 每日学习的视频课程将于每天00:00更新，学习完成截止时间为每天23:59\n" +
//                "7. 每完每天的视频课程学习，需提交学习心得\n" +
//                "8. 学习期间有任意一天未完成当天学习视频，则视为打卡失败，无法获得奖励，但可继续学习课程\n" +
//                "9. 报名或学习中遇到问题请联系勺子课堂工作人员：勺因斯坦（微信号：shaoziketang999）");
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(this);
        mMyStudyGroup.setOnClickListener(this);
    }

    @Override
    public void initOther() {

    }

    @Override
    protected void onResume() {
        super.onResume();
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

                        mSGHomeCourseAdapter = new SGHomeCourseAdapter(mContext, listBeans);
                        mSGHomeCourseAdapter.setOnJoinItemClickListener(StudyGroupHomeActivity.this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_l:
                finish();
                break;
            case R.id.my_study_group:
                Intent intent;
                if(StringUtils.isEmpty(mUserId)){
                    intent = new Intent(mContext,LoginNewActivity.class);
                }else{
                    intent = new Intent(this,MyPunchCardActivity.class);
                }
                startActivity(intent);

                break;
        }
    }

    @Override
    public void onJoinClick(String pid,String vid,String bao) {
        Intent intent;
        if(StringUtils.isEmpty(mUserId)){
            intent = new Intent(mContext,LoginNewActivity.class);
        }else{
            intent = new Intent(mContext,StudyGroupInfoActivity.class);
            intent.putExtra("isbao",bao);
            intent.putExtra("pid",pid);
            intent.putExtra("vid",vid);
        }
        startActivity(intent);
    }
}
