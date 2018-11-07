package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/25.
 */

public class LikeOrRemindEntity {

    /**
     * list : [{"user_id":"38256","child_id":"38256","title":"赞了你的学习","content":"点击查看一周数据排行榜","item_id":"1295","item_type":"1","create_time":"01月25日","name":"李伟-勺子","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","is_staff":0},{"user_id":"39347","child_id":"38256","title":"赞了你的学习","content":"点击查看一周数据排行榜","item_id":"1295","item_type":"1","create_time":"01月25日","name":"丁豪-勺子","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","is_staff":0}]
     * code : 200
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

    public static class ListBean {
        /**
         * user_id : 38256
         * child_id : 38256
         * title : 赞了你的学习
         * content : 点击查看一周数据排行榜
         * item_id : 1295
         * item_type : 1
         * create_time : 01月25日
         * name : 李伟-勺子
         * avator : https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg
         * is_staff : 0
         */

        private String user_id;
        private String child_id;
        private String title;
        private String content;
        private String item_id;
        private String item_type;
        private String create_time;
        private String name;
        private String avator;
        private int is_staff;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getChild_id() {
            return child_id;
        }

        public void setChild_id(String child_id) {
            this.child_id = child_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public int getIs_staff() {
            return is_staff;
        }

        public void setIs_staff(int is_staff) {
            this.is_staff = is_staff;
        }
    }
}
