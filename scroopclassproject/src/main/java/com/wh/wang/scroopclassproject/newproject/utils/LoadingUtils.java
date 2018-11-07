package com.wh.wang.scroopclassproject.newproject.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by teitsuyoshi on 2018/6/7.
 */

public class LoadingUtils {

    private static LoadingUtils INSTANCE = new LoadingUtils();

    private LoadingUtils() {
    }

    public static LoadingUtils getInstance(){
        if (INSTANCE==null) {
            INSTANCE = new LoadingUtils();
        }
        return INSTANCE;
    }

    public static View VideoActivityLoading(FrameLayout rootView){
        View view;

        if(!NetUtil.isNetworkAvailable(MyApplication.mApplication)){
            view = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.layout_reconnection,null,false);
            view.findViewById(R.id.reconnection_layout).setVisibility(View.VISIBLE);
            ImageView reconnectionGif = (ImageView) view.findViewById(R.id.reconnection_gif);
            TextView reconnection = (TextView) view.findViewById(R.id.reconnection);
            Glide.with(MyApplication.mApplication).load(R.drawable.nosignal).asGif().into(reconnectionGif);
            reconnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new ReconnectionEntity(true));
                    Toast.makeText(MyApplication.mApplication, "正在重新请求网络..", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            view = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.loading_activity,null,false);
            ImageView loadingImg = (ImageView) view.findViewById(R.id.loading);
            loadingImg.setImageResource(R.drawable.loading_anilist);
            AnimationDrawable animationDrawable = (AnimationDrawable) loadingImg.getDrawable();
            animationDrawable.start();
        }

        view.setVisibility(View.VISIBLE);
        rootView.addView(view);
        return view;
    }


    private static Dialog mNetDialog;

    public void showNetLoading(Context context){

        if(mNetDialog==null){
            mNetDialog = new Dialog(context,R.style.MyDialog);
            mNetDialog.setContentView(R.layout.dig_loading);
            ImageView loading = (ImageView) mNetDialog.findViewById(R.id.loading);
            loading.setImageResource(R.drawable.loading_anilist);
            AnimationDrawable animationDrawable = (AnimationDrawable) loading.getDrawable();
            animationDrawable.start();
            mNetDialog.setCanceledOnTouchOutside(false);
            mNetDialog.setCancelable(false);
        }
        mNetDialog.show();
    }

    public void hideNetLoading(){
        try {

            if(mNetDialog != null) {
                if(mNetDialog.isShowing()) { //check if dialog is showing.

                    Context context = ((ContextWrapper)mNetDialog.getContext()).getBaseContext();

                    if(context instanceof Activity) {
                        if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                            mNetDialog.dismiss();
                    } else //if the Context used wasnt an Activity, then dismiss it too
                        mNetDialog.dismiss();
                }
                mNetDialog = null;
            }

        }catch (Exception e){

        }finally {
            mNetDialog = null;
        }

    }

}
