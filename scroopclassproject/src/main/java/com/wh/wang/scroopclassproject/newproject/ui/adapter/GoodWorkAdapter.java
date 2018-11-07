package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.callback.OnWorkClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkInfoEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by chdh on 2018/3/1.
 */

public class GoodWorkAdapter extends RecyclerView.Adapter<GoodWorkAdapter.WelcomeWorkViewHolder> {
    private Context mContext;
    private List<WorkInfoEntity.InfoBean.YouxiuBean> mGoodBeanList;
    private LayoutInflater mInflater;

    public GoodWorkAdapter(Context context, List<WorkInfoEntity.InfoBean.YouxiuBean> goodBeanList) {
        mContext = context;
        mGoodBeanList = goodBeanList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public WelcomeWorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WelcomeWorkViewHolder(mInflater.inflate(R.layout.item_home_work, parent, false));
    }

    @Override
    public void onBindViewHolder(final WelcomeWorkViewHolder holder, final int position) {
        if (mGoodBeanList != null && mGoodBeanList.get(position) != null && holder != null) {
            final WorkInfoEntity.InfoBean.YouxiuBean youxiuBean = mGoodBeanList.get(position);
            holder.mWorkName.setText(youxiuBean.getUser_name());
            Glide.with(MyApplication.mApplication).load(youxiuBean.getUser_head()).centerCrop().into(holder.mWorkAvatar);
            holder.mItemWorkTime.setText(youxiuBean.getShijian());
            holder.mLikeNum.setText(youxiuBean.getZhan_sum());
            holder.mReplayNum.setText(youxiuBean.getComment_sum());
            holder.mItemWorkContent.setText(youxiuBean.getPinglun());
            holder.mTop.setVisibility(View.VISIBLE);
            holder.mTop.setText("优秀作业");
            if (youxiuBean.getIfrenzheng() == 1) {
                holder.mWorkAttestation.setVisibility(View.VISIBLE);
            } else {
                holder.mWorkAttestation.setVisibility(View.GONE);
            }
            if (youxiuBean.getIfzhan() == 1) {
                holder.mLikeIcon.setImageResource(R.drawable.zuoye_ydz);
            } else {
                holder.mLikeIcon.setImageResource(R.drawable.zuoye_dz);
            }
            holder.mLikeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnWorkClickListener != null) {
                        mOnWorkClickListener.onWorkLikeClick(position, youxiuBean.getId(), youxiuBean.getIfzhan(), holder.mLikeIcon, holder.mLikeNum, -1);
                    }
                }
            });
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnWorkClickListener != null) {
                        mOnWorkClickListener.onWorkItemClick(position, youxiuBean.getId());
                    }
                }
            });
            holder.mReplayIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnWorkClickListener != null) {
                        mOnWorkClickListener.onWorkRemarkClick(position, youxiuBean.getId());
                    }
                }
            });

            if (youxiuBean.getImg_id() != null && youxiuBean.getImg_id().size() > 0) {
                if (holder.mItemWorkImg.getChildCount() > 0) {
                    holder.mItemWorkImg.removeAllViews();
                }
                holder.mItemWorkImg.setVisibility(View.VISIBLE);
                int screenWidth = ScreenUtils.getScreenWidth(mContext);
                int imgWidth = 0;
                if (youxiuBean.getImg_id().size() < 3) {
                    imgWidth = (screenWidth - DisplayUtil.dip2px(mContext, 15 + 15 + 3)) / 2;
                } else {
                    imgWidth = (screenWidth - DisplayUtil.dip2px(mContext, 15 + 15 + 6)) / 3;
                }
                for (int i = 0; i < (youxiuBean.getImg_id().size() > 3 ? 3 : youxiuBean.getImg_id().size()); i++) {
                    ImageView workImg = (ImageView) mInflater.inflate(R.layout.item_work_img, holder.mItemWorkImg, false);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) workImg.getLayoutParams();
                    if (i != youxiuBean.getImg_id().size() - 1) {
                        layoutParams.setMargins(0, 0, DisplayUtil.dip2px(mContext, 3), 0);
                    }
                    layoutParams.width = imgWidth;
                    layoutParams.height = imgWidth;
                    workImg.setLayoutParams(layoutParams);
                    holder.mItemWorkImg.addView(workImg);
                    Glide.with(mContext).load(youxiuBean.getImg_id().get(i).getUrl()).centerCrop().into(workImg);
                }
            } else {
                holder.mItemWorkImg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mGoodBeanList != null ? mGoodBeanList.size() : 0;
    }

    class WelcomeWorkViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mWorkAvatar;
        private TextView mWorkName;
        private ImageView mWorkAttestation;
        private TextView mItemWorkContent;
        private TextView mItemWorkTime;
        private TextView mReplayNum;
        private TextView mLikeNum;
        private LinearLayout mItemWorkImg;
        private TextView mTop;
        private ImageView mReplayIcon;
        private ImageView mLikeIcon;
        private View mView;

        public WelcomeWorkViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mWorkAvatar = (RoundedImageView) itemView.findViewById(R.id.work_avatar);
            mWorkName = (TextView) itemView.findViewById(R.id.work_name);
            mWorkAttestation = (ImageView) itemView.findViewById(R.id.work_attestation);
            mItemWorkContent = (TextView) itemView.findViewById(R.id.item_work_content);
            mItemWorkTime = (TextView) itemView.findViewById(R.id.item_work_time);
            mReplayNum = (TextView) itemView.findViewById(R.id.replay_num);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mItemWorkImg = (LinearLayout) itemView.findViewById(R.id.item_work_img);
            mTop = (TextView) itemView.findViewById(R.id.top);
            mReplayIcon = (ImageView) itemView.findViewById(R.id.replay_icon);
            mLikeIcon = (ImageView) itemView.findViewById(R.id.like_icon);
        }
    }

    private OnWorkClickListener mOnWorkClickListener;

    public void setOnWorkClickListener(OnWorkClickListener onWorkClickListener) {
        mOnWorkClickListener = onWorkClickListener;
    }
}
