package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by Administrator on 2017/12/14.
 */

public class CheckEventEntity {

    /**
     * status : 1
     * info : 报名资料检查成功，请完成支付!
     */

    private int status;
    private String info;
    private String money;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

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
