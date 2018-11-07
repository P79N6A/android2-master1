package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/10/16.
 */

public class MineThirdBean implements Serializable {


    /**
     * code : 200
     * list : [{"content":"消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息","create_time":"10月16日","id":"5","item_id":"1287","item_type":"3"}]
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
         * content : 消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息
         * create_time : 10月16日
         * id : 5
         * item_id : 1287
         * item_type : 3
         */

        private String content;
        private String create_time;
        private String id;
        private String item_id;
        private int item_type;
        private String title;
        private String type;

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

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public int getItem_type() {
            return item_type;
        }

        public void setItem_type(int item_type) {
            this.item_type = item_type;
        }

    }
}
