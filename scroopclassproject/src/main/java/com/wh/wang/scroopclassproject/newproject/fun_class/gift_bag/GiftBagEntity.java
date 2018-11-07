package com.wh.wang.scroopclassproject.newproject.fun_class.gift_bag;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/9/18.
 */

public class GiftBagEntity {

    /**
     * code : 1
     * msg : 成功
     * info : {"video_data":[{"title":"餐厅经营数据分析与实操","img":"https://img.shaoziketang.com/img20180614152941_100.h.jpg","learn":"6224"},{"title":"7种外卖品类全面解析","img":"https://img.shaoziketang.com/img20180423184102_100.h.png","learn":"7348"},{"title":"如何快速达成餐厅业绩指标","img":"https://img.shaoziketang.com/img20180306111027_100.h.jpg","learn":"10200"},{"title":"运营经理必学之门店运营流程","img":"https://img.shaoziketang.com/img20180129154012_100.h.jpg","learn":"3345"}],"coupon":[]}
     */

    private int code;
    private String msg;
    private InfoBean info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
        private List<VideoDataBean> video_data;
        private List<?> coupon;

        public List<VideoDataBean> getVideo_data() {
            return video_data;
        }

        public void setVideo_data(List<VideoDataBean> video_data) {
            this.video_data = video_data;
        }

        public List<?> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<?> coupon) {
            this.coupon = coupon;
        }

        public static class VideoDataBean {
            /**
             * title : 餐厅经营数据分析与实操
             * img : https://img.shaoziketang.com/img20180614152941_100.h.jpg
             * learn : 6224
             */

            private String title;
            private String img;
            private String learn;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLearn() {
                return learn;
            }

            public void setLearn(String learn) {
                this.learn = learn;
            }
        }
    }
}
