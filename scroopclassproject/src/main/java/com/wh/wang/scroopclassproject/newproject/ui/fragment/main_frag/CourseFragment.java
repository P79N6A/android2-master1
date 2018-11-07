package com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag;


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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseLabelEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.LabelCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewHomeSearchActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CourseAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CourseLabelAdapter;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils.getStatusBarHeight;

public class CourseFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener {
//    private RelativeLayout mTitlebarNew;
    private RecyclerView mLabelList;
    private SwipeToLoadLayout mFragFreeSwipe;
    private ImageView mImageviewArrow;
    private RecyclerView mSwipeTarget;
    private CourseLabelAdapter mCourseLabelAdapter;
    private ImageView mImageview;
    private TextView mSearchContent;
//    private ImageView mCalendar;
//    private ImageView mDownload;
    private FrameLayout mSearchContentL;
    private int mCurrentPos = -1;
    private CourseAdapter mCourseAdapter;
    private List<CourseLabelEntity.InfoBean.AllTypeBean> mAllType;
    private List<CourseLabelEntity.InfoBean.CourseListBean> mCourseList = new ArrayList<>();
    private LabelCoursePresenter mLabelCoursePresenter = new LabelCoursePresenter();
    private int mPage = 0;
    private String mLabelID = "";
    private boolean isSlidingToLast = false;

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        initView(view);
        initRecycle();
        initListener();
        return view;
    }



    private void initListener() {
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
//                        selectLabel(mLabelID,false);
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
        mFragFreeSwipe.setOnRefreshListener(this);
        mFragFreeSwipe.setOnLoadMoreListener(this);
        mSearchContentL.setOnClickListener(this);
    }

    private void initRecycle() {

        mLabelList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        mSwipeTarget.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
    }

    private void initView(View view) {
        mSearchContent = (TextView) view.findViewById(R.id.search_content);
        mSearchContentL = (FrameLayout) view.findViewById(R.id.search_content_l);
        mLabelList = (RecyclerView) view.findViewById(R.id.label_list);
        mFragFreeSwipe = (SwipeToLoadLayout) view.findViewById(R.id.frag_free_swipe);
        mImageviewArrow = (ImageView) view.findViewById(R.id.imageview_arrow);
        mSwipeTarget = (RecyclerView) view.findViewById(R.id.swipe_target);
        mImageview = (ImageView) view.findViewById(R.id.imageview);
        mSearchContent.setText(mSearchKey);
        view.findViewById(R.id.content).setPadding(0, getStatusBarHeight(getContext()), 0, 0);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    private void selectLabel(final String id, final boolean isLabel) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLabelCoursePresenter.getLabelCourse(id, mPage + "", new OnResultListener() {

                    @Override
                    public void onSuccess(Object... value) {
                        CourseLabelEntity entity = (CourseLabelEntity) value[0];
                        if(isLabel){
                            mAllType=entity.getInfo().getAllType();
                            if(mAllType!=null&&mAllType.size()>0){
                                int pos = getLabelPos(id);
                                mCourseAdapter = null;
                                if (mCourseList!=null) mCourseList.clear();
                                mCurrentPos = pos;
                                mAllType.get(mCurrentPos).setCheck(true);
                                Log.e("DH_LABEL","mCurrentPos:"+mCurrentPos);
                                mCourseLabelAdapter = new CourseLabelAdapter(mAllType,getContext());
                                mLabelList.setAdapter(mCourseLabelAdapter);
                                mCourseLabelAdapter.setOnLabelClickListener(new CourseLabelAdapter.OnLabelClickListener() {
                                    @Override
                                    public void onLabelClick(int old_p, int new_p, CourseLabelEntity.InfoBean.AllTypeBean allTypeBean) {
                                        if(old_p!=new_p){
                                            Log.e("DH_LABEL",(mAllType==null)+"");
                                            mPage = 0;
                                            mAllType.get(old_p).setCheck(false);
                                            mAllType.get(new_p).setCheck(true);
                                            mCurrentPos = new_p;
                                            mLabelID = allTypeBean.getId();
                                            mCourseLabelAdapter.notifyItemChanged(old_p);
                                            mCourseLabelAdapter.notifyItemChanged(new_p);
                                            mCourseAdapter = null;
                                            mCourseList.clear();
                                            selectLabel(allTypeBean.getId(),false);
                                        }
                                    }
                                });
                            }
                        }

                        if(entity.getInfo().getCourseList().size()==0){
                            Toast.makeText(MyApplication.mApplication, "没有更多内容了", Toast.LENGTH_SHORT).show();
                        }else{
                            mCourseList.addAll(entity.getInfo().getCourseList());
                            if(mCourseAdapter==null){
                                if(mAllType==null){
                                    selectLabel(mLabelID,true);
                                }else{
                                    mCourseAdapter = new CourseAdapter(entity.getInfo(),mCourseList,getContext(),mAllType.get(mCurrentPos).getName());
                                    mSwipeTarget.setAdapter(mCourseAdapter);
                                    mCourseAdapter.setOnCourseClickListener(new CourseAdapter.OnCourseClickListener() {
                                        @Override
                                        public void onCourseClick(int pos, String id, String type) {
                                            GotoNextActUtils.getInstance().nextActivity(getContext(),id,type);
                                        }
                                    });
                                }

                            }else{
                                mCourseAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
            }
        },300);

    }

    private int getLabelPos(String id) {
        for (int i = 0; i < mAllType.size(); i++) {
            if(id.equals(mAllType.get(i).getId())){
                return i;
            }
        }
        return 0;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mFragFreeSwipe.setRefreshing(false);
                    break;
                case 1:
                    mFragFreeSwipe.setLoadingMore(false);
                    break;
            }
        }
    };
    @Override
    public void onRefresh() {
        mPage = 0;
        mCourseAdapter = null;
        mCourseList.clear();
        selectLabel(mLabelID,false);
        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        selectLabel(mLabelID,false);
        mHandler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.search_content_l:
                intent = new Intent(getContext(), NewHomeSearchActivity.class);
                intent.putExtra("re_key",mSearchKey);
                startActivity(intent);
//                startActivityForResult(intent, Constant.SEARCH_REQ);
                break;
//            case R.id.download:
//                intent = new Intent(getContext(), DownManagerActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.calendar:
//                intent = new Intent(getContext(), TableActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    public void setLabel(String labelID) {
        //定位标签
        mLabelID = labelID;
        mPage = 0;
        selectLabel(mLabelID,true);
    }

    private String mSearchKey = "";
    public void setSearchKey(String key) {
        mSearchKey = key;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler!=null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
