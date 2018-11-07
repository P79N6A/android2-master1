package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayLikeEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/9/27.
 */

public class EssayDetailPresenter {

    public void getEssayDetail(String id, String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getEssayDetail(id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<EssayDetailEntity>() {
                    @Override
                    public void onResponse(Call<EssayDetailEntity> call, Response<EssayDetailEntity> response) {
                        if (response!=null) {
                            EssayDetailEntity body = response.body();
                            if (body!=null) {
                                listener.onSuccess(body);
                            }else{
                                Log.e("DH_ESSAY","EssayDetailEntity is NULL");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EssayDetailEntity> call, Throwable t) {
                        Log.e("DH_ESSAY","error:"+t.toString());
                    }
                });
    }


    public Call<EssayLikeEntity> essayLike(String id,String user_id,final OnResultListener listener){
        Call<EssayLikeEntity> call = NetUtil.getRetrofit3(Api.class)
                .essayLike(id, user_id, "2", Api.timeStr, Api.sign);

        call.enqueue(new Callback<EssayLikeEntity>() {
            @Override
            public void onResponse(Call<EssayLikeEntity> call, Response<EssayLikeEntity> response) {
                if (response!=null) {
                    if (response.body()!=null) {
                        listener.onSuccess(response.body());
//                        try {
//                            Log.e("DH_ESSAY_LIKE",new String(response.body().bytes()));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<EssayLikeEntity> call, Throwable t) {
                Log.e("DH_ESSAY","error:"+t.toString());
            }
        });
        return call;
    }
}
