package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class FreeCourseEntity {

    /**
     * status : 1
     * info : {"courseList":[{"new_price":"0.00","id":"1305","img":"http://sz.wimg.cc/img20171121164102_100.h.png","title":"免费公开课 | 1年从0做到8000万，我是如何玩转外卖平台的？","learn":"3456","name":"曾姝骞","duan":"廖记棒棒鸡外卖事业部总监"},{"new_price":"0.00","id":"1304","img":"http://sz.wimg.cc/img20171113165102_100.h.png","title":"免费公开课 | 从8000万到30亿，我是如何打造周黑鸭品牌的？","learn":"7087","name":"李火苗","duan":"上海赤火品牌营销机构 创始人兼CEO"},{"new_price":"0.00","id":"1302","img":"http://sz.wimg.cc/img20171107155252_100.h.png","title":"免费公开课 | 4个步骤搞定餐饮品牌升级","learn":"4176","name":"王鹿鹿","duan":"餐饮品牌策略师"},{"new_price":"0.00","id":"1301","img":"http://sz.wimg.cc/img20171031172853_100.h.png","title":"免费公开课 | 利润增长的突破口\u2014\u2014如何玩转会员积分？","learn":"5280","name":"梁丽","duan":"北平三兄弟涮肉联合创始人CMO"},{"new_price":"0.00","id":"1300","img":"http://sz.wimg.cc/img20171023182324_100.h.png","title":"免费公开课 | 加盟热潮来袭，入局前你需要先了解这几件事","learn":"5439","name":"陈实","duan":"中国首部特许经营行业标准制定者"},{"new_price":"0.00","id":"1299","img":"http://sz.wimg.cc/img20171017002515_100.h.jpg","title":"免费公开课 | 外卖选址，一流商圈、三流租金如何找？","learn":"5383","name":"吴楠","duan":"青渝蓝之麻辣香锅联合创始人"},{"new_price":"0.00","id":"1291","img":"http://sz.wimg.cc/img20170925155005_100.h.png","title":"勺子公开课|连锁餐饮业的商业模式创新","learn":"9625","name":null,"duan":null},{"new_price":"0.00","id":"1278","img":"http://sz.wimg.cc/img20170918111903_100.h.png","title":"1.0 - 4.0，菜单迭代升级该这么做","learn":"6258","name":"陈莞","duan":"野肆联合创始人"}]}
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
        private List<CourseListBean> courseList;

        public List<CourseListBean> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseListBean> courseList) {
            this.courseList = courseList;
        }

        public static class CourseListBean {
            /**
             * new_price : 0.00
             * id : 1305
             * img : http://sz.wimg.cc/img20171121164102_100.h.png
             * title : 免费公开课 | 1年从0做到8000万，我是如何玩转外卖平台的？
             * learn : 3456
             * name : 曾姝骞
             * duan : 廖记棒棒鸡外卖事业部总监
             */

            private String new_price;
            private String id;
            private String img;
            private String title;
            private String learn;
            private String name;
            private String duan;
            private String type;
            private String ifnew;

            public String getIfnew() {
                return ifnew;
            }

            public void setIfnew(String ifnew) {
                this.ifnew = ifnew;
            }

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

            public String getDuan() {
                return duan;
            }

            public void setDuan(String duan) {
                this.duan = duan;
            }
        }
    }
}
