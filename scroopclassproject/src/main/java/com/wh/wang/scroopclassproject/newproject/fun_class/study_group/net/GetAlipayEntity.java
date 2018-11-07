package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

/**
 * Created by teitsuyoshi on 2018/8/29.
 */

public class GetAlipayEntity {


    /**
     * code : 200
     * msg : 获取支付宝信息成功
     * info : {"user_id":"39347","user_name":"夏目","alipay_accout":"18410115623","alipay_name":"丁豪"}
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
        /**
         * user_id : 39347
         * user_name : 夏目
         * alipay_accout : 18410115623
         * alipay_name : 丁豪
         */

        private String user_id;
        private String user_name;
        private String alipay_accout;
        private String alipay_name;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAlipay_accout() {
            return alipay_accout;
        }

        public void setAlipay_accout(String alipay_accout) {
            this.alipay_accout = alipay_accout;
        }

        public String getAlipay_name() {
            return alipay_name;
        }

        public void setAlipay_name(String alipay_name) {
            this.alipay_name = alipay_name;
        }
    }
}
