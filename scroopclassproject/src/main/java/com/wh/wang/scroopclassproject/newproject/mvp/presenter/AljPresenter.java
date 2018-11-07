package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ALJCommitEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ALJInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.AljDetailsEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShowFree7Entity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/5/2.
 */

public class AljPresenter {

    public void commitInfo(Map<String,String> map, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .aljCommit(map,Api.timeStr,Api.sign)
                .enqueue(new Callback<ALJCommitEntity>() {
                    @Override
                    public void onResponse(Call<ALJCommitEntity> call, Response<ALJCommitEntity> response) {
                        if(response!=null){
                            ALJCommitEntity aljCommitEntity = response.body();
                            if(aljCommitEntity!=null){
                                listener.onSuccess(aljCommitEntity);
                            }else{
                                listener.onFault("ALJCommitEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ALJCommitEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void getAljInfo(String user_id,String event_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .aljInfo(user_id,event_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<ALJInfoEntity>() {
                    @Override
                    public void onResponse(Call<ALJInfoEntity> call, Response<ALJInfoEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_ALJ_INFO",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            ALJInfoEntity aljInfoEntity = response.body();
                            if(aljInfoEntity!=null){
                                listener.onSuccess(aljInfoEntity);
                            }else{
                                listener.onFault("ALJCommitEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ALJInfoEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getAljFree7Vip(String user_id,String event_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .aljFree7Vip(user_id,event_id,"4","1",Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_ALJ_Free7",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                            if(aljCommitEntity!=null){
//                                listener.onSuccess(aljCommitEntity);
//                            }else{
//                                listener.onFault("ALJCommitEntity is NULL");
//                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getAljDetails(String user_id,String event_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .aljDetails(user_id,event_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<AljDetailsEntity>() {
                    @Override
                    public void onResponse(Call<AljDetailsEntity> call, Response<AljDetailsEntity> response) {
                        if(response!=null){
                            AljDetailsEntity aljDetailsEntity = response.body();
                            if(aljDetailsEntity!=null){
                                listener.onSuccess(aljDetailsEntity);
                            }else{
                                listener.onFault("AljDetailsEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<AljDetailsEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void show7Free(String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .show7Free(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<ShowFree7Entity>() {
                    @Override
                    public void onResponse(Call<ShowFree7Entity> call, Response<ShowFree7Entity> response) {
                        if(response!=null){
                            ShowFree7Entity showFree7Entity = response.body();
                            if(showFree7Entity!=null){
                                listener.onSuccess(showFree7Entity);
                            }else{
                                listener.onFault("ShowFree7Entity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ShowFree7Entity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


}
