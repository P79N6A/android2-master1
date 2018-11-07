package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.DownFinishListViewAdapter;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/11/10.
 */

public class DownManagerActivity extends Activity {
    private TextView mDownedManagerTvTip;
    private TextView title_action;
    private TextView downing_manager_rlayout_tv_nums;
    private RelativeLayout downing_manager_rlayout;
    private ListView downed_listview;
    private ArrayList<SQLDownLoadInfo> allDownLoadInfoList;
    private ArrayList<SQLDownLoadInfo> downLoadingList;
    private DataKeeper dataKeeper;
    private TextView downing_manager_tv_tip;
    private RelativeLayout downing_manager_rlayout_kong;
    private RelativeLayout manage_bottom_bottom;
    private RelativeLayout manage_bottom_bottom_all;
    private RelativeLayout manage_bottom_bottom_delete;
    private DownFinishListViewAdapter downFinishListViewAdapter;
    private final String FILEPATH = FileHelper.getFileDefaultPath();
    private ImageView mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.down_manager, "我的下载");
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        downLoadingList = dataKeeper.getUserDownLoadInfoIsFinish(0);
//        downing_manager_rlayout_tv_nums.setText("正在下载" + downLoadingList.size() + "个课件");
        initData();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        title_action = (TextView) findViewById(R.id.title_action);
        title_action.setText("管理");
        //downing_manager_tv_tip 已完成  downing_manager_rlayout正在下载父类   downing_manager_rlayout_kong 空格行
        downing_manager_tv_tip = (TextView) findViewById(R.id.downing_manager_tv_tip);
        downing_manager_rlayout = (RelativeLayout) findViewById(R.id.downing_manager_rlayout);
        downing_manager_rlayout_tv_nums = (TextView) findViewById(R.id.downing_manager_rlayout_tv_nums);
        downing_manager_rlayout_kong = (RelativeLayout) findViewById(R.id.downing_manager_rlayout_kong);
        manage_bottom_bottom = (RelativeLayout) findViewById(R.id.manage_bottom_bottom);
        manage_bottom_bottom_all = (RelativeLayout) findViewById(R.id.manage_bottom_bottom_all);
        manage_bottom_bottom_delete = (RelativeLayout) findViewById(R.id.manage_bottom_bottom_delete);
        mDownedManagerTvTip = (TextView) findViewById(R.id.downed_manager_tv_tip);
        mNoData = (ImageView) findViewById(R.id.no_data);

        downed_listview = (ListView) findViewById(R.id.downed_listview);
        dataKeeper = new DataKeeper(this);

    }

    /**
     * 下载数据
     */
    private void initData() {
        downLoadingList = dataKeeper.getUserDownLoadInfoIsFinish(0);
        if (downLoadingList.size() > 0) {
        downing_manager_rlayout_tv_nums.setText("正在下载" + downLoadingList.size() + "个课件");
            downing_manager_tv_tip.setVisibility(View.VISIBLE);
            downing_manager_rlayout.setVisibility(View.VISIBLE);
            downing_manager_rlayout_kong.setVisibility(View.VISIBLE);
        } else {
            downing_manager_tv_tip.setVisibility(View.GONE);
            downing_manager_rlayout.setVisibility(View.GONE);
            downing_manager_rlayout_kong.setVisibility(View.GONE);
        }

        allDownLoadInfoList = dataKeeper.getUserDownLoadInfoIsFinish(1);
        if (allDownLoadInfoList != null && allDownLoadInfoList.size() > 0) {
            Log.e("whwh", "allDownLoadInfoList==before=" + allDownLoadInfoList.size());
            mDownedManagerTvTip.setVisibility(View.VISIBLE);
            for (int i = 0; i < allDownLoadInfoList.size() - 1; i++) {
                for (int j = allDownLoadInfoList.size() - 1; j > i; j--) {
                    if (allDownLoadInfoList.get(j).getFatherId() == (allDownLoadInfoList.get(i).getFatherId())) {
                        allDownLoadInfoList.remove(j);
                    }
                }
            }

            Log.e("whwh", "allDownLoadInfoList==before=" + allDownLoadInfoList.size());
            downFinishListViewAdapter = new DownFinishListViewAdapter(this, allDownLoadInfoList);
            downed_listview.setAdapter(downFinishListViewAdapter);
            downFinishListViewAdapter.notifyDataSetChanged();
        }else{
            mDownedManagerTvTip.setVisibility(View.GONE);
        }

        if((allDownLoadInfoList == null || allDownLoadInfoList.size() <= 0)&&(downLoadingList==null||downLoadingList.size()<=0)){
            mNoData.setVisibility(View.VISIBLE);
        }else{
            mNoData.setVisibility(View.GONE);
        }
    }

    /**
     * 下载监听事件
     */
    private void initListener() {

        downing_manager_rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownManagerActivity.this, DownLoadingActivity.class);
                startActivity(intent);
            }
        });


        title_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allDownLoadInfoList != null && allDownLoadInfoList.size() > 0) {

                    downFinishListViewAdapter.showcheck = true;
                    manage_bottom_bottom.setVisibility(View.VISIBLE);
                    downFinishListViewAdapter.notifyDataSetChanged();
                } else {
                    BToast.showText(DownManagerActivity.this, "尚未下载完成记录!");
                }
            }
        });

        manage_bottom_bottom_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < allDownLoadInfoList.size(); i++) {
                    allDownLoadInfoList.get(i).setCheck(true);
                }
                downFinishListViewAdapter.notifyDataSetChanged();
            }
        });

        manage_bottom_bottom_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SQLDownLoadInfo> downTemp = new ArrayList<>();
                List<SQLDownLoadInfo> fileTemp = new ArrayList<>();
                for (int i = 0; i < allDownLoadInfoList.size(); i++) {
                    if (allDownLoadInfoList.get(i).isCheck()) {
                        ArrayList<SQLDownLoadInfo> fatherList = dataKeeper.getUserDownLoadInfoByFatherId(allDownLoadInfoList.get(i).getFatherId());
                        for (int j = 0; j < fatherList.size(); j++) {
                            fileTemp.add(fatherList.get(j));
                        }
                        downTemp.add(allDownLoadInfoList.get(i));
                    }
                }
                for (int j = 0; j < fileTemp.size(); j++) {
                    dataKeeper.deleteDownLoadInfo(fileTemp.get(j).getFatherId(), fileTemp.get(j).getChildId());
                    File downloadFile = new File(FILEPATH + "/" + "."+fileTemp.get(j).getChildId() + ".mp4");
                    Log.e("whwh", "downloadFile" + fileTemp.get(j).getChildId());
                    if (downloadFile.exists()) {
                        downloadFile.delete();
                    }
                }

                allDownLoadInfoList.removeAll(downTemp);//同时删除所有刚刚添加到temp中的元素
                downFinishListViewAdapter.notifyDataSetChanged();

                if(allDownLoadInfoList.size()<=0&&(allDownLoadInfoList == null || allDownLoadInfoList.size() <= 0)){
                    mNoData.setVisibility(View.VISIBLE);
                }else{
                    mNoData.setVisibility(View.GONE);
                }
            }
        });
    }
}
