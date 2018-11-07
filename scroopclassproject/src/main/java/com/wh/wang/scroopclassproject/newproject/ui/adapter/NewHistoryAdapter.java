package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SearchHotHistoryEntity;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class NewHistoryAdapter extends RecyclerView.Adapter<NewHistoryAdapter.HistoryViewHolder> {
    private Context mContext;
    private List<SearchHotHistoryEntity.HistoryBean> mHistoryBeanList;
    private LayoutInflater mLayoutInflater;

    public NewHistoryAdapter(Context context, List<SearchHotHistoryEntity.HistoryBean> historyBeanList) {
        mContext = context;
        mHistoryBeanList = historyBeanList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(mLayoutInflater.inflate(R.layout.item_new_search_history,parent,false));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        final SearchHotHistoryEntity.HistoryBean historyBean = mHistoryBeanList.get(position);
        holder.mSearchTitle.setText(historyBean.getContent());
        holder.mSearchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnHistoryClickListener!=null) {
                    mOnHistoryClickListener.onSearchHistoryClick(historyBean.getContent());
                }
            }
        });
        holder.mItemDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnHistoryClickListener!=null) {
                    mOnHistoryClickListener.onDelHistory(historyBean.getContent(),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryBeanList!=null?mHistoryBeanList.size():0;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView mSearchTitle;
        private ImageView mItemDel;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            mSearchTitle = (TextView) itemView.findViewById(R.id.search_title);
            mItemDel = (ImageView) itemView.findViewById(R.id.item_del);

        }
    }

    private OnHistoryClickListener mOnHistoryClickListener;

    public void setOnHistoryClickListener(OnHistoryClickListener onHistoryClickListener) {
        mOnHistoryClickListener = onHistoryClickListener;
    }

    public interface OnHistoryClickListener{
        void onSearchHistoryClick(String content);
        void onDelHistory(String content,int pos);
    }
}
