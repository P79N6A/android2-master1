package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseLabelEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/28.
 */

public class LabelCoursePresenter {

    public void getLabelCourse(String typeId, String page, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getCourseInfo(typeId,page)
                .enqueue(new Callback<CourseLabelEntity>() {
                    @Override
                    public void onResponse(Call<CourseLabelEntity> call, Response<CourseLabelEntity> response) {
                        if(response!=null){
                            CourseLabelEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CourseLabelEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CourseLabelEntity> call, Throwable t) {
                        listener.onFault("error"+t.toString());
                    }
                });
    }
}
