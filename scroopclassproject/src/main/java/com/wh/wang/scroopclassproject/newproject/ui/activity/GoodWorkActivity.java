package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.callback.OnWorkClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.LikePresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.WorkPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.GoodWorkAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.List;

public class GoodWorkActivity extends Activity implements OnWorkClickListener {
    private TextView mTitlebarbackssName;
    private RecyclerView mGoodWorkList;
    private WorkPresenter mWorkPresenter;
    private String mUser_id;
    private GoodWorkAdapter mGoodWorkAdapter;
    private LikePresenter mLikePresenter = new LikePresenter();
    private List<WorkInfoEntity.InfoBean.YouxiuBean> mYouxiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_work);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mGoodWorkList = (RecyclerView) findViewById(R.id.good_work_list);
        mGoodWorkList.setLayoutManager(new LinearLayoutManager(this));
        mTitlebarbackssName.setText(R.string.good_work);
        findViewById(R.id.titlebarbackss_imageViewback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String video_id = getIntent().getStringExtra("video_id");
        String cate_id = getIntent().getStringExtra("cate_id");

        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mWorkPresenter = new WorkPresenter();
        mWorkPresenter.getGoodWork(video_id, cate_id, mUser_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                WorkInfoEntity entity = (WorkInfoEntity) value[0];
                if (entity.getCode() == 200) {
                    mYouxiu = entity.getInfo().getYouxiu();
                    if (mYouxiu != null) {
                        initList(mYouxiu);
                    }
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    private void initList(List<WorkInfoEntity.InfoBean.YouxiuBean> youxiu) {
        mGoodWorkAdapter = new GoodWorkAdapter(this, youxiu);
        mGoodWorkList.setAdapter(mGoodWorkAdapter);
        mGoodWorkAdapter.setOnWorkClickListener(this);
    }

    @Override
    public void onWorkItemClick(int pos, String id) {
        Intent intent = new Intent(this, HomeWorkDetailsActivity.class);
        intent.putExtra("list_id", id);
        startActivity(intent);
    }

    @Override
    public void onWorkLikeClick(final int pos, String id, int like, final ImageView likeImg, final TextView textView, final int flag) {
        mLikePresenter.workLike(id, mUser_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                WorkLikeEntity entity = (WorkLikeEntity) value[0];
                mYouxiu.get(pos).setIfzhan(entity.getZhan());
                int zan_sum = 0;
                try {
                    zan_sum = Integer.parseInt(mYouxiu.get(pos).getZhan_sum());
                } catch (Exception e) {
                    zan_sum = 0;
                }
                if (entity.getZhan() == 1) {
                    mYouxiu.get(pos).setZhan_sum((zan_sum + 1) + "");
                    likeImg.setImageResource(R.drawable.zuoye_ydz);
                    textView.setText((zan_sum + 1) + "");
                } else {
                    mYouxiu.get(pos).setZhan_sum((zan_sum - 1) + "");
                    likeImg.setImageResource(R.drawable.zuoye_dz);
                    textView.setText((zan_sum - 1) + "");
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_LIST_ZAN", error);
            }
        });
    }

    @Override
    public void onWorkRemarkClick(int pos, String id) {
        Intent intent = new Intent(this, HomeWorkDetailsActivity.class);
        intent.putExtra("list_id", id);
        intent.putExtra("location_remark", true);
        startActivity(intent);
    }
}
