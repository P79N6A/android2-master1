package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.view.OldPriceTextView;

import java.util.List;

/**
 * Created by chdh on 2018/1/31.
 */

public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.ApplyViewHolder> {
    private List<ContactEntity.InfoBean> mInfoBeanList;
    private double mPrice;
    private LayoutInflater mInflater;
    private boolean mHasVPrice = false;
    public ApplyAdapter(List<ContactEntity.InfoBean> infoBeanList, double price,boolean hasVPrice) {
        mInfoBeanList = infoBeanList;
        mPrice = price;
        mHasVPrice = hasVPrice;
        mInflater = LayoutInflater.from(MyApplication.getApplication());
    }

    @Override
    public ApplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplyViewHolder(mInflater.inflate(R.layout.item_apply,parent,false));
    }

    @Override
    public void onBindViewHolder(ApplyViewHolder holder, int position) {
        if(holder!=null&&mInfoBeanList!=null&&mInfoBeanList.get(position)!=null){
            ContactEntity.InfoBean infoBean = mInfoBeanList.get(position);
            holder.mName.setText(infoBean.getName());
            holder.mPhone.setText(infoBean.getPhone());
            if(mHasVPrice){
                holder.mOPriceFrag.setVisibility(View.VISIBLE);
                double v_price = 0.00;
                try {
                    v_price = Double.parseDouble(infoBean.getPrice());
                }catch (Exception e){
                    v_price = mPrice;
                }
                if(v_price<mPrice){
                    holder.mOPrice.setCutLine(true);
                }else{
                    holder.mOPrice.setCutLine(false);
                }
                holder.mOPrice.setText(mPrice+"");
            }else{
                holder.mVPrice.setTextColor(Color.parseColor("#282828"));
                holder.mOPriceFrag.setVisibility(View.GONE);
            }
            holder.mVPrice.setText(infoBean.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return mInfoBeanList!=null?mInfoBeanList.size():0;
    }

    class ApplyViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mPhone;
        private OldPriceTextView mOPrice;
        private TextView mVPrice;
        private FrameLayout mOPriceFrag;

        public ApplyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPhone = (TextView) itemView.findViewById(R.id.phone);
            mOPrice = (OldPriceTextView) itemView.findViewById(R.id.o_price);
            mVPrice = (TextView) itemView.findViewById(R.id.v_price);
            mOPriceFrag = (FrameLayout) itemView.findViewById(R.id.o_price_frag);

        }
    }
}
