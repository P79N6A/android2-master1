package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MoreOrAllCourseEntity {

    /**
     * status : 1
     * info : {"courseList":[{"id":"35","title":"20人管理1000人!丰茂烤串的人力资源管理法则","img":"http://sz.wimg.cc/p/1/k.png","teacher_id":"147","teacher_name":"王东生","new_price":"0.00"},{"id":"34","title":"从1家到83家门店，东方饺子王22年品牌发展之道","img":"http://sz.wimg.cc/p/1/i.png","teacher_id":"146","teacher_name":"马宏波","new_price":"0.00"},{"id":"33","title":"木屋烧烤营运秘笈!创始人这样hold住55家门店管理","img":"http://sz.wimg.cc/p/1/b.png","teacher_id":"145","teacher_name":"隋政军","new_price":"0.00"},{"id":"32","title":"创单日33.8次翻台纪录，遇见小面创业史深度剖析","img":"http://sz.wimg.cc/p/1/9.png","teacher_id":"144","teacher_name":"宋奇","new_price":"0.00"},{"id":"29","title":"从0到1，一个价值千万餐饮品牌的崛起过程","img":"http://sz.wimg.cc/p/1/2.png","teacher_id":"141","teacher_name":"刘晴","new_price":"0.00"}]}
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
             * id : 35
             * title : 20人管理1000人!丰茂烤串的人力资源管理法则
             * img : http://sz.wimg.cc/p/1/k.png
             * teacher_id : 147
             * teacher_name : 王东生
             * new_price : 0.00
             */

            private String id;
            private String title;
            private String img;
            private String teacher_id;
            private String teacher_name;
            private String new_price;
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
