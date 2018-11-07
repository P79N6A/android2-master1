package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LocalVideoActivity;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.utils.FileUtils;

import java.io.Serializable;
import java.util.List;

public class DownedListAdapter extends BaseAdapter {
    public List<SQLDownLoadInfo> mDatas;
    private Context mcontext;
    public boolean showcheck = false;

    public DownedListAdapter(Context context, List<SQLDownLoadInfo> mDatas) {
        this.mcontext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.down_detail_list_item, null);
            holder.finish_llayout = (LinearLayout) convertView.findViewById(R.id.finish_llayout);
            holder.finish_item_cb = (CheckBox) convertView.findViewById(R.id.finish_item_cb);
            holder.finish_item_name = (TextView) convertView.findViewById(R.id.finish_item_name);
            holder.finish_item_size = (TextView) convertView.findViewById(R.id.finish_item_size);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (showcheck) {
            holder.finish_item_cb.setVisibility(View.VISIBLE);
        } else {
            holder.finish_item_cb.setVisibility(View.GONE);
        }

        holder.finish_item_cb.setClickable(true);
        holder.finish_item_cb.setChecked(mDatas.get(position).isCheck());
        if (mDatas.get(position).isCheck()) {
            holder.finish_item_cb.setBackgroundResource(R.drawable.right);
        } else {
            holder.finish_item_cb.setBackgroundResource(R.drawable.none);
        }

        final int poss = position;
        final Holder finalHolder = holder;
        holder.finish_item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDatas.get(poss).setCheck(isChecked);
                if (isChecked) {
                    finalHolder.finish_item_cb.setBackgroundResource(R.drawable.right);
                } else {
                    finalHolder.finish_item_cb.setBackgroundResource(R.drawable.none);
                }

            }
        });

        holder.finish_item_name.setText(mDatas.get(position).getChildTitle());

        holder.finish_item_size.setText(FileUtils.getFormatSize(mDatas.get(position).getChildSize()));

        final int pos = position;
        holder.finish_llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLDownLoadInfo localBean = mDatas.get(pos);
                if(mOnCheckVideoPlayClickListener!=null){
                    mOnCheckVideoPlayClickListener.onCheckPlayClick(localBean);
                }

            }
        });

        return convertView;
    }

    static class Holder {
        LinearLayout finish_llayout = null;
        TextView finish_item_name = null;
        TextView finish_item_size = null;
        CheckBox finish_item_cb = null;
    }

    private OnCheckVideoPlayClickListener mOnCheckVideoPlayClickListener;

    public void setOnCheckVideoPlayClickListener(OnCheckVideoPlayClickListener onCheckVideoPlayClickListener) {
        mOnCheckVideoPlayClickListener = onCheckVideoPlayClickListener;
    }

    public interface OnCheckVideoPlayClickListener{
        void onCheckPlayClick(SQLDownLoadInfo sqlDownLoadInfo);
    }
}
