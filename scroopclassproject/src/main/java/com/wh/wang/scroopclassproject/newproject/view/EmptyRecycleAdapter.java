package com.wh.wang.scroopclassproject.newproject.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wh.wang.scroopclassproject.base.MyApplication;

/**
 * Created by teitsuyoshi on 2018/5/24.
 */

public class EmptyRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int EMPTY_RECYCLEVIEW = 0;
    private final int NO_EMPTY_RECYCLEVIEW = 1;
    private RecyclerView.Adapter mInnerAdapter;
    private int mEmptyLayout;

    public EmptyRecycleAdapter(RecyclerView.Adapter mInnerAdapter, int mEmptyLayout) {
        this.mInnerAdapter = mInnerAdapter;
        this.mEmptyLayout = mEmptyLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==EMPTY_RECYCLEVIEW){
            return new EmptyViewHolder(LayoutInflater.from(MyApplication.mApplication).inflate(mEmptyLayout,parent,false));
        }else{
            return mInnerAdapter.onCreateViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof EmptyViewHolder){
            return;
        }
        mInnerAdapter.onBindViewHolder(holder,position);
    }

    @Override
    public int getItemViewType(int position) {
        if(mInnerAdapter!=null&&mInnerAdapter.getItemCount()!=0){
            return NO_EMPTY_RECYCLEVIEW;
        }else{
            return EMPTY_RECYCLEVIEW;
        }
    }

    @Override
    public int getItemCount() {
        if(mInnerAdapter!=null){
            if(mInnerAdapter.getItemCount()!=0){
//                Log.e("DH_EMPTY","展示有效数据");
                return mInnerAdapter.getItemCount();
            }else{
//                Log.e("DH_EMPTY","展示空数据");
                return 1;
            }
        }
        Log.e("DH_EMPTY","展示数据个数");
        return 1;
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder{

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void emptyNotifyDataSetChanged(){
        if(mInnerAdapter!=null){
            mInnerAdapter.notifyDataSetChanged();
        }
        EmptyRecycleAdapter.this.notifyDataSetChanged();
    }

    public void setLayout(int layout){
        mEmptyLayout = layout;
    }
}
