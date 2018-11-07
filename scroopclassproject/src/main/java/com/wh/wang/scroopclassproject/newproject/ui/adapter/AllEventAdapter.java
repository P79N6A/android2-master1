package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MonthEventEntity;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/5.
 */

public class AllEventAdapter extends RecyclerView.Adapter<AllEventAdapter.AllEventClassViewHolder>  {
    private Context mContext;
    private List<MonthEventEntity.MonthListBean> mEventList;
    private LayoutInflater mLayoutInflater;

    public AllEventAdapter(Context context, List<MonthEventEntity.MonthListBean> eventList) {
        mContext = context;
        mEventList = eventList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AllEventClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllEventClassViewHolder(mLayoutInflater.inflate(R.layout.item_new_event,parent,false));
    }

    @Override
    public void onBindViewHolder(AllEventClassViewHolder holder, int position) {
        final MonthEventEntity.MonthListBean monthListBean = mEventList.get(position);
        if(StringUtils.isNotEmpty(monthListBean.getImg()))
            Glide.with(mContext).load(monthListBean.getImg()).centerCrop().into(holder.mEventImg);
        if(StringUtils.isNotEmpty(monthListBean.getTitle()))
            holder.mEventTitle.setText(monthListBean.getTitle());
        if(StringUtils.isNotEmpty(monthListBean.getStart_shijian()))
            holder.mEventTime.setText(monthListBean.getStart_shijian());
        if(StringUtils.isNotEmpty(monthListBean.getAddress()))
            holder.mEventAddress.setText(monthListBean.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnEventItemClickListener!=null)
                    mOnEventItemClickListener.onEventItemClick(monthListBean.getSel_type(),monthListBean.getId(),monthListBean.getType());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventList==null?0:mEventList.size();
    }

    class AllEventClassViewHolder extends RecyclerView.ViewHolder{
        private TextView mEventTime;
        private RoundedImageView mEventImg;
        private TextView mEventTitle;
        private TextView mEventAddress;

        public AllEventClassViewHolder(View itemView) {
            super(itemView);
            mEventTime = (TextView) itemView.findViewById(R.id.event_time);
            mEventImg = (RoundedImageView) itemView.findViewById(R.id.event_img);
            mEventTitle = (TextView) itemView.findViewById(R.id.event_title);
            mEventAddress = (TextView) itemView.findViewById(R.id.event_address);
        }
    }

    private OnEventItemClickListener mOnEventItemClickListener;

    public void setOnEventItemClickListener(OnEventItemClickListener onEventItemClickListener) {
        mOnEventItemClickListener = onEventItemClickListener;
    }

    public interface OnEventItemClickListener{
        void onEventItemClick(String sel_type,String id,String type);
    }
}
