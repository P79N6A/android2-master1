package com.wh.wang.scroopclassproject.newproject.view;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyPriceEntity;

import java.math.BigDecimal;
import java.util.List;

import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_ALIPAY;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_TRANSFER;
import static com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity.WAY_WEICHAT;


/**
 * Created by chdh on 2017/3/21.
 */

public class PopCompanyPay extends PopupWindow implements View.OnClickListener {
//    private final CompanyPriceEntity.InfoBean.OneBean mOne;
    private final List<CompanyPriceEntity.InfoBean.ManyBean> mMany;
    private final CompanyPriceEntity.InfoBean mBean;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private WindowManager mWindowManager;
    private DisplayMetrics mMetrics;
    public static final int H = 448;
    private TextView mPayCourseTitle;
    private TextView mPayCoursePrice;
    private RelativeLayout mPayWeichat;
    private ImageView mWeichatSelect;
    private RelativeLayout mPayAlipay;
    private ImageView mAlipaySelect;
    private RelativeLayout mPayTransfer;
    private ImageView mTransferSelect;
    private TextView mPayCancel;
    private TextView mPayOk;
    private TextView mReminder;
//    private ImageView mAdd;
//    private TextView mPayNumTv;
//    private ImageView mSubtract;
//    private TextView mPayHint;
    private TextView mPayType1;
    private TextView mPayType2;
    private TextView mPayType3;
    private TextView[] mPayTypes;
//    private RelativeLayout mTaocan1;
//    private RelativeLayout mTaocan2;
//    private RelativeLayout mPayPerNum;
//    private RelativeLayout mPaySetMeal;
//    private ImageView mTaocan1Icon;
//    private ImageView mTaocan2Icon;
    private ImageView mPayTitleIcon;

//    private int MIN_NUM = 5;
//    private int MAX_NUM = 30;

    //支付方式 2：支付宝 3：微信 4:对公转账
    private int PAY_WAY = WAY_WEICHAT;
    private int[] mPaySelect = {R.drawable.qiye_pay_unselect, R.drawable.qiye_pay_select};
    // 0：套餐  1：人数  购买
    private int SET_MEAL_TYPE = 0;
    private int[] mSetMealSelect = {R.drawable.qiye_pay_untancan, R.drawable.qiye_pay_taocan};

    //0: 5-10  1: 15-20  2: 20-30
    private int SET_MEAL = 0;
    private int[] mNumSelect = {R.drawable.apply_examine_bg, R.drawable.imm_vip_shape};
    private String[] mNumColorSelect = {"#282828","#ffffff"};
    private OnPayClickListener mOnPayClickListener;

    //购买人数
    private double mPayNum = 1;

    //Tao_ID
    private String mTaoId = "2";

//    private double mPrice = 0.00;
    private String mSumPrice = "0.00";
    public void setOnPayClickListener(OnPayClickListener mOnPayClickListener) {
        this.mOnPayClickListener = mOnPayClickListener;
    }

    public PopCompanyPay(final Context context, CompanyPriceEntity.InfoBean bean) {
        super(context);
        mContext = context;
//        mOne = bean.getOne();
        mBean = bean;
        mMany = bean.getMany();
//        MIN_NUM = Integer.parseInt(mOne.getMinnum());
//        MAX_NUM = Integer.parseInt(mOne.getMaxnum());
//        mPayNum = MIN_NUM;
//        try {
//            mPrice = Double.parseDouble(mOne.getPrice());
//        }catch (Exception e){
//            mPrice = 599;
//        }
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        View view = mLayoutInflater.inflate(R.layout.pop_company_pay, null, false);
        initView(view);
//        mPayNumTv.setText(MIN_NUM+"");
        mPayCourseTitle.setText(bean.getGoodsInfo().getTitle());
        Glide.with(MyApplication.mApplication).load(bean.getGoodsInfo().getImg()).centerCrop().into(mPayTitleIcon);
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
        mPayTransfer.setOnClickListener(this);
        mPayCancel.setOnClickListener(this);
        mPayOk.setOnClickListener(this);
//        mAdd.setOnClickListener(this);
//        mSubtract.setOnClickListener(this);
        mPayType1.setOnClickListener(this);
        mPayType2.setOnClickListener(this);
        mPayType3.setOnClickListener(this);
//        mTaocan1.setOnClickListener(this);
//        mTaocan2.setOnClickListener(this);
    }

