package com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.net.VipListEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.net.VipPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

public class VipListActivity extends BaseActivity implements View.OnClickListener {
    private VipPresenter mVipPresenter = new VipPresenter();
    private String mAvatar;
    private String mUserId;

    private RelativeLayout mKnowVip;
    private TextView mKnowTv;
    private TextView mKnowPrice;
    private TextView mOpenKnow;
    private TextView mKnowInfo;
    private RelativeLayout mActionVip;
    private TextView mActionTv;
    private TextView mActionPrice;
    private TextView mOpenAction;
    private TextView mActionInfo;
    private TextView mKnowTime;
    private TextView mActionTime;



    private String mKnowStatus;
    private String mActionStatus;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_vip_list;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void initView() {

        mKnowVip = (RelativeLayout) findViewById(R.id.know_vip);
        mKnowTv = (TextView) findViewById(R.id.know_tv);
        mKnowPrice = (TextView) findViewById(R.id.know_price);
        mOpenKnow = (TextView) findViewById(R.id.open_know);
        mKnowInfo = (TextView) findViewById(R.id.know_info);
        mActionVip = (RelativeLayout) findViewById(R.id.action_vip);
        mActionTv = (TextView) findViewById(R.id.action_tv);
        mActionPrice = (TextView) findViewById(R.id.action_price);
        mOpenAction = (TextView) findViewById(R.id.open_action);
        mActionInfo = (TextView) findViewById(R.id.action_info);
        mKnowTime = (TextView) findViewById(R.id.know_time);
        mActionTime = (TextView) findViewById(R.id.action_time);


    }


    @Override
    public void initDatas() {
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mAvatar = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "avatar", "");
        mTitleC.setText("会员中心");
    }

    @Override
    public void initListener() {
        mKnowVip.setOnClickListener(this);
        mActionVip.setOnClickListener(this);
        mOpenKnow.setOnClickListener(this);
        mOpenAction.setOnClickListener(this);
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initOther() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mVipPresenter.getNewVipList(mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                VipListEntity vipListEntity = (VipListEntity) value[0];
                if (vipListEntity.getInfo()!=null) {
                    VipListEntity.InfoBean info = vipListEntity.getInfo();
                    mActionStatus = info.getAction().getStatus();
                    mKnowStatus = info.getKnow().getStatus();
                    mKnowPrice.setText(info.getKnow().getPrice()+"元/年");
                    mActionPrice.setText(info.getAction().getPrice()+"元/年");

                    if("1".equals(mKnowStatus)){
                        if("1".equals(mActionStatus)){
                            mOpenKnow.setText("查看");
                        }else{
                            mOpenKnow.setText("续费");
                        }
                        mKnowTime.setVisibility(View.VISIBLE);
                        mKnowTime.setText("有效期："+info.getKnow().getC_end_time());
                    }else{
                        if("1".equals(mActionStatus)){
                            mOpenKnow.setText("查看");
                        }else{
                            mOpenKnow.setText("开通");
                        }
                        mKnowTime.setVisibility(View.GONE);
                    }

                    if("1".equals(mActionStatus)){
                        mOpenAction.setText("续费");
                        mActionTime.setVisibility(View.VISIBLE);
                        mActionTime.setText("有效期："+info.getAction().getMember_end());
                    }else{
                        if("1".equals(mKnowStatus)){
                            mOpenAction.setText("升级");
                        }
                        mActionTime.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ERROR_VIP_LIST",error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.open_know:
            case R.id.know_vip:
                if("1".equals(mActionStatus)){
                    Toast toast = Toast.makeText(this, "您已是行动会员\n享受新知会员所有权益", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                intent = new Intent(this,VipDetailActivity.class);
                intent.putExtra("event_id","1407");
                intent.putExtra("know_status",mKnowStatus);
                intent.putExtra("action_status",mActionStatus);
                startActivity(intent);
                break;
            case R.id.open_action:
            case R.id.action_vip:
                intent = new Intent(this,VipDetailActivity.class);
                intent.putExtra("event_id","997");
                intent.putExtra("know_status",mKnowStatus);
                intent.putExtra("action_status",mActionStatus);
                startActivity(intent);
                break;
        }
    }

}
