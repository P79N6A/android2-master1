package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/17.
 */

public class SGDetail2_0Entity {



    /**
     * code : 200
     * msg : 详情页数据获取成功
     * info : {"popup":"1","card_status":[],"is_complete":1,"Scholarship":{"bonus":"满$10$人1000元#满$50$人5000元#满$100$人10000元","bonus_rang":"1000-10000","num":["10","50","100"]},"status":"","info":{"relative_video":"1","id":"26","title":"学习小组2.0测试1","img":"https://img.shaoziketang.com/img20180724145219_100.h.png","publist_shijian":"1532415161","publist_shijian_ex":"2018-07-24 14:52:41","now_time":"1534467490","start_shijian":"马上","baoming":"0","img_qr":"","img_con":"https://img.shaoziketang.com/img_app20180724145227_100.h.png","img_con_vip":"https://img.shaoziketang.com/img_suping20180724145233_100.h.png"},"user_count":{"count":"0","avator":["4"]},"teacher":{"teacher_name":"","duan":"","head":""},"img_qr":"","text":"","punch":"0","card_ext":3}
     */

    private String code;
    private String msg;
    private InfoBeanX info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InfoBeanX getInfo() {
        return info;
    }

    public void setInfo(InfoBeanX info) {
        this.info = info;
    }

    public static class InfoBeanX implements Serializable{
        /**
         * popup : 1
         * card_status : []
         * is_complete : 1
         * Scholarship : {"bonus":"满$10$人1000元#满$50$人5000元#满$100$人10000元","bonus_rang":"1000-10000","num":["10","50","100"]}
         * status :
         * info : {"relative_video":"1","id":"26","title":"学习小组2.0测试1","img":"https://img.shaoziketang.com/img20180724145219_100.h.png","publist_shijian":"1532415161","publist_shijian_ex":"2018-07-24 14:52:41","now_time":"1534467490","start_shijian":"马上","baoming":"0","img_qr":"","img_con":"https://img.shaoziketang.com/img_app20180724145227_100.h.png","img_con_vip":"https://img.shaoziketang.com/img_suping20180724145233_100.h.png"}
         * user_count : {"count":"0","avator":["4"]}
         * teacher : {"teacher_name":"","duan":"","head":""}
         * img_qr :
         * text :
         * punch : 0
         * card_ext : 3
         */

        private String popup;
        private String is_complete;
        private ScholarshipBean Scholarship;
        private String status;
        private InfoBean info;
        private UserCountBean user_count;
        private TeacherBean teacher;
        private String img_qr;
        private String text;
        private String punch;
        private String punch_card;
        private int card_ext;
        private List<CardStatusBean> card_status;
        private String notice;
        private String realbonus;
        private String cate_title;
        private String maxnum;
        private String islimit;
        private AchieveCardBean achieveCard;
        private CateInfo cate_title_num;
        private String cashback;


        public String getCashback() {
            return cashback;
        }

        public void setCashback(String cashback) {
            this.cashback = cashback;
        }

        public CateInfo getCate_title_num() {
            return cate_title_num;
        }

        public void setCate_title_num(CateInfo cate_title_num) {
            this.cate_title_num = cate_title_num;
        }

        public String getPunch_card() {
            return punch_card;
        }

        public void setPunch_card(String punch_card) {
            this.punch_card = punch_card;
        }

        public String getIslimit() {
            return islimit;
        }

        public void setIslimit(String islimit) {
            this.islimit = islimit;
        }

        public AchieveCardBean getAchieveCard() {
            return achieveCard;
        }

        public void setAchieveCard(AchieveCardBean achieveCard) {
            this.achieveCard = achieveCard;
        }

        public String getMaxnum() {
            return maxnum;
        }

        public void setMaxnum(String maxnum) {
            this.maxnum = maxnum;
        }

        public String getCate_title() {
            return cate_title;
        }

        public void setCate_title(String cate_title) {
            this.cate_title = cate_title;
        }

        public String getRealbonus() {
            return realbonus;
        }

