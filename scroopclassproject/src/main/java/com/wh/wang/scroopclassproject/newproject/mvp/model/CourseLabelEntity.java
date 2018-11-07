package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class CourseLabelEntity {

    /**
     * status : 1
     * info : {"firstHot":1,"scroll":[{"fenlei_id":"29","title":"外卖新手开店指南","type":"0","courseId":"1303","img":"https://img.shaoziketang.com/img_small20171127173319_100.h.jpg"}],"allType":[{"id":"29","name":"推荐","logo":"https://www.shaoziketang.com/public/images/tuijian.png"},{"id":"8","name":"营运力","logo":""},{"id":"9","name":"营销力","logo":""},{"id":"10","name":"支持力","logo":""},{"id":"30","name":"战略力","logo":""},{"id":"31","name":"人才力","logo":""}],"courseList":[{"id":"1315","type":"0","title":"你的餐厅为什么招不到人？","img":"https://img.shaoziketang.com/img_app20171206121237_100.l.jpg","teacher_id":"180","teacher_name":"陈斯琦","new_price":"29.90"},{"id":"1314","type":"0","title":"餐饮品牌如何申请商标","img":"https://img.shaoziketang.com/img_app20171206121220_100.l.jpg","teacher_id":"88","teacher_name":"吴敬清","new_price":"29.90"},{"id":"1312","type":"0","title":"菜品的中心价格带定价法","img":"https://img.shaoziketang.com/img_app20171206121111_100.l.jpg","teacher_id":"56","teacher_name":"张祖弢","new_price":"29.90"},{"id":"1309","type":"3","title":"如何用最少的钱让排名进入外卖平台首页？","img":"https://img.shaoziketang.com/img_app20171206120816_100.l.jpg","teacher_id":"216","teacher_name":"李知洋","new_price":"0.00"},{"id":"1304","type":"3","title":"从8000万到30亿，我是如何打造周黑鸭品牌的？","img":"https://img.shaoziketang.com/img_app20171205142902_100.l.jpg","teacher_id":"212","teacher_name":"李火苗","new_price":"0.00"},{"id":"1303","type":"0","title":"外卖新手开店指南","img":"https://img.shaoziketang.com/img_app20171205142921_100.l.jpg","teacher_id":"108","teacher_name":"吴楠","new_price":"299.00"},{"id":"1302","type":"3","title":"4个步骤搞定餐饮品牌升级","img":"https://img.shaoziketang.com/img_app20171205143228_100.l.jpg","teacher_id":"210","teacher_name":"王鹿鹿","new_price":"0.00"},{"id":"1301","type":"3","title":"利润增长的突破口\u2014\u2014如何玩转会员积分？","img":"https://img.shaoziketang.com/img_app20171205155427_100.l.jpg","teacher_id":"66","teacher_name":"梁丽","new_price":"0.00"}],"laterNum":3}
     */

    private int status;
    private InfoBean info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * firstHot : 1
         * scroll : [{"fenlei_id":"29","title":"外卖新手开店指南","type":"0","courseId":"1303","img":"https://img.shaoziketang.com/img_small20171127173319_100.h.jpg"}]
         * allType : [{"id":"29","name":"推荐","logo":"https://www.shaoziketang.com/public/images/tuijian.png"},{"id":"8","name":"营运力","logo":""},{"id":"9","name":"营销力","logo":""},{"id":"10","name":"支持力","logo":""},{"id":"30","name":"战略力","logo":""},{"id":"31","name":"人才力","logo":""}]
         * courseList : [{"id":"1315","type":"0","title":"你的餐厅为什么招不到人？","img":"https://img.shaoziketang.com/img_app20171206121237_100.l.jpg","teacher_id":"180","teacher_name":"陈斯琦","new_price":"29.90"},{"id":"1314","type":"0","title":"餐饮品牌如何申请商标","img":"https://img.shaoziketang.com/img_app20171206121220_100.l.jpg","teacher_id":"88","teacher_name":"吴敬清","new_price":"29.90"},{"id":"1312","type":"0","title":"菜品的中心价格带定价法","img":"https://img.shaoziketang.com/img_app20171206121111_100.l.jpg","teacher_id":"56","teacher_name":"张祖弢","new_price":"29.90"},{"id":"1309","type":"3","title":"如何用最少的钱让排名进入外卖平台首页？","img":"https://img.shaoziketang.com/img_app20171206120816_100.l.jpg","teacher_id":"216","teacher_name":"李知洋","new_price":"0.00"},{"id":"1304","type":"3","title":"从8000万到30亿，我是如何打造周黑鸭品牌的？","img":"https://img.shaoziketang.com/img_app20171205142902_100.l.jpg","teacher_id":"212","teacher_name":"李火苗","new_price":"0.00"},{"id":"1303","type":"0","title":"外卖新手开店指南","img":"https://img.shaoziketang.com/img_app20171205142921_100.l.jpg","teacher_id":"108","teacher_name":"吴楠","new_price":"299.00"},{"id":"1302","type":"3","title":"4个步骤搞定餐饮品牌升级","img":"https://img.shaoziketang.com/img_app20171205143228_100.l.jpg","teacher_id":"210","teacher_name":"王鹿鹿","new_price":"0.00"},{"id":"1301","type":"3","title":"利润增长的突破口\u2014\u2014如何玩转会员积分？","img":"https://img.shaoziketang.com/img_app20171205155427_100.l.jpg","teacher_id":"66","teacher_name":"梁丽","new_price":"0.00"}]
         * laterNum : 3
         */

        private int firstHot;
        private int laterNum;
        private List<ScrollBean> scroll;
        private List<AllTypeBean> allType;
        private List<CourseListBean> courseList;

        public int getFirstHot() {
            return firstHot;
        }

        public void setFirstHot(int firstHot) {
            this.firstHot = firstHot;
        }

        public int getLaterNum() {
            return laterNum;
        }

        public void setLaterNum(int laterNum) {
            this.laterNum = laterNum;
        }

        public List<ScrollBean> getScroll() {
            return scroll;
        }

        public void setScroll(List<ScrollBean> scroll) {
            this.scroll = scroll;
        }

        public List<AllTypeBean> getAllType() {
            return allType;
        }

        public void setAllType(List<AllTypeBean> allType) {
            this.allType = allType;
        }

        public List<CourseListBean> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseListBean> courseList) {
            this.courseList = courseList;
        }

        public static class ScrollBean {
            /**
             * fenlei_id : 29
             * title : 外卖新手开店指南
             * type : 0
             * courseId : 1303
             * img : https://img.shaoziketang.com/img_small20171127173319_100.h.jpg
             */

            private String fenlei_id;
            private String title;
            private String type;
            private String courseId;
            private String img;
            private String ifnew;

            public String getIfnew() {
                return ifnew;
            }

            public void setIfnew(String ifnew) {
                this.ifnew = ifnew;
            }

            public String getFenlei_id() {
                return fenlei_id;
            }

            public void setFenlei_id(String fenlei_id) {
                this.fenlei_id = fenlei_id;
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

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class AllTypeBean {
            /**
             * id : 29
             * name : 推荐
             * logo : https://www.shaoziketang.com/public/images/tuijian.png
             */

            private String id;
            private String name;
            private String logo;
            private boolean isCheck;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }

        public static class CourseListBean {
            /**
             * id : 1315
             * type : 0
             * title : 你的餐厅为什么招不到人？
             * img : https://img.shaoziketang.com/img_app20171206121237_100.l.jpg
             * teacher_id : 180
             * teacher_name : 陈斯琦
             * new_price : 29.90
             */

            private String id;
            private String type;
            private String title;
            private String img;
            private String teacher_id;
            private String teacher_name;
            private String new_price;
            private String ifnew;

            public String getIfnew() {
                return ifnew;
            }

            public void setIfnew(String ifnew) {
                this.ifnew = ifnew;
            }

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

            public String getNew_price() {
                return new_price;
            }

            public void setNew_price(String new_price) {
                this.new_price = new_price;
            }
        }
    }
}
