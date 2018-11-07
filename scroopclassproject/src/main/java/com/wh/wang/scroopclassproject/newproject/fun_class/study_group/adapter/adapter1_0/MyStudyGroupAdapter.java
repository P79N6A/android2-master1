package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0;

import android.content.Context;
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
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.MyStudyGroupEntity;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/13.
 */

public class MyStudyGroupAdapter extends RecyclerView.Adapter<MyStudyGroupAdapter.MyStudyGroupViewHolder> {
    private Context mContext;
    private List<MyStudyGroupEntity.MyclassBean> mMySGList;
    private LayoutInflater mInflater;

    public MyStudyGroupAdapter(Context context, List<MyStudyGroupEntity.MyclassBean> mySGList) {
        mContext = context;
        mMySGList = mySGList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyStudyGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyStudyGroupViewHolder(mInflater.inflate(R.layout.item_my_study_group,parent,false));
    }

    @Override
    public void onBindViewHolder(MyStudyGroupViewHolder holder, int position) {
        final MyStudyGroupEntity.MyclassBean myclassBean = mMySGList.get(position);
        holder.mTitleCourse.setText(myclassBean.getTitle());
        if (myclassBean.getImg()==null||"".equals(myclassBean.getImg())) {
            holder.mImgCourse.setImageResource(R.drawable.ivnull);
        }else{
            Glide.with(MyApplication.mApplication).load(myclassBean.getImg()).centerCrop().into(holder.mImgCourse);
        }



        if("1".equals(myclassBean.getIsfinish())){
            holder.mImmJoin.setText("查看");
            holder.mMyRank.setText("排名：第"+myclassBean.getRank()+"名");
        }else{
            if("2".equals(myclassBean.getStatus())){
                holder.mMyRank.setText("今日排名：第"+myclassBean.getRank()+"名");
                holder.mImmJoin.setText("查看");
            }else{
                holder.mMyRank.setText("尚未打卡，暂无排名");
                holder.mImmJoin.setText("立即打卡");
            }
        }
        if("1".equals(myclassBean.getType())){
            holder.mTiaozhanIcon.setVisibility(View.VISIBLE);
        }else{
            holder.mTiaozhanIcon.setVisibility(View.GONE);
        }

        holder.mCateCourse.setText("共"+myclassBean.getCate_sum()+"小节");
        if(position==mMySGList.size()-1){
            holder.mLine.setVisibility(View.GONE);
        }else{
            holder.mLine.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnStudyGroupItemClickListener!=null) {
                    mOnStudyGroupItemClickListener.onSGItemClick(myclassBean.getId(),myclassBean.getRelative_video(),myclassBean.getType());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMySGList!=null?mMySGList.size():0;
    }

    class MyStudyGroupViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView mImgCourse;
        private TextView mTitleCourse;
        private TextView mMyRank;
        private TextView mCateCourse;
        private TextView mImmJoin;
        private View mLine;
        private ImageView mTiaozhanIcon;


        public MyStudyGroupViewHolder(View itemView) {
            super(itemView);
            mLine = (View) itemView.findViewById(R.id.line);
            mImgCourse = (RoundedImageView) itemView.findViewById(R.id.img_course);
            mTitleCourse = (TextView) itemView.findViewById(R.id.title_course);
            mMyRank = (TextView) itemView.findViewById(R.id.my_rank);
            mCateCourse = (TextView) itemView.findViewById(R.id.cate_course);
            mImmJoin = (TextView) itemView.findViewById(R.id.imm_join);
            mTiaozhanIcon = (ImageView) itemView.findViewById(R.id.tiaozhan_icon);

        }
    }

    private OnStudyGroupItemClickListener mOnStudyGroupItemClickListener;

    public void setOnStudyGroupItemClickListener(OnStudyGroupItemClickListener onStudyGroupItemClickListener) {
        mOnStudyGroupItemClickListener = onStudyGroupItemClickListener;
    }

    public interface OnStudyGroupItemClickListener{
        void onSGItemClick(String pid,String vid,String type);
    }
}
