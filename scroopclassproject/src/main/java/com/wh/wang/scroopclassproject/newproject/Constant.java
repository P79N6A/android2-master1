package com.wh.wang.scroopclassproject.newproject;

/**
 * Created by Administrator on 2017/12/13.
 */

public class Constant {
    public static String IMEI = "";
    public static final int SEARCH_REQ = 1000;
    public static final int SEARCH_RES = 1001;
    public static boolean isPublicCourseShare = false; //是否为公开课分享
    public static boolean isShareInform = false; //分享后是否需要通知后台
    public static boolean isFree7Vip = false;
    public static boolean SHARE_STATUE = false;
    public static String isCompany = "0"; //是否为企业号
    public static int isPayCourseOrAction = -1;  //-1 无效  0 课程  1 活动 2 企业 3 小组
    public static String isPayCourseType = "";  //临时视频类型
    public static String temporaryOrderNo = ""; //临时，用于支付完成后进行数据传递
    public static String temporaryEventNo = ""; //临时id/订单号，用于支付/分享完成后进行数据传递
    public static final String BASE_URL = "http://apptwo.shaoziketang.com/"; //
    public static final String BASE_URL_2 = "https://www.shaoziketang.com/"; //https://www.shaoziketang.com/
    public static final String BASE_URL_3 = "http://admin.shaoziketang.com/"; //
    public static final String BASE_URL_TEST = "http://api.dev.shaoziketang.com/"; //测试
    public static final int IS_LIVE = 0;
    /**
     * BASE_URL
     */
    //视频详情页
    public static final String VIDEO_DETAIL = "?c=home&m=videoDetail";

    //上传视频进度
    public static final String VIDEO_PROGRESS = "?c=home&m=userVideoStudy";

    //下载视频
    public static final String DOWNLOAD_VIDEO = "?c=home&m=userDownloadLog";

    //获取个人信息
    public static final String USER_INFOS = "?c=home&m=userInfo";

    //检查版本
    public static final String CHECK_UPDATE = "?c=home&m=updateApp";

    //删除学习记录
    public static final String DELETE_STUDY_RECORD = "?c=home&m=deleteUserVideoStudyLog";

    //获取消息
    public static final String MSG = "?c=home&m=showUserMoney";

    //反馈
    public static final String FEED_BACK = "?c=home&m=sendfeedback";

    //公开课报名
    public static final String EVENT_OPEN = "?c=home&m=addEventOpen";

    //活动报名
    public static final String EVENT_DETAIL = "?c=home&m=eventDeatil";

    //检查离线视频是否可以播放
    public static final String CHECK_OFF_LINE_VIDEO = "?c=home&m=userVideoVip";

    //获取活动订单信息
    public static final String ADD_EVENT_INFO = "?c=home&m=addEventDeatil";

    //内训注册
    public static final String NX_REGISTER = "?c=train&m=reg";

    //头像上传
    public static final String UPLOAD_AVATAR = "?c=home&m=upFile";

    //企业号状态审核
    public static final String COMPANY_STATE = "?c=Train&m=companyInfo";

    //分享后通知后台
    public static final String SHARE = "?c=train&m=share";

    //剩余邀请数
    public static final String RESIDUE_INVITE = "?c=train&m=companyPersonNum";

   //获取企业成员
    public static final String COMPANY_MEMBER = "?c=train&m=companyList";

    //设置管理员
    public static final String SET_ADMIN = "?c=train&m=companySetAdmin";

    //删除成员
    public static final String DEL_MEMBER = "?c=train&m=companydelete";

    //获取企业课程信息
    public static final String COMPANY_COURSE = "?c=train&m=companyStudyVideo";

    //获取普通成员企业课程信息
    public static final String COMPANY_COMMON_COURSE = "?c=train&m=companyCommonStudyVideo";

    //赞
    public static final String ZAN = "?c=train&m=companyZhan";

    //分享列表和企业分享
    public static final String SHARE_LIST = "?c=train&m=companyPersonList";
    public static final String COMPANT_SHARE = "?c=train&m=share";


    //提醒
    public static final String REMIND = "?c=train&m=companyTixing";

