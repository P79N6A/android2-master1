package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PublishEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkDetailsEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkRemarkEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/3/1.
 */

public class WorkPresenter {

    public void getWorkInfo(String video_id, String cate_id, String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getWorkInfo(video_id,cate_id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<WorkInfoEntity>() {
                    @Override
                    public void onResponse(Call<WorkInfoEntity> call, Response<WorkInfoEntity> response) {
                        if(response!=null){
                            WorkInfoEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("WorkInfoEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkInfoEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getGoodWorkJson(String video_id, String cate_id, String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getGoodWorkInfoJson(video_id,cate_id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
//                            WorkInfoEntity entity = response.body();
//                            if(entity!=null){
//                                listener.onSuccess(entity);
//                            }else{
//                                listener.onFault("WorkInfoEntity is NULL");
//                            }
                            try {
                                Log.e("DH_GOOD_WORK",new String(response.body().bytes()));
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

    public void getGoodWork(String video_id, String cate_id, String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getGoodWorkInfo(video_id,cate_id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<WorkInfoEntity>() {
                    @Override
                    public void onResponse(Call<WorkInfoEntity> call, Response<WorkInfoEntity> response) {
                        if(response!=null){
                            WorkInfoEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("WorkInfoEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkInfoEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void getWorkDetails(String list_id,String user_id,final  OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getWorkDetails(list_id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<WorkDetailsEntity>() {
                    @Override
                    public void onResponse(Call<WorkDetailsEntity> call, Response<WorkDetailsEntity> response) {
                        if(response!=null){
                            WorkDetailsEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("WorkDetailsEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkDetailsEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void addWorkRemark(String id,String user_id,String content,String parent_id,String rename,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .addWorkRemark(id,user_id,content,parent_id,rename,Api.timeStr,Api.sign)
                .enqueue(new Callback<WorkRemarkEntity>() {
                    @Override
                    public void onResponse(Call<WorkRemarkEntity> call, Response<WorkRemarkEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_ADD_REMARK",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            WorkRemarkEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("WorkRemarkEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkRemarkEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void goodWork(String id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .goodWork(id,Api.timeStr,Api.sign)
                .enqueue(new Callback<WorkLikeEntity>() {
                    @Override
                    public void onResponse(Call<WorkLikeEntity> call, Response<WorkLikeEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_GOOD_WORK",new String(response.body().bytes()));
//                                listener.onSuccess();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            WorkLikeEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
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

    public void remarkLike(String id,String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .remarkLike(id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_REMARK_LIKE",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            listener.onSuccess();
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

    public void upWorkContent(String user_id,String zuoye_id,String content,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .upWorkContent(user_id,zuoye_id,content,"android",Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_WORK_CONTENT",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                            PublishEntity entity = response.body();
//                            if(entity!=null){
//                                if(entity.getCode()==200){
                                    listener.onSuccess();
//                                }else{
//                                    listener.onFault("code is "+entity.getCode());
//                                }
//                            }else{
//                                listener.onFault("PublishEntity is NULL");
//                            }
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


    public void deleteWork(String id,String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .deleteWork(id,user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<PublishEntity>() {
                    @Override
                    public void onResponse(Call<PublishEntity> call, Response<PublishEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_DELETE",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            PublishEntity entity = response.body();
                            if(entity!=null){
                                if(entity.getCode()==200){
                                    listener.onSuccess();
                                }else{

                                    listener.onFault("code is "+entity.getCode());
                                }
                            }else{

                                listener.onFault("PublishEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<PublishEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void deleteWorkRemark(String id,String parent_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .deleteWorkRemark(id,parent_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<PublishEntity>() {
                    @Override
                    public void onResponse(Call<PublishEntity> call, Response<PublishEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_DELETE",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            PublishEntity entity = response.body();
                            if(entity!=null){
                                if(entity.getCode()==200){
                                    listener.onSuccess();
                                }else{
                                    listener.onFault("code is "+entity.getCode());
                                }
                            }else{

                                listener.onFault("PublishEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<PublishEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
