package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchEntity;
import com.wh.wang.scroopclassproject.newproject.view.SearchTextView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<NewSearchEntity.InfoBean.CourseListBean> mCourseList;
    private OnResultClickListener mOnResultClickListener;
    private String mKeyWord;

    public void setOnResultClickListener(OnResultClickListener mOnResultClickListener) {
        this.mOnResultClickListener = mOnResultClickListener;
    }

    public SearchResultAdapter(List<NewSearchEntity.InfoBean.CourseListBean> mList,String keyword) {
        mKeyWord = keyword;
        this.mCourseList = mList;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(mLayoutInflater.inflate(R.layout.item_new_search,parent,false));
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, final int position) {
        if(mCourseList!=null){
            final NewSearchEntity.InfoBean.CourseListBean courseListBean = mCourseList.get(position);
            if(courseListBean.getImg()!=null){
                Glide.with(MyApplication.mApplication).load(courseListBean.getImg()).placeholder(R.drawable.ivnull).centerCrop().into(holder.mItemSearchImg);
            }
            if(courseListBean.getTitle()!=null){
                String title = courseListBean.getTitle();
                holder.mItemSearchTitle.setSpecifiedTextsColor(title, mKeyWord, Color.parseColor("#FF0000"));
//                holder.mItemSearchTitle.setText(title);
            }
            if(courseListBean.getTeacher_name()!=null&&!"".equals(courseListBean.getTeacher_name().trim())){
                holder.mItemSearchTeacher.setSpecifiedTextsColor("讲师："+courseListBean.getTeacher_name(), mKeyWord, Color.parseColor("#FF0000"));
//                holder.mItemSearchTeacher.setText("讲师："+courseListBean.getTeacher_name());
            }else{
                holder.mItemSearchTeacher.setVisibility(View.GONE);
            }
            if(courseListBean.getLearn()!=null){
                holder.mItemSearchBrowseNum.setSpecifiedTextsColor(courseListBean.getLearn()+"人浏览", mKeyWord, Color.parseColor("#FF0000"));
//                holder.mItemSearchBrowseNum.setText(courseListBean.getLearn()+"人浏览");
            }
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnResultClickListener!=null){
                        mOnResultClickListener.onResultClick(position,courseListBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder{
        private ImageView mItemSearchImg;
        private SearchTextView mItemSearchTitle;
        private SearchTextView mItemSearchTeacher;
        private SearchTextView mItemSearchBrowseNum;
        private RelativeLayout mItem;


        public SearchResultViewHolder(View itemView) {
            super(itemView);
            mItemSearchImg = (ImageView) itemView.findViewById(R.id.item_search_img);
            mItemSearchTitle = (SearchTextView) itemView.findViewById(R.id.item_search_title);
            mItemSearchTeacher = (SearchTextView) itemView.findViewById(R.id.item_search_teacher);
            mItemSearchBrowseNum = (SearchTextView) itemView.findViewById(R.id.item_search_browse_num);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);

        }
    }

    public interface OnResultClickListener{
        void onResultClick(int pos,NewSearchEntity.InfoBean.CourseListBean bean);
    }

    public void resetKeyWord(String key){
        mKeyWord = key;
    }
}
