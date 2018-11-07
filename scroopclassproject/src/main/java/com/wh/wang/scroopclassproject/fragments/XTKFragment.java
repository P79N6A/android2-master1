package com.wh.wang.scroopclassproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.XTKAdapter;
import com.wh.wang.scroopclassproject.base.BaseFragment;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.JXKBean;
import com.wh.wang.scroopclassproject.bean.XTKBean;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;
import com.wh.wang.scroopclassproject.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/11/21.
 */

public class XTKFragment extends BaseFragment implements View.OnClickListener {

    private ListView xtk_listview;
    private List<XTKBean.WuDaLiypeBean> wudaliList;
    private List<JXKBean.EventsBean> eventList;  //报名
    private List<XTKBean.SystemCourseBean> xtkcList;  //课程父类
    private List<XTKBean.SystemCourseBean.CourseDetailBean> courseDetailList;  //课程详情
    private RelativeLayout mReconnectionLayout;
    private ImageView mReconnectionGif;
    private TextView mReconnection;

    @Override
    public View initView(LayoutInflater inflater) {
        EventBus.getDefault().register(this);
        //添加页面布局
        View view = inflater.inflate(R.layout.fragment_xtk, null);
        xtk_listview = (ListView) view.findViewById(R.id.xtk_listview);

        mReconnectionLayout = (RelativeLayout) view.findViewById(R.id.reconnection_layout);
        mReconnectionGif = (ImageView) view.findViewById(R.id.reconnection_gif);
        mReconnection = (TextView) view.findViewById(R.id.reconnection);
        Glide.with(MyApplication.mApplication).load(R.drawable.nosignal).asGif().into(mReconnectionGif);

        // 显示第一个位置
        xtk_listview.setSelection(0);
        mReconnection.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getXTKDataFromNet();
    }

