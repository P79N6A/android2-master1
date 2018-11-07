package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.QuestionActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.downloads.DownLoadManager;
import com.wh.wang.scroopclassproject.downloads.DownLoadService;
import com.wh.wang.scroopclassproject.jpush.ExampleUtil;
import com.wh.wang.scroopclassproject.jpush.LocalBroadcastManager;
import com.wh.wang.scroopclassproject.jpush.TagAliasOperatorHelper;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReplacePageEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.activity.DiscountCouponActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0.StudyGroupHomeActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity.VipListActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckUpdateEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ScanEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ScanResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckUpdatePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.ScanPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag.BaseInfoFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag.CourseFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag.HomeFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag.MyStudyFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag.StudyGroupFragment;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.utils.PhoneStateUtils;
import com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.utils.UpdateManager;
import com.wh.wang.scroopclassproject.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.wh.wang.scroopclassproject.jpush.TagAliasOperatorHelper.ACTION_DELETE;
import static com.wh.wang.scroopclassproject.jpush.TagAliasOperatorHelper.ACTION_SET;

public class NewMainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, HomeFragment.OnSearchKeyClickListener, View.OnClickListener {
    private static final int READ_PHONE_STATE_REQUEST_CODE = 110;
    private FrameLayout mContent;
    private RadioGroup mMenu;
//    private ImageView mGiftBag;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentManager mSupportFragmentManager;
    private int pos;
    private int mShowFunction = 0;
    private RadioButton mMainBoutique;
    private RadioButton mMainStudyGroup;
    private RadioButton mMainCourse;
    private RadioButton mMainMyStudy;
    private RadioButton mMainMyInfo;
    private RadioButton[] mRbs;

    private RelativeLayout mServiceContent;
    private ImageView mClose;
    private RoundedImageView mAvatar;
    private TextView mDes;
    private TextView mCheck;

    public static boolean isForeground = false;
    private String mSavePath;
    public static DownLoadManager manager;
    private CheckUpdatePresenter mCheckUpdatePresenter = new CheckUpdatePresenter();
    private String mIs_display_company;
//    private TextView mMainStudyGroup;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    /*获取下载管理器*/
                    manager = DownLoadService.getDownLoadManager();
                    /*设置用户ID，客户端切换用户时可以显示相应用户的下载任务*/
                    if (manager == null) {
                        manager = DownLoadService.getDownLoadManager();
                    }
                    try {
                        manager.changeUser(mUser_Id);
                    /*断点续传需要服务器的支持，设置该项时要先确保服务器支持断点续传功能*/
                        manager.setSupportBreakpoint(true);
                    } catch (Exception e) {
                        manager = DownLoadService.getDownLoadManager();
                    }

                    break;
                case 2:
                    String version = (String) msg.obj;
                    if (isUpdate(version)) {
                        showAlertDialog();
                        SharedPreferenceUtil.clearInfo(NewMainActivity.this);
                    }
                    break;
            }
        }
    };
    private String mUser_Id;
    private String versionName;
    private String alj_id;
    private DialogUtils mDialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser_Id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            /**
             * 设置标题栏位子颜色
             */
            StatusBarUtils.setStatusTextColor(true, this);
        }
        setContentView(R.layout.activity_new_main);
        initView();
        initFragment();
        initListener();
        initOther();
