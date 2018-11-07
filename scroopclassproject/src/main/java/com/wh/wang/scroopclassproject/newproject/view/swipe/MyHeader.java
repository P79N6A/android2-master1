package com.wh.wang.scroopclassproject.newproject.view.swipe;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;
import com.wh.wang.scroopclassproject.R;


/**
 * Created by gzc on 2017/3/30.
 */

public class MyHeader extends SwipeRefreshHeaderLayout {
    private ImageView mImageviewArrow;
//    private ImageView mImageviewSuccess;
//    private ProgressBar mProgressbarHeader;
//    private TextView mTextviewHeader;
    //自定义View的高度
    private int mHeaderTop;
    //箭头的上下转动的动画,xml代码最后贴出来
    private Animation mAnimation_Up,mAnimation_Down;
    //主要是判断箭头的指向
    private boolean isRotate=false;

    public MyHeader(Context context) {
        this(context,null);
    }

    public MyHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyHeader(Context context, AttributeSet attrs,
                    int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置高度为300,单位是px--像素
        //这里主要是为了方便
        mHeaderTop=300;
        //两个动画
        mAnimation_Up= AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        mAnimation_Down= AnimationUtils.loadAnimation(context, R.anim.rotate_down);
    }

    //上拉加在更多的笔记中有介绍
    @Override
    protected void onFinishInflate() {
        mImageviewArrow = (ImageView) findViewById(R.id.imageview_arrow);
        mImageviewArrow.setImageResource(R.drawable.loading_anilist);
        AnimationDrawable animationDrawable = (AnimationDrawable) mImageviewArrow.getDrawable();
        animationDrawable.start();
//        mImageviewSuccess = (ImageView) findViewById(R.id.imageview_success);

//        mProgressbarHeader = (ProgressBar) findViewById(R.id.progressbar_header);

//        mTextviewHeader = (TextView) findViewById(R.id.textview_header);
    }

    //上拉加在更多的笔记中有介绍
    @Override
    public void onPrepare() {
        mImageviewArrow.clearAnimation();
        mImageviewArrow.setVisibility(View.VISIBLE);
    }

    //上拉加在更多的笔记中有介绍
    @Override
    public void onMove(int y, boolean isComplete,
                       boolean automatic) {
        if(!isComplete) {
            mImageviewArrow.setVisibility(View.VISIBLE);
            if (y >= mHeaderTop) {
                if(!isRotate){
//                    mTextviewHeader.setText("松开刷新");
                    mImageviewArrow.clearAnimation();
//                    mImageviewArrow.startAnimation(mAnimation_Up);
                    isRotate=true;
                }
            } else {
                if(isRotate){
//                    mTextviewHeader.setText("下拉刷新");
                    mImageviewArrow.clearAnimation();
//                    mImageviewArrow.startAnimation(mAnimation_Down);
                    isRotate=false;
                }
            }
        }
    }

    //上拉加在更多的笔记中有介绍
    @Override
    public void onComplete() {
//        mTextviewHeader.setText("刷新完成");
//        mProgressbarHeader.setVisibility(View.GONE);
        mImageviewArrow.setVisibility(View.GONE);
//        mImageviewSuccess.setVisibility(View.VISIBLE);
    }

    //这个是正在刷新执行的方法
    @Override
    public void onRefresh() {
//        mTextviewHeader.setText("正在刷新");
//        mProgressbarHeader.setVisibility(View.VISIBLE);
        mImageviewArrow.clearAnimation();
        mImageviewArrow.setVisibility(View.VISIBLE);
//        mImageviewSuccess.setVisibility(View.GONE);
    }

    //上拉加在更多的笔记中有介绍
    @Override
    public void onReset() {
        mImageviewArrow.clearAnimation();
        mImageviewArrow.setVisibility(View.VISIBLE);
//        mImageviewSuccess.setVisibility(View.GONE);
//        mProgressbarHeader.setVisibility(View.VISIBLE);
    }
}

