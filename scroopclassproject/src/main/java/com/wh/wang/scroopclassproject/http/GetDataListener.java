package com.wh.wang.scroopclassproject.http;

import java.io.IOException;


public abstract class GetDataListener {
    public abstract void onSuccess(Object data);
    public abstract void onFailure(IOException e);
}
