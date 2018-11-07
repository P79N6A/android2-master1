package com.wh.wang.scroopclassproject.bean;

import java.util.List;

/**
 * Created by wang on 2017/10/16.
 */

public class MsgNumsBean {

    /**
     * code : 200
     * msg : 查询余额成功
     * info : [{"id":"17334","ios_integral":"750","mess_num":"1"}]
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
         * id : 17334
         * ios_integral : 750
         * mess_num : 1
         */

        private String id;
        private String ios_integral;
        private String mess_num;

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

        public String getMess_num() {
            return mess_num;
        }

        public void setMess_num(String mess_num) {
            this.mess_num = mess_num;
        }
    }
}
