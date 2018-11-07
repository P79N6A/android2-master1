package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chdh on 2018/1/19.
 */

public class CompanyStateEntity implements Serializable{

    /**
     * code : 200
     * msg : 查询成功
     * info : {"parent_id":"0","name":"","ifadmin":"0","ifcompany":"0","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","position":"哈哈","company_name":"123","study_time":"44114","v_res":[{"id":"1324","title":"餐厅如何设定业绩指标？","img":"https://img.shaoziketang.com/img20180111160352_100.h.png","type":"3"},{"id":"1323","title":"合伙创业不可不知的股份划分\u201c潜规则\u201d","img":"https://img.shaoziketang.com/img20180102144258_100.h.png","type":"3"},{"id":"1322","title":"7种外卖品类全面解析，这些策略正是你需要的","img":"https://img.shaoziketang.com/img20171226171530_100.h.jpg","type":"3"},{"id":"1321","title":"堂食做得好，外卖起不来？获客、转化、产品打造，你都做好了吗？","img":"https://img.shaoziketang.com/img20171219154550_100.h.jpg","type":"3"},{"id":"1319","title":"餐厅用工8大深坑，你知道多少？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171211153335_100.h.jpg","type":"3"},{"id":"1309","title":"如何用最少的钱让排名进入外卖平台首页？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206120808_100.h.png","type":"3"},{"id":"1310","title":"美团大众点评引流方法解析","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206120908_100.h.jpg","type":"0"},{"id":"1311","title":"如何利用混合毛利设置菜品组合？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121048_100.h.jpg","type":"0"},{"id":"1312","title":"菜品的中心价格带定价法","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121121_100.h.jpg","type":"0"},{"id":"1313","title":"会员储值卡如何设计储值溢价","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121139_100.h.jpg","type":"0"}],"share_video":[{"id":"1305","title":"1年从0做到8000万，我是如何玩转外卖平台的？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171205142625_100.h.jpg","type":"3","share_time":"2018-01-20","num_per":"40%","un_rank":"1"},{"id":"1308","title":"餐厅危机管理必备手册","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171205142534_100.h.jpg","type":"3","share_time":"2018-01-20","num_per":"0%","un_rank":"1"},{"id":"1317","title":"外卖店最高效的动线设计怎么做？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121341_100.h.jpg","type":"0","share_time":"2018-01-19","num_per":"0%","un_rank":"1"},{"id":"1316","title":"如何进行门店训练安排","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121307_100.h.jpg","type":"0","share_time":"2018-01-18","num_per":"0%","un_rank":"1"},{"id":"1315","title":"你的餐厅为什么招不到人？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121249_100.h.jpg","type":"0","share_time":"2018-01-17","num_per":"0%","un_rank":"1"}]}
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

    public static class InfoBean  implements Serializable{
        /**
         * parent_id : 0
         * name :
         * ifadmin : 0
         * ifcompany : 0
         * avator : https://www.shaoziketang.com/application/views/head/20180117134848.png
         * position : 哈哈
         * company_name : 123
         * study_time : 44114
         * v_res : [{"id":"1324","title":"餐厅如何设定业绩指标？","img":"https://img.shaoziketang.com/img20180111160352_100.h.png","type":"3"},{"id":"1323","title":"合伙创业不可不知的股份划分\u201c潜规则\u201d","img":"https://img.shaoziketang.com/img20180102144258_100.h.png","type":"3"},{"id":"1322","title":"7种外卖品类全面解析，这些策略正是你需要的","img":"https://img.shaoziketang.com/img20171226171530_100.h.jpg","type":"3"},{"id":"1321","title":"堂食做得好，外卖起不来？获客、转化、产品打造，你都做好了吗？","img":"https://img.shaoziketang.com/img20171219154550_100.h.jpg","type":"3"},{"id":"1319","title":"餐厅用工8大深坑，你知道多少？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171211153335_100.h.jpg","type":"3"},{"id":"1309","title":"如何用最少的钱让排名进入外卖平台首页？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206120808_100.h.png","type":"3"},{"id":"1310","title":"美团大众点评引流方法解析","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206120908_100.h.jpg","type":"0"},{"id":"1311","title":"如何利用混合毛利设置菜品组合？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121048_100.h.jpg","type":"0"},{"id":"1312","title":"菜品的中心价格带定价法","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121121_100.h.jpg","type":"0"},{"id":"1313","title":"会员储值卡如何设计储值溢价","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121139_100.h.jpg","type":"0"}]
         * share_video : [{"id":"1305","title":"1年从0做到8000万，我是如何玩转外卖平台的？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171205142625_100.h.jpg","type":"3","share_time":"2018-01-20","num_per":"40%","un_rank":"1"},{"id":"1308","title":"餐厅危机管理必备手册","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171205142534_100.h.jpg","type":"3","share_time":"2018-01-20","num_per":"0%","un_rank":"1"},{"id":"1317","title":"外卖店最高效的动线设计怎么做？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121341_100.h.jpg","type":"0","share_time":"2018-01-19","num_per":"0%","un_rank":"1"},{"id":"1316","title":"如何进行门店训练安排","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121307_100.h.jpg","type":"0","share_time":"2018-01-18","num_per":"0%","un_rank":"1"},{"id":"1315","title":"你的餐厅为什么招不到人？","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171206121249_100.h.jpg","type":"0","share_time":"2018-01-17","num_per":"0%","un_rank":"1"}]
         */

