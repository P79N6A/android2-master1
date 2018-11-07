package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.CouseInfoActivity;
import com.wh.wang.scroopclassproject.activity.ReadDetailActivity;
import com.wh.wang.scroopclassproject.activity.SuperiorDetailActivity;
import com.wh.wang.scroopclassproject.bean.SuperiorBean;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.utils.GotoNextActUtils;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import java.io.Serializable;
import java.util.List;

import static com.wh.wang.scroopclassproject.R.id.superiror_five_rlayout_title;
import static com.wh.wang.scroopclassproject.R.id.superiror_four_iv_more;
import static com.wh.wang.scroopclassproject.R.id.superiror_four_maintalk_one;
import static com.wh.wang.scroopclassproject.R.id.superiror_four_rlayout_one;
import static com.wh.wang.scroopclassproject.R.id.superiror_four_rlayout_title;
import static com.wh.wang.scroopclassproject.R.id.superiror_four_study_one;
import static com.wh.wang.scroopclassproject.R.id.superiror_four_title_one;
import static com.wh.wang.scroopclassproject.R.id.superiror_four_tv_more;
import static com.wh.wang.scroopclassproject.R.id.superiror_six_rlayout_four;
import static com.wh.wang.scroopclassproject.R.id.superiror_six_rlayout_one;
import static com.wh.wang.scroopclassproject.R.id.superiror_six_rlayout_three;
import static com.wh.wang.scroopclassproject.R.id.superiror_six_rlayout_two;
import static com.wh.wang.scroopclassproject.R.id.superiror_three_two_one_iv;
import static com.wh.wang.scroopclassproject.R.id.superiror_three_two_one_rlayout_content;
import static com.wh.wang.scroopclassproject.R.id.superiror_three_two_one_tv_date;
import static com.wh.wang.scroopclassproject.R.id.superiror_three_two_one_tv_location;
import static com.wh.wang.scroopclassproject.R.id.superiror_three_two_one_tv_mtwoy;


/**
 * Created by wang on 2017/8/15.
 */

