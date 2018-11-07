package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreOrAllCourseEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class LoadMoreCourseAdapter extends RecyclerView.Adapter<LoadMoreCourseAdapter.CourseViewHolder> {
    private List<MoreOrAllCourseEntity.InfoBean.CourseListBean> mCourseList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mPhoneW;
    private OnMoreCourseClickListener mOnMoreCourseClickListener;

    public void setmOnMoreCourseClickListener(OnMoreCourseClickListener mOnMoreCourseClickListener) {
        this.mOnMoreCourseClickListener = mOnMoreCourseClickListener;
    }

    public LoadMoreCourseAdapter(List<MoreOrAllCourseEntity.InfoBean.CourseListBean> mCourseList,Context context, int w) {
        this.mCourseList = mCourseList;
        mPhoneW = w;
        mContext = context;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseViewHolder(mInflater.inflate(R.layout.item_more_course,parent,false));
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, final int position) {
        if(mCourseList!=null&&mCourseList.get(position)!=null){
            final MoreOrAllCourseEntity.InfoBean.CourseListBean courseListBean = mCourseList.get(position);
            String ifnew = courseListBean.getIfnew();

            if("1".equals(ifnew)||"2".equals(ifnew)){
                holder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_new_course_bg));
                holder.mTag.setText(R.string.new_course_tag);
                holder.mTag.setVisibility(View.VISIBLE);
            }else{
                holder.mTag.setVisibility(View.GONE);
            }
//            else if("2".equals(ifnew)){
//                holder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_update_course_bg));
//                holder.mTag.setText(R.string.update_course_tag);
//                holder.mTag.setVisibility(View.VISIBLE);
//            }
            if(courseListBean.getImg()!=null){
                Glide.with(MyApplication.mApplication).load(courseListBean.getImg()).centerCrop().into(holder.mItemCourseImg);
            }
            holder.mItemCourseTitle.setText(courseListBean.getTitle());
            String new_price = courseListBean.getNew_price();
            double price = 0;
            if(new_price!=null){
                try {
                    price = Double.parseDouble(new_price);
                }catch (Exception e){
                    price = 0;
                }
            }
            if(price!=0){
                holder.mItemCoursePrice.setText("¥ "+price);
            }else{
                holder.mItemCoursePrice.setText("免费");
            }
            holder.mCourseItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnMoreCourseClickListener!=null){
                        mOnMoreCourseClickListener.onMoreCourseClick(position,courseListBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mItemCourseImg;
        private TextView mItemCourseTitle;
        private TextView mItemCoursePrice;
        private RelativeLayout mCourseItem;
        private TextView mTag;

        private double rate = 9/(5.0);
        public CourseViewHolder(View itemView) {
            super(itemView);
            mItemCourseImg = (RoundedImageView) itemView.findViewById(R.id.item_course_img);
            mItemCourseTitle = (TextView) itemView.findViewById(R.id.item_course_title);
            mItemCoursePrice = (TextView) itemView.findViewById(R.id.item_course_price);
            mCourseItem = (RelativeLayout) itemView.findViewById(R.id.course_item);
            mTag = (TextView) itemView.findViewById(R.id.tag);

            ViewGroup.LayoutParams layoutParams = mItemCourseImg.getLayoutParams();
            layoutParams.height = (int) ((mPhoneW- DisplayUtil.dip2px(MyApplication.mApplication,30))/2/rate);
            mItemCourseImg.setLayoutParams(layoutParams);
        }
    }

    public interface OnMoreCourseClickListener{
        void onMoreCourseClick(int pos,MoreOrAllCourseEntity.InfoBean.CourseListBean bean);
    }
}
