package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.JXKBean;
import com.wh.wang.scroopclassproject.newproject.ui.activity.MoreCourseActivity;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.util.List;

import static com.wh.wang.scroopclassproject.R.id.type_two_iv_title;
import static com.wh.wang.scroopclassproject.R.id.type_two_rlayout_title;


/**
 * Created by wang on 2017/11/21.
 */

public class JXCAdapter extends BaseAdapter {

    private List<JXKBean.EventsBean> eventList;  //报名
    private List<JXKBean.ElaborateCourseBean> jxkcList;  //课程父类
    private Context mContext;
    private LayoutInflater inflater;
    private int TypeOne = 0;//注意这个不同布局的类型起始值必须从0开始
    private int TypeTwo = 1;
    private List<JXKBean.ElaborateCourseBean.CourseDetailBean> courseDetailList;
    private GridViewAdapter gadapter;

    public JXCAdapter(Context mContext, List<JXKBean.EventsBean> eventList, List<JXKBean.ElaborateCourseBean> jxkcList) {
        this.mContext = mContext;
        this.eventList = eventList;
        this.jxkcList = jxkcList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return jxkcList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TypeOne;
        } else {
            return TypeTwo;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TypeOneViewHolder typeOneViewHolder = null;
        TypeTwoViewHolder typeTwoViewHolder = null;
        TypeThreeViewHolder typeThreeViewHolder = null;
        int Type = getItemViewType(position);

        if (convertView == null) {
            if (Type == TypeOne) {
                convertView = inflater.inflate(R.layout.jxk_typeone, parent, false);
                typeOneViewHolder = new TypeOneViewHolder();
                typeOneViewHolder.type_one_grid = (GridView) convertView.findViewById(R.id.type_one_grid);

                convertView.setTag(typeOneViewHolder);

            } else if (Type == TypeTwo) {

                convertView = inflater.inflate(R.layout.jxk_typetwo, parent, false);
                typeTwoViewHolder = new TypeTwoViewHolder();

                typeTwoViewHolder.type_two_rlayout_title = (RelativeLayout)
                        convertView.findViewById(type_two_rlayout_title);

                typeTwoViewHolder.type_two_iv_title = (TextView)
                        convertView.findViewById(type_two_iv_title);

                //------------------------
                typeTwoViewHolder.type_two_rlayout_one = (RelativeLayout)
                        convertView.findViewById(R.id.type_two_rlayout_one);

                typeTwoViewHolder.type_two_iv_one = (ImageView)
                        convertView.findViewById(R.id.type_two_iv_one);

                typeTwoViewHolder.type_two_title_one = (TextView)
                        convertView.findViewById(R.id.type_two_title_one);

                typeTwoViewHolder.type_two_maintalk_one = (TextView)
                        convertView.findViewById(R.id.type_two_maintalk_one);

                //------------------------
                typeTwoViewHolder.type_two_rlayout_two = (RelativeLayout)
                        convertView.findViewById(R.id.type_two_rlayout_two);

                typeTwoViewHolder.type_two_iv_two = (ImageView)
                        convertView.findViewById(R.id.type_two_iv_two);

                typeTwoViewHolder.type_two_title_two = (TextView)
                        convertView.findViewById(R.id.type_two_title_two);

                typeTwoViewHolder.type_two_maintalk_two = (TextView)
                        convertView.findViewById(R.id.type_two_maintalk_two);

                //------------------------
                typeTwoViewHolder.type_two_rlayout_three = (RelativeLayout)
                        convertView.findViewById(R.id.type_two_rlayout_three);

                typeTwoViewHolder.type_two_iv_three = (ImageView)
                        convertView.findViewById(R.id.type_two_iv_three);

                typeTwoViewHolder.type_two_title_three = (TextView)
                        convertView.findViewById(R.id.type_two_title_three);

                typeTwoViewHolder.type_two_maintalk_three = (TextView)
                        convertView.findViewById(R.id.type_two_maintalk_three);

                //------------------------
                typeTwoViewHolder.type_two_rlayout_four = (RelativeLayout)
                        convertView.findViewById(R.id.type_two_rlayout_four);

                typeTwoViewHolder.type_two_iv_four = (ImageView)
                        convertView.findViewById(R.id.type_two_iv_four);

                typeTwoViewHolder.type_two_title_four = (TextView)
                        convertView.findViewById(R.id.type_two_title_four);

                typeTwoViewHolder.type_two_maintalk_four = (TextView)
                        convertView.findViewById(R.id.type_two_maintalk_four);

                convertView.setTag(typeTwoViewHolder);

            }
        } else {

            if (Type == TypeOne) {
                typeOneViewHolder = (TypeOneViewHolder) convertView.getTag();
            } else if (Type == TypeTwo) {
                typeTwoViewHolder = (TypeTwoViewHolder) convertView.getTag();
            }
        }


        if (Type == TypeOne) {
            int size = eventList.size();
            int length = 166;
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            //getWindowManager().getDefaultDisplay().getMetrics(dm);
            float density = dm.density;
            int gridviewWidth = (int) (size * (length + 4) * density);
            int itemWidth = (int) (length * density);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
            typeOneViewHolder.type_one_grid.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键

            typeOneViewHolder.type_one_grid.setColumnWidth(itemWidth); // 设置列表项宽
            typeOneViewHolder.type_one_grid.setHorizontalSpacing(10); // 设置列表项水平间距
            typeOneViewHolder.type_one_grid.setStretchMode(GridView.NO_STRETCH);
            typeOneViewHolder.type_one_grid.setNumColumns(size); // 设置列数量=列表集合数

            gadapter = new GridViewAdapter(mContext,
                    eventList);
            typeOneViewHolder.type_one_grid.setAdapter(gadapter);


        } else if (Type == TypeTwo) {
            final int pos = position - 1;
            JXKBean.ElaborateCourseBean elaborateCourseBean = jxkcList.get(pos);
            typeTwoViewHolder.type_two_iv_title.setText(elaborateCourseBean.getName());
            ImageLoader.getInstance().displayImage(elaborateCourseBean.getCourse_detail().get(0).getImg(), typeTwoViewHolder.type_two_iv_one, MyDisplayOptions.getOptions());
            typeTwoViewHolder.type_two_title_one.setText(elaborateCourseBean.getCourse_detail().get(0).getTitle());
            String tempStrprice = elaborateCourseBean.getCourse_detail().get(0).getNew_price();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                typeTwoViewHolder.type_two_maintalk_one.setText("免费");
            } else {
                typeTwoViewHolder.type_two_maintalk_one.setText("¥ " + tempprice);
            }

            typeTwoViewHolder.type_two_rlayout_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JXKBean.ElaborateCourseBean elaborateCourseBean = jxkcList.get(pos);
                    Intent intent = new Intent(mContext, MoreCourseActivity.class);
                    Bundle bundle = new Bundle();
                    int id = Integer.parseInt(elaborateCourseBean.getId());
                    bundle.putString("flag","0");
                    bundle.putInt("jxid", id);
                    bundle.putString("jxname", elaborateCourseBean.getName());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            typeTwoViewHolder.type_two_rlayout_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JXKBean.ElaborateCourseBean.CourseDetailBean jxDetailBean = jxkcList.get(pos).getCourse_detail().get(0);
                    GotoNextActUtils.getInstance().nextActivity(mContext,jxDetailBean.getId(),jxDetailBean.getType());
//                    Intent intent;
//                    if("4".equals(jxDetailBean.getType())){
//                        intent = new Intent(mContext, SumUpActivity.class);
//                    }else{
//                        intent = new Intent(mContext, VideoInfosActivity.class);
//                    }
//                    intent.putExtra("id",jxDetailBean.getId());
//                    intent.putExtra("type",jxDetailBean.getType());
//                    mContext.startActivity(intent);
                }
            });

            ImageLoader.getInstance().displayImage(elaborateCourseBean.getCourse_detail().get(1).getImg(), typeTwoViewHolder.type_two_iv_two, MyDisplayOptions.getOptions());
            typeTwoViewHolder.type_two_title_two.setText(elaborateCourseBean.getCourse_detail().get(1).getTitle());

            String tempStrprice2 = elaborateCourseBean.getCourse_detail().get(1).getNew_price();
            double tempprice2 = Double.parseDouble(tempStrprice2);
            if (tempprice2 == 0.0) {
                typeTwoViewHolder.type_two_maintalk_two.setText("免费");
            } else {
                typeTwoViewHolder.type_two_maintalk_two.setText("¥ " + tempprice2);
            }

            typeTwoViewHolder.type_two_rlayout_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JXKBean.ElaborateCourseBean.CourseDetailBean jxDetailBean = jxkcList.get(pos).getCourse_detail().get(1);
                    GotoNextActUtils.getInstance().nextActivity(mContext,jxDetailBean.getId(),jxDetailBean.getType());
