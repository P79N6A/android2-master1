package com.wh.wang.scroopclassproject.newproject.eventmodel;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ReconnectionEntity {
    private boolean reconnection;

    public ReconnectionEntity(boolean reconnection) {
        this.reconnection = reconnection;
    }

    public boolean isReconnection() {
        return reconnection;
    }

    public void setReconnection(boolean reconnection) {
        this.reconnection = reconnection;
    }
}
