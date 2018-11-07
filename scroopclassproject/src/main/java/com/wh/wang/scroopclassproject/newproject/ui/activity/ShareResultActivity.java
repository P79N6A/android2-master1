package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

public class ShareResultActivity extends Activity implements View.OnClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private TextView mCopy;
    private String mShaozi_code;
    private RoundedImageView mShareAvatar;
    private TextView mShareName;
    private TextView mShareCode;
    private String mShaozi_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_result);
        initView();
        initListener();
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mCopy = (TextView) findViewById(R.id.copy);
        mShareAvatar = (RoundedImageView) findViewById(R.id.share_avatar);
        mShareName = (TextView) findViewById(R.id.share_name);
        mShareCode = (TextView) findViewById(R.id.share_code);

        mShaozi_code = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"shaozi_code","");
        String img = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"shaozi_img","");
        mShaozi_name = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication,"shaozi_name","");
        if(img!=null&&!"".equals(img)){
            Glide.with(MyApplication.mApplication).load(img).centerCrop().into(mShareAvatar);
        }
        mShareName.setText("微信号："+mShaozi_name);
        mShareCode.setText("进群暗号："+mShaozi_code);
        mTitlebarbackssName.setText("分享成功");
    }


    private void initListener() {
        mTitlebarbackssImageViewback.setOnClickListener(this);
        mCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titlebarbackss_imageViewback:
                finish();
                break;
            case R.id.copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(mShaozi_name);
                Toast.makeText(this, "复制成功，文案提示，复制成功，赶紧去添加微信吧", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
