package com.wh.wang.scroopclassproject.bean;

import java.util.List;

/**
 * Created by wang on 2017/9/5.
 */

public class CouseLable {


    private List<LabelBean> label;

    public List<LabelBean> getLabel() {
        return label;
    }

    public void setLabel(List<LabelBean> label) {
        this.label = label;
    }

    public static class LabelBean {

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
