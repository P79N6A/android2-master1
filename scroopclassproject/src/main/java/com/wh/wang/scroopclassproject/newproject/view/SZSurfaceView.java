package com.wh.wang.scroopclassproject.newproject.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.utils.Formatter;
import com.wh.wang.scroopclassproject.newproject.utils.NetUtil;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/1/21.
 */

public class SZSurfaceView extends RelativeLayout implements View.OnClickListener, View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    private ImageView mBack;
    private ImageView mShare;
    private TextView mSpeed;
    private SurfaceView mSzSurface;
    private AliVcMediaPlayer mPlayer;
    private ImageView mStart;
    private TextView mTotal;
    private SeekBar mBottomSeekProgress;
    private TextView mCurrent;
    //是否开始新的播放
    private boolean isStart = false;
    
    private ProgressBar mLoading;
    private ImageView mVideoImg;
    private RelativeLayout mLayoutTop;
    private LinearLayout mLayoutBottom;
    private Timer mTimer = new Timer();
    private Context mContext;
    private SumUpEntity.DirBean mUrlDir;
    private int mIndex = 0;
    private Map<String,SumUpEntity.DirBean> mUrlMap;
    private String mImgUrl;

    private FrameLayout mSurfaceContainer;
    private ImageView mFullscreen;
    private Activity mActivity;
    private boolean isCompleted;
    private boolean inSeek;

    private int mCurrentSpeed = 0;
    private float[] mSpeeds = new float[]{1.0f,1.25f,1.5f,2.0f};
    public static int FULL_SCREEN = 0;

    private int mTryIndex = 0;
    private int mBreakpoint = 0;
    public SZSurfaceView(Context context) {
        super(context,null);
    }

    public SZSurfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
        initListener();
        initVodPlayer();
    }

    public void setVideoUrl(final SumUpEntity.DirBean dir){
        isStart = false;
        mUrlDir = dir;
        if(mUrlMap!=null&&mUrlMap.size()>1){
            new Thread(){
                @Override
                public void run() {
                    Iterator<Map.Entry<String, SumUpEntity.DirBean>> iterator = mUrlMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, SumUpEntity.DirBean> next = iterator.next();
                        if(next.getValue().getUrl().equals(dir.getUrl())){
                            mIndex = Integer.parseInt(next.getKey());
                        }
                    }
                }
            }.start();
        }
    }


    private String mSumUrl = "";
    private boolean mIsSumUp = false;
    public void setVideoUrl(String url,boolean isSumUp){
        isStart = false;
        mIsSumUp = isSumUp;
        mUrlDir = new SumUpEntity.DirBean();
        mUrlDir.setUrl(url);
    }

    public void setVideoUrl(Map<String,SumUpEntity.DirBean> map, int index){
        mTryIndex = index;
        mUrlMap = map;
        setVideoUrl(map.get(index+""));
    }

    public void start(){

        if(!NetUtil.isNetworkAvailable(mContext)){
            Toast.makeText(MyApplication.mApplication, R.string.the_network_is_not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if(mUrlDir!=null&&mUrlDir.getCanshow()==null||"0".equals(mUrlDir.getCanshow())){
            Toast.makeText(MyApplication.mApplication, "该小节未到上课时间", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isStart){
            if (mPlayer != null) {
                mPlayer.reset();
                mPlayer.prepareToPlay(mUrlDir.getUrl());
                isStart = true;
                isCompleted = false;
                mLoading.setVisibility(VISIBLE);
                mStart.setVisibility(INVISIBLE);
                mStart.setImageResource(R.drawable.jc_click_pause_selector);
                if(mOnProcessClickListener!=null){
                    mOnProcessClickListener.startProcessClick();
                }
            }
        }else{
            if (mPlayer.isPlaying()) {
                pause();
            } else {
                if (mPlayer.getVolume()==0) {
                    Toast.makeText(MyApplication.mApplication, "当前处于静音状态", Toast.LENGTH_SHORT).show();
                }
                resume();
            }
        }
    }

    View view;
    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.view_sz_surfaceview,this);

        mShare = (ImageView) findViewById(R.id.share);
        mSpeed = (TextView)findViewById(R.id.speed);
        mBack = (ImageView) findViewById(R.id.back);
        mSzSurface = (SurfaceView) view.findViewById(R.id.sz_surface);
        mStart = (ImageView) findViewById(R.id.start);
        mTotal = (TextView) findViewById(R.id.total);
        mBottomSeekProgress = (SeekBar) findViewById(R.id.bottom_seek_progress);
        mCurrent = (TextView) findViewById(R.id.current);
        mLoading = (ProgressBar) findViewById(R.id.p_loading);
        mVideoImg = (ImageView) findViewById(R.id.video_img);
        mLayoutTop = (RelativeLayout) findViewById(R.id.layout_top);
        mLayoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
        mSurfaceContainer = (FrameLayout) findViewById(R.id.surface_container);
        mFullscreen = (ImageView) findViewById(R.id.fullscreen);


    }

    public void setVideoImg(String url){
        mImgUrl = url;
        mVideoImg.setVisibility(VISIBLE);
        Glide.with(mContext).load(url).centerCrop().into(mVideoImg);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    private void initListener() {
        mStart.setOnClickListener(this);
        mFullscreen.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mSurfaceContainer.setOnTouchListener(this);
        mBottomSeekProgress.setOnSeekBarChangeListener(this);
        mSzSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                // 对于从后台切换到前台,需要重+
                // 设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(holder.getSurface());
                }

            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

                if (mPlayer != null) {
                    mPlayer.setSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

        mSpeed.setOnClickListener(this);
    }

    private void initVodPlayer() {
        mPlayer = new AliVcMediaPlayer(mContext, mSzSurface);
        mPlayer.setMuteMode(false);
        mPlayer.setVolume(30);
        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//开启缓存
//        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
//        mPlayer.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
//开启循环播放
//        mPlayer.setCirclePlay(true);
        mPlayer.setPreparedListener(new MyPrepareListener(this));
        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
//        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
        mPlayer.setFrameInfoListener(new MyFrameInfoListener(this));
        mPlayer.setErrorListener(new MyErrorListener(this));
        mPlayer.setCompletedListener(new MyCompletedListener(this));
        mPlayer.setSeekCompleteListener(new MySeekCompleteListener(this));
        mPlayer.setStoppedListener(new MyPlayerStoppedListener(this));
        mPlayer.setBufferingUpdateListener(new MyBufferUpdateListener(this));
        //打开、关闭播放器日志
//        mPlayer.enableNativeLog();
        mPlayer.disableNativeLog();
    }

    //TODO
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                start();
                break;
            case R.id.back:
                if(FULL_SCREEN==1){
                    FULL_SCREEN = 0;
                    mShare.setVisibility(VISIBLE);
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    if(mOnBackClickListener!=null){
                        mOnBackClickListener.onBackClick();
                    }
                }
                break;
            case R.id.fullscreen:
                if(mActivity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    //切换竖屏
                    FULL_SCREEN = 0;
                    mShare.setVisibility(VISIBLE);
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    //切换横屏
                    FULL_SCREEN = 1;
                    mShare.setVisibility(GONE);
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                break;
            case R.id.share:
                if(mOnShareClickListener!=null){
                    mOnShareClickListener.onShareClick();
                }
                break;
            case R.id.speed:
                mCurrentSpeed++;
                if(mCurrentSpeed>mSpeeds.length-1){
                    mCurrentSpeed = 0;
                }
                mSpeed.setText(mSpeeds[mCurrentSpeed]+"x");
                mPlayer.setPlaySpeed(mSpeeds[mCurrentSpeed]);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if(isShow==0){
                    startDismissControl();
                    mLayoutTop.setVisibility(VISIBLE);
                    mLayoutBottom.setVisibility(VISIBLE);
                    mStart.setVisibility(VISIBLE);
                    isShow = 1;
                }else if(isShow==1){
                    if(mTimer==null){
                        mTimer = new Timer();
                    }
                    mTimer.cancel();
                    mLayoutTop.setVisibility(INVISIBLE);
                    mLayoutBottom.setVisibility(INVISIBLE);
                    mStart.setVisibility(INVISIBLE);
                    isShow = 0;
                }
                break;
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            clearTimer();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        startDismissControl();
        if (mPlayer != null) {
            mPlayer.seekTo(seekBar.getProgress());
            if (isCompleted) {
                inSeek = false;
            } else {
                inSeek = true;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mOnProcessClickListener!=null){
                        mOnProcessClickListener.updateProcess();
                    }
                }
            },300);
        }
    }

    public boolean isPlaying() {
        if(mPlayer!=null){
            return mPlayer.isPlaying();
        }
        return false;
    }

    //设置断点播放位置
    public void setBreakpoint(int cate_player) {
        mBreakpoint = cate_player;
    }


    private static class MyPrepareListener implements MediaPlayer.MediaPlayerPreparedListener {
        private WeakReference<SZSurfaceView> szSurfaceViewWeakReference;

        public MyPrepareListener(SZSurfaceView szSurfaceView) {
            szSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }

        @Override
        public void onPrepared() {
            SZSurfaceView szSurfaceView = szSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onPrepared();
            }
        }
    }

    private void onPrepared() {
        mPlayer.seekTo(mBreakpoint*1000);
        mPlayer.play();
        if (mPlayer.getVolume()==0) {
            Toast.makeText(MyApplication.mApplication, "当前处于静音状态", Toast.LENGTH_SHORT).show();
        }
//        isPlaying = true;
    }

    private static class MyPcmDataListener implements MediaPlayer.MediaPlayerPcmDataListener {

        private WeakReference<SZSurfaceView> szSurfaceViewWeakReference;

        public MyPcmDataListener(SZSurfaceView szSurfaceView) {
            szSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }


        @Override
        public void onPcmData(byte[] bytes, int i) {
            SZSurfaceView szSurfaceView = szSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onPcmData(bytes, i);
            }
        }
    }
    private void onPcmData(byte[] bytes, int i) {
        //pcm数据获取到了

    }

    private static class MyCircleStartListener implements MediaPlayer.MediaPlayerCircleStartListener {
        private WeakReference<SZSurfaceView> szSurfaceViewWeakReference;

        public MyCircleStartListener(SZSurfaceView szSurfaceView) {
            szSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }

        @Override
        public void onCircleStart() {

            SZSurfaceView szSurfaceView = szSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onCircleStart();
            }
        }
    }

    private void onCircleStart() {
        //循环播放开始
    }

    private static class MyFrameInfoListener implements MediaPlayer.MediaPlayerFrameInfoListener {

        private WeakReference<SZSurfaceView> szSurfaceViewWeakReference;

        public MyFrameInfoListener(SZSurfaceView szSurfaceView) {
            szSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }

        @Override
        public void onFrameInfoListener() {
            SZSurfaceView szSurfaceView = szSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onFrameInfoListener();
            }
        }
    }

    private void onFrameInfoListener() {
        inSeek = false;
        mVideoImg.setVisibility(GONE);
        mLoading.setVisibility(INVISIBLE);
        startDismissControl();
        isShow = 1;
        showVideoProgressInfo();
//        showVideoSizeInfo();
//
//        updateLogInfo();
    }
    public int getCurrentTime(){
        if(mPlayer!=null){
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }

    private int oldPosition = 0;
    private void showVideoProgressInfo() {
//        if(!NetUtil.isNetworkAvailable(mContext)){
//            if(progressUpdateTimer!=null){
//                pause();
//            }
//            Toast.makeText(MyApplication.mApplication, "当前网络不可用", Toast.LENGTH_SHORT).show();
//            return;
//        }
        int curPosition = (int) mPlayer.getCurrentPosition();
        int duration = (int) mPlayer.getDuration();
        int bufferPosition = mPlayer.getBufferPosition();

        if ((mPlayer.isPlaying())&&!inSeek) {
            //判断缓冲状态
            if(oldPosition == curPosition){
                mLoading.setVisibility(VISIBLE);
                mLayoutBottom.setVisibility(GONE);
                mLayoutTop.setVisibility(GONE);
                mStart.setVisibility(GONE);
            }else{

                mLoading.setVisibility(GONE);
            }
            oldPosition = curPosition;

            mCurrent.setText(Formatter.formatTime(curPosition));
            mTotal.setText(Formatter.formatTime(duration));
            mBottomSeekProgress.setMax(duration);
            mBottomSeekProgress.setSecondaryProgress(bufferPosition);
            mBottomSeekProgress.setProgress(curPosition);

        }
        if(curPosition>=180000||mIndex!=mTryIndex){
            if(mOnProcessClickListener!=null){
                if(!mOnProcessClickListener.processClick()){
                    mLayoutTop.setVisibility(VISIBLE);
                    mLayoutBottom.setVisibility(INVISIBLE);
                    if (mIndex!=mTryIndex)
                        mVideoImg.setVisibility(VISIBLE);
                    else
                        mVideoImg.setVisibility(GONE);
                    isShow = -1;
                    pause();
                    return;
                }else{
                    mVideoImg.setVisibility(GONE);
                }
            }
        }else{
            mVideoImg.setVisibility(GONE);
        }
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        progressUpdateTimer.removeMessages(0);
        progressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
    }

    private Handler progressUpdateTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            showVideoProgressInfo();
        }
    };

    private static class MyErrorListener implements MediaPlayer.MediaPlayerErrorListener {

        private WeakReference<SZSurfaceView> szSurfaceViewWeakReference;

        public MyErrorListener(SZSurfaceView szSurfaceView) {
            szSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }


        @Override
        public void onError(int i, String msg) {
            SZSurfaceView szSurfaceView = szSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onError(i, msg);
            }
        }
    }

    private void onError(int i, String msg) {
        Toast.makeText(MyApplication.mApplication, getResources().getString(R.string.toast_fail_msg)+msg, Toast.LENGTH_SHORT).show();
        pause();
    }

    private static class MyCompletedListener implements MediaPlayer.MediaPlayerCompletedListener {

        private WeakReference<SZSurfaceView> szSurfaceViewWeakReference;

        public MyCompletedListener(SZSurfaceView szSurfaceView) {
            szSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }

        @Override
        public void onCompleted() {
            SZSurfaceView szSurfaceView = szSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onCompleted();
            }
        }
    }

    private void onCompleted() {
        if(mUrlMap!=null){
            //TODO
//            if(mUrl!=null&&mUrl.startsWith("szzb")){
//                String[] url = mUrl.substring(0,4).split("&&");
//                if(url!=null&&url.length>0){
//                    String live = url[0];
//                    String status = url[1];
//                    String room = url[2];
//                    String pass = url[3];
//                }
//            }
            mBreakpoint = 0;
            int tempIndex = mIndex;
            if(mUrlMap.size()>mIndex+1){
                mIndex++;
                mUrlDir = mUrlMap.get(mIndex + "");
                if("0".equals(mUrlDir.getCanshow())){
                    pause();
                }else{
                    mOnNextVideoClickListener.onNextClick(mUrlDir,mIndex);
                }

                isStart = false;
                setVideoImg(mImgUrl);
            }else{
                mIndex = 0;
                isCompleted = true;
                isStart = false;
                setVideoImg(mImgUrl);
                mStart.setVisibility(VISIBLE);
                mStart.setImageResource(R.drawable.jc_click_play_selector);
                showVideoProgressInfo();
                stopUpdateTimer();
                Toast.makeText(MyApplication.mApplication, "课程播放完毕", Toast.LENGTH_SHORT).show();
            }
            if("0".equals(mUrlDir.getCanshow())){
                pause();
                Toast.makeText(MyApplication.mApplication, "下一小结尚未开放", Toast.LENGTH_SHORT).show();
                return;
            }else{
                if(mOnNextVideoClickListener!=null){
                    mOnNextVideoClickListener.onFinishClick(mUrlMap.get(tempIndex+""),tempIndex); //TODO
                }
            }

        }else{
            Toast.makeText(MyApplication.mApplication, "小结播放完毕", Toast.LENGTH_SHORT).show();
        }
    }
    public void stopUpdateTimer()  {
        if(progressUpdateTimer!=null){
            progressUpdateTimer.removeMessages(0);
        }
    }

    private static class MySeekCompleteListener implements MediaPlayer.MediaPlayerSeekCompleteListener {


        private WeakReference<SZSurfaceView> SZSurfaceViewWeakReference;

        public MySeekCompleteListener(SZSurfaceView szSurfaceView) {
            SZSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }

        @Override
        public void onSeekCompleted() {
            SZSurfaceView szSurfaceView = SZSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onSeekCompleted();
            }
        }
    }

    private void onSeekCompleted() {
//        logStrs.add(format.format(new Date()) + getString(R.string.log_seek_completed));
        inSeek = false;
    }

    private static class MyPlayerStoppedListener implements MediaPlayer.MediaPlayerStoppedListener {

        private WeakReference<SZSurfaceView> SZSurfaceViewWeakReference;

        public MyPlayerStoppedListener(SZSurfaceView szSurfaceView) {
            SZSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }

        @Override
        public void onStopped() {
            SZSurfaceView szSurfaceView = SZSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onStopped();
            }
        }
    }

    private void onStopped() {
//        logStrs.add(format.format(new Date()) + getString(R.string.log_play_stopped));
    }

    private static class MyBufferUpdateListener implements MediaPlayer.MediaPlayerBufferingUpdateListener {

        private WeakReference<SZSurfaceView> SZSurfaceViewWeakReference;

        public MyBufferUpdateListener(SZSurfaceView szSurfaceView) {
            SZSurfaceViewWeakReference = new WeakReference<SZSurfaceView>(szSurfaceView);
        }

        @Override
        public void onBufferingUpdateListener(int percent) {
            SZSurfaceView szSurfaceView = SZSurfaceViewWeakReference.get();
            if (szSurfaceView != null) {
                szSurfaceView.onBufferingUpdateListener(percent);
            }
        }
    }

    private void onBufferingUpdateListener(int percent) {
//        updateBufferingProgress(percent);
    }

    public void savePlayerState() {
        if (mPlayer.isPlaying()) {
            pause();
        }
    }
    public void  pause() {
        mLoading.setVisibility(INVISIBLE);
        mStart.setVisibility(VISIBLE);
        clearTimer();
        if (mPlayer != null) {
            mStart.setImageResource(R.drawable.jc_click_play_selector);
            mPlayer.pause();
        }
        //TODO
        if(mOnProcessClickListener!=null){
            mOnProcessClickListener.updateProcess();
        }
    }

    private void resume() {
        startDismissControl();
        mLoading.setVisibility(INVISIBLE);
        mStart.setVisibility(VISIBLE);
        if (mPlayer != null) {
            mStart.setImageResource(R.drawable.jc_click_pause_selector);
            mPlayer.play();
            showVideoProgressInfo();
        }
    }

    public void destroy() {
        clearTimer();
        if (mPlayer != null) {
            mPlayer.releaseVideoSurface();
            mPlayer.stop();
            mPlayer.destroy();
        }
    }

    public void stop() {
        stopUpdateTimer();
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    private int isShow = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
        }
        return super.onTouchEvent(event);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isShow = 0;
                    mLayoutTop.setVisibility(INVISIBLE);
                    mLayoutBottom.setVisibility(INVISIBLE);
                    mStart.setVisibility(INVISIBLE);
                    break;
            }
        }
    };

    public class  ControllerTimerTask extends TimerTask{

        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    }
    
    public void startDismissControl(){
        clearTimer();
        mTimer = new Timer();
        mTimer.schedule(new ControllerTimerTask(),2500);
    }

    private void clearTimer() {
        if(mTimer!=null){
            mTimer.cancel();
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。
//            FULL_SCREEN = 0;
//            //显示状态栏
//            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//            //设置view的布局，宽高之类
//            ViewGroup.LayoutParams surfaceViewLayoutParams = view.getLayoutParams();
//            surfaceViewLayoutParams.height = (int) (getWight(mContext) * 9.0f / 16);
//            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//
//        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
//            FULL_SCREEN = 1;
//            //隐藏状态栏
//            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
//            //设置view的布局，宽高
//            ViewGroup.LayoutParams surfaceViewLayoutParams = view.getLayoutParams();
//            surfaceViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            surfaceViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//
//        }
//    }
//
//    public static int getWight(Context mContext) {
//        DisplayMetrics dm = new DisplayMetrics();
//        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenWidth = dm.widthPixels;
//        return screenWidth;
//    }

    //分享回调
    private OnShareClickListener mOnShareClickListener;

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        mOnShareClickListener = onShareClickListener;
    }

    public interface OnShareClickListener{
        void onShareClick();
    }

    //进度回调
    private OnProcessClickListener mOnProcessClickListener;

    public void setOnProcessClickListener(OnProcessClickListener onProcessClickListener) {
        mOnProcessClickListener = onProcessClickListener;
    }

    public interface OnProcessClickListener{
        boolean processClick();
        void updateProcess();
        void startProcessClick();
    }


    private OnNextVideoClickListener mOnNextVideoClickListener;

    public void setOnNextVideoClickListener(OnNextVideoClickListener mOnNextVideoClickListener) {
        this.mOnNextVideoClickListener = mOnNextVideoClickListener;
    }

    //视频切换回调
    public interface OnNextVideoClickListener{
        void onNextClick(SumUpEntity.DirBean dirBean,int index);
        void onFinishClick(SumUpEntity.DirBean dirBean,int index);
    }

    //Back回调
    private OnBackClickListener mOnBackClickListener;

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        mOnBackClickListener = onBackClickListener;
    }

    public interface OnBackClickListener{
        void onBackClick();
    }
}
