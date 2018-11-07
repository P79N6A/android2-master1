package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by chdh on 2018/1/31.
 */

public class OperationContactEntity {


    /**
     * status : 1
     * info : {"id":10,"name":"陈杰","phone":"15831605923"}
     * error :
     */

    private int status;
    private InfoBean info;
    private String error;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class InfoBean {
        /**
         * id : 10
         * name : 陈杰
         * phone : 15831605923
         */
        private String id;
        private String name;
        private String phone;
        private boolean joinAccess;
        private String reason;
        private String price;

        public boolean isJoinAccess() {
            return joinAccess;
        }

        public void setJoinAccess(boolean joinAccess) {
            this.joinAccess = joinAccess;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
