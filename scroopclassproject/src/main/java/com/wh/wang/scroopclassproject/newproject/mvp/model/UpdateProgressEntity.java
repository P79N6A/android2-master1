package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by Administrator on 2017/12/19.
 */

public class UpdateProgressEntity {

    /**
     * code : 200
     * msg : 更新成功
     * info : {"sign":"13458233341b2a59f1e3970b4daa5c45","shijian":"20171219143131","video_duration":"2000","video_file_id":"6617","player_time":"0","app":"4","user_id":"39347","video_id":"1157","is_login":"1","rand_str":"5a38ac0c7a0d5"}
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
         * sign : 13458233341b2a59f1e3970b4daa5c45
         * shijian : 20171219143131
         * video_duration : 2000
         * video_file_id : 6617
         * player_time : 0
         * app : 4
         * user_id : 39347
         * video_id : 1157
         * is_login : 1
         * rand_str : 5a38ac0c7a0d5
         */

        private String sign;
        private String shijian;
        private String video_duration;
        private String video_file_id;
        private String player_time;
        private String app;
        private String user_id;
        private String video_id;
        private String is_login;
        private String rand_str;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(String video_duration) {
            this.video_duration = video_duration;
        }

        public String getVideo_file_id() {
            return video_file_id;
        }

        public void setVideo_file_id(String video_file_id) {
            this.video_file_id = video_file_id;
        }

        public String getPlayer_time() {
            return player_time;
        }

        public void setPlayer_time(String player_time) {
            this.player_time = player_time;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }

        public String getRand_str() {
            return rand_str;
        }

        public void setRand_str(String rand_str) {
            this.rand_str = rand_str;
        }
    }
}
