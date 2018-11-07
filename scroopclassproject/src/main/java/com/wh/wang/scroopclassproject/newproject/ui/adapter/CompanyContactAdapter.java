package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;

import java.util.List;

/**
 * Created by chdh on 2018/1/31.
 */

public class CompanyContactAdapter extends RecyclerView.Adapter<CompanyContactAdapter.CompanyContactViewHolder> {
    private List<ContactEntity.InfoBean> mInfoBeanList;
    private LayoutInflater mInflater;

    public CompanyContactAdapter(List<ContactEntity.InfoBean> infoBeanList) {
        mInfoBeanList = infoBeanList;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public CompanyContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompanyContactViewHolder(mInflater.inflate(R.layout.item_select_member,parent,false));
    }

    @Override
    public void onBindViewHolder(final CompanyContactViewHolder holder, final int position) {
        if(holder!=null&&mInfoBeanList!=null&&mInfoBeanList.get(position)!=null){
            final ContactEntity.InfoBean infoBean = mInfoBeanList.get(position);
            if(infoBean.getAvatar()!=null){
                Glide.with(MyApplication.getApplication()).load(infoBean.getAvatar()).centerCrop().into(holder.mAvatar);
            }else{
                holder.mAvatar.setImageResource(R.drawable.qiye_zwpic);
            }
            if(infoBean.isCheck()){
                holder.mSelect.setImageResource(R.drawable.bm_icon_select);
            }else{
                holder.mSelect.setImageResource(R.drawable.bm_icon_unselect);
            }

            if(infoBean.isOperation()||!infoBean.isJoinAccess()){
                holder.mView.setOnClickListener(null);
            }else{
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if(infoBean.isCheck()){
//                            holder.mSelect.setImageResource(R.drawable.bm_icon_unselect);
//                            infoBean.setCheck(false);
//                        }else{
//                            holder.mSelect.setImageResource(R.drawable.bm_icon_select);
//                            infoBean.setCheck(true);
//                        }
                        if(mOnSelectClickListener!=null){
                            mOnSelectClickListener.onSelectClick(position,infoBean,holder.mSelect);
                        }
                    }
                });
            }


            if(!infoBean.isJoinAccess()){
                holder.mHint.setText(""+infoBean.getReason());
                holder.mSelect.setVisibility(View.INVISIBLE);
                holder.mName.setTextColor(Color.parseColor("#9AA3B2"));
                holder.mPosition.setTextColor(Color.parseColor("#9AA3B2"));
            }else{
                holder.mHint.setText("");
                holder.mSelect.setVisibility(View.VISIBLE);
                holder.mName.setTextColor(Color.parseColor("#282828"));
                holder.mPosition.setTextColor(Color.parseColor("#282828"));
            }
            holder.mName.setText(infoBean.getName());
            holder.mPosition.setText(infoBean.getPosition());

        }
    }

    @Override
    public int getItemCount() {
        return mInfoBeanList!=null?mInfoBeanList.size():0;
    }

    class CompanyContactViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mPosition;
        private ImageView mSelect;
        private View mView;
        private TextView mHint;

        public CompanyContactViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPosition = (TextView) itemView.findViewById(R.id.position);
            mSelect = (ImageView) itemView.findViewById(R.id.select);
            mHint = (TextView) itemView.findViewById(R.id.hint);
        }
    }

    private OnSelectClickListener mOnSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        mOnSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener{
        void onSelectClick(int pos,ContactEntity.InfoBean infoBean,ImageView img);
    }
}
