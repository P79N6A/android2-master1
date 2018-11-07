package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.TableActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ReplacePageEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.NewHomeEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.AllEventsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEssayMoreActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewMoreActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.GridSpacingItemDecoration;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;
import com.wh.wang.scroopclassproject.newproject.utils.ViewPageUtils;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dh on 2018/6/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private NewHomeEntity mNewHomeEntity;
    private String[] mIds = {"8", "31", "10", "30", "9"};//五力id
    private List<RadioButton> mRbs;

    public HomeAdapter(Context context, NewHomeEntity newHomeEntity) {
        mContext = context;
        mNewHomeEntity = newHomeEntity;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    private final int WHEEL = 0;
    private final int WU_LI = 1;
    private final int ACTION = 2;
    private final int ESSAY = 3;
    private final int VIDEO_V = 4;
    private final int VIDEO_H = 5;

    private int mCurrentPos;
    // 开始
    private static final int START = -1;
    // 停止
    private static final int STOP = -2;
    // 更新
    private static final int UPDATE = -3;
    // 接受传过来的当前页面数
    private static final int RECORD = -4;

    //
//    public int getAppSatus(Context context, String pageName) {
//
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);
//
//        //判断程序是否在栈顶
//        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
//            return 1;
//        } else {
//            //判断程序是否在栈里
//            for (ActivityManager.RunningTaskInfo info : list) {
//                if (info.topActivity.getPackageName().equals(pageName)) {
//                    return 2;
//                }
//            }
//            return 3;//栈里找不到，返回3
//        }
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case WHEEL:
                return new WheelViewHolder(mLayoutInflater.inflate(R.layout.item_home_wheel, parent, false));
            case WU_LI:
                return new WuLiViewHolder(mLayoutInflater.inflate(R.layout.item_system_wudali, parent, false));
            case ESSAY:
                return new EssayViewHolder(mLayoutInflater.inflate(R.layout.item_home_essay, parent, false));
            case VIDEO_V:
            case VIDEO_H:
                return new VideoViewHolder(mLayoutInflater.inflate(R.layout.item_home_video, parent, false), viewType);
            case ACTION:
                return new OfflineViewHolder(mLayoutInflater.inflate(R.layout.item_home_action, parent, false));

        }
        return new WuLiViewHolder(mLayoutInflater.inflate(R.layout.item_system_wudali, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final int index = position - 2;
        if (holder != null) {
            if (holder instanceof WheelViewHolder) {
                final WheelViewHolder wheelViewHolder = (WheelViewHolder) holder;
                mRbs = new ArrayList<>();
                if (wheelViewHolder.mWheelRg.getChildCount() > 0)
                    wheelViewHolder.mWheelRg.removeAllViews();
                for (int i = 0; i < mNewHomeEntity.getInfo().getScroll().size(); i++) {
                    RadioButton radioButton = (RadioButton) mLayoutInflater.inflate(R.layout.view_wheel_select, wheelViewHolder.mWheelRg, false);

                    mRbs.add(radioButton);
                    wheelViewHolder.mWheelRg.addView(radioButton);
                    if (i == 0) {
                        radioButton.setChecked(true);
                    }
                }
                NewHomeWheelAdapter newHomeWheelAdapter = new NewHomeWheelAdapter(mNewHomeEntity.getInfo().getScroll(), mContext);
                wheelViewHolder.mWheel.setAdapter(newHomeWheelAdapter);
                newHomeWheelAdapter.setOnWheelItemClickListener(new NewHomeWheelAdapter.OnWheelItemClickListener() {
                    @Override
                    public void onWheelItem(String id, String sel_type, String type) {
                        if (mOnHomeItemClickListener != null) {

                            mOnHomeItemClickListener.onHomeItemClick(id, sel_type, type);
                        }
                    }
                });

                mCurrentPos = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mNewHomeEntity.getInfo().getScroll().size();
                wheelViewHolder.mWheel.setCurrentItem(mCurrentPos);
                wheelViewHolder.mHandler.sendEmptyMessage(START);
                wheelViewHolder.mWheel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        mRbs.get(position % mNewHomeEntity.getInfo().getScroll().size()).setChecked(true);
                        wheelViewHolder.mHandler
                                .sendMessage(Message.obtain(wheelViewHolder.mHandler, RECORD, position, 0));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        switch (state) {
                            // 当滑动时让当前轮播停止
                            case ViewPager.SCROLL_STATE_DRAGGING:
                                wheelViewHolder.mHandler.sendEmptyMessage(STOP);
                                break;
                            // 滑动停止时继续轮播
                            case ViewPager.SCROLL_STATE_IDLE:
                                wheelViewHolder.mHandler.sendEmptyMessage(START);
                                break;
                        }
                    }
                });
            } else if (holder instanceof WuLiViewHolder) {
                WuLiViewHolder wuLiViewHolder = (WuLiViewHolder) holder;
                for (int i = 0; i < wuLiViewHolder.wulis.length; i++) {
                    final int finalI = i;
                    wuLiViewHolder.wulis[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EventBus.getDefault().post(new ReplacePageEntity(1, mIds[finalI]));
                        }
                    });
                }

            } else if (holder instanceof VideoViewHolder) {
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                final NewHomeEntity.InfoBean.CourseBean courseBean = mNewHomeEntity.getInfo().getCourse().get(index);
                if (courseBean != null) {
                    videoViewHolder.mTitle.setText(courseBean.getName());
                    Glide.with(mContext).load(courseBean.getLogo()).centerCrop().into(videoViewHolder.mIcon);
                    videoViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, NewMoreActivity.class);
                            intent.putExtra("title", courseBean.getName());
                            intent.putExtra("ve", courseBean.getVe());
                            intent.putExtra("hs", courseBean.getHs());
                            intent.putExtra("id", courseBean.getId());
                            mContext.startActivity(intent);
                        }
                    });
                    if (StringUtils.isNotEmpty(courseBean.getBanner())) {
                        videoViewHolder.mBanner.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(courseBean.getBanner()).centerCrop().into(videoViewHolder.mBanner);
                        videoViewHolder.mBanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mOnHomeItemClickListener != null) {
                                    mOnHomeItemClickListener.onHomeItemClick(courseBean.getProduct_id(), courseBean.getSel_type(), courseBean.getType());
                                }
                            }
                        });
                    } else {
                        videoViewHolder.mBanner.setVisibility(View.GONE);
                    }

                    HomeVideoAdapter homeVideoAdapter = new HomeVideoAdapter(mContext, courseBean.getCourse_detail(), courseBean.getHs(), courseBean.getVe());
                    videoViewHolder.mVideoList.setAdapter(homeVideoAdapter);
                    homeVideoAdapter.setOnVideoItemClickListener(new HomeVideoAdapter.OnVideoItemClickListener() {
                        @Override
                        public void onVideoItemClick(String id, String sel_type, String type) {
                            if (mOnHomeItemClickListener != null) {
                                mOnHomeItemClickListener.onHomeItemClick(id, sel_type, type);
                            }
                        }
                    });
                }
            } else if (holder instanceof OfflineViewHolder) {
                final OfflineViewHolder offlineViewHolder = (OfflineViewHolder) holder;

                final NewHomeEntity.InfoBean.CourseBean actionBean = mNewHomeEntity.getInfo().getCourse().get(index);

                Glide.with(mContext).load(actionBean.getLogo()).centerCrop().into(offlineViewHolder.mIcon);
                if (StringUtils.isNotEmpty(actionBean.getBanner())) {
                    offlineViewHolder.mBanner.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(actionBean.getBanner()).centerCrop().into(offlineViewHolder.mBanner);
                    offlineViewHolder.mBanner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mOnHomeItemClickListener != null) {
                                mOnHomeItemClickListener.onHomeItemClick(actionBean.getProduct_id(), actionBean.getSel_type(), actionBean.getType());
                            }
                        }
                    });
                } else {
                    offlineViewHolder.mBanner.setVisibility(View.GONE);
                }
                offlineViewHolder.mTitle.setText(actionBean.getName());
                int size = offlineViewHolder.mMonths.length > mNewHomeEntity.getInfo().getEvent().size() ?
                        mNewHomeEntity.getInfo().getEvent().size() : offlineViewHolder.mMonths.length;
                for (int i = 0; i < size; i++) {
                    final NewHomeEntity.InfoBean.EventBean eventBean = mNewHomeEntity.getInfo().getEvent().get(i);
                    Log.e("DH_MONTH", "sum:" + eventBean.getSum());
                    if (!"0".equals(eventBean.getSum()) && StringUtils.isNotEmpty(eventBean.getSum())) {
                        offlineViewHolder.mMonths[i].setVisibility(View.VISIBLE);
                    }
                    if (eventBean.isChecked()) {
                        offlineViewHolder.mMonths[i].setTextColor(Color.parseColor("#FFFFFF"));
                        offlineViewHolder.mMonths[i].setBackgroundResource(R.drawable.shape_home_action_maincolor_bg);
                        offlineViewHolder.mMonths[i].setText(eventBean.getMonth() + "月·" + eventBean.getSum() + "节课");
                        HomeActionAdapter homeActionAdapter = new HomeActionAdapter(mContext, eventBean.getList());
                        offlineViewHolder.mActionList.setAdapter(homeActionAdapter);
                        homeActionAdapter.setOnActionItemClickListener(new HomeActionAdapter.OnActionItemClickListener() {
                            @Override
                            public void onActionItemClick(String id, String sel_type, String type) {
                                if (mOnHomeItemClickListener != null) {
                                    mOnHomeItemClickListener.onHomeItemClick(id, "2", type);
                                }
                            }
                        });
                        try {
                            int sum = Integer.parseInt(eventBean.getSum());
                            if (sum > 3) {
                                offlineViewHolder.mRemain.setVisibility(View.VISIBLE);
                                offlineViewHolder.mRemain.setText("剩余" + (sum - 3) + "节线下好课，点击查看更多");
                                offlineViewHolder.mRemain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(mContext, AllEventsActivity.class);
                                        intent.putExtra("month", eventBean.getMonth());
                                        intent.putExtra("month_flag", eventBean.getW());
                                        mContext.startActivity(intent);
                                    }
                                });
                            } else {
                                offlineViewHolder.mRemain.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            offlineViewHolder.mRemain.setVisibility(View.GONE);
                        }


                    } else {
                        offlineViewHolder.mMonths[i].setTextColor(mContext.getResources().getColor(R.color.originator));
                        offlineViewHolder.mMonths[i].setBackgroundResource(R.drawable.shape_home_action_hui_bg);
                        offlineViewHolder.mMonths[i].setText(eventBean.getMonth() + "月");
                    }

                    offlineViewHolder.mMonths[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!eventBean.isChecked()) {
                                for (int i1 = 0; i1 < mNewHomeEntity.getInfo().getEvent().size(); i1++) {
                                    mNewHomeEntity.getInfo().getEvent().get(i1).setChecked(false);
                                }
                                eventBean.setChecked(true);
                            }

                            notifyItemChanged(position);
                        }
                    });
                }

                offlineViewHolder.mAllAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, TableActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            } else if (holder instanceof EssayViewHolder) {
                final EssayViewHolder essayViewHolder = (EssayViewHolder) holder;
                final NewHomeEntity.InfoBean.CourseBean courseBean = mNewHomeEntity.getInfo().getCourse().get(index);
                if (courseBean != null) {
                    Glide.with(mContext).load(courseBean.getLogo()).centerCrop().into(essayViewHolder.mIcon);
                    essayViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, NewEssayMoreActivity.class);
//                            intent.putExtra("title", courseBean.getName());
//                            intent.putExtra("ve", courseBean.getVe());
//                            intent.putExtra("hs", courseBean.getHs());
//                            intent.putExtra("id", courseBean.getId());
                            mContext.startActivity(intent);
                        }
                    });
                    if (StringUtils.isNotEmpty(courseBean.getBanner())) {
                        essayViewHolder.mBanner.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(courseBean.getBanner()).centerCrop().into(essayViewHolder.mBanner);
                        essayViewHolder.mBanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mOnHomeItemClickListener != null) {
                                    mOnHomeItemClickListener.onHomeItemClick(courseBean.getProduct_id(), courseBean.getSel_type(), courseBean.getType());
                                }
                            }
                        });
                    } else {
                        essayViewHolder.mBanner.setVisibility(View.GONE);
                    }
                    final List<NewHomeEntity.InfoBean.YueduBean> yueduBeanList = mNewHomeEntity.getInfo().getYuedu();
