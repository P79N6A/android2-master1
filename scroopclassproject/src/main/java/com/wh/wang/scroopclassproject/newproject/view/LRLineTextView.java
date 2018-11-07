package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

/**
 * Created by teitsuyoshi on 2018/7/11.
 */

public class LRLineTextView extends TextView {
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;
    private String mText;
    private int mTextWidth;
    private int mLineSpace = 10;
    public LRLineTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LRLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mLinePaint.setColor(getCurrentTextColor());
        mLinePaint.setStrokeWidth(1);
        mLineSpace = DisplayUtil.dip2px(mContext,mLineSpace);
        if (getText()!=null) {
            mText = getText().toString();
            mLinePaint.setTextSize(getTextSize());
            Rect rect = new Rect();
            mLinePaint.getTextBounds(mText,0,mText.length(), rect);
            mTextWidth = rect.width();
            Log.e("DH_TEXT",mText+"  w:"+mTextWidth);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0,getHeight()/2,getWidth()/2-mTextWidth/2-mLineSpace,getHeight()/2,mLinePaint);
        canvas.drawLine(getWidth()/2+mTextWidth/2+mLineSpace,getHeight()/2,getWidth(),getHeight()/2,mLinePaint);
    }
}
