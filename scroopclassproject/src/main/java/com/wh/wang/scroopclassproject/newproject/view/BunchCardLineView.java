package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

/**
 * Created by teitsuyoshi on 2018/7/10.
 */

public class BunchCardLineView extends View {
    private Paint mLinePaint = new Paint((Paint.ANTI_ALIAS_FLAG));
    private Paint mBigCPaint = new Paint();
    private Paint mSmallCPaint = new Paint();
    private Path mLineLPath = new Path();
    private Path mLineRPath = new Path();
    private Context mContext;

    private int mBigCRadius = 5;
    private int mSmallCRadius = 3;
    private int mCenterY = 0;
    private int mGiftSize = 8;

    private int mMagin = 0;

    private int mGiftId = R.drawable.bunch_gift;
    private boolean mIsGift = false;
    private Rect mGiftRect = new Rect();

    public BunchCardLineView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BunchCardLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mLinePaint.setColor(Color.parseColor("#8B8F97"));
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setPathEffect(new DashPathEffect(new float[] {5, 4}, 0));
        mBigCPaint.setColor(Color.parseColor("#333740"));
        mBigCPaint.setAntiAlias(true);
        mBigCPaint.setDither(true);
        mSmallCPaint.setColor(Color.parseColor("#333740"));
        mSmallCPaint.setAntiAlias(true);
        mSmallCPaint.setDither(true);

        mLinePaint.setStrokeWidth(2);
        mSmallCPaint.setStrokeWidth(10);
        mBigCPaint.setStyle(Paint.Style.STROKE);

        mBigCRadius = DisplayUtil.dip2px(MyApplication.mApplication,mBigCRadius);
        mSmallCRadius = DisplayUtil.dip2px(MyApplication.mApplication,mSmallCRadius);
        mGiftSize = DisplayUtil.dip2px(MyApplication.mApplication,mGiftSize);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterY = getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        if(mIsGift){
            mMagin = mGiftSize;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),mGiftId);
            //设置爆炸动画位置 当前拖拽气泡的位置
            mGiftRect.set((int)(getWidth()/2-mGiftSize),(int)(mCenterY-mGiftSize)
                    ,(int)(getWidth()/2+mGiftSize), (int) (mCenterY+mGiftSize));
            canvas.drawBitmap(bitmap,null, mGiftRect,mBigCPaint);
        }else{
            mMagin = mBigCRadius;
            if(mIsToday){
                mSmallCPaint.setColor(getResources().getColor(R.color.main_color));
                mBigCPaint.setColor(getResources().getColor(R.color.main_color));
            }else{
                mSmallCPaint.setColor(Color.parseColor("#333740"));
                mBigCPaint.setColor(Color.parseColor("#333740"));
            }
            canvas.drawCircle(getWidth()/2,mCenterY,mSmallCRadius,mSmallCPaint);
            canvas.drawCircle(getWidth()/2,mCenterY,mBigCRadius,mBigCPaint);
        }

//        mLineLPath.reset();
//        mLineRPath.reset();

        mLineLPath.moveTo(0, mCenterY);
        mLineLPath.lineTo(getWidth()/2-mMagin, mCenterY);

        mLineRPath.moveTo(getWidth()/2+mMagin, mCenterY);
        mLineRPath.lineTo(getWidth(), mCenterY);

        canvas.drawPath(mLineLPath, mLinePaint);
        canvas.drawPath(mLineRPath, mLinePaint);

//        canvas.drawLine(0,mCenterY,getWidth()/2-mMagin,mCenterY,mLinePaint);
//        canvas.drawLine(getWidth()/2+mMagin,mCenterY,getWidth(),mCenterY,mLinePaint);
    }

    public void setGift(boolean isGift){
        mIsGift = isGift;
        postInvalidate();
    }

    private boolean mIsToday = false;
    public void drawToday(boolean isToday){
        mIsToday = isToday;
        postInvalidate();
    }
}
