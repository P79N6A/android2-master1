package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCourseEntity;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by chdh on 2018/1/19.
 */

public class CompanyRemindAdapter extends RecyclerView.Adapter<CompanyRemindAdapter.RemindViewHolder> {
    private String mUser_id;
    private List<CompanyCourseEntity.InfoBean.NofinishBean> mList;
    private LayoutInflater mInflater;
    public CompanyRemindAdapter(List<CompanyCourseEntity.InfoBean.NofinishBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    @Override
    public RemindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemindViewHolder(mInflater.inflate(R.layout.item_no_learned,parent,false));
    }

    @Override
    public void onBindViewHolder(final RemindViewHolder holder, final int position) {
        if(holder!=null&&mList!=null&&mList.get(position)!=null){
            final CompanyCourseEntity.InfoBean.NofinishBean nofinishBean = mList.get(position);
            holder.mName.setText(nofinishBean.getName());
            holder.mTime.setText("已观看"+nofinishBean.getPlayer_time()+"分钟");
            holder.mPercent.setText("完成"+nofinishBean.getPercent());
            if(StringUtils.isNotEmpty(nofinishBean.getAvator())){
                Glide.with(MyApplication.getApplication()).load(nofinishBean.getAvator()).centerCrop().into(holder.mAvatar);
            }else{
                holder.mAvatar.setImageResource(R.drawable.qiye_zwpic);
            }
            Log.e("DH_TX","TX:"+nofinishBean.getTixing());
            if(nofinishBean.getUser_id()!=null&&nofinishBean.getUser_id().equals(mUser_id)){
                holder.mRemind.setVisibility(View.INVISIBLE);
            }else{
                holder.mRemind.setVisibility(View.VISIBLE);
            }
            if(nofinishBean.getTixing()==null||"1".equals(nofinishBean.getTixing())){
                holder.mRemind.setBackgroundResource(R.drawable.apply_examine_bg);
                holder.mRemind.setTextColor(Color.parseColor("#C2C3C6"));
                holder.mRemind.setText("已提醒");

                holder.mRemind.setOnClickListener(null);
            }else{
                holder.mRemind.setBackgroundResource(R.drawable.shape_many_pay_bg);
                holder.mRemind.setTextColor(Color.parseColor("#FFAD1E"));
                holder.mRemind.setText("提醒");
                holder.mRemind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnTiXingClickListener!=null){
                            mOnTiXingClickListener.onTXClick(position,nofinishBean.getUser_id(),holder.mRemind);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class RemindViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mTime;
        private TextView mRemind;
        private TextView mPercent;

        public RemindViewHolder(View itemView) {
            super(itemView);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mRemind = (TextView) itemView.findViewById(R.id.remind);
            mPercent = (TextView) itemView.findViewById(R.id.percent);

        }
    }

    private OnTiXingClickListener mOnTiXingClickListener;

    public void setOnTiXingClickListener(OnTiXingClickListener onTiXingClickListener) {
        mOnTiXingClickListener = onTiXingClickListener;
    }

    public interface OnTiXingClickListener{
        void onTXClick(int pos,String id,TextView textView);
    }
}
