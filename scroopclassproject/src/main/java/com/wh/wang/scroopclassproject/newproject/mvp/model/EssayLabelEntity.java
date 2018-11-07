package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by teitsuyoshi on 2018/8/3.
 */

public class EssayLabelEntity {

    private String type;
    private String name;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
