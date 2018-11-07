package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0.StudyGroupRankActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0.SGPunchCardAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0.SGDetailRankAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetailEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.PunchSpacesItemDecoration;
import com.wh.wang.scroopclassproject.newproject.utils.SaveBitmapAsync;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private RecyclerView mPunchCardList;
    private TextView mQrHint;
//    private TextView mQrTv;
    private TextView mRankNum;
    private LinearLayout mRanking;
    private ImageView mRankIcon;


    private TextView mRecordTv;
    private TextView mClickedNum;

    private RecyclerView mRankingList;
    private String mIsStart;
    private String mPid;
    private String mVid;
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mUserId;
    private SGDetailEntity.InfoBeanX mInfo;
    private RoundedImageView mPerRankAvatar;
    private TextView mRankName;
    private TextView mCheckRank;
    private TextView mPunchCard;
    private String mComplete;
    private ImageView mQr;

    public JoinedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if(bundle!=null){
            mIsStart = bundle.getString("isStart","0");
            mVid = bundle.getString("vid","0");
            mPid = bundle.getString("pid","0");
            mComplete = bundle.getString("complete","0");
            mInfo = (SGDetailEntity.InfoBeanX) bundle.getSerializable("info");
            mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        }
        View view = inflater.inflate(R.layout.fragment_joined, container, false);
        mQrHint = (TextView) view.findViewById(R.id.qr_hint);
        mRankNum = (TextView) view.findViewById(R.id.rank_num);
        mQr = (ImageView) view.findViewById(R.id.qr);
//        mQrTv = (TextView) view.findViewById(R.id.qr_tv);
        mQr.setVisibility(View.VISIBLE);
