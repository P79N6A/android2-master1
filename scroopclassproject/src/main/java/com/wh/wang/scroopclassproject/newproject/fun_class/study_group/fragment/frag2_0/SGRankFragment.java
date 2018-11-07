package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0.NewSGRankAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.NewSGRankEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SGRankFragment extends Fragment implements View.OnClickListener, OnResultListener {
    private RecyclerView mChallengeList;
    private RecyclerView mOutList;
    private NewSGRankAdapter mSuccessRankAdapter;
    private NewSGRankAdapter mFailRankAdapter;
    private EmptyRecycleAdapter mSuccessEmptyAdapter;
    private EmptyRecycleAdapter mFailEmptyAdapter;

    private TextView mRankInfo;
    private TextView mChallengingTv;
    private TextView mChallengingNum;
    private TextView mChallengeAll;
    private TextView mOutTv;
    private TextView mOutNum;
    private TextView mOutAll;
    private String mType;


    private List<NewSGRankEntity.InfoBean.RankBean> mSuccessList = new ArrayList<>();
    private List<NewSGRankEntity.InfoBean.RankBean> mFailList = new ArrayList<>();

    public static final int SUCCESS = 1; //成功
    public static final int FAIL = 2;  // 失败
    public static final int INIT = 3;  // 初始化
    private int mStatus = INIT;  //

    private int mSuccessPage = 0;
    private int mFailPage = 0;
    private int mPage = 0;

    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mPid;
    private String mVid;
    private String mUser_id;

    public SGRankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mType = getArguments().getString("type");
            mPid = getArguments().getString("pid");
            mVid = getArguments().getString("vid");
            mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "user_id", "");
        }
        View view = inflater.inflate(R.layout.fragment_sgrank, container, false);

        mRankInfo = (TextView) view.findViewById(R.id.rank_info);
        mChallengingTv = (TextView) view.findViewById(R.id.challenging_tv);
        mChallengingNum = (TextView) view.findViewById(R.id.challenging_num);
        mChallengeList = (RecyclerView) view.findViewById(R.id.challenge_list);
        mChallengeAll = (TextView) view.findViewById(R.id.challenge_all);
        mOutTv = (TextView) view.findViewById(R.id.out_tv);
        mOutNum = (TextView) view.findViewById(R.id.out_num);
        mOutList = (RecyclerView) view.findViewById(R.id.out_list);
        mOutAll = (TextView) view.findViewById(R.id.out_all);
        mChallengeList = (RecyclerView) view.findViewById(R.id.challenge_list);
        mChallengeList.setLayoutManager(new LinearLayoutManager(getContext()));
        mOutList = (RecyclerView) view.findViewById(R.id.out_list);
        mOutList.setLayoutManager(new LinearLayoutManager(getContext()));

        mChallengeList.setHasFixedSize(true);
        mChallengeList.setNestedScrollingEnabled(false);

        mOutList.setHasFixedSize(true);
        mOutList.setNestedScrollingEnabled(false);

        if ("1".equals(mType)) {
            mChallengingTv.setText("今日已打卡");
            mOutTv.setText("今日未打卡");
        }else{
            mChallengingTv.setText("挑战中");
            mOutTv.setText("已出局");
        }

        initListener();
        mSuccessRankAdapter = new NewSGRankAdapter(getContext(), mSuccessList, mType,"success");
        mSuccessEmptyAdapter = new EmptyRecycleAdapter(mSuccessRankAdapter, R.layout.layout_empty);
        mChallengeList.setAdapter(mSuccessEmptyAdapter);


        mFailRankAdapter = new NewSGRankAdapter(getContext(), mFailList, mType,"out");
        mFailEmptyAdapter = new EmptyRecycleAdapter(mFailRankAdapter, R.layout.layout_empty);
        mOutList.setAdapter(mFailEmptyAdapter);

        accessNet();
        return view;
    }

    private void accessNet() {
        mStudyGroupPresenter.getNewRankList(mUser_id, mPid, mVid, mType, String.valueOf(mStatus), String.valueOf(mPage), this);
    }

    private void initListener() {
        mChallengeAll.setOnClickListener(this);
        mOutAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.challenge_all:
                mStatus = SUCCESS;
                mSuccessPage++;
                mPage = mSuccessPage;
                break;
            case R.id.out_all:
                mStatus = FAIL;
                mFailPage++;
                mPage = mFailPage;
                break;
        }
        accessNet();
    }

    @Override
    public void onSuccess(Object... value) {
        NewSGRankEntity newSGRankEntity = (NewSGRankEntity) value[0];
        if (newSGRankEntity.getInfo() != null) {
            NewSGRankEntity.InfoBean info = newSGRankEntity.getInfo();
            if (mStatus == INIT) {
                if (info.getSuccess() != null && info.getSuccess().size() > 0) {

                    mSuccessList.addAll(info.getSuccess());
                    if (mSuccessList.size()<5) {
                        mChallengeAll.setVisibility(View.INVISIBLE);
                    }else{
                        mChallengeAll.setVisibility(View.VISIBLE);
                    }
                } else {
                    mChallengeAll.setVisibility(View.INVISIBLE);
                }

                if (info.getFail() != null && info.getFail().size() > 0) {
                    mFailList.addAll(info.getFail());
                    if (mFailList.size()<5) {
                        mOutAll.setVisibility(View.INVISIBLE);
                    }else{
                        mOutAll.setVisibility(View.VISIBLE);
                    }
                } else {
                    mOutAll.setVisibility(View.INVISIBLE);
                }
                mFailEmptyAdapter.notifyDataSetChanged();
                mSuccessEmptyAdapter.notifyDataSetChanged();

                if (info.getFinish_num()!=null) {
                    mChallengingNum.setText("("+info.getFinish_num().getFinish_num()+")");
                    mOutNum.setText("("+info.getFinish_num().getUnfinish_num()+")");
                    if ("1".equals(mType)) {
                        mRankInfo.setText("今日已打卡"+info.getFinish_num().getFinish_num()+"人，未打卡"+info.getFinish_num().getUnfinish_num()+"人");
                    }else{
                        mRankInfo.setText(info.getFinish_num().getFinish_num()+"人累计打卡中，"+info.getFinish_num().getUnfinish_num()+"人已出局");
                    }
                }


            }else if(mStatus == SUCCESS){
                if (info.getSuccess() != null && info.getSuccess().size() > 0) {
                    mChallengeAll.setVisibility(View.VISIBLE);
                    mSuccessList.addAll(info.getSuccess());
                    mSuccessEmptyAdapter.notifyDataSetChanged();
                }else{
                    mChallengeAll.setVisibility(View.INVISIBLE);
                    Toast.makeText(MyApplication.mApplication, "已经全部加载", Toast.LENGTH_SHORT).show();
                }
            }else if(mStatus == FAIL){
                if (info.getFail() != null && info.getFail().size() > 0) {
                    mFailList.addAll(info.getFail());
                    mOutAll.setVisibility(View.VISIBLE);
                    mFailEmptyAdapter.notifyDataSetChanged();
                } else {
                    mOutAll.setVisibility(View.INVISIBLE);
                    Toast.makeText(MyApplication.mApplication, "已经全部加载", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            mChallengeAll.setVisibility(View.INVISIBLE);
            mOutAll.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFault(String error) {
        Log.e("DH_RANK_ERROR", error);
        mChallengeAll.setVisibility(View.INVISIBLE);
        mOutAll.setVisibility(View.INVISIBLE);
    }
}
