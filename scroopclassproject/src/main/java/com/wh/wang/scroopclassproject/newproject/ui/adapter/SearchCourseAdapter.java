package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchResultEntity;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.search_result.OnSearchItemClickListener;
import com.wh.wang.scroopclassproject.newproject.view.SearchTextView;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/1.
 */

public class SearchCourseAdapter extends RecyclerView.Adapter<SearchCourseAdapter.SearchCourseViewHolder> {
    private Context mContext;
    private List<NewSearchResultEntity.CourseListBean> mCourseListBeansList;
    private LayoutInflater mLayoutInflater;
    private String mKey;

    public SearchCourseAdapter(Context context, List<NewSearchResultEntity.CourseListBean> courseListBeansList, String key) {
        mContext = context;
        mCourseListBeansList = courseListBeansList;
        mLayoutInflater = LayoutInflater.from(context);
        mKey = key;
    }

    @Override
    public SearchCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchCourseViewHolder(mLayoutInflater.inflate(R.layout.item_search_course, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchCourseViewHolder holder, int position) {
        final NewSearchResultEntity.CourseListBean courseListBean = mCourseListBeansList.get(position);
        if (StringUtils.isNotEmpty(courseListBean.getImg()))
            Glide.with(mContext).load(courseListBean.getImg()).centerCrop().into(holder.mSearchCourseImg);
        if (StringUtils.isNotEmpty(courseListBean.getTitle()))
            holder.mCourseTitle.setSpecifiedTextsColor(courseListBean.getTitle(), mKey, Color.parseColor("#ff0000"));
        if (StringUtils.isNotEmpty(courseListBean.getTeacher_name()))
            holder.mCourseTeacher.setSpecifiedTextsColor("讲师:" + courseListBean.getTeacher_name(), mKey, Color.parseColor("#ff0000"));
        if (StringUtils.isNotEmpty(courseListBean.getLearn()))
            holder.mCourseLearnNum.setSpecifiedTextsColor(courseListBean.getLearn() + "人已学习", mKey, Color.parseColor("#ff0000"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnSearchItemClickListener != null) {
                    mOnSearchItemClickListener.onSearchItemClick(courseListBean.getId(),
                            courseListBean.getSel_type() == null ? "1" : courseListBean.getSel_type(), courseListBean.getType() == null ? "0" : courseListBean.getType());
                }
            }
        });
    }

    public void notifCourse(String key) {
        mKey = key;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCourseListBeansList != null ? mCourseListBeansList.size() : 0;
    }

    public void setKey(String key) {
        mKey = key;
    }

    class SearchCourseViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mSearchCourseImg;
        private SearchTextView mCourseTitle;
        private SearchTextView mCourseTeacher;
        private SearchTextView mCourseLearnNum;

        public SearchCourseViewHolder(View itemView) {
            super(itemView);

            mSearchCourseImg = (RoundedImageView) itemView.findViewById(R.id.search_course_img);
            mCourseTitle = (SearchTextView) itemView.findViewById(R.id.course_title);
            mCourseTeacher = (SearchTextView) itemView.findViewById(R.id.course_teacher);
            mCourseLearnNum = (SearchTextView) itemView.findViewById(R.id.course_learn_num);
        }
    }

    private OnSearchItemClickListener mOnSearchItemClickListener;

    public void setOnSearchItemClickListener(OnSearchItemClickListener onSearchItemClickListener) {
        mOnSearchItemClickListener = onSearchItemClickListener;
    }
}
