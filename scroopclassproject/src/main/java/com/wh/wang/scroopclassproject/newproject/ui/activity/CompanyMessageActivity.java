package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShareCompanyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ShareListEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CompanySharePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.ShareInfomPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CompanyShareListAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.io.Serializable;
import java.util.List;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class CompanyMessageActivity extends Activity implements View.OnClickListener {
    private RecyclerView mMemberList;
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private TextView mTitlebarbackssAction;
    private List<ShareListEntity.InfoBean.PersonListBean> mPersonList;
    private TextView mSelectNum;
    private TextView mAllSelect;
    private TextView mShareBt;
    //剩余个数
    private int mYunumber;

    private CompanyShareListAdapter mCompanyShareListAdapter;
    private CompanySharePresenter mCompanySharePresenter = new CompanySharePresenter();
    private String mUserId;
    private int num = 0;
    private boolean isFree = false;
    private String mVideoId;
    private Serializable mType;
    private ShareInfomPresenter mShareInfomPresenter = new ShareInfomPresenter();
    private String mCourseOrAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_message);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mTitlebarbackssAction.setOnClickListener(this);
        mAllSelect.setOnClickListener(this);
    }

    private void initData() {
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mVideoId = getIntent().getStringExtra("id");
        mType = getIntent().getStringExtra("type");
        mCourseOrAction = getIntent().getStringExtra("courseOrAction");
//        mUserId = "42980";
        getCompanyShareInfo();

    }

    private void getCompanyShareInfo() {
        mCompanySharePresenter.getShareList(mUserId, mVideoId, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                ShareListEntity entity = (ShareListEntity) value[0];
                if (entity.getCode() == 200) {
                    if ("1".equals(mType)) {
                        mAllSelect.setVisibility(View.VISIBLE);
                        mShareBt.setVisibility(View.GONE);
                    } else {
                        mAllSelect.setVisibility(View.GONE);
                        mShareBt.setVisibility(View.VISIBLE);
                    }
                    if("0.00".equals(entity.getInfo().getNew_price())){
                        mShareBt.setVisibility(View.GONE);
                        isFree = true;
                    }

                    try {
                        mYunumber = Integer.parseInt(entity.getInfo().getYunumber());
                    } catch (Exception e) {
                        mYunumber = 0;
                    }
                    mShareBt.setText("您已为" + entity.getInfo().getTotal() + "人购买此课程，还可以分享给" + entity.getInfo().getYunumber() + "人！");

                    mPersonList = entity.getInfo().getPersonList();
                    mCompanyShareListAdapter = new CompanyShareListAdapter(mPersonList);
                    mMemberList.setAdapter(mCompanyShareListAdapter);
                    mCompanyShareListAdapter.setOnSelectClickListener(new CompanyShareListAdapter.OnSelectClickListener() {
                        @Override
                        public void onSelectClick(int pos, int state, ImageView img) {
                            if (state == 0) {
                                if(!isFree){
                                    if ((mYunumber <= num && "2".equals(mType))) {
                                        Toast.makeText(CompanyMessageActivity.this, "达到分享上线", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                num++;
                                img.setImageResource(R.drawable.qiye_pay_taocan);
                                mPersonList.get(pos).setSelect(1);
                            } else {
                                num--;
                                img.setImageResource(R.drawable.qiye_pay_untancan);
                                mPersonList.get(pos).setSelect(0);
                            }
                            mSelectNum.setText("已选" + num + "人");
                        }
                    });
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_mYunumber",error +"  mVideoId");
            }
        });
    }

    private void initView() {
        mMemberList = (RecyclerView) findViewById(R.id.member_list);
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssAction = (TextView) findViewById(R.id.titlebarbackss_action);
        mMemberList.setLayoutManager(new LinearLayoutManager(this));
        mTitlebarbackssAction.setText("完成");
        mTitlebarbackssName.setText("企业成员列表");
        mSelectNum = (TextView) findViewById(R.id.select_num);
        mAllSelect = (TextView) findViewById(R.id.all_select);
        mShareBt = (TextView) findViewById(R.id.share_bt);
        mShareBt.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
            case R.id.titlebarbackss_action:
                StringBuffer shareTouser = new StringBuffer();
                for (int i = 0; i < mPersonList.size(); i++) {
                    if (mPersonList.get(i).getSelect() == 1) {
                        shareTouser.append(mPersonList.get(i).getId() + ",");
                    }
                }
                String s = "";
                if (shareTouser != null && shareTouser.toString() != null && shareTouser.toString().length() > 0) {
                    s = shareTouser.toString();
                    String user_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
                    mShareInfomPresenter.shareInform(user_id, mCourseOrAction, mVideoId, s, "1", new OnResultListener() {
                        @Override
                        public void onSuccess(Object... value) {
                            ShareCompanyEntity entity = (ShareCompanyEntity) value[0];
                            if (entity.getCode() == 200) {
                                CompanyMessageActivity.this.finish();
                                Toast.makeText(CompanyMessageActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CompanyMessageActivity.this, entity.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFault(String error) {
                            Toast.makeText(CompanyMessageActivity.this, "请求服务器异常", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(this, "请选择企业成员", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.all_select:
                for (int i = 0; i < mPersonList.size(); i++) {
                    mPersonList.get(i).setSelect(1);
                }
                if (mCompanyShareListAdapter != null) {
                    mCompanyShareListAdapter.notifyDataSetChanged();
                }
                num = mPersonList.size();
                mSelectNum.setText("已选" + num + "人");
                break;
        }
    }
}
