package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.OnJoinStudyGroupClickListener;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.OnShareClickListener;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.view.NewJoinedNumProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoJoinChallengeFragment extends Fragment implements View.OnClickListener {
    private FrameLayout mIntroduce;
    private Introduce_2_0_Fragment mIntroduceFragment;
    private String mTitle;
    private int mNum;
    private SGDetail2_0Entity.InfoBeanX.ScholarshipBean mScholarship;
    private SGDetail2_0Entity.InfoBeanX.TeacherBean mTeacher;
    private TextView mSgTitle;
    private TextView mBonus;
    private TextView mBonusThreshold;
    private NewJoinedNumProgress mJoinedNum;
    private LinearLayout mBonusDes;
    private FrameLayout mStage1;
    private TextView mStageContent1;
    private FrameLayout mStage2;
    private TextView mStageContent2;
    private FrameLayout mStage3;
    private TextView mStageContent3;
    private FrameLayout[] mStageFrags;
    private TextView[] mStageTvs;
    private TextView mJoinGroup;
    private String mVid;
    private TextView mInvite;
    private TextView mShare;


    public NoJoinChallengeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_join_challenge, container, false);
        mIntroduce = (FrameLayout) view.findViewById(R.id.introduce);
        mSgTitle = (TextView) view.findViewById(R.id.sg_title);
        mBonus = (TextView) view.findViewById(R.id.bonus);
        mBonusThreshold = (TextView) view.findViewById(R.id.bonus_threshold);
        mJoinedNum = (NewJoinedNumProgress) view.findViewById(R.id.joined_num);
        mJoinGroup = (TextView) view.findViewById(R.id.join_group);
        mStage1 = (FrameLayout) view.findViewById(R.id.stage_1);
        mStageContent1 = (TextView) view.findViewById(R.id.stage_content_1);
        mStage2 = (FrameLayout) view.findViewById(R.id.stage_2);
        mStageContent2 = (TextView) view.findViewById(R.id.stage_content_2);
        mStage3 = (FrameLayout) view.findViewById(R.id.stage_3);
        mStageContent3 = (TextView) view.findViewById(R.id.stage_content_3);
        mStageFrags = new FrameLayout[]{mStage1,mStage2,mStage3};
        mStageTvs = new TextView[]{mStageContent1,mStageContent2,mStageContent3};
        mInvite = (TextView) view.findViewById(R.id.invite);
        mShare = (TextView) view.findViewById(R.id.share);
        Bundle arguments = getArguments();
        if (arguments !=null) {
            mTitle = arguments.getString("title");
            mSgTitle.setText(mTitle);

            if (arguments.getString("num")!=null) {
                try {
                    mNum = Integer.parseInt(arguments.getString("num"));
                }catch (Exception e){
                    mNum = 0;
                }
            }else{
                mNum = 0;
            }
            mVid = arguments.getString("vid");
            mScholarship = (SGDetail2_0Entity.InfoBeanX.ScholarshipBean) arguments.getSerializable("scholarship");
            mTeacher = (SGDetail2_0Entity.InfoBeanX.TeacherBean) arguments.getSerializable("teacher");
            if (mScholarship!=null) {
                mBonus.setText("奖金："+mScholarship.getBonus_rang()+"元");

                List<String> nums = mScholarship.getNum();
                mJoinedNum.setCurrentNum(mNum);
                if (nums!=null&&nums.size()>0) {
                    mJoinedNum.setMax(Integer.parseInt(nums.get(nums.size()-1)));
                }
                if (nums!=null&&nums.size()>1) {
                    List<Integer> mThreshold = new ArrayList<>();
                    for (int i = 0; i < nums.size() - 1; i++) {
                        mThreshold.add(Integer.parseInt(nums.get(i)));
                    }
                    mJoinedNum.setLimitNum(mThreshold);
                }
                if (mScholarship.getBonus()!=null) {
                    String[] bonus = mScholarship.getBonus().split("#");
                    if(bonus!=null&&bonus.length>0){
                        for (int i = 0; i < bonus.length; i++) {
                            if(i<mStageFrags.length){
                                mStageFrags[i].setVisibility(View.VISIBLE);
                                mStageTvs[i].setText(bonus[i]);
                            }
                        }
                    }
                }
            }


        }
        if(mIntroduceFragment==null){
            mIntroduceFragment = new Introduce_2_0_Fragment();
        }

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if(!mIntroduceFragment.isAdded()){
            Bundle teacher = new Bundle();
            teacher.putSerializable("teacher",mTeacher);
            teacher.putString("type","1");
            teacher.putString("vid",mVid);
            mIntroduceFragment.setArguments(teacher);
            fragmentTransaction.add(R.id.introduce,mIntroduceFragment);
            fragmentTransaction.commit();
        }else{
            if (!mIntroduceFragment.isVisible()) {
                fragmentTransaction.show(mIntroduceFragment);
                fragmentTransaction.commit();
            }
        }
        mJoinGroup.setOnClickListener(this);
        mInvite.setOnClickListener(this);
        mShare.setOnClickListener(this);
        return view;
    }

    private OnJoinStudyGroupClickListener mOnJoinStudyGroupClickListener;
    private OnShareClickListener mOnShareClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnJoinStudyGroupClickListener){
            mOnJoinStudyGroupClickListener = (OnJoinStudyGroupClickListener) activity;
        }

        if (activity instanceof OnShareClickListener) {
            mOnShareClickListener = (OnShareClickListener) activity;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join_group:
                if (mOnJoinStudyGroupClickListener!=null) {
                    mOnJoinStudyGroupClickListener.onJoinSGClick();
                }
                break;
            case R.id.invite:
            case R.id.share:
                if (mOnShareClickListener!=null) {
                    mOnShareClickListener.onShareClick();
                }
                break;
        }

    }

}
