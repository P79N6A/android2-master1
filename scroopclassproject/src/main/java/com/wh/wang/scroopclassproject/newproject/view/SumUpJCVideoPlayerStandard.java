package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 这里可以监听到视频播放的生命周期和播放状态
 * 所有关于视频的逻辑都应该写在这里
 * Created by Nathen on 2017/7/2.
 */
public class SumUpJCVideoPlayerStandard extends JCVideoPlayerStandard {

    public static SumUpActivity activity;

    public SumUpJCVideoPlayerStandard(Context context) {
        super(context);
    }

    public SumUpJCVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == fm.jiecao.jcvideoplayer_lib.R.id.fullscreen) {
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                //click quit fullscreen
            } else {
                //click goto fullscreen
            }
        }

        if (i == fm.jiecao.jcvideoplayer_lib.R.id.back) {
            if (currentScreen != SCREEN_WINDOW_FULLSCREEN) {
                activity.finish();
            }
        }
        if(i == fm.jiecao.jcvideoplayer_lib.R.id.share){
            activity.share();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void startVideo() {
        super.startVideo();
    }

    /**
     * onPrepared
     */
    @Override
    public void onVideoRendingStart() {
        super.onVideoRendingStart();
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
    }

    @Override
    public void onStatePlaybackBufferingStart() {
        super.onStatePlaybackBufferingStart();
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
    }

    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
    }

    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
    }

    @Override
    public void setProgressAndText(int progress, int position, int duration) {
        super.setProgressAndText(progress, position, duration);
//        activity.progress(position);
//        activity.videoProgress(position);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        int data = activity.videosizes;
        Log.e("whwh", "data===" + data);
        int videoindex = activity.videoindex;
        Log.e("whwh", "index===" + videoindex);
        if (videoindex < data) {
            onStatePreparingChangingUrl(videoindex, 0);
            startVideo();
        } else {
            onStatePause();
        }
        Log.e("whwh", "onAutoCompletion");
    }
}
