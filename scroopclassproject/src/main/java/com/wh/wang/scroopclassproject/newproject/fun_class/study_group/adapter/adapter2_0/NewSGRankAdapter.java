package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.NewSGRankEntity;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/24.
 */

public class NewSGRankAdapter extends RecyclerView.Adapter<NewSGRankAdapter.SGRankViewHolder> {
    private Context mContext;
    private List<NewSGRankEntity.InfoBean.RankBean> mRankBeanList;
    private LayoutInflater mLayoutInflater;
    private String mType; //1 今日  2 成败
    private String mFlag = "success";

    public NewSGRankAdapter(Context context, List<NewSGRankEntity.InfoBean.RankBean> rankBeanList,String type,String flag) {
        mContext = context;
        mRankBeanList = rankBeanList;
        mLayoutInflater = LayoutInflater.from(context);
        mType = type;
        mFlag = flag;
    }

    @Override
    public SGRankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SGRankViewHolder(mLayoutInflater.inflate(R.layout.item_sg_rank,parent,false));
    }

    @Override
    public void onBindViewHolder(SGRankViewHolder holder, int position) {
        NewSGRankEntity.InfoBean.RankBean rankBean = mRankBeanList.get(position);
        holder.mRankNum.setText(String.valueOf(position+1));
        Glide.with(mContext).load(rankBean.getUser_avator()).centerCrop().into(holder.mAvatar);
        holder.mName.setText(rankBean.getUser_name());
        if("1".equals(mType)){
            int time = (int) Double.parseDouble(rankBean.getV_time());
            holder.mDays.setText(time+"分钟");
        }else if("2".equals(mType)){
            if ("out".equals(mFlag)) {
                holder.mDays.setText("第"+rankBean.getWhichDayOut()+"天出局");
            }else{
                holder.mDays.setText("连续打卡"+rankBean.getNum()+"天");
            }

        }
    }

    @Override
    public int getItemCount() {
        return mRankBeanList!=null?mRankBeanList.size():0;
    }

    class SGRankViewHolder extends RecyclerView.ViewHolder{
        private TextView mRankNum;
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mDays;

        public SGRankViewHolder(View itemView) {
            super(itemView);

            mRankNum = (TextView) itemView.findViewById(R.id.rank_num);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mDays = (TextView) itemView.findViewById(R.id.days);
        }
    }
}
