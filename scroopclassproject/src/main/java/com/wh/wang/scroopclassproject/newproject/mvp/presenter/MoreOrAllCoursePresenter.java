package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayLabelEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.FreeCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreEssayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreOrAllCourseEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MoreOrAllCoursePresenter {
    public void getMoreCourse(String typeId, String page, final OnResultListener listener) {
        Log.e("DH_COURSE", typeId + "  " + page);
        NetUtil.getRetrofit2(Api.class)
                .getMoreCourse("1", "1", typeId, page)
                .enqueue(new Callback<MoreOrAllCourseEntity>() {
                    @Override
                    public void onResponse(Call<MoreOrAllCourseEntity> call, Response<MoreOrAllCourseEntity> response) {

                        if (response != null) {
                            MoreOrAllCourseEntity entity = response.body();
                            if (entity != null) {
                                if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
                                    listener.onSuccess(entity);
                                } else {
                                    listener.onFault("Status :" + entity.getStatus());
                                }
                            } else {
                                listener.onFault("MoreCourseEntity is NULL");
                            }
                        } else {
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MoreOrAllCourseEntity> call, Throwable t) {
                        listener.onFault("error:" + t.toString());
                    }
                });
    }

    public void getAllCourse(String page, final OnResultListener listener) {
        NetUtil.getRetrofit2(Api.class)
                .getAllCourse("1", "1", page)
                .enqueue(new Callback<MoreOrAllCourseEntity>() {
                    @Override
                    public void onResponse(Call<MoreOrAllCourseEntity> call, Response<MoreOrAllCourseEntity> response) {

                        if (response != null) {
                            MoreOrAllCourseEntity entity = response.body();
                            if (entity != null) {
                                if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
                                    listener.onSuccess(entity);
                                } else {
                                    listener.onFault("Status :" + entity.getStatus());
                                }
                            } else {
                                listener.onFault("AllCourseEntity is NULL");
                            }
                        } else {
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MoreOrAllCourseEntity> call, Throwable t) {
                        listener.onFault("error:" + t.toString());
                    }
                });
    }

    public void getFreeCourse(String page, final OnResultListener listener) {
        NetUtil.getRetrofit2(Api.class)
                .getFreeCourse("1", "1", page)
                .enqueue(new Callback<FreeCourseEntity>() {
                    @Override
                    public void onResponse(Call<FreeCourseEntity> call, Response<FreeCourseEntity> response) {

                        if (response != null) {
                            FreeCourseEntity entity = response.body();
                            if (entity != null) {
                                if (entity.getStatus() == 1 || "1".equals(entity.getStatus())) {
                                    listener.onSuccess(entity);
                                } else {
                                    listener.onFault("Status :" + entity.getStatus());
                                }
                            } else {
                                listener.onFault("FreeCourseEntity is NULL");
                            }
                        } else {
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<FreeCourseEntity> call, Throwable t) {
                        listener.onFault("error:" + t.toString());
                    }
                });
    }

    public void getMoreEssayLabel(final OnResultListener listener) {
        NetUtil.getRetrofit(Api.class)
                .getEssayLabels()
                .enqueue(new Callback<List<EssayLabelEntity>>() {
                    @Override
                    public void onResponse(Call<List<EssayLabelEntity>> call, Response<List<EssayLabelEntity>> response) {

                        if (response != null) {
                            List<EssayLabelEntity> entity = response.body();
                            if (entity != null) {
                                listener.onSuccess(entity);
                            } else {
                                listener.onFault("EssayLabelEntity is NULL");
                            }
                        } else {
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EssayLabelEntity>> call, Throwable t) {
                        listener.onFault("error:" + t.toString());
                    }
                });
    }

    public void getMoreEssaies(String type,String page,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getMoreEssays(type,page)
                .enqueue(new Callback<MoreEssayEntity>() {
                    @Override
                    public void onResponse(Call<MoreEssayEntity> call, Response<MoreEssayEntity> response) {

                        if (response != null) {
                            MoreEssayEntity entity = response.body();
                            if (entity != null) {
                                listener.onSuccess(entity);
                            } else {
                                listener.onFault("MoreEssayEntity is NULL");
                            }
                        } else {
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MoreEssayEntity> call, Throwable t) {
                        listener.onFault("error:" + t.toString());
                    }
                });
    }
}
