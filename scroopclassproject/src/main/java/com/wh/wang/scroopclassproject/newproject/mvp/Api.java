package com.wh.wang.scroopclassproject.newproject.mvp;

import com.wh.wang.scroopclassproject.bean.TipsBean;
import com.wh.wang.scroopclassproject.bean.VedioDetailInfo;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponListEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.gift_bag.GiftBagEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.gift_bag.GiftBagStatusEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.GetAlipayEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.MyStudyGroupEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.NewSGRankEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetailEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGHome2_0Entity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGHomeEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGJoinEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRankEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRemarkDetailEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRemarkEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.net.VipListEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ALJCommitEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ALJInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.AljDetailsEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CustomerEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DelHistoryEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayLabelEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.InviteEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MonthEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreEssayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewMoreEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PublishEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ScanResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SearchHotHistoryEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShowFree7Entity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.TelResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.VideoFinishEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkDetailsEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.AdminEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckEventEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckOffLineVideoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckUpdateEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CollectEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CommentEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCommonCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyPriceEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CompanyStateEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseLabelEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DeleteMember;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DeleteRecordEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventApplyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventOpenEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.FeedBackEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.FreeCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.LikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.LikeOrRemindEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MemberEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MessageEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MoreOrAllCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MyStudyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewSearchInitEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.OperationContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.OrderInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PayEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PaySuccessEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.RegisterEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ResidueInviteEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ScanEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShareCompanyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShareListEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.TryOutEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.UpdateProgressEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.UserInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkRemarkEntity;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/12/13.
 */

public interface Api {
    String timeStr = StringUtils.getTimestamp();
    String sign = Md5Util.md5("www.szkt.com" + timeStr);
    /**
     * New搜索历史
     */
    @GET(Constant.SEARCH_HISTORY)
    Call<NewSearchInitEntity> getSearchHistory(@Query("uid") String id, @Query("shijian") String timeStr, @Query("sign") String sign);

    /**
     * New搜索
     */
    @GET(Constant.SEARCH)
    Call<NewSearchEntity> getSearchResult(@Query("key")String key ,@Query("uid") String id,@Query("page") String page,@Query("type")String type
                                    ,@Query("pageRow") String pageRow,@Query("shijian") String shijian,@Query("sign") String sign);

