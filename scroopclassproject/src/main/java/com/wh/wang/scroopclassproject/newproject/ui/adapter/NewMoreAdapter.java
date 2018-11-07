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
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewMoreEntity;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class NewMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int mPhoneW;
    private Context mContext;
    private List<NewMoreEntity.InfoBean.ListBean> mMoreList;
    private String mVe;
    private String mHs;

    private LayoutInflater mLayoutInflater;

    public NewMoreAdapter(Context context, List<NewMoreEntity.InfoBean.ListBean> moreList, String ve, String hs) {
        mContext = context;
        mMoreList = moreList;
        mVe = ve;
        mHs = hs;
        mLayoutInflater = LayoutInflater.from(context);

        mPhoneW = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ("2".equals(mVe)) {
            return new EssayViewHolder(mLayoutInflater.inflate(R.layout.item_home_essay_v, parent, false));
        } else {
            if ("1".equals(mHs)) {
                return new ThreeCourseViewHolder(mLayoutInflater.inflate(R.layout.item_home_v_video, parent, false));
            } else {
                return new FourCourseViewHolder(mLayoutInflater.inflate(R.layout.item_home_more_h_video, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewMoreEntity.InfoBean.ListBean bean = mMoreList.get(position);
        if (holder instanceof ThreeCourseViewHolder) {
            ThreeCourseViewHolder courseViewHolder = (ThreeCourseViewHolder) holder;
            if (position == mMoreList.size() - 1) {
                courseViewHolder.mCutLine.setVisibility(View.GONE);
            }
            courseViewHolder.mVideoTitle.setText(bean.getTitle());
            courseViewHolder.mLearnNum.setText(bean.getLearn() + "人已学习");
            courseViewHolder.mVideoTime.setText("时长:"+bean.getVideo_length() + "分钟/" + bean.getCate_sum() + "节");
            if ("0.00".equals(bean.getNew_price())) {
                courseViewHolder.mPrice.setText("免费");
            } else {
                courseViewHolder.mPrice.setText("¥" + bean.getNew_price());
            }

            courseViewHolder.mOriginator.setText(bean.getTeacher_name() + " " + bean.getDuan());
            Glide.with(mContext).load(bean.getImg()).centerCrop().into(courseViewHolder.mVideoImg);
        } else if (holder instanceof FourCourseViewHolder) {
            FourCourseViewHolder courseViewHolder = (FourCourseViewHolder) holder;
            courseViewHolder.mVideoTitle.setText(bean.getTitle());
            courseViewHolder.mVideoNum.setText(bean.getLearn() + "人已学习");
            courseViewHolder.mVideoTime.setText("时长:"+bean.getVideo_length() + "分钟/" + bean.getCate_sum() + "节");
            Glide.with(mContext).load(bean.getImg()).centerCrop().into(courseViewHolder.mVideoImg);

        } else if (holder instanceof EssayViewHolder) {
            EssayViewHolder essayViewHolder = (EssayViewHolder) holder;
            Glide.with(mContext).load(bean.getImg()).centerCrop().into(essayViewHolder.mEssayImg);
            essayViewHolder.mEssayTitle.setText(bean.getTitle());
            essayViewHolder.mSeeEssayNum.setText(bean.getLearn() + "人");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnMoreClickListener != null) {
                    mOnMoreClickListener.onMoreClick(bean.getId(), bean.getSel_type(), bean.getType());
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

    class FourCourseViewHolder extends RecyclerView.ViewHolder {
        //        private RoundedImageView mVideoImg;
//        private TextView mVideoTitle;
//        private TextView mLearnNum;
//        private TextView mVideoTime;
//        private float rate = 9/5.0f;
        private RoundedImageView mVideoImg;
        private TextView mVideoTitle;
        private TextView mVideoTime;
        private TextView mVideoNum;


        public FourCourseViewHolder(View itemView) {
            super(itemView);

            mVideoImg = (RoundedImageView) itemView.findViewById(R.id.video_img);
            mVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
            mVideoTime = (TextView) itemView.findViewById(R.id.video_time);
            mVideoNum = (TextView) itemView.findViewById(R.id.video_num);
//            mVideoImg = (RoundedImageView) itemView.findViewById(R.id.video_img);
//            mVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
//            mLearnNum = (TextView) itemView.findViewById(R.id.learn_num);
//            mVideoTime = (TextView) itemView.findViewById(R.id.video_time);
//
//            int w = (mPhoneW - DisplayUtil.dip2px(mContext,15+15+9))/2;
//            ViewGroup.LayoutParams layoutParams = mVideoImg.getLayoutParams();
//            layoutParams.width = w;
//            layoutParams.height = (int) (w/rate);
//            mVideoImg.setLayoutParams(layoutParams);
        }
    }

    class ThreeCourseViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mVideoImg;
        private TextView mVideoTitle;
        private TextView mOriginator;
        private TextView mVideoTime;
        private TextView mLearnNum;
        private TextView mPrice;
        private float rate = 7 / 9f;
        private View mCutLine;


        public ThreeCourseViewHolder(View itemView) {
            super(itemView);

            mVideoImg = (RoundedImageView) itemView.findViewById(R.id.video_img);
            mVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
            mOriginator = (TextView) itemView.findViewById(R.id.originator);
            mVideoTime = (TextView) itemView.findViewById(R.id.video_time);
            mLearnNum = (TextView) itemView.findViewById(R.id.learn_num);
            mPrice = (TextView) itemView.findViewById(R.id.price);
            mCutLine = (View) itemView.findViewById(R.id.cut_line);
            mCutLine.setVisibility(View.VISIBLE);
        }
    }

    private OnMoreClickListener mOnMoreClickListener;

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        mOnMoreClickListener = onMoreClickListener;
    }

    public interface OnMoreClickListener {
        void onMoreClick(String id, String sel_type, String type);
    }
}
