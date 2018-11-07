package com.wh.wang.scroopclassproject.newproject.sql;

import android.content.Context;

/**
 * Created by teitsuyoshi on 2018/5/21.
 */

public class SQLiteHelperManager {
    private static SQLiteHelperManager manager = new SQLiteHelperManager();
    private static SQLiteHelper mSQLiteHelper;
    private SQLiteHelperManager() {
    }

    public static SQLiteHelperManager getInstance(Context context){
        if(manager == null){
            manager = new SQLiteHelperManager();
        }
        if(mSQLiteHelper==null){
            mSQLiteHelper = new SQLiteHelper(context);
        }
        return manager;
    }

}
