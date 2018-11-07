package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;

/**
 * Created by wang on 2017/8/18.
 *
 * 注册实体类
 */

public class registerBean implements Serializable{


    /**
     * code : 200
     * msg : 恭喜，注册成功
     * user_id : 27153
     */

    private int code;
    private String msg;
    private int user_id;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "registerBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
