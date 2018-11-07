package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.VideoInfosActivity;
import com.wh.wang.scroopclassproject.bean.SearchInfo;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by wang on 2017/8/17.
 */

public class SearchVideoAdapter extends CommonAdapter {

    private Context context;
    private List<SearchInfo.VideoBean> mDatas;

    private RelativeLayout search_item_course_rl;
    private ImageView search_item_course_iv_pics;
    private TextView search_item_course_tv_titles;
    private TextView search_item_course_tv_money;


    public SearchVideoAdapter(Context context, List<SearchInfo.VideoBean> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent,
                R.layout.search_course_item, position);

        search_item_course_rl = viewHolder.getView(R.id.search_item_course_rl);
        search_item_course_iv_pics = viewHolder.getView(R.id.search_item_course_iv_pics);
        search_item_course_tv_titles = viewHolder.getView(R.id.search_item_course_tv_titles);
        search_item_course_tv_money = viewHolder.getView(R.id.search_item_course_tv_money);


        SearchInfo.VideoBean videoBean = mDatas.get(position);
        ImageLoader.getInstance().displayImage(videoBean.getImg(), search_item_course_iv_pics, MyDisplayOptions.getOptions());
        search_item_course_tv_titles.setText(videoBean.getTitle());
        String priceStr = videoBean.getNew_price();
        double price = Double.parseDouble(priceStr);
        if (price == 0.0) {
            search_item_course_tv_money.setText("免费");
        } else {
            search_item_course_tv_money.setText("¥ " + priceStr);
        }

        final int pos = position;
        search_item_course_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInfo.VideoBean videoBean = mDatas.get(pos);
                GotoNextActUtils.getInstance().nextActivity(context,videoBean.getId(),videoBean.getType());
            }
        });

        return viewHolder.getConvertView();
    }
}
