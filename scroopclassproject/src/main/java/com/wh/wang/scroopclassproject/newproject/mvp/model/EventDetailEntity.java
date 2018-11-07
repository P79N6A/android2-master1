package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/3.
 */

public class EventDetailEntity implements Serializable {

    /**
     * info : {"id":"1288","title":"西贝、乐凯撒、大龙燚、云味馆、莆田....这些品牌都在上的外卖必修课","price":"0.00","vip_price":"0.00","spsonsor":"3332","address":"3333宇宙中心3","start_shijian":"2018-01-02","isshow":"0","isbao":"1","isrepeat":"0","img_code":"https://img.shaoziketang.com/img_code20180103134707_100.h.jpg","isvip":"0","event_power":"0","share_url":"http://www.shaoziketang.com/event/detail/1288","ticket_id":"","rand_str":"5a4c4bb2eaadd","ios_integral":"0.00","is_vip":0,"is_login":"1"}
     * status : 1
     */

    private InfoBean info;
    private String status;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class InfoBean implements Serializable{
        /**
         * id : 1288
         * title : 西贝、乐凯撒、大龙燚、云味馆、莆田....这些品牌都在上的外卖必修课
         * price : 0.00
         * vip_price : 0.00
         * spsonsor : 3332
         * address : 3333宇宙中心3
         * start_shijian : 2018-01-02
         * isshow : 0
         * isbao : 1
         * isrepeat : 0
         * img_code : https://img.shaoziketang.com/img_code20180103134707_100.h.jpg
         * isvip : 0
         * event_power : 0
         * share_url : http://www.shaoziketang.com/event/detail/1288
         * ticket_id :
         * rand_str : 5a4c4bb2eaadd
         * ios_integral : 0.00
         * is_vip : 0
         * is_login : 1
         */

        private String id;
        private String title;
        private String price;
        private String vip_price;
        private String spsonsor;
        private String address;
        private String start_shijian;
        private String isshow;
        private String isbao;
        private String isrepeat;
        private String img_code;
        private String isvip;
        private String event_power;
        private String share_url;
        private String ticket_id;
        private String rand_str;
        private String ios_integral;
        private int is_vip;
        private String is_login;
        private String code_hao;
        private String video_id;
        private String shaozi_img;
        private String shaozi_name;
        private String real_price;
        private String sub_price;
        private String bao;
        private String old_price;
        private String go_on;
        private String price_two;

        public String getPrice_two() {
            return price_two;
        }

        public void setPrice_two(String price_two) {
            this.price_two = price_two;
        }

        public String getGo_on() {
            return go_on;
        }

        public void setGo_on(String go_on) {
            this.go_on = go_on;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getBao() {
            return bao;
        }

        public void setBao(String bao) {
            this.bao = bao;
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

        public String getCode_hao() {
            return code_hao;
        }

        public void setCode_hao(String code_hao) {
            this.code_hao = code_hao;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getShaozi_img() {
            return shaozi_img;
        }

        public void setShaozi_img(String shaozi_img) {
            this.shaozi_img = shaozi_img;
        }

        public String getShaozi_name() {
            return shaozi_name;
        }

        public void setShaozi_name(String shaozi_name) {
            this.shaozi_name = shaozi_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getSpsonsor() {
            return spsonsor;
        }

        public void setSpsonsor(String spsonsor) {
            this.spsonsor = spsonsor;
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

        public String getIsshow() {
            return isshow;
        }

        public void setIsshow(String isshow) {
            this.isshow = isshow;
        }

        public String getIsbao() {
            return isbao;
        }

        public void setIsbao(String isbao) {
            this.isbao = isbao;
        }

        public String getIsrepeat() {
            return isrepeat;
        }

        public void setIsrepeat(String isrepeat) {
            this.isrepeat = isrepeat;
        }

        public String getImg_code() {
            return img_code;
        }

        public void setImg_code(String img_code) {
            this.img_code = img_code;
        }

        public String getIsvip() {
            return isvip;
        }

        public void setIsvip(String isvip) {
            this.isvip = isvip;
        }

        public String getEvent_power() {
            return event_power;
        }

        public void setEvent_power(String event_power) {
            this.event_power = event_power;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        public String getRand_str() {
            return rand_str;
        }

        public void setRand_str(String rand_str) {
            this.rand_str = rand_str;
        }

        public String getIos_integral() {
            return ios_integral;
        }

        public void setIos_integral(String ios_integral) {
            this.ios_integral = ios_integral;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }
    }
}
