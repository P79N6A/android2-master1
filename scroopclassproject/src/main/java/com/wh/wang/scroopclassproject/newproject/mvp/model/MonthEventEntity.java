package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/5.
 */

public class MonthEventEntity {

    private List<MonthListBean> month_list;

    public List<MonthListBean> getMonth_list() {
        return month_list;
    }

    public void setMonth_list(List<MonthListBean> month_list) {
        this.month_list = month_list;
    }

    public static class MonthListBean {
        /**
         * id : 1387
         * type : 1
         * title : 超级店长提升营第14期
         * img : https://img.shaoziketang.com/img_new20180704113728_100.h.png
         * address : 沈阳
         * start_shijian : 7月25日
         * sel_type : 2
         */

        private String id;
        private String type;
        private String title;
        private String img;
        private String address;
        private String start_shijian;
        private String sel_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStart_shijian() {
            return start_shijian;
        }

        public void setStart_shijian(String start_shijian) {
            this.start_shijian = start_shijian;
        }

        public String getSel_type() {
            return sel_type;
        }

        public void setSel_type(String sel_type) {
            this.sel_type = sel_type;
        }
    }
}
