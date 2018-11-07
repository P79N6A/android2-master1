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
import com.wh.wang.scroopclassproject.adapter.MineFinishListViewAdapter;
import com.wh.wang.scroopclassproject.adapter.MineNullDataAdapter;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
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

public class MineCourseFinishedActivity extends Activity {
    private Context context;
    private ListView mine_finished_listview;
    private ProgressBar mine_finished_pb_loading;
    private String userid;
    private List<MineInfo.StudyBean> finishLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.mine_course_finished, "已学课程");
        context = this;
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mine_finished_listview = (ListView) findViewById(R.id.mine_finished_listview);
        mine_finished_pb_loading = (ProgressBar) findViewById(R.id.mine_finished_pb_loading);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        getMineDatasFromNet(userid);
    }

    private void getMineDatasFromNet(String userid) {
        //联网
        //视频内容 Constants.mineUrl + userid
        RequestParams params = new RequestParams(Constants.mineCeshiUrl + userid + Constants.extra);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "MineCollectActivity---联网成功---result===" + result);
                //主线程
                processData(result);
                mine_finished_pb_loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "MineCollectActivity---联网失败---" + ex.getMessage());
                mine_finished_pb_loading.setVisibility(View.GONE);
                ToastUtils.showToast((Activity) context, "联网失败!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "MineCollectActivity---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "MineCollectActivity---onFinished---");
            }
        });
    }


    private void processData(String result) {
        finishLists = parseJsonFinish(result);
        showFinishData();
    }

    /**
     * 将数据添加到适配器里
     */
    private void showFinishData() {

        if (finishLists != null && finishLists.size() > 0) {
            MineFinishListViewAdapter mineFinishListViewAdapter = new MineFinishListViewAdapter(context, finishLists);
            mine_finished_listview.setAdapter(mineFinishListViewAdapter);
            mineFinishListViewAdapter.notifyDataSetChanged();
        } else {
            setNullListView(4);
        }
    }

    /**
     * 当没有数据时
     */
    private void setNullListView(int flag) {
        MineNullDataAdapter nullAdapter = new MineNullDataAdapter(context, flag);
        mine_finished_listview.setAdapter(nullAdapter);
        mine_finished_listview.setCacheColorHint(Color.TRANSPARENT);//让ListView滑动的时候条目背景色不会变成黑色
        mine_finished_listview.setDivider(null);//去掉条目间的分割线
        mine_finished_listview.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色（也就是条目被点击时，背景没颜色）
        nullAdapter.notifyDataSetChanged();
    }


    /**
     * 设置监听事件
     */
    private void initListener() {

    }

    private List<MineInfo.StudyBean> parseJsonFinish(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray finishArray = jsonObject.optJSONArray("finish");
            Log.e("whwh", "orderArray==" + finishArray.toString());
            finishLists = new ArrayList<>();
            if (finishArray != null && finishArray.length() > 0) {

                for (int i = 0; i < finishArray.length(); i++) {

                    JSONObject finishObj = finishArray.optJSONObject(i);

                    if (finishObj != null) {
                        String finish_id = finishObj.optString("id");
                        String finish_img = finishObj.optString("img");
                        String finish_per = finishObj.optString("per");
                        String finish_title = finishObj.optString("title");
                        int finish_type = finishObj.optInt("type");

                        MineInfo.StudyBean finishbean = new MineInfo.StudyBean();
                        finishbean.setId(finish_id);
                        finishbean.setImg(finish_img);
                        finishbean.setPer(finish_per);
                        finishbean.setTitle(finish_title);
                        finishbean.setType(finish_type);
                        finishLists.add(finishbean);
                    }
                }
            } else {
                setNullListView(4);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return finishLists;
    }

}
