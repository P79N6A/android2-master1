package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/10/17.
 */

public class MineFeedBackBean implements Serializable {

    /**
     * code : 200
     * list : [{"avator":"http://www.shaoziketang.com/application/views/pic/image53.jpg","content":"用户回复内容","create_time":"10月15日","id":"28","user_id":"17334","user_type":"0"},{"avator":"http://www.shaoziketang.com/application/views/pic/image53.jpg","content":"后台回复消息","create_time":"10月15日","id":"27","user_id":"33","user_type":"1"},{"avator":"http://www.shaoziketang.com/application/views/pic/image53.jpg","content":"用户发送内容","create_time":"10月15日","id":"26","user_id":"17334","user_type":"0"},{"avator":"http://www.shaoziketang.com/application/views/head/image53.jpg","content":"这是一个反馈测试。","create_time":"10月17日","id":"40","user_id":"17334","user_type":"0"},{"avator":"http://www.shaoziketang.com/application/views/head/image53.jpg","content":"啊测试","create_time":"10月17日","id":"41","user_id":"17334","user_type":"0"},{"avator":"http://www.shaoziketang.com/application/views/head/image59.jpg","content":"反馈啦","create_time":"10月18日","id":"42","user_id":"17334","user_type":"0"}]
     * msg : 查询成功
     */

    private int code;
    private String msg;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * avator : http://www.shaoziketang.com/application/views/pic/image53.jpg
         * content : 用户回复内容
         * create_time : 10月15日
         * id : 28
         * user_id : 17334
         * user_type : 0
         */

        private String avator;
        private String content;
        private String create_time;
        private String id;
        private String user_id;
        private int user_type;

        public ListBean(String avator, String content,String create_time, String id, String user_id, int user_type) {
            this.avator = avator;
            this.content = content;
            this.create_time = create_time;
            this.id = id;
            this.user_id = user_id;
            this.user_type = user_type;
        }

        public ListBean() {

        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }
    }
}
