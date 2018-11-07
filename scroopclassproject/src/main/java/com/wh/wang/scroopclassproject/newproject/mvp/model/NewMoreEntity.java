package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class NewMoreEntity {

    /**
     * code : 200
     * msg : 更多课程获取成功
     * info : {"list":[{"learn":"261","id":"1555","type":"0","title":"如何有效设定门店目标","img":"https://img.shaoziketang.com/img_app20180627175504_100.h.png","teacher_name":"勺布斯","new_price":"29.90","cate_sum":"1","video_length":"20","duan":"你身边的餐饮专家","ifnew":"1"},{"learn":"1627","id":"1553","type":"3","title":"HACCP食品安全体系全应用","img":"https://img.shaoziketang.com/img_app20180613142028_100.h.jpg","teacher_name":"食品580","new_price":"0.00","cate_sum":"9","video_length":"89","duan":"福建省科技型企业","ifnew":"0"},{"learn":"1127","id":"1546","type":"0","title":"餐厅如何分析顾客评价","img":"https://img.shaoziketang.com/img_app20180607143237_100.h.jpg","teacher_name":"刘海丽","new_price":"29.90","cate_sum":"3","video_length":"50","duan":"捷荟大数据创始人兼CEO","ifnew":"0"},{"learn":"2656","id":"1523","type":"0","title":"企业如何进行战略定位","img":"https://img.shaoziketang.com/img_app20180515160522_100.h.jpg","teacher_name":"李广宇","new_price":"299.00","cate_sum":"14","video_length":"263","duan":"罗盘战略定位咨询公司\u2014创始人","ifnew":"0"},{"learn":"1094","id":"1524","type":"0","title":"营销战中的四种战略模型","img":"https://img.shaoziketang.com/img_app20180515163051_100.h.jpg","teacher_name":"李广宇","new_price":"299.00","cate_sum":"8","video_length":"146","duan":"罗盘战略定位咨询公司\u2014创始人","ifnew":"0"}]}
     */

    private int code;
    private String msg;
    private InfoBean info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * learn : 261
             * id : 1555
             * type : 0
             * title : 如何有效设定门店目标
             * img : https://img.shaoziketang.com/img_app20180627175504_100.h.png
             * teacher_name : 勺布斯
             * new_price : 29.90
             * cate_sum : 1
             * video_length : 20
             * duan : 你身边的餐饮专家
             * ifnew : 1
             */

            private String learn;
            private String id;
            private String type;
            private String sel_type;
            private String title;
            private String img;
            private String teacher_name;
            private String new_price;
            private String cate_sum;
            private String video_length;
            private String duan;
            private String ifnew;

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

            public String getCate_sum() {
                return cate_sum;
            }

            public void setCate_sum(String cate_sum) {
                this.cate_sum = cate_sum;
            }

            public String getVideo_length() {
                return video_length;
            }

            public void setVideo_length(String video_length) {
                this.video_length = video_length;
            }

            public String getDuan() {
                return duan;
            }

            public void setDuan(String duan) {
                this.duan = duan;
            }

            public String getIfnew() {
                return ifnew;
            }

            public void setIfnew(String ifnew) {
                this.ifnew = ifnew;
            }
        }
    }
}