    private void getXTKDataFromNet() {
        RequestParams params = new RequestParams(Constants.superiorUrl4);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getXTKDataFromNet---联网成功---result===" + result);
                //主线程
                mReconnectionLayout.setVisibility(View.GONE);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getXTKDataFromNet---联网失败---" + ex.getMessage());
                mReconnectionLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getXTKDataFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getXTKDataFromNet---onFinished---");
            }
        });
    }

    private void processData(String result) {
        eventList = parseJsonEvent(result);
        xtkcList = parseJsonXTKC(result);
        wudaliList = parseJsonWuDaLi(result);
        setXTKAdapter();
    }


    /**
     * 适配器
     */
    private void setXTKAdapter() {
        XTKAdapter xtkAdapter = new XTKAdapter(mContext, eventList, xtkcList, wudaliList);
        xtk_listview.setAdapter(xtkAdapter);
    }


    private List<JXKBean.EventsBean> parseJsonEvent(String result) {
        try {

            JSONObject eventObject = new JSONObject(result);
            JSONArray eventArray = eventObject.optJSONArray("events");
            eventList = new ArrayList<>();
            if (eventArray != null && eventArray.length() > 0) {
                for (int i = 0; i < eventArray.length(); i++) {
                    JSONObject eventObj = eventArray.optJSONObject(i);
                    if (eventObj != null) {
                        String event_id = eventObj.optString("id");
                        String event_img = eventObj.optString("img");
                        String event_price = eventObj.optString("price");
                        String event_title = eventObj.optString("title");
                        String event_vip_price = eventObj.optString("vip_price");
                        JXKBean.EventsBean eventBean = new JXKBean.EventsBean();
                        eventBean.setId(event_id);
                        eventBean.setImg(event_img);
                        eventBean.setPrice(event_price);
                        eventBean.setTitle(event_title);
                        eventBean.setVip_price(event_vip_price);
                        eventList.add(eventBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventList;

    }

    private List<XTKBean.SystemCourseBean> parseJsonXTKC(String result) {
        try {

            JSONObject courseObj = new JSONObject(result).getJSONObject("course");
            JSONArray elaborateCourseArray = courseObj.optJSONArray("systemCourse");
            xtkcList = new ArrayList<>();
            if (elaborateCourseArray != null && elaborateCourseArray.length() > 0) {
                for (int i = 0; i < elaborateCourseArray.length(); i++) {
                    JSONObject elaborateObj = elaborateCourseArray.optJSONObject(i);
                    if (elaborateObj != null) {
                        JSONArray course_detailArray = elaborateObj.optJSONArray("course_detail");
                        courseDetailList = new ArrayList<>();
                        if (course_detailArray != null && course_detailArray.length() > 0) {
                            for (int j = 0; j < course_detailArray.length(); j++) {
                                JSONObject course_detailObj = course_detailArray.optJSONObject(j);
                                if (course_detailObj != null) {
                                    String course_detail_id = course_detailObj.optString("id");
                                    String course_detail_img = course_detailObj.optString("img");
                                    String course_detail_new_price = course_detailObj.optString("new_price");
                                    String course_teacher_id = course_detailObj.optString("teacher_id");
                                    String course_teacher_name = course_detailObj.optString("teacher_name");
                                    String course_detail_title = course_detailObj.optString("title");
                                    String type = course_detailObj.optString("type");
                                    XTKBean.SystemCourseBean.CourseDetailBean courseDetailBean = new XTKBean.SystemCourseBean.CourseDetailBean();
                                    courseDetailBean.setId(course_detail_id);
                                    courseDetailBean.setImg(course_detail_img);
                                    courseDetailBean.setNew_price(course_detail_new_price);
                                    courseDetailBean.setTeacher_id(course_teacher_id);
                                    courseDetailBean.setTeacher_name(course_teacher_name);
                                    courseDetailBean.setTitle(course_detail_title);
                                    courseDetailBean.setType(type);
                                    courseDetailList.add(courseDetailBean);
                                }
                            }
                        }

                        String elaborate_id = elaborateObj.optString("id");
                        String elaborate_ifon = elaborateObj.optString("ifon");
                        String elaborate_name = elaborateObj.optString("name");
                        String elaborate_pai = elaborateObj.optString("pai");
                        String elaborate_parent_id = elaborateObj.optString("parent_id");
                        XTKBean.SystemCourseBean systemBean = new XTKBean.SystemCourseBean();
                        systemBean.setCourse_detail(courseDetailList);
                        systemBean.setId(elaborate_id);
                        systemBean.setIfon(elaborate_ifon);
                        systemBean.setName(elaborate_name);
                        systemBean.setPai(elaborate_pai);
                        systemBean.setParent_id(elaborate_parent_id);
                        xtkcList.add(systemBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return xtkcList;
    }

    private List<XTKBean.WuDaLiypeBean> parseJsonWuDaLi(String result) {
        try {

            JSONObject wudaliObject = new JSONObject(result);
            JSONArray wudaliArray = wudaliObject.optJSONArray("wudali");
            wudaliList = new ArrayList<>();
            if (wudaliArray != null && wudaliArray.length() > 0) {
                for (int i = 0; i < wudaliArray.length(); i++) {
                    JSONObject typeObj = wudaliArray.optJSONObject(i);
                    if (typeObj != null) {
                        String type_id = typeObj.optString("id");
                        String type_name = typeObj.optString("name");
                        XTKBean.WuDaLiypeBean wudaliBean = new XTKBean.WuDaLiypeBean();
                        wudaliBean.setId(type_id);
                        wudaliBean.setName(type_name);
                        wudaliList.add(wudaliBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wudaliList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reconnection(ReconnectionEntity entity){
        Log.e("DH_Reconnection","xt 重连数据");
        getXTKDataFromNet();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reconnection:
                Toast.makeText(mContext, "重连数据中", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new ReconnectionEntity(true));
                break;
        }
    }
}
