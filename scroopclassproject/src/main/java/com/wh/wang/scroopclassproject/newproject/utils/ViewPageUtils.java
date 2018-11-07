package com.wh.wang.scroopclassproject.newproject.utils;

import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.view.FixedSpeedScroller;

import java.lang.reflect.Field;

/**
 * Created by teitsuyoshi on 2018/7/6.
 */

public class ViewPageUtils {
    private static FixedSpeedScroller mScroller = null;
    /**
     * 设置ViewPager的滑动时间
     * @param viewpager ViewPager控件
     * @param DurationSwitch 滑动延时
     */
    public static void controlViewPagerSpeed( ViewPager viewpager, int DurationSwitch) {
        try {
            Field mField;

            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            mScroller = new FixedSpeedScroller(MyApplication.mApplication,
                    new AccelerateInterpolator());
            mScroller.setmDuration(DurationSwitch);
            mField.set(viewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
