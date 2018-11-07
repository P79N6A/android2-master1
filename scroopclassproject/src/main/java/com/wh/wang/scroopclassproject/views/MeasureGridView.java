package com.wh.wang.scroopclassproject.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by wh on 2017/8/15.
 */
public class MeasureGridView extends GridView {


    public MeasureGridView(Context context) {
        super(context);
    }

    public MeasureGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
