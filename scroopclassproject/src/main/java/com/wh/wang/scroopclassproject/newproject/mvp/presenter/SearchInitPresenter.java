package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchInitEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/13.
 */

public class SearchInitPresenter {

    public void getSearchInitData(String id, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getSearchHistory(id,Api.timeStr,Api.sign)
                .enqueue(new Callback<NewSearchInitEntity>() {
                    @Override
                    public void onResponse(Call<NewSearchInitEntity> call, Response<NewSearchInitEntity> response) {
                        if(response!=null&&response.body()!=null){
                            NewSearchInitEntity entity = response.body();
                            if(entity.getStatus()==1||"1".equals(entity.getStatus())){
                                listener.onSuccess(entity);
                            }else{
                                Log.e("DH_SEARCH_HISTORY",""+entity.getStatus());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NewSearchInitEntity> call, Throwable t) {
                        Log.e("DH_SEARCH_HISTORY","Error:"+t.toString());
                        listener.onFault(t.toString());
                    }
                });
    }

}
