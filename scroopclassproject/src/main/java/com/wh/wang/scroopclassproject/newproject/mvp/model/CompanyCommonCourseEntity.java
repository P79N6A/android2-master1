package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/23.
 */

public class CompanyCommonCourseEntity {

    /**
     * code : 200
     * msg : 查询成功
     * info : {"finish":[{"player_time":32,"video_id":"1322","user_id":"38256","last_play_time":"2018-01-18 13:48:56","video_duration":"2417","name":"图样","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","zhan":"1","zhansum":1,"percent":"100%"},{"player_time":42,"video_id":"1322","user_id":"39347","last_play_time":"2018-01-04 17:57:09","video_duration":"2560","name":"","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","zhan":"1","zhansum":1,"percent":"100%"},{"id":"17334","name":"  陈森林","avator":"https://www.shaoziketang.com/application/views/head/df712402ecac9bd50ea57d0259da8362.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"17334"},{"id":"34150","name":"前端测试-勺子课堂","avator":"https://www.shaoziketang.com/application/views/head/77020e44f0f56a4c9c3fa8cb9ed60afb.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"34150"},{"id":"34478","name":"22","avator":"https://www.shaoziketang.com/application/views/pic/img20171218180257_100.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"34478"},{"id":"40448","name":"明年","avator":"https://www.shaoziketang.com/application/views/pic/img20171222093534_100.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"40448"}],"nofinish":[],"title":"1年从0做到8000万，我是如何玩转外卖平台的？","video_length":"00:0","total_time":12,"total_person":6}
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
        /**
         * finish : [{"player_time":32,"video_id":"1322","user_id":"38256","last_play_time":"2018-01-18 13:48:56","video_duration":"2417","name":"图样","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","zhan":"1","zhansum":1,"percent":"100%"},{"player_time":42,"video_id":"1322","user_id":"39347","last_play_time":"2018-01-04 17:57:09","video_duration":"2560","name":"","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","zhan":"1","zhansum":1,"percent":"100%"},{"id":"17334","name":"  陈森林","avator":"https://www.shaoziketang.com/application/views/head/df712402ecac9bd50ea57d0259da8362.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"17334"},{"id":"34150","name":"前端测试-勺子课堂","avator":"https://www.shaoziketang.com/application/views/head/77020e44f0f56a4c9c3fa8cb9ed60afb.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"34150"},{"id":"34478","name":"22","avator":"https://www.shaoziketang.com/application/views/pic/img20171218180257_100.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"34478"},{"id":"40448","name":"明年","avator":"https://www.shaoziketang.com/application/views/pic/img20171222093534_100.jpg","zhan":"0","zhansum":1,"player_time":0,"percent":"0%","user_id":"40448"}]
         * nofinish : []
         * title : 1年从0做到8000万，我是如何玩转外卖平台的？
         * video_length : 00:0
         * total_time : 12
         * total_person : 6
         */

        private String title;
        private String video_length;
        private String total_time;
        private String total_person;
        private List<FinishBean> finish;
        private List<?> nofinish;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideo_length() {
            return video_length;
        }

        public void setVideo_length(String video_length) {
            this.video_length = video_length;
        }

        public String getTotal_time() {
            return total_time;
        }

        public void setTotal_time(String total_time) {
            this.total_time = total_time;
        }

        public String getTotal_person() {
            return total_person;
        }

        public void setTotal_person(String total_person) {
            this.total_person = total_person;
        }

        public List<FinishBean> getFinish() {
            return finish;
        }

        public void setFinish(List<FinishBean> finish) {
            this.finish = finish;
        }

        public List<?> getNofinish() {
            return nofinish;
        }

        public void setNofinish(List<?> nofinish) {
            this.nofinish = nofinish;
        }

        public static class FinishBean {
            /**
             * player_time : 32
             * video_id : 1322
             * user_id : 38256
             * last_play_time : 2018-01-18 13:48:56
             * video_duration : 2417
             * name : 图样
             * avator : https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg
             * zhan : 1
             * zhansum : 1
             * percent : 100%
             * id : 17334
             */

            private int player_time;
            private String video_id;
            private String user_id;
            private String last_play_time;
            private String video_duration;
            private String name;
            private String avator;
            private String zhan;
            private String zhansum;
            private String percent;
            private String id;

            public int getPlayer_time() {
                return player_time;
            }

            public void setPlayer_time(int player_time) {
                this.player_time = player_time;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getLast_play_time() {
                return last_play_time;
            }

            public void setLast_play_time(String last_play_time) {
                this.last_play_time = last_play_time;
            }

            public String getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(String video_duration) {
                this.video_duration = video_duration;
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

            public String getZhan() {
                return zhan;
            }

            public void setZhan(String zhan) {
                this.zhan = zhan;
            }

            public String getZhansum() {
                return zhansum;
            }

            public void setZhansum(String zhansum) {
                this.zhansum = zhansum;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
