package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by teitsuyoshi on 2018/5/7.
 */

public class ShowFree7Entity {

    /**
     * code : 200
     * msg : 查询成功
     * info : {"is_display_vip":"1"}
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
         * is_display_vip : 1
         */

        private String is_display_vip;

        public String getIs_display_vip() {
            return is_display_vip;
        }

        public void setIs_display_vip(String is_display_vip) {
            this.is_display_vip = is_display_vip;
        }
    }
}
