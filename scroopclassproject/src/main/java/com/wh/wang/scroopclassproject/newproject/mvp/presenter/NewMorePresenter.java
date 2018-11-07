package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MonthEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewMoreEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class NewMorePresenter {

    public void getNewMoreInfoForJson(String type_id, String page, final OnResultListener listener){
        Log.e("DH_MORE",type_id);
        NetUtil.getRetrofit(Api.class)
                .getNewMoreInfoForJson(type_id,page,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_MORE",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFault("error"+t.toString());
                    }
                });
    }

    public void getNewMoreInfo(String type_id, String page, final OnResultListener listener){
        Log.e("DH_MORE",type_id);
        NetUtil.getRetrofit(Api.class)
                .getNewMoreInfo(type_id,page,Api.timeStr,Api.sign)
                .enqueue(new Callback<NewMoreEntity>() {
                    @Override
                    public void onResponse(Call<NewMoreEntity> call, Response<NewMoreEntity> response) {
                        if(response!=null){
                            NewMoreEntity moreEntity = response.body();
                            if(moreEntity!=null){
                                listener.onSuccess(moreEntity);
                            }else{
                                listener.onFault("NewMoreEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<NewMoreEntity> call, Throwable t) {
                        listener.onFault("error"+t.toString());
                    }
                });
    }


    public void getMonthEvents(String where,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getMontEvents(where,Api.timeStr,Api.sign)
                .enqueue(new Callback<MonthEventEntity>() {
                    @Override
                    public void onResponse(Call<MonthEventEntity> call, Response<MonthEventEntity> response) {
                        if(response!=null){
                            MonthEventEntity monthEventEntity = response.body();
                            if (monthEventEntity!=null) {
                                listener.onSuccess(monthEventEntity);
                            }else{
                                listener.onFault("MonthEventEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MonthEventEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

}
