package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.UserInfoEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/22.
 */

public class UserInfoPresenter {

    public void getUserInfo(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getUserInfo(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<UserInfoEntity>() {
                    @Override
                    public void onResponse(Call<UserInfoEntity> call, Response<UserInfoEntity> response) {
                        if(response!=null){
                            UserInfoEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("UserInfoEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfoEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
