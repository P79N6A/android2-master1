package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.CourseBean;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.util.List;

/**
 * Created by wang on 2017/11/22.
 */

public class CourseListAdapter extends BaseAdapter {

    private List<CourseBean.InfoBean.CourseListBean> courseList;  //视频集合
    private List<CourseBean.InfoBean.ScrollBean> scrollList;  //广告集合
    private Context mContext;
    private LayoutInflater inflater;
    private int TypeOne = 0;//注意这个不同布局的类型起始值必须从0开始
    private int TypeTwo = 1;
    private int TypeThree = 2;
    private String name;
    private int flagg;
    private final int[] imageColor = {R.drawable.coure_point_one, R.drawable.coure_point_two, R.drawable.coure_point_three,
            R.drawable.coure_point_four, R.drawable.coure_point_five};

    public CourseListAdapter(Context mContext, List<CourseBean.InfoBean.CourseListBean> courseList,
                             List<CourseBean.InfoBean.ScrollBean> scrollList, String name, int flagg) {
        this.mContext = mContext;
        this.courseList = courseList;
        this.scrollList = scrollList;
        this.name = name;
        this.flagg = flagg;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return courseList.size() + 2;
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

        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;
        ViewHolderThree viewHolderThree = null;
        ViewHolderFour viewHolderfour = null;
        int Type = getItemViewType(position);

        if (convertView == null) {
            if (Type == TypeOne) {
                convertView = inflater.inflate(R.layout.courselist_item_one, parent, false);
                viewHolderOne = new ViewHolderOne();
                viewHolderOne.courselist_item_one_rlayout_one = (RelativeLayout) convertView.findViewById(R.id.courselist_item_one_rlayout_one);
                viewHolderOne.courselist_item_one_iv_one = (ImageView) convertView.findViewById(R.id.courselist_item_one_iv_one);
                viewHolderOne.courselist_item_one_rlayout_two = (RelativeLayout) convertView.findViewById(R.id.courselist_item_one_rlayout_two);
                viewHolderOne.courselist_item_one_iv_two = (ImageView) convertView.findViewById(R.id.courselist_item_one_iv_two);

                convertView.setTag(viewHolderOne);

            } else if (Type == TypeTwo) {

                convertView = inflater.inflate(R.layout.courselist_item_two, parent, false);
                viewHolderTwo = new ViewHolderTwo();
                viewHolderTwo.courselist_item_two_tv = (TextView) convertView.findViewById(R.id.courselist_item_two_tv);
                convertView.setTag(viewHolderTwo);

            } else if (Type == TypeThree) {

                if (flagg == 0) {

                    convertView = inflater.inflate(R.layout.courselist_item_three, parent, false);
                    viewHolderThree = new ViewHolderThree();
                    viewHolderThree.courselist_item_three_rlayout = (RelativeLayout) convertView.findViewById(R.id.courselist_item_three_rlayout);
                    viewHolderThree.courselist_item_two_rlayout = (RelativeLayout) convertView.findViewById(R.id.courselist_item_two_rlayout);
                    viewHolderThree.courselist_item_two_iv = (ImageView) convertView.findViewById(R.id.courselist_item_two_iv);
                    viewHolderThree.courselist_item_two_tv = (TextView) convertView.findViewById(R.id.courselist_item_two_tv);
                    convertView.setTag(viewHolderThree);
                } else {
                    convertView = inflater.inflate(R.layout.courselist_item_four, parent, false);
                    viewHolderfour = new ViewHolderFour();
                    viewHolderfour.courselist_item_four_rlayout = (RelativeLayout) convertView.findViewById(R.id.courselist_item_four_rlayout);
                    viewHolderfour.courselist_item_four_iv = (ImageView) convertView.findViewById(R.id.courselist_item_four_iv);
                    viewHolderfour.courselist_item_four_tv_title = (TextView) convertView.findViewById(R.id.courselist_item_four_tv_title);
                    viewHolderfour.courselist_item_four_tv_price = (TextView) convertView.findViewById(R.id.courselist_item_four_tv_price);
                    convertView.setTag(viewHolderfour);
                }
            }
        } else {

            if (Type == TypeOne) {
                viewHolderOne = (ViewHolderOne) convertView.getTag();
            } else if (Type == TypeTwo) {
                viewHolderTwo = (ViewHolderTwo) convertView.getTag();
            } else if (Type == TypeThree) {
                if (flagg == 0) {
                    viewHolderThree = (ViewHolderThree) convertView.getTag();
                } else {
                    viewHolderfour = (ViewHolderFour) convertView.getTag();
                }
            }
        }

        if (Type == TypeOne) {

            if (scrollList.size() == 0) {
                viewHolderOne.courselist_item_one_rlayout_one.setVisibility(View.GONE);
                viewHolderOne.courselist_item_one_rlayout_two.setVisibility(View.GONE);
            } else if (scrollList.size() == 1) {
                viewHolderOne.courselist_item_one_rlayout_one.setVisibility(View.VISIBLE);
                viewHolderOne.courselist_item_one_rlayout_two.setVisibility(View.GONE);
                String imgUrl = scrollList.get(0).getImg();
                ImageLoader.getInstance().displayImage(imgUrl, viewHolderOne.courselist_item_one_iv_one, MyDisplayOptions.getOptions());

                //final int cPos = position;
                viewHolderOne.courselist_item_one_rlayout_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseBean.InfoBean.ScrollBean scBean = scrollList.get(0);
                        GotoNextActUtils.getInstance().nextActivity(mContext,scBean.getCourseId(),scBean.getType());
//                        Intent intent;
//                        if("4".equals(scBean.getType())){
//                            intent = new Intent(mContext, SumUpActivity.class);
//                        }else{
//                            intent = new Intent(mContext, VideoInfosActivity.class);
//                        }
//                        intent.putExtra("id",scBean.getCourseId());
//                        intent.putExtra("type",scBean.getType());
//                        mContext.startActivity(intent);

//                        Bundle bundle = new Bundle();
//                        bundle.putInt("index", 9);
//                        bundle.putSerializable("id",scBean.getCourseId());
//                        intent.putExtras(bundle);
                    }
                });
            } else if (scrollList.size() > 1) {
                viewHolderOne.courselist_item_one_rlayout_one.setVisibility(View.VISIBLE);
                viewHolderOne.courselist_item_one_rlayout_two.setVisibility(View.VISIBLE);
                String imgUrlOne = scrollList.get(0).getImg();
                ImageLoader.getInstance().displayImage(imgUrlOne, viewHolderOne.courselist_item_one_iv_one, MyDisplayOptions.getOptions());
                viewHolderOne.courselist_item_one_rlayout_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseBean.InfoBean.ScrollBean scBean = scrollList.get(0);
                        GotoNextActUtils.getInstance().nextActivity(mContext,scBean.getCourseId(),scBean.getType());
//                        Intent intent;
//                        if("4".equals(scBean.getType())){
//                            intent = new Intent(mContext, SumUpActivity.class);
//                        }else{
//                            intent = new Intent(mContext, VideoInfosActivity.class);
//                        }
//                        intent.putExtra("id",scBean.getCourseId());
//                        intent.putExtra("type",scBean.getType());
//                        mContext.startActivity(intent);
                    }
                });
                String imgUrlTwo = scrollList.get(1).getImg();
                ImageLoader.getInstance().displayImage(imgUrlTwo, viewHolderOne.courselist_item_one_iv_two, MyDisplayOptions.getOptions());
                viewHolderOne.courselist_item_one_rlayout_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseBean.InfoBean.ScrollBean scBean = scrollList.get(0);
                        GotoNextActUtils.getInstance().nextActivity(mContext,scBean.getCourseId(),scBean.getType());
