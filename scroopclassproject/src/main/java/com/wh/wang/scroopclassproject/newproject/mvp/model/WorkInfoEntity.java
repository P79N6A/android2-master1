package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chdh on 2018/3/1.
 */

public class WorkInfoEntity implements Serializable{

    /**
     * code : 200
     * msg : 查询成功
     * info : {"title":"胡 顶戴顶替 模压","teacher_name":"曹新跃","comefrom":"勺子课程","shijian":"2018-02-28 17:59:33","youxiu":[{"user_head":"http://sz.wimg.cc/p/ttp://thirdwx.m.qlogo.m.cn/mmopen/vi_32/Q0j4TwGTfTKACtQghiaGr55gfKWjqfkiaPIhqz0YSkMUViaD8iaJ4jzEPRjg8gMsdeOy2ib1O63RxE8KBTuH6LNs18Q/132"}]}
     * top : [{"id":"1","user_head":"http://sz.wimg.cc/p/ttp://thirdwx.m.qlogo.m.cn/mmopen/vi_32/Q0j4TwGTfTKACtQghiaGr55gfKWjqfkiaPIhqz0YSkMUViaD8iaJ4jzEPRjg8gMsdeOy2ib1O63RxE8KBTuH6LNs18Q/132","user_name":"测试永哥","pinglun":"这个还是不错的","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 13:00:47","img_id":[{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120437_100.h.jpg"}],"ifzhan":0},{"id":"2","user_head":"https://www.shaoziketang.com/application/views/head/20180117134848.png","user_name":"丁豪","pinglun":"张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 13:01:25","img_id":[{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120437_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120723_100.h.jpg"}],"ifzhan":0},{"id":"3","user_head":"http://sz.wimg.cc/p/http://thirdwx.m.qlogo.m.cn/mmopen/vi_32/DYAIOgq83erUeSv7oiaHqbo69BH1k0Rd27mS2qHCPJ7pKiczDcAPhZrX5azRhwH9nXNs7YTN8ibkXjQKbRGLLMckw/132","user_name":"霍澍","pinglun":"轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 13:02:07","img_id":[{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"}],"ifzhan":0},{"id":"4","user_head":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","user_name":"李伟-勺子","pinglun":"sdfasdfsafsafdasdfsadfasdfasdf","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 14:47:48","img_id":[],"ifzhan":0}]
     * all : [{"id":"2","user_head":"https://www.shaoziketang.com/application/views/head/20180117134848.png","user_name":"丁豪","pinglun":"张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 13:01:25","img_id":[{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120437_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120723_100.h.jpg"}],"ifzhan":0},{"id":"4","user_head":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","user_name":"李伟-勺子","pinglun":"sdfasdfsafsafdasdfsadfasdfasdf","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 14:47:48","img_id":[],"ifzhan":0},{"id":"3","user_head":"http://sz.wimg.cc/p/http://thirdwx.m.qlogo.m.cn/mmopen/vi_32/DYAIOgq83erUeSv7oiaHqbo69BH1k0Rd27mS2qHCPJ7pKiczDcAPhZrX5azRhwH9nXNs7YTN8ibkXjQKbRGLLMckw/132","user_name":"霍澍","pinglun":"轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂轻易勺子课堂","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 13:02:07","img_id":[{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"}],"ifzhan":0},{"id":"1","user_head":"http://sz.wimg.cc/p/ttp://thirdwx.m.qlogo.m.cn/mmopen/vi_32/Q0j4TwGTfTKACtQghiaGr55gfKWjqfkiaPIhqz0YSkMUViaD8iaJ4jzEPRjg8gMsdeOy2ib1O63RxE8KBTuH6LNs18Q/132","user_name":"测试永哥","pinglun":"这个还是不错的","zhan_sum":"0","comment_sum":"0","shijian":"2018-03-01 13:00:47","img_id":[{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120437_100.h.jpg"}],"ifzhan":0}]
     */

    private int code;
    private String msg;
    private InfoBean info;
    private List<TopBean> top;
    private List<AllBean> all;

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

    public List<TopBean> getTop() {
        return top;
    }

