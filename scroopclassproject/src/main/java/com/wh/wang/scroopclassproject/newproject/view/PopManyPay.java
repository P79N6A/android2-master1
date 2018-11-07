package com.wh.wang.scroopclassproject.newproject.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class PopManyPay extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private WindowManager mWindowManager;
    private DisplayMetrics mMetrics;
    public static final int H = 390;
    private TextView mPayCourseTitle;
    private TextView mPayCoursePrice;
    private RelativeLayout mPayWeichat;
    private ImageView mWeichatSelect;
    private RelativeLayout mPayAlipay;
    private ImageView mAlipaySelect;
    private TextView mPayCancel;
    private TextView mPayOk;
    private TextView mReminder;
    private TextView mSinglePay;
    private TextView mManyPay;
    private RelativeLayout mBuyForMyself;
    private ImageView mBuyForMyselfIcon;
    private ImageView mAdd;
    private TextView mPayNumTv;
    private ImageView mSubtract;
    private TextView mPayHint;


    //支付方式 2：支付宝 3：微信
    private int PAY_WAY = WAY_WEICHAT;
    private int[] mPaySelect = {R.drawable.pay_unselect, R.drawable.pay_select};
    private OnPayClickListener mOnPayClickListener;

    //单人双人 0 单人  1 双人
    private int PAY_TYPE = 0;
    //自己是否能够观看 0 能  1 不能
    private int MYSELF_ALLOW = 0;
    //购买人数
    private double mPayNum = 1;
    //为自己购买状态 0 不为自己购买  1 为自己购买
    private int mBuyForMeState = 1;

    private double mPrice = 0.00;
    public void setOnPayClickListener(OnPayClickListener mOnPayClickListener) {
        this.mOnPayClickListener = mOnPayClickListener;
    }

    public PopManyPay(final Context context, String title, double price,int pay) {
        super(context);
        mContext = context;
        mPrice = price;
        MYSELF_ALLOW = pay;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        View view = mLayoutInflater.inflate(R.layout.pop_many_pay, null, false);
        initView(view);
        mPayCourseTitle.setText(title);
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
        },index, reminder.length()-1, Spanned.SPAN_MARK_MARK);
        spanStr.setSpan(new NoUnderlineSpan(),index, reminder.length()-1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
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
        mSinglePay.setOnClickListener(this);
        mManyPay.setOnClickListener(this);
        mBuyForMyself.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mSubtract.setOnClickListener(this);
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
        mSinglePay = (TextView) view.findViewById(R.id.single_pay);
        mManyPay = (TextView) view.findViewById(R.id.many_pay);
        mBuyForMyself = (RelativeLayout) view.findViewById(R.id.buy_for_myself);
        mBuyForMyselfIcon = (ImageView) view.findViewById(R.id.buy_for_myself_icon);
        mAdd = (ImageView) view.findViewById(R.id.add);
        mPayNumTv = (TextView) view.findViewById(R.id.pay_num);
        mSubtract = (ImageView) view.findViewById(R.id.subtract);
        mPayHint = (TextView) view.findViewById(R.id.pay_hint);

        if(MYSELF_ALLOW==1){
            PAY_TYPE = 1;
            mBuyForMeState = 0;
            mBuyForMyselfIcon.setImageResource(R.drawable.pay_unselect);
            mBuyForMyself.setVisibility(View.GONE);
            mSinglePay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.apply_examine_bg));
            mManyPay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_many_pay_bg));
            mSinglePay.setTextColor(mContext.getResources().getColor(R.color.examine));
            mManyPay.setTextColor(mContext.getResources().getColor(R.color.many_pay));
            mSinglePay.setText("为自己购买");
            mPayHint.setText(R.string.select_extra_pay_num);
            mPayCoursePrice.setText(mul(mPayNum,mPrice)+"元");
        }
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
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(screenWidth,heightPX);
        view.setLayoutParams(layoutParams);
        this.setContentView(view);
    }
    
    int num = 1;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                int extraNum = 0;
                if(PAY_TYPE==1){
                    extraNum = Integer.parseInt(mPayNumTv.getText().toString());
                }
                if(mOnPayClickListener!=null){
                    mOnPayClickListener.onPayClick(PAY_WAY, (int) mPayNum,extraNum,mBuyForMeState,PAY_TYPE);
                }
