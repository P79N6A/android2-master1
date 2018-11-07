package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShareCompanyEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/19.
 */

public class ShareInfomPresenter {
    public void shareInform(String user_id, String type, String product_id,String isCompany, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .shareInform(user_id,type,product_id,isCompany,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_SHARE_INFORM",new String(response.body().bytes()));
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

    public void shareInform(String user_id, String type, String product_id,String shareTouser,String isCompany, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .shareInform(user_id,type,product_id,shareTouser,isCompany,Api.timeStr,Api.sign)
                .enqueue(new Callback<ShareCompanyEntity>() {
                    @Override
                    public void onResponse(Call<ShareCompanyEntity> call, Response<ShareCompanyEntity> response) {
                        if(response!=null){
                            ShareCompanyEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("ShareCompanyEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ShareCompanyEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
