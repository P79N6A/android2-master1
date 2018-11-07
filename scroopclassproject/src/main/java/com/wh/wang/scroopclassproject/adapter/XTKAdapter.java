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
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.JXKBean;
import com.wh.wang.scroopclassproject.bean.XTKBean;
import com.wh.wang.scroopclassproject.newproject.ui.activity.MoreCourseActivity;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.util.List;


/**
 * Created by wang on 2017/11/21.
 */

public class XTKAdapter extends BaseAdapter {

    private List<JXKBean.EventsBean> eventList;  //报名
    private List<XTKBean.SystemCourseBean> xtkcList;  //课程父类
    private Context mContext;
    private LayoutInflater inflater;
    private int TypeOne = 0;//注意这个不同布局的类型起始值必须从0开始
    private int TypeTwo = 1;
    private int TypeThree = 2;
    private List<XTKBean.SystemCourseBean.CourseDetailBean> courseDetailList;
    private List<XTKBean.WuDaLiypeBean> wudaliList;

    public XTKAdapter(Context mContext, List<JXKBean.EventsBean> eventList, List<XTKBean.SystemCourseBean> xtkcList, List<XTKBean.WuDaLiypeBean> wudaliList) {
        this.mContext = mContext;
        this.eventList = eventList;
        this.xtkcList = xtkcList;
        this.wudaliList = wudaliList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return xtkcList.size() + 2;
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
        } else if (position == 1) {
            return TypeTwo;
        } else {
            return TypeThree;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TypeOneViewHolder typeOneViewHolder = null;
        TypeTwoViewHolder typeTwoViewHolder = null;
        TypeThreeViewHolder typeThreeViewHolder = null;
        int Type = getItemViewType(position);

        if (convertView == null) {
            if (Type == TypeOne) {
                convertView = inflater.inflate(R.layout.xtk_typeone, parent, false);
                typeOneViewHolder = new TypeOneViewHolder();
                typeOneViewHolder.xt_type_one_rlayout_flag_one = (RelativeLayout) convertView.findViewById(R.id.xt_type_one_rlayout_flag_one);
                typeOneViewHolder.xt_type_one_rlayout_flag_two = (RelativeLayout) convertView.findViewById(R.id.xt_type_one_rlayout_flag_two);
                typeOneViewHolder.xt_type_one_rlayout_flag_three = (RelativeLayout) convertView.findViewById(R.id.xt_type_one_rlayout_flag_three);
                typeOneViewHolder.xt_type_one_rlayout_flag_four = (RelativeLayout) convertView.findViewById(R.id.xt_type_one_rlayout_flag_four);
                typeOneViewHolder.xt_type_one_rlayout_flag_five = (RelativeLayout) convertView.findViewById(R.id.xt_type_one_rlayout_flag_five);

                convertView.setTag(typeOneViewHolder);

            } else if (Type == TypeTwo) {
                convertView = inflater.inflate(R.layout.xtk_typetwo, parent, false);
                typeTwoViewHolder = new TypeTwoViewHolder();
                typeTwoViewHolder.xt_type_two_grid = (GridView)
                        convertView.findViewById(R.id.xt_type_two_grid);

                convertView.setTag(typeTwoViewHolder);
            } else if (Type == TypeThree) {

                convertView = inflater.inflate(R.layout.xtk_typethree, parent, false);
                typeThreeViewHolder = new TypeThreeViewHolder();

                typeThreeViewHolder.xt_type_three_rlayout_title = (RelativeLayout)
                        convertView.findViewById(R.id.xt_type_three_rlayout_title);

                typeThreeViewHolder.xt_type_three_iv_title = (TextView)
                        convertView.findViewById(R.id.xt_type_three_iv_title);

                //------------------------
                typeThreeViewHolder.xt_type_three_rlayout_one = (RelativeLayout)
                        convertView.findViewById(R.id.xt_type_three_rlayout_one);

                typeThreeViewHolder.xt_type_three_iv_one = (ImageView)
                        convertView.findViewById(R.id.xt_type_three_iv_one);

                typeThreeViewHolder.xt_type_three_title_one = (TextView)
                        convertView.findViewById(R.id.xt_type_three_title_one);

                typeThreeViewHolder.xt_type_three_maintalk_one = (TextView)
                        convertView.findViewById(R.id.xt_type_three_maintalk_one);

                //------------------------
                typeThreeViewHolder.xt_type_three_rlayout_two = (RelativeLayout)
                        convertView.findViewById(R.id.xt_type_three_rlayout_two);

                typeThreeViewHolder.xt_type_three_iv_two = (ImageView)
                        convertView.findViewById(R.id.xt_type_three_iv_two);

                typeThreeViewHolder.xt_type_three_title_two = (TextView)
                        convertView.findViewById(R.id.xt_type_three_title_two);

                typeThreeViewHolder.xt_type_three_maintalk_two = (TextView)
                        convertView.findViewById(R.id.xt_type_three_maintalk_two);

                //------------------------
                typeThreeViewHolder.xt_type_three_rlayout_three = (RelativeLayout)
                        convertView.findViewById(R.id.xt_type_three_rlayout_three);

                typeThreeViewHolder.xt_type_three_iv_three = (ImageView)
                        convertView.findViewById(R.id.xt_type_three_iv_three);

                typeThreeViewHolder.xt_type_three_title_three = (TextView)
                        convertView.findViewById(R.id.xt_type_three_title_three);

                typeThreeViewHolder.xt_type_three_maintalk_three = (TextView)
                        convertView.findViewById(R.id.xt_type_three_maintalk_three);

                //------------------------
                typeThreeViewHolder.xt_type_three_rlayout_four = (RelativeLayout)
                        convertView.findViewById(R.id.xt_type_three_rlayout_four);

                typeThreeViewHolder.xt_type_three_iv_four = (ImageView)
                        convertView.findViewById(R.id.xt_type_three_iv_four);

                typeThreeViewHolder.xt_type_three_title_four = (TextView)
                        convertView.findViewById(R.id.xt_type_three_title_four);

                typeThreeViewHolder.xt_type_three_maintalk_four = (TextView)
                        convertView.findViewById(R.id.xt_type_three_maintalk_four);

                convertView.setTag(typeThreeViewHolder);
            }
        } else {

            if (Type == TypeOne) {
                typeOneViewHolder = (TypeOneViewHolder) convertView.getTag();
            } else if (Type == TypeTwo) {
                typeTwoViewHolder = (TypeTwoViewHolder) convertView.getTag();
            } else if (Type == TypeThree) {
                typeThreeViewHolder = (TypeThreeViewHolder) convertView.getTag();
            }
        }


        if (Type == TypeOne) {

            final int wPos = position;
            typeOneViewHolder.xt_type_one_rlayout_flag_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.WuDaLiypeBean wudaliBean = wudaliList.get(0);
                    Intent intent = new Intent(mContext, MoreCourseActivity.class);
                    Bundle bundle = new Bundle();
                    MobclickAgent.onEvent(mContext, "yingyunli");
                    int id = Integer.parseInt(wudaliBean.getId());
                    bundle.putString("flag", "0");
                    bundle.putInt("jxid", id);
                    bundle.putString("jxname", wudaliBean.getName());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            typeOneViewHolder.xt_type_one_rlayout_flag_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.WuDaLiypeBean wudaliBean = wudaliList.get(1);
                    Intent intent = new Intent(mContext, MoreCourseActivity.class);
                    Bundle bundle = new Bundle();
                    MobclickAgent.onEvent(mContext, "rencaili");
                    int id = Integer.parseInt(wudaliBean.getId());
                    bundle.putString("flag", "0");
                    bundle.putInt("jxid", id);
                    bundle.putString("jxname", wudaliBean.getName());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            typeOneViewHolder.xt_type_one_rlayout_flag_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.WuDaLiypeBean wudaliBean = wudaliList.get(2);
                    Intent intent = new Intent(mContext, MoreCourseActivity.class);
                    Bundle bundle = new Bundle();
                    MobclickAgent.onEvent(mContext, "zhichili_new");
                    int id = Integer.parseInt(wudaliBean.getId());
                    bundle.putString("flag", "0");
                    bundle.putInt("jxid", id);
                    bundle.putString("jxname", wudaliBean.getName());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            typeOneViewHolder.xt_type_one_rlayout_flag_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.WuDaLiypeBean wudaliBean = wudaliList.get(3);
                    Intent intent = new Intent(mContext, MoreCourseActivity.class);
                    Bundle bundle = new Bundle();
                    MobclickAgent.onEvent(mContext, "zhanlueli");
                    int id = Integer.parseInt(wudaliBean.getId());
                    bundle.putString("flag", "0");
                    bundle.putInt("jxid", id);
                    bundle.putString("jxname", wudaliBean.getName());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            typeOneViewHolder.xt_type_one_rlayout_flag_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.WuDaLiypeBean wudaliBean = wudaliList.get(4);
                    Intent intent = new Intent(mContext, MoreCourseActivity.class);
                    Bundle bundle = new Bundle();
                    MobclickAgent.onEvent(mContext, "yingxiaoli");
                    int id = Integer.parseInt(wudaliBean.getId());
                    bundle.putString("flag", "0");
                    bundle.putInt("jxid", id);
                    bundle.putString("jxname", wudaliBean.getName());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });


        } else if (Type == TypeTwo) {
            int size = eventList.size();
            int length = 166;
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            //getWindowManager().getDefaultDisplay().getMetrics(dm);
            float density = dm.density;
            int gridviewWidth = (int) (size * (length + 4) * density);
            int itemWidth = (int) (length * density);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
            typeTwoViewHolder.xt_type_two_grid.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键

            typeTwoViewHolder.xt_type_two_grid.setColumnWidth(itemWidth); // 设置列表项宽
            typeTwoViewHolder.xt_type_two_grid.setHorizontalSpacing(10); // 设置列表项水平间距
            typeTwoViewHolder.xt_type_two_grid.setStretchMode(GridView.NO_STRETCH);
            typeTwoViewHolder.xt_type_two_grid.setNumColumns(size); // 设置列数量=列表集合数

            GridViewAdapter gadapter = new GridViewAdapter(mContext, eventList);
            typeTwoViewHolder.xt_type_two_grid.setAdapter(gadapter);

        } else if (Type == TypeThree) {
            final int pos = position - 2;
            XTKBean.SystemCourseBean systemCourseBean = xtkcList.get(pos);
            typeThreeViewHolder.xt_type_three_iv_title.setText(systemCourseBean.getName());
            ImageLoader.getInstance().displayImage(systemCourseBean.getCourse_detail().get(0).getImg(), typeThreeViewHolder.xt_type_three_iv_one, MyDisplayOptions.getOptions());
            typeThreeViewHolder.xt_type_three_title_one.setText(systemCourseBean.getCourse_detail().get(0).getTitle());

            String tempStrprice = systemCourseBean.getCourse_detail().get(0).getNew_price();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                typeThreeViewHolder.xt_type_three_maintalk_one.setText("免费");
            } else {
                typeThreeViewHolder.xt_type_three_maintalk_one.setText("¥ " + tempprice);
            }

            typeThreeViewHolder.xt_type_three_rlayout_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.SystemCourseBean.CourseDetailBean xtDetailBean = xtkcList.get(pos).getCourse_detail().get(0);
                    GotoNextActUtils.getInstance().nextActivity(mContext,xtDetailBean.getId(),xtDetailBean.getType());
                }
            });

            ImageLoader.getInstance().displayImage(systemCourseBean.getCourse_detail().get(1).getImg(), typeThreeViewHolder.xt_type_three_iv_two, MyDisplayOptions.getOptions());
            typeThreeViewHolder.xt_type_three_title_two.setText(systemCourseBean.getCourse_detail().get(1).getTitle());
            String tempStrprice2 = systemCourseBean.getCourse_detail().get(1).getNew_price();
            double tempprice2 = Double.parseDouble(tempStrprice2);
            if (tempprice2 == 0.0) {
                typeThreeViewHolder.xt_type_three_maintalk_two.setText("免费");
            } else {
                typeThreeViewHolder.xt_type_three_maintalk_two.setText("¥ " + tempprice2);
            }

            typeThreeViewHolder.xt_type_three_rlayout_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.SystemCourseBean.CourseDetailBean xtDetailBean = xtkcList.get(pos).getCourse_detail().get(1);

                    GotoNextActUtils.getInstance().nextActivity(mContext,xtDetailBean.getId(),xtDetailBean.getType());
                }
            });

            ImageLoader.getInstance().displayImage(systemCourseBean.getCourse_detail().get(2).getImg(), typeThreeViewHolder.xt_type_three_iv_three, MyDisplayOptions.getOptions());
            typeThreeViewHolder.xt_type_three_title_three.setText(systemCourseBean.getCourse_detail().get(2).getTitle());
            String tempStrprice3 = systemCourseBean.getCourse_detail().get(2).getNew_price();
            double tempprice3 = Double.parseDouble(tempStrprice3);
            if (tempprice3 == 0.0) {
                typeThreeViewHolder.xt_type_three_maintalk_three.setText("免费");
            } else {
                typeThreeViewHolder.xt_type_three_maintalk_three.setText("¥ " + tempprice3);
            }

            typeThreeViewHolder.xt_type_three_rlayout_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.SystemCourseBean.CourseDetailBean xtDetailBean = xtkcList.get(pos).getCourse_detail().get(2);
                    GotoNextActUtils.getInstance().nextActivity(mContext,xtDetailBean.getId(),xtDetailBean.getType());
                }
            });
            
            ImageLoader.getInstance().displayImage(systemCourseBean.getCourse_detail().get(3).getImg(), typeThreeViewHolder.xt_type_three_iv_four, MyDisplayOptions.getOptions());
            typeThreeViewHolder.xt_type_three_title_four.setText(systemCourseBean.getCourse_detail().get(3).getTitle());
            String tempStrprice4 = systemCourseBean.getCourse_detail().get(3).getNew_price();
            double tempprice4 = Double.parseDouble(tempStrprice4);
            if (tempprice4 == 0.0) {
                typeThreeViewHolder.xt_type_three_maintalk_four.setText("免费");
            } else {
                typeThreeViewHolder.xt_type_three_maintalk_four.setText("¥ " + tempprice4);
            }

            typeThreeViewHolder.xt_type_three_rlayout_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.SystemCourseBean.CourseDetailBean xtDetailBean = xtkcList.get(pos).getCourse_detail().get(3);
                    GotoNextActUtils.getInstance().nextActivity(mContext,xtDetailBean.getId(),xtDetailBean.getType());
                }
            });

            typeThreeViewHolder.xt_type_three_rlayout_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XTKBean.SystemCourseBean systemCourseBean = xtkcList.get(pos);
                    Intent intent = new Intent(mContext, MoreCourseActivity.class);
                    Bundle bundle = new Bundle();
                    int id = Integer.parseInt(systemCourseBean.getId());
                    bundle.putString("flag","0");
                    bundle.putInt("jxid", id);
                    bundle.putString("jxname", systemCourseBean.getName());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }


    /**
     * 第一个item
     */
    private class TypeOneViewHolder {
        private RelativeLayout xt_type_one_rlayout_flag_one;
        private RelativeLayout xt_type_one_rlayout_flag_two;
        private RelativeLayout xt_type_one_rlayout_flag_three;
        private RelativeLayout xt_type_one_rlayout_flag_four;
        private RelativeLayout xt_type_one_rlayout_flag_five;
    }

    /**
     * 第一个item
     */
    private class TypeTwoViewHolder {
        private GridView xt_type_two_grid;
    }

    /**
     * 第三个item
     */
    private class TypeThreeViewHolder {
        private RelativeLayout xt_type_three_rlayout_title;
        private TextView xt_type_three_iv_title;

        private RelativeLayout xt_type_three_rlayout_one;
        private ImageView xt_type_three_iv_one;
        private TextView xt_type_three_title_one;
        private TextView xt_type_three_maintalk_one;

        private RelativeLayout xt_type_three_rlayout_two;
        private ImageView xt_type_three_iv_two;
        private TextView xt_type_three_title_two;
        private TextView xt_type_three_maintalk_two;

        private RelativeLayout xt_type_three_rlayout_three;
        private ImageView xt_type_three_iv_three;
        private TextView xt_type_three_title_three;
        private TextView xt_type_three_maintalk_three;

        private RelativeLayout xt_type_three_rlayout_four;
        private ImageView xt_type_three_iv_four;
        private TextView xt_type_three_title_four;
        private TextView xt_type_three_maintalk_four;
    }
}
