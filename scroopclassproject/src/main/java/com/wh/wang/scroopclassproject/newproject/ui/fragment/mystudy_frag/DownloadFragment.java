package com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.DownLoadedDetailActivity;
import com.wh.wang.scroopclassproject.activity.DownLoadingActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.StudyDownLoadAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment implements View.OnClickListener {
    private String mUserId;
    private String mMobile;
    private RelativeLayout mStudyShowDowning;
    private TextView mStudyShowDowningNum;
    private TextView mFinishDownManager;
    private RecyclerView mFinishDownList;
    private LinearLayout mFinishDownMenu;
    private TextView mAllSelect;
    private TextView mDeleteSelect;
    private RelativeLayout mFinishDown;
    private LinearLayout mStudyDowning;
    private ImageView mNoData;
    private ImageView mNoLogin;

    private DataKeeper mDataKeeper;
    private final String FILEPATH = FileHelper.getFileDefaultPath();
    private boolean mIsShowMenu = false;
    private boolean mIsAllSelect = false;
    private StudyDownLoadAdapter mStudyDownLoadAdapter;
    private ArrayList<SQLDownLoadInfo> mUserAllDownLoadInfo;

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        initView(view);
        initRecycle();
        initListener();
        return view;
    }

    private void initListener() {
        mFinishDownManager.setOnClickListener(this);
        mAllSelect.setOnClickListener(this);
        mStudyShowDowning.setOnClickListener(this);
        mDeleteSelect.setOnClickListener(this);
    }

    private void initView(View view) {
        mStudyDowning = (LinearLayout) view.findViewById(R.id.study_downing);
        mStudyShowDowning = (RelativeLayout) view.findViewById(R.id.study_show_downing);
        mStudyShowDowningNum = (TextView) view.findViewById(R.id.study_show_downing_num);
        mFinishDown = (RelativeLayout) view.findViewById(R.id.finish_down);
        mFinishDownManager = (TextView) view.findViewById(R.id.finish_down_manager);
        mFinishDownList = (RecyclerView) view.findViewById(R.id.finish_down_list);
        mFinishDownMenu = (LinearLayout) view.findViewById(R.id.finish_down_menu);
        mAllSelect = (TextView) view.findViewById(R.id.all_select);
        mDeleteSelect = (TextView) view.findViewById(R.id.delete_select);
        mNoData = (ImageView) view.findViewById(R.id.no_data);
        mNoLogin = (ImageView) view.findViewById(R.id.no_login);
    }

    private void initRecycle() {
        mFinishDownList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"user_id","");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        if(StringUtils.isNotEmpty(mUserId)&&StringUtils.isNotEmpty(mMobile)){
            /**
             * 获取正在下载的个数
             */
            mDataKeeper = new DataKeeper(MyApplication.mApplication);
            ArrayList<SQLDownLoadInfo> userDownLoadingInfo = mDataKeeper.getUserDownLoadInfoIsFinish(0);
            mUserAllDownLoadInfo = mDataKeeper.getUserDownLoadInfoIsFinish(1);
            if((userDownLoadingInfo!=null&&userDownLoadingInfo.size()>0)||
                    (mUserAllDownLoadInfo!=null&&mUserAllDownLoadInfo.size()>0)){
                NothingShow(2);
                if(userDownLoadingInfo!=null&&userDownLoadingInfo.size()>0){
                    mStudyDowning.setVisibility(View.VISIBLE);
                    mStudyShowDowningNum.setText("正在下载"+userDownLoadingInfo.size()+"个视频");
                }else{
                    mStudyDowning.setVisibility(View.GONE);
                }
                if(mUserAllDownLoadInfo!=null&&mUserAllDownLoadInfo.size()>0){
                    mFinishDown.setVisibility(View.VISIBLE);
                    mStudyDownLoadAdapter = new StudyDownLoadAdapter(mUserAllDownLoadInfo);
                    mFinishDownList.setAdapter(mStudyDownLoadAdapter);
                    mStudyDownLoadAdapter.setOnDownLoadClickListener(new StudyDownLoadAdapter.OnDownLoadClickListener() {
                        @Override
                        public void onDownLoadClick(int pos, SQLDownLoadInfo sqlDownLoadInfo) {
                            Intent intent = new Intent(getContext(), DownLoadedDetailActivity.class);
                            intent.putExtra("fatherId", sqlDownLoadInfo.getFatherId());
                            startActivity(intent);
                        }
                    });
                }else{
                    mFinishDown.setVisibility(View.GONE);//TODO No Data
                }
            }else{
                mStudyDowning.setVisibility(View.GONE);
                mFinishDown.setVisibility(View.GONE);//TODO No Data
                NothingShow(0);
            }

        }else{
            NothingShow(1);
            mFinishDown.setVisibility(View.GONE);//TODO No Data
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.finish_down_manager:
                if(mStudyDownLoadAdapter!=null){
                    mStudyDownLoadAdapter.showOrNotSelect(!mIsShowMenu);
                    if(!mIsShowMenu){
                        mIsShowMenu = true;
                        mFinishDownManager.setText("取消");
                        mFinishDownMenu.setVisibility(View.VISIBLE);
                    }else{
                        mIsShowMenu = false;
                        mFinishDownManager.setText("管理");
                        mFinishDownMenu.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.all_select:
                if(mUserAllDownLoadInfo!=null&&mUserAllDownLoadInfo.size()>0&&mStudyDownLoadAdapter!=null){
                    if(!mIsAllSelect){
                        mIsAllSelect = true;
                        for (int i = 0; i < mUserAllDownLoadInfo.size(); i++) {
                            mUserAllDownLoadInfo.get(i).setCheck(true);
                        }
                        mAllSelect.setText("取消");
                    }else{
                        mIsAllSelect = false;
                        for (int i = 0; i < mUserAllDownLoadInfo.size(); i++) {
                            mUserAllDownLoadInfo.get(i).setCheck(false);
                        }
                        mAllSelect.setText("全选");
                    }
                    mStudyDownLoadAdapter.notifyDataSetChanged();

                }
                break;
            case R.id.study_show_downing:
                Intent intent = new Intent(getContext(), DownLoadingActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_select:
                if(mUserAllDownLoadInfo!=null&&mUserAllDownLoadInfo.size()>0&&mStudyDownLoadAdapter!=null){
                    Iterator<SQLDownLoadInfo> iterator = mUserAllDownLoadInfo.iterator();
                    while (iterator.hasNext()){
                        SQLDownLoadInfo next = iterator.next();
                        if(next.isCheck()){
                            ArrayList<SQLDownLoadInfo> fatherList = mDataKeeper.getUserDownLoadInfoByFatherId(next.getFatherId());
                            for (int i = 0; i < fatherList.size(); i++) {
                                mDataKeeper.deleteDownLoadInfo(fatherList.get(i).getFatherId(), fatherList.get(i).getChildId());
                                File downloadFile = new File(FILEPATH + "/" + "." + fatherList.get(i).getChildId() + ".mp4");
                                if (downloadFile.exists()) {
                                    downloadFile.delete();
                                }
                            }
                            iterator.remove();
                        }
                    }
                    mStudyDownLoadAdapter.notifyDataSetChanged();
                    if(mUserAllDownLoadInfo.size()<=0){
                        mFinishDown.setVisibility(View.GONE);
                        //TODO No Data
                        NothingShow(0);
                    }
                }

                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mIsShowMenu){
            mIsShowMenu = false;
            mFinishDownManager.setText("管理");
            mFinishDownMenu.setVisibility(View.GONE);
        }

    }

    private void NothingShow(int flag){
        if(flag==0){//没数据
            mNoData.setVisibility(View.VISIBLE);
            mNoLogin.setVisibility(View.GONE);
        }else if(flag==1){//没登录
            mNoData.setVisibility(View.GONE);
            mNoLogin.setVisibility(View.VISIBLE);
        }else{
            mNoData.setVisibility(View.GONE);
            mNoLogin.setVisibility(View.GONE);
        }
    }
}