//                Toast.makeText(mContext, "当前购买数量: "+mPayNum, Toast.LENGTH_SHORT).show();
                break;
            case R.id.single_pay:
                if(MYSELF_ALLOW==1){
                    Toast.makeText(mContext, "您已可以免费观看课程", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PAY_TYPE!=0){
                    PAY_TYPE = 0;
                    mPayNum = 1;
                    mBuyForMyself.setVisibility(View.GONE);
                    mPayNumTv.setText("1");
                    mSinglePay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_many_pay_bg));
                    mManyPay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.apply_examine_bg));
                    mSinglePay.setTextColor(mContext.getResources().getColor(R.color.many_pay));
                    mManyPay.setTextColor(mContext.getResources().getColor(R.color.examine));

                    //点击单人时重置为自己购买状态
                    mBuyForMyselfIcon.setImageResource(R.drawable.pay_select);
                    mBuyForMeState = 1;
                }
                mPayHint.setText(R.string.select_pay_num);
                mPayCoursePrice.setText(mul(mPayNum,mPrice)+"元");
                break;
            case R.id.many_pay:
                if(MYSELF_ALLOW==1){
                    return;
                }
                if(PAY_TYPE!=1){
                    PAY_TYPE = 1;
                    if(mBuyForMeState==0){
                        mPayNum = 1;
                    }else{
                        mPayNum = 2;
                    }
                    mBuyForMyself.setVisibility(View.VISIBLE);
                    mSinglePay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.apply_examine_bg));
                    mManyPay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_many_pay_bg));
                    mSinglePay.setTextColor(mContext.getResources().getColor(R.color.examine));
                    mManyPay.setTextColor(mContext.getResources().getColor(R.color.many_pay));
                }
                mPayHint.setText(R.string.select_extra_pay_num);
                mPayCoursePrice.setText(mul(mPayNum,mPrice)+"元");
                break;
            case R.id.buy_for_myself:
                if(MYSELF_ALLOW==1){
                    Toast.makeText(mContext, "您已可以免费观看课程", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mBuyForMeState==0){
                    mBuyForMyselfIcon.setImageResource(R.drawable.pay_select);
                    mBuyForMeState = 1;
                    mPayNum++;
                    mPayCoursePrice.setText(mul(mPayNum,mPrice)+"元");
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("取消勾选后,自己将无法观看课程")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mBuyForMyselfIcon.setImageResource(R.drawable.pay_unselect);
                                    mBuyForMeState = 0;
                                    mPayNum--;
                                    mPayCoursePrice.setText(mul(mPayNum,mPrice)+"元");
                                }
                            })
                            .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    Dialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }

                break;
            case R.id.add:
                //单人购买时禁止加减
                if(PAY_TYPE==0){
                    Toast.makeText(mContext, "为自己购买无法增加人数", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPayNumTv.getText()!=null&&mPayNumTv.getText().toString()!=null){
                    num = Integer.parseInt(mPayNumTv.getText().toString());
                }
                mPayNumTv.setText(num+1+"");
                mPayNum++;
                mPayCoursePrice.setText(mul(mPayNum,mPrice)+"元");
                break;
            case R.id.subtract:
                if(PAY_TYPE==0){
                    Toast.makeText(mContext, "为自己购买无法减少人数", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mPayNumTv.getText()!=null&&mPayNumTv.getText().toString()!=null){
                    num = Integer.parseInt(mPayNumTv.getText().toString());
                }
                if(num==1){
                    return;
                }
                mPayNumTv.setText(num-1+"");
                mPayNum--;
                mPayCoursePrice.setText(mul(mPayNum,mPrice)+"元");
                break;
        }
    }

    public void changePayWay(int payWay){
        mWeichatSelect.setImageResource(mPaySelect[payWay==WAY_WEICHAT?1:0]);
        mAlipaySelect.setImageResource(mPaySelect[payWay==WAY_ALIPAY?1:0]);
    }

    public interface OnPayClickListener{
        void onPayClick(int payWay,int payNum,int extraNum,int payForMeState,int payType);
    }

    class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }

    public static Double mul(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }
}
