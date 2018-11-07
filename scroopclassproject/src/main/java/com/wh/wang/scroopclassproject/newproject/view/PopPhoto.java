package com.wh.wang.scroopclassproject.newproject.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;

/**
 * Created by chdh on 2018/1/17.
 */

public class PopPhoto extends PopupWindow implements View.OnClickListener {
    private WindowManager mWindowManager;
    private DisplayMetrics mMetrics;
    public static final float H = 112.5f;

    private Context mContext;
    private LayoutInflater mInflater = LayoutInflater.from(MyApplication.mApplication);
    private TextView mCamera;
    private TextView mPhotoAlbum;

    public PopPhoto(Context context) {
        super(context);
        mContext = context;
        View view = mInflater.inflate(R.layout.view_select_photo,null,false);
        mCamera = (TextView) view.findViewById(R.id.camera);
        mPhotoAlbum = (TextView) view.findViewById(R.id.photo_album);
        initBaseInfo(view);
        initListener();
    }

    private void initListener() {
        mCamera.setOnClickListener(this);
        mPhotoAlbum.setOnClickListener(this);
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
    private int mWay = -1;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
                mWay = 0;
                break;
            case R.id.photo_album:
                mWay = 1;
                break;
        }
        this.dismiss();
        if(mOnSelectPhotoClickListener!=null){
            mOnSelectPhotoClickListener.onSelectPhotoClick(mWay);
        }
    }

    private OnSelectPhotoClickListener mOnSelectPhotoClickListener;

    public void setOnSelectPhotoClickListener(OnSelectPhotoClickListener onSelectPhotoClickListener) {
        mOnSelectPhotoClickListener = onSelectPhotoClickListener;
    }

    public interface OnSelectPhotoClickListener{
        void onSelectPhotoClick(int way);
    }
}
