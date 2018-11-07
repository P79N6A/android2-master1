package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0.SGRankAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRankEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonRankFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    private RoundedImageView mPerRankAvatar;
    private TextView mRankNum;
    private RecyclerView mRankingList;
    private View mRankCutLine;
    private int mPage = 0; //分页
//    private String mType = ""; // 2 总榜  null 个人
    private String mPid;
    private String mVid;
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mUserId;
    private SwipeToLoadLayout mSwipeLayout;
    private List<SGRankEntity.InfoBean.OtherBean> mRankDataList = new ArrayList<>();
    private SGRankAdapter mSGRankAdapter;
    private RoundedImageView mTop2Head;
    private TextView mRankTop2Name;
    private RoundedImageView mTop1Head;
    private TextView mRankTop1Name;
    private RoundedImageView mTop3Head;
    private TextView mRankTop3Name;
    private TextView mRankName;


    private TextView[] mTopNames;
    private RoundedImageView[] mTopHeads;
    public PersonRankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle arguments = getArguments();
        if(arguments!=null){
//            mType = "1".equals(arguments.getString("type","1"))?"":"2";
            mPid = arguments.getString("pid");
            mVid = arguments.getString("vid");
        }
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        mSwipeLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipe_layout);
        mRankTop3Name = (TextView) view.findViewById(R.id.rank_top_3_name);
        mRankTop1Name = (TextView) view.findViewById(R.id.rank_top_1_name);
        mRankTop2Name = (TextView) view.findViewById(R.id.rank_top_2_name);
        mPerRankAvatar = (RoundedImageView) view.findViewById(R.id.per_rank_avatar);
        mRankNum = (TextView) view.findViewById(R.id.rank_num);
        mRankingList = (RecyclerView) view.findViewById(R.id.ranking_list);
        mRankingList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRankCutLine = (View) view.findViewById(R.id.rank_cut_line);
        mRankCutLine.setVisibility(View.GONE);
        mRankName = (TextView) view.findViewById(R.id.rank_name);
        mRankingList.setHasFixedSize(true);
        mRankingList.setNestedScrollingEnabled(false);
        mTop2Head = (RoundedImageView) view.findViewById(R.id.top2_head);
        mTop1Head = (RoundedImageView) view.findViewById(R.id.top1_head);
        mTop3Head = (RoundedImageView) view.findViewById(R.id.top3_head);

        mTopNames = new TextView[]{mRankTop1Name,mRankTop2Name,mRankTop3Name};
        mTopHeads = new RoundedImageView[]{mTop1Head,mTop2Head,mTop3Head};
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadMoreListener(this);
    }

    private void initData() {
        ViewGroup.LayoutParams avatarLayoutParams = mPerRankAvatar.getLayoutParams();
        avatarLayoutParams.width = DisplayUtil.dip2px(MyApplication.mApplication, 42);
        avatarLayoutParams.height = DisplayUtil.dip2px(MyApplication.mApplication, 42);
        mPerRankAvatar.setLayoutParams(avatarLayoutParams);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        accessNet();
    }

    private void accessNet() {
        mStudyGroupPresenter.getStudyGroupRank(mUserId,mPid,mVid,"1",mPage+"", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                SGRankEntity sgRankEntity = (SGRankEntity) value[0];
                if ("200".equals(sgRankEntity.getCode())) {

                    if(sgRankEntity.getInfo().getMyself()!=null){
                        SGRankEntity.InfoBean.MyselfBean myselfBean = sgRankEntity.getInfo().getMyself();
                        Glide.with(MyApplication.mApplication).load(myselfBean.getUser_avator()).centerCrop().into(mPerRankAvatar);
                        mRankName.setText(myselfBean.getUser_name()+"位列排行榜");
                        setRankText("第"+myselfBean.getMyranking()+"名");
                    }
                    if(mPage==0){
                        mRankDataList.clear();
                    }
                    mRankDataList.addAll(sgRankEntity.getInfo().getOther());
                    if(mSGRankAdapter!=null){
                        mSGRankAdapter.notifyDataSetChanged();
                    }else{
                        mSGRankAdapter = new SGRankAdapter(getContext(),mRankDataList);
                        mRankingList.setAdapter(mSGRankAdapter);
                    }


                    if(mRankDataList.size()>0){
                        int count = mRankDataList.size()>3?3:mRankDataList.size();
                        for (int i = 0; i < count; i++) {
                            mTopNames[i].setText(mRankDataList.get(i).getUser_name());
                            if(StringUtils.isNotEmpty(mRankDataList.get(i).getUser_avator()))
                                Glide.with(MyApplication.mApplication).load(mRankDataList.get(i).getUser_avator()).centerCrop().into(mTopHeads[i]);
                        }
                    }

                } else {
                    Toast.makeText(MyApplication.mApplication, sgRankEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR", "My Study Group:" + error);
            }
        });
    }


    private void setRankText(String s) {
        Spannable sp = new SpannableString(s);
        sp.setSpan(new AbsoluteSizeSpan(20, true), 1, s.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mRankNum.setText(sp);
    }


    private Handler mSwipeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mSwipeLayout.setRefreshing(false);
                    break;
                case 1:
                    mSwipeLayout.setLoadingMore(false);
                    break;
            }
        }
    };
    @Override
    public void onRefresh() {
        mPage = 0;
        accessNet();
        mSwipeHandler.sendEmptyMessageAtTime(0,1500);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        accessNet();
        mSwipeHandler.sendEmptyMessageAtTime(1,1500);
    }
}
