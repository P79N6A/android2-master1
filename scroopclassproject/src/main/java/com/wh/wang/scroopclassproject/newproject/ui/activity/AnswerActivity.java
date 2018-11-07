package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wh.wang.scroopclassproject.R;

public class AnswerActivity extends Activity {
    private WebView mActivityAnswer;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        String url = getIntent().getStringExtra("exam_url");
        mActivityAnswer = (WebView) findViewById(R.id.activity_answer);
        setWebview(url);
    }

    private void setWebview(String url) {

            //设置支持javaScript
            mSettings = mActivityAnswer.getSettings();
            //设置支持javaScript
            mSettings.setJavaScriptEnabled(true);
            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不缓存，只从网络加载数据
            //设置双击变大变小
            mSettings.setUseWideViewPort(true);
            //增加缩放按钮
            mSettings.setBuiltInZoomControls(true);
            mSettings.setSupportZoom(true);
            //设置文字大小
            mSettings.setTextSize(WebSettings.TextSize.SMALLER);
            mSettings.setTextZoom(100);
            //不让从当前网页跳转到系统的浏览器中
            mActivityAnswer.setWebViewClient(new WebViewClient() {
                //当加载页面完成的时候回调
                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Override
                // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if("http://www.shaoziketang.com/pcshaozi/enterprise.html".equals(url)){
                        finish();
                        return true;
                    }
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
            mActivityAnswer.loadUrl(url);
    }

    /**
     * 加载H5页面
     */
//    private void setWebviewData(String url) {
//        //设置支持javaScript
//        mSettings = mWebview.getSettings();
//        //设置支持javaScript
//        mSettings.setJavaScriptEnabled(true);
//        //设置双击变大变小
//        mSettings.setUseWideViewPort(true);
//        //增加缩放按钮
//        mSettings.setBuiltInZoomControls(true);
//        mSettings.setSupportZoom(true);
//        //设置文字大小
//        mSettings.setTextSize(WebSettings.TextSize.SMALLER);
//        mSettings.setTextZoom(100);
//        //不让从当前网页跳转到系统的浏览器中
//        mWebview.setWebViewClient(new WebViewClient() {
//            //当加载页面完成的时候回调
//            @Override
//            public void onLoadResource(WebView view, String url) {
//                super.onLoadResource(view, url);
//            }
//
//            @Override
//            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.e("DH_WEB_URL",url);
//                if("http://www.shaoziketang.com/pcshaozi/enterprise.html".equals(url)){
//                    finish();
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//        });
//        mWebview.loadUrl(url);
//    }


    @Override
    protected void onPause() {
        super.onPause();
//        if (mActivityAnswer != null) {
//            mActivityAnswer.pauseTimers();
//            mActivityAnswer.onHide();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mActivityAnswer != null) {
//            mActivityAnswer.resumeTimers();
//            mActivityAnswer.onShow();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mActivityAnswer != null) {
//            mActivityAnswer.onDestroy();
//        }
        if( mActivityAnswer!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mActivityAnswer.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mActivityAnswer);
            }

            mActivityAnswer.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mActivityAnswer.getSettings().setJavaScriptEnabled(false);
            mActivityAnswer.clearHistory();
            mActivityAnswer.clearView();
            mActivityAnswer.removeAllViews();
            mActivityAnswer.destroy();

        }
    }
}
