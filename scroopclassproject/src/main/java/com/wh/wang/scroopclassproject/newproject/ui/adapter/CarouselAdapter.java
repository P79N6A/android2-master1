package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.ReadDetailActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.AnswerActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CasebookInviteActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.OpenClassActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class CarouselAdapter extends PagerAdapter {
    private final int mPhoneW;
    private List<MainCourseEntity.ScrollBean> mScrolls;
    private Activity activity;
    private LayoutInflater mLayoutInflater;
    private double rate = 9/(5.0);

    public CarouselAdapter(List<MainCourseEntity.ScrollBean> mScrolls, Activity activity) {
        this.mScrolls = mScrolls;
        this.activity = activity;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        DisplayMetrics d = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(d);
        mPhoneW = d.widthPixels;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view  = mLayoutInflater.inflate(R.layout.item_carousel,container,false);
        ImageView scrollImg = (ImageView) view.findViewById(R.id.scroll_img);
//        ViewGroup.LayoutParams layoutParams = scrollImg.getLayoutParams();
//        layoutParams.height = (int) (mPhoneW/rate);
//        scrollImg.setLayoutParams(layoutParams);
        int pos = position%mScrolls.size();
        pos = Math.abs(pos);
        final MainCourseEntity.ScrollBean scrollBean = mScrolls.get(pos);
        Glide.with(MyApplication.mApplication).load(scrollBean.getImg()).centerCrop().into(scrollImg);
        scrollImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (scrollBean.getType()){
                    case "1": //普通视频
                        intent = new Intent(activity, NewVideoInfoActivity.class);
                        intent.putExtra("id",scrollBean.getId());
                        intent.putExtra("type",scrollBean.getType());
                        activity.startActivity(intent);
                        break;
                    case "2": //文章(下架)
                        intent = new Intent(activity, ReadDetailActivity.class);
                        intent.putExtra("indexs", 7);
                        MobclickAgent.onEvent(activity,"indexshox");
                        intent.putExtra("bannerBean", (Serializable) scrollBean);
                        activity.startActivity(intent);
                        break;
                    case "3": //活动报名帖
                        MobclickAgent.onEvent(activity,"indexshox");
                        intent = new Intent(activity, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",scrollBean.getId());
                        activity.startActivity(intent);
                        break;
                    case "4": //小结
                        intent = new Intent(activity, SumUpActivity.class);
                        intent.putExtra("id",scrollBean.getId());
                        intent.putExtra("type",scrollBean.getType());
                        activity.startActivity(intent);
                        break;
                    case "5": //公开课
                        intent = new Intent(activity, OpenClassActivity.class);
                        intent.putExtra("id",scrollBean.getId());
                        intent.putExtra("type",scrollBean.getType());
                        activity.startActivity(intent);
                        break;
                    case "6"://直播
                        intent = new Intent(activity, AnswerActivity.class);
                        intent.putExtra("exam_url",scrollBean.getUrl());
                        activity.startActivity(intent);
                        break;
                    case "8"://案例集
                        intent = new Intent(activity, CasebookInviteActivity.class);
                        intent.putExtra("event_id",scrollBean.getId());
                        activity.startActivity(intent);
                        break;
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
