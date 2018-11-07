package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/9/25.
 * <p>
 * 首页第三版本页面数据
 */

public class SuperiorBean {


    private List<BannerBean> banner;
    private List<ThreecourseBean> threecourse;
    private List<EventBean> event;
    private List<JingxuanBean> jingxuan;
    private List<FreeCourseBean> free_course;
    private List<TypicalCourseBean> typical_course;
    private List<ReadNewsBean> readNews;
    private List<ReadVideoBean> readVideo;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<ThreecourseBean> getThreecourse() {
        return threecourse;
    }

    public void setThreecourse(List<ThreecourseBean> threecourse) {
        this.threecourse = threecourse;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public List<JingxuanBean> getJingxuan() {
        return jingxuan;
    }

    public void setJingxuan(List<JingxuanBean> jingxuan) {
        this.jingxuan = jingxuan;
    }

    public List<FreeCourseBean> getFree_course() {
        return free_course;
    }

    public void setFree_course(List<FreeCourseBean> free_course) {
        this.free_course = free_course;
    }

    public List<TypicalCourseBean> getTypical_course() {
        return typical_course;
    }

    public void setTypical_course(List<TypicalCourseBean> typical_course) {
        this.typical_course = typical_course;
    }

    public List<ReadNewsBean> getReadNews() {
        return readNews;
    }

    public void setReadNews(List<ReadNewsBean> readNews) {
        this.readNews = readNews;
    }

    public List<ReadVideoBean> getReadVideo() {
        return readVideo;
    }

    public void setReadVideo(List<ReadVideoBean> readVideo) {
        this.readVideo = readVideo;
    }

    public static class BannerBean implements Serializable {
        /**
         * id : 1173
         * product_id : 1173
         * img : http://sz.wimg.cc/p/a/F/Q.png
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
    }

    public static class ThreecourseBean implements Serializable {
        /**
         * hometitle : 张振强测试3
         * id : 96
         * img : http://sz.wimg.cc/p/9/q/y.png
         */

        private String hometitle;
        private String id;
        private String img;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ThreecourseBean() {
        }

        public ThreecourseBean(String hometitle) {
            this.hometitle = hometitle;
        }

        public String getHometitle() {
            return hometitle;
        }

        public void setHometitle(String hometitle) {
            this.hometitle = hometitle;
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
    }

    public static class EventBean implements Serializable {
        /**
         * id : 997
         * type : 1
         * title : 加入勺子课堂成长会员，享受更多特权!
         * img : http://sz.wimg.cc/p/8/E/L.png
         * price : 3650.00
         * start_shijian : 2017年度
         */

        private String id;
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
    }

    public static class JingxuanBean implements Serializable {
        /**
         * id : 1278
         * new_price : 0.00
         * title : 1.0 - 4.0，菜单迭代升级该这么做
         * img : http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20170918111903_100.h.png
         */

        private String id;
        private String new_price;
        private String title;
        private String img;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class FreeCourseBean implements Serializable {
        /**
         * new_price : 0.00
         * id : 838
         * img : http://sz.wimg.cc/p/7/R/W.png
         * title : 勺子公开课 | 系统化、双厨房，和合谷实现千家门店的两板斧!(1)
         * learn : 3901
         * name : 赵申
         */

        private String new_price;
        private String id;
        private String img;
        private String title;
        private String learn;
        private String name;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNew_price() {
            return new_price;
        }

        public void setNew_price(String new_price) {
            this.new_price = new_price;
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
    }

    public static class TypicalCourseBean implements Serializable {
        /**
         * id : 1183
         * img : http://sz.wimg.cc/p/a/H/Z.png
         * title : 品牌细分决定市场规模
         * total_learn_nums : 0
         * name : 史远
         * new_price : 99.00
         */

        private String id;
        private String img;
        private String title;
        private String total_learn_nums;
        private String name;
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
    }

    public static class ReadNewsBean implements Serializable {
        /**
         * id : 1292
         * title : 日翻台10次，150秒出餐，这个酸菜鱼不简单!
         * img : http://sz.wimg.cc/p/b/J/2.jpg
         */

        private String id;
        private String title;
        private String img;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class ReadVideoBean implements Serializable {
        /**
         * id : 1252
         * title : 消费者:我为什么给你的餐厅差评？【你问我答(第14期)】
         * img : http://sz.wimg.cc/p/b/d/j.png
         */

        private String id;
        private String title;
        private String img;
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
    }
}
