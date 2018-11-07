package com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.ChangeInfoNewActivity;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.activity.MineMsgActivity;
import com.wh.wang.scroopclassproject.activity.SettingActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.DiscountCouponActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity.VipListActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CustomerEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MessageEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.UserInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CustomerPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MessagePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.UserInfoPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyResultActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyVIPXWalkActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.EnterpriseApplyActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.ExamineDefeatActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.FeedBackActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.InviteActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewOrderWebActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.ServiceDetailActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.utils.SaveBitmapAsync;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils.getStatusBarHeight;

public class BaseInfoFragment extends Fragment implements View.OnClickListener {
    private RoundedImageView mBaseInfoAvatar;
    private TextView mBaseInfoName;
    private ImageView mBaseInfoUserType;
    private TextView mUnloadTitle;
    private ImageView mBaseInfoEdit;
    private String mUserId;
    private String mMobile;
    private RelativeLayout mBaseInfoOrder;
    private RelativeLayout mBaseInfoMsg;
    private TextView mBaseInfoMsgNum;
    private RelativeLayout mBaseInfoFeedback;
    private RelativeLayout mBaseInfoSet;
    private ImageView mBaseInfoScan;
    private RelativeLayout mBaseInfoQy;
    private TextView mBaseInfoQyTitle;
    private RelativeLayout mBaseInfoCoupon;

    private ImageView mTel;
    private TextView mTelTv;
    private ImageView mWx;
    private TextView mWxTv;



    private UserInfoPresenter mUserInfoPresenter = new UserInfoPresenter();
    private MessagePresenter mMessagePresenter = new MessagePresenter();
    private CustomerPresenter mCustomerPresenter = new CustomerPresenter();

    //:0未提交申请  -1企业号正在审核中  1 企业号审核通过   2 企业号审核不通过
    private String mIfcompany;
    private String mIsStaff;
    private String mIs_test_company;

    //2 购买未过期  -2 购买已经过期  3 试用未过期  -3 试用已过期 其他未购买
    private String mIs_company_status;

    //有时间 表示已经购买  为空字符串 表示未购买
    private String mCompany_endshijian;

    private RelativeLayout mVipGy;
    private TextView mVipYgTime;
    private TextView mVipTime;
    private TextView mCompanyTitle;
    private TextView mCompanyTime;
    private LinearLayout mVipOrQy;
    private TextView mImmOpenVip;
    private RelativeLayout mVipCenter;
    private RelativeLayout mCompanyCenter;
    private TextView mVipYgTitle;
    private TextView mVipTitle;
    private RelativeLayout mBaseInfoInvite;
    private String nickname;
    private RelativeLayout mBaseInfoTitle;
    private DialogUtils mDialogUtils;
    private RelativeLayout mPersonService;
    private TextView mServiceCheck;
    private RoundedImageView mServiceAvatar;
    private TextView mServiceName;
    private TextView mTelHint;
    private CustomerEntity.InfoBean mCustomerInfo;

