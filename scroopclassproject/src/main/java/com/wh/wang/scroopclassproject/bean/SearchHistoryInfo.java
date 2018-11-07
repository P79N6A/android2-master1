package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/9/12.
 */

public class SearchHistoryInfo {


    private List<HistoryBean> history;
    private List<LabelBean> label;

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public List<LabelBean> getLabel() {
        return label;
    }

    public void setLabel(List<LabelBean> label) {
        this.label = label;
    }

    public static class HistoryBean implements Serializable {
        /**
         * content : 百度电话客服撒可富来看
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class LabelBean implements Serializable {
        /**
         * id : 1
         * name : 营运力
         * parentid : 0
         */

        private String id;
        private String name;
        private String parentid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }
    }
}
