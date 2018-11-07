package com.wh.wang.scroopclassproject.newproject.ui.adapter;

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
import com.wh.wang.scroopclassproject.newproject.mvp.model.MyStudyEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class StudyCollectAdapter extends RecyclerView.Adapter<StudyCollectAdapter.CollectViewHolder> {
    private List<MyStudyEntity.CollectBean> mCollects;
    private LayoutInflater mInflater;
    private OnCollectClickListener mOnCollectClickListener;

    public void setOnCollectClickListener(OnCollectClickListener mOnCollectClickListener) {
        this.mOnCollectClickListener = mOnCollectClickListener;
    }

    public StudyCollectAdapter(List<MyStudyEntity.CollectBean> mCollects) {
        this.mCollects = mCollects;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public CollectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectViewHolder(mInflater.inflate(R.layout.item_study,parent,false));
    }

    @Override
    public void onBindViewHolder(CollectViewHolder holder, final int position) {
        if(holder!=null&&mCollects.get(position)!=null){
            final MyStudyEntity.CollectBean collectBean = mCollects.get(position);
            Glide.with(MyApplication.mApplication).load(collectBean.getImg()).placeholder(R.drawable.ivnull).centerCrop().into(holder.mItemStudyImg);
            holder.mItemStudyTitle.setText(collectBean.getTitle());
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnCollectClickListener!=null){
                        mOnCollectClickListener.onCollectClick(position,collectBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCollects!=null?mCollects.size():0;
    }

    class CollectViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mItem;
        private ImageView mItemStudyImg;
        private TextView mItemStudyTitle;

        public CollectViewHolder(View itemView) {
            super(itemView);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
            mItemStudyImg = (ImageView) itemView.findViewById(R.id.item_study_img);
            mItemStudyTitle = (TextView) itemView.findViewById(R.id.item_study_title);
        }
    }


    public interface OnCollectClickListener{
        void onCollectClick(int pos,MyStudyEntity.CollectBean collectBean);
    }
}
