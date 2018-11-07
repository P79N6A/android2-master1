package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyPriceEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/19.
 */

public class CompanyPricePresenter {
    public void getCompanyPrice(String user_id,final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getCompanyPrice(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<CompanyPriceEntity>() {
                    @Override
                    public void onResponse(Call<CompanyPriceEntity> call, Response<CompanyPriceEntity> response) {
                        if(response!=null){
                            CompanyPriceEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CompanyPriceEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyPriceEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
