package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/11/21.
 */

public class JXKBean implements Serializable {


    public static class ElaborateCourseBean implements Serializable {

        private String id;
        private String ifon;
        private String name;
        private String pai;
        private String parent_id;
        private List<CourseDetailBean> course_detail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIfon() {
            return ifon;
        }

        public void setIfon(String ifon) {
            this.ifon = ifon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPai() {
            return pai;
        }

        public void setPai(String pai) {
            this.pai = pai;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public List<CourseDetailBean> getCourse_detail() {
            return course_detail;
        }

        public void setCourse_detail(List<CourseDetailBean> course_detail) {
            this.course_detail = course_detail;
        }

        public static class CourseDetailBean implements Serializable{
            /**
             * id : 61
             * img : http://sz.wimg.cc/p/a/F/P.png
             * new_price : 99.00
             * teacher_id : 197
             * teacher_name : 李明元
             * title : 成为未来的餐饮玩家，你将需要这几项能力
             */

            private String id;
            private String img;
            private String new_price;
            private String teacher_id;
            private String teacher_name;
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

            public String getTeacher_id() {
                return teacher_id;
            }

            public void setTeacher_id(String teacher_id) {
                this.teacher_id = teacher_id;
            }

            public String getTeacher_name() {
                return teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

    }

    public static class EventsBean implements Serializable {
        /**
         * id : 1305
         * img : http://sz.wimg.cc/img20171115190922_100.h.png
         * price : 1999.00
         * title : 这门餐饮老板都在学的连锁加盟必修课，今年最后一场啦！
         * vip_price : 499.00
         */

        private String id;
        private String img;
        private String price;
        private String title;
        private String vip_price;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVip_price() {
            return vip_price;
        }

        public void setVip_price(String vip_price) {
            this.vip_price = vip_price;
        }
    }

    public static class ScrollBean implements Serializable {
        /**
         * backcolor : #fdfdfd
         * id : 1303
         * img : http://sz.wimg.cc/img20171108173301_100.h.png
         * isclick : 1
         * product_id : 1303
         * title : 勺子实训营 | 外卖新手开店指南
         * type : 1
         * x : 373
         */

        private String backcolor;
        private String id;
        private String img;
        private String isclick;
        private String product_id;
        private String title;
        private String type;
        private String x;

        public String getBackcolor() {
            return backcolor;
        }

        public void setBackcolor(String backcolor) {
            this.backcolor = backcolor;
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

        public String getIsclick() {
            return isclick;
        }

        public void setIsclick(String isclick) {
            this.isclick = isclick;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }
    }

}
