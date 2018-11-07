package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MessageEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/28.
 */

public class MessagePresenter {
    public void getMessage(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getMessage(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<MessageEntity>() {
                    @Override
                    public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                        if(response!=null){
                            MessageEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("MessageEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
