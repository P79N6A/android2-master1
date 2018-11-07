package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/11/22.
 */

public class CourseBean implements Serializable {


    /**
     * info : {"allType":[{"id":"29","name":"推荐"},{"id":"8","name":"营运力"},{"id":"9","name":"营销力"},{"id":"10","name":"支持力"},{"id":"30","name":"战略力"},{"id":"31","name":"人才力"}],"courseList":[{"id":"281","img":"http://sz.wimg.cc/img20171009174912_100.h.png","new_price":"299.00","teacher_id":"208","teacher_name":"段燕青","title":"能引爆市场的节日营销该怎么做"},{"id":"280","img":"http://sz.wimg.cc/img20171017002515_100.h.jpg","new_price":"0.00","teacher_id":"108","teacher_name":"吴楠","title":"免费公开课 | 外卖选址，一流商圈、三流租金如何找？"},{"id":"279","img":"http://sz.wimg.cc/img20171023182324_100.h.png","new_price":"0.00","teacher_id":"92","teacher_name":"陈实","title":"免费公开课 | 加盟热潮来袭，入局前你需要先了解这几件事"},{"id":"278","img":"http://sz.wimg.cc/img20171031172853_100.h.png","new_price":"0.00","teacher_id":"66","teacher_name":"梁丽","title":"免费公开课 | 利润增长的突破口\u2014\u2014如何玩转会员积分？"},{"id":"277","img":"http://sz.wimg.cc/img20171107155252_100.h.png","new_price":"0.00","teacher_id":"210","teacher_name":"王鹿鹿","title":"免费公开课 | 4个步骤搞定餐饮品牌升级"},{"id":"276","img":"http://sz.wimg.cc/img20171108173301_100.h.png","new_price":"299.00","teacher_id":"108","teacher_name":"吴楠","title":"勺子实训营 | 外卖新手开店指南"},{"id":"275","img":"http://sz.wimg.cc/img20171113165102_100.h.png","new_price":"0.00","teacher_id":"212","teacher_name":"李火苗","title":"免费公开课 | 从8000万到30亿，我是如何打造周黑鸭品牌的？"}],"firstHot":1,"laterNum":-1,"scroll":[{"courseId":"1299","fenlei_id":"29","img":"http://sz.wimg.cc/img20171017002515_100.h.jpg","title":"免费公开课 | 外卖选址，一流商圈、三流租金如何找？"},{"courseId":"1298","fenlei_id":"29","img":"http://sz.wimg.cc/img20171009174912_100.h.png","title":"能引爆市场的节日营销该怎么做"},{"courseId":"1304","fenlei_id":"29","img":"http://sz.wimg.cc/img20171113165102_100.h.png","title":"免费公开课 | 从8000万到30亿，我是如何打造周黑鸭品牌的？"}]}
     * status : 1
     */

    private InfoBean info;
    private int status;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class InfoBean implements Serializable {
        /**
         * allType : [{"id":"29","name":"推荐"},{"id":"8","name":"营运力"},{"id":"9","name":"营销力"},{"id":"10","name":"支持力"},{"id":"30","name":"战略力"},{"id":"31","name":"人才力"}]
         * courseList : [{"id":"281","img":"http://sz.wimg.cc/img20171009174912_100.h.png","new_price":"299.00","teacher_id":"208","teacher_name":"段燕青","title":"能引爆市场的节日营销该怎么做"},{"id":"280","img":"http://sz.wimg.cc/img20171017002515_100.h.jpg","new_price":"0.00","teacher_id":"108","teacher_name":"吴楠","title":"免费公开课 | 外卖选址，一流商圈、三流租金如何找？"},{"id":"279","img":"http://sz.wimg.cc/img20171023182324_100.h.png","new_price":"0.00","teacher_id":"92","teacher_name":"陈实","title":"免费公开课 | 加盟热潮来袭，入局前你需要先了解这几件事"},{"id":"278","img":"http://sz.wimg.cc/img20171031172853_100.h.png","new_price":"0.00","teacher_id":"66","teacher_name":"梁丽","title":"免费公开课 | 利润增长的突破口\u2014\u2014如何玩转会员积分？"},{"id":"277","img":"http://sz.wimg.cc/img20171107155252_100.h.png","new_price":"0.00","teacher_id":"210","teacher_name":"王鹿鹿","title":"免费公开课 | 4个步骤搞定餐饮品牌升级"},{"id":"276","img":"http://sz.wimg.cc/img20171108173301_100.h.png","new_price":"299.00","teacher_id":"108","teacher_name":"吴楠","title":"勺子实训营 | 外卖新手开店指南"},{"id":"275","img":"http://sz.wimg.cc/img20171113165102_100.h.png","new_price":"0.00","teacher_id":"212","teacher_name":"李火苗","title":"免费公开课 | 从8000万到30亿，我是如何打造周黑鸭品牌的？"}]
         * firstHot : 1
         * laterNum : -1
         * scroll : [{"courseId":"1299","fenlei_id":"29","img":"http://sz.wimg.cc/img20171017002515_100.h.jpg","title":"免费公开课 | 外卖选址，一流商圈、三流租金如何找？"},{"courseId":"1298","fenlei_id":"29","img":"http://sz.wimg.cc/img20171009174912_100.h.png","title":"能引爆市场的节日营销该怎么做"},{"courseId":"1304","fenlei_id":"29","img":"http://sz.wimg.cc/img20171113165102_100.h.png","title":"免费公开课 | 从8000万到30亿，我是如何打造周黑鸭品牌的？"}]
         */

        private int firstHot;
        private int laterNum;
        private List<AllTypeBean> allType;
        private List<CourseListBean> courseList;
        private List<ScrollBean> scroll;

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

        public List<ScrollBean> getScroll() {
            return scroll;
        }

        public void setScroll(List<ScrollBean> scroll) {
            this.scroll = scroll;
        }

        public static class AllTypeBean implements Serializable {
            /**
             * id : 29
             * name : 推荐
             */

            private String id;
            private String name;
            private String logo;

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

        public static class CourseListBean implements Serializable {
            /**
             * id : 281
             * img : http://sz.wimg.cc/img20171009174912_100.h.png
             * new_price : 299.00
             * teacher_id : 208
             * teacher_name : 段燕青
             * title : 能引爆市场的节日营销该怎么做
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

        public static class ScrollBean implements Serializable {
            /**
             * courseId : 1299
             * fenlei_id : 29
             * img : http://sz.wimg.cc/img20171017002515_100.h.jpg
             * title : 免费公开课 | 外卖选址，一流商圈、三流租金如何找？
             */

            private String courseId;
            private String fenlei_id;
            private String img;
            private String title;
            private String type;

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

            public String getFenlei_id() {
                return fenlei_id;
            }

            public void setFenlei_id(String fenlei_id) {
                this.fenlei_id = fenlei_id;
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
        }
    }
}
