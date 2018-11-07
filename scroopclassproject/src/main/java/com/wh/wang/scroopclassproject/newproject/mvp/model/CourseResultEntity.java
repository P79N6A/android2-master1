package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/9.
 */

public class CourseResultEntity {

    /**
     * status : 1
     * info : {"product_id":"1303","order_type":"7","num":"2","buy_self":"1","out_link":[{"link":"825a548f92ba74d","mobile":""},{"link":"405a548f92bb100","mobile":""}]}
     */

    private int status;
    private InfoBean info;

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

    public static class InfoBean {
        /**
         * product_id : 1303
         * order_type : 7
         * num : 2
         * buy_self : 1
         * out_link : [{"link":"825a548f92ba74d","mobile":""},{"link":"405a548f92bb100","mobile":""}]
         */

        private String product_id;
        private String order_type;
        private String num;
        private String buy_self;
        private String saveMoney;
        private String person;
        private String is_buy_company;
        private String is_company;
        private List<OutLinkBean> out_link;

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getIs_buy_company() {
            return is_buy_company;
        }

        public void setIs_buy_company(String is_buy_company) {
            this.is_buy_company = is_buy_company;
        }

        public String getIs_company() {
            return is_company;
        }

        public void setIs_company(String is_company) {
            this.is_company = is_company;
        }

        public String getSaveMoney() {
            return saveMoney;
        }

        public void setSaveMoney(String saveMoney) {
            this.saveMoney = saveMoney;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getBuy_self() {
            return buy_self;
        }

        public void setBuy_self(String buy_self) {
            this.buy_self = buy_self;
        }

        public List<OutLinkBean> getOut_link() {
            return out_link;
        }

        public void setOut_link(List<OutLinkBean> out_link) {
            this.out_link = out_link;
        }

        public static class OutLinkBean {
            /**
             * link : 825a548f92ba74d
             * mobile :
             */

            private String link;
            private String mobile;

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
    }
}
