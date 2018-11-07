package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by chdh on 2018/2/24.
 */

public class CommentEntity {

    /**
     * code : 200
     * msg : 评论成功
     * info : {"re_name":"","parentid":"2858","nickname":"丁豪","shijian":"2018-02-24 09:31:15","id":"2858","user_id":"39347","content":"666","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","up_user":""}
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
         * re_name :
         * parentid : 2858
         * nickname : 丁豪
         * shijian : 2018-02-24 09:31:15
         * id : 2858
         * user_id : 39347
         * content : 666
         * avator : https://www.shaoziketang.com/application/views/head/20180117134848.png
         * up_user :
         */

        private String re_name;
        private String parentid;
        private String nickname;
        private String shijian;
        private String id;
        private String user_id;
        private String content;
        private String avator;
        private String up_user;

        public String getRe_name() {
            return re_name;
        }

        public void setRe_name(String re_name) {
            this.re_name = re_name;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getUp_user() {
            return up_user;
        }

        public void setUp_user(String up_user) {
            this.up_user = up_user;
        }
    }
}
