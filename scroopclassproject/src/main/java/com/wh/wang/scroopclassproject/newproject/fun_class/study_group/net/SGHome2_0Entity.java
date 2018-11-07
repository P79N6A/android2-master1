package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/16.
 */

public class SGHome2_0Entity {


    /**
     * code : 200
     * msg : 首页数据获取成功
     * info : {"title":"这是一个标题占位符","list":{"status":2,"myJoin":[{"id":"23","relative_video":"1571","img":"https://img.shaoziketang.com/img20180718154912_100.h.jpg","publist_shijian":"2018-07-26 00:00:00","end_shijian":"2018-07-28 23:59:59","baoming":"50","maxsum":"50","cate_sum":"3","start_shijian":"7月26日--28日","bonus_rang":"","is_baoming":1,"ispunch":0,"day":21}],"punch_card":"2","hot":[{"id":"35","relative_video":"1585","img":"https://img.shaoziketang.com/img20180814171902_100.h.png","publist_shijian":"2018-08-18 00:00:00","end_shijian":"2018-08-20 23:59:59","baoming":"49","maxsum":"100","cate_sum":"3","start_shijian":"8月18日-20日","bonus_rang":"","ispunch":0,"day":2}]}}
     */

    private String code;
    private String msg;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * title : 这是一个标题占位符
         * list : {"status":2,"myJoin":[{"id":"23","relative_video":"1571","img":"https://img.shaoziketang.com/img20180718154912_100.h.jpg","publist_shijian":"2018-07-26 00:00:00","end_shijian":"2018-07-28 23:59:59","baoming":"50","maxsum":"50","cate_sum":"3","start_shijian":"7月26日--28日","bonus_rang":"","is_baoming":1,"ispunch":0,"day":21}],"punch_card":"2","hot":[{"id":"35","relative_video":"1585","img":"https://img.shaoziketang.com/img20180814171902_100.h.png","publist_shijian":"2018-08-18 00:00:00","end_shijian":"2018-08-20 23:59:59","baoming":"49","maxsum":"100","cate_sum":"3","start_shijian":"8月18日-20日","bonus_rang":"","ispunch":0,"day":2}]}
         */

        private String title;
        private ListBean list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * status : 2
             * myJoin : [{"id":"23","relative_video":"1571","img":"https://img.shaoziketang.com/img20180718154912_100.h.jpg","publist_shijian":"2018-07-26 00:00:00","end_shijian":"2018-07-28 23:59:59","baoming":"50","maxsum":"50","cate_sum":"3","start_shijian":"7月26日--28日","bonus_rang":"","is_baoming":1,"ispunch":0,"day":21}]
             * punch_card : 2
             * hot : [{"id":"35","relative_video":"1585","img":"https://img.shaoziketang.com/img20180814171902_100.h.png","publist_shijian":"2018-08-18 00:00:00","end_shijian":"2018-08-20 23:59:59","baoming":"49","maxsum":"100","cate_sum":"3","start_shijian":"8月18日-20日","bonus_rang":"","ispunch":0,"day":2}]
             */

            private int status;
            private String punch_card;
            private String totalDay;
            private List<MyJoinBean> myJoin;
            private List<HotBean> hot;

            public String getTotalDay() {
                return totalDay;
            }

