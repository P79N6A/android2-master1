package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.SuperiorDetailActivity;
import com.wh.wang.scroopclassproject.bean.SuperiorInfo;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by wang on 2017/8/15.
 */

public class SuperiorListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private RelativeLayout rlayout_one;
    private RelativeLayout superiror_center_right;
    private ImageView superiror_item_iv_one;
    private TextView superiror_item_title_one;
    private TextView superiror_item_maintalk_one;
    private TextView superiror_item_study_one;
    private RelativeLayout rlayout_two;
    private ImageView superiror_item_iv_two;
    private TextView superiror_item_title_two;
    private TextView superiror_item_maintalk_two;
    private TextView superiror_item_study_two;
    private RelativeLayout rlayout_three;
    private ImageView superiror_item_iv_three;
    private TextView superiror_item_title_three;
    private TextView superiror_item_maintalk_three;
    private TextView superiror_item_study_three;
    private RelativeLayout rlayout_superiror_center;
    private TextView superiror_center_tv_video;
    private RelativeLayout rlayout_four;

    private ImageView superiror_item_iv_four;
    private TextView superiror_item_title_four;
    private TextView superiror_item_maintalk_four;
    private TextView superiror_item_study_four;

    private List<SuperiorInfo.EventBean> eventList;
    private List<SuperiorInfo.SuperiorBean> freeList;
    private List<SuperiorInfo.SuperiorBean> goodList;
    private List<SuperiorInfo.SuperiorBean> qaList;
    private List<SuperiorInfo.SuperiorBean> newList;
    private List<SuperiorInfo.SuperiorBean> typicalList;
    private List<SuperiorInfo.SuperiorBean> tempList;
    private ImageView superior_event_one_iv;
    private ImageView superior_event_two_iv;
    private LinearLayout llayout_two;
    private RelativeLayout superior_item_two;
    private RelativeLayout superior_item_one;
    private RelativeLayout rlayout_superiror_event_center;
    private RelativeLayout superior_event_one_rl;
    private ImageView superior_event_one_iv1;
    private TextView superior_event_one_tv_location;
    private TextView superior_event_one_tv_date;
    private TextView superior_event_one_tv_money;
    private RelativeLayout superior_event_two_rl;
    private ImageView superior_event_two_iv1;
    private TextView superior_event_two_tv_location;
    private TextView superior_event_two_tv_date;
    private TextView superior_event_two_tv_money;
    private RelativeLayout superior_event_two_rl_one;
    private LinearLayout superior_event_two_rl_two;
    private RelativeLayout superior_event_two_rl_two_one;
    private RelativeLayout superior_event_two_rl_two_two;
    private ImageView superior_event_two_iv_two_one;
    private ImageView superior_event_two_iv_two_two;
    private TextView superior_event_two_tv_title_two_one;
    private TextView superior_event_two_tv_talk_two_one;
    private TextView superior_event_two_tv_learn_two_one;
    private TextView superior_event_two_tv_title_two_two;
    private TextView superior_event_two_tv_talk_two_two;
    private TextView superior_event_two_tv_learn_two_two;

    public SuperiorListViewAdapter(Context context,
                                   List<SuperiorInfo.EventBean> eventList,
                                   List<SuperiorInfo.SuperiorBean> goodList,
                                   List<SuperiorInfo.SuperiorBean> freeList,
                                   List<SuperiorInfo.SuperiorBean> typicalList,
                                   List<SuperiorInfo.SuperiorBean> newList,
                                   List<SuperiorInfo.SuperiorBean> qaList) {
        this.context = context;
        this.eventList = eventList;
        this.goodList = goodList;
        this.freeList = freeList;
        this.typicalList = typicalList;
        this.newList = newList;
        this.qaList = qaList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent,
                R.layout.superior_item, position);

        superior_item_one = viewHolder.getView(R.id.superior_item_one);
        rlayout_superiror_event_center = viewHolder.getView(R.id.rlayout_superiror_event_center);

        //数据为1
        superior_event_one_rl = viewHolder.getView(R.id.superior_event_one_rl);
        superior_event_one_iv1 = viewHolder.getView(R.id.superior_event_one_iv);
        superior_event_one_tv_location = viewHolder.getView(R.id.superior_event_one_tv_location);
        superior_event_one_tv_date = viewHolder.getView(R.id.superior_event_one_tv_date);
        superior_event_one_tv_money = viewHolder.getView(R.id.superior_event_one_tv_money);

        //数据为2时
        superior_event_two_rl = viewHolder.getView(R.id.superior_event_two_rl);
        superior_event_two_rl_one = viewHolder.getView(R.id.superior_event_two_rl_one); //一张图片
        superior_event_two_iv1 = viewHolder.getView(R.id.superior_event_two_iv);
        superior_event_two_tv_location = viewHolder.getView(R.id.superior_event_two_tv_location);
        superior_event_two_tv_date = viewHolder.getView(R.id.superior_event_two_tv_date);
        superior_event_two_tv_money = viewHolder.getView(R.id.superior_event_two_tv_money);

        //数据为3时
        superior_event_two_rl_two = viewHolder.getView(R.id.superior_event_two_rl_two);
        superior_event_two_rl_two_one = viewHolder.getView(R.id.superior_event_two_rl_two_one);
        superior_event_two_iv_two_one = viewHolder.getView(R.id.superior_event_two_iv_two_one);
        superior_event_two_tv_title_two_one = viewHolder.getView(R.id.superior_event_two_tv_title_two_one);
        superior_event_two_tv_talk_two_one = viewHolder.getView(R.id.superior_event_two_tv_talk_two_one);
        superior_event_two_tv_learn_two_one = viewHolder.getView(R.id.superior_event_two_tv_learn_two_one);

        superior_event_two_rl_two_two = viewHolder.getView(R.id.superior_event_two_rl_two_two);
        superior_event_two_iv_two_two = viewHolder.getView(R.id.superior_event_two_iv_two_two);
        superior_event_two_tv_title_two_two = viewHolder.getView(R.id.superior_event_two_tv_title_two_two);
        superior_event_two_tv_talk_two_two = viewHolder.getView(R.id.superior_event_two_tv_talk_two_two);
        superior_event_two_tv_learn_two_two = viewHolder.getView(R.id.superior_event_two_tv_learn_two_two);


        //数据为4时
        //----------------------------------------------------------------------------------

        superior_item_two = viewHolder.getView(R.id.superior_item_two);
        rlayout_superiror_center = viewHolder.getView(R.id.rlayout_superiror_center);
        superiror_center_tv_video = viewHolder.getView(R.id.superiror_center_tv_video);
        rlayout_one = viewHolder.getView(R.id.rlayout_one);
        superiror_item_iv_one = viewHolder.getView(R.id.superiror_item_iv_one);
        superiror_item_title_one = viewHolder.getView(R.id.superiror_item_title_one);
        superiror_item_maintalk_one = viewHolder.getView(R.id.superiror_item_maintalk_one);
        superiror_item_study_one = viewHolder.getView(R.id.superiror_item_study_one);

        rlayout_two = viewHolder.getView(R.id.rlayout_two);
        superiror_item_iv_two = viewHolder.getView(R.id.superiror_item_iv_two);
        superiror_item_title_two = viewHolder.getView(R.id.superiror_item_title_two);
        superiror_item_maintalk_two = viewHolder.getView(R.id.superiror_item_maintalk_two);
        superiror_item_study_two = viewHolder.getView(R.id.superiror_item_study_two);

        llayout_two = viewHolder.getView(R.id.llayout_two);

        rlayout_three = viewHolder.getView(R.id.rlayout_three);
        superiror_item_iv_three = viewHolder.getView(R.id.superiror_item_iv_three);
        superiror_item_title_three = viewHolder.getView(R.id.superiror_item_title_three);
        superiror_item_maintalk_three = viewHolder.getView(R.id.superiror_item_maintalk_three);
        superiror_item_study_three = viewHolder.getView(R.id.superiror_item_study_three);

        rlayout_four = viewHolder.getView(R.id.rlayout_four);
        superiror_item_iv_four = viewHolder.getView(R.id.superiror_item_iv_four);
        superiror_item_title_four = viewHolder.getView(R.id.superiror_item_title_four);
        superiror_item_maintalk_four = viewHolder.getView(R.id.superiror_item_maintalk_four);
        superiror_item_study_four = viewHolder.getView(R.id.superiror_item_study_four);


        if (position == 0) {
            superior_item_one.setVisibility(View.VISIBLE);
            superior_item_two.setVisibility(View.INVISIBLE);
            if (eventList.size() > 0) {
                superior_event_one_rl.setVisibility(View.VISIBLE);
                superior_event_two_rl.setVisibility(View.GONE);
                superior_event_two_rl_one.setVisibility(View.GONE);
                superior_event_two_rl_two.setVisibility(View.GONE);
                SuperiorInfo.EventBean eventBean = eventList.get(0);
                ImageLoader.getInstance().displayImage(eventBean.getImg(), superior_event_one_iv1, MyDisplayOptions.getOptions());
                String eventTitle = eventBean.getTitle();
                if (eventTitle.length() > 5) {
                    eventTitle = eventTitle.substring(0, 5) + "...";
                }

                String evnetTime = eventBean.getStart_shijian();
                if (evnetTime.length() > 10) {
                    evnetTime = evnetTime.substring(0, 10) + "...";
                }

                superior_event_one_tv_location.setText(eventTitle);
                superior_event_one_tv_date.setText(evnetTime);
                superior_event_one_tv_money.setText("¥ " + eventBean.getPrice());

                superior_event_one_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorInfo.EventBean eventBean = eventList.get(0);
                        Intent intent = new Intent(context, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",eventBean.getId());
                        context.startActivity(intent);
                    }
                });

            }

            if (eventList.size() > 1) {
                //TODO WHWH
                superior_event_one_rl.setVisibility(View.VISIBLE);
                superior_event_two_rl.setVisibility(View.VISIBLE);
                superior_event_two_rl_one.setVisibility(View.VISIBLE);
                superior_event_two_rl_two.setVisibility(View.GONE);
                SuperiorInfo.EventBean eventBean = eventList.get(1);
                ImageLoader.getInstance().displayImage(eventBean.getImg(), superior_event_two_iv1, MyDisplayOptions.getOptions());

                String eventTitle = eventBean.getTitle();
                if (eventTitle.length() > 5) {
                    eventTitle = eventTitle.substring(0, 5) + "...";
                }

                String evnetTime = eventBean.getStart_shijian();
                if (evnetTime.length() > 10) {
                    evnetTime = evnetTime.substring(0, 10) + "...";
                }
                superior_event_two_tv_location.setText(eventTitle);
                superior_event_two_tv_date.setText(evnetTime);
                superior_event_two_tv_money.setText("¥ " + eventBean.getPrice());

                superior_event_two_rl_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorInfo.EventBean eventBean = eventList.get(1);
                        Intent intent = new Intent(context, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",eventBean.getId());
                        context.startActivity(intent);
                    }
                });
            }

            if (eventList.size() > 2) {
                superior_event_one_rl.setVisibility(View.VISIBLE);
                superior_event_two_rl.setVisibility(View.VISIBLE);
                superior_event_two_rl_one.setVisibility(View.GONE);
                superior_event_two_rl_two.setVisibility(View.VISIBLE);
                SuperiorInfo.EventBean eventBean = eventList.get(1);
                ImageLoader.getInstance().displayImage(eventBean.getImg(), superior_event_two_iv_two_one, MyDisplayOptions.getOptions());
                superior_event_two_tv_title_two_one.setText(eventBean.getTitle());
                double tempprice = Double.parseDouble(eventBean.getPrice());
                if (tempprice == 0.0) {
                    superior_event_two_tv_talk_two_one.setText("免费");
                } else {
                    superior_event_two_tv_talk_two_one.setText("¥ " + tempprice);
                }

                superior_event_two_rl_two_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorInfo.EventBean eventBean1 = eventList.get(1);
                        Intent intent = new Intent(context, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",eventBean1.getId());
                        context.startActivity(intent);
                    }
                });


                SuperiorInfo.EventBean eventBean2 = eventList.get(2);
                ImageLoader.getInstance().displayImage(eventBean2.getImg(), superior_event_two_iv_two_two, MyDisplayOptions.getOptions());
                superior_event_two_tv_title_two_two.setText(eventBean2.getTitle());
                double tempprice2 = Double.parseDouble(eventBean2.getPrice());
                if (tempprice2 == 0.0) {
                    superior_event_two_tv_talk_two_two.setText("免费");
                } else {
                    superior_event_two_tv_talk_two_two.setText("¥ " + tempprice2);
                }

                superior_event_two_rl_two_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorInfo.EventBean eventBean = eventList.get(2);
                        Intent intent = new Intent(context, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",eventBean.getId());
                        context.startActivity(intent);
                    }
                });
            }

            //TODO WHWH
            rlayout_superiror_event_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.showToast((Activity) context, "eventList----->" + eventList.size() + "posion=" + position);
                    //3.传递列表数据-对象-序列化
                    Intent intent = new Intent(context, SuperiorDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("eventlist", (Serializable) eventList);
                    bundle.putInt("flagg", 0);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });


        } else if (position == 1) {
            superior_item_one.setVisibility(View.GONE);
            superior_item_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_one.setVisibility(View.VISIBLE);
            superiror_item_maintalk_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_three.setVisibility(View.VISIBLE);
            superiror_item_maintalk_four.setVisibility(View.VISIBLE);
            superiror_center_tv_video.setText("/  好评推荐  /");
            tempList = goodList;
            setTempData(tempList);
            rlayout_superiror_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.showToast((Activity) context, "goodList----->" + goodList.size() + "posion=" + position);
                    //3.传递列表数据-对象-序列化
                    Intent intent = new Intent(context, SuperiorDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tempList", (Serializable) goodList);
                    bundle.putInt("flagg", 1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else if (position == 2) {
            superior_item_one.setVisibility(View.GONE);
            superior_item_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_one.setVisibility(View.VISIBLE);
            superiror_item_maintalk_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_three.setVisibility(View.VISIBLE);
            superiror_item_maintalk_four.setVisibility(View.VISIBLE);
            superiror_center_tv_video.setText("/  免费好课  /");
            tempList = freeList;
            setTempData(tempList);
            rlayout_superiror_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.showToast((Activity) context, "freeList----->" + freeList.size() + "posion=" + position);
                    //3.传递列表数据-对象-序列化
                    Intent intent = new Intent(context, SuperiorDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tempList", (Serializable) freeList);
                    bundle.putInt("flagg", 2);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        } else if (position == 3) {
            superior_item_one.setVisibility(View.GONE);
            superior_item_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_one.setVisibility(View.VISIBLE);
            superiror_item_maintalk_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_three.setVisibility(View.VISIBLE);
            superiror_item_maintalk_four.setVisibility(View.VISIBLE);
            Log.e("whwh", "position == 2");
            superiror_center_tv_video.setText("/  经典演讲  /");
            tempList = typicalList;
            setTempData(tempList);

            rlayout_superiror_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ToastUtils.showToast((Activity) context, "typicalList----->" + typicalList.size() + "posion=" + position);
                    //3.传递列表数据-对象-序列化
                    Intent intent = new Intent(context, SuperiorDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tempList", (Serializable) typicalList);
                    bundle.putInt("flagg", 3);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else if (position == 4) {
            superior_item_one.setVisibility(View.GONE);
            superior_item_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_one.setVisibility(View.VISIBLE);
            superiror_item_maintalk_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_three.setVisibility(View.VISIBLE);
            superiror_item_maintalk_four.setVisibility(View.VISIBLE);
            Log.e("whwh", "position == 4");
            superiror_center_tv_video.setText("/  最新上架  /");
            tempList = newList;
            setTempData(tempList);
            rlayout_superiror_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.showToast((Activity) context, "qaList----->" + newList.size() + "posion=" + position);
                    //3.传递列表数据-对象-序列化
                    Intent intent = new Intent(context, SuperiorDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tempList", (Serializable) newList);
                    bundle.putInt("flagg", 1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        } else if (position == 5) {
            superior_item_one.setVisibility(View.GONE);
            superior_item_two.setVisibility(View.VISIBLE);
            superiror_item_maintalk_one.setVisibility(View.INVISIBLE);
            superiror_item_maintalk_two.setVisibility(View.INVISIBLE);
            superiror_item_maintalk_three.setVisibility(View.INVISIBLE);
            superiror_item_maintalk_four.setVisibility(View.INVISIBLE);
            Log.e("whwh", "position == 3");
            superiror_center_tv_video.setText("/   你问我答  /");
            tempList = qaList;
            setTempData(tempList);
            rlayout_superiror_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.showToast((Activity) context, "qaList----->" + qaList.size() + "posion=" + position);
                    //3.传递列表数据-对象-序列化
                    Intent intent = new Intent(context, SuperiorDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tempList", (Serializable) qaList);
                    bundle.putInt("flagg", 2);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        return viewHolder.getConvertView();
    }

    private void setTempData(final List<SuperiorInfo.SuperiorBean> tempList) {
        if (tempList.size() > 0) {

            rlayout_one.setVisibility(View.VISIBLE);
            rlayout_two.setVisibility(View.INVISIBLE);
            llayout_two.setVisibility(View.GONE);
            rlayout_three.setVisibility(View.INVISIBLE);
            rlayout_four.setVisibility(View.INVISIBLE);
            SuperiorInfo.SuperiorBean tempbean = tempList.get(0);
            ImageLoader.getInstance().displayImage(tempbean.getImg(), superiror_item_iv_one, MyDisplayOptions.getOptions());
            superiror_item_title_one.setText(tempbean.getTitle());
            String tempStrprice = tempbean.getNew_price();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                superiror_item_maintalk_one.setText("免费");
            } else {
                superiror_item_maintalk_one.setText("¥ " + tempprice);
            }
            superiror_item_study_one.setVisibility(View.INVISIBLE);
            superiror_item_study_one.setText(tempbean.getTotal_learn_nums() + "已学");
            rlayout_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.showToast((Activity) context, "tempList.get(0)");
                    SuperiorInfo.SuperiorBean tempbean = tempList.get(0);
                    Intent intent = new Intent(context, NewVideoInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", 2);
                    bundle.putSerializable("tempbean", (Serializable) tempbean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        if (tempList.size() > 1) {

            rlayout_one.setVisibility(View.VISIBLE);
            rlayout_two.setVisibility(View.VISIBLE);
            llayout_two.setVisibility(View.GONE);
            rlayout_three.setVisibility(View.INVISIBLE);
            rlayout_four.setVisibility(View.INVISIBLE);

            final SuperiorInfo.SuperiorBean tempbean = tempList.get(1);
            ImageLoader.getInstance().displayImage(tempbean.getImg(), superiror_item_iv_two, MyDisplayOptions.getOptions());
            superiror_item_title_two.setText(tempbean.getTitle());

            String tempStrprice = tempbean.getNew_price();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                superiror_item_maintalk_two.setText("免费");
            } else {
                superiror_item_maintalk_two.setText("¥ " + tempprice);
            }
            superiror_item_study_two.setVisibility(View.INVISIBLE);
            superiror_item_study_two.setText(tempbean.getTotal_learn_nums() + "已学");

            rlayout_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.showToast((Activity) context, "tempList.get(1)");
                    //3.传递列表数据-对象-序列化
                    SuperiorInfo.SuperiorBean tempbean = tempList.get(1);
                    GotoNextActUtils.getInstance().nextActivity(context,tempbean.getId(),tempbean.getType());
//                    Intent intent = new Intent(context, VideoInfosActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("index", 2);
//                    bundle.putSerializable("tempbean", (Serializable) tempbean);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
                }
            });

        }

        if (tempList.size() > 2) {
            rlayout_one.setVisibility(View.VISIBLE);
            rlayout_two.setVisibility(View.VISIBLE);
            llayout_two.setVisibility(View.VISIBLE);
            rlayout_three.setVisibility(View.VISIBLE);
            rlayout_four.setVisibility(View.INVISIBLE);

            SuperiorInfo.SuperiorBean tempbean = tempList.get(2);
            ImageLoader.getInstance().displayImage(tempbean.getImg(), superiror_item_iv_three, MyDisplayOptions.getOptions());
            superiror_item_title_three.setText(tempbean.getTitle());
            String tempStrprice = tempbean.getNew_price();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                superiror_item_maintalk_three.setText("免费");
            } else {
                superiror_item_maintalk_three.setText("¥ " + tempprice);
            }
            superiror_item_study_three.setVisibility(View.INVISIBLE);
            superiror_item_study_three.setText(tempbean.getTotal_learn_nums() + "已学");

            rlayout_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorInfo.SuperiorBean tempbean = tempList.get(2);
                    GotoNextActUtils.getInstance().nextActivity(context,tempbean.getId(),tempbean.getType());
//                    Intent intent = new Intent(context, VideoInfosActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("index", 2);
//                    bundle.putSerializable("tempbean", (Serializable) tempbean);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
                }
            });
        }

        if (tempList.size() > 3) {

            rlayout_one.setVisibility(View.VISIBLE);
            rlayout_two.setVisibility(View.VISIBLE);
            llayout_two.setVisibility(View.VISIBLE);
            rlayout_three.setVisibility(View.VISIBLE);
            rlayout_four.setVisibility(View.VISIBLE);

            SuperiorInfo.SuperiorBean tempbean = tempList.get(3);
            ImageLoader.getInstance().displayImage(tempbean.getImg(), superiror_item_iv_four, MyDisplayOptions.getOptions());
            superiror_item_title_four.setText(tempbean.getTitle());

            //superiror_item_maintalk_four.setText("主 讲");
            //superiror_item_study_four.setText(tempbean.getTotal_learn_nums() + "已学");
            String tempStrprice = tempbean.getNew_price();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                superiror_item_maintalk_four.setText("免费");
            } else {
                superiror_item_maintalk_four.setText("¥ " + tempprice);
            }
            superiror_item_study_four.setVisibility(View.INVISIBLE);
            superiror_item_study_four.setText(tempbean.getTotal_learn_nums() + "已学");

            rlayout_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorInfo.SuperiorBean tempbean = tempList.get(3);
                    GotoNextActUtils.getInstance().nextActivity(context,tempbean.getId(),tempbean.getType());
                }
            });
        }
    }
}
