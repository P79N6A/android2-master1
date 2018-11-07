package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CustomerEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CustomerPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

public class ServiceDetailActivity extends BaseActivity implements OnResultListener {
    private TextView mName;
    private TextView mDes;
    private ImageView mQr;
    private TextView mWxId;
    private LinearLayout mWxContent;
    private LinearLayout mTelContent;
    private RoundedImageView mAvatar;
    private DialogUtils mDialogUtils;
    private CustomerPresenter mCustomerPresenter;
    private String mUserId;
    private CustomerEntity.InfoBean mInfoBean;
    private String mPhone; //客服手机号
    private String mWxCode = "";

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        if (getIntent()!=null) {
            mInfoBean = getIntent().getParcelableExtra("info");
        }
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    @Override
    public void initView() {
        mName = (TextView) findViewById(R.id.name);
        mDes = (TextView) findViewById(R.id.des);
        mQr = (ImageView) findViewById(R.id.qr);
        mWxId = (TextView) findViewById(R.id.wx_id);
        mWxContent = (LinearLayout) findViewById(R.id.wx_content);
        mTelContent = (LinearLayout) findViewById(R.id.tel_content);
        mAvatar = (RoundedImageView) findViewById(R.id.avatar);
    }

    @Override
    public void initDatas() {
        mTitleC.setText("专属客服");

        mDialogUtils = new DialogUtils(this);

        mCustomerPresenter = new CustomerPresenter();

        if (mInfoBean == null) {
            mCustomerPresenter.getCustomerInfo(mUserId,this);
        }else{
            setDataToUI(mInfoBean);
        }

    }


    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTelContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogUtils.showServiceTel(mPhone, 0, mWxCode, mInfoBean.getQr_code(), new DialogUtils.OnTelResultClickListener() {
                    @Override
                    public void onTelResultClick(String state) {
                        mCustomerPresenter.telResult(mUserId,mInfoBean.getCustomer_id(),state);
                    }
                });
            }
        });
        mWxContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDialogUtils.showCopyWxDig("wx");
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(mWxCode);
                Toast.makeText(MyApplication.mApplication, "复制成功，即将打开微信", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    Toast.makeText(MyApplication.mApplication, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initOther() {

    }


    @Override
    public void onSuccess(Object... value) {
        CustomerEntity customerEntity = (CustomerEntity) value[0];
        mInfoBean = customerEntity.getInfo();
        setDataToUI(mInfoBean);
    }

    @Override
    public void onFault(String error) {
        Log.e("DH_CUSTOMER",error);
    }


    private void setDataToUI(CustomerEntity.InfoBean infoBean) {
        if (StringUtils.isNotEmpty(infoBean.getAvator())) {
            Glide.with(this).load(infoBean.getAvator()).centerCrop().into(mAvatar);
        }
        if(StringUtils.isNotEmpty(infoBean.getName())){
            mName.setText(infoBean.getName());
        }
        String per = "会员用户";
        if("1".equals(infoBean.getIsvip())||"2".equals(infoBean.getIsvip())){
            per = "行动会员";
        }else if("4".equals(infoBean.getIsvip())){
            per = "新知会员";
        }
        mDes.setText("尊敬的"+per+"，您好，我叫"+infoBean.getName()+"，很高兴成为您的专属顾问，您可以添加我的微信，随时随地联系我，我会竭诚全力为您服务！");

        if (StringUtils.isNotEmpty(infoBean.getQr_code())) {
            Glide.with(this).load(infoBean.getQr_code()).centerCrop().into(mQr);
        }
        mWxCode = infoBean.getWechat_number();
        mPhone = infoBean.getPhone();
        mWxId.setText("微信ID："+mWxCode);
    }
}
