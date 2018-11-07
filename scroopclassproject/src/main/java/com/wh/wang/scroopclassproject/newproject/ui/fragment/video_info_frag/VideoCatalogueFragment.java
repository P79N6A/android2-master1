package com.wh.wang.scroopclassproject.newproject.ui.fragment.video_info_frag;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.AnswerActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.VideoCatalogueAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoCatalogueFragment extends Fragment implements VideoCatalogueAdapter.OnCatalogueClickListener, View.OnClickListener {
    private ScrollView mCatalogueScroll;
    private RecyclerView mCatalogueList;
    private List<SumUpEntity.DirBean> mDirBeanList;
    private VideoCatalogueAdapter mVideoCatalogueAdapter;
    private ImageView mTestIcon;
    private String mVideo_id;
    private RelativeLayout mTest;
    private TextView mTestResult;
    private TextView mTestHint;
    private Button mStartTest;
    private boolean mIsPass = false;
    private String mUserId;
    private String mLogin_rand_str;
    private String mRand_str;
    private String mExam_url;

    public VideoCatalogueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnCatalogueVideoClickListener) {
            mOnCatalogueVideoClickListener = (OnCatalogueVideoClickListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_catalogue, container, false);
        mCatalogueList = (RecyclerView) view.findViewById(R.id.catalogue_list);
        mCatalogueScroll = (ScrollView) view.findViewById(R.id.catalogue_scroll);
        mTestIcon = (ImageView) view.findViewById(R.id.test_icon);
        mTestResult = (TextView) view.findViewById(R.id.test_result);
        mTestHint = (TextView) view.findViewById(R.id.test_hint);
        mStartTest = (Button) view.findViewById(R.id.start_test);
        mTest = (RelativeLayout) view.findViewById(R.id.test);

        mStartTest.setOnClickListener(this);
        mCatalogueList.setLayoutManager(new LinearLayoutManager(getContext()));
        initZB();
        return view;
    }

    private void initZB() {
//        handleIntentemm(getActivity().getIntent());
    }

    public void initCatalogue(List<SumUpEntity.DirBean> dirBeanList, final String cate_id, String video_id, String rand_str, final SumUpEntity.ExamBean examBean) {
        if(mDirBeanList!=null){
            return;
        }
        mDirBeanList = dirBeanList;
        mVideo_id = video_id;
        mRand_str = rand_str;
        mExam_url = examBean.getExam_url();
        Log.e("DH_EXAM_URL",mExam_url);
        mCatalogueList.post(new Runnable() {
            @Override
            public void run() {
                mVideoCatalogueAdapter = new VideoCatalogueAdapter(mDirBeanList, getContext(),cate_id);
                mCatalogueList.setAdapter(mVideoCatalogueAdapter);
                mVideoCatalogueAdapter.setOnCatalogueClickListener(VideoCatalogueFragment.this);

                if("1".equals(examBean.getIfkaoshi())){
                    mTest.setVisibility(View.VISIBLE);
                    if("1".equals(examBean.getIs_join())){
                        mTestResult.setText("测试结果:"+examBean.getFen()+"分");
                        if("1".equals(examBean.getIs_pass())){
                            mTestHint.setText("测试通过,点击查看毕业证书");
                            mStartTest.setText("查看证书");
                            mIsPass = true;
                        }else{
                            mTestHint.setText("测试未通过,点击右侧按钮重新测试");
                            mStartTest.setText("重新测试");
                        }
                    }
                }else{
                    mTest.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mLogin_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    /**
     * 目录点击
     *
     * @param pos
     * @param dirBean
     */
    @Override
    public void onCatalogueClick(int pos, SumUpEntity.DirBean dirBean) {
        if (mOnCatalogueVideoClickListener != null) {

            mOnCatalogueVideoClickListener.OnCatalogueVideoClick(dirBean);
        }
    }

    /**
     * 定位小结位置
     * @param view
     */
    @Override
    public void onCateLocation(final View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] locations = new int[2];
                view.getLocationInWindow(locations);
                mCatalogueScroll.scrollBy(0,locations[1]- DisplayUtil.dip2px(MyApplication.mApplication,240));
            }
        },300);
    }

    private OnCatalogueVideoClickListener mOnCatalogueVideoClickListener;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.start_test:
                Log.e("DH_TEST",mUserId+"  "+mRand_str+"   "+mLogin_rand_str);
                if(StringUtils.isNotEmpty(mUserId)&&mRand_str.equals(mLogin_rand_str)){
                    intent = new Intent(getContext(), AnswerActivity.class);
//                    if(mIsPass){
//                        mExam_url+="&res=1";
//                    }
                    intent.putExtra("exam_url",mExam_url);
                    startActivity(intent);
//                    EventBus.getDefault().post(new TKZBEntity());
//                    intent = new Intent(getContext(), LiveCacheActivity.class);
//                    startActivity(intent);
                }else{
                    intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    //刷新定位小结
    public void notifyCate(String cateID) {
        if(mVideoCatalogueAdapter!=null){
            mVideoCatalogueAdapter.nofitPlayID(cateID);
        }
    }


    public interface OnCatalogueVideoClickListener {
        void OnCatalogueVideoClick(SumUpEntity.DirBean dirBean);
    }
}
