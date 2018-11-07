package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import android.util.Log;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by teitsuyoshi on 2018/7/11.
 */

public class StudyGroupPresenter {


    public void getSGHomeInfo(String user_id, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getSGHome(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<SGHomeEntity>() {
                    @Override
                    public void onResponse(Call<SGHomeEntity> call, Response<SGHomeEntity> response) {
                        if (response!=null) {
                            SGHomeEntity sgHomeEntity = response.body();
                            if (sgHomeEntity!=null) {
                                listener.onSuccess(sgHomeEntity);
                            }else{
                                listener.onFault("SGHomeEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGHomeEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void getSGDetail(String uid,String pid,String vid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getSGDetail(uid,pid,vid,Api.timeStr,Api.sign)
                .enqueue(new Callback<SGDetailEntity>() {
                    @Override
                    public void onResponse(Call<SGDetailEntity> call, Response<SGDetailEntity> response) {
                        if (response!=null) {
                            SGDetailEntity sgDetailEntity = response.body();
                            if (sgDetailEntity!=null) {
                                listener.onSuccess(sgDetailEntity);
                            }else{
                                listener.onFault("SGDetailEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGDetailEntity> call, Throwable t) {

                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void joinStudyGroup(String uid,String pid,String vid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .joinStudyGroup(uid,pid,vid,Api.timeStr,Api.sign)
                .enqueue(new Callback<SGJoinEntity>() {
                    @Override
                    public void onResponse(Call<SGJoinEntity> call, Response<SGJoinEntity> response) {
                        if (response!=null) {
                            SGJoinEntity sgJoinEntity = response.body();
                            if (sgJoinEntity!=null) {
                                listener.onSuccess(sgJoinEntity);
                            }else{
                                listener.onFault("SGDetailEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGJoinEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getMyStudyGroup(String uid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getMyStudyGroup(uid,Api.timeStr,Api.sign)
                .enqueue(new Callback<MyStudyGroupEntity>() {
                    @Override
                    public void onResponse(Call<MyStudyGroupEntity> call, Response<MyStudyGroupEntity> response) {
                        if (response!=null) {
                            MyStudyGroupEntity myStudyGroupEntity = response.body();
                            if (myStudyGroupEntity!=null) {
                                listener.onSuccess(myStudyGroupEntity);
                            }else{
                                listener.onFault("MyStudyGroupEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MyStudyGroupEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void getStudyGroupRank(String uid,String pid,String vid,String type,String page,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getStudyGroupRank(uid,pid,vid,type,page,"10",Api.timeStr,Api.sign)
                .enqueue(new Callback<SGRankEntity>() {
                    @Override
                    public void onResponse(Call<SGRankEntity> call, Response<SGRankEntity> response) {
                        if (response!=null) {
                            SGRankEntity sgRankEntity = response.body();
                            if (sgRankEntity!=null) {
                                listener.onSuccess(sgRankEntity);
                            }else{
                                listener.onFault("SGRankEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGRankEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void showCoupun(String uid,String pid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .showCoupon(uid,"4",pid,Api.timeStr,Api.sign)
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


    public void getSGHome2_0(String uid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getSGHome2_0(uid,Api.timeStr,Api.sign)
                .enqueue(new Callback<SGHome2_0Entity>() {
                    @Override
                    public void onResponse(Call<SGHome2_0Entity> call, Response<SGHome2_0Entity> response) {
                        if (response!=null) {
                            SGHome2_0Entity sgHome2_0Entity = response.body();
                            if (sgHome2_0Entity!=null) {
                                if ("200".equals(sgHome2_0Entity.getCode())) {
                                    listener.onSuccess(sgHome2_0Entity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, sgHome2_0Entity.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("SGHome2_0Entity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGHome2_0Entity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void getSGHome2_0Json(String uid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getSGHome2_0Json(uid,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response!=null) {
                            try {
                                Log.e("DH_SG_HOME",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public Call<SGDetail2_0Entity> getSGDetail2_0(String uid,String pid,String vid,String type,final OnResultListener listener){
        Call<SGDetail2_0Entity> call = NetUtil.getRetrofit(Api.class)
                .getSGDetail2_0(uid, pid, vid, type, Api.timeStr, Api.sign);
        call.enqueue(new Callback<SGDetail2_0Entity>() {
                    @Override
                    public void onResponse(Call<SGDetail2_0Entity> call, Response<SGDetail2_0Entity> response) {
                        if (response!=null) {
                            SGDetail2_0Entity sgDetail2_0Entity = response.body();
                            if (sgDetail2_0Entity!=null) {
                                if ("200".equals(sgDetail2_0Entity.getCode())) {
                                    listener.onSuccess(sgDetail2_0Entity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, sgDetail2_0Entity.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("SGDetail2_0Entity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGDetail2_0Entity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
        return call;
    }

    public void getSGDetail2_0Json(String uid,String pid,String vid,String type,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getSGDetail2_0Json(uid,pid,vid,type,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response!=null) {
                            try {
                                Log.e("DH_SG_DETAIL",new String(response.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            listener.onSuccess();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void joinStudyGroup2_0(String uid,String pid,String vid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .joinStudyGroup2_0(uid,pid,vid,Api.timeStr,Api.sign)
                .enqueue(new Callback<SGJoinEntity>() {
                    @Override
                    public void onResponse(Call<SGJoinEntity> call, Response<SGJoinEntity> response) {
                        if (response!=null) {
                            SGJoinEntity sgJoinEntity = response.body();
                            if (sgJoinEntity!=null) {
                                if ("200".equals(sgJoinEntity.getCode())) {
                                    listener.onSuccess(sgJoinEntity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, sgJoinEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                listener.onFault("SGDetailEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGJoinEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void getSGRemark(String uid,String pid,String vid,String type,String page,String pageRow,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getSGRemark(uid,pid,vid,type,page,pageRow,Api.timeStr,Api.sign)
                .enqueue(new Callback<SGRemarkEntity>() {
                    @Override
                    public void onResponse(Call<SGRemarkEntity> call, Response<SGRemarkEntity> response) {
                        if (response!=null) {
                            SGRemarkEntity sgRemarkEntity = response.body();
                            if (sgRemarkEntity!=null) {
                                if ("200".equals(sgRemarkEntity.getCode())) {
                                    listener.onSuccess(sgRemarkEntity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, sgRemarkEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("SGRemarkEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGRemarkEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void getRemarkDetail(String vid,String id,String uid,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getRemarkDetail(vid,id,uid,Api.timeStr,Api.sign)
                .enqueue(new Callback<SGRemarkDetailEntity>() {
                    @Override
                    public void onResponse(Call<SGRemarkDetailEntity> call, Response<SGRemarkDetailEntity> response) {
                        if (response!=null) {
                            SGRemarkDetailEntity sgRemarkEntity = response.body();
                            if (sgRemarkEntity!=null) {
                                if ("200".equals(sgRemarkEntity.getCode())) {
                                    listener.onSuccess(sgRemarkEntity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, sgRemarkEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("SGRemarkEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<SGRemarkDetailEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void getRemarkDetailJSON(String vid,String id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getRemarkDetailJSON(vid,id,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response!=null) {
                            try {
                                Log.e("json",new String(response.body().bytes()));
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


    public void bindAlipay(String uid,String pid,String vid,String event,String uname,String alipay_accout,String alipay_name,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .bindAlipay(uid,pid,vid,event,uname,alipay_accout,alipay_name,Api.timeStr,Api.sign)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response!=null) {
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

    public void getAlipayInfo(String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getAlipayInfo(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<GetAlipayEntity>() {
                    @Override
                    public void onResponse(Call<GetAlipayEntity> call, Response<GetAlipayEntity> response) {
                        if (response!=null) {
                            if (response.body()!=null) {
                                if (response.body().getCode()==200) {
                                    listener.onSuccess(response.body());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAlipayEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }


    public void sgRemarkLike(String user_id,String id,final OnResultListener listener){
        NetUtil.getRetrofit2(Api.class)
                .sgRemarkLike(user_id,id,Api.timeStr,Api.sign)
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



    public void getNewRankList(String uid,String pid,String vid,String type,String qid,String page,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getNewRankInfo(uid,pid,vid,type,qid,page,Api.timeStr,Api.sign)
                .enqueue(new Callback<NewSGRankEntity>() {
                    @Override
                    public void onResponse(Call<NewSGRankEntity> call, Response<NewSGRankEntity> response) {
                        if (response!=null) {
                            if (response.body()!=null) {
                                NewSGRankEntity newSGRankEntity = response.body();
                                if ("200".equals(newSGRankEntity.getCode())) {
                                    listener.onSuccess(newSGRankEntity);
                                }else{
                                    Toast.makeText(MyApplication.mApplication, newSGRankEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                listener.onFault("NewSGRankEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<NewSGRankEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
