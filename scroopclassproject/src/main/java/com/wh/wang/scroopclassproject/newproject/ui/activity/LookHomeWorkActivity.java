package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.callback.OnWorkClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.LikePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.WorkPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.AllWorkAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.WelcomeWorkAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

public class LookHomeWorkActivity extends Activity implements OnWorkClickListener {
    private TextView mTitlebarbackssName;
    private WorkPresenter mWorkPresenter;
    private TextView mWorkTitle;
    private TextView mWorkFrom;
    private TextView mWorkTime;
    private RelativeLayout mGoodWork;
    private TextView mGoodWorkNum;
    private LinearLayout mGoodWorkPerson;
    private TextView mWorkContent;
    private RecyclerView mWorkListWelcome;
    private RecyclerView mWorkListAll;
    private WelcomeWorkAdapter mWelcomeWorkAdapter;
    private AllWorkAdapter mAllWorkAdapter;
    private LikePresenter mLikePresenter = new LikePresenter();
    private String mUser_id;
    private FrameLayout mAddWork;
    private String mTitle;
    private List<WorkInfoEntity.AllBean> mAll;
    private List<WorkInfoEntity.TopBean> mTop;
    private TextView mWelcome;
    private String mZuoYeId;
    private String mVideo_id;
    private String mCate_id;
    private TextView mRemarkTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        initView();
        initListener();
    }

    private void initListener() {
        mAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookHomeWorkActivity.this,PublishWorkActivity.class);
                intent.putExtra("title",mTitle);
                intent.putExtra("zuoye_id",mZuoYeId);
                startActivity(intent);
            }
        });
       findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mWorkTitle = (TextView) findViewById(R.id.work_title);
        mWorkFrom = (TextView) findViewById(R.id.work_from);
        mWorkTime = (TextView) findViewById(R.id.work_time);
        mGoodWork = (RelativeLayout) findViewById(R.id.good_work);
        mGoodWorkNum = (TextView) findViewById(R.id.good_work_num);
        mGoodWorkPerson = (LinearLayout) findViewById(R.id.good_work_person);
        mWorkContent = (TextView) findViewById(R.id.work_content);
        mWorkListWelcome = (RecyclerView) findViewById(R.id.work_list_welcome);
        mWorkListAll = (RecyclerView) findViewById(R.id.work_list_all);
        mAddWork = (FrameLayout) findViewById(R.id.add_work);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mWelcome = (TextView) findViewById(R.id.welcome);
        mRemarkTv = (TextView) findViewById(R.id.remark_tv);
        mRemarkTv.setHint("发布作业");
        mWorkListWelcome.setLayoutManager(new LinearLayoutManager(this));
        mWorkListWelcome.setHasFixedSize(true);
        mWorkListWelcome.setNestedScrollingEnabled(false);
        mWorkListAll.setLayoutManager(new LinearLayoutManager(this));
        mWorkListAll.setHasFixedSize(true);
        mWorkListAll.setNestedScrollingEnabled(false);
        findViewById(R.id.remark).setVisibility(View.GONE);

        mTitlebarbackssName.setText(R.string.work);

        mVideo_id = getIntent().getStringExtra("video_id");
        mCate_id = getIntent().getStringExtra("cate_id");
        Log.e("DH_mCate_id",mVideo_id+"  "+mCate_id);
