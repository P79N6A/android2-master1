package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.OrderInfoEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/25.
 */

public class OrderListPresenter {
    public void getOrderList(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getOrderList(user_id)
                .enqueue(new Callback<OrderInfoEntity>() {
                    @Override
                    public void onResponse(Call<OrderInfoEntity> call, Response<OrderInfoEntity> response) {
                        if(response!=null){
                            OrderInfoEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("OrderInfoEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderInfoEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
