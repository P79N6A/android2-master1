package com.wh.wang.scroopclassproject.downloads;

import java.io.Serializable;

/**
 * 类功能描述：下载任务对象</br>
 */
public class SQLDownLoadInfo implements Serializable{

    private String userID;
    private int fatherId;
    private String fatherTitle;
    private String fatherImg;
    private int childId;
    private String childTitle;
    private String childUrl;

    private String childFilePath;
    private long childSize;
    private long childDownSize;
    private int isFinish;

    private boolean isCheck = false;

    // private String taskID;
    //private String url;
    //private String filePath;
    //private String fileName;
    //private long fileSize;
    //private long downloadSize;


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    public String getFatherTitle() {
        return fatherTitle;
    }

    public void setFatherTitle(String fatherTitle) {
        this.fatherTitle = fatherTitle;
    }

    public String getFatherImg() {
        return fatherImg;
    }

    public void setFatherImg(String fatherImg) {
        this.fatherImg = fatherImg;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildTitle() {
        return childTitle;
    }

    public void setChildTitle(String childTitle) {
        this.childTitle = childTitle;
    }

    public String getChildUrl() {
        return childUrl;
    }

    public void setChildUrl(String childUrl) {
        this.childUrl = childUrl;
    }

    public String getChildFilePath() {
        return childFilePath;
    }

    public void setChildFilePath(String childFilePath) {
        this.childFilePath = childFilePath;
    }

    public long getChildSize() {
        return childSize;
    }

    public void setChildSize(long childSize) {
        this.childSize = childSize;
    }

    public long getChildDownSize() {
        return childDownSize;
    }

    public void setChildDownSize(long childDownSize) {
        this.childDownSize = childDownSize;
    }
}
