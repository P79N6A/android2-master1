package com.wh.wang.scroopclassproject.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.views.CenterTextView;

/**
 * Created by wang on 2017/12/11.
 */

public class SureAndCancelDialog extends Dialog implements android.view.View.OnClickListener {


    private CenterTextView tv_sure_cancel_dialog_tip;
    private RelativeLayout sure_cancel_dialog_cancel;
    private RelativeLayout sure_cancel_dialog_sure;
    private onSCClickListener mOnSCClickListener;
    private String content;
    private String cancelStr;
    private boolean isVisibleCancel;
    private String sureStr;
    private TextView tv_sure_cancel_dialog_cancel;
    private TextView tv_sure_cancel_dialog_sure;
    private View sure_cancel_view;

    public SureAndCancelDialog(Context context, String content, String cancelStr, boolean isVisibleCancel,
                               String sureStr, onSCClickListener mOnSCClickListener) {
        super(context, R.style.DialogStyle);
        this.mOnSCClickListener = mOnSCClickListener;
        this.content = content;
        this.cancelStr = cancelStr;
        this.isVisibleCancel = isVisibleCancel;
        this.sureStr = sureStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sure_cancel_dialog);
        tv_sure_cancel_dialog_tip = (CenterTextView) findViewById(R.id.tv_sure_cancel_dialog_tip); //提示的内容
        tv_sure_cancel_dialog_tip.setText(content);

        sure_cancel_dialog_cancel = (RelativeLayout) findViewById(R.id.rlayout_sure_cancel_dialog_cancel);//取消
        tv_sure_cancel_dialog_cancel = (TextView) findViewById(R.id.tv_sure_cancel_dialog_cancel);
        tv_sure_cancel_dialog_cancel.setText(cancelStr);

        //中间竖线
        sure_cancel_view = findViewById(R.id.sure_cancel_view);

        sure_cancel_dialog_sure = (RelativeLayout) findViewById(R.id.rlayout_sure_cancel_dialog_sure);//确定
        tv_sure_cancel_dialog_sure = (TextView) findViewById(R.id.tv_sure_cancel_dialog_sure);
        tv_sure_cancel_dialog_sure.setText(sureStr);

        if (isVisibleCancel) {
            tv_sure_cancel_dialog_cancel.setVisibility(View.VISIBLE);
            sure_cancel_view.setVisibility(View.VISIBLE);
        } else {
            tv_sure_cancel_dialog_cancel.setVisibility(View.GONE);
            sure_cancel_view.setVisibility(View.GONE);
        }

        sure_cancel_dialog_cancel.setOnClickListener(this);
        sure_cancel_dialog_sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlayout_sure_cancel_dialog_cancel:
                if (mOnSCClickListener != null) {
                    mOnSCClickListener.onCancel();
                }
                dismiss();
                break;
            case R.id.rlayout_sure_cancel_dialog_sure:
                if (mOnSCClickListener != null) {
                    mOnSCClickListener.onOkClick();
                    dismiss();
                }
                break;

            default:
                break;
        }
    }


    public interface onSCClickListener {
        public void onOkClick();

        void onCancel();
    }
}
