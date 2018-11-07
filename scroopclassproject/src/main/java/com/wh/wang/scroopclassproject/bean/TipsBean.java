package com.wh.wang.scroopclassproject.bean;

public class TipsBean {


    /**
     * message : 若您为自己报名,账号不一致,需要您使用填写开通会员的手机号进行登陆方可享受会员权
     * info : 系统检測到当前开通会员填写的手机号与您目前登陆定的手机号不一致,足否登继续报名?
     * code : -1
     */

    private String message;
    private String info;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
