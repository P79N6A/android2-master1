package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.OnJoinStudyGroupClickListener;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.OnShareClickListener;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoJoinFreeFragment extends Fragment implements View.OnClickListener {


    private Introduce_2_0_Fragment mIntroduceFragment;
    private TextView mSgTitle;
    private LinearLayout mJoinHead;
    private TextView mJoinNum;
    private TextView mJoinGroup;
    private String mUserId;
    private String mTitle;
    private SGDetail2_0Entity.InfoBeanX.UserCountBean mUserCount;
    private SGDetail2_0Entity.InfoBeanX.TeacherBean mTeacher;
    private List<String> mAvators;
    private String mVid;
    private String mMaxnum;
    private TextView mShare;


    public NoJoinFreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if(bundle!=null){
//            mIsStart = bundle.getString("isStart","0");
//            mVid = bundle.getString("vid","0");
//            mPid = bundle.getString("pid","0");
//            mComplete = bundle.getString("complete","0");
//            mInfo = (SGDetailEntity.InfoBeanX) bundle.getSerializable("info");
            mTitle = bundle.getString("title");
            mVid = bundle.getString("vid");
            mMaxnum = bundle.getString("maxnum");
            mUserCount = (SGDetail2_0Entity.InfoBeanX.UserCountBean) bundle.getSerializable("userCount");
            mTeacher = (SGDetail2_0Entity.InfoBeanX.TeacherBean) bundle.getSerializable("teacher");
            mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        }
        View view = inflater.inflate(R.layout.fragment_no_join_free, container, false);
        mSgTitle = (TextView) view.findViewById(R.id.sg_title);
        mJoinHead = (LinearLayout) view.findViewById(R.id.join_head);
        mJoinNum = (TextView) view.findViewById(R.id.join_num);
        mJoinGroup = (TextView) view.findViewById(R.id.join_group);
        mShare = (TextView) view.findViewById(R.id.share);
        mJoinGroup.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mJoinNum.setText(mUserCount.getCount()+"/"+mMaxnum);
        if (mUserCount!=null) {
            mAvators = mUserCount.getAvator();
        }
        mSgTitle.setText(mTitle);
        initHead(5);

        if(mIntroduceFragment==null){
            mIntroduceFragment = new Introduce_2_0_Fragment();
        }
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if(!mIntroduceFragment.isAdded()){
            Bundle teacher = new Bundle();
            teacher.putSerializable("teacher",mTeacher);
            teacher.putString("type","0");
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
        return view;
    }


    private void initHead(int count) {
        for (int i = 0; i < count; i++) {
            RoundedImageView head = new RoundedImageView(getContext());
            head.setOval(true);

            head.setScaleType(ImageView.ScaleType.CENTER_CROP);
            head.setImageResource(R.drawable.rank_default);
            if (mAvators != null && mAvators.size() > 0) {
                if (mAvators.size() - 1 >= i) {
                    Glide.with(getContext()).load(mAvators.get(i)).centerCrop().into(head);
                }
            }
            mJoinHead.addView(head);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.width = DisplayUtil.dip2px(getContext(), 32);
            layoutParams.height = DisplayUtil.dip2px(getContext(), 32);
            if (i > 0) {
                layoutParams.setMargins(DisplayUtil.dip2px(MyApplication.mApplication, 10), 0, 0, 0);
            }
            head.setLayoutParams(layoutParams);

        }
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

            case R.id.share:
                if (mOnShareClickListener!=null) {
                    mOnShareClickListener.onShareClick();
                }
                break;
        }

    }
}
