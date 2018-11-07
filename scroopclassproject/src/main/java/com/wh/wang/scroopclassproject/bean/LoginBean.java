package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;

/**
 * Created by wang on 2017/8/17.
 */

public class LoginBean implements Serializable {
    /**
     * info : {"area":"beijing","avatar":"","brand":"love","is_blocked":"","is_vip":0,"job":"","mobile":"13965019845","nickname":"wh","password":"","position":"android","token":"","user_id":"27743"}
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

    public static class InfoBean {
        /**
         * area : beijing
         * avatar :
         * brand : love
         * is_blocked :
         * is_vip : 0
         * job :
         * mobile : 13965019845
         * nickname : wh
         * password :
         * position : android
         * token :
         * user_id : 27743
         */

        private String area;
        private String avatar;
        private String brand;
        private String is_blocked;
        private int is_vip;
        private String job;
        private String mobile;
        private String nickname;
        private String password;
        private String position;
        private String token;
        private String user_id;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getIs_blocked() {
            return is_blocked;
        }

        public void setIs_blocked(String is_blocked) {
            this.is_blocked = is_blocked;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "area='" + area + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", brand='" + brand + '\'' +
                    ", is_blocked='" + is_blocked + '\'' +
                    ", is_vip=" + is_vip +
                    ", job='" + job + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", password='" + password + '\'' +
                    ", position='" + position + '\'' +
                    ", token='" + token + '\'' +
                    ", user_id='" + user_id + '\'' +
                    '}';
        }
    }
}
