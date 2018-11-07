package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseLabelEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CourseLabelEntity.InfoBean mInfoBean;
    private List<CourseLabelEntity.InfoBean.CourseListBean> mCourseBeans;
    private Context mContext;
    private LayoutInflater mInflater;
    private String mTitle;
    private int []mItemColor = {R.drawable.course_icon_1_shape,R.drawable.course_icon_2_shape,
            R.drawable.course_icon_3_shape,R.drawable.course_icon_4_shape,R.drawable.course_icon_5_shape};
    public CourseAdapter(CourseLabelEntity.InfoBean mInfoBean,List<CourseLabelEntity.InfoBean.CourseListBean> courses,
                         Context mContext,String title) {
        this.mInfoBean = mInfoBean;
        this.mContext = mContext;
        mCourseBeans = courses;
        mTitle = title;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            return new CourseHeadViewHolder(mInflater.inflate(R.layout.item_course_head,parent,false));
        }else if(viewType==1){
            return new RecommendCourseViewHolder(mInflater.inflate(R.layout.item_recomment_course,parent,false));
        }
        return new OtherCourseViewHolder(mInflater.inflate(R.layout.item_other_course,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder!=null){
            if(holder instanceof CourseHeadViewHolder){
                CourseHeadViewHolder courseHeadViewHolder = (CourseHeadViewHolder) holder;
                if(mInfoBean.getScroll()!=null&&mInfoBean.getScroll().size()>0){
                    courseHeadViewHolder.mCourseHeadImg.setVisibility(View.VISIBLE);
                    final List<CourseLabelEntity.InfoBean.ScrollBean> scroll = mInfoBean.getScroll();
                    String ifnew = scroll.get(0).getIfnew();

                    if("1".equals(ifnew)||"2".equals(ifnew)){
                        courseHeadViewHolder.mTag.setBackgroundColor(Color.parseColor("#FF5715"));
                        courseHeadViewHolder.mTag.setText(R.string.new_course_tag);
                        courseHeadViewHolder.mTag.setVisibility(View.VISIBLE);
                    }else{
                        courseHeadViewHolder.mTag.setVisibility(View.GONE);
                    }
//                    else if("2".equals(ifnew)){
//                        courseHeadViewHolder.mTag.setBackgroundColor(Color.parseColor("#66B5F2"));
//                        courseHeadViewHolder.mTag.setText(R.string.update_course_tag);
//                        courseHeadViewHolder.mTag.setVisibility(View.VISIBLE);
//                    }
                    Glide.with(MyApplication.mApplication).load(scroll.get(0).getImg()).centerCrop().into(courseHeadViewHolder.mCourseHeadImg);
                    courseHeadViewHolder.mCourseHeadImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mOnCourseClickListener!=null){
                                mOnCourseClickListener.onCourseClick(position,scroll.get(0).getCourseId(),scroll.get(0).getType());
                            }
                        }
                    });
                }else{
                    courseHeadViewHolder.mCourseHeadImg.setVisibility(View.GONE);
                    courseHeadViewHolder.mTag.setVisibility(View.GONE);
                }
                courseHeadViewHolder.mCourseHeadTitle.setText(mTitle);
            }else if(holder instanceof RecommendCourseViewHolder){
                RecommendCourseViewHolder recommendCourseViewHolder = (RecommendCourseViewHolder) holder;
                recommendCourseViewHolder.mRecommendIcon.setBackgroundResource(mItemColor[(position-1)%5]);
                if(position-1>=0&&mCourseBeans!=null&&mCourseBeans.get(position-1)!=null
                        &&mCourseBeans.size()>0){
                    final CourseLabelEntity.InfoBean.CourseListBean courseListBean = mCourseBeans.get(position - 1);

                    String ifnew = courseListBean.getIfnew();

                    if("1".equals(ifnew)||"2".equals(ifnew)){
                        recommendCourseViewHolder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_new_course_radius_4_bg));
                        recommendCourseViewHolder.mTag.setText(R.string.new_course_tag);
                        recommendCourseViewHolder.mTag.setVisibility(View.VISIBLE);
                    }else{
                        recommendCourseViewHolder.mTag.setVisibility(View.GONE);
                    }
