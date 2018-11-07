package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.TryOutEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/24.
 */

public class TryOutPresenter {
    public void tryOut(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .tryOut(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<TryOutEntity>() {
                    @Override
                    public void onResponse(Call<TryOutEntity> call, Response<TryOutEntity> response) {
                        if(response!=null){
                            TryOutEntity entity = response.body();
                            if (entity!=null){
                                if(entity.getCode()==200){
                                    listener.onSuccess();
                                }else{
                                    Toast.makeText(MyApplication.mApplication, entity.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<TryOutEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
