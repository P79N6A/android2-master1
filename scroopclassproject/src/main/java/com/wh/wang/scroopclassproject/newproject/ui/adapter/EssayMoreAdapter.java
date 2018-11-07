package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreEssayEntity;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class EssayMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int mPhoneW;
    private Context mContext;
    private List<MoreEssayEntity.ListBean> mMoreList;
    private LayoutInflater mLayoutInflater;

    public EssayMoreAdapter(Context context, List<MoreEssayEntity.ListBean> moreList) {
        mContext = context;
        mMoreList = moreList;
        mLayoutInflater = LayoutInflater.from(context);

        mPhoneW = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EssayViewHolder(mLayoutInflater.inflate(R.layout.item_home_more_essay, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MoreEssayEntity.ListBean bean = mMoreList.get(position);
        EssayViewHolder essayViewHolder = (EssayViewHolder) holder;
        Glide.with(mContext).load(bean.getImg()).centerCrop().into(essayViewHolder.mEssayImg);
        essayViewHolder.mEssayTitle.setText(bean.getTitle());
        essayViewHolder.mSeeEssayNum.setText(bean.getLearn() + "人");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnEssayMoreClickListener != null) {
                    mOnEssayMoreClickListener.onMoreClick(bean.getId(), bean.getSel_type(), bean.getType());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMoreList != null ? mMoreList.size() : 0;
    }

    class EssayViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mEssayImg;
        private TextView mEssayTitle;
        private TextView mSeeEssayNum;
        private View mCutLine;


        public EssayViewHolder(View itemView) {
            super(itemView);
            mEssayImg = (RoundedImageView) itemView.findViewById(R.id.essay_img);
            mEssayTitle = (TextView) itemView.findViewById(R.id.essay_title);
            mSeeEssayNum = (TextView) itemView.findViewById(R.id.see_essay_num);
            mCutLine = (View) itemView.findViewById(R.id.cut_line);
            mCutLine.setVisibility(View.INVISIBLE);
            mEssayTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    private OnEssayMoreClickListener mOnEssayMoreClickListener;

    public void setOnMoreClickListener(OnEssayMoreClickListener onMoreClickListener) {
        mOnEssayMoreClickListener = onMoreClickListener;
    }

    public interface OnEssayMoreClickListener {
        void onMoreClick(String id, String sel_type, String type);
    }
}
