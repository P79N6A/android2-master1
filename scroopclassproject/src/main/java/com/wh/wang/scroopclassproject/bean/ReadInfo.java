package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/8/16.
 */

public class ReadInfo implements Serializable{


    private List<BannerBean> banner;
    private List<NewsBean> news;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public static class BannerBean implements Serializable{
        /**
         * id :
         * img :
         * isclick :
         * product_id :
         * type:
         *
         */

        private String id;
        private String img;
        private String isclick;
        private String product_id;
        private String type;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "BannerBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", isclick='" + isclick + '\'' +
                    ", product_id='" + product_id + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public static class NewsBean implements Serializable{
        /**
         * post_title : 麦当劳餐厅动线悄然变革，这场精心设计的“局”你学会了吗？
         * post_key : 5993a53e68a71
         * post_type : news
         * post_image : a/R/R.jpg|900|500
         * cate_name : 营销
         * create_date : 8-16
         */

        private String author;
        private String id;
        private String img;
        private String publish_shijian;
        private String title;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public String getPublish_shijian() {
            return publish_shijian;
        }

        public void setPublish_shijian(String publish_shijian) {
            this.publish_shijian = publish_shijian;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "NewsBean{" +
                    "author='" + author + '\'' +
                    ", id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", publish_shijian='" + publish_shijian + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
