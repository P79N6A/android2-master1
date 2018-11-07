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

public class SearchEventAdapter extends RecyclerView.Adapter<SearchEventAdapter.SearchEventViewHolder> {

    private Context mContext;
    private List<NewSearchResultEntity.EventListBean> mEventListBeanList;
    private LayoutInflater mLayoutInflater;
    private String mKey;
    private OnSearchItemClickListener mOnSearchItemClickListener;

    public SearchEventAdapter(Context context, List<NewSearchResultEntity.EventListBean> eventListBeanList,String key) {
        mContext = context;
        mEventListBeanList = eventListBeanList;
        mLayoutInflater = LayoutInflater.from(context);
        mKey = key;
    }

    @Override
    public SearchEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchEventViewHolder(mLayoutInflater.inflate(R.layout.item_search_course,parent,false));
    }

    @Override
    public void onBindViewHolder(SearchEventViewHolder holder, int position) {
        final NewSearchResultEntity.EventListBean eventListBean = mEventListBeanList.get(position);
        if(StringUtils.isNotEmpty(eventListBean.getTitle())){
            holder.mCourseTitle.setSpecifiedTextsColor(eventListBean.getTitle(),mKey, Color.parseColor("#FF0000"));
        }


        if(StringUtils.isNotEmpty(eventListBean.getImg()))
            Glide.with(mContext).load(eventListBean.getImg()).centerCrop().into(holder.mSearchCourseImg);
        if(StringUtils.isNotEmpty(eventListBean.getAddress()))
            holder.mCourseTeacher.setSpecifiedTextsColor(eventListBean.getAddress(),mKey, Color.parseColor("#FF0000"));


        if(StringUtils.isNotEmpty(eventListBean.getStart_shijian()))
            holder.mCourseLearnNum.setSpecifiedTextsColor(eventListBean.getStart_shijian(),mKey, Color.parseColor("#FF0000"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnSearchItemClickListener!=null){
                    mOnSearchItemClickListener.onSearchItemClick(eventListBean.getId(),eventListBean.getSel_type()==null?"2":eventListBean.getSel_type(),
                            eventListBean.getType()==null?"3":eventListBean.getType());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventListBeanList!=null?mEventListBeanList.size():0;
    }

    public void setOnSearchItemClickListener(OnSearchItemClickListener onSearchItemClickListener) {
        mOnSearchItemClickListener = onSearchItemClickListener;
    }

    class SearchEventViewHolder extends RecyclerView.ViewHolder{
//        private RoundedImageView mEventImg;
//        private SearchTextView mAddress;
//        private SearchTextView mTime;
//        private SearchTextView mTeacher;
        private RoundedImageView mSearchCourseImg;
        private SearchTextView mCourseTitle;
        private SearchTextView mCourseTeacher;
        private SearchTextView mCourseLearnNum;


        public SearchEventViewHolder(View itemView) {
            super(itemView);
//            mEventImg = (RoundedImageView) itemView.findViewById(R.id.event_img);
//            mAddress = (SearchTextView) itemView.findViewById(R.id.address);
//            mTime = (SearchTextView) itemView.findViewById(R.id.time);
//            mTeacher = (SearchTextView) itemView.findViewById(R.id.teacher);

            mSearchCourseImg = (RoundedImageView) itemView.findViewById(R.id.search_course_img);
            mCourseTitle = (SearchTextView) itemView.findViewById(R.id.course_title);
            mCourseTeacher = (SearchTextView) itemView.findViewById(R.id.course_teacher);
            mCourseLearnNum = (SearchTextView) itemView.findViewById(R.id.course_learn_num);
        }
    }
}
