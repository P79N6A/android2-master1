package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/7/25.
 */

public class CouponPresenter {


    public void getCouponList(String user_id, String type, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getCouponList(user_id,type,Api.timeStr,Api.sign)
                .enqueue(new Callback<CouponListEntity>() {
                    @Override
                    public void onResponse(Call<CouponListEntity> call, Response<CouponListEntity> response) {
                        if (response!=null) {
                            CouponListEntity couponListEntity = response.body();
                            if (couponListEntity!=null) {
                                listener.onSuccess(couponListEntity);
                            }else{
                                listener.onFault("CouponListEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CouponListEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void selectCoupon(String user_id, String type,String coupon_code,String video_id,String category,String Payprice, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .selectCoupon(user_id,type,coupon_code,video_id,category,Payprice,Api.timeStr,Api.sign)
                .enqueue(new Callback<CouponListEntity>() {
                    @Override
                    public void onResponse(Call<CouponListEntity> call, Response<CouponListEntity> response) {
                        if (response!=null) {
                            CouponListEntity couponListEntity = response.body();
                            if (couponListEntity!=null) {
                                listener.onSuccess(couponListEntity);
                            }else{
                                listener.onFault("CouponListEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CouponListEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
