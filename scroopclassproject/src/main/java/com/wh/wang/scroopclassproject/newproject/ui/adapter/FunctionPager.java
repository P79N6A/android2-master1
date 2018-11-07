package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chdh on 2018/3/7.
 */

public class FunctionPager extends PagerAdapter {
    private List<Integer> mImg_id;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public FunctionPager( Context context) {
        mImg_id = new ArrayList<>();
        mImg_id.add(R.drawable.gn1_icon);
        mImg_id.add(R.drawable.gn2_icon);
        mImg_id.add(R.drawable.gn3_icon);
        mImg_id.add(R.drawable.gn4_icon);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImg_id!=null?mImg_id.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view  = mLayoutInflater.inflate(R.layout.fragment_qy_function,container,false);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView function = (TextView) view.findViewById(R.id.function);
        TextView describe = (TextView) view.findViewById(R.id.describe);
        icon.setImageResource(mImg_id.get(position));
        switch (position){
            case 0:
                function.setText(R.string.function_1);
                describe.setText(R.string.describe_1);
                break;
            case 1:
                function.setText(R.string.function_2);
                describe.setText(R.string.describe_2);
                break;
            case 2:
                function.setText(R.string.function_3);
                describe.setText(R.string.describe_3);
                break;
            case 3:
                function.setText(R.string.function_4);
                describe.setText(R.string.describe_4);
                break;
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
