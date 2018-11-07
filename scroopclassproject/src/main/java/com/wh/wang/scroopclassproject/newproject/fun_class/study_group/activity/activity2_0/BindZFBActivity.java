package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.BindZFBInfoFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.BindZFBResultFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

public class BindZFBActivity extends BaseActivity implements BindZFBInfoFragment.OnCommitZFBClickListener {
    private TextView mCommit;
    private int mStatus;  //0 填写信息  1 反馈页
    private FragmentManager mSupportFragmentManager;
    private BindZFBInfoFragment mBindZFBInfoFragment;
    private BindZFBResultFragment mBindZFBResultFragment;
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mUserId;
    private String mNickname;
    private String mVid;
    private String mPid;
    private String mEvent;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_bind_zfb;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(this,"user_id","");
        mNickname = SharedPreferenceUtil.getStringFromSharedPreference(this, "nickname", "");
        mVid = getIntent().getStringExtra("vid");
        mPid = getIntent().getStringExtra("pid");
        mEvent = getIntent().getStringExtra("event");
    }

    @Override
    public void initView() {

        mCommit = (TextView) findViewById(R.id.commit);
    }

    @Override
    public void initDatas() {
        mTitleC.setText("绑定支付宝");
        mSupportFragmentManager = getSupportFragmentManager();
        if(mBindZFBInfoFragment==null){
            mBindZFBInfoFragment = new BindZFBInfoFragment();
        }
        mStatus = 0;
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.bind_info,mBindZFBInfoFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStatus==0){
                    mBindZFBInfoFragment.checkInfo();
                }else{
                   Intent intent = new Intent(BindZFBActivity.this, NewMainActivity.class);
                   startActivity(intent);
                }

            }
        });
    }

    @Override
    public void initOther() {

    }

    @Override
    public void onCommitClick(String name, String no) {
        showMakeSureDig(name,no);
    }

    private Dialog mMakeSureDig;
    private void showMakeSureDig(final String name, final String no) {
        mMakeSureDig = new Dialog(BindZFBActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dig_bing_zfb_info, null);
        TextView sureName = (TextView) inflate.findViewById(R.id.sure_name);
        TextView sureNo = (TextView) inflate.findViewById(R.id.sure_no);
        TextView makeSure = (TextView) inflate.findViewById(R.id.make_sure);
        TextView alter = (TextView) inflate.findViewById(R.id.alter);

        sureName.setText("账户名："+name);
        sureNo.setText("账号："+no);
        alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMakeSureDig.dismiss();
            }
        });

        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingUtils.getInstance().showNetLoading(BindZFBActivity.this);
                mStudyGroupPresenter.bindAlipay(mUserId,mPid,mVid,mEvent, mNickname, no, name, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        LoadingUtils.getInstance().hideNetLoading();
                        Toast.makeText(mContext, "绑定成功", Toast.LENGTH_SHORT).show();
                        mMakeSureDig.dismiss();
                        replaceFrag();
                    }

                    @Override
                    public void onFault(String error) {
                        Toast.makeText(mContext, "绑定失败", Toast.LENGTH_SHORT).show();
                        LoadingUtils.getInstance().hideNetLoading();
                    }
                });

            }
        });
        //将布局设置给Dialog
        mMakeSureDig.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mMakeSureDig.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mMakeSureDig.show();//显示对话框
    }

    private void replaceFrag() {
        mCommit.setText("返回学习小组首页");
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.remove(mBindZFBInfoFragment);
        if(mBindZFBResultFragment==null)
            mBindZFBResultFragment = new BindZFBResultFragment();
        fragmentTransaction.add(R.id.bind_info,mBindZFBResultFragment);
        fragmentTransaction.commit();
        mStatus = 1;
    }
}
