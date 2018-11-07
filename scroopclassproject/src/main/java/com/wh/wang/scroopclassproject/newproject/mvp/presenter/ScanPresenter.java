package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ScanEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ScanResultEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/3.
 */

public class ScanPresenter {
    public void getScanResult(String id, final String uid, final OnResultListener listener){
        Log.e("DH_SCAN",uid);
        NetUtil.getRetrofit2(Api.class)
                .getScanResult(id,uid)
                .enqueue(new Callback<ScanEntity>() {
                    @Override
                    public void onResponse(Call<ScanEntity> call, Response<ScanEntity> response) {
                        if(response!=null){
                            ScanEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("ScanEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ScanEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void signUp(Map<String,String> map, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .signUp(map)
                .enqueue(new Callback<ScanResultEntity>() {
                    @Override
                    public void onResponse(Call<ScanResultEntity> call, Response<ScanResultEntity> response) {
                        if(response!=null){
                            ScanResultEntity scanResultEntity = response.body();
                            if (scanResultEntity!=null) {
                                listener.onSuccess(scanResultEntity);
                            }else{
                                Toast.makeText(MyApplication.mApplication, "error:"+response.code(), Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ScanResultEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
