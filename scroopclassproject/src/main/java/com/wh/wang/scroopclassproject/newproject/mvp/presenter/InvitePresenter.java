package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.InviteEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/4/17.
 */

public class InvitePresenter {

    public void getInviteList(String uid, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .inviteList(uid)
                .enqueue(new Callback<InviteEntity>() {
                    @Override
                    public void onResponse(Call<InviteEntity> call, Response<InviteEntity> response) {
                        if(response!=null){
                            InviteEntity inviteEntity = response.body();
                            if(inviteEntity!=null){
                                listener.onSuccess(inviteEntity);
                            }else{
                                listener.onFault("InviteEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<InviteEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
