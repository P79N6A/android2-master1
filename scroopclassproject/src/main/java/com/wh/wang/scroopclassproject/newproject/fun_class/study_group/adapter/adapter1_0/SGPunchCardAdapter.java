package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter1_0;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetailEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.newproject.view.BunchCardLineView;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/10.
 */

public class SGPunchCardAdapter extends RecyclerView.Adapter<SGPunchCardAdapter.PunchCardViewHolder> {
    private Context mContext;
    private List<SGDetailEntity.InfoBeanX.CardStatusBean> mPunchCardList;
    private LayoutInflater mInflater;

    public SGPunchCardAdapter(Context context, List<SGDetailEntity.InfoBeanX.CardStatusBean> punchCardList) {
        mContext = context;
        mPunchCardList = punchCardList;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public PunchCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PunchCardViewHolder(mInflater.inflate(R.layout.item_punch_card, parent, false));
    }

    @Override
    public void onBindViewHolder(PunchCardViewHolder holder, int position) {


        if (position == getItemCount() - 1) {
            holder.mPunchCardIcon.setGift(true);
        } else {
            holder.mPunchCardIcon.setGift(false);
        }
//        if(mPunchCardList!=null&&mPunchCardList.size()>position){
        SGDetailEntity.InfoBeanX.CardStatusBean cardStatusBean = mPunchCardList.get(position);

        //显示当前天 -1为活动未开始 不显示
        if ("1".equals(cardStatusBean.getGreen())) {
            holder.mPunchCardIcon.drawToday(true);
        } else {
            holder.mPunchCardIcon.drawToday(false);
        }

        if ("2".equals(cardStatusBean.getStatus())) {
            holder.mImg.setImageResource(R.drawable.xuanzhong);
        } else {
            holder.mImg.setImageResource(R.drawable.weixuanzhong);
        }

//        }
        holder.mPunchCardDay.setText((position + 1) + "天");

    }

    @Override
    public int getItemCount() {
        return mPunchCardList.size();
    }

    class PunchCardViewHolder extends RecyclerView.ViewHolder {
        private TextView mPunchCardDay;
        private BunchCardLineView mPunchCardIcon;
        private ImageView mImg;


        public PunchCardViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.img);
            mPunchCardDay = (TextView) itemView.findViewById(R.id.punch_card_day);
            mPunchCardIcon = (BunchCardLineView) itemView.findViewById(R.id.punch_card_icon);

            if (getItemCount() <= 7) {
                int SrceenW = ScreenUtils.getScreenWidth(mContext);
                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                layoutParams.width = (int) ((SrceenW - DisplayUtil.dip2px(mContext, 30)) / (getItemCount() * 1.0f));
                itemView.setLayoutParams(layoutParams);
            }

        }
    }
}
