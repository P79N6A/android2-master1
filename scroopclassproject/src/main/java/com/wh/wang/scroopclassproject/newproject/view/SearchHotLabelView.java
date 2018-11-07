package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

import java.util.List;

/**
 * Created by dh on 2018/6/22.
 */

public class SearchHotLabelView extends ViewGroup {
    //标签文本集合
    private List<String> mLabels;
    private Context mContext;
    //标签文本Padding
    private int mTextLeftPadding = 8;
    private int mTextRightPadding = 8;
    private int mTextTopPadding = 4;
    private int mTextBottomPadding = 4;
    //标签文本大小
    private int mTextSize = 12;
    //标签背景
    private Drawable mLabelBg;
    //标签文本颜色
    private String mTextColor = "#3C4A55";
    //保存标签位置
    private static final int LABEL_POS = R.id.label_pos;
    //保存标签信息
    private static final int LABEL_DATA = R.id.label_data;

    //标签间隔
    private int mLabelInterval;
    //标签行间隔
    private int mLabelLineInterval;

    public SearchHotLabelView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SearchHotLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }


    private void init() {
        mLabelBg = getResources().getDrawable(R.drawable.shape_hot_label_bg);
        mLabelInterval = DisplayUtil.dip2px(mContext,10);
        mLabelLineInterval = DisplayUtil.dip2px(mContext,10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //控件宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //获取子控件数
        int childCount = getChildCount();
        //子控件高度
        int childHeight = 0;
        //行数
        int lineCount = 1;
        //当前行的宽度
        int lineWidth = 0;
        //当前行最大有效宽度
        int maxLineWidth = width-getPaddingLeft()-getPaddingRight();
        //是否为行首
        boolean begin = true;

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            //测量子控件
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
            //获取最高子控件高度
            childHeight = Math.max(childHeight,view.getMeasuredHeight());
            //如果不是行首则累加标签间距
            if(!begin){
                lineWidth += mLabelInterval;
            }else{
                begin = false;
            }
            //当累加宽度超过最大有效宽度则换行
            if(view.getMeasuredWidth()+lineWidth>=maxLineWidth){
                lineCount++;
                lineWidth = 0;
                begin = true;
            }
            lineWidth+=view.getMeasuredWidth();
        }


        int height = getPaddingBottom()+getPaddingTop()+childHeight*lineCount+mLabelLineInterval*(lineCount-1);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //最大宽度
        int width = r-l;
        //行当前宽度
        int lineWidth = getPaddingLeft();
        //当前高度
        int lineHeight = getPaddingTop();

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            //当前控件宽度
            int childWidth = view.getMeasuredWidth();
            //当前控件高度
            int childHeight = view.getMeasuredHeight();

            if(lineWidth+childWidth+getPaddingRight()>width){
                //新排一行 累加控件高度和行间隔
                lineHeight+=childHeight+mLabelLineInterval;
                //重置行首
                lineWidth = getPaddingLeft();
            }
            //摆放控件位置
            view.layout(lineWidth,lineHeight,lineWidth+childWidth,lineHeight+childHeight);
            lineWidth+=childWidth+mLabelInterval;
        }

//        int x = getPaddingLeft();
//        int y = getPaddingTop();
//
//        int contentWidth = r - l;
//        int maxItemHeight = 0;
//
//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            View view = getChildAt(i);
//
//            if (contentWidth < x + view.getMeasuredWidth() + getPaddingRight()) {
//                x = getPaddingLeft();
//                y += mLabelInterval;
//                y += maxItemHeight;
//                maxItemHeight = 0;
//            }
//            view.layout(x, y, x + view.getMeasuredWidth(), y + view.getMeasuredHeight());
//            x += view.getMeasuredWidth();
//            x += mLabelInterval;
//            maxItemHeight = Math.max(maxItemHeight, view.getMeasuredHeight());
//        }
    }



    public void setLabels(List<String> labels){
        clearLabels();
        mLabels = labels;
        //遍历标签集合添加控件
        for (int i = 0; i < labels.size(); i++) {
            final TextView label = new TextView(mContext);
            //设置Padding
            label.setPadding(DisplayUtil.dip2px(mContext,mTextLeftPadding),DisplayUtil.dip2px(mContext,mTextTopPadding)
                            ,DisplayUtil.dip2px(mContext,mTextRightPadding),DisplayUtil.dip2px(mContext,mTextBottomPadding));
            //设置文字大小
            label.setTextSize(mTextSize);
            //设置背景 多个控件共用一个drawable时会出现问题
            label.setBackgroundDrawable(mLabelBg.getConstantState().newDrawable());
            //设置文本颜色
            label.setTextColor(Color.parseColor(mTextColor));
            //设置文本
            label.setText(labels.get(i));
            //设置Tag信息 位置和文本
            label.setTag(LABEL_POS,i);
            label.setTag(LABEL_DATA,labels.get(i));
            label.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnSearchLabelClickListener!=null){
                        mOnSearchLabelClickListener.onLabelClick((int)label.getTag(LABEL_POS),(String) label.getTag(LABEL_DATA));
                    }
                }
            });
            //添加控件
            addView(label);
        }
    }

    private void clearLabels() {
        int childCount = getChildCount();
        if(childCount>0){
            removeAllViews();
        }
    }

    private OnSearchLabelClickListener mOnSearchLabelClickListener;

    public void setOnSearchLabelClickListener(OnSearchLabelClickListener onSearchLabelClickListener) {
        mOnSearchLabelClickListener = onSearchLabelClickListener;
    }

    public interface OnSearchLabelClickListener{
        void onLabelClick(int index, String data);
    }
}
