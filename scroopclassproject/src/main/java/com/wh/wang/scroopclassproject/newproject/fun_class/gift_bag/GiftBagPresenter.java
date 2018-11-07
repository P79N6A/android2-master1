package com.wh.wang.scroopclassproject.newproject.fun_class.gift_bag;

import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/9/18.
 */

public class GiftBagPresenter {

    public void getGiftInfo(String user_id, final OnResultListener listener) {
        NetUtil.getRetrofit3(Api.class)
                .getGiftInfo(user_id,"1")
                .enqueue(new Callback<GiftBagEntity>() {
                    @Override
                    public void onResponse(Call<GiftBagEntity> call, Response<GiftBagEntity> response) {
                        if (response != null) {
                            if (response.body() != null) {
                                GiftBagEntity giftBagEntity = response.body();
                                if (giftBagEntity.getCode()==1) {
                                    listener.onSuccess(giftBagEntity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, giftBagEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("GiftBagEntity is NULL");
                            }
                        } else {
                            listener.onFault("Response<GiftBagEntity> is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<GiftBagEntity> call, Throwable t) {
                        listener.onFault("error" + t.toString());
                    }
                });
    }


    public void getGiftStatus(String user_id,final OnResultListener listener){
        NetUtil.getRetrofit3(Api.class)
                .getGiftStatus(user_id)
                .enqueue(new Callback<GiftBagStatusEntity>() {
                    @Override
                    public void onResponse(Call<GiftBagStatusEntity> call, Response<GiftBagStatusEntity> response) {
                        if (response != null) {
                            if (response.body() != null) {
                                listener.onSuccess(response.body());
                            }else{
                                listener.onFault("GiftBagEntity is NULL");
                            }
                        } else {
                            listener.onFault("Response<GiftBagEntity> is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<GiftBagStatusEntity> call, Throwable t) {
                        listener.onFault("error" + t.toString());
                    }
                });
    }
}
