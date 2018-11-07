package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by chdh on 2018/1/15.
 */

public class CheckOffLineVideoEntity {

    /**
     * code : 200
     * msg : 查询成功
     * is_buy : 1
     * is_vip : 1
     * type : 1
     */

    private int code;
    private String msg;
    private String is_buy;
    private String is_vip;
    private String type;

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

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
