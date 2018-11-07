package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/6/28.
 */

public class NewHomePresenter {

    public void getNewHomeInfo(final OnResultListener listener) {
        NetUtil.getRetrofit(Api.class)
                .getNewHomeInfo(Api.timeStr, Api.sign)
                .enqueue(new Callback<NewHomeEntity>() {
                    @Override
                    public void onResponse(Call<NewHomeEntity> call, Response<NewHomeEntity> response) {
                        if (response != null) {
                            NewHomeEntity homeEntity = response.body();
                            if (homeEntity != null) {
                                listener.onSuccess(homeEntity);
                            } else {
                                listener.onFault("NewHomeEntity is NULL");
                            }
                        } else {
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<NewHomeEntity> call, Throwable t) {
                        listener.onFault("error:" + t.toString());
                    }
                });

    }

    public void getNewHomeInfoForJson(final OnResultListener listener) {
        Log.e("DH_NEW_HOME","shijian:"+Api.timeStr+"  sign:"+Api.sign);
        NetUtil.getRetrofit(Api.class)
                .getNewHomeInfoForJson(Api.timeStr, Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response != null) {
                            try {
                                Log.e("DH_NEW_HOME",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFault("error:" + t.toString());
                    }
                });
    }
}
