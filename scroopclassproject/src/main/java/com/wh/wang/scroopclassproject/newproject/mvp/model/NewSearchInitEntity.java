package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class NewSearchInitEntity {

    /**
     * status : 1
     * info : {"searchSet":["123","456"],"commCourse":[{"id":"1318","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121418_100.h.jpg","title":"外卖新店开业活动策划","learn":"178","teacher_name":"曾姝骞","sub_title":"","good":"0"},{"id":"1317","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121341_100.h.jpg","title":"外卖店最高效的动线设计怎么做？","learn":"112","teacher_name":"吴楠","sub_title":"","good":"0"},{"id":"1316","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121307_100.h.jpg","title":"如何进行门店训练安排","learn":"152","teacher_name":"韩燕","sub_title":"","good":"0"},{"id":"1315","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121249_100.h.jpg","title":"你的餐厅为什么招不到人？","learn":"250","teacher_name":"陈斯琦","sub_title":"","good":"0"},{"id":"1314","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121211_100.h.jpg","title":"餐饮品牌如何申请商标","learn":"125","teacher_name":"吴敬清","sub_title":"","good":"0"}],"label":[{"id":"1","name":"营运力","parentid":"0"},{"id":"7","name":"营销力","parentid":"0"},{"id":"13","name":"支持力","parentid":"0"},{"id":"19","name":"战略力","parentid":"0"},{"id":"26","name":"人才力","parentid":"0"}]}
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
        private List<String> searchSet;
        private List<CommCourseBean> commCourse;
        private List<LabelBean> label;

        public List<String> getSearchSet() {
            return searchSet;
        }

        public void setSearchSet(List<String> searchSet) {
            this.searchSet = searchSet;
        }

        public List<CommCourseBean> getCommCourse() {
            return commCourse;
        }

        public void setCommCourse(List<CommCourseBean> commCourse) {
            this.commCourse = commCourse;
        }

        public List<LabelBean> getLabel() {
            return label;
        }

        public void setLabel(List<LabelBean> label) {
            this.label = label;
        }

        public static class CommCourseBean {
            /**
             * id : 1318
             * img : http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121418_100.h.jpg
             * title : 外卖新店开业活动策划
             * learn : 178
             * teacher_name : 曾姝骞
             * sub_title :
             * good : 0
             */

            private String id;
            private String img;
            private String title;
            private String learn;
            private String teacher_name;
            private String sub_title;
            private String good;
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
        }

        public static class LabelBean {
            /**
             * id : 1
             * name : 营运力
             * parentid : 0
             */

            private String id;
            private String name;
            private String parentid;

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

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }
        }
    }
}
