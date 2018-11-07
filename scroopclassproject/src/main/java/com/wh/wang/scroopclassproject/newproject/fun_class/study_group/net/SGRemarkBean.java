package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/22.
 */

public class SGRemarkBean {
    /**
     * id : 8566
     * content : 老师给我们分享的这些特别是法律上的一些细节，感觉很受用，假如没有这些学习，靠自己摸索还真是不知道要遇到多少失败，真的很有价值的课程。
     * nickname : 👻summer🎀
     * avator : 4/m/6.jpg|960|960
     * shijian : 2018-07-17 23:27:19
     * up_user : null
     * re_name : null
     * parentid : 0
     */

    private String id;
    private String content;
    private String nickname;
    private String avator;
    private String shijian;
    private String up_user;
    private String re_name;
    private String parentid;
    private String num;
    private List<SGRemarkBean> reply;
    private String user_id;
    private String thumbs_up_status;
    private String thumbs_up_num;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getThumbs_up_status() {
        return thumbs_up_status;
    }

    public void setThumbs_up_status(String thumbs_up_status) {
        this.thumbs_up_status = thumbs_up_status;
    }

    public String getThumbs_up_num() {
        return thumbs_up_num;
    }

    public void setThumbs_up_num(String thumbs_up_num) {
        this.thumbs_up_num = thumbs_up_num;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<SGRemarkBean> getReply() {
        return reply;
    }

    public void setReply(List<SGRemarkBean> reply) {
        this.reply = reply;
    }

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getUp_user() {
        return up_user;
    }

    public void setUp_user(String up_user) {
        this.up_user = up_user;
    }

    public String getRe_name() {
        return re_name;
    }

    public void setRe_name(String re_name) {
        this.re_name = re_name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}
