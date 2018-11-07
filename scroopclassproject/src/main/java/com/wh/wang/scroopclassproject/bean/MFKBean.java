package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;

/**
 * Created by wang on 2017/11/21.
 */

public class MFKBean implements Serializable {


    public static class FreeCourseBean implements Serializable {
        /**
         * duan : 上海赤火品牌营销机构 创始人兼CEO
         * id : 1304
         * img : http://sz.wimg.cc/img20171113165102_100.h.png
         * learn : 6744
         * name : 李火苗
         * new_price : 0.00
         * title : 免费公开课 | 从8000万到30亿，我是如何打造周黑鸭品牌的？
         */

        private String duan;
        private String id;
        private String img;
        private String learn;
        private String name;
        private String new_price;
        private String title;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDuan() {
            return duan;
        }

        public void setDuan(String duan) {
            this.duan = duan;
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

        public String getLearn() {
            return learn;
        }

        public void setLearn(String learn) {
            this.learn = learn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
