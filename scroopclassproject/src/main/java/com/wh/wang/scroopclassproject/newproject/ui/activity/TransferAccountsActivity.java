package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;

public class TransferAccountsActivity extends Activity {
    private TextView mPayFinish;
    private TextView mError;
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_accounts);
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mPayFinish = (TextView) findViewById(R.id.pay_finish);
        mError = (TextView) findViewById(R.id.error);
        mTitlebarbackssName.setText("对公转账");

        mTitlebarbackssImageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPayFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        mError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                        .parse("tel:" + "400-900-3650"));
                startActivity(intentNO);
            }
        });
    }

    private Dialog mPayFinishDig;
    public void showDialog(){
        mPayFinishDig = new Dialog(this, R.style.MyDialog);
        View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_pay_finish,null,false);
        TextView relieveTv = (TextView) dView.findViewById(R.id.relieve);
        relieveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPayFinishDig!=null&&mPayFinishDig.isShowing()){
                    mPayFinishDig.dismiss();
                }
                TransferAccountsActivity.this.finish();
            }
        });
        mPayFinishDig.setContentView(dView);
        mPayFinishDig.show();
    }
}