    //试试看
    public static final String COMPANY_FREE = "?c=train&m=companyShiYong";

    //赞和评论列表
    public static final String LIKE_OR_REMIND = "?c=home&m=listZanTi";

    //报名支付成功
    public static final String PAY_SUCCESS = "?c=event&m=getEventMarkInfo";

    //添加评论
    public static final String ADD_COMMENT = "?c=news&m=addComment";

    //删除评论
    public static final String DELETE_COMMENT = "?c=news&m=deleteComment";

    //获取作业信息
    public static final String WORK_INFO = "?c=homework&m=getZuoyeList";

    //获取优秀作业
    public static final String GOOD_WORK_LIST = "?c=homework&m=getyouxiulist";

    //作业点赞
    public static final String WORK_LIKE = "?c=homework&m=listzhan";

    //作业详情
    public static final String WORK_DETAILS = "?c=homework&m=getZuobyid";

   //评为优秀作业
    public static final String GOOD_WORK = "?c=homework&m=youxiu_ajax";

    //评论点赞
    public static final String REMARK_LIKE = "?c=homework&m=zhan_ajax";

    //添加评论
    public static final String ADD_WORK_REMARK = "?c=homework&m=addComment";

    //上传作业图片
    public static final String UP_WORK_IMG = "?c=homework&m=upImg";

    //上传内容
    public static final String UP_WORK_CONTENT = "?c=homework&m=zuoyelist_comment_ajax";

    //删除作业
    public static final String DELETE_WORK = "?c=homework&m=deletezuoye";

    //删除作业评论
    public static final String DELETE_WORK_REMARK = "?c=homework&m=deletecomment";

    //取消引导
    public static final String CANCEL_ = "?c=home&m=updateClickCompanyNum";

    //邀请列表
    public static final String INVITE_LIST = "user/share_vip";

    //案例集_提交信息
    public static final String ALJ_COMMIT = "?c=course&m=saveAddressInfo";

    //案例集_获取状态列表
    public static final String ALJ_INFO = "?c=Course&m=getInviteStatus";

    //案例集_获取7天免费会员
    public static final String ALJ_FREE_7VIP = "?c=Course&m=share ";

    //案例集_活动详情
    public static final String ALJ_DETAILS = "?c=Course&m=caseBookDetail";

    //案例集_展示7天会员
    public static final String SHOW_7_FREE = "?c=Course&m=getUserSevenStatus";

    //视频播放完毕后弹窗
    public static final String VIDEO_FINISH_DIG = "?c=home&m=getCompanyUser";

    //主页通知
    public static final String MAIN_HINT = "index/homeClick";

    //新版首页
    public static final String NEW_HOME = "?c=index&m=newhome_m";

    //新版更多
    public static final String NEW_MORE = "?c=index&m=more_m";

    //新搜索 热门 历史
    public static final String SEARCH_HOT_HISTORY = "?c=search&m=indexready";

    //删除单条历史
    public static final String DEL_HISTORY_SIMPLE = "?c=search&m=delsearch";

    //删除全部历史
    public static final String DEL_HISTORY_ALL = "?c=search&m=delsearchlist";

    //搜索结果
    public static final String SEARCH_RESULT = "?c=search&m=indexsearch";

    //按月请求活动列表
    public static final String MONTH_EVENT_LIST = "?c=course&m=MonthEventList";

    //学习小组相关
    //首页
    public static final String STUDY_GROUP_HOME = "?c=SGroup&m=list";

    //详情
    public static final String STUDY_GROUP_DETAIL = "?c=SGroup&m=detail";

    //加入小组
    public static final String STUDY_GROUP_JOIN = "?c=SGroup&m=immediatelyJoin";

    //排行榜
    public static final String STUDY_GROUP_RANK = "?c=SGroup&m=RankingList";

    //我的学习小组
    public static final String MY_STUDY_GROUP = "?c=SGroup&m=myCardList";

    //小组优惠券
    public static final String SHOW_COUPON = "?c=Home&m=quanclick";

    //优惠券相关
    //优惠券列表
    public static final String COUPON_LIST = "?c=Coupon&m=CouponList";

