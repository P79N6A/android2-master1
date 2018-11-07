package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.CourseAdapter;
import com.wh.wang.scroopclassproject.bean.CourseBean;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class CouseInfoActivity extends Activity {

    private Context context;
    private TextView title_name;
    private int wudaliId;
    private ListView couse_info_listview;
    private TextView couse_info_nonet;
    private ProgressBar couse_info_pb_loading;
    private List<CourseBean.InfoBean.CourseListBean> courseList;  //课程集合
    private CourseAdapter courseAdapter;
    private String wudaliName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.couse_info, "课程分类");
        context = this;
        wudaliId = getIntent().getIntExtra("wudaliId", 0);
        wudaliName = getIntent().getStringExtra("wudaliName");
        title_name = (TextView) findViewById(R.id.titlebarbackss_name);
        title_name.setText(wudaliName);
        initView();
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 初始化布局
     */
    private void initView() {
        couse_info_listview = (ListView) findViewById(R.id.couse_info_listview);
        couse_info_nonet = (TextView) findViewById(R.id.couse_info_nonet);
        couse_info_pb_loading = (ProgressBar) findViewById(R.id.couse_info_pb_loading);
    }

    /**
     * 初始化数据
     */
    private void setData() {
        getCouseFromNet(wudaliId);
    }

    /**
     * 获取网络
     */
    private void getCouseFromNet(int typeId) {

        RequestParams params = new RequestParams(Constants.courseUrl2 + typeId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getCourseFromNet---联网成功---result===" + result);
                //主线程
                processCourseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getCourseFromNet---联网失败---" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getCourseFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getCourseFromNet---onFinished---");
            }
        });
    }


    /**
     * 解析数据
     */
    private void processCourseData(String result) {
        courseList = parseJsonCourseList(result);
        Log.e("whwh", "courseList==" + courseList.size());
        showData();

    }


    /**
     * 处理数据
     */
    private void showData() {
        //ProgressBar隐藏
        couse_info_pb_loading.setVisibility(View.GONE);
        //设置适配器
        if (courseList != null && courseList.size() > 0) {
            couse_info_listview.setVisibility(View.VISIBLE);
            courseAdapter = new CourseAdapter(context, courseList);
            couse_info_listview.setAdapter(courseAdapter);
            //把文本隐藏
            couse_info_nonet.setVisibility(View.GONE);
        } else {
            //没有数据
            //文本显示
            couse_info_nonet.setVisibility(View.VISIBLE);
            couse_info_listview.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 课程数据解析
     */
    private List<CourseBean.InfoBean.CourseListBean> parseJsonCourseList(String result) {
        try {

            JSONObject infoObj = new JSONObject(result).getJSONObject("info");
            int status = new JSONObject(result).optInt("status");
            if (status == 1) {
                if (infoObj != null) {
                    JSONArray courseArray = infoObj.optJSONArray("courseList");
                    courseList = new ArrayList<>();
                    if (courseArray != null && courseArray.length() > 0) {
                        for (int i = 0; i < courseArray.length(); i++) {
                            JSONObject courseObj = courseArray.optJSONObject(i);
                            if (courseObj != null) {

                                String course_id = courseObj.optString("id");
                                String course_img = courseObj.optString("img");
                                Log.e("whwh", "course_img==" + course_img);
                                String course_new_price = courseObj.optString("new_price");
                                String course_teacher_id = courseObj.optString("teacher_id");
                                String course_teacher_name = courseObj.optString("teacher_name");
                                String course_title = courseObj.optString("title");
                                String type = courseObj.optString("type");

                                CourseBean.InfoBean.CourseListBean courseBean = new CourseBean.InfoBean.CourseListBean();
                                courseBean.setId(course_id);
                                courseBean.setImg(course_img);
                                courseBean.setNew_price(course_new_price);
                                courseBean.setTeacher_id(course_teacher_id);
                                courseBean.setTeacher_name(course_teacher_name);
                                courseBean.setTitle(course_title);
                                courseBean.setType(type);
                                courseList.add(courseBean);
                            }
                        }
                    }
                }

            } else {
                Log.e("whwh", "服务器接口数据有毛病!");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return courseList;
    }
}
