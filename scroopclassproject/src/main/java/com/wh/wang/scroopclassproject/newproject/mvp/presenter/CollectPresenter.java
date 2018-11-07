package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CollectEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/19.
 */

public class CollectPresenter {

    public void changeCollect(String id, String user_id, String type, final OnResultListener listener){
        NetUtil.getRetrofit3(Api.class)
                .changeCollect(id,user_id,type)
                .enqueue(new Callback<CollectEntity>() {
                    @Override
                    public void onResponse(Call<CollectEntity> call, Response<CollectEntity> response) {
                        if(response!=null){
                            CollectEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CollectEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CollectEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
