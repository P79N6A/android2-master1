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
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SearchEssayAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.NewIntentUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchEssayFragment extends Fragment implements OnLoadMoreListener {

    private String mUser_id;
    private String mKey;
    private int mPage;
    private NewSearchPresenter mNewSearchPresenter = new NewSearchPresenter();

    private SwipeToLoadLayout mSwipeEssay;
    private TextView mEssayCount;
    private RecyclerView mEssayList;
    private List<NewSearchResultEntity.NewsListBean> mEssayListBeans = new ArrayList<>();
    private SearchEssayAdapter mSearchEssayAdapter;
    private EmptyRecycleAdapter mEmptyRecycleAdapter;
    private boolean mIsNewSearch;

    public SearchEssayFragment() {
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    setData(mKey);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_essay, container, false);
        mSwipeEssay = (SwipeToLoadLayout) view.findViewById(R.id.swipe_essay);
        mEssayCount = (TextView) view.findViewById(R.id.essay_count);
        mEssayList = (RecyclerView) view.findViewById(R.id.essay_list);
        mEssayList.setLayoutManager(new LinearLayoutManager(getContext()));
//        mSearchEssayAdapter = new SearchEssayAdapter(getContext(), mEssayListBeans);
//        mEssayList.setAdapter(mSearchEssayAdapter);
        mEssayList.setHasFixedSize(true);
        mEssayList.setNestedScrollingEnabled(false);
        initListener();
        return view;
    }

    private void initListener() {
        mSwipeEssay.setOnLoadMoreListener(this);
    }

    public void setResultData(final boolean isNewSearch, String key) {
        mIsNewSearch = isNewSearch;
        setData(key);
    }

    private void setData(final String key) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mEssayCount==null){
                    mHandler.sendEmptyMessageDelayed(0,200);
                    return;
                }
                if(mUser_id==null)
                    mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
                if(mIsNewSearch){
                    mKey = key;
                    mPage = 0;
                }else{
                    mPage++;
                }

                mNewSearchPresenter.getNewSearchResult(mKey, mUser_id, mPage+"", 10+"", "news", new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {

                        NewSearchResultEntity entity = (NewSearchResultEntity) value[0];
                        if(mIsNewSearch){
                            if(!"0".equals(entity.getCourseCount())){
                                mEssayCount.setVisibility(View.VISIBLE);
                                mEssayCount.setText("共找到相关文章"+entity.getNewsCount()+"篇");
                            }else{
                                mEssayCount.setVisibility(View.GONE);
                            }

                            mEssayListBeans.clear();
                        }
                        Log.e("DH_SEARCH_KEY","essay size:"+entity.getNewsList().size());
                        if(entity.getNewsList().size()>0||mIsNewSearch){

                            mEssayListBeans.addAll(entity.getNewsList());
                            if(mEmptyRecycleAdapter!=null){
                                mSearchEssayAdapter.setKey(mKey);
                                mEmptyRecycleAdapter.notifyDataSetChanged();
                            }else{

                                mSearchEssayAdapter = new SearchEssayAdapter(getContext(), mEssayListBeans,mKey);
                                mEmptyRecycleAdapter = new EmptyRecycleAdapter(mSearchEssayAdapter, R.layout.layout_search_empty);
                                mEssayList.setAdapter(mEmptyRecycleAdapter);
                                mSearchEssayAdapter.setOnSearchItemClickListener(new OnSearchItemClickListener() {
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
        },200);

    }

    private Handler mLoadMoreHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mSwipeEssay.setLoadingMore(false);
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
        mHandler.removeCallbacksAndMessages(null);
    }
}
