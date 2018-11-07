package com.wh.wang.scroopclassproject.newproject.fun_class.gift_bag;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.DiscountCouponActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

public class GiftBagActivity extends BaseActivity implements OnResultListener {
    private ImageView mHead;
    private TextView mCouponPrice;
    private RecyclerView mCourseList;
    private GiftBagPresenter mGiftBagPresenter = new GiftBagPresenter();
    private String mUserId;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_gift_bag;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void initView() {
        mHead = (ImageView) findViewById(R.id.head);
        mCouponPrice = (TextView) findViewById(R.id.coupon_price);
        mCourseList = (RecyclerView) findViewById(R.id.course_list);


        int headH = (int) (ScreenUtils.getScreenWidth(this) * 5 /9f);
        ViewGroup.LayoutParams headParams = mHead.getLayoutParams();
        headParams.height = headH;
        mHead.setLayoutParams(headParams);

        mCourseList.setHasFixedSize(false);
        mCourseList.setNestedScrollingEnabled(false);
        mCourseList.setLayoutManager(new LinearLayoutManager(this));
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "123");
    }

    @Override
    public void initDatas() {
        mTitleC.setText("新手礼包");
        mCouponPrice.setText(getPriceText("200"));
        LoadingUtils.getInstance().showNetLoading(this);
        mGiftBagPresenter.getGiftInfo(mUserId,this);
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.coupon_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiftBagActivity.this, DiscountCouponActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.all_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiftBagActivity.this, NewMainActivity.class);
                intent.putExtra("flag","3");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void initOther() {

    }


    private Spannable getPriceText(String price) {

        Spannable sp = new SpannableString("¥ "+price);
        sp.setSpan(new AbsoluteSizeSpan(20,true),0,2,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return sp;
    }

    @Override
    public void onSuccess(Object... value) {
        LoadingUtils.getInstance().hideNetLoading();
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication,"gift_bag","0");
        GiftBagEntity giftBagEntity = (GiftBagEntity) value[0];
        mCourseList.setAdapter(new GiftBagCourseAdapter(this,giftBagEntity.getInfo().getVideo_data()));
    }

    @Override
    public void onFault(String error) {
        SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication,"gift_bag","0");
        LoadingUtils.getInstance().hideNetLoading();
        Log.e("GiftBagActivity",error);
    }
}
