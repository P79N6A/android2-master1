package com.wh.wang.scroopclassproject.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wh.wang.scroopclassproject.bean.DownVideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/11/9.
 */

public class VideoDB extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "VIDEODATA.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "video_table";
    private static VideoDB videoDB = null;

    private VideoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static VideoDB getInstance(Context context) {
        if (null == videoDB) {
            videoDB = new VideoDB(context);
        }

        return videoDB;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + "videoDown" + "("
                + "fatherId" + " INTEGER( 11 ),"
                + "fatherTitle" + " varchar( 255 ),"
                + "fatherImg" + " varchar( 255 ),"
                + "childId" + " INTEGER( 11 ),"
                + "childTitle" + " varchar( 255 ),"
                + "childUrl" + " varchar( 255 ),"
                + "childStart" + " INTEGER( 11 ),"
                + "childFinished" + " INTEGER( 11 ),"
                + "childPrograss" + " INTEGER( 11 ));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 添加数据的操作
     */
    public long insertVideo(DownVideoBean downVideoBean) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fatherId", downVideoBean.getFatherId());
        cv.put("fatherTitle", downVideoBean.getFatherTitle());
        cv.put("fatherImg", downVideoBean.getFatherImg());
        cv.put("childId", downVideoBean.getChildId());
        cv.put("childTitle", downVideoBean.getChildTitle());
        cv.put("childUrl", downVideoBean.getChildUrl());
        cv.put("childStart", downVideoBean.getChildStart());
        cv.put("childFinished", downVideoBean.getChildFinished());
        cv.put("childPrograss", downVideoBean.getChildPrograss());

        long row = db.insert("videoDown", null, cv);
        db.close();
        return row;

    }

    /**
     * 判断是否存在保持唯一性的id
     */
    public Boolean isHave(int fatherId, int childId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean b = false;
        String fatherIdStr = Integer.toString(fatherId);
        String childIdStr = Integer.toString(childId);
        Cursor cursor = db.query("videoDown", null, "fatherId=? and childId=?",
                new String[]{fatherIdStr, childIdStr}, null, null, null);
        b = cursor.moveToFirst();
        cursor.close();
        db.close();
        return b;
    }

    /**
     * 根据id删除数据的操作
     */
    public void deleteVideo(int fatherId, int childId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "fatherId=? and childId=?";
        String[] whereValue = {Integer.toString(fatherId), Integer.toString(childId)};
        db.delete("videoDown", where, whereValue);
        db.close();
    }


    /**
     * 读取所有数据的操作
     */
    public List<DownVideoBean> ReadVideoAll() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<DownVideoBean> readvideoAllList = new ArrayList<DownVideoBean>();
        Cursor cursor = db.query("videoDown", new String[]{"fatherId", "fatherTitle",
                        "fatherImg", "childId", "childTitle", "childUrl", "childStart", "childFinished",
                        "childPrograss"}, null, null, null, null,
                null);
        while (cursor.moveToNext()) {
            DownVideoBean allVideoBean = new DownVideoBean();
            int fatherId = cursor.getInt(cursor.getColumnIndex("fatherId"));
            String fatherTitle = cursor.getString(cursor.getColumnIndex("fatherTitle"));
            String fatherImg = cursor.getString(cursor.getColumnIndex("fatherImg"));

            int childId = cursor.getInt(cursor.getColumnIndex("childId"));
            String childTitle = cursor.getString(cursor.getColumnIndex("childTitle"));
            String childUrl = cursor.getString(cursor.getColumnIndex("childUrl"));
            int childStart = cursor.getInt(cursor.getColumnIndex("childStart"));
            int childFinished = cursor.getInt(cursor.getColumnIndex("childFinished"));
            int childPrograss = cursor.getInt(cursor.getColumnIndex("childPrograss"));

            allVideoBean.setFatherId(fatherId);
            allVideoBean.setFatherTitle(fatherTitle);
            allVideoBean.setFatherImg(fatherImg);
            allVideoBean.setChildId(childId);
            allVideoBean.setChildTitle(childTitle);
            allVideoBean.setChildUrl(childUrl);
            allVideoBean.setChildStart(childStart);
            allVideoBean.setChildFinished(childFinished);
            allVideoBean.setChildPrograss(childPrograss);

            readvideoAllList.add(allVideoBean);
        }
        cursor.close();
        db.close();
        return readvideoAllList;
    }

    /**
     * 根据id读取所有数据操作
     */
    public List<DownVideoBean> ReadVideoById(int fatherId) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<DownVideoBean> readvideoIdList = new ArrayList<DownVideoBean>();
        Cursor cursor = db.query("videoDown", new String[]{"fatherTitle",
                        "fatherImg", "childId", "childTitle", "childUrl", "childStart", "childFinished",
                        "childPrograss"}, "fatherId = ?", new String[]{fatherId + ""}, null, null,
                null);
        while (cursor.moveToNext()) {
            DownVideoBean idVideoBean = new DownVideoBean();
            String fatherTitle = cursor.getString(cursor.getColumnIndex("fatherTitle"));
            String fatherImg = cursor.getString(cursor.getColumnIndex("fatherImg"));
            int childId = cursor.getInt(cursor.getColumnIndex("childId"));
            String childTitle = cursor.getString(cursor.getColumnIndex("childTitle"));
            String childUrl = cursor.getString(cursor.getColumnIndex("childUrl"));
            int childStart = cursor.getInt(cursor.getColumnIndex("childStart"));
            int childFinished = cursor.getInt(cursor.getColumnIndex("childFinished"));
            int childPrograss = cursor.getInt(cursor.getColumnIndex("childPrograss"));

            idVideoBean.setFatherId(fatherId);
            idVideoBean.setFatherTitle(fatherTitle);
            idVideoBean.setFatherImg(fatherImg);
            idVideoBean.setChildId(childId);
            idVideoBean.setChildTitle(childTitle);
            idVideoBean.setChildUrl(childUrl);
            idVideoBean.setChildStart(childStart);
            idVideoBean.setChildFinished(childFinished);
            idVideoBean.setChildPrograss(childPrograss);

            readvideoIdList.add(idVideoBean);
        }
        cursor.close();
        db.close();
        return readvideoIdList;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("videoDown", null, null);
        db.close();
    }
}
