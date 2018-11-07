package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MyStudyEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/27.
 */

public class MyStudyPresenter {
    public void getStudyInfo(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getStudyInfo(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<MyStudyEntity>() {
                    @Override
                    public void onResponse(Call<MyStudyEntity> call, Response<MyStudyEntity> response) {
                        if(response!=null){
                            MyStudyEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("MyStudyEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MyStudyEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
