package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/3.
 */

public class MoreEssayEntity {
    /**
     * list : [{"id":"1737","title":"餐饮管理知识必备：看\u201c商户合伙人\u201d如何支招！","img":"https://img.shaoziketang.com/img20180803141328_100.h.jpg","sub_title":"在互联网经济诞生前，面对\u201c餐饮\u201d这块大饼，该如何优化经验模式","learn":"4","publish_shijian":"2018-08-03 00:00:00","type":"2","sel_type":"3"},{"id":"1736","title":"如何经营好一家餐饮店，保护商标是个关键！","img":"https://img.shaoziketang.com/img20180803140557_100.h.jpg","sub_title":"漫长的商标之路，如何堤防半路杀出程咬金？","learn":"3","publish_shijian":"2018-08-03 00:00:00","type":"2","sel_type":"3"},{"id":"1728","title":"餐饮又被跨界：滴滴联手COSTA开出两家300平\u201c车载\u201d网红咖啡馆\u2026","img":"https://img.shaoziketang.com/img20180801145615_100.h.jpg","sub_title":"【滴滴出行】携【礼橙专车】联合【xCOSTA COFFEE】，在北京&上海开了两家300平的网红\u201c车载\u201d咖啡馆，吸引了不少人过来打卡！","learn":"15","publish_shijian":"2018-08-01 00:00:00","type":"2","sel_type":"3"},{"id":"1724","title":"未来10年要在中国开1500家店，这个加拿大咖啡品牌有何来头？","img":"https://img.shaoziketang.com/img20180801114648_100.h.jpg","sub_title":"全球咖啡市场规模预计到2020年规模有望达3000亿元，到2025年或超10000亿元。","learn":"18","publish_shijian":"2018-08-01 00:00:00","type":"2","sel_type":"3"},{"id":"1721","title":"2018年6月美国餐饮劳工数据：工资反弹？","img":"https://img.shaoziketang.com/img20180731141040_100.h.jpg","sub_title":"美国餐饮服务场所的就业增长速度超过了整体经济...","learn":"4","publish_shijian":"2018-07-31 00:00:00","type":"2","sel_type":"3"},{"id":"1719","title":"月增20家直营店，收购武汉永和，安徽快餐大哥\u201c老乡鸡\u201d如何向全国扩张？","img":"https://img.shaoziketang.com/img20180731135927_100.h.jpg","sub_title":"老乡鸡全国门店数已达560家...","learn":"5","publish_shijian":"2018-07-31 00:00:00","type":"2","sel_type":"3"},{"id":"1716","title":"解读老牌餐饮品牌九毛九\u2026.","img":"https://img.shaoziketang.com/img20180731120101_100.h.jpg","sub_title":"再读九毛九：创立23年，142家门店\u2026","learn":"25","publish_shijian":"2018-07-31 00:00:00","type":"2","sel_type":"3"},{"id":"1712","title":"饿了么未来餐厅到底是什么样？","img":"https://img.shaoziketang.com/img20180730181033_100.h.jpg","sub_title":"外卖逐渐工业化...","learn":"7","publish_shijian":"2018-07-30 00:00:00","type":"2","sel_type":"3"},{"id":"1702","title":"网红度假区的餐饮生意该如何做？","img":"https://img.shaoziketang.com/img20180727142351_100.h.jpg","sub_title":"人群在哪里店就在哪里...","learn":"9","publish_shijian":"2018-07-27 00:00:00","type":"2","sel_type":"3"},{"id":"1698","title":"餐饮管理方法论：\u201c九常\u201d管理法","img":"https://img.shaoziketang.com/img20180727101256_100.h.jpg","sub_title":"餐饮营销...","learn":"14","publish_shijian":"2018-07-27 00:00:00","type":"2","sel_type":"3"}]
     * count : 1073
     * page : 0
     * type : 1
     * maxpage : 108
     */

    private String count;
    private int page;
    private int type;
    private int maxpage;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaxpage() {
        return maxpage;
    }

    public void setMaxpage(int maxpage) {
        this.maxpage = maxpage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1737
         * title : 餐饮管理知识必备：看“商户合伙人”如何支招！
         * img : https://img.shaoziketang.com/img20180803141328_100.h.jpg
         * sub_title : 在互联网经济诞生前，面对“餐饮”这块大饼，该如何优化经验模式
         * learn : 4
         * publish_shijian : 2018-08-03 00:00:00
         * type : 2
         * sel_type : 3
         */

        private String id;
        private String title;
        private String img;
        private String sub_title;
        private String learn;
        private String publish_shijian;
        private String type;
        private String sel_type;

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

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getLearn() {
            return learn;
        }

        public void setLearn(String learn) {
            this.learn = learn;
        }

        public String getPublish_shijian() {
            return publish_shijian;
        }

        public void setPublish_shijian(String publish_shijian) {
            this.publish_shijian = publish_shijian;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSel_type() {
            return sel_type;
        }

        public void setSel_type(String sel_type) {
            this.sel_type = sel_type;
        }
    }
}
