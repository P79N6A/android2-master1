package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by chdh on 2018/1/22.
 */

public class LikeEntity {

    /**
     * code : 200
     * msg : 点赞结果
     * info : {"zhan":1}
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
         * zhan : 1
         */

        private String zhan;

        public String getZhan() {
            return zhan;
        }

        public void setZhan(String zhan) {
            this.zhan = zhan;
        }
    }
}
