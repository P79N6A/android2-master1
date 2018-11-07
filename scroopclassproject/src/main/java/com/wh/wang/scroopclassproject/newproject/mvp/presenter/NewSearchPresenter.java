package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DelHistoryEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SearchHotHistoryEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class NewSearchPresenter {

    public void getHotAndHistory(String uid, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getHotAndHistory(uid,Api.timeStr,Api.sign)
                .enqueue(new Callback<SearchHotHistoryEntity>() {
                    @Override
                    public void onResponse(Call<SearchHotHistoryEntity> call, Response<SearchHotHistoryEntity> response) {
                        if(response!=null){
                            SearchHotHistoryEntity historyEntity = response.body();
                            if(historyEntity!=null){
                                listener.onSuccess(historyEntity);
                            }else{
                                listener.onFault("SearchHotHistoryEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchHotHistoryEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void delSimpleHistory(String uid,String content,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .delSimpleHistory(uid,content,Api.timeStr,Api.sign)
                .enqueue(new Callback<DelHistoryEntity>() {
                    @Override
                    public void onResponse(Call<DelHistoryEntity> call, Response<DelHistoryEntity> response) {
                        if(response!=null){
                            DelHistoryEntity body = response.body();
                            if(body!=null){
                                listener.onSuccess(body);
                            }else{
                                listener.onFault("DelHistoryEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<DelHistoryEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void delAllHitory(String uid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .delAllHistory(uid,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response!=null){
                            try {
                                Log.e("DH_DEL_HISTORY","a: "+new String(response.body().bytes()));
                                listener.onSuccess();
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

    public void getNewSearchResult(String key,String uid,String page,String pageRow,String type,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getNewSearchResult(key,uid,page,pageRow,type,Api.timeStr,Api.sign)
                .enqueue(new Callback<NewSearchResultEntity>() {
                    @Override
                    public void onResponse(Call<NewSearchResultEntity> call, Response<NewSearchResultEntity> response) {
                        if(response!=null){
                            NewSearchResultEntity body = response.body();
                            if(body!=null){
                                listener.onSuccess(body);
                            }else{
                                listener.onFault("NewSearchResultEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<NewSearchResultEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
