package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class HomeActionAdapter extends RecyclerView.Adapter<HomeActionAdapter.ActionBannerViewHolder> {
    private Context mContext;
    private List<NewHomeEntity.InfoBean.EventBean.ListBean> mActionBeanList;
    private LayoutInflater mLayoutInflater;

    public HomeActionAdapter(Context context, List<NewHomeEntity.InfoBean.EventBean.ListBean> actionBeanList) {
        mContext = context;
        mActionBeanList = actionBeanList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ActionBannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActionBannerViewHolder(mLayoutInflater.inflate(R.layout.item_home_action_banner,parent,false));
    }

    @Override
    public void onBindViewHolder(ActionBannerViewHolder holder, int position) {
        final NewHomeEntity.InfoBean.EventBean.ListBean bean = mActionBeanList.get(position);
        Glide.with(mContext).load(bean.getImg()).centerCrop().into(holder.mActionBanner);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnActionItemClickListener!=null){
                    mOnActionItemClickListener.onActionItemClick(bean.getId(),bean.getSel_type(),bean.getType());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mActionBeanList!=null?mActionBeanList.size():0;
    }

    class ActionBannerViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mActionBanner;
        private float rate = 345/100f;
        public ActionBannerViewHolder(View itemView) {
            super(itemView);

            int w = ScreenUtils.getScreenWidth(mContext)- DisplayUtil.dip2px(mContext,30);
            mActionBanner = (RoundedImageView) itemView.findViewById(R.id.action_banner);
            ViewGroup.LayoutParams layoutParams = mActionBanner.getLayoutParams();
            layoutParams.height = (int) (w / rate);
            mActionBanner.setLayoutParams(layoutParams);
        }
    }

    private OnActionItemClickListener mOnActionItemClickListener;

    public void setOnActionItemClickListener(OnActionItemClickListener onActionItemClickListener) {
        mOnActionItemClickListener = onActionItemClickListener;
    }

    public interface OnActionItemClickListener{
        void onActionItemClick(String id,String sel_type,String type);
    }
}
