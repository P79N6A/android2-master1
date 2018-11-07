package com.wh.wang.scroopclassproject.newproject.eventmodel;

import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;

import java.util.List;

/**
 * Created by chdh on 2018/1/31.
 */

public class EventContactEntity {
    private List<ContactEntity.InfoBean> mInfoBeanList;

    public EventContactEntity(List<ContactEntity.InfoBean> infoBeanList) {
        mInfoBeanList = infoBeanList;
    }

    public List<ContactEntity.InfoBean> getInfoBeanList() {
        return mInfoBeanList;
    }

    public void setInfoBeanList(List<ContactEntity.InfoBean> infoBeanList) {
        mInfoBeanList = infoBeanList;
    }
}
