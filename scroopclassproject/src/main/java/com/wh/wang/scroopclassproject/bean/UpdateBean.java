package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/9/18.
 */

public class UpdateBean {

    /**
     * version : 2
     * apk : http://apptwo.shaoziketang.com/shaoziAndroidApk.apk
     */

    private String version;
    private String apk;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "version='" + version + '\'' +
                ", apk='" + apk + '\'' +
                '}';
    }
}
