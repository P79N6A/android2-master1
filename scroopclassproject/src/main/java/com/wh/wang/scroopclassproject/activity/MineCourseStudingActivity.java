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
import com.wh.wang.scroopclassproject.adapter.MineStudyListViewAdapter;
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

public class MineCourseStudingActivity extends Activity {
    private Context context;
    private ListView mine_study_listview;
    private ProgressBar mine_study_pb_loading;
    private String userid;
    private List<MineInfo.StudyBean> studyLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.mine_course_studing, "在学课程");
        context = this;
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mine_study_listview = (ListView) findViewById(R.id.mine_study_listview);
        mine_study_pb_loading = (ProgressBar) findViewById(R.id.mine_study_pb_loading);
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
                mine_study_pb_loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "MineCollectActivity---联网失败---" + ex.getMessage());
                mine_study_pb_loading.setVisibility(View.GONE);
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
        studyLists = parseJsonStudy(result);
        showStudytData();
    }

    /**
     * 将数据添加到适配器里
     */
    private void showStudytData() {

        if (studyLists != null && studyLists.size() > 0) {
            MineStudyListViewAdapter mineStudyListViewAdapter = new MineStudyListViewAdapter(context, studyLists);
            mine_study_listview.setAdapter(mineStudyListViewAdapter);
            mineStudyListViewAdapter.notifyDataSetChanged();
        } else {
            setNullListView(3);
        }
    }

    /**
     * 当没有数据时
     */
    private void setNullListView(int flag) {
        MineNullDataAdapter nullAdapter = new MineNullDataAdapter(context, flag);
        mine_study_listview.setAdapter(nullAdapter);
        mine_study_listview.setCacheColorHint(Color.TRANSPARENT);//让ListView滑动的时候条目背景色不会变成黑色
        mine_study_listview.setDivider(null);//去掉条目间的分割线
        mine_study_listview.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色（也就是条目被点击时，背景没颜色）
        nullAdapter.notifyDataSetChanged();
    }


    /**
     * 设置监听事件
     */
    private void initListener() {

    }

    private List<MineInfo.StudyBean> parseJsonStudy(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray studyArray = jsonObject.optJSONArray("study");
            Log.e("whwh", "orderArray==" + studyArray.toString());
            studyLists = new ArrayList<>();
            if (studyArray != null && studyArray.length() > 0) {

                for (int i = 0; i < studyArray.length(); i++) {

                    JSONObject studyObj = studyArray.optJSONObject(i);

                    if (studyObj != null) {
                        String study_id = studyObj.optString("id");
                        String study_img = studyObj.optString("img");
                        String study_per = studyObj.optString("per");
                        String study_title = studyObj.optString("title");
                        int study_type = studyObj.optInt("type");

                        MineInfo.StudyBean studybean = new MineInfo.StudyBean();
                        studybean.setId(study_id);
                        studybean.setImg(study_img);
                        studybean.setPer(study_per);
                        studybean.setTitle(study_title);
                        studybean.setType(study_type);
                        studyLists.add(studybean);
                    }
                }
            } else {
                setNullListView(3);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studyLists;
    }
}
