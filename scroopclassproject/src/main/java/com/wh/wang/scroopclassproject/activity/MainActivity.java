package com.wh.wang.scroopclassproject.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.BasePager;
import com.wh.wang.scroopclassproject.base.ReplaceFragment;
import com.wh.wang.scroopclassproject.downloads.DownLoadManager;
import com.wh.wang.scroopclassproject.downloads.DownLoadService;
import com.wh.wang.scroopclassproject.jpush.ExampleUtil;
import com.wh.wang.scroopclassproject.jpush.LocalBroadcastManager;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReplacePageEntity;
import com.wh.wang.scroopclassproject.newproject.utils.PhoneStateUtils;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.viewPager.CourseViewPager;
import com.wh.wang.scroopclassproject.viewPager.MineViewPager;
import com.wh.wang.scroopclassproject.viewPager.ReadViewPager;
import com.wh.wang.scroopclassproject.viewPager.SuperiorViewPager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.wh.wang.scroopclassproject.R.id.main_rb_course;
import static com.wh.wang.scroopclassproject.R.id.main_rg;

public class MainActivity extends FragmentActivity {
    private static final int READ_PHONE_STATE_REQUEST_CODE = 110;
    private RadioGroup mainRg;
    private int position;
    private ArrayList<BasePager> basePagerss;
    private String username;
    private String user_id;
    public static boolean isForeground = false;
    private String mSavePath;
    public static DownLoadManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Intent getIntent = getIntent();
        //int flag = getIntent.getIntExtra("flag",0);
        if (Build.VERSION.SDK_INT >= 23) {
            //检查系统是否已经授予了指定权限
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
            ToastUtils.showToast(MainActivity.this, "请安装SD卡!");
        }
        manager = DownLoadService.getDownLoadManager();
        handler.sendEmptyMessageDelayed(1, 50);
        registerMessageReceiver();
        initView();
        initData();
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*获取下载管理器*/
            manager = DownLoadService.getDownLoadManager();
            /*设置用户ID，客户端切换用户时可以显示相应用户的下载任务*/
            manager.changeUser("27155");
            /*断点续传需要服务器的支持，设置该项时要先确保服务器支持断点续传功能*/
            manager.setSupportBreakpoint(true);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        isForeground = true;
        username = SharedPreferenceUtil.getStringFromSharedPreference(this, "username", "");
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        if (Constants.isFromSearch == true) {
            position = 1;
            mainRg.check(main_rb_course);
            Constants.isFromSearch = false;
            int courseid = SharedPreferenceUtil.getIntValueByKey(MainActivity.this, "courseid", 0);
            Constants.couseLable = courseid;
            Log.e("whwh", "courseid===" + courseid);
        }

        Log.e("whwh", "MainActivity--->user_id==" + user_id);
        if (position == 3) {
            BasePager mPager = basePagerss.get(position);
            mPager.onResumeVisible();
        }
        if (position == 2) {
            BasePager mPager = basePagerss.get(position);
            mPager.onResumeVisible();
        }

    }


    /**
     * 初始化布局
     */
    private void initView() {
        mainRg = (RadioGroup) findViewById(main_rg);
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        if (StringUtils.isNotEmpty(user_id)) {
            String[] tags = {user_id};
            Set<String> tagSet = new HashSet<>(Arrays.asList(tags));
            //给极光推送设置标签和别名
            JPushInterface.setAliasAndTags(MainActivity.this, user_id, tagSet, tagAliasCallback);
        }
    }

    //极光服务器设置别名是否成功的回调
    private final TagAliasCallback tagAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tagSet) {
            switch (code) {
                case 0:
                    Log.e("whwh", "设置别名成功");
                    break;
                default:
                    Log.e("whwh", "设置别名失败");
                    break;
            }
        }
    };


    /**
     * 初始化数据
     */
    private void initData() {
        Log.e("whwh", "MainActivity--->initData");

        //添加各个页面
        basePagerss = new ArrayList<>();
        basePagerss.add(new SuperiorViewPager(this)); //加载精品页面
        basePagerss.add(new CourseViewPager(this)); //加载课程页面
//        basePagerss.add(new MyStudyPager(this)); //加载阅读页面
        basePagerss.add(new ReadViewPager(this)); //加载阅读页面
        basePagerss.add(new MineViewPager(this)); //加载我的页面

        //RadioGroup 监听事件
        mainRg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        mainRg.check(R.id.main_rb_superior);

    }


    /**
     * RadioGroup设置监听
     */
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                default:
                    position = 0;  //默认为精品
                    break;
                case main_rb_course: //课程
                    position = 1;
                    break;
                case R.id.main_rb_read: //阅读
                    position = 2;
                    break;
                case R.id.main_rb_mine: //我的
                    position = 3;
                    break;
            }
            setFragment();
        }
    }


    /**
     * 使用fragment添加到FrameLayout
     */
    private void setFragment() {
        Log.e("whwh", "MainActivity--->setFragment");
        //得到fragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //替换
        fragmentTransaction.replace(R.id.main_flayout_content,
                new ReplaceFragment(getPager()));
        //提交
        fragmentTransaction.commit();
    }

    /**
     * 根据位置获取对应的页面
     */
    private BasePager getPager() {
        BasePager mPager = basePagerss.get(position);
        if (mPager != null && !mPager.isInitData) {
            mPager.baseData();
            mPager.isInitData = true; //防止连续点击某一个RadioButton 连续初始化问题（代码优化）
        } else {
            mPager.resumeData();
        }
        return mPager;
    }

    /**
     * 是否已经退出
     */
    private boolean isExit = false;
    private Handler ehandler = new Handler();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (position != 0) {//不是第一页面
                position = 0;
                mainRg.check(R.id.main_rb_superior);//首页
                return true;
            } else if (!isExit) {
                isExit = true;
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                ehandler.postDelayed(new Runnable() {
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
        ehandler.removeCallbacksAndMessages(null);
        StringUtils.delFolder(mSavePath);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        isForeground = false;
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
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

    public class MessageReceiver extends BroadcastReceiver {

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void replacePage(ReplacePageEntity entity){
        position = entity.getPos();
        setFragment();
        mainRg.check(main_rb_course);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults!=null&&grantResults.length>0){
            doNext(requestCode,grantResults);
        }
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_PHONE_STATE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constant.IMEI = PhoneStateUtils.getIMEI(this);
            }
        }
    }

}
