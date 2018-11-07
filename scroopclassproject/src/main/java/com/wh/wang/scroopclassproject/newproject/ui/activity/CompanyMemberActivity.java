package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.callback.OnMemberItemClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.AdminEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.DeleteMember;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MemberEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CompanyMemberPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.SetMemberPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.AdminMemberAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CommonMemberAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.view.SwipeItemLayout;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

import java.util.List;

public class CompanyMemberActivity extends Activity implements View.OnClickListener, DialogUtils.OnShareInviteClickListener, OnRefreshListener, OnMemberItemClickListener {
    private RecyclerView mManagerList;
    private RecyclerView mMemberList;
    private AdminMemberAdapter mAdminMemberAdapter;
    private CommonMemberAdapter mCommonMemberAdapter;
    private TextView mCompanyGroupNum;
    private TextView mCompanyGroupName;
    private ImageView mBack;
    private SwipeToLoadLayout mFragSwipe;
    private TextView mManager;
    private TextView mMember;

    private TextView mInvite;
    private String mUser_id;
    private DialogUtils mDialogUtils;
    private int mStatus;
    private CompanyMemberPresenter mCompanyMemberPersenter = new CompanyMemberPresenter();
    private SetMemberPresenter mSetMemberPresenter = new SetMemberPresenter();
//    private TextView mInviteBt;
    private String mParent_id;

