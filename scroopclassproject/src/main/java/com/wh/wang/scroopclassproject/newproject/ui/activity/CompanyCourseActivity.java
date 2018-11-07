package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCommonCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.LikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CompanyCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.LikePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.RemindPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CommonMemberCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CompanyLearnedAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CompanyRemindAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CompanyCourseActivity extends Activity implements View.OnClickListener, OnRefreshListener {
    private View LoadingView;
    private RecyclerView mLearnedList;
    private RecyclerView mNeverLearnedList;
    private ImageView mBack;
    private String mId;
    private int mStatus;
    private RelativeLayout mNeverLearnedTitle;
    private TextView mLearnedNum;
    private TextView mTimeNum;
    private TextView mLearnedPer;
    private TextView mNeverLearned;
    private TextView mOneKeyRemind;

    private CompanyCoursePresenter mCompanyCoursePresenter = new CompanyCoursePresenter();
    private RemindPresenter mRemindPresenter = new RemindPresenter();
    private List<CompanyCourseEntity.InfoBean.FinishBean> mFinish;
    private List<CompanyCourseEntity.InfoBean.NofinishBean> mNofinish;
    private List<CompanyCommonCourseEntity.InfoBean.FinishBean> mCommonFinish;

    private CompanyLearnedAdapter mCompanyLearnedAdapter;
    private LikePresenter mLikePresenter = new LikePresenter();
    private String mUser_id;
    private RelativeLayout mLearnedTitle;
    private CommonMemberCourseAdapter mCommonMemberCourseAdapter;
    private CompanyRemindAdapter mCompanyRemindAdapter;
    private TextView mCompanyGroupName;
    private TextView mCompanyGroupNum;
    private TextView mOneKeyLike;
    private SwipeToLoadLayout mFragSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_course);

        EventBus.getDefault().register(this);
        FrameLayout rootView = (FrameLayout) findViewById(android.R.id.content);
        LoadingView = LoadingUtils.VideoActivityLoading(rootView);
        initView();
        initRecycle();
        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initListener() {
        mOneKeyLike.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mOneKeyRemind.setOnClickListener(this);
        mFragSwipe.setOnRefreshListener(this);
    }


    private void initView() {
        mLearnedList = (RecyclerView) findViewById(R.id.learned_list);
        mNeverLearnedList = (RecyclerView) findViewById(R.id.never_learned_list);
        mNeverLearnedTitle = (RelativeLayout) findViewById(R.id.never_learned_title);
        mLearnedNum = (TextView) findViewById(R.id.learned_num);
        mTimeNum = (TextView) findViewById(R.id.time_num);
        mLearnedPer = (TextView) findViewById(R.id.learned_per);
        mNeverLearned = (TextView) findViewById(R.id.never_learned);
        mLearnedTitle = (RelativeLayout) findViewById(R.id.learned_title);
        mCompanyGroupName = (TextView) findViewById(R.id.company_group_name);
        mCompanyGroupNum = (TextView) findViewById(R.id.company_group_num);
        mOneKeyLike = (TextView) findViewById(R.id.one_key_like);
        mBack = (ImageView) findViewById(R.id.back);
        mOneKeyRemind = (TextView) findViewById(R.id.one_key_remind);
        mFragSwipe = (SwipeToLoadLayout) findViewById(R.id.frag_swipe);

    }

    private void initRecycle() {
        mLearnedList.setLayoutManager(new LinearLayoutManager(this));
        mNeverLearnedList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
//        mUser_id = "42980";//TODO
        mId = getIntent().getStringExtra("id");
        mStatus = getIntent().getIntExtra("status",0);
        Log.e("DH_Course",mId);
        getCompanyCourseInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reconnection(ReconnectionEntity reconnectionEntity){
        getCompanyCourseInfo();
    }


    private void getCompanyCourseInfo() {
        if(mStatus==2){
            mNeverLearnedTitle.setVisibility(View.GONE);
            mNeverLearnedList.setVisibility(View.GONE);
            mLearnedTitle.setVisibility(View.GONE);
            mCompanyCoursePresenter.getCompanyCommonCourse(mUser_id, mId, new OnResultListener() {

                @Override
                public void onSuccess(Object... value) {
                    LoadingView.setVisibility(View.GONE);
                    CompanyCommonCourseEntity entity = (CompanyCommonCourseEntity) value[0];
                    if(entity.getCode()==200){
                        if(entity.getInfo()!=null){
                            mCompanyGroupName.setText(entity.getInfo().getTitle());
                            mCompanyGroupNum.setText(entity.getInfo().getVideo_length());
                            mLearnedNum.setText(entity.getInfo().getTotal_person()+"");
                            mTimeNum.setText(entity.getInfo().getTotal_time()+"");
                            mCommonFinish = entity.getInfo().getFinish();
                            if(mCommonFinish!=null&&mCommonFinish.size()>0){
                                mCommonMemberCourseAdapter = new CommonMemberCourseAdapter(mCommonFinish);
                                mLearnedList.setAdapter(mCommonMemberCourseAdapter);
                                mCommonMemberCourseAdapter.setOnLikeClickListener(new CommonMemberCourseAdapter.OnLikeClickListener() {
                                    @Override
                                    public void onLikeClick(final int pos, final ImageView img, final TextView tv, String id) {
                                        mLikePresenter.like(mUser_id, id,"0",mId, new OnResultListener() {
                                            @Override
                                            public void onSuccess(Object... value) {
                                                LikeEntity likeEntity = (LikeEntity) value[0];
                                                if(likeEntity.getCode()==200){
                                                    int num = Integer.parseInt(tv.getText().toString());
                                                    if("0".equals(likeEntity.getInfo().getZhan())){
                                                        img.setImageResource(R.drawable.learn_list_unlike);
                                                        if(num>0){
                                                            num--;
                                                        }
                                                        mCommonFinish.get(pos).setZhan("0");
                                                    }else{
                                                        img.setImageResource(R.drawable.learn_list_like);
                                                        num++;
                                                        mCommonFinish.get(pos).setZhan("1");
                                                    }
                                                    mCommonFinish.get(pos).setZhansum(num+"");
                                                    tv.setText(num+"");
                                                }else{
                                                    Toast.makeText(CompanyCourseActivity.this, ""+likeEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onFault(String error) {
                                                Log.e("DH_LIKE",error);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    }

                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_COMMON_COURSE",error);
                }
            });
        }else{
            mLearnedTitle.setVisibility(View.VISIBLE);
            mNeverLearnedTitle.setVisibility(View.VISIBLE);
            mNeverLearnedList.setVisibility(View.VISIBLE);
            mCompanyCoursePresenter.getCompanyCourse(mUser_id, mId, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    LoadingView.setVisibility(View.GONE);
                    CompanyCourseEntity entity = (CompanyCourseEntity) value[0];
                    if(entity.getCode()==200){
                        mCompanyGroupName.setText(entity.getInfo().getTitle());
                        mCompanyGroupNum.setText(entity.getInfo().getVideo_length());
                        mLearnedPer.setText("已学完("+(entity.getInfo().getFinish()==null?0:entity.getInfo().getFinish().size())+"人)");
                        mNeverLearned.setText("未学完("+(entity.getInfo().getNofinish()==null?0:entity.getInfo().getNofinish().size())+"人)");
                        mLearnedNum.setText(entity.getInfo().getTotal_person()+"");
                        mTimeNum.setText(entity.getInfo().getTotal_time()+"");
                        mFinish = entity.getInfo().getFinish();
                        if(mFinish!=null&&mFinish.size()>0){
                            checkOneLike();
                            mLearnedTitle.setVisibility(View.VISIBLE);
                            mLearnedList.setVisibility(View.VISIBLE);
                            mCompanyLearnedAdapter = new CompanyLearnedAdapter(mFinish);
                            mLearnedList.setAdapter(mCompanyLearnedAdapter);
                            mCompanyLearnedAdapter.setOnLikeClickListener(new CompanyLearnedAdapter.OnLikeClickListener() {
                                @Override
                                public void onLikeClick(final int pos, final ImageView img, final TextView textView, String id) {
                                    Log.e("DH_LIKE",mUser_id+"  "+id);
                                    mLikePresenter.like(mUser_id, id,"0",mId, new OnResultListener() {
                                        @Override
                                        public void onSuccess(Object... value) {
                                            LikeEntity likeEntity = (LikeEntity) value[0];
                                            if(likeEntity.getCode()==200){
                                                int num = Integer.parseInt(textView.getText().toString());
                                                if("0".equals(likeEntity.getInfo().getZhan())){
                                                    img.setImageResource(R.drawable.learn_list_unlike);
                                                    num--;
                                                    mFinish.get(pos).setZhan("0");
                                                    oneLikeState(1);
                                                }else{
                                                    img.setImageResource(R.drawable.learn_list_like);
                                                    num++;
                                                    mFinish.get(pos).setZhan("1");
                                                    checkOneLike();
                                                }
                                                mFinish.get(pos).setZhansum(num+"");
                                                textView.setText(num+"");
                                            }else{
                                                Toast.makeText(CompanyCourseActivity.this, ""+likeEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onFault(String error) {
                                            Log.e("DH_LIKE",error);
                                        }
                                    });
                                }
                            });
                        }else{
                            mLearnedTitle.setVisibility(View.GONE);
                            mLearnedList.setVisibility(View.GONE);
                        }

                        mNofinish = entity.getInfo().getNofinish();
                        if(mNofinish!=null&&mNofinish.size()>0){
                            checkOneRemind();
                            mNeverLearnedTitle.setVisibility(View.VISIBLE);
                            mNeverLearnedList.setVisibility(View.VISIBLE);
                            mCompanyRemindAdapter = new CompanyRemindAdapter(mNofinish);
                            mNeverLearnedList.setAdapter(mCompanyRemindAdapter);
                            mCompanyRemindAdapter.setOnTiXingClickListener(new CompanyRemindAdapter.OnTiXingClickListener() {
                                @Override
                                public void onTXClick(final int pos, String id, final TextView textView) {
                                    mRemindPresenter.remind(mUser_id, mId, id+",", new OnResultListener() {
                                        @Override
                                        public void onSuccess(Object... value) {
                                            textView.setBackgroundResource(R.drawable.apply_examine_bg);
                                            textView.setTextColor(Color.parseColor("#C2C3C6"));
                                            textView.setText("已提醒");
                                            mNofinish.get(pos).setTixing("1");
                                            checkOneRemind();
                                        }

                                        @Override
                                        public void onFault(String error) {

                                        }
                                    });
                                }
                            });
                        }else{
                            mNeverLearnedTitle.setVisibility(View.GONE);
                            mNeverLearnedList.setVisibility(View.GONE);
                        }
                    }else{
                        Toast.makeText(CompanyCourseActivity.this, entity.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_Course",error);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one_key_like:
                mLikePresenter.like(mUser_id, "0","1",mId, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        LikeEntity likeEntity = (LikeEntity) value[0];
                        if(likeEntity.getCode()==200){
                            if(mFinish!=null&&mFinish.size()>0){
                                for (int i = 0; i < mFinish.size(); i++) {
                                    CompanyCourseEntity.InfoBean.FinishBean finishBean = mFinish.get(i);
                                    if("0".equals(finishBean.getZhan())){
                                        finishBean.setZhan("1");
                                        int num = 0;
                                        try{
                                            num = Integer.parseInt(finishBean.getZhansum());
                                        }catch (Exception e){
                                            num = 0;
                                        }
                                        finishBean.setZhansum((num+1)+"");
                                    }
                                }
                                if(mCompanyLearnedAdapter!=null){
                                    mCompanyLearnedAdapter.notifyDataSetChanged();
                                }
                                oneLikeState(0);
                            }
                        }else{
                            Toast.makeText(CompanyCourseActivity.this, ""+likeEntity.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
                break;
            case R.id.back:
                finish();
                break;
            case R.id.one_key_remind:
                StringBuffer child_id = new StringBuffer();
                for (int i = 0; i < mNofinish.size(); i++) {
                    if("0".equals(mNofinish.get(i).getTixing())){
                        mNofinish.get(i).setTixing("1");
                        child_id.append(mNofinish.get(i).getUser_id()+",");
                    }
                }
                mRemindPresenter.remind(mUser_id, mId, child_id==null?"":child_id.toString(), new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        mOneKeyRemind.setOnClickListener(null);
                        mOneKeyRemind.setText("已提醒");
                        mOneKeyRemind.setBackgroundResource(R.drawable.shape_ed_bg);
                        if(mCompanyRemindAdapter!=null){
                            mCompanyRemindAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
                break;
        }
    }

    public void oneLikeState(int state){
        mOneKeyLike.setVisibility(View.VISIBLE);
        if(state==0){
            mOneKeyLike.setBackgroundResource(R.drawable.shape_ed_bg);
            mOneKeyLike.setText("已点赞");
            mOneKeyLike.setOnClickListener(null);
        }else{
            mOneKeyLike.setBackgroundResource(R.drawable.imm_vip_shape);
            mOneKeyLike.setText("一键点赞");
            mOneKeyLike.setOnClickListener(CompanyCourseActivity.this);
        }
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    oneLikeState(0);
                    break;
                case 1:
                    oneLikeState(1);
                    break;
                case 2:
                    mOneKeyRemind.setOnClickListener(CompanyCourseActivity.this);
                    mOneKeyRemind.setText("全部提醒");
                    mOneKeyRemind.setBackgroundResource(R.drawable.imm_vip_shape);
                    break;
                case 3:
                    mOneKeyRemind.setOnClickListener(null);
                    mOneKeyRemind.setText("已提醒");
                    mOneKeyRemind.setBackgroundResource(R.drawable.shape_ed_bg);
                    break;
            }
        }
    };
    public void checkOneLike(){
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < mFinish.size(); i++) {
                    if("0".equals(mFinish.get(i).getZhan())){
                        mHandler.sendEmptyMessage(1);
                        break;
                    }
                    if(i==mFinish.size()-1){
                        mHandler.sendEmptyMessage(0);
                    }
                }
            }
        }.start();
    }

    public void checkOneRemind(){
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < mNofinish.size(); i++) {
                    if(mNofinish.get(i).getUser_id().equals(mUser_id)){
                        mNofinish.get(i).setTixing("1");
                    }
                    if("0".equals(mNofinish.get(i).getTixing())){
                        mHandler.sendEmptyMessage(2);
                        break;
                    }
                    if(i==mNofinish.size()-1){
                        mHandler.sendEmptyMessage(3);
                    }
                }
            }
        }.start();
    }

    @Override
    public void onRefresh() {
        getCompanyCourseInfo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.mApplication, "刷新成功", Toast.LENGTH_SHORT).show();
                mFragSwipe.setRefreshing(false);
            }
        },1500);
    }
}
