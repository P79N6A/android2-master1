package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/3/2.
 */

public class WorkDetailsEntity {

    /**
     * code : 200
     * info : {"user_id":"39347","user_name":"丁豪","user_head":"https://www.shaoziketang.com/application/views/head/20180117134848.png","shijian":"2018-03-01 13:01:25","liulan":"8","zhan_sum":"0","comment_sum":"0","title":"胡 顶戴顶替 模压","ifrenzheng":0}
     * comment : [{"re_name":"","nickname":"大白LEE®","id":"2886","up_user":4,"user_id":"35285","content":"sdsdsfdf","avator":"http://wx.qlogo.cn/mmopen/vi_32/46AUkKhEV2aS7oPOEm9ZbKUepQEYeEb9ibWiasV82Rm98klHKnPK9n8leT2lvawuplCNRPmjZPIiaOYMR6yIrkkPQ/0","shijian":"2018-03-01 18:07:19","child":[{"re_name":"zhang","nickname":"图样","id":"2887","up_user":0,"user_id":"38256","content":"sh iv sadflasjf","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","shijian":"2018-03-01 18:18:00"}]},{"re_name":"","nickname":"大白LEE®","id":"2885","up_user":1,"user_id":"35285","content":"","avator":"http://wx.qlogo.cn/mmopen/vi_32/46AUkKhEV2aS7oPOEm9ZbKUepQEYeEb9ibWiasV82Rm98klHKnPK9n8leT2lvawuplCNRPmjZPIiaOYMR6yIrkkPQ/0","shijian":"2018-03-01 18:06:27","child":[]}]
     */

    private int code;
    private InfoBean info;
    private List<CommentBean> comment;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class InfoBean {
        /**
         * user_id : 39347
         * user_name : 丁豪
         * user_head : https://www.shaoziketang.com/application/views/head/20180117134848.png
         * shijian : 2018-03-01 13:01:25
         * liulan : 8
         * zhan_sum : 0
         * comment_sum : 0
         * title : 胡 顶戴顶替 模压
         * ifrenzheng : 0
         */

        private String user_id;
        private String user_name;
        private String user_head;
        private String shijian;
        private String liulan;
        private String zhan_sum;
        private String comment_sum;
        private String title;
        private int ifrenzheng;
        private String content;
        private String url;
        private int is_youxiu;
        private int ifteacherme;

        public int getIfteacherme() {
            return ifteacherme;
        }

        public void setIfteacherme(int ifteacherme) {
            this.ifteacherme = ifteacherme;
        }

        public int getIs_youxiu() {
            return is_youxiu;
        }

        public void setIs_youxiu(int is_youxiu) {
            this.is_youxiu = is_youxiu;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_head() {
            return user_head;
        }

        public void setUser_head(String user_head) {
            this.user_head = user_head;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getLiulan() {
            return liulan;
        }

        public void setLiulan(String liulan) {
            this.liulan = liulan;
        }

        public String getZhan_sum() {
            return zhan_sum;
        }

        public void setZhan_sum(String zhan_sum) {
            this.zhan_sum = zhan_sum;
        }

        public String getComment_sum() {
            return comment_sum;
        }

        public void setComment_sum(String comment_sum) {
            this.comment_sum = comment_sum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIfrenzheng() {
            return ifrenzheng;
        }

        public void setIfrenzheng(int ifrenzheng) {
            this.ifrenzheng = ifrenzheng;
        }
    }

    public static class CommentBean {
        /**
         * re_name :
         * nickname : 大白LEE®
         * id : 2886
         * up_user : 4
         * user_id : 35285
         * content : sdsdsfdf
         * avator : http://wx.qlogo.cn/mmopen/vi_32/46AUkKhEV2aS7oPOEm9ZbKUepQEYeEb9ibWiasV82Rm98klHKnPK9n8leT2lvawuplCNRPmjZPIiaOYMR6yIrkkPQ/0
         * shijian : 2018-03-01 18:07:19
         * child : [{"re_name":"zhang","nickname":"图样","id":"2887","up_user":0,"user_id":"38256","content":"sh iv sadflasjf","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","shijian":"2018-03-01 18:18:00"}]
         */

        private String re_name;
        private String nickname;
        private String id;
        private int up_user;
        private String user_id;
        private String content;
        private String avator;
        private String shijian;
        private int ifrenzheng;
        private int ifzhan;
        private String parentid;
        private List<ChildBean> child;

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public int getIfrenzheng() {
            return ifrenzheng;
        }

        public void setIfrenzheng(int ifrenzheng) {
            this.ifrenzheng = ifrenzheng;
        }

        public int getIfzhan() {
            return ifzhan;
        }

        public void setIfzhan(int ifzhan) {
            this.ifzhan = ifzhan;
        }

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUp_user() {
            return up_user;
        }

        public void setUp_user(int up_user) {
            this.up_user = up_user;
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

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * re_name : zhang
             * nickname : 图样
             * id : 2887
             * up_user : 0
             * user_id : 38256
             * content : sh iv sadflasjf
             * avator : https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg
             * shijian : 2018-03-01 18:18:00
             */

            private String re_name;
            private String nickname;
            private String id;
            private int up_user;
            private String user_id;
            private String content;
            private String avator;
            private String shijian;
            private String parentid;

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getUp_user() {
                return up_user;
            }

            public void setUp_user(int up_user) {
                this.up_user = up_user;
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

            public String getShijian() {
                return shijian;
            }

            public void setShijian(String shijian) {
                this.shijian = shijian;
            }
        }
    }
}
