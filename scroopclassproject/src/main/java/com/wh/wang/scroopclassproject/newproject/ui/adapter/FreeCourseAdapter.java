package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.FreeCourseEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class FreeCourseAdapter extends RecyclerView.Adapter<FreeCourseAdapter.FreeViewHolder> {
    private List<FreeCourseEntity.InfoBean.CourseListBean> mFreeCourseList;
    private int mPhoneW;
    private LayoutInflater mInflater;
    private OnFreeCourseClickListener mOnFreeCourseClickListener;
    private Context mContext;
    public void setOnFreeCourseClickListener(OnFreeCourseClickListener mOnFreeCourseClickListener) {
        this.mOnFreeCourseClickListener = mOnFreeCourseClickListener;
    }

    public FreeCourseAdapter(List<FreeCourseEntity.InfoBean.CourseListBean> mFreeCourseList,Context context, int mPhoneW) {
        this.mFreeCourseList = mFreeCourseList;
        this.mPhoneW = mPhoneW;
        mContext = context;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public FreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreeViewHolder(mInflater.inflate(R.layout.item_free_course,parent,false));
    }

    @Override
    public void onBindViewHolder(FreeViewHolder holder, final int position) {
        if(mFreeCourseList!=null&&mFreeCourseList.get(position)!=null){
            final FreeCourseEntity.InfoBean.CourseListBean courseListBean = mFreeCourseList.get(position);
            String ifnew = courseListBean.getIfnew();

            if("1".equals(ifnew)||"2".equals(ifnew)){
                holder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_new_free_course_bg));
                holder.mTag.setText(R.string.new_course_tag);
                holder.mTag.setVisibility(View.VISIBLE);
            }else{
                holder.mTag.setVisibility(View.GONE);
            }
//            else if("2".equals(ifnew)){
//                holder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_update_free_course_bg));
//                holder.mTag.setText(R.string.update_course_tag);
//                holder.mTag.setVisibility(View.VISIBLE);
//            }
            Glide.with(MyApplication.mApplication).load(courseListBean.getImg()).centerCrop().into(holder.mFreeCourseImg);
            holder.mFreeCourseTitle.setText(courseListBean.getTitle());
            holder.mFreeCourseName.setText(courseListBean.getName());
            holder.mFreeCourseInfo.setText(courseListBean.getDuan());
            holder.mFreeCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnFreeCourseClickListener!=null){
                        mOnFreeCourseClickListener.onFreeCourseClick(position,courseListBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFreeCourseList==null?0:mFreeCourseList.size();
    }

    class FreeViewHolder extends RecyclerView.ViewHolder{
        private ImageView mFreeCourseImg;
        private TextView mFreeCourseTitle;
        private TextView mFreeCourseName;
        private TextView mFreeCourseInfo;
        private RelativeLayout mFreeCourse;
        private TextView mTag;


        private double rate = 9/(5.0);
        public FreeViewHolder(View itemView) {
            super(itemView);

            mFreeCourse = (RelativeLayout) itemView.findViewById(R.id.free_course);
            mFreeCourseImg = (ImageView) itemView.findViewById(R.id.free_course_img);
            mFreeCourseTitle = (TextView) itemView.findViewById(R.id.free_course_title);
            mFreeCourseName = (TextView) itemView.findViewById(R.id.free_course_name);
            mFreeCourseInfo = (TextView) itemView.findViewById(R.id.free_course_info);
            mTag = (TextView) itemView.findViewById(R.id.tag);

            ViewGroup.LayoutParams layoutParams = mFreeCourseImg.getLayoutParams();
            layoutParams.height = (int) (mPhoneW/rate);
            mFreeCourseImg.setLayoutParams(layoutParams);
        }
    }
    public interface OnFreeCourseClickListener{
        void onFreeCourseClick(int pos,FreeCourseEntity.InfoBean.CourseListBean bean);
    }
}
