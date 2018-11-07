package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DeleteRecordEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/27.
 */

public class DeleteRecordPresenter {
    public void deleteRecord(String video_id, String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .deleteStudyRecord(video_id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<DeleteRecordEntity>() {
                    @Override
                    public void onResponse(Call<DeleteRecordEntity> call, Response<DeleteRecordEntity> response) {
                        if(response!=null){
                            DeleteRecordEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("DeleteRecordEntity is NULL");
                            }
                        }else{
                            Log.e("DH_VIDES","response is NULL");
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteRecordEntity> call, Throwable t) {
                        Log.e("DH_VIDES","error:"+t.toString());
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
