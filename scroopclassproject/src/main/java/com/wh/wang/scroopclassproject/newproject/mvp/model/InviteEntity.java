package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/4/17.
 */

public class InviteEntity {

    /**
     * status : 1
     * info : {"list":[{"name":"樊翠翠","nickname":"","phone":"159***480","member_start":"2018-03-22"},{"name":"霍澍","nickname":"Dean Huo","phone":"185***334","member_start":"2018-02-01"},{"name":"图样","nickname":"图样","phone":"182***355","member_start":"未开通会员"},{"name":"丁豪","nickname":"夏目","phone":"184***623","member_start":"2018-03-13"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : 樊翠翠
             * nickname :
             * phone : 159***480
             * member_start : 2018-03-22
             */

            private String name;
            private String nickname;
            private String phone;
            private String member_start;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMember_start() {
                return member_start;
            }

            public void setMember_start(String member_start) {
                this.member_start = member_start;
            }
        }
    }
}
