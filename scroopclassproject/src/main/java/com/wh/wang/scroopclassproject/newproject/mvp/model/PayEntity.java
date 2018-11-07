package com.wh.wang.scroopclassproject.newproject.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/14.
 */

public class PayEntity {

    /**
     * status : 1
     * info : {"order_no":"201712110848307158267","wechat":{"appid":"wx7711b78effa9ae30","partnerid":"1488125332","prepayid":"wx201712111741279bdbe516f00069702068","noncestr":"5a2e52c79d42c","timestamp":1512985287,"package":"Sign=WXPay","sign":"54D3B2FECA0849220F0F6019C5A56E04"},"alipay":""}
     */

    private int status = 1;
    private InfoBean info;
    private String fee;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

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
         * order_no : 201712110848307158267
         * wechat : {"appid":"wx7711b78effa9ae30","partnerid":"1488125332","prepayid":"wx201712111741279bdbe516f00069702068","noncestr":"5a2e52c79d42c","timestamp":1512985287,"package":"Sign=WXPay","sign":"54D3B2FECA0849220F0F6019C5A56E04"}
         * alipay :
         */

        private String order_no;
        private WechatBean wechat;
        private String alipay;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public WechatBean getWechat() {
            return wechat;
        }

        public void setWechat(WechatBean wechat) {
            this.wechat = wechat;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public static class WechatBean {
            /**
             * appid : wx7711b78effa9ae30
             * partnerid : 1488125332
             * prepayid : wx201712111741279bdbe516f00069702068
             * noncestr : 5a2e52c79d42c
             * timestamp : 1512985287
             * package : Sign=WXPay
             * sign : 54D3B2FECA0849220F0F6019C5A56E04
             */

            private String appid;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private String timestamp;
            @SerializedName("package")
            private String packageX;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
