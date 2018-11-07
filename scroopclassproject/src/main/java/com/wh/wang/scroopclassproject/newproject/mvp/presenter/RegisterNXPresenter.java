package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.RegisterEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/17.
 */

public class RegisterNXPresenter {
    public void registerNx(String user_id, Map<String,String> map, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .registNX(user_id,map,Api.timeStr,Api.sign)
                .enqueue(new Callback<RegisterEntity>() {
                    @Override
                    public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                        if(response!=null){
                            RegisterEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("RegisterEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });

    }
}
