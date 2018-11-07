package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.callback.OnAddressClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ALJInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.AljPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CasebooInviteAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.casebook_frag.CasebookStateFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.casebook_frag.CompleteInfoFragment;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import java.util.List;

public class CasebookInfoActivity extends FragmentActivity implements CompleteInfoFragment.OnInfoClickListener
        , View.OnClickListener ,OnAddressClickListener{
    private TextView mTitlebarbackssName;
    private TextView mCreateInvite;
    private ScrollView mInviteScroll;

    private int mPhoneHight;
    private ImageView mNoDataIcon;
    private TextView mNoDataTv;
    private RecyclerView mInviteList;
    private TextView mContinueCheck;
    private Dialog mAddressDialog;


    private String CURRENT_PROCCESS = "0"; //0 填写信息  1 好友助力  2 等待发货  3 订单详情

    private FragmentManager mFragmentManager;
    private CompleteInfoFragment mCompleteInfoFragment;
    private CasebookStateFragment mCasebookStateFragment;

    private AljPresenter mAljPresenter = new AljPresenter();
    private List<ALJInfoEntity.InfoBean.ListBean> mListBeanList;

    private String mUser_id = "";
    private String mEvent_id = "";
    private String order;
//    private DialogUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casebook_info);
        mEvent_id = getIntent().getStringExtra("event_id");
        String free_vip = getIntent().getStringExtra("free_vip");
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
//        mEvent_id = "1363";
//        dialogUtils = new DialogUtils(this);
//        dialogUtils.showLoading();
        LoadingUtils.getInstance().showNetLoading(this);
//        if(free_vip!=null&&"1".equals(free_vip)){
//
//
//        }

        initView();
        mAddressDialog = new DialogUtils().initAddressDig(this,DialogUtils.THREE_LEVEL);
        initSize();
        initListener();
        mAljPresenter.getAljInfo(mUser_id, mEvent_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                ALJInfoEntity aljInfoEntity = (ALJInfoEntity) value[0];
                if(aljInfoEntity.getCode()==200){
                    if(aljInfoEntity.getInfo()!=null){
                        ALJInfoEntity.InfoBean info = aljInfoEntity.getInfo();
                        if(StringUtils.isEmpty(info.getName())||StringUtils.isEmpty(info.getAddress())||StringUtils.isEmpty(info.getTel())){
                            CURRENT_PROCCESS = "0";
                        }else{
                            CURRENT_PROCCESS = info.getType()+"";
                        }
                        if("0".equals(info.getIs_display_vip())){
                            showFree7VipDig();
                        }
                        switch (CURRENT_PROCCESS){
                            case "0":
                                mTitlebarbackssName.setText(R.string.share_address);
                                break;
                            case "1":
                                mTitlebarbackssName.setText(R.string.wait_friend);
                                break;
                            case "2":
                                mTitlebarbackssName.setText(R.string.get_book);
                                break;
                            case "3":
                                mTitlebarbackssName.setText(R.string.check_logistics);
                                break;
                        }
                        mListBeanList = info.getList();
                        order = info.getCode();
                        setFrag(info.getName(),info.getTel(),info.getAddress());
                        if(mListBeanList==null||mListBeanList.size()==0){
                            mNoDataIcon.setVisibility(View.VISIBLE);
                            mNoDataTv.setVisibility(View.VISIBLE);
                            mInviteList.setVisibility(View.GONE);
                            mContinueCheck.setVisibility(View.GONE);
                        }else{
                            if(mListBeanList.size()>5){
                                ViewGroup.LayoutParams lp = mInviteList.getLayoutParams();
                                lp.height = DisplayUtil.dip2px(MyApplication.mApplication,68 * 5);
                                mInviteList.setLayoutParams(lp);
                                mContinueCheck.setVisibility(View.VISIBLE);
                            }else{
                                mContinueCheck.setVisibility(View.GONE);
                            }
                            mNoDataIcon.setVisibility(View.GONE);
                            mNoDataTv.setVisibility(View.GONE);
                            mInviteList.setVisibility(View.VISIBLE);
                            mInviteList.setLayoutManager(new LinearLayoutManager(CasebookInfoActivity.this));
                            mInviteList.setAdapter(new CasebooInviteAdapter(CasebookInfoActivity.this,mListBeanList));
                        }
                    }


                }else{
                    Toast.makeText(MyApplication.mApplication, aljInfoEntity.getMsg(), Toast.LENGTH_SHORT).show();
                }
                LoadingUtils.getInstance().hideNetLoading();
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ALJ_INFO",error);
                LoadingUtils.getInstance().hideNetLoading();
            }
        });


