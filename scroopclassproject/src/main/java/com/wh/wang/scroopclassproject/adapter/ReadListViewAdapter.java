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
import com.wh.wang.scroopclassproject.bean.ReadInfo;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEssayDetailActivity;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/8/15.
 */

public class ReadListViewAdapter extends CommonAdapter {

    private Context context;
    private List<ReadInfo.NewsBean> mDatas;

    ImageView read_item_iv_pic;
    TextView read_item_tv_title;
    TextView read_item_tv_date;
    private RelativeLayout read_item_rl;

    public ReadListViewAdapter(Context context, List<ReadInfo.NewsBean> mDatas) {
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
                R.layout.read_item, position);

        read_item_rl = viewHolder.getView(R.id.read_item_rl);
        read_item_iv_pic = viewHolder.getView(R.id.read_item_iv_pic);
        read_item_tv_title = viewHolder.getView(R.id.read_item_tv_title);
        read_item_tv_date = viewHolder.getView(R.id.read_item_tv_date);

        ReadInfo.NewsBean newsBean = mDatas.get(position);
        ImageLoader.getInstance().displayImage(newsBean.getImg(), read_item_iv_pic, MyDisplayOptions.getOptions());
        read_item_tv_title.setText(newsBean.getTitle());
        read_item_tv_date.setText(newsBean.getPublish_shijian());

        final int pos = position;
        read_item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递列表数据-对象-序列化
                ReadInfo.NewsBean newsBean = mDatas.get(pos);
                Intent intent = new Intent(context, NewEssayDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("indexs", 1);
                bundle.putSerializable("tempbean", (Serializable) newsBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return viewHolder.getConvertView();
    }
}
