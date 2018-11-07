package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.DownedListAdapter;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CheckOffLineVideoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckOffLineVideoPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.utils.FileUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DownLoadedDetailActivity extends Activity {

    private ListView downed_listview;
    private RelativeLayout downed_bottom_bottom_all;
    private RelativeLayout downed_bottom_bottom_delete;
    private TextView title_action;
    private TextView downed_bottom_top_tv;
    private RelativeLayout downed_bottom_bottom;
    private TextView downed_bottom_bottom_all_tv;
    private TextView downed_bottom_bottom_delete_tv;
    private List<SQLDownLoadInfo> temp;
    private Context context;
    private int flagg;
    private DataKeeper dataKeeper;
    private ArrayList<SQLDownLoadInfo> fatherIdList;
    private DownedListAdapter downFinishListViewAdapter;
    private final String FILEPATH = FileHelper.getFileDefaultPath();
    private CheckOffLineVideoPresenter mCheckOffLineVideoPresenter = new CheckOffLineVideoPresenter();
    private AlertDialog mDialog;
    private String mUser_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle(this, R.layout.down_loaded_detail, "已下载");
        context = this;
        flagg = getIntent().getIntExtra("fatherId", 0);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
    }

    /**
     * 初始化布局
     */
    private void initView() {
        title_action = (TextView) findViewById(R.id.title_action);
        title_action.setText("管理");

        downed_listview = (ListView) findViewById(R.id.downed_detial_listview);

        downed_bottom_top_tv = (TextView) findViewById(R.id.downed_bottom_top_tv);

        downed_bottom_bottom = (RelativeLayout) findViewById(R.id.downed_bottom_bottom);
        downed_bottom_bottom.setVisibility(View.GONE);
        downed_bottom_bottom_all = (RelativeLayout) findViewById(R.id.downed_bottom_bottom_all);
        downed_bottom_bottom_all_tv = (TextView) findViewById(R.id.downed_bottom_bottom_all_tv);
        downed_bottom_bottom_delete = (RelativeLayout) findViewById(R.id.downed_bottom_bottom_delete);
        downed_bottom_bottom_delete_tv = (TextView) findViewById(R.id.downed_bottom_bottom_delete_tv);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        downed_bottom_top_tv.setText("可用空间" + FileUtils.getSDAvailableSize(this));
        dataKeeper = new DataKeeper(this);
        fatherIdList = dataKeeper.getUserDownLoadInfoByFatherId(flagg);
        downFinishListViewAdapter = new DownedListAdapter(this, fatherIdList);
        downed_listview.setAdapter(downFinishListViewAdapter);
        downFinishListViewAdapter.setOnCheckVideoPlayClickListener(new DownedListAdapter.OnCheckVideoPlayClickListener() {
            @Override
            public void onCheckPlayClick(final SQLDownLoadInfo sqlDownLoadInfo) {
                if(mCheckOffLineVideoPresenter==null){
                    mCheckOffLineVideoPresenter = new CheckOffLineVideoPresenter();
                }
                mCheckOffLineVideoPresenter.checkOffLineVideo(sqlDownLoadInfo.getFatherId()+"", mUser_id, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        CheckOffLineVideoEntity entity = (CheckOffLineVideoEntity) value[0];
                        Log.e("DH_CHECK_VIDEO",entity.getIs_buy()+"  "+entity.getIs_vip());
                        if(entity.getCode()==200){
                            if("0".equals(entity.getIs_vip())){
                                if("0".equals(entity.getIs_buy())){
                                    showDig();
                                    return;
                                }
                            }
                        }
                        Intent intent = new Intent(DownLoadedDetailActivity.this, LocalVideoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("localBean", (Serializable) sqlDownLoadInfo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onFault(String error) {
                        Intent intent = new Intent(DownLoadedDetailActivity.this, LocalVideoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("localBean", (Serializable) sqlDownLoadInfo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }
        });
    }

    private void showDig() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("您的会员已过期\n您可以续费会员后观看离线视频")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mDialog!=null){
                            if(mDialog.isShowing()){
                                mDialog.dismiss();
                            }
                        }
                    }
                })
                .setPositiveButton("续费", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DownLoadedDetailActivity.this, NewEventDetailsActivity.class);
                        intent.putExtra("event_id","997");
                        startActivity(intent);
                        finish();
                    }
                });
        mDialog = builder.create();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    /**
     * 监听事件
     */
    private void initListener() {

        title_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downFinishListViewAdapter.showcheck = true;
                downed_bottom_bottom.setVisibility(View.VISIBLE);
                downFinishListViewAdapter.notifyDataSetChanged();
            }
        });

        downed_bottom_bottom_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < fatherIdList.size(); i++) {
                    fatherIdList.get(i).setCheck(true);
                }
                downFinishListViewAdapter.notifyDataSetChanged();
            }
        });

        downed_bottom_bottom_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = new ArrayList<>();
                for (int i = 0; i < fatherIdList.size(); i++) {
                    //allTask.get(i).setCheck(true);
                    if (fatherIdList.get(i).isCheck()) {
                        //删表
                        dataKeeper.deleteDownLoadInfo(fatherIdList.get(i).getFatherId(), fatherIdList.get(i).getChildId());
                        File downloadFile = new File(FILEPATH + "/" + "." + fatherIdList.get(i).getChildId() + ".mp4");
                        if (downloadFile.exists()) {
                            downloadFile.delete();
                        }

                        temp.add(fatherIdList.get(i));
                    }
                }

                fatherIdList.removeAll(temp);//同时删除所有刚刚添加到temp中的元素
                downFinishListViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
