package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.callback.OnWorkClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkInfoEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by chdh on 2018/3/1.
 */

public class WelcomeWorkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<WorkInfoEntity.TopBean> mTopBeanList;
    private LayoutInflater mInflater;
    private boolean isShowAll = false;

    public WelcomeWorkAdapter(Context context, List<WorkInfoEntity.TopBean> topBeanList) {
        mContext = context;
        mTopBeanList = topBeanList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            return new WelcomeWorkViewHolder(mInflater.inflate(R.layout.item_home_work,parent,false));
        }else{
            return new WelcomeMoreViewHolder(mInflater.inflate(R.layout.item_work_more,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(mTopBeanList!=null&&mTopBeanList.get(position)!=null&&holder!=null){

            if(holder instanceof WelcomeWorkViewHolder ){
                final WelcomeWorkViewHolder welcomeWorkViewHolder = (WelcomeWorkViewHolder) holder;
                final WorkInfoEntity.TopBean topBean = mTopBeanList.get(position);
                welcomeWorkViewHolder.mWorkName.setText(topBean.getUser_name());
                Glide.with(MyApplication.mApplication).load(topBean.getUser_head()).centerCrop().into(welcomeWorkViewHolder.mWorkAvatar);
                welcomeWorkViewHolder.mItemWorkTime.setText(topBean.getShijian());
                welcomeWorkViewHolder.mLikeNum.setText(topBean.getZhan_sum());
                welcomeWorkViewHolder.mReplayNum.setText(topBean.getComment_sum());
                welcomeWorkViewHolder.mItemWorkContent.setText(topBean.getPinglun());
                if(topBean.getIftop()==1){
                    welcomeWorkViewHolder.mTop.setVisibility(View.VISIBLE);
                }else{
                    if(topBean.getIs_youxiu()==1){
                        welcomeWorkViewHolder.mTop.setVisibility(View.VISIBLE);
                        welcomeWorkViewHolder.mTop.setText("优秀作业");
                    }else{
                        welcomeWorkViewHolder.mTop.setVisibility(View.GONE);
                    }
                }
                if(topBean.getIfrenzheng()==1){
                    welcomeWorkViewHolder.mWorkAttestation.setVisibility(View.VISIBLE);
                }else{
                    welcomeWorkViewHolder.mWorkAttestation.setVisibility(View.GONE);
                }
                if(topBean.getIfzhan()==1){
                    welcomeWorkViewHolder.mLikeIcon.setImageResource(R.drawable.zuoye_ydz);
                }else{
                    welcomeWorkViewHolder.mLikeIcon.setImageResource(R.drawable.zuoye_dz);
                }
                welcomeWorkViewHolder.mLikeIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnWorkClickListener!=null){
                            mOnWorkClickListener.onWorkLikeClick(position,topBean.getId(),topBean.getIfzhan(),welcomeWorkViewHolder.mLikeIcon,welcomeWorkViewHolder.mLikeNum,1);
                        }
                    }
                });
                welcomeWorkViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnWorkClickListener!=null){
                            mOnWorkClickListener.onWorkItemClick(position,topBean.getId());
                        }
                    }
                });
                welcomeWorkViewHolder.mReplayIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnWorkClickListener!=null){
                            mOnWorkClickListener.onWorkRemarkClick(position,topBean.getId());
                        }
                    }
                });

                if(topBean.getImg_id()!=null&&topBean.getImg_id().size()>0){
                    if(welcomeWorkViewHolder.mItemWorkImg.getChildCount()>0){
                        welcomeWorkViewHolder.mItemWorkImg.removeAllViews();
                    }
                    welcomeWorkViewHolder.mItemWorkImg.setVisibility(View.VISIBLE);
                    int screenWidth = ScreenUtils.getScreenWidth(mContext);
                    int imgWidth = 0;
                    if(topBean.getImg_id().size()<3){
                        imgWidth = (screenWidth- DisplayUtil.dip2px(mContext,15+15+3))/2;
                    }else{
                        imgWidth = (screenWidth- DisplayUtil.dip2px(mContext,15+15+6))/3;
                    }
                    for (int i = 0; i < (topBean.getImg_id().size()>3?3:topBean.getImg_id().size()); i++) {
                        ImageView workImg = (ImageView) mInflater.inflate(R.layout.item_work_img, welcomeWorkViewHolder.mItemWorkImg, false);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) workImg.getLayoutParams();
                        if(i!=topBean.getImg_id().size()-1){
                            layoutParams.setMargins(0, 0, DisplayUtil.dip2px(mContext,3), 0);
                        }
                        Log.e("DH_imgWidth",imgWidth+"");
                        layoutParams.width = imgWidth;
                        layoutParams.height = imgWidth;
                        workImg.setLayoutParams(layoutParams);
                        welcomeWorkViewHolder.mItemWorkImg.addView(workImg);
                        Glide.with(mContext).load(topBean.getImg_id().get(i).getUrl()).centerCrop().into(workImg);
                    }
                }else{
                    welcomeWorkViewHolder.mItemWorkImg.setVisibility(View.GONE);
                }
            }else if(holder instanceof WelcomeMoreViewHolder ){
                WelcomeMoreViewHolder welcomeMoreViewHolder = (WelcomeMoreViewHolder) holder;
                welcomeMoreViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShowAll = true;
                        WelcomeWorkAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mTopBeanList==null){
            return 0;
        }
        if(mTopBeanList.size()>2){
            if(!isShowAll){
                return 3;
            }else{
                return mTopBeanList.size();
            }
        }else{
            return mTopBeanList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==2&&mTopBeanList.size()>2&&!isShowAll){
            return 0;
        }
        return 1;
    }



    class WelcomeMoreViewHolder extends RecyclerView.ViewHolder{
        private TextView mMore;

        public WelcomeMoreViewHolder(View itemView) {
            super(itemView);
            mMore = (TextView) itemView.findViewById(R.id.more);
        }
    }
    class WelcomeWorkViewHolder extends RecyclerView.ViewHolder{
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
        private RelativeLayout mMenu;

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
