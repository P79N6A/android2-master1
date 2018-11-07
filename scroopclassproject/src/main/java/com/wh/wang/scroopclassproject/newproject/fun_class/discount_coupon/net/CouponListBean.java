package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net;

/**
 * Created by teitsuyoshi on 2018/7/25.
 */

public class CouponListBean {
    /**
     * name : dasd
     * end_time : 2018-09-30 00:00:00
     * mark : 规则1；规则2；规则3；规则3；
     * price : 10.00
     */

    private String name;
    private String end_time;
    private String mark;
    private String price;
    private String sill_price;
    private String id;
    private String code;
    private String isShow = "0";

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSill_price() {
        return sill_price;
    }

    public void setSill_price(String sill_price) {
        this.sill_price = sill_price;
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

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
