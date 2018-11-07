package com.wh.wang.scroopclassproject.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/9/12.
 */

public class SearchInfo implements Serializable {


    @SerializedName("new")
    private List<NewBean> newX;
    private List<VideoBean> video;

    public List<NewBean> getNewX() {
        return newX;
    }

    public void setNewX(List<NewBean> newX) {
        this.newX = newX;
    }

    public List<VideoBean> getVideo() {
        return video;
    }

    public void setVideo(List<VideoBean> video) {
        this.video = video;
    }

    public static class NewBean implements Serializable {
        /**
         * id : 789
         * img : http://sz.wimg.cc/p/b/l/!.l.jpg
         * title : 50平只做外卖，连开5家直营店，年入500万!
         */

        private String id;
        private String img;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        @Override
        public String toString() {
            return "NewBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public static class VideoBean implements Serializable {
        /**
         * id : 191
         * img : http://sz.wimg.cc/p/b/o/e.l.png
         * new_price : 0.0
         * title : 勺子公开课 | 打通外卖四率，让单量翻翻!
         */

        private String id;
        private String img;
        private String new_price;
        private String title;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNew_price() {
            return new_price;
        }

        public void setNew_price(String new_price) {
            this.new_price = new_price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "VideoBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", new_price='" + new_price + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
