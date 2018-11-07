package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayCommentBean;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/9/27.
 */

public class EssayRemarkAdapter extends RecyclerView.Adapter<EssayRemarkAdapter.EssayRemarkViewHolder> {
    private final String mNickname;
    private String mUserId;
    private Context mContext;
    private List<EssayCommentBean> mList;
    private LayoutInflater mLayoutInflater;

    public EssayRemarkAdapter(Context context, List<EssayCommentBean> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mNickname = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
    }

    @Override
    public EssayRemarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EssayRemarkViewHolder(mLayoutInflater.inflate(R.layout.item_video_remark,parent,false));
    }

    @Override
    public void onBindViewHolder(EssayRemarkViewHolder holder, final int position) {
        final EssayCommentBean essayCommentBean = mList.get(position);
        if (StringUtils.isNotEmpty(essayCommentBean.getAvator())) {
            Glide.with(MyApplication.mApplication).load(essayCommentBean.getAvator()).into(holder.mAvatar);
        }
        holder.mName.setText(StringUtils.isEmpty(essayCommentBean.getNickname())?mNickname:essayCommentBean.getNickname());
        holder.mContent.setText(essayCommentBean.getContent());
        holder.mTime.setText(essayCommentBean.getShijian());
        if (mUserId.equals(essayCommentBean.getUser_id())) {
            holder.mDelete.setVisibility(View.VISIBLE);
            holder.mDeleteIcon.setVisibility(View.VISIBLE);
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("是否删除此条评论吗?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mOnRemarkClickListener!=null) {
                                        mOnRemarkClickListener.onDelRemark(position);
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

        if (essayCommentBean.getChild()!=null&&essayCommentBean.getChild().size()>0) {
            holder.mReplayList.setVisibility(View.VISIBLE);
            EssayReplayAdapter essayReplayAdapter = new EssayReplayAdapter(mContext, essayCommentBean.getChild());
            holder.mReplayList.setAdapter(essayReplayAdapter);
            essayReplayAdapter.setOnEssayReplyClickListener(new EssayReplayAdapter.OnEssayReplyClickListener() {
                @Override
                public void onEssayReplayClick(String rename) {
                    if (mOnRemarkClickListener!=null) {
                        mOnRemarkClickListener.onRemarkClick(position,essayCommentBean.getId(),rename);
                    }
                }

                @Override
                public void onReplyDelClick(final int pos, String id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("是否删除此条回复?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mOnRemarkClickListener!=null) {
                                        mOnRemarkClickListener.onDelReply(position,essayCommentBean.getChild(),pos);
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
                if (mOnRemarkClickListener!=null) {
                    mOnRemarkClickListener.onRemarkClick(position,essayCommentBean.getId(),essayCommentBean.getNickname());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class EssayRemarkViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mContent;
        private TextView mTime;
        private TextView mReplay;
        private ImageView mReplayIcon;
        private TextView mDelete;
        private ImageView mDeleteIcon;
        private RecyclerView mReplayList;

        public EssayRemarkViewHolder(View itemView) {
            super(itemView);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mReplay = (TextView) itemView.findViewById(R.id.replay);
            mReplayIcon = (ImageView) itemView.findViewById(R.id.replay_icon);
            mDelete = (TextView) itemView.findViewById(R.id.delete);
            mDeleteIcon = (ImageView) itemView.findViewById(R.id.delete_icon);
            mReplayList = (RecyclerView) itemView.findViewById(R.id.replay_list);
            mReplayList.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    private OnRemarkClickListener mOnRemarkClickListener;

    public void setOnRemarkClickListener(OnRemarkClickListener onRemarkClickListener) {
        mOnRemarkClickListener = onRemarkClickListener;
    }

    public interface OnRemarkClickListener{
        void onRemarkClick(int pos,String parentId,String reName);
        void onDelRemark(int pos);
        void onDelReply(int pos,List<EssayCommentBean> childs,int child_pos);
    }
}
