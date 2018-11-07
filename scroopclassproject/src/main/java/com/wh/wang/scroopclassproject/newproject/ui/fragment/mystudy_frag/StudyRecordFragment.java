package com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DeleteRecordEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MyStudyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.DeleteRecordPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MainCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MyStudyPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEssayDetailActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.StudyRecordAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.wh.wang.scroopclassproject.R.id.pop_finiish;

public class StudyRecordFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout mRecord;
    private TextView mRecordManager;
    private TextView mRecordScreen;
    private RecyclerView mRecordList;
    private LinearLayout mFinishRecordMenu;
    private TextView mAllSelect;
    private TextView mDeleteSelect;
//    private ImageView mNoData;
//    private ImageView mNoLogin;

    private List<MyStudyEntity.StudyBean> mStudyList = new ArrayList<>();
    private StudyRecordAdapter mStudyRecordAdapter;
    private boolean mIsShowMenu = false;
    private boolean mIsAllSelect = false;
    private DeleteRecordPresenter mDeleteRecordPresenter = new DeleteRecordPresenter();
    private String mUserId;
    private String mMobile;
    private MyStudyPresenter mMyStudyPresenter = new MyStudyPresenter();
    private PopupWindow mPopupWindow;
    private EmptyRecycleAdapter mEmptyRecycleAdapter;

    public StudyRecordFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_record, container, false);
        initView(view);
        initRecycle();
        initListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"user_id","");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        selectList(0);
    }

    private void initListener() {
        mRecordManager.setOnClickListener(this);
        mRecordScreen.setOnClickListener(this);
        mAllSelect.setOnClickListener(this);
        mDeleteSelect.setOnClickListener(this);
    }

    private void initRecycle() {
        mRecordList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
    }

    private void initView(View view) {
        mRecord = (RelativeLayout) view.findViewById(R.id.record);
        mRecordManager = (TextView) view.findViewById(R.id.record_manager);
        mRecordScreen = (TextView) view.findViewById(R.id.record_screen);
        mRecordList = (RecyclerView) view.findViewById(R.id.record_list);
        mFinishRecordMenu = (LinearLayout) view.findViewById(R.id.finish_record_menu);
        mAllSelect = (TextView) view.findViewById(R.id.all_select);
        mDeleteSelect = (TextView) view.findViewById(R.id.delete_select);
//        mNoData = (ImageView) view.findViewById(R.id.no_data);
//        mNoLogin = (ImageView) view.findViewById(R.id.no_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record_manager:
                if(mStudyRecordAdapter!=null){
                    if(!mIsShowMenu){
                        mIsShowMenu = true;
                        mRecordManager.setText(R.string.cancel);
                        mStudyRecordAdapter.showSelect(true);
                        mFinishRecordMenu.setVisibility(View.VISIBLE);
                    }else{
                        mIsShowMenu = false;
                        mRecordManager.setText(R.string.manager);
                        mStudyRecordAdapter.showSelect(false);
                        mFinishRecordMenu.setVisibility(View.GONE);
                    }
                    mEmptyRecycleAdapter.emptyNotifyDataSetChanged();
                }
                break;
            case R.id.record_screen:
                showPopupWindow(view);
                break;
            case R.id.all_select:
                if(mStudyList!=null&&mStudyList.size()>0&&mStudyRecordAdapter!=null){
                    if(!mIsAllSelect){
                        mIsAllSelect = true;
                        for (int i = 0; i < mStudyList.size(); i++) {
                            mStudyList.get(i).setCheck(true);
                        }
                        mAllSelect.setText(R.string.cancel);
                    }else{
                        mIsAllSelect = false;
                        for (int i = 0; i < mStudyList.size(); i++) {
                            mStudyList.get(i).setCheck(false);
                        }
                        mAllSelect.setText("全选");
                    }

                    mEmptyRecycleAdapter.emptyNotifyDataSetChanged();
                }
                break;
            case R.id.delete_select:
                if(mStudyList!=null&&mStudyList.size()>0&&mStudyRecordAdapter!=null){
                    StringBuffer sb = new StringBuffer();
                    Iterator<MyStudyEntity.StudyBean> iterator = mStudyList.iterator();
                    while (iterator.hasNext()){
                        MyStudyEntity.StudyBean next = iterator.next();
                        if(next.isCheck()){
                            sb.append(next.getId()+",");
                            iterator.remove();
                        }
                    }
                    String ids = sb.toString();
                    mDeleteRecordPresenter.deleteRecord(ids, mUserId, new OnResultListener() {
                        @Override
                        public void onSuccess(Object... value) {
                            DeleteRecordEntity entity = (DeleteRecordEntity) value[0];
                            if(entity.getCode()==200){
                                Toast.makeText(MyApplication.mApplication, "成功删除", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFault(String error) {

                        }
                    });
                    mEmptyRecycleAdapter.emptyNotifyDataSetChanged();
                }
                break;
        }
    }

    public void showRecordData(List<MyStudyEntity.StudyBean> studyBeanList) {
        if(studyBeanList==null||studyBeanList.size()==0){
            //TODO

        }
        if(mStudyRecordAdapter==null||mEmptyRecycleAdapter==null){
            mStudyRecordAdapter = new StudyRecordAdapter(studyBeanList);
            mEmptyRecycleAdapter = new EmptyRecycleAdapter(mStudyRecordAdapter, R.layout.layout_empty);
            mRecordList.setAdapter(mEmptyRecycleAdapter);
            mStudyRecordAdapter.setOnRecordClickListener(new StudyRecordAdapter.OnRecordClickListener() {
                @Override
                public void onRecordClick(int pos, MyStudyEntity.StudyBean studyBean,String tag) {
                    if("2".equals(tag)){
                        new MainCoursePresenter().mainHint(mUserId, "1", studyBean.getId(), new OnResultListener() {
                            @Override
                            public void onSuccess(Object... value) {

                            }

                            @Override
                            public void onFault(String error) {

                            }
                        });
                    }

                    Intent intent = null;
                    switch (studyBean.getType()){
                        case 1:
                            intent = new Intent(getContext(), NewVideoInfoActivity.class);
                            intent.putExtra("type",studyBean.getType()+"");
                            break;
                        case 2:
                            intent = new Intent(getContext(), NewEssayDetailActivity.class);
                            break;
                        case 3:
                            break;
                        case 4:
                            intent = new Intent(getContext(), SumUpActivity.class);
                            intent.putExtra("type",studyBean.getType()+"");
                            break;
                    }
                    if(intent!=null){
                        intent.putExtra("id",studyBean.getId()+"");
                        startActivity(intent);
                    }
                }
            });
        }else{
            mEmptyRecycleAdapter.emptyNotifyDataSetChanged();
        }
    }

    private void selectList(final int i) {
        if(mStudyList!=null&&mStudyList.size()>0){
            mStudyList.clear();
        }
        if(StringUtils.isNotEmpty(mUserId)&&StringUtils.isNotEmpty(mMobile)){
//            mRecord.setVisibility(View.VISIBLE);
            mRecordManager.setVisibility(View.VISIBLE);
            mRecordScreen.setVisibility(View.VISIBLE);
            LoadingUtils.getInstance().showNetLoading(getContext());
            mMyStudyPresenter.getStudyInfo(mUserId, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    LoadingUtils.getInstance().hideNetLoading();
                    MyStudyEntity entity = (MyStudyEntity) value[0];
                    switch (i){
                        case 1:
//                            if(entity.getStudy()!=null&&entity.getStudy().size()>0){
////                                mNoData.setVisibility(View.GONE);
//                                mStudyList.addAll(entity.getStudy());
//                                NothingShow(2);
//                            }else{
//                                NothingShow(0);
//                            }
                            mStudyList.addAll(entity.getStudy());
                            break;
                        case 2:
//                            if(entity.getFinish()!=null&&entity.getFinish().size()>0){
////                                mNoData.setVisibility(View.GONE);
//                                mStudyList.addAll(entity.getFinish());
//                                NothingShow(2);
//                            }else{
//                                NothingShow(0);
//                            }
                            mStudyList.addAll(entity.getFinish());
                            break;
                        case 0:
                        default:
//                            if(entity.getAllstudy()!=null&&entity.getAllstudy().size()>0){
////                                mNoData.setVisibility(View.GONE);
//                                mStudyList.addAll(entity.getAllstudy());
//                                NothingShow(2);
//                            }else{
//                                NothingShow(0);
//                            }
                            mStudyList.addAll(entity.getAllstudy());
                            break;
                    }
                    showRecordData(mStudyList);
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_RECORD",error);
                    mStudyRecordAdapter = null;
                    LoadingUtils.getInstance().hideNetLoading();
                    mRecordList.setAdapter(new EmptyRecycleAdapter(mStudyRecordAdapter, R.layout.layout_empty));
                }
            });
        }else{
//            mRecord.setVisibility(View.GONE);
//            NothingShow(1);
            mRecordManager.setVisibility(View.GONE);
            mRecordScreen.setVisibility(View.GONE);
            mStudyRecordAdapter = null;
            mRecordList.setAdapter(new EmptyRecycleAdapter(mStudyRecordAdapter, R.layout.layout_no_login));
        }

    }

    private void showPopupWindow(View view) {
        View popupView = View.inflate(MyApplication.mApplication, R.layout.popupwindow_layout, null);
        RelativeLayout pop_all = (RelativeLayout) popupView.findViewById(R.id.pop_all);
        RelativeLayout pop_study = (RelativeLayout) popupView.findViewById(R.id.pop_study);
        RelativeLayout pop_finish = (RelativeLayout) popupView.findViewById(pop_finiish);

        pop_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectList(0);
                mPopupWindow.dismiss();
            }
        });

        pop_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectList(1);
                mPopupWindow.dismiss();
            }
        });

        pop_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectList(2);
                mPopupWindow.dismiss();
            }
        });


        //透明动画(透明--->不透明)
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(300);
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
                DisplayUtil.dip2px(MyApplication.mApplication,70),
                DisplayUtil.dip2px(MyApplication.mApplication,125), true);
        //2,设置一个透明背景(new ColorDrawable())
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //3,指定窗体位置
        //mPopupWindow.showAsDropDown(view, 50, -view.getHeight());
        int []locations = new int[2];
        view.getLocationInWindow(locations);
        mPopupWindow.showAtLocation(mRecord, Gravity.RIGHT,0,
                locations[1]-(DisplayUtil.dip2px(MyApplication.mApplication,220)));

        //4,popupView执行动画
        popupView.startAnimation(animationSet);
    }

//    private void NothingShow(int flag){
//        if(flag==0){
//            mNoData.setVisibility(View.VISIBLE);
//            mNoLogin.setVisibility(View.GONE);
//        }else if(flag==1){
//            mNoData.setVisibility(View.GONE);
//            mNoLogin.setVisibility(View.VISIBLE);
//        }else{
//            mNoData.setVisibility(View.GONE);
//            mNoLogin.setVisibility(View.GONE);
//        }
//    }
}
