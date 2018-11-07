package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventApplyEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/15.
 */

public class EventApplyInfoPresenter {

    public void getEventApplyInfo(String id, String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getEventApplyInfo(id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<EventApplyEntity>() {
                    @Override
                    public void onResponse(Call<EventApplyEntity> call, Response<EventApplyEntity> response) {
                        if(response!=null){
                            EventApplyEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("EventApplyEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<EventApplyEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
