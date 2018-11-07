package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by teitsuyoshi on 2018/5/2.
 */

public class ALJCommitEntity {


    /**
     * code : 200
     * msg : 领取成功
     * info : {"type":"1","name":"陈森","tel":"13106084007","address":"北京北京勺子课堂","list":[{"id":"11","pid":"111","uid":"17334","u_phone":"15575163724","u_avator":"https://www.shaoziketang.com/application/views/head/ea4145750009e6af015e3245a30e0143.jpg","u_name":"你好吗","create_time":"2018-05-02"}]}
     */

    private int code;
    private String msg;

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

}
