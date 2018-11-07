package com.wh.wang.scroopclassproject.newproject.eventmodel;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ReplacePageEntity {
    private int pos;
    private String label_id;
    private int location;

    public ReplacePageEntity(int pos, String label_id) {
        this.pos = pos;
        this.label_id = label_id;
    }

    public ReplacePageEntity(int pos, int location) {
        this.pos = pos;
        this.location = location;
    }

    public String getLabel_id() {
        return label_id;
    }

    public void setLabel_id(String label_id) {
        this.label_id = label_id;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public ReplacePageEntity(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
