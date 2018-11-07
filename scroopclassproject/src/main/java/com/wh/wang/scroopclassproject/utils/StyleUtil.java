package com.wh.wang.scroopclassproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * 窗口风格通用类
 *
 * @author wang
 */
public class StyleUtil {

    private static View view;

    /**
     * 带有标题栏的
     */
    public static void customStyle(final Activity activity, int contentViewId,
                                   String title) {
        // 如果有父Activity，如：是TAB中的一页，则直接设置ContentView
        if (activity.getParent() != null) {
            activity.setContentView(contentViewId);
            return;
        }
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.setContentView(contentViewId);
        view = LayoutInflater.from(activity).inflate(R.layout.titlebarback, null);
        TextView text = (TextView) activity.findViewById(R.id.title_name);
        text.setText(title);
        ImageView title_imageViewback = (ImageView) activity
                .findViewById(R.id.title_imageViewback);// gua_top_back
        title_imageViewback.setVisibility(View.VISIBLE);
        title_imageViewback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    /**
     * 带有标题栏的
     */
    public static void customStyle2(final Activity activity, int contentViewId,
                                    String title) {
        // 如果有父Activity，如：是TAB中的一页，则直接设置ContentView
        if (activity.getParent() != null) {
            activity.setContentView(contentViewId);
            return;
        }

        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.setContentView(contentViewId);
        view = LayoutInflater.from(activity).inflate(R.layout.titlebarback_new, null);
        TextView text = (TextView) activity.findViewById(R.id.titlebarbackss_name);
        text.setText(title);
        ImageView title_imageViewback = (ImageView) activity
                .findViewById(R.id.titlebarbackss_imageViewback);// gua_top_back
        title_imageViewback.setVisibility(View.VISIBLE);
        title_imageViewback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }


    //启动新的activity
    public static void goToActivity(Context context, Class Activity, Bundle bundle) {
        Intent intent = new Intent(context, Activity);
        //携带数据
        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
        }
        context.startActivity(intent);
    }
}
