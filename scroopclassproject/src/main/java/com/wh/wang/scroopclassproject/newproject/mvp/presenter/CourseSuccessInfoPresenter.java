package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseResultEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/9.
 */

public class CourseSuccessInfoPresenter {

    public void getSuccessInfo(String no, String page, String pageRow, String uid, String randStr, final OnResultListener listener){
//        Log.e("DH_SUCCESS_INFO","no: "+no+"  \npage: "+page+"  \npageRow: "+pageRow+"  \nuid: "+uid+"  \nrandStr: "+randStr);
        NetUtil.getRetrofit2(Api.class)
                .getCourseSuccessInfo(no,page,pageRow,uid,randStr)
                .enqueue(new Callback<CourseResultEntity>() {
                    @Override
                    public void onResponse(Call<CourseResultEntity> call, Response<CourseResultEntity> response) {
                        if(response!=null){
                            CourseResultEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CourseResultEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CourseResultEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
