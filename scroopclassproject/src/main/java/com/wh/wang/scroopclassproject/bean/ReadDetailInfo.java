package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/9/13.
 */

public class ReadDetailInfo {

    /**
     * id : 796
     * title : 参透人性!餐饮玩法的底层逻辑
     * sub_title : 5YWt5bqm56m66Ze044CB57qm5pK46YO95piv5LuA5LmI6ay877yf5Li65LuA5LmI4oCc55yf5q2j5L2T4oCd5Lya6K6p5Lq65LmQ5LqO5Lyg5pKt77yf6L+Z6IOM5ZCO5YW25a6e5pyJ552A5LiA5aWX5a6M5pW06YC76L6R5ZKM5omT5rOVIQ==
     * author : 杨庆亮
     * publish_shijian : 2016-02-17 15:21:29
     * collect_status :
     */

    private String id;
    private String title;
    private String sub_title;
    private String author;
    private String publish_shijian;
    private String collect_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish_shijian() {
        return publish_shijian;
    }

    public void setPublish_shijian(String publish_shijian) {
        this.publish_shijian = publish_shijian;
    }

    public String getCollect_status() {
        return collect_status;
    }

    public void setCollect_status(String collect_status) {
        this.collect_status = collect_status;
    }

    @Override
    public String toString() {
        return "ReadDetailInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", sub_title='" + sub_title + '\'' +
                ", author='" + author + '\'' +
                ", publish_shijian='" + publish_shijian + '\'' +
                ", collect_status='" + collect_status + '\'' +
                '}';
    }
}