//                        Intent intent;
//                        if("4".equals(scBean.getType())){
//                            intent = new Intent(mContext, SumUpActivity.class);
//                        }else{
//                            intent = new Intent(mContext, VideoInfosActivity.class);
//                        }
//                        intent.putExtra("id",scBean.getCourseId());
//                        intent.putExtra("type",scBean.getType());
//                        mContext.startActivity(intent);
                    }
                });
            }

        } else if (Type == TypeTwo) {
            viewHolderTwo.courselist_item_two_tv.setText(name);

        } else if (Type == TypeThree) {
            if (flagg == 0) {
                int pos_one = position - 2;
                //TODO WHWH
                int realPosition = pos_one % imageColor.length;
                // 设置对应页面的文本信息
                viewHolderThree.courselist_item_two_iv.setBackgroundResource(imageColor[realPosition]);
                //viewHolderThree.courselist_item_two_rlayout.setBackgroundColor(Color.parseColor(imageColor[realPosition]));
                viewHolderThree.courselist_item_two_tv.setText(courseList.get(pos_one).getTitle());
                final int tPos = pos_one;
                viewHolderThree.courselist_item_three_rlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseBean.InfoBean.CourseListBean kcBean = courseList.get(tPos);
                        GotoNextActUtils.getInstance().nextActivity(mContext,kcBean.getId(),kcBean.getType());
//                        Intent intent;
//                        if("4".equals(kcBean.getType())){
//                            intent = new Intent(mContext, SumUpActivity.class);
//                        }else{
//                            intent = new Intent(mContext, VideoInfosActivity.class);
//                        }
//                        intent.putExtra("id",kcBean.getId());
//                        intent.putExtra("type",kcBean.getType());
//                        mContext.startActivity(intent);
                    }
                });


            } else {
                int pos_two = position - 2;
                String imgUrl = courseList.get(pos_two).getImg();
                ImageLoader.getInstance().displayImage(imgUrl, viewHolderfour.courselist_item_four_iv, MyDisplayOptions.getOptions());
                viewHolderfour.courselist_item_four_tv_title.setText(courseList.get(pos_two).getTitle());

                String tempStrprice = courseList.get(pos_two).getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    viewHolderfour.courselist_item_four_tv_price.setText("免费");
                } else {
                    viewHolderfour.courselist_item_four_tv_price.setText("¥ " + tempprice);
                }

                final int wPos = pos_two;
                viewHolderfour.courselist_item_four_rlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseBean.InfoBean.CourseListBean kcBean = courseList.get(wPos);
                        GotoNextActUtils.getInstance().nextActivity(mContext,kcBean.getId(),kcBean.getType());
