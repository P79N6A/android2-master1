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
import com.wh.wang.scroopclassproject.bean.SearchInfo;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEssayDetailActivity;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by wang on 2017/8/17.
 */

public class SearchReadAdapter extends CommonAdapter {

    private Context context;
    private List<SearchInfo.NewBean> mDatas;

    private RelativeLayout search_item_read_rl;
    private ImageView search_item_read_iv_pics;
    private TextView search_item_read_tv_titles;


    public SearchReadAdapter(Context context, List<SearchInfo.NewBean> mDatas) {
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
                R.layout.search_read_item, position);

        search_item_read_rl = viewHolder.getView(R.id.search_item_read_rl);
        search_item_read_iv_pics = viewHolder.getView(R.id.search_item_read_iv_pics);
        search_item_read_tv_titles = viewHolder.getView(R.id.search_item_read_tv_titles);


        SearchInfo.NewBean readBean = mDatas.get(position);
        ImageLoader.getInstance().displayImage(readBean.getImg(), search_item_read_iv_pics, MyDisplayOptions.getOptions());
        search_item_read_tv_titles.setText(readBean.getTitle());

        final int pos = position;
        search_item_read_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInfo.NewBean readBean = mDatas.get(pos);
                Intent intent = new Intent(context, NewEssayDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("indexs", 9);
                bundle.putSerializable("readBean", (Serializable) readBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return viewHolder.getConvertView();
    }
}
