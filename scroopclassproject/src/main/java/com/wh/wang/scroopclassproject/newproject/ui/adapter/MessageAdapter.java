package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.LikeOrRemindEntity;

import java.util.List;

/**
 * Created by chdh on 2018/1/24.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<LikeOrRemindEntity.ListBean> mList;
    private int mFlag;
    private LayoutInflater mInflater;

    public MessageAdapter(List<LikeOrRemindEntity.ListBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(MyApplication.getApplication());
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(mInflater.inflate(R.layout.item_msg,parent,false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        if(holder!=null&&mList!=null&&mList.get(position)!=null){
            LikeOrRemindEntity.ListBean listBean = mList.get(position);
            Glide.with(MyApplication.mApplication).load(listBean.getAvator()).centerCrop().into(holder.mMsgIcon);
            holder.mMsgTime.setText(listBean.getCreate_time());
            holder.mMsgContent.setText(listBean.getContent());
            holder.mMsgName.setText(listBean.getName());
            holder.mMsgInfo.setText(listBean.getTitle());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnLikeOrRemindClickListener!=null){
                        mOnLikeOrRemindClickListener.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mMsgIcon;
        private TextView mMsgTime;
        private TextView mMsgName;
        private TextView mMsgInfo;
        private TextView mMsgContent;

        private View mView;
        public MessageViewHolder(View itemView) {
            super(itemView);
            mMsgIcon = (RoundedImageView) itemView.findViewById(R.id.msg_icon);
            mMsgTime = (TextView) itemView.findViewById(R.id.msg_time);
            mMsgName = (TextView) itemView.findViewById(R.id.msg_name);
            mMsgInfo = (TextView) itemView.findViewById(R.id.msg_info);
            mMsgContent = (TextView) itemView.findViewById(R.id.msg_content);
            mView = itemView;
        }
    }

    private OnLikeOrRemindClickListener mOnLikeOrRemindClickListener;

    public void setOnLikeOrRemindClickListener(OnLikeOrRemindClickListener onLikeOrRemindClickListener) {
        mOnLikeOrRemindClickListener = onLikeOrRemindClickListener;
    }

    public interface OnLikeOrRemindClickListener{
        void onClick(int pos);
    }
}
