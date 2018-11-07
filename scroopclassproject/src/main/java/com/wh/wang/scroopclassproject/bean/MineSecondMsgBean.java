package com.wh.wang.scroopclassproject.bean;

/**
 * Created by wang on 2017/10/16.
 */

public class MineSecondMsgBean {


    /**
     * code : 200
     * feedback_num : 1
     * msg : 查询成功
     * new_feedback : {"content":"反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈","create_time":"10月16日","id":"29"}
     * new_notice : {"content":"消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息","create_time":"10月16日","id":"5"}
     * notice_num : 0
     */

    private int code;
    private int feedback_num;
    private String msg;
    private NewFeedbackBean new_feedback;
    private NewNoticeBean new_notice;
    private int notice_num;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getFeedback_num() {
        return feedback_num;
    }

    public void setFeedback_num(int feedback_num) {
        this.feedback_num = feedback_num;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public NewFeedbackBean getNew_feedback() {
        return new_feedback;
    }

    public void setNew_feedback(NewFeedbackBean new_feedback) {
        this.new_feedback = new_feedback;
    }

    public NewNoticeBean getNew_notice() {
        return new_notice;
    }

    public void setNew_notice(NewNoticeBean new_notice) {
        this.new_notice = new_notice;
    }

    public int getNotice_num() {
        return notice_num;
    }

    public void setNotice_num(int notice_num) {
        this.notice_num = notice_num;
    }

    public static class NewFeedbackBean {
        /**
         * content : 反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈反馈
         * create_time : 10月16日
         * id : 29
         */

        private String content;
        private String create_time;
        private String id;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class NewNoticeBean {
        /**
         * content : 消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息消息
         * create_time : 10月16日
         * id : 5
         */

        private String content;
        private String create_time;
        private String id;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
