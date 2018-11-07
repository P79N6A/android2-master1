package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreOrAllCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MoreOrAllCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.LoadMoreCourseAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.GridSpacingItemDecoration;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;

import java.util.ArrayList;
import java.util.List;

public class MoreCourseActivity extends Activity implements OnRefreshListener, View.OnClickListener, LoadMoreCourseAdapter.OnMoreCourseClickListener {
    private RecyclerView mSwipeTarget;
    private SwipeToLoadLayout mBrowseTitleSwipe;
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private ImageView mImageviewArrow;
    private ImageView mNoData;
    private RelativeLayout mReconnectionLayout;
    private ImageView mReconnectionGif;
    private TextView mReconnection;

    //加载更多或者加载全部标志位 0：更多 1：全部
    private String flag = "0";
    private int jxid;
    private String mCourseTitle;
    private int page = 0;
    private List<MoreOrAllCourseEntity.InfoBean.CourseListBean> mCourseListBean = new ArrayList<>();

    private MoreOrAllCoursePresenter mMoreOrAllCoursePresenter = new MoreOrAllCoursePresenter();

    private boolean mNoHasMore;
    private Handler mMoreCourseHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mBrowseTitleSwipe.setRefreshing(false);
                    break;
                case 1:
                    MoreOrAllCourseEntity entity = (MoreOrAllCourseEntity) msg.obj;
                    if(entity.getInfo()!=null&&entity.getInfo().getCourseList()!=null&&entity.getInfo().getCourseList().size()>0){
                        if(page==0){
                            mCourseListBean.clear();
                        }
                        mNoData.setVisibility(View.GONE);
                        mCourseListBean.addAll(entity.getInfo().getCourseList());

                        if(loadMoreCourseAdapter==null){
                            loadMoreCourseAdapter = new LoadMoreCourseAdapter(mCourseListBean,MoreCourseActivity.this,mPhoneW);
                            mSwipeTarget.setAdapter(loadMoreCourseAdapter);
                        }else{
                            loadMoreCourseAdapter.notifyDataSetChanged();
                        }
                        page++;

                    }else{
                        if(page==0){
                            mNoData.setVisibility(View.VISIBLE);
                        }else{
                            mNoData.setVisibility(View.GONE);
                        }
                        if(!mNoHasMore){
                            Toast.makeText(MyApplication.mApplication, getResources().getString(R.string.has_no_more), Toast.LENGTH_SHORT).show();
                            mNoHasMore = true;
                        }
                    }
                    break;
            }
        }
    };
    private LoadMoreCourseAdapter loadMoreCourseAdapter;
    private int mPhoneW;
    private boolean isSlidingToLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_course);
        initView();
        initSize();
        initIntentData();
        initRecycle();
        initListener();
        getNetData();
    }

    private void initRecycle() {
        mSwipeTarget.setLayoutManager(new GridLayoutManager(MyApplication.mApplication,2));
        mSwipeTarget.addItemDecoration(new GridSpacingItemDecoration(2, DisplayUtil.dip2px(MyApplication.mApplication,10), false));
        loadMoreCourseAdapter = new LoadMoreCourseAdapter(mCourseListBean,this,mPhoneW);
        mSwipeTarget.setAdapter(loadMoreCourseAdapter);
    }

    private void initListener() {
        mBrowseTitleSwipe.setOnRefreshListener(this);
        mReconnection.setOnClickListener(this);
        mTitlebarbackssImageViewback.setOnClickListener(this);
        if(loadMoreCourseAdapter!=null){
            loadMoreCourseAdapter.setmOnMoreCourseClickListener(this);
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

    private void initView() {
        mBrowseTitleSwipe = (SwipeToLoadLayout) findViewById(R.id.browse_title_swipe);
        mSwipeTarget = (RecyclerView) findViewById(R.id.swipe_target);
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mImageviewArrow = (ImageView) findViewById(R.id.imageview_arrow);
        mNoData = (ImageView) findViewById(R.id.no_data);
        mReconnectionLayout = (RelativeLayout) findViewById(R.id.reconnection_layout);
        mReconnectionGif = (ImageView) findViewById(R.id.reconnection_gif);
        mReconnection = (TextView) findViewById(R.id.reconnection);

        mImageviewArrow.setImageResource(R.drawable.loading_anilist);
        AnimationDrawable animationDrawable = (AnimationDrawable) mImageviewArrow.getDrawable();
        animationDrawable.start();
        Glide.with(MyApplication.mApplication).load(R.drawable.nosignal).asGif().into(mReconnectionGif);
    }


    private void initIntentData() {
        flag = getIntent().getStringExtra("flag")==null?"0":getIntent().getStringExtra("flag");
        if("0".equals(flag)){
            jxid = getIntent().getIntExtra("jxid", 0);
            mCourseTitle = getIntent().getStringExtra("jxname");
        }else{
            mCourseTitle = "全部课程";
        }

        mTitlebarbackssName.setText(mCourseTitle);
    }

    private void getNetData() {
        if("0".equals(flag)){
            mMoreOrAllCoursePresenter.getMoreCourse(jxid+"", page+"", new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    mReconnectionLayout.setVisibility(View.GONE);
                    Log.e("DH_COURSE","More Success");
                    MoreOrAllCourseEntity entity = (MoreOrAllCourseEntity) value[0];
                    Message msg = mMoreCourseHandler.obtainMessage();
                    msg.what=1;
                    msg.obj = entity;
                    mMoreCourseHandler.sendMessage(msg);
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_COURSE",error);
                    mNoData.setVisibility(View.GONE);
                    mReconnectionLayout.setVisibility(View.VISIBLE);
                }
            });
        }else if("1".equals(flag)){
            mMoreOrAllCoursePresenter.getAllCourse(page+"", new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    Log.e("DH_COURSE","All Success");
                    mReconnectionLayout.setVisibility(View.GONE);
                    MoreOrAllCourseEntity entity = (MoreOrAllCourseEntity) value[0];
                    Message msg = mMoreCourseHandler.obtainMessage();
                    msg.what=1;
                    msg.obj = entity;
                    mMoreCourseHandler.sendMessage(msg);
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_COURSE",error);
                    mNoData.setVisibility(View.GONE);
                    mReconnectionLayout.setVisibility(View.VISIBLE);
                }
            });
        }
    }
    /**
     * 下拉刷新监听
     */
    @Override
    public void onRefresh() {
        mNoHasMore = false;
        page = 0;
        getNetData();
        mMoreCourseHandler.sendEmptyMessageDelayed(0,1000);
    }

    private void initSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mPhoneW = displayMetrics.widthPixels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
            case R.id.reconnection:
                Toast.makeText(this, "正在重新连接..", Toast.LENGTH_SHORT).show();
                mNoHasMore = false;
                page = 0;
                getNetData();
                break;
        }
    }

    /**
     * 课程点击监听
     * @param pos
     * @param bean
     */
    @Override
    public void onMoreCourseClick(int pos, MoreOrAllCourseEntity.InfoBean.CourseListBean bean) {
        GotoNextActUtils.getInstance().nextActivity(this,bean.getId(),bean.getType());
    }
}
