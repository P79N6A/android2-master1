package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.CourseBean;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.util.List;

/**
 * Created by wang on 2017/11/22.
 */

public class CourseLableAdapter extends BaseAdapter {

    private List<CourseBean.InfoBean.AllTypeBean> typeList;  //父类标签集合
    private Context mContext;
    private LayoutInflater inflater;
    private int fireHot;
    int mSelect = 0;   //选中项

    public CourseLableAdapter(Context mContext, List<CourseBean.InfoBean.AllTypeBean> typeList, int fireHot) {
        this.mContext = mContext;
        this.typeList = typeList;
        this.fireHot = fireHot;
        inflater = LayoutInflater.from(mContext);
    }

    public void changeSelected(int positon) { //刷新方法
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.courselable_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.courseLable_rlayout = (RelativeLayout) convertView.findViewById(R.id.courseLable_rlayout);
            viewHolder.courseLable_iv = (ImageView) convertView.findViewById(R.id.courseLable_iv);
            viewHolder.courseLable_tv = (TextView) convertView.findViewById(R.id.courseLable_tv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (mSelect == position) {
            viewHolder.courseLable_rlayout.setBackgroundResource(R.drawable.couse_btn_press);  //选中项背景
        } else {
            viewHolder.courseLable_rlayout.setBackgroundColor(Color.parseColor("#eef2f9"));  //其它项背景
        }

        viewHolder.courseLable_tv.setText(typeList.get(position).getName());
        String logoUrl = typeList.get(position).getLogo();
        Log.e("whwh", "logoUrl==" + logoUrl + ":::" + logoUrl.length());
        if (logoUrl.length() != 0) {
            viewHolder.courseLable_iv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(typeList.get(position).getLogo(), viewHolder.courseLable_iv, MyDisplayOptions.getOptions());
        } else {
            viewHolder.courseLable_iv.setVisibility(View.GONE);
        }

//        if (position == 0 && fireHot == 1) {
//            viewHolder.courseLable_iv.setVisibility(View.VISIBLE);
//            viewHolder.courseLable_iv.setImageResource(R.drawable.tuijian);
//        }

        return convertView;
    }


    /**
     * 第一个item
     */
    private class ViewHolder {
        private RelativeLayout courseLable_rlayout;
        private ImageView courseLable_iv;
        private TextView courseLable_tv;
    }
}
