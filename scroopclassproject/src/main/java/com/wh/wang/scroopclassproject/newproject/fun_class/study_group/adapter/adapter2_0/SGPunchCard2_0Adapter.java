package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.newproject.view.BunchCardLineView;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/10.
 */

public class SGPunchCard2_0Adapter extends RecyclerView.Adapter<SGPunchCard2_0Adapter.PunchCardViewHolder> {
    private Context mContext;
    private List<SGDetail2_0Entity.InfoBeanX.CardStatusBean> mPunchCardList;
    private LayoutInflater mInflater;

    public SGPunchCard2_0Adapter(Context context, List<SGDetail2_0Entity.InfoBeanX.CardStatusBean> punchCardList) {
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
        SGDetail2_0Entity.InfoBeanX.CardStatusBean cardStatusBean = mPunchCardList.get(position);

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
