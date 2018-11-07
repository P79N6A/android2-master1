package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.MineNullDataAdapter;
import com.wh.wang.scroopclassproject.adapter.MineOrderListViewAdapter;
import com.wh.wang.scroopclassproject.bean.MineInfo;
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

import java.util.ArrayList;
import java.util.List;

public class MineOrderActivity extends Activity {
    private Context context;
    private ListView mine_order_listview;
    private ProgressBar mine_order_pb_loading;
    private String userid;
    private List<MineInfo.OrderBean> orderLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.mine_order, "我的订单");
        context = this;
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mine_order_listview = (ListView) findViewById(R.id.mine_order_listview);
        mine_order_pb_loading = (ProgressBar) findViewById(R.id.mine_order_pb_loading);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        if(StringUtils.isNotEmpty(userid)){
            getMineDatasFromNet(userid);
        }
    }

    private void getMineDatasFromNet(String userid) {
        //联网
        //视频内容 Constants.mineUrl + userid
        RequestParams params = new RequestParams(Constants.mineCeshiUrl + userid + Constants.extra);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "MineOrderActivity---联网成功---result===" + result);
                //主线程
                processData(result);
                mine_order_pb_loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "MineOrderActivity---联网失败---" + ex.getMessage());
                mine_order_pb_loading.setVisibility(View.GONE);
                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "MineOrderActivity---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "MineOrderActivity---onFinished---");
            }
        });
    }


    private void processData(String result) {
        orderLists = parseJsonOrder(result);
        showOrderData();
    }

    /**
     * 将数据添加到适配器里
     */
    private void showOrderData() {

        if (orderLists != null && orderLists.size() > 0) {
            MineOrderListViewAdapter mineOrderListViewAdapter = new MineOrderListViewAdapter(context, orderLists);
            mine_order_listview.setAdapter(mineOrderListViewAdapter);
            mineOrderListViewAdapter.notifyDataSetChanged();
        } else {
            setNullListView(2);
        }
    }

    /**
     * 当没有数据时
     */
    private void setNullListView(int flag) {
        MineNullDataAdapter nullAdapter = new MineNullDataAdapter(context, flag);
        mine_order_listview.setAdapter(nullAdapter);
        mine_order_listview.setCacheColorHint(Color.TRANSPARENT);//让ListView滑动的时候条目背景色不会变成黑色
        mine_order_listview.setDivider(null);//去掉条目间的分割线
        mine_order_listview.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色（也就是条目被点击时，背景没颜色）
        nullAdapter.notifyDataSetChanged();
    }


    /**
     * 设置监听事件
     */
    private void initListener() {

    }

    private List<MineInfo.OrderBean> parseJsonOrder(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray orderArray = jsonObject.optJSONArray("order");
            Log.e("whwh", "orderArray==" + orderArray.toString());
            orderLists = new ArrayList<>();
            if (orderArray != null && orderArray.length() > 0) {

                for (int i = 0; i < orderArray.length(); i++) {

                    JSONObject orderObj = orderArray.optJSONObject(i);

                    if (orderObj != null) {
                        String order_id = orderObj.optString("id");
                        String order_img = orderObj.optString("img");
                        String order_money = orderObj.optString("money");
                        String order_title = orderObj.optString("title");
                        int order_type = orderObj.optInt("type");

                        MineInfo.OrderBean orderbean = new MineInfo.OrderBean();
                        orderbean.setId(order_id);
                        orderbean.setImg(order_img);
                        orderbean.setMoney(order_money);
                        orderbean.setTitle(order_title);
                        orderbean.setType(order_type);
                        orderLists.add(orderbean);
                    }
                }
            } else {
                setNullListView(2);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orderLists;
    }

}
