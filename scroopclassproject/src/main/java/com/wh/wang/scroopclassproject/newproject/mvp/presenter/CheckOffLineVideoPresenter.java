package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckOffLineVideoEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/15.
 */

public class CheckOffLineVideoPresenter {

    public void checkOffLineVideo(String video_id, String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .checkOffLineVideo(video_id,user_id)
                .enqueue(new Callback<CheckOffLineVideoEntity>() {
                    @Override
                    public void onResponse(Call<CheckOffLineVideoEntity> call, Response<CheckOffLineVideoEntity> response) {
                        if(response!=null){
                            CheckOffLineVideoEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CheckOffLineVideoEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckOffLineVideoEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
