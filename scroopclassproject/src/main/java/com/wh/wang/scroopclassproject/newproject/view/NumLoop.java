package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;

import java.util.List;


/**
 * Created by chdh on 2017/3/21.
 */

public class NumLoop extends PopupWindow implements View.OnClickListener, OnItemSelectedListener {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private WindowManager mWindowManager;
    private DisplayMetrics mMetrics;
    public static final int H = 210;
    private List<String> mList;

    private ImageView mKeyCancel;
    private ImageView mKeyOk;
    private LoopView mNumLoop;
    public NumLoop(final Context context, List<String> list) {
        super(context);
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        View view = mLayoutInflater.inflate(R.layout.view_register_loop, null, false);
        initView(view);
        initLoop();
        initListener();
        initBaseInfo(view);
    }

    private void initLoop() {
        mNumLoop.setNotLoop();
        mNumLoop.setTextSize(16);
        mNumLoop.setItems(mList);
        mNumLoop.setOuterTextColor(mContext.getResources().getColor(R.color.white_color_aplha));
        mNumLoop.setCenterTextColor(mContext.getResources().getColor(R.color.black));
    }

    private void initListener() {
        mNumLoop.setListener(this);
        mKeyOk.setOnClickListener(this);
        mKeyCancel.setOnClickListener(this);
    }

    private void initView(View view) {
        mKeyCancel = (ImageView) view.findViewById(R.id.key_cancel);
        mKeyOk = (ImageView) view.findViewById(R.id.key_ok);
        mNumLoop = (LoopView) view.findViewById(R.id.num_loop);
    }

    private void initBaseInfo(View view) {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(mMetrics);
        int heightPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, H, mMetrics);
        int screenWidth = mMetrics.widthPixels;
        this.setWidth(screenWidth);
        this.setHeight(heightPX);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.popAnim);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(screenWidth,heightPX);
        view.setLayoutParams(layoutParams);
        this.setContentView(view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.key_ok:
                if(mOnSelectItemClickListener!=null){
                    this.dismiss();
                    mOnSelectItemClickListener.onSelectItemClick(mNumLoop.getSelectedItem(),mList.get(mNumLoop.getSelectedItem()));
                }
                break;
            case R.id.key_cancel:
                this.dismiss();
                break;
        }
    }

    @Override
    public void onItemSelected(int index) {

    }

    private OnSelectItemClickListener mOnSelectItemClickListener;

    public void setOnSelectItemClickListener(OnSelectItemClickListener onSelectItemClickListener) {
        mOnSelectItemClickListener = onSelectItemClickListener;
    }

    public interface OnSelectItemClickListener{
        void onSelectItemClick(int pos,String item);
    }
}
