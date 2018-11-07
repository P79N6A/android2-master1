package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCourseEntity;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by chdh on 2018/1/19.
 */

public class CompanyLearnedAdapter extends RecyclerView.Adapter<CompanyLearnedAdapter.LearnedViewHolder> {
    private List<CompanyCourseEntity.InfoBean.FinishBean> mList;
    private LayoutInflater mInflater;
    public CompanyLearnedAdapter(List<CompanyCourseEntity.InfoBean.FinishBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public LearnedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LearnedViewHolder(mInflater.inflate(R.layout.item_learned,parent,false));
    }

    @Override
    public void onBindViewHolder(final LearnedViewHolder holder, final int position) {
        if(holder!=null&&mList!=null&&mList.get(position)!=null){
            final CompanyCourseEntity.InfoBean.FinishBean finishBean = mList.get(position);
            holder.mName.setText(finishBean.getName());
            holder.mTime.setText(finishBean.getLast_play_time());
            if(StringUtils.isNotEmpty(finishBean.getAvator())){
                Glide.with(MyApplication.getApplication()).load(finishBean.getAvator()).centerCrop().into(holder.mAvatar);
            }else{
                holder.mAvatar.setImageResource(R.drawable.qiye_zwpic);
            }
            if(position<3){
                holder.mPosition.setTextColor(Color.parseColor("#ff4200"));
            }else{
                holder.mPosition.setTextColor(Color.parseColor("#282828"));
            }
            holder.mPosition.setText((position+1)+"");
            if(finishBean.getZhansum()!=null&&!"".equals(finishBean.getZhansum())){
                holder.mLikeNum.setText(finishBean.getZhansum()+"");
            }else{
                holder.mLikeNum.setText("0");
            }
            if("1".equals(finishBean.getZhan())){
                holder.mLike.setImageResource(R.drawable.learn_list_like);
            }else{
                holder.mLike.setImageResource(R.drawable.learn_list_unlike);
            }
            holder.mLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnLikeClickListener!=null){
                        mOnLikeClickListener.onLikeClick(position,holder.mLike,holder.mLikeNum,finishBean.getUser_id());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class LearnedViewHolder extends RecyclerView.ViewHolder{
        private TextView mPosition;
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mTime;
        private ImageView mLike;
        private TextView mLikeNum;

        public LearnedViewHolder(View itemView) {
            super(itemView);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mPosition = (TextView) itemView.findViewById(R.id.position);
            mLike = (ImageView) itemView.findViewById(R.id.like);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
        }
    }

    private OnLikeClickListener mOnLikeClickListener;

    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        mOnLikeClickListener = onLikeClickListener;
    }

    public interface OnLikeClickListener{
        void onLikeClick(int pos,ImageView img,TextView textView,String id);
    }
}