            public void setTotalDay(String totalDay) {
                this.totalDay = totalDay;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getPunch_card() {
                return punch_card;
            }

            public void setPunch_card(String punch_card) {
                this.punch_card = punch_card;
            }

            public List<MyJoinBean> getMyJoin() {
                return myJoin;
            }

            public void setMyJoin(List<MyJoinBean> myJoin) {
                this.myJoin = myJoin;
            }

            public List<HotBean> getHot() {
                return hot;
            }

            public void setHot(List<HotBean> hot) {
                this.hot = hot;
            }

            public static class MyJoinBean implements Serializable{
                /**
                 * id : 23
                 * relative_video : 1571
                 * img : https://img.shaoziketang.com/img20180718154912_100.h.jpg
                 * publist_shijian : 2018-07-26 00:00:00
                 * end_shijian : 2018-07-28 23:59:59
                 * baoming : 50
                 * maxsum : 50
                 * cate_sum : 3
                 * start_shijian : 7月26日--28日
                 * bonus_rang :
                 * is_baoming : 1
                 * ispunch : 0
                 * day : 21
                 */

                private String id;
                private String relative_video;
                private String img;
                private String publist_shijian;
                private String end_shijian;
                private String baoming;
                private String maxsum;
                private String cate_sum;
                private String start_shijian;
                private String bonus_rang;
                private String is_baoming;
                private String ispunch;
                private String day;
                private String title;
                private String type;

                public String getIs_baoming() {
                    return is_baoming;
                }

                public void setIs_baoming(String is_baoming) {
                    this.is_baoming = is_baoming;
                }

                public String getIspunch() {
                    return ispunch;
                }

                public void setIspunch(String ispunch) {
                    this.ispunch = ispunch;
                }

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getRelative_video() {
                    return relative_video;
                }

                public void setRelative_video(String relative_video) {
                    this.relative_video = relative_video;
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

                public String getEnd_shijian() {
                    return end_shijian;
                }

                public void setEnd_shijian(String end_shijian) {
                    this.end_shijian = end_shijian;
                }

                public String getBaoming() {
                    return baoming;
                }

                public void setBaoming(String baoming) {
                    this.baoming = baoming;
                }

                public String getMaxsum() {
                    return maxsum;
                }

                public void setMaxsum(String maxsum) {
                    this.maxsum = maxsum;
                }

                public String getCate_sum() {
                    return cate_sum;
                }

                public void setCate_sum(String cate_sum) {
                    this.cate_sum = cate_sum;
                }

                public String getStart_shijian() {
                    return start_shijian;
                }

                public void setStart_shijian(String start_shijian) {
                    this.start_shijian = start_shijian;
                }

                public String getBonus_rang() {
                    return bonus_rang;
                }

                public void setBonus_rang(String bonus_rang) {
                    this.bonus_rang = bonus_rang;
                }
            }

            public static class HotBean {
                /**
                 * id : 35
                 * relative_video : 1585
                 * img : https://img.shaoziketang.com/img20180814171902_100.h.png
                 * publist_shijian : 2018-08-18 00:00:00
                 * end_shijian : 2018-08-20 23:59:59
                 * baoming : 49
                 * maxsum : 100
                 * cate_sum : 3
                 * start_shijian : 8月18日-20日
                 * bonus_rang :
                 * ispunch : 0
                 * day : 2
                 */

                private String id;
                private String relative_video;
                private String img;
                private String publist_shijian;
                private String end_shijian;
                private String baoming;
                private String maxsum;
                private String cate_sum;
                private String start_shijian;
                private String bonus_rang;
                private int ispunch;
                private int day;
                private String title;
                private String type;
                private String is_baoming;

                public String getIs_baoming() {
                    return is_baoming;
                }

                public void setIs_baoming(String is_baoming) {
                    this.is_baoming = is_baoming;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getRelative_video() {
                    return relative_video;
                }

                public void setRelative_video(String relative_video) {
                    this.relative_video = relative_video;
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

                public String getEnd_shijian() {
                    return end_shijian;
                }

                public void setEnd_shijian(String end_shijian) {
                    this.end_shijian = end_shijian;
                }

                public String getBaoming() {
                    return baoming;
                }

                public void setBaoming(String baoming) {
                    this.baoming = baoming;
                }

                public String getMaxsum() {
                    return maxsum;
                }

                public void setMaxsum(String maxsum) {
                    this.maxsum = maxsum;
                }

                public String getCate_sum() {
                    return cate_sum;
                }

                public void setCate_sum(String cate_sum) {
                    this.cate_sum = cate_sum;
                }

                public String getStart_shijian() {
                    return start_shijian;
                }

                public void setStart_shijian(String start_shijian) {
                    this.start_shijian = start_shijian;
                }

                public String getBonus_rang() {
                    return bonus_rang;
                }

                public void setBonus_rang(String bonus_rang) {
                    this.bonus_rang = bonus_rang;
                }

                public int getIspunch() {
                    return ispunch;
                }

                public void setIspunch(int ispunch) {
                    this.ispunch = ispunch;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }
            }
        }
    }
}
