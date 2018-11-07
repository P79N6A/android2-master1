package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponListBean;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/24.
 */

public class SelectCouponListAdapter extends RecyclerView.Adapter<SelectCouponListAdapter.CouponViewHolder> {

    private Context mContext;
    private List<CouponListBean> mCouponList;
    private LayoutInflater mLayoutInflater;
    private String mCoupon;
    private double mCoursePrice;
    public SelectCouponListAdapter(Context context, List<CouponListBean> couponList,String coupon,String course_price) {
        mContext = context;
        mCouponList = couponList;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCoupon = coupon;
        try {
            mCoursePrice = Double.parseDouble(course_price);
        }catch (Exception e){
            mCoursePrice = 0;
        }

    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponViewHolder(mLayoutInflater.inflate(R.layout.item_coupon,parent,false));
    }

    @Override
    public void onBindViewHolder(final CouponViewHolder holder, final int position) {
        final CouponListBean couponListBean = mCouponList.get(position);
        holder.mCouponName.setText(couponListBean.getName());

        holder.mCouponTime.setText("有效期至"+couponListBean.getEnd_time());
        holder.mCouponPrice.setText(couponListBean.getPrice());
        holder.mCouponCondition.setText("满"+couponListBean.getSill_price()+"元减"+couponListBean.getPrice()+"元");
        if((couponListBean.getId()).equals(mCoupon)){
           holder.mCouponChecked.setVisibility(View.VISIBLE);
        }else{

            holder.mCouponChecked.setVisibility(View.GONE);
        }
        if(StringUtils.isNotEmpty(couponListBean.getMark())){
            String []rules = couponListBean.getMark().split("#");
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

        holder.mCouponRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(couponListBean.getIsShow()==null||"0".equals(couponListBean.getIsShow())){
                    couponListBean.setIsShow("1");
                    holder.mCouponRuleDetail.setVisibility(View.VISIBLE);
                }else{
                    couponListBean.setIsShow("0");
                    holder.mCouponRuleDetail.setVisibility(View.GONE);
                }
            }
        });
        int sillPrice = 0;
        try {
            sillPrice = Integer.parseInt(couponListBean.getSill_price());
        }catch (Exception e){
            sillPrice = 0;
        }
        Log.e("DH_COUPON","price:"+mCoursePrice+"  "+sillPrice);
        if(mCoursePrice>=sillPrice){
            holder.mCouponName.setTextColor(mContext.getResources().getColor(R.color.video_title_v));
            holder.mCouponPrice.setTextColor(mContext.getResources().getColor(R.color.main_color));
            holder.mCouponCondition.setTextColor(mContext.getResources().getColor(R.color.video_title_v));
            holder.mCouponUnit.setTextColor(mContext.getResources().getColor(R.color.main_color));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnSelectCouponListener!=null){
                        mOnSelectCouponListener.onCouponClick(position);
                    }
                }
            });
        }else{
            holder.mCouponName.setTextColor(mContext.getResources().getColor(R.color.video_title));
            holder.mCouponPrice.setTextColor(mContext.getResources().getColor(R.color.video_title));
            holder.mCouponCondition.setTextColor(mContext.getResources().getColor(R.color.video_title));
            holder.mCouponUnit.setTextColor(mContext.getResources().getColor(R.color.video_title));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyApplication.mApplication, "此优惠券未达减免要求", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mCouponList!=null?mCouponList.size():0;
    }

    class CouponViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mItemCoupon;
        private TextView mCouponName;
        private TextView mCouponPrice;
        private TextView mCouponTime;
        private TextView mCouponRule;
        private TextView mCouponCondition;
        private LinearLayout mCouponRuleDetail;
        private RoundedImageView mCouponChecked;
        private TextView mCouponUnit;



        public CouponViewHolder(View itemView) {
            super(itemView);
            mCouponName = (TextView) itemView.findViewById(R.id.coupon_name);
            mCouponPrice = (TextView) itemView.findViewById(R.id.coupon_price);
            mCouponTime = (TextView) itemView.findViewById(R.id.coupon_time);
            mCouponRule = (TextView) itemView.findViewById(R.id.coupon_rule);
            mCouponCondition = (TextView) itemView.findViewById(R.id.coupon_condition);
            mCouponRuleDetail = (LinearLayout) itemView.findViewById(R.id.coupon_rule_detail);
            mItemCoupon = (RelativeLayout) itemView.findViewById(R.id.item_coupon);
            mItemCoupon.setBackgroundResource(R.drawable.shape_coupon_bg_line);
            mCouponChecked = (RoundedImageView) itemView.findViewById(R.id.coupon_checked);
            mCouponUnit = (TextView) itemView.findViewById(R.id.coupon_unit);

        }
    }

    private OnSelectCouponListener mOnSelectCouponListener;

    public void setOnSelectCouponListener(OnSelectCouponListener onSelectCouponListener) {
        mOnSelectCouponListener = onSelectCouponListener;
    }

    public interface OnSelectCouponListener{
        void onCouponClick(int pos);
    }
}
