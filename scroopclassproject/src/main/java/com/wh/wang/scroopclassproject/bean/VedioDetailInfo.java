package com.wh.wang.scroopclassproject.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/9/7.
 */

public class VedioDetailInfo implements Serializable {
    private List<DirBean> dir;
    private List<InfoBean> info;
    private List<RelBean> rel;
    private List<CommentBean> comment;

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public List<DirBean> getDir() {
        return dir;
    }

    public void setDir(List<DirBean> dir) {
        this.dir = dir;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public List<RelBean> getRel() {
        return rel;
    }

    public void setRel(List<RelBean> rel) {
        this.rel = rel;
    }

    public static class DirBean implements Serializable {
        /**
         * create_time : 2017-08-27 23:14:49
         * id : 70
         * url : http://shaozi-video.oss-cn-beijing.aliyuncs.com/9/l/9.mp4
         * video_file_title : 1-1 “餐饮三要素”(经营导向)
         * video_id : 158
         * video_sort : 0
         * video_title : “餐饮三要素”(经营导向)
         */

        private String create_time;
        private String id;
        private String url;
        private String video_file_title;
        private String video_id;
        private String video_sort;
        private String video_title;
        private int length;
        private String download_url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideo_file_title() {
            return video_file_title;
        }

        public void setVideo_file_title(String video_file_title) {
            this.video_file_title = video_file_title;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getVideo_sort() {
            return video_sort;
        }

        public void setVideo_sort(String video_sort) {
            this.video_sort = video_sort;
        }

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }
    }

    public static class InfoBean implements Serializable {
        /**
         * id : 158
         * img : http://sz.wimg.cc/p/3/s/p.l.png
         * learn : 0
         * name : 蒋毅
         * new_price : 0.0
         * old_price : 0
         * pay_status : 2
         * relative_course :
         * title : 步步为营，教你从无到有开餐厅
         * vip_price : 0
         */

        private String collect_status;
        private String id;
        private String img;
        private String ios_integral;
        private int is_vip;
        private String learn;
        private String name;
        private String new_price;
        private String old_price;
        private String pay_status;
        private String rand_str;
        private String relative_course;
        private String title;
        private String vip_id;
        private String vip_price;
        private String duan;
        private String good;
        private String head;
        private String is_login;
        private String student;
        private String sub_title;
        private String share_url;
        private String publish_shijian;
        private String now_time;
        private String is_buy_company;
        private String is_company;
        private String total;
        private String yunumber;
        private String person;

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
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

        public String getIs_buy_company() {
            return is_buy_company;
        }

        public void setIs_buy_company(String is_buy_company) {
            this.is_buy_company = is_buy_company;
        }

        public String getIs_company() {
            return is_company;
        }

        public void setIs_company(String is_company) {
            this.is_company = is_company;
        }

        public String getPublish_shijian() {
            return publish_shijian;
        }

        public void setPublish_shijian(String publish_shijian) {
            this.publish_shijian = publish_shijian;
        }

        public String getNow_time() {
            return now_time;
        }

        public void setNow_time(String now_time) {
            this.now_time = now_time;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIos_integral() {
            return ios_integral;
        }

        public void setIos_integral(String ios_integral) {
            this.ios_integral = ios_integral;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getLearn() {
            return learn;
        }

        public void setLearn(String learn) {
            this.learn = learn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNew_price() {
            return new_price;
        }

        public void setNew_price(String new_price) {
            this.new_price = new_price;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getRand_str() {
            return rand_str;
        }

        public void setRand_str(String rand_str) {
            this.rand_str = rand_str;
        }

        public String getRelative_course() {
            return relative_course;
        }

        public void setRelative_course(String relative_course) {
            this.relative_course = relative_course;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVip_id() {
            return vip_id;
        }

        public void setVip_id(String vip_id) {
            this.vip_id = vip_id;
        }

        public String getVip_price() {
            return vip_price;
        }

        public void setVip_price(String vip_price) {
            this.vip_price = vip_price;
        }


        public String getDuan() {
            return duan;
        }

        public void setDuan(String duan) {
            this.duan = duan;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }

        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }


    public static class CommentBean implements Serializable {
        /**
         * avator : http://www.shaoziketang.com/application/views/head/image97.jpg
         * child : [{"avator":"http://www.shaoziketang.com/application/views/head/d45708c7b712b8b3c1d62a5b2413f3c1.jpg","content":"呃呃，是的。老师讲课的思路很清晰，内容很充实，非常实用。从不同的方面，不同的角度，去讲课。确实学到了不少关于招聘的内容。欢迎大家来勺子课堂学习。","id":"2058","nickname":"你好我是森林","parentid":"2054","re_name":"kk测试","shijian":"2017-11-02 09:32:49","up_user":"","user_id":"17334"},{"avator":"http://www.shaoziketang.com/application/views/head/image97.jpg","content":"是的，有同感，确实学到了不少关于招聘的内容。推荐给大家，希望能够帮助到大家。","id":"2055","nickname":"kk测试","parentid":"2054","re_name":"kk测试","shijian":"2017-11-02 09:14:50","up_user":"17334,","user_id":"26091"}]
         * content : 本次课程将从招聘技巧切入，帮你“撩”到人，然后从明确招聘标准开始，到招聘效果评估，形成一个完整的闭环，让你能方便的继续长久“撩人”。让我学到了很多的知识。非常的感谢。
         * id : 2054
         * nickname : kk测试
         * parentid :
         * re_name :
         * shijian : 2017-11-02 09:13:07
         * up_user :
         * user_id : 26091
         */

        private String avator;
        private String content;
        private String id;
        private String nickname;
        private String parentid;
        private String re_name;
        private String shijian;
        private String up_user;
        private String user_id;
        private List<ReplyBean> replyList = new ArrayList<ReplyBean>();

        public CommentBean() {
        }

        public CommentBean(String avator, String content, String id, String nickname, String parentid, String re_name, String shijian, String up_user, String user_id) {
            this.avator = avator;
            this.content = content;
            this.id = id;
            this.nickname = nickname;
            this.parentid = parentid;
            this.re_name = re_name;
            this.shijian = shijian;
            this.up_user = up_user;
            this.user_id = user_id;
        }


        public CommentBean(String avator, String content, String id, String nickname, String parentid, String re_name, String shijian, String up_user, String user_id, List<ReplyBean> replyList) {
            this.avator = avator;
            this.content = content;
            this.id = id;
            this.nickname = nickname;
            this.parentid = parentid;
            this.re_name = re_name;
            this.shijian = shijian;
            this.up_user = up_user;
            this.user_id = user_id;
            this.replyList = replyList;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getRe_name() {
            return re_name;
        }

        public void setRe_name(String re_name) {
            this.re_name = re_name;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getUp_user() {
            return up_user;
        }

        public void setUp_user(String up_user) {
            this.up_user = up_user;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<ReplyBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyBean> replyList) {
            this.replyList = replyList;
        }

    }


    public static class RelBean implements Serializable {
        /**
         * id : 146
         * img : http://sz.wimg.cc/p/1/x/M.l.png
         * name : 杨楷
         * title : 餐饮品牌如何玩转新媒体!
         * total_learn_nums : 0
         * video_id : 95
         */

        private String id;
        private String img;
        private String name;
        private String title;
        private String total_learn_nums;
        private String video_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotal_learn_nums() {
            return total_learn_nums;
        }

        public void setTotal_learn_nums(String total_learn_nums) {
            this.total_learn_nums = total_learn_nums;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        @Override
        public String toString() {
            return "RelBean{" +
                    "id='" + id + '\'' +
                    ", img='" + img + '\'' +
                    ", name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    ", total_learn_nums='" + total_learn_nums + '\'' +
                    ", video_id='" + video_id + '\'' +
                    '}';
        }
    }
}
