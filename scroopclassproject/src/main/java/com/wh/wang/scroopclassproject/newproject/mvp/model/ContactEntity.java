package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chdh on 2018/1/31.
 */

public class ContactEntity implements Serializable{

    /**
     * status : 1
     * info : [{"id":"1","user_id":"34478","name":"zhangsan","phone":"17600736503","crate_time":"2018-01-30 15:51:09"}]
     * error :
     */

    private int status;
    private int statusCode;
    private String error;
    private List<InfoBean> info;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable{
        /**
         * id : 1
         * user_id : 34478
         * name : zhangsan
         * phone : 17600736503
         * crate_time : 2018-01-30 15:51:09
         */
        private String avatar;
        private String position;
        private String id;
        private String user_id;
        private String name;
        private String phone;
        private String crate_time;
        private String reason;
        private boolean joinAccess;

        //选中状态
        private boolean isCheck;
        //选中无法操作
        private boolean isOperation;
        //有价格代表可以报名
        private String price;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public boolean isJoinAccess() {
            return joinAccess;
        }

        public void setJoinAccess(boolean joinAccess) {
            this.joinAccess = joinAccess;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isOperation() {
            return isOperation;
        }

        public void setOperation(boolean operation) {
            isOperation = operation;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getCrate_time() {
            return crate_time;
        }

        public void setCrate_time(String crate_time) {
            this.crate_time = crate_time;
        }
    }
}
