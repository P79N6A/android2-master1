package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.VideoFinishEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/18.
 */

public class SumUpInfoPresenter {

    public void getSumUpInfo(String user_id, String id,String type,String idfa, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getSumUpInfo(user_id,id,type,idfa)
                .enqueue(new Callback<SumUpEntity>() {
                    @Override
                    public void onResponse(Call<SumUpEntity> call, Response<SumUpEntity> response) {
                        if(response!=null){
                            if(response.body()!=null){
                                SumUpEntity entity = response.body();
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("SumUpEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SumUpEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void showFinishDig(String user_id,String id,String cate_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .showFinishDig(user_id,id,cate_id)
                .enqueue(new Callback<VideoFinishEntity>() {
                    @Override
                    public void onResponse(Call<VideoFinishEntity> call, Response<VideoFinishEntity> response) {
                        if(response!=null){
                            VideoFinishEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("VideoFinishEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoFinishEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
//    public void getVideoInfo(String user_id, String id,String type,String idfa, final OnResultListener listener){
//        NetUtil.getRetrofit(Api.class)
//                .getVideoInfo(user_id,id,type,idfa)
//                .enqueue(new Callback<VedioDetailInfo>() {
//                    @Override
//                    public void onResponse(Call<VedioDetailInfo> call, Response<VedioDetailInfo> response) {
//                        if(response!=null){
//                            if(response.body()!=null){
//                                VedioDetailInfo entity = response.body();
//                                listener.onSuccess(entity);
//                            }else{
//                                listener.onFault("SumUpEntity is NULL");
//                            }
//                        }else{
//                            listener.onFault("response is NULL");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<VedioDetailInfo> call, Throwable t) {
//                        listener.onFault("error:"+t.toString());
//                    }
//                });
//    }


}
