package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.callback.OnWorkClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkInfoEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by chdh on 2018/3/1.
 */

public class AllWorkAdapter extends RecyclerView.Adapter<AllWorkAdapter.AllWorkViewHolder> {

    private Context mContext;
    private List<WorkInfoEntity.AllBean> mAllBeanList;
    private LayoutInflater mInflater;

    public AllWorkAdapter(Context context, List<WorkInfoEntity.AllBean> allBeanList) {
        mContext = context;
        mAllBeanList = allBeanList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public AllWorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllWorkViewHolder(mInflater.inflate(R.layout.item_home_work,parent,false));
    }

    @Override
    public void onBindViewHolder(final AllWorkViewHolder holder, final int position) {
        if(mAllBeanList!=null&&mAllBeanList.get(position)!=null&&holder!=null){
            final WorkInfoEntity.AllBean allBean = mAllBeanList.get(position);
            holder.mItemWorkContent.setText(allBean.getPinglun());
            holder.mItemWorkTime.setText(allBean.getShijian());
            holder.mWorkName.setText(allBean.getUser_name());
            Glide.with(mContext).load(allBean.getUser_head()).centerCrop().into(holder.mWorkAvatar);
            holder.mLikeNum.setText(allBean.getZhan_sum());
            holder.mReplayNum.setText(allBean.getComment_sum());
            if(allBean.getIs_youxiu()==1){
                holder.mTop.setVisibility(View.VISIBLE);
                holder.mTop.setText("优秀作业");
            }else{
                holder.mTop.setVisibility(View.GONE);
            }
            if(allBean.getIfrenzheng()==1){
                holder.mWorkAttestation.setVisibility(View.VISIBLE);
            }else{
                holder.mWorkAttestation.setVisibility(View.GONE);
            }
            if(allBean.getIfzhan()==1){
                holder.mLikeIcon.setImageResource(R.drawable.zuoye_ydz);
            }else{
                holder.mLikeIcon.setImageResource(R.drawable.zuoye_dz);
            }
            holder.mLikeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnWorkClickListener!=null){
                        mOnWorkClickListener.onWorkLikeClick(position,allBean.getId(),allBean.getIfzhan(),holder.mLikeIcon,holder.mLikeNum,0);
                    }
                }
            });
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnWorkClickListener!=null){
                        mOnWorkClickListener.onWorkItemClick(position,allBean.getId());
                    }
                }
            });
            holder.mReplayIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mOnWorkClickListener!=null){
                        mOnWorkClickListener.onWorkRemarkClick(position,allBean.getId());
                    }
                }
            });

            if(allBean.getIfmy()==1&&position==0){
                holder.mMyWork.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mLine.getLayoutParams();
                layoutParams.height = DisplayUtil.dip2px(mContext,20);
                holder.mLine.setLayoutParams(layoutParams);
            }else{
                holder.mMyWork.setVisibility(View.GONE);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mLine.getLayoutParams();
                layoutParams.height = DisplayUtil.dip2px(mContext, (float) 0.5);
                holder.mLine.setLayoutParams(layoutParams);
            }
            if(allBean.getImg_id()!=null&&allBean.getImg_id().size()>0){
                holder.mItemWorkImg.setVisibility(View.VISIBLE);
                int screenWidth = ScreenUtils.getScreenWidth(mContext);
                int imgWidth = 0;
                if(allBean.getImg_id().size()<3){
                    imgWidth = (screenWidth- DisplayUtil.dip2px(mContext,15+15+3))/2;
                }else{
                    imgWidth = (screenWidth- DisplayUtil.dip2px(mContext,15+15+6))/3;
                }
                for (int i = 0; i < (allBean.getImg_id().size()>3?3:allBean.getImg_id().size()); i++) {
                    ImageView workImg = (ImageView) mInflater.inflate(R.layout.item_work_img, holder.mItemWorkImg, false);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) workImg.getLayoutParams();
                    if(i!=allBean.getImg_id().size()-1){
                        layoutParams.setMargins(0, 0, DisplayUtil.dip2px(mContext,3), 0);
                    }
                    layoutParams.width = imgWidth;
                    layoutParams.height = imgWidth;
                    workImg.setLayoutParams(layoutParams);
                    holder.mItemWorkImg.addView(workImg);
                    Glide.with(mContext).load(allBean.getImg_id().get(i).getUrl()).centerCrop().into(workImg);
                }
            }else{
                holder.mItemWorkImg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAllBeanList!=null?mAllBeanList.size():0;
    }

    class AllWorkViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mMenu;
        private RoundedImageView mWorkAvatar;
        private TextView mWorkName;
        private ImageView mWorkAttestation;
        private TextView mItemWorkContent;
        private TextView mItemWorkTime;
        private TextView mReplayNum;
        private TextView mLikeNum;
        private LinearLayout mItemWorkImg;
        private LinearLayout mMyWork;
        private View mLine;
        private ImageView mReplayIcon;
        private ImageView mLikeIcon;
        private View mView;
        private TextView mTop;
        public AllWorkViewHolder(View itemView) {
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
            mMyWork = (LinearLayout) itemView.findViewById(R.id.my_work);
            mLine = (View) itemView.findViewById(R.id.line);
            mReplayIcon = (ImageView) itemView.findViewById(R.id.replay_icon);
            mLikeIcon = (ImageView) itemView.findViewById(R.id.like_icon);
            mTop = (TextView) itemView.findViewById(R.id.top);
            mMenu = (RelativeLayout) itemView.findViewById(R.id.menu);
            mMenu.setOnClickListener(null);
        }
    }

    private OnWorkClickListener mOnWorkClickListener;

    public void setOnWorkClickListener(OnWorkClickListener onWorkClickListener) {
        mOnWorkClickListener = onWorkClickListener;
    }
    //    private OnWorkItemClickListener mOnWorkItemClickListener;
//
//    public void setOnWorkItemClickListener(OnWorkItemClickListener onWorkItemClickListener) {
//        mOnWorkItemClickListener = onWorkItemClickListener;
//    }
//
//    public interface OnWorkItemClickListener{
//        void onWorkItemClick(int pos,String id);
//    }
//
//    private OnWorkLikeClickListener mOnWorkLikeClickListener;
//
//    public void setOnWorkLikeClickListener(OnWorkLikeClickListener onWorkLikeClickListener) {
//        mOnWorkLikeClickListener = onWorkLikeClickListener;
//    }
//
//    public interface OnWorkLikeClickListener{
//        void onWorkLikeClick(int pos,String id,int like,ImageView likeImg);
//    }
}