public class SuperiorNewViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private List<SuperiorBean.ThreecourseBean> zxList;//最新消息滚动
    private List<SuperiorBean.EventBean> eventList; //热门课程
    private List<SuperiorBean.JingxuanBean> jxList; //精选课程
    private List<SuperiorBean.FreeCourseBean> mfList; //免费课程
    private List<SuperiorBean.TypicalCourseBean> jdList; //经典演讲
    private List<SuperiorBean.ReadNewsBean> zwList; //最热话题文章
    private List<SuperiorBean.ReadVideoBean> zsList; //最热话题视频

    private int TypeOne = 0;//注意这个不同布局的类型起始值必须从0开始
    private int TypeTwo = 1;
    private int TypeThreeOne = 2;
    private int TypeThreeTwo = 2;
    private int TypeThreeThree = 2;
    private int TypeFour = 3;
    private int TypeFive = 4;
    private int TypeSix = 5;
    private int TypeSeven = 6;
    private boolean isShow = true;


    public SuperiorNewViewAdapter(Context context,
                                  List<SuperiorBean.ThreecourseBean> zxList,
                                  List<SuperiorBean.EventBean> eventList,
                                  List<SuperiorBean.JingxuanBean> jxList,
                                  List<SuperiorBean.FreeCourseBean> mfList,
                                  List<SuperiorBean.TypicalCourseBean> jdList,
                                  List<SuperiorBean.ReadNewsBean> zwList,
                                  List<SuperiorBean.ReadVideoBean> zsList) {
        this.context = context;
        this.zxList = zxList;
        this.eventList = eventList;
        this.jxList = jxList;
        this.mfList = mfList;
        this.jdList = jdList;
        this.zwList = zwList;
        this.zsList = zsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 7;
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
        } else if (position == 2) {
            if (eventList.size() == 1) {
                return TypeThreeOne;
            } else if (eventList.size() == 2) {
                return TypeThreeTwo;
            } else {
                return TypeThreeThree;
            }
        } else if (position == 3) {
            return TypeFour;
        } else if (position == 4) {
            return TypeFive;
        } else if (position == 5) {
            return TypeSix;
        } else {
            return TypeSeven;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 7;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TypeOneViewHolder typeOneViewHolder = null;
        TypeTwoViewHolder typeTwoViewHolder = null;
        TypeThreeOneViewHolder typeThreeOneViewHolder = null;
        TypeThreeTwoViewHolder typeThreeTwoViewHolder = null;
        TypeThreeThreeViewHolder typeThreeThreeViewHolder = null;
        TypeFourViewHolder typeFourViewHolder = null;
        TypeFiveViewHolder typeFiveViewHolder = null;
        TypeSixViewHolder typeSixViewHolder = null;
        TypeSevenViewHolder typeSevenViewHolder = null;

        int Type = getItemViewType(position);

        if (convertView == null) {
            if (Type == TypeOne) {
                convertView = inflater.inflate(R.layout.superior_new_one, parent, false);
                typeOneViewHolder = new TypeOneViewHolder();
                typeOneViewHolder.superiror_one_llayout =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_one_llayout);
                typeOneViewHolder.superiror_one_rlayout_flag_one =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_one_rlayout_flag_one);
                typeOneViewHolder.superiror_one_rlayout_flag_two =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_one_rlayout_flag_two);
                typeOneViewHolder.superiror_one_rlayout_flag_three =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_one_rlayout_flag_three);
                typeOneViewHolder.superiror_one_rlayout_flag_four =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_one_rlayout_flag_four);
                typeOneViewHolder.superiror_one_rlayout_flag_five =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_one_rlayout_flag_five);
                convertView.setTag(typeOneViewHolder);

            } else if (Type == TypeTwo) {

                convertView = inflater.inflate(R.layout.superior_new_two, parent, false);
                typeTwoViewHolder = new TypeTwoViewHolder();
                typeTwoViewHolder.marquee_view = (ViewFlipper)
                        convertView.findViewById(R.id.marquee_view);
                convertView.setTag(typeTwoViewHolder);

            } else if (Type == TypeThreeOne && eventList.size() == 1) {
                convertView = inflater.inflate(R.layout.superior_new_three_one, parent, false);
                typeThreeOneViewHolder = new TypeThreeOneViewHolder();
                //标题栏父类显示更多
                typeThreeOneViewHolder.superiror_three_one_rlayout_title =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_one_rlayout_title);
                //内容栏父类
                typeThreeOneViewHolder.superiror_three_one_rlayout_content =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_one_rlayout_content);
                //显示图片
                typeThreeOneViewHolder.superiror_three_one_iv =
                        (ImageView) convertView.findViewById(R.id.superiror_three_one_iv);
                //位置
                typeThreeOneViewHolder.superiror_three_one_tv_location =
                        (TextView) convertView.findViewById(R.id.superiror_three_one_tv_location);
                //时间
                typeThreeOneViewHolder.superiror_three_one_tv_date =
                        (TextView) convertView.findViewById(R.id.superiror_three_one_tv_date);
                //钱数
                typeThreeOneViewHolder.superiror_three_one_tv_money =
                        (TextView) convertView.findViewById(R.id.superiror_three_one_tv_money);
                convertView.setTag(typeThreeOneViewHolder);

            } else if (Type == TypeThreeTwo && eventList.size() == 2) {
                convertView = inflater.inflate(R.layout.superior_new_three_two, parent, false);
                typeThreeTwoViewHolder = new TypeThreeTwoViewHolder();
                //标题栏父类显示更多
                typeThreeTwoViewHolder.superiror_three_two_rlayout_title =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_two_rlayout_title);

                //更多
                typeThreeTwoViewHolder.superiror_three_two_rlayout_title_iv_more =
                        (ImageView) convertView.findViewById(R.id.superiror_three_two_rlayout_title_iv_more);

                typeThreeTwoViewHolder.superiror_three_two_rlayout_title_tv_more =
                        (TextView) convertView.findViewById(R.id.superiror_three_two_rlayout_title_tv_more);


                //内容栏父类
                typeThreeTwoViewHolder.superiror_three_two_one_rlayout_content =
                        (RelativeLayout) convertView.findViewById(superiror_three_two_one_rlayout_content);
                //显示图片
                typeThreeTwoViewHolder.superiror_three_two_one_iv =
                        (ImageView) convertView.findViewById(superiror_three_two_one_iv);
                //位置
                typeThreeTwoViewHolder.superiror_three_two_one_tv_location =
                        (TextView) convertView.findViewById(superiror_three_two_one_tv_location);
                //时间
                typeThreeTwoViewHolder.superiror_three_two_one_tv_date =
                        (TextView) convertView.findViewById(superiror_three_two_one_tv_date);
                //钱数
                typeThreeTwoViewHolder.superiror_three_two_one_tv_mtwoy =
                        (TextView) convertView.findViewById(superiror_three_two_one_tv_mtwoy);

                //第二张 内容栏父类
                typeThreeTwoViewHolder.superiror_three_two_two_rlayout_content =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_two_two_rlayout_content);
                //显示图片
                typeThreeTwoViewHolder.superiror_three_two_two_iv =
                        (ImageView) convertView.findViewById(R.id.superiror_three_two_two_iv);
                //位置
                typeThreeTwoViewHolder.superiror_three_two_two_tv_location =
                        (TextView) convertView.findViewById(R.id.superiror_three_two_two_tv_location);
                //时间
                typeThreeTwoViewHolder.superiror_three_two_two_tv_date =
                        (TextView) convertView.findViewById(R.id.superiror_three_two_two_tv_date);
                //钱数
                typeThreeTwoViewHolder.superiror_three_two_two_tv_mtwoy =
                        (TextView) convertView.findViewById(R.id.superiror_three_two_two_tv_mtwoy);

                convertView.setTag(typeThreeTwoViewHolder);

            } else if (Type == TypeThreeThree && eventList.size() >= 3) {
                convertView = inflater.inflate(R.layout.superior_new_three_three, parent, false);
                typeThreeThreeViewHolder = new TypeThreeThreeViewHolder();
                //标题栏父类显示更多
                typeThreeThreeViewHolder.superiror_three_three_rlayout_title =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_three_rlayout_title);
                typeThreeThreeViewHolder.superiror_three_three_rlayout_title_iv_more =
                        (ImageView) convertView.findViewById(R.id.superiror_three_three_rlayout_title_iv_more);

                typeThreeThreeViewHolder.superiror_three_three_rlayout_title_tv_more =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_rlayout_title_tv_more);

                //内容栏父类
                typeThreeThreeViewHolder.superiror_three_three_one_rlayout_content =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_three_one_rlayout_content);
                //显示图片
                typeThreeThreeViewHolder.superiror_three_three_one_iv =
                        (ImageView) convertView.findViewById(R.id.superiror_three_three_one_iv);
                //位置
                typeThreeThreeViewHolder.superiror_three_three_one_tv_location =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_one_tv_location);
                //时间
                typeThreeThreeViewHolder.superiror_three_three_one_tv_date =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_one_tv_date);
                //钱数
                typeThreeThreeViewHolder.superiror_three_three_one_tv_mthreey =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_one_tv_mthreey);

                //第二张 内容栏父类
                typeThreeThreeViewHolder.superiror_three_three_two_rlayout_content =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_three_two_rlayout_content);
                //显示图片
                typeThreeThreeViewHolder.superiror_three_three_two_iv =
                        (ImageView) convertView.findViewById(R.id.superiror_three_three_two_iv);
                //标题
                typeThreeThreeViewHolder.superiror_three_three_two_tv_title =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_two_tv_title);
                //主讲
                typeThreeThreeViewHolder.superiror_three_three_two_tv_talk =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_two_tv_talk);
                //多少人学习
                typeThreeThreeViewHolder.superiror_three_three_two_tv_learn =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_two_tv_learn);

                //第三张 内容栏父类
                typeThreeThreeViewHolder.superiror_three_three_three_rlayout_content =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_three_three_three_rlayout_content);
                //显示图片
                typeThreeThreeViewHolder.superiror_three_three_three_iv =
                        (ImageView) convertView.findViewById(R.id.superiror_three_three_three_iv);
                //标题
                typeThreeThreeViewHolder.superiror_three_three_three_tv_title =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_three_tv_title);
                //主讲
                typeThreeThreeViewHolder.superiror_three_three_three_tv_talk =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_three_tv_talk);
                //多少人学习
                typeThreeThreeViewHolder.superiror_three_three_three_tv_learn =
                        (TextView) convertView.findViewById(R.id.superiror_three_three_three_tv_learn);
                convertView.setTag(typeThreeThreeViewHolder);
            } else if (Type == TypeFour) {
                convertView = inflater.inflate(R.layout.superior_new_four, parent, false);
                typeFourViewHolder = new TypeFourViewHolder();

                //标题栏父类显示更多
                typeFourViewHolder.superiror_four_rlayout_title =
                        (RelativeLayout) convertView.findViewById(superiror_four_rlayout_title);

                typeFourViewHolder.superiror_four_iv_more =
                        (ImageView) convertView.findViewById(superiror_four_iv_more);

                typeFourViewHolder.superiror_four_tv_more =
                        (TextView) convertView.findViewById(superiror_four_tv_more);

                //第一张 内容栏父类
                typeFourViewHolder.superiror_four_rlayout_one =
                        (RelativeLayout) convertView.findViewById(superiror_four_rlayout_one);
                //显示图片
                typeFourViewHolder.superiror_four_iv_one =
                        (ImageView) convertView.findViewById(R.id.superiror_four_iv_one);
                //标题
                typeFourViewHolder.superiror_four_title_one =
                        (TextView) convertView.findViewById(superiror_four_title_one);
                //主讲
                typeFourViewHolder.superiror_four_maintalk_one =
                        (TextView) convertView.findViewById(superiror_four_maintalk_one);
                //多少人学习
                typeFourViewHolder.superiror_four_study_one =
                        (TextView) convertView.findViewById(superiror_four_study_one);

                //第二张 内容栏父类
                typeFourViewHolder.superiror_four_rlayout_two =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_four_rlayout_two);
                //显示图片
                typeFourViewHolder.superiror_four_iv_two =
                        (ImageView) convertView.findViewById(R.id.superiror_four_iv_two);
                //标题
                typeFourViewHolder.superiror_four_title_two =
                        (TextView) convertView.findViewById(R.id.superiror_four_title_two);
                //主讲
                typeFourViewHolder.superiror_four_maintalk_two =
                        (TextView) convertView.findViewById(R.id.superiror_four_maintalk_two);
                //多少人学习
                typeFourViewHolder.superiror_four_study_two =
                        (TextView) convertView.findViewById(R.id.superiror_four_study_two);

                //第三张 内容栏父类
                typeFourViewHolder.superiror_four_rlayout_three =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_four_rlayout_three);
                //显示图片
                typeFourViewHolder.superiror_four_iv_three =
                        (ImageView) convertView.findViewById(R.id.superiror_four_iv_three);
                //标题
                typeFourViewHolder.superiror_four_title_three =
                        (TextView) convertView.findViewById(R.id.superiror_four_title_three);
                //主讲
                typeFourViewHolder.superiror_four_maintalk_three =
                        (TextView) convertView.findViewById(R.id.superiror_four_maintalk_three);
                //多少人学习
                typeFourViewHolder.superiror_four_study_three =
                        (TextView) convertView.findViewById(R.id.superiror_four_study_three);

                //第四张 内容栏父类
                typeFourViewHolder.superiror_four_rlayout_four =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_four_rlayout_four);
                //显示图片
                typeFourViewHolder.superiror_four_iv_four =
                        (ImageView) convertView.findViewById(R.id.superiror_four_iv_four);
                //标题
                typeFourViewHolder.superiror_four_title_four =
                        (TextView) convertView.findViewById(R.id.superiror_four_title_four);
                //主讲
                typeFourViewHolder.superiror_four_maintalk_four =
                        (TextView) convertView.findViewById(R.id.superiror_four_maintalk_four);
                //多少人学习
                typeFourViewHolder.superiror_four_study_four =
                        (TextView) convertView.findViewById(R.id.superiror_four_study_four);

                convertView.setTag(typeFourViewHolder);
            } else if (Type == TypeFive) {
                convertView = inflater.inflate(R.layout.superior_new_five, parent, false);
                typeFiveViewHolder = new TypeFiveViewHolder();

                //标题栏父类显示更多
                typeFiveViewHolder.superiror_five_rlayout_title =
                        (RelativeLayout) convertView.findViewById(superiror_five_rlayout_title);

                typeFiveViewHolder.superiror_five_iv_more =
                        (ImageView) convertView.findViewById(R.id.superiror_five_iv_more);

                typeFiveViewHolder.superiror_five_tv_more =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_more);

                //第一张 内容栏父类
                typeFiveViewHolder.superiror_five_rlayout_one =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_five_rlayout_one);
                //显示图片
                typeFiveViewHolder.superiror_five_iv_one =
                        (ImageView) convertView.findViewById(R.id.superiror_five_iv_one);
                //标题
                typeFiveViewHolder.superiror_five_tv_title_one =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_title_one);
                //金额
                typeFiveViewHolder.superiror_five_tv_money_one =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_money_one);


                //第二张 内容栏父类
                typeFiveViewHolder.superiror_five_rlayout_two =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_five_rlayout_two);
                //显示图片
                typeFiveViewHolder.superiror_five_iv_two =
                        (ImageView) convertView.findViewById(R.id.superiror_five_iv_two);
                //标题
                typeFiveViewHolder.superiror_five_tv_title_two =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_title_two);
                //金额
                typeFiveViewHolder.superiror_five_tv_money_two =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_money_two);


                //第三张 内容栏父类
                typeFiveViewHolder.superiror_five_rlayout_three =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_five_rlayout_three);
                //显示图片
                typeFiveViewHolder.superiror_five_iv_three =
                        (ImageView) convertView.findViewById(R.id.superiror_five_iv_three);
                //标题
                typeFiveViewHolder.superiror_five_tv_title_three =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_title_three);
                //金额
                typeFiveViewHolder.superiror_five_tv_money_three =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_money_three);

                //第四张 内容栏父类
                typeFiveViewHolder.superiror_five_rlayout_four =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_five_rlayout_four);
                //显示图片
                typeFiveViewHolder.superiror_five_iv_four =
                        (ImageView) convertView.findViewById(R.id.superiror_five_iv_four);
                //标题
                typeFiveViewHolder.superiror_five_tv_title_four =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_title_four);
                //金额
                typeFiveViewHolder.superiror_five_tv_money_four =
                        (TextView) convertView.findViewById(R.id.superiror_five_tv_money_four);

                convertView.setTag(typeFiveViewHolder);
            } else if (Type == TypeSix) {
                convertView = inflater.inflate(R.layout.superior_new_six, parent, false);
                typeSixViewHolder = new TypeSixViewHolder();

                //标题栏父类显示更多
                typeSixViewHolder.superiror_six_rlayout_title =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_six_rlayout_title);

                typeSixViewHolder.superiror_six_iv_more =
                        (ImageView) convertView.findViewById(R.id.superiror_six_iv_more);

                typeSixViewHolder.superiror_six_tv_more =
                        (TextView) convertView.findViewById(R.id.superiror_six_tv_more);

                //第一张 内容栏父类
                typeSixViewHolder.superiror_six_rlayout_one =
                        (RelativeLayout) convertView.findViewById(superiror_six_rlayout_one);
                //显示图片
                typeSixViewHolder.superiror_six_iv_one =
                        (ImageView) convertView.findViewById(R.id.superiror_six_iv_one);
                //标题
                typeSixViewHolder.superiror_six_title_one =
                        (TextView) convertView.findViewById(R.id.superiror_six_title_one);
                //主讲
                typeSixViewHolder.superiror_six_maintalk_one =
                        (TextView) convertView.findViewById(R.id.superiror_six_maintalk_one);
                //多少人学习
                typeSixViewHolder.superiror_six_study_one =
                        (TextView) convertView.findViewById(R.id.superiror_six_study_one);

                //第二张 内容栏父类
                typeSixViewHolder.superiror_six_rlayout_two =
                        (RelativeLayout) convertView.findViewById(superiror_six_rlayout_two);
                //显示图片
                typeSixViewHolder.superiror_six_iv_two =
                        (ImageView) convertView.findViewById(R.id.superiror_six_iv_two);
                //标题
                typeSixViewHolder.superiror_six_title_two =
                        (TextView) convertView.findViewById(R.id.superiror_six_title_two);
                //主讲
                typeSixViewHolder.superiror_six_maintalk_two =
                        (TextView) convertView.findViewById(R.id.superiror_six_maintalk_two);
                //多少人学习
                typeSixViewHolder.superiror_six_study_two =
                        (TextView) convertView.findViewById(R.id.superiror_six_study_two);

                //第三张 内容栏父类
                typeSixViewHolder.superiror_six_rlayout_three =
                        (RelativeLayout) convertView.findViewById(superiror_six_rlayout_three);
                //显示图片
                typeSixViewHolder.superiror_six_iv_three =
                        (ImageView) convertView.findViewById(R.id.superiror_six_iv_three);
                //标题
                typeSixViewHolder.superiror_six_title_three =
                        (TextView) convertView.findViewById(R.id.superiror_six_title_three);
                //主讲
                typeSixViewHolder.superiror_six_maintalk_three =
                        (TextView) convertView.findViewById(R.id.superiror_six_maintalk_three);
                //多少人学习
                typeSixViewHolder.superiror_six_study_three =
                        (TextView) convertView.findViewById(R.id.superiror_six_study_three);

                //第四张 内容栏父类
                typeSixViewHolder.superiror_six_rlayout_four =
                        (RelativeLayout) convertView.findViewById(superiror_six_rlayout_four);
                //显示图片
                typeSixViewHolder.superiror_six_iv_four =
                        (ImageView) convertView.findViewById(R.id.superiror_six_iv_four);
                //标题
                typeSixViewHolder.superiror_six_title_four =
                        (TextView) convertView.findViewById(R.id.superiror_six_title_four);
                //主讲
                typeSixViewHolder.superiror_six_maintalk_four =
                        (TextView) convertView.findViewById(R.id.superiror_six_maintalk_four);
                //多少人学习
                typeSixViewHolder.superiror_six_study_four =
                        (TextView) convertView.findViewById(R.id.superiror_six_study_four);
                convertView.setTag(typeSixViewHolder);
            } else if (Type == TypeSeven) {
                convertView = inflater.inflate(R.layout.superior_new_seven, parent, false);
                typeSevenViewHolder = new TypeSevenViewHolder();

                //标题栏父类显示更多
                typeSevenViewHolder.superiror_seven_rlayout_title =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_seven_rlayout_title);

                //看文章
                typeSevenViewHolder.superiror_seven_wen =
                        (Button) convertView.findViewById(R.id.superiror_seven_wen);
                //看视频
                typeSevenViewHolder.superiror_seven_video =
                        (Button) convertView.findViewById(R.id.superiror_seven_video);


                //------------------------看文章---------------------------
                typeSevenViewHolder.superiror_seven_llayout_wen =
                        (LinearLayout) convertView.findViewById(R.id.superiror_seven_llayout_wen);

                typeSevenViewHolder.rl_seven_one =
                        (RelativeLayout) convertView.findViewById(R.id.rl_seven_one);
                typeSevenViewHolder.rl_seven_one_tv_title =
                        (TextView) convertView.findViewById(R.id.rl_seven_one_tv_title);

                typeSevenViewHolder.rl_seven_two =
                        (RelativeLayout) convertView.findViewById(R.id.rl_seven_two);
                typeSevenViewHolder.rl_seven_two_tv_title =
                        (TextView) convertView.findViewById(R.id.rl_seven_two_tv_title);

                typeSevenViewHolder.rl_seven_three =
                        (RelativeLayout) convertView.findViewById(R.id.rl_seven_three);
                typeSevenViewHolder.rl_seven_three_tv_title =
                        (TextView) convertView.findViewById(R.id.rl_seven_three_tv_title);

                typeSevenViewHolder.rl_seven_four =
                        (RelativeLayout) convertView.findViewById(R.id.rl_seven_four);
                typeSevenViewHolder.rl_seven_four_tv_title =
                        (TextView) convertView.findViewById(R.id.rl_seven_four_tv_title);

                typeSevenViewHolder.rl_seven_five =
                        (RelativeLayout) convertView.findViewById(R.id.rl_seven_five);
                typeSevenViewHolder.rl_seven_five_tv_title =
                        (TextView) convertView.findViewById(R.id.rl_seven_five_tv_title);

                typeSevenViewHolder.rl_seven_six =
                        (RelativeLayout) convertView.findViewById(R.id.rl_seven_six);
                typeSevenViewHolder.rl_seven_six_tv_title =
                        (TextView) convertView.findViewById(R.id.rl_seven_six_tv_title);

                typeSevenViewHolder.rl_seven_seven_btn =
                        (Button) convertView.findViewById(R.id.rl_seven_seven_btn);

                //------------------------看视频------------------------
                typeSevenViewHolder.superiror_seven_rlayout_video =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_seven_rlayout_video);

                //第一个
                typeSevenViewHolder.superiror_seven_rlayout_one =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_seven_rlayout_one);

                typeSevenViewHolder.superiror_seven_iv_one =
                        (ImageView) convertView.findViewById(R.id.superiror_seven_iv_one);

                typeSevenViewHolder.superiror_seven_title_one =
                        (TextView) convertView.findViewById(R.id.superiror_seven_title_one);

                //第二个
                typeSevenViewHolder.superiror_seven_rlayout_two =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_seven_rlayout_two);

                typeSevenViewHolder.superiror_seven_iv_two =
                        (ImageView) convertView.findViewById(R.id.superiror_seven_iv_two);

                typeSevenViewHolder.superiror_seven_title_two =
                        (TextView) convertView.findViewById(R.id.superiror_seven_title_two);

                //第三个
                typeSevenViewHolder.superiror_seven_rlayout_three =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_seven_rlayout_three);

                typeSevenViewHolder.superiror_seven_iv_three =
                        (ImageView) convertView.findViewById(R.id.superiror_seven_iv_three);

                typeSevenViewHolder.superiror_seven_title_three =
                        (TextView) convertView.findViewById(R.id.superiror_seven_title_three);

                //第四个
                typeSevenViewHolder.superiror_seven_rlayout_four =
                        (RelativeLayout) convertView.findViewById(R.id.superiror_seven_rlayout_four);

                typeSevenViewHolder.superiror_seven_iv_four =
                        (ImageView) convertView.findViewById(R.id.superiror_seven_iv_four);

                typeSevenViewHolder.superiror_seven_title_four =
                        (TextView) convertView.findViewById(R.id.superiror_seven_title_four);

                typeSevenViewHolder.rl_seven_four_btn =
                        (Button) convertView.findViewById(R.id.rl_seven_four_btn);

                convertView.setTag(typeSevenViewHolder);
            }
        } else {

            if (Type == TypeOne) {
                typeOneViewHolder = (TypeOneViewHolder) convertView.getTag();
            } else if (Type == TypeTwo) {
                typeTwoViewHolder = (TypeTwoViewHolder) convertView.getTag();
            } else if (Type == TypeThreeOne && eventList.size() == 1) {
                typeThreeOneViewHolder = (TypeThreeOneViewHolder) convertView.getTag();
            } else if (Type == TypeThreeTwo && eventList.size() == 2) {
                typeThreeTwoViewHolder = (TypeThreeTwoViewHolder) convertView.getTag();
            } else if (Type == TypeThreeThree && eventList.size() >= 3) {
                typeThreeThreeViewHolder = (TypeThreeThreeViewHolder) convertView.getTag();
            } else if (Type == TypeFour) {
                typeFourViewHolder = (TypeFourViewHolder) convertView.getTag();
            } else if (Type == TypeFive) {
                typeFiveViewHolder = (TypeFiveViewHolder) convertView.getTag();
            } else if (Type == TypeSix) {
                typeSixViewHolder = (TypeSixViewHolder) convertView.getTag();
            } else if (Type == TypeSeven) {
                typeSevenViewHolder = (TypeSevenViewHolder) convertView.getTag();
            }
        }

        //设置数据
        if (Type == TypeOne) {
            typeOneViewHolder.superiror_one_llayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 100);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });


            typeOneViewHolder.superiror_one_rlayout_flag_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });


            typeOneViewHolder.superiror_one_rlayout_flag_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 26);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            typeOneViewHolder.superiror_one_rlayout_flag_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 13);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            typeOneViewHolder.superiror_one_rlayout_flag_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 19);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            typeOneViewHolder.superiror_one_rlayout_flag_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 7);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        } else if (Type == TypeTwo) {

            for (int i = 0; i < zxList.size(); i++) {
                View view = View.inflate(context, R.layout.superior_new_two_item, null);
                TextView fctext = (TextView) view.findViewById(R.id.fctext);
                fctext.setText(zxList.get(i).getHometitle());
                RelativeLayout rlayout = (RelativeLayout) view.findViewById(R.id.superior_new_two_item_rlayout);
                typeTwoViewHolder.marquee_view.addView(view);

                final int ss = i;
                rlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ThreecourseBean zxbean = zxList.get(ss);
                        GotoNextActUtils.getInstance().nextActivity(context,zxbean.getId(),zxbean.getType());
                    }
                });
            }

        } else if (Type == TypeThreeOne && eventList.size() == 1) {

            SuperiorBean.EventBean eventBean = eventList.get(0);
            ImageLoader.getInstance().displayImage(eventBean.getImg(), typeThreeOneViewHolder.superiror_three_one_iv, MyDisplayOptions.getOptions());
            String eventTitle = eventBean.getTitle();
            if (eventTitle.length() > 5) {
                eventTitle = eventTitle.substring(0, 5) + "...";
            }

            String evnetTime = eventBean.getStart_shijian();
            if (evnetTime.length() > 10) {
                evnetTime = evnetTime.substring(0, 10) + "...";
            }
            typeThreeOneViewHolder.superiror_three_one_tv_location.setText(eventTitle);
            typeThreeOneViewHolder.superiror_three_one_tv_date.setText(evnetTime);
            typeThreeOneViewHolder.superiror_three_one_tv_money.setText("¥ " + eventBean.getPrice());

            typeThreeOneViewHolder.superiror_three_one_rlayout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorBean.EventBean eventBean = eventList.get(0);
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",eventBean.getId());
                    context.startActivity(intent);
                }
            });


        } else if (Type == TypeThreeTwo && eventList.size() == 2) {
            typeThreeTwoViewHolder.superiror_three_two_rlayout_title_iv_more.setVisibility(View.INVISIBLE);
            SuperiorBean.EventBean eventBean = eventList.get(0);
            ImageLoader.getInstance().displayImage(eventBean.getImg(), typeThreeTwoViewHolder.superiror_three_two_one_iv, MyDisplayOptions.getOptions());
            String eventTitle = eventBean.getTitle();
            if (eventTitle.length() > 5) {
                eventTitle = eventTitle.substring(0, 5) + "...";
            }

            String evnetTime = eventBean.getStart_shijian();
            if (evnetTime.length() > 10) {
                evnetTime = evnetTime.substring(0, 10) + "...";
            }

            typeThreeTwoViewHolder.superiror_three_two_one_tv_location.setText(eventTitle);
            typeThreeTwoViewHolder.superiror_three_two_one_tv_date.setText(evnetTime);
            typeThreeTwoViewHolder.superiror_three_two_one_tv_mtwoy.setText("¥ " + eventBean.getPrice());

            typeThreeTwoViewHolder.superiror_three_two_one_rlayout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorBean.EventBean eventBean = eventList.get(0);
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",eventBean.getId());
                    context.startActivity(intent);
                }
            });

            SuperiorBean.EventBean eventBean2 = eventList.get(1);
            ImageLoader.getInstance().displayImage(eventBean2.getImg(), typeThreeTwoViewHolder.superiror_three_two_two_iv, MyDisplayOptions.getOptions());
            String eventTitle2 = eventBean2.getTitle();
            if (eventTitle2.length() > 5) {
                eventTitle2 = eventTitle2.substring(0, 5) + "...";
            }

            String evnetTime2 = eventBean2.getStart_shijian();
            if (evnetTime2.length() > 10) {
                evnetTime2 = evnetTime2.substring(0, 10) + "...";
            }
            typeThreeTwoViewHolder.superiror_three_two_two_tv_location.setText(eventTitle2);
            typeThreeTwoViewHolder.superiror_three_two_two_tv_date.setText(evnetTime2);
            typeThreeTwoViewHolder.superiror_three_two_two_tv_mtwoy.setText("¥ " + eventBean2.getPrice());
            typeThreeTwoViewHolder.superiror_three_two_two_rlayout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorBean.EventBean eventBean = eventList.get(1);
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",eventBean.getId());
                    context.startActivity(intent);
                }
            });

        } else if (Type == TypeThreeThree && eventList.size() >= 3) {
            if (eventList.size() == 3) {
                typeThreeThreeViewHolder.superiror_three_three_rlayout_title_iv_more.setVisibility(View.INVISIBLE);
                typeThreeThreeViewHolder.superiror_three_three_rlayout_title_tv_more.setVisibility(View.INVISIBLE);
            } else if (eventList.size() > 3) {
                typeThreeThreeViewHolder.superiror_three_three_rlayout_title_iv_more.setVisibility(View.VISIBLE);
                typeThreeThreeViewHolder.superiror_three_three_rlayout_title_tv_more.setVisibility(View.VISIBLE);
                typeThreeThreeViewHolder.superiror_three_three_rlayout_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SuperiorDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("eventList", (Serializable) eventList);
                        bundle.putInt("flagg", 10);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
            //---------------------------------------------------------------------------------------------
            SuperiorBean.EventBean eventBean = eventList.get(0);
            ImageLoader.getInstance().displayImage(eventBean.getImg(), typeThreeThreeViewHolder.superiror_three_three_one_iv, MyDisplayOptions.getOptions());
            String eventTitle = eventBean.getTitle();
            if (eventTitle.length() > 5) {
                eventTitle = eventTitle.substring(0, 5) + "...";
            }

            String evnetTime = eventBean.getStart_shijian();
            if (evnetTime.length() > 10) {
                evnetTime = evnetTime.substring(0, 10) + "...";
            }
            typeThreeThreeViewHolder.superiror_three_three_one_tv_location.setText(eventTitle);
            typeThreeThreeViewHolder.superiror_three_three_one_tv_date.setText(evnetTime);
            typeThreeThreeViewHolder.superiror_three_three_one_tv_mthreey.setText("¥ " + eventBean.getPrice());
            typeThreeThreeViewHolder.superiror_three_three_one_rlayout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorBean.EventBean eventBean = eventList.get(0);
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",eventBean.getId());
                    context.startActivity(intent);
                }
            });

            //---------------------------------------------------------------------------------------------
            SuperiorBean.EventBean eventBean2 = eventList.get(1);
            ImageLoader.getInstance().displayImage(eventBean2.getImg(), typeThreeThreeViewHolder.superiror_three_three_two_iv,
                    MyDisplayOptions.getOptions());

            typeThreeThreeViewHolder.superiror_three_three_two_tv_title.setText(eventBean2.getTitle());
            String tempStrprice = eventBean2.getPrice();
            double tempprice = Double.parseDouble(tempStrprice);
            if (tempprice == 0.0) {
                typeThreeThreeViewHolder.superiror_three_three_two_tv_talk.setText("免费");
            } else {
                typeThreeThreeViewHolder.superiror_three_three_two_tv_talk.setText("¥ " + tempprice);
            }
            typeThreeThreeViewHolder.superiror_three_three_two_tv_learn.setVisibility(View.INVISIBLE);
            //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

            typeThreeThreeViewHolder.superiror_three_three_two_rlayout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorBean.EventBean eventBean = eventList.get(1);
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",eventBean.getId());
                    context.startActivity(intent);
                }
            });

            SuperiorBean.EventBean eventBean3 = eventList.get(2);
            ImageLoader.getInstance().displayImage(eventBean3.getImg(), typeThreeThreeViewHolder.superiror_three_three_three_iv,
                    MyDisplayOptions.getOptions());

            typeThreeThreeViewHolder.superiror_three_three_three_tv_title.setText(eventBean3.getTitle());
            String tempStrpric3 = eventBean3.getPrice();
            double tempprice3 = Double.parseDouble(tempStrpric3);
            if (tempprice3 == 0.0) {
                typeThreeThreeViewHolder.superiror_three_three_three_tv_talk.setText("免费");
            } else {
                typeThreeThreeViewHolder.superiror_three_three_three_tv_talk.setText("¥ " + tempprice3);
            }
            typeThreeThreeViewHolder.superiror_three_three_three_tv_learn.setVisibility(View.INVISIBLE);
            //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

            typeThreeThreeViewHolder.superiror_three_three_three_rlayout_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuperiorBean.EventBean eventBean = eventList.get(2);
                    Intent intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",eventBean.getId());
                    context.startActivity(intent);
                }
            });


        } else if (Type == TypeFour) {

            //精选课程判断四种情况
            if (jxList.size() > 0) {
                typeFourViewHolder.superiror_four_rlayout_one.setVisibility(View.VISIBLE);
                SuperiorBean.JingxuanBean jxbean = jxList.get(0);
                ImageLoader.getInstance().displayImage(jxbean.getImg(), typeFourViewHolder.superiror_four_iv_one,
                        MyDisplayOptions.getOptions());

                typeFourViewHolder.superiror_four_title_one.setText(jxbean.getTitle());
                String tempStrprice = jxbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFourViewHolder.superiror_four_maintalk_one.setText("免费");
                } else {
                    typeFourViewHolder.superiror_four_maintalk_one.setText("¥ " + tempprice);
                }
                typeFourViewHolder.superiror_four_study_one.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeFourViewHolder.superiror_four_rlayout_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.JingxuanBean jxbean = jxList.get(0);
                        GotoNextActUtils.getInstance().nextActivity(context,jxbean.getId(),jxbean.getType());
                    }
                });

            } else {
                typeFourViewHolder.superiror_four_rlayout_two.setVisibility(View.INVISIBLE);
                typeFourViewHolder.superiror_four_rlayout_three.setVisibility(View.INVISIBLE);
                typeFourViewHolder.superiror_four_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (jxList.size() > 1) {
                typeFourViewHolder.superiror_four_rlayout_one.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_rlayout_two.setVisibility(View.VISIBLE);
                SuperiorBean.JingxuanBean jxbean = jxList.get(1);
                ImageLoader.getInstance().displayImage(jxbean.getImg(), typeFourViewHolder.superiror_four_iv_two,
                        MyDisplayOptions.getOptions());

                typeFourViewHolder.superiror_four_title_two.setText(jxbean.getTitle());
                String tempStrprice = jxbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFourViewHolder.superiror_four_maintalk_two.setText("免费");
                } else {
                    typeFourViewHolder.superiror_four_maintalk_two.setText("¥ " + tempprice);
                }
                typeFourViewHolder.superiror_four_study_two.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeFourViewHolder.superiror_four_rlayout_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.JingxuanBean jxbean = jxList.get(1);
                        GotoNextActUtils.getInstance().nextActivity(context,jxbean.getId(),jxbean.getType());
                    }
                });
            } else {
                typeFourViewHolder.superiror_four_rlayout_three.setVisibility(View.INVISIBLE);
                typeFourViewHolder.superiror_four_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (jxList.size() > 2) {
                typeFourViewHolder.superiror_four_rlayout_one.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_rlayout_two.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_rlayout_three.setVisibility(View.VISIBLE);
                SuperiorBean.JingxuanBean jxbean = jxList.get(2);
                ImageLoader.getInstance().displayImage(jxbean.getImg(), typeFourViewHolder.superiror_four_iv_three,
                        MyDisplayOptions.getOptions());

                typeFourViewHolder.superiror_four_title_three.setText(jxbean.getTitle());
                String tempStrprice = jxbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFourViewHolder.superiror_four_maintalk_three.setText("免费");
                } else {
                    typeFourViewHolder.superiror_four_maintalk_three.setText("¥ " + tempprice);
                }
                typeFourViewHolder.superiror_four_study_three.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeFourViewHolder.superiror_four_rlayout_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.JingxuanBean jxbean = jxList.get(1);
                        GotoNextActUtils.getInstance().nextActivity(context,jxbean.getId(),jxbean.getType());
                    }
                });
            } else {
                typeFourViewHolder.superiror_four_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (jxList.size() > 3) {
                typeFourViewHolder.superiror_four_rlayout_one.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_rlayout_two.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_rlayout_three.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_rlayout_four.setVisibility(View.VISIBLE);

                SuperiorBean.JingxuanBean jxbean = jxList.get(3);
                ImageLoader.getInstance().displayImage(jxbean.getImg(), typeFourViewHolder.superiror_four_iv_four,
                        MyDisplayOptions.getOptions());

                typeFourViewHolder.superiror_four_title_four.setText(jxbean.getTitle());
                String tempStrprice = jxbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFourViewHolder.superiror_four_maintalk_four.setText("免费");
                } else {
                    typeFourViewHolder.superiror_four_maintalk_four.setText("¥ " + tempprice);
                }

                typeFourViewHolder.superiror_four_study_four.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeFourViewHolder.superiror_four_rlayout_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.JingxuanBean jxbean = jxList.get(1);
                        GotoNextActUtils.getInstance().nextActivity(context,jxbean.getId(),jxbean.getType());
                    }
                });
            }

            if (jxList.size() <= 4) {
                typeFourViewHolder.superiror_four_rlayout_title.setClickable(false);
                typeFourViewHolder.superiror_four_iv_more.setVisibility(View.INVISIBLE);
                typeFourViewHolder.superiror_four_tv_more.setVisibility(View.INVISIBLE);

            } else {

                typeFourViewHolder.superiror_four_rlayout_title.setClickable(true);
                typeFourViewHolder.superiror_four_iv_more.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_tv_more.setVisibility(View.VISIBLE);
                typeFourViewHolder.superiror_four_rlayout_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SuperiorDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("jxList", (Serializable) jxList);
                        bundle.putInt("flagg", 1);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }

        } else if (Type == TypeFive) {

            if (mfList.size() > 0) {
                typeFiveViewHolder.superiror_five_rlayout_one.setVisibility(View.VISIBLE);
                SuperiorBean.FreeCourseBean mfbean = mfList.get(0);
                ImageLoader.getInstance().displayImage(mfbean.getImg(), typeFiveViewHolder.superiror_five_iv_one,
                        MyDisplayOptions.getOptions());

                typeFiveViewHolder.superiror_five_tv_title_one.setText(mfbean.getTitle());
                String tempStrprice = mfbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFiveViewHolder.superiror_five_tv_money_one.setText("免费");
                } else {
                    typeFiveViewHolder.superiror_five_tv_money_one.setText("¥ " + tempprice);
                }

                typeFiveViewHolder.superiror_five_rlayout_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.FreeCourseBean mfbean = mfList.get(0);
                        GotoNextActUtils.getInstance().nextActivity(context,mfbean.getId(),mfbean.getType());
                    }
                });
            } else {
                typeFiveViewHolder.superiror_five_rlayout_two.setVisibility(View.INVISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_three.setVisibility(View.INVISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (mfList.size() > 1) {
                typeFiveViewHolder.superiror_five_rlayout_one.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_two.setVisibility(View.VISIBLE);

                SuperiorBean.FreeCourseBean mfbean = mfList.get(1);
                ImageLoader.getInstance().displayImage(mfbean.getImg(), typeFiveViewHolder.superiror_five_iv_two,
                        MyDisplayOptions.getOptions());

                typeFiveViewHolder.superiror_five_tv_title_two.setText(mfbean.getTitle());
                String tempStrprice = mfbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFiveViewHolder.superiror_five_tv_money_two.setText("免费");
                } else {
                    typeFiveViewHolder.superiror_five_tv_money_two.setText("¥ " + tempprice);
                }

                typeFiveViewHolder.superiror_five_rlayout_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.FreeCourseBean mfbean = mfList.get(1);
                        GotoNextActUtils.getInstance().nextActivity(context,mfbean.getId(),mfbean.getType());
                    }
                });
            } else {
                typeFiveViewHolder.superiror_five_rlayout_three.setVisibility(View.INVISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (mfList.size() > 2) {
                typeFiveViewHolder.superiror_five_rlayout_one.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_two.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_three.setVisibility(View.VISIBLE);
                SuperiorBean.FreeCourseBean mfbean = mfList.get(2);
                ImageLoader.getInstance().displayImage(mfbean.getImg(), typeFiveViewHolder.superiror_five_iv_three,
                        MyDisplayOptions.getOptions());

                typeFiveViewHolder.superiror_five_tv_title_three.setText(mfbean.getTitle());
                String tempStrprice = mfbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFiveViewHolder.superiror_five_tv_money_three.setText("免费");
                } else {
                    typeFiveViewHolder.superiror_five_tv_money_three.setText("¥ " + tempprice);
                }

                typeFiveViewHolder.superiror_five_rlayout_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.FreeCourseBean mfbean = mfList.get(2);
                        GotoNextActUtils.getInstance().nextActivity(context,mfbean.getId(),mfbean.getType());
                    }
                });
            } else {
                typeFiveViewHolder.superiror_five_rlayout_four.setVisibility(View.INVISIBLE);
            }


            if (mfList.size() > 3) {
                typeFiveViewHolder.superiror_five_rlayout_one.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_two.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_three.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_four.setVisibility(View.VISIBLE);
                SuperiorBean.FreeCourseBean mfbean = mfList.get(3);
                ImageLoader.getInstance().displayImage(mfbean.getImg(), typeFiveViewHolder.superiror_five_iv_four,
                        MyDisplayOptions.getOptions());

                typeFiveViewHolder.superiror_five_tv_title_four.setText(mfbean.getTitle());
                String tempStrprice = mfbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeFiveViewHolder.superiror_five_tv_money_four.setText("免费");
                } else {
                    typeFiveViewHolder.superiror_five_tv_money_four.setText("¥ " + tempprice);
                }

                typeFiveViewHolder.superiror_five_rlayout_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.FreeCourseBean mfbean = mfList.get(3);
                        GotoNextActUtils.getInstance().nextActivity(context,mfbean.getId(),mfbean.getType());
                    }
                });
            }

            if (mfList.size() <= 4) {
                typeFiveViewHolder.superiror_five_rlayout_title.setClickable(false);
                typeFiveViewHolder.superiror_five_iv_more.setVisibility(View.INVISIBLE);
                typeFiveViewHolder.superiror_five_tv_more.setVisibility(View.INVISIBLE);

            } else {
                typeFiveViewHolder.superiror_five_rlayout_title.setClickable(true);
                typeFiveViewHolder.superiror_five_iv_more.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_tv_more.setVisibility(View.VISIBLE);
                typeFiveViewHolder.superiror_five_rlayout_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SuperiorDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("mfList", (Serializable) mfList);
                        bundle.putInt("flagg", 2);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });

            }
        } else if (Type == TypeSix) {
            if (jdList.size() > 0) {
                typeSixViewHolder.superiror_six_rlayout_one.setVisibility(View.VISIBLE);
                SuperiorBean.TypicalCourseBean jdbean = jdList.get(0);
                ImageLoader.getInstance().displayImage(jdbean.getImg(), typeSixViewHolder.superiror_six_iv_one,
                        MyDisplayOptions.getOptions());

                typeSixViewHolder.superiror_six_title_one.setText(jdbean.getTitle());
                String tempStrprice = jdbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeSixViewHolder.superiror_six_maintalk_one.setText("免费");
                } else {
                    typeSixViewHolder.superiror_six_maintalk_one.setText("¥ " + tempprice);
                }
                typeSixViewHolder.superiror_six_study_one.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeSixViewHolder.superiror_six_rlayout_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.TypicalCourseBean jdbean = jdList.get(0);
                        GotoNextActUtils.getInstance().nextActivity(context,jdbean.getId(),jdbean.getType());
                    }
                });
            } else {
                typeSixViewHolder.superiror_six_rlayout_two.setVisibility(View.INVISIBLE);
                typeSixViewHolder.superiror_six_rlayout_three.setVisibility(View.INVISIBLE);
                typeSixViewHolder.superiror_six_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (jdList.size() > 1) {
                typeSixViewHolder.superiror_six_rlayout_one.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_rlayout_two.setVisibility(View.VISIBLE);
                SuperiorBean.TypicalCourseBean jdbean = jdList.get(1);
                ImageLoader.getInstance().displayImage(jdbean.getImg(), typeSixViewHolder.superiror_six_iv_two,
                        MyDisplayOptions.getOptions());

                typeSixViewHolder.superiror_six_title_two.setText(jdbean.getTitle());
                String tempStrprice = jdbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeSixViewHolder.superiror_six_maintalk_two.setText("免费");
                } else {
                    typeSixViewHolder.superiror_six_maintalk_two.setText("¥ " + tempprice);
                }
                typeSixViewHolder.superiror_six_study_two.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeSixViewHolder.superiror_six_rlayout_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.TypicalCourseBean jdbean = jdList.get(1);
                        GotoNextActUtils.getInstance().nextActivity(context,jdbean.getId(),jdbean.getType());
                    }
                });
            } else {
                typeSixViewHolder.superiror_six_rlayout_three.setVisibility(View.INVISIBLE);
                typeSixViewHolder.superiror_six_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (jdList.size() > 2) {
                typeSixViewHolder.superiror_six_rlayout_one.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_rlayout_two.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_rlayout_three.setVisibility(View.VISIBLE);
                SuperiorBean.TypicalCourseBean jdbean = jdList.get(2);
                ImageLoader.getInstance().displayImage(jdbean.getImg(), typeSixViewHolder.superiror_six_iv_three,
                        MyDisplayOptions.getOptions());

                typeSixViewHolder.superiror_six_title_three.setText(jdbean.getTitle());
                String tempStrprice = jdbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeSixViewHolder.superiror_six_maintalk_three.setText("免费");
                } else {
                    typeSixViewHolder.superiror_six_maintalk_three.setText("¥ " + tempprice);
                }
                typeSixViewHolder.superiror_six_study_three.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeSixViewHolder.superiror_six_rlayout_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.TypicalCourseBean jdbean = jdList.get(2);
                        GotoNextActUtils.getInstance().nextActivity(context,jdbean.getId(),jdbean.getType());
                    }
                });
            } else {
                typeSixViewHolder.superiror_six_rlayout_four.setVisibility(View.INVISIBLE);
            }

            if (jxList.size() > 3) {
                typeSixViewHolder.superiror_six_rlayout_one.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_rlayout_two.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_rlayout_three.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_rlayout_four.setVisibility(View.VISIBLE);

                SuperiorBean.TypicalCourseBean jdbean = jdList.get(3);
                ImageLoader.getInstance().displayImage(jdbean.getImg(), typeSixViewHolder.superiror_six_iv_four,
                        MyDisplayOptions.getOptions());

                typeSixViewHolder.superiror_six_title_four.setText(jdbean.getTitle());
                String tempStrprice = jdbean.getNew_price();
                double tempprice = Double.parseDouble(tempStrprice);
                if (tempprice == 0.0) {
                    typeSixViewHolder.superiror_six_maintalk_four.setText("免费");
                } else {
                    typeSixViewHolder.superiror_six_maintalk_four.setText("¥ " + tempprice);
                }
                typeSixViewHolder.superiror_six_study_four.setVisibility(View.INVISIBLE);
                //typeFourViewHolder.superiror_four_study_one.setText(jxbean.getTotal_learn_nums() + "已学");

                typeSixViewHolder.superiror_six_rlayout_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.TypicalCourseBean jdbean = jdList.get(3);
                        GotoNextActUtils.getInstance().nextActivity(context,jdbean.getId(),jdbean.getType());
                    }
                });
            }

            if (jdList.size() <= 4) {
                typeSixViewHolder.superiror_six_rlayout_title.setClickable(false);
                typeSixViewHolder.superiror_six_iv_more.setVisibility(View.INVISIBLE);
                typeSixViewHolder.superiror_six_tv_more.setVisibility(View.INVISIBLE);

            } else {
                typeSixViewHolder.superiror_six_rlayout_title.setClickable(true);
                typeSixViewHolder.superiror_six_iv_more.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_tv_more.setVisibility(View.VISIBLE);
                typeSixViewHolder.superiror_six_rlayout_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SuperiorDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("jdList", (Serializable) jdList);
                        bundle.putInt("flagg", 3);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }

        } else if (Type == TypeSeven) {

            isShowData(isShow);

            final TypeSevenViewHolder finalTypeSevenViewHolder = typeSevenViewHolder;
            typeSevenViewHolder.superiror_seven_wen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalTypeSevenViewHolder.superiror_seven_wen.setBackgroundResource(R.drawable.button_sharp_main_color_selector);
                    finalTypeSevenViewHolder.superiror_seven_wen.setTextColor(Color.parseColor("#FFFFFF"));
                    finalTypeSevenViewHolder.superiror_seven_video.setBackgroundResource(R.drawable.button_sharp_tran_selector);
                    finalTypeSevenViewHolder.superiror_seven_video.setTextColor(Color.parseColor("#000000"));
                    isShow = true;
                    isShowData(isShow);
                }
            });

            typeSevenViewHolder.superiror_seven_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalTypeSevenViewHolder.superiror_seven_video.setBackgroundResource(R.drawable.button_sharp_main_color_selector);
                    finalTypeSevenViewHolder.superiror_seven_video.setTextColor(Color.parseColor("#FFFFFF"));
                    finalTypeSevenViewHolder.superiror_seven_wen.setBackgroundResource(R.drawable.button_sharp_tran_selector);
                    finalTypeSevenViewHolder.superiror_seven_wen.setTextColor(Color.parseColor("#000000"));
                    isShow = false;
                    isShowData(isShow);
                }
            });

        }

        return convertView;
    }

    private void isShowData(boolean isShow) {
        if (isShow) {
            TypeSevenViewHolder.superiror_seven_llayout_wen.setVisibility(View.VISIBLE);
            TypeSevenViewHolder.superiror_seven_rlayout_video.setVisibility(View.INVISIBLE);
            if (zwList.size() > 0) {
                SuperiorBean.ReadNewsBean zwbean = zwList.get(0);
                TypeSevenViewHolder.rl_seven_one_tv_title.setText(zwbean.getTitle());
                TypeSevenViewHolder.rl_seven_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadNewsBean zwbean = zwList.get(0);
                        Intent intent = new Intent(context, ReadDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("indexs", 10);
                        bundle.putSerializable("zwbean", (Serializable) zwbean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }

            if (zwList.size() > 1) {
                SuperiorBean.ReadNewsBean zwbean = zwList.get(1);
                TypeSevenViewHolder.rl_seven_two_tv_title.setText(zwbean.getTitle());
                TypeSevenViewHolder.rl_seven_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadNewsBean zwbean = zwList.get(1);
                        Intent intent = new Intent(context, ReadDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("indexs", 10);
                        bundle.putSerializable("zwbean", (Serializable) zwbean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }

            if (zwList.size() > 2) {
                SuperiorBean.ReadNewsBean zwbean = zwList.get(2);
                TypeSevenViewHolder.rl_seven_three_tv_title.setText(zwbean.getTitle());
                TypeSevenViewHolder.rl_seven_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadNewsBean zwbean = zwList.get(2);
                        Intent intent = new Intent(context, ReadDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("indexs", 10);
                        bundle.putSerializable("zwbean", (Serializable) zwbean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }

            if (zwList.size() > 3) {
                SuperiorBean.ReadNewsBean zwbean = zwList.get(3);
                TypeSevenViewHolder.rl_seven_four_tv_title.setText(zwbean.getTitle());
                TypeSevenViewHolder.rl_seven_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadNewsBean zwbean = zwList.get(3);
                        Intent intent = new Intent(context, ReadDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("indexs", 10);
                        bundle.putSerializable("zwbean", (Serializable) zwbean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }

            if (zwList.size() > 4) {
                SuperiorBean.ReadNewsBean zwbean = zwList.get(4);
                TypeSevenViewHolder.rl_seven_five_tv_title.setText(zwbean.getTitle());
                TypeSevenViewHolder.rl_seven_five.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadNewsBean zwbean = zwList.get(4);
                        Intent intent = new Intent(context, ReadDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("indexs", 10);
                        bundle.putSerializable("zwbean", (Serializable) zwbean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
            if (zwList.size() > 5) {
                SuperiorBean.ReadNewsBean zwbean = zwList.get(5);
                TypeSevenViewHolder.rl_seven_six_tv_title.setText(zwbean.getTitle());
                TypeSevenViewHolder.rl_seven_six.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadNewsBean zwbean = zwList.get(5);
                        Intent intent = new Intent(context, ReadDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("indexs", 10);
                        bundle.putSerializable("zwbean", (Serializable) zwbean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }

            TypeSevenViewHolder.rl_seven_seven_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 100);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        } else {
            TypeSevenViewHolder.superiror_seven_llayout_wen.setVisibility(View.INVISIBLE);
            TypeSevenViewHolder.superiror_seven_rlayout_video.setVisibility(View.VISIBLE);
            if (zsList.size() > 0) {
                SuperiorBean.ReadVideoBean zsbean = zsList.get(0);
                ImageLoader.getInstance().displayImage(zsbean.getImg(), TypeSevenViewHolder.superiror_seven_iv_one,
                        MyDisplayOptions.getOptions());

                TypeSevenViewHolder.superiror_seven_title_one.setText(zsbean.getTitle());

                TypeSevenViewHolder.superiror_seven_rlayout_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadVideoBean zsbean = zsList.get(0);
                        GotoNextActUtils.getInstance().nextActivity(context,zsbean.getId(),zsbean.getType());
                    }
                });
            }

            if (zsList.size() > 1) {
                SuperiorBean.ReadVideoBean zsbean = zsList.get(1);
                ImageLoader.getInstance().displayImage(zsbean.getImg(), TypeSevenViewHolder.superiror_seven_iv_two,
                        MyDisplayOptions.getOptions());

                TypeSevenViewHolder.superiror_seven_title_two.setText(zsbean.getTitle());

                TypeSevenViewHolder.superiror_seven_rlayout_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadVideoBean zsbean = zsList.get(1);
                        GotoNextActUtils.getInstance().nextActivity(context,zsbean.getId(),zsbean.getType());
                    }
                });
            }

            if (zsList.size() > 2) {
                SuperiorBean.ReadVideoBean zsbean = zsList.get(2);
                ImageLoader.getInstance().displayImage(zsbean.getImg(), TypeSevenViewHolder.superiror_seven_iv_three,
                        MyDisplayOptions.getOptions());

                TypeSevenViewHolder.superiror_seven_title_three.setText(zsbean.getTitle());

                TypeSevenViewHolder.superiror_seven_rlayout_three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadVideoBean zsbean = zsList.get(2);
                        GotoNextActUtils.getInstance().nextActivity(context,zsbean.getId(),zsbean.getType());
                    }
                });
            }

            if (zsList.size() > 3) {
                SuperiorBean.ReadVideoBean zsbean = zsList.get(3);
                ImageLoader.getInstance().displayImage(zsbean.getImg(), TypeSevenViewHolder.superiror_seven_iv_four,
                        MyDisplayOptions.getOptions());

                TypeSevenViewHolder.superiror_seven_title_four.setText(zsbean.getTitle());

                TypeSevenViewHolder.superiror_seven_rlayout_four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SuperiorBean.ReadVideoBean zsbean = zsList.get(3);
                        GotoNextActUtils.getInstance().nextActivity(context,zsbean.getId(),zsbean.getType());
                    }
                });
            }

            TypeSevenViewHolder.rl_seven_four_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CouseInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("couseflag", 100);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }


    /**
     * 第一个item
     */
    private class TypeOneViewHolder {
        private RelativeLayout superiror_one_llayout;
        private RelativeLayout superiror_one_rlayout_flag_one;
        private RelativeLayout superiror_one_rlayout_flag_two;
        private RelativeLayout superiror_one_rlayout_flag_three;
        private RelativeLayout superiror_one_rlayout_flag_four;
        private RelativeLayout superiror_one_rlayout_flag_five;
    }

    /**
     * 第二个item
     */
    private class TypeTwoViewHolder {
        private ViewFlipper marquee_view;
    }

    /**
     * 第三个item 有一个数据时
     */
    private class TypeThreeOneViewHolder {
        private RelativeLayout superiror_three_one_rlayout_title; //标题父类
        private RelativeLayout superiror_three_one_rlayout_content; //内容父类
        private ImageView superiror_three_one_iv;//图片
        private TextView superiror_three_one_tv_location;//位置
        private TextView superiror_three_one_tv_date;//时间
        private TextView superiror_three_one_tv_money;//金额

    }

    /**
     * 第三个item 有两个数据时
     */
    private class TypeThreeTwoViewHolder {
        private RelativeLayout superiror_three_two_rlayout_title; //标题父类
        private ImageView superiror_three_two_rlayout_title_iv_more; //标题父类
        private TextView superiror_three_two_rlayout_title_tv_more; //标题父类


        //第一张图片
        private RelativeLayout superiror_three_two_one_rlayout_content; //内容父类
        private ImageView superiror_three_two_one_iv;//图片
        private TextView superiror_three_two_one_tv_location;//位置
        private TextView superiror_three_two_one_tv_date;//时间
        private TextView superiror_three_two_one_tv_mtwoy;//金额

        //第二张图片
        private RelativeLayout superiror_three_two_two_rlayout_content; //内容父类
        private ImageView superiror_three_two_two_iv;//图片
        private TextView superiror_three_two_two_tv_location;//位置
        private TextView superiror_three_two_two_tv_date;//时间
        private TextView superiror_three_two_two_tv_mtwoy;//金额
    }

    /**
     * 第三个item 有三个数据时
     */
    private class TypeThreeThreeViewHolder {

        private RelativeLayout superiror_three_three_rlayout_title;
        private ImageView superiror_three_three_rlayout_title_iv_more;
        private TextView superiror_three_three_rlayout_title_tv_more;

        private RelativeLayout superiror_three_three_one_rlayout_content;
        private ImageView superiror_three_three_one_iv;
        private TextView superiror_three_three_one_tv_location;
        private TextView superiror_three_three_one_tv_date;
        private TextView superiror_three_three_one_tv_mthreey;

        private RelativeLayout superiror_three_three_two_rlayout_content;
        private ImageView superiror_three_three_two_iv;
        private TextView superiror_three_three_two_tv_title;
        private TextView superiror_three_three_two_tv_talk;
        private TextView superiror_three_three_two_tv_learn;


        private RelativeLayout superiror_three_three_three_rlayout_content;
        private ImageView superiror_three_three_three_iv;
        private TextView superiror_three_three_three_tv_title;
        private TextView superiror_three_three_three_tv_talk;
        private TextView superiror_three_three_three_tv_learn;
    }

    /**
     * 第四个item
     */
    private class TypeFourViewHolder {
        private RelativeLayout superiror_four_rlayout_title;
        private ImageView superiror_four_iv_more;
        private TextView superiror_four_tv_more;

        private RelativeLayout superiror_four_rlayout_one;
        private ImageView superiror_four_iv_one;
        private TextView superiror_four_title_one;
        private TextView superiror_four_maintalk_one;
        private TextView superiror_four_study_one;

        private RelativeLayout superiror_four_rlayout_two;
        private ImageView superiror_four_iv_two;
        private TextView superiror_four_title_two;
        private TextView superiror_four_maintalk_two;
        private TextView superiror_four_study_two;


        private RelativeLayout superiror_four_rlayout_three;
        private ImageView superiror_four_iv_three;
        private TextView superiror_four_title_three;
        private TextView superiror_four_maintalk_three;
        private TextView superiror_four_study_three;

        private RelativeLayout superiror_four_rlayout_four;
        private ImageView superiror_four_iv_four;
        private TextView superiror_four_title_four;
        private TextView superiror_four_maintalk_four;
        private TextView superiror_four_study_four;

    }

    /**
     * 第五个item
     */
    private class TypeFiveViewHolder {
        private RelativeLayout superiror_five_rlayout_title; //标题父类
        private ImageView superiror_five_iv_more;
        private TextView superiror_five_tv_more;

        private RelativeLayout superiror_five_rlayout_one; //内容父类
        private ImageView superiror_five_iv_one;//图片
        private TextView superiror_five_tv_title_one;//标题
        private TextView superiror_five_tv_money_one;//金额

        private RelativeLayout superiror_five_rlayout_two; //内容父类
        private ImageView superiror_five_iv_two;//图片
        private TextView superiror_five_tv_title_two;//标题
        private TextView superiror_five_tv_money_two;//金额

        private RelativeLayout superiror_five_rlayout_three; //内容父类
        private ImageView superiror_five_iv_three;//图片
        private TextView superiror_five_tv_title_three;//标题
        private TextView superiror_five_tv_money_three;//金额

        private RelativeLayout superiror_five_rlayout_four; //内容父类
        private ImageView superiror_five_iv_four;//图片
        private TextView superiror_five_tv_title_four;//标题
        private TextView superiror_five_tv_money_four;//金额

    }

    /**
     * 第六个item
     */
    private class TypeSixViewHolder {
        private RelativeLayout superiror_six_rlayout_title;
        private ImageView superiror_six_iv_more;
        private TextView superiror_six_tv_more;

        private RelativeLayout superiror_six_rlayout_one;
        private ImageView superiror_six_iv_one;
        private TextView superiror_six_title_one;
        private TextView superiror_six_maintalk_one;
        private TextView superiror_six_study_one;

        private RelativeLayout superiror_six_rlayout_two;
        private ImageView superiror_six_iv_two;
        private TextView superiror_six_title_two;
        private TextView superiror_six_maintalk_two;
        private TextView superiror_six_study_two;


        private RelativeLayout superiror_six_rlayout_three;
        private ImageView superiror_six_iv_three;
        private TextView superiror_six_title_three;
        private TextView superiror_six_maintalk_three;
        private TextView superiror_six_study_three;

        private RelativeLayout superiror_six_rlayout_four;
        private ImageView superiror_six_iv_four;
        private TextView superiror_six_title_four;
        private TextView superiror_six_maintalk_four;
        private TextView superiror_six_study_four;

    }

    /**
     * 第七个item
     */
    public static class TypeSevenViewHolder {
        private RelativeLayout superiror_seven_rlayout_title;
        private Button superiror_seven_wen;
        private Button superiror_seven_video;

        //------------------看文章--------------------
        private static LinearLayout superiror_seven_llayout_wen;
        private static RelativeLayout rl_seven_one;
        private static TextView rl_seven_one_tv_title;
        private static RelativeLayout rl_seven_two;
        private static TextView rl_seven_two_tv_title;
        private static RelativeLayout rl_seven_three;
        private static TextView rl_seven_three_tv_title;
        private static RelativeLayout rl_seven_four;
        private static TextView rl_seven_four_tv_title;
        private static RelativeLayout rl_seven_five;
        private static TextView rl_seven_five_tv_title;
        private static RelativeLayout rl_seven_six;
        private static TextView rl_seven_six_tv_title;
        private static Button rl_seven_seven_btn;

        //------------------看视频--------------------
        private static RelativeLayout superiror_seven_rlayout_video;
        private static RelativeLayout superiror_seven_rlayout_one;
        private static ImageView superiror_seven_iv_one;
        private static TextView superiror_seven_title_one;

        private static RelativeLayout superiror_seven_rlayout_two;
        private static ImageView superiror_seven_iv_two;
        private static TextView superiror_seven_title_two;

        private static RelativeLayout superiror_seven_rlayout_three;
        private static ImageView superiror_seven_iv_three;
        private static TextView superiror_seven_title_three;

        private static RelativeLayout superiror_seven_rlayout_four;
        private static ImageView superiror_seven_iv_four;
        private static TextView superiror_seven_title_four;
        private static Button rl_seven_four_btn;
    }
}
