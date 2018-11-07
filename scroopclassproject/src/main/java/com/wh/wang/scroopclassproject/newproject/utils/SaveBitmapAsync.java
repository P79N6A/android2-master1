package com.wh.wang.scroopclassproject.newproject.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.base.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by teitsuyoshi on 2018/5/22.
 */

public class SaveBitmapAsync extends AsyncTask<Bitmap,Void,Void> {
    private Activity activity;
    private String fileName;
    private boolean isRecycle = true;
    public SaveBitmapAsync() {
    }

    public SaveBitmapAsync(Activity activity, String fileName,boolean isRecycle) {
        this.activity = activity;
        this.fileName = fileName;
        this.isRecycle = isRecycle;
    }

    public SaveBitmapAsync(Activity activity, String fileName,boolean isRecycle,OnSaveImgClickListener listener) {
        this.activity = activity;
        this.fileName = fileName;
        this.isRecycle = isRecycle;
        mOnSaveImgClickListener = listener;
    }

    @Override
    protected Void doInBackground(Bitmap... bitmaps) {
        saveImageToGallery(bitmaps[0]);
        publishProgress();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mOnSaveImgClickListener!=null) {
            mOnSaveImgClickListener.onSaveSuccessClick();
        }else{
            Toast.makeText(MyApplication.mApplication, "保存成功", Toast.LENGTH_SHORT).show();
        }

    }

    public void saveImageToGallery(Bitmap bmp) {
        if(bmp==null) return;
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
//        String fileName = file.getAbsolutePath();
//        File appDir = new File(file ,fileName);
//        if (!appDir.exists()) {
//            appDir.mkdirs();
//        }
        File currentFile = new File(file, fileName);
        if(currentFile.exists()){
            currentFile.delete();
            try {
                currentFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            if(isRecycle){
                bmp.recycle();
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }

    private OnSaveImgClickListener mOnSaveImgClickListener;
    public interface OnSaveImgClickListener{
        void onSaveSuccessClick();
    }
}