//        mAljPresenter.show7Free(mUser_id, new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//                ShowFree7Entity entity = (ShowFree7Entity) value[0];
//                if(entity.getCode()==200){
//                    if("0".equals(entity.getInfo().getIs_display_vip())){
//                        showFree7VipDig();
//                    }
//                }else{
//                    Toast.makeText(MyApplication.mApplication, entity.getMsg(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFault(String error) {
//
//            }
//        });
    }

    private void showFree7VipDig() {
        final Dialog freeVipDig = new Dialog(this, R.style.MyDialog);
        View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_free_7_vip,null,false);
        freeVipDig.setContentView(dView);
        TextView sure = (TextView) dView.findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                freeVipDig.dismiss();
            }
        });
        freeVipDig.show();
    }

    private void setFrag(String name,String tel,String address) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if("0".equals(CURRENT_PROCCESS)){
            mCompleteInfoFragment = new CompleteInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_id",mEvent_id);
            mCompleteInfoFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.invite_state_layout,mCompleteInfoFragment);
        }else {
            replaceFrag(name,tel,address);
        }
        fragmentTransaction.commit();
    }

    private void initListener() {
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCreateInvite.setOnClickListener(this);
        mInviteList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy==0&&mListBeanList.size()>5){
                    mContinueCheck.setVisibility(View.VISIBLE);
                }else{
                    mContinueCheck.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mCreateInvite = (TextView) findViewById(R.id.create_invite);
        mNoDataIcon = (ImageView) findViewById(R.id.no_data_icon);
        mNoDataTv = (TextView) findViewById(R.id.no_data_tv);
        mInviteList = (RecyclerView) findViewById(R.id.invite_list);
        mContinueCheck = (TextView) findViewById(R.id.continue_check);
        mInviteScroll = (ScrollView) findViewById(R.id.invite_scroll);
        mTitlebarbackssName.setText(R.string.invite_friend);

    }


    private void initSize() {
        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneHight = d.heightPixels;
    }



    @Override
    public void onAddressClick() {
//        startKeyStoreAni();

        mAddressDialog.show();//显示对话框

    }

    @Override
    public void onNextClick(int type,String name,String tel,String address) {
        if(mListBeanList!=null&&mListBeanList.size()<2){
            if("0".equals(CURRENT_PROCCESS)&&mCompleteInfoFragment!=null){
                CURRENT_PROCCESS = "1";
                replaceFrag(name,tel,address);
            }
        }else{
            CURRENT_PROCCESS = "2";
            mTitlebarbackssName.setText(R.string.wait_friend);
            replaceFrag(name,tel,address);
        }
        mInviteScroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                mInviteScroll.scrollTo(0,0);
            }
        },300);

    }

    private void replaceFrag(String name,String tel,String address){
        mCasebookStateFragment = new CasebookStateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("state",CURRENT_PROCCESS);
        bundle.putString("name",name);
        bundle.putString("tel",tel);
        bundle.putString("address",address);
        bundle.putString("order_id",order);
        mCasebookStateFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if(mCompleteInfoFragment!=null&&mCompleteInfoFragment.isAdded()){
            fragmentTransaction.remove(mCompleteInfoFragment);
        }
        fragmentTransaction.add(R.id.invite_state_layout,mCasebookStateFragment);
        fragmentTransaction.commit();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_invite:
                showShareDig();
                break;
        }
    }

    private Dialog mShareDialog;

    private void showShareDig() {
        //分享
        mShareDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        //初始化控件
        RelativeLayout share_rlayout_py = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_py);
        RelativeLayout share_rlayout_pyq = (RelativeLayout) inflate.findViewById(R.id.share_rlayout_pyq);
        RelativeLayout share_llayout_concel = (RelativeLayout) inflate.findViewById(R.id.share_llayout_concel);
        share_rlayout_py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                ShareUtil.weiChat(Constants.wx_api, 7,
                        CasebookInfoActivity.this, "http://www.shaoziketang.com/pcshaozi/invite_index.html?parent_id="+mUser_id,
                        getString(R.string.alj_share_title), getString(R.string.alj_share_desc),R.drawable.wx_xct_st);
            }
        });

        share_rlayout_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
                ShareUtil.weiChat(Constants.wx_api, 8,
                        CasebookInfoActivity.this, "http://www.shaoziketang.com/pcshaozi/invite_index.html?parent_id="+mUser_id,
                        getString(R.string.alj_share_title), getString(R.string.alj_share_desc),R.drawable.wx_xct_st);
            }
        });

        share_llayout_concel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
            }
        });

        //将布局设置给Dialog
        mShareDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mShareDialog.getWindow();
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
        mShareDialog.show();//显示对话框
    }

    @Override
    public void onAddressClick(String address) {
        if (mCompleteInfoFragment!=null) {
            mCompleteInfoFragment.setAddress(address);
        }
    }
}
