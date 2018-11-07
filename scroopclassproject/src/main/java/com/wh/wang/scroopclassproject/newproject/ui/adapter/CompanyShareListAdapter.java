package com.wh.wang.scroopclassproject.newproject.ui.adapter;

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
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShareListEntity;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by chdh on 2018/1/23.
 */

public class CompanyShareListAdapter extends RecyclerView.Adapter<CompanyShareListAdapter.ShareListViewHolder> {

    private List<ShareListEntity.InfoBean.PersonListBean> mPersonList;
    private LayoutInflater mInflater;
    public CompanyShareListAdapter(List<ShareListEntity.InfoBean.PersonListBean> personList) {
        mPersonList = personList;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public ShareListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShareListViewHolder(mInflater.inflate(R.layout.item_select_member,parent,false));
    }

    @Override
    public void onBindViewHolder(final ShareListViewHolder holder, final int position) {
        if(holder!=null&&mPersonList!=null&&mPersonList.get(position)!=null){
            final ShareListEntity.InfoBean.PersonListBean personListBean = mPersonList.get(position);
            if(StringUtils.isNotEmpty(personListBean.getAvator())){
                Glide.with(MyApplication.mApplication).load(personListBean.getAvator()).centerCrop().into(holder.mAvatar);
            }else{
                holder.mAvatar.setImageResource(R.drawable.qiye_zwpic);
            }
            holder.mName.setText(personListBean.getName());
            holder.mPosition.setText(personListBean.getBrand());
            if(personListBean.getSelect()==0){
                holder.mSelect.setImageResource(R.drawable.qiye_pay_untancan);
            }else{
                holder.mSelect.setImageResource(R.drawable.qiye_pay_taocan);
            }
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnSelectClickListener!=null){
                        mOnSelectClickListener.onSelectClick(position,personListBean.getSelect(),holder.mSelect);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mPersonList!=null?mPersonList.size():0;
    }

    class ShareListViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mPosition;
        private ImageView mSelect;
        private View mView;
        public ShareListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPosition = (TextView) itemView.findViewById(R.id.position);
            mSelect = (ImageView) itemView.findViewById(R.id.select);
        }
    }

    private OnSelectClickListener mOnSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        mOnSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener{
        void onSelectClick(int pos,int state,ImageView img);
    }
}
