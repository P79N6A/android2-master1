package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.ReadDetailActivity;
import com.wh.wang.scroopclassproject.bean.MineThirdBean;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by wang on 2017/8/17.
 */

public class MineMsgDetailAdapter extends CommonAdapter {

    private Context context;
    private List<MineThirdBean.ListBean> mDatas;
    private RelativeLayout mine_item_rlayout_talk;
    private TextView mine_item_tv_talk;
    private TextView mine_item_tv_talkTime;
    private TextView mine_item_tv_talkContent;


    public MineMsgDetailAdapter(Context context, List<MineThirdBean.ListBean> mDatas) {
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
                R.layout.msg_detail_item, position);

        mine_item_rlayout_talk = viewHolder.getView(R.id.mine_item_rlayout_talk);
        mine_item_tv_talk = viewHolder.getView(R.id.mine_item_tv_talk);
        mine_item_tv_talkTime = viewHolder.getView(R.id.mine_item_tv_talkTime);
        mine_item_tv_talkContent = viewHolder.getView(R.id.mine_item_tv_talkContent);

        MineThirdBean.ListBean listBean = mDatas.get(position);
        mine_item_tv_talk.setText(listBean.getTitle());
        mine_item_tv_talkContent.setText(listBean.getContent());
        mine_item_tv_talkTime.setText(listBean.getCreate_time());

        final int pos = position;

        //设置监听事件
        mine_item_rlayout_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递列表数据-对象-序列化
                MineThirdBean.ListBean listBean = mDatas.get(pos);
                if (listBean.getItem_type() == 1) {
                    Intent intent = new Intent(context, NewVideoInfoActivity.class);
                    intent.putExtra("id",listBean.getItem_id());
                    intent.putExtra("type",listBean.getItem_type()+"");
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("index", 10);
//                    bundle.putSerializable("listBean", (Serializable) listBean);
//                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else if (listBean.getItem_type() == 2) {

                    Intent intent = new Intent(context, ReadDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("indexs", 11);
                    bundle.putSerializable("listBean", (Serializable) listBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else if (listBean.getItem_type() == 3) {
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",listBean.getId());
                    context.startActivity(intent);
                }else if(listBean.getItem_type() == 4){
                    Intent intent = new Intent(context, SumUpActivity.class);//TODO SumUpActivity
                    intent.putExtra("id",listBean.getId());
                    intent.putExtra("type",listBean.getType()+"");
                    context.startActivity(intent);

                }
            }
        });


        return viewHolder.getConvertView();
    }
}
