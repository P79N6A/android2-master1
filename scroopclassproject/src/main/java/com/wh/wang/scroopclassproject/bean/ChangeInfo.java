package com.wh.wang.scroopclassproject.bean;

import java.util.List;

/**
 * Created by wang on 2017/9/9.
 */

public class ChangeInfo {


    /**
     * code : 200
     * info : [{"area":"北京","avator":"http://sz.wimg.cc/p/","brand":"京","grade":"1","id":"27075","nickname":"han","position":"Android"}]
     * msg : 信息更新成功
     */

    private int code;
    private String msg;
    private List<InfoBean> info;

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

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * area : 北京
         * avator : http://sz.wimg.cc/p/
         * brand : 京
         * grade : 1
         * id : 27075
         * nickname : han
         * position : Android
         */

        private String area;
        private String avator;
        private String brand;
        private String grade;
        private String id;
        private String nickname;
        private String position;
        private String sex;
        private String mensum;
        private String email;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMensum() {
            return mensum;
        }

        public void setMensum(String mensum) {
            this.mensum = mensum;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
