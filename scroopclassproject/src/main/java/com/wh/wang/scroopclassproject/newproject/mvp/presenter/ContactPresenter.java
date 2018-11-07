package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.OperationContactEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/31.
 */

public class ContactPresenter {
    public void getContact(String user_id, String randStr,String event_id,String isVideo, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getContact(user_id,randStr,event_id,isVideo)
                .enqueue(new Callback<ContactEntity>() {
                    @Override
                    public void onResponse(Call<ContactEntity> call, Response<ContactEntity> response) {
                        if(response!=null){
                            ContactEntity contactEntity = response.body();
                            if(contactEntity!=null){
                                listener.onSuccess(contactEntity);
                            }else{
                                listener.onFault("ContactEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getCompanyContact(String user_id, String randStr,String event_id,String isVideo, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getCompanyContact(user_id,randStr,event_id,isVideo)
                .enqueue(new Callback<ContactEntity>() {
                    @Override
                    public void onResponse(Call<ContactEntity> call, Response<ContactEntity> response) {
                        if(response!=null){
                            ContactEntity contactEntity = response.body();
                            if(contactEntity!=null){
                                listener.onSuccess(contactEntity);
                            }else{
                                listener.onFault("ContactEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void contactOperation(String user_id,String randStr,String id,String name,String phone,String event_id,String isVideo,final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .contactOperation(user_id,randStr,event_id,id,name,phone,isVideo)
                .enqueue(new Callback<OperationContactEntity>() {
                    @Override
                    public void onResponse(Call<OperationContactEntity> call, Response<OperationContactEntity> response) {
                        if(response!=null){
                            OperationContactEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("OperationContactEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<OperationContactEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void contactOperationJson(String user_id,String randStr,String id,String name,String phone,String event_id,final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .contactOperationJson(user_id,randStr,event_id,id,name,phone)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_CONTACT",new String(response.body().bytes()));
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

    public void getCompanyContactJson(String user_id, String randStr,String event_id,String isVideo, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getCompanyContactJson(user_id,randStr,event_id,isVideo)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_COMPANY_CONTACT",new String(response.body().bytes()));
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
}
