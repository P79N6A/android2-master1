package com.wh.wang.scroopclassproject.newproject.mvp.model;

import java.util.List;

/**
 * Created by teitsuyoshi on 2018/9/27.
 */

public class EssayCommentBean {
    /**
     * parentid : 13937
     * re_name :
     * nickname : xiao
     * id : 13937
     * up_user :
     * user_id : 50201
     * content : 如今消费者拥有越来越多的选择，如何让消费者优先选择自己，是当今商家探索的重要内容，除了研发独一无二的产品外研发出更多的产品覆盖更多的消费者也是目前重要的获客手段，海底捞上市后需要走的路依然很长
     * avator : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIsfaQqIJEYorIUhUtYicQEZntV5XR5LiaDU6iav91mNHAeB1njHwebViaTX99HnZqV2VRVTCxLFx7C3Q/132
     * shijian : 2018-09-27 12:10:30
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
    private List<EssayCommentBean> child;

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

    public List<EssayCommentBean> getChild() {
        return child;
    }

    public void setChild(List<EssayCommentBean> child) {
        this.child = child;
    }
}
