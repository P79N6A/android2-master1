package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCommonCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCourseEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/22.
 */

public class CompanyCoursePresenter {

    public void getCompanyCourse(String user_id, String video_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getCompanyCourse(user_id,video_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<CompanyCourseEntity>() {
                    @Override
                    public void onResponse(Call<CompanyCourseEntity> call, Response<CompanyCourseEntity> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_COURSE",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            CompanyCourseEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CompanyCourseEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyCourseEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getCompanyCommonCourse(String user_id, String video_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getCompanyCommonCourse(user_id,video_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<CompanyCommonCourseEntity>() {
                    @Override
                    public void onResponse(Call<CompanyCommonCourseEntity> call, Response<CompanyCommonCourseEntity> response) {
                        if(response!=null){
                            CompanyCommonCourseEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("CompanyCourseEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyCommonCourseEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
