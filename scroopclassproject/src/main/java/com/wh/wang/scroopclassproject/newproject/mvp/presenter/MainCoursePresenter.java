package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/25.
 */

public class MainCoursePresenter {

    public void getMainCourse(String ver,final OnResultListener listener){
        String user_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        NetUtil.getRetrofit2(Api.class)
                .getMainCourse(user_id,ver,"4")
                .enqueue(new Callback<MainCourseEntity>() {
                    @Override
                    public void onResponse(Call<MainCourseEntity> call, Response<MainCourseEntity> response) {
                        if(response!=null){
                            MainCourseEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("MainCourseEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MainCourseEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void mainHint(String user_id,String type,String video_id,final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .mainHint(user_id,type,video_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        listener.onSuccess();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
