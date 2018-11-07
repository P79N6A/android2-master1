package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.MineMsgDetailAdapter;
import com.wh.wang.scroopclassproject.bean.MineThirdBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MineMsgDetailActivity extends Activity {

    private Context context;
    private ListView mine_msg_listview;
    private String userid;
    private List<MineThirdBean.ListBean> beanLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.mine_msg_detail, "消息通知");
        context = this;
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mine_msg_listview = (ListView) findViewById(R.id.mine_msg_listview);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        getMineMsgDatasFromNet(userid);
    }

    private void getMineMsgDatasFromNet(String userid) {
        HttpUserManager.getInstance().updateThridMsg(userid, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {
            }
        }, MineThirdBean.class);
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", " msg.obj" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                String msgStr = jsonObject.optString("msg");
                JSONArray listArray = jsonObject.optJSONArray("list");
                beanLists = new ArrayList<>();
                if (listArray != null && listArray.length() > 0) {
                    for (int i = 0; i < listArray.length(); i++) {
                        JSONObject listObj = listArray.optJSONObject(i);
                        if (listObj != null) {
                            String title = listObj.optString("title");
                            String content = listObj.optString("content");
                            String create_time = listObj.optString("create_time");
                            String id = listObj.optString("id");
                            String item_id = listObj.optString("item_id");
                            int item_type = listObj.optInt("item_type");
                            String type = listObj.optString("type");

                            MineThirdBean.ListBean listBean = new MineThirdBean.ListBean();
                            listBean.setTitle(title);
                            listBean.setContent(content);
                            listBean.setCreate_time(create_time);
                            listBean.setId(id);
                            listBean.setItem_id(item_id);
                            listBean.setItem_type(item_type);
                            listBean.setType(type);
                            beanLists.add(listBean);
                        }
                    }
                }

                if (beanLists != null && beanLists.size() > 0) {
                    MineMsgDetailAdapter msgDetailViewAdapter = new MineMsgDetailAdapter(MineMsgDetailActivity.this, beanLists);
                    mine_msg_listview.setAdapter(msgDetailViewAdapter);
                    msgDetailViewAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
