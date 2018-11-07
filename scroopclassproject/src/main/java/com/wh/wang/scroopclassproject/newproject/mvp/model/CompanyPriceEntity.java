package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/19.
 */

public class CompanyPriceEntity {

    /**
     * status : 1
     * info : {"one":{"id":"1","des":"1","price":"599.00","tao_type":"1","minnum":"5","maxnum":"30","status":"1"},"many":[{"id":"2","des":"5-10人","price":"10000.00","tao_type":"2","minnum":"5","maxnum":"10","status":"1"},{"id":"3","des":"10-20人","price":"20000.00","tao_type":"2","minnum":"10","maxnum":"20","status":"1"},{"id":"4","des":"20-30人","price":"30000.00","tao_type":"2","minnum":"20","maxnum":"30","status":"1"}],"goodsInfo":{"img":"https://www.shaoziketang.com/public/images/ah.png","title":"企业会员，来啊~"}}
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
         * one : {"id":"1","des":"1","price":"599.00","tao_type":"1","minnum":"5","maxnum":"30","status":"1"}
         * many : [{"id":"2","des":"5-10人","price":"10000.00","tao_type":"2","minnum":"5","maxnum":"10","status":"1"},{"id":"3","des":"10-20人","price":"20000.00","tao_type":"2","minnum":"10","maxnum":"20","status":"1"},{"id":"4","des":"20-30人","price":"30000.00","tao_type":"2","minnum":"20","maxnum":"30","status":"1"}]
         * goodsInfo : {"img":"https://www.shaoziketang.com/public/images/ah.png","title":"企业会员，来啊~"}
         */

        private OneBean one;
        private GoodsInfoBean goodsInfo;
        private List<ManyBean> many;
        private int is_vip;

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public OneBean getOne() {
            return one;
        }

        public void setOne(OneBean one) {
            this.one = one;
        }

        public GoodsInfoBean getGoodsInfo() {
            return goodsInfo;
        }

        public void setGoodsInfo(GoodsInfoBean goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        public List<ManyBean> getMany() {
            return many;
        }

        public void setMany(List<ManyBean> many) {
            this.many = many;
        }

        public static class OneBean {
            /**
             * id : 1
             * des : 1
             * price : 599.00
             * tao_type : 1
             * minnum : 5
             * maxnum : 30
             * status : 1
             */

            private String id;
            private String des;
            private String price;
            private String tao_type;
            private String minnum;
            private String maxnum;
            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTao_type() {
                return tao_type;
            }

            public void setTao_type(String tao_type) {
                this.tao_type = tao_type;
            }

            public String getMinnum() {
                return minnum;
            }

            public void setMinnum(String minnum) {
                this.minnum = minnum;
            }

            public String getMaxnum() {
                return maxnum;
            }

            public void setMaxnum(String maxnum) {
                this.maxnum = maxnum;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class GoodsInfoBean {
            /**
             * img : https://www.shaoziketang.com/public/images/ah.png
             * title : 企业会员，来啊~
             */

            private String img;
            private String title;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class ManyBean {
            /**
             * id : 2
             * des : 5-10人
             * price : 10000.00
             * tao_type : 2
             * minnum : 5
             * maxnum : 10
             * status : 1
             */

            private String id;
            private String des;
            private String price;
            private String vip_price;
            private String tao_type;
            private String minnum;
            private String maxnum;
            private String status;

            public String getVip_price() {
                return vip_price;
            }

            public void setVip_price(String vip_price) {
                this.vip_price = vip_price;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTao_type() {
                return tao_type;
            }

            public void setTao_type(String tao_type) {
                this.tao_type = tao_type;
            }

            public String getMinnum() {
                return minnum;
            }

            public void setMinnum(String minnum) {
                this.minnum = minnum;
            }

            public String getMaxnum() {
                return maxnum;
            }

            public void setMaxnum(String maxnum) {
                this.maxnum = maxnum;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
