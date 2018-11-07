package com.wh.wang.scroopclassproject.utils;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.wh.wang.scroopclassproject.bean.SuperiorBean;

import java.util.List;

/**
 * wang
 * <p>
 * 作用：常量类，配置网络地址
 */
public class Constants {

    public static String baseUrl = "apptwo.shaoziketang.com";
    public static String normalUrl = "apptwo.shaoziketang.com";

    public static String ceshiUrl = "www.shaoziapp.com";
    public static String timeStr = StringUtils.getTimestamp();
    public static String extra = "&shijian=" + timeStr + "&sign=" + Md5Util.md5("www.szkt.com" + timeStr);

    /**
     * 首页的联网地址
     */
    public static String superiorUrl2 = "http://" + baseUrl + "/?c=home&m=home";

    public static String superiorUrl3 = "http://" + baseUrl + "/?c=home&m=newhome";

    //public static String superiorUrl4 = "http://www.sosoapi.com/pass/mock/8336//index/home_m?app=1";
    //public static String supMoreUrl = "http://www.sosoapi.com/pass/mock/8336//course/typeMore?app=1&wap=1";
    //public static String supAllUrl = "http://www.sosoapi.com/pass/mock/8336//course/allCourse_m?app=1&wap=1";
    //public static String supmfUrl = "http://www.sosoapi.com/pass/mock/8336//course/free_m?app=1&wap=1";
    public static String superiorUrl4 = "https://www.shaoziketang.com/Index/home_m?app=1";
    ///course/lists_m
    public static String supMoreUrl = "https://www.shaoziketang.com/course/typeMore?app=1";
    public static String supAllUrl = "https://www.shaoziketang.com/course/allCourse_m?app=1";
    public static String supmfUrl = "https://www.shaoziketang.com/course/free_m?app=1";

    public static String welcomeUrl = "http://" + baseUrl + "/?c=home&m=getWelcomeMess" + extra;


    /**
     * 精选的联网地址
     */

    public static String courseUrll = "http://" + baseUrl + "/?c=home&m=label";
    public static String courseUrld = "http://" + baseUrl + "/?c=home&m=labelCourse";
    public static String courseUrl2 = "https://www.shaoziketang.com/course/lists_m?app=1&typeId=";

    /**
     * 阅读的联网地址
     */
    public static String readUrl = "http://api.shaoziketang.com/index.php?c=index&m=read";
    public static String readUrl2 = "http://" + baseUrl + "/?c=home&m=read";

    /**
     * 阅读详情
     */
    public static String readDetailUrl = "http://" + baseUrl + "/?c=home&m=newsDetail";

    /**
     * 阅读的联网地址
     */
    //http://admin.shaoziketang.com/?c=app&m=get_center&user_id=33988
    public static String mineCeshiUrl = "http://admin.shaoziketang.com/?c=app&m=get_center&user_id=";
    public static String mineUrl = "http://121.40.248.175/szkadmin/?c=app&m=collect_ajax";


    /**
     * 登录验证码联网网址
     */
    public static String loginCodeUrl = "http://php.shaoziketang.com/user/login";
    public static String bindCodeUrl = "https://www.shaoziketang.com/User/bindMobile";


    /**
     * 未完成课程的联网地址
     */
    //17334 27155
    public static String mineunFinishedUrl = "http://api.shaoziketang.com/index.php?c=base&m=unFinished&user_id=";

    /**
     * 收藏课程的联网地址
     */
    public static String minemyFavouriteUrl = "http://api.shaoziketang.com/index.php?c=base&m=myFavourite&user_id=";

    /**
     * 视频详情联网地址
     */
    public static String superiorVideoUrl = "http://api.shaoziketang.com/index.php?c=base&m=showVideoDetail&post_id=";


    public static String VideoDetailUrl = "http://" + baseUrl + "/?c=home&m=videoDetail";

    public static String startVideoData = "http://" + baseUrl + "/?c=home&m=userDownloadLog";

    public static String deleteStudyUrl = "http://" + baseUrl + "/?c=home&m=deleteUserVideoStudyLog";


    public static String VideoAddCommentUrl = "http://" + baseUrl + "/?c=news&m=addComment";

    public static String VideoDeleteCommentUrl = "http://" + baseUrl + "/?c=news&m=deleteComment";


    //收藏
    public static String loveUrl = "http://admin.shaoziketang.com/szkadmin/?c=app&m=collect_ajax";

    /**
     * 搜索
     */
    public static String searchUrl = "http://" + baseUrl + "/?c=home&m=search";

    public static String updateUrl = "http://" + baseUrl + "/?c=home&m=updateApp";

    /**
     * 搜索历史
     */
    public static String searchHistotyUrl = "http://" + baseUrl + "/?c=home&m=getSearchHistory";

    //活动详情
    public static String eventDetailUrl = "http://" + baseUrl + "/?c=home&m=eventDeatil";
    public static String eventFromlUrl = "http://" + baseUrl + "/?c=home&m=addEventDeatil";
    public static String eventSubmitUrl = "http://" + baseUrl + "/?c=home&m=addEvent";
    public static String eventGetMoneyUrl = "http://" + baseUrl + "/?c=event&m=getUserEventMoney";

    //提交支付结果请求
    public static String paylUrl = "http://" + baseUrl + "/?c=home&m=updateIntegra";

    /**
     * 头像上传
     */
    public static String avaterUrl = "http://" + baseUrl + "/?c=home&m=upFile";
    public static String avaterUrl2 = "http://admin.shaoziketang.com/?c=welcome&m=getImg";

    /**
     * 意见反馈
     */
    public static String mineIdeaUrl = "http://" + baseUrl + "/?c=home&m=sendfeedback";


    public static String videoProUrl = "http://" + baseUrl + "/?c=home&m=userVideoStudy";


    /**
     * 消息数量
     */
    public static String mineMsgNumsUrl = "http://" + baseUrl + "/?c=home&m=showUserMoney";

    /**
     * 二级消息
     */
    public static String mineSecondMsgUrl = "http://" + baseUrl + "/?c=home&m=messDetail";

    /**
     * 三级消息
     */
    public static String mineThridMsgUrl = "http://" + baseUrl + "/?c=home&m=msgList";


    /**
     * 反馈消息接口
     */
    public static String mineFeedbackMsgUrl = "http://" + baseUrl + "/?c=home&m=feedbackDetail";


    public static String batch_join = "";


    //--------------------------------------------------------------------
    public static IWXAPI wx_api; //全局的微信api对象

    public static final String APP_ID = "wx7711b78effa9ae30";
    public static final String APP_SECRET = "1bdabadd962c657b1bb32ba0678e9875";
    //商户号
    public static final String MCH_ID = "1488125332";
    //API密钥，在商户平台设置
    public static final String API_KEY = "shaozikejiweixinzhifu12345678910";

    public static int weixinFlag = 10;

    private final int weixinFlags = 20;

    public static int eventFlag = 0;

    public static boolean isFromSearch = false;

    public static int couseLable = 0;

    public static int avatarFlag = 0;

    public static List<SuperiorBean.ThreecourseBean> treeList;//最新消息滚动

    public static boolean isFatherClick = true;

    public static int loginCode = -1;
    public static String weichat_code = "";
}
