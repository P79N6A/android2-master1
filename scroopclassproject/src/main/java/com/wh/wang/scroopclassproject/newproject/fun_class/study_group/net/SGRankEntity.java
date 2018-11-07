package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/14.
 */

public class SGRankEntity {

    /**
     * code : 200
     * msg : 排行榜数据获取成功
     * info : {"other":[{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"1"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"1"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"3"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"2"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"3"}],"myself":{"user_avator":"","user_name":"","myranking":"1"}}
     */

    private String code;
    private String msg;
    private InfoBean info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * other : [{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"1"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"1"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"3"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"2"},{"user_name":"","user_avator":"","v_num":"0","v_time":"0","user_id":"3"}]
         * myself : {"user_avator":"","user_name":"","myranking":"1"}
         */

        private MyselfBean myself;
        private List<OtherBean> other;

        public MyselfBean getMyself() {
            return myself;
        }

        public void setMyself(MyselfBean myself) {
            this.myself = myself;
        }

        public List<OtherBean> getOther() {
            return other;
        }

        public void setOther(List<OtherBean> other) {
            this.other = other;
        }

        public static class MyselfBean {
            /**
             * user_avator :
             * user_name :
             * myranking : 1
             */

            private String user_avator;
            private String user_name;
            private String myranking;

            public String getUser_avator() {
                return user_avator;
            }

            public void setUser_avator(String user_avator) {
                this.user_avator = user_avator;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getMyranking() {
                return myranking;
            }

            public void setMyranking(String myranking) {
                this.myranking = myranking;
            }
        }

        public static class OtherBean {
            /**
             * user_name :
             * user_avator :
             * v_num : 0
             * v_time : 0
             * user_id : 1
             */

            private String user_name;
            private String user_avator;
            private String v_num;
            private String v_time;
            private String user_id;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_avator() {
                return user_avator;
            }

            public void setUser_avator(String user_avator) {
                this.user_avator = user_avator;
            }

            public String getV_num() {
                return v_num;
            }

            public void setV_num(String v_num) {
                this.v_num = v_num;
            }

            public String getV_time() {
                return v_time;
            }

            public void setV_time(String v_time) {
                this.v_time = v_time;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
