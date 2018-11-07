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

public class StudyRecordAdapter extends RecyclerView.Adapter<StudyRecordAdapter.RecordViewHolder> {
    private List<MyStudyEntity.StudyBean> mStudyList;
    private LayoutInflater mInflater;

    public StudyRecordAdapter(List<MyStudyEntity.StudyBean> mStudyList) {
        this.mStudyList = mStudyList;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordViewHolder(mInflater.inflate(R.layout.item_study,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecordViewHolder holder, final int position) {
        if(holder!=null&&mStudyList!=null&&mStudyList.size()>0){
            final MyStudyEntity.StudyBean studyBean = mStudyList.get(position);
            String ifnew = studyBean.getIfnew();
            if("2".equals(ifnew)){
                holder.mTag.setVisibility(View.VISIBLE);
            }else{
                holder.mTag.setVisibility(View.GONE);
            }
            if(mIsShowSelect){
                holder.mSelect.setVisibility(View.VISIBLE);
                if(studyBean.isCheck()){
                    holder.mSelect.setImageResource(R.drawable.right);
                }else{
                    holder.mSelect.setImageResource(R.drawable.none);
                }
                holder.mSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!studyBean.isCheck()){
                            holder.mSelect.setImageResource(R.drawable.right);
                            studyBean.setCheck(true);
                        }else{
                            holder.mSelect.setImageResource(R.drawable.none);
                            studyBean.setCheck(false);
                        }
                    }
                });
            }else{
                holder.mSelect.setVisibility(View.GONE);
            }
            holder.mItemStudyProcess.setVisibility(View.VISIBLE);
            Glide.with(MyApplication.mApplication).load(studyBean.getImg()).placeholder(R.drawable.ivnull).centerCrop().into(holder.mItemStudyImg);
            holder.mItemStudyTitle.setText(studyBean.getTitle());
            holder.mItemStudyProcess.setText("已完成"+studyBean.getPer());
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnRecordClickListener!=null){
                        mOnRecordClickListener.onRecordClick(position,studyBean,studyBean.getIfnew());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mStudyList!=null?mStudyList.size():0;
    }

    class RecordViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mItem;
        private ImageView mItemStudyImg;
        private TextView mItemStudyTitle;
        private TextView mItemStudyProcess;
        private ImageView mSelect;
        private TextView mTag;


        public RecordViewHolder(View itemView) {
            super(itemView);
            mSelect = (ImageView) itemView.findViewById(R.id.select);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
            mItemStudyImg = (ImageView) itemView.findViewById(R.id.item_study_img);
            mItemStudyTitle = (TextView) itemView.findViewById(R.id.item_study_title);
            mItemStudyProcess = (TextView) itemView.findViewById(R.id.item_study_process);
            mTag = (TextView) itemView.findViewById(R.id.tag);

        }
    }

    private boolean mIsShowSelect = false;
    public void showSelect(boolean isShow){
        mIsShowSelect = isShow;
    }

    private OnRecordClickListener mOnRecordClickListener;

    public void setOnRecordClickListener(OnRecordClickListener mOnRecordClickListener) {
        this.mOnRecordClickListener = mOnRecordClickListener;
    }

    public interface OnRecordClickListener{
        void onRecordClick(int pos,MyStudyEntity.StudyBean studyBean,String tag);
    }
}
