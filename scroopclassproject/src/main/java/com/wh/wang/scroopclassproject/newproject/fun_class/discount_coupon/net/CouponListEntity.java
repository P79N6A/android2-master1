package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/25.
 */

public class CouponListEntity {


    /**
     * code : 200
     * msg : 详情页数据获取成功
     * info : [{"name":"dasd","end_time":"2018-09-30 00:00:00","mark":"规则1；规则2；规则3；规则3；","price":"10.00"}]
     */

    private String code;
    private String msg;
    private List<CouponListBean> info;

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

    public List<CouponListBean> getInfo() {
        return info;
    }

    public void setInfo(List<CouponListBean> info) {
        this.info = info;
    }

//    public static class InfoBean {
//        /**
//         * name : dasd
//         * end_time : 2018-09-30 00:00:00
//         * mark : 规则1；规则2；规则3；规则3；
//         * price : 10.00
//         */
//
//        private String name;
//        private String end_time;
//        private String mark;
//        private String price;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getEnd_time() {
//            return end_time;
//        }
//
//        public void setEnd_time(String end_time) {
//            this.end_time = end_time;
//        }
//
//        public String getMark() {
//            return mark;
//        }
//
//        public void setMark(String mark) {
//            this.mark = mark;
//        }
//
//        public String getPrice() {
//            return price;
//        }
//
//        public void setPrice(String price) {
//            this.price = price;
//        }
//    }
}