//                    Intent intent;
//                    if("4".equals(jxDetailBean.getType())){
//                        intent = new Intent(mContext, SumUpActivity.class);
//                    }else{
//                        intent = new Intent(mContext, VideoInfosActivity.class);
//                    }
//                    intent.putExtra("id",jxDetailBean.getId());
//                    intent.putExtra("type",jxDetailBean.getType());
//                    mContext.startActivity(intent);
                }
            });

            ImageLoader.getInstance().displayImage(elaborateCourseBean.getCourse_detail().get(2).getImg(), typeTwoViewHolder.type_two_iv_three, MyDisplayOptions.getOptions());
            typeTwoViewHolder.type_two_title_three.setText(elaborateCourseBean.getCourse_detail().get(2).getTitle());

            String tempStrprice3 = elaborateCourseBean.getCourse_detail().get(2).getNew_price();
            double tempprice3 = Double.parseDouble(tempStrprice3);
            if (tempprice3 == 0.0) {
                typeTwoViewHolder.type_two_maintalk_three.setText("免费");
            } else {
                typeTwoViewHolder.type_two_maintalk_three.setText("¥ " + tempprice3);
            }

            typeTwoViewHolder.type_two_rlayout_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JXKBean.ElaborateCourseBean.CourseDetailBean jxDetailBean = jxkcList.get(pos).getCourse_detail().get(2);
                    GotoNextActUtils.getInstance().nextActivity(mContext,jxDetailBean.getId(),jxDetailBean.getType());
