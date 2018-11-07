package com.wh.wang.scroopclassproject.viewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.DownLoadingActivity;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.activity.TableActivity;
import com.wh.wang.scroopclassproject.adapter.DownFinishListViewAdapter;
import com.wh.wang.scroopclassproject.adapter.MineCollectListViewAdapter;
import com.wh.wang.scroopclassproject.adapter.MineNullDataAdapter;
import com.wh.wang.scroopclassproject.adapter.MineStudyListViewAdapter;
import com.wh.wang.scroopclassproject.base.BasePager;
import com.wh.wang.scroopclassproject.bean.MineIdeaBean;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wh.wang.scroopclassproject.R.id.pop_finiish;

/**
 * Created by wang on 2017/8/14.
 * 阅读
 */

public class ReadViewPager extends BasePager {

    private ImageView read_iv_top_head;
    private TextView read_tv_top_head;
    private RelativeLayout read_rlayout_study_history;
    private TextView read_tv_study_history;
    private View read_view_one;
    private RelativeLayout read_rlayout_collect;
    private TextView read_tv_collect;
    private View read_view_two;
    private RelativeLayout read_rlayout_download;
    private TextView read_tv_download;
    private View read_view_three;
    private ListView read_listview;
    private String userid;
    private List<MineInfo.StudyBean> studyLists;
    private List<MineInfo.CollectBean> collectLists;
    private List<MineInfo.StudyBean> finishLists;
    private List<MineInfo.StudyBean> allStudyLists;
    private ProgressBar read_pb_loading;
    private RelativeLayout read_rlayout_center_set;
    private TextView read_tv_manager;
    private TextView read_tv_request;
    private MineStudyListViewAdapter mineStudyListViewAdapter;
    private RelativeLayout study_bottom_bottom_all;
    private TextView study_bottom_bottom_all_tv;
    private RelativeLayout study_bottom_bottom_delete;
    private TextView study_bottom_bottom_delete_tv;
    private RelativeLayout study_bottom_bottom;
    private List<MineInfo.StudyBean> stutyTemp;
    private PopupWindow mPopupWindow;
    private static int tagg = 1; //1 全部 2 在学 3已学
    private RelativeLayout read_rlayout_center;
    private TextView downing_manager_rlayout_tv_nums;
    private RelativeLayout downing_manager_rlayout;
    private ListView downed_listview;
    private ArrayList<SQLDownLoadInfo> allDownLoadInfoList;
    private ArrayList<SQLDownLoadInfo> downLoadingList;
    private DataKeeper dataKeeper;
    private LinearLayout read_rlayout_center_down;
    private TextView read_downing_manager_tv_tip;
    private RelativeLayout read_downing_manager_rlayout_kong;
    private TextView read_downed_manager_tv_manager;
    private DownFinishListViewAdapter downFinishListViewAdapter;
    private final String FILEPATH = FileHelper.getFileDefaultPath();
    private RelativeLayout read_rlayout_empty;
    private RelativeLayout read_downed_manager_fahter;
    private ImageView read_iv_table;
    private MineCollectListViewAdapter mineCollectListViewAdapter;

    public ReadViewPager(Context context) {
        super(context);
    }


