package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.ListAdapter;
import com.wh.wang.scroopclassproject.bean.DownVideoBean;
import com.wh.wang.scroopclassproject.utils.FileUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import java.util.ArrayList;
import java.util.List;

import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity.manager;


public class DownLoadingActivity extends Activity {

    private ListView downing_listview;
    private ListAdapter adapter;
    private RelativeLayout downing_bottom_bottom_all;
    private RelativeLayout downing_bottom_bottom_delete;
    private ArrayList<DownVideoBean> allTask;
    private TextView title_action;
    private TextView downing_bottom_top_tv;
    private RelativeLayout downing_bottom_bottom;
    private TextView downing_bottom_bottom_all_tv;
    private TextView downing_bottom_bottom_delete_tv;
    private List<DownVideoBean> temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.down_loading, "我的下载");
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        title_action = (TextView) findViewById(R.id.title_action);
        title_action.setText("管理");
        downing_listview = (ListView) findViewById(R.id.downing_listview);

        downing_bottom_top_tv = (TextView) findViewById(R.id.downing_bottom_top_tv);
        downing_bottom_top_tv.setText(FileUtils.getSDAvailableSize(this));

        downing_bottom_bottom = (RelativeLayout) findViewById(R.id.downing_bottom_bottom);
        downing_bottom_bottom.setVisibility(View.GONE);

        downing_bottom_bottom_all = (RelativeLayout) findViewById(R.id.downing_bottom_bottom_all);
        downing_bottom_bottom_all_tv = (TextView) findViewById(R.id.downing_bottom_bottom_all_tv);
        downing_bottom_bottom_delete = (RelativeLayout) findViewById(R.id.downing_bottom_bottom_delete);
        downing_bottom_bottom_delete_tv = (TextView) findViewById(R.id.downing_bottom_bottom_delete_tv);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        adapter = new ListAdapter(DownLoadingActivity.this, manager);
        downing_listview.setAdapter(adapter);
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        downing_bottom_bottom_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTask = adapter.listdata;
                if (allTask==null) {
                    return;
                }
                for (int i = 0; i < allTask.size(); i++) {
                    allTask.get(i).setCheck(true);
                }
                adapter.notifyDataSetChanged();
            }
        });


        downing_bottom_bottom_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTask = adapter.listdata;
                if(allTask==null){
                    return;
                }
                temp = new ArrayList<>();
                for (int i = 0; i < allTask.size(); i++) {
                    //allTask.get(i).setCheck(true);
                    if (allTask.get(i).isCheck()) {
                        //删表
                        manager.deleteTask(allTask.get(i).getChildId() + "");
                        temp.add(allTask.get(i));
                    }
                }

                allTask.removeAll(temp);//同时删除所有刚刚添加到temp中的元素
                adapter.notifyDataSetChanged();
            }

        });


        title_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.showcheck = true;
                downing_bottom_bottom.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

        });

    }

}
