package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponListBean;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/24.
 */

public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.CouponViewHolder> {

    private Context mContext;
    private List<CouponListBean> mCouponList;
    private LayoutInflater mLayoutInflater;
    private String mStatus; //1/其他 未使用 2 已使用 3 已失效
    public CouponListAdapter(Context context, List<CouponListBean> couponList,String status) {
        mContext = context;
        mCouponList = couponList;
        mLayoutInflater = LayoutInflater.from(mContext);
        mStatus = status;
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponViewHolder(mLayoutInflater.inflate(R.layout.item_coupon,parent,false));
    }

    @Override
    public void onBindViewHolder(final CouponViewHolder holder, int position) {
        final CouponListBean infoBean = mCouponList.get(position);
        holder.mCouponName.setText(infoBean.getName());
        if(StringUtils.isNotEmpty(infoBean.getMark())){
            String[] rules = infoBean.getMark().split("#");
            for (int i = 0; i < rules.length; i++) {
                if(StringUtils.isNotEmpty(rules[i])){
                    TextView tv = new TextView(mContext);
                    tv.setTextColor(mContext.getResources().getColor(R.color.video_title));
                    tv.setTextSize(12);
                    tv.setText(rules[i]);
                    holder.mCouponRuleDetail.addView(tv);
                }

            }
        }
        holder.mCouponTime.setText("有效期至"+infoBean.getEnd_time());
        holder.mCouponPrice.setText(infoBean.getPrice());
        holder.mCouponCondition.setText("满"+infoBean.getSill_price()+"元减"+infoBean.getPrice()+"元");
        holder.mCouponRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infoBean.getIsShow()==null||"0".equals(infoBean.getIsShow())){
                    infoBean.setIsShow("1");
                    holder.mCouponRuleDetail.setVisibility(View.VISIBLE);
                }else{
                    infoBean.setIsShow("0");
                    holder.mCouponRuleDetail.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCouponList!=null?mCouponList.size():0;
    }

    class CouponViewHolder extends RecyclerView.ViewHolder{
        private TextView mCouponName;
        private TextView mCouponPrice;
        private TextView mCouponTime;
        private TextView mCouponRule;
        private TextView mCouponCondition;
        private LinearLayout mCouponRuleDetail;
        private ImageView mCouponStatus;
        private TextView mCouponUnit;


        public CouponViewHolder(View itemView) {
            super(itemView);
            mCouponName = (TextView) itemView.findViewById(R.id.coupon_name);
            mCouponPrice = (TextView) itemView.findViewById(R.id.coupon_price);
            mCouponTime = (TextView) itemView.findViewById(R.id.coupon_time);
            mCouponRule = (TextView) itemView.findViewById(R.id.coupon_rule);
            mCouponCondition = (TextView) itemView.findViewById(R.id.coupon_condition);
            mCouponRuleDetail = (LinearLayout) itemView.findViewById(R.id.coupon_rule_detail);
            mCouponStatus = (ImageView) itemView.findViewById(R.id.coupon_status);
            mCouponUnit = (TextView) itemView.findViewById(R.id.coupon_unit);
            if("2".equals(mStatus)){
                mCouponStatus.setVisibility(View.VISIBLE);
                mCouponStatus.setImageResource(R.drawable.coupon_used);
            }else if("3".equals(mStatus)){
                mCouponStatus.setVisibility(View.VISIBLE);
                mCouponStatus.setImageResource(R.drawable.coupon_past_due);
                mCouponName.setTextColor(mContext.getResources().getColor(R.color.video_title));
                mCouponPrice.setTextColor(mContext.getResources().getColor(R.color.video_title));
                mCouponCondition.setTextColor(mContext.getResources().getColor(R.color.video_title));
                mCouponUnit.setTextColor(mContext.getResources().getColor(R.color.video_title));
            }else{
                mCouponStatus.setVisibility(View.GONE);
            }
        }
    }
}
