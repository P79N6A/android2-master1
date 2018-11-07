package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by chdh on 2018/1/24.
 */

public class RemindEntity {

    /**
     * code : 200
     * msg : 查询成功
     * info : {"tixing":1}
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
         * tixing : 1
         */

        private int tixing;

        public int getTixing() {
            return tixing;
        }

        public void setTixing(int tixing) {
            this.tixing = tixing;
        }
    }
}
