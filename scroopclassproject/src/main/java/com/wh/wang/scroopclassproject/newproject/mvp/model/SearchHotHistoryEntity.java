package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/6/29.
 */

public class SearchHotHistoryEntity {

    /**
     * guide : 外卖
     * hot : ["外卖爆单","人力资源","品牌","店长","菜单","绩效","公开课"]
     * history : [{"id":"7203","user_id":"39347","content":"外卖","create_time":"2017-12-14 09:24:07","ip":"106.120.116.198","isdel":"0"},{"id":"7151","user_id":"39347","content":"嘿嘿","create_time":"2017-12-13 14:44:27","ip":"106.120.116.198","isdel":"0"},{"id":"7145","user_id":"39347","content":"够够","create_time":"2017-12-13 13:21:43","ip":"106.120.116.198","isdel":"0"},{"id":"7134","user_id":"39347","content":"里","create_time":"2017-12-13 10:59:38","ip":"106.120.116.198","isdel":"0"},{"id":"7132","user_id":"39347","content":"哈","create_time":"2017-12-13 10:56:46","ip":"106.120.116.198","isdel":"0"}]
     */

    private String guide;
    private List<String> hot;
    private List<HistoryBean> history;

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public List<String> getHot() {
        return hot;
    }

    public void setHot(List<String> hot) {
        this.hot = hot;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public static class HistoryBean {
        /**
         * id : 7203
         * user_id : 39347
         * content : 外卖
         * create_time : 2017-12-14 09:24:07
         * ip : 106.120.116.198
         * isdel : 0
         */

        private String id;
        private String user_id;
        private String content;
        private String create_time;
        private String ip;
        private String isdel;

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getIsdel() {
            return isdel;
        }

        public void setIsdel(String isdel) {
            this.isdel = isdel;
        }
    }
}
