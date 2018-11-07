package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/23.
 */

public class ShareListEntity {

    /**
     * code : 200
     * msg : 点赞结果
     * info : {"personList":[{"id":"17334","name":"  陈森林","avator":"https://www.shaoziketang.com/application/views/head/df712402ecac9bd50ea57d0259da8362.jpg"},{"id":"34150","name":"前端测试-勺子课堂","avator":"https://www.shaoziketang.com/application/views/head/77020e44f0f56a4c9c3fa8cb9ed60afb.jpg"},{"id":"34478","name":"22","avator":"https://www.shaoziketang.com/application/views/pic/img20171218180257_100.jpg"},{"id":"38256","name":"图样","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg"},{"id":"39347","name":"","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png"},{"id":"40448","name":"明年","avator":"https://www.shaoziketang.com/application/views/pic/img20171222093534_100.jpg"},{"id":"42980","name":"","avator":"http://admin.shaoziketang.com/application/public/image/id_phone.png"}]}
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
        private String total;
        private String yunumber;
        private String new_price;


        public String getNew_price() {
            return new_price;
        }

        public void setNew_price(String new_price) {
            this.new_price = new_price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getYunumber() {
            return yunumber;
        }

        public void setYunumber(String yunumber) {
            this.yunumber = yunumber;
        }

        private List<PersonListBean> personList;

        public List<PersonListBean> getPersonList() {
            return personList;
        }

        public void setPersonList(List<PersonListBean> personList) {
            this.personList = personList;
        }

        public static class PersonListBean {
            /**
             * id : 17334
             * name :   陈森林
             * avator : https://www.shaoziketang.com/application/views/head/df712402ecac9bd50ea57d0259da8362.jpg
             */

            private String id;
            private String name;
            private String avator;
            private String brand;
            private int select;

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

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

            public String getAvator() {
                return avator;
            }

            public void setAvator(String avator) {
                this.avator = avator;
            }
        }
    }
}