//        String gift_bag = SharedPreferenceUtil.getStringFromSharedPreference(NewMainActivity.this,"gift_bag","0");
//        if(StringUtils.isEmpty(mUser_Id)||"1".equals(gift_bag)){
//            SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication,"gift_bag","0");
//            showGiftDig();
//        }else{
//            checkUpdate();
//        }
        checkUpdate();
    }



    private void setImmersionStatusBar(View view) {
        if (view == null) {
            try {
                Log.e("DH_EXCEPTION", "view is null");
                throw new Exception("view is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            /**
             * 设置标题栏位子颜色
             */
            StatusBarUtils.setStatusTextColor(true, this);

//            int statusBarHeight = getStatusBarHeight(this);
//            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//            layoutParams.height = view.getMeasuredHeight()+statusBarHeight;
//            view.setLayoutParams(layoutParams);
//
//            view.setPadding(0, statusBarHeight, 0, 0);

        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }



    private Set<String> tags = new HashSet<String>();
    private static int sequence = 1;

    TagAliasOperatorHelper.TagAliasBean AliasBean;
    TagAliasOperatorHelper.TagAliasBean TagsBean;
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        isForeground = true;
        mUser_Id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
//        String gift_bag = SharedPreferenceUtil.getStringFromSharedPreference(NewMainActivity.this,"gift_bag","0");
//        if ("1".equals(gift_bag)&&pos==0) {
//            mGiftBag.setVisibility(View.VISIBLE);
//        }else{
//            mGiftBag.setVisibility(View.GONE);
//        }
        if (StringUtils.isNotEmpty(mUser_Id)) {
            if (AliasBean==null) {
                sequence++;
                AliasBean = new TagAliasOperatorHelper.TagAliasBean();
                AliasBean.action = ACTION_SET;
                AliasBean.alias = mUser_Id;
                AliasBean.isAliasAction = true;
                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, AliasBean);
            }

            if (TagsBean==null) {
                sequence++;
                TagsBean = new TagAliasOperatorHelper.TagAliasBean();
                TagsBean.action = ACTION_SET;
                tags.add(mUser_Id);
                TagsBean.tags = tags;
                TagsBean.isAliasAction = false;
                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, TagsBean);
            }
        }else{
            if (AliasBean!=null) {
                sequence++;
                AliasBean = new TagAliasOperatorHelper.TagAliasBean();
                AliasBean.action = ACTION_DELETE;
                AliasBean.alias = mUser_Id;
                AliasBean.isAliasAction = true;
                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, AliasBean);
            }

            if (TagsBean!=null) {
                sequence++;
                TagsBean = new TagAliasOperatorHelper.TagAliasBean();
                TagsBean.action = ACTION_DELETE;
                tags.add(mUser_Id);
                TagsBean.tags = tags;
                TagsBean.isAliasAction = false;
                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, TagsBean);
            }
        }


        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
