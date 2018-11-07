package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by chdh on 2018/1/22.
 */

public class ResidueInviteEntity {

    /**
     * code : 200
     * msg : 查询成功
     * info : {"sub_num":"31","count_num":"32"}
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
         * sub_num : 31
         * count_num : 32
         */

        private String sub_num;
        private String count_num;

        public String getSub_num() {
            return sub_num;
        }

        public void setSub_num(String sub_num) {
            this.sub_num = sub_num;
        }

        public String getCount_num() {
            return count_num;
        }

        public void setCount_num(String count_num) {
            this.count_num = count_num;
        }
    }
}
