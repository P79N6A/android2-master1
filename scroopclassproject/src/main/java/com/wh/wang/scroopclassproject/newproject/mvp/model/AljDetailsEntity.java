package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by teitsuyoshi on 2018/5/2.
 */

public class AljDetailsEntity {

    /**
     * code : 200
     * msg : 案例集详情页查询成功
     * info : {"list":{"content":""},"user_status":"0"}
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
         * list : {"content":""}
         * user_status : 0
         */

        private ListBean list;
        private String user_status;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public static class ListBean {
            /**
             * content :
             */

            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
