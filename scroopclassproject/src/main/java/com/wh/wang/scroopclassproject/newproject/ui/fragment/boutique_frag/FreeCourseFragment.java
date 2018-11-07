package com.wh.wang.scroopclassproject.newproject.ui.fragment.boutique_frag;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.wh.wang.scroopclassproject.newproject.mvp.model.FreeCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MoreOrAllCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.FreeCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.LinearSpacesItemDecoration;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class FreeCourseFragment extends Fragment implements OnRefreshListener, FreeCourseAdapter.OnFreeCourseClickListener, View.OnClickListener {
    private SwipeToLoadLayout mFragFreeSwipe;
    private RecyclerView mSwipeTarget;
    private ImageView mImageviewArrow;
    private RelativeLayout mReconnectionLayout;
    private ImageView mReconnectionGif;
    private TextView mReconnection;


    private List<FreeCourseEntity.InfoBean.CourseListBean> mFreeCourseList = new ArrayList<>();
    private MoreOrAllCoursePresenter mMoreOrAllCoursePresenter = new MoreOrAllCoursePresenter();
    private int page = 0;
    private int mPhoneW;
    private FreeCourseAdapter mFreeCourseAdapter;

    private boolean mNoHasMore;//控制只显示一次 没有更多 提示
    private Handler mFreeCourseHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mFragFreeSwipe.setRefreshing(false);
                    break;
                case 1:
                    FreeCourseEntity entity = (FreeCourseEntity) msg.obj;
                    if(entity.getInfo()!=null&&entity.getInfo().getCourseList()!=null&&entity.getInfo().getCourseList().size()>0){
                        mReconnectionLayout.setVisibility(View.GONE);
                        if(mFragFreeSwipe.getVisibility()==View.GONE){
                            mFragFreeSwipe.setVisibility(View.VISIBLE);
                        }
                        if(page==0){
                            mFreeCourseList.clear();
                        }
                        mFreeCourseList.addAll(entity.getInfo().getCourseList());

                        if(mFreeCourseAdapter!=null){
                            mFreeCourseAdapter.notifyDataSetChanged();
                        }else{
                            mFreeCourseAdapter = new FreeCourseAdapter(mFreeCourseList,getContext(),mPhoneW);
                            mSwipeTarget.setAdapter(mFreeCourseAdapter);
                        }
                        page++;
                    }else{
                        if(!mNoHasMore){
                            Toast.makeText(getContext(), getResources().getString(R.string.has_no_more), Toast.LENGTH_SHORT).show();
                            mNoHasMore = true;
                        }
                    }
                    break;
            }
        }
    };
    private boolean isSlidingToLast;

//    @Override
//    public View initView(LayoutInflater inflater) {
//        return view;
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_free_course,null,false);
        mImageviewArrow = (ImageView) view.findViewById(R.id.imageview_arrow);
        mFragFreeSwipe = (SwipeToLoadLayout) view.findViewById(R.id.frag_free_swipe);
        mSwipeTarget = (RecyclerView) view.findViewById(R.id.swipe_target);
        mReconnectionLayout = (RelativeLayout) view.findViewById(R.id.reconnection_layout);
        mReconnectionGif = (ImageView) view.findViewById(R.id.reconnection_gif);
        mReconnection = (TextView) view.findViewById(R.id.reconnection);

        mImageviewArrow.setImageResource(R.drawable.loading_anilist);
        AnimationDrawable animationDrawable = (AnimationDrawable) mImageviewArrow.getDrawable();
        animationDrawable.start();

        Glide.with(MyApplication.mApplication).load(R.drawable.nosignal).asGif().into(mReconnectionGif);
        initSize();
        initRecycle();
        initListener();
        getNetData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initRecycle() {
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        mSwipeTarget.addItemDecoration(new LinearSpacesItemDecoration(DisplayUtil.dip2px(MyApplication.mApplication,10),0));
        mFreeCourseAdapter = new FreeCourseAdapter(mFreeCourseList,getContext(),mPhoneW);
        mSwipeTarget.setAdapter(mFreeCourseAdapter);
    }

    private void initListener() {
        mReconnection.setOnClickListener(this);
        mFragFreeSwipe.setOnRefreshListener(this);
        if(mFreeCourseAdapter!=null){
            mFreeCourseAdapter.setOnFreeCourseClickListener(this);
        }
        mSwipeTarget.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部，并且是向下滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        getNetData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 0;
        mNoHasMore = false;
        getNetData();
        mFreeCourseHandler.sendEmptyMessageDelayed(0,1000);
    }

    private boolean mIsEndRequest = true;
    private void getNetData() {
        if(!mIsEndRequest){
            return;
        }
        mIsEndRequest = false;
        mMoreOrAllCoursePresenter.getFreeCourse(page+"", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                mIsEndRequest = true;
                FreeCourseEntity entity = (FreeCourseEntity) value[0];
                Message msg = mFreeCourseHandler.obtainMessage();
                msg.what=1;
                msg.obj = entity;
                mFreeCourseHandler.sendMessage(msg);
            }

            @Override
            public void onFault(String error) {
                mIsEndRequest = true;
                mFragFreeSwipe.setVisibility(View.GONE);
                mReconnectionLayout.setVisibility(View.VISIBLE);
                Log.e("DH_FREE_COURSE",error);
            }
        });
    }


    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneW = d.widthPixels;
    }

    /**
     * 免费课点击事件
     * @param pos
     * @param bean
     */
    @Override
    public void onFreeCourseClick(int pos, FreeCourseEntity.InfoBean.CourseListBean bean) {
        if(bean!=null){
            GotoNextActUtils.getInstance().nextActivity(getActivity(),bean.getId(),bean.getType());
//            Intent intent;
//            if("4".equals(bean.getType())){
//                intent = new Intent(mContext, SumUpActivity.class);
//            }else{
//                intent = new Intent(mContext, VideoInfosActivity.class);
//            }
//            intent.putExtra("type",bean.getType());
//            intent.putExtra("id",bean.getId());
//            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mNoHasMore = false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reconnection(ReconnectionEntity entity){
        getNetData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
