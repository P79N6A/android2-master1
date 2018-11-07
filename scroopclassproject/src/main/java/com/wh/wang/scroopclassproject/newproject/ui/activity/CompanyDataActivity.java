package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class CompanyDataActivity extends Activity {
    private WebView mWebview;
    private WebSettings mSettings;
    private String mUser_id;
    private String mFlag = "www";
    private String mFinishFlag = "http://www.shaoziketang.com/wapshaozi/www.shaoziketang.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_data);
        mWebview = (WebView) findViewById(R.id.webview);
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
        setWebview();
    }
    private void setWebview() {
        String url_type = getIntent().getStringExtra("url_type");
        String url = "";
        if("0".equals(url_type)){//企业学习时常
            String id = getIntent().getStringExtra("parent_id");
            url = "http://"+mFlag+".shaoziketang.com/wapshaozi/data-report.html?qid="+id;
        }else if("1".equals(url_type)){//排行榜
            String id = getIntent().getStringExtra("parent_id");
            url = "http://"+mFlag+".shaoziketang.com/wapshaozi/rank-list-another.html?qid="+id;
        }else if("2".equals(url_type)){//个人查看一周数据
            url = "http://"+mFlag+".shaoziketang.com/wapshaozi/oneself-record.html?qid="+mUser_id;
        }else if("3".equals(url_type)){//管理员查看一周数据
            String id = getIntent().getStringExtra("parent_id");
            Log.e("DH_DATA",id);
            url = "http://"+mFlag+".shaoziketang.com/wapshaozi/record.html?qid="+id;
        }
        //设置支持javaScript
        mSettings = mWebview.getSettings();
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
        mWebview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("DH_URL",url);
                if(mFinishFlag.equals(url)){
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
        mWebview.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebview.clearCache(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
