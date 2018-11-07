package com.wh.wang.scroopclassproject.views;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wh.wang.scroopclassproject.R;

/**
 * @author 冯帅
 * @describe 自定义居中弹出dialog
 */
public class CenterDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private int layoutResID;

    /**
     * 要监听的控件id
     */
    private int[] listenedItems;

    private OnCenterItemClickListener listener;

    public CenterDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.dialog_custom);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        setContentView(layoutResID);
        // 宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);

        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }

    public interface OnCenterItemClickListener {

        void OnCenterItemClick(CenterDialog dialog, View view);

    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        listener.OnCenterItemClick(this, view);
    }
}

