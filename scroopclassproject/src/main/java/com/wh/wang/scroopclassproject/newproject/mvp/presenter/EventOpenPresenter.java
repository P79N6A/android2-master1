package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventOpenEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/3.
 */

public class EventOpenPresenter {
    public void eventOpen(String event_id, String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .eventOpen(event_id,user_id,Api.sign,Api.timeStr,"4")
                .enqueue(new Callback<EventOpenEntity>() {
                    @Override
                    public void onResponse(Call<EventOpenEntity> call, Response<EventOpenEntity> response) {
                        if(response!=null){
                            EventOpenEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("EventOpenPresenter is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<EventOpenEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
