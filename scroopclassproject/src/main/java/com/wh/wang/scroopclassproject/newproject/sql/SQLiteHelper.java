package com.wh.wang.scroopclassproject.newproject.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by teitsuyoshi on 2018/5/21.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    //当前数据可版本
    private static final int CURRENT_VERSION = 1;
    //数据库名字
    private static final String DATA_NAME = "sz_database";
    //创建视频进度数据库
    private final String CREATE_VIDEO_PROGRESS = "create table video_progress (id text primary key , progress text)";
    public SQLiteHelper(Context context) {
        super(context, DATA_NAME, null, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_VIDEO_PROGRESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.e("DH_DATABASE","数据库已经打开");
    }
}
