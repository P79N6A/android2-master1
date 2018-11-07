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
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/28.
 */

public class HomeVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NewHomeEntity.InfoBean.CourseBean.CourseDetailBean> mCourseBeanList;
    private String hs;
    private String ve;
    private LayoutInflater mLayoutInflater;
    private int mPhoneW;
    public HomeVideoAdapter(Context context, List<NewHomeEntity.InfoBean.CourseBean.CourseDetailBean> courseBeanList, String hs, String ve) {
        mContext = context;
        mCourseBeanList = courseBeanList;
        this.hs = hs;
        this.ve = ve;
        mLayoutInflater = LayoutInflater.from(context);
        mPhoneW = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if("2".equals(ve)){
            return new EssayViewHolder(mLayoutInflater.inflate(R.layout.item_home_essay_v,parent,false));
        }else{
            if("1".equals(hs)){
                return new ThreeCourseViewHolder(mLayoutInflater.inflate(R.layout.item_home_v_video,parent,false));
            }else{
                return new FourCourseViewHolder(mLayoutInflater.inflate(R.layout.item_home_h_video,parent,false));
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewHomeEntity.InfoBean.CourseBean.CourseDetailBean courseBean = mCourseBeanList.get(position);
        if(holder instanceof ThreeCourseViewHolder){

            ThreeCourseViewHolder courseViewHolder = (ThreeCourseViewHolder) holder;

            if(position==mCourseBeanList.size()-1){
                courseViewHolder.mCutLine.setVisibility(View.GONE);
            }
            courseViewHolder.mVideoTitle.setText(courseBean.getTitle());
            courseViewHolder.mLearnNum.setText(courseBean.getLearn()+"人已学");
            courseViewHolder.mVideoTime.setText("时长:"+courseBean.getVideo_length()+"分钟/"+courseBean.getCate_sum()+"节");
            if("0.00".equals(courseBean.getNew_price())){
                courseViewHolder.mPrice.setText("免费");
            }else{
                courseViewHolder.mPrice.setText("¥"+courseBean.getNew_price());
            }

            courseViewHolder.mOriginator.setText(courseBean.getTeacher_name()+" "+courseBean.getDuan());
            Glide.with(mContext).load(courseBean.getImg()).centerCrop().into(courseViewHolder.mVideoImg);
        }else if(holder instanceof FourCourseViewHolder){
            FourCourseViewHolder courseViewHolder = (FourCourseViewHolder) holder;
            courseViewHolder.mVideoTitle.setText(courseBean.getTitle());
            courseViewHolder.mLearnNum.setText(courseBean.getLearn()+"人已学");
            courseViewHolder.mVideoTime.setText(courseBean.getVideo_length()+"分钟/"+courseBean.getCate_sum()+"节");
            Glide.with(mContext).load(courseBean.getImg()).centerCrop().into(courseViewHolder.mVideoImg);

        }else if(holder instanceof EssayViewHolder){
            EssayViewHolder essayViewHolder = (EssayViewHolder) holder;
            Glide.with(mContext).load(courseBean.getImg()).centerCrop().into(essayViewHolder.mEssayImg);
            essayViewHolder.mEssayTitle.setText(courseBean.getTitle());
            essayViewHolder.mSeeEssayNum.setText(courseBean.getLearn()+"人");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnVideoItemClickListener!=null){
                    mOnVideoItemClickListener.onVideoItemClick(courseBean.getId(),courseBean.getSel_type(),courseBean.getType());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourseBeanList!=null?mCourseBeanList.size():0;
    }

    class EssayViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mEssayImg;
        private TextView mEssayTitle;
        private TextView mSeeEssayNum;

        public EssayViewHolder(View itemView) {
            super(itemView);
            mEssayImg = (RoundedImageView) itemView.findViewById(R.id.essay_img);
            mEssayTitle = (TextView) itemView.findViewById(R.id.essay_title);
            mSeeEssayNum = (TextView) itemView.findViewById(R.id.see_essay_num);
            mEssayTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    class FourCourseViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mVideoImg;
        private TextView mVideoTitle;
        private TextView mLearnNum;
        private TextView mVideoTime;
        private float rate = 9/5.0f;
        public FourCourseViewHolder(View itemView) {
            super(itemView);

            mVideoImg = (RoundedImageView) itemView.findViewById(R.id.video_img);
            mVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
            mLearnNum = (TextView) itemView.findViewById(R.id.learn_num);
            mVideoTime = (TextView) itemView.findViewById(R.id.video_time);

            int w = (mPhoneW - DisplayUtil.dip2px(mContext,15+15+9))/2;
            ViewGroup.LayoutParams layoutParams = mVideoImg.getLayoutParams();
            layoutParams.width = w;
            layoutParams.height = (int) (w/rate);
            mVideoImg.setLayoutParams(layoutParams);
        }
    }

    class ThreeCourseViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mVideoImg;
        private TextView mVideoTitle;
        private TextView mOriginator;
        private TextView mVideoTime;
        private TextView mLearnNum;
        private TextView mPrice;
        private float rate = 7/9f;
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

        }
    }

    private OnVideoItemClickListener mOnVideoItemClickListener;

    public void setOnVideoItemClickListener(OnVideoItemClickListener onVideoItemClickListener) {
        mOnVideoItemClickListener = onVideoItemClickListener;
    }

    public interface OnVideoItemClickListener{
        void onVideoItemClick(String id,String sel_type,String type);
    }
}
