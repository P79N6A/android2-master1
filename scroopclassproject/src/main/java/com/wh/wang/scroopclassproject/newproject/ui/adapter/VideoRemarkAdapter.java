package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by chdh on 2018/2/23.
 */

public class VideoRemarkAdapter extends RecyclerView.Adapter<VideoRemarkAdapter.RemarkViewHolder> {
    private String mUserId;
    private List<SumUpEntity.CommentBean> mCommentBeanList;
    private Context mContext;
    private LayoutInflater mInflater;
    private VideoReplayAdapter mVideoReplayAdapter;

    public VideoRemarkAdapter(List<SumUpEntity.CommentBean> commentBeanList, Context context) {
        mCommentBeanList = commentBeanList;
        mContext = context;
        mInflater = LayoutInflater.from(MyApplication.mApplication);

        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    @Override
    public RemarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemarkViewHolder(mInflater.inflate(R.layout.item_video_remark,parent,false));
    }

    @Override
    public void onBindViewHolder(RemarkViewHolder holder, final int position) {
        if(holder!=null&&mCommentBeanList.get(position)!=null){
            final SumUpEntity.CommentBean commentBean = mCommentBeanList.get(position);
            if(commentBean.getNickname()!=null&&!"".equals(commentBean.getNickname().trim())){
                holder.mName.setText(commentBean.getNickname());
            }else{
                holder.mName.setText("勺子用户");
            }
            Log.e("DH_COMMENT_AVATAR","avatar: "+commentBean.getAvator());
            Glide.with(MyApplication.mApplication).load(commentBean.getAvator()).centerCrop().into(holder.mAvatar);
            holder.mContent.setText(commentBean.getContent());
            holder.mTime.setText(commentBean.getShijian());
            if(commentBean.getUser_id().equals(mUserId)){
                holder.mDelete.setVisibility(View.VISIBLE);
                holder.mDeleteIcon.setVisibility(View.VISIBLE);
                holder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("是否删除此评论?")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(mOnDeleteRemarkClickListener!=null){
                                            mOnDeleteRemarkClickListener.onDelRemark(position,commentBean.getId());
                                        }
                                    }
                                })
                                .setNegativeButton("否",null);
                        Dialog d = builder.create();
                        d.setCancelable(false);
                        d.setCanceledOnTouchOutside(false);
                        d.show();
                    }
                });
            }else{
                holder.mDelete.setVisibility(View.GONE);
                holder.mDeleteIcon.setVisibility(View.GONE);
            }
            if(commentBean.getChild()!=null&&commentBean.getChild().size()>0){
                holder.mReplayList.setVisibility(View.VISIBLE);
                mVideoReplayAdapter = new VideoReplayAdapter(commentBean.getChild(),mContext);
                holder.mReplayList.setAdapter(mVideoReplayAdapter);
                mVideoReplayAdapter.setOnSecondReplayClickListener(new VideoReplayAdapter.OnSecondReplayClickListener() {
                    @Override
                    public void onReplayClick(String p_id, String r_name) {
                        if(mOnReplayClickListener!=null){
                            mOnReplayClickListener.onReplayClick(commentBean.getChild(),p_id,r_name);
                        }
                    }
                });
                mVideoReplayAdapter.setOnSecondDeleteClickListener(new VideoReplayAdapter.OnSecondDeleteClickListener() {
                    @Override
                    public void onSecondDelClick(final int pos, final String id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("是否删除此条回复?")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(mOnDeleteReplayClickListener!=null){
                                            mOnDeleteReplayClickListener.onDelReplay(pos,commentBean.getChild(),id);
                                        }
                                    }
                                })
                                .setNegativeButton("否",null);
                        Dialog d = builder.create();
                        d.setCancelable(false);
                        d.setCanceledOnTouchOutside(false);
                        d.show();
                    }
                });
            }else{
                holder.mReplayList.setVisibility(View.GONE);
            }
            holder.mReplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnReplayClickListener!=null){
                        mOnReplayClickListener.onReplayClick(commentBean.getChild(),commentBean.getParentid(),commentBean.getNickname());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCommentBeanList!=null?mCommentBeanList.size():0;
    }


    class RemarkViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mContent;
        private TextView mTime;
        private TextView mReplay;
        private TextView mDelete;
        private ImageView mDeleteIcon;
        private RecyclerView mReplayList;

        public RemarkViewHolder(View itemView) {
            super(itemView);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mReplay = (TextView) itemView.findViewById(R.id.replay);
            mDelete = (TextView) itemView.findViewById(R.id.delete);
            mReplayList = (RecyclerView) itemView.findViewById(R.id.replay_list);
            mDeleteIcon = (ImageView) itemView.findViewById(R.id.delete_icon);
            mReplayList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        }
    }

    private OnReplayClickListener mOnReplayClickListener;

    public void setOnReplayClickListener(OnReplayClickListener onReplayClickListener) {
        mOnReplayClickListener = onReplayClickListener;
    }

    public interface OnReplayClickListener{
        void onReplayClick(List<SumUpEntity.CommentBean.ReplyBean> replyBeanList,String p_id,String r_name);
    }

    private OnDeleteRemarkClickListener mOnDeleteRemarkClickListener;

    public void setOnDeleteRemarkClickListener(OnDeleteRemarkClickListener onDeleteRemarkClickListener) {
        mOnDeleteRemarkClickListener = onDeleteRemarkClickListener;
    }

    public interface OnDeleteRemarkClickListener{
        void onDelRemark(int pos,String id);
    }

    private OnDeleteReplayClickListener mOnDeleteReplayClickListener;

    public void setOnDeleteReplayClickListener(OnDeleteReplayClickListener onDeleteReplayClickListener) {
        mOnDeleteReplayClickListener = onDeleteReplayClickListener;
    }

    public interface OnDeleteReplayClickListener{
        void onDelReplay(int pos,List<SumUpEntity.CommentBean.ReplyBean> mReplyList,String id);
    }
}
