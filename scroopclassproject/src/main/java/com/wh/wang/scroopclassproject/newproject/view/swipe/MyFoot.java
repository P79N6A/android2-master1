package com.wh.wang.scroopclassproject.newproject.view.swipe;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreFooterLayout;
import com.wh.wang.scroopclassproject.R;


/**
 * Created by gzc on 2017/3/30.
 */

public class MyFoot extends SwipeLoadMoreFooterLayout {
    //这个自定义View的高度
    int mBottomHeight;
    //用到的三个组件
    ImageView mImageView;
//    TextView mTextView;
//    ProgressBar mProgressBar;
    public MyFoot(Context context) {
        super(context);
    }
    public MyFoot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyFoot(Context context, AttributeSet attrs,
                  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置高度为300,单位是px--像素
        //这里主要是为了方便
        mBottomHeight=300;
    }

    //xml文件生成之后会执行这个方法,这个xml就是
    //这个自定义View,在xml中搭建好布局,每个组件设置好
    //id,在xml加载完成后,执行这个方法进行组件实例化
    //因为在自定义View中没办法像Activity一样实例化组件
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView= (ImageView) findViewById(R.id.imageview);
        mImageView.setImageResource(R.drawable.loading_anilist);
        AnimationDrawable fanimationDrawable = (AnimationDrawable) mImageView.getDrawable();
        fanimationDrawable.start();
//        mTextView= (TextView) findViewById(R.id.textview);
//        mProgressBar= (ProgressBar) findViewById(R.id.progressbar);
    }

    //以下的这几个方法都是复写了父类SwipeLoadMoreFooterLayout
    //方法

    //准备的时候执行这个方法,并不知道有什么卵用,
    //自己感觉就是在给相应的组件设置初始化状态
    //这个方法里的内容比较好理解,结合具体情境理解即可
    @Override
    public void onPrepare() {
        mImageView.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(View.GONE);
    }

    //从方法的名称可以知道方法的功能,就是向上拉动
    //的时候执行方法,我只知道第一个参数的含义
    //在这里的含义就是:本View的上边距离手机底部的距离
    //具体的参数含义去百度吧,方法内容,结合情境自行理解
    @Override
    public void onMove(int y, boolean isComplete,
                       boolean automatic) {
        if(-y>=mBottomHeight){
//            mTextView.setText("松开加载更多");
        }else{
//            mTextView.setText("放手加载更多");
        }
    }

    //加载完成时执行的方法
    @Override
    public void onComplete() {
        mImageView.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(View.GONE);
//        mTextView.setText("加载完成");
    }

    //正在加载时执行的方法
    @Override
    public void onLoadMore() {
//        mProgressBar.setVisibility(View.VISIBLE);
//        mTextView.setText("正在加载");
    }

    //不知道.......,我感觉就是重置,和onPrepare
    //方法的功能有点相似
    @Override
    public void onReset() {
        mImageView.setVisibility(View.VISIBLE);
    }
}
