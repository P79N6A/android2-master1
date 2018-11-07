package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/9/10.
 */

public class MineInfo {


    private List<CollectBean> collect;
    private List<OrderBean> order;
    private List<StudyBean> study;

    public List<CollectBean> getCollect() {
        return collect;
    }

    public void setCollect(List<CollectBean> collect) {
        this.collect = collect;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public List<StudyBean> getStudy() {
        return study;
    }

    public void setStudy(List<StudyBean> study) {
        this.study = study;
    }

    public static class CollectBean implements Serializable {
        /**
         * id : 793
         * img : http://sz.wimg.cc/p/b/r/H.h.jpg
         * title : 风靡美国13年的网红甜品店，来上海因排队被迫停业!发生了什么？
         * type : 2
         */

        private String id;
        private String img;
        private String title;
        private int type;
        private String money;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return "CollectBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", money='" + money + '\'' +
                    '}';
        }
    }
    
    public static class OrderBean implements Serializable {
        /**
         * id : 125
         * img : http://sz.wimg.cc/p/a/I/d.h.jpg
         * money : 999
         * title : 勺子邀你来“做东”，班级餐访式私董会!
         * type : 3
         */

        private String id;
        private String img;
        private String money;
        private String title;
        private int type;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class StudyBean implements Serializable {
        /**
         * id : 16
         * img : http://sz.wimg.cc/p/9/q/k.h.png
         * per : 2%
         * title : 营销产品化
         * type : 1
         */

        private String id;
        private String img;
        private String per;
        private String title;
        private int type;
        private String money;
        private boolean isCheck = false;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
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

        public String getPer() {
            return per;
        }

        public void setPer(String per) {
            this.per = per;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return "StudyBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", per='" + per + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", money='" + money + '\'' +
                    '}';
        }
    }

}
