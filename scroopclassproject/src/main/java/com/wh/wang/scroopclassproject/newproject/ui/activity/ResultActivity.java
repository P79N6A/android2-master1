package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.MessageBean;
import com.wh.wang.scroopclassproject.newproject.eventmodel.FinishTaskEntity;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.result_frag.NewApplyResultFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.result_frag.NewPayResultFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ResultActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private FrameLayout mResultFrag;
    private FragmentManager mSupportFragmentManager;
    private String mType;
    private NewApplyResultFragment mNewApplyResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        EventBus.getDefault().post(new FinishTaskEntity());
        EventBus.getDefault().register(this);
        initView();
//        initIntent();
    }

    private void initView() {
        mResultFrag = (FrameLayout) findViewById(R.id.result_frag);
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mTitlebarbackssImageViewback.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initIntent();

    }

    private void initIntent() {
        mType = getIntent().getStringExtra("type");
        String order_no = getIntent().getStringExtra("order_no");
        mSupportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        if(mType!=null){
            if("0".equals(mType)){
                String course_type = getIntent().getStringExtra("course_type");
                mTitlebarbackssName.setText("购买成功");
                NewPayResultFragment payResultFragment = new NewPayResultFragment();
                Bundle b = new Bundle();
                b.putString("order_no",order_no==null?"":order_no);
                b.putString("course_type",course_type==null?"1":course_type);
                b.putString("type",mType);
                payResultFragment.setArguments(b);
                fragmentTransaction.add(R.id.result_frag,payResultFragment);
                fragmentTransaction.commit();
            }else if("1".equals(mType)){
                mTitlebarbackssName.setText("报名成功");
                mNewApplyResultFragment = new NewApplyResultFragment();
                Bundle b = new Bundle();
                b.putString("order_no",order_no==null?"":order_no);
                b.putString("type",mType);
                mNewApplyResultFragment.setArguments(b);
                fragmentTransaction.add(R.id.result_frag,mNewApplyResultFragment);
                fragmentTransaction.commit();


            }else {
                mTitlebarbackssName.setText("报名成功");
                mNewApplyResultFragment = new NewApplyResultFragment();
                Bundle b = new Bundle();
                b.putString("order_no",order_no==null?"":order_no);
                b.putString("type",mType);
                mNewApplyResultFragment.setArguments(b);
                fragmentTransaction.add(R.id.result_frag,mNewApplyResultFragment);
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebarbackss_imageViewback:
                MessageBean bean = new MessageBean();
                bean.setMes("mes");
                EventBus.getDefault().post(bean);
                finish();

//                if("1".equals(mType)){
//                    finishApply();
//                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishTask(FinishTaskEntity entity){
        if(this!=null){
            finish();
        }
    }
    //    @Override
//    public void onBackPressed() {
//        if("1".equals(mType)){
//            finishApply();
//        }else{
//            super.onBackPressed();
//        }
//    }
//    private void finishApply(){
//        if(mNewApplyResultFragment!=null){
//            mNewApplyResultFragment.finish();
//        }
//        finish();
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MessageBean bean = new MessageBean();
        bean.setMes("mes");
        EventBus.getDefault().post(bean);
        return super.onKeyDown(keyCode, event);
    }
}
