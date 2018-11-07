package com.wh.wang.scroopclassproject.newproject.ui.fragment.search_result;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.TableActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchResultEntity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SearchCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SearchEssayAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SearchEventAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.NewIntentUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchAllFragment extends Fragment implements View.OnClickListener, OnSearchItemClickListener {

    private RelativeLayout mOfflineEvent;
    private TextView mOfflineNum;
    private TextView mCheckMore;
    private RecyclerView mOfflineList;
    private RelativeLayout mCourse;
    private TextView mCourseNum;
    private RecyclerView mCourseList;
    private RelativeLayout mEssay;
    private TextView mEssayNum;
    private RecyclerView mEssayList;
    private NewSearchResultEntity mNewSearchResultEntity;
    private TextView mCourseLookAll;
    private TextView mEssayLookAll;
    private SearchEventAdapter mSearchEventAdapter;
    private SearchCourseAdapter mSearchCourseAdapter;
    private SearchEssayAdapter mSearchEssayAdapter;
    private String mKey;

    public SearchAllFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_all, container, false);

        mOfflineEvent = (RelativeLayout) view.findViewById(R.id.offline_event);
        mOfflineNum = (TextView) view.findViewById(R.id.offline_num);
        mCheckMore = (TextView) view.findViewById(R.id.check_more);
        mOfflineList = (RecyclerView) view.findViewById(R.id.offline_list);
        mOfflineList.setLayoutManager(new LinearLayoutManager(getContext()));

        mOfflineList.setHasFixedSize(true);
        mOfflineList.setNestedScrollingEnabled(false);
        mCourse = (RelativeLayout) view.findViewById(R.id.course);
        mCourseNum = (TextView) view.findViewById(R.id.course_num);
        mCourseList = (RecyclerView) view.findViewById(R.id.course_list);
        mCourseList.setLayoutManager(new LinearLayoutManager(getContext()));

        mCourseList.setHasFixedSize(true);
        mCourseList.setNestedScrollingEnabled(false);
        mEssay = (RelativeLayout) view.findViewById(R.id.essay);
        mEssayNum = (TextView) view.findViewById(R.id.essay_num);
        mEssayList = (RecyclerView) view.findViewById(R.id.essay_list);
        mEssayList.setLayoutManager(new LinearLayoutManager(getContext()));

        mEssayList.setHasFixedSize(true);
        mEssayList.setNestedScrollingEnabled(false);
        mCourseLookAll = (TextView) view.findViewById(R.id.course_look_all);
        mEssayLookAll = (TextView) view.findViewById(R.id.essay_look_all);
        mDataHandler.sendEmptyMessageAtTime(0,100);
        initListener();
        return view;
    }

    private void initListener() {
        mCourseLookAll.setOnClickListener(this);
        mEssayLookAll.setOnClickListener(this);
        mCheckMore.setOnClickListener(this);
    }

    private int count = 0;
    private Handler mDataHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(mNewSearchResultEntity!=null){
                        setDataForUI(mNewSearchResultEntity);
                    }else{
                        if(count<5){
                            count++;
                            mDataHandler.sendEmptyMessageAtTime(0,1500);
                        }else{
                            Toast.makeText(MyApplication.mApplication, "请求超时", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };

    private void setDataForUI(NewSearchResultEntity entity) {
        if(entity.getEventList()!=null&&entity.getEventList().size()>0){
            mOfflineEvent.setVisibility(View.VISIBLE);
            mOfflineNum.setText("线下课（"+entity.getEventCount()+"节）");
            mSearchEventAdapter = new SearchEventAdapter(getContext(), entity.getEventList(),mKey);
            mOfflineList.setAdapter(mSearchEventAdapter);
            mSearchEventAdapter.setOnSearchItemClickListener(this);
        }else{
            mOfflineEvent.setVisibility(View.GONE);
        }

        if(entity.getCourseList()!=null&&entity.getCourseList().size()>0){
            mCourse.setVisibility(View.VISIBLE);
            mCourseNum.setText("课程（"+entity.getCourseCount()+"节）");
            if(entity.getCourseList().size()>=3){
                mCourseLookAll.setVisibility(View.VISIBLE);
            }
            mSearchCourseAdapter = new SearchCourseAdapter(getContext(), entity.getCourseList(),mKey);
            mCourseList.setAdapter(mSearchCourseAdapter);
            mSearchCourseAdapter.setOnSearchItemClickListener(this);
        }else{
            mCourse.setVisibility(View.GONE);
        }

        if(entity.getNewsList()!=null&&entity.getNewsList().size()>0){
            mEssay.setVisibility(View.VISIBLE);
            mEssayNum.setText("文章（"+entity.getNewsCount()+"篇）");
            if(entity.getNewsList().size()>=3){
                mEssayLookAll.setVisibility(View.VISIBLE);
            }
            mSearchEssayAdapter = new SearchEssayAdapter(getContext(), entity.getNewsList(),mKey);
            mEssayList.setAdapter(mSearchEssayAdapter);
            mSearchEssayAdapter.setOnSearchItemClickListener(this);
        }else{
            mEssay.setVisibility(View.GONE);
        }
    }

    public void setResultData(final NewSearchResultEntity entity,String key) {
        mKey = key;
        if(mNewSearchResultEntity==null){
            mNewSearchResultEntity = entity;
        }else{
            setDataForUI(entity);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDataHandler.removeMessages(0);
        mDataHandler = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.course_look_all:
                if(mOnLookAllClickListener!=null){
                    mOnLookAllClickListener.onLookAllClick(1);
                }
                break;
            case R.id.essay_look_all:
                if(mOnLookAllClickListener!=null){
                    mOnLookAllClickListener.onLookAllClick(2);
                }
                break;
            case R.id.check_more:
                Intent intent = new Intent(getContext(), TableActivity.class);
                startActivity(intent);
                break;
        }
    }

    private OnLookAllClickListener mOnLookAllClickListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnLookAllClickListener){
            mOnLookAllClickListener = (OnLookAllClickListener) activity;
        }
    }

    @Override
    public void onSearchItemClick(String id, String sel_type, String type) {
        new NewIntentUtils().IntentActivity(sel_type,type,id,getContext());
    }

    public interface OnLookAllClickListener{
        void onLookAllClick(int pos);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
