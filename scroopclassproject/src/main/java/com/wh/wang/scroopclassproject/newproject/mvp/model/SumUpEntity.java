package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 */

public class SumUpEntity {
    private List<InfoBean> info;
    private List<DirBean> dir;
    private List<RelBean> rel;
    private List<CommentBean> comment;
    private ExamBean exam;
    private String isCoupon;

    public String getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(String isCoupon) {
        this.isCoupon = isCoupon;
    }

    public ExamBean getExam() {
        return exam;
    }

    public void setExam(ExamBean exam) {
        this.exam = exam;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public List<DirBean> getDir() {
        return dir;
    }

    public void setDir(List<DirBean> dir) {
        this.dir = dir;
    }

    public List<RelBean> getRel() {
        return rel;
    }

    public void setRel(List<RelBean> rel) {
        this.rel = rel;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class InfoBean {
        /**
         * id : 1295
         * title : 491张振强
         * sub_title : 1sdsdsasdasda
         * old_price : 0.00
         * new_price : 0.02
         * vip_price : 0.00
         * img : https://img.shaoziketang.com/img20171219140251_100.h.png
         * learn : 219
         * relative_course : 1300,1301,1302,1303,1304,1305,
         * student : 我是一个好人，来吧
         * good : 97%
         * ppt : http://admin.shaoziketang.com/ceshi/vendor/aliyuncs/oss-sdk-php/samples/ppt/ppt20171108105836_100.xlsx
         * head : http://sz.wimg.cc/p/4/o/w.m.jpg
         * name : 曹新跃
         * duan : 井格老灶火锅开发总监
         * parentcourse : 1319
         * item : {"id":"1319","title":"餐厅用工8大深坑，你知道多少？","new_price":"0.00","vip_price":"0.00","img":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171211153335_100.h.jpg","type":"3"}
         * share_url : http://www.shaoziketang.com/course/detail/1295
         * pay_status : 2
         * collect_status :
         * vip_id : 997
         * rand_str : 5a39c0d42db68
         * ios_integral : 0.00
         * is_vip : 0
         * is_login : 1
         */

        private String id;
        private String title;
        private String sub_title;
        private String old_price;
        private String new_price;
        private String vip_price;
        private String img;
        private String learn;
        private String relative_course;
        private String student;
        private String good;
        private String ppt;
        private String head;
        private String name;
        private String duan;
        private String parentcourse;
        private ItemBean item;
        private String share_url;
        private String pay_status;
        private String collect_status;
        private int vip_id;
        private String rand_str;
        private String ios_integral;
        private int is_vip;
        private String is_login;
        private String publish_shijian;
        private String now_time;
        private String is_buy_company;
        private String is_company;
        private String total;
        private String yunumber;
        private String person;
        private String cate_id;
        private String ex_click;
        private int cate_player;

        public int getCate_player() {
            return cate_player;
        }

        public void setCate_player(int cate_player) {
            this.cate_player = cate_player;
        }

        public String getEx_click() {
            return ex_click;
        }

        public void setEx_click(String ex_click) {
            this.ex_click = ex_click;
        }

        private String study_achieve_url;

        public String getStudy_achieve_url() {
            return study_achieve_url;
        }

        public void setStudy_achieve_url(String study_achieve_url) {
            this.study_achieve_url = study_achieve_url;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
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

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getNew_price() {
            return new_price;
        }

        public void setNew_price(String new_price) {
            this.new_price = new_price;
        }

        public String getVip_price() {
            return vip_price;
        }

        public void setVip_price(String vip_price) {
            this.vip_price = vip_price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLearn() {
            return learn;
        }

        public void setLearn(String learn) {
            this.learn = learn;
        }

        public String getRelative_course() {
            return relative_course;
        }

        public void setRelative_course(String relative_course) {
            this.relative_course = relative_course;
        }

        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getPpt() {
            return ppt;
        }

        public void setPpt(String ppt) {
            this.ppt = ppt;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDuan() {
            return duan;
        }

        public void setDuan(String duan) {
            this.duan = duan;
        }

        public String getParentcourse() {
            return parentcourse;
        }

        public void setParentcourse(String parentcourse) {
            this.parentcourse = parentcourse;
        }

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public int getVip_id() {
            return vip_id;
        }

        public void setVip_id(int vip_id) {
            this.vip_id = vip_id;
        }

        public String getRand_str() {
            return rand_str;
        }

        public void setRand_str(String rand_str) {
            this.rand_str = rand_str;
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

        public String getIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }

        public static class ItemBean {
            /**
             * id : 1319
             * title : 餐厅用工8大深坑，你知道多少？
             * new_price : 0.00
             * vip_price : 0.00
             * img : http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171211153335_100.h.jpg
             * type : 3
             */

            private String id;
            private String title;
            private String new_price;
            private String vip_price;
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

            public String getNew_price() {
                return new_price;
            }

            public void setNew_price(String new_price) {
                this.new_price = new_price;
            }

            public String getVip_price() {
                return vip_price;
            }

            public void setVip_price(String vip_price) {
                this.vip_price = vip_price;
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
    }

    public static class DirBean {
        /**
         * id : 6668
         * video_id : 1295
         * video_title : 2--知识
         * create_time : 2017-12-19 17:42:08
         * url : http://u.wimg.cc/20171115134705.mp4?OSSAccessKeyId=LTAITJNMFGO3ZU7e&Expires=1513741779&Signature=QqcfsdrBT1ZA%2BEUUJx9yDRL6Ewc%3D
         * video_sort : 1
         * video_file_title : 5、开店前，你还需要调研的项目（11月15日）
         * length : 731
         * download_url : 20171115134705.mp4
         */

        private String id;
        private String video_id;
        private String video_title;
        private String create_time;
        private String url;
        private String video_sort;
        private String video_file_title;
        private String length;
        private String download_url;
        private String zuoye_id;
        private String is_live;
        private String live_status;
        private String live_room;
        private String live_pass;
        private String ifnew;
        private String canshow;

        public String getCanshow() {
            return canshow;
        }

        public void setCanshow(String canshow) {
            this.canshow = canshow;
        }

        private int player_time;

        public int getPlaytime() {
            return player_time;
        }

        public void setPlaytime(int playtime) {
            this.player_time = playtime;
        }

        public String getIfnew() {
            return ifnew;
        }

        public void setIfnew(String ifnew) {
            this.ifnew = ifnew;
        }

        public String getIs_live() {
            return is_live;
        }

        public void setIs_live(String is_live) {
            this.is_live = is_live;
        }

        public String getLive_status() {
            return live_status;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }

        public String getLive_room() {
            return live_room;
        }

        public void setLive_room(String live_room) {
            this.live_room = live_room;
        }

        public String getLive_pass() {
            return live_pass;
        }

        public void setLive_pass(String live_pass) {
            this.live_pass = live_pass;
        }

        public String getZuoye_id() {
            return zuoye_id;
        }

        public void setZuoye_id(String zuoye_id) {
            this.zuoye_id = zuoye_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideo_sort() {
            return video_sort;
        }

        public void setVideo_sort(String video_sort) {
            this.video_sort = video_sort;
        }

        public String getVideo_file_title() {
            return video_file_title;
        }

        public void setVideo_file_title(String video_file_title) {
            this.video_file_title = video_file_title;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }
    }

    public static class RelBean {
        /**
         * id : 1305
         * video_id : 0
         * img : http://shaozi-new.oss-cn-beijing.aliyuncs.com/img20171205142625_100.h.jpg
         * title : 1年从0做到8000万，我是如何玩转外卖平台的？
         * total_learn_nums : 1943
         * name : 曾姝骞
         */

        private String id;
        private String video_id;
        private String img;
        private String title;
        private String total_learn_nums;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CommentBean {
        /**
         * parentid : 1765
         * re_name :
         * nickname : yummy
         * id : 1765
         * up_user :
         * user_id : 27758
         * content : 怎么每个小节都要购买
         * avator : http://sz.wimg.cc/p/b/9/g.m.jpg
         * shijian : 2017-10-04 10:52:41
         * child : []
         */

        private String parentid;
        private String re_name;
        private String nickname;
        private String id;
        private String up_user;
        private String user_id;
        private String content;
        private String avator;
        private String shijian;
        private List<ReplyBean> child;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public List<ReplyBean> getChild() {
            return child;
        }

        public void setChild(List<ReplyBean> child) {
            this.child = child;
        }

        public static class ReplyBean {

            /**
             * avator : http://www.shaoziketang.com/application/views/head/d45708c7b712b8b3c1d62a5b2413f3c1.jpg
             * content : 呃呃，是的。老师讲课的思路很清晰，内容很充实，非常实用。从不同的方面，不同的角度，去讲课。确实学到了不少关于招聘的内容。欢迎大家来勺子课堂学习。
             * id : 2058
             * nickname : 你好我是森林
             * parentid : 2054
             * re_name : kk测试
             * shijian : 2017-11-02 09:32:49
             * up_user :
             * user_id : 17334
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
        }
    }

    public static class ExamBean{
        private String ifkaoshi;
        private String fen;
        private String is_pass;
        private String is_join;
        private String exam_url;

        public String getExam_url() {
            return exam_url;
        }

        public void setExam_url(String exam_url) {
            this.exam_url = exam_url;
        }

        public String getIfkaoshi() {
            return ifkaoshi;
        }

        public void setIfkaoshi(String ifkaoshi) {
            this.ifkaoshi = ifkaoshi;
        }

        public String getFen() {
            return fen;
        }

        public void setFen(String fen) {
            this.fen = fen;
        }

        public String getIs_pass() {
            return is_pass;
        }

        public void setIs_pass(String is_pass) {
            this.is_pass = is_pass;
        }

        public String getIs_join() {
            return is_join;
        }

        public void setIs_join(String is_join) {
            this.is_join = is_join;
        }
    }
}
