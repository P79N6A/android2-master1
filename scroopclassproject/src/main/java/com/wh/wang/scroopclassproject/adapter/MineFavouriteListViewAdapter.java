package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.MyFavourite;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.util.List;


/**
 * Created by wang on 2017/8/17.
 */

public class MineFavouriteListViewAdapter extends CommonAdapter {

    private Context context;
    private List<MyFavourite.MyFavouriteBean> mDatas;

    ImageView mine_item_favouriteiv_pics;
    TextView mine_item_favouritetv_titles;
    ImageView mine_item_favouriteiv_backs;

    public MineFavouriteListViewAdapter(Context context, List<MyFavourite.MyFavouriteBean> mDatas) {
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
                R.layout.mine_favourite_item, position);

        mine_item_favouriteiv_pics = viewHolder.getView(R.id.mine_item_favouriteiv_pics);
        mine_item_favouritetv_titles = viewHolder.getView(R.id.mine_item_favouritetv_titles);
        mine_item_favouriteiv_backs = viewHolder.getView(R.id.mine_item_favouriteiv_backs);


        MyFavourite.MyFavouriteBean unavouriteBean = (MyFavourite.MyFavouriteBean) mDatas.get(position);
        ImageLoader.getInstance().displayImage(unavouriteBean.getPost_image(), mine_item_favouriteiv_pics, MyDisplayOptions.getOptions());
        mine_item_favouritetv_titles.setText(unavouriteBean.getPost_title());
        // mine_item_favouriteiv_backs

        return viewHolder.getConvertView();
    }
}
