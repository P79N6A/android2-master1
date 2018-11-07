package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CustomerEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.TelResultEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import org.xutils.common.util.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/10/10.
 */

public class CustomerPresenter {

    public void getCustomerInfo(String uid, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getCustomerInfo(uid,Api.timeStr,Api.sign)
                .enqueue(new Callback<CustomerEntity>() {
                    @Override
                    public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                        if (response!=null) {
                            if (response.body()!=null) {
                                if ("200".equals(response.body().getCode())) {
                                    LogUtil.e(response.body().toString());
                                    listener.onSuccess(response.body());
                                }else{
                                    Toast.makeText(MyApplication.mApplication, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("error  code:"+response.code());
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void telResult(String uid,String cid,String conversation_status){
        NetUtil.getRetrofit(Api.class)
                .telResult(uid,cid,conversation_status,"2",Api.timeStr,Api.sign)
                .enqueue(new Callback<TelResultEntity>() {
                    @Override
                    public void onResponse(Call<TelResultEntity> call, Response<TelResultEntity> response) {
                        if (response!=null) {
                            if (response.body()!=null) {
                                Log.e("DH_TEL","success");
                            }else{
                                Log.e("DH_TEL","fail:"+response.code());
                            }
                        }else{
                            Log.e("DH_TEL","response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<TelResultEntity> call, Throwable t) {
                        Log.e("DH_TEL","error:"+t.toString());
                    }
                });
    }
}
