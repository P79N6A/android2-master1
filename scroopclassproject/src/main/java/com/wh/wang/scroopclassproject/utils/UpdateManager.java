package com.wh.wang.scroopclassproject.utils;

/**
 * Created by wang on 2017/9/17.
 */

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.UpdateBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateManager {

    private ProgressBar mProgressBar;
    private Dialog mDownloadDialog;

    private String mSavePath;
    private int mProgress;

    private boolean mIsCancel = false;

    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private Context mContext;
    private String mVersion_code;
    private String mVersion_apk;

    public UpdateManager(Context context) {
        mContext = context;
    }


    private Handler mUpdateProgressHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOADING:
                    // 设置进度条
                    mProgressBar.setProgress(mProgress);
                    break;
                case DOWNLOAD_FINISH:
                    // 隐藏当前下载对话框
                    mDownloadDialog.dismiss();
                    // 安装 APK 文件
                    installAPK();
            }
        }

        ;
    };

    /*
     * 检测软件是否需要更新
     */
    public void checkUpdate() {
        HttpUserManager.getInstance().updatess(new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, UpdateBean.class);
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                mVersion_code = jsonObject.getString("version");
                mVersion_apk = jsonObject.getString("apk");
                Log.e("whwh", "mVersion_code==" + mVersion_code);
                Log.e("whwh", "mVersion_apk==" + mVersion_apk);
                if (isUpdate()) {
                    //Toast.makeText(mContext, "需要更新", Toast.LENGTH_SHORT).show();
                    // 显示提示更新对话框
                    showNoticeDialog();
                } else {
                    //Toast.makeText(mContext, "最新版本", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /*
     * 与本地版本比较判断是否需要更新
     */
    protected boolean isUpdate() {
        int serverVersion = Integer.parseInt(mVersion_code);
        int localVersion = 1;

        try {
            localVersion = mContext.getPackageManager().getPackageInfo("com.wh.wang.scroopclassproject", 0).versionCode;
            Log.e("whwh", "localVersion===" + localVersion);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (serverVersion > localVersion)
            return true;
        else
            return false;
    }

    /*
     * 有更新时显示提示对话框
     */
    protected void showNoticeDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("提示");
        String message = "软件有更新，要下载安装吗？\n";
        builder.setMessage(message);

        builder.setPositiveButton("更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });

        builder.setNegativeButton("下次再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /*
     * 显示正在下载对话框
     */
    protected void showDownloadDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("下载中");
        View view = LayoutInflater.from(mContext).inflate(R.layout.updata_loading, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.updata_progress);
        builder.setView(view);

        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
                // 设置下载状态为取消
                mIsCancel = true;
            }
        });

        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        // 下载文件
        downloadAPK();
    }

    /*
     * 开启新线程下载文件
     */
    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                        mSavePath = sdPath + "jikedownload";
                        File dir = new File(mSavePath);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(mVersion_apk).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, "szkt");
                        //if (!apkFile.exists())


                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);

                            // 下载完成
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
     * 下载到本地后执行安装
     */
    protected void installAPK() {
        File apkFile = new File(mSavePath, "szkt");
        if (!apkFile.exists())
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, "com.wh.wang.scroopclassproject.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
        // android.os.Process.killProcess(android.os.Process.myPid());
    }
}
