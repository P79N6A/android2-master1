package com.wh.wang.scroopclassproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class LocalVideoActivity extends AppCompatActivity {

    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private SQLDownLoadInfo localBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_video);
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.jiecao_player_view);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        localBean = (SQLDownLoadInfo) getIntent().getSerializableExtra("localBean");
        String path = localBean.getChildFilePath();
        String name = localBean.getChildTitle();
        Log.e("whwh", "path==" + path + "==" + "name==" + name);
        jcVideoPlayerStandard.setUp(path, JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "" + name);
        jcVideoPlayerStandard.startVideo();  // 开始自动播放

    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        // 视频的回退按钮设置点击事件
        jcVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                jcVideoPlayerStandard.release(); // 释放视频
                LocalVideoActivity.this.finish(); // 结束当前界面
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            jcVideoPlayerStandard.release(); // 释放视频
            LocalVideoActivity.this.finish(); // 结束当前界面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
