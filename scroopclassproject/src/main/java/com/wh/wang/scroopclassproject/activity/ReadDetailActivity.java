package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.JXKBean;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.bean.MineThirdBean;
import com.wh.wang.scroopclassproject.bean.ReadDetailInfo;
import com.wh.wang.scroopclassproject.bean.ReadInfo;
import com.wh.wang.scroopclassproject.bean.SearchInfo;
import com.wh.wang.scroopclassproject.bean.SuperiorBean;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.views.MultiStateView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/***
 * wang
 *  阅读详情
 */
public class ReadDetailActivity extends Activity {

    private Context context;
    private WebView webview;
    private String url;
    private WebSettings webSettings;
    private TextView read_detail_title;
    private TextView read_detail_date;
    private ProgressBar pb_loading;
    private Intent intent;
    private ReadInfo.NewsBean tempbean;
    private String readDetail_id;
    private String author;
    private String time;
    private int index;
    private ImageView read_detail_ib_have;
    private ReadDetailInfo readDetailInfo;
    private String user_id;
    private String readTitle;
    private MultiStateView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.read_detail, "文章详情");
        context = this;
        iniView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void iniView() {
        mStateView = (MultiStateView) findViewById(R.id.read_detail_stateview);
        read_detail_title = (TextView) findViewById(R.id.read_detail_title);
        read_detail_date = (TextView) findViewById(R.id.read_detail_date);
        read_detail_ib_have = (ImageView) findViewById(R.id.read_detail_ib_have);
        webview = (WebView) findViewById(R.id.webview);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        intent = getIntent();
        index = intent.getIntExtra("indexs", 0);
//        if (index == 1) {
//            tempbean = (ReadInfo.NewsBean) getIntent().getSerializableExtra("tempbean");
//            readDetail_id = tempbean.getId();
//        } else if (index == 3) {
//            MineInfo.CollectBean collectBean = (MineInfo.CollectBean) getIntent().getSerializableExtra("collectBean");
//            readDetail_id = collectBean.getId();
//        } else if (index == 4) {
//            MineInfo.OrderBean orderBean = (MineInfo.OrderBean) getIntent().getSerializableExtra("orderBean");
//            readDetail_id = orderBean.getId();
//        } else if (index == 5) {
//            MineInfo.StudyBean studyBean = (MineInfo.StudyBean) getIntent().getSerializableExtra("studyBean");
//            readDetail_id = studyBean.getId();
//        } else if (index == 6) {
//            MineInfo.StudyBean finishBean = (MineInfo.StudyBean) getIntent().getSerializableExtra("finishBean");
//            readDetail_id = finishBean.getId();
//        } else if (index == 7) {
//            JXKBean.ScrollBean bannerBean = (JXKBean.ScrollBean) getIntent().getSerializableExtra("bannerBean");
//            readDetail_id = bannerBean.getProduct_id();
//        } else if (index == 8) {
//            ReadInfo.BannerBean readbannerBean = (ReadInfo.BannerBean) getIntent().getSerializableExtra("readbannerBean");
//            readDetail_id = readbannerBean.getProduct_id();
//        } else if (index == 9) {
//            SearchInfo.NewBean readBean = (SearchInfo.NewBean) getIntent().getSerializableExtra("readBean");
//            readDetail_id = readBean.getId();
//        } else if (index == 10) {
//            SuperiorBean.ReadNewsBean zwbean = (SuperiorBean.ReadNewsBean) getIntent().getSerializableExtra("zwbean");
//            readDetail_id = zwbean.getId();
//        } else if (index == 11) {
//            MineThirdBean.ListBean listBean = (MineThirdBean.ListBean) getIntent().getSerializableExtra("listBean");
//            readDetail_id = listBean.getItem_id();
//        }else{
//            readDetail_id = getIntent().getStringExtra("id");
//        }
        readDetail_id = getIntent().getStringExtra("id");
        Log.e("DH_ESSAY", "id==" + readDetail_id);

        setData(readDetail_id);
        getReadDetailDataFromNet(readDetail_id, user_id);
    }

    /**
     * 获取阅读详情页面信息
     */
    private void getReadDetailDataFromNet(String idd, String userid) {
        mStateView.setViewState(MultiStateView.ViewState.LOADING);
        //联网
        //视频内容
        RequestParams params = new RequestParams(Constants.readDetailUrl);
        params.addBodyParameter("id", idd);
        params.addBodyParameter("user_id", userid);
        String timeStr = StringUtils.getTimestamp();
        params.addBodyParameter("shijian", timeStr);
        params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dealReadDetailResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mStateView.setViewState(MultiStateView.ViewState.ERROR);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getReadDetailDataFromNet---onFinished---");
            }
        });
    }


    /**
     * 处理阅读详情
     */
    private void dealReadDetailResult(String result) {
        readDetailInfo = parseJsonReadDetail(result);
        mStateView.setViewState(MultiStateView.ViewState.CONTENT);
        author = readDetailInfo.getAuthor();
        time = readDetailInfo.getPublish_shijian();
        readTitle = readDetailInfo.getTitle();
        read_detail_title.setText(readTitle);
        read_detail_date.setText("作者: " + author + "    " + time);

        if (StringUtils.isNotEmpty(readDetailInfo.getCollect_status())) {
            read_detail_ib_have.setImageResource(R.drawable.sc_press);
        } else {
            read_detail_ib_have.setImageResource(R.drawable.sc_normal);
        }
        initListener();
    }

    /**
     * 设置监听事件
     */
    private void initListener() {
        read_detail_ib_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNotEmpty(user_id)) {
                    String collectState = readDetailInfo.getCollect_status();
                    if (StringUtils.isNotEmpty(collectState)) {
                        setLoveDataFromNet(readDetail_id, user_id, "2");
                    } else {
                        setLoveDataFromNet(readDetail_id, user_id, "2");
                    }
                } else {
                    Intent intent = new Intent(ReadDetailActivity.this, LoginNewActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 网络获取数据
     */
    private void setLoveDataFromNet(String ids, String user_id, String type) {
        //联网
        RequestParams params = new RequestParams(Constants.loveUrl);
        params.addBodyParameter("id", ids);
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("type", "2");
        String timeStr = StringUtils.getTimestamp();
        params.addBodyParameter("shijian", timeStr);
        params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "setLoveDataFromNet---联网成功---result===" + result);
                dealResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "setLoveDataFromNet---联网失败---" + ex.getMessage());
                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "setLoveDataFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "setLoveDataFromNet---onFinished---");
            }
        });

    }

    private void dealResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int err = jsonObject.optInt("err");
            int number = jsonObject.optInt("number");
            if (err == 0) {
                ToastUtils.showToast(this, "成功收藏" + number + "条");
                read_detail_ib_have.setImageResource(R.drawable.sc_press);
                // video_detail_tv_collect.setText("已收藏");
            } else if (err == -1) {
                ToastUtils.showToast(this, "取消收藏");
                read_detail_ib_have.setImageResource(R.drawable.sc_normal);
                //video_detail_tv_collect.setText("未收藏");
            } else {
                ToastUtils.showToast(this, "收藏失败");
                read_detail_ib_have.setImageResource(R.drawable.sc_normal);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置数据
     */
    private void setData(String id) {
        url = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=2&id=" + id;
        //设置支持javaScript
        webSettings = webview.getSettings();
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
        webview.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_loading.setVisibility(View.GONE);
            }
        });

        webview.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重写onKeyDown，当浏览网页，WebView可以后退时执行后退操作。
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private ReadDetailInfo parseJsonReadDetail(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String read_detail_author = jsonObject.optString("author");
            String read_detail_collect_status = jsonObject.optString("collect_status");
            String read_detail_id = jsonObject.optString("id");
            String read_detail_publish_shijian = jsonObject.optString("publish_shijian");
            String read_detail_sub_title = jsonObject.optString("sub_title");
            String read_detail_title = jsonObject.optString("title");
            readDetailInfo = new ReadDetailInfo();

            readDetailInfo.setAuthor(read_detail_author);
            readDetailInfo.setCollect_status(read_detail_collect_status);
            readDetailInfo.setId(read_detail_id);
            readDetailInfo.setPublish_shijian(read_detail_publish_shijian);
            readDetailInfo.setSub_title(read_detail_sub_title);
            readDetailInfo.setTitle(read_detail_title);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return readDetailInfo;
    }
}
