package com.wh.wang.scroopclassproject.newproject.sql;

import android.content.Context;

/**
 * Created by teitsuyoshi on 2018/5/21.
 */

public class VideoProgressManager {
    private static VideoProgressManager videoProgressManager;
    private SQLiteHelperManager sqLiteHelperManager;
    private VideoProgressManager(Context context) {
        sqLiteHelperManager = SQLiteHelperManager.getInstance(context);
    }

    public static VideoProgressManager getInstance(Context context){
        if(videoProgressManager==null){
            videoProgressManager = new VideoProgressManager(context);
        }
        return videoProgressManager;
    }

}
