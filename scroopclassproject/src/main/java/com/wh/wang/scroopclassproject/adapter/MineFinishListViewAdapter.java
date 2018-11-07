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
import com.wh.wang.scroopclassproject.activity.ReadDetailActivity;
import com.wh.wang.scroopclassproject.activity.VideoInfosActivity;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by wang on 2017/8/17.
 */

public class MineFinishListViewAdapter extends CommonAdapter {

    private Context context;
    private List<MineInfo.StudyBean> mDatas;

    private RelativeLayout mine_item_finish_rl;
    private ImageView mine_item_finish_iv_pics;
    private TextView mine_item_finish_tv_titles;


    public MineFinishListViewAdapter(Context context, List<MineInfo.StudyBean> mDatas) {
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
                R.layout.mine_finish_item, position);

        mine_item_finish_rl = viewHolder.getView(R.id.mine_item_finish_rl);
        mine_item_finish_iv_pics = viewHolder.getView(R.id.mine_item_finish_iv_pics);
        mine_item_finish_tv_titles = viewHolder.getView(R.id.mine_item_finish_tv_titles);


        MineInfo.StudyBean finishBean = mDatas.get(position);
        ImageLoader.getInstance().displayImage(finishBean.getImg(), mine_item_finish_iv_pics, MyDisplayOptions.getOptions());
        mine_item_finish_tv_titles.setText(finishBean.getTitle());

        final int pos = position;

        //设置监听事件
        mine_item_finish_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递列表数据-对象-序列化
                MineInfo.StudyBean finishBean = mDatas.get(pos);
                if (finishBean.getType() == 1) {
                    Intent intent = new Intent(mContext, VideoInfosActivity.class);
                    intent.putExtra("id",finishBean.getId());
                    intent.putExtra("type",finishBean.getType()+"");
                    mContext.startActivity(intent);
                } else if (finishBean.getType() == 2) {

                    Intent intent = new Intent(context, ReadDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("indexs", 6);
                    bundle.putSerializable("finishBean", (Serializable) finishBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else if(finishBean.getType()==4){
                    Intent intent = new Intent(mContext, SumUpActivity.class); //TODO SumUpActivity
                    intent.putExtra("id",finishBean.getId());
                    intent.putExtra("type",finishBean.getType()+"");
                    mContext.startActivity(intent);
                }
            }
        });

        return viewHolder.getConvertView();
    }
}
