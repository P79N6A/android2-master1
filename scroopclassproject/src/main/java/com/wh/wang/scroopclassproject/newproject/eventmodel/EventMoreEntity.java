package com.wh.wang.scroopclassproject.newproject.eventmodel;

/**
 * Created by chdh on 2018/1/15.
 */

public class EventMoreEntity {
    private String name;
    private String tel;
    private String price;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
