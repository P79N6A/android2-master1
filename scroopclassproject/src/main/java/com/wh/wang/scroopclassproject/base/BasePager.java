package com.wh.wang.scroopclassproject.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by wang on 2017/8/14.
 * <p>
 * 公共类 基类
 */

public abstract class BasePager{

    public final Context context; //上下文
    public View rootView; //接收各个页面实例化
    public boolean isInitData;


    public BasePager(Context context) {
        this.context = context;
        rootView = baseView();
    }

    /**
     * 强制子页面实现该方法，实现想要的特定的效果
     */
    public abstract View baseView();


    /**
     * 当子页面，需要绑定数据，或者联网请求数据并且绑定的时候，重写该方法
     */
    public void baseData() {

    }

    /**
     * 当子页面,已经存在,需要重新刷新数据是 重写该方法
     */

    public void resumeData() {

    }

    /**
     * 当子页面,已经存在,从一个activity页面返回时,走的方法
     */
    public void onResumeVisible() {

    }
}
