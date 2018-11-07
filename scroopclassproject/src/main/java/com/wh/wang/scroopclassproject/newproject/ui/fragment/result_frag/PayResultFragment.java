package com.wh.wang.scroopclassproject.newproject.ui.fragment.result_frag;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CourseResultEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CourseSuccessInfoPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyMessageActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.PayCourseSAdapter;
import com.wh.wang.scroopclassproject.newproject.view.swipe.MyFoot;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;


public class PayResultFragment extends Fragment implements OnLoadMoreListener, View.OnClickListener {
    private TextView mExamineCourse;
    private TextView mResultHint;
    private int mPage = 0;
    private final int mPageRow = 10;
    private CourseSuccessInfoPresenter mCourseSuccessInfoPresenter = new CourseSuccessInfoPresenter();
    private String mUser_id;
    private String mLoginRandStr;
    private String mName;
    private String mOrder_no;
    private SwipeToLoadLayout mShareUrlList;
    private RecyclerView mSwipeTarget;
    private MyFoot mFoot;
    private List<CourseResultEntity.InfoBean.OutLinkBean> mOut_link = new ArrayList<>();
    private RelativeLayout mManyPay;
    private RelativeLayout mSinglePay;
    private TextView mSingleBt;
    private String mCourse_type;
    private TextView mOneKeyShare;


    public PayResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_result, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mShareUrlList.setOnLoadMoreListener(this);
        mExamineCourse.setOnClickListener(this);
        mSingleBt.setOnClickListener(this);
        mOneKeyShare.setOnClickListener(this);
    }

    private void initData() {
        mName = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.getApplication(),"nickname","");
        Bundle b = getArguments();
        mOrder_no = b.getString("order_no");
        mCourse_type = b.getString("course_type");
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "user_id", "");
        mLoginRandStr = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "login_rand_str", "");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mOut_link!=null&&mOut_link.size()>0){
            mOut_link.clear();
        }
        getSuccessInfo();
    }

    int mNum = 0;
    private String mCourseId;

    private void getSuccessInfo() {
        mCourseSuccessInfoPresenter.getSuccessInfo(mOrder_no, mPage+"", mPageRow+"", mUser_id, mLoginRandStr, new OnResultListener() {
            private PayCourseSAdapter mPayCourseSAdapter;

            @Override
            public void onSuccess(Object... value) {
                CourseResultEntity entity = (CourseResultEntity) value[0];
                if(entity.getStatus()==1){
                    CourseResultEntity.InfoBean entityInfo = entity.getInfo();
                    if(entityInfo!=null){
                        try {
                            mCourseId = entityInfo.getProduct_id();
                            mNum = Integer.parseInt(entityInfo.getNum());
                            if(entityInfo.getIs_company()!=null&&"1".equals(entityInfo.getIs_company())){
                                if(entityInfo.getIs_buy_company()==null||"0".equals(entityInfo.getIs_buy_company())){
                                    mOneKeyShare.setVisibility(View.VISIBLE);
                                    if(entityInfo.getPerson()!=null&&!"0".equals(entityInfo.getPerson())){
                                        Intent intent = new Intent(getContext(), CompanyMessageActivity.class);
                                        intent.putExtra("id",entityInfo.getProduct_id());
                                        intent.putExtra("type", "2");
                                        intent.putExtra("courseOrAction", "2");
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(mContext, "暂无同事加入，无法分享至企业号", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            if(mNum>0){
                                Log.e("DH_PAY_PARAM",mNum+"  "+mOrder_no);
                                mSinglePay.setVisibility(View.GONE);
                                mManyPay.setVisibility(View.VISIBLE);
                                if("1".equals(entityInfo.getBuy_self())){
                                    mExamineCourse.setVisibility(View.VISIBLE);
                                }else{
                                    mExamineCourse.setVisibility(View.GONE);
                                }
                                if(entityInfo.getOut_link().size()>0){
                                    mOut_link.addAll(entityInfo.getOut_link());
                                    if(mPayCourseSAdapter==null){
                                        mPayCourseSAdapter = new PayCourseSAdapter(mOut_link);
                                        mSwipeTarget.setAdapter(mPayCourseSAdapter);
                                        mPayCourseSAdapter.setOnShareUrlClickListener(new PayCourseSAdapter.OnShareUrlClickListener() {
                                            @Override
                                            public void onShareUrlClick(String url, String tel) {
                                                Log.e("DH_SUCCESS_INFO"," url: "+url);
                                                ShareUtil.weiChat(Constants.wx_api, 7, MyApplication.getApplication(),
                                                        url,
                                                        "您的好友"+mName+"为您购买了一节好课,点击领取",
                                                        "勺子课堂-餐饮线上教育专家");
                                            }
                                        });
                                    }else{
                                        mPayCourseSAdapter.notifyDataSetChanged();
                                    }
                                }else{
                                    Toast.makeText(MyApplication.mApplication, "没有更多了", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                mSinglePay.setVisibility(View.VISIBLE);
                                mManyPay.setVisibility(View.GONE);

                            }
                        }catch (Exception e){
                            Log.e("DH_SUCCESS_NUM","-1 "+e.toString());
                            mSinglePay.setVisibility(View.VISIBLE);
                            mManyPay.setVisibility(View.GONE);
                        }

                    }
                }else{
                    Log.e("DH_SUCCESS_INFO",entity.getStatus()+"");
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    private void initView(View view) {
        mExamineCourse = (TextView) view.findViewById(R.id.examine_course);
        mResultHint = (TextView) view.findViewById(R.id.result_hint);
        mShareUrlList = (SwipeToLoadLayout) view.findViewById(R.id.share_url_list);
        mSwipeTarget = (RecyclerView) view.findViewById(R.id.swipe_target);
        mFoot = (MyFoot) view.findViewById(R.id.foot);
        mManyPay = (RelativeLayout) view.findViewById(R.id.many_pay);
        mSinglePay = (RelativeLayout) view.findViewById(R.id.single_pay);
        mSingleBt = (TextView) view.findViewById(R.id.single_bt);
        mOneKeyShare = (TextView) view.findViewById(R.id.one_key_share);

        mSwipeTarget.setLayoutManager(new LinearLayoutManager(MyApplication.getApplication()));
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mShareUrlList.setLoadingMore(false);
                    break;
            }
        }
    };
    @Override
    public void onLoadMore() {
        mPage++;
        getSuccessInfo();
        mHandler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.examine_course:
            case R.id.single_bt:
                if("4".equals(mCourse_type)){
                    intent = new Intent(getActivity(), SumUpActivity.class);
                }else{
                    intent = new Intent(getActivity(), NewVideoInfoActivity.class);
                }
                intent.putExtra("id",mCourseId);
                intent.putExtra("type",mCourse_type);
                startActivity(intent);
                break;
            case R.id.one_key_share:
                intent = new Intent(getActivity(), CompanyMessageActivity.class);
                intent.putExtra("id",mCourseId);
                intent.putExtra("type","0");
                intent.putExtra("courseOrAction","2");
                startActivity(intent);
                break;
        }
    }
}
