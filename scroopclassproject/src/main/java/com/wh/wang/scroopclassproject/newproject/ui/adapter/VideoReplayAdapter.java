package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by chdh on 2018/2/23.
 */

public class VideoReplayAdapter extends RecyclerView.Adapter<VideoReplayAdapter.VideoReplayViewHolder> {
    private String mUserId;
    private List<SumUpEntity.CommentBean.ReplyBean> mReplyBeanList;
    private Context mContext;
    private LayoutInflater mInflater;

    public VideoReplayAdapter(List<SumUpEntity.CommentBean.ReplyBean> replyBeanList, Context context) {
        mReplyBeanList = replyBeanList;
        mContext = context;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    @Override
    public VideoReplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoReplayViewHolder(mInflater.inflate(R.layout.item_video_replay,parent,false));
    }

    @Override
    public void onBindViewHolder(VideoReplayViewHolder holder, final int position) {
        if(holder!=null&&mReplyBeanList.get(position)!=null){
            final SumUpEntity.CommentBean.ReplyBean replyBean = mReplyBeanList.get(position);
            String remark_name = subName(replyBean.getNickname())+" ";
            String content = remark_name+"回复 "+subName(replyBean.getRe_name())+": "+replyBean.getContent();
            SpannableString spanStr = new SpannableString(content);
            spanStr.setSpan(new ForegroundColorSpan(Color.parseColor("#16BC6E")), remark_name.length(), remark_name.length()+2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.mReplayContent.setText(spanStr);
            holder.mTime.setText(replyBean.getShijian());
            if(replyBean.getUser_id().equals(mUserId)){
                holder.mDelete.setVisibility(View.VISIBLE);
                holder.mDeleteIcon.setVisibility(View.VISIBLE);
                holder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnSecondDeleteClickListener!=null){
                            mOnSecondDeleteClickListener.onSecondDelClick(position,replyBean.getId());
                        }
                    }
                });
            }else{
                holder.mDelete.setVisibility(View.GONE);
                holder.mDeleteIcon.setVisibility(View.GONE);
            }
            holder.mReplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnSecondReplayClickListener!=null){
                        mOnSecondReplayClickListener.onReplayClick(replyBean.getParentid(),replyBean.getNickname());
                    }
                }
            });
        }
    }

    private String subName(String nickname) {
        if(nickname==null){
            nickname = "勺子用户";
        }
        if(nickname.length()>3){
            return nickname.substring(0,3)+"...";
        }
        return nickname;
    }

    @Override
    public int getItemCount() {
        return mReplyBeanList!=null?mReplyBeanList.size():0;
    }

    class VideoReplayViewHolder extends RecyclerView.ViewHolder{
        private TextView mReplayContent;
        private TextView mTime;
        private TextView mReplay;
        private TextView mDelete;
        private ImageView mDeleteIcon;

        public VideoReplayViewHolder(View itemView) {
            super(itemView);
            mReplayContent = (TextView) itemView.findViewById(R.id.replay_content);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mReplay = (TextView) itemView.findViewById(R.id.replay);
            mDelete = (TextView) itemView.findViewById(R.id.delete);
            mDeleteIcon = (ImageView) itemView.findViewById(R.id.delete_icon);
        }
    }

    private OnSecondReplayClickListener mOnSecondReplayClickListener;

    public void setOnSecondReplayClickListener(OnSecondReplayClickListener onSecondReplayClickListener) {
        mOnSecondReplayClickListener = onSecondReplayClickListener;
    }

    public interface OnSecondReplayClickListener{
        void onReplayClick(String p_id,String r_name);
    }

    private OnSecondDeleteClickListener mOnSecondDeleteClickListener;

    public void setOnSecondDeleteClickListener(OnSecondDeleteClickListener onSecondDeleteClickListener) {
        mOnSecondDeleteClickListener = onSecondDeleteClickListener;
    }

    public interface OnSecondDeleteClickListener{
        void onSecondDelClick(int pos,String id);
    }
}
