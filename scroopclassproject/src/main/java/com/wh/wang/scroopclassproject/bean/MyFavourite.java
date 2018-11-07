package com.wh.wang.scroopclassproject.bean;

import java.util.List;

/**
 * Created by wang on 2017/8/17.
 * 我的课程 我的收藏实体类
 */

public class MyFavourite {

    /**
     * myFavourite : [[{"post_id":"1120","post_type":"news","post_key":"597547ea3801f","post_title":"如何把1美分的咖啡卖到15美元？","post_image":"a/1/6.jpg|900|500","cate_name":"营销","percent":null},{"post_id":"1092","post_type":"news","post_key":"596c10ef72872","post_title":"绝味鸭脖的广告被吐槽，究竟犯了什么错？","post_image":"9/y/M.jpg|900|500","cate_name":"营销","percent":null},{"post_id":"608","post_type":"video","post_key":"58b91a96a4895","post_title":"人员配置与人力模型构建","post_image":"9/p/M.png|900|500","cate_name":"运营","percent":null}]]
     * msg : 查询成功
     * code : 200
     */

    private String msg;
    private int code;
    private List<List<MyFavouriteBean>> myFavourite;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<List<MyFavouriteBean>> getMyFavourite() {
        return myFavourite;
    }

    public void setMyFavourite(List<List<MyFavouriteBean>> myFavourite) {
        this.myFavourite = myFavourite;
    }

    public static class MyFavouriteBean {
        /**
         * post_id : 1120
         * post_type : news
         * post_key : 597547ea3801f
         * post_title : 如何把1美分的咖啡卖到15美元？
         * post_image : a/1/6.jpg|900|500
         * cate_name : 营销
         * percent : null
         */

        private int post_id;
        private String post_type;
        private String post_key;
        private String post_title;
        private String post_image;
        private String cate_name;
        private Object percent;

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public String getPost_type() {
            return post_type;
        }

        public void setPost_type(String post_type) {
            this.post_type = post_type;
        }

        public String getPost_key() {
            return post_key;
        }

        public void setPost_key(String post_key) {
            this.post_key = post_key;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getPost_image() {
            return post_image;
        }

        public void setPost_image(String post_image) {
            this.post_image = post_image;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public Object getPercent() {
            return percent;
        }

        public void setPercent(Object percent) {
            this.percent = percent;
        }

        @Override
        public String toString() {
            return "MyFavouriteBean{" +
                    "post_id='" + post_id + '\'' +
                    ", post_type='" + post_type + '\'' +
                    ", post_key='" + post_key + '\'' +
                    ", post_title='" + post_title + '\'' +
                    ", post_image='" + post_image + '\'' +
                    ", cate_name='" + cate_name + '\'' +
                    ", percent=" + percent +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyFavourite{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", myFavourite=" + myFavourite +
                '}';
    }
}
