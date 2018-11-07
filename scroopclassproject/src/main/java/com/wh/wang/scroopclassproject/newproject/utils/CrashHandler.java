package com.wh.wang.scroopclassproject.newproject.utils;

/**
 * Created by teitsuyoshi on 2018/9/10.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeSet;

import android.text.format.Time;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.mvp.presenter.UpLogPresenter;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    public static final boolean DEBUG = true;
    /** 系统默认的UncaughtException处理类 */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler INSTANCE;

    private Context mContext;
    /** 使用Properties来保存设备的信息和错误堆栈信息*/
    private Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "STACK_TRACE";
    /** 错误报告文件的扩展名 */
    private static final String CRASH_REPORTER_EXTENSION = ".cr";

    private static  Object syncRoot = new Object();

    private UpLogPresenter mUpLogPresenter = new UpLogPresenter();
    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {}

    /** 获取CrashHandler实例 ,单例模式*/
    public static CrashHandler getInstance() {
       /* if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;*/
        // 防止多线程访问安全，这里使用了双重锁
        if (INSTANCE == null)
        {

            synchronized (syncRoot)
            {

                if (INSTANCE == null)
                {
                    INSTANCE =  new CrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象,
     * 获取系统默认的UncaughtException处理器,
     * 设置该CrashHandler为程序的默认处理器
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //Sleep一会后结束程序 给保存本地和上传服务器预留时间
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }

//            Intent intent = new Intent(mContext, WelcomeActivity.class);
//            @SuppressLint("WrongConstant")
//            PendingIntent restartIntent = PendingIntent.getActivity(
//                    mContext.getApplicationContext(), 0, intent,
//                    Intent.FLAG_ACTIVITY_NEW_TASK);
//            //退出程序
//            AlarmManager mgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
//                    restartIntent); // 1秒钟后重启应用

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息
     * 发送错误报告等操作均在此完成.
     * 开发者可以根据自己的情况来自定义异常处理逻辑
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            Log.w(TAG, "handleException --- ex==null");
            return true;
        }
        final String msg = ex.toString() + "\r\n";

//        final String msg = ex.getLocalizedMessage() + "\r\n";
        if(msg == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
//                Looper.prepare(); //主线程里面搞事情 Toast

                if(DEBUG){
                    //收集设备信息
//                    collectCrashDeviceInfo(mContext);
                    //保存错误报告文件
                    String stackInfo = "\r\n";
                    for (int i = 0; i < ex.getStackTrace().length; i++) {
                        stackInfo +=ex.getStackTrace()[i].toString()+"\r\n";
                    }
                    String errorInfo = msg+stackInfo+collectCrashDeviceInfo(mContext);
                    String path = LogToFile.w("my",errorInfo);

                    File file = new File(path);
                    if(file.exists()){
                        String upfile = Base64.encodeToString(errorInfo.getBytes(),Base64.DEFAULT);
                        Log.e("upfile",upfile);
                        mUpLogPresenter.upLogFile(upfile);

//                        Toast toast = Toast.makeText(mContext, "程序异常，正在生成错误报\n告，即将退出",
//                                Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
                    }


                }
//                Looper.loop();
            }
        }.start();

        //保存错误报告文件
//        saveCrashInfoToFile(ex);
        //发送错误报告到服务器
        //sendCrashReportsToServer(mContext);
        return true;
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext);
    }
    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     * @param ctx
     */
    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));
            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                postReport(cr);
                cr.delete();// 删除已发送的报告
            }
        }
    }
    private void postReport(File file) {
        // TODO 发送错误报告到服务器
    }

    /**
     * 获取错误报告文件名
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    /**
     * 保存错误信息到文件中
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();
        mDeviceCrashInfo.put("EXEPTION", ex.getLocalizedMessage());
        mDeviceCrashInfo.put(STACK_TRACE, result);
        try {
            //long timestamp = System.currentTimeMillis();
            Time t = new Time("GMT+8");
            t.setToNow(); // 取得系统时间
            int date = t.year * 10000 + t.month * 100 + t.monthDay;
            int time = t.hour * 10000 + t.minute * 100 + t.second;
            String fileName = "crash-" + date + "-" + time + CRASH_REPORTER_EXTENSION;
            FileOutputStream trace = mContext.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            mDeviceCrashInfo.store(trace, "");
            trace.flush();
            trace.close();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing report file...", e);
        }
        return null;
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    public String collectCrashDeviceInfo(Context ctx) {
        String msg = "\r\nandroid ver : "+BuildVars.BuildInfo(android.os.Build.VERSION.SDK_INT)+"  "+android.os.Build.VERSION.SDK_INT+"\r\n";
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME,
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, ""+pi.versionCode);

                msg += VERSION_CODE+" : "+pi.versionCode+"\r\n";
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
            msg+="Error while collect crash info"+e+"\r\n";
        }
        //使用反射来收集设备信息.在Build类中包含各种设备信息,
        //例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        //具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), ""+field.get(null));
                if (DEBUG) {
//                    Log.d(TAG, field.getName() + " : " + field.get(null));
                    msg += field.getName()+" : "+field.get(null)+"\r\n";
                }

            } catch (Exception e) {
                msg+="Error while collect crash info"+e+"\r\n";
                Log.e(TAG, "Error while collect crash info", e);
            }
        }
        return msg;
    }


}