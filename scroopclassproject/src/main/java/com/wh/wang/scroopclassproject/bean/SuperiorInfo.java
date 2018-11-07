package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/8/14.
 * 精品的实体类
 */

public class SuperiorInfo implements Serializable {

    private List<BannerBean> banner;
    private List<EventBean> event;
    private List<SuperiorBean> good_course;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }


    public static class BannerBean implements Serializable {
        /**
         * id : 160
         * product_id : 115
         * img : 9/E/d.l.png
         * type : 1
         * isclick : 1
         */

        private String id;
        private String product_id;
        private String img;
        private String type;
        private String isclick;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsclick() {
            return isclick;
        }

        public void setIsclick(String isclick) {
            this.isclick = isclick;
        }

        @Override
        public String toString() {
            return "BannerBean{" +
                    "id='" + id + '\'' +
                    ", product_id='" + product_id + '\'' +
                    ", img='" + img + '\'' +
                    ", type='" + type + '\'' +
                    ", isclick='" + isclick + '\'' +
                    '}';
        }
    }

    public static class EventBean implements Serializable {
        /**
         * id : 123
         * event_id : 1143
         * type : 1
         * title : 外卖升级!品类革命先行
         * img : http://sz.m.wimg.m.cc/p/a/o/V.m.m.m.png
         * price : 199
         * start_shijian : 0000-00-00
         */

        private String id;
        private String event_id;
        private String type;
        private String title;
        private String img;
        private String price;
        private String start_shijian;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEvent_id() {
            return event_id;
        }

        public void setEvent_id(String event_id) {
            this.event_id = event_id;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStart_shijian() {
            return start_shijian;
        }

        public void setStart_shijian(String start_shijian) {
            this.start_shijian = start_shijian;
        }

        @Override
        public String toString() {
            return "EventBean{" +
                    "id='" + id + '\'' +
                    ", event_id='" + event_id + '\'' +
                    ", type='" + type + '\'' +
                    ", title='" + title + '\'' +
                    ", img='" + img + '\'' +
                    ", price='" + price + '\'' +
                    ", start_shijian='" + start_shijian + '\'' +
                    '}';
        }
    }

    public static class SuperiorBean implements Serializable {
        /**
         * id : 169
         * img : 7/R/W.m.png
         * title : 勺子公开课 | 系统化、双厨房，和合谷实现千家门店的两板斧!(1)
         * total_learn_nums : 0
         * name : null
         */

        private String id;
        private String img;
        private String title;
        private String total_learn_nums;
        private String new_price;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotal_learn_nums() {
            return total_learn_nums;
        }

        public void setTotal_learn_nums(String total_learn_nums) {
            this.total_learn_nums = total_learn_nums;
        }

        public String getNew_price() {
            return new_price;
        }

        public void setNew_price(String new_price) {
            this.new_price = new_price;
        }

        @Override
        public String toString() {
            return "SuperiorBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", title='" + title + '\'' +
                    ", total_learn_nums='" + total_learn_nums + '\'' +
                    ", new_price=" + new_price +
                    '}';
        }
    }


}
