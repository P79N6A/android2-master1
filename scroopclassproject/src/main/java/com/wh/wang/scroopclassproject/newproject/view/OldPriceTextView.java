package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * Created by chdh on 2018/1/10.
 */

public class OldPriceTextView extends TextView {
    private boolean isCutLine = false;
    private int mLine_color = 0xFF282828;
    private float mDimension = 4;

    public OldPriceTextView(Context context) {
        super(context,null);
    }

    public OldPriceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.lineTv);
        mLine_color = array.getColor(R.styleable.lineTv_line_color, 0xFF282828);
        mDimension = array.getDimension(R.styleable.lineTv_line_height, 4);
        isCutLine = array.getBoolean(R.styleable.lineTv_show_line, false);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isCutLine){
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setColor(mLine_color);
            p.setStrokeWidth(mDimension);
            canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,p);
        }
    }

    public void setCutLine(boolean cutLine) {
        isCutLine = cutLine;
        invalidate();
    }
}
