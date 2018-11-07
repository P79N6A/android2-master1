package com.wh.wang.scroopclassproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.ReadDetailActivity;
import com.wh.wang.scroopclassproject.activity.VideoInfosActivity;
import com.wh.wang.scroopclassproject.adapter.JXCAdapter;
import com.wh.wang.scroopclassproject.adapter.MyPagerAdapter;
import com.wh.wang.scroopclassproject.base.BaseFragment;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.bean.JXKBean;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReconnectionEntity;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReplacePageEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/11/21.
 */

public class JXKFragment extends BaseFragment implements View.OnClickListener {

    private View mheaderView;
    private View mfooterView;
    private ListView jxk_listview;
    private ViewPager jxk_viewpager;
    private RelativeLayout mReconnectionLayout;
    private ImageView mReconnectionGif;
    private TextView mReconnection;

    private List<JXKBean.ScrollBean> scrollList;  //轮播图
    private List<JXKBean.EventsBean> eventList;  //报名
    private List<JXKBean.ElaborateCourseBean> jxkcList;  //课程父类
    private List<JXKBean.ElaborateCourseBean.CourseDetailBean> courseDetailList;  //课程详情
    // 记录当前的页数
    private int mCount = 0;
    // 开始
    public static final int START = -1;
    // 停止
    public static final int STOP = -2;
    // 更新
    public static final int UPDATE = -3;
    // 接受传过来的当前页面数
    public static final int RECORD = -4;
    private List<ImageView> mList;
    private MyPagerAdapter mAdapter;

    private Handler vhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case START:
                    vhandler.sendEmptyMessageDelayed(UPDATE, 3000);
                    break;
                case STOP:
                    vhandler.removeMessages(UPDATE);
                    break;
                case UPDATE:
                    mCount++;
                    jxk_viewpager.setCurrentItem(mCount);
                    break;
                case RECORD:
                    mCount = msg.arg1;
                    break;

