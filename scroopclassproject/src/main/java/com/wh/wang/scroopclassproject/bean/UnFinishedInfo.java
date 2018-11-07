package com.wh.wang.scroopclassproject.bean;

import java.util.List;

/**
 * Created by wang on 2017/8/17.
 * 我的课程中 未学习的课程
 */

public class UnFinishedInfo {


    /**
     * unFinished : [[{"percent":"74","post_id":"1181","post_type":"video","post_key":"598af5392ae1c","post_title":"员工幸福是一切餐厅经营的原点","post_image":"a/H/X.png|900|500"},{"percent":"22","post_id":"1178","post_type":"video","post_key":"598a80551116f","post_title":"巴奴杜中兵:产品主义2.0-餐饮品牌的基因","post_image":"a/G/D.png|900|500"},{"percent":"0","post_id":"771","post_type":"video","post_key":"58e5ad046e541","post_title":"菜单吸引力法则","post_image":"7/m/A.png|1920|1080"},{"percent":"5","post_id":"622","post_type":"video","post_key":"58bcef1368d0e","post_title":"招聘评估","post_image":"9/p/P.png|900|500"},{"percent":"8","post_id":"100","post_type":"video","post_key":"100","post_title":"快!准!稳!四个核心环节确保餐厅工作有效性!","post_image":"1/C/R.png|900|506"},{"percent":"44","post_id":"1157","post_type":"video","post_key":"598342e23c1b9","post_title":"升级门店运营流程，构建连锁门店竞争力","post_image":"a/F/K.png|900|500"},{"percent":"23","post_id":"1157","post_type":"video","post_key":"598342e23c1b9","post_title":"升级门店运营流程，构建连锁门店竞争力","post_image":"a/F/K.png|900|500"},{"percent":"2","post_id":"1076","post_type":"video","post_key":"5963905b5e9e8","post_title":"餐饮企业的合伙人制","post_image":"9/q/8.png|900|500"},{"percent":"1","post_id":"88","post_type":"video","post_key":"88","post_title":"培训体系的搭建","post_image":"9/q/w.png|1000|500"},{"percent":"15","post_id":"96","post_type":"video","post_key":"96","post_title":"薪酬福利与员工激励","post_image":"9/q/y.png|1000|500"},{"percent":"4","post_id":"410","post_type":"video","post_key":"5860a7dd9356b","post_title":"如何进驻购物中心","post_image":"a/7/Q.png|900|500"},{"percent":"89","post_id":"1110","post_type":"video","post_key":"596d812239378","post_title":"店长线上基础班 | 业务计划","post_image":"9/E/e.png|1920|1080"},{"percent":"96","post_id":"822","post_type":"video","post_key":"58ff2295550e3","post_title":"外卖特训营 | 掌握这4点，让顾客爱上你的外卖包装!","post_image":"7/K/X.png|1000|500"},{"percent":"15","post_id":"790","post_type":"video","post_key":"58f42a69aac4d","post_title":"会员体系搭建","post_image":"9/q/h.png|900|500"},{"percent":"3","post_id":"661","post_type":"video","post_key":"58c9fecc6b624","post_title":"勺子公开课丨突破同质化竞争，品牌升级应该这么做!","post_image":"6/G/J.jpg|960|532"},{"percent":"1","post_id":"771","post_type":"video","post_key":"58e5ad046e541","post_title":"菜单吸引力法则","post_image":"7/m/A.png|1920|1080"},{"percent":"19","post_id":"768","post_type":"video","post_key":"58e450a51ab2f","post_title":"勺子公开课 | 云味馆引爆市场的秘籍，以创新打造品牌护城河!","post_image":"7/i/S.png|900|500"}]]
     * msg : 查询成功
     * code : 200
     */

    private String msg;
    private int code;
    private List<List<UnFinishedBean>> unFinished;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<List<UnFinishedBean>> getUnFinished() {
        return unFinished;
    }

    public void setUnFinished(List<List<UnFinishedBean>> unFinished) {
        this.unFinished = unFinished;
    }

    public static class UnFinishedBean {
        /**
         * percent : 74
         * post_id : 1181
         * post_type : video
         * post_key : 598af5392ae1c
         * post_title : 员工幸福是一切餐厅经营的原点
         * post_image : a/H/X.png|900|500
         */

        private int percent;
        private int post_id;
        private String post_type;
        private String post_key;
        private String post_title;
        private String post_image;

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public String getPost_type() {
            return post_type;
        }

        public void setPost_type(String post_type) {
            this.post_type = post_type;
        }

        public String getPost_key() {
            return post_key;
        }

        public void setPost_key(String post_key) {
            this.post_key = post_key;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getPost_image() {
            return post_image;
        }

        public void setPost_image(String post_image) {
            this.post_image = post_image;
        }

        @Override
        public String toString() {
            return "UnFinishedBean{" +
                    "percent=" + percent +
                    ", post_id=" + post_id +
                    ", post_type='" + post_type + '\'' +
                    ", post_key='" + post_key + '\'' +
                    ", post_title='" + post_title + '\'' +
                    ", post_image='" + post_image + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UnFinishedInfo{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", unFinished=" + unFinished +
                '}';
    }
}
