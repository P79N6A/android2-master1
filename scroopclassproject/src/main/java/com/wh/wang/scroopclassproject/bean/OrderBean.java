package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

public class OrderBean implements Serializable {
    private String productName;
    private String className;
    private boolean isclass;
    private Double totalPrice;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private List<PersonBean>personBeans;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isIsclass() {
        return isclass;
    }

    public void setIsclass(boolean isclass) {
        this.isclass = isclass;
    }

    public List<PersonBean> getPersonBeans() {
        return personBeans;
    }

    public void setPersonBeans(List<PersonBean> personBeans) {
        this.personBeans = personBeans;
    }

    public static class PersonBean implements Serializable{
        private String personName;
        private String personNum;

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getPersonNum() {
            return personNum;
        }

        public void setPersonNum(String personNum) {
            this.personNum = personNum;
        }
    }

}
