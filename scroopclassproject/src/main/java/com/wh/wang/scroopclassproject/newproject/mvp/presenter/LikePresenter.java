package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.LikeEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/22.
 */

public class LikePresenter {
    public void like(String user_id, String child_id,String type,String video_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .like(user_id,child_id,type,video_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<LikeEntity>() {
                    @Override
                    public void onResponse(Call<LikeEntity> call, Response<LikeEntity> response) {
                        if(response!=null){
                            LikeEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("LikeEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<LikeEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }



    public void workLike(String id,String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .workLike(id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<WorkLikeEntity>() {
                    @Override
                    public void onResponse(Call<WorkLikeEntity> call, Response<WorkLikeEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_WORK_LIKE",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            WorkLikeEntity entity = response.body();
                            if(entity!=null){
                                if(entity.getCode()==200){
                                    listener.onSuccess(entity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, "点赞异常", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("WorkLikeEntity is NULL");
                            }

                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkLikeEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });

    }
}
