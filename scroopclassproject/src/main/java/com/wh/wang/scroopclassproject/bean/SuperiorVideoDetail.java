package com.wh.wang.scroopclassproject.bean;

import java.util.List;

/**
 * Created by wang on 2017/8/30.
 */

public class SuperiorVideoDetail {

    /**
     * code : 200
     * msg : 视频详情查询成功
     * info : [{"post_id":"1178","video_file":"a/u/u.mp4","post_image":"a/G/D.png|900|500","post_title":"巴奴杜中兵:产品主义2.0-餐饮品牌的基因","price":"99.00","post_content":"","vip_price":"0.00","pay_status":null}]
     */

    private int code;
    private String msg;
    private List<InfoBean> info;

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

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * post_id : 1178
         * video_file : a/u/u.mp4
         * post_image : a/G/D.png|900|500
         * post_title : 巴奴杜中兵:产品主义2.0-餐饮品牌的基因
         * price : 99.00
         * post_content :
         * vip_price : 0.00
         * pay_status : null
         */

        private String post_id;
        private String video_file;
        private String post_image;
        private String post_title;
        private double price;
        private String post_content;

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getVideo_file() {
            return video_file;
        }

        public void setVideo_file(String video_file) {
            this.video_file = video_file;
        }

        public String getPost_image() {
            return post_image;
        }

        public void setPost_image(String post_image) {
            this.post_image = post_image;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public double getPrice(double price) {
            return this.price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPost_content() {
            return post_content;
        }

        public void setPost_content(String post_content) {
            this.post_content = post_content;
        }

    }
}
