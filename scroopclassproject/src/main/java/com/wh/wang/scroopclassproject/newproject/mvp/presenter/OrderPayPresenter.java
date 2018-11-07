package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/14.
 */

public class OrderPayPresenter {

    public void orderPay(String goodsType, String id, String user_id, String coupon,String company, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)//getRetrofit2
                .orderPay(goodsType,id,user_id,coupon,"4",company)
                .enqueue(new Callback<PayEntity>() {
                    @Override
                    public void onResponse(Call<PayEntity> call, Response<PayEntity> response) {
                        if(response!=null){
                            PayEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("PayEntity is NULL  code:"+response.code());
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<PayEntity> call, Throwable t) {
                        listener.onFault("Error"+t.toString());
                    }
                });
    }
    public void orderPay(Map<String,String> map, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .orderPay(map,"")
                .enqueue(new Callback<PayEntity>() {
                    @Override
                    public void onResponse(Call<PayEntity> call, Response<PayEntity> response) {
                        if(response!=null){
                            PayEntity entity = response.body();
                            if(entity!=null){
                                if(entity.getStatus()==1||"1".equals(entity.getStatus())){
                                    listener.onSuccess(entity);
                                }else{
                                    listener.onFault("Status is 0");
                                }
                            }else{
                                listener.onFault("PayEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<PayEntity> call, Throwable t) {
                        listener.onFault("Error"+t.toString());
                    }
                });
    }

    public void orderPay(Map<String,String> map,String batch_join, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .orderPay(map,batch_join)
                .enqueue(new Callback<PayEntity>() {
                    @Override
                    public void onResponse(Call<PayEntity> call, Response<PayEntity> response) {
                        if(response!=null){
                            PayEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
//                                if(entity.getStatus()==1||"1".equals(entity.getStatus())){
//
//                                }else{
//                                    listener.onFault("Status is 0");
//                                }
                            }else{
                                listener.onFault("PayEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<PayEntity> call, Throwable t) {
                        listener.onFault("Error"+t.toString());
                    }
                });
    }
    public void orderPayJson(Map<String,String> map,String batch_join, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .orderPayJson(map,batch_join)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_ORDER_PAY",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFault("Error"+t.toString());
                    }
                });
    }

}
