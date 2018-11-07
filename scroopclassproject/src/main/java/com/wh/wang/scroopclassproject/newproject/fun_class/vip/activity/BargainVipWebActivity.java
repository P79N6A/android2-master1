package com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventInfoActivity;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

public class BargainVipWebActivity extends BaseActivity {

    private WebView mBargainWeb;
    private WebSettings mSettings;
    private String mUserId;
    private Bundle mBundle;
    private String mShareFlag = "wapshaozi/wap_x.html"; //http://www.shaoziketang.com/wapshaozi/wap_x.html
    private String mBuyFlag = "wap_vip_new_know.html"; //http://www.shaoziketang.com//wapshaozi/information-fill.html?eventid=1407&title=新知会员&original_price=899
    private String mBuyOther = "information-fill.html";
    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_bargain_vip_web;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mBundle = getIntent().getBundleExtra("bundle");
    }

    @Override
    public void initView() {
        mBargainWeb = (WebView) findViewById(R.id.bargain_web);
    }

    @Override
    public void initDatas() {
        mTitleC.setText("邀请好友砍价");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");

    }

    @Override
    public void initListener() {

        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setWebview("http://www.shaoziketang.com/pcshaozi/NewKnowledge.html?u_id="+mUserId+"&type=4");
    }

    @Override
    public void initOther() {

    }

    private void setWebview(String url) {
        //设置支持javaScript
        mSettings = mBargainWeb.getSettings();
        //设置支持javaScript
        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        //设置文字大小
        mSettings.setTextSize(WebSettings.TextSize.SMALLER);
        mSettings.setTextZoom(100);
        mBargainWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("DH_BAR_URL","url:"+url);
                if(url.contains(mShareFlag)){
                    showShareDig();
                    return true;
                }else if(url.contains(mBuyFlag)||url.contains(mBuyOther)){
                    Intent intent = new Intent(BargainVipWebActivity.this, NewEventInfoActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
        mBargainWeb.loadUrl(url);
    }

    private Dialog mShareDialog;

    private void showShareDig() {
        //分享
        mShareDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        //初始化控件
        RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
        RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
        RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
        share_rlayout_py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                //msgApi
                ShareUtil.weiChat(Constants.wx_api, 7,
                        BargainVipWebActivity.this, "http://www.shaoziketang.com/pcshaozi/NewKnowledge_help.html?parent_id="+ mUserId,
                        "我正在开通勺子课堂新知会员，帮我砍价！一起获得新知！",
                        "600节餐饮好课免费学",R.drawable.zhengfangxing);
            }
        });

        share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                ShareUtil.weiChat(Constants.wx_api, 8,
                        BargainVipWebActivity.this, "http://www.shaoziketang.com/pcshaozi/NewKnowledge_help.html?parent_id=" + mUserId,
                        "我正在开通勺子课堂新知会员，帮我砍价！一起获得新知！",
                        "600节餐饮好课免费学",R.drawable.zhengfangxing);
            }
        });

        share_llayout_concel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
            }
        });

        //将布局设置给Dialog
        mShareDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mShareDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mShareDialog.show();//显示对话框
    }

    @Override
    protected void onDestroy() {
        if( mBargainWeb!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mBargainWeb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mBargainWeb);
            }

            mBargainWeb.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mBargainWeb.getSettings().setJavaScriptEnabled(false);
            mBargainWeb.clearHistory();
            mBargainWeb.clearView();
            mBargainWeb.removeAllViews();
            mBargainWeb.destroy();

        }
        super.onDestroy();
    }
}
