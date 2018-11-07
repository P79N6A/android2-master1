package com.wh.wang.scroopclassproject.newproject.eventmodel;

import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by chdh on 2018/1/31.
 */

public class ContactMap implements Serializable {
    private Map<String,ContactEntity.InfoBean> mInfoBeanMap;

    public ContactMap(Map<String, ContactEntity.InfoBean> infoBeanMap) {
        mInfoBeanMap = infoBeanMap;
    }

    public Map<String, ContactEntity.InfoBean> getInfoBeanMap() {
        return mInfoBeanMap;
    }

    public void setInfoBeanMap(Map<String, ContactEntity.InfoBean> infoBeanMap) {
        mInfoBeanMap = infoBeanMap;
    }
}
