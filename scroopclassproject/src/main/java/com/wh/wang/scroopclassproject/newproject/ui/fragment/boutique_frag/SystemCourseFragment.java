package com.wh.wang.scroopclassproject.newproject.ui.fragment.boutique_frag;


import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MainCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SystemRecycleAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.LinearSpacesItemDecoration;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemCourseFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mSystemList;
    private RelativeLayout mReconnectionLayout;
    private TextView mReconnection;
    private MainCoursePresenter mMainCoursePresenter = new MainCoursePresenter();
    private ImageView mReconnectionGif;

    public SystemCourseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_course, container, false);
        mSystemList = (RecyclerView) view.findViewById(R.id.system_list);
        mReconnectionGif = (ImageView) view.findViewById(R.id.reconnection_gif);
        mReconnectionLayout = (RelativeLayout) view.findViewById(R.id.reconnection_layout);
        mReconnection = (TextView) view.findViewById(R.id.reconnection);

        Glide.with(MyApplication.mApplication).load(R.drawable.nosignal).asGif().into(mReconnectionGif);
        initRecycle();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mReconnection.setOnClickListener(this);
    }

    private void initData() {
        getNetData();

    }

    private void initRecycle() {
        mSystemList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        mSystemList.addItemDecoration(new LinearSpacesItemDecoration(DisplayUtil.dip2px(MyApplication.mApplication,10),2));
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
                mSystemList.setVisibility(View.VISIBLE);
                mSystemList.setAdapter(new SystemRecycleAdapter(getActivity(),entity));
            }

            @Override
            public void onFault(String error) {
                mReconnectionLayout.setVisibility(View.VISIBLE);
                mSystemList.setVisibility(View.GONE);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
