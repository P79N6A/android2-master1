package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.SuperiorDetailAdapter;
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

public class SuperMoreActivity extends Activity {

    private TextView title_name;
    private Context context;
    private int jxid;
    private String jxname;
    private PullToRefreshListView super_more_listview;
    private ProgressBar super_more_pb_loading;
    private List<CourseBean.InfoBean.CourseListBean> courseList;  //课程集合
    private List<CourseBean.InfoBean.CourseListBean> allCourseList;
    int pageIndex = 0;
    private TextView super_more_tv_nonet;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private ListView listview;
    private SuperiorDetailAdapter superDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.super_more, "更多课程");
        context = this;
        jxid = getIntent().getIntExtra("jxid", 0);
        Log.e("whwh", "jxid==" + jxid);
        jxname = getIntent().getStringExtra("jxname");
        title_name = (TextView) findViewById(R.id.titlebarbackss_name);
        title_name.setText(jxname);
        initView();
        initData(pageIndex);
        initListener();
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
        super_more_listview = (PullToRefreshListView) findViewById(R.id.super_more_listview);
        listview = super_more_listview.getRefreshableView();
        super_more_pb_loading = (ProgressBar) findViewById(R.id.super_more_pb_loading);
        super_more_tv_nonet = (TextView) findViewById(R.id.super_more_tv_nonet);
        allCourseList = new ArrayList<CourseBean.InfoBean.CourseListBean>();
    }

    /**
     * 初始化数据
     */
    private void initData(int pageIndex) {

        getDataFromNet(jxid, pageIndex);
    }

    private void initListener() {
        super_more_listview.setMode(PullToRefreshBase.Mode.BOTH);
        super_more_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = true;
                int page = pageIndex = 0;
                initData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isLoadMore = true;
                int page = pageIndex++;
                initData(page);
            }
        });
    }

    private void getDataFromNet(int jxid, int pageIndex) {
        RequestParams params = new RequestParams(Constants.supMoreUrl + "&typeId=" + jxid + "&page=" + pageIndex);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getMoreDataNet---联网成功---result===" + result);
                //主线程
                processData(result);
                super_more_listview.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getMoreDataNet---联网失败---" + ex.getMessage());
                super_more_listview.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getMoreDataNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getMoreDataNet---onFinished---");
            }
        });
    }


    private void processData(String result) {
        courseList = parseJsonCourseList(result);
        if (isRefresh) {
            isRefresh = false;
            allCourseList.clear();
            allCourseList.addAll(courseList);
            setSuperDetailAdapter();
        } else if (isLoadMore) {
            isLoadMore = false;
            allCourseList.addAll(courseList);
            setSuperDetailAdapter();
        } else {
            allCourseList.clear();
            allCourseList.addAll(courseList);
            setSuperDetailAdapter();
        }
    }

    private void setSuperDetailAdapter() {
        if (courseList != null && courseList.size() > 0) {
            superDetailAdapter = new SuperiorDetailAdapter(context, allCourseList);
            super_more_listview.setAdapter(superDetailAdapter);
            //把文本隐藏
            super_more_tv_nonet.setVisibility(View.GONE);

        } else {
            //super_more_tv_nonet.setVisibility(View.VISIBLE);
        }

        //ProgressBar隐藏
        super_more_pb_loading.setVisibility(View.GONE);
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

                                CourseBean.InfoBean.CourseListBean courseBean = new CourseBean.InfoBean.CourseListBean();
                                courseBean.setId(course_id);
                                courseBean.setImg(course_img);
                                courseBean.setNew_price(course_new_price);
                                courseBean.setTeacher_id(course_teacher_id);
                                courseBean.setTeacher_name(course_teacher_name);
                                courseBean.setTitle(course_title);
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