//                    Intent intent;
//                    if("4".equals(jxDetailBean.getType())){
//                        intent = new Intent(mContext, SumUpActivity.class);
//                    }else{
//                        intent = new Intent(mContext, VideoInfosActivity.class);
//                    }
//                    intent.putExtra("id",jxDetailBean.getId());
//                    intent.putExtra("type",jxDetailBean.getType());
//                    mContext.startActivity(intent);
                }
            });

            ImageLoader.getInstance().displayImage(elaborateCourseBean.getCourse_detail().get(3).getImg(), typeTwoViewHolder.type_two_iv_four, MyDisplayOptions.getOptions());
            typeTwoViewHolder.type_two_title_four.setText(elaborateCourseBean.getCourse_detail().get(3).getTitle());
            String tempStrprice4 = elaborateCourseBean.getCourse_detail().get(3).getNew_price();
            double tempprice4 = Double.parseDouble(tempStrprice4);
            if (tempprice4 == 0.0) {
                typeTwoViewHolder.type_two_maintalk_four.setText("免费");
            } else {
                typeTwoViewHolder.type_two_maintalk_four.setText("¥ " + tempprice4);
            }
            typeTwoViewHolder.type_two_rlayout_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JXKBean.ElaborateCourseBean.CourseDetailBean jxDetailBean = jxkcList.get(pos).getCourse_detail().get(3);
                    GotoNextActUtils.getInstance().nextActivity(mContext,jxDetailBean.getId(),jxDetailBean.getType());
//                    Intent intent;
//                    if("4".equals(jxDetailBean.getType())){
//                        intent = new Intent(mContext, SumUpActivity.class);
//                    }else{
//                        intent = new Intent(mContext, VideoInfosActivity.class);
//                    }
//                    intent.putExtra("id",jxDetailBean.getId());
//                    intent.putExtra("type",jxDetailBean.getType());
//                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }


    /**
     * 第一个item
     */
    private class TypeOneViewHolder {
        private GridView type_one_grid;
    }


    /**
     * 第二个item
     */
    private class TypeTwoViewHolder {
        private RelativeLayout type_two_rlayout_title;
        private TextView type_two_iv_title;

        private RelativeLayout type_two_rlayout_one;
        private ImageView type_two_iv_one;
        private TextView type_two_title_one;
        private TextView type_two_maintalk_one;

        private RelativeLayout type_two_rlayout_two;
        private ImageView type_two_iv_two;
        private TextView type_two_title_two;
        private TextView type_two_maintalk_two;

        private RelativeLayout type_two_rlayout_three;
        private ImageView type_two_iv_three;
        private TextView type_two_title_three;
        private TextView type_two_maintalk_three;

        private RelativeLayout type_two_rlayout_four;
        private ImageView type_two_iv_four;
        private TextView type_two_title_four;
        private TextView type_two_maintalk_four;
    }

    /**
     * 第三个item
     */
    private class TypeThreeViewHolder {
        private RelativeLayout type_three;
    }
}
