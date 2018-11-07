package com.wh.wang.scroopclassproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity;
import com.wh.wang.scroopclassproject.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 程序启动页面
 * wh
 */
public class WelcomeActivity extends BaseActivity {

    private Handler mHandler = new Handler();
    private Intent mIntent;
    private TextView welcome_tv_one;
    private TextView welcome_tv_two;
    private ImageView mLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initView();
        initData();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        welcome_tv_one = (TextView) findViewById(R.id.welcome_tv_one);
        welcome_tv_two = (TextView) findViewById(R.id.welcome_tv_two);
        mLogo = (ImageView) findViewById(R.id.logo);
        mLogo.setVisibility(View.VISIBLE);
        ani(mLogo,100);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        getWelcomeDatasFromNet();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextToMainActivity();
            }

        }, 2000);
    }

    private void getWelcomeDatasFromNet() {
        RequestParams params = new RequestParams(Constants.welcomeUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getWelcomeDatasFromNet---联网成功---result===" + result);
                //主线程
                processData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getWelcomeDatasFromNet---联网失败---" + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getWelcomeDatasFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getWelcomeDatasFromNet---onFinished---");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void processData(String result) {
        try {

            JSONObject infoObj = new JSONObject(result).getJSONObject("info");
            String contentStr = infoObj.optString("content");
            setData(contentStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData(String contentStr) {
        if (contentStr.indexOf("#") != -1) {
            welcome_tv_one.setVisibility(View.VISIBLE);
            welcome_tv_two.setVisibility(View.VISIBLE);
            String oneStr = contentStr.substring(0, contentStr.indexOf("#"));
            String twoStr = contentStr.substring(contentStr.indexOf("#") + 1, contentStr.length());
            welcome_tv_two.setText(oneStr);
            welcome_tv_one.setText(twoStr);
            ani(welcome_tv_one,300);
            ani(welcome_tv_two,300);
        } else {
            welcome_tv_one.setVisibility(View.GONE);
            welcome_tv_two.setVisibility(View.VISIBLE);
            welcome_tv_two.setText(contentStr);
            ani(welcome_tv_one,300);
        }
    }

    public void ani(View view,int during){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(during);
        view.startAnimation(alphaAnimation);
    }


    private void delayStartActivity() {

    }

    /**
     * 跳转到下一个页面
     */
    private void nextToMainActivity() {
        mIntent = new Intent(this, NewMainActivity.class);
        startActivity(mIntent);
        finish();
    }

    /**
     * 点击直接进入下一个页面
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        nextToMainActivity();
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //把所有的消息和回调移除
        mHandler.removeCallbacksAndMessages(null);
    }
}
