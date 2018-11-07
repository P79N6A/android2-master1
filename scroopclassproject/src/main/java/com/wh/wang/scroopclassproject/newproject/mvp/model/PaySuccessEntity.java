package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/2/6.
 */

public class PaySuccessEntity {

    /**
     * code : 200
     * msg : 查询成功
     * info : {"relative_video":"","img_code":"https://img.shaoziketang.com/img_code20180131162309_100.h.png","code_hao":"","weixin_hao":"","title":"外卖爆单7步法\u2014\u2014第一期","address":"勺子课堂","start_shijian":"2018年3月14日（周三）","video_type":"","weixin_name":"勺布斯","real_price":"1314","sub_price":"2336.00"}
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
        /**
         * relative_video :
         * img_code : https://img.shaoziketang.com/img_code20180131162309_100.h.png
         * code_hao :
         * weixin_hao :
         * title : 外卖爆单7步法——第一期
         * address : 勺子课堂
         * start_shijian : 2018年3月14日（周三）
         * video_type :
         * weixin_name : 勺布斯
         * real_price : 1314
         * sub_price : 2336.00
         */

        private String relative_video;
        private String img_code;
        private String code_hao;
        private String weixin_hao;
        private String title;
        private String address;
        private String start_shijian;
        private String video_type;
        private String weixin_name;
        private String real_price;
        private String sub_price;
        private String is_vip;
        private String my_bao;
        private String price;
        private String vip_price;
        private String pid;
        private List<Messages>batch;

        public List<Messages> getBatch() {
            return batch;
        }

        public void setBatch(List<Messages> batch) {
            this.batch = batch;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVip_price() {
            return vip_price;
        }

        public void setVip_price(String vip_price) {
            this.vip_price = vip_price;
        }

        public String getMy_bao() {
            return my_bao;
        }

        public void setMy_bao(String my_bao) {
            this.my_bao = my_bao;
        }

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getRelative_video() {
            return relative_video;
        }

        public void setRelative_video(String relative_video) {
            this.relative_video = relative_video;
        }

        public String getImg_code() {
            return img_code;
        }

        public void setImg_code(String img_code) {
            this.img_code = img_code;
        }

        public String getCode_hao() {
            return code_hao;
        }

        public void setCode_hao(String code_hao) {
            this.code_hao = code_hao;
        }

        public String getWeixin_hao() {
            return weixin_hao;
        }

        public void setWeixin_hao(String weixin_hao) {
            this.weixin_hao = weixin_hao;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStart_shijian() {
            return start_shijian;
        }

        public void setStart_shijian(String start_shijian) {
            this.start_shijian = start_shijian;
        }

        public String getVideo_type() {
            return video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getWeixin_name() {
            return weixin_name;
        }

        public void setWeixin_name(String weixin_name) {
            this.weixin_name = weixin_name;
        }

        public String getReal_price() {
            return real_price;
        }

        public void setReal_price(String real_price) {
            this.real_price = real_price;
        }

        public String getSub_price() {
            return sub_price;
        }

        public void setSub_price(String sub_price) {
            this.sub_price = sub_price;
        }

        public static class Messages {
            public String name;
            public String phone;
            private String money;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
