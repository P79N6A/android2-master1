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
import com.wh.wang.scroopclassproject.bean.CourseBean;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2017/9/4.
 */

public class CourseAdapter extends CommonAdapter {
    private Context context;
    private List mDatas;
    RelativeLayout rlayout_one;
    ImageView superiror_item_iv_one;
    TextView superiror_item_title_one;
    TextView superiror_item_maintalk_one;
    TextView superiror_item_study_one;

    RelativeLayout rlayout_two;
    ImageView superiror_item_iv_two;
    TextView superiror_item_title_two;
    TextView superiror_item_maintalk_two;
    TextView superiror_item_study_two;

    public CourseAdapter(Context context, List mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas.size() % 2 == 0)
            return mDatas.size() / 2;
        else
            return mDatas.size() / 2 + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent,
                R.layout.superior_detail_item, position);

        rlayout_one = viewHolder.getView(R.id.superior_event_item_rlayout_one);
        superiror_item_iv_one = viewHolder.getView(R.id.superior_event_item_iv_one);
        superiror_item_title_one = viewHolder.getView(R.id.superior_event_item_title_one);
        superiror_item_maintalk_one = viewHolder.getView(R.id.superior_event_item_maintalk_one);
        superiror_item_study_one = viewHolder.getView(R.id.superior_event_item_study_one);

        rlayout_two = viewHolder.getView(R.id.superior_event_item_rlayout_two);
        superiror_item_iv_two = viewHolder.getView(R.id.superior_event_item_iv_two);
        superiror_item_title_two = viewHolder.getView(R.id.superior_event_item_title_two);
        superiror_item_maintalk_two = viewHolder.getView(R.id.superior_event_item_maintalk_two);
        superiror_item_study_two = viewHolder.getView(R.id.superior_event_item_study_two);

        position = position * 2;
        if (position < mDatas.size()) {
            rlayout_one.setVisibility(View.VISIBLE);

            CourseBean.InfoBean.CourseListBean courseListBean = (CourseBean.InfoBean.CourseListBean) mDatas.get(position);
            String temp_img = courseListBean.getImg();
            ImageLoader.getInstance().displayImage(temp_img, superiror_item_iv_one, MyDisplayOptions.getOptions());
            superiror_item_title_one.setText(courseListBean.getTitle());
            superiror_item_maintalk_one.setVisibility(View.VISIBLE);
            String tempStrprice = courseListBean.getNew_price();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                superiror_item_maintalk_one.setText("免费");
            } else {
                superiror_item_maintalk_one.setText("¥ " + tempprice);
            }

            superiror_item_study_one.setVisibility(View.INVISIBLE);
            final int proPosition = position;
            rlayout_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //3.传递列表数据-对象-序列化
                    CourseBean.InfoBean.CourseListBean kcBean = (CourseBean.InfoBean.CourseListBean) mDatas.get(proPosition);
                    GotoNextActUtils.getInstance().nextActivity(context,kcBean.getId(),kcBean.getType());
//                    Intent intent = new Intent(context, VideoInfosActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("index", 9);
//                    bundle.putSerializable("id", kcBean.getId());
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
                }
            });

        } else {
            rlayout_one.setVisibility(View.INVISIBLE);
        }

        if (position + 1 < mDatas.size()) {

            rlayout_two.setVisibility(View.VISIBLE);
            CourseBean.InfoBean.CourseListBean courseListBean = (CourseBean.InfoBean.CourseListBean) mDatas.get(position + 1);
            String temp_img = courseListBean.getImg();
            ImageLoader.getInstance().displayImage(temp_img, superiror_item_iv_two, MyDisplayOptions.getOptions());
            superiror_item_title_two.setText(courseListBean.getTitle());
            superiror_item_maintalk_two.setTextColor(superiror_item_maintalk_two.getResources().getColor(R.color.main_color));
            superiror_item_maintalk_two.setText("¥ " + courseListBean.getNew_price());
            superiror_item_study_two.setVisibility(View.INVISIBLE);

            final int proPosition = position + 1;
            rlayout_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //3.传递列表数据-对象-序列化
                    CourseBean.InfoBean.CourseListBean kcBean = (CourseBean.InfoBean.CourseListBean) mDatas.get(proPosition);
                    GotoNextActUtils.getInstance().nextActivity(context,kcBean.getId(),kcBean.getType());

//                    Intent intent = new Intent(context, VideoInfosActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("index", 9);
//                    bundle.putSerializable("id", (Serializable) kcBean.getId());
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
                }
            });
        } else {
            rlayout_two.setVisibility(View.INVISIBLE);
        }
        return viewHolder.getConvertView();
    }
}
