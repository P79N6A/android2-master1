package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/22.
 */

public class MemberEntity {


    /**
     * code : 200
     * msg : 查询成功
     * info : {"admin_list":[{"id":"38256","name":"图样","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","ifadmin":"1","position":"吧"},{"id":"42980","name":"","avator":"http://sz.wimg.cc/p/","ifadmin":"1","position":"qqq"}],"v_list":[{"id":"39347","name":"","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","ifadmin":"0","position":"哈哈"},{"id":"40448","name":"明年","avator":"https://www.shaoziketang.com/application/views/pic/img20171222093534_100.jpg","ifadmin":"0","position":"名额"}],"number":3}
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
         * admin_list : [{"id":"38256","name":"图样","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","ifadmin":"1","position":"吧"},{"id":"42980","name":"","avator":"http://sz.wimg.cc/p/","ifadmin":"1","position":"qqq"}]
         * v_list : [{"id":"39347","name":"","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","ifadmin":"0","position":"哈哈"},{"id":"40448","name":"明年","avator":"https://www.shaoziketang.com/application/views/pic/img20171222093534_100.jpg","ifadmin":"0","position":"名额"}]
         * number : 3
         */

        private String number;
        private String company_title;
        private List<AdminListBean> admin_list;
        private List<VListBean> v_list;

        public String getCompany_title() {
            return company_title;
        }

        public void setCompany_title(String company_title) {
            this.company_title = company_title;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public List<AdminListBean> getAdmin_list() {
            return admin_list;
        }

        public void setAdmin_list(List<AdminListBean> admin_list) {
            this.admin_list = admin_list;
        }

        public List<VListBean> getV_list() {
            return v_list;
        }

        public void setV_list(List<VListBean> v_list) {
            this.v_list = v_list;
        }

        public static class AdminListBean {
            /**
             * id : 38256
             * name : 图样
             * avator : https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg
             * ifadmin : 1
             * position : 吧
             */

            private String id;
            private String name;
            private String avator;
            private String ifadmin;
            private String position;

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

            public String getAvator() {
                return avator;
            }

            public void setAvator(String avator) {
                this.avator = avator;
            }

            public String getIfadmin() {
                return ifadmin;
            }

            public void setIfadmin(String ifadmin) {
                this.ifadmin = ifadmin;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }

        public static class VListBean {
            /**
             * id : 39347
             * name :
             * avator : https://www.shaoziketang.com/application/views/head/20180117134848.png
             * ifadmin : 0
             * position : 哈哈
             */

            private String id;
            private String name;
            private String avator;
            private String ifadmin;
            private String position;

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

            public String getAvator() {
                return avator;
            }

            public void setAvator(String avator) {
                this.avator = avator;
            }

            public String getIfadmin() {
                return ifadmin;
            }

            public void setIfadmin(String ifadmin) {
                this.ifadmin = ifadmin;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }
    }
}
