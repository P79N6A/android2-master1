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
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class StudyDownLoadAdapter extends RecyclerView.Adapter<StudyDownLoadAdapter.DownLoadViewHolder> {
    private List<SQLDownLoadInfo> mSqlDownList;
    private LayoutInflater mLayoutInflater;
    private boolean mIsShowAll = false;

    public StudyDownLoadAdapter(List<SQLDownLoadInfo> mSqlDownList) {
        this.mSqlDownList = mSqlDownList;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
    }


    @Override
    public DownLoadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownLoadViewHolder(mLayoutInflater.inflate(R.layout.item_download,parent,false));
    }

    @Override
    public void onBindViewHolder(final DownLoadViewHolder holder, final int position) {
        if(holder!=null&&mSqlDownList!=null&&mSqlDownList.get(position)!=null){
            final SQLDownLoadInfo downLoadBean = mSqlDownList.get(position);
            if(mIsShowAll){
                holder.mSelect.setVisibility(View.VISIBLE);
                if(downLoadBean.isCheck()){
                    holder.mSelect.setImageResource(R.drawable.right);
                }else{
                    holder.mSelect.setImageResource(R.drawable.none);
                }
                holder.mSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(downLoadBean.isCheck()){
                            holder.mSelect.setImageResource(R.drawable.none);
                            downLoadBean.setCheck(false);
                        }else{
                            holder.mSelect.setImageResource(R.drawable.right);
                            downLoadBean.setCheck(true);
                        }
                    }
                });

            }else{
                holder.mSelect.setVisibility(View.GONE);
            }
            holder.mItemDownloadTitle.setText(downLoadBean.getFatherTitle());
            Glide.with(MyApplication.mApplication).load(downLoadBean.getFatherImg()).placeholder(R.drawable.ivnull).centerCrop().into(holder.mItemDownloadImg);
            DataKeeper dataKeeper = new DataKeeper(MyApplication.mApplication);
            ArrayList<SQLDownLoadInfo> list = dataKeeper.getUserDownLoadInfoByFatherId(downLoadBean.getFatherId());
            holder.mItemTime.setText(list.size()+"课时");
            Double fatherSize = 0.0;
            for (int i = 0; i < list.size(); i++) {
                fatherSize = fatherSize + list.get(i).getChildSize();
            }
            holder.mItemDownloadSize.setText(FileUtils.getFormatSize(fatherSize));
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnDownLoadClickListener!=null){
                        mOnDownLoadClickListener.onDownLoadClick(position,downLoadBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mSqlDownList!=null?mSqlDownList.size():0;
    }

    class DownLoadViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mItem;
        private ImageView mSelect;
        private ImageView mItemDownloadImg;
        private TextView mItemDownloadTitle;
        private TextView mItemTime;
        private TextView mItemDownloadSize;

        public DownLoadViewHolder(View itemView) {
            super(itemView);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
            mSelect = (ImageView) itemView.findViewById(R.id.select);
            mItemDownloadImg = (ImageView) itemView.findViewById(R.id.item_download_img);
            mItemDownloadTitle = (TextView) itemView.findViewById(R.id.item_download_title);
            mItemTime = (TextView) itemView.findViewById(R.id.item_time);
            mItemDownloadSize = (TextView) itemView.findViewById(R.id.item_download_size);
        }
    }

    public void showOrNotSelect(boolean isShow){
        mIsShowAll = isShow;
        StudyDownLoadAdapter.this.notifyDataSetChanged();
    }

    private OnDownLoadClickListener mOnDownLoadClickListener;

    public void setOnDownLoadClickListener(OnDownLoadClickListener mOnDownLoadClickListener) {
        this.mOnDownLoadClickListener = mOnDownLoadClickListener;
    }

    public interface OnDownLoadClickListener{
        void onDownLoadClick(int pos,SQLDownLoadInfo sqlDownLoadInfo);
    }
}
