package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyStateEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/18.
 */

public class CompanyStatePresenter {

    public void getCompanyState(String user_id,String status,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getCompanyState(user_id,status,Api.timeStr,Api.sign)
                .enqueue(new Callback<CompanyStateEntity>() {
                    @Override
                    public void onResponse(Call<CompanyStateEntity> call, Response<CompanyStateEntity> response) {
                        if(response!=null){
                            CompanyStateEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CompanyStateEntity is NULL");
                            }
//                            try {
//                                Log.e("DH_COMPANY_STATE",new String(response.body().bytes()));
//                                listener.onSuccess();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyStateEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
