package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by Administrator on 2017/12/26.
 */

public class CheckUpdateEntity {

    /**
     * version : 10
     * apk : http://apptwo.shaoziketang.com/shaoziAndroidApk.apk
     */

    private String version;
    private String apk;
    private String is_display_company;
    private String event;
    private String is_display_event;
    private String is_display_vip;
    private CouponBean coupon;

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public String getIs_display_vip() {
        return is_display_vip;
    }

    public void setIs_display_vip(String is_display_vip) {
        this.is_display_vip = is_display_vip;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getIs_display_event() {
        return is_display_event;
    }

    public void setIs_display_event(String is_display_event) {
        this.is_display_event = is_display_event;
    }

    public String getIs_display_company() {
        return is_display_company;
    }

    public void setIs_display_company(String is_display_company) {
        this.is_display_company = is_display_company;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }


    public static class CouponBean{

        /**
         * ispaly : 1
         * coupon : {"id":"1","coupon":"2"}
         */

        private String ispaly;  //1 展示
        private Coupon coupon;

        public String getIspaly() {
            return ispaly;
        }

        public void setIspaly(String ispaly) {
            this.ispaly = ispaly;
        }

        public Coupon getCoupon() {
            return coupon;
        }

        public void setCoupon(Coupon coupon) {
            this.coupon = coupon;
        }

        public static class Coupon {
            private String img;
            private String url_type;
            private String item_id;
            private String coupon_type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl_type() {
                return url_type;
            }

            public void setUrl_type(String url_type) {
                this.url_type = url_type;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getCoupon_type() {
                return coupon_type;
            }

            public void setCoupon_type(String coupon_type) {
                this.coupon_type = coupon_type;
            }
        }
    }
}
