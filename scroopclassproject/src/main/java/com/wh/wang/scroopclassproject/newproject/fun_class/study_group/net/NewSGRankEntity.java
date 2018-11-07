package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/24.
 */

public class NewSGRankEntity {


    /**
     * code : 200
     * msg : 详情页数据获取成功
     * info : {"success":[{"user_avator":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKI6DiaI0tU2NGL3sJicPO93NFHuYucxR2Ngeb1Qk5r3ac1Bb0ib82rvpdicOMKIRTuGj7z8CzKzGsXxA/132","user_name":"李冰","v_time":"0","end_shijian":"2018-08-26 14:52:48","status":"2","user_id":"37737","num":"2"}],"fail":[{"user_avator":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKI6DiaI0tU2NGL3sJicPO93NFHuYucxR2Ngeb1Qk5r3ac1Bb0ib82rvpdOy6w6oDX0SuZqia9ZvgYaCQ/132","user_name":"夏目","v_time":"0","end_shijian":"2018-08-26 14:52:48","status":"2","user_id":"39347","num":"1"},{"user_avator":"http://thirdwx.qlogo.cn/mmopen/vi_32/CclwEzE8spDaict7TQ6YicKVGoAj0zUHLfflP7w9vY1gqtYegVCtH4zx4T3gCrovu7EmpXUibiaJsyrEdkGm7tsg4A/132","user_name":"勺因斯坦","v_time":"0","end_shijian":"2018-08-26 14:52:48","status":"2","user_id":"2318471","num":"1"}]}
     */

    private String code;
    private String msg;
    private InfoBean info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        private List<RankBean> success;
        private List<RankBean> fail;
        private BaseInfo finish_num;


        public BaseInfo getFinish_num() {
            return finish_num;
        }

        public void setFinish_num(BaseInfo finish_num) {
            this.finish_num = finish_num;
        }

        public List<RankBean> getSuccess() {
            return success;
        }

        public void setSuccess(List<RankBean> success) {
            this.success = success;
        }

        public List<RankBean> getFail() {
            return fail;
        }

        public void setFail(List<RankBean> fail) {
            this.fail = fail;
        }

        public static class RankBean {
            /**
             * user_avator : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKI6DiaI0tU2NGL3sJicPO93NFHuYucxR2Ngeb1Qk5r3ac1Bb0ib82rvpdicOMKIRTuGj7z8CzKzGsXxA/132
             * user_name : 李冰
             * v_time : 0
             * end_shijian : 2018-08-26 14:52:48
             * status : 2
             * user_id : 37737
             * num : 2
             */

            private String user_avator;
            private String user_name;
            private String v_time;
            private String end_shijian;
            private String status;
            private String user_id;
            private String num;
            private String whichDayOut;

            public String getWhichDayOut() {
                return whichDayOut;
            }

            public void setWhichDayOut(String whichDayOut) {
                this.whichDayOut = whichDayOut;
            }

            public String getUser_avator() {
                return user_avator;
            }

            public void setUser_avator(String user_avator) {
                this.user_avator = user_avator;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getV_time() {
                return v_time;
            }

            public void setV_time(String v_time) {
                this.v_time = v_time;
            }

            public String getEnd_shijian() {
                return end_shijian;
            }

            public void setEnd_shijian(String end_shijian) {
                this.end_shijian = end_shijian;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }


        public static class BaseInfo{

            /**
             * finish_num : 1
             * unfinish_num : 40
             */

            private int finish_num;
            private int unfinish_num;

            public int getFinish_num() {
                return finish_num;
            }

            public void setFinish_num(int finish_num) {
                this.finish_num = finish_num;
            }

            public int getUnfinish_num() {
                return unfinish_num;
            }

            public void setUnfinish_num(int unfinish_num) {
                this.unfinish_num = unfinish_num;
            }
        }
    }
}
