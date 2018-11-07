package com.wh.wang.scroopclassproject.newproject.mvp;

/**
 * Created by Administrator on 2017/12/13.
 */

public interface OnResultListener {
    public void onSuccess(Object... value);
    public void onFault(String error);
}
