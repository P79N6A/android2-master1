package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/9/12.
 */

public class UpLogPresenter {

    public void upLogFile(String upfile){
        NetUtil.getRetrofit(Api.class)
                .upLogFile("4",upfile)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("DH_UPLOAD","success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("DH_UPLOAD","error"+t.toString());
                    }
                });
    }
}
