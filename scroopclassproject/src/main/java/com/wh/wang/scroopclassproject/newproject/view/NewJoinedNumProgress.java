package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teitsuyoshi on 2018/8/10.
 */

public class NewJoinedNumProgress extends View {
    private int mRadius = 12;
    private Paint mProSRCPaint = new Paint();
    private Paint mProDSTPaint = new Paint();
    private Context mContext;


    private int mPaddingLeft;
    private int mPaddingRight;

    private int mProHeight; //初始高度

    private int Max = 150; //最大进度
    private int mCurrentNum = 0;  //当前进度
    private int mSillNum = 0;

    private float mProWidth = 0; //当前进度长度位置

    private int mRealWidth; //有效长度

    private int mStageWidth; //每一段的长度

    private LinearGradient mLinearGradient; //进度条渐变色

    private int[] mGradientColor = new int[]{Color.parseColor("#B4EC51"), Color.parseColor("#429321")};

    private int mTextWidth;
    private int mTextHeight;
    private int mTextLRPadding = 8;
    private int mTextTBPadding = mRadius;

    private String mText = mCurrentNum + "人";
    private Paint mTextPaint = new Paint();
    private Paint mTextBgPaint = new Paint();

    private List<Integer> mLimitNum = new ArrayList<>();

    private Paint mBottomTextPaint = new Paint();
    private int mBottomOffset;
    private Bitmap mZhouBgBitmap;

    public NewJoinedNumProgress(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public NewJoinedNumProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public NewJoinedNumProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
//        mLimitNum.add(50,100);

        mProSRCPaint.setColor(Color.parseColor("#dcdcdc"));
        mProSRCPaint.setAntiAlias(true);
        mProSRCPaint.setDither(true);

        mProDSTPaint.setColor(Color.parseColor("#8dc63f"));
        mProDSTPaint.setAntiAlias(true);
        mProDSTPaint.setDither(true);

        mTextPaint.setTextSize(DisplayUtil.sp2px(mContext,12));
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));

        mTextLRPadding = DisplayUtil.dip2px(mContext,12);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
        mTextWidth = rect.width();//文本的宽度
        mTextHeight = rect.height();//文本的高度

        mTextBgPaint.setColor(Color.parseColor("#8dc63f"));
        mTextBgPaint.setAntiAlias(true);
        mTextBgPaint.setDither(true);

        mBottomTextPaint.setTextSize(DisplayUtil.sp2px(mContext,12));
        mBottomTextPaint.setColor(Color.parseColor("#8B8F97"));
        mBottomOffset = mTextHeight+DisplayUtil.dip2px(mContext,5);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();

        mProHeight = getHeight() - mRadius;
        mRealWidth = getWidth() - mPaddingLeft - mPaddingRight - 2 * mRadius;
        mProWidth = mPaddingLeft + mRadius;
        mStageWidth = mRealWidth/(mLimitNum.size()+1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(new RectF(mRadius + mPaddingLeft,
                mProHeight - mRadius / 2-mBottomOffset,
                getWidth() - mRadius - mPaddingRight,
                mProHeight + mRadius / 2-mBottomOffset), mProSRCPaint);


        if (mCurrentNum >= mSillNum) {
            if (mCurrentNum >= Max) {
                mProWidth = getWidth() - mRadius - mPaddingRight;
            } else {
                mProWidth = ((mCurrentNum - mSillNum) / (float) (Max - mSillNum)) * mRealWidth + mRadius + mPaddingRight;
            }
            //渐变
            mLinearGradient = new LinearGradient(mPaddingLeft,
                    getHeight() - mRadius,
                    mProWidth,
                    getHeight() - mRadius,
                    mGradientColor, null, Shader.TileMode.CLAMP);
            mProDSTPaint.setShader(mLinearGradient);
            //进度
            canvas.drawRect(new RectF(mRadius + mPaddingLeft,
                    mProHeight - mRadius / 2-mBottomOffset,
                    mProWidth,
                    mProHeight + mRadius / 2-mBottomOffset), mProDSTPaint);

            //初始点为绿色渐变
            canvas.drawCircle(mPaddingLeft + mRadius, mProHeight-mBottomOffset, mRadius, mProDSTPaint);

        } else {
            //起始点
            canvas.drawCircle(mPaddingLeft + mRadius, mProHeight-mBottomOffset, mRadius, mProSRCPaint);
        }

        //画中心点
        for (int i = 0; i < mLimitNum.size(); i++) {
            if(mLimitNum.get(i)>=mSillNum&&mLimitNum.get(i)<=Max){
                if (mCurrentNum >= mLimitNum.get(i)) {
                    canvas.drawCircle(((mLimitNum.get(i) - mSillNum) / (float) (Max - mSillNum)) * mRealWidth + mRadius + mPaddingRight, mProHeight-mBottomOffset, mRadius, mProDSTPaint);
//                canvas.drawCircle(mPaddingLeft+mRadius+mStageWidth*(i+1), mProHeight, mRadius, mProDSTPaint);
                } else {
                    canvas.drawCircle(((mLimitNum.get(i) - mSillNum) / (float) (Max - mSillNum)) * mRealWidth + mRadius + mPaddingRight, mProHeight-mBottomOffset, mRadius, mProSRCPaint);
                }
            }

        }
//        if (mCurrentNum >= Max / 2) {
//            canvas.drawCircle(mPaddingLeft+mRadius+mRealWidth / 2, mProHeight, mRadius, mProDSTPaint);
//        } else {
//            canvas.drawCircle(mPaddingLeft+mRadius+mRealWidth / 2, mProHeight, mRadius, mProSRCPaint);
//        }

        //终点
        if (mCurrentNum >= Max) {

            canvas.drawCircle(getWidth() - mRadius - mPaddingRight, mProHeight-mBottomOffset, mRadius, mProDSTPaint);
        } else {

            canvas.drawCircle(getWidth() - mRadius - mPaddingRight, mProHeight-mBottomOffset, mRadius, mProSRCPaint);
        }

        if(mZhouBgBitmap==null){
            int w  = mTextWidth  + mTextLRPadding;
            int h = mProHeight-mBottomOffset-mRadius-(getHeight() - (mRadius * 4) - mTextHeight - mTextTBPadding-mBottomOffset);
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.renshuzhou);
            mZhouBgBitmap = setImgSize(bitmap,w,h);
        }

        canvas.drawBitmap(mZhouBgBitmap,mProWidth - mTextWidth / 2 - mTextLRPadding/2,
                getHeight() - (mRadius * 4) - mTextHeight - mTextTBPadding-mBottomOffset,mTextBgPaint);

