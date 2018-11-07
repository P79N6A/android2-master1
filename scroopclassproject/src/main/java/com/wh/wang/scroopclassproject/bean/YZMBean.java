package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;

/**
 * 验证码
 * Created by wang on 2017/12/1.
 */

public class YZMBean implements Serializable {

    private int status;
    private String info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
