package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;

/**
 * Created by wang on 2017/10/23.
 */

public class EventGetMoneyBean implements Serializable {


    /**
     * code : 200
     * msg : 查询成功
     * info : {"price":"299.00","user_name":"wh","mobile":"13965019845"}
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

    public static class InfoBean implements Serializable {
        /**
         * price : 299.00
         * user_name : wh
         * mobile : 13965019845
         */

        private Double price;
        private String user_name;
        private String mobile;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
