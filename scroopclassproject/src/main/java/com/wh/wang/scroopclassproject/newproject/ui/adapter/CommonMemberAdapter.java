package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.callback.OnMemberItemClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MemberEntity;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by chdh on 2018/1/19.
 */

public class CommonMemberAdapter extends RecyclerView.Adapter<CommonMemberAdapter.MemberViewHolder> {
    private List<MemberEntity.InfoBean.VListBean> mVListBeanList;
    private LayoutInflater mInflater;
    private int mStatus;

    public CommonMemberAdapter(List<MemberEntity.InfoBean.VListBean> listBeen, int status) {
        mVListBeanList = listBeen;
        mStatus = status;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberViewHolder(mInflater.inflate(R.layout.item_member, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, final int position) {
        if (holder != null && mVListBeanList != null && mVListBeanList.get(position) != null) {
            if (mStatus == 0) {
                holder.mRead.setText("设置管理员");
            } else if (mStatus == 1) {
                holder.mDel.setVisibility(View.VISIBLE);
                holder.mRead.setVisibility(View.GONE);
            }

            final MemberEntity.InfoBean.VListBean vListBean = mVListBeanList.get(position);
            holder.mName.setText(vListBean.getName());
            if(StringUtils.isNotEmpty(vListBean.getAvator())){
                Glide.with(MyApplication.mApplication).load(vListBean.getAvator()).error(R.drawable.qiye_zwpic).centerCrop().into(holder.mAvatar);
            }else{
                holder.mAvatar.setImageResource(R.drawable.qiye_zwpic);
            }
            holder.mTime.setText(vListBean.getPosition());
            holder.mRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnManagerClickListener != null) {
                        mOnManagerClickListener.onManagerClick(vListBean.getName(), position, vListBean.getId());
                    }
                }
            });
            holder.mDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnDelMemberClickListener!=null){
                        mOnDelMemberClickListener.onDelClick(position,vListBean.getId());
                    }
                }
            });
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.onItemClick(position,vListBean.getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mVListBeanList != null ? mVListBeanList.size() : 0;
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mTime;
        private Button mRead;
        private Button mDel;
        private RelativeLayout mMenu;
        private RelativeLayout mItem;

        public MemberViewHolder(View itemView) {
            super(itemView);
            mItem = (RelativeLayout) itemView.findViewById(R.id.item);
            mAvatar = (RoundedImageView) itemView.findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mRead = (Button) itemView.findViewById(R.id.read);
            mDel = (Button) itemView.findViewById(R.id.del);
            mMenu = (RelativeLayout) itemView.findViewById(R.id.menu);
        }
    }

    private OnManagerClickListener mOnManagerClickListener;

    public void setOnManagerClickListener(OnManagerClickListener onManagerClickListener) {
        mOnManagerClickListener = onManagerClickListener;
    }

    public interface OnManagerClickListener {
        void onManagerClick(String name, int pos, String id);
    }

    private OnDelMemberClickListener mOnDelMemberClickListener;

    public void setOnDelMemberClickListener(OnDelMemberClickListener onDelMemberClickListener) {
        mOnDelMemberClickListener = onDelMemberClickListener;
    }

    public interface OnDelMemberClickListener {
        void onDelClick(int pos, String id);
    }

    private OnMemberItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnMemberItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
