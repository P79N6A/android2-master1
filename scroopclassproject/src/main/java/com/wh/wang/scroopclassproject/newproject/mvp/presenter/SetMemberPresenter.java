package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.AdminEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DeleteMember;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/22.
 */

public class SetMemberPresenter {

    public void setAdmin(String parent_id, String child_id,String type, final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .setAdmin(parent_id,child_id,type,Api.timeStr,Api.sign)
                .enqueue(new Callback<AdminEntity>() {
                    @Override
                    public void onResponse(Call<AdminEntity> call, Response<AdminEntity> response) {
                        if(response!=null){
                            AdminEntity entity = response.body();
                            if(entity!=null){
                                listener.onSuccess(entity);
                            }else{
                                listener.onFault("AdminEntity is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<AdminEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }

    public void delMember(String parent_id, String child_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .delMember(parent_id,child_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<DeleteMember>() {
                    @Override
                    public void onResponse(Call<DeleteMember> call, Response<DeleteMember> response) {
                        if(response!=null){
//                            try {
//                                Log.e("DH_DEL",new String(response.body().bytes()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            DeleteMember deleteMember = response.body();
                            if(deleteMember!=null){
                                listener.onSuccess(deleteMember);
                            }else{
                                listener.onFault("DeleteMember is NULL");
                            }
                        }else{
                            listener.onFault("response is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteMember> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
