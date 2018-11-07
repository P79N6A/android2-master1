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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkDetailsEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.WorkPresenter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by chdh on 2018/3/2.
 */

public class WorkRemarkAdapter extends RecyclerView.Adapter<WorkRemarkAdapter.WorkRemarkViewHolder> {

    private String mUser_id;
    private Context mContext;
    private List<WorkDetailsEntity.CommentBean> mCommentBeanList;
    private LayoutInflater mInflater;
    private WorkPresenter mWorkPresenter = new WorkPresenter();

    public WorkRemarkAdapter(Context context, List<WorkDetailsEntity.CommentBean> commentBeanList) {
        mContext = context;
        mCommentBeanList = commentBeanList;
        mInflater = LayoutInflater.from(context);

        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
    }

    @Override
    public WorkRemarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorkRemarkViewHolder(mInflater.inflate(R.layout.item_home_work,parent,false));
    }

    @Override
    public void onBindViewHolder(final WorkRemarkViewHolder holder, final int position) {
        if(holder!=null&&mCommentBeanList!=null&&mCommentBeanList.get(position)!=null){
            final WorkDetailsEntity.CommentBean commentBean = mCommentBeanList.get(position);
            holder.mItemWorkTime.setText(commentBean.getShijian());
            holder.mItemWorkContent.setText(commentBean.getContent());
            holder.mWorkName.setText(commentBean.getNickname());
            Glide.with(mContext).load(commentBean.getAvator()).centerCrop().into(holder.mWorkAvatar);
            if(commentBean.getIfrenzheng()==1){
                holder.mWorkAttestation.setVisibility(View.VISIBLE);
            }else{
                holder.mWorkAttestation.setVisibility(View.GONE);
            }
            if(commentBean.getUser_id().equals(mUser_id)){
                holder.mDelete.setVisibility(View.VISIBLE);
                holder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("是否删除此条评论?")
                                .setNegativeButton("取消",null)
                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.e("DH_COMMENT_ID",commentBean.getId()+"");
                                        mWorkPresenter.deleteWorkRemark(commentBean.getId(),commentBean.getParentid(), new OnResultListener() {
                                            @Override
                                            public void onSuccess(Object... value) {
                                                mCommentBeanList.remove(position);
                                                WorkRemarkAdapter.this.notifyDataSetChanged();
                                                Toast.makeText(MyApplication.mApplication, "成功删除", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFault(String error) {
                                                Log.e("DH_DELETE",error);
                                            }
                                        });
                                    }
                                });
                        Dialog d = builder.create();
                        d.show();
                    }
                });
            }else{
                holder.mDelete.setVisibility(View.GONE);
            }
            if(commentBean.getIfzhan()==1){
                holder.mLikeIcon.setImageResource(R.drawable.zuoye_ydz);
            }else{
                holder.mLikeIcon.setImageResource(R.drawable.zuoye_dz);
            }
            holder.mLikeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWorkPresenter.remarkLike(commentBean.getId(), mUser_id, new OnResultListener() {
                        @Override
                        public void onSuccess(Object... value) {
                            if(commentBean.getIfzhan()==1){
                                commentBean.setIfzhan(0);
                                holder.mLikeIcon.setImageResource(R.drawable.zuoye_dz);
                                commentBean.setUp_user(commentBean.getUp_user()-1);
                            }else{
                                commentBean.setIfzhan(1);
                                holder.mLikeIcon.setImageResource(R.drawable.zuoye_ydz);
                                commentBean.setUp_user(commentBean.getUp_user()+1);
                            }
                            holder.mLikeNum.setText(commentBean.getUp_user()+"");
                        }

                        @Override
                        public void onFault(String error) {

                        }
                    });
                }
            });
            holder.mReplayIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnReplayClickListener!=null){
                        mOnReplayClickListener.onReplayClick(commentBean.getChild(),commentBean.getId(),commentBean.getNickname());
                    }
                }
            });
            holder.mLikeNum.setText(commentBean.getUp_user()+"");
            if(commentBean.getChild()!=null&&commentBean.getChild().size()>0){
                holder.mReplayList.setVisibility(View.VISIBLE);
                holder.mReplayList.setLayoutManager(new LinearLayoutManager(mContext));
                holder.mReplayList.setHasFixedSize(true);
                holder.mReplayList.setNestedScrollingEnabled(false);
                WorkReplayAdapter workReplayAdapter = new WorkReplayAdapter(commentBean.getChild(),mContext);
                holder.mReplayList.setAdapter(workReplayAdapter);
                workReplayAdapter.setOnSecondReplayClickListener(new WorkReplayAdapter.OnSecondReplayClickListener() {
                    @Override
                    public void onReplayClick(String p_id, String r_name) {
                        if(mOnReplayClickListener!=null){
                            mOnReplayClickListener.onReplayClick(commentBean.getChild(),p_id,r_name);
                        }
                    }
                });
                workReplayAdapter.setOnSecondDeleteClickListener(new WorkReplayAdapter.OnSecondDeleteClickListener() {
                    @Override
                    public void onSecondDelClick(final int pos, final String id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("是否删除此条回复?")
                                .setNegativeButton("取消",null)
                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        mWorkPresenter.deleteWorkRemark(id,commentBean.getChild().get(pos).getParentid(), new OnResultListener() {
                                            @Override
                                            public void onSuccess(Object... value) {
                                                commentBean.getChild().remove(pos);
                                                WorkRemarkAdapter.this.notifyDataSetChanged();
                                                Toast.makeText(MyApplication.mApplication, "成功删除", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFault(String error) {
                                                Log.e("DH_DELETE",error);
                                            }
                                        });
                                    }
                                });
                        Dialog d = builder.create();
                        d.show();
                    }
                });
            }else{
                holder.mReplayList.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCommentBeanList!=null?mCommentBeanList.size():0;
    }

    class WorkRemarkViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mWorkAvatar;
        private TextView mWorkName;
        private TextView mItemWorkContent;
        private TextView mItemWorkTime;
        private TextView mReplayNum;
        private ImageView mReplayIcon;
        private TextView mLikeNum;
        private ImageView mLikeIcon;
        private RecyclerView mReplayList;
        private ImageView mDelete;
        private ImageView mWorkAttestation;

        public WorkRemarkViewHolder(View itemView) {
            super(itemView);
            mWorkAvatar = (RoundedImageView) itemView.findViewById(R.id.work_avatar);
            mWorkName = (TextView) itemView.findViewById(R.id.work_name);
            mItemWorkContent = (TextView) itemView.findViewById(R.id.item_work_content);
            mItemWorkTime = (TextView) itemView.findViewById(R.id.item_work_time);
            mReplayNum = (TextView) itemView.findViewById(R.id.replay_num);
            mReplayIcon = (ImageView) itemView.findViewById(R.id.replay_icon);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mLikeIcon = (ImageView) itemView.findViewById(R.id.like_icon);
            mReplayList = (RecyclerView) itemView.findViewById(R.id.replay_list);
            mDelete = (ImageView) itemView.findViewById(R.id.delete);
            mWorkAttestation = (ImageView) itemView.findViewById(R.id.work_attestation);
        }
    }

    private OnReplayClickListener mOnReplayClickListener;

    public void setOnReplayClickListener(OnReplayClickListener onReplayClickListener) {
        mOnReplayClickListener = onReplayClickListener;
    }

    public interface OnReplayClickListener{
        void onReplayClick(List<WorkDetailsEntity.CommentBean.ChildBean> replyBeanList,String p_id,String r_name);
    }

    private OnRemarkLikeClickListener mOnRemarkLikeClickListener;

    public void setOnRemarkLikeClickListener(OnRemarkLikeClickListener onRemarkLikeClickListener) {
        mOnRemarkLikeClickListener = onRemarkLikeClickListener;
    }

    public interface OnRemarkLikeClickListener{
        void onRemarkLikeClick(int pos,String id,ImageView img,TextView textView);
    }
}