//                    if (yueduBeanList!=null&&yueduBeanList.size()>0) {
//                        yueduBeanList.get(0).setCheck(true);
//                    }
                    EssayLabelAdapter essayLabelAdapter = new EssayLabelAdapter(mContext, yueduBeanList);
                    essayViewHolder.mTitleList.setAdapter(essayLabelAdapter);
                    essayLabelAdapter.setOnEssayLabelItemClickListener(new EssayLabelAdapter.OnEssayLabelItemClickListener() {
                        @Override
                        public void onEssayLabelClick(int pos) {
                            HomeVideoAdapter homeVideoAdapter = new HomeVideoAdapter(mContext, yueduBeanList.get(pos).getNews().getList(), courseBean.getHs(), courseBean.getVe());
                            essayViewHolder.mEssayList.setAdapter(homeVideoAdapter);
                            homeVideoAdapter.setOnVideoItemClickListener(new HomeVideoAdapter.OnVideoItemClickListener() {
                                @Override
                                public void onVideoItemClick(String id, String sel_type, String type) {
                                    if (mOnHomeItemClickListener != null) {
                                        mOnHomeItemClickListener.onHomeItemClick(id, sel_type, type);
                                    }
                                }
                            });
                        }
                    });

                    HomeVideoAdapter homeVideoAdapter = new HomeVideoAdapter(mContext, yueduBeanList.get(0).getNews().getList(), courseBean.getHs(), courseBean.getVe());
                    essayViewHolder.mEssayList.setAdapter(homeVideoAdapter);
                    homeVideoAdapter.setOnVideoItemClickListener(new HomeVideoAdapter.OnVideoItemClickListener() {
                        @Override
                        public void onVideoItemClick(String id, String sel_type, String type) {
                            if (mOnHomeItemClickListener != null) {
                                mOnHomeItemClickListener.onHomeItemClick(id, sel_type, type);
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return WHEEL;
        } else if (position == 1) {
            return WU_LI;
        } else {
            int index = position - 2;
            if (mNewHomeEntity != null && mNewHomeEntity.getInfo() != null && mNewHomeEntity.getInfo().getCourse() != null) {
                NewHomeEntity.InfoBean.CourseBean courseBean = mNewHomeEntity.getInfo().getCourse().get(index);
                if ("0".equals(courseBean.getVe())) {// 课程
                    if ("1".equals(courseBean.getHs())) { //竖向展示
                        return VIDEO_V;
                    }
                    return VIDEO_H; //横向展示
                } else if ("1".equals(courseBean.getVe())) {//活动
                    return ACTION;
                } else if ("2".equals(courseBean.getVe())) {//文章
                    return ESSAY;
                }
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (mNewHomeEntity == null || mNewHomeEntity.getInfo() == null || mNewHomeEntity.getInfo().getCourse() == null) {
            return 2;
        } else {
            return mNewHomeEntity.getInfo().getCourse().size() + 2; //预留轮播和五力的位置
        }
    }

    class WheelViewHolder extends RecyclerView.ViewHolder {
        private ViewPager mWheel;
        private float rate = 5 / 2.0f;
        private RelativeLayout mWheelContent;
        private RadioGroup mWheelRg;


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
                        mCurrentPos++;
                        mWheel.setCurrentItem(mCurrentPos);
                        break;
                    case RECORD:
                        mCurrentPos = msg.arg1;
                        break;
                    default:
                        break;
                }

            }
        };

        public WheelViewHolder(View itemView) {
            super(itemView);
            mWheel = (ViewPager) itemView.findViewById(R.id.wheel);
            mWheelContent = (RelativeLayout) itemView.findViewById(R.id.wheel_content);
            mWheelRg = (RadioGroup) itemView.findViewById(R.id.wheel_rg);
            //控制滑动速度
            ViewPageUtils.controlViewPagerSpeed(mWheel, 300);
            //设置viewpager大小
            int w = ScreenUtils.getScreenWidth(mContext);//- DisplayUtil.dip2px(mContext,15+15);
            ViewGroup.LayoutParams layoutParams = mWheelContent.getLayoutParams();
            layoutParams.height = (int) (w / rate);
            mWheelContent.setLayoutParams(layoutParams);

        }
    }

    class WuLiViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mWudali1;
        private RelativeLayout mWudali2;
        private RelativeLayout mWudali3;
        private RelativeLayout mWudali4;
        private RelativeLayout mWudali5;
        private RelativeLayout[] wulis;

        public WuLiViewHolder(View itemView) {
            super(itemView);

            mWudali1 = (RelativeLayout) itemView.findViewById(R.id.wudali_1);
            mWudali2 = (RelativeLayout) itemView.findViewById(R.id.wudali_2);
            mWudali3 = (RelativeLayout) itemView.findViewById(R.id.wudali_3);
            mWudali4 = (RelativeLayout) itemView.findViewById(R.id.wudali_4);
            mWudali5 = (RelativeLayout) itemView.findViewById(R.id.wudali_5);
            wulis = new RelativeLayout[]{mWudali1, mWudali2, mWudali3, mWudali4, mWudali5};

        }
    }

    class OfflineViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mBanner;
        private ImageView mIcon;
        private TextView mTitle;
        private RecyclerView mActionList;
        private TextView mRemain;
        private TextView mMonth1;
        private TextView mMonth2;
        private TextView mMonth3;
        private TextView mAllAction;
        private TextView[] mMonths;

        public OfflineViewHolder(View itemView) {
            super(itemView);

            mBanner = (RoundedImageView) itemView.findViewById(R.id.banner);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mActionList = (RecyclerView) itemView.findViewById(R.id.action_list);
            mRemain = (TextView) itemView.findViewById(R.id.remain);
            mActionList.setLayoutManager(new LinearLayoutManager(mContext));
            mMonth1 = (TextView) itemView.findViewById(R.id.month_1);
            mMonth2 = (TextView) itemView.findViewById(R.id.month_2);
            mMonth3 = (TextView) itemView.findViewById(R.id.month_3);
            mAllAction = (TextView) itemView.findViewById(R.id.all_action);
            mMonths = new TextView[]{mMonth1, mMonth2, mMonth3};
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon;
        private TextView mTitle;
        private TextView mMore;
        private RecyclerView mVideoList;
        private RoundedImageView mBanner;


        public VideoViewHolder(View itemView, int type) {
            super(itemView);
            mBanner = (RoundedImageView) itemView.findViewById(R.id.banner);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mMore = (TextView) itemView.findViewById(R.id.more);
            mVideoList = (RecyclerView) itemView.findViewById(R.id.video_list);
            if (type == VIDEO_H) {
                mVideoList.setLayoutManager(new GridLayoutManager(mContext, 2));
                mVideoList.addItemDecoration(new GridSpacingItemDecoration(2, DisplayUtil.dip2px(MyApplication.mApplication, 9), true));
            } else {
                mVideoList.setLayoutManager(new LinearLayoutManager(mContext));
            }

        }
    }

    class EssayViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mBanner;
        private ImageView mIcon;
        private RecyclerView mTitleList;
        private RecyclerView mEssayList;
        private TextView mMore;

        public EssayViewHolder(View itemView) {
            super(itemView);

            mBanner = (RoundedImageView) itemView.findViewById(R.id.banner);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTitleList = (RecyclerView) itemView.findViewById(R.id.title_list);
            mEssayList = (RecyclerView) itemView.findViewById(R.id.essay_list);
            mMore = (TextView) itemView.findViewById(R.id.more);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mTitleList.setLayoutManager(linearLayoutManager);
            mEssayList.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    private OnHomeItemClickListener mOnHomeItemClickListener;

    public void setOnHomeItemClickListener(OnHomeItemClickListener onHomeItemClickListener) {
        mOnHomeItemClickListener = onHomeItemClickListener;
    }

    public interface OnHomeItemClickListener {
        void onHomeItemClick(String id, String sel_type, String type);
    }
}
