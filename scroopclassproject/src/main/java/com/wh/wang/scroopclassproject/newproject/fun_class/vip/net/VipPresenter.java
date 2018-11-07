package com.wh.wang.scroopclassproject.newproject.fun_class.vip.net;

import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/7/30.
 */

public class VipPresenter {

    public void getNewVipList(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getNewVipList(user_id)
                .enqueue(new Callback<VipListEntity>() {
                    @Override
                    public void onResponse(Call<VipListEntity> call, Response<VipListEntity> response) {
                        if(response!=null){
                            VipListEntity vipListEntity = response.body();
                            if(vipListEntity!=null){
                                if("200".equals(vipListEntity.getCode())){
                                    listener.onSuccess(vipListEntity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, vipListEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("VipListEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<VipListEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
