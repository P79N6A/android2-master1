package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCommonCourseEntity;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by chdh on 2018/1/22.
 */

public class CommonMemberCourseAdapter extends RecyclerView.Adapter<CommonMemberCourseAdapter.CourseViewHolder> {
    private List<CompanyCommonCourseEntity.InfoBean.FinishBean> mList;
    private LayoutInflater mInflater;

    public CommonMemberCourseAdapter(List<CompanyCommonCourseEntity.InfoBean.FinishBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(MyApplication.getApplication());
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseViewHolder(mInflater.inflate(R.layout.item_common_course,parent,false));
    }

    @Override
    public void onBindViewHolder(final CourseViewHolder holder, final int position) {
        if(holder!=null&&mList!=null&&mList.get(position)!=null){
            switch (position){
                case 0:
                    holder.mPosition.setBackgroundResource(R.drawable.pos_1);
                    holder.mPosition.setTextColor(Color.parseColor("#d74950"));
                    holder.mPosition.setTextSize(11);
                    break;
                case 1:
                    holder.mPosition.setBackgroundResource(R.drawable.pos_2);
                    holder.mPosition.setTextColor(Color.parseColor("#d74950"));
                    holder.mPosition.setTextSize(11);
                    break;
                case 2:
                    holder.mPosition.setBackgroundResource(R.drawable.pos_3);
                    holder.mPosition.setTextColor(Color.parseColor("#d74950"));
                    holder.mPosition.setTextSize(11);
                    break;
                default:
                    holder.mPosition.setTextColor(Color.parseColor("#282828"));
                    holder.mPosition.setTextSize(13);
                    break;
            }
            final CompanyCommonCourseEntity.InfoBean.FinishBean finishBean = mList.get(position);
            holder.mPosition.setText((position+1)+"");
            holder.mName.setText(finishBean.getName());
            holder.mTime.setText(finishBean.getPlayer_time()+"分钟");
            holder.mLikeNum.setText(finishBean.getZhansum()+"");
            if(StringUtils.isNotEmpty(finishBean.getAvator())){
                Glide.with(MyApplication.getApplication()).load(finishBean.getAvator()).centerCrop().into(holder.mAvatar);
            }else{
                holder.mAvatar.setImageResource(R.drawable.qiye_zwpic);
            }
            if("1".equals(finishBean.getZhan())){
                holder.mLike.setImageResource(R.drawable.learn_list_like);
            }else{
                holder.mLike.setImageResource(R.drawable.learn_list_unlike);
            }

            holder.mLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnLikeClickListener!=null){
                        mOnLikeClickListener.onLikeClick(position,holder.mLike,holder.mLikeNum,finishBean.getUser_id());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout mPosFrag;
        private TextView mPosition;
        private RoundedImageView mAvatar;
        private TextView mName;
        private TextView mTime;
        private TextView mLikeNum;
        private ImageView mLike;

        public CourseViewHolder(View itemView) {
            super(itemView);
            mPosFrag = (FrameLayout) itemView.findViewById(R.id.pos_frag);
            mPosition = (TextView)itemView. findViewById(R.id.position);
            mAvatar = (RoundedImageView)itemView. findViewById(R.id.avatar);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mLike = (ImageView) itemView.findViewById(R.id.like);
        }
    }

    private OnLikeClickListener mOnLikeClickListener;

    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        mOnLikeClickListener = onLikeClickListener;
    }

    public interface OnLikeClickListener{
        void onLikeClick(int pos,ImageView img,TextView tv,String id);
    }
}
