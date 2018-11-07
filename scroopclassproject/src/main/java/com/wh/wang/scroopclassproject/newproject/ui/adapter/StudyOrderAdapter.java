package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MyStudyEntity;

import java.util.List;

/**
 * Created by chdh on 2018/1/25.
 */

public class StudyOrderAdapter extends RecyclerView.Adapter<StudyOrderAdapter.OrderViewHolder> {
    private List<MyStudyEntity.OrderBean> mOrderBeanList;
    private LayoutInflater mInflater;

    public StudyOrderAdapter(List<MyStudyEntity.OrderBean> orderBeanList) {
        mOrderBeanList = orderBeanList;
        mInflater = LayoutInflater.from(MyApplication.getApplication());
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(mInflater.inflate(R.layout.item_study,parent,false));
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        if(holder!=null&&mOrderBeanList!=null&&mOrderBeanList.get(position)!=null){
            final MyStudyEntity.OrderBean orderBean = mOrderBeanList.get(position);
            String ifnew = orderBean.getIfnew();
            if("2".equals(ifnew)){
                holder.mTag.setVisibility(View.VISIBLE);
            }else{
                holder.mTag.setVisibility(View.GONE);
            }
            Glide.with(MyApplication.mApplication).load(orderBean.getImg()).centerCrop().into(holder.mItemStudyImg);
            holder.mItemStudyProcess.setVisibility(View.VISIBLE);
            holder.mItemStudyProcess.setTextColor(Color.parseColor("#89c635"));
            if("0.00".equals(orderBean.getMoney())){
                holder.mItemStudyProcess.setText("免费");

            }else{
                holder.mItemStudyProcess.setText("￥"+orderBean.getMoney());

            }
            holder.mItemStudyTitle.setText(orderBean.getTitle());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnOrderClickListener!=null){
                        mOnOrderClickListener.onOrderClick(position,orderBean.getId(),orderBean.getType());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mOrderBeanList!=null?mOrderBeanList.size():0;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        private ImageView mItemStudyImg;
        private TextView mItemStudyTitle;
        private TextView mItemStudyProcess;
        private View mView;
        private TextView mTag;


        public OrderViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mItemStudyImg = (ImageView) itemView.findViewById(R.id.item_study_img);
            mItemStudyTitle = (TextView) itemView.findViewById(R.id.item_study_title);
            mItemStudyProcess = (TextView) itemView.findViewById(R.id.item_study_process);
            mTag = (TextView) itemView.findViewById(R.id.tag);

        }
    }

    private OnOrderClickListener mOnOrderClickListener;

    public void setOnOrderClickListener(OnOrderClickListener onOrderClickListener) {
        mOnOrderClickListener = onOrderClickListener;
    }

    public interface OnOrderClickListener{
        void onOrderClick(int pos,String id,String type);
    }
}
