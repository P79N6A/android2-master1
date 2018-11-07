package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CommentEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/2/24.
 */

public class CommentPresenter {

    public void addCommentJson(Map<String,String> map,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .addCommentJson(map,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_COMMENT",new String(response.body().bytes()));
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
                        listener.onFault("Error:"+t.toString());
                    }
                });

    }

    public void addComment(Map<String,String> map,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .addComment(map,Api.timeStr,Api.sign)
                .enqueue(new Callback<CommentEntity>() {
                    @Override
                    public void onResponse(Call<CommentEntity> call, Response<CommentEntity> response) {
                        if(response!=null){
                            CommentEntity entity = response.body();
                            if(entity!=null){
                                if (entity.getCode()==200) {
                                    listener.onSuccess(entity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, entity.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                listener.onFault("CommentEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentEntity> call, Throwable t) {
                        listener.onFault("Error:"+t.toString());
                    }
                });

    }

    public void deleteCommentJson(String id,String product_id,String type,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .deleteCommentJson(id,product_id,type,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_COMMENT",new String(response.body().bytes()));
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
                        listener.onFault("Error:"+t.toString());
                    }
                });

    }
}
