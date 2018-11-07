package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.SuperiorBean;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import java.util.ArrayList;
import java.util.List;

public class SuperiorDetailActivity extends Activity {

    private Context context;
    private ListView superior_detail_listview;
    private TextView superiror_detail_nonet;
    private ProgressBar superiror_detail_pb_loading;
    private Intent intent;
    private List<SuperiorBean.EventBean> eventList;
    private List<SuperiorBean.JingxuanBean> jxList;
    private List<SuperiorBean.FreeCourseBean> mfList;
    private List<SuperiorBean.TypicalCourseBean> jdList;
    private int flagg;
    private TextView title_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.superior_detail, "更多课程");
        context = this;
        flagg = getIntent().getIntExtra("flagg", 0);
        title_name = (TextView) findViewById(R.id.title_name);
        if (flagg == 10) {
            title_name.setText("热门课程");
        } else if (flagg == 1) {
            title_name.setText("精品好课");
        } else if (flagg == 2) {
            title_name.setText("免费课程");
        } else if (flagg == 3) {
            title_name.setText("经典课程");
        }
        intent = getIntent();
        initView();
        setData();
    }

    /**
     * 初始化控件布局
     */
    private void initView() {
        superior_detail_listview = (ListView) findViewById(R.id.superior_detail_listview);
        superiror_detail_nonet = (TextView) findViewById(R.id.superiror_detail_nonet);
        superiror_detail_pb_loading = (ProgressBar) findViewById(R.id.superiror_detail_pb_loading);

    }


    /**
     * 设置数据
     */
    private void setData() {
        if (flagg == 10) {
            eventList = (ArrayList<SuperiorBean.EventBean>) getIntent().getSerializableExtra("eventList");
            setSuperDetailAdapter(flagg);
        } else if (flagg == 1) {
            jxList = (ArrayList<SuperiorBean.JingxuanBean>) getIntent().getSerializableExtra("jxList");
            setSuperDetailAdapter(flagg);
        } else if (flagg == 2) {
            mfList = (ArrayList<SuperiorBean.FreeCourseBean>) getIntent().getSerializableExtra("mfList");
            setSuperDetailAdapter(flagg);
        } else if (flagg == 3) {
            jdList = (ArrayList<SuperiorBean.TypicalCourseBean>) getIntent().getSerializableExtra("jdList");
            setSuperDetailAdapter(flagg);
        }
    }

    /**
     * 设置适配器
     */
    private void setSuperDetailAdapter(int flagg) {
        if (flagg == 10) {
            //设置适配器
            if (eventList != null && eventList.size() > 0) {
                //TODO WH
                //SuperiorDetailAdapter superDetailAdapter = new SuperiorDetailAdapter(context, flagg, eventList);
               // superior_detail_listview.setAdapter(superDetailAdapter);
                //把文本隐藏
                superiror_detail_nonet.setVisibility(View.GONE);
            } else {
                superiror_detail_nonet.setVisibility(View.VISIBLE);
            }
            //ProgressBar隐藏
            superiror_detail_pb_loading.setVisibility(View.GONE);

        } else if (flagg == 1) {
            //设置适配器
            if (jxList != null && jxList.size() > 0) {
                //TODO WH
                //SuperiorDetailAdapter superDetailAdapter = new SuperiorDetailAdapter(context, flagg, jxList);
                //superior_detail_listview.setAdapter(superDetailAdapter);
                //把文本隐藏
                superiror_detail_nonet.setVisibility(View.GONE);

            } else {
                superiror_detail_nonet.setVisibility(View.VISIBLE);
            }
            //ProgressBar隐藏
            superiror_detail_pb_loading.setVisibility(View.GONE);
        } else if (flagg == 2) {
            //设置适配器
            if (mfList != null && mfList.size() > 0) {
                //TODO WH
                //SuperiorDetailAdapter superDetailAdapter = new SuperiorDetailAdapter(context, flagg, mfList);
                //superior_detail_listview.setAdapter(superDetailAdapter);
                //把文本隐藏
                superiror_detail_nonet.setVisibility(View.GONE);

            } else {
                superiror_detail_nonet.setVisibility(View.VISIBLE);
            }
            //ProgressBar隐藏
            superiror_detail_pb_loading.setVisibility(View.GONE);
        } else if (flagg == 3) {
            //设置适配器
            if (jdList != null && jdList.size() > 0) {
                //TODO WH
                //SuperiorDetailAdapter superDetailAdapter = new SuperiorDetailAdapter(context, flagg, jdList);
                //superior_detail_listview.setAdapter(superDetailAdapter);
                //把文本隐藏
                superiror_detail_nonet.setVisibility(View.GONE);

            } else {
                superiror_detail_nonet.setVisibility(View.VISIBLE);
            }
            //ProgressBar隐藏
            superiror_detail_pb_loading.setVisibility(View.GONE);
        }
    }
}
