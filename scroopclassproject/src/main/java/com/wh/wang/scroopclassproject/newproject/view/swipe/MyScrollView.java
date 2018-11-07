package com.wh.wang.scroopclassproject.newproject.view.swipe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by gzc on 2017/4/19.
 */

public class MyScrollView extends ScrollView {
    private ScrollViewListener mScrollViewListener;

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewListener != null) {
            mScrollViewListener.onScrollChanged(this,l,t, oldl,oldt);
        }
    }

    public void setOnScrollChangedListener(ScrollViewListener scrollViewListener) {
        mScrollViewListener=scrollViewListener;
    }

    public interface ScrollViewListener {
        void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);
    }

}
