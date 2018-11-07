package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;

import java.util.List;

/**
 * Created by chdh on 2018/1/30.
 */

public class ApplyContactAdapter extends RecyclerView.Adapter<ApplyContactAdapter.ApplyViewHolder> {
    private List<ContactEntity.InfoBean> mEventMoreEntityList;
    private LayoutInflater mInflater;

    public ApplyContactAdapter(List<ContactEntity.InfoBean> eventMoreEntityList) {
        mEventMoreEntityList = eventMoreEntityList;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public ApplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplyViewHolder(mInflater.inflate(R.layout.item_apply_contact,parent,false));
    }

    @Override
    public void onBindViewHolder(ApplyViewHolder holder, final int position) {
        if(holder!=null&&mEventMoreEntityList!=null&&mEventMoreEntityList.get(position)!=null){
            final ContactEntity.InfoBean eventMoreEntity = mEventMoreEntityList.get(position);
            holder.mName.setText(eventMoreEntity.getName());
            holder.mTel.setText(eventMoreEntity.getPhone());
            holder.mDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEventMoreEntityList.remove(position);
                    ApplyContactAdapter.this.notifyDataSetChanged();
                    if(mOnDelClickListener!=null){
                        mOnDelClickListener.onDelClick(eventMoreEntity);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEventMoreEntityList!=null?mEventMoreEntityList.size():0;
    }

    class ApplyViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mTel;
        private ImageView mDel;

        public ApplyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTel = (TextView) itemView.findViewById(R.id.tel);
            mDel = (ImageView) itemView.findViewById(R.id.del);
        }
    }

    private OnDelClickListener mOnDelClickListener;

    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        mOnDelClickListener = onDelClickListener;
    }

    public interface OnDelClickListener{
        void onDelClick(ContactEntity.InfoBean bean);
    }
}
