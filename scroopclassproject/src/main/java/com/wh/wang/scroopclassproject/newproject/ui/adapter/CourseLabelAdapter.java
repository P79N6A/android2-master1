package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseLabelEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CourseLabelAdapter extends RecyclerView.Adapter<CourseLabelAdapter.LabelViewHolder>{
    private List<CourseLabelEntity.InfoBean.AllTypeBean> mAllTypes;
    private LayoutInflater mInflater;
    private Context mContext;
    public CourseLabelAdapter(List<CourseLabelEntity.InfoBean.AllTypeBean> mAllTypes,Context context) {
        this.mAllTypes = mAllTypes;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
        mContext = context;
    }

    @Override
    public LabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LabelViewHolder(mInflater.inflate(R.layout.item_label,parent,false));
    }
    int old = 0;
    @Override
    public void onBindViewHolder(LabelViewHolder holder, final int position) {
        if(holder!=null&&mAllTypes!=null&&mAllTypes.get(position)!=null){
            final CourseLabelEntity.InfoBean.AllTypeBean allTypeBean = mAllTypes.get(position);
            if(allTypeBean.isCheck()){
                old = position;
                holder.mItemLabel.setBackgroundColor(mContext.getResources().getColor(R.color.label_select));
            }else{
                holder.mItemLabel.setBackgroundColor(mContext.getResources().getColor(R.color.label_unselect));
            }
            if(allTypeBean.getLogo()!=null&&!"".equals(allTypeBean.getLogo())){
                holder.mLabelIcon.setVisibility(View.VISIBLE);
                Glide.with(MyApplication.mApplication).load(allTypeBean.getLogo()).into(holder.mLabelIcon);
            }else{
                holder.mLabelIcon.setVisibility(View.GONE);
            }
            holder.mLabelName.setText(allTypeBean.getName());
            holder.mItemLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnLabelClickListener!=null){
                        mOnLabelClickListener.onLabelClick(old,position,allTypeBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAllTypes!=null?mAllTypes.size():0;
    }

    class LabelViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mItemLabel;
        private ImageView mLabelIcon;
        private TextView mLabelName;

        public LabelViewHolder(View itemView) {
            super(itemView);
            mItemLabel = (RelativeLayout) itemView.findViewById(R.id.item_label);
            mLabelIcon = (ImageView) itemView.findViewById(R.id.label_icon);
            mLabelName = (TextView) itemView.findViewById(R.id.label_name);
        }
    }

    private OnLabelClickListener mOnLabelClickListener;

    public void setOnLabelClickListener(OnLabelClickListener mOnLabelClickListener) {
        this.mOnLabelClickListener = mOnLabelClickListener;
    }

    public interface OnLabelClickListener{
        void onLabelClick(int old_p,int new_p,CourseLabelEntity.InfoBean.AllTypeBean allTypeBean);
    }
}
