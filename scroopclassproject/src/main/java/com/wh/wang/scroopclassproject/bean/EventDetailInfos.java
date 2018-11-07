package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;

/**
 * Created by wang on 2017/9/8.
 */

public class EventDetailInfos {


    /**
     * info : {"address":"在线课堂","event_power":"0","id":"123","isbao":"1","isrepeat":"0","isshow":"1","isvip":"1","price":"199","spsonsor":"勺子课堂","start_shijian":"0000-00-00","ticket_id":"6","title":"外卖升级!品类革命先行","vip_price":"0"}
     * status : 0
     */

    public static class InfoBean implements Serializable {
        /**
         * address : 在线课堂//
         * event_power : 0//
         * id : 123//
         * isbao : 1
         * isrepeat : 0
         * isshow : 1
         * isvip : 1
         * price : 199
         * spsonsor : 勺子课堂
         * start_shijian : 0000-00-00
         * ticket_id : 6
         * title : 外卖升级!品类革命先行
         * vip_price : 0
         */

        private String address;//
        private int event_power;//
        private int id;//
        private int isbao;//
        private int isrepeat;
        private int isshow;//
        private int isvip;//
        private double price;//
        private String spsonsor;//
        private String start_shijian;//
        private String ticket_id;//
        private String title;//
        private double vip_price;//
        private String rand_str;
        private String ios_integral;
        private String share_url;
        private int is_login;
        private int status;

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getIs_login() {
            return is_login;
        }

        public void setIs_login(int is_login) {
            this.is_login = is_login;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getEvent_power() {
            return event_power;
        }

        public void setEvent_power(int event_power) {
            this.event_power = event_power;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsbao() {
            return isbao;
        }

        public void setIsbao(int isbao) {
            this.isbao = isbao;
        }

        public int getIsrepeat() {
            return isrepeat;
        }

        public void setIsrepeat(int isrepeat) {
            this.isrepeat = isrepeat;
        }

        public int getIsshow() {
            return isshow;
        }

        public void setIsshow(int isshow) {
            this.isshow = isshow;
        }

        public int getIsvip() {
            return isvip;
        }

        public void setIsvip(int isvip) {
            this.isvip = isvip;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSpsonsor() {
            return spsonsor;
        }

        public void setSpsonsor(String spsonsor) {
            this.spsonsor = spsonsor;
        }

        public String getStart_shijian() {
            return start_shijian;
        }

        public void setStart_shijian(String start_shijian) {
            this.start_shijian = start_shijian;
        }

        public String getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(String ticket_id) {
            this.ticket_id = ticket_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getVip_price() {
            return vip_price;
        }

        public void setVip_price(double vip_price) {
            this.vip_price = vip_price;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "address='" + address + '\'' +
                    ", event_power=" + event_power +
                    ", id=" + id +
                    ", isbao=" + isbao +
                    ", isrepeat=" + isrepeat +
                    ", isshow=" + isshow +
                    ", isvip=" + isvip +
                    ", price=" + price +
                    ", spsonsor='" + spsonsor + '\'' +
                    ", start_shijian='" + start_shijian + '\'' +
                    ", ticket_id='" + ticket_id + '\'' +
                    ", title='" + title + '\'' +
                    ", vip_price=" + vip_price +
                    ", rand_str='" + rand_str + '\'' +
                    ", ios_integral='" + ios_integral + '\'' +
                    ", status=" + status +
                    '}';
        }
    }
}