    @GET(Constant.SEARCH)
    Call<ResponseBody> getSearchResultJson(@Query("key")String key , @Query("uid") String id, @Query("page") String page, @Query("type")String type
            , @Query("pageRow") String pageRow, @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 订单支付
     * @param goodsType
     * @param id
     * @param user_id
     * @param coupon
     * @param pay_type
     * @return
     */
    @GET(Constant.PAY)
    Call<PayEntity> orderPay(@Query("goodsType") String goodsType,@Query("id") String id,@Query("user_id") String user_id,
                             @Query("coupon_cut") String coupon,@Query("pay_type") String pay_type,@Query("company")String company);

    @FormUrlEncoded
    @POST(Constant.PAY)
    Call<PayEntity> orderPay(@QueryMap Map<String,String> map,@Field("batch_join")String batch_join);

    @FormUrlEncoded
    @POST(Constant.PAY)
    Call<ResponseBody> orderPayJson(@QueryMap Map<String,String> map,@Field("batch_join")String batch_join);

    /**
     * 检查报名
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.CHECK_EVENT)
    Call<CheckEventEntity> checkEvent(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST(Constant.CHECK_EVENT)
    Call<ResponseBody> checkEventJson(@FieldMap Map<String,String> map);

    /**
     * 检查更多报名
     * @param p
     * @param id
     * @param tid
     * @return
     */
    @GET(Constant.CHECK_MORE_EVENT)
    Call<CheckEventEntity> checkMore(@Query("p")String p ,@Query("id")String id,@Query("tid")String tid,@Query("uid")String uid);

    /**
     * 加载更多课程
     * @param app
     * @param wap
     * @param typeId
     * @param page
     * @return
     */
    @GET(Constant.MORE_COURSE)
    Call<MoreOrAllCourseEntity> getMoreCourse(@Query("app") String app,@Query("wap")String wap,@Query("typeId")String typeId,
                                              @Query("page")String page);


    /**
     * 加载全部课程
     * @param app
     * @param wap
     * @param page
     * @return
     */
    @GET(Constant.ALL_COURSE)
    Call<MoreOrAllCourseEntity> getAllCourse(@Query("app")String app,@Query("wap")String wap,@Query("page")String page);

    /**
     * 加载精品_免费课程
     * @param app
     * @param wap
     * @param page
     * @return
     */
    @GET(Constant.FREE_COURSE)
    Call<FreeCourseEntity> getFreeCourse(@Query("app")String app, @Query("wap")String wap, @Query("page")String page);

    /**
     * 加载精品_精选和系统课程
     * @return
     */
    @GET(Constant.MAIN_COURSE)
    Call<MainCourseEntity> getMainCourse(@Query("user_id") String user_id,@Query("ver")String ver,@Query("app_type")String app_type);

    /**
     * 视频详情页
     */
    @FormUrlEncoded
    @POST(Constant.VIDEO_DETAIL)
    Call<SumUpEntity> getSumUpInfo(@Field("user_id")String user_id,@Field("id")String id,@Field("type") String type,@Field("idfa") String idfa);

    @FormUrlEncoded
    @POST(Constant.VIDEO_DETAIL)
    Call<VedioDetailInfo> getVideoInfo(@Field("user_id")String user_id, @Field("id")String id, @Field("type") String type, @Field("idfa") String idfa);

    /**
     * 视频上传进度
     */
    @FormUrlEncoded
    @POST(Constant.VIDEO_PROGRESS)
    Call<UpdateProgressEntity> updateVideoProgress(@FieldMap Map<String,String> map);

    /**
     * 收藏
     * @param id
     * @param user_id
     * @param type 视频收藏 / 阅读收藏
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.COLLECT)
    Call<CollectEntity> changeCollect(@Field("id")String id, @Field("user_id")String user_id, @Field("type")String type);

    /**
     * 上传下载信息
     * @param file_id
     * @param status
     * @param user_id
     * @param app
     * @param shijian
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.DOWNLOAD_VIDEO)
    Call<ResponseBody> downLoadVideo(@Field("file_id")String file_id,@Field("status")String status,@Field("user_id")String user_id,
                                     @Field("app")String app,@Field("shijian")String shijian,@Field("sign")String sign);


    /**
     * 获取用户信息
     * @param user_id
     * @param shijian
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.USER_INFOS)
    Call<UserInfoEntity> getUserInfo(@Field("user_id")String user_id,@Field("shijian")String shijian,@Field("sign")String sign);

    /**
     * 检查是否需要更新
     * @param type 客户端类型
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.CHECK_UPDATE)
    Call<CheckUpdateEntity> checkUpdate(@Field("user_id")String user_id,@Field("type")String type,@Field("version")String version,
                                        @Field("apk")String apk);

    /**
     * 获取“我的学习”信息
     * @param user_id
     * @param shijian
     * @param sign
     * @return
     */
    @GET(Constant.MY_STUDY+"/{user_id}")
    Call<MyStudyEntity> getStudyInfo(@Path("user_id")String user_id, @Query("shijian")String shijian, @Query("sign")String sign);

    /**
     * 删除我的学习记录
     */
    @FormUrlEncoded
    @POST(Constant.DELETE_STUDY_RECORD)
    Call<DeleteRecordEntity> deleteStudyRecord(@Field("video_id")String video_id, @Field("user_id")String user_id, @Field("shijian")String shijian,
                                               @Field("sign")String sign);

    /**
     * 获取消息
     */
    @FormUrlEncoded
    @POST(Constant.MSG)
    Call<MessageEntity> getMessage(@Field("id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 获取标签和课程
     */
    @GET(Constant.COURSE_LABEL)
    Call<CourseLabelEntity> getCourseInfo(@Query("typeId")String typeId, @Query("page") String page);

    /**
     * 反馈
     */
    @FormUrlEncoded
    @POST(Constant.FEED_BACK)
    Call<FeedBackEntity> feedBack(@Field("user_id")String user_id, @Field("content")String content,
                                  @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 扫码
     */
    @GET(Constant.SCAN)
    Call<ScanEntity> getScanResult(@Query("id")String id,@Query("uid")String uid);

    /**
     * 公开课报名
     */
    @FormUrlEncoded
    @POST(Constant.EVENT_OPEN)
    Call<EventOpenEntity> eventOpen(@Field("event_id")String event_id, @Field("user_id")String user_id,
                                    @Field("sign")String sign, @Field("shijian")String shijian, @Field("order_app")String order_app);

    /**
     * 获取活动详情
     * @param event_id
     * @param user_id
     * @param shijian
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.EVENT_DETAIL)
    Call<EventDetailEntity> getEventDetails(@Field("id")String event_id,@Field("user_id")String user_id,@Field("shijian")String shijian,
                                            @Field("sign")String sign);

    /**
     * 课程购买成功
     */
    @GET(Constant.COURSE_SUCCESS)
    Call<CourseResultEntity> getCourseSuccessInfo(@Query("no")String no, @Query("page")String page, @Query("pageRow")String pageRow, @Query("uid")String uid, @Query("randStr")String randStr);


    /**
     * 检查离线视频是否可以播放
     * @param video_id  视频id parent_id
     * @param user_id  用户id
     * @return
     */
    @GET(Constant.CHECK_OFF_LINE_VIDEO)
    Call<CheckOffLineVideoEntity> checkOffLineVideo(@Query("video_id")String video_id,@Query("user_id")String user_id);

    /**
     * 获取活动报名时的用户信息
     */
    @FormUrlEncoded
    @POST(Constant.ADD_EVENT_INFO)
    Call<EventApplyEntity> getEventApplyInfo(@Field("id")String id, @Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 内训注册
     */
    @FormUrlEncoded
    @POST(Constant.NX_REGISTER)
    Call<RegisterEntity> registNX(@Field("user_id")String user_id, @FieldMap Map<String,String> map, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 头像上传
     */
    @FormUrlEncoded
    @POST(Constant.UPLOAD_AVATAR)
    Call<ResponseBody> uploadAvatar(@Field("img")String img, @Field("app")String app);

    /**
     * 企业号审核状态
     */
    @FormUrlEncoded
    @POST(Constant.COMPANY_STATE)
    Call<CompanyStateEntity> getCompanyState(@Field("user_id")String user_id,@Field("status")String status, @Field("shijian")String shijian, @Field("sign")String sign);


    /**
     * 获取企业支付套餐信息
     */
    @GET(Constant.COMPANY_PRICE)
    Call<CompanyPriceEntity> getCompanyPrice(@Query("uid")String u_id,@Query("shijian") String shijian, @Query("sign")String sign);

    /**
     * 分享通知
     */
    @FormUrlEncoded
    @POST(Constant.SHARE)
    Call<ResponseBody> shareInform(@Field("user_id")String user_id,@Field("type")String type ,@Field("product_id")String product_id,@Field("ifcompany")String ifcompany,
                                   @Field("shijian")String shijian, @Field("sign")String sign);
    @FormUrlEncoded
    @POST(Constant.SHARE)
    Call<ShareCompanyEntity> shareInform(@Field("user_id")String user_id, @Field("type")String type , @Field("product_id")String product_id,
                                         @Field("shareTouser")String shareTouser,@Field("ifcompany")String ifcompany, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 查询剩余邀请数
     */
    @FormUrlEncoded
    @POST(Constant.RESIDUE_INVITE)
    Call<ResidueInviteEntity> getResidueInvite(@Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 获取成员列表
     */
    @FormUrlEncoded
    @POST(Constant.COMPANY_MEMBER)
    Call<MemberEntity> getCompanyMember(@Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 设置管理员
     */
    @FormUrlEncoded
    @POST(Constant.SET_ADMIN)
    Call<AdminEntity> setAdmin(@Field("user_id")String user_id, @Field("child_id")String child_id, @Field("type")String type, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 删除成员
     */
    @FormUrlEncoded
    @POST(Constant.DEL_MEMBER)
    Call<DeleteMember> delMember(@Field("user_id")String user_id, @Field("child_id")String child_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 获取企业课程信息
     */
    @FormUrlEncoded
    @POST(Constant.COMPANY_COURSE)
    Call<CompanyCourseEntity> getCompanyCourse(@Field("user_id")String user_id, @Field("video_id")String video_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 获取企业课程信息
     */
    @FormUrlEncoded
    @POST(Constant.COMPANY_COMMON_COURSE)
    Call<CompanyCommonCourseEntity> getCompanyCommonCourse(@Field("user_id")String user_id, @Field("video_id")String video_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 赞
     * type:0 单个点赞  1y 一键点赞
     */
    @FormUrlEncoded
    @POST(Constant.ZAN)
    Call<LikeEntity> like(@Field("user_id")String user_id, @Field("child_id")String child_id,@Field("type") String type,@Field("video_id")String video_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 分享列表和分享
     */
    @FormUrlEncoded
    @POST(Constant.SHARE_LIST)
    Call<ShareListEntity> getShareList(@Field("user_id")String user_id,@Field("video_id")String video_id, @Field("shijian")String shijian, @Field("sign")String sign);

    @FormUrlEncoded
    @POST(Constant.COMPANT_SHARE)
    Call<ResponseBody> companyShare(@Field("user_id")String user_id,@Field("shareTouser")String shareTouser );

    /**
     * 提醒
     */
    @FormUrlEncoded
    @POST(Constant.REMIND)
    Call<ResponseBody> remind(@Field("user_id")String user_id,@Field("video_id")String video_id,@Field("child_id")String child_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 试试看
     */
    @FormUrlEncoded
    @POST(Constant.COMPANY_FREE)
    Call<TryOutEntity> tryOut(@Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 我的学习——已购买
     */
    @GET(Constant.ORDER_LIST+"/{id}")
    Call<OrderInfoEntity> getOrderList(@Path("id") String user_id);

    /**
     * 点赞和提醒列表
     */
//    @FormUrlEncoded
    @GET(Constant.LIKE_OR_REMIND)
    Call<LikeOrRemindEntity> getLikeOrRemindList(@Query("user_id") String user_id, @Query("type")String type, @Query("shijian")String shijian, @Query("sign")String sign);

    /**
     * 联系人相关
     * get 方法是获取联系人
     * 如果post 传了 id 但是没有传name 和phone 代表是删除
     * 如果post 传了id 传了name 和phone 代表是更新
     * 如果post 没传id 传了name 和phone 代表新增
     */
    @GET(Constant.CONTACT)
    Call<ContactEntity> getContact(@Query("uid")String uid,@Query("randStr")String randStr,@Query("eid")String event_id,@Query("isVideo")String isVideo);

    @GET(Constant.COMPANY_CONTACT)
    Call<ContactEntity> getCompanyContact(@Query("uid")String uid,@Query("randStr")String randStr,@Query("eid")String event_id,@Query("isVideo")String isVideo);
    @GET(Constant.COMPANY_CONTACT)
    Call<ResponseBody> getCompanyContactJson(@Query("uid")String uid,@Query("randStr")String randStr,@Query("eid")String event_id,@Query("isVideo")String isVideo);

    @FormUrlEncoded
    @POST(Constant.CONTACT)
    Call<OperationContactEntity> contactOperation(@Query("uid") String uid, @Query("randStr")String randStr,@Query("eid") String event_id
            , @Field("id")String id, @Field("name")String name, @Field("phone")String phone,@Query("isVideo")String isVideo);

    @FormUrlEncoded
    @POST(Constant.CONTACT)
    Call<ResponseBody> contactOperationJson(@Query("uid") String uid, @Query("randStr")String randStr,@Query("eid") String event_id, @Field("id")String id, @Field("name")String name, @Field("phone")String phone);

    /**
     * 支付报名成功
     */
    @FormUrlEncoded
    @POST(Constant.PAY_SUCCESS)
    Call<ResponseBody> paySuccessJson(@Field("user_id")String user_id,@Field("event_id")String event_id);


    @GET(Constant.PAY_SUCCESS)
    Call<PaySuccessEntity> paySuccess(@Query("user_id") String user_id, @Query("event_id")String event_id, @Query("shijian")String shijian, @Query("sign")String sign);

    /**
     * 添加评论
     */
    @FormUrlEncoded
    @POST(Constant.ADD_COMMENT)
    Call<ResponseBody> addCommentJson(@FieldMap Map<String,String> map,@Field("shijian")String shijian,@Field("sign")String sign);

    @FormUrlEncoded
    @POST(Constant.ADD_COMMENT)
    Call<CommentEntity> addComment(@FieldMap Map<String,String> map, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 删除评论
     */
    @FormUrlEncoded
    @POST(Constant.DELETE_COMMENT)
    Call<ResponseBody> deleteCommentJson(@Field("id")String id,@Field("product_id")String product_id,@Field("type")String type,@Field("shijian")String shijian,@Field("sign")String sign);

    @FormUrlEncoded
    @POST(Constant.DELETE_COMMENT)
    Call<ResponseBody> deleteComment(@Field("id")String id,@Field("product_id")String product_id,@Field("type")String type,@Field("shijian")String shijian,@Field("sign")String sign);

    /**
     * 获取作业信息
     */
    @FormUrlEncoded
    @POST(Constant.WORK_INFO)
    Call<ResponseBody> getWorkInfoJson(@Field("video_id")String video_id,@Field("cate_id")String cate_id,@Field("user_id")String user_id,@Field("shijian")String shijian,@Field("sign")String sign);

    @FormUrlEncoded
    @POST(Constant.WORK_INFO)
    Call<WorkInfoEntity> getWorkInfo(@Field("video_id")String video_id, @Field("cate_id")String cate_id, @Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);


    /**
     * 获取优秀作业
     * @param video_id
     * @param cate_id
     * @param user_id
     * @param shijian
     * @param sign
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.GOOD_WORK_LIST)
    Call<ResponseBody> getGoodWorkInfoJson(@Field("video_id")String video_id,@Field("cate_id")String cate_id,@Field("user_id")String user_id,@Field("shijian")String shijian,@Field("sign")String sign);

    @FormUrlEncoded
    @POST(Constant.GOOD_WORK_LIST)
    Call<WorkInfoEntity> getGoodWorkInfo(@Field("video_id")String video_id, @Field("cate_id")String cate_id, @Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 作业点赞
     */
    @FormUrlEncoded
    @POST(Constant.WORK_LIKE)
    Call<WorkLikeEntity> workLike(@Field("id")String id, @Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 作业详情
     */
    @FormUrlEncoded
    @POST(Constant.WORK_DETAILS)
    Call<WorkDetailsEntity> getWorkDetails(@Field("list_id")String  list_id,@Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 添加作业评论
     */
    @FormUrlEncoded
    @POST(Constant.ADD_WORK_REMARK)
    Call<WorkRemarkEntity> addWorkRemark(@Field("id")String id, @Field("user_id")String user_id, @Field("content")String content, @Field("parentid")String parentid,
                                         @Field("re_name")String re_name, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 优秀作业
     */
    @FormUrlEncoded
    @POST(Constant.GOOD_WORK)
    Call<WorkLikeEntity> goodWork(@Field("id")String id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 评论点赞
     */
    @FormUrlEncoded
    @POST(Constant.REMARK_LIKE)
    Call<ResponseBody> remarkLike(@Field("id")String id,@Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 上传作业图片
     */
    @FormUrlEncoded
    @POST(Constant.UP_WORK_IMG)
    Call<ResponseBody> upWorkImg(@Field("img")String img, @Field("app")String app, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 上传作业内容
     */
    @FormUrlEncoded
    @POST(Constant.UP_WORK_CONTENT)
    Call<ResponseBody> upWorkContent(@Field("user_id")String user_id, @Field("zuoye_id")String zuoye_id, @Field("content")String content
            , @Field("type")String type, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 删除作业
     */
    @FormUrlEncoded
    @POST(Constant.DELETE_WORK)
    Call<PublishEntity> deleteWork(@Field("id")String id,@Field("user_id")String user_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 删除作业评论
     */
    @FormUrlEncoded
    @POST(Constant.DELETE_WORK_REMARK)
    Call<PublishEntity> deleteWorkRemark(@Field("id")String id,@Field("parentid")String parent_id, @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 取消引导
     */
    @GET(Constant.CANCEL_)
    Call<ResponseBody> cancel_yd(@Query("user_id")String user_id,@Query("type") String type);

    /**
     * 邀请列表
     */
    @GET(Constant.INVITE_LIST)
    Call<InviteEntity> inviteList(@Query("uid")String uid);

    /**
     * 案例集--提交信息
     */
    @GET(Constant.ALJ_COMMIT)
    Call<ALJCommitEntity> aljCommit(@QueryMap Map<String,String> map, @Query("shijian") String shijian, @Query("sign") String sign);
    /**
     * 案例集--案例集信息
     */
    @GET(Constant.ALJ_INFO)
    Call<ALJInfoEntity> aljInfo(@Query("user_id")String user_id, @Query("event_id")String event_id, @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 案例集--分享七天免费会员
     */
    @GET(Constant.ALJ_FREE_7VIP)
    Call<ResponseBody> aljFree7Vip(@Query("user_id")String user_id,@Query("event_id")String event_id, @Query("app")String app,@Query("status")String status,
                                   @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 案例集--案例集信息
     */
    @GET(Constant.ALJ_DETAILS)
    Call<AljDetailsEntity> aljDetails(@Query("user_id")String user_id, @Query("event_id")String event_id, @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 案例集--展示7天会员
     */
    @FormUrlEncoded
    @POST(Constant.SHOW_7_FREE)
    Call<ShowFree7Entity> show7Free(@Field("user_id")String user_id, @Field("shijian") String shijian, @Field("sign") String sign);

    /**
     * 视频结束后的弹窗
     */
    @GET(Constant.VIDEO_FINISH_DIG)
    Call<VideoFinishEntity> showFinishDig(@Query("user_id")String user_id,@Query("id")String id,@Query("cate_id")String childId);

    /**
     * 主页提示通知消失
     */
    @GET(Constant.MAIN_HINT)
    Call<ResponseBody> mainHint(@Query("user_id")String user_id,@Query("type") String type,@Query("video_id")String video_id);

    /**
     * 新首页
     */
    @GET(Constant.NEW_HOME)
    Call<NewHomeEntity> getNewHomeInfo(@Query("shijian") String shijian, @Query("sign") String sign);
    @GET(Constant.NEW_HOME)
    Call<ResponseBody> getNewHomeInfoForJson(@Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 新版更多
     */
    @GET(Constant.NEW_MORE)
    Call<ResponseBody> getNewMoreInfoForJson(@Query("type_id")String type_id,@Query("page") String page,@Query("shijian") String shijian, @Query("sign") String sign);
    @GET(Constant.NEW_MORE)
    Call<NewMoreEntity> getNewMoreInfo(@Query("type_id")String type_id, @Query("page") String page, @Query("shijian") String shijian, @Query("sign") String sign);


    /**
     *搜索 历史 热门
     */
    @GET(Constant.SEARCH_HOT_HISTORY)
    Call<SearchHotHistoryEntity> getHotAndHistory(@Query("uid")String uid,@Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 删除 单条 全部
     */

    @GET(Constant.DEL_HISTORY_SIMPLE)
    Call<DelHistoryEntity> delSimpleHistory(@Query("uid") String uid, @Query("content")String content, @Query("shijian") String shijian, @Query("sign") String sign);

    @GET(Constant.DEL_HISTORY_ALL)
    Call<ResponseBody> delAllHistory(@Query("uid") String uid,@Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 搜索结果
     */
    @GET(Constant.SEARCH_RESULT)
    Call<NewSearchResultEntity> getNewSearchResult(@Query("key")String key, @Query("uid")String uid, @Query("page")String page, @Query("pageRow")String pageRow
                            , @Query("type")String type, @Query("shijian")String shijian, @Query("sign")String sign);

    /**
     * 按月请求活动
     */
    @GET(Constant.MONTH_EVENT_LIST)
    Call<MonthEventEntity> getMontEvents(@Query("where")String where, @Query("shijian")String shijian, @Query("sign")String sign);



     // ------------  学习小组相关  ------------
    /**
     * 首页
     */
    @GET(Constant.STUDY_GROUP_HOME)
    Call<SGHomeEntity> getSGHome(@Query("uid")String user_id,@Query("shijian")String shijian, @Query("sign")String sign);

    /**
     * 详情
     */
    @FormUrlEncoded
    @POST(Constant.STUDY_GROUP_DETAIL)
    Call<SGDetailEntity> getSGDetail(@Field("uid")String uid, @Field("pid")String pid, @Field("vid")String vid,
                                     @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 加入小组
     */
    @FormUrlEncoded
    @POST(Constant.STUDY_GROUP_JOIN)
    Call<SGJoinEntity> joinStudyGroup(@Field("uid")String uid, @Field("pid")String pid, @Field("vid")String vid,
                                      @Field("shijian")String shijian, @Field("sign")String sign);

    /**
     * 我的小组
     */
    @FormUrlEncoded
    @POST(Constant.MY_STUDY_GROUP)
    Call<MyStudyGroupEntity> getMyStudyGroup(@Field("uid")String uid,@Field("shijian")String shijian, @Field("sign")String sign);


    /**
     * 小组排行榜
     */
    @FormUrlEncoded
    @POST(Constant.STUDY_GROUP_RANK)
    Call<SGRankEntity> getStudyGroupRank(@Field("uid")String uid, @Field("pid")String pid, @Field("vid")String vid,
                                         @Field("type")String type,@Field("page")String page,@Field("pageRow")String pageRow, @Field("shijian")String shijian, @Field("sign")String sign);


    /**
     * 小组优惠券
     */
    @GET(Constant.SHOW_COUPON)
    Call<ResponseBody> showCoupon(@Query("uid")String uid,@Query("type")String type,@Query("pid")String pid,@Query("shijian") String shijian, @Query("sign") String sign);


    /**
     * 优惠券列表
     */
    @GET(Constant.COUPON_LIST)
    Call<CouponListEntity> getCouponList(@Query("user_id")String user_id,@Query("type")String type,@Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 选择优惠券
     */
    @GET(Constant.SELECT_COUPON)
    Call<CouponListEntity> selectCoupon(@Query("user_id")String user_id,@Query("type")String type,
            @Query("coupon_code")String coupon_code,@Query("video_id")String video_id,@Query("category")String category
            ,@Query("Payprice")String Payprice,@Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 新会员列表页
     */
    @GET(Constant.NEW_VIP_LIST)
    Call<VipListEntity> getNewVipList(@Query("user_id")String user_id);

    /**
     * 获取更多文章label
     */
    @GET(Constant.ESSAY_LABEL)
    Call<List<EssayLabelEntity>> getEssayLabels();


    /**
     * 获取更多文章label
     */
    @GET(Constant.MORE_ESSAY)
    Call<MoreEssayEntity> getMoreEssays(@Query("type")String type,@Query("page")String page);

    /**
     * 学习小组2.0 首页
     */
    @GET(Constant.STUDY_GROUP_HOME_2_0)
    Call<SGHome2_0Entity> getSGHome2_0(@Query("uid")String uid,@Query("shijian") String shijian, @Query("sign") String sign);

    @GET(Constant.STUDY_GROUP_HOME_2_0)
    Call<ResponseBody> getSGHome2_0Json(@Query("uid")String uid,@Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 学习小组2.0 详情页
     */
    @GET(Constant.STUDY_GROUP_DETAIL_2_0)
    Call<SGDetail2_0Entity> getSGDetail2_0(@Query("uid")String uid,@Query("pid")String pid,@Query("vid")String vid,
                                           @Query("type")String type,@Query("shijian") String shijian, @Query("sign") String sign);

    @GET(Constant.STUDY_GROUP_DETAIL_2_0)
    Call<ResponseBody> getSGDetail2_0Json(@Query("uid")String uid,@Query("pid")String pid,@Query("vid")String vid,
                                           @Query("type")String type,@Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 学习小组2.0 加入小组
     */
    @GET(Constant.ADD_STUDY_GROUP_2_0)
    Call<SGJoinEntity> joinStudyGroup2_0(@Query("uid")String uid,@Query("pid")String pid,@Query("vid")String vid,
                                      @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 学习小组2.0 获取心得
     */
    @GET(Constant.STUDY_GROUP_REMARK)
    Call<SGRemarkEntity> getSGRemark(@Query("uid")String uid, @Query("pid")String pid, @Query("vid")String vid,@Query("type")String type, @Query("page")String page,
                                     @Query("pageRow")String pageRow, @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 心得详情
     */
    @GET(Constant.REMARK_DETAIL)
    Call<SGRemarkDetailEntity> getRemarkDetail(@Query("vid")String vid, @Query("id")String id,@Query("uid")String uid,
                                               @Query("shijian") String shijian, @Query("sign") String sign);

    @GET(Constant.REMARK_DETAIL)
    Call<ResponseBody> getRemarkDetailJSON(@Query("vid")String vid, @Query("id")String id,
                                               @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 绑定支付宝
     */
    @GET(Constant.BIND_ALIPAY)
    Call<ResponseBody> bindAlipay(@Query("uid")String uid,@Query("pid")String pid,@Query("vid")String vid,@Query("event")String event,@Query("uname")String uname,@Query("alipay_accout")String alipay_accout,
                                  @Query("alipay_name")String alipay_name, @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 获取支付宝信息
     */
    @GET(Constant.GET_ALIPAY_INFO)
    Call<GetAlipayEntity> getAlipayInfo(@Query("user_id")String user_id, @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 心得点赞
     */
    @FormUrlEncoded
    @POST(Constant.SG_REMARK_LIKE)
    Call<ResponseBody> sgRemarkLike(@Field("user_id") String user_id,@Field("id") String id, @Field("shijian") String shijian, @Field("sign") String sign);

    /**
     * 成败榜
     */
    @GET(Constant.NEW_SG_RANK)
    Call<NewSGRankEntity> getNewRankInfo(@Query("uid")String uid,@Query("pid")String pid,@Query("vid")String vid,@Query("type")String type,@Query("qid")String qid,
                                         @Query("page")String page, @Query("shijian") String shijian, @Query("sign") String sign);

    /**
     * 上传log
     */
    @FormUrlEncoded
    @POST(Constant.UP_LOG)
    Call<ResponseBody> upLogFile(@Field("app") String app, @Field("upfile") String upfile);

    /**
     * 新手礼包
     */
    @GET(Constant.GIFt_BAG)
    Call<GiftBagEntity> getGiftInfo(@Query("user_id")String user_id,@Query("source")String source);

    /**
     * 领取状态
     */
    @GET(Constant.GIFT_DIG)
    Call<GiftBagStatusEntity> getGiftStatus(@Query("user_id")String user_id);

    /**
     * 签到
     */
    @GET(Constant.QIANDAO)
    Call<ScanResultEntity> signUp(@QueryMap Map<String,String> map);

    /**
     * 阅读详情
     */
    @FormUrlEncoded
    @POST(Constant.ESSAY_DERAIL)
    Call<EssayDetailEntity> getEssayDetail(@Field("id")String id,@Field("user_id")String user_id,@Field("shijian") String shijian, @Field("sign") String sign);

    /**
     * 阅读点赞
     */
    @FormUrlEncoded
    @POST(Constant.ESSAY_LIKE)
    Call<EssayLikeEntity> essayLike(@Field("id")String id, @Field("user_id")String user_id, @Field("type")String type, @Field("shijian") String shijian, @Field("sign") String sign);

    /**
     * 获取客服信息
     */
    @GET(Constant.CUSTOMER_INFO)
    Call<CustomerEntity> getCustomerInfo(@Query("uid")String uid, @Query("shijian")String shijian, @Query("sign")String sign);

    /**
     * 客服电话反馈
     */
    @GET(Constant.TEL_RESULT)
    Call<TelResultEntity> telResult(@Query("uid")String uid,@Query("cid")String cid,@Query("conversation_status")String conversation_status,
                                    @Query("source_type")String source_type,@Query("shijian")String shijian, @Query("sign")String sign);

    /**
     * 获取订单提示信息
     * @return
     */
    @GET(".")
    Call<TipsBean> getTips(@Query("c")String c,@Query("m")String m,@Query("type")String type,@Query("shijian")String time,@Query("sign")String sign);
}
