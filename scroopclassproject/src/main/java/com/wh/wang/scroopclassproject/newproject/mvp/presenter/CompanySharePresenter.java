package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShareListEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/23.
 */

public class CompanySharePresenter  {
    public void getShareList(String user_id,String video_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getShareList(user_id,video_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<ShareListEntity>() {
                    @Override
                    public void onResponse(Call<ShareListEntity> call, Response<ShareListEntity> response) {
                        if(response!=null){
                            ShareListEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("ShareListEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ShareListEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
