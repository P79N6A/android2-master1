package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class SystemCourseAdapter extends RecyclerView.Adapter<SystemCourseAdapter.CourseViewHolder> {
    private Activity mContext;
    private List<MainCourseEntity.CourseBean.SystemCourseBean.CourseDetailBeanX> mCourseDetails;
    private LayoutInflater mLayoutInflater;

    public SystemCourseAdapter(Activity mContext, List<MainCourseEntity.CourseBean.SystemCourseBean.CourseDetailBeanX> mCourseDetails) {
        this.mContext = mContext;
        this.mCourseDetails = mCourseDetails;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseViewHolder(mLayoutInflater.inflate(R.layout.item_more_course,parent,false));
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, final int position) {
        if(holder!=null){
            String img = mCourseDetails.get(position).getImg()==null?"":mCourseDetails.get(position).getImg();
            String title = mCourseDetails.get(position).getTitle()==null?"":mCourseDetails.get(position).getTitle();
            String price = mCourseDetails.get(position).getNew_price()==null?"":mCourseDetails.get(position).getNew_price();
            String ifnew = mCourseDetails.get(position).getIfnew();

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
            Glide.with(MyApplication.mApplication).load(img).centerCrop().into(holder.mItemCourseImg);
            holder.mItemCourseTitle.setText(title);
            if("0.00".equals(price)){
                holder.mItemCoursePrice.setText("免费");
            }else{
                holder.mItemCoursePrice.setText("¥"+price);
            }
            holder.mCourseItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GotoNextActUtils.getInstance().nextActivity(mContext,mCourseDetails.get(position).getId(),mCourseDetails.get(position).getType());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCourseDetails.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mCourseItem;
        private ImageView mItemCourseImg;
        private TextView mItemCourseTitle;
        private TextView mItemCoursePrice;
        private double rate = 9/(5.0);
        private TextView mTag;


        public CourseViewHolder(View itemView) {
            super(itemView);
            mCourseItem = (RelativeLayout) itemView.findViewById(R.id.course_item);
            mItemCourseImg = (ImageView) itemView.findViewById(R.id.item_course_img);
            mItemCourseTitle = (TextView) itemView.findViewById(R.id.item_course_title);
            mItemCoursePrice = (TextView) itemView.findViewById(R.id.item_course_price);
            mTag = (TextView) itemView.findViewById(R.id.tag);

            DisplayMetrics d = new DisplayMetrics();
            mContext.getWindowManager().getDefaultDisplay().getMetrics(d);
            ViewGroup.LayoutParams layoutParams = mItemCourseImg.getLayoutParams();
            layoutParams.height = (int) ((d.widthPixels- DisplayUtil.dip2px(MyApplication.mApplication,30))/2/rate);
            mItemCourseImg.setLayoutParams(layoutParams);
        }
    }
}
