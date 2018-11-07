package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;

import java.util.List;

/**
 * Created by chdh on 2018/1/20.
 */

public class SelectMemberAdapter extends RecyclerView.Adapter<SelectMemberAdapter.SelectViewHolder> {
    private List<String> mList;
    private LayoutInflater mInflater;
    public SelectMemberAdapter(List<String> list) {
        mList = list;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectViewHolder(mInflater.inflate(R.layout.item_select_member,parent,false));
    }

    @Override
    public void onBindViewHolder(SelectViewHolder holder, int position) {
        if(holder!=null&&mList!=null&&mList.get(position)!=null){

        }
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class SelectViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mPosition;
        private ImageView mSelect;

        public SelectViewHolder(View itemView) {
            super(itemView);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPosition = (TextView) itemView.findViewById(R.id.position);
            mSelect = (ImageView) itemView.findViewById(R.id.select);
        }
    }
}