    @Override
    public View baseView() {
        View view = View.inflate(context, R.layout.read_viewpager, null);
        read_iv_top_head = (ImageView) view.findViewById(R.id.read_iv_top_head);
        read_iv_table = (ImageView) view.findViewById(R.id.read_iv_table);
        read_tv_top_head = (TextView) view.findViewById(R.id.read_tv_top_head);

        //学习记录
        read_rlayout_study_history = (RelativeLayout) view.findViewById(R.id.read_rlayout_study_history);
        read_tv_study_history = (TextView) view.findViewById(R.id.read_tv_study_history);
        read_tv_study_history.setTextColor(Color.parseColor("#89c635"));
        read_view_one = (View) view.findViewById(R.id.read_view_one);
        read_view_one.setVisibility(View.VISIBLE);
        read_rlayout_center = (RelativeLayout) view.findViewById(R.id.read_rlayout_center);
        read_rlayout_center_set = (RelativeLayout) view.findViewById(R.id.read_rlayout_center_set); //管理和筛选的父类
        read_rlayout_center_set.setVisibility(View.VISIBLE);
        read_tv_manager = (TextView) view.findViewById(R.id.read_tv_manager); //管理
        read_tv_request = (TextView) view.findViewById(R.id.read_tv_request); //筛选
        study_bottom_bottom = (RelativeLayout) view.findViewById(R.id.study_bottom_bottom);//全选 和 删除 父类
        study_bottom_bottom.setVisibility(View.GONE);
        study_bottom_bottom_all = (RelativeLayout) view.findViewById(R.id.study_bottom_bottom_all); //全选父类
        study_bottom_bottom_all_tv = (TextView) view.findViewById(R.id.study_bottom_bottom_all_tv);//全选
        study_bottom_bottom_delete = (RelativeLayout) view.findViewById(R.id.study_bottom_bottom_delete);//删除父类
        study_bottom_bottom_delete_tv = (TextView) view.findViewById(R.id.study_bottom_bottom_delete_tv);//删除


        //我的收藏
        read_rlayout_collect = (RelativeLayout) view.findViewById(R.id.read_rlayout_collect);
        read_tv_collect = (TextView) view.findViewById(R.id.read_tv_collect);
        read_tv_collect.setTextColor(Color.parseColor("#283431"));
        read_view_two = (View) view.findViewById(R.id.read_view_two);
        read_view_two.setVisibility(View.GONE);

        //我的下载
        read_rlayout_download = (RelativeLayout) view.findViewById(R.id.read_rlayout_download);
        read_tv_download = (TextView) view.findViewById(R.id.read_tv_download);
        read_tv_download.setTextColor(Color.parseColor("#283431"));
        read_view_three = (View) view.findViewById(R.id.read_view_three);
        read_view_three.setVisibility(View.GONE);

        read_listview = (ListView) view.findViewById(R.id.read_listview);
        read_listview.setVisibility(View.VISIBLE);
        read_pb_loading = (ProgressBar) view.findViewById(R.id.read_pb_loading);
        read_downed_manager_tv_manager = (TextView) view.findViewById(R.id.read_downed_manager_tv_manager);

        //下载管理
        read_rlayout_center_down = (LinearLayout) view.findViewById(R.id.read_rlayout_center_down);
        read_downed_manager_fahter = (RelativeLayout) view.findViewById(R.id.read_downed_manager_fahter);

        read_rlayout_center_down.setVisibility(View.GONE);
        read_downing_manager_tv_tip = (TextView) view.findViewById(R.id.read_downing_manager_tv_tip);
        downing_manager_rlayout = (RelativeLayout) view.findViewById(R.id.read_downing_manager_rlayout);
        downing_manager_rlayout_tv_nums = (TextView) view.findViewById(R.id.read_downing_manager_rlayout_tv_nums);
        read_downing_manager_rlayout_kong = (RelativeLayout) view.findViewById(R.id.read_downing_manager_rlayout_kong);
        downed_listview = (ListView) view.findViewById(R.id.read_downed_listview);
        downed_listview.setVisibility(View.GONE);

        read_rlayout_empty = (RelativeLayout) view.findViewById(R.id.read_rlayout_empty);
        read_rlayout_empty.setVisibility(View.GONE);
        setDatasRead();
        setListener();
        return view;
    }


