package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/7/11.
 */

public class SGHomeEntity {


    /**
     * code : 200
     * msg : 首页数据获取成功
     * info : {"title":"这是一个标题占位符","list":[{"id":"21","relative_video":"946","img":"https://img.shaoziketang.com/img20180709120552_100.h.jpg","publist_shijian":"2018-07-09 12:06:09","end_shijian":"2018-07-16 12:06:11","baoming":"0","maxsum":"50","cate_sum":"8","url":null,"isbaoming":"0"},{"id":"21","relative_video":"946","img":"https://img.shaoziketang.com/img20180709120552_100.h.jpg","publist_shijian":"2018-07-09 12:06:09","end_shijian":"2018-07-16 12:06:11","baoming":"0","maxsum":"50","cate_sum":"8","url":null,"isbaoming":"0"},{"id":"21","relative_video":"946","img":"https://img.shaoziketang.com/img20180709120552_100.h.jpg","publist_shijian":"2018-07-09 12:06:09","end_shijian":"2018-07-16 12:06:11","baoming":"0","maxsum":"50","cate_sum":"8","url":"9/i/H.mp4","isbaoming":"0"}]}
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
         * list : [{"id":"21","relative_video":"946","img":"https://img.shaoziketang.com/img20180709120552_100.h.jpg","publist_shijian":"2018-07-09 12:06:09","end_shijian":"2018-07-16 12:06:11","baoming":"0","maxsum":"50","cate_sum":"8","url":null,"isbaoming":"0"},{"id":"21","relative_video":"946","img":"https://img.shaoziketang.com/img20180709120552_100.h.jpg","publist_shijian":"2018-07-09 12:06:09","end_shijian":"2018-07-16 12:06:11","baoming":"0","maxsum":"50","cate_sum":"8","url":null,"isbaoming":"0"},{"id":"21","relative_video":"946","img":"https://img.shaoziketang.com/img20180709120552_100.h.jpg","publist_shijian":"2018-07-09 12:06:09","end_shijian":"2018-07-16 12:06:11","baoming":"0","maxsum":"50","cate_sum":"8","url":"9/i/H.mp4","isbaoming":"0"}]
         */

        private String title;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 21
             * relative_video : 946
             * img : https://img.shaoziketang.com/img20180709120552_100.h.jpg
             * publist_shijian : 2018-07-09 12:06:09
             * end_shijian : 2018-07-16 12:06:11
             * baoming : 0
             * maxsum : 50
             * cate_sum : 8
             * url : null
             * isbaoming : 0
             */

            private String id;
            private String relative_video;
            private String img;
            private String publist_shijian;
            private String end_shijian;
            private String baoming;
            private String maxsum;
            private String cate_sum;
            private Object url;
            private String is_baoming;
            private String start_shijian;

            public String getStart_shijian() {
                return start_shijian;
            }

            public void setStart_shijian(String start_shijian) {
                this.start_shijian = start_shijian;
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

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public String getIs_baoming() {
                return is_baoming;
            }

            public void setIs_baoming(String is_baoming) {
                this.is_baoming = is_baoming;
            }
        }
    }
}