//        if (StringUtils.isNotEmpty(mUser_Id) && tags.size() == 0) {
//            tags.add(mUser_Id);//设置tag
//            //上下文、别名【Sting行】、标签【Set型】、回调
//            JPushInterface.setAliasAndTags(this, mUser_Id, tags
//                    , new TagAliasCallback() {
//                        @Override
//                        public void gotResult(int i, String s, Set<String> set) {
//                            Log.e("DH_JPushInterface", i + "  " + s + "  userid:" + mUser_Id);
//
//                        }
//                    });
//        }
//        Log.e("DH_XM_PUSH", "rid:"+MiPushClient.getRegId(getApplicationContext()));
    }

    private void initOther() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);
            }
        }

        StringUtils.isGrantExternalRW(this);
        EventBus.getDefault().register(this);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String sdPath = Environment.getExternalStorageDirectory() + "/";
            mSavePath = sdPath + "jikedownload";
            File dir = new File(mSavePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

        } else {
            ToastUtils.showToast(this, "请安装SD卡!");
        }
        manager = DownLoadService.getDownLoadManager();
        mHandler.sendEmptyMessageDelayed(0, 50);
        registerMessageReceiver();
        mUser_Id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");

    }

    private void initFragment() {
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new CourseFragment());
        mFragmentList.add(new StudyGroupFragment());
        mFragmentList.add(new MyStudyFragment());
        mFragmentList.add(new BaseInfoFragment());
        mSupportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, mFragmentList.get(0));
        fragmentTransaction.commit();
    }

    private void initListener() {
        mMenu.setOnCheckedChangeListener(this);
//        mGiftBag.setOnClickListener(this);
    }

    private void initView() {
        mContent = (FrameLayout) findViewById(R.id.content);
        mMenu = (RadioGroup) findViewById(R.id.menu);
        mMainBoutique = (RadioButton) findViewById(R.id.main_boutique);
        mMainCourse = (RadioButton) findViewById(R.id.main_course);
        mMainMyStudy = (RadioButton) findViewById(R.id.main_my_study);
        mMainMyInfo = (RadioButton) findViewById(R.id.main_my_info);
        mMainStudyGroup = (RadioButton) findViewById(R.id.main_study_group);


        mServiceContent = (RelativeLayout) findViewById(R.id.service_content);
        mClose = (ImageView) findViewById(R.id.close);
        mAvatar = (RoundedImageView) findViewById(R.id.avatar);
        mDes = (TextView) findViewById(R.id.des);
        mCheck = (TextView) findViewById(R.id.check);
//        mGiftBag = (ImageView) findViewById(R.id.gift_bag);
        mRbs = new RadioButton[]{mMainBoutique, mMainCourse, mMainStudyGroup, mMainMyStudy, mMainMyInfo};
        mDialogUtils = new DialogUtils(this);
        Button button = (Button) findViewById(R.id.bt_clear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(NewMainActivity.this,"清除成功！");
                WXPayEntryActivity.sendRequestWithHttpURLConnection();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.main_boutique:
                pos = 0;
                break;
            case R.id.main_course:
                pos = 1;
                break;
            case R.id.main_study_group:
                pos = 2;
                break;
            case R.id.main_my_study:
                pos = 3;
                break;
            case R.id.main_my_info:
                pos = 4;
                break;
        }
        replaceFrag(pos);
    }

    private String mLabelID = "29";

    private void replaceFrag(int pos) {
//        mGiftBag.setVisibility(View.GONE);
//        if (pos==0) {
//            String gift_bag = SharedPreferenceUtil.getStringFromSharedPreference(NewMainActivity.this,"gift_bag","0");
//            if(StringUtils.isEmpty(mUser_Id)||"1".equals(gift_bag)){
//                mGiftBag.setVisibility(View.VISIBLE);
//            }
//        }
        if (pos == 4) {
            if (mFragmentList.get(pos).isAdded()) {
                BaseInfoFragment baseInfoFragment = (BaseInfoFragment) mFragmentList.get(pos);
                baseInfoFragment.getUserType();
            }
        }

        if (pos == 2) {
            if (mFragmentList.get(pos).isAdded()) {
                StudyGroupFragment studyGroupFragment = (StudyGroupFragment) mFragmentList.get(pos);
                studyGroupFragment.accessNet();
            }
        }

        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        if (!mFragmentList.get(pos).isAdded()) {
            fragmentTransaction.add(R.id.content, mFragmentList.get(pos));
            if (mFragmentList.get(pos).isHidden()) {
                fragmentTransaction.show(mFragmentList.get(pos));
            }
        } else {
            if (mFragmentList.get(pos).isHidden()) {
                fragmentTransaction.show(mFragmentList.get(pos));
            }
        }

        for (int i = 0; i < mFragmentList.size(); i++) {
            if (i != pos) {
                fragmentTransaction.hide(mFragmentList.get(i));
            }
        }
        fragmentTransaction.commit();
        if (pos == 1) {
            CourseFragment courseFragment = (CourseFragment) mFragmentList.get(pos);
            courseFragment.setLabel(mLabelID == null ? "" : mLabelID);
            mLabelID = "";
        }
        if (pos == 3 && toMyStudy) {
            toMyStudy = false;
            MyStudyFragment studyFragment = (MyStudyFragment) mFragmentList.get(pos);
            studyFragment.setLocation(myStudy_location);
        }
//        if (mShowFunction == 1) {
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mShowFunction = 0;
//                    showFunctionDig();
//                }
//            }, 300);
//        }
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.wh.wang.scroopclassproject.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onSearchKeyClick(String key) {
        CourseFragment courseFragment = (CourseFragment) mFragmentList.get(1);
        courseFragment.setSearchKey(key);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.main_study_group:
                intent = new Intent(this, StudyGroupHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.gift_bag:
//                if (StringUtils.isNotEmpty(mUser_Id)) {
//                    mGiftBag.setVisibility(View.GONE);
//                    intent = new Intent(NewMainActivity.this, GiftBagActivity.class);
//                }else{
//                    mGiftBag.setVisibility(View.VISIBLE);
//                    intent = new Intent(NewMainActivity.this, LoginNewActivity.class);
//                }
//                startActivity(intent);
                break;
        }
    }

//    private void showGiftDig() {
//        mGiftBag.setVisibility(View.GONE);
//        mDialogUtils.showGiftBagDig(new DialogUtils.OnGiftBagClickListener() {
//            @Override
//            public void onGetGiftBagClick() {
//
//                Intent intent = null;
//                if (StringUtils.isNotEmpty(mUser_Id)) {
//                    mGiftBag.setVisibility(View.GONE);
//                    intent = new Intent(NewMainActivity.this, GiftBagActivity.class);
//                }else{
//                    mGiftBag.setVisibility(View.VISIBLE);
//                    intent = new Intent(NewMainActivity.this, LoginNewActivity.class);
//                }
//                startActivity(intent);
//
//            }
//
//            @Override
//            public void onGiftBagDismissClick() {
//                if(pos==0){ //防止其他fragment显示礼包
//                    SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication,"gift_bag","1");
//                    mGiftBag.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//    }

    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    //主页更新提示通知跳转"我的学习" 标志
    private boolean toMyStudy = false;
    //主页更新提示通知跳转"我的学习" fragment展示
    private int myStudy_location = -1;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void replacePage(ReplacePageEntity entity) {
        //获取切换pos
        pos = entity.getPos();
        if (pos == 3) {
            toMyStudy = true;
            myStudy_location = entity.getLocation();
        }
//        replaceFrag(pos);
        if (StringUtils.isNotEmpty(entity.getLabel_id())) {
            //获取五力id并跳转定位到课程frag
            mLabelID = entity.getLabel_id();
            CourseFragment courseFragment = (CourseFragment) mFragmentList.get(1);
            courseFragment.setLabel(mLabelID);
        }
        mRbs[pos].setChecked(true);

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void live(TKZBEntity entity){
//        Intent intent = new Intent(this, LiveCacheActivity.class);
//        startActivity(intent);
//    }

    /**
     * 权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0) {
            doNext(requestCode, grantResults);
        }
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_PHONE_STATE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Constant.IMEI = PhoneStateUtils.getIMEI(this);
                SharedPreferenceUtil.putStringValueByKey(MyApplication.mApplication, "imei", PhoneStateUtils.getIMEI(this));
            }
        }
    }

    /**
     * 是否已经退出
     */
    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pos != 0) {//不是第一页面
                pos = 0;
                mRbs[pos].setChecked(true);//首页
                return true;
            } else if (!isExit) {
                isExit = true;
                Toast.makeText(MyApplication.mApplication, "再按一次退出", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        //方式二：移除所有的未被执行的消息
        mHandler.removeCallbacksAndMessages(null);
        StringUtils.delFolder(mSavePath);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        isForeground = false;
    }

    private void checkUpdate() {
        try {
            versionName = getPackageManager().getPackageInfo("com.wh.wang.scroopclassproject", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionName = "-1";
        }
        Log.e("DH_VERSION", versionName + "");
        mCheckUpdatePresenter.checkUpdate(mUser_Id, "1", new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                CheckUpdateEntity entity = (CheckUpdateEntity) value[0];
//                alj_id = entity.getEvent();

                String version = entity.getVersion();
                Log.e("DH_VERSION", " isPlay:" + entity.getCoupon().getIspaly());
                if (entity.getCoupon() != null && "1".equals(entity.getCoupon().getIspaly())) {
//                    Toast.makeText(NewMainActivity.this, "展示优惠券", Toast.LENGTH_SHORT).show();
                    if (entity.getCoupon().getCoupon() != null) {
                        CheckUpdateEntity.CouponBean.Coupon coupon = entity.getCoupon().getCoupon();
                        showCoupon(coupon);
                    }
                } else {
                    if (isUpdate(version)) {
                        showAlertDialog();
                        SharedPreferenceUtil.clearInfo(NewMainActivity.this);
                    }
                }
//                if (isUpdate(version)) {
//                    showAlertDialog();
//                    SharedPreferenceUtil.clearInfo(NewMainActivity.this);
//                } else {
//                    if ("1".equals(entity.getIs_display_vip())) {
//                        showVIPDig();
////                        showWEBDig();
//                    }
////                    if ("0".equals(entity.getIs_display_event())) {
////                        if (mALJDig == null) {
////                            mALJDig = new Dialog(NewMainActivity.this, R.style.MyDialog);
////                        }
////                        showALJDig();
////                    }
////                    mIs_display_company = entity.getIs_display_company();
////                    if ("1".equals(mIs_display_company)) {
////                        if (mCompanyDig == null) {
////                            mCompanyDig = new Dialog(NewMainActivity.this, R.style.MyDialog);
////                        }
////                        if (!mCompanyDig.isShowing()) {
////                            showCompanyDig();
////                        }
////                    }
//                }

//                mIs_display_company = entity.getIs_display_company();
//                if("1".equals(mIs_display_company)){
//                    if (mCompanyDig == null) {
//                        mCompanyDig = new Dialog(NewMainActivity.this, R.style.MyDialog);
//                    }
//                    if (!mCompanyDig.isShowing()) {
//                        showCompanyDig();
//                    }
//                }else{
//                    Message msg = mHandler.obtainMessage();
//                    msg.obj = entity.getVersion();
//                    msg.what = 2;
//                    mHandler.sendMessage(msg);
//                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR_VERSION", error);
            }
        });
    }

    private Dialog mCouponDig;

    private void showCoupon(final CheckUpdateEntity.CouponBean.Coupon coupon) {
        final View dView = LayoutInflater.from(this).inflate(R.layout.dig_coupon_main, null, false);
        if (mCouponDig == null) {
            mCouponDig = new Dialog(this, R.style.MyDialog);
        }
        mCouponDig.setContentView(dView);
        final ImageView couponImg = (ImageView) dView.findViewById(R.id.coupon);


        Glide.with(MyApplication.mApplication).load(coupon.getImg()).into(couponImg);
//        int SW = ScreenUtils.getScreenWidth(this);
//        loadIntoUseFitWidth(NewMainActivity.this,coupon.getImg(), (int) (SW*Dig_rate),couponImg);
        couponImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(coupon.getUrl_type())) {
                    //弹窗消失
                    mCouponDig.dismiss();
                } else if ("1".equals(coupon.getUrl_type())) {
                    //列表页
                    mCouponDig.dismiss();
                    if (StringUtils.isNotEmpty(mUser_Id)) {
                        Intent intent = new Intent(NewMainActivity.this, DiscountCouponActivity.class);
                        startActivity(intent);
                    }
                } else if ("2".equals(coupon.getUrl_type())) {
                    //跳转详情
                    mCouponDig.dismiss();
                    if ("1".equals(coupon.getCoupon_type())) { //视频
                        Intent intent = new Intent(NewMainActivity.this, NewVideoInfoActivity.class);
                        intent.putExtra("id", coupon.getItem_id());
                        startActivity(intent);
                    } else if ("2".equals(coupon.getCoupon_type())) {
                        Intent intent = new Intent(NewMainActivity.this, NewEventDetailsActivity.class);
                        intent.putExtra("event_id", coupon.getItem_id());
                        startActivity(intent);
                    }
                } else if ("3".equals(coupon.getUrl_type())) {
                    mCouponDig.dismiss();
                    Intent intent = new Intent(NewMainActivity.this, VipListActivity.class);
                    startActivity(intent);
                } else if ("4".equals(coupon.getUrl_type())) {
                    mCouponDig.dismiss();
                    Intent intent = new Intent(NewMainActivity.this, VipListActivity.class);
                    startActivity(intent);
                }
            }
        });
        mCouponDig.show();
    }

    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final int d_w, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        float scale = (float) d_w / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.width = d_w;
                        params.height = vh;
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .into(imageView);
    }


    private boolean isUpdate(String mVersion_code) {
        int serverVersion = Integer.parseInt(mVersion_code);
        int localVersion = 1;

        try {
            localVersion = getPackageManager().getPackageInfo("com.wh.wang.scroopclassproject", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (serverVersion > localVersion)
            return true;
        else
            return false;
    }


    private AlertDialog dialog;

    private void showAlertDialog() {
        if (!isFinishing()) { //当前activity is Running
            dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogStyle)).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setView(LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.version_dialog, null));
            dialog.show();
            dialog.getWindow().setContentView(R.layout.version_dialog);
            Button btn_version_result = (Button) dialog.findViewById(R.id.btn_version_result);
            btn_version_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String mSavePath = Environment.getExternalStorageDirectory() + "/jikedownload";
                    File dir = new File(mSavePath);
                    if (dir.exists()) {
                        new UpdateManager(NewMainActivity.this).checkUpdate();
                    }
                }
            });

            TextView btn_version_question = (TextView) dialog.findViewById(R.id.btn_version_question);
            btn_version_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NewMainActivity.this, QuestionActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.SEARCH_RES) {
            if (data.getIntExtra("page", 1) == 0) {
                mRbs[0].setChecked(true);
            } else {
                mLabelID = data.getStringExtra("id");
                if (pos == 1) {
                    CourseFragment courseFragment = (CourseFragment) mFragmentList.get(1);
                    courseFragment.setLabel(mLabelID);
                } else {
                    mRbs[1].setChecked(true);
                }
                mRbs[1].setChecked(true);
            }

        }

        /**
         * 二维码扫描结果反馈
         */
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(MyApplication.mApplication, "未扫描到", Toast.LENGTH_LONG).show();
            } else {
                ScanPresenter scanPresenter = new ScanPresenter();

                String url = result.getContents();
                Log.e("QR_URL",url);
                if(url.contains("wapshaozi/event_qr.html")){
                    if(url.contains("?")){
                        String[] split = url.split("\\?");
                        if(split.length>1){
                            String[] params = split[1].split("&");
                            Map<String,String> paramMap = new HashMap<>();
                            paramMap.put("staff",mUser_Id);
                            for (int i = 0; i < params.length; i++) {
                                String param = params[i];
                                paramMap.put(param.split("=")[0],param.split("=")[1]);
                                Log.e("QR_URL","key:"+param.split("=")[0]+"  value:"+param.split("=")[1]);
                            }
                            scanPresenter.signUp(paramMap, new OnResultListener() {
                                @Override
                                public void onSuccess(Object... value) {
                                    ScanResultEntity scanResultEntity = (ScanResultEntity) value[0];

                                    int status = -1;
                                    try {
                                        status = Integer.parseInt(scanResultEntity.getCode());
                                    }catch (Exception e){
                                        status = -1;
                                    }
                                    Intent intent = new Intent(NewMainActivity.this,ScanResultActivity.class);

                                    switch (status){
                                        case ScanResultActivity.SUCCESS:
                                            if (scanResultEntity.getInfo()!=null) {
                                                intent.putExtra("name",scanResultEntity.getInfo().getName());
                                                intent.putExtra("tel",scanResultEntity.getInfo().getTel());
                                            }

                                        case ScanResultActivity.SIGNED:
                                            if (scanResultEntity.getInfo()!=null) {
                                                intent.putExtra("title",scanResultEntity.getInfo().getTitle());
                                            }

                                        case ScanResultActivity.FAIL:
                                            intent.putExtra("type",status);
                                            startActivity(intent);
                                            break;
                                        default:
                                            break;
                                    }


                                }

                                @Override
                                public void onFault(String error) {
                                    Intent intent = new Intent(NewMainActivity.this,ScanResultActivity.class);
                                    intent.putExtra("type",ScanResultActivity.FAIL);
                                    startActivity(intent);
                                }
                            });
                            //TODO 网络请求
                        }else{
                            Intent intent = new Intent(NewMainActivity.this,ScanResultActivity.class);
                            intent.putExtra("type",ScanResultActivity.FAIL);
                            startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(NewMainActivity.this,ScanResultActivity.class);
                        intent.putExtra("type",ScanResultActivity.FAIL);
                        startActivity(intent);
                    }
                }else if (url.contains("/")) {
                    int index = url.lastIndexOf("/");
                    String u = url.substring(0, index + 1);
                    if ("http://www.shaoziketang.com/event/detail/".equals(u)) {
                        final String id = url.substring(index + 1, url.length());
                        if (StringUtils.isNotEmpty(id)) {
                            mUser_Id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
                            scanPresenter.getScanResult(id, mUser_Id, new OnResultListener() {
                                @Override
                                public void onSuccess(Object... value) {
                                    ScanEntity entity = (ScanEntity) value[0];
                                    if (entity.getStatus() == 1) {
                                        Intent intent = new Intent(NewMainActivity.this, ScanResultActivity.class);
                                        intent.putExtra("type", ScanResultActivity.OLD);
                                        intent.putExtra("id", id);
                                        intent.putExtra("name", entity.getInfo().getName());
                                        intent.putExtra("tel", entity.getInfo().getTel());
                                        intent.putExtra("title", entity.getInfo().getTitle());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MyApplication.mApplication, entity.getInfo().getError(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFault(String error) {
                                    Log.e("DH_SCAN", error);
                                    Toast.makeText(MyApplication.mApplication, "数据异常", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(MyApplication.mApplication, "二维码过期或无效，请扫描正确的二维码", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(MyApplication.mApplication, "二维码过期或无效，请扫描正确的二维码", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MyApplication.mApplication, "二维码过期或无效，请扫描正确的二维码", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /**
         * 企业号跳转标识
         */
        setIntent(intent);
        String flag = getIntent().getStringExtra("flag");
        if ("0".equals(flag)) {
            if (mRbs != null) {
                mRbs[0].setChecked(true);
            }
        }else if("3".equals(flag)){
            if (mRbs != null) {
                mRbs[3].setChecked(true);
            }
        }
    }


}
