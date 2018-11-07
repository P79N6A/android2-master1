package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.FeedBackAdapter;
import com.wh.wang.scroopclassproject.bean.MineFeedBackBean;
import com.wh.wang.scroopclassproject.bean.MineIdeaBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;
import com.wh.wang.scroopclassproject.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MineFreebackctivity extends Activity {

    private Context context;
    private String userid;
    private ListView mine_feedback_lv_msg;
    private EditText mine_feedback_et_msg;
    private Button mine_feedback_btn_send;
    private List<MineFeedBackBean.ListBean> feedbackLists;
    private FeedBackAdapter feedBackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.mine_freebackctivity, "勺子官方反馈");
        context = this;
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mine_feedback_lv_msg = (ListView) findViewById(R.id.mine_feedback_lv_msg);
        mine_feedback_et_msg = (EditText) findViewById(R.id.mine_feedback_et_Msg);
        mine_feedback_btn_send = (Button) findViewById(R.id.mine_feedback_btn_send);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        getFeedBackDatasFromNet(userid);
    }

    /**
     * 监听事件
     */
    private void initListener() {
        mine_feedback_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mine_feedback_et_msg.getText().toString();
                if (StringUtils.isEmpty(content)) {
                    BToast.showText(MineFreebackctivity.this, "内容不能为空");
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String create_time = formatter.format(curDate);
                String avator = "http://www.shaoziketang.com/application/views/pic/image53.jpg";
                String id = "30";
                String user_id = SharedPreferenceUtil.getStringFromSharedPreference(MineFreebackctivity.this, "user_id", "");
                int user_type = 0;
                MineFeedBackBean.ListBean feedbackBean = new MineFeedBackBean.ListBean(avator, content, create_time, id, user_id, user_type);
                feedbackLists.add(feedbackBean);
                feedBackAdapter.notifyDataSetChanged(); // 当有新消息时，刷新ListView中的显示
                mine_feedback_lv_msg.setSelection(feedbackLists.size()); // 将ListView	定位到最后一行
                mine_feedback_et_msg.setText(""); // 清空输入框中的内容

                sendMsgHttp(userid, content);
            }
        });
    }

    /**
     * 提交到服务器上
     */
    private void sendMsgHttp(String userid, String content) {
        HttpUserManager.getInstance().updateIdea(userid, content, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, MineIdeaBean.class);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    String msgStrs = jsonObject.optString("msg");
                } else {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(MineFreebackctivity.this, msgStrs);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取数据
     */
    private void getFeedBackDatasFromNet(String userid) {
        RequestParams params = new RequestParams(Constants.mineFeedbackMsgUrl);
        params.addBodyParameter("user_id", userid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "MineFreebackctivity---联网成功---result===" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "MineFreebackctivity---联网失败---" + ex.getMessage());

                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "MineFreebackctivity---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "MineFreebackctivity---onFinished---");
            }
        });
    }

    /**
     * 数据解析
     */
    private void processData(String result) {
        feedbackLists = parseJsonFeedBack(result);
        //Log.e("whwh", "feedbackLists==" + feedbackLists.size() + "," + feedbackLists.toString());
        showFeedBackDatas();
    }

    /**
     * 数据处理
     */
    private void showFeedBackDatas() {
        if (feedbackLists != null && feedbackLists.size() > 0) {
            feedBackAdapter = new FeedBackAdapter(context, feedbackLists);
            mine_feedback_lv_msg.setAdapter(feedBackAdapter);
            feedBackAdapter.notifyDataSetChanged();
        }
    }

    private List<MineFeedBackBean.ListBean> parseJsonFeedBack(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.optInt("code");
            String msgStr = jsonObject.optString("msg");
            JSONArray listArray = jsonObject.optJSONArray("list");
            feedbackLists = new ArrayList<>();
            if (listArray != null && listArray.length() > 0) {
                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject listObj = listArray.optJSONObject(i);
                    if (listObj != null) {
                        String avator = listObj.optString("avator");
                        String content = listObj.optString("content");
                        String create_time = listObj.optString("create_time");
                        String id = listObj.optString("id");
                        String user_id = listObj.optString("user_id");
                        int user_type = listObj.optInt("user_type");
                        Log.e("whwh", "user_type==" + user_type);
                        MineFeedBackBean.ListBean feedbackBean = new MineFeedBackBean.ListBean();
                        feedbackBean.setAvator(avator);
                        feedbackBean.setContent(content);
                        feedbackBean.setCreate_time(create_time);
                        feedbackBean.setId(id);
                        feedbackBean.setUser_id(user_id);
                        feedbackBean.setUser_type(user_type);
                        feedbackLists.add(feedbackBean);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return feedbackLists;
    }
}
