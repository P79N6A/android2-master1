package com.wh.wang.scroopclassproject.newproject.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by teitsuyoshi on 2018/10/10.
 */

public class CustomerEntity implements Parcelable{


    /**
     * code : 200
     * msg : 获取成功
     * info : {"user_id":"39347","user_name":"one","name":"张三2","phone":"18037582562","avator":"http://thirdwx.qlogo.cn/mmopen/vi_32/HZ9UeezIaJlpYmDlYc2mKYsnHeAoQ4mJP8QhgibRJtQAR3XV00OuER7HyOqFfYdNTO6ib6psccGhAbIhhvm","working_interval":"周一至周五","wechat_number":"Fox Love Life","qr_code":"http://thirdwx.qlogo.cn/mmopen/vi_32/HZ9UeezIaJlpYmDlYc2mKYsnHeAoQ4mJP8QhgibRJtQAR3XV00OuER7HyOqFfYdNTO6ib6psccGhAbIhhvm","isplay":2}
     */

    private String code;
    private String msg;
    private InfoBean info;

    protected CustomerEntity(Parcel in) {
        code = in.readString();
        msg = in.readString();
    }

    public static final Creator<CustomerEntity> CREATOR = new Creator<CustomerEntity>() {
        @Override
        public CustomerEntity createFromParcel(Parcel in) {
            return new CustomerEntity(in);
        }

        @Override
        public CustomerEntity[] newArray(int size) {
            return new CustomerEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(msg);
    }

    public static class InfoBean implements Parcelable{
        /**
         * user_id : 39347
         * user_name : one
         * name : 张三2
         * phone : 18037582562
         * avator : http://thirdwx.qlogo.cn/mmopen/vi_32/HZ9UeezIaJlpYmDlYc2mKYsnHeAoQ4mJP8QhgibRJtQAR3XV00OuER7HyOqFfYdNTO6ib6psccGhAbIhhvm
         * working_interval : 周一至周五
         * wechat_number : Fox Love Life
         * qr_code : http://thirdwx.qlogo.cn/mmopen/vi_32/HZ9UeezIaJlpYmDlYc2mKYsnHeAoQ4mJP8QhgibRJtQAR3XV00OuER7HyOqFfYdNTO6ib6psccGhAbIhhvm
         * isplay : 2
         */

        private String user_id;
        private String user_name;
        private String name;
        private String phone;
        private String avator;
        private String working_interval;
        private String wechat_number;
        private String qr_code;
        private String isplay;
        private String isvip;
        private String customer_id;


        protected InfoBean(Parcel in) {
            user_id = in.readString();
            user_name = in.readString();
            name = in.readString();
            phone = in.readString();
            avator = in.readString();
            working_interval = in.readString();
            wechat_number = in.readString();
            qr_code = in.readString();
            isplay = in.readString();
            isvip = in.readString();
            customer_id = in.readString();
        }

        public static final Creator<InfoBean> CREATOR = new Creator<InfoBean>() {
            @Override
            public InfoBean createFromParcel(Parcel in) {
                return new InfoBean(in);
            }

            @Override
            public InfoBean[] newArray(int size) {
                return new InfoBean[size];
            }
        };

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getWorking_interval() {
            return working_interval;
        }

        public void setWorking_interval(String working_interval) {
            this.working_interval = working_interval;
        }

        public String getWechat_number() {
            return wechat_number;
        }

        public void setWechat_number(String wechat_number) {
            this.wechat_number = wechat_number;
        }

        public String getQr_code() {
            return qr_code;
        }

        public void setQr_code(String qr_code) {
            this.qr_code = qr_code;
        }

        public String getIsplay() {
            return isplay;
        }

        public void setIsplay(String isplay) {
            this.isplay = isplay;
        }

        public String getIsvip() {
            return isvip;
        }

        public void setIsvip(String isvip) {
            this.isvip = isvip;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(user_id);
            dest.writeString(user_name);
            dest.writeString(name);
            dest.writeString(phone);
            dest.writeString(avator);
            dest.writeString(working_interval);
            dest.writeString(wechat_number);
            dest.writeString(qr_code);
            dest.writeString(isplay);
            dest.writeString(isvip);
            dest.writeString(customer_id);
        }
    }
}
