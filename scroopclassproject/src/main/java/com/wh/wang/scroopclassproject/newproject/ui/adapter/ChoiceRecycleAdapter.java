package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReplacePageEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MainCoursePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.MoreCourseActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.GridSpacingItemDecoration;
import com.wh.wang.scroopclassproject.newproject.utils.MainSpaceItemDecoration;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/12/25.
 */

public class ChoiceRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private MainCourseEntity mMainCourseEntity;
    private LayoutInflater mLayoutInflater;
    private Activity mContext;
    String user_id ;
    public ChoiceRecycleAdapter(Activity context,MainCourseEntity mMainCourseEntity) {
        this.mMainCourseEntity = mMainCourseEntity;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
        mContext = context;
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new CarouselViewHolder(mLayoutInflater.inflate(R.layout.item_choiceness_vp,parent,false));
            case 2:
                return new AllViewHolder(mLayoutInflater.inflate(R.layout.item_choiceness_all,parent,false));
            case 1:
            case 3:
                return new CourseViewHolder(mLayoutInflater.inflate(R.layout.item_choiceness_course,parent,false),viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder!=null){
            if(holder instanceof CarouselViewHolder){
                final CarouselViewHolder carouselViewHolder = (CarouselViewHolder) holder;
                String indexShow = mMainCourseEntity.getIndexshow();
                int location = 0;
                if("1".equals(indexShow)){
                    carouselViewHolder.mMainHint.setVisibility(View.VISIBLE);
                    carouselViewHolder.mHintText.setText(R.string.order_main_hint);
                    location = 0;
                }else if("2".equals(indexShow)){
                    carouselViewHolder.mMainHint.setVisibility(View.VISIBLE);
                    carouselViewHolder.mHintText.setText(R.string.record_main_hint);
                    location = 1;
                }else{
                    carouselViewHolder.mMainHint.setVisibility(View.GONE);
                }


                final int finalLocation = location;
                carouselViewHolder.mMainHint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hintNotification();
                        carouselViewHolder.mMainHint.setVisibility(View.GONE);
                        EventBus.getDefault().post(new ReplacePageEntity(2, finalLocation));
                    }
                });
                carouselViewHolder.mCarousel.setAdapter(new CarouselAdapter(mMainCourseEntity.getScroll(),mContext));
                mCurrentPos = Integer.MAX_VALUE / 2-Integer.MAX_VALUE / 2 % mMainCourseEntity.getScroll().size();
                carouselViewHolder.mCarousel.setCurrentItem(mCurrentPos);
                ((CarouselViewHolder) holder).mHandler.sendEmptyMessage(START);
                carouselViewHolder.mCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        ((CarouselViewHolder) holder).mHandler
                                .sendMessage(Message.obtain(((CarouselViewHolder) holder).mHandler, RECORD, position, 0));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        switch (state) {
                            // 当滑动时让当前轮播停止
                            case ViewPager.SCROLL_STATE_DRAGGING:
                                ((CarouselViewHolder) holder).mHandler.sendEmptyMessage(STOP);
                                break;
                            // 滑动停止时继续轮播
                            case ViewPager.SCROLL_STATE_IDLE:
                                ((CarouselViewHolder) holder).mHandler.sendEmptyMessage(START);
                                break;
                        }
                    }
                });
            }else if(holder instanceof AllViewHolder){
                AllViewHolder allViewHolder = (AllViewHolder) holder;
                allViewHolder.mItemAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new ReplacePageEntity(1));
                    }
                });
            }else{
                CourseViewHolder courseViewHolder = (CourseViewHolder) holder;
                if(position==1){
                    courseViewHolder.mItemMore.setVisibility(View.GONE);
                    courseViewHolder.mItemTitleName.setText("火热报名中");
                    courseViewHolder.mItemCourseList.setAdapter(new ChoicenessEventAdapter(mContext,mMainCourseEntity.getEvents(),0));
                }else{
                    final MainCourseEntity.CourseBean.ElaborateCourseBean elaborateCourseBean
                            = mMainCourseEntity.getCourse().getElaborateCourse().get(position - 2);
                    courseViewHolder.mItemMore.setVisibility(View.VISIBLE);
                    courseViewHolder.mItemMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, MoreCourseActivity.class);
                            int id = Integer.parseInt(elaborateCourseBean.getId());
                            intent.putExtra("flag","0");
                            intent.putExtra("jxid",id);
                            intent.putExtra("jxname", elaborateCourseBean.getName());
                            mContext.startActivity(intent);
                        }
                    });
                    courseViewHolder.mItemTitleName.setText(elaborateCourseBean.getName());
                    courseViewHolder.mItemCourseList.setAdapter(new ElaborateCourseAdapter(mContext,elaborateCourseBean.getCourse_detail()));
                }


            }
        }
    }

    private void hintNotification() {
        new MainCoursePresenter().mainHint(user_id, "0", "0", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {

            }

            @Override
            public void onFault(String error) {
                Log.e("DH",error);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return 0;
            case 1:
                return 1;
            case 6:
                return 2;
            default:
                return 3;
        }
    }

    class CarouselViewHolder extends RecyclerView.ViewHolder{
        private ViewPager mCarousel;
        private double rate = 9/(5.0);
        private RelativeLayout mMainHint;
        private ImageView mHintClose;
        private TextView mHintText;


        private Handler mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case START:
                        mHandler.removeMessages(UPDATE);
                        mHandler.sendEmptyMessageDelayed(UPDATE, 3000);
                        break;
                    case STOP:
                        mHandler.removeMessages(UPDATE);
                        break;
                    case UPDATE:
                        Log.e("DH_HANLDER",""+mCurrentPos);
                        mCurrentPos++;
                        mCarousel.setCurrentItem(mCurrentPos);
                        break;
                    case RECORD:
                        mCurrentPos = msg.arg1;
                        break;
                    default:
                        break;
                }

            }
        };
        public CarouselViewHolder(View itemView) {
            super(itemView);
            mCarousel = (ViewPager) itemView.findViewById(R.id.carousel);
            mMainHint = (RelativeLayout) itemView.findViewById(R.id.main_hint);
            mHintClose = (ImageView) itemView.findViewById(R.id.hint_close);
            mHintText = (TextView) itemView.findViewById(R.id.hint_text);

            DisplayMetrics d = new DisplayMetrics();
            mContext.getWindowManager().getDefaultDisplay().getMetrics(d);
            int mPhoneW = d.widthPixels;
            ViewGroup.LayoutParams layoutParams = mCarousel.getLayoutParams();
            layoutParams.height = (int) (mPhoneW/rate);
            mCarousel.setLayoutParams(layoutParams);

            mHintClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hintNotification();
                    mMainHint.setVisibility(View.GONE);
                }
            });
        }
    }

    class AllViewHolder extends RecyclerView.ViewHolder{
        private TextView mItemAll;

        public AllViewHolder(View itemView) {
            super(itemView);
            mItemAll = (TextView) itemView.findViewById(R.id.item_all);
        }
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private TextView mItemTitleName;
        private RecyclerView mItemCourseList;
        private TextView mItemMore;

        public CourseViewHolder(View itemView,int orientation) {
            super(itemView);
            mItemTitleName = (TextView) itemView.findViewById(R.id.item_title_name);
            mItemCourseList = (RecyclerView) itemView.findViewById(R.id.item_course_list);
            mItemMore = (TextView) itemView.findViewById(R.id.item_more);
            if(orientation==1){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.mApplication);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mItemCourseList.setLayoutManager(linearLayoutManager);
                mItemCourseList.addItemDecoration(new MainSpaceItemDecoration(DisplayUtil.dip2px(MyApplication.mApplication,10)));
            }else{
                mItemCourseList.setLayoutManager(new GridLayoutManager(MyApplication.mApplication,2));
                mItemCourseList.addItemDecoration(new GridSpacingItemDecoration(2, DisplayUtil.dip2px(MyApplication.mApplication,10), true));
            }
        }
    }

    class MainHintViewHolder extends RecyclerView.ViewHolder{
        public View mainHintView;
        private ImageView mHintClose;


        public MainHintViewHolder(View itemView) {
            super(itemView);
            mainHintView = itemView;
            mainHintView.setVisibility(View.GONE);
            mHintClose = (ImageView) itemView.findViewById(R.id.hint_close);

        }
    }

    private int mCurrentPos;
    // 开始
    public static final int START = -1;
    // 停止
    public static final int STOP = -2;
    // 更新
    public static final int UPDATE = -3;
    // 接受传过来的当前页面数
    public static final int RECORD = -4;

}