    private String mCurrentId;
    private String mManagerName;
    private List<MemberEntity.InfoBean.AdminListBean> mAdmin_list;
    private String mParent_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_member);
        initView();
        initRecycle();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemberInfo();
    }

    private void getMemberInfo() {
        mCompanyMemberPersenter.getCompanyMember(mUser_id, new OnResultListener() {

            private List<MemberEntity.InfoBean.VListBean> mV_list;
            @Override
            public void onSuccess(Object... value) {
                MemberEntity entity = (MemberEntity) value[0];
                if(entity.getInfo()!=null){
                    if(entity.getInfo()!=null&&entity.getInfo().getNumber()!=null&&!"".equals(entity.getInfo().getNumber())){
                        try {
                            int num = Integer.parseInt(entity.getInfo().getNumber());
                            mCompanyGroupNum.setText("企业成员" + ( num+ 1) + "人");
                        }catch (Exception e){
                            mCompanyGroupNum.setText("企业成员0人");
                        }
                    }else{
                        mCompanyGroupNum.setText("企业成员0人");
                    }
                    mCompanyGroupName.setText(entity.getInfo().getCompany_title());
//                    if (entity.getInfo().getV_list() != null && entity.getInfo().getV_list().size() > 0) {
//                        mInviteBt.setVisibility(View.GONE);
//                    } else {
//                        mInviteBt.setVisibility(View.VISIBLE);
//                    }
                    mAdmin_list = entity.getInfo().getAdmin_list();
                    mManager.setText("管理员("+mAdmin_list.size()+")人");
                    setAdminList(mAdmin_list);
                    mV_list = entity.getInfo().getV_list();
                    if(mV_list!=null){
                        setCommonList(entity.getInfo().getV_list());
                        mMember.setText("其他成员("+mV_list.size()+")人");
                    }else{
                        mMember.setText("其他成员(0)人");
                    }
                }

            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ADMIN",error);
            }
        });

    }

    private void setCommonList(List<MemberEntity.InfoBean.VListBean> v_list) {
        mCommonMemberAdapter = new CommonMemberAdapter(v_list, mStatus);
        mMemberList.setAdapter(mCommonMemberAdapter);
        mCommonMemberAdapter.setOnItemClickListener(this);
        mCommonMemberAdapter.setOnDelMemberClickListener(new CommonMemberAdapter.OnDelMemberClickListener() {
            @Override
            public void onDelClick(int pos, String id) {
                mCurrentId = id;
                showDialog("您确定从企业号中删除该成员？", "删除", 2);

            }
        });
        mCommonMemberAdapter.setOnManagerClickListener(new CommonMemberAdapter.OnManagerClickListener() {
            @Override
            public void onManagerClick(String name, int pos, String id) {
                mCurrentId = id;
                if (mAdmin_list != null && mAdmin_list.size() >= 2) {
                    showRelieveDialog(mManagerName);
                } else {
                    showDialog("您确定设置该成员为管理员？", "设置", 1);
                }
            }
        });
    }

    private void setAdminList(List<MemberEntity.InfoBean.AdminListBean> admin_list) {

        mAdminMemberAdapter = new AdminMemberAdapter(admin_list, mStatus, mParent_id);
        mManagerList.setAdapter(mAdminMemberAdapter);
        mAdminMemberAdapter.setOnItemClickListener(this);
        if(mAdmin_list.size()>1){
            mManagerName = mAdmin_list.get(1).getName();
        }
        mAdminMemberAdapter.setOnManagerClickListener(new AdminMemberAdapter.OnManagerClickListener() {
            @Override
            public void onManagerClick(String name, int pos, String id) {
                mCurrentId = id;
                showDialog("您确定取消该成员管理员身份？", "解除管理员", 0);
            }
        });
        mAdminMemberAdapter.setOnDelMemberClickListener(new AdminMemberAdapter.OnDelMemberClickListener() {
            @Override
            public void onDelClick(int pos, String id) {
                showDialog("您确定从企业号中删除该成员？", "删除", 2);
            }
        });
    }

    private void initListener() {
        mInvite.setOnClickListener(this);
//        mInviteBt.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mDialogUtils.setOnShareInviteClickListener(this);
        mFragSwipe.setOnRefreshListener(this);
    }

    private void initView() {
        mManagerList = (RecyclerView) findViewById(R.id.manager_list);
        mMemberList = (RecyclerView) findViewById(R.id.member_list);
        mInvite = (TextView) findViewById(R.id.invite);
//        mInviteBt = (TextView) findViewById(R.id.invite_bt);
        mCompanyGroupNum = (TextView) findViewById(R.id.company_group_num);
        mCompanyGroupName = (TextView) findViewById(R.id.company_group_name);
        mBack = (ImageView) findViewById(R.id.back);
        mFragSwipe = (SwipeToLoadLayout) findViewById(R.id.frag_swipe);
        mManager = (TextView) findViewById(R.id.manager);
        mMember = (TextView) findViewById(R.id.member);


        mManagerList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mMemberList.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void initData() {
        mDialogUtils = new DialogUtils(this);
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
//        mUser_id = "42980";//TODO
        mStatus = getIntent().getIntExtra("status", -1);
        mParent_id = getIntent().getStringExtra("parent_id");
        if("0".equals(mParent_id)){
            mParent_id = mUser_id;
            mStatus = 0;
        }
        mParent_name = getIntent().getStringExtra("parent_name");
    }

    private void initRecycle() {
        mManagerList.setLayoutManager(new LinearLayoutManager(MyApplication.getApplication()));
        mMemberList.setLayoutManager(new LinearLayoutManager(MyApplication.getApplication()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invite:
//            case R.id.invite_bt:
                mDialogUtils.showInviteDialog(mUser_id);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void showDialog(String title, String bt_name, final int flag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setPositiveButton(bt_name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (flag == 2) { //删除操作
                            delMember();
                        } else if (flag == 1) { //设置管理员
                            setAdmin(flag);
                        } else if (flag == 0) { //取消管理员
                            setAdmin(flag);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(false);
        d.show();
    }

    private void setAdmin(int type) {
        Log.e("DH_PARAMS", mParent_id + "  " + mCurrentId + "  " + type);
        mSetMemberPresenter.setAdmin(mParent_id, mCurrentId, type + "", new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                AdminEntity entity = (AdminEntity) value[0];
                if(entity.getCode()==200){
                    getMemberInfo();
                }else{
                    Toast.makeText(MyApplication.mApplication, ""+entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_ADMIN", error);
            }
        });
    }

    private void delMember() {
        mSetMemberPresenter.delMember(mParent_id, mCurrentId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                DeleteMember entity = (DeleteMember) value[0];
                if(entity.getCode()==200){
                    getMemberInfo();
                }else{
                    Toast.makeText(MyApplication.mApplication, ""+entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    private Dialog mRelieveDialog;

    public void showRelieveDialog(String name) {
        mRelieveDialog = new Dialog(this, R.style.MyDialog);
        View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_relieve_manager, null, false);
        TextView infoTv = (TextView) dView.findViewById(R.id.info);
        TextView cancelTv = (TextView) dView.findViewById(R.id.cancel);
        TextView relieveTv = (TextView) dView.findViewById(R.id.relieve);
        String info = "当前管理员为" + name + ",设置新的管理员将解除旧管理员权限是否继续?";
        int index = info.indexOf(",");
        SpannableString spannableString = new SpannableString(info);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4894f2")), 6, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(MyApplication.mApplication, 14)), 6, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        infoTv.setText(spannableString);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRelieveDialog != null && mRelieveDialog.isShowing()) {
                    mRelieveDialog.dismiss();
                }
            }
        });
        relieveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRelieveDialog != null && mRelieveDialog.isShowing()) {
                    mRelieveDialog.dismiss();
                }
                setAdmin(2);
            }
        });
        mRelieveDialog.setContentView(dView);
        mRelieveDialog.show();
    }

    @Override
    public void onInviteClick() {
        ShareUtil.weiChat(Constants.wx_api, 7, this,
                "http://www.shaoziketang.com/wapshaozi/enterprise.html?qid=" + mParent_id,
                mParent_name+"邀请您加入勺子课堂企业号",
                "好知识抱团学习才有趣，赶快点击加入吧！");
    }

    @Override
    public void onRefresh() {
        getMemberInfo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.mApplication, "刷新成功", Toast.LENGTH_SHORT).show();
                mFragSwipe.setRefreshing(false);
            }
        },1500);
    }

    //成员条目点击监听
    @Override
    public void onItemClick(int pos, String id) {
        if(id!=null){
            if(id.equals(mUser_id)){
                Intent intent = new Intent(this,CompanyDataActivity.class);
                intent.putExtra("url_type","2");
                intent.putExtra("parent_id",mUser_id);
                startActivity(intent);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("给他分享一节课")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CompanyMemberActivity.this,NewMainActivity.class);
                                intent.putExtra("flag","0");
                                startActivity(intent);
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        }
    }
}
