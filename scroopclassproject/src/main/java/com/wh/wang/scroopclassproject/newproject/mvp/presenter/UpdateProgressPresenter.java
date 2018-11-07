package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.UpdateProgressEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/19.
 */

public class UpdateProgressPresenter {
    public void updateProgress(Map<String,String> map, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .updateVideoProgress(map)
                .enqueue(new Callback<UpdateProgressEntity>() {
                    @Override
                    public void onResponse(Call<UpdateProgressEntity> call, Response<UpdateProgressEntity> response) {
                        if(response!=null){
                            UpdateProgressEntity entity = response.body();
                            if(entity!=null){
                                if(entity.getCode()!=200){
                                    listener.onSuccess(entity);
                                }else{
                                    listener.onFault("code:"+entity.getCode()+" msg:"+entity.getMsg());
                                }
                            }else{
                                listener.onFault("UpdateProgressEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProgressEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
