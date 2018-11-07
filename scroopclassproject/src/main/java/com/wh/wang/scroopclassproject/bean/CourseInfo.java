package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;

/**
 * Created by wang on 2017/8/16.
 * <p>
 * 课程实体类
 */

public class CourseInfo implements Serializable {

    /**
     * id : 173
     * video_id : 1075
     * img : 9/p/~.l.png
     * title : 互联网浪潮下餐饮食材供应链的变革
     * new_price : 0
     * total_learn_nums : 0
     */

    private String id;
    private String video_id;
    private String img;
    private String title;
    private String new_price;
    private String total_learn_nums;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    public String getTotal_learn_nums() {
        return total_learn_nums;
    }

    public void setTotal_learn_nums(String total_learn_nums) {
        this.total_learn_nums = total_learn_nums;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "id='" + id + '\'' +
                ", video_id='" + video_id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", new_price='" + new_price + '\'' +
                ", total_learn_nums='" + total_learn_nums + '\'' +
                '}';
    }
}
