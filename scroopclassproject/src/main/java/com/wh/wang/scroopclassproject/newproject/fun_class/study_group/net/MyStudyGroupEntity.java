package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/14.
 */

public class MyStudyGroupEntity {


    /**
     * myclass : [{"id":"20","status":"0","title":"餐厅利润管理的关键点","img":"https://img.shaoziketang.com/img20180709120348_100.h.jpg","cate_sum":"20","relative_video":"1043","rank":1},{"id":"21","status":"0","title":"加盟的全生命周期管理","img":"https://img.shaoziketang.com/img20180709120552_100.h.jpg","cate_sum":"8","relative_video":"946","rank":1}]
     * myfinishclass : []
     * status : 1
     * msg : 返回数据成功
     */

    private String status;
    private String msg;
    private List<MyclassBean> myclass;
    private List<MyclassBean> myfinishclass;

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

    public List<MyclassBean> getMyclass() {
        return myclass;
    }

    public void setMyclass(List<MyclassBean> myclass) {
        this.myclass = myclass;
    }

    public List<MyclassBean> getMyfinishclass() {
        return myfinishclass;
    }

    public void setMyfinishclass(List<MyclassBean> myfinishclass) {
        this.myfinishclass = myfinishclass;
    }

    public static class MyclassBean {
        /**
         * id : 20
         * status : 0
         * title : 餐厅利润管理的关键点
         * img : https://img.shaoziketang.com/img20180709120348_100.h.jpg
         * cate_sum : 20
         * relative_video : 1043
         * rank : 1
         */

        private String id;
        private String status;
        private String title;
        private String img;
        private String cate_sum;
        private String relative_video;
        private String dayrank;
        private String isfinish;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDayrank() {
            return dayrank;
        }

        public void setDayrank(String dayrank) {
            this.dayrank = dayrank;
        }

        public String getIsfinish() {
            return isfinish;
        }

        public void setIsfinish(String isfinish) {
            this.isfinish = isfinish;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getCate_sum() {
            return cate_sum;
        }

        public void setCate_sum(String cate_sum) {
            this.cate_sum = cate_sum;
        }

        public String getRelative_video() {
            return relative_video;
        }

        public void setRelative_video(String relative_video) {
            this.relative_video = relative_video;
        }

        public String getRank() {
            return dayrank;
        }

        public void setRank(String dayrank) {
            this.dayrank = dayrank;
        }
    }
}
