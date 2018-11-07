package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.MFKBean;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.util.List;


/**
 * Created by wang on 2017/8/17.
 */

public class MFKAdapter extends CommonAdapter {

    private Context context;
    private List<MFKBean.FreeCourseBean> mDatas;

    public MFKAdapter(Context context, List<MFKBean.FreeCourseBean> mDatas) {
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
                R.layout.mfk_typeone, position);

        LinearLayout mf_type_one_llayout_top = viewHolder.getView(R.id.mf_type_one_llayout_top);
        ImageView mf_type_one_iv = viewHolder.getView(R.id.mf_type_one_iv);
        TextView mf_type_one_tv_title = viewHolder.getView(R.id.mf_type_one_tv_title);
        TextView mf_type_one_tv_name = viewHolder.getView(R.id.mf_type_one_tv_name);
        TextView mf_type_one_tv_duan = viewHolder.getView(R.id.mf_type_one_tv_duan);


        MFKBean.FreeCourseBean freeCourseBean = mDatas.get(position);
        ImageLoader.getInstance().displayImage(freeCourseBean.getImg(), mf_type_one_iv, MyDisplayOptions.getOptions());
        mf_type_one_tv_title.setText(freeCourseBean.getTitle());
        mf_type_one_tv_name.setText(freeCourseBean.getName());
        mf_type_one_tv_duan.setText(freeCourseBean.getDuan());

        final int pos = position;

        //设置监听事件
        mf_type_one_llayout_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递列表数据-对象-序列化
                MFKBean.FreeCourseBean freeCourseBea = mDatas.get(pos);
                GotoNextActUtils.getInstance().nextActivity(mContext,freeCourseBea.getId(),freeCourseBea.getType());
//                Intent intent;
//                if("4".equals(freeCourseBea.getType())){
//                    intent = new Intent(mContext, SumUpActivity.class);
//                }else{
//                    intent = new Intent(mContext, VideoInfosActivity.class);
//                }
//                intent.putExtra("id",freeCourseBea.getId());
//                intent.putExtra("type",freeCourseBea.getType());
//                mContext.startActivity(intent);
            }
        });

        return viewHolder.getConvertView();
    }
}
