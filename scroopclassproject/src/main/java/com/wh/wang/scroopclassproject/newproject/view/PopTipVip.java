package com.wh.wang.scroopclassproject.newproject.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;

public class PopTipVip extends PopupWindow implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private OnCheckClickListener mOnCheckClickListener;

    public void setOnPayClickListener(OnCheckClickListener mOnPayClickListener) {
        this.mOnCheckClickListener = mOnPayClickListener;
    }
    public PopTipVip(Context context,String phoneNum) {
        super(context);
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        View view = mLayoutInflater.inflate(R.layout.pop_tipvip,null,false);
        TextView tvPhoneNum = (TextView) view.findViewById(R.id.tv_phoneNum);
        TextView tvBack = (TextView) view.findViewById(R.id.tv_back);
        tvBack.setOnClickListener(this);
        TextView tvcontinue = (TextView) view.findViewById(R.id.tv_continue);
        tvcontinue.setOnClickListener(this);
        tvPhoneNum.setText("当前登录绑定账号："+phoneNum);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                this.dismiss();
                break;
            case R.id.tv_continue:
                break;
        }
    }
    public interface OnCheckClickListener {

        void onCheckClick();
    }
}
