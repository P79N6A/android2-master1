package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/11/3.
 */

public class ReplyResultBean {


    /**
     * code : 200
     * msg : 回复成功
     * info : {"re_name":"wang","nickname":"wang","parentid":2190,"shijian":"2017-11-03 12:10:41","id":"2193","user_id":27155,"content":"五音不全","avator":"http://www.shaoziketang.com/application/views/head/20171024164942.png","up_user":""}
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
         * re_name : wang
         * nickname : wang
         * parentid : 2190
         * shijian : 2017-11-03 12:10:41
         * id : 2193
         * user_id : 27155
         * content : 五音不全
         * avator : http://www.shaoziketang.com/application/views/head/20171024164942.png
         * up_user :
         */

        private String re_name;
        private String nickname;
        private int parentid;
        private String shijian;
        private String id;
        private int user_id;
        private String content;
        private String avator;
        private String up_user;

        public String getRe_name() {
            return re_name;
        }

        public void setRe_name(String re_name) {
            this.re_name = re_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
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
