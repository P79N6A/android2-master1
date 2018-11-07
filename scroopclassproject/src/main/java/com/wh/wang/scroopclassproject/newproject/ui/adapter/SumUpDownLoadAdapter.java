package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class SumUpDownLoadAdapter extends RecyclerView.Adapter<SumUpDownLoadAdapter.SumUpDownLoadViewHolder>{
    private DataKeeper mDataKeeper;
    private List<SumUpEntity.DirBean> mDownLoadList;
    private LayoutInflater mInflater;
    private OnDownloadClickListener mOnDownloadClickListener;

    public void setOnDownloadClickListener(OnDownloadClickListener mOnDownloadClickListener) {
        this.mOnDownloadClickListener = mOnDownloadClickListener;
    }

    public SumUpDownLoadAdapter(List<SumUpEntity.DirBean> mDownLoadList, Context context) {
        this.mDownLoadList = mDownLoadList;
        mDataKeeper = new DataKeeper(context);
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public SumUpDownLoadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SumUpDownLoadViewHolder(mInflater.inflate(R.layout.item_sum_up_download,parent,false));
    }

    @Override
    public void onBindViewHolder(final SumUpDownLoadViewHolder holder, final int position) {
        if(mDownLoadList!=null&&mDownLoadList.size()>0&&mDownLoadList.get(position)!=null){
            final SumUpEntity.DirBean dirBean = mDownLoadList.get(position);
            holder.mSumUpDownloadName.setText(dirBean.getVideo_title());
            int f_id = Integer.parseInt(dirBean.getVideo_id());
            int c_id = Integer.parseInt(dirBean.getId());
            File downloadFile = new File(FileHelper.getFileDefaultPath() + "/" + "." + c_id + ".mp4");
            if(downloadFile.exists()||mDataKeeper.isHave(f_id,c_id)){
                holder.mSumUpDownloadBt.setImageResource(R.drawable.course_down_noclicl);
                holder.mSumUpDownloadBt.setOnClickListener(null);
            }else{
                holder.mSumUpDownloadBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if("0".equals(dirBean.getCanshow())){
                            Toast.makeText(MyApplication.mApplication, "此小结未上线", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        holder.mSumUpDownloadBt.setImageResource(R.drawable.course_down_noclicl);
                        Toast.makeText(MyApplication.mApplication, "添加成功，正在为您下载..", Toast.LENGTH_SHORT).show();
                        if(mOnDownloadClickListener!=null){
                            mOnDownloadClickListener.onDownloadClick(position,dirBean);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDownLoadList.size();
    }

    public class SumUpDownLoadViewHolder extends RecyclerView.ViewHolder{
        private TextView mSumUpDownloadName;
        private ImageView mSumUpDownloadBt;
        private RelativeLayout mItem;

        public SumUpDownLoadViewHolder(View itemView) {
            super(itemView);
            mSumUpDownloadName = (TextView) itemView.findViewById(R.id.sum_up_download_name);
            mSumUpDownloadBt = (ImageView) itemView.findViewById(R.id.sum_up_download_bt);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
        }
    }

    public interface OnDownloadClickListener{
        void onDownloadClick(int pos,SumUpEntity.DirBean bean);
    }
}
