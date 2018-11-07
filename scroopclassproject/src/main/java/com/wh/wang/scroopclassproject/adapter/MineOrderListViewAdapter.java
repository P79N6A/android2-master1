package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.ReadDetailActivity;
import com.wh.wang.scroopclassproject.activity.VideoInfosActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.OpenClassActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by wang on 2017/8/17.
 */

public class MineOrderListViewAdapter extends CommonAdapter {

    private Context context;
    private List<MineInfo.OrderBean> mDatas;

    private RelativeLayout mine_item_order_rl;
    private ImageView mine_item_order_iv_pics;
    private TextView mine_item_order_tv_titles;
    private TextView mine_item_order_tv_money;


    public MineOrderListViewAdapter(Context context, List<MineInfo.OrderBean> mDatas) {
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
                R.layout.mine_order_item, position);

        mine_item_order_rl = viewHolder.getView(R.id.mine_item_order_rl);
        mine_item_order_iv_pics = viewHolder.getView(R.id.mine_item_order_iv_pics);
        mine_item_order_tv_titles = viewHolder.getView(R.id.mine_item_order_tv_titles);
        mine_item_order_tv_money = viewHolder.getView(R.id.mine_item_order_tv_money);


        MineInfo.OrderBean orderBean = mDatas.get(position);
//        ImageLoader.getInstance().displayImage(orderBean.getImg(), mine_item_order_iv_pics, MyDisplayOptions.getOptions());
        Glide.with(MyApplication.mApplication).load(orderBean.getImg()).centerCrop().into(mine_item_order_iv_pics);
        mine_item_order_tv_titles.setText(orderBean.getTitle());
        String moneyStr = orderBean.getMoney();
        double money = Double.parseDouble(moneyStr);
        if (money == 0.0) {
            mine_item_order_tv_money.setText("免费");
        } else {
            mine_item_order_tv_money.setText("¥ " + moneyStr);
        }

        final int pos = position;

        //设置监听事件
        mine_item_order_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递列表数据-对象-序列化
                MineInfo.OrderBean orderBean = mDatas.get(pos);
                if (orderBean.getType() == 1) {
                    Intent intent = new Intent(context, VideoInfosActivity.class);
                    intent.putExtra("id",orderBean.getId());
                    intent.putExtra("type",orderBean.getType()+"");
                    context.startActivity(intent);
                } else if (orderBean.getType() == 2) {

                    Intent intent = new Intent(context, ReadDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("indexs", 4);
                    bundle.putSerializable("orderBean", (Serializable) orderBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else if (orderBean.getType() == 3) {
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",orderBean.getId());
                    context.startActivity(intent);
//                    Intent intent = new Intent(context, OpenClassActivity.class);
//                    intent.putExtra("id",orderBean.getId());
//                    intent.putExtra("type",orderBean.getType()+"");
//                    context.startActivity(intent);
                }else if(orderBean.getType() == 4){
                    Intent intent = new Intent(context, SumUpActivity.class);//TODO SumUpActivity
                    intent.putExtra("id",orderBean.getId());
                    intent.putExtra("type",orderBean.getType()+"");
                    context.startActivity(intent);
                }else if(orderBean.getType()==5){
                    Intent intent = new Intent(context, OpenClassActivity.class);
                    intent.putExtra("id",orderBean.getId());
                    intent.putExtra("type",orderBean.getType()+"");
                    context.startActivity(intent);
                }
            }
        });


        return viewHolder.getConvertView();
    }
}