    private void setDatasRead() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        if (StringUtils.isNotEmpty(userid)) {
            read_iv_top_head.setVisibility(View.VISIBLE);
            read_tv_top_head.setVisibility(View.VISIBLE);
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(context, "avatar", "");
            if (!avatar.isEmpty()) {
                ImageLoader.getInstance().displayImage(avatar, read_iv_top_head, MyDisplayOptions.getOptions());
            } else {
                read_iv_top_head.setImageResource(R.drawable.noheadportrait);
            }

            String name = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
            if (StringUtils.isNotEmpty(name)) {
                read_tv_top_head.setText(name);
            } else {
                read_tv_top_head.setText("勺子用户");
            }

        } else {
            read_iv_top_head.setImageResource(R.drawable.read_no_login);
            read_tv_top_head.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void baseData() {
        super.baseData();
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        if (StringUtils.isNotEmpty(userid)) {

            read_rlayout_center.setVisibility(View.VISIBLE);
            read_iv_top_head.setVisibility(View.VISIBLE);
            read_tv_top_head.setVisibility(View.VISIBLE);
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(context, "avatar", "");
            if (!avatar.isEmpty()) {
                ImageLoader.getInstance().displayImage(avatar, read_iv_top_head, MyDisplayOptions.getOptions());
            } else {
                read_iv_top_head.setImageResource(R.drawable.noheadportrait);
            }

            String name = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
            if (StringUtils.isNotEmpty(name)) {
                read_tv_top_head.setText(name);
            } else {
                read_tv_top_head.setText("勺子用户");
            }

            tagg = 1;
            getMineDatasFromNet(userid, tagg);
            //handler.sendEmptyMessageDelayed(1, 50);

        } else {
            read_rlayout_center.setVisibility(View.GONE);
            read_pb_loading.setVisibility(View.GONE);
            read_iv_top_head.setImageResource(R.drawable.read_no_login);
            read_tv_top_head.setVisibility(View.INVISIBLE);
            read_rlayout_empty.setVisibility(View.VISIBLE);
            read_iv_top_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void resumeData() {
        super.resumeData();
        Log.e("whwh", "resumeData=====");
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        if (StringUtils.isNotEmpty(userid)) {
            read_iv_top_head.setVisibility(View.VISIBLE);
            read_tv_top_head.setVisibility(View.VISIBLE);
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(context, "avatar", "");
            if (!avatar.isEmpty()) {
                ImageLoader.getInstance().displayImage(avatar, read_iv_top_head, MyDisplayOptions.getOptions());
            } else {
                read_iv_top_head.setImageResource(R.drawable.noheadportrait);
            }

            String name = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
            if (StringUtils.isNotEmpty(name)) {
                read_tv_top_head.setText(name);
            } else {
                read_tv_top_head.setText("勺子用户");
            }

            //handler.sendEmptyMessageDelayed(1, 50);
            read_rlayout_empty.setVisibility(View.GONE);
            read_rlayout_center.setVisibility(View.VISIBLE);
            read_rlayout_center_down.setVisibility(View.GONE);
            read_rlayout_center_set.setVisibility(View.VISIBLE);
            study_bottom_bottom.setVisibility(View.GONE);
            read_tv_study_history.setTextColor(Color.parseColor("#89c635"));
            read_view_one.setVisibility(View.VISIBLE);
            read_tv_collect.setTextColor(Color.parseColor("#283431"));
            read_view_two.setVisibility(View.GONE);
            read_tv_download.setTextColor(Color.parseColor("#283431"));
            read_view_three.setVisibility(View.GONE);
            read_listview.setVisibility(View.VISIBLE);
            downed_listview.setVisibility(View.GONE);
            tagg = 1;
            getMineDatasFromNet(userid, tagg);

        } else {
            read_rlayout_center.setVisibility(View.GONE);
            read_pb_loading.setVisibility(View.GONE);
            read_iv_top_head.setImageResource(R.drawable.read_no_login);
            read_tv_top_head.setVisibility(View.INVISIBLE);
            read_rlayout_empty.setVisibility(View.VISIBLE);

            read_iv_top_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onResumeVisible() {
        super.onResumeVisible();
        Log.e("whwh", "视频可视了!");
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        if (StringUtils.isNotEmpty(userid)) {
            read_iv_top_head.setVisibility(View.VISIBLE);
            read_tv_top_head.setVisibility(View.VISIBLE);
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(context, "avatar", "");
            if (!avatar.isEmpty()) {
                ImageLoader.getInstance().displayImage(avatar, read_iv_top_head, MyDisplayOptions.getOptions());
            } else {
                read_iv_top_head.setImageResource(R.drawable.noheadportrait);
            }

            String name = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
            if (StringUtils.isNotEmpty(name)) {
                read_tv_top_head.setText(name);
            } else {
                read_tv_top_head.setText("勺子用户");
            }

            //handler.sendEmptyMessageDelayed(1, 50);
            read_rlayout_empty.setVisibility(View.GONE);
            read_rlayout_center.setVisibility(View.VISIBLE);
            read_rlayout_center_down.setVisibility(View.GONE);
            read_rlayout_center_set.setVisibility(View.VISIBLE);
            study_bottom_bottom.setVisibility(View.GONE);
            read_tv_study_history.setTextColor(Color.parseColor("#89c635"));
            read_view_one.setVisibility(View.VISIBLE);
            read_tv_collect.setTextColor(Color.parseColor("#283431"));
            read_view_two.setVisibility(View.GONE);
            read_tv_download.setTextColor(Color.parseColor("#283431"));
            read_view_three.setVisibility(View.GONE);
            read_listview.setVisibility(View.VISIBLE);
            downed_listview.setVisibility(View.GONE);
            tagg = 1;
            getMineDatasFromNet(userid, tagg);
            read_iv_top_head.setOnClickListener(null);

        } else {
            read_rlayout_center.setVisibility(View.GONE);
            read_pb_loading.setVisibility(View.GONE);
            read_iv_top_head.setImageResource(R.drawable.read_no_login);
            read_tv_top_head.setVisibility(View.INVISIBLE);
            read_rlayout_empty.setVisibility(View.VISIBLE);
            read_iv_top_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }


    private void getMineDatasFromNet(String userid, final int tagg) {
        //联网 33988  17334
        read_pb_loading.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams(Constants.mineCeshiUrl + userid + Constants.extra);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "ReadViewPager---联网成功---result===" + result);
                //主线程
                processData(result, tagg);
                read_pb_loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "ReadViewPager---联网失败---" + ex.getMessage());
                read_pb_loading.setVisibility(View.GONE);
                read_rlayout_empty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "ReadViewPager---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "ReadViewPager---onFinished---");
            }
        });
    }


    private void processData(String result, int tagg) {

        if (tagg == 1) {
            allStudyLists = parseJsonAllStudy(result);
            showAllStudytData();
        } else if (tagg == 2) {
            studyLists = parseJsonStudy(result);
            showStudytData();
        } else if (tagg == 3) {
            finishLists = parseJsonFinish(result);
            showFinishData();
        } else if (tagg == 4) {
            collectLists = parseJsonCollect(result);
            showCollectData();
        }

    }


    private void setListener() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");

        read_iv_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TableActivity.class);
                context.startActivity(intent);
            }
        });

        read_rlayout_study_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtils.isNotEmpty(userid)){
                    read_rlayout_empty.setVisibility(View.GONE);
                    read_rlayout_center.setVisibility(View.VISIBLE);
                }else{
                    read_rlayout_empty.setVisibility(View.VISIBLE);
                    read_rlayout_center.setVisibility(View.GONE);

                }
                read_rlayout_center_down.setVisibility(View.GONE);
                read_rlayout_center_set.setVisibility(View.VISIBLE);
                study_bottom_bottom.setVisibility(View.GONE);
                read_tv_study_history.setTextColor(Color.parseColor("#89c635"));
                read_view_one.setVisibility(View.VISIBLE);
                read_tv_collect.setTextColor(Color.parseColor("#283431"));
                read_view_two.setVisibility(View.GONE);
                read_tv_download.setTextColor(Color.parseColor("#283431"));
                read_view_three.setVisibility(View.GONE);
                read_listview.setVisibility(View.VISIBLE);
                downed_listview.setVisibility(View.GONE);
                tagg = 1;
                getMineDatasFromNet(userid, tagg);
            }
        });

        read_rlayout_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtils.isNotEmpty(userid)){
                    read_rlayout_empty.setVisibility(View.GONE);
                    read_rlayout_center.setVisibility(View.VISIBLE);
                }else{
                    read_rlayout_empty.setVisibility(View.VISIBLE);
                    read_rlayout_center.setVisibility(View.GONE);
                }
                read_rlayout_center_down.setVisibility(View.GONE);
                study_bottom_bottom.setVisibility(View.GONE);
                read_rlayout_center_set.setVisibility(View.GONE);
                read_tv_study_history.setTextColor(Color.parseColor("#283431"));
                read_view_one.setVisibility(View.GONE);
                read_tv_collect.setTextColor(Color.parseColor("#89c635"));
                read_view_two.setVisibility(View.VISIBLE);
                read_tv_download.setTextColor(Color.parseColor("#283431"));
                read_view_three.setVisibility(View.GONE);
                read_listview.setVisibility(View.VISIBLE);
                downed_listview.setVisibility(View.GONE);
                tagg = 4;
                getMineDatasFromNet(userid, tagg);
            }
        });

        read_rlayout_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagg = 5;
                if(StringUtils.isNotEmpty(userid)){
                    read_rlayout_empty.setVisibility(View.GONE);
                    read_rlayout_center_down.setVisibility(View.VISIBLE);
                    read_listview.setVisibility(View.GONE);
                    downed_listview.setVisibility(View.VISIBLE);
                    dataKeeper = new DataKeeper(context);
                    downLoadingList = dataKeeper.getUserDownLoadInfoIsFinish(0);
                    downing_manager_rlayout_tv_nums.setText("正在下载" + downLoadingList.size() + "个视频");
                    if (downLoadingList.size() == 0) {
                        read_downing_manager_tv_tip.setVisibility(View.GONE);
                        downing_manager_rlayout.setVisibility(View.GONE);
                        read_downing_manager_rlayout_kong.setVisibility(View.GONE);
                    } else {
                        read_downing_manager_tv_tip.setVisibility(View.VISIBLE);
                        downing_manager_rlayout.setVisibility(View.VISIBLE);
                        read_downing_manager_rlayout_kong.setVisibility(View.VISIBLE);
                    }


                    allDownLoadInfoList = dataKeeper.getUserDownLoadInfoIsFinish(1);
                    if (allDownLoadInfoList != null && allDownLoadInfoList.size() > 0) {
                        read_rlayout_download.setVisibility(View.VISIBLE);
                        read_rlayout_empty.setVisibility(View.GONE);

                        for (int i = 0; i < allDownLoadInfoList.size() - 1; i++) {
                            for (int j = allDownLoadInfoList.size() - 1; j > i; j--) {
                                if (allDownLoadInfoList.get(j).getFatherId() == (allDownLoadInfoList.get(i).getFatherId())) {
                                    allDownLoadInfoList.remove(j);
                                }
                            }
                        }

                        if (allDownLoadInfoList != null && allDownLoadInfoList.size() > 0) {
                            read_downed_manager_fahter.setVisibility(View.VISIBLE);
                            read_rlayout_empty.setVisibility(View.GONE);
                            downFinishListViewAdapter = new DownFinishListViewAdapter(context, allDownLoadInfoList);
                            downed_listview.setAdapter(downFinishListViewAdapter);
                            downFinishListViewAdapter.notifyDataSetChanged();
                        } else {
                            //TODO WHWH
                            read_downed_manager_fahter.setVisibility(View.GONE);
                            read_rlayout_empty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //TODO WHWH
                        read_downed_manager_fahter.setVisibility(View.GONE);
                        read_rlayout_empty.setVisibility(View.VISIBLE);
                    }
                }else{
                    read_rlayout_empty.setVisibility(View.VISIBLE);
                    read_rlayout_center_down.setVisibility(View.GONE);
                }
                read_rlayout_center.setVisibility(View.GONE);
//                read_rlayout_center_down.setVisibility(View.VISIBLE);
                study_bottom_bottom.setVisibility(View.GONE);
                read_tv_study_history.setTextColor(Color.parseColor("#283431"));
                read_view_one.setVisibility(View.GONE);
                read_tv_collect.setTextColor(Color.parseColor("#283431"));
                read_view_two.setVisibility(View.GONE);
                read_tv_download.setTextColor(Color.parseColor("#89c635"));
                read_view_three.setVisibility(View.VISIBLE);

            }
        });

        downing_manager_rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DownLoadingActivity.class);
                context.startActivity(intent);
            }
        });

        //下载的已完成管理
        read_downed_manager_tv_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagg = 5;
                if (allDownLoadInfoList != null && allDownLoadInfoList.size() > 0) {

                    downFinishListViewAdapter.showcheck = true;
                    study_bottom_bottom.setVisibility(View.VISIBLE);
                    downFinishListViewAdapter.notifyDataSetChanged();
                } else {
                    BToast.showText(context, "尚未下载完成记录!");
                }
            }
        });

        //学习中心的管理

        read_tv_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tagg == 1) { //全部

                    if (allStudyLists != null && allStudyLists.size() > 0) {
                        mineStudyListViewAdapter.showcheck = true;
                        study_bottom_bottom.setVisibility(View.VISIBLE);
                        mineStudyListViewAdapter.notifyDataSetChanged();
                    } else {
                        BToast.showText(context, "尚未学习记录!");
                    }

                } else if (tagg == 2) { //在学

                    if (studyLists != null && studyLists.size() > 0) {
                        mineStudyListViewAdapter.showcheck = true;
                        study_bottom_bottom.setVisibility(View.VISIBLE);
                        mineStudyListViewAdapter.notifyDataSetChanged();
                    } else {
                        BToast.showText(context, "尚未学习记录!");
                    }

                } else if (tagg == 3) { //已学

                    if (finishLists != null && finishLists.size() > 0) {
                        mineStudyListViewAdapter.showcheck = true;
                        study_bottom_bottom.setVisibility(View.VISIBLE);
                        mineStudyListViewAdapter.notifyDataSetChanged();
                    } else {
                        BToast.showText(context, "尚未下载记录!");
                    }
                }
            }
        });


        //学习中心的筛选
        read_tv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        //学习类全选
        study_bottom_bottom_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tagg == 1) {

                    for (int i = 0; i < allStudyLists.size(); i++) {
                        allStudyLists.get(i).setCheck(true);
                    }
                    mineStudyListViewAdapter.notifyDataSetChanged();
                } else if (tagg == 2) {
                    for (int i = 0; i < studyLists.size(); i++) {
                        studyLists.get(i).setCheck(true);
                    }
                    mineStudyListViewAdapter.notifyDataSetChanged();
                } else if (tagg == 3) {

                    for (int i = 0; i < finishLists.size(); i++) {
                        finishLists.get(i).setCheck(true);
                    }
                    mineStudyListViewAdapter.notifyDataSetChanged();
                } else if (tagg == 5) {

                    for (int i = 0; i < allDownLoadInfoList.size(); i++) {
                        allDownLoadInfoList.get(i).setCheck(true);
                    }
                    downFinishListViewAdapter.notifyDataSetChanged();
                }

            }
        });

        //学习类删除
        study_bottom_bottom_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stutyTemp = new ArrayList<>();
                StringBuffer sBuffer = new StringBuffer();

                if (tagg == 1) {
                    for (int i = 0; i < allStudyLists.size(); i++) {
                        if (allStudyLists.get(i).isCheck()) {
                            stutyTemp.add(allStudyLists.get(i));
                            sBuffer.append(allStudyLists.get(i).getId())
                                    .append(",");
                        }
                    }

                    String idStr = sBuffer.toString();
                    Log.e("whwh", "tagg=1--->idStr==" + idStr);
                    setNetStudy(idStr, userid);

                } else if (tagg == 2) {
                    for (int i = 0; i < studyLists.size(); i++) {
                        if (studyLists.get(i).isCheck()) {
                            stutyTemp.add(studyLists.get(i));
                            sBuffer.append(studyLists.get(i).getId())
                                    .append(",");
                        }
                    }

                    String idStr = sBuffer.toString();
                    Log.e("whwh", "tagg=2--->idStr==" + idStr);
                    setNetStudy(idStr, userid);

                } else if (tagg == 3) {

                    for (int i = 0; i < finishLists.size(); i++) {
                        if (finishLists.get(i).isCheck()) {
                            stutyTemp.add(finishLists.get(i));
                            sBuffer.append(finishLists.get(i).getId())
                                    .append(",");
                        }
                    }

                    String idStr = sBuffer.toString();
                    Log.e("whwh", "tagg=3--->idStr==" + idStr);
                    setNetStudy(idStr, userid);

                } else if (tagg == 5) {
                    //TODO WH
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
                        File downloadFile = new File(FILEPATH + "/" + "." + fileTemp.get(j).getChildId() + ".mp4");
                        Log.e("whwh", "downloadFile" + fileTemp.get(j).getChildId());
                        if (downloadFile.exists()) {
                            downloadFile.delete();
                        }
                    }

                    allDownLoadInfoList.removeAll(downTemp);//同时删除所有刚刚添加到temp中的元素
                    if (allDownLoadInfoList.size() > 0) {
                        read_downed_manager_fahter.setVisibility(View.VISIBLE);
                        study_bottom_bottom.setVisibility(View.VISIBLE);
                        read_rlayout_empty.setVisibility(View.GONE);

                    } else {
                        read_downed_manager_fahter.setVisibility(View.GONE);
                        study_bottom_bottom.setVisibility(View.GONE);
                        read_rlayout_empty.setVisibility(View.VISIBLE);
                    }
                    downFinishListViewAdapter.notifyDataSetChanged();
                }
            }
        });

        downing_manager_rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DownLoadingActivity.class);
                context.startActivity(intent);
            }
        });

    }

    private void showPopupWindow(View view) {
        View popupView = View.inflate(context, R.layout.popupwindow_layout, null);

        RelativeLayout pop_all = (RelativeLayout) popupView.findViewById(R.id.pop_all);
        RelativeLayout pop_study = (RelativeLayout) popupView.findViewById(R.id.pop_study);
        RelativeLayout pop_finish = (RelativeLayout) popupView.findViewById(pop_finiish);

        pop_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagg = 1;
                read_rlayout_empty.setVisibility(View.GONE);
                study_bottom_bottom.setVisibility(View.GONE);
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                getMineDatasFromNet(userid, 1);
            }
        });

        pop_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagg = 2;
                read_rlayout_empty.setVisibility(View.GONE);
                study_bottom_bottom.setVisibility(View.GONE);
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                getMineDatasFromNet(userid, 2);
            }
        });

        pop_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tagg = 3;
                read_rlayout_empty.setVisibility(View.GONE);
                study_bottom_bottom.setVisibility(View.GONE);
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                getMineDatasFromNet(userid, 3);
            }
        });


        //透明动画(透明--->不透明)
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(100);
        alphaAnimation.setFillAfter(true);
        //动画集合Set
        AnimationSet animationSet = new AnimationSet(true);
        //添加两个动画
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        //1,创建窗体对象,指定宽高

        mPopupWindow = new PopupWindow(popupView,
                300,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //2,设置一个透明背景(new ColorDrawable())
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //3,指定窗体位置
        //mPopupWindow.showAsDropDown(view, 50, -view.getHeight());
        mPopupWindow.showAsDropDown(view, 30, 5);
        //4,popupView执行动画
        popupView.startAnimation(animationSet);
    }

    private void setNetStudy(String video_id, String userId) {
        HttpUserManager.getInstance().deleteStudy(video_id, userId, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, MineIdeaBean.class);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {

                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(context, msgStrs);
                    if (tagg == 1) {
                        allStudyLists.removeAll(stutyTemp);
                        //TODO WHWH
                        if (allStudyLists.size() == 0) {
                            read_rlayout_center_set.setVisibility(View.GONE);
                            study_bottom_bottom.setVisibility(View.GONE);
                            read_listview.setVisibility(View.GONE);
                            read_rlayout_empty.setVisibility(View.VISIBLE);
                        }

                    } else if (tagg == 2) {
                        studyLists.removeAll(stutyTemp);//同时删除所有刚刚添加到temp中的元素
                    } else if (tagg == 3) {
                        finishLists.removeAll(stutyTemp);
                    }

                    mineStudyListViewAdapter.notifyDataSetChanged();
                } else {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(context, msgStrs);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 将正在学习的数据添加到适配器里
     */
    private void showStudytData() {
        if (studyLists != null && studyLists.size() > 0) {
            read_rlayout_empty.setVisibility(View.GONE);
            read_listview.setVisibility(View.VISIBLE);
            mineStudyListViewAdapter = new MineStudyListViewAdapter(context, studyLists);
            read_listview.setAdapter(mineStudyListViewAdapter);
            mineStudyListViewAdapter.notifyDataSetChanged();

        } else {
            read_listview.setVisibility(View.GONE);
            read_rlayout_empty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 将全部学习的数据添加到适配器里
     */
    private void showAllStudytData() {

        if (allStudyLists != null && allStudyLists.size() > 0) {
            read_rlayout_empty.setVisibility(View.GONE);
            read_listview.setVisibility(View.VISIBLE);
            mineStudyListViewAdapter = new MineStudyListViewAdapter(context, allStudyLists);
            read_listview.setAdapter(mineStudyListViewAdapter);
            mineStudyListViewAdapter.notifyDataSetChanged();

        } else {
            read_rlayout_center_set.setVisibility(View.GONE);
            study_bottom_bottom.setVisibility(View.GONE);
            read_listview.setVisibility(View.GONE);
            read_rlayout_empty.setVisibility(View.VISIBLE);
        }
    }


    private void showFinishData() {

        if (finishLists != null && finishLists.size() > 0) {
            read_rlayout_empty.setVisibility(View.GONE);
            read_listview.setVisibility(View.VISIBLE);
            mineStudyListViewAdapter = new MineStudyListViewAdapter(context, finishLists);
            read_listview.setAdapter(mineStudyListViewAdapter);
            mineStudyListViewAdapter.notifyDataSetChanged();
        } else {
            read_listview.setVisibility(View.GONE);
            read_rlayout_empty.setVisibility(View.VISIBLE);
        }

    }

    private void showCollectData() {

        if (collectLists != null && collectLists.size() > 0) {
            read_rlayout_empty.setVisibility(View.GONE);
            read_listview.setVisibility(View.VISIBLE);
            mineCollectListViewAdapter = new MineCollectListViewAdapter(context, collectLists);
            read_listview.setAdapter(mineCollectListViewAdapter);
            mineCollectListViewAdapter.notifyDataSetChanged();
        } else {
            read_listview.setVisibility(View.GONE);
            read_rlayout_empty.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 当没有数据时
     */
    private void setNullListView(int flag) {
        MineNullDataAdapter nullAdapter = new MineNullDataAdapter(context, flag);
        read_listview.setAdapter(nullAdapter);
        read_listview.setCacheColorHint(Color.TRANSPARENT);//让ListView滑动的时候条目背景色不会变成黑色
        read_listview.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色（也就是条目被点击时，背景没颜色）
        read_listview.setDividerHeight(0);
        nullAdapter.notifyDataSetChanged();
    }

    private List<MineInfo.StudyBean> parseJsonStudy(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray studyArray = jsonObject.optJSONArray("study");
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
                        studybean.setCheck(false);
                        studyLists.add(studybean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studyLists;
    }


    private List<MineInfo.StudyBean> parseJsonAllStudy(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray allStudyArray = jsonObject.optJSONArray("allstudy");
            allStudyLists = new ArrayList<>();
            if (allStudyArray != null && allStudyArray.length() > 0) {

                for (int i = 0; i < allStudyArray.length(); i++) {

                    JSONObject allStudyObj = allStudyArray.optJSONObject(i);

                    if (allStudyObj != null) {
                        String allstudy_id = allStudyObj.optString("id");
                        String allstudy_img = allStudyObj.optString("img");
                        String allstudy_per = allStudyObj.optString("per");
                        String allstudy_title = allStudyObj.optString("title");
                        int allstudy_type = allStudyObj.optInt("type");

                        MineInfo.StudyBean allStudyBean = new MineInfo.StudyBean();
                        allStudyBean.setId(allstudy_id);
                        allStudyBean.setImg(allstudy_img);
                        allStudyBean.setPer(allstudy_per);
                        allStudyBean.setTitle(allstudy_title);
                        allStudyBean.setType(allstudy_type);
                        allStudyBean.setCheck(false);
                        allStudyLists.add(allStudyBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allStudyLists;
    }

    //解析数据
    private List<MineInfo.CollectBean> parseJsonCollect(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray collectArray = jsonObject.optJSONArray("collect");
            collectLists = new ArrayList<>();
            if (collectArray != null && collectArray.length() > 0) {

                for (int i = 0; i < collectArray.length(); i++) {

                    JSONObject collectObj = collectArray.optJSONObject(i);

                    if (collectObj != null) {
                        String collect_id = collectObj.optString("id");
                        String collect_img = collectObj.optString("img");
                        String collect_title = collectObj.optString("title");
                        int collect_type = collectObj.optInt("type");

                        MineInfo.CollectBean collectbean = new MineInfo.CollectBean();
                        collectbean.setId(collect_id);
                        collectbean.setImg(collect_img);
                        collectbean.setTitle(collect_title);
                        collectbean.setType(collect_type);
                        collectLists.add(collectbean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectLists;
    }

    private List<MineInfo.StudyBean> parseJsonFinish(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray finishArray = jsonObject.optJSONArray("finish");
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
                        finishbean.setCheck(false);
                        finishLists.add(finishbean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return finishLists;
    }

}
