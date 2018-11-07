package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by Administrator on 2018/1/3.
 */

public class ScanEntity {

    /**
     * status : 1
     * info : {"name":"1","tel":"123","title":"123"}
     */

    private int status;
    private InfoBean info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * name : 1
         * tel : 123
         * title : 123
         */

        private String name;
        private String tel;
        private String title;
        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