    private void initView(View view) {
        mPayCourseTitle = (TextView) view.findViewById(R.id.title);
        mPayCoursePrice = (TextView) view.findViewById(R.id.pay_course_price);
        mPayWeichat = (RelativeLayout) view.findViewById(R.id.pay_weichat);
        mWeichatSelect = (ImageView) view.findViewById(R.id.weichat_select);
        mPayAlipay = (RelativeLayout) view.findViewById(R.id.pay_alipay);
        mAlipaySelect = (ImageView) view.findViewById(R.id.alipay_select);
        mPayTransfer = (RelativeLayout) view.findViewById(R.id.pay_transfer);
        mTransferSelect = (ImageView) view.findViewById(R.id.transfer_select);
        mPayTitleIcon = (ImageView) view.findViewById(R.id.pay_title_icon);

        mPayCancel = (TextView) view.findViewById(R.id.pay_cancel);
        mPayOk = (TextView) view.findViewById(R.id.pay_ok);
        mReminder = (TextView) view.findViewById(R.id.reminder);
        mPayType1 = (TextView) view.findViewById(R.id.pay_type_1);
        mPayType2 = (TextView) view.findViewById(R.id.pay_type_2);
        mPayType3 = (TextView) view.findViewById(R.id.pay_type_3);
//        mAdd = (ImageView) view.findViewById(R.id.add);
//        mPayNumTv = (TextView) view.findViewById(R.id.pay_num);
//        mSubtract = (ImageView) view.findViewById(R.id.subtract);
//        mPayHint = (TextView) view.findViewById(R.id.pay_hint);

//        mTaocan1 = (RelativeLayout) view.findViewById(R.id.taocan_1);
//        mTaocan2 = (RelativeLayout) view.findViewById(R.id.taocan_2);
//        mPayPerNum = (RelativeLayout) view.findViewById(R.id.pay_per_num);
//        mPaySetMeal = (RelativeLayout) view.findViewById(R.id.pay_set_meal);
//        mTaocan1Icon = (ImageView) view.findViewById(R.id.taocan_1_icon);
//        mTaocan2Icon = (ImageView) view.findViewById(R.id.taocan_2_icon);
        mPayTypes = new TextView[]{mPayType1,mPayType2,mPayType3};
        for (int i = 0; i < ((mMany.size()>3)?3:mMany.size()); i++) {
            mPayTypes[i].setText(mMany.get(i).getDes());
        }

        mTaoId = mMany.get(SET_MEAL).getId();
        if(mBean.getIs_vip()==1){
            mSumPrice = mMany.get(SET_MEAL).getVip_price();
        }else{
            mSumPrice = mMany.get(SET_MEAL).getPrice();
        }
        mPayCoursePrice.setText(mSumPrice+"元");
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
            case R.id.pay_transfer:
                PAY_WAY = WAY_TRANSFER;
                changePayWay(PAY_WAY);
                break;
            case R.id.pay_cancel:
                this.dismiss();
                break;
            case R.id.pay_ok:
                if(mOnPayClickListener!=null){
                    mOnPayClickListener.onPayClick(PAY_WAY, (int) mPayNum,mTaoId);
                }
                break;
//            case R.id.add:
//                if(mPayNumTv.getText()!=null&&mPayNumTv.getText().toString()!=null){
//                    num = Integer.parseInt(mPayNumTv.getText().toString());
//                }
//                if(num==MAX_NUM){
//                    Toast.makeText(mContext, "已经到了最大购买人数", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mPayNumTv.setText(num+1+"");
//                mPayNum++;
//                mSumPrice = mul(mPayNum,mPrice)+"";
//                mPayCoursePrice.setText(mSumPrice+"元");
//                break;
//            case R.id.subtract:
//                if(mPayNumTv.getText()!=null&&mPayNumTv.getText().toString()!=null){
//                    num = Integer.parseInt(mPayNumTv.getText().toString());
//                }
//                if(num==MIN_NUM){
//                    Toast.makeText(mContext, "已经到了最小购买人数", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mPayNumTv.setText(num-1+"");
//                mPayNum--;
//                mSumPrice = mul(mPayNum,mPrice)+"";
//                mPayCoursePrice.setText(mSumPrice+"元");
//                break;
            case R.id.pay_type_1:
                SET_MEAL = 0;
                changePayNum(SET_MEAL);
                break;
            case R.id.pay_type_2:
                SET_MEAL = 1;
                changePayNum(SET_MEAL);
                break;
            case R.id.pay_type_3:
                SET_MEAL = 2;
                changePayNum(SET_MEAL);
                break;
//            case R.id.taocan_1:
//                SET_MEAL_TYPE = 0;
//                mPayPerNum.setVisibility(View.GONE);
//                mPaySetMeal.setVisibility(View.VISIBLE);
//                changeTaoCan(SET_MEAL_TYPE);
//                mTaoId = mMany.get(SET_MEAL).getId();
//                mSumPrice = mMany.get(SET_MEAL).getPrice();
//                mPayCoursePrice.setText(mSumPrice+"元");
//                break;
//            case R.id.taocan_2:
//                SET_MEAL_TYPE = 1;
//                mTaoId = mOne.getId();
//                mPayPerNum.setVisibility(View.VISIBLE);
//                mPaySetMeal.setVisibility(View.GONE);
//                changeTaoCan(SET_MEAL_TYPE);
//                mSumPrice = mul(mPayNum,mPrice)+"";
//                mPayCoursePrice.setText(mSumPrice+"元");
//                break;
        }
    }
