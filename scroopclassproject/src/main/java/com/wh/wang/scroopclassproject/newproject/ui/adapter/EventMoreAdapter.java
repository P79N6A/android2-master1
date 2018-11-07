package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.EventMoreEntity;

import java.util.List;

/**
 * Created by chdh on 2018/1/15.
 */

public class EventMoreAdapter extends RecyclerView.Adapter<EventMoreAdapter.EventMoreViewHolder> {
    private List<EventMoreEntity> mEventMoreEntities;
    private LayoutInflater mInflater;

    public EventMoreAdapter(List<EventMoreEntity> eventMoreEntities) {
        mEventMoreEntities = eventMoreEntities;
        mInflater = LayoutInflater.from(MyApplication.getApplication());
    }

    @Override
    public EventMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventMoreViewHolder(mInflater.inflate(R.layout.item_event_more,parent,false));
    }

    @Override
    public void onBindViewHolder(EventMoreViewHolder holder, final int position) {
        if(holder!=null&&mEventMoreEntities.get(position)!=null){
            EventMoreEntity eventMoreEntity = mEventMoreEntities.get(position);
            holder.mAddName.setText(eventMoreEntity.getName());
            holder.mAddTel.setText(eventMoreEntity.getTel());
            holder.mAddMoney.setText(eventMoreEntity.getPrice());
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnEventMoreDeleteClickListener!=null){
                        mOnEventMoreDeleteClickListener.onMoreDeleteClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEventMoreEntities.size();
    }

    class EventMoreViewHolder extends RecyclerView.ViewHolder{
        private TextView mAddName;
        private TextView mAddTel;
        private ImageView mDelete;
        private TextView mAddMoney;

        public EventMoreViewHolder(View itemView) {
            super(itemView);
            mAddName = (TextView) itemView.findViewById(R.id.add_name);
            mAddTel = (TextView) itemView.findViewById(R.id.add_tel);
            mDelete = (ImageView) itemView.findViewById(R.id.delete);
            mAddMoney = (TextView) itemView.findViewById(R.id.add_money);
        }
    }

    private OnEventMoreDeleteClickListener mOnEventMoreDeleteClickListener;

    public void setOnEventMoreDeleteClickListener(OnEventMoreDeleteClickListener onEventMoreDeleteClickListener) {
        mOnEventMoreDeleteClickListener = onEventMoreDeleteClickListener;
    }

    public interface OnEventMoreDeleteClickListener{
        void onMoreDeleteClick(int pos);
    }
}
