package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PaySuccessEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/2/6.
 */

public class PaySuccessPresenter {

    public void paySuccess(String user_id, String event_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .paySuccess(user_id,event_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<PaySuccessEntity>() {
                    @Override
                    public void onResponse(Call<PaySuccessEntity> call, Response<PaySuccessEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_PAY_SUCCESS",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            PaySuccessEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("PaySuccessEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<PaySuccessEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });

    }
}
