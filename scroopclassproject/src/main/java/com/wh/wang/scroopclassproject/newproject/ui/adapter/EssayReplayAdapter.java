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
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayCommentBean;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/9/27.
 */

public class EssayReplayAdapter extends RecyclerView.Adapter<EssayReplayAdapter.EssayReplayViewHolder> {
    private final String mNickname;
    private String mUserId;
    private Context mContext;
    private List<EssayCommentBean> mList;
    private LayoutInflater mLayoutInflater;

    public EssayReplayAdapter(Context context, List<EssayCommentBean> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mNickname = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
    }

    @Override
    public EssayReplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EssayReplayViewHolder(mLayoutInflater.inflate(R.layout.item_video_replay,parent,false));
    }

    @Override
    public void onBindViewHolder(EssayReplayViewHolder holder, final int position) {
        final EssayCommentBean essayCommentBean = mList.get(position);
        String remark_name = subName(StringUtils.isEmpty(essayCommentBean.getNickname())?mNickname:essayCommentBean.getNickname())+" ";
        String content = remark_name+"回复 "+subName(essayCommentBean.getRe_name())+": "+essayCommentBean.getContent();
        SpannableString spanStr = new SpannableString(content);
        spanStr.setSpan(new ForegroundColorSpan(Color.parseColor("#16BC6E")), remark_name.length(), remark_name.length()+2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.mReplayContent.setText(spanStr);
        holder.mTime.setText(essayCommentBean.getShijian());
        if(essayCommentBean.getUser_id().equals(mUserId)){
            holder.mDelete.setVisibility(View.VISIBLE);
            holder.mDeleteIcon.setVisibility(View.VISIBLE);
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnEssayReplyClickListener!=null) {
                        mOnEssayReplyClickListener.onReplyDelClick(position,essayCommentBean.getId());
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
                if (mOnEssayReplyClickListener!=null) {
                    mOnEssayReplyClickListener.onEssayReplayClick(essayCommentBean.getNickname());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class EssayReplayViewHolder extends RecyclerView.ViewHolder{
        private TextView mReplayContent;
        private TextView mTime;
        private TextView mReplay;
        private ImageView mReplayIcon;
        private TextView mDelete;
        private ImageView mDeleteIcon;

        public EssayReplayViewHolder(View itemView) {
            super(itemView);

            mReplayContent = (TextView) itemView.findViewById(R.id.replay_content);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mReplay = (TextView) itemView.findViewById(R.id.replay);
            mReplayIcon = (ImageView) itemView.findViewById(R.id.replay_icon);
            mDelete = (TextView) itemView.findViewById(R.id.delete);
            mDeleteIcon = (ImageView) itemView.findViewById(R.id.delete_icon);
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

    private OnEssayReplyClickListener mOnEssayReplyClickListener;

    public void setOnEssayReplyClickListener(OnEssayReplyClickListener onEssayReplyClickListener) {
        mOnEssayReplyClickListener = onEssayReplyClickListener;
    }

    public interface OnEssayReplyClickListener{
        void onEssayReplayClick(String rename);
        void onReplyDelClick(int pos,String id);
    }

}
