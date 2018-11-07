package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoHolder>{
    private Context mContext;
    private List<ContactEntity.InfoBean> infoList;
    private String flag;

    public UserInfoAdapter(Context mContext, List<ContactEntity.InfoBean> infoList,String flag) {
        this.mContext = mContext;
        this.infoList = infoList;
        this.flag = flag;
    }

    @Override
    public UserInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserInfoHolder mHolder = new UserInfoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_userinfo,parent,false));
        return mHolder;
    }

    @Override
    public void onBindViewHolder(UserInfoHolder holder, int position) {
        if ("0".equals(flag))
            holder.tvPrice.setVisibility(View.GONE);
        holder.tvName.setText(infoList.get(position).getName());
        holder.tvPhone.setText(infoList.get(position).getPhone());
        holder.tvPrice.setText("￥"+infoList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class UserInfoHolder extends RecyclerView.ViewHolder{
        private final TextView tvName;
        private final TextView tvPhone;
        private final TextView tvPrice;
        public UserInfoHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
