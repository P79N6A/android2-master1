package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ALJInfoEntity;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/4/28.
 */

public class CasebooInviteAdapter extends RecyclerView.Adapter<CasebooInviteAdapter.CasebookInviteViewHolder> {
    private Context context;
    private List<ALJInfoEntity.InfoBean.ListBean> mListBeanList;
    private LayoutInflater inflater;

    public CasebooInviteAdapter(Context context, List<ALJInfoEntity.InfoBean.ListBean> list) {
        this.context = context;
        mListBeanList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CasebookInviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CasebookInviteViewHolder(inflater.inflate(R.layout.item_casebook_invite,parent,false));
    }

    @Override
    public void onBindViewHolder(CasebookInviteViewHolder holder, int position) {
        if(holder!=null&&mListBeanList.get(position)!=null){
            ALJInfoEntity.InfoBean.ListBean listBean = mListBeanList.get(position);
            holder.mName.setText(listBean.getU_name());
            holder.mMoney.setText(listBean.getCreate_time());
            Glide.with(context).load(listBean.getU_avator()).centerCrop().into(holder.mHead);
        }
    }

    @Override
    public int getItemCount() {
        return mListBeanList!=null?mListBeanList.size():0;
    }

    class CasebookInviteViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mHead;
        private TextView mName;
        private TextView mMoney;

        public CasebookInviteViewHolder(View itemView) {
            super(itemView);

            mHead = (RoundedImageView) itemView.findViewById(R.id.head);
            mName = (TextView) itemView.findViewById(R.id.name);
            mMoney = (TextView) itemView.findViewById(R.id.money);
        }
    }
}