        private String parent_id;
        private String name;
        private String ifadmin;
        private String ifcompany;
        private String avator;
        private String position;
        private String company_name;
        private String study_time;
        private String study_number;
        private String yuannumber;
        private String wuli_id;
        private List<VResBean> v_res;
        private List<ShareVideoBean> share_video;

        public String getWuli_id() {
            return wuli_id;
        }

        public void setWuli_id(String wuli_id) {
            this.wuli_id = wuli_id;
        }

        public String getYuannumber() {
            return yuannumber;
        }

        public void setYuannumber(String yuannumber) {
            this.yuannumber = yuannumber;
        }

        public String getStudy_number() {
            return study_number;
        }

        public void setStudy_number(String study_number) {
            this.study_number = study_number;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIfadmin() {
            return ifadmin;
        }

        public void setIfadmin(String ifadmin) {
            this.ifadmin = ifadmin;
        }

        public String getIfcompany() {
            return ifcompany;
        }

        public void setIfcompany(String ifcompany) {
            this.ifcompany = ifcompany;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getStudy_time() {
            return study_time;
        }

        public void setStudy_time(String study_time) {
            this.study_time = study_time;
        }

        public List<VResBean> getV_res() {
            return v_res;
        }

        public void setV_res(List<VResBean> v_res) {
            this.v_res = v_res;
        }

        public List<ShareVideoBean> getShare_video() {
            return share_video;
        }

        public void setShare_video(List<ShareVideoBean> share_video) {
            this.share_video = share_video;
        }

        public static class VResBean  implements Serializable{
            /**
             * id : 1324
             * title : 餐厅如何设定业绩指标？
             * img : https://img.shaoziketang.com/img20180111160352_100.h.png
             * type : 3
             */

            private String id;
            private String title;
            private String img;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ShareVideoBean  implements Serializable{
            /**
             * id : 1305
             * title : 1年从0做到8000万，我是如何玩转外卖平台的？
             * img : http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171205142625_100.h.jpg
             * type : 3
             * share_time : 2018-01-20
             * num_per : 40%
             * un_rank : 1
             */

            private String id;
            private String title;
            private String img;
            private String type;
            private String share_time;
            private String num_per;
            private String un_rank;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getShare_time() {
                return share_time;
            }

            public void setShare_time(String share_time) {
                this.share_time = share_time;
            }

            public String getNum_per() {
                return num_per;
            }

            public void setNum_per(String num_per) {
                this.num_per = num_per;
            }

            public String getUn_rank() {
                return un_rank;
            }

            public void setUn_rank(String un_rank) {
                this.un_rank = un_rank;
            }
        }
    }
}
