package com.wh.wang.scroopclassproject.newproject.mvp.model;

/**
 * Created by Administrator on 2017/12/22.
 */

public class UserInfoEntity {

    /**
     * code : 200
     * info : {"mobile":"18410115623","openid":"","senior":"0","member_end":"2017-11-14","id":"39347","avator":"https://www.shaoziketang.com/application/views/head/20171212172032.png","nickname":"丁豪勺子课程","ios_integral":"0.00","rand_str":"5a3c624c5c417","area":"我去诺","brand":"诺言","position":"有你","email":"名模","sex":null,"mensum":"0","mess_num":0}
     * msg : 查询成功
     */

    private int code;
    private InfoBean info;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class InfoBean {
        /**
         * mobile : 18410115623
         * openid :
         * senior : 0
         * member_end : 2017-11-14
         * id : 39347
         * avator : https://www.shaoziketang.com/application/views/head/20171212172032.png
         * nickname : 丁豪勺子课程
         * ios_integral : 0.00
         * rand_str : 5a3c624c5c417
         * area : 我去诺
         * brand : 诺言
         * position : 有你
         * email : 名模
         * sex : null
         * mensum : 0
         * mess_num : 0
         */

        private String mobile;
        private String openid;
        private String senior;
        private String member_end;
        private String id;
        private String avator;
        private String nickname;
        private String ios_integral;
        private String rand_str;
        private String area;
        private String brand;
        private String position;
        private String email;
        private Object sex;
        private String mensum;
        private int mess_num;
        private int is_vip;
        private String ifcompany;
        private String company_endshijian;
        private String is_display_company;
        private String is_staff;
        private String is_test_company;
        private String bao;
        private String go_on;
        private String is_company_status;

        public String getIs_company_status() {
            return is_company_status;
        }

        public void setIs_company_status(String is_company_status) {
            this.is_company_status = is_company_status;
        }

        public String getBao() {
            return bao;
        }

        public void setBao(String bao) {
            this.bao = bao;
        }

        public String getGo_on() {
            return go_on;
        }

        public void setGo_on(String go_on) {
            this.go_on = go_on;
        }

        public String getIs_test_company() {
            return is_test_company;
        }

        public void setIs_test_company(String is_test_company) {
            this.is_test_company = is_test_company;
        }

        public String getIs_staff() {
            return is_staff;
        }

        public void setIs_staff(String is_staff) {
            this.is_staff = is_staff;
        }

        public String getIs_display_company() {
            return is_display_company;
        }

        public void setIs_display_company(String is_display_company) {
            this.is_display_company = is_display_company;
        }

        public String getIfcompany() {
            return ifcompany;
        }

        public void setIfcompany(String ifcompany) {
            this.ifcompany = ifcompany;
        }

        public String getCompany_endshijian() {
            return company_endshijian;
        }

        public void setCompany_endshijian(String company_endshijian) {
            this.company_endshijian = company_endshijian;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getSenior() {
            return senior;
        }

        public void setSenior(String senior) {
            this.senior = senior;
        }

        public String getMember_end() {
            return member_end;
        }

        public void setMember_end(String member_end) {
            this.member_end = member_end;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIos_integral() {
            return ios_integral;
        }

        public void setIos_integral(String ios_integral) {
            this.ios_integral = ios_integral;
        }

        public String getRand_str() {
            return rand_str;
        }

        public void setRand_str(String rand_str) {
            this.rand_str = rand_str;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public String getMensum() {
            return mensum;
        }

        public void setMensum(String mensum) {
            this.mensum = mensum;
        }

        public int getMess_num() {
            return mess_num;
        }

        public void setMess_num(int mess_num) {
            this.mess_num = mess_num;
        }
    }
}
