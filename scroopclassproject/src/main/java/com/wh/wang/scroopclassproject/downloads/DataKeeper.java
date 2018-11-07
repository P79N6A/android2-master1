package com.wh.wang.scroopclassproject.downloads;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.wh.wang.scroopclassproject.downloads.SQLiteHelper.TABLE_NAME;

/**
 * 类功能描述：信息存储类，主要在任务下载各个环节执行数据的存储</br>
 */

public class DataKeeper {
    private static SQLiteHelper dbhelper;
    private SQLiteDatabase db;

    public DataKeeper(Context context) {
        this.dbhelper = new SQLiteHelper(context);
    }

    /**
     * (保存一个任务的下载信息到数据库)
     *
     * @param downloadInfo
     */
    public void saveDownLoadInfo(SQLDownLoadInfo downloadInfo) {
        ContentValues cv = new ContentValues();
        cv.put("userID", downloadInfo.getUserID());
        cv.put("fatherId", downloadInfo.getFatherId());
        cv.put("fatherTitle", downloadInfo.getFatherTitle());
        cv.put("fatherImg", downloadInfo.getFatherImg());
        cv.put("childId", downloadInfo.getChildId());
        cv.put("childTitle", downloadInfo.getChildTitle());
        cv.put("childUrl", downloadInfo.getChildUrl());
        cv.put("childFilePath", downloadInfo.getChildFilePath());
        cv.put("childSize", downloadInfo.getChildSize());
        cv.put("childDownSize", downloadInfo.getChildDownSize());
        cv.put("isFinish", downloadInfo.getIsFinish());
        Cursor cursor = null;
        try {
            db = dbhelper.getWritableDatabase();
            cursor = db.rawQuery(
                    "SELECT * from " + TABLE_NAME
                            + " WHERE fatherId = ? AND childId = ? ", new String[]{downloadInfo.getFatherId() + "", downloadInfo.getChildId() + ""});
            if (cursor.moveToNext()) {
                db.update(TABLE_NAME, cv, "fatherId = ? AND childId = ? ", new String[]{downloadInfo.getFatherId() + "", downloadInfo.getChildId() + ""});
            } else {
                db.insert(TABLE_NAME, null, cv);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //userID  fatherId  fatherTitle fatherImg childId childTitle childUrl childFilePath childSize childDownSize isFinish
    public SQLDownLoadInfo getDownLoadInfo(int fatherId, int childId) {
        SQLDownLoadInfo downloadinfo = null;
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * from " + TABLE_NAME
                        + "WHERE fatherId = ? AND childId = ? ", new String[]{fatherId + "", childId + ""});
        if (cursor.moveToNext()) {
            downloadinfo = new SQLDownLoadInfo();

            downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
            downloadinfo.setFatherId(cursor.getInt(cursor.getColumnIndex("fatherId")));
            downloadinfo.setFatherTitle(cursor.getString(cursor.getColumnIndex("fatherTitle")));
            downloadinfo.setFatherImg(cursor.getString(cursor.getColumnIndex("fatherImg")));
            downloadinfo.setChildId(cursor.getInt(cursor.getColumnIndex("childId")));
            downloadinfo.setChildTitle(cursor.getString(cursor.getColumnIndex("childTitle")));
            downloadinfo.setChildUrl(cursor.getString(cursor.getColumnIndex("childUrl")));
            downloadinfo.setChildFilePath(cursor.getString(cursor.getColumnIndex("childFilePath")));
            downloadinfo.setChildSize(cursor.getLong(cursor.getColumnIndex("childSize")));
            downloadinfo.setChildDownSize(cursor.getLong(cursor.getColumnIndex("childDownSize")));
            downloadinfo.setIsFinish(cursor.getInt(cursor.getColumnIndex("isFinish")));
        }
        cursor.close();
        db.close();
        return downloadinfo;
    }

    /**
     * 判断是否存在保持唯一性的id
     */
    public Boolean isHave(int fatherId, int childId) {
        db = dbhelper.getReadableDatabase();
        Boolean b = false;
        String fatherIdStr = Integer.toString(fatherId);
        String childIdStr = Integer.toString(childId);
        Cursor cursor = db.query(TABLE_NAME, null, "fatherId=? and childId=?",
                new String[]{fatherIdStr, childIdStr}, null, null, null);
        b = cursor.moveToFirst();
        cursor.close();
        db.close();
        return b;
    }


    //修改数据库中的数据
    public void updateUserDownLoadInfo(long downLoadSize, int isFinish, int childid, int fatherid) {
        db = dbhelper.getWritableDatabase();

        String where = "fatherId = ? and " + "childId = ? ";
        String isFinishStr = Integer.toString(isFinish);
        String[] whereValue = {Integer.toString(fatherid), Integer.toString(childid)};
        ContentValues cv = new ContentValues();
        cv.put("isFinish", isFinishStr);
        db.update(TABLE_NAME, cv, where, whereValue);
        db.close();
    }

    public ArrayList<SQLDownLoadInfo> getAllDownLoadInfo() {
        ArrayList<SQLDownLoadInfo> downloadinfoList = new ArrayList<SQLDownLoadInfo>();
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * from " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            SQLDownLoadInfo downloadinfo = new SQLDownLoadInfo();
            downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
            downloadinfo.setFatherId(cursor.getInt(cursor.getColumnIndex("fatherId")));
            downloadinfo.setFatherTitle(cursor.getString(cursor.getColumnIndex("fatherTitle")));
            downloadinfo.setFatherImg(cursor.getString(cursor.getColumnIndex("fatherImg")));
            downloadinfo.setChildId(cursor.getInt(cursor.getColumnIndex("childId")));
            downloadinfo.setChildTitle(cursor.getString(cursor.getColumnIndex("childTitle")));
            downloadinfo.setChildUrl(cursor.getString(cursor.getColumnIndex("childUrl")));
            downloadinfo.setChildFilePath(cursor.getString(cursor.getColumnIndex("childFilePath")));
            downloadinfo.setChildSize(cursor.getLong(cursor.getColumnIndex("childSize")));
            downloadinfo.setChildDownSize(cursor.getLong(cursor.getColumnIndex("childDownSize")));
            downloadinfo.setIsFinish(cursor.getInt(cursor.getColumnIndex("isFinish")));
            downloadinfoList.add(downloadinfo);
        }
        cursor.close();
        db.close();
        return downloadinfoList;

    }

    public ArrayList<SQLDownLoadInfo> getUserDownLoadInfo(String userID) {
        ArrayList<SQLDownLoadInfo> downloadinfoList = new ArrayList<SQLDownLoadInfo>();
        db = dbhelper.getWritableDatabase();
        try {
            Cursor cursor = null;
            cursor = db.rawQuery(
                    "SELECT * from " + TABLE_NAME + " WHERE userID = '" + userID + "'", null);
            while (cursor.moveToNext()) {
                SQLDownLoadInfo downloadinfo = new SQLDownLoadInfo();
                downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
                downloadinfo.setFatherId(cursor.getInt(cursor.getColumnIndex("fatherId")));
                downloadinfo.setFatherTitle(cursor.getString(cursor.getColumnIndex("fatherTitle")));
                downloadinfo.setFatherImg(cursor.getString(cursor.getColumnIndex("fatherImg")));
                downloadinfo.setChildId(cursor.getInt(cursor.getColumnIndex("childId")));
                downloadinfo.setChildTitle(cursor.getString(cursor.getColumnIndex("childTitle")));
                downloadinfo.setChildUrl(cursor.getString(cursor.getColumnIndex("childUrl")));
                downloadinfo.setChildFilePath(cursor.getString(cursor.getColumnIndex("childFilePath")));
                downloadinfo.setChildSize(cursor.getLong(cursor.getColumnIndex("childSize")));
                downloadinfo.setChildDownSize(cursor.getLong(cursor.getColumnIndex("childDownSize")));
                downloadinfo.setIsFinish(cursor.getInt(cursor.getColumnIndex("isFinish")));
                downloadinfoList.add(downloadinfo);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return downloadinfoList;
    }


    public ArrayList<SQLDownLoadInfo> getUserDownLoadInfoIsFinish(int isFinish) {
        ArrayList<SQLDownLoadInfo> downloadinfoList = new ArrayList<SQLDownLoadInfo>();
        db = dbhelper.getWritableDatabase();
        try {
            Cursor cursor = null;
            cursor = db.rawQuery(
                    "SELECT * from " + TABLE_NAME + " WHERE isFinish = '" + isFinish + "'", null);
            while (cursor.moveToNext()) {
                SQLDownLoadInfo downloadinfo = new SQLDownLoadInfo();
                downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
                downloadinfo.setFatherId(cursor.getInt(cursor.getColumnIndex("fatherId")));
                downloadinfo.setFatherTitle(cursor.getString(cursor.getColumnIndex("fatherTitle")));
                downloadinfo.setFatherImg(cursor.getString(cursor.getColumnIndex("fatherImg")));
                downloadinfo.setChildId(cursor.getInt(cursor.getColumnIndex("childId")));
                downloadinfo.setChildTitle(cursor.getString(cursor.getColumnIndex("childTitle")));
                downloadinfo.setChildUrl(cursor.getString(cursor.getColumnIndex("childUrl")));
                downloadinfo.setChildFilePath(cursor.getString(cursor.getColumnIndex("childFilePath")));
                downloadinfo.setChildSize(cursor.getLong(cursor.getColumnIndex("childSize")));
                downloadinfo.setChildDownSize(cursor.getLong(cursor.getColumnIndex("childDownSize")));
                downloadinfo.setIsFinish(cursor.getInt(cursor.getColumnIndex("isFinish")));
                downloadinfoList.add(downloadinfo);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return downloadinfoList;
    }

    public ArrayList<SQLDownLoadInfo> getUserDownLoadInfoByFatherId(int fatherId) {
        ArrayList<SQLDownLoadInfo> downloadinfoList = new ArrayList<SQLDownLoadInfo>();
        db = dbhelper.getWritableDatabase();
        try {
            Cursor cursor = null;
            cursor = db.rawQuery(
                    "SELECT * from " + TABLE_NAME + " WHERE fatherId = '" + fatherId + "'", null);
            while (cursor.moveToNext()) {
                SQLDownLoadInfo downloadinfo = new SQLDownLoadInfo();
                downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
                downloadinfo.setFatherId(cursor.getInt(cursor.getColumnIndex("fatherId")));
                downloadinfo.setFatherTitle(cursor.getString(cursor.getColumnIndex("fatherTitle")));
                downloadinfo.setFatherImg(cursor.getString(cursor.getColumnIndex("fatherImg")));
                downloadinfo.setChildId(cursor.getInt(cursor.getColumnIndex("childId")));
                downloadinfo.setChildTitle(cursor.getString(cursor.getColumnIndex("childTitle")));
                downloadinfo.setChildUrl(cursor.getString(cursor.getColumnIndex("childUrl")));
                downloadinfo.setChildFilePath(cursor.getString(cursor.getColumnIndex("childFilePath")));
                downloadinfo.setChildSize(cursor.getLong(cursor.getColumnIndex("childSize")));
                downloadinfo.setChildDownSize(cursor.getLong(cursor.getColumnIndex("childDownSize")));
                downloadinfo.setIsFinish(cursor.getInt(cursor.getColumnIndex("isFinish")));
                downloadinfoList.add(downloadinfo);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return downloadinfoList;
    }


    public void deleteDownLoadInfo(int fatherId, int childId) {
        db = dbhelper.getWritableDatabase();
        db.delete(TABLE_NAME, "fatherId = ? AND childId = ? ", new String[]{fatherId + "", childId + ""});
        db.close();
    }

    public void deleteUserDownLoadInfo(String userID) {
        db = dbhelper.getWritableDatabase();
        db.delete(TABLE_NAME, "userID = ? ", new String[]{userID});
        db.close();
    }

    public void deleteAllDownLoadInfo() {
        db = dbhelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
