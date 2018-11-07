package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGHome2_0Entity;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/9.
 */

public class SGHomeCourse_2_0_Adapter extends RecyclerView.Adapter<SGHomeCourse_2_0_Adapter.HomeCourseViewHolder> {
    private Context mContext;
    private List<SGHome2_0Entity.InfoBean.ListBean.HotBean> mSGCourseList;
    private LayoutInflater mLayoutInflater;

    public SGHomeCourse_2_0_Adapter(Context context, List<SGHome2_0Entity.InfoBean.ListBean.HotBean> SGCourseList) {
        mContext = context;
        mSGCourseList = SGCourseList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public HomeCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeCourseViewHolder(mLayoutInflater.inflate(R.layout.item_study_group_2_0,parent,false));
    }

    @Override
    public void onBindViewHolder(HomeCourseViewHolder holder, final int position) {
        final SGHome2_0Entity.InfoBean.ListBean.HotBean bean = mSGCourseList.get(position);
        holder.mNumCourse.setText("已报名人数："+bean.getBaoming()+"/"+bean.getMaxsum());
        holder.mCateCourse.setText("共"+bean.getCate_sum()+"小节");
        Glide.with(mContext).load(bean.getImg()).centerCrop().into(holder.mImgCourse);
        holder.mTitleCourse.setText(bean.getTitle());
        if("1".equals(bean.getType())){
            holder.mTiaozhanIcon.setVisibility(View.VISIBLE);
            holder.mBonusPool.setVisibility(View.VISIBLE);
            holder.mBonusPool.setText("奖金："+bean.getBonus_rang()+"元");
        }else{
            holder.mTiaozhanIcon.setVisibility(View.GONE);
            holder.mBonusPool.setVisibility(View.GONE);
        }
        holder.mTime.setText("时间:"+bean.getStart_shijian());

        int baoNum = 0;
        int max = 0;
        try {
            baoNum = Integer.parseInt(bean.getBaoming());
        }catch (Exception e){
            baoNum = 0;
        }
        try {
            max = Integer.parseInt(bean.getMaxsum());
        }catch (Exception e){
            max = 0;
        }

        if("1".equals(bean.getIs_baoming())){
            holder.mImmJoin.setText("已报名");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnJoinItemClickListener!=null){
                        mOnJoinItemClickListener.onJoinClick(bean.getId(),bean.getRelative_video(),bean.getIs_baoming(),bean.getType());
//                        mOnJoinItemClickListener.onJoinClick();
                    }
                }
            });
        }else{
            if(baoNum>=max){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("该小组已满员，您可选择未满员的学习小组哦")
                                .setPositiveButton("确定",null);
                        Dialog dialog = builder.create();
                        dialog.show();
                    }
                });
                holder.mImmJoin.setText("已报满");
            }else{
                holder.mImmJoin.setText("加入");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnJoinItemClickListener!=null){
                            mOnJoinItemClickListener.onJoinClick(bean.getId(),bean.getRelative_video(),bean.getIs_baoming(),bean.getType());
//                            mOnJoinItemClickListener.onJoinClick();
                        }
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return mSGCourseList!=null?mSGCourseList.size():0;
    }

    class HomeCourseViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mImgCourse;
        private TextView mTitleCourse;
        private TextView mNumCourse;
        private TextView mCateCourse;
        private TextView mImmJoin;
        private TextView mBonusPool;
        private TextView mTime;
        private ImageView mTiaozhanIcon;


        public HomeCourseViewHolder(View itemView) {
            super(itemView);

            mImgCourse = (RoundedImageView) itemView.findViewById(R.id.img_course);
            mTitleCourse = (TextView) itemView.findViewById(R.id.title_course);
            mNumCourse = (TextView) itemView.findViewById(R.id.num_course);
            mCateCourse = (TextView) itemView.findViewById(R.id.cate_course);
            mImmJoin = (TextView) itemView.findViewById(R.id.imm_join);
            mBonusPool = (TextView) itemView.findViewById(R.id.bonus_pool);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mTiaozhanIcon = (ImageView) itemView.findViewById(R.id.tiaozhan_icon);
        }
    }

    private OnJoinItemClickListener mOnJoinItemClickListener;

    public void setOnJoinItemClickListener(OnJoinItemClickListener onJoinItemClickListener) {
        mOnJoinItemClickListener = onJoinItemClickListener;
    }

    public interface OnJoinItemClickListener{
        void onJoinClick(String id,String vid,String isbao,String type);
    }
}
