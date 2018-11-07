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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.DownLoadedDetailActivity;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.utils.FileUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.util.ArrayList;
import java.util.List;

import static com.wh.wang.scroopclassproject.R.id.down_finish_rl;
import static com.wh.wang.scroopclassproject.R.id.down_finish_rl_iv;
import static com.wh.wang.scroopclassproject.R.id.down_finish_rl_tv_nums;
import static com.wh.wang.scroopclassproject.R.id.down_finish_rl_tv_size;
import static com.wh.wang.scroopclassproject.R.id.down_finish_rl_tv_title;

public class DownFinishListViewAdapter extends BaseAdapter {
    public List<SQLDownLoadInfo> mDatas;
    private Context mcontext;
    public boolean showcheck = false;
    private DataKeeper dataKeeper;
    private ArrayList<SQLDownLoadInfo> fatherIdList;

    public DownFinishListViewAdapter(Context context, List<SQLDownLoadInfo> mDatas) {
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
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.down_finish_item, null);
            holder.down_finish_rl = (RelativeLayout) convertView.findViewById(down_finish_rl);
            holder.dowm_finish_item_cb = (CheckBox) convertView.findViewById(R.id.dowm_finish_item_cb);
            holder.down_finish_rl_iv = (ImageView) convertView.findViewById(down_finish_rl_iv);
            holder.down_finish_rl_tv_title = (TextView) convertView.findViewById(down_finish_rl_tv_title);
            holder.down_finish_rl_tv_nums = (TextView) convertView.findViewById(down_finish_rl_tv_nums);
            holder.down_finish_rl_tv_size = (TextView) convertView.findViewById(down_finish_rl_tv_size);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (showcheck) {
            holder.dowm_finish_item_cb.setVisibility(View.VISIBLE);
        } else {
            holder.dowm_finish_item_cb.setVisibility(View.GONE);
        }

        holder.dowm_finish_item_cb.setClickable(true);
        holder.dowm_finish_item_cb.setChecked(mDatas.get(position).isCheck());
        if (mDatas.get(position).isCheck()) {
            holder.dowm_finish_item_cb.setBackgroundResource(R.drawable.right);
        } else {
            holder.dowm_finish_item_cb.setBackgroundResource(R.drawable.none);
        }

        final int poss = position;
        final Holder finalHolder = holder;
        holder.dowm_finish_item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDatas.get(poss).setCheck(isChecked);
                if (isChecked) {
                    finalHolder.dowm_finish_item_cb.setBackgroundResource(R.drawable.right);
                } else {
                    finalHolder.dowm_finish_item_cb.setBackgroundResource(R.drawable.none);
                }

            }
        });

        SQLDownLoadInfo downLoadBean = mDatas.get(position);
        ImageLoader.getInstance().displayImage(downLoadBean.getFatherImg(), holder.down_finish_rl_iv, MyDisplayOptions.getOptions());
        holder.down_finish_rl_tv_title.setText(downLoadBean.getFatherTitle());

        dataKeeper = new DataKeeper(mcontext);
        fatherIdList = dataKeeper.getUserDownLoadInfoByFatherId(mDatas.get(position).getFatherId());

        holder.down_finish_rl_tv_nums.setText(fatherIdList.size() + "课时");

        Double fatherSize = 0.0;
        for (int i = 0; i < fatherIdList.size(); i++) {
            fatherSize = fatherSize + fatherIdList.get(i).getChildSize();
        }

        holder.down_finish_rl_tv_size.setText(FileUtils.getFormatSize(fatherSize));
        final int pos = position;

        //设置监听事件
        holder.down_finish_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递列表数据-对象-序列化
                SQLDownLoadInfo downLoadBean = mDatas.get(pos);
                Intent intent = new Intent(mcontext, DownLoadedDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fatherId", downLoadBean.getFatherId());
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });

        return convertView;
    }

    static class Holder {
        RelativeLayout down_finish_rl = null;
        CheckBox dowm_finish_item_cb = null;
        ImageView down_finish_rl_iv = null;
        TextView down_finish_rl_tv_title = null;
        TextView down_finish_rl_tv_nums = null;
        TextView down_finish_rl_tv_size = null;

    }
}
