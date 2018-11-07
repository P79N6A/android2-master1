package com.wh.wang.scroopclassproject.newproject.mvp.presenter;

import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MemberEntity;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/22.
 */

public class CompanyMemberPresenter {

    public void getCompanyMember(String user_id,final OnResultListener listener){
        NetUtil.getRetrofit(Api.class)
                .getCompanyMember(user_id,Api.timeStr,Api.sign)
                .enqueue(new Callback<MemberEntity>() {
                    @Override
                    public void onResponse(Call<MemberEntity> call, Response<MemberEntity> response) {
//                        try {
//                            Log.e("DH_MEMBER_LIST",new String(response.body().bytes()));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        if(response!=null){
                            MemberEntity entity = response.body();
                           if(entity!=null){
                               listener.onSuccess(entity);
                           }else{
                               listener.onFault("response is NULL");
                           }
                        }else{
                            listener.onFault("MemberEntity is NULL");
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberEntity> call, Throwable t) {
                        listener.onFault("error:"+t.toString());
                    }
                });
    }
}
