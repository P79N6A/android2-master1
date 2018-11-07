package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.InviteEntity;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/4/17.
 */

public class InviteListAdapter extends RecyclerView.Adapter<InviteListAdapter.InviteViewHolder> {
    private List<InviteEntity.InfoBean.ListBean> mInviteList;
    private LayoutInflater mInflater;

    public InviteListAdapter(List<InviteEntity.InfoBean.ListBean> mInviteList) {
        this.mInviteList = mInviteList;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public InviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InviteViewHolder(mInflater.inflate(R.layout.item_invite,parent,false));
    }

    @Override
    public void onBindViewHolder(InviteViewHolder holder, int position) {
        if(holder!=null&&mInviteList!=null&&mInviteList.get(position)!=null){
            InviteEntity.InfoBean.ListBean bean = mInviteList.get(position);
            holder.mName.setText(bean.getName());
            holder.mTel.setText(bean.getPhone());
            holder.mOpenTime.setText(bean.getMember_start());
        }
    }

    @Override
    public int getItemCount() {
        return mInviteList!=null?mInviteList.size():0;
    }

    class InviteViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mTel;
        private TextView mOpenTime;

        public InviteViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.name);
            mTel = (TextView) itemView.findViewById(R.id.tel);
            mOpenTime = (TextView) itemView.findViewById(R.id.open_time);
        }
    }
}