    public void setTop(List<TopBean> top) {
        this.top = top;
    }

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class InfoBean {
        /**
         * title : 胡 顶戴顶替 模压
         * teacher_name : 曹新跃
         * comefrom : 勺子课程
         * shijian : 2018-02-28 17:59:33
         * youxiu : [{"user_head":"http://sz.wimg.cc/p/ttp://thirdwx.m.qlogo.m.cn/mmopen/vi_32/Q0j4TwGTfTKACtQghiaGr55gfKWjqfkiaPIhqz0YSkMUViaD8iaJ4jzEPRjg8gMsdeOy2ib1O63RxE8KBTuH6LNs18Q/132"}]
         */

        private String title;
        private String teacher_name;
        private String comefrom;
        private String shijian;
        private String content;
        private String id;
        private List<YouxiuBean> youxiu;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getComefrom() {
            return comefrom;
        }

        public void setComefrom(String comefrom) {
            this.comefrom = comefrom;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public List<YouxiuBean> getYouxiu() {
            return youxiu;
        }

        public void setYouxiu(List<YouxiuBean> youxiu) {
            this.youxiu = youxiu;
        }

        public static class YouxiuBean {
            /**
             * id : 59
             * user_id : 38256
             * user_head : https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg
             * user_name : 图样
             * pinglun : 啦啦啦
             * zhan_sum : 0
             * comment_sum : -1
             * shijian : 2018-03-05 18:36:26
             * img_id : [{"url":"https://www.shaoziketang.com/https://www.shaoziketang.com/application/views/zuoye/bf541888137fea6414d44a6ce8540259.jpg"}]
             * ifzhan : 0
             * iftop : 1
             * ifmy : 0
             * ifrenzheng : 0
             */

            private String id;
            private String user_id;
            private String user_head;
            private String user_name;
            private String pinglun;
            private String zhan_sum;
            private String comment_sum;
            private String shijian;
            private int ifzhan;
            private int iftop;
            private int ifmy;
            private int ifrenzheng;
            private List<ImgIdBean> img_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_head() {
                return user_head;
            }

            public void setUser_head(String user_head) {
                this.user_head = user_head;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getPinglun() {
                return pinglun;
            }

            public void setPinglun(String pinglun) {
                this.pinglun = pinglun;
            }

            public String getZhan_sum() {
                return zhan_sum;
            }

            public void setZhan_sum(String zhan_sum) {
                this.zhan_sum = zhan_sum;
            }

            public String getComment_sum() {
                return comment_sum;
            }

            public void setComment_sum(String comment_sum) {
                this.comment_sum = comment_sum;
            }

            public String getShijian() {
                return shijian;
            }

            public void setShijian(String shijian) {
                this.shijian = shijian;
            }

            public int getIfzhan() {
                return ifzhan;
            }

            public void setIfzhan(int ifzhan) {
                this.ifzhan = ifzhan;
            }

            public int getIftop() {
                return iftop;
            }

            public void setIftop(int iftop) {
                this.iftop = iftop;
            }

            public int getIfmy() {
                return ifmy;
            }

            public void setIfmy(int ifmy) {
                this.ifmy = ifmy;
            }

            public int getIfrenzheng() {
                return ifrenzheng;
            }

            public void setIfrenzheng(int ifrenzheng) {
                this.ifrenzheng = ifrenzheng;
            }

            public List<ImgIdBean> getImg_id() {
                return img_id;
            }

            public void setImg_id(List<ImgIdBean> img_id) {
                this.img_id = img_id;
            }

            public static class ImgIdBean {
                /**
                 * url : https://www.shaoziketang.com/https://www.shaoziketang.com/application/views/zuoye/bf541888137fea6414d44a6ce8540259.jpg
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }

    public static class TopBean implements Serializable{
        /**
         * id : 1
         * user_head : http://sz.wimg.cc/p/ttp://thirdwx.m.qlogo.m.cn/mmopen/vi_32/Q0j4TwGTfTKACtQghiaGr55gfKWjqfkiaPIhqz0YSkMUViaD8iaJ4jzEPRjg8gMsdeOy2ib1O63RxE8KBTuH6LNs18Q/132
         * user_name : 测试永哥
         * pinglun : 这个还是不错的
         * zhan_sum : 0
         * comment_sum : 0
         * shijian : 2018-03-01 13:00:47
         * img_id : [{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120437_100.h.jpg"}]
         * ifzhan : 0
         */

        private String id;
        private String user_head;
        private String user_name;
        private String pinglun;
        private String zhan_sum;
        private String comment_sum;
        private String shijian;
        private int ifzhan;
        private List<ImgIdBean> img_id;
        private int is_youxiu;
        private int iftop;
        private int ifrenzheng;

        public int getIs_youxiu() {
            return is_youxiu;
        }

        public void setIs_youxiu(int is_youxiu) {
            this.is_youxiu = is_youxiu;
        }

        public int getIftop() {
            return iftop;
        }

        public void setIftop(int iftop) {
            this.iftop = iftop;
        }

        public int getIfrenzheng() {
            return ifrenzheng;
        }

        public void setIfrenzheng(int ifrenzheng) {
            this.ifrenzheng = ifrenzheng;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_head() {
            return user_head;
        }

        public void setUser_head(String user_head) {
            this.user_head = user_head;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPinglun() {
            return pinglun;
        }

        public void setPinglun(String pinglun) {
            this.pinglun = pinglun;
        }

        public String getZhan_sum() {
            return zhan_sum;
        }

        public void setZhan_sum(String zhan_sum) {
            this.zhan_sum = zhan_sum;
        }

        public String getComment_sum() {
            return comment_sum;
        }

        public void setComment_sum(String comment_sum) {
            this.comment_sum = comment_sum;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public int getIfzhan() {
            return ifzhan;
        }

        public void setIfzhan(int ifzhan) {
            this.ifzhan = ifzhan;
        }

        public List<ImgIdBean> getImg_id() {
            return img_id;
        }

        public void setImg_id(List<ImgIdBean> img_id) {
            this.img_id = img_id;
        }

        public static class ImgIdBean {
            /**
             * url : http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class AllBean {
        /**
         * id : 2
         * user_head : https://www.shaoziketang.com/application/views/head/20180117134848.png
         * user_name : 丁豪
         * pinglun : 张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试张振强测试
         * zhan_sum : 0
         * comment_sum : 0
         * shijian : 2018-03-01 13:01:25
         * img_id : [{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120437_100.h.jpg"},{"url":"http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120723_100.h.jpg"}]
         * ifzhan : 0
         */

        private String id;
        private String user_head;
        private String user_name;
        private String pinglun;
        private String zhan_sum;
        private String comment_sum;
        private String shijian;
        private int ifzhan;
        private int ifrenzheng;
        private int ifmy;
        private int is_youxiu;
        private List<ImgIdBeanX> img_id;

        public int getIs_youxiu() {
            return is_youxiu;
        }

        public void setIs_youxiu(int is_youxiu) {
            this.is_youxiu = is_youxiu;
        }

        public int getIfrenzheng() {
            return ifrenzheng;
        }

        public void setIfrenzheng(int ifrenzheng) {
            this.ifrenzheng = ifrenzheng;
        }

        public int getIfmy() {
            return ifmy;
        }

        public void setIfmy(int ifmy) {
            this.ifmy = ifmy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_head() {
            return user_head;
        }

        public void setUser_head(String user_head) {
            this.user_head = user_head;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPinglun() {
            return pinglun;
        }

        public void setPinglun(String pinglun) {
            this.pinglun = pinglun;
        }

        public String getZhan_sum() {
            return zhan_sum;
        }

        public void setZhan_sum(String zhan_sum) {
            this.zhan_sum = zhan_sum;
        }

        public String getComment_sum() {
            return comment_sum;
        }

        public void setComment_sum(String comment_sum) {
            this.comment_sum = comment_sum;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public int getIfzhan() {
            return ifzhan;
        }

        public void setIfzhan(int ifzhan) {
            this.ifzhan = ifzhan;
        }

        public List<ImgIdBeanX> getImg_id() {
            return img_id;
        }

        public void setImg_id(List<ImgIdBeanX> img_id) {
            this.img_id = img_id;
        }

        public static class ImgIdBeanX {
            /**
             * url : http://shaozi-new.oss-cn-beijing.aliyuncs.com/img_app20171206120344_100.h.jpg
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
