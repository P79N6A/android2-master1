package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/11/8.
 */

public class DownVideoBean {

    private int fatherId;
    private String fatherTitle;
    private String fatherImg;

    private int childId;
    private String childTitle;
    private String childUrl;
    private int childStart;
    private long childFinished;
    private long childSize;
    private int childPrograss;
    private boolean isChildDownloading;
    private boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isChildDownloading() {
        return isChildDownloading;
    }

    public void setOnDownloading(boolean isChildDownloading) {
        this.isChildDownloading = isChildDownloading;
    }

    public int getProgress() {
        if (childSize == 0) {
            return 0;
        } else {
            return ((int) (100 * childFinished / childSize));
        }
    }

    public long getChildSize() {
        return childSize;
    }

    public void setChildSize(long childSize) {
        this.childSize = childSize;
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

    public int getChildStart() {
        return childStart;
    }

    public void setChildStart(int childStart) {
        this.childStart = childStart;
    }

    public long getChildFinished() {
        return childFinished;
    }

    public void setChildFinished(long childFinished) {
        this.childFinished = childFinished;
    }

    public int getChildPrograss() {
        return childPrograss;
    }

    public void setChildPrograss(int childPrograss) {
        this.childPrograss = childPrograss;
    }
}
