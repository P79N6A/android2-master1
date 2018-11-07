package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRankEntity;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/11.
 */

public class SGRankAdapter extends RecyclerView.Adapter<SGRankAdapter.RankViewHolder> {
    private Context mContext;
    private List<SGRankEntity.InfoBean.OtherBean> mRankList;
    private LayoutInflater mLayoutInflater;

    private int[] mRankImg = {R.drawable.jiangai1,R.drawable.jiangpai2,R.drawable.jiangpai3};

    public SGRankAdapter(Context context, List<SGRankEntity.InfoBean.OtherBean> rankList) {
        mContext = context;
        mRankList = rankList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RankViewHolder(mLayoutInflater.inflate(R.layout.item_study_group_rank,parent,false));
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        SGRankEntity.InfoBean.OtherBean otherBean = mRankList.get(position);
        if(position<3){
            holder.mRankImg.setVisibility(View.VISIBLE);
            holder.mRankTv.setVisibility(View.GONE);
            holder.mRankImg.setImageResource(mRankImg[position]);
        }else{
            holder.mRankImg.setVisibility(View.GONE);
            holder.mRankTv.setVisibility(View.VISIBLE);
            holder.mRankTv.setText(String.valueOf((position+1)));
        }
//        long min = 0;
//        try {
//            min = Long.parseLong(otherBean.getV_time());
//        }catch (Exception e){
//            min = 0;
//        }
        holder.mCateNum.setText(otherBean.getV_num()+"节");
        holder.mStudyTime.setText(otherBean.getV_time()+"分钟");
        holder.mName.setText(otherBean.getUser_name());
        if(StringUtils.isNotEmpty(otherBean.getUser_avator()))
            Glide.with(mContext).load(otherBean.getUser_avator()).centerCrop().into(holder.mAvatar);
    }

    @Override
    public int getItemCount() {
        if(mRankList!=null){
            return mRankList.size();
        }
        return 0;
    }

    class RankViewHolder extends RecyclerView.ViewHolder{
        private ImageView mRankImg;
        private TextView mRankTv;
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mCateNum;
        private TextView mStudyTime;

        public RankViewHolder(View itemView) {
            super(itemView);
            mRankImg = (ImageView) itemView.findViewById(R.id.rank_img);
            mRankTv = (TextView) itemView.findViewById(R.id.rank_tv);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mCateNum = (TextView) itemView.findViewById(R.id.cate_num);
            mStudyTime = (TextView) itemView.findViewById(R.id.study_time);
        }
    }
}
