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
import com.wh.wang.scroopclassproject.activity.ReadDetailActivity;
import com.wh.wang.scroopclassproject.activity.VideoInfosActivity;
import com.wh.wang.scroopclassproject.bean.MineInfo;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.io.Serializable;
import java.util.List;

import static com.wh.wang.scroopclassproject.R.id.mine_item_study_iv_pics;
import static com.wh.wang.scroopclassproject.R.id.mine_item_study_rl;
import static com.wh.wang.scroopclassproject.R.id.mine_item_study_tv_pre;
import static com.wh.wang.scroopclassproject.R.id.mine_item_study_tv_titles;

public class MineStudyListViewAdapter extends BaseAdapter {
    private List<MineInfo.StudyBean> mDatas;
    private Context mcontext;
    public boolean showcheck = false;

    public MineStudyListViewAdapter(Context context, List<MineInfo.StudyBean> mDatas) {
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
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.mine_study_item, null);
            holder.mine_item_study_rl = (RelativeLayout) convertView.findViewById(mine_item_study_rl);
            holder.mine_item_study_iv_pics = (ImageView) convertView.findViewById(mine_item_study_iv_pics);
            holder.mine_item_study_tv_titles = (TextView) convertView.findViewById(mine_item_study_tv_titles);
            holder.mine_item_study_tv_pre = (TextView) convertView.findViewById(mine_item_study_tv_pre);
            holder.study_item_cb = (CheckBox) convertView.findViewById(R.id.study_item_cb);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (showcheck) {
            holder.study_item_cb.setVisibility(View.VISIBLE);
        } else {
            holder.study_item_cb.setVisibility(View.GONE);
        }

        holder.study_item_cb.setClickable(true);
        holder.study_item_cb.setChecked(mDatas.get(position).isCheck());
        if (mDatas.get(position).isCheck()) {
            holder.study_item_cb.setBackgroundResource(R.drawable.right);
        } else {
            holder.study_item_cb.setBackgroundResource(R.drawable.none);
        }

        final int poss = position;
        final Holder finalHolder = holder;
        holder.study_item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDatas.get(poss).setCheck(isChecked);
                if (isChecked) {
                    finalHolder.study_item_cb.setBackgroundResource(R.drawable.right);
                } else {
                    finalHolder.study_item_cb.setBackgroundResource(R.drawable.none);
                }

            }
        });

        MineInfo.StudyBean studyBean = mDatas.get(position);
        ImageLoader.getInstance().displayImage(studyBean.getImg(), holder.mine_item_study_iv_pics, MyDisplayOptions.getOptions());
        holder.mine_item_study_tv_titles.setText(studyBean.getTitle());
        holder.mine_item_study_tv_pre.setText("观看至" + studyBean.getPer());

        //设置监听事件
        final int pos = position;
        holder.mine_item_study_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递列表数据-对象-序列化
                MineInfo.StudyBean studyBean = mDatas.get(pos);
                if (studyBean.getType() == 1) {
                    Intent intent = new Intent(mcontext, VideoInfosActivity.class);
                    intent.putExtra("id",studyBean.getId());
                    intent.putExtra("type",studyBean.getType()+"");
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("index", 5);
//                    bundle.putSerializable("studyBean", (Serializable) studyBean);
//                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
                } else if (studyBean.getType() == 2) {

                    Intent intent = new Intent(mcontext, ReadDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("indexs", 5);
                    bundle.putSerializable("studyBean", (Serializable) studyBean);
                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
                } else if(studyBean.getType() == 4){
                    Intent intent = new Intent(mcontext, SumUpActivity.class);//TODO SumUpActivity
                    intent.putExtra("id",studyBean.getId());
                    intent.putExtra("type",studyBean.getType()+"");
                    mcontext.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    static class Holder {

        RelativeLayout mine_item_study_rl = null;
        ImageView mine_item_study_iv_pics = null;
        TextView mine_item_study_tv_titles = null;
        TextView mine_item_study_tv_pre = null;
        CheckBox study_item_cb = null;
    }
}