    //选择优惠券/兑换优惠券
    public static final String SELECT_COUPON = "?c=Coupon&m=ChoiceCoupon";

    //新会员列表页
    public static final String NEW_VIP_LIST = "?c=home&m=vipList";

    //更多文章标题
    public static final String ESSAY_LABEL = "?c=news&m=index_type_news";

    //更多文章
    public static final String MORE_ESSAY = "?c=news&m=type_news";

    //学习小组2.0{
    //首页
    public static final String STUDY_GROUP_HOME_2_0 = "?c=SGroup&m=listTwo";

    //详情
    public static final String STUDY_GROUP_DETAIL_2_0 = "?c=SGroup&m=detailTwo";

    //加入小组
    public static final String ADD_STUDY_GROUP_2_0 = "?c=SGroup&m=immediatelyJoinTwo";

    //获取心得
    public static final String STUDY_GROUP_REMARK = "?c=SGroup&m=getHeart";

    //心得详情
    public static final String REMARK_DETAIL = "?c=SGroup&m=getHeartDetail";

    //绑定支付宝
    public static final String BIND_ALIPAY = "?c=SGroup&m=saveUserAlipay";

    //获取支付宝信息
    public static final String GET_ALIPAY_INFO = "?c=SGroup&m=getAlipayInfo";

    //成败榜}
    public static final String NEW_SG_RANK = "?c=SGroup&m=billBoard";

    //上传log日志
    public static final String UP_LOG = "?c=file&m=uplog";

    //文章详情
    public static final String ESSAY_DERAIL = "?c=home&m=newsDetail";

    //获取客服信息
    public static final String CUSTOMER_INFO = "?c=SGroup&m=getCustomerInfo";

    //客服电话反馈
    public static final String TEL_RESULT = "?c=SGroup&m=callRecord";

    /**
     * BASE_URL_2
     */
    //搜索历史
    public static final String SEARCH_HISTORY = "user/searchInit";

    //搜索
    public static final String SEARCH = "user/search_m";

    //支付
    public static final String PAY = "Orders/pay";

    //报名检查
    public static final String CHECK_EVENT = "Event/detail_m";

    //更多报名检查
    public static final String CHECK_MORE_EVENT = "event/getUserEventPrice";

    //更多课程
    public static final String MORE_COURSE = "course/typeMore";

    //全部课程
    public static final String ALL_COURSE = "course/allCourse_m";

    //首页_精品课_精选与系统
    public static final String MAIN_COURSE = "Index/home_m?app=1";

    //首页_精品课_免费课
    public static final String FREE_COURSE = "course/free_m";

    //课程标签
    public static final String COURSE_LABEL = "course/lists_m?app=1";

    //扫码
    public static final String SCAN = "Qr/event";

    //课程购买成功
    public static final String COURSE_SUCCESS = "orders/payQuery";

    //获取企业支付套餐信息
    public static final String COMPANY_PRICE = "Course/getCompanyPrice";

    // 购买记录
    public static final String ORDER_LIST = "study/get_newcenter";

    //联系人相关
    public static final String CONTACT = "user/contacts";
    public static final String COMPANY_CONTACT = "user/companyContacts";

    //我的学习
    public static final String MY_STUDY = "study/get_center";

    //心得点赞
    public static final String SG_REMARK_LIKE = "news/zhan_ajax";

    //签到二维码
    public static final String QIANDAO = "Qr/signIn";

    /**
     * BASE_URL_3
     */
    /**
     * 收藏
     */
    public static final String COLLECT = "szkadmin/?c=app&m=collect_ajax";

    /**
     * 学习记录
     */
    public static final String NEW_MY_STUDY = "?c=app&m=get_center";

    /**
     * 新手礼包
     */
    public static final String GIFt_BAG = "?c=api&m=showDiscount";

    /**
     * 礼包弹窗
     */
    public static final String GIFT_DIG = "?c=api&m=getNewUser";

    /**
     * 文章点赞
     */
    public static final String ESSAY_LIKE = "szkadmin/?c=app&m=collect_ajax";


}