                default:
                    break;
            }

        }
    };
    private RelativeLayout type_three;

    @Override
    public View initView(LayoutInflater inflater) {
        EventBus.getDefault().register(this);
        //添加页面布局
        View view = inflater.inflate(R.layout.fragment_jxk, null);
        jxk_listview = (ListView) view.findViewById(R.id.jxk_listview);
        mReconnectionLayout = (RelativeLayout) view.findViewById(R.id.reconnection_layout);
        mReconnectionGif = (ImageView) view.findViewById(R.id.reconnection_gif);
        mReconnection = (TextView) view.findViewById(R.id.reconnection);

        Glide.with(MyApplication.mApplication).load(R.drawable.nosignal).asGif().into(mReconnectionGif);

        mheaderView = LayoutInflater.from(mContext).inflate(R.layout.setsuperiorheader, null);
        jxk_viewpager = (ViewPager) mheaderView.findViewById(R.id.jxk_viewpager);
        mfooterView = LayoutInflater.from(mContext).inflate(R.layout.jxk_typethree, null);
        type_three = (RelativeLayout) mfooterView.findViewById(R.id.type_three);
        jxk_listview.addHeaderView(mheaderView);
        jxk_listview.addFooterView(mfooterView);
        // 显示第一个位置
        jxk_listview.setSelection(0);
        setInitListener();
        return view;
    }

    private void setInitListener() {
        mReconnection.setOnClickListener(this);
        type_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MoreCourseActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("flag","1");
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
                EventBus.getDefault().post(new ReplacePageEntity(1));
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getJXKDataFromNet();
    }

    //初始化精选课程的数据
    private void getJXKDataFromNet() {
        RequestParams params = new RequestParams(Constants.superiorUrl4);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mReconnectionLayout.setVisibility(View.GONE);
                Log.e("whwh", "JXKDataFromNet---联网成功---result===" + result);
                //主线程
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "gJXKDataFromNet---联网失败---" + ex.getMessage());
                mReconnectionLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "JXKDataFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "JXKDataFromNet---onFinished---");
            }
        });
    }

    private void processData(String result) {
        scrollList = parseJsonScorll(result);
        eventList = parseJsonEvent(result);
        jxkcList = parseJsonJXKC(result);
        setViewPager();
        setJXKAdapter();
    }

    private void setJXKAdapter() {
        JXCAdapter jxkAdapter = new JXCAdapter(mContext, eventList, jxkcList);
        jxk_listview.setAdapter(jxkAdapter);
    }

    private void setViewPager() {
        initViewPager();
        setListener();
        mAdapter = new MyPagerAdapter(mList);
        jxk_viewpager.setAdapter(mAdapter);
        int i = Integer.MAX_VALUE / 2 % mList.size();
        // 默认在中间，让用户看不到边界
        jxk_viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - i);
        vhandler.sendEmptyMessage(START);
    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mList = new ArrayList<ImageView>();
        ImageView imageView;
        for (int i = 0; i < scrollList.size(); i++) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            // 使用的ImageLoader网络加载图片，需先在Application和清单文件中配置在使用

            ImageLoader.getInstance().displayImage(scrollList.get(i).getImg(), imageView, MyDisplayOptions.getOptions());
            mList.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("whwh", mCount % scrollList.size() + "");
                    JXKBean.ScrollBean scrollBean = scrollList.get(mCount % scrollList.size());
                    int isClick = Integer.parseInt(scrollBean.getIsclick());
                    if (isClick == 1) {
                        int isType = Integer.parseInt(scrollBean.getType());
                        if (isType == 3) {  //活动
                            MobclickAgent.onEvent(mContext,"indexshox");
                            Intent intent = new Intent(mContext, NewEventDetailsActivity.class);
                            intent.putExtra("event_id",scrollBean.getId());
                            mContext.startActivity(intent);
                        } else if (isType == 1) { //视频
                            Intent intent = new Intent(mContext, VideoInfosActivity.class);
                            intent.putExtra("id",scrollBean.getId());
                            intent.putExtra("type",scrollBean.getType());
                            mContext.startActivity(intent);
//                            Intent intent = new Intent(mContext, VideoInfosActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("index", 9);
//                            MobclickAgent.onEvent(mContext,"indexshox");
//                            bundle.putSerializable("id", (Serializable) scrollBean.getId());
//                            intent.putExtras(bundle);
//                            mContext.startActivity(intent);

                        } else if (isType == 2) { //阅读
                            Intent intent = new Intent(mContext, ReadDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("indexs", 7);
                            MobclickAgent.onEvent(mContext,"indexshox");
                            bundle.putSerializable("bannerBean", (Serializable) scrollBean);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }else if(isType==4){
                            Intent intent = new Intent(mContext, SumUpActivity.class);
                            intent.putExtra("id",scrollBean.getId());
                            intent.putExtra("type",scrollBean.getType());
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    private void setListener() {
        jxk_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

                vhandler.sendMessage(Message.obtain(vhandler, RECORD, arg0, 0));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

                switch (arg0) {
                    // 当滑动时让当前轮播停止
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        vhandler.sendEmptyMessage(STOP);
                        break;
                    // 滑动停止时继续轮播
                    case ViewPager.SCROLL_STATE_IDLE:
                        vhandler.sendEmptyMessage(START);
                        break;
                }
            }
        });
    }

    /**
     * 解析轮播图数据
     */
    private List<JXKBean.ScrollBean> parseJsonScorll(String result) {

        try {

            JSONObject scrollObject = new JSONObject(result);
            JSONArray scrollArray = scrollObject.optJSONArray("scroll");
            scrollList = new ArrayList<>();
            if (scrollArray != null && scrollArray.length() > 0) {
                for (int i = 0; i < scrollArray.length(); i++) {
                    JSONObject scrollObj = scrollArray.optJSONObject(i);
                    if (scrollObj != null) {
                        String scroll_backcolor = scrollObj.optString("backcolor");
                        String scroll_id = scrollObj.optString("id");
                        String scroll_img = scrollObj.optString("img");
                        String scroll_isclick = scrollObj.optString("isclick");
                        String scroll_product_id = scrollObj.optString("product_id");
                        String scroll_title = scrollObj.optString("title");
                        String scroll_type = scrollObj.optString("type");
                        String scroll_x = scrollObj.optString("x");
                        JXKBean.ScrollBean scrollBean = new JXKBean.ScrollBean();
                        scrollBean.setBackcolor(scroll_backcolor);
                        scrollBean.setId(scroll_id);
                        scrollBean.setImg(scroll_img);
                        scrollBean.setIsclick(scroll_isclick);
                        scrollBean.setProduct_id(scroll_product_id);
                        scrollBean.setTitle(scroll_title);
                        scrollBean.setType(scroll_type);
                        scrollBean.setX(scroll_x);
                        scrollList.add(scrollBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return scrollList;
    }

    /**
     * 解析报名的数据
     */
    private List<JXKBean.EventsBean> parseJsonEvent(String result) {

        try {

            JSONObject eventObject = new JSONObject(result);
            JSONArray eventArray = eventObject.optJSONArray("events");
            eventList = new ArrayList<>();
            if (eventArray != null && eventArray.length() > 0) {
                for (int i = 0; i < eventArray.length(); i++) {
                    JSONObject eventObj = eventArray.optJSONObject(i);
                    if (eventObj != null) {
                        String event_id = eventObj.optString("id");
                        String event_img = eventObj.optString("img");
                        String event_price = eventObj.optString("price");
                        String event_title = eventObj.optString("title");
                        String event_vip_price = eventObj.optString("vip_price");
                        JXKBean.EventsBean eventBean = new JXKBean.EventsBean();
                        eventBean.setId(event_id);
                        eventBean.setImg(event_img);
                        eventBean.setPrice(event_price);
                        eventBean.setTitle(event_title);
                        eventBean.setVip_price(event_vip_price);
                        eventList.add(eventBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    /**
     * 解析精选课程的数据
     */
    private List<JXKBean.ElaborateCourseBean> parseJsonJXKC(String result) {

        try {

            JSONObject courseObj = new JSONObject(result).getJSONObject("course");
            JSONArray elaborateCourseArray = courseObj.optJSONArray("elaborateCourse");
            jxkcList = new ArrayList<>();
            if (elaborateCourseArray != null && elaborateCourseArray.length() > 0) {
                for (int i = 0; i < elaborateCourseArray.length(); i++) {
                    JSONObject elaborateObj = elaborateCourseArray.optJSONObject(i);
                    if (elaborateObj != null) {
                        JSONArray course_detailArray = elaborateObj.optJSONArray("course_detail");
                        courseDetailList = new ArrayList<>();
                        if (course_detailArray != null && course_detailArray.length() > 0) {
                            for (int j = 0; j < course_detailArray.length(); j++) {
                                JSONObject course_detailObj = course_detailArray.optJSONObject(j);
                                if (course_detailObj != null) {
                                    String course_detail_id = course_detailObj.optString("id");
                                    String course_detail_img = course_detailObj.optString("img");
                                    String course_detail_new_price = course_detailObj.optString("new_price");
                                    String course_teacher_id = course_detailObj.optString("teacher_id");
                                    String course_teacher_name = course_detailObj.optString("teacher_name");
                                    String course_detail_title = course_detailObj.optString("title");
                                    String type = course_detailObj.optString("type");
                                    JXKBean.ElaborateCourseBean.CourseDetailBean courseDetailBean = new JXKBean.ElaborateCourseBean.CourseDetailBean();
                                    courseDetailBean.setId(course_detail_id);
                                    courseDetailBean.setImg(course_detail_img);
                                    courseDetailBean.setNew_price(course_detail_new_price);
                                    courseDetailBean.setTeacher_id(course_teacher_id);
                                    courseDetailBean.setTeacher_name(course_teacher_name);
                                    courseDetailBean.setTitle(course_detail_title);
                                    courseDetailBean.setType(type);
                                    courseDetailList.add(courseDetailBean);
                                }
                            }
                        }

                        String elaborate_id = elaborateObj.optString("id");
                        String elaborate_ifon = elaborateObj.optString("ifon");
                        String elaborate_name = elaborateObj.optString("name");
                        String elaborate_pai = elaborateObj.optString("pai");
                        String elaborate_parent_id = elaborateObj.optString("parent_id");
                        JXKBean.ElaborateCourseBean elaboraBean = new JXKBean.ElaborateCourseBean();
                        elaboraBean.setCourse_detail(courseDetailList);
                        elaboraBean.setId(elaborate_id);
                        elaboraBean.setIfon(elaborate_ifon);
                        elaboraBean.setName(elaborate_name);
                        elaboraBean.setPai(elaborate_pai);
                        elaboraBean.setParent_id(elaborate_parent_id);
                        jxkcList.add(elaboraBean);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jxkcList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reconnection(ReconnectionEntity entity){
        Log.e("DH_Reconnection","jx 重连数据");
        getJXKDataFromNet();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reconnection:
                Toast.makeText(mContext, "重新连接中", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new ReconnectionEntity(true));
                break;
        }
    }
}
