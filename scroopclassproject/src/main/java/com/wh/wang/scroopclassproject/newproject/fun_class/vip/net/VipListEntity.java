package com.wh.wang.scroopclassproject.newproject.fun_class.vip.net;

/**
 * Created by teitsuyoshi on 2018/7/30.
 */

public class VipListEntity {


    /**
     * info : {"action":{"status":"0","member_end":""},"know":{"status":"1","c_end_time":"2018-07-31"}}
     * msg : 查询成功
     * code : 200
     */

    private InfoBean info;
    private String msg;
    private String code;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class InfoBean {
        /**
         * action : {"status":"0","member_end":""}
         * know : {"status":"1","c_end_time":"2018-07-31"}
         */

        private ActionBean action;
        private KnowBean know;

        public ActionBean getAction() {
            return action;
        }

        public void setAction(ActionBean action) {
            this.action = action;
        }

        public KnowBean getKnow() {
            return know;
        }

        public void setKnow(KnowBean know) {
            this.know = know;
        }

        public static class ActionBean {
            /**
             * status : 0
             * member_end :
             */

            private String status;
            private String member_end;
            private String price;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMember_end() {
                return member_end;
            }

            public void setMember_end(String member_end) {
                this.member_end = member_end;
            }
        }

        public static class KnowBean {
            /**
             * status : 1
             * c_end_time : 2018-07-31
             */

            private String status;
            private String c_end_time;
            private String price;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getC_end_time() {
                return c_end_time;
            }

            public void setC_end_time(String c_end_time) {
                this.c_end_time = c_end_time;
            }
        }
    }
}
