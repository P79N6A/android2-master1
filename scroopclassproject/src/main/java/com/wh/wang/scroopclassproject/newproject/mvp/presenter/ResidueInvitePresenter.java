package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ResidueInviteEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/22.
 */

public class ResidueInvitePresenter {

    public void getResidueInvite(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getResidueInvite(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResidueInviteEntity>() {
                    @Override
                    public void onResponse(Call<ResidueInviteEntity> call, Response<ResidueInviteEntity> response) {
                        if(response!=null){
                            if(response.body()!=null){
                                listener.onSuccess(response.body());
                            }else{
                                listener.onFault("ResidueInviteEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResidueInviteEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