//                        Intent intent;
//                        if("4".equals(kcBean.getType())){
//                            intent = new Intent(mContext, SumUpActivity.class);
//                        }else{
//                            intent = new Intent(mContext, VideoInfosActivity.class);
//                        }
//                        intent.putExtra("id",kcBean.getId());
//                        intent.putExtra("type",kcBean.getType());
//                        mContext.startActivity(intent);
                    }
                });
            }
        }

        return convertView;
    }


    /**
     * 第一个item
     */
    private class ViewHolderOne {
        private RelativeLayout courselist_item_one_rlayout_one;
        private ImageView courselist_item_one_iv_one;
        private RelativeLayout courselist_item_one_rlayout_two;
        private ImageView courselist_item_one_iv_two;
    }

    /**
     * 第二个item
     */
    private class ViewHolderTwo {

        private TextView courselist_item_two_tv;
    }

    /**
     * 第三个item
     */
    private class ViewHolderThree {
        private RelativeLayout courselist_item_three_rlayout;
        private RelativeLayout courselist_item_two_rlayout;
        private ImageView courselist_item_two_iv;
        private TextView courselist_item_two_tv;
    }

    private class ViewHolderFour {
        private RelativeLayout courselist_item_four_rlayout;
        private ImageView courselist_item_four_iv;
        private TextView courselist_item_four_tv_title;
        private TextView courselist_item_four_tv_price;
    }
}