        public void setRealbonus(String realbonus) {
            this.realbonus = realbonus;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getPopup() {
            return popup;
        }

        public void setPopup(String popup) {
            this.popup = popup;
        }

        public String getIs_complete() {
            return is_complete;
        }

        public void setIs_complete(String is_complete) {
            this.is_complete = is_complete;
        }

        public ScholarshipBean getScholarship() {
            return Scholarship;
        }

        public void setScholarship(ScholarshipBean Scholarship) {
            this.Scholarship = Scholarship;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public UserCountBean getUser_count() {
            return user_count;
        }

        public void setUser_count(UserCountBean user_count) {
            this.user_count = user_count;
        }

        public TeacherBean getTeacher() {
            return teacher;
        }

        public void setTeacher(TeacherBean teacher) {
            this.teacher = teacher;
        }

        public String getImg_qr() {
            return img_qr;
        }

        public void setImg_qr(String img_qr) {
            this.img_qr = img_qr;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPunch() {
            return punch;
        }

        public void setPunch(String punch) {
            this.punch = punch;
        }

        public int getCard_ext() {
            return card_ext;
        }

        public void setCard_ext(int card_ext) {
            this.card_ext = card_ext;
        }

        public List<CardStatusBean> getCard_status() {
            return card_status;
        }

        public void setCard_status(List<CardStatusBean> card_status) {
            this.card_status = card_status;
        }

        public static class CateInfo implements Serializable{
            private String cate_title;
            private String cate_num;

            public String getCate_title() {
                return cate_title;
            }

            public void setCate_title(String cate_title) {
                this.cate_title = cate_title;
            }

            public String getCate_num() {
                return cate_num;
            }

            public void setCate_num(String cate_num) {
                this.cate_num = cate_num;
            }
        }
        public static class AchieveCardBean implements Serializable{
            String user_name;
            String title;
            String s_time;
            String v_time;
            String z_time;
            String day;
            String totalday;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getTotalday() {
                return totalday;
            }

            public void setTotalday(String totalday) {
                this.totalday = totalday;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getS_time() {
                return s_time;
            }

            public void setS_time(String s_time) {
                this.s_time = s_time;
            }

            public String getV_time() {
                return v_time;
            }

            public void setV_time(String v_time) {
                this.v_time = v_time;
            }

            public String getZ_time() {
                return z_time;
            }

            public void setZ_time(String z_time) {
                this.z_time = z_time;
            }
        }

        public static class CardStatusBean implements Serializable{
            /**
             * status : 0
             * s_time : 2018-08-24
             * green : 0
             */

            private String status;
            private String s_time;
            private int green;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getS_time() {
                return s_time;
            }

            public void setS_time(String s_time) {
                this.s_time = s_time;
            }

            public int getGreen() {
                return green;
            }

            public void setGreen(int green) {
                this.green = green;
            }
        }
        public static class ScholarshipBean implements Serializable{
            /**
             * bonus : 满$10$人1000元#满$50$人5000元#满$100$人10000元
             * bonus_rang : 1000-10000
             * num : ["10","50","100"]
             */

            private String bonus;
            private String bonus_rang;
            private List<String> num;

            public String getBonus() {
                return bonus;
            }

            public void setBonus(String bonus) {
                this.bonus = bonus;
            }

            public String getBonus_rang() {
                return bonus_rang;
            }

            public void setBonus_rang(String bonus_rang) {
                this.bonus_rang = bonus_rang;
            }

            public List<String> getNum() {
                return num;
            }

            public void setNum(List<String> num) {
                this.num = num;
            }
        }

        public static class InfoBean implements Serializable{
            /**
             * relative_video : 1
             * id : 26
             * title : 学习小组2.0测试1
             * img : https://img.shaoziketang.com/img20180724145219_100.h.png
             * publist_shijian : 1532415161
             * publist_shijian_ex : 2018-07-24 14:52:41
             * now_time : 1534467490
             * start_shijian : 马上
             * baoming : 0
             * img_qr :
             * img_con : https://img.shaoziketang.com/img_app20180724145227_100.h.png
             * img_con_vip : https://img.shaoziketang.com/img_suping20180724145233_100.h.png
             */

            private String relative_video;
            private String id;
            private String event;
            private String title;
            private String img;
            private String publist_shijian;
            private String publist_shijian_ex;
            private String now_time;
            private String start_shijian;
            private String baoming;
            private String img_qr;
            private String img_con;
            private String img_con_vip;
            private String cate_sum;
            private String ya_money;


            public String getYa_money() {
                return ya_money;
            }

            public void setYa_money(String ya_money) {
                this.ya_money = ya_money;
            }

            public String getEvent() {
                return event;
            }

            public void setEvent(String event) {
                this.event = event;
            }

            public String getCate_sum() {
                return cate_sum;
            }

            public void setCate_sum(String cate_sum) {
                this.cate_sum = cate_sum;
            }

            public String getRelative_video() {
                return relative_video;
            }

            public void setRelative_video(String relative_video) {
                this.relative_video = relative_video;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPublist_shijian() {
                return publist_shijian;
            }

            public void setPublist_shijian(String publist_shijian) {
                this.publist_shijian = publist_shijian;
            }

            public String getPublist_shijian_ex() {
                return publist_shijian_ex;
            }

            public void setPublist_shijian_ex(String publist_shijian_ex) {
                this.publist_shijian_ex = publist_shijian_ex;
            }

            public String getNow_time() {
                return now_time;
            }

            public void setNow_time(String now_time) {
                this.now_time = now_time;
            }

            public String getStart_shijian() {
                return start_shijian;
            }

            public void setStart_shijian(String start_shijian) {
                this.start_shijian = start_shijian;
            }

            public String getBaoming() {
                return baoming;
            }

            public void setBaoming(String baoming) {
                this.baoming = baoming;
            }

            public String getImg_qr() {
                return img_qr;
            }

            public void setImg_qr(String img_qr) {
                this.img_qr = img_qr;
            }

            public String getImg_con() {
                return img_con;
            }

            public void setImg_con(String img_con) {
                this.img_con = img_con;
            }

            public String getImg_con_vip() {
                return img_con_vip;
            }

            public void setImg_con_vip(String img_con_vip) {
                this.img_con_vip = img_con_vip;
            }
        }

        public static class UserCountBean implements Serializable{
            /**
             * count : 0
             * avator : ["4"]
             */

            private String count;
            private List<String> avator;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public List<String> getAvator() {
                return avator;
            }

            public void setAvator(List<String> avator) {
                this.avator = avator;
            }
        }

        public static class TeacherBean implements Serializable{
            /**
             * teacher_name :
             * duan :
             * head :
             */

            private String teacher_name;
            private String duan;
            private String head;

            public String getTeacher_name() {
                return teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getDuan() {
                return duan;
            }

            public void setDuan(String duan) {
                this.duan = duan;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
        }
    }
}
