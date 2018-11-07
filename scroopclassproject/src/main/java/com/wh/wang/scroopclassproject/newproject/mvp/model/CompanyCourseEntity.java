package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by chdh on 2018/1/22.
 */

public class CompanyCourseEntity {

    /**
     * code : 200
     * msg : 查询成功
     * info : {"finish":[{"player_time":32,"video_id":"1322","user_id":"38256","last_play_time":"2018-01-18 13:48:56","video_duration":"2417","name":"图样","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","zhan":0,"percent":"100%"}],"nofinish":[{"player_time":4,"video_id":"1322","user_id":"39347","last_play_time":"2018-01-04 17:57:09","video_duration":"2560","name":"","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","zhan":0,"percent":"10%"},{"id":"40448","name":"明年","avator":"https://www.shaoziketang.com/application/views/pic/img20171222093534_100.jpg","zhan":0,"player_time":"0","percent":"0%","user_id":"40448"}],"total_time":12,"total_person":3}
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
         * finish : [{"player_time":32,"video_id":"1322","user_id":"38256","last_play_time":"2018-01-18 13:48:56","video_duration":"2417","name":"图样","avator":"https://www.shaoziketang.com/application/views/head/1ae7a1a6bb147bc925f6c15bf4992ef5.jpg","zhan":0,"percent":"100%"}]
         * nofinish : [{"player_time":4,"video_id":"1322","user_id":"39347","last_play_time":"2018-01-04 17:57:09","video_duration":"2560","name":"","avator":"https://www.shaoziketang.com/application/views/head/20180117134848.png","zhan":0,"percent":"10%"},{"id":"40448","name":"明年","avator":"https://www.shaoziketang.com/application/views/pic/img20171222093534_100.jpg","zhan":0,"player_time":"0","percent":"0%","user_id":"40448"}]
         * total_time : 12
         * total_person : 3
         */

        private String total_time;
        private String total_person;
        private String title;
        private String video_length;
        private List<FinishBean> finish;
        private List<NofinishBean> nofinish;
        private String tixing;

        public String getTixing() {
            return tixing;
        }

        public void setTixing(String tixing) {
            this.tixing = tixing;
        }

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

        public List<NofinishBean> getNofinish() {
            return nofinish;
        }

        public void setNofinish(List<NofinishBean> nofinish) {
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
             * zhan : 0
             * percent : 100%
             */

            private String player_time;
            private String video_id;
            private String user_id;
            private String last_play_time;
            private String video_duration;
            private String name;
            private String avator;
            private String zhan;
            private String percent;
            private String zhansum;

            public String getZhansum() {
                return zhansum;
            }

            public void setZhansum(String zhansum) {
                this.zhansum = zhansum;
            }

            public String getPlayer_time() {
                return player_time;
            }

            public void setPlayer_time(String player_time) {
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

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }
        }

        public static class NofinishBean {
            /**
             * player_time : 4
             * video_id : 1322
             * user_id : 39347
             * last_play_time : 2018-01-04 17:57:09
             * video_duration : 2560
             * name :
             * avator : https://www.shaoziketang.com/application/views/head/20180117134848.png
             * zhan : 0
             * percent : 10%
             * id : 40448
             */

            private String player_time;
            private String video_id;
            private String user_id;
            private String last_play_time;
            private String video_duration;
            private String name;
            private String avator;
            private String zhan;
            private String percent;
            private String id;
            private String tixing;

            public String getTixing() {
                return tixing;
            }

            public void setTixing(String tixing) {
                this.tixing = tixing;
            }

            public String getPlayer_time() {
                return player_time;
            }

            public void setPlayer_time(String player_time) {
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
