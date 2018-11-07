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

public class SearchEssayAdapter extends RecyclerView.Adapter<SearchEssayAdapter.SearchEssayViewHolder> {
    private Context mContext;
    private List<NewSearchResultEntity.NewsListBean> mNewsListBeanList;
    private LayoutInflater mLayoutInflater;
    private String mKey;
    public SearchEssayAdapter(Context context, List<NewSearchResultEntity.NewsListBean> newsListBeans,String key) {
        mContext = context;
        mNewsListBeanList = newsListBeans;
        mLayoutInflater = LayoutInflater.from(context);
        mKey = key;
    }

    @Override
    public SearchEssayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchEssayViewHolder(mLayoutInflater.inflate(R.layout.item_home_essay_v,parent,false));
    }

    @Override
    public void onBindViewHolder(SearchEssayViewHolder holder, int position) {
        final NewSearchResultEntity.NewsListBean newsListBean = mNewsListBeanList.get(position);
        if(StringUtils.isNotEmpty(newsListBean.getImg()))
            Glide.with(mContext).load(newsListBean.getImg()).centerCrop().into(holder.mEssayImg);
        if(StringUtils.isNotEmpty(newsListBean.getTitle()))
            holder.mEssayTitle.setSpecifiedTextsColor(newsListBean.getTitle(),mKey, Color.parseColor("#ff0000"));
        if(StringUtils.isNotEmpty(newsListBean.getLearn()))
            holder.mSeeEssayNum.setSpecifiedTextsColor(newsListBean.getLearn()+"人",mKey, Color.parseColor("#ff0000"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnSearchItemClickListener!=null){
                    mOnSearchItemClickListener.onSearchItemClick(newsListBean.getId(),newsListBean.getSel_type()==null?"3":newsListBean.getSel_type()
                            ,newsListBean.getType()==null?"2":newsListBean.getType());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsListBeanList!=null?mNewsListBeanList.size():0;
    }

    public void setKey(String key) {
        mKey = key;
    }

    class SearchEssayViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mEssayImg;
        private SearchTextView mEssayTitle;
        private SearchTextView mSeeEssayNum;
        private View mCutLine;

        public SearchEssayViewHolder(View itemView) {
            super(itemView);

            mEssayImg = (RoundedImageView) itemView.findViewById(R.id.essay_img);
            mEssayTitle = (SearchTextView) itemView.findViewById(R.id.essay_title);
            mSeeEssayNum = (SearchTextView) itemView.findViewById(R.id.see_essay_num);
            mCutLine = (View) itemView.findViewById(R.id.cut_line);
            mCutLine.setVisibility(View.VISIBLE);
        }
    }

    private OnSearchItemClickListener mOnSearchItemClickListener;

    public void setOnSearchItemClickListener(OnSearchItemClickListener onSearchItemClickListener) {
        mOnSearchItemClickListener = onSearchItemClickListener;
    }
}
