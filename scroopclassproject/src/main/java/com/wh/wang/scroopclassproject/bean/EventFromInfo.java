package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/9/13.
 */

public class EventFromInfo {

    /**
     * name :
     * tel :
     * address :
     * remark :
     * area : 北京
     * brand : 将军印
     * position : 总经理
     * email :
     */

    private String name;
    private String tel;
    private String address;
    private String remark;
    private String area;
    private String brand;
    private String position;
    private String email;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EventFromInfo{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", remark='" + remark + '\'' +
                ", area='" + area + '\'' +
                ", brand='" + brand + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
