package com.wh.wang.scroopclassproject.newproject.ui.fragment.essay_more_frag;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreEssayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MoreOrAllCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.EssayMoreAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.NewIntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EssayMoreFragment extends Fragment implements OnResultListener, OnLoadMoreListener, OnRefreshListener, EssayMoreAdapter.OnEssayMoreClickListener {
    private String mType;
    private int mPage = 0;
    private MoreOrAllCoursePresenter mMoreOrAllCoursePresenter = new MoreOrAllCoursePresenter();
    private SwipeToLoadLayout mSwipeLayout;
    private RecyclerView mSwipeTarget;

    private List<MoreEssayEntity.ListBean> mMoreEsayList = new ArrayList<>();
    private EssayMoreAdapter mEssayMoreAdapter;

    public EssayMoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments()!=null) {
            mType = getArguments().getString("type");
        }
        View view = inflater.inflate(R.layout.fragment_essay_more, container, false);
        mSwipeLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipe_layout);
        mSwipeTarget = (RecyclerView) view.findViewById(R.id.swipe_target);
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        accessNet();
        initListener();
        return view;
    }

    private void initListener() {
        mSwipeLayout.setOnLoadMoreListener(this);
        mSwipeLayout.setOnRefreshListener(this);
    }

    private void accessNet() {
        mMoreOrAllCoursePresenter.getMoreEssaies(mType,String.valueOf(mPage),this);
    }

    @Override
    public void onSuccess(Object... value) {
        LoadingUtils.getInstance().hideNetLoading();
        MoreEssayEntity moreEssayEntity = (MoreEssayEntity) value[0];
        if(mPage==0){
            mMoreEsayList.clear();
        }

        if(moreEssayEntity.getList()!=null&&moreEssayEntity.getList().size()>0){
            mMoreEsayList.addAll(moreEssayEntity.getList());
        }else{
            if(mPage!=0){
                Toast.makeText(MyApplication.mApplication, "没有更多了", Toast.LENGTH_SHORT).show();
            }
        }

        if(mEssayMoreAdapter==null){
            mEssayMoreAdapter = new EssayMoreAdapter(getContext(),mMoreEsayList);
            mEssayMoreAdapter.setOnMoreClickListener(this);
            mSwipeTarget.setAdapter(mEssayMoreAdapter);
        }else{
            mEssayMoreAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onFault(String error) {
        LoadingUtils.getInstance().hideNetLoading();
        Toast.makeText(MyApplication.mApplication, "请求异常", Toast.LENGTH_SHORT).show();
    }


    private Handler mSwipeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mSwipeLayout.setRefreshing(false);
                    accessNet();
                    break;
                case 1:
                    mSwipeLayout.setLoadingMore(false);
                    accessNet();
                    break;
            }
        }
    };

    @Override
    public void onLoadMore() {
        mPage++;
        mSwipeHandler.sendEmptyMessageAtTime(1,1500);
    }

    @Override
    public void onRefresh() {
        mPage = 0;
        mSwipeHandler.sendEmptyMessageAtTime(0,1500);
    }

    @Override
    public void onMoreClick(String id, String sel_type, String type) {
        new NewIntentUtils().IntentActivity(sel_type,type,id,getContext());
    }
}