//        canvas.drawRoundRect(new RectF(mProWidth - mTextWidth / 2 - mTextLRPadding,
//                getHeight() - (mRadius * 4) - mTextHeight - mTextTBPadding-mBottomOffset,
//                mProWidth + mTextWidth / 2 + mTextLRPadding,
//                mProHeight-mBottomOffset-mRadius), 4, 4, mTextBgPaint);
        canvas.drawText(mText, mProWidth - mTextWidth / 2, getHeight() - (mRadius * 4) - mTextTBPadding / 2-mBottomOffset, mTextPaint);

        canvas.drawText(mSillNum+"人", mPaddingLeft + mRadius - mTextWidth/4, getHeight(), mBottomTextPaint);
        for (int i = 0; i < mLimitNum.size(); i++) {
            canvas.drawText(mLimitNum.get(i)+"人",
                    ((mLimitNum.get(i) - mSillNum) / (float) (Max - mSillNum)) * mRealWidth + mRadius + mPaddingRight- mTextWidth / 2,
                    getHeight(), mBottomTextPaint);
        }
        canvas.drawText(Max+"人", getWidth() - mRadius - mPaddingRight - mTextWidth / 2, getHeight(), mBottomTextPaint);

    }

    public void setMax(int max) {
        Max = max;
    }

    public void setCurrentNum(int currentNum) {
        mCurrentNum = currentNum;
        Rect rect = new Rect();

        mText = currentNum + "人";
        mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
        mTextWidth = rect.width();//文本的宽度
        mTextHeight = rect.height();//文本的高度
        postInvalidate();
    }


    public Bitmap setImgSize(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
//        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        bm.recycle();
        return newbm;
    }

    public void setLimitNum(List<Integer> limitNum) {
        mLimitNum = limitNum;
    }
}
