package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DelHistoryEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SearchHotHistoryEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.NewSearchPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.NewHistoryAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.search_result.SearchAllFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.search_result.SearchCourseFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.search_result.SearchEssayFragment;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.newproject.view.SearchHotLabelView;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NewHomeSearchActivity extends FragmentActivity implements NewHistoryAdapter.OnHistoryClickListener
        , View.OnClickListener, SearchHotLabelView.OnSearchLabelClickListener, TextView.OnEditorActionListener,SearchAllFragment.OnLookAllClickListener, TextWatcher {
    private SearchHotLabelView mHotLabels;
    private NewSearchPresenter mNewSearchPresenter;
    private String mUser_id;
    private LinearLayout mHotHistory;
    private RelativeLayout mSearchHistory;
    private ImageView mClearHistory;
    private RecyclerView mHistoryList;
    private RelativeLayout mHotInfo;
    private NewHistoryAdapter mNewHistoryAdapter;
    private List<String> mHot;
    private List<SearchHotHistoryEntity.HistoryBean> mHistory;
    private TextView mAll;
    private TextView mCourse;
    private TextView mEssay;
    private FrameLayout mLine;
    private int mPhoneW;
    private TextView[] mTitleTvs;
    private EditText mNewSearchEdit;
    private RelativeLayout mResult;
    private RelativeLayout mNoData;
    private ImageView mDelKey;

    private String mKey = "";
    private ViewPager mResultVp;
    private RelativeLayout mSearchResult;


    private SearchAllFragment mSearchAllFragment = new SearchAllFragment();
    private SearchCourseFragment mSearchCourseFragment = new SearchCourseFragment();
    private SearchEssayFragment mSearchEssayFragment = new SearchEssayFragment();
    private List<Fragment> mFragments = new ArrayList<>();
    private String mRecommendKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_search);

        mRecommendKey = getIntent().getStringExtra("re_key");
        mKey = mRecommendKey;
        mSearchResult = (RelativeLayout) findViewById(R.id.search_result);
        mHotLabels = (SearchHotLabelView) findViewById(R.id.hot_labels);
        mHotHistory = (LinearLayout) findViewById(R.id.hot_history);
        mSearchHistory = (RelativeLayout) findViewById(R.id.search_history);
        mClearHistory = (ImageView) findViewById(R.id.clear_history);
        mHistoryList = (RecyclerView) findViewById(R.id.history_list);
        mDelKey = (ImageView) findViewById(R.id.del_key);

        mHistoryList.setHasFixedSize(true);
        mHistoryList.setNestedScrollingEnabled(false);
        mHotInfo = (RelativeLayout) findViewById(R.id.hot_info);
        mAll = (TextView) findViewById(R.id.all);
        mCourse = (TextView) findViewById(R.id.course);
        mEssay = (TextView) findViewById(R.id.essay);
        mLine = (FrameLayout) findViewById(R.id.line);
        mResult = (RelativeLayout) findViewById(R.id.result);
        mNoData = (RelativeLayout) findViewById(R.id.no_data);
        mNewSearchEdit = (EditText) findViewById(R.id.new_search_edit);
        mResultVp = (ViewPager) findViewById(R.id.result_vp);


        mNewSearchEdit.setHint(mRecommendKey);

        mTitleTvs = new TextView[]{mAll,mCourse,mEssay};

        mHistoryList.setLayoutManager(new LinearLayoutManager(this));
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mPhoneW = ScreenUtils.getScreenWidth(this);

        ViewGroup.LayoutParams layoutParams = mLine.getLayoutParams();
        layoutParams.width = mPhoneW/3;
        mLine.setLayoutParams(layoutParams);
        initFragment();
        initListener();
        getHistoryAndHot();
    }

    private void initFragment() {
        mFragments.add(mSearchAllFragment);
        mFragments.add(mSearchCourseFragment);
        mFragments.add(mSearchEssayFragment);
        mResultVp.setAdapter(new BoutiqueVPAdapter(getSupportFragmentManager(),mFragments));
        mResultVp.setOffscreenPageLimit(3);
    }

    private void initListener() {
        mClearHistory.setOnClickListener(this);
        mHotLabels.setOnSearchLabelClickListener(this);
        mAll.setOnClickListener(this);
        mCourse.setOnClickListener(this);
        mEssay.setOnClickListener(this);
        mNewSearchEdit.setOnEditorActionListener(this);
        mNewSearchEdit.addTextChangedListener(this);
        mDelKey.setOnClickListener(this);
        mResultVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectInfoAni(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        findViewById(R.id.new_search_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getHistoryAndHot() {
        mNewSearchPresenter = new NewSearchPresenter();
        mNewSearchPresenter.getHotAndHistory(mUser_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                SearchHotHistoryEntity entity = (SearchHotHistoryEntity) value[0];
                mHot = entity.getHot();
                if (mHot != null && mHot.size() > 0) {
                    mHotLabels.setLabels(mHot);
                } else {
                    mHotInfo.setVisibility(View.GONE);
                }
                mHistory = entity.getHistory();
                if (mHistory != null && mHistory.size() > 0) {
                    mNewHistoryAdapter = new NewHistoryAdapter(NewHomeSearchActivity.this, mHistory);
                    mHistoryList.setAdapter(mNewHistoryAdapter);
                    mNewHistoryAdapter.setOnHistoryClickListener(NewHomeSearchActivity.this);
                } else {
                    mSearchHistory.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR_SEARCH_HOT", error);
            }
        });
    }

    @Override
    public void onSearchHistoryClick(String content) {
        mKey = content;
        search();
    }

    @Override
    public void onDelHistory(String content, final int pos) {
        Toast.makeText(this, "删除" + content, Toast.LENGTH_SHORT).show();
        mHistory.remove(pos);
        if (mNewHistoryAdapter != null) {
            mNewHistoryAdapter.notifyDataSetChanged();
        }
        if (mHistory.size() == 0) {
            mSearchHistory.setVisibility(View.GONE);
        }
        mNewSearchPresenter.delSimpleHistory(mUser_id, content, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                DelHistoryEntity entity = (DelHistoryEntity) value[0];
                if (entity.getStatus() == 1) {
                    Log.e("DH_ERROR_DEL", entity.getMsg());
                } else {
                    Log.e("DH_ERROR_DEL", "删除" + entity.getStatus());
                }

            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR_DEL", error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_history:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Dialog dialog;
                builder.setMessage("是否清空历史记录")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mNewSearchPresenter.delSimpleHistory(mUser_id,"", new OnResultListener() {
                                    @Override
                                    public void onSuccess(Object... value) {
                                        mHistory.clear();
                                        if (mNewHistoryAdapter != null) {
                                            mNewHistoryAdapter.notifyDataSetChanged();
                                        }
                                        mSearchHistory.setVisibility(View.GONE);
                                        Toast.makeText(MyApplication.mApplication, "成功清除", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFault(String error) {
                                        Log.e("DH_ERROR_DEL", error);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否", null);
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.all:
                mResultVp.setCurrentItem(0);
                break;
            case R.id.course:
                mResultVp.setCurrentItem(1);
                break;
            case R.id.essay:
                mResultVp.setCurrentItem(2);
                break;
            case R.id.del_key:
                mNewSearchEdit.setText("");
                mKey = "";
                break;
        }
    }

    @Override
    public void onLabelClick(int index, String data) {
        mKey = data;
        search();
    }


    private int startX = 0;
    private void selectInfoAni(final int item) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mPhoneW / 3 * item, 0, 0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTitleTvs[item].setTextColor(getResources().getColor(R.color.t_select));
                for (int i = 0; i < mTitleTvs.length; i++) {
                    if (i != item) {
                        mTitleTvs[i].setTextColor(getResources().getColor(R.color.t_unselect));
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mLine.startAnimation(translateAnimation);
        startX = mPhoneW / 3 * item;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_SEARCH==i||KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
            if(mNewSearchEdit!=null&&mNewSearchEdit.getText()!=null&&mNewSearchEdit.getText().toString()!=null
                    &&!"".equals(mNewSearchEdit.getText().toString().trim())&&mKey!=null){
                if(!mKey.equals(mNewSearchEdit.getText().toString().trim())){
                    mKey = mNewSearchEdit.getText().toString().trim();
                    search();
                }else{
                    Toast.makeText(MyApplication.mApplication,"搜索成功",Toast.LENGTH_SHORT).show();
                }
            }else{
                mKey = mRecommendKey;
                search();
//                Toast.makeText(MyApplication.mApplication, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return false;
    }

    private int mPage = 0;
    private void search(){
        LoadingUtils.getInstance().showNetLoading(this);
        KeyBoardUtils.hideKeyboard(this);
        mSearchHistory.setVisibility(View.GONE);
        mNewSearchEdit.setText(mKey);
        mNewSearchEdit.setSelection(mKey.length());
        mNewSearchPresenter.getNewSearchResult(mKey, mUser_id, mPage+"", 3+"", "", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_SEARCH_KEY",mKey);
                NewSearchResultEntity entity = (NewSearchResultEntity) value[0];
                if("1".equals(entity.getStatus())){
                    if(entity.getCourseList()!=null&&entity.getCourseList().size()!=0||
                            entity.getEventList()!=null&&entity.getEventList().size()!=0||
                            entity.getNewsList()!=null&&entity.getNewsList().size()!=0){
                        mNoData.setVisibility(View.GONE);
                        mResult.setVisibility(View.VISIBLE);
                        mHotInfo.setVisibility(View.GONE);
                        mSearchAllFragment.setResultData(entity,mKey);
                        mSearchCourseFragment.setResultData(true,mKey);
                        mSearchEssayFragment.setResultData(true,mKey);

                    }else{
                        mNoData.setVisibility(View.VISIBLE);
                        mResult.setVisibility(View.GONE);
                        mHotInfo.setVisibility(View.VISIBLE);
                    }
                }else{
                    mNoData.setVisibility(View.VISIBLE);
                    mResult.setVisibility(View.GONE);
                    mHotInfo.setVisibility(View.VISIBLE);
                }

                Toast.makeText(MyApplication.mApplication, entity.getMsg(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_ERROR_SEARCH",error);
                mNoData.setVisibility(View.VISIBLE);
                mResult.setVisibility(View.GONE);
                mHotInfo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onLookAllClick(int pos) {
        if(mResultVp!=null){
            mResultVp.setCurrentItem(pos);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s!=null&&StringUtils.isNotEmpty(s.toString())){
            mDelKey.setVisibility(View.VISIBLE);
        }else{
            mDelKey.setVisibility(View.GONE);
            mKey = mRecommendKey;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