//        mVideo_id = "1414";
//        mCate_id = "9046";
    }

    @Override
    protected void onResume() {
        super.onResume();
        //1414  9046
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mWorkPresenter = new WorkPresenter();
        mWorkPresenter.getWorkInfo(mVideo_id, mCate_id, mUser_id, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                WorkInfoEntity entity = (WorkInfoEntity) value[0];
                if(entity.getCode()==200){
                    WorkInfoEntity.InfoBean info = entity.getInfo();
                    mTitle = info.getTitle();
                    mAll = entity.getAll();
                    mTop = entity.getTop();
                    if(info!=null)
                        initBaseInfo(info);
                    if(mTop!=null&&mTop.size()>0){
                        mWelcome.setVisibility(View.VISIBLE);
                        mWorkListWelcome.setVisibility(View.VISIBLE);
                        initWelcomeList(mTop);
                    }else{
                        mWelcome.setVisibility(View.GONE);
                        mWorkListWelcome.setVisibility(View.GONE);
                    }
                    if(mAll!=null&&mAll.size()>0){
                        initAllList(mAll);
                        mWorkListAll.setVisibility(View.VISIBLE);
                    }else{
                        mWorkListAll.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_WORK_INFO",error);
            }
        });
    }

    private void initAllList(final List<WorkInfoEntity.AllBean> all) {
        mAllWorkAdapter = new AllWorkAdapter(this,all);
        mWorkListAll.setAdapter(mAllWorkAdapter);
        mAllWorkAdapter.setOnWorkClickListener(this);
//        mAllWorkAdapter.setOnWorkItemClickListener(new AllWorkAdapter.OnWorkItemClickListener() {
//            @Override
//            public void onWorkItemClick(int pos, String id) {
//                Intent intent = new Intent(LookHomeWorkActivity.this,HomeWorkDetailsActivity.class);
//                intent.putExtra("list_id",id);
//                startActivity(intent);
//            }
//        });
//        mAllWorkAdapter.setOnWorkLikeClickListener(new AllWorkAdapter.OnWorkLikeClickListener() {
//            @Override
//            public void onWorkLikeClick(final int pos, String id, int like, final ImageView likeImg) {
//                mLikePresenter.workLike(id, mUser_id, new OnResultListener() {
//                    @Override
//                    public void onSuccess(Object... value) {
//                        WorkLikeEntity entity = (WorkLikeEntity) value[0];
//                        all.get(pos).setIfzhan(entity.getZhan());
//                        int zan_sum = 0;
//                        try{
//                            zan_sum = Integer.parseInt(all.get(pos).getZhan_sum());
//                        }catch (Exception e){
//                            zan_sum = 0;
//                        }
//                        if(entity.getZhan()==1){
//                            all.get(pos).setZhan_sum((zan_sum+1)+"");
//                        }else{
//                            all.get(pos).setZhan_sum((zan_sum-1)+"");
//                        }
//                        mAllWorkAdapter.notifyItemChanged(pos);
//                    }
//
//                    @Override
//                    public void onFault(String error) {
//
//                    }
//                });
//            }
//        });
    }

    private void initWelcomeList(final List<WorkInfoEntity.TopBean> top) {
        mWelcomeWorkAdapter = new WelcomeWorkAdapter(this,top);
        mWorkListWelcome.setAdapter(mWelcomeWorkAdapter);
        mWelcomeWorkAdapter.setOnWorkClickListener(this);
    }

    private void initBaseInfo(final WorkInfoEntity.InfoBean info) {
        mWorkTitle.setText(info.getTitle());
        mWorkFrom.setText(info.getComefrom());
        mWorkTime.setText(info.getShijian());
        mWorkContent.setText(info.getContent());
        mZuoYeId = info.getId();
        LayoutInflater mInflater = LayoutInflater.from(this);
        if(info.getYouxiu()!=null&&info.getYouxiu().size()>0){
            mGoodWork.setVisibility(View.VISIBLE);
            mGoodWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LookHomeWorkActivity.this,GoodWorkActivity.class);
                    intent.putExtra("video_id",mVideo_id);
                    intent.putExtra("cate_id",mCate_id);
                    startActivity(intent);
                }
            });
            mGoodWorkNum.setText(info.getYouxiu().size()+"个优秀作业");
            if(mGoodWorkPerson.getChildCount()>0){
                mGoodWorkPerson.removeAllViews();
            }
            for (int i = 0; i < (info.getYouxiu().size()>6?6:info.getYouxiu().size()); i++) {
                final WorkInfoEntity.InfoBean.YouxiuBean youxiuBean = info.getYouxiu().get(i);
                RoundedImageView view = (RoundedImageView) mInflater.inflate(R.layout.item_goodwork_avatar,mGoodWorkPerson,false);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(DisplayUtil.dip2px(this,7),0,0,0);
                Glide.with(this).load(youxiuBean.getUser_head()).centerCrop().into(view);
                mGoodWorkPerson.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LookHomeWorkActivity.this,HomeWorkDetailsActivity.class);
                        intent.putExtra("list_id",youxiuBean.getId());
                        startActivity(intent);
                    }
                });
            }
        }else{
            mGoodWork.setVisibility(View.GONE);
        }
    }

    /**
     * 作业列表点击监听
     * @param pos
     * @param id
     */
    @Override
    public void onWorkItemClick(int pos, String id) {
        Intent intent = new Intent(LookHomeWorkActivity.this,HomeWorkDetailsActivity.class);
        intent.putExtra("list_id",id);
        startActivity(intent);
    }

    /**
     * 作业列表点赞监听
     * @param pos
     * @param id
     * @param like
     * @param likeImg
     * @param textView
     */
    int likeFlag = 0;
    @Override
    public void onWorkLikeClick(final int pos, String id, int like, final ImageView likeImg, final TextView textView,final int flag) {

        synchronized (LookHomeWorkActivity.class){
            mLikePresenter.workLike(id, mUser_id, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    likeFlag = 0;
                    WorkLikeEntity entity = (WorkLikeEntity) value[0];
                    if(flag==1){
                        mTop.get(pos).setIfzhan(entity.getZhan());
                        int zan_sum = 0;
                        try{
                            zan_sum = Integer.parseInt(mTop.get(pos).getZhan_sum());
                        }catch (Exception e){
                            zan_sum = 0;
                        }
                        if(entity.getZhan()==1){
                            mTop.get(pos).setZhan_sum((zan_sum+1)+"");
                            likeImg.setImageResource(R.drawable.zuoye_ydz);
                            textView.setText((zan_sum+1)+"");
                        }else{
                            mTop.get(pos).setZhan_sum((zan_sum-1)+"");
                            likeImg.setImageResource(R.drawable.zuoye_dz);
                            textView.setText((zan_sum-1)+"");
                        }
                    }else{
                        mAll.get(pos).setIfzhan(entity.getZhan());
                        int zan_sum = 0;
                        try{
                            zan_sum = Integer.parseInt(mAll.get(pos).getZhan_sum());
                        }catch (Exception e){
                            zan_sum = 0;
                        }
                        if(entity.getZhan()==1){
                            mAll.get(pos).setZhan_sum((zan_sum+1)+"");
                            likeImg.setImageResource(R.drawable.zuoye_ydz);
                            textView.setText((zan_sum+1)+"");
                        }else{
                            mAll.get(pos).setZhan_sum((zan_sum-1)+"");
                            likeImg.setImageResource(R.drawable.zuoye_dz);
                            textView.setText((zan_sum-1)+"");
                        }
                    }

//                mWelcomeWorkAdapter.notifyItemChanged(pos);
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_LIST_ZAN",error);
                }
            });
        }
    }

    /**
     * 作业列表评论监听
     * @param pos
     * @param id
     */
    @Override
    public void onWorkRemarkClick(int pos, String id) {
        Intent intent = new Intent(this,HomeWorkDetailsActivity.class);
        intent.putExtra("list_id",id);
        intent.putExtra("location_remark",true);
        startActivity(intent);
    }
}
