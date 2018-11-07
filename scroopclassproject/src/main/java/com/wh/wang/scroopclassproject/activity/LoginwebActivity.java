package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

public class LoginwebActivity extends Activity {

    private WebView login_webview;
    private String url;
    private WebSettings webSettings;
    private ProgressBar login_pb_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.loginweb, "用户协议");
        login_webview = (WebView) findViewById(R.id.login_webview);
        login_pb_loading = (ProgressBar) findViewById(R.id.login_pb_loading);
        setData();
    }

    /**
     * 设置数据
     */
    private void setData() {
        url = "http://www.shaoziketang.com/application/views/agreement.php";
        //设置支持javaScript
        webSettings = login_webview.getSettings();
        //设置支持javaScript
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //设置文字大小
        webSettings.setTextSize(WebSettings.TextSize.SMALLER);
        webSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        login_webview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                login_pb_loading.setVisibility(View.GONE);
            }
        });

        login_webview.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重写onKeyDown，当浏览网页，WebView可以后退时执行后退操作。
        if (keyCode == KeyEvent.KEYCODE_BACK && login_webview.canGoBack()) {
            login_webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
