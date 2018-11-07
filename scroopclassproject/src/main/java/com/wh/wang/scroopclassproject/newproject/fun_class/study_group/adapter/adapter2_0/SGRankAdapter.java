package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/16.
 */

public class SGRankAdapter extends RecyclerView.Adapter<SGRankAdapter.RankViewHolder> {

    private Context mContext;
    private List<String> mRankList;
    private LayoutInflater mLayoutInflater;

    public SGRankAdapter(Context context, List<String> rankList) {
        mContext = context;
        mRankList = rankList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RankViewHolder(mLayoutInflater.inflate(R.layout.item_sg_rank,parent,false));
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        holder.mRankNum.setText(String.valueOf(position+1));
        holder.mName.setText(mRankList.get(position));
        holder.mDays.setText("已连续打卡"+(position+10)+"天");
    }

    @Override
    public int getItemCount() {
        return mRankList!=null?mRankList.size():0;
    }

    class RankViewHolder extends RecyclerView.ViewHolder{
        private TextView mRankNum;
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mDays;

        public RankViewHolder(View itemView) {
            super(itemView);
            mRankNum = (TextView) itemView.findViewById(R.id.rank_num);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mDays = (TextView) itemView.findViewById(R.id.days);
        }
    }
}