//                    else if("2".equals(ifnew)){
//                        recommendCourseViewHolder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_update_course_radius_4_bg));
//                        recommendCourseViewHolder.mTag.setText(R.string.update_course_tag);
//                        recommendCourseViewHolder.mTag.setVisibility(View.VISIBLE);
//                    }
                    recommendCourseViewHolder.mRecommendName.setText(courseListBean.getTitle());
                    recommendCourseViewHolder.mItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mOnCourseClickListener!=null){
                                mOnCourseClickListener.onCourseClick(position,courseListBean.getId(),courseListBean.getType());
                            }
                        }
                    });
                }
            }else{
                OtherCourseViewHolder otherCourseViewHolder = (OtherCourseViewHolder) holder;
                if(position-1>=0&&mCourseBeans!=null&&mCourseBeans.get(position-1)!=null
                        &&mCourseBeans.size()>0){
                    final CourseLabelEntity.InfoBean.CourseListBean courseListBean = mCourseBeans.get(position - 1);
                    String ifnew = courseListBean.getIfnew();

                    if("1".equals(ifnew)||"2".equals(ifnew)){
                        otherCourseViewHolder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_new_course_radius_4_bg));
                        otherCourseViewHolder.mTag.setText(R.string.new_course_tag);
                        otherCourseViewHolder.mTag.setVisibility(View.VISIBLE);
                    }else{
                        otherCourseViewHolder.mTag.setVisibility(View.GONE);
                    }
//                    else if("2".equals(ifnew)){
//                        otherCourseViewHolder.mTag.setBackground(mContext.getResources().getDrawable(R.drawable.shape_update_course_radius_4_bg));
//                        otherCourseViewHolder.mTag.setText(R.string.update_course_tag);
//                        otherCourseViewHolder.mTag.setVisibility(View.VISIBLE);
//                    }
                    Glide.with(MyApplication.mApplication).load(courseListBean.getImg()).centerCrop().into(otherCourseViewHolder.mItemCourseImg);
                    otherCourseViewHolder.mItemCourseTitle.setText(courseListBean.getTitle());
                    if("0.00".equals(courseListBean.getNew_price())){
                        otherCourseViewHolder.mItemCoursePrice.setText("免费");
                    }else{
                        otherCourseViewHolder.mItemCoursePrice.setText("¥ "+courseListBean.getNew_price());
                    }
                    otherCourseViewHolder.mItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mOnCourseClickListener!=null){
                                mOnCourseClickListener.onCourseClick(position,courseListBean.getId(),courseListBean.getType());
                            }
                        }
                    });
                }
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else{
            if("推荐".equals(mTitle)){
                return 1;
            }
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return mCourseBeans!=null?(mCourseBeans.size()+1):1;
    }

    class OtherCourseViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mItem;
        private ImageView mItemCourseImg;
        private TextView mItemCourseTitle;
        private TextView mItemCoursePrice;
        private TextView mTag;


        public OtherCourseViewHolder(View itemView) {
            super(itemView);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
            mItemCourseImg = (ImageView) itemView.findViewById(R.id.item_course_img);
            mItemCourseTitle = (TextView) itemView.findViewById(R.id.item_course_title);
            mItemCoursePrice = (TextView) itemView.findViewById(R.id.item_course_price);
            mTag = (TextView) itemView.findViewById(R.id.tag);

        }
    }

    class RecommendCourseViewHolder extends RecyclerView.ViewHolder{
        private View mRecommendIcon;
        private TextView mRecommendName;
        private LinearLayout mItem;
        private TextView mTag;


        public RecommendCourseViewHolder(View itemView) {
            super(itemView);
            mTag = (TextView) itemView.findViewById(R.id.tag);

            mItem = (LinearLayout) itemView.findViewById(R.id.item);
            mRecommendIcon =  itemView.findViewById(R.id.recommend_icon);
            mRecommendName = (TextView) itemView.findViewById(R.id.recommend_name);
        }
    }
    class CourseHeadViewHolder extends RecyclerView.ViewHolder{
        private ImageView mCourseHeadImg;
        private TextView mCourseHeadTitle;
        private TextView mTag;

        public CourseHeadViewHolder(View itemView) {
            super(itemView);
            mCourseHeadImg = (ImageView) itemView.findViewById(R.id.course_head_img);
            mCourseHeadTitle = (TextView) itemView.findViewById(R.id.course_head_title);
            mTag = (TextView) itemView.findViewById(R.id.tag);
        }
    }

    private OnCourseClickListener mOnCourseClickListener;

    public void setOnCourseClickListener(OnCourseClickListener mOnCourseClickListener) {
        this.mOnCourseClickListener = mOnCourseClickListener;
    }

    public interface OnCourseClickListener{
        void onCourseClick(int pos,String id,String type);
    }
}
