package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseResultEntity;

import java.util.List;

/**
 * Created by chdh on 2018/1/9.
 */

public class PayCourseSAdapter extends RecyclerView.Adapter<PayCourseSAdapter.SuccessShareViewHolder> {
    private List<CourseResultEntity.InfoBean.OutLinkBean> mOutLinkBeen;
    private LayoutInflater mInflater;

    public PayCourseSAdapter(List<CourseResultEntity.InfoBean.OutLinkBean> outLinkBeen) {
        mOutLinkBeen = outLinkBeen;
        mInflater = LayoutInflater.from(MyApplication.getApplication());
    }

    @Override
    public SuccessShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuccessShareViewHolder(mInflater.inflate(R.layout.item_pay_success_share,parent,false));
    }

    @Override
    public void onBindViewHolder(SuccessShareViewHolder holder, int position) {
        if(holder!=null&&mOutLinkBeen!=null&&mOutLinkBeen.get(position)!=null){
            final CourseResultEntity.InfoBean.OutLinkBean outLinkBean = mOutLinkBeen.get(position);
            if(outLinkBean.getMobile()==null||"".equals(outLinkBean.getMobile().trim())){
                holder.mUrl.setText(outLinkBean.getLink());
                holder.mShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnShareUrlClickListener!=null){
                            mOnShareUrlClickListener.onShareUrlClick(outLinkBean.getLink(),outLinkBean.getMobile());
                        }
                    }
                });
            }else{
                holder.mUrl.setText("领取人手机号："+outLinkBean.getMobile());
                holder.mShare.setOnClickListener(null);
                holder.mShare.setText("已领取");
                holder.mShare.setBackgroundResource(R.drawable.apply_share_no_bg);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mOutLinkBeen==null?0:mOutLinkBeen.size();
    }

    class SuccessShareViewHolder extends RecyclerView.ViewHolder{
        private TextView mUrl;
        private TextView mShare;

        public SuccessShareViewHolder(View itemView) {
            super(itemView);
            mUrl = (TextView) itemView.findViewById(R.id.url);
            mShare = (TextView) itemView.findViewById(R.id.share);
        }
    }
    private OnShareUrlClickListener mOnShareUrlClickListener;

    public void setOnShareUrlClickListener(OnShareUrlClickListener onShareUrlClickListener) {
        mOnShareUrlClickListener = onShareUrlClickListener;
    }

    public interface OnShareUrlClickListener{
        void onShareUrlClick(String url,String tel);
    }
}
