package com.wh.wang.scroopclassproject.http;

import android.util.Log;

import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 用户模块接口管理类
 */
public class HttpUserManager {
    private static HttpUserManager manager = new HttpUserManager();
    private OkHttpClient okHttp = new OkHttpClient();
    private String URL_USER = "user/";

    private HttpUserManager() {
    }

    public static HttpUserManager getInstance() {
        return manager;
    }

    private String URL_LOGIN = "login.json";

    /**
     * 请求登录接口
     *
     * @param name
     * @param password
     * @param listener
     * @param clazz
     */
    public void login(int isType, String name, String password, String imei, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("mobile", name);
        if (isType == 1) {
            params.add("pwd", password);
        } else if (isType == 2) {
            params.add("rand", password);
        }
        params.add("idfa", imei == null ? "" : imei);
        params.add("src", "4");
        params.add("apk", "1"); //TODO  今日头条渠道 1
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String loginUrl = "http://php.shaoziketang.com/user/login";
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 微信登录
     */
    public void weiChatLogin(String code, String imei, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("code", code + "");
        params.add("idfa", imei + "");
        params.add("apk", "1"); //TODO  今日头条渠道 1
        params.add("src", "4");
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String loginUrl = "http://www.shaoziketang.com/user/loginCode";
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 微信登录后绑定手机
     */
    //String bindwp_nike, String bindwp_phone, String bindwp_yzm
    public void bindWP(String bindwp_nike, String bindwp_phone, String bindwp_yzm, String user_id, String rand_str, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("phone", bindwp_phone);
        params.add("code", bindwp_yzm);
        params.add("randStr", rand_str);
        params.add("uid", user_id);
        params.add("realName", bindwp_nike);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String loginUrl = "http://www.shaoziketang.com/User/appBindPhone";
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    /**
     * 微信登录后绑定手机
     */
    //String bindwp_nike, String bindwp_phone, String bindwp_yzm
    public void unBindPhone(String phone, String code, String uid, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("phone", phone);
        params.add("code", code);
        params.add("uid", uid);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String loginUrl = "http://www.shaoziketang.com/User/unBindPhone";
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    /**
     * 绑定微信
     */
    public void weiChatBind(String code, String uid, String randStr, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("code", code);
        params.add("uid", uid);
        params.add("randStr", randStr);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String weichatBindUrl = "http://www.shaoziketang.com/user/bindWx";
        Request request = new Request.Builder().url(weichatBindUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    /**
     * 解绑微信
     */
    public void weiChatUnBind(String uid, String randStr, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("uid", uid);
        params.add("randStr", randStr);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String weichatUnbindUrl = "http://www.shaoziketang.com/User/unBindWx";
        Request request = new Request.Builder().url(weichatUnbindUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 发表评论
     *
     * @param listener
     * @param clazz
     */
    public void addComment(String id, String content, String type, String userid, String parentid, String re_name, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("id", id);
        params.add("content", content);
        params.add("type", type);
        params.add("user_id", userid);
        params.add("parentid", parentid);
        params.add("re_name", re_name);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        Log.e("whwh", "addComment==" + id + "," + userid + "," + parentid + "," + re_name);
        String addCommentUrl = Constants.VideoAddCommentUrl;
        Request request = new Request.Builder().url(addCommentUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 删除评论
     *
     * @param listener
     * @param clazz
     * @param id         评论id
     * @param product_id 课程id
     * @param type       1 视频课程 2阅读
     */
    public void deleteComment(String id, String product_id, String type, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("id", id);
        params.add("product_id", product_id);
        params.add("type", type);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String deleteCommentUrl = Constants.VideoDeleteCommentUrl;
        Request request = new Request.Builder().url(deleteCommentUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 请求登录接口
     *
     * @param listener
     * @param clazz
     */
    //dialogName, event_dialog_tel, src,event_id
    public void getMoney(String dialogName, String event_dialog_tel, String src, String event_id, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("user_name", dialogName);
        params.add("mobile", event_dialog_tel);
        params.add("event_id", event_id);
        params.add("src", "4");
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String getMoneyUrl = "http://apptwo.shaoziketang.com/?c=event&m=getUserEventMoney";
        Request request = new Request.Builder().url(getMoneyUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 请求登录接口
     *
     * @param upfile
     * @param listener
     * @param clazz
     */
    public void updateAvater(String upfile, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("img", upfile);
        params.add("app", "1");
        String avaterUrl = Constants.avaterUrl;
        Request request = new Request.Builder().url(avaterUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * @param listener
     * @param clazz
     */
    // eventUpdatePay String userid, String id, double price, String batch_join, int num,
    public void eventUpdatePay(String userid, String id, double price, String batch_join, int num, String tradeNo, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("status", "buy");
        params.add("user_id", userid);
        params.add("id", id);
        params.add("money", price + "");
        params.add("order_type", "1");
        params.add("order_app", "4");
        params.add("batch_join", batch_join);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        if (num == 2) {
            params.add("order_wz", "2");
            params.add("order_true", tradeNo == null ? "" : tradeNo);
        } else if (num == 1) {
            params.add("order_wz", "1");
            params.add("order_true", tradeNo == null ? "" : tradeNo);
        }

        String avaterUrl = Constants.paylUrl;
        Request request = new Request.Builder().url(avaterUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 意见反馈
     *
     * @param listener
     * @param clazz
     */
    public void updateIdea(String userid, String content, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("user_id", userid);
        params.add("content", content);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String mineIdeaUrl = Constants.mineIdeaUrl;
        Request request = new Request.Builder().url(mineIdeaUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * 上传视频进度
     */

    //video_id,  user_id,  video_file_id, player_time, video_duration
    public void VideoProgress(String pid, String video_id, String user_id, String video_file_id, String player_time, String video_duration, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("pid", pid);
        params.add("video_id", video_id);
        params.add("user_id", user_id);
        params.add("video_file_id", video_file_id);
        params.add("player_time", player_time);
        params.add("video_duration", video_duration);
        params.add("app", "4");
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String videoProgressUrl = Constants.videoProUrl;
        Request request = new Request.Builder().url(videoProgressUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    // int file_id, int status,  String user_id,app	4
    public void startDownload(int file_id, int status, String user_id, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("file_id", file_id + "");
        params.add("status", status + "");
        params.add("user_id", user_id);
        params.add("app", 4 + "");
        params.add("user_id", user_id);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String startDownloadUrl = Constants.startVideoData;
        Request request = new Request.Builder().url(startDownloadUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    public void deleteStudy(String video_id, String user_id, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("video_id", video_id);
        params.add("user_id", user_id);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String deleteStudyUrl = Constants.deleteStudyUrl;
        Request request = new Request.Builder().url(deleteStudyUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    /**
     * 一级消息
     *
     * @param listener
     * @param clazz
     */
    public void updateMsg(String userid, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("id", userid);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String mineIdeaUrl = Constants.mineMsgNumsUrl;
        Request request = new Request.Builder().url(mineIdeaUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 二级消息
     *
     * @param listener
     * @param clazz
     */
    public void updateSecondMsg(String userid, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("user_id", userid);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String mineIdeaUrl = Constants.mineSecondMsgUrl;
        Request request = new Request.Builder().url(mineIdeaUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    /**
     * 三级消息
     *
     * @param listener
     * @param clazz
     */
    public void updateThridMsg(String userid, GetDataListener listener, Class<?> clazz) {

        FormBody.Builder params = new FormBody.Builder();
        params.add("user_id", userid);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String mineIdeaUrl = Constants.mineThridMsgUrl;
        Request request = new Request.Builder().url(mineIdeaUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 更新接口
     *
     * @param listener
     * @param clazz
     */
    public void updatess(GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("type", "0");
        String loginUrl = "http://apptwo.shaoziketang.com/?c=home&m=updateApp";
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    //更改用户信息  user_id, name,sex,place, nice, employ, nums,emails
    public void post_changeInfos(String id, String nickname, String sex, String area, String brand, String position, String mensum, String email, String avator, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("id", id);
        params.add("nickname", nickname);
        params.add("sex", sex);
        params.add("area", area);
        params.add("brand", brand);
        params.add("position", position);
        params.add("mensum", mensum);
        params.add("email", email);
        params.add("avator", avator);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String loginUrl = "http://apptwo.shaoziketang.com/?c=home&m=updateUser";
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    //获取用户信息user_id
    public void post_getInfos(String id, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("user_id", id);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String changeInfoUrl = "http://apptwo.shaoziketang.com/?c=home&m=userInfo";
        Request request = new Request.Builder().url(changeInfoUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    //更改密码
    public void post_changePassword(String oldpassword, String newpassword, String user_id, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("user_id", user_id);
        params.add("passwd", oldpassword);
        String timeStr = StringUtils.getTimestamp();
        params.add("shijian", timeStr);
        params.add("sign", Md5Util.md5("www.szkt.com" + timeStr));
        String loginUrl = "http://apptwo.shaoziketang.com/?c=home&m=updatePwd";
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }


    /***
     * 视频详情接口
     */

    public void videodetail(String user_id, String id, GetDataListener listener, Class<?> clazz) {
        FormBody.Builder params = new FormBody.Builder();
        params.add("user_id", user_id);
        params.add("id", id);
        String loginUrl = Constants.VideoDetailUrl;
        Request request = new Request.Builder().url(loginUrl).post(params.build()).build();
        HttpCore.getInstance().httpPostServer(okHttp, request, listener, clazz);
    }


    /**
     * 请求修改密码接口
     *
     * @param oldPassword
     * @param newPassword
     * @param reNewPassword
     * @param listener
     * @param clazz
     */
    public void editPassword(String oldPassword, String newPassword, String reNewPassword, GetDataListener listener, Class<?> clazz) {

        FormBody body = new FormBody.Builder().add("old_password", oldPassword).add("new_password", newPassword).add("new_password2", reNewPassword).build();
        // Request request = new Request.Builder().url(HttpUrl.baseUrl+URL_USER+URL_EDIT_PASSWORD).post(body).build();
        // HttpCore.getInstance().httpPostServer(okHttp,request,listener,clazz);
    }
}
