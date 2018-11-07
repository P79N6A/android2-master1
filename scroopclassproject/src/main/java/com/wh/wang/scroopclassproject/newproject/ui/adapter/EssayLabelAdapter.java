package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/3.
 */

public class EssayLabelAdapter extends RecyclerView.Adapter<EssayLabelAdapter.EssayLabelViewHolder> {

    private Context mContext;
    private List<NewHomeEntity.InfoBean.YueduBean> mYueduBeanList;
    private LayoutInflater mLayoutInflater;
    private int mCurrentPos = 0;
    public EssayLabelAdapter(Context context, List<NewHomeEntity.InfoBean.YueduBean> yueduBeanList) {
        mContext = context;
        mYueduBeanList = yueduBeanList;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public EssayLabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EssayLabelViewHolder(mLayoutInflater.inflate(R.layout.item_home_essay_label,parent,false));
    }

    @Override
    public void onBindViewHolder(EssayLabelViewHolder holder, final int position) {
        NewHomeEntity.InfoBean.YueduBean yueduBean = mYueduBeanList.get(position);
        TextPaint paint = holder.mEssayTitle.getPaint();
        if(yueduBean.isCheck()){
            mCurrentPos = position;
            holder.mEssayTitle.setTextColor(mContext.getResources().getColor(R.color.video_title_v));
            holder.mEssayTitle.setTextSize(16);
            paint.setFakeBoldText(true);
        }else{
            holder.mEssayTitle.setTextColor(mContext.getResources().getColor(R.color.video_title));
            holder.mEssayTitle.setTextSize(14);
            paint.setFakeBoldText(false);
        }
        holder.mEssayTitle.setText(yueduBean.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==mCurrentPos){
                    return;
                }
                mYueduBeanList.get(position).setCheck(true);
                mYueduBeanList.get(mCurrentPos).setCheck(false);
                EssayLabelAdapter.this.notifyItemChanged(position);
                EssayLabelAdapter.this.notifyItemChanged(mCurrentPos);
                mCurrentPos = position;
                if (mOnEssayLabelItemClickListener!=null) {
                    mOnEssayLabelItemClickListener.onEssayLabelClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mYueduBeanList!=null?mYueduBeanList.size():0;
    }

    class EssayLabelViewHolder extends RecyclerView.ViewHolder{
        private TextView mEssayTitle;

        public EssayLabelViewHolder(View itemView) {
            super(itemView);
            mEssayTitle = (TextView) itemView.findViewById(R.id.essay_title);
        }
    }

    private OnEssayLabelItemClickListener mOnEssayLabelItemClickListener;

    public void setOnEssayLabelItemClickListener(OnEssayLabelItemClickListener onEssayLabelItemClickListener) {
        mOnEssayLabelItemClickListener = onEssayLabelItemClickListener;
    }

    public interface OnEssayLabelItemClickListener{
        void onEssayLabelClick(int pos);
    }

}
