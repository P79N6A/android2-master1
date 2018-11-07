package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/5/2.
 */

public class ALJInfoEntity {

    /**
     * code : 200
     * msg : 查询成功
     * info : {"type":"0","name":null,"tel":null,"code":null,"address":"","list":[{"id":"11","pid":"111","uid":"17334","u_phone":"15575163724","u_avator":"https://www.shaoziketang.com/application/views/head/ea4145750009e6af015e3245a30e0143.jpg","u_name":"你好吗","create_time":"2018-05-02"}]}
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
         * type : 0
         * name : null
         * tel : null
         * code : null
         * address :
         * list : [{"id":"11","pid":"111","uid":"17334","u_phone":"15575163724","u_avator":"https://www.shaoziketang.com/application/views/head/ea4145750009e6af015e3245a30e0143.jpg","u_name":"你好吗","create_time":"2018-05-02"}]
         */

        private String type;
        private String name;
        private String tel;
        private String code;
        private String address;
        private String is_display_vip;
        private List<ListBean> list;

        public String getIs_display_vip() {
            return is_display_vip;
        }

        public void setIs_display_vip(String is_display_vip) {
            this.is_display_vip = is_display_vip;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 11
             * pid : 111
             * uid : 17334
             * u_phone : 15575163724
             * u_avator : https://www.shaoziketang.com/application/views/head/ea4145750009e6af015e3245a30e0143.jpg
             * u_name : 你好吗
             * create_time : 2018-05-02
             */

            private String id;
            private String pid;
            private String uid;
            private String u_phone;
            private String u_avator;
            private String u_name;
            private String create_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getU_phone() {
                return u_phone;
            }

            public void setU_phone(String u_phone) {
                this.u_phone = u_phone;
            }

            public String getU_avator() {
                return u_avator;
            }

            public void setU_avator(String u_avator) {
                this.u_avator = u_avator;
            }

            public String getU_name() {
                return u_name;
            }

            public void setU_name(String u_name) {
                this.u_name = u_name;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
