package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayLabelEntity;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/3.
 */

public class EssayMoreLabelAdapter extends RecyclerView.Adapter<EssayMoreLabelAdapter.LabelViewHolder>{
    private Context mContext;
    private List<EssayLabelEntity> mEssayLabelEntities;
    private LayoutInflater mLayoutInflater;
    private int mLabelNum = 4;
    private int mCurrentPos = 0;
    private int mMaxItem = 4;

    private int mPhoneW;
    private double mViewRate = 1/4f;

    public EssayMoreLabelAdapter(Context context, List<EssayLabelEntity> essayLabelEntities) {
        mContext = context;
        mEssayLabelEntities = essayLabelEntities;
        mLayoutInflater = LayoutInflater.from(context);

        if(mEssayLabelEntities.size()>mMaxItem){
            mLabelNum = mMaxItem;
        }else{
            mLabelNum = mEssayLabelEntities.size();
        }
    }

    @Override
    public LabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LabelViewHolder(mLayoutInflater.inflate(R.layout.item_home_essay_label,parent,false));
    }

    @Override
    public void onBindViewHolder(LabelViewHolder holder, final int position) {
        final EssayLabelEntity essayLabelEntity = mEssayLabelEntities.get(position);
        if(essayLabelEntity.isCheck()){
            holder.mEssayTitle.setTextColor(mContext.getResources().getColor(R.color.main_color));
            holder.mLine.setVisibility(View.VISIBLE);
        }else{
            holder.mEssayTitle.setTextColor(mContext.getResources().getColor(R.color.video_title_v));
            holder.mLine.setVisibility(View.GONE);
        }
        holder.mEssayTitle.setText(essayLabelEntity.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==mCurrentPos)
                    return;
//                mEssayLabelEntities.get(position).setCheck(true);
//                mEssayLabelEntities.get(mCurrentPos).setCheck(false);
//                notifyItemChanged(position);
//                notifyItemChanged(mCurrentPos);
//                mCurrentPos = position;
                if(mOnEssayLabelClickListener!=null){
                    mOnEssayLabelClickListener.onEssayLabelClick(position,essayLabelEntity.getType());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEssayLabelEntities!=null?mEssayLabelEntities.size():0;
    }

    public void notifyPos(int item) {
        mEssayLabelEntities.get(item).setCheck(true);
        mEssayLabelEntities.get(mCurrentPos).setCheck(false);
        notifyItemChanged(item);
        notifyItemChanged(mCurrentPos);
        mCurrentPos = item;
    }

    class LabelViewHolder extends RecyclerView.ViewHolder{
        private TextView mEssayTitle;
        private FrameLayout mLine;
        private View mShowLine;

        private int mItemW;
        public LabelViewHolder(View itemView) {
            super(itemView);
            mEssayTitle = (TextView) itemView.findViewById(R.id.essay_title);
            mLine = (FrameLayout) itemView.findViewById(R.id.line);
            mShowLine = (View) itemView.findViewById(R.id.show_line);
            mEssayTitle.setTextSize(14);
            mEssayTitle.setTextColor(mContext.getResources().getColor(R.color.video_title_v));

            if(mEssayLabelEntities.size()>mMaxItem){
                mItemW = (ScreenUtils.getScreenWidth(MyApplication.mApplication)-
                        ScreenUtils.getScreenWidth(MyApplication.mApplication)/(mMaxItem+1)/2)/mMaxItem;
            }else{
                mItemW = ScreenUtils.getScreenWidth(MyApplication.mApplication)/mLabelNum;
            }

            ViewGroup.LayoutParams layoutParams = mEssayTitle.getLayoutParams();
            layoutParams.width = mItemW;
            mEssayTitle.setLayoutParams(layoutParams);

            int LineW = mItemW;
            ViewGroup.LayoutParams lineParams = mLine.getLayoutParams();
            lineParams.width = LineW;
            mLine.setLayoutParams(lineParams);
            ViewGroup.LayoutParams showLineParams = mShowLine.getLayoutParams();
            showLineParams.width = (int) (LineW * mViewRate);
            mShowLine.setLayoutParams(showLineParams);
            mLine.setVisibility(View.VISIBLE);

        }
    }

    private OnEssayLabelClickListener mOnEssayLabelClickListener;

    public void setOnEssayLabelClickListener(OnEssayLabelClickListener onEssayLabelClickListener) {
        mOnEssayLabelClickListener = onEssayLabelClickListener;
    }

    public interface OnEssayLabelClickListener{
        void onEssayLabelClick(int pos,String type);
    }
}
