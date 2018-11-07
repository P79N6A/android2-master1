package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.FeedBackEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/1.
 */

public class FeedBackPresenter {

    public void feedBack(String user_id, String content,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .feedBack(user_id,content,Api.timeStr,Api.sign)
                .enqueue(new Callback<FeedBackEntity>() {
                    @Override
                    public void onResponse(Call<FeedBackEntity> call, Response<FeedBackEntity> response) {
                        if(response!=null){
                            FeedBackEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("FeedBackEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<FeedBackEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
