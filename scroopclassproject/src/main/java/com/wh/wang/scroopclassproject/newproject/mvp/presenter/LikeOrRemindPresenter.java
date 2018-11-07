package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.LikeOrRemindEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/25.
 */

public class LikeOrRemindPresenter {
    public void getLikeOrRemindList(String user_id, String type, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getLikeOrRemindList(user_id,type,Api.timeStr,Api.sign)
                .enqueue(new Callback<LikeOrRemindEntity>() {
                    @Override
                    public void onResponse(Call<LikeOrRemindEntity> call, Response<LikeOrRemindEntity> response) {
                        if (response!=null){
//                            try {
//                                Log.e("DH_LR",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            LikeOrRemindEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("LikeOrRemindEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<LikeOrRemindEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });

    }
}
