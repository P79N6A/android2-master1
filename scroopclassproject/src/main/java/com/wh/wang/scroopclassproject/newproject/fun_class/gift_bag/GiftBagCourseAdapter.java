package com.wh.wang.scroopclassproject.newproject.fun_class.gift_bag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/9/17.
 */

public class GiftBagCourseAdapter extends RecyclerView.Adapter<GiftBagCourseAdapter.GiftCourseViewHolder> {
    private Context mContext;
    private List<GiftBagEntity.InfoBean.VideoDataBean> mVideoDataBeans;
    private LayoutInflater mLayoutInflater;

    public GiftBagCourseAdapter(Context context, List<GiftBagEntity.InfoBean.VideoDataBean> videoDataBeans) {
        mContext = context;
        mVideoDataBeans = videoDataBeans;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GiftCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GiftCourseViewHolder(mLayoutInflater.inflate(R.layout.item_gift_bag_course,parent,false));
    }

    @Override
    public void onBindViewHolder(GiftCourseViewHolder holder, int position) {
        GiftBagEntity.InfoBean.VideoDataBean videoDataBean = mVideoDataBeans.get(position);
        if (videoDataBean!=null) {
            Glide.with(mContext).load(videoDataBean.getImg()).centerCrop().into(holder.mCourseImg);
            holder.mCourseLearn.setText(videoDataBean.getLearn()+"人学习");
            holder.mCourseTitle.setText(videoDataBean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mVideoDataBeans!=null?mVideoDataBeans.size():0;
    }

    class GiftCourseViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mCourseImg;
        private TextView mCourseTitle;
        private TextView mCourseLearn;


        public GiftCourseViewHolder(View itemView) {
            super(itemView);
            mCourseImg = (RoundedImageView) itemView.findViewById(R.id.course_img);
            mCourseTitle = (TextView) itemView.findViewById(R.id.course_title);
            mCourseLearn = (TextView) itemView.findViewById(R.id.course_learn);
        }
    }
}
