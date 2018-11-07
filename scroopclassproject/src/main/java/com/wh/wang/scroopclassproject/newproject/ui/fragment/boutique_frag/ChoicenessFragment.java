package com.wh.wang.scroopclassproject.newproject.ui.fragment.boutique_frag;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MainCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.ChoiceRecycleAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.LinearSpacesItemDecoration;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChoicenessFragment extends Fragment implements OnRefreshListener, View.OnClickListener {
    private RecyclerView mSwipeTarget;
    private MainCoursePresenter mMainCoursePresenter = new MainCoursePresenter();
    private ImageView mImageviewArrow;
    private SwipeToLoadLayout mFragFreeSwipe;
    private RelativeLayout mReconnectionLayout;
    private TextView mReconnection;
    private ImageView mReconnectionGif;


    public ChoicenessFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choiceness, container, false);
        initView(view);
        initRecycle();
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        mSwipeTarget = (RecyclerView) view.findViewById(R.id.swipe_target);
        mImageviewArrow = (ImageView) view.findViewById(R.id.imageview_arrow);
        mFragFreeSwipe = (SwipeToLoadLayout) view.findViewById(R.id.frag_free_swipe);

        mReconnectionGif = (ImageView) view.findViewById(R.id.reconnection_gif);
        mReconnectionLayout = (RelativeLayout) view.findViewById(R.id.reconnection_layout);
        mReconnection = (TextView) view.findViewById(R.id.reconnection);


        Glide.with(MyApplication.mApplication).load(R.drawable.nosignal).asGif().into(mReconnectionGif);
    }

    private void initListener() {
        mFragFreeSwipe.setOnRefreshListener(this);
        mReconnection.setOnClickListener(this);
    }

    private void initData() {
        getNetData();
    }

    private void initRecycle() {
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        mSwipeTarget.addItemDecoration(new LinearSpacesItemDecoration(DisplayUtil.dip2px(MyApplication.mApplication,10),1));
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mFragFreeSwipe.setRefreshing(false);
                    break;
            }
        }
    };
    @Override
    public void onRefresh() {
        getNetData();
        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reconnection(ReconnectionEntity entity){
        getNetData();
    }

    private void getNetData() {

        mMainCoursePresenter.getMainCourse(StringUtils.getAppInfo(getActivity()),new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                MainCourseEntity entity = (MainCourseEntity) value[0];
                mReconnectionLayout.setVisibility(View.GONE);
                mSwipeTarget.setVisibility(View.VISIBLE);
                mSwipeTarget.setAdapter(new ChoiceRecycleAdapter(getActivity(),entity));
            }

            @Override
            public void onFault(String error) {
                mReconnectionLayout.setVisibility(View.VISIBLE);
                mSwipeTarget.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reconnection:
                Toast.makeText(getActivity(), "重新连接中..", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new ReconnectionEntity(true));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
