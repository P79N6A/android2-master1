package com.wh.wang.scroopclassproject.newproject.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class NewSearchResultEntity {

    /**
     * courseList : [{"id":"1305","type":"3","img":"https://img.shaoziketang.com/img20180614163903_100.h.jpg","title":"1年从0做到8000万，我是如何玩转外卖平台的？","learn":"1万+","teacher_name":"曾姝骞","sub_title":"开店前你应该做哪些准备？","good":"96","cate_id":"-1","rank":"2","class":"3","cate":"0"},{"id":"1309","type":"0","img":"https://img.shaoziketang.com/img20180614165420_100.h.jpg","title":"如何用最少的钱让排名进入外卖平台首页？","learn":"1万+","teacher_name":"李知洋","sub_title":"竞价推广怎么玩？","good":"97","cate_id":"-1","rank":"2","class":"3","cate":"0"},{"id":"1192","type":"0","img":"https://img.shaoziketang.com/img20180314182243_100.h.jpg","title":"外卖选品怎么做","learn":"1万+","teacher_name":"吴楠","sub_title":"本次课程从找到自己真正的竞争对手出发，学会如何获取数据并加以分析、找到自己的市场新出路，才可能在外卖红海中占有一席之地。","good":"97","cate_id":"-1","rank":"2","class":"3","cate":"0"}]
     * courseCount : 39
     * newsList : [{"id":"1662","title":"外卖店铺装修做到这4点，你的订单也蹭蹭涨...","sub_title":"这是一个颜值即正义的时代...","publish_shijian":"2018-06-24 00:00:00","img":"https://img.shaoziketang.com/img20180624024719_100.h.jpg","rank":"2"},{"id":"1631","title":"外卖包装：品牌、体验、颜值如何共存？","sub_title":"效率与颜值的兼顾。","publish_shijian":"2018-05-17 00:00:00","img":"https://img.shaoziketang.com/img20180517104319_100.h.png","rank":"2"},{"id":"1619","title":"当外卖市场规模达2000亿时，云海肴、乡村基劝你冷静一些\u2026\u2026","sub_title":"有些话不得不说\n","publish_shijian":"2018-05-03 00:00:00","img":"https://img.shaoziketang.com/img20180503181443_100.h.jpg","rank":"2"}]
     * newsCount : 141
     * eventList : [{"id":"1372","img":"https://img.shaoziketang.com/img20180511142343_100.h.png","type":"1","title":"外卖盈利系统落地第11期 北京","address":"北京","publish_shijian":"2018-06-07 17:52:05","edit_user":"寇俊奇","rank":"2","class":"3"},{"id":"1383","img":"https://img.shaoziketang.com/img20180601111309_100.h.jpg","type":"1","title":"外卖盈利系统落地 第12期  广州","address":"广州","publish_shijian":"2018-06-06 10:30:42","edit_user":"寇俊奇","rank":"2","class":"3"},{"id":"1356","img":"https://img.shaoziketang.com/img20180511175103_100.h.png","type":"1","title":"外卖盈利系统落地第八期 长沙 ","address":"长沙","publish_shijian":"2018-04-02 18:08:34","edit_user":"寇俊奇","rank":"2","class":"3"}]
     * eventCount : 7
     */

    private String courseCount;
    private String newsCount;
    private String eventCount;
    private String status;
    private String msg;
    private List<CourseListBean> courseList;
    private List<NewsListBean> newsList;
    private List<EventListBean> eventList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public String getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(String newsCount) {
        this.newsCount = newsCount;
    }

    public String getEventCount() {
        return eventCount;
    }

    public void setEventCount(String eventCount) {
        this.eventCount = eventCount;
    }

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public List<NewsListBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsListBean> newsList) {
        this.newsList = newsList;
    }

    public List<EventListBean> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventListBean> eventList) {
        this.eventList = eventList;
    }

    public static class CourseListBean {
        /**
         * id : 1305
         * type : 3
         * img : https://img.shaoziketang.com/img20180614163903_100.h.jpg
         * title : 1年从0做到8000万，我是如何玩转外卖平台的？
         * learn : 1万+
         * teacher_name : 曾姝骞
         * sub_title : 开店前你应该做哪些准备？
         * good : 96
         * cate_id : -1
         * rank : 2
         * class : 3
         * cate : 0
         */

        private String id;
        private String type;
        private String img;
        private String title;
        private String learn;
        private String teacher_name;
        private String sub_title;
        private String good;
        private String cate_id;
        private String rank;
        @SerializedName("class")
        private String classX;
        private String sel_type;

        public String getSel_type() {
            return sel_type;
        }

        public void setSel_type(String sel_type) {
            this.sel_type = sel_type;
        }

        //        private String cate;

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

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

//        public String getCate() {
//            return cate;
//        }
//
//        public void setCate(String cate) {
//            this.cate = cate;
//        }
    }

    public static class NewsListBean {
        /**
         * id : 1662
         * title : 外卖店铺装修做到这4点，你的订单也蹭蹭涨...
         * sub_title : 这是一个颜值即正义的时代...
         * publish_shijian : 2018-06-24 00:00:00
         * img : https://img.shaoziketang.com/img20180624024719_100.h.jpg
         * rank : 2
         */

        private String id;
        private String title;
        private String sub_title;
        private String publish_shijian;
        private String img;
        private String rank;
        private String learn;
        private String type;
        private String sel_type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSel_type() {
            return sel_type;
        }

        public void setSel_type(String sel_type) {
            this.sel_type = sel_type;
        }

        public String getLearn() {
            return learn;
        }

        public void setLearn(String learn) {
            this.learn = learn;
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

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getPublish_shijian() {
            return publish_shijian;
        }

        public void setPublish_shijian(String publish_shijian) {
            this.publish_shijian = publish_shijian;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }

    public static class EventListBean {
        /**
         * id : 1372
         * img : https://img.shaoziketang.com/img20180511142343_100.h.png
         * type : 1
         * title : 外卖盈利系统落地第11期 北京
         * address : 北京
         * publish_shijian : 2018-06-07 17:52:05
         * edit_user : 寇俊奇
         * rank : 2
         * class : 3
         */

        private String id;
        private String img;
        private String type;
        private String sel_type;
        private String title;
        private String address;
        private String publish_shijian;
        private String edit_user;
        private String rank;
        private String start_shijian;

        public String getSel_type() {
            return sel_type;
        }

        public void setSel_type(String sel_type) {
            this.sel_type = sel_type;
        }

        public String getStart_shijian() {
            return start_shijian;
        }

        public void setStart_shijian(String start_shijian) {
            this.start_shijian = start_shijian;
        }

        @SerializedName("class")
        private String classX;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPublish_shijian() {
            return publish_shijian;
        }

        public void setPublish_shijian(String publish_shijian) {
            this.publish_shijian = publish_shijian;
        }

        public String getEdit_user() {
            return edit_user;
        }

        public void setEdit_user(String edit_user) {
            this.edit_user = edit_user;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }
    }
}
