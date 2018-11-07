package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRemarkBean;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/20.
 */

public class SGRemarkAdapter extends RecyclerView.Adapter<SGRemarkAdapter.RemarkViewHolder> {
    private Context mContext;
    private List<SGRemarkBean> mRemarkList;
    private LayoutInflater mLayoutInflater;
    private String mType;
    private String mReName = "";
    private String mUserId;
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();

    public SGRemarkAdapter(Context context, List<SGRemarkBean> remarkList,String type,String name) {
        mContext = context;
        mRemarkList = remarkList;
        mType = type;
        mLayoutInflater = LayoutInflater.from(context);
        mReName = name;
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"user_id","");
    }

    @Override
    public RemarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemarkViewHolder(mLayoutInflater.inflate(R.layout.item_sg_remark,parent,false));
    }

    @Override
    public void onBindViewHolder(final RemarkViewHolder holder, final int position) {
        final SGRemarkBean sgRemarkBean = mRemarkList.get(position);
        Glide.with(MyApplication.mApplication).load(sgRemarkBean.getAvator()).centerCrop().into(holder.mAvatar);
        if(mUserId.equals(sgRemarkBean.getUser_id())){
            holder.mDelIcon.setVisibility(View.VISIBLE);
            holder.mDelTv.setVisibility(View.VISIBLE);
            holder.mDelTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("是否删除?")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mOnRemarkItemClickListener!=null) {
                                        mOnRemarkItemClickListener.onDelClick(sgRemarkBean.getId(),position);
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    Dialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                }
            });
        }else{
            holder.mDelIcon.setVisibility(View.GONE);
            holder.mDelTv.setVisibility(View.GONE);
            holder.mDelTv.setOnClickListener(null);
        }
        holder.mContent.setText(sgRemarkBean.getContent());
        holder.mTime.setText(sgRemarkBean.getShijian());
        if("0".equals(mType)){
            holder.mName.setText(sgRemarkBean.getNickname());
            holder.mRemarkNum.setText(StringUtils.isEmpty(sgRemarkBean.getNum())?"0":sgRemarkBean.getNum());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRemarkItemClickListener!=null) {
                        mOnRemarkItemClickListener.onRemarkClick(sgRemarkBean.getId());
                    }
                }
            });
            holder.mRemarkIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRemarkItemClickListener!=null) {
                        mOnRemarkItemClickListener.onRemarkClick(sgRemarkBean.getId());
                    }
                }
            });
        }else if("1".equals(mType)){
            if(mReName!=null&&mReName.equals(sgRemarkBean.getRe_name())){
                holder.mName.setText(sgRemarkBean.getNickname());
            }else{
                holder.mName.setText(sgRemarkBean.getNickname() +" 回复 "+sgRemarkBean.getRe_name());
            }

            holder.mRemarkNum.setText("回复");
            holder.mRemarkNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnReplayClickListener!=null) {
                        mOnReplayClickListener.onReplayClick(sgRemarkBean.getParentid(),sgRemarkBean.getNickname());
                    }
                }
            });
            holder.mRemarkIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnReplayClickListener!=null) {
                        mOnReplayClickListener.onReplayClick(sgRemarkBean.getParentid(),sgRemarkBean.getNickname());
                    }
                }
            });
        }
        if ("0".equals(sgRemarkBean.getThumbs_up_status())) {
            holder.mLikeIcon.setImageResource(R.drawable.dianzankongxin);
        }else{
            holder.mLikeIcon.setImageResource(R.drawable.dianzanicon);
        }

        holder.mLikeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DH_LIKE","user_id:"+mUserId+"  id:"+sgRemarkBean.getId());
                mStudyGroupPresenter.sgRemarkLike(mUserId, sgRemarkBean.getId(), new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        if("0".equals(sgRemarkBean.getThumbs_up_status())){
                            sgRemarkBean.setThumbs_up_status("1");
                            holder.mLikeIcon.setImageResource(R.drawable.dianzanicon);

                            sgRemarkBean.setThumbs_up_num(String.valueOf(Integer.parseInt(sgRemarkBean.getThumbs_up_num())+1));
                        }else if("1".equals(sgRemarkBean.getThumbs_up_status())){
                            sgRemarkBean.setThumbs_up_status("0");
                            holder.mLikeIcon.setImageResource(R.drawable.dianzankongxin);
                            if(Integer.parseInt(sgRemarkBean.getThumbs_up_num())>0){
                                sgRemarkBean.setThumbs_up_num(String.valueOf(Integer.parseInt(sgRemarkBean.getThumbs_up_num())-1));
                            }

                        }
                        holder.mLikeNum.setText(sgRemarkBean.getThumbs_up_num());
                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
            }
        });
        holder.mLikeNum.setText(sgRemarkBean.getThumbs_up_num());

    }

    @Override
    public int getItemCount() {
        return mRemarkList!=null?mRemarkList.size():0;
    }

    class RemarkViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mTime;
        private TextView mContent;
        private TextView mLikeNum;
        private ImageView mLikeIcon;
        private TextView mRemarkNum;
        private ImageView mRemarkIcon;
        private TextView mDelTv;
        private ImageView mDelIcon;


        public RemarkViewHolder(View itemView) {
            super(itemView);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mLikeIcon = (ImageView) itemView.findViewById(R.id.like_icon);
            mRemarkNum = (TextView) itemView.findViewById(R.id.remark_num);
            mRemarkIcon = (ImageView) itemView.findViewById(R.id.remark_icon);
            mDelTv = (TextView) itemView.findViewById(R.id.del_tv);
            mDelIcon = (ImageView) itemView.findViewById(R.id.del_icon);
        }
    }

    private OnRemarkItemClickListener mOnRemarkItemClickListener;

    public void setOnRemarkItemClickListener(OnRemarkItemClickListener onRemarkItemClickListener) {
        mOnRemarkItemClickListener = onRemarkItemClickListener;
    }

    public interface OnRemarkItemClickListener{
        void onRemarkClick(String id);
        void onDelClick(String id,int pos);
    }


    private OnReplayClickListener mOnReplayClickListener;

    public void setOnReplayClickListener(OnReplayClickListener onReplayClickListener) {
        mOnReplayClickListener = onReplayClickListener;
    }

    public interface OnReplayClickListener{
        void onReplayClick(String parentid,String rename);
    }
}
