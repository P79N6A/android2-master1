package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class NewHomeWheelAdapter extends PagerAdapter {
    private List<NewHomeEntity.InfoBean.ScrollBean> mScrolls ;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private float rate = 5/2.0f;

    public NewHomeWheelAdapter(List<NewHomeEntity.InfoBean.ScrollBean> mScrolls, Context context) {
        this.mScrolls = mScrolls;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view  = mLayoutInflater.inflate(R.layout.item_carousel,container,false);
        ImageView scrollImg = (ImageView) view.findViewById(R.id.scroll_img);

        int w = ScreenUtils.getScreenWidth(mContext);//- DisplayUtil.dip2px(mContext,15+15);
        ViewGroup.LayoutParams layoutParams = scrollImg.getLayoutParams();
        layoutParams.width = w;
        layoutParams.height = (int) (w/rate);
        scrollImg.setLayoutParams(layoutParams);

        int pos = position%mScrolls.size();
        pos = Math.abs(pos);
        final NewHomeEntity.InfoBean.ScrollBean scrollBean = mScrolls.get(pos);
        Glide.with(MyApplication.mApplication).load(scrollBean.getImg()).into(scrollImg);
        scrollImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnWheelItemClickListener!=null) {
                    mOnWheelItemClickListener.onWheelItem(scrollBean.getProduct_id(),scrollBean.getSel_type(),scrollBean.getType());
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private OnWheelItemClickListener mOnWheelItemClickListener;

    public void setOnWheelItemClickListener(OnWheelItemClickListener onWheelItemClickListener) {
        mOnWheelItemClickListener = onWheelItemClickListener;
    }

    public interface OnWheelItemClickListener{
        void onWheelItem(String id,String sel_type,String type);
    }
}
