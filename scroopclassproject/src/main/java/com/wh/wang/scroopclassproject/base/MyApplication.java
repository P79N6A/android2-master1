package com.wh.wang.scroopclassproject.base;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.alivc.player.AliVcMediaPlayer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.wh.wang.scroopclassproject.downloads.DownLoadService;
import com.wh.wang.scroopclassproject.newproject.utils.CrashHandler;
import com.wh.wang.scroopclassproject.newproject.utils.LogToFile;
import com.wh.wang.scroopclassproject.utils.CacheFileUtils;
import com.wh.wang.scroopclassproject.utils.DensityUtils;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;

import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {

    public static MyApplication mApplication;

    public static String user_id;
    public static String token;
    public static String user_type;
    public static String phone;

    public static String userType;
    public static String showUrl;


    public static MyApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        AliVcMediaPlayer.init(this);
        this.startService(new Intent(this, DownLoadService.class));
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能
        UMConfigure.init(this, "", "", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        DensityUtils.init(this);
        CacheFileUtils.initFiles();
        initImageLoader();

        LogToFile.init(this);
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());

        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
                Log.d("DH_XM_LOG",tag);
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d("DH_XM_LOG", content, t);
            }

            @Override
            public void log(String content) {
                Log.d("DH_XM_LOG", content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(6)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCache(new UnlimitedDiskCache(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/poor/img")))//自定义缓存路径
                .memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(4 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(1000) //缓存的文件数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);//全局初始化此配置
        // Initialize ImageLoader with configuration.
    }
}
