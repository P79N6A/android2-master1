package com.wh.wang.scroopclassproject.bean;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.activity.EventFromActivity;

import java.io.Serializable;

/**
 * Created by wang on 2017/10/19.
 */

public class AddPersonBean implements Serializable {

    private static final long serialVersionUID = 1L;
    public RelativeLayout add_person_rlayout;
    public LinearLayout add_person_llayout;
    public int id;
    public TextView add_person_tv_name;
    public TextView add_person_tv_tel;
    public ImageView add_person_iv_delete_tag;
    public TextView add_person_tv_money;
    public EventFromActivity.DeletePersonListener delListen;

    public RelativeLayout getAdd_person_rlayout() {
        return add_person_rlayout;
    }

    public void setDelListen(EventFromActivity.DeletePersonListener delListen) {
        this.delListen = delListen;
    }

    public EventFromActivity.DeletePersonListener getDelListen() {
        return this.delListen;
    }

    public void setAdd_person_rlayout(RelativeLayout add_person_rlayout) {
        this.add_person_rlayout = add_person_rlayout;
    }

    public LinearLayout getAdd_person_llayout() {
        return add_person_llayout;
    }

    public void setAdd_person_llayout(LinearLayout add_person_llayout) {
        this.add_person_llayout = add_person_llayout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextView getAdd_person_tv_name() {
        return add_person_tv_name;
    }

    public void setAdd_person_tv_name(TextView add_person_tv_name) {
        this.add_person_tv_name = add_person_tv_name;
    }

    public TextView getAdd_person_tv_tel() {
        return add_person_tv_tel;
    }

    public void setAdd_person_tv_tel(TextView add_person_tv_tel) {
        this.add_person_tv_tel = add_person_tv_tel;
    }

    public ImageView getAdd_person_iv_delete_tag() {
        return add_person_iv_delete_tag;
    }

    public void setAdd_person_iv_delete_tag(ImageView add_person_iv_delete_tag) {
        this.add_person_iv_delete_tag = add_person_iv_delete_tag;
    }

    public TextView getAdd_person_tv_money() {
        return add_person_tv_money;
    }

    public void setAdd_person_tv_money(TextView add_person_tv_money) {
        this.add_person_tv_money = add_person_tv_money;
    }
}
