package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/14.
 */

public class CheckEventPresenter {

    public void checkEvent(Map<String,String> map, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .checkEvent(map)
                .enqueue(new Callback<CheckEventEntity>() {
                    @Override
                    public void onResponse(Call<CheckEventEntity> call, Response<CheckEventEntity> response) {
                        if(response!=null){
                            CheckEventEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CheckEventEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckEventEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void checkEventJson(Map<String,String> map, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .checkEventJson(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_CHECK_JSON",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

    public void checkMoreEvent(String p,String id,String tid,String uid, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .checkMore(p,id,tid,uid)
                .enqueue(new Callback<CheckEventEntity>() {
                    @Override
                    public void onResponse(Call<CheckEventEntity> call, Response<CheckEventEntity> response) {
                        if(response!=null){
                            CheckEventEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CheckEventEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckEventEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
