package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.adapter.SelectCouponListAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponListBean;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponListEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;

import java.util.List;

public class SelectCouponActivity extends BaseActivity implements SelectCouponListAdapter.OnSelectCouponListener, View.OnClickListener {

//    private EditText mCouponExchange;
//    private TextView mCouponBt;
    private RecyclerView mCouponList;
    private List<CouponListBean> mCouponsList;
    private SelectCouponListAdapter mSelectCouponListAdapter;
    private String mCoupon;
    private String mVideoId;
    private String mUserId;
    private CouponPresenter mCouponPresenter = new CouponPresenter();
    private String mCoursePrice;
    private String mCategory;
    private TextView mCannotUse;


    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_select_coupon;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mCoupon = getIntent().getStringExtra("coupon_id");
        mVideoId = getIntent().getStringExtra("id");
        mCoursePrice = getIntent().getStringExtra("course_price");
        mCategory = getIntent().getStringExtra("category");
//        mVideoId = "1567";
    }

    @Override
    public void initView() {
//        mCouponExchange = (EditText) findViewById(R.id.coupon_exchange);
//        mCouponBt = (TextView) findViewById(R.id.coupon_bt);
        mCouponList = (RecyclerView) findViewById(R.id.coupon_list);
        mCannotUse = (TextView) findViewById(R.id.cannot_use);
        mCouponList.setLayoutManager(new LinearLayoutManager(this));
        mTitleR.setVisibility(View.VISIBLE);
    }

    @Override
    public void initDatas() {
        mTitleC.setText("优惠券");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        accessNet("2","");

    }

    @Override
    public void initListener() {
        mCannotUse.setOnClickListener(this);
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCouponActivity.this,CouponRuleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initOther() {

    }

    @Override
    public void onCouponClick(int pos) {
        if(mCouponsList!=null&&mCouponsList.size()>0){
            CouponListBean couponListBean = mCouponsList.get(pos);
            Intent intent = new Intent();
            intent.putExtra("coupon_id",couponListBean.getId());
            intent.putExtra("price",couponListBean.getPrice());
            intent.putExtra("coupon_code",couponListBean.getCode());
            setResult(NewVideoInfoActivity.COUPON_RESULT,intent);
            finish();
        }

    }

    private void accessNet(final String type, String coupon_code){
        Log.e("DH_COUPON","user_id:"+mUserId+"  type:"+type+"  coupon:"+coupon_code+"  vid:"+mVideoId+"  category:"+mCategory+"  price:"+mCoursePrice);
        LoadingUtils.getInstance().showNetLoading(this);
        mCouponPresenter.selectCoupon(mUserId, type, coupon_code, mVideoId,mCategory,mCoursePrice, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
                CouponListEntity couponListEntity = (CouponListEntity) value[0];
                if("200".equals(couponListEntity.getCode())){

                    if("2".equals(type)){
                        mCouponsList = couponListEntity.getInfo();
                        if(mCouponsList!=null&&mCouponsList.size()>0){
                            mCannotUse.setVisibility(View.VISIBLE);
                        }else{
                            mCannotUse.setVisibility(View.GONE);
                        }
                        mSelectCouponListAdapter = new SelectCouponListAdapter(SelectCouponActivity.this, mCouponsList,mCoupon,mCoursePrice);
                        mSelectCouponListAdapter.setOnSelectCouponListener(SelectCouponActivity.this);
                        mCouponList.setAdapter(new EmptyRecycleAdapter(mSelectCouponListAdapter, R.layout.layout_coupon_empty));
                    }
//                    else if("1".equals(type)){
//                        if(couponListEntity.getInfo()!=null&&couponListEntity.getInfo().size()>0){
//                            Toast.makeText(mContext, "兑换成功", Toast.LENGTH_SHORT).show();
//                            CouponListBean couponListBean = couponListEntity.getInfo().get(0);
//                            Intent intent = new Intent();
//                            Log.e("DH_COUPON","coupon_id:"+couponListBean.getId());
//                            intent.putExtra("coupon_id",couponListBean.getId());
//                            intent.putExtra("price",couponListBean.getPrice());
//                            setResult(NewVideoInfoActivity.COUPON_RESULT,intent);
//                            finish();
//                        }
//                    }

                }else{
                    LoadingUtils.getInstance().hideNetLoading();
                    Toast.makeText(MyApplication.mApplication, ""+couponListEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_ERROR_COUPON_SELECT",error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.coupon_bt:
//                if(mCouponExchange.getText()!=null&& StringUtils.isNotEmpty(mCouponExchange.getText().toString())){
//                    String coupon_code = mCouponExchange.getText().toString();
//                    accessNet("1",coupon_code);
//                }else{
//                    Toast.makeText(MyApplication.mApplication, "请输入兑换码", Toast.LENGTH_SHORT).show();
//                }
//
//                break;
            case R.id.cannot_use:
                Intent intent = new Intent();
                intent.putExtra("coupon_id","");
                intent.putExtra("price","");
                intent.putExtra("coupon_code","");
                setResult(NewVideoInfoActivity.COUPON_RESULT,intent);
                finish();
                break;
        }
    }
}
