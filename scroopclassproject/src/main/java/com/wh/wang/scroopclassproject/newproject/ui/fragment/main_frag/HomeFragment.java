package com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.DownManagerActivity;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.activity.TableActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.NewHomePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewHomeSearchActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.HomeAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.NewIntentUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import static com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils.getStatusBarHeight;

public class HomeFragment extends Fragment implements View.OnClickListener, HomeAdapter.OnHomeItemClickListener, OnRefreshListener {

    private NewHomePresenter mNewHomePresenter;
    private RecyclerView mHomeList;
    private TextView mSearchContent;
    private ImageView mDownload;
    private HomeAdapter mHomeAdapter;
    private ImageView mCalendar;
    private LinearLayout mSearchContentL;
    private SwipeToLoadLayout mFragHomeSwipe;
    private String mUserId;
    private String mSearchKey;


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeList = (RecyclerView) view.findViewById(R.id.swipe_target);
        mSearchContent = (TextView) view.findViewById(R.id.search_content);
        mDownload = (ImageView) view.findViewById(R.id.download);
        mCalendar = (ImageView) view.findViewById(R.id.calendar);
        mSearchContentL = (LinearLayout) view.findViewById(R.id.search_content_l);
        mFragHomeSwipe = (SwipeToLoadLayout) view.findViewById(R.id.frag_home_swipe);

        mHomeList.setLayoutManager(new LinearLayoutManager(getContext()));
        view.findViewById(R.id.content).setPadding(0, getStatusBarHeight(getContext()), 0, 0);
        initListener();

        mNewHomePresenter = new NewHomePresenter();
        getHomeData();
        return view;

    }



    private void getHomeData() {
        mNewHomePresenter.getNewHomeInfo(new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                NewHomeEntity homeEntity = (NewHomeEntity) value[0];
                if("200".equals(homeEntity.getCode())){
                    mSearchKey = homeEntity.getInfo().getS_keyword();//默认搜索关键字
                    if(!StringUtils.isNotEmpty(mSearchKey)){
                        mSearchKey = getString(R.string.search_more_hink);
                    }
                    if(mOnSearchKeyClickListener!=null){
                        mOnSearchKeyClickListener.onSearchKeyClick(mSearchKey);
                    }
                    mSearchContent.setText(mSearchKey);
                    if(homeEntity.getInfo().getEvent()!=null){
                        for (int i = 0; i < homeEntity.getInfo().getEvent().size(); i++) {
                            if(!"0".equals(homeEntity.getInfo().getEvent().get(i).getSum())
                                    &&StringUtils.isNotEmpty(homeEntity.getInfo().getEvent().get(i).getSum())){
                                homeEntity.getInfo().getEvent().get(i).setChecked(true);
                                break;
                            }
                        }
                        if (homeEntity.getInfo().getYuedu()!=null&&homeEntity.getInfo().getYuedu().size()>0) {
                            homeEntity.getInfo().getYuedu().get(0).setCheck(true);
                        }
                        mHomeAdapter = new HomeAdapter(getContext(), homeEntity);
                        mHomeList.setAdapter(mHomeAdapter);
                        mHomeAdapter.setOnHomeItemClickListener(HomeFragment.this);
                    }

                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR","NewHome: "+error);
            }
        });
    }

    private void initListener() {

        mSearchContentL.setOnClickListener(this);
        mDownload.setOnClickListener(this);
        mCalendar.setOnClickListener(this);
        mFragHomeSwipe.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.search_content_l:
                intent = new Intent(getContext(), NewHomeSearchActivity.class);
                intent.putExtra("re_key",mSearchKey);
                startActivity(intent);
                break;
            case R.id.download:
                if(StringUtils.isNotEmpty(mUserId))
                {
                    intent = new Intent(getContext(), DownManagerActivity.class);
                }
                else
                {
                    intent = new Intent(getContext(), LoginNewActivity.class);
                }
                startActivity(intent);

                break;
            case R.id.calendar:
                intent = new Intent(getContext(), TableActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onHomeItemClick(String id, String sel_type, String type) {
        new NewIntentUtils().IntentActivity(sel_type,type,id,getContext());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnSearchKeyClickListener){
            mOnSearchKeyClickListener = (OnSearchKeyClickListener) activity;
        }
    }

    private OnSearchKeyClickListener mOnSearchKeyClickListener;

    @Override
    public void onRefresh() {
        mRefreshHandler.sendEmptyMessageDelayed(0,1500);
    }

    public interface OnSearchKeyClickListener{
        void onSearchKeyClick(String key);
    }

    private Handler mRefreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    getHomeData();
                    mFragHomeSwipe.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRefreshHandler.removeCallbacksAndMessages(null);
    }
}
