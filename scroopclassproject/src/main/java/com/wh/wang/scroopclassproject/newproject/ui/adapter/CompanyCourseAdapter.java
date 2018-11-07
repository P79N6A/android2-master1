package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyStateEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by chdh on 2018/1/19.
 */

public class CompanyCourseAdapter extends RecyclerView.Adapter<CompanyCourseAdapter.CourseViewHolder> {
    private List<CompanyStateEntity.InfoBean.ShareVideoBean> mList;
    private int mStatus;
    private LayoutInflater mInflater;
    private Context mContext;
    public CompanyCourseAdapter(List<CompanyStateEntity.InfoBean.ShareVideoBean> list, int status, Context context) {
        mList = list;
        mStatus = status;
        mContext = context;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseViewHolder(mInflater.inflate(R.layout.item_company_course,parent,false));
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, final int position) {
        if(holder!=null&&mList!=null&&mList.get(position)!=null){
            final CompanyStateEntity.InfoBean.ShareVideoBean shareVideoBean = mList.get(position);
            if(shareVideoBean.getShare_time()!=null){
                if(position==0){
                    holder.mTimeFrag.setVisibility(View.VISIBLE);
                }else{
                    if(shareVideoBean.getShare_time().equals(mList.get((position-1)).getShare_time())){
                        holder.mTimeFrag.setVisibility(View.GONE);
                    }else{
                        holder.mTimeFrag.setVisibility(View.VISIBLE);
                    }
                }
            }else{
                holder.mTimeFrag.setVisibility(View.GONE);
            }
            if(StringUtils.isNotEmpty(shareVideoBean.getImg())){
                Glide.with(MyApplication.mApplication).load(shareVideoBean.getImg()).centerCrop().into(holder.mCourseImg);
            }else{
                holder.mCourseImg.setImageResource(R.drawable.qiye_zwpic);
            }

            holder.mTitle.setText(shareVideoBean.getTitle());
            holder.mTime.setText(shareVideoBean.getShare_time());
            if(mStatus==2){
                if("100%".equals(shareVideoBean.getNum_per())){
                    holder.mLearnedNum.setVisibility(View.INVISIBLE);
                    holder.mLearnedTv.setText("已学完");
                }else{
                    holder.mLearnedNum.setVisibility(View.VISIBLE);
                    holder.mLearnedNum.setText(shareVideoBean.getNum_per());
                    holder.mLearnedTv.setText("已完成");
                }
                holder.mRankTv.setVisibility(View.VISIBLE);
                holder.mLearningTv.setVisibility(View.GONE);
                if(shareVideoBean.getUn_rank()!=null&&!"0".equals(shareVideoBean.getUn_rank())){
                    holder.mLearningNum.setVisibility(View.VISIBLE);
                    holder.mRankTv.setText("当前排名");
                    holder.mLearningNum.setText(shareVideoBean.getUn_rank());
                }else{
                    holder.mLearningNum.setVisibility(View.GONE);
                    holder.mRankTv.setText("暂无排名");
                }
            }else{
                holder.mLearnedTv.setText("人已学完");
                holder.mRankTv.setVisibility(View.GONE);
                holder.mLearningTv.setVisibility(View.VISIBLE);
                holder.mRemind.setVisibility(View.VISIBLE);
                holder.mLearnedNum.setText(shareVideoBean.getNum_per());
                holder.mLearningNum.setText(shareVideoBean.getUn_rank());
            }
            holder.mCourseInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if("4".equals(shareVideoBean.getType())){
                        intent = new Intent(mContext, SumUpActivity.class);
                        intent.putExtra("type",shareVideoBean.getType());
                        intent.putExtra("id",shareVideoBean.getId());

                    }else{
                        intent = new Intent(mContext, NewVideoInfoActivity.class);
                        intent.putExtra("type",shareVideoBean.getType());
                        intent.putExtra("id",shareVideoBean.getId());
                        mContext.startActivity(intent);
                    }
                }
            });
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnCompanyCourseClickListener!=null){
                        mOnCompanyCourseClickListener.onCourseClick(shareVideoBean.getId(),position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout mTimeFrag;
        private TextView mTime;
        private ImageView mCourseImg;
        private TextView mTitle;
        private TextView mRemind;
        private TextView mLearnedNum;
        private TextView mLearnedTv;
        private TextView mLearningNum;
        private TextView mLearningTv;
        private RelativeLayout mItem;
        private TextView mRankTv;
        private RelativeLayout mCourseInfo;

        public CourseViewHolder(View itemView) {
            super(itemView);
            mTimeFrag = (FrameLayout) itemView.findViewById(R.id.time_frag);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mCourseImg = (ImageView) itemView.findViewById(R.id.course_img);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mRemind = (TextView) itemView.findViewById(R.id.remind);
            mLearnedNum = (TextView) itemView.findViewById(R.id.learned_num);
            mLearnedTv = (TextView) itemView.findViewById(R.id.learned_tv);
            mLearningNum = (TextView) itemView.findViewById(R.id.learning_num);
            mLearningTv = (TextView) itemView.findViewById(R.id.learning_tv);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
            mRankTv = (TextView) itemView.findViewById(R.id.rank_tv);

            mCourseInfo = (RelativeLayout)itemView. findViewById(R.id.course_info);

        }
    }

    private OnCompanyCourseClickListener mOnCompanyCourseClickListener;

    public void setOnCompanyCourseClickListener(OnCompanyCourseClickListener onCompanyCourseClickListener) {
        mOnCompanyCourseClickListener = onCompanyCourseClickListener;
    }

    public interface OnCompanyCourseClickListener{
        void onCourseClick(String id,int pos);
    }
}
