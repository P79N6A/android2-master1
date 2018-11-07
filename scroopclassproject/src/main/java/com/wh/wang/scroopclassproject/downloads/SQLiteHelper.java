//
//  SQLiteHelper.java
//  FeOA
//
//  Created by LuTH on 2011-12-17.
//  Copyright 2011 flyrise. All rights reserved.
//
package com.wh.wang.scroopclassproject.downloads;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 类功能描述：</br>
 *
 * @author zhuiji7
 * @version 1.0
 *          </p>
 * @email 470508081@qq.com
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String mDatabasename = "filedownloader3";
    private static CursorFactory mFactory = null;
    private static final int mVersion = 1;
    public static final String TABLE_NAME = "downloadinfo3"; //文件下载信息数据表名称

    public SQLiteHelper(Context context) {
        super(context, mDatabasename, mFactory, mVersion);
    }

    public SQLiteHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建文件下载信息数据表
        String downloadsql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
                + "userID VARCHAR, "
                + "fatherId VARCHAR, "
                + "fatherTitle VARCHAR, "
                + "fatherImg VARCHAR, "
                + "childId VARCHAR, "
                + "childTitle VARCHAR, "
                + "childUrl VARCHAR, "
                + "childFilePath VARCHAR, "
                + "childSize VARCHAR, "
                + "childDownSize VARCHAR, "
                + "isFinish INTEGER( 11 ) "
                + ")";
        db.execSQL(downloadsql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
