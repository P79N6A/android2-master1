package com.wh.wang.scroopclassproject.newproject.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;

import java.math.BigDecimal;

import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;


/**
 * Created by chdh on 2017/3/21.
 */

public class PopPay extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private WindowManager mWindowManager;
    private DisplayMetrics mMetrics;
    public static final int H = 312;
    private TextView mPayCourseTitle;
    private TextView mPayCoursePrice;
    private RelativeLayout mPayWeichat;
    private ImageView mWeichatSelect;
    private RelativeLayout mPayAlipay;
    private ImageView mAlipaySelect;
    private TextView mPayCancel;
    private TextView mPayOk;
    private TextView mReminder;
    private RelativeLayout mCoupon;
    private TextView mCouponInfo;

    private double mPrice;
    //支付方式 2：支付宝 3：微信
    private int PAY_WAY = WAY_WEICHAT;
    private int[] mPaySelect = {R.drawable.pay_unselect, R.drawable.pay_select};
    private OnPayClickListener mOnPayClickListener;

    public void setOnPayClickListener(OnPayClickListener mOnPayClickListener) {
        this.mOnPayClickListener = mOnPayClickListener;
    }

    public PopPay(final Context context, String title, String price) {
        super(context);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        View view = mLayoutInflater.inflate(R.layout.pop_pay, null, false);
        initView(view);
        mPayCourseTitle.setText(title);
        mPrice = Double.parseDouble(price);
        mPayCoursePrice.setText(price + "元");
        String reminder = context.getResources().getString(R.string.reminder);
        int index = reminder.indexOf("4");
        SpannableString spanStr = new SpannableString(reminder);
        spanStr.setSpan(new ForegroundColorSpan(Color.parseColor("#8DC63F")), index, reminder.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spanStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Uri uri = Uri.parse("tel:4009003650");
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "您已禁止拨打电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                context.startActivity(intent);
            }
        }, index, reminder.length() - 1, Spanned.SPAN_MARK_MARK);
        spanStr.setSpan(new NoUnderlineSpan(), index, reminder.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mReminder.setText(spanStr);
        mReminder.setMovementMethod(LinkMovementMethod.getInstance());

        initListener();
        initBaseInfo(view);


    }

    private void initListener() {
        mPayWeichat.setOnClickListener(this);
        mPayAlipay.setOnClickListener(this);
        mPayCancel.setOnClickListener(this);
        mPayOk.setOnClickListener(this);
        mCoupon.setOnClickListener(this);
    }

    private void initView(View view) {
        mPayCourseTitle = (TextView) view.findViewById(R.id.pay_course_title);
        mPayCoursePrice = (TextView) view.findViewById(R.id.pay_course_price);
        mPayWeichat = (RelativeLayout) view.findViewById(R.id.pay_weichat);
        mWeichatSelect = (ImageView) view.findViewById(R.id.weichat_select);
        mPayAlipay = (RelativeLayout) view.findViewById(R.id.pay_alipay);
        mAlipaySelect = (ImageView) view.findViewById(R.id.alipay_select);
        mPayCancel = (TextView) view.findViewById(R.id.pay_cancel);
        mPayOk = (TextView) view.findViewById(R.id.pay_ok);
        mReminder = (TextView) view.findViewById(R.id.reminder);

        mCoupon = (RelativeLayout) view.findViewById(R.id.coupon);
        mCouponInfo = (TextView) view.findViewById(R.id.coupon_info);

    }

    private void initBaseInfo(View view) {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(mMetrics);
        int heightPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, H, mMetrics);
        int screenWidth = mMetrics.widthPixels;
        this.setWidth(screenWidth);
        this.setHeight(heightPX);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.popAnim);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(screenWidth, heightPX);
        view.setLayoutParams(layoutParams);
        this.setContentView(view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_weichat:
                PAY_WAY = WAY_WEICHAT;
                changePayWay(PAY_WAY);
                break;
            case R.id.pay_alipay:
                PAY_WAY = WAY_ALIPAY;
                changePayWay(PAY_WAY);
                break;
            case R.id.pay_cancel:
                this.dismiss();
                break;
            case R.id.pay_ok:
                if (mOnPayClickListener != null) {
                    mOnPayClickListener.onPayClick(PAY_WAY);
                }
                break;
            case R.id.coupon:
                if (mOnPayClickListener != null) {
                    mOnPayClickListener.onSelectCouponClick();
                }
                break;
        }
    }

    public void changePayWay(int payWay) {
        mWeichatSelect.setImageResource(mPaySelect[payWay == WAY_WEICHAT ? 1 : 0]);
        mAlipaySelect.setImageResource(mPaySelect[payWay == WAY_ALIPAY ? 1 : 0]);
    }

    public void setCouponInfo(String price,String couponID) {
        if(!"".equals(couponID)){
            double coupon_price = 0;
            try {
                coupon_price = Double.parseDouble(price);
            } catch (Exception e) {
                coupon_price = 0;
            }
            mPayCoursePrice.setText(sub(mPrice,coupon_price) + "元");
            mCouponInfo.setText(setCouponText("-" + price + "元", "#FFAD1E"));
        }else{
            mPayCoursePrice.setText(mPrice + "元");
            mCouponInfo.setText("不使用优惠券");
        }

    }

    public interface OnPayClickListener {
        void onPayClick(int payWay);

        void onSelectCouponClick();
    }

    class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }

    private Spannable setCouponText(String s, String color) {
        Spannable sp = new SpannableString(s);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    public static Double sub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
}
