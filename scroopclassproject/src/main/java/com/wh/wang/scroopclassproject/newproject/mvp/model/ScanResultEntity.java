package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by teitsuyoshi on 2018/9/20.
 */

public class ScanResultEntity {


    /**
     * code : 2
     * mes : 您已签到
     * info : {"name":"周雪","tel":"15948083540","title":"打赏创造者手把手教你\u201c全活儿\u201d"}
     */

    private String code;
    private String mes;
    private InfoBean info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * name : 周雪
         * tel : 15948083540
         * title : 打赏创造者手把手教你“全活儿”
         */

        private String name;
        private String tel;
        private String title;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