//    public void changeTaoCan(int taoCan){
//        mTaocan1Icon.setImageResource(mSetMealSelect[taoCan==0?1:0]);
//        mTaocan2Icon.setImageResource(mSetMealSelect[taoCan==1?1:0]);
//    }

    public void changePayNum(int taoFlag){
        mPayType1.setBackgroundResource(mNumSelect[taoFlag==0?1:0]);
        mPayType1.setTextColor(Color.parseColor(mNumColorSelect[taoFlag==0?1:0]));
        mPayType2.setBackgroundResource(mNumSelect[taoFlag==1?1:0]);
        mPayType2.setTextColor(Color.parseColor(mNumColorSelect[taoFlag==1?1:0]));
        mPayType3.setBackgroundResource(mNumSelect[taoFlag==2?1:0]);
        mPayType3.setTextColor(Color.parseColor(mNumColorSelect[taoFlag==2?1:0]));


        mTaoId = mMany.get(taoFlag).getId();
        mSumPrice = mMany.get(taoFlag).getPrice();
        Log.e("DH_COMPANY_P",mMany.get(taoFlag).getVip_price()+"");
        if(mBean.getIs_vip()==1){
            mSumPrice = mMany.get(SET_MEAL).getVip_price();
        }else{
            mSumPrice = mMany.get(SET_MEAL).getPrice();
        }
        mPayCoursePrice.setText(mSumPrice+"元");
    }
    public void changePayWay(int payWay){
        mWeichatSelect.setImageResource(mPaySelect[payWay==WAY_WEICHAT?1:0]);
        mAlipaySelect.setImageResource(mPaySelect[payWay==WAY_ALIPAY?1:0]);
        mTransferSelect.setImageResource(mPaySelect[payWay==WAY_TRANSFER?1:0]);
    }

    public interface OnPayClickListener{
        void onPayClick(int payWay, int payNum, String taoId);
    }

    @SuppressLint("ParcelCreator")
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
