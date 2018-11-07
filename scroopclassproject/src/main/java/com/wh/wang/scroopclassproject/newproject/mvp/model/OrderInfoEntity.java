package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/25.
 */

public class OrderInfoEntity {

    private List<OrderBean> order;
    private List<CollectBean> collect;
    private List<StudyBean> study;
    private List<FinishBean> finish;
    private List<AllstudyBean> allstudy;

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public List<CollectBean> getCollect() {
        return collect;
    }

    public void setCollect(List<CollectBean> collect) {
        this.collect = collect;
    }

    public List<StudyBean> getStudy() {
        return study;
    }

    public void setStudy(List<StudyBean> study) {
        this.study = study;
    }

    public List<FinishBean> getFinish() {
        return finish;
    }

    public void setFinish(List<FinishBean> finish) {
        this.finish = finish;
    }

    public List<AllstudyBean> getAllstudy() {
        return allstudy;
    }

    public void setAllstudy(List<AllstudyBean> allstudy) {
        this.allstudy = allstudy;
    }

    public static class OrderBean {
        /**
         * pid : 47982
         * num : 0
         * order_money : 0.00
         * order_no :
         * create_shijian : 2018-01-19 13:35:10
         * finish_shijian : 2018-01-19 13:35:10
         * title : 餐厅如何围绕用户做好营销
         * img : https://img.shaoziketang.com/img20171206112136_100.h.jpg
         * money : 0.00
         * id : 144
         * type : 1
         * child : []
         */

        private String pid;
        private int num;
        private String order_money;
        private String order_no;
        private String create_shijian;
        private String finish_shijian;
        private String title;
        private String img;
        private String money;
        private String id;
        private String type;
        private List<?> child;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getCreate_shijian() {
            return create_shijian;
        }

        public void setCreate_shijian(String create_shijian) {
            this.create_shijian = create_shijian;
        }

        public String getFinish_shijian() {
            return finish_shijian;
        }

        public void setFinish_shijian(String finish_shijian) {
            this.finish_shijian = finish_shijian;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<?> getChild() {
            return child;
        }

        public void setChild(List<?> child) {
            this.child = child;
        }
    }

    public static class CollectBean {
        /**
         * title : 491张振强
         * img : https://img.shaoziketang.com/img20180103163948_100.h.jpg
         * money : 0.10
         * id : 1295
         * type : 1
         */

        private String title;
        private String img;
        private String money;
        private String id;
        private int type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class StudyBean {
        /**
         * title : 跳开4个思维陷阱，摆脱老套节日营销
         * img : https://img.shaoziketang.com/img20171206104510_100.h.jpg
         * per : 2%
         * id : 419
         * money : 0.00
         * type : 1
         */

        private String title;
        private String img;
        private String per;
        private String id;
        private String money;
        private int type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPer() {
            return per;
        }

        public void setPer(String per) {
            this.per = per;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class FinishBean {
        /**
         * title : 7种外卖品类全面解析，这些策略正是你需要的
         * img : https://img.shaoziketang.com/img20171226171530_100.h.jpg
         * per : 100%
         * id : 1322
         * money : 0.00
         * type : 1
         */

        private String title;
        private String img;
        private String per;
        private String id;
        private String money;
        private int type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPer() {
            return per;
        }

        public void setPer(String per) {
            this.per = per;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class AllstudyBean {
        /**
         * title : 跳开4个思维陷阱，摆脱老套节日营销
         * img : https://img.shaoziketang.com/img20171206104510_100.h.jpg
         * per : 2%
         * id : 419
         * money : 0.00
         * type : 1
         */

        private String title;
        private String img;
        private String per;
        private String id;
        private String money;
        private int type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPer() {
            return per;
        }

        public void setPer(String per) {
            this.per = per;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
