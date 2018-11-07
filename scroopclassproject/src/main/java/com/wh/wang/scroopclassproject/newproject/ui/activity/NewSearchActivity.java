package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchInitEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.SearchInitPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.SearchResultPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SearchHistoryAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.SearchResultAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.GlideRoundTransform;
import com.wh.wang.scroopclassproject.newproject.utils.GridSpacingItemDecoration;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class NewSearchActivity extends Activity implements View.OnClickListener, SearchHistoryAdapter.OnHistoryClickListener, SearchResultAdapter.OnResultClickListener, TextView.OnEditorActionListener {
    private RelativeLayout mNewSearchBanner;
    private RecyclerView mSearchResultList;
    private RelativeLayout mNewSearchHistory;
    private RelativeLayout mNewSearchHistoryNoData;
    private RecyclerView mNewSearchHistoryList;
    private RelativeLayout mSearchDefault;
    private LinearLayout mHotTags;
    private LayoutInflater mLayoutInflater;
    private LinearLayout mNewSearchLikeTopTwo;
    private TextView mNewSearchCancelOrSearch;
    private EditText mNewSearchEdit;
    private double rate = 9/(5.0);
    private ImageView mLikeTop1;
    private ImageView mLikeTop2;
    private TextView mLikeTop3;
    private TextView mLikeTop4;
    private TextView mLikeTop5;
    private View[] mViews;

    private SearchInitPresenter mSearchHistoryPresenter;
    //搜索相关
    private SearchResultPresenter mSearchResultPresenter;
    private int page = 0;
    private String keyWord = "";
    private List<NewSearchEntity.InfoBean.CourseListBean> mSearchCourseList = new ArrayList<>();
    private SearchResultAdapter searchResultAdapter;
    private List<String> mHistory;
    private String mUserId;
    private boolean isSlidingToLast;
    private boolean isNoMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_search);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        initView();
        initSize();
        initPresenter();
        initRecycle();
        initData();
        initListener();
        initOther();
    }

    private void initOther() {
    }

    private void initPresenter() {
        mSearchHistoryPresenter = new SearchInitPresenter();
        mSearchResultPresenter = new SearchResultPresenter();
    }

    private void initView() {
        mNewSearchBanner = (RelativeLayout) findViewById(R.id.new_search_banner);
        mNewSearchHistory = (RelativeLayout) findViewById(R.id.new_search_history);
        mNewSearchHistoryNoData = (RelativeLayout) findViewById(R.id.new_search_history_no_data);
        mNewSearchHistoryList = (RecyclerView) findViewById(R.id.new_search_history_list);
        mSearchResultList = (RecyclerView) findViewById(R.id.search_result_list);
        mHotTags = (LinearLayout) findViewById(R.id.hot_tags);
        mNewSearchLikeTopTwo = (LinearLayout) findViewById(R.id.new_search_like_top_two);
        mNewSearchCancelOrSearch = (TextView) findViewById(R.id.new_search_cancel_or_search);
        mNewSearchEdit = (EditText) findViewById(R.id.new_search_edit);
        mSearchDefault = (RelativeLayout) findViewById(R.id.search_default);
        mLikeTop1 = (ImageView) findViewById(R.id.like_top_1);
        mLikeTop2 = (ImageView) findViewById(R.id.like_top_2);
        mLikeTop3 = (TextView) findViewById(R.id.like_top_3);
        mLikeTop4 = (TextView) findViewById(R.id.like_top_4);
        mLikeTop5 = (TextView) findViewById(R.id.like_top_5);
        mViews = new View[]{mLikeTop1,mLikeTop2,mLikeTop3,mLikeTop4,mLikeTop5};
    }

    private void initRecycle() {
        mNewSearchHistoryList.setLayoutManager(new GridLayoutManager(MyApplication.mApplication,2));
        mNewSearchHistoryList.addItemDecoration(new GridSpacingItemDecoration(2, DisplayUtil.dip2px(MyApplication.mApplication,8), false));

        mSearchResultList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
    }

    private void initListener() {
        mNewSearchCancelOrSearch.setOnClickListener(this);
        mNewSearchEdit.setOnEditorActionListener(this);
        mSearchResultList.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        search(true);
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

    private void initData() {
        getHistoryData();
    }

    /**
     * 获取搜索历史和热门搜索
     */
    public void getHistoryData() {
        mSearchHistoryPresenter.getSearchInitData(mUserId, new OnResultListener() {
            private SearchHistoryAdapter searchHistoryAdapter;

            @Override
            public void onSuccess(Object... value) {
                NewSearchInitEntity entity = (NewSearchInitEntity) value[0];
                if(entity.getInfo()!=null){
                    Log.e("DH_HISTORY_SEARCH","size:"+entity.getInfo().getSearchSet().size()+"  "+mUserId);
                    if(entity.getInfo().getSearchSet()!=null&&entity.getInfo().getSearchSet().size()>0){
                        mHistory = entity.getInfo().getSearchSet();
                        searchHistoryAdapter = new SearchHistoryAdapter(NewSearchActivity.this.mHistory);
                        mNewSearchHistoryList.setAdapter(searchHistoryAdapter);
                        searchHistoryAdapter.setOnHistoryClickListener(NewSearchActivity.this);
                    }else{
                        mNewSearchBanner.setVisibility(View.GONE);
                    }
                    if(entity.getInfo().getCommCourse()!=null){
                        for (int i = 0; i < entity.getInfo().getCommCourse().size(); i++) {
                            final NewSearchInitEntity.InfoBean.CommCourseBean commCourseBean = entity.getInfo().getCommCourse().get(i);
                            if(i==0){
                                Glide.with(MyApplication.mApplication)
                                        .load(commCourseBean.getImg())
                                        .transform(new GlideRoundTransform(MyApplication.mApplication,DisplayUtil.dip2px(MyApplication.mApplication,4)))
                                        .into(mLikeTop1);

                            }else if(i==1){
                                Glide.with(MyApplication.mApplication)
                                        .load(commCourseBean.getImg())
                                        .transform(new GlideRoundTransform(MyApplication.mApplication,DisplayUtil.dip2px(MyApplication.mApplication,4)))
                                        .into(mLikeTop2);
                            }else if(i==2){
                                mLikeTop3.setText((i+1)+"."+commCourseBean.getTitle());
                            }else if(i==3){
                                mLikeTop4.setText((i+1)+"."+commCourseBean.getTitle());
                            }else if(i==4){
                                mLikeTop5.setText((i+1)+"."+commCourseBean.getTitle());
                            }
                            mViews[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    GotoNextActUtils.getInstance().nextActivity(NewSearchActivity.this,commCourseBean.getId(),commCourseBean.getType());
                                }
                            });

                        }
                    }
                    if(entity.getInfo().getLabel()!=null){
                        if(entity.getInfo().getLabel().size()>0){
                            setHotTags(entity.getInfo().getLabel());
                        }
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_HISTORY","error:"+error);
                mNewSearchBanner.setVisibility(View.GONE);
            }
        });
    }

    public void setHotTags(List<NewSearchInitEntity.InfoBean.LabelBean> hotTags) {
        for (int i = 0; i < hotTags.size(); i++) {
            final NewSearchInitEntity.InfoBean.LabelBean labelBean = hotTags.get(i);
            View view =  mLayoutInflater.inflate(R.layout.view_hot_search,null,false);
            TextView tag = (TextView) view.findViewById(R.id.hot_tag);
            tag.setText(labelBean.getName());
            final int I = i;
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    // 设置结果，并进行传送
                    intent.putExtra("pos", I);
                    intent.putExtra("id", labelBean.getId());
                    NewSearchActivity.this.setResult(Constant.SEARCH_RES, intent);
                    finish();
            }
            });
            mHotTags.addView(view);
        }
    }

    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        setViewWH(d.widthPixels);
    }

    private void setViewWH(int widthPixels) {
        double h = ((widthPixels-DisplayUtil.dip2px(MyApplication.mApplication,38))/2)/rate;
        ViewGroup.LayoutParams layoutParams = mNewSearchLikeTopTwo.getLayoutParams();
        layoutParams.height = (int) h;
        mNewSearchLikeTopTwo.setLayoutParams(layoutParams);
    }

    /**
     * TODO
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_search_cancel_or_search:
                finish();
                break;
        }
    }

    private void search(final boolean isMore) {
        if(isMore){
            page++;
        }else{
            mSearchCourseList.clear();
            page = 0;
        }
        mSearchResultPresenter.getSearchResult(mUserId, keyWord, page, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_SEARCH","success");
                NewSearchEntity entity = (NewSearchEntity) value[0];
                if(entity.getInfo()!=null&&entity.getInfo().getCourseList()!=null&&entity.getInfo().getCourseList().size()>0){
                    mSearchDefault.setVisibility(View.GONE);
                    mSearchResultList.setVisibility(View.VISIBLE);
                    mSearchCourseList.addAll(entity.getInfo().getCourseList());
                    if(searchResultAdapter==null){
                        searchResultAdapter = new SearchResultAdapter(mSearchCourseList,keyWord);
                        mSearchResultList.setAdapter(searchResultAdapter);
                        searchResultAdapter.setOnResultClickListener(NewSearchActivity.this);
                    }else{
                        searchResultAdapter.resetKeyWord(keyWord);
                        searchResultAdapter.notifyDataSetChanged();
                    }
                }else{
                    if(isMore){
                        if(!isNoMore){
                            Toast.makeText(MyApplication.mApplication, "没有更多了", Toast.LENGTH_SHORT).show();
                            isNoMore = true;
                        }
                    }else{
                        mNewSearchBanner.setVisibility(View.VISIBLE);
                        mSearchDefault.setVisibility(View.VISIBLE);
                        mSearchResultList.setVisibility(View.GONE);
                        mNewSearchHistory.setVisibility(View.GONE);
                        mNewSearchHistoryNoData.setVisibility(View.VISIBLE);
                        Toast.makeText(MyApplication.mApplication, "未找到相关课程", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_SEARCH",error);
            }
        });
    }

    /**
     * 历史记录点击监听
     * @param s
     */
    @Override
    public void historyListener(String s) {
        keyWord = s;
        isNoMore = false;
        mNewSearchEdit.setText(s);
        search(false);
    }

    /**
     * 搜索结果点击监听
     * @param pos 点击位置
     * @param bean 点击Item
     */
    @Override
    public void onResultClick(int pos, NewSearchEntity.InfoBean.CourseListBean bean) {
        GotoNextActUtils.getInstance().nextActivity(this,bean.getId(),bean.getType());
//        Intent intent;
//        if("4".equals(bean.getType())){
//            intent = new Intent(NewSearchActivity.this, SumUpActivity.class);
//        }else{
//            intent = new Intent(NewSearchActivity.this, VideoInfosActivity.class);
//        }
//        intent.putExtra("type",bean.getType());
//        intent.putExtra("id",bean.getId());
//        startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_SEARCH==i||KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
            if(mNewSearchEdit!=null&&mNewSearchEdit.getText()!=null&&mNewSearchEdit.getText().toString()!=null
                &&!"".equals(mNewSearchEdit.getText().toString().trim())){
                    if(!keyWord.equals(mNewSearchEdit.getText().toString().trim())){
                        keyWord = mNewSearchEdit.getText().toString().trim();
                        isNoMore = false;
                        search(false);
                    }
            }else{
                keyWord = "";
                Toast.makeText(MyApplication.mApplication, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
}
