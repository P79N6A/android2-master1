package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventDetailEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/3.
 */

public class EventDetailsPresenter {
    public void getEventDetails(String event_id, String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getEventDetails(event_id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<EventDetailEntity>() {
                    @Override
                    public void onResponse(Call<EventDetailEntity> call, Response<EventDetailEntity> response) {
                        if(response!=null){
                            EventDetailEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("EventDetailEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<EventDetailEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
