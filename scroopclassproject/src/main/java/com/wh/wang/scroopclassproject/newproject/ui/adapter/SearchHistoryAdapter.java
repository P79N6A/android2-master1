package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.HistoryViewHolder>{
    private List<String> mHistoryList;
    private LayoutInflater mLayoutInfater;
    private OnHistoryClickListener mOnHistoryClickListener;

    public void setOnHistoryClickListener(OnHistoryClickListener mOnHistoryClickListener) {
        this.mOnHistoryClickListener = mOnHistoryClickListener;
    }

    public SearchHistoryAdapter(List<String> mHistoryList) {
        this.mHistoryList = mHistoryList;
        mLayoutInfater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(mLayoutInfater.inflate(R.layout.item_search_history,parent,false));
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        if(mHistoryList!=null&&mHistoryList.size()>0){
            holder.mItemHistory.setText(mHistoryList.get(position));
            holder.mItemHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnHistoryClickListener!=null){
                        mOnHistoryClickListener.historyListener(mHistoryList.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }



    class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView mItemHistory;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            mItemHistory = (TextView) itemView.findViewById(R.id.item_history);
        }
    }

    public interface OnHistoryClickListener{
        void historyListener(String s);
    }
}