//        mQrTv.setVisibility(View.VISIBLE);
        mRanking = (LinearLayout) view.findViewById(R.id.ranking);
        mRankIcon = (ImageView) view.findViewById(R.id.rank_icon);
        mCheckRank = (TextView) view.findViewById(R.id.check_rank);
        mCheckRank.setVisibility(View.VISIBLE);
        mPerRankAvatar = (RoundedImageView) view.findViewById(R.id.per_rank_avatar);
        mRankName = (TextView) view.findViewById(R.id.rank_name);
        mPunchCard = (TextView) view.findViewById(R.id.punch_card);


        mRecordTv = (TextView) view.findViewById(R.id.record_tv);
        mClickedNum = (TextView) view.findViewById(R.id.clicked_num);

        mRankingList = (RecyclerView) view.findViewById(R.id.ranking_list);
        mRankingList.setLayoutManager(new LinearLayoutManager(getContext()));

        mPunchCardList = (RecyclerView) view.findViewById(R.id.punch_card_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPunchCardList.addItemDecoration(new PunchSpacesItemDecoration(15));
        mPunchCardList.setLayoutManager(linearLayoutManager);


        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mCheckRank.setOnClickListener(this);
        mPunchCard.setOnClickListener(this);
        mQr.setOnLongClickListener(this);
    }

    private void initData() {
        initNetDatas();

        mQrHint.setText(R.string.save_qr);
    }

    private Bitmap mQrImgBitmap;
    private void initNetDatas() {
        if(mInfo==null)
            return;
        mComplete = mInfo.getIs_complete();
        if("1".equals(mComplete)||"2".equals(mInfo.getStatus())){
            mPunchCard.setText("已打卡");
        }else{
            mPunchCard.setText("打卡");
        }
        List<SGDetailEntity.InfoBeanX.CardStatusBean> card_status;
        if(mInfo.getCard_status()!=null&&mInfo.getCard_status().size()>0){
            card_status = mInfo.getCard_status();
        }else{
            card_status = new ArrayList<>();
            int count = 0;
            try {
                count = Integer.parseInt(mInfo.getCard_ext());
            }catch (Exception e){
                count = 0;
            }
            for (int i = 0; i < count; i++) {
                card_status.add(new SGDetailEntity.InfoBeanX.CardStatusBean());
            }
        }

//
        mPunchCardList.setAdapter(new SGPunchCardAdapter(getContext(),card_status));

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRecordTv.getLayoutParams();
        if("0".equals(mIsStart)){
            mRanking.setVisibility(View.GONE);
            mClickedNum.setVisibility(View.GONE);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mRecordTv.setLayoutParams(layoutParams);
        }else{
            mRanking.setVisibility(View.VISIBLE);
            mRankIcon.setVisibility(View.VISIBLE);
            mClickedNum.setVisibility(View.VISIBLE);
            mClickedNum.setText("今日已打卡："+(mInfo.getPunch()==null?"0":mInfo.getPunch())+"人");
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mRecordTv.setLayoutParams(layoutParams);


            if(mInfo.getRank()!=null){
                initRank();
                mRankingList.setAdapter(new SGDetailRankAdapter(getContext(),mInfo.getRank().getOther()));
            }
        }

//        Glide.with(MyApplication.mApplication).load(mInfo.getInfo().getImg_qr()).centerCrop().into(mQr);
        Glide.with(MyApplication.mApplication).load(mInfo.getInfo().getImg_qr()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (mQrImgBitmap == null) {
                    mQr.setImageBitmap(resource);
                    mQrImgBitmap = resource;
                }
            }
        });
    }

    private void initRank() {
        if(mInfo.getRank().getMyself()!=null){
            //排名字体设置
            SGDetailEntity.InfoBeanX.RankBean.MyselfBean myselfBean = mInfo.getRank().getMyself();
            setRankText("第"+myselfBean.getMyranking()+"名");
            mRankName.setText(myselfBean.getUser_name()+"位列排行榜");
            Glide.with(getContext()).load(myselfBean.getUser_avator()).centerCrop().into(mPerRankAvatar);
        }

    }

    private void setRankText(String s) {
        Spannable sp = new SpannableString(s) ;
        sp.setSpan(new AbsoluteSizeSpan(20,true),1,s.length()-1,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mRankNum.setText(sp);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.check_rank:
                intent = new Intent(getContext(), StudyGroupRankActivity.class);
                intent.putExtra("pid",mPid);
                intent.putExtra("vid",mVid);
                intent.putExtra("complete",mComplete);
                startActivity(intent);
                break;
            case R.id.punch_card:
                if(mOnPunchCardClickListener!=null){
                    mOnPunchCardClickListener.onPunchCardClick(mVid);
                }
                break;
        }
    }


    public void changeStatus(String s) {
        mIsStart = s;
        accessNet();
    }

    public void accessNet(){
        LoadingUtils.getInstance().showNetLoading(getContext());
        mStudyGroupPresenter.getSGDetail(mUserId, mPid, mVid, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
                SGDetailEntity sgDetailEntity = (SGDetailEntity) value[0];
                mInfo = sgDetailEntity.getInfo();
                initNetDatas();

            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_ERROR","DH_JOINED_DETAIL"+error);
            }
        });
    }

    private OnPunchCardClickListener mOnPunchCardClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnPunchCardClickListener){
            mOnPunchCardClickListener = (OnPunchCardClickListener) activity;
        }
    }


    private SaveBitmapAsync mSaveBitmapAsync;
    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("是否确定保存图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mSaveBitmapAsync==null){
                            mSaveBitmapAsync = new SaveBitmapAsync(getActivity(), "qr_" + System.currentTimeMillis() + ".jpg", false);
                        }

                        mSaveBitmapAsync.execute(mQrImgBitmap);
                    }
                })
                .setNegativeButton("取消",null);
        Dialog dialog = builder.create();
        dialog.show();

        return true;
    }

    public interface OnPunchCardClickListener{
        void onPunchCardClick(String id);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(mQrImgBitmap!=null){
//            mQrImgBitmap.recycle();
//        }
    }
}