    public BaseInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_info, container, false);
        initView(view);
        initListener();
        initOther();
        return view;
    }

    private void initOther() {
        Constants.wx_api = WXAPIFactory.createWXAPI(getContext(), Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
    }

    private void initListener() {
        mBaseInfoEdit.setOnClickListener(this);
        mBaseInfoOrder.setOnClickListener(this);
        mBaseInfoMsg.setOnClickListener(this);
        mBaseInfoFeedback.setOnClickListener(this);
        mBaseInfoSet.setOnClickListener(this);
        mBaseInfoScan.setOnClickListener(this);
        mBaseInfoQy.setOnClickListener(this);
        mVipGy.setOnClickListener(this);
        mVipCenter.setOnClickListener(this);
        mCompanyCenter.setOnClickListener(this);
        mBaseInfoInvite.setOnClickListener(this);
        mBaseInfoCoupon.setOnClickListener(this);
        mTel.setOnClickListener(this);
        mTelTv.setOnClickListener(this);
        mWx.setOnClickListener(this);
        mWxTv.setOnClickListener(this);
        mServiceCheck.setOnClickListener(this);
    }

    private void initView(View view) {
        mBaseInfoAvatar = (RoundedImageView) view.findViewById(R.id.base_info_avatar);
        mBaseInfoName = (TextView) view.findViewById(R.id.base_info_name);
        mBaseInfoUserType = (ImageView) view.findViewById(R.id.base_info_user_type);
        mUnloadTitle = (TextView) view.findViewById(R.id.unload_title);
        mBaseInfoEdit = (ImageView) view.findViewById(R.id.base_info_edit);
        mBaseInfoOrder = (RelativeLayout) view.findViewById(R.id.base_info_order);
        mBaseInfoMsg = (RelativeLayout) view.findViewById(R.id.base_info_msg);
        mBaseInfoMsgNum = (TextView) view.findViewById(R.id.base_info_msg_num);
        mBaseInfoFeedback = (RelativeLayout) view.findViewById(R.id.base_info_feedback);
        mBaseInfoSet = (RelativeLayout) view.findViewById(R.id.base_info_set);
        mBaseInfoScan = (ImageView) view.findViewById(R.id.base_info_scan);
        mBaseInfoQy = (RelativeLayout) view.findViewById(R.id.base_info_qy);
        mBaseInfoQyTitle = (TextView) view.findViewById(R.id.base_info_qy_title);
        mBaseInfoInvite = (RelativeLayout) view.findViewById(R.id.base_info_invite);
        mBaseInfoTitle = (RelativeLayout) view.findViewById(R.id.base_info_title);
        mBaseInfoCoupon = (RelativeLayout) view.findViewById(R.id.base_info_coupon);
        mPersonService = (RelativeLayout) view.findViewById(R.id.person_service);
        mServiceCheck = (TextView) view.findViewById(R.id.service_check);
        mServiceAvatar = (RoundedImageView) view.findViewById(R.id.service_avatar);
        mServiceName = (TextView) view.findViewById(R.id.service_name);
        mTelHint = (TextView) view.findViewById(R.id.tel_hint);


        mTel = (ImageView) view.findViewById(R.id.tel);
        mTelTv = (TextView) view.findViewById(R.id.tel_tv);
        mWx = (ImageView) view.findViewById(R.id.wx);
        mWxTv = (TextView) view.findViewById(R.id.wx_tv);

        mVipGy = (RelativeLayout) view.findViewById(R.id.vip_gy);
        mVipYgTime = (TextView) view.findViewById(R.id.vip_yg_time);
        mVipTime = (TextView) view.findViewById(R.id.vip_time);
        mCompanyTitle = (TextView) view.findViewById(R.id.company_title);
        mCompanyTime = (TextView) view.findViewById(R.id.company_time);
        mVipOrQy = (LinearLayout) view.findViewById(R.id.vip_or_qy);
        mImmOpenVip = (TextView) view.findViewById(R.id.imm_open_vip);
        mVipCenter = (RelativeLayout) view.findViewById(R.id.vip_center);
        mCompanyCenter = (RelativeLayout) view.findViewById(R.id.company_center);
        mVipYgTitle = (TextView) view.findViewById(R.id.vip_yg_title);
        mVipTitle = (TextView) view.findViewById(R.id.vip_title);

        mBaseInfoTitle.post(new Runnable() {
            @Override
            public void run() {
                int statusBarHeight = getStatusBarHeight(getContext());
                mBaseInfoTitle.measure(0,0);
                ViewGroup.LayoutParams layoutParams = mBaseInfoTitle.getLayoutParams();
                layoutParams.height = mBaseInfoTitle.getHeight()+statusBarHeight;
                mBaseInfoTitle.setLayoutParams(layoutParams);
                mBaseInfoTitle.setPadding(0, statusBarHeight, 0, 0);
            }
        });
        mDialogUtils = new DialogUtils(getContext());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

    }

    @Override
    public void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        if (StringUtils.isNotEmpty(mUserId) && StringUtils.isNotEmpty(mMobile)) {
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "avatar", "");
            nickname = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
            mBaseInfoAvatar.setOnClickListener(null);
            Glide.with(MyApplication.mApplication).load(avatar).centerCrop().into(mBaseInfoAvatar);
            if (StringUtils.isNotEmpty(nickname)) {
                mBaseInfoName.setText(nickname);
            } else {
                mBaseInfoName.setText("点击头像编辑个人资料");
            }
            if (mUserInfoPresenter == null) {
                mUserInfoPresenter = new UserInfoPresenter();
            }
            mBaseInfoEdit.setVisibility(View.VISIBLE);
            mBaseInfoScan.setVisibility(View.VISIBLE);
            mUnloadTitle.setVisibility(View.GONE);
            mBaseInfoUserType.setVisibility(View.VISIBLE);
            getUserType();
            getMsgNum();

        } else {
            mBaseInfoAvatar.setOnClickListener(this);
            mBaseInfoAvatar.setImageResource(R.drawable.mine_touxiang);
            mBaseInfoName.setText("未登录");
            mUnloadTitle.setVisibility(View.VISIBLE);
            mBaseInfoUserType.setVisibility(View.GONE);
            mBaseInfoEdit.setVisibility(View.INVISIBLE);
            mBaseInfoScan.setVisibility(View.INVISIBLE);
            mBaseInfoMsgNum.setVisibility(View.GONE);
            mBaseInfoInvite.setVisibility(View.GONE);
            mPersonService.setVisibility(View.GONE);
            mVipOrQy.setVisibility(View.VISIBLE);
            mVipGy.setVisibility(View.GONE);
            mVipTime.setText("立即开通会员");
            mCompanyTitle.setText("开通企业号");
            mCompanyTime.setText("购买享有更多特权");
        }
    }

    /**
     * 获取用户类型
     */
    public void getUserType() {

        mUserInfoPresenter.getUserInfo(mUserId, new OnResultListener() { //获取用户基本信息

            @Override
            public void onSuccess(Object... value) {
                UserInfoEntity entity = (UserInfoEntity) value[0];
                int isVip = entity.getInfo().getIs_vip();
                mCompany_endshijian = entity.getInfo().getCompany_endshijian();
                mIfcompany = entity.getInfo().getIfcompany();
                mIs_test_company = entity.getInfo().getIs_test_company();
                mIs_company_status = entity.getInfo().getIs_company_status();
                mIsStaff = entity.getInfo().getIs_staff();
                String bao = entity.getInfo().getBao();
                String go_on = entity.getInfo().getGo_on();
                if ("1".equals(entity.getInfo().getIs_display_company())) {
                    mBaseInfoQy.setVisibility(View.VISIBLE);
                } else {
                    mBaseInfoQy.setVisibility(View.GONE);
                }
                if ((isVip==1||isVip==2)) {
                    SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "is_vip", "1");
                    mCustomerPresenter.getCustomerInfo(mUserId, new OnResultListener() { //获取客服信息
                        @Override
                        public void onSuccess(Object... value) {
                            CustomerEntity customerEntity = (CustomerEntity) value[0];
                            if (customerEntity.getInfo()!=null) {
                                mCustomerInfo = customerEntity.getInfo();
                                if (mCustomerInfo!=null) {
                                    if ("2".equals(mCustomerInfo.getIsplay())) {
                                        mPersonService.setVisibility(View.VISIBLE);
                                        if (StringUtils.isNotEmpty(mCustomerInfo.getAvator())) {
                                            Glide.with(getContext()).load(mCustomerInfo.getAvator()).into(mServiceAvatar);
                                            mServiceName.setText("专属顾问："+mCustomerInfo.getName());
                                            mTelHint.setText(mCustomerInfo.getWorking_interval());
                                        }
                                    }else{
                                        mPersonService.setVisibility(View.GONE);
                                    }
                                }

                            }
                        }

                        @Override
                        public void onFault(String error) {

                        }
                    });
                }else{
                    mPersonService.setVisibility(View.GONE);
                    SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "is_vip", "0");
                }

                Log.e("DH_APP_ID","vip:"+isVip);
                if (isVip == 1) {
                    mBaseInfoUserType.setImageResource(R.drawable.xing2);
                    mBaseInfoInvite.setVisibility(View.GONE);
                } else if(isVip == 2){
                    mBaseInfoUserType.setImageResource(R.drawable.xin2);
                }else{
                    mBaseInfoUserType.setImageResource(R.drawable.normalpic);
                    mBaseInfoInvite.setVisibility(View.GONE);
                }

                if ("1".equals(bao)) {
                    mVipYgTitle.setText("会员中心");
                    mVipTitle.setText("会员中心");
                    mImmOpenVip.setText("已是会员");

                    if (entity.getInfo().getMember_end() != null && !"".equals(entity.getInfo().getMember_end()) && !"0000-00-00".equals(entity.getInfo().getMember_end())) {
                        mVipTime.setText(entity.getInfo().getMember_end() + "到期");
                        mVipYgTime.setText(entity.getInfo().getMember_end() + "到期");
                        mVipYgTime.setVisibility(View.VISIBLE);
                        mVipTime.setVisibility(View.VISIBLE);
                    } else {
                        mVipYgTime.setVisibility(View.GONE);
                        mVipTime.setVisibility(View.GONE);
                    }
                } else {
                    if ("1".equals(go_on)) {
                        mVipYgTitle.setText("会员中心");
//                        mVipTitle.setText("立即续费");
                        mImmOpenVip.setText("续费");

                        if (entity.getInfo().getMember_end() != null && !"".equals(entity.getInfo().getMember_end()) && !"0000-00-00".equals(entity.getInfo().getMember_end())) {
                            mVipTime.setText(entity.getInfo().getMember_end() + "到期");
                            mVipYgTime.setText(entity.getInfo().getMember_end() + "到期");
                            mVipYgTime.setVisibility(View.VISIBLE);
                            mVipTime.setVisibility(View.VISIBLE);
                        } else {
                            mVipYgTime.setVisibility(View.GONE);
                            mVipTime.setVisibility(View.GONE);
                        }
                    } else {
                        mVipYgTitle.setText("开通会员");
                        mVipTitle.setText("开通会员");
                        mImmOpenVip.setText("开通会员");
                        mVipTime.setVisibility(View.GONE);
                        mVipYgTime.setVisibility(View.GONE);
                    }
                }
                /**
                 * 1. 未开通  按钮显示文案：开通勺子课堂企业号   跳转至：申请流程页面
                 *    外部按钮文案：开通企业号
                 * 2. 试用版本   按钮显示文案：购买企业号   跳转至：付费弹窗选择购买套餐类型
                 *    外部按钮文案： 购买企业号+试用到期日期
                 * 3. 试用版本试用已结束   按钮显示文案：试用已结束，请付费后使用   跳转至：付费弹窗选择购买套餐类型
                 *    外部按钮文案： 购买企业号+试用已结束
                 * 4. 已开通且已购买    按钮显示文案：续费企业号   跳转至：付费弹窗选择套餐类型
                 *    外部按钮文案： 已开通+试用到期日期
                 * 5. 审核中   按钮显示文案：企业号申请审核中  按钮无法点
                 *    外部按钮文案：审核中
                 * 6. 审核失败   按钮显示文案：审核未通过，点击重新审核      点击跳转至申请流程页面
                 *    外部按钮文案：开通企业号
                 */
                if ("1".equals(mIfcompany)) {
                    if ("1".equals(mIsStaff)) {
                        mVipOrQy.setVisibility(View.GONE);
                        mVipGy.setVisibility(View.VISIBLE);
                        mBaseInfoQyTitle.setText("进入我的企业");
                    } else {
                        mVipOrQy.setVisibility(View.VISIBLE);
                        mVipGy.setVisibility(View.GONE);
                        mCompanyTime.setVisibility(View.VISIBLE);
                        switch (mIs_company_status) {
                            case "2":
                                mBaseInfoQyTitle.setText("进入我的企业");
                                mCompanyTitle.setText("已开通");
                                mCompanyTime.setText(mCompany_endshijian + "到期");
                                break;
                            case "-2":
                                mBaseInfoQyTitle.setText("进入我的企业");
                                mCompanyTitle.setText("购买企业号");
                                mCompanyTime.setText(mCompany_endshijian + "到期");
                                break;
                            case "3":
                                mBaseInfoQyTitle.setText("试用我的企业");
                                mCompanyTitle.setText("购买企业号");
                                mCompanyTime.setText(mCompany_endshijian + "到期");
                                break;
                            case "-3":
                                mBaseInfoQyTitle.setText("试用我的企业");
                                mCompanyTitle.setText("购买企业号");
                                mCompanyTime.setText("试用已到期");
                                break;
                            default:
                                mBaseInfoQyTitle.setText("试用我的企业");
                                mCompanyTitle.setText("开通企业号");
                                mCompanyTime.setText("购买享有更多特权");
                                break;
                        }
                    }

//                    //审核通过 是否为免费
//                    if ("1".equals(mIsStaff)) {
//                        mVipOrQy.setVisibility(View.GONE);
//                        mVipGy.setVisibility(View.VISIBLE);
//                        mBaseInfoQyTitle.setText("进入我的企业");
//                    } else {
//                        mVipOrQy.setVisibility(View.VISIBLE);
//                        mVipGy.setVisibility(View.GONE);
//                        if ("1".equals(mIs_test_company)) {
//                            //试用
//                            mBaseInfoQyTitle.setText("试用我的企业");
//                            mCompanyTitle.setText("购买企业号");
//                        } else {
//                            //购买
//                            if (mCompany_endshijian != null && !"".equals(mCompany_endshijian)&& !"0000-00-00".equals(mCompany_endshijian)) {
//                                mCompanyTime.setVisibility(View.VISIBLE);
//                                mCompanyTime.setText(mCompany_endshijian+"到期");
//
//                                mBaseInfoQyTitle.setText("进入我的企业");
//                                mCompanyTitle.setText("已开通");
//                            } else {
//                                //未购买
//                                mBaseInfoQyTitle.setText("试用我的企业");
//                                mCompanyTitle.setText("购买企业号");
//                                mCompanyTime.setVisibility(View.GONE);
//                            }
//                        }
//                    }
                } else if ("-1".equals(mIfcompany)) {
                    mBaseInfoQyTitle.setText("审核中");

                    mVipOrQy.setVisibility(View.VISIBLE);
                    mVipGy.setVisibility(View.GONE);
                    mCompanyTitle.setText("审核中");
                    mCompanyTime.setVisibility(View.GONE);
                } else {//未开通  审核失败
                    mBaseInfoQyTitle.setText("开通企业号");

                    mVipOrQy.setVisibility(View.VISIBLE);
                    mVipGy.setVisibility(View.GONE);
                    mCompanyTitle.setText("开通企业号");
                    mCompanyTime.setVisibility(View.VISIBLE);
                    mCompanyTime.setText("购买享有更多特权");
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ISVIP", error);
            }
        });
    }

    /**
     * 获取消息数
     */
    private void getMsgNum() {
        mMessagePresenter.getMessage(mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                MessageEntity entity = (MessageEntity) value[0];
                if (entity.getInfo() != null && entity.getInfo().size() > 0) {
                    if (entity.getInfo().get(0).getMess_num() > 0) {
                        mBaseInfoMsgNum.setVisibility(View.VISIBLE);
                        if (entity.getInfo().get(0).getMess_num() > 99) {
                            mBaseInfoMsgNum.setText(entity.getInfo().get(0).getMess_num() + "+");
                        } else {
                            mBaseInfoMsgNum.setText(entity.getInfo().get(0).getMess_num() + "");
                        }
                    } else {
                        mBaseInfoMsgNum.setVisibility(View.GONE);
                    }
                } else {
                    mBaseInfoMsgNum.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.base_info_avatar:
                intent = new Intent(getContext(), LoginNewActivity.class);
                startActivity(intent);
                break;
            case R.id.base_info_edit:
                intent = new Intent(getContext(), ChangeInfoNewActivity.class);
                startActivity(intent);
                break;
            case R.id.base_info_order:
                if (StringUtils.isNotEmpty(mUserId)) {
                    intent = new Intent(getContext(), NewOrderWebActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.base_info_msg:
                if (StringUtils.isNotEmpty(mUserId)) {
                    intent = new Intent(getContext(), MineMsgActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.base_info_feedback:
                intent = new Intent(getContext(), FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.base_info_set:
                intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.base_info_scan:
                new IntentIntegrator(getActivity())
                        .setOrientationLocked(false)
//                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
                break;
            case R.id.base_info_qy:
                if (StringUtils.isNotEmpty(mUserId)) {
                    if (mIfcompany == null) {
                        Toast.makeText(MyApplication.mApplication, "获取企业状态异常", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ("1".equals(mIfcompany)) {
                        //审核通过
                        if ("1".equals(mIsStaff)) {
                            intent = new Intent(getContext(), CompanyInfoActivity.class);
                            startActivityForResult(intent, Constant.SEARCH_REQ);
                        } else {
                            switch (mIs_company_status) {
                                case "2":
                                case "3":
                                    intent = new Intent(getContext(), CompanyInfoActivity.class);
                                    startActivityForResult(intent, Constant.SEARCH_REQ);
                                    break;
                                case "-2":
                                case "-3":
                                    Toast.makeText(MyApplication.mApplication, "您的企业已到期请前往购买", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    intent = new Intent(getContext(), CompanyResultActivity.class);
                                    intent.putExtra("result_type", "0");
                                    startActivity(intent);
                                    break;
                            }
                        }
//                        if ("1".equals(mIsStaff)) {
//                            intent = new Intent(getContext(), CompanyInfoActivity.class);
//                            startActivityForResult(intent, Constant.SEARCH_REQ);
//                        } else {
//                            if (mCompany_endshijian != null && !"".equals(mCompany_endshijian)) {
//                                //购买
//                                intent = new Intent(getContext(), CompanyInfoActivity.class);
//                                startActivityForResult(intent, Constant.SEARCH_REQ);
//                            } else {
//                                //未购买
//                                intent = new Intent(getContext(), CompanyResultActivity.class);
//                                intent.putExtra("result_type", "0");
//                                startActivity(intent);
//
//                            }
//                        }

                    } else if ("2".equals(mIfcompany)) {
                        //未通过
                        intent = new Intent(getContext(), ExamineDefeatActivity.class);
                        startActivity(intent);
                    } else if ("-1".equals(mIfcompany)) {
                        //审核中
                        intent = new Intent(getContext(), EnterpriseApplyActivity.class);
                        intent.putExtra("state", "1");
                        startActivityForResult(intent, Constant.SEARCH_REQ);
                    } else if ("0".equals(mIfcompany)) {
                        //未注册
                        intent = new Intent(getContext(), EnterpriseApplyActivity.class);
                        intent.putExtra("state", "0");
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.vip_gy:
            case R.id.vip_center:
                if (StringUtils.isNotEmpty(mUserId)) {
                    isPayCourseOrAction=4;
                    intent = new Intent(getContext(), VipListActivity.class);
//                    intent.putExtra("event_id","1407");
//                    intent.putExtra("flag", "0");
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.company_center:
                if (StringUtils.isNotEmpty(mUserId)) {
                    String state = "-1";  //-1 异常/审核  0 注册 1 付费 2
                    if (mIfcompany == null) {
                        Toast.makeText(MyApplication.mApplication, "获取企业状态异常", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ("1".equals(mIfcompany)) {
                        //审核通过
//                        String state = "-1";
//                        if ((mCompany_endshijian != null && !"".equals(mCompany_endshijian)) || !"1".equals(mIs_test_company)) {
//                            //购买
//                            intent = new Intent(getContext(), CompanyInfoActivity.class);
//                            startActivityForResult(intent, Constant.SEARCH_REQ);
//                        } else {
//                            //未购买 TODO
//                            intent = new Intent(getContext(), CompanyVIPXWalkActivity.class);
//                            intent.putExtra("flag", "1");
//                            startActivity(intent);
//                        }
                        if ("2".equals(mIs_company_status)) {
                            break;
                        }
                        //购买
                        state = "1";
                    } else if ("0".equals(mIfcompany) || "2".equals(mIfcompany)) {
                        //未注册
                        state = "0";
                    } else if ("-1".equals(mIfcompany)) {
                        //审核中
                        state = "-1";
                    }
                    intent = new Intent(getContext(), CompanyVIPXWalkActivity.class);
                    intent.putExtra("flag", "1");
                    intent.putExtra("state", state);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.base_info_invite:
                initInviteDialog();
                break;
            case R.id.my_invite:
                intent = new Intent(getContext(), InviteActivity.class);
                startActivity(intent);
                break;
            case R.id.share_wechat:
                SHARE_FLAG = 0;
                captureWebView(inviteWb);
                break;
            case R.id.share_pyq:
                SHARE_FLAG = 1;
                captureWebView(inviteWb);
                break;
            case R.id.save_local:
                SHARE_FLAG = 2;
                captureWebView(inviteWb);
                break;
            case R.id.base_info_coupon:
                if (StringUtils.isNotEmpty(mUserId)) {
                    intent = new Intent(getContext(), DiscountCouponActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tel:
            case R.id.tel_tv:
                mDialogUtils.showServiceTel(mCustomerInfo.getPhone(), 0, mCustomerInfo.getWechat_number(),
                        mCustomerInfo.getQr_code(), new DialogUtils.OnTelResultClickListener() {
                            @Override
                            public void onTelResultClick(String state) {
                                mCustomerPresenter.telResult(mUserId,mCustomerInfo.getCustomer_id(),state);
                            }
                        });
                break;
            case R.id.wx:
            case R.id.wx_tv:
                if (mCustomerInfo!=null) {
                    mDialogUtils.showCopyWxDig(mCustomerInfo.getAvator(),mCustomerInfo.getWechat_number(),mCustomerInfo.getName(),mCustomerInfo.getQr_code());
                }
                break;
            case R.id.service_check:
                intent = new Intent(getContext(), ServiceDetailActivity.class);
                if (mCustomerInfo!=null) {
                    intent.putExtra("info",mCustomerInfo);
                }
                startActivity(intent);
                break;
        }
    }

    private Dialog mInviteDig;
    private WebView inviteWb;
    private int SHARE_FLAG = -1; //0 微信  1 朋友圈  2 本地

    private void initInviteDialog() {

        mInviteDig = new Dialog(getContext(), R.style.InviteDialogStyle);
        View inflate = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_invite, null);

        TextView myInvite = (TextView) inflate.findViewById(R.id.my_invite);
        LinearLayout shareWechat = (LinearLayout) inflate.findViewById(R.id.share_wechat);
        LinearLayout sharePyq = (LinearLayout) inflate.findViewById(R.id.share_pyq);
        LinearLayout saveLocal = (LinearLayout) inflate.findViewById(R.id.save_local);
        TextView inviteCancel = (TextView) inflate.findViewById(R.id.invite_cancel);
        inviteWb = (WebView) inflate.findViewById(R.id.invite_wb);
        myInvite.setOnClickListener(this);
        shareWechat.setOnClickListener(this);
        sharePyq.setOnClickListener(this);
        saveLocal.setOnClickListener(this);
        inviteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInviteDig != null) {
                    mInviteDig.dismiss();
                }
            }
        });
        setWebView(inviteWb, "https://www.shaoziketang.com/pcshaozi/VIP_share.html?parent_id=" + mUserId + "&nickname=" + nickname);
        //将布局设置给Dialog
        mInviteDig.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mInviteDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getActivity().getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mInviteDig.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (inviteWb != null) {
                    inviteWb.destroy();
                }
            }
        });
        if (mInviteDig != null) {
            mInviteDig.show();
        }
    }

    private void setWebView(WebView wb, String url) {
        WebSettings settings = wb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setTextSize(WebSettings.TextSize.SMALLER);
        settings.setTextZoom(100);
        wb.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        wb.loadUrl(url);
    }


    private void captureWebView(WebView webView) {

        Picture snapShot = webView.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),
                snapShot.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        if (SHARE_FLAG == 2) {
            new SaveBitmapAsync(getActivity(), "sz_vip_invite.jpg", true).execute(bmp);
        } else {
            ShareUtil.wechatShareImg(Constants.wx_api, bmp, SHARE_FLAG, getContext(), true);
        }
        SHARE_FLAG = -1;
    }


//    class SaveBitmapAsync extends AsyncTask<Bitmap,Void,Void> {
//        private Activity activity;
//        private String fileName;
//        public SaveBitmapAsync() {
//        }
//
//        public SaveBitmapAsync(Activity activity, String fileName) {
//            this.activity = activity;
//            this.fileName = fileName;
//        }
//
//        @Override
//        protected Void doInBackground(Bitmap... bitmaps) {
//            saveImageToGallery(bitmaps[0]);
//            publishProgress();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Toast.makeText(MyApplication.mApplication, "保存成功", Toast.LENGTH_SHORT).show();
//        }
//
//        public void saveImageToGallery(Bitmap bmp) {
//            if(bmp==null) return;
//            // 首先保存图片
//            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
////        String fileName = file.getAbsolutePath();
////        File appDir = new File(file ,fileName);
////        if (!appDir.exists()) {
////            appDir.mkdirs();
////        }
//            File currentFile = new File(file, fileName);
//            if(currentFile.exists()){
//                currentFile.delete();
//                try {
//                    currentFile.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(currentFile);
//                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                bmp.recycle();
//                fos.flush();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (fos != null) {
//                        fos.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                    Uri.fromFile(new File(currentFile.getPath()))));
//        }
//
//    }


}
