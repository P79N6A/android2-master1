package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckUpdateEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/26.
 */

public class CheckUpdatePresenter {
    public void checkUpdate(String user_id,String version,final OnResultListener listener) {
        NetUtil.getRetrofit(Api.class)
                .checkUpdate(user_id,"0",version,"1")
                .enqueue(new Callback<CheckUpdateEntity>() {
                    @Override
                    public void onResponse(Call<CheckUpdateEntity> call, Response<CheckUpdateEntity> response) {
                        if(response!=null){
                            CheckUpdateEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else {
                                listener.onFault("CheckUpdateEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckUpdateEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
