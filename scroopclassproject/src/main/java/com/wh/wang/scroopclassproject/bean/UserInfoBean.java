package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/12/4.
 */

public class UserInfoBean {


    /**
     * code : 200
     * info : {"area":"","avator":"http://www.shaoziketang.com/public/images/8e.png","brand":"","email":"","id":"38252","ios_integral":"0","mensum":"0","mess_num":0,"mobile":"13965019845","nickname":"wang","openid":"","position":"","rand_str":"5a25275c74059","senior":"0"}
     * msg : 查询成功
     */

    private int code;
    private InfoBean info;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class InfoBean {
        /**
         * area :
         * avator : http://www.shaoziketang.com/public/images/8e.png
         * brand :
         * email :
         * id : 38252
         * ios_integral : 0
         * mensum : 0
         * mess_num : 0
         * mobile : 13965019845
         * nickname : wang
         * openid :
         * position :
         * rand_str : 5a25275c74059
         * senior : 0
         */

        private String area;
        private String avator;
        private String brand;
        private String email;
        private String id;
        private String ios_integral;
        private String mensum;
        private int mess_num;
        private String mobile;
        private String nickname;
        private String openid;
        private String position;
        private String rand_str;
        private String senior; //0代表男 1代表女

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIos_integral() {
            return ios_integral;
        }

        public void setIos_integral(String ios_integral) {
            this.ios_integral = ios_integral;
        }

        public String getMensum() {
            return mensum;
        }

        public void setMensum(String mensum) {
            this.mensum = mensum;
        }

        public int getMess_num() {
            return mess_num;
        }

        public void setMess_num(int mess_num) {
            this.mess_num = mess_num;
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

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRand_str() {
            return rand_str;
        }

        public void setRand_str(String rand_str) {
            this.rand_str = rand_str;
        }

        public String getSenior() {
            return senior;
        }

        public void setSenior(String senior) {
            this.senior = senior;
        }
    }
}
