package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by chdh on 2018/1/22.
 */

public class AdminEntity {

    /**
     * code : -1
     * msg : 信息错误，请核对
     * info :
     */

    private int code;
    private String msg;
    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
