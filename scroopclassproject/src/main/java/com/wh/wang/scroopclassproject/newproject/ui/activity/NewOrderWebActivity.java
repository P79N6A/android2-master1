package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

public class NewOrderWebActivity extends Activity {
    private WebView mOrderListWb;
    private WebSettings mSettings;
    private String mUserId;
    private String TestOrOfficial = "www";
    private String mCourseFlag = "http://"+TestOrOfficial+".shaoziketang.com/course/detail/";
    private String mEventFlag = "http://"+TestOrOfficial+".shaoziketang.com/event/detail/";
    private String mShareFlag = "http://"+TestOrOfficial+".shaoziketang.com/wapshaozi/payment.html";
    private String mFinishFlag = "http://"+TestOrOfficial+".shaoziketang.com/wapshaozi/personal.html";
    private String mDetailFinishFlag = "http://www.shaoziketang.com/wapshaozi/www.shaoziketang.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_web);
        mOrderListWb = (WebView) findViewById(R.id.order_list_wb);
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
//        mUserId = "38564";
        Log.e("DH_USER_ID",mUserId+"");
        if(mUserId!=null&&!"".equals(mUserId)){
            setWebview();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setWebview() {
        final String pid = getIntent().getStringExtra("pid");
        String url = "http://"+TestOrOfficial+".shaoziketang.com/wapshaozi/my-order.html?personalid="+mUserId;
        if(StringUtils.isNotEmpty(pid)){
            url = "http://www.shaoziketang.com/wapshaozi/personal-order-another.html?pid="+pid;
        }
        Log.e("DH_WEB_ORDER",url+"  pid:"+pid);
        //设置支持javaScript
        mSettings = mOrderListWb.getSettings();
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
        mOrderListWb.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("DH_WEB_URL","click: "+url);
                if (url!=null&&url.length()>0) {
                    if(url.contains(mCourseFlag)){
                        int last = url.lastIndexOf("/");
                        String data = url.substring(last+1,url.length());
                        String id ="";
                        String type = "";
                        if(data!=null&&data.length()>1){
                            if(data.split("\\?").length>1){
                                id = data.split("\\?")[0];
                                type = data.split("\\?")[1].split("=")[1];
                            }
                        }
                        Log.e("DH_WEB_URL","course id: "+id+"  type: "+type);
                        Intent intent = null;
                        if("1".equals(type)){
                            intent = new Intent(NewOrderWebActivity.this,NewVideoInfoActivity.class);
                        }else if("4".equals(type)){
                            intent = new Intent(NewOrderWebActivity.this,SumUpActivity.class);
                        }
                        intent.putExtra("id",id);
                        intent.putExtra("type",type);
                        startActivity(intent);
                        return true;
                    }else if(url.contains(mEventFlag)){
                        int last = url.lastIndexOf("/");
                        String data = url.substring(last+1,url.length());
                        String id = "";
                        String type = "";
                        if(data!=null&&data.length()>1){
                            if(data.split("\\?").length>1){
                                id = data.split("\\?")[0];
                                type = data.split("\\?")[1].split("=")[1];
                            }
                        }
                        Log.e("DH_WEB_URL","event id: "+id+"  type: "+type);
                        Intent intent = null;
                        if("3".equals(type)){
                            intent = new Intent(NewOrderWebActivity.this,NewEventDetailsActivity.class);
                            intent.putExtra("event_id",id);
                        }else if("5".equals(type)){
                            intent = new Intent(NewOrderWebActivity.this,OpenClassActivity.class);
                            intent.putExtra("id",id);
                        }
                        startActivity(intent);
                        return true;
                    }else if(url.contains(mShareFlag)){
                        int index = url.lastIndexOf("oid");
                        String data = url.substring(index+4);
                        String id = "";
                        String type = "";
                        if(data!=null&&data.length()>1){
                            if(data.split("&").length>1){
                                id = data.split("&")[0];
                                type = data.split("&")[1].split("=")[1];
                            }
                        }
                        Log.e("DH_WEB_URL","course id: "+id+"  "+data);
                        Intent intent = new Intent(NewOrderWebActivity.this,ResultActivity.class);
                        intent.putExtra("type","0");
                        intent.putExtra("order_no",id);
                        intent.putExtra("course_type",type);
                        startActivity(intent);
                        return true;
                    }else if(mFinishFlag.equals(url)||mDetailFinishFlag.equals(url)){
                        finish();
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mOrderListWb.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrderListWb.clearCache(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mOrderListWb.canGoBack()) {
            mOrderListWb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
