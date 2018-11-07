package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewMoreEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.NewMorePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.NewMoreAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.NewIntentUtils;

import java.util.ArrayList;
import java.util.List;

public class NewMoreActivity extends Activity implements OnRefreshListener, OnLoadMoreListener {
    private SwipeToLoadLayout mBrowseTitleSwipe;
    private RecyclerView mSwipeTarget;
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;

    private String mTitle;
    private String mMoreVE;
    private String mMoreHS;
    private String mId;
    private int mPage = 0;
    private NewMoreAdapter mNewMoreAdapter;
    private List<NewMoreEntity.InfoBean.ListBean> mMoreBeanList = new ArrayList<>();
    private boolean isSlidingToLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_more);

        mTitle = getIntent().getStringExtra("title");
        mMoreVE = getIntent().getStringExtra("ve");
        mMoreHS = getIntent().getStringExtra("hs");
        mId = getIntent().getStringExtra("id");


        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mBrowseTitleSwipe = (SwipeToLoadLayout) findViewById(R.id.browse_title_swipe);
        mSwipeTarget = (RecyclerView) findViewById(R.id.swipe_target);

        mTitlebarbackssName.setText(mTitle);

//        if ("2".equals(mMoreVE) || "1".equals(mMoreHS)) {
            mSwipeTarget.setLayoutManager(new LinearLayoutManager(this));
//        } else {
//            mSwipeTarget.setLayoutManager(new GridLayoutManager(this, 2));
//            mSwipeTarget.addItemDecoration(new GridSpacingItemDecoration(2, DisplayUtil.dip2px(MyApplication.mApplication, 9), true));
//        }
        mNewMoreAdapter = new NewMoreAdapter(NewMoreActivity.this, mMoreBeanList, mMoreVE, mMoreHS);
        mSwipeTarget.setAdapter(mNewMoreAdapter);
        initListener();
        getNetData();
    }

    private void initListener() {
        mBrowseTitleSwipe.setOnRefreshListener(this);
        mBrowseTitleSwipe.setOnLoadMoreListener(this);
//        mSwipeTarget.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                // 当不滚动时
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    //获取最后一个完全显示的ItemPosition
//                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
//                    int totalItemCount = manager.getItemCount();
//                    // 判断是否滚动到底部，并且是向下滚动
//                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//                        mPage++;
//                        getNetData();
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//                if (dy > 0) {
//                    isSlidingToLast = true;
//                } else {
//                    isSlidingToLast = false;
//                }
//            }
//        });

        mNewMoreAdapter.setOnMoreClickListener(new NewMoreAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(String id, String sel_type, String type) {
                new NewIntentUtils().IntentActivity(sel_type, type, id, NewMoreActivity.this);
            }
        });
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean isNoMore = false;

    private void getNetData() {
        new NewMorePresenter().getNewMoreInfo(mId, mPage + "", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                NewMoreEntity moreEntity = (NewMoreEntity) value[0];
                if (moreEntity.getCode() == 200) {
                    if (moreEntity.getInfo().getList().size() > 0) {
                        if (mPage == 0) {
                            if (mMoreBeanList != null && mMoreBeanList.size() > 0) {
                                mMoreBeanList.clear();
                            }
                        }
                        mMoreBeanList.addAll(moreEntity.getInfo().getList());
                        if (mNewMoreAdapter != null) {
                            mNewMoreAdapter.notifyDataSetChanged();
                        } else {
                            mNewMoreAdapter = new NewMoreAdapter(NewMoreActivity.this, mMoreBeanList, mMoreVE, mMoreHS);
                            mSwipeTarget.setAdapter(mNewMoreAdapter);
                        }
                    } else {
                        if (!isNoMore) {
                            isNoMore = true;
                            Toast.makeText(MyApplication.mApplication, "没有更多了", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(MyApplication.mApplication, moreEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR", error);
            }
        });
    }

    private Handler mMoreHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mPage = 0;
                    getNetData();
                    mBrowseTitleSwipe.setRefreshing(false);
                    break;
                case 1:
                    mPage++;
                    getNetData();
                    mBrowseTitleSwipe.setLoadingMore(false);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        mMoreHandler.sendEmptyMessageAtTime(0, 1500);
    }

    @Override
    public void onLoadMore() {
        mMoreHandler.sendEmptyMessageAtTime(1, 1500);
    }
}
