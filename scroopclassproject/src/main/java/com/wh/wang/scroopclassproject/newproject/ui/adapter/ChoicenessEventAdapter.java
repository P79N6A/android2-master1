package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CasebookInviteActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.OpenClassActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */

public class ChoicenessEventAdapter extends RecyclerView.Adapter<ChoicenessEventAdapter.EventViewHolder> {
    private Context mContext;
    private List<MainCourseEntity.EventsBean> mEventList;
    private LayoutInflater mLayoutInflater;
    private int mIsSys = 0;
    public ChoicenessEventAdapter(Context mContext, List<MainCourseEntity.EventsBean> mEventList,int isSys) {
        this.mContext = mContext;
        this.mEventList = mEventList;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        mIsSys = isSys;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mIsSys==0){
            return new EventViewHolder(mLayoutInflater.inflate(R.layout.item_choiceness_event,parent,false));
        }else{
            return new EventViewHolder(mLayoutInflater.inflate(R.layout.item_system_event,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        if(holder!=null){
            Glide.with(MyApplication.mApplication)
                    .load(mEventList.get(position).getImg())
//                    .transform(new GlideRoundTransform(MyApplication.mApplication,DisplayUtil.dip2px(MyApplication.mApplication,3)))
//                    .placeholder(R.drawable.ivnull)
                    .into(holder.mItemEventImg);
            holder.mItemEventImg.setCornerRadius(DisplayUtil.dip2px(mContext,3));
            holder.mItemEventImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    if("5".equals(mEventList.get(position).getType())){
                        intent = new Intent(mContext, OpenClassActivity.class);
                        intent.putExtra("id",mEventList.get(position).getId());
                        intent.putExtra("type",mEventList.get(position).getType());
                    }else if("8".equals(mEventList.get(position).getType())){
                        intent = new Intent(mContext, CasebookInviteActivity.class);
                        intent.putExtra("event_id",mEventList.get(position).getId());
                    }else{
                        intent = new Intent(mContext, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",mEventList.get(position).getId());//mEventList.get(position).getId()
                    }
                    if(intent!=null){
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mItemEventImg;

        public EventViewHolder(View itemView) {
            super(itemView);
            mItemEventImg = (RoundedImageView) itemView.findViewById(R.id.item_event_img);

        }
    }
}
