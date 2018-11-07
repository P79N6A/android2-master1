package com.wh.wang.scroopclassproject.newproject.ui.fragment.search_result;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.NewSearchPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SearchCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.NewIntentUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCourseFragment extends Fragment implements OnLoadMoreListener {
    private TextView mCourseCount;
    private RecyclerView mCourseList;
    private int mPage = 0;
    private String mKey = "";
    private NewSearchPresenter mNewSearchPresenter = new NewSearchPresenter();
    private String mUser_id;
    private List<NewSearchResultEntity.CourseListBean> mCourseListBeans = new ArrayList<>();
    private SearchCourseAdapter mSearchCourseAdapter;
    private SwipeToLoadLayout mSwipeCourse;
    private EmptyRecycleAdapter mEmptyRecycleAdapter;

    public SearchCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_course, container, false);
        mCourseCount = (TextView) view.findViewById(R.id.course_count);
        mCourseList = (RecyclerView) view.findViewById(R.id.course_list);
        mSwipeCourse = (SwipeToLoadLayout) view.findViewById(R.id.swipe_course);

        mCourseList.setLayoutManager(new LinearLayoutManager(getContext()));
//        mSearchCourseAdapter = new SearchCourseAdapter(getContext(), mCourseListBeans,mKey);
//        mEmptyRecycleAdapter = new EmptyRecycleAdapter(mSearchCourseAdapter,R.layout.layout_search_empty);
//        mCourseList.setAdapter(mEmptyRecycleAdapter);

        mCourseList.setHasFixedSize(true);
        mCourseList.setNestedScrollingEnabled(false);
        initListener();
        return view;
    }

    private void initListener() {
        mSwipeCourse.setOnLoadMoreListener(this);
    }


    public void setResultData(final boolean isNewSearch, final String key) {
        mLoadMoreHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mUser_id==null)
                    mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
                if(isNewSearch){
                    mKey = key;
                    mPage = 0;
                }else{
                    mPage++;
                }

                mNewSearchPresenter.getNewSearchResult(mKey, mUser_id, mPage+"", 10+"", "course", new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        NewSearchResultEntity entity = (NewSearchResultEntity) value[0];
                        if(isNewSearch){
                            if(!"0".equals(entity.getCourseCount())){
                                mCourseCount.setText("共找到相关课程"+entity.getCourseCount()+"节");
                            }else{
                                mCourseCount.setVisibility(View.GONE);
                            }

                            mCourseListBeans.clear();
                        }
                        Log.e("DH_SEARCH_KEY","course size:"+entity.getCourseList().size());
                        if(entity.getCourseList().size()>0||isNewSearch){
                            mCourseListBeans.addAll(entity.getCourseList());
                            if(mEmptyRecycleAdapter!=null){
                                mSearchCourseAdapter.setKey(mKey);
                                mEmptyRecycleAdapter.notifyDataSetChanged();
                            }else{
                                mSearchCourseAdapter = new SearchCourseAdapter(getContext(), mCourseListBeans,mKey);
                                mEmptyRecycleAdapter = new EmptyRecycleAdapter(mSearchCourseAdapter,R.layout.layout_search_empty);
                                mCourseList.setAdapter(mEmptyRecycleAdapter);
                                mSearchCourseAdapter.setOnSearchItemClickListener(new OnSearchItemClickListener() {
                                    @Override
                                    public void onSearchItemClick(String id, String sel_type, String type) {
                                        new NewIntentUtils().IntentActivity(sel_type,type,id,getContext());
                                    }
                                });
                            }
                        }else{
                            Toast.makeText(MyApplication.mApplication, "没有更多了", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFault(String error) {
                        Log.e("DH_ERROR_SEARCH",error);
                    }
                });
            }
        },300);

    }

    private Handler mLoadMoreHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mSwipeCourse.setLoadingMore(false);
                    setResultData(false,null);
                    break;
            }
        }
    };

    @Override
    public void onLoadMore() {
        mLoadMoreHandler.sendEmptyMessageAtTime(0,1500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoadMoreHandler.removeCallbacksAndMessages(null);
    }
}
