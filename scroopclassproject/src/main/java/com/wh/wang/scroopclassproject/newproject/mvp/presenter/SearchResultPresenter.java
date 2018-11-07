package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/13.
 */

public class SearchResultPresenter {

    public void getSearchResult(String uid, String keyword, int page, final OnResultListener listener){
        Log.e("DH_SEARCH",uid+"  "+keyword+" "+page);
        NetUtil.getRetrofit2(Api.class)
                .getSearchResult(keyword,uid,page+"","course","10",Api.timeStr,Api.sign)
                .enqueue(new Callback<NewSearchEntity>() {
                    @Override
                    public void onResponse(Call<NewSearchEntity> call, Response<NewSearchEntity> response) {
                        if(response!=null&&response.body()!=null){
                            NewSearchEntity entity = response.body();
                            if(entity.getStatus()==1||"1".equals(entity.getStatus())){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("Status is 0");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<NewSearchEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getSearchResultJson(String uid, String keyword, int page, final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .getSearchResultJson(keyword,uid,page+"","course","8",Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.e("DH_SEARCH",new String(response.body().bytes()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
