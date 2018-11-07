package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils;
import com.wh.wang.scroopclassproject.newproject.utils.WindowUtils;

public class TableActivity extends Activity {

    private WebSettings webSettings;
    private String url;
    private WebView table_webview;
    private ProgressBar table_pb_loading;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowUtils.getInstance().fullScreen(this);
        StatusBarUtils.setStatusTextColor(true,this);
        setContentView(R.layout.table);
        mContext = this;
        initView();
        initData();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        table_webview = (WebView) findViewById(R.id.table_webview);
        table_pb_loading = (ProgressBar) findViewById(R.id.table_pb_loading);
    }

    /**
     * 初始化布局
     */
    private void initData() {
        setWebview();
    }

    private void setWebview() {
        url = "https://www.shaoziketang.com/wapshaozi/scheduleios.html";
        //设置支持javaScript
        webSettings = table_webview.getSettings();
        //设置支持javaScript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不缓存，只从网络加载数据
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //设置文字大小
        webSettings.setTextSize(WebSettings.TextSize.SMALLER);

        webSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        table_webview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
//                if (url.contains("/course/detail")) {
//                    String web_id = url.substring(url.lastIndexOf("/") + 1);
//                    Intent intent = new Intent(mContext, VideoInfosActivity.class);
//                    Bundle bundle = new Bundle();
//                    MobclickAgent.onEvent(mContext, "indexshox");
//                    bundle.putString("id", web_id);
//                    intent.putExtras(bundle);
//                    mContext.startActivity(intent);
//
//                    return true;
//                } else
                Log.e("DH_WEB_URL",url);
                if (url.contains("/event/detail")) {
                    String web_id = url.substring(url.lastIndexOf("/") + 1);
                    Intent intent = new Intent(TableActivity.this, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",web_id);
                    startActivity(intent);
                    return true;
                } else if (url.contains("/undefined")) {
                    finish();
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                table_pb_loading.setVisibility(View.GONE);
            }
        });

        table_webview.loadUrl(url);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清楚webview缓存
        table_webview.clearCache(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true
        if ((keyCode == KeyEvent.KEYCODE_BACK) && table_webview.canGoBack()) {
            table_webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
