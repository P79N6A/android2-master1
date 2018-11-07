package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0.MyPunchCardActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0.StudyGroupInfo_2_0_Activity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0.MyStudyGroup_2_0_Adapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGHome2_0Entity;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SGHomeDataFragment extends Fragment implements View.OnClickListener, MyStudyGroup_2_0_Adapter.OnStudyGroupItemClickListener {

    private TextView mCheckMySg;
    private RecyclerView mMySgList;
    private String mUserId;
    private String mAvatar;
    private RoundedImageView mAvatarImg;
    private TextView mName;
    private TextView mDayNum;
    private TextView mShare;
    private String mNickName;
    private EmptyRecycleAdapter mEmptyRecycleAdapter;
    private MyStudyGroup_2_0_Adapter mMyStudyGroup_2_0_adapter;
    private String mPunch_card;

    public SGHomeDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sghome_data, container, false);
        mCheckMySg = (TextView) view.findViewById(R.id.check_my_sg);
        mAvatarImg = (RoundedImageView) view.findViewById(R.id.avatar);
        mName = (TextView) view.findViewById(R.id.name);
        mDayNum = (TextView) view.findViewById(R.id.day_num);
        mShare = (TextView) view.findViewById(R.id.share);

        mMySgList = (RecyclerView) view.findViewById(R.id.my_sg_list);
        mMySgList.setLayoutManager(new LinearLayoutManager(getContext()));
        mMySgList.setHasFixedSize(true);
        mMySgList.setNestedScrollingEnabled(false);

        if (getArguments()!=null) {
            Bundle bundle = getArguments();
            mPunch_card = bundle.getString("punch_card");
            List<SGHome2_0Entity.InfoBean.ListBean.MyJoinBean> my_list = (List<SGHome2_0Entity.InfoBean.ListBean.MyJoinBean>) bundle.getSerializable("my_list");
            setData(mPunch_card,my_list);
        }

        initListener();
        return view;
    }

    private void initListener() {
        mCheckMySg.setOnClickListener(this);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPunch_card==null||"0".equals(mPunch_card)) {
                    Toast.makeText(MyApplication.mApplication, "请完成学习后再分享", Toast.LENGTH_SHORT).show();
                    return;
                }
                showShareDig();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_my_sg:
                Intent intent;
                if(StringUtils.isEmpty(mUserId)){
                    intent = new Intent(getContext(),LoginNewActivity.class);
                }else{
                    intent = new Intent(getContext(),MyPunchCardActivity.class);
                }
                startActivity(intent);
                break;
        }
    }

    public void setData(String punch_card, List<SGHome2_0Entity.InfoBean.ListBean.MyJoinBean> myJoin) {
        if (punch_card!=null&&punch_card.length()>0) {
            mDayNum.setText(getDayNumText(punch_card));
        }

        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mAvatar = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "avatar", "");
        mNickName = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
        Glide.with(getContext()).load(mAvatar).centerCrop().into(mAvatarImg);
        mName.setText(mNickName);
        mMyStudyGroup_2_0_adapter = new MyStudyGroup_2_0_Adapter(getContext(), myJoin);
        mEmptyRecycleAdapter = new EmptyRecycleAdapter(mMyStudyGroup_2_0_adapter, R.layout.layout_empty_mysg);
        mMySgList.setAdapter(mEmptyRecycleAdapter);
        mMyStudyGroup_2_0_adapter.setOnStudyGroupItemClickListener(this);
    }

    private Spannable getDayNumText(String punch_card) {

        Spannable sp = new SpannableString("累计学习 "+punch_card+" 天");
        sp.setSpan(new AbsoluteSizeSpan(28,true),5,5+punch_card.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#86B93F")),5,5+punch_card.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return sp;
    }

    @Override
    public void onSGItemClick(String pid, String vid,String type) {
        Intent intent;
        if(StringUtils.isEmpty(mUserId)){
            intent = new Intent(getContext(),LoginNewActivity.class);
        }else{
            intent = new Intent(getContext(),StudyGroupInfo_2_0_Activity.class);
            intent.putExtra("pid",pid);
            intent.putExtra("vid",vid);
            intent.putExtra("is_bao","1");
            intent.putExtra("type",type);
        }
        startActivity(intent);
    }

    private Dialog mShareDialog;

    private void showShareDig() {
        //分享
        mShareDialog = new Dialog(getContext(), R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.share_dialog, null);
        //初始化控件
        RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
        RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
        RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
        share_rlayout_py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                ShareUtil.weiChat(Constants.wx_api, 7,
                        getContext(), "http://www.shaoziketang.com/wapshaozi/study_group_new_index.html",
                        "我已在勺子课堂累计学习"+mPunch_card+"天，爱学习的餐饮人都在这", "勺子课堂，你身边的餐饮专家");
            }
        });

        share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                ShareUtil.weiChat(Constants.wx_api, 8,
                        getContext(), "http://www.shaoziketang.com/wapshaozi/study_group_new_index.html",
                        "我已在勺子课堂累计学习"+mPunch_card+"天，爱学习的餐饮人都在这", "勺子课堂，你身边的餐饮专家");
            }
        });

        share_llayout_concel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
            }
        });

        //将布局设置给Dialog
        mShareDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mShareDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getActivity().getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mShareDialog.show();//显示对话框
    }
}
