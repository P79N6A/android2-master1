package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.JXKBean;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.util.List;

/**
 * Created by wang on 2017/11/22.
 */

public class GridViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<JXKBean.EventsBean> eventList;  //报名
    private Context mContext;
    private int flag;

    public GridViewAdapter(Context mContext, List<JXKBean.EventsBean> eventList) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.eventList = eventList;
    }


    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewholder = null;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_item, null);
            viewholder.grid_rlayout = (RelativeLayout) convertView.findViewById(R.id.grid_rlayout);
            viewholder.grid_iv = (ImageView) convertView.findViewById(R.id.grid_iv);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(eventList.get(position).getImg(), viewholder.grid_iv,
                MyDisplayOptions.getOptions());

        final int ePos = position;
        viewholder.grid_rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("whwh", "点击了==" + position);
                JXKBean.EventsBean eventBean = eventList.get(ePos);
                Intent intent = new Intent(mContext, NewEventDetailsActivity.class);
                intent.putExtra("event_id",eventBean.getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        ImageView grid_iv;
        RelativeLayout grid_rlayout;
    }
}
