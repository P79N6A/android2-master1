package com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MyStudyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MyStudyPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEssayDetailActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.StudyCollectAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends Fragment {
    private RecyclerView mCollectList;
    //    private ImageView mNoData;
//    private ImageView mNoLogin;
    private StudyCollectAdapter mStudyCollectAdapter;
    private String mUserId;
    private String mMobile;
    private MyStudyPresenter mMyStudyPresenter = new MyStudyPresenter();

    public CollectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        mCollectList = (RecyclerView) view.findViewById(R.id.collect_list);
//        mNoData = (ImageView) view.findViewById(R.id.no_data);
//        mNoLogin = (ImageView) view.findViewById(R.id.no_login);

        mCollectList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        if (StringUtils.isNotEmpty(mUserId) && StringUtils.isNotEmpty(mMobile)) {
            mMyStudyPresenter.getStudyInfo(mUserId, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    MyStudyEntity entity = (MyStudyEntity) value[0];
//                    if(entity.getCollect()!=null&&entity.getCollect().size()>0){
////                        NothingShow(2);
//                    }else{
//                        NothingShow(0);
//                    }
                    showCollectData(entity.getCollect());

                }

                @Override
                public void onFault(String error) {
                    mCollectList.setAdapter(new EmptyRecycleAdapter(null, R.layout.layout_empty));
                }
            });
        } else {
//            mCollectList.setVisibility(View.GONE);
//            NothingShow(1);
            mCollectList.setAdapter(new EmptyRecycleAdapter(null, R.layout.layout_no_login));
        }
    }

    public void showCollectData(final List<MyStudyEntity.CollectBean> collects) {
//        if(collects!=null&&collects.size()>0){
//            if(mStudyCollectAdapter==null){
        mStudyCollectAdapter = new StudyCollectAdapter(collects);
        mCollectList.setAdapter(new EmptyRecycleAdapter(mStudyCollectAdapter, R.layout.layout_empty));
        mStudyCollectAdapter.setOnCollectClickListener(new StudyCollectAdapter.OnCollectClickListener() {
            @Override
            public void onCollectClick(int pos, MyStudyEntity.CollectBean collectBean) {
                Intent intent = null;
                switch (collectBean.getType()) {
                    case 1:
                        intent = new Intent(getContext(), NewVideoInfoActivity.class);
                        intent.putExtra("type", collectBean.getType() + "");
                        break;
                    case 2:
                        intent = new Intent(getContext(), NewEssayDetailActivity.class);
                        break;
                    case 3:
                        break;
                    case 4:
                        intent = new Intent(getContext(), SumUpActivity.class);
                        intent.putExtra("type", collectBean.getType() + "");
                        break;
                }
                if (intent != null) {
                    intent.putExtra("id", collectBean.getId() + "");
                    startActivity(intent);
                } else {

                }
            }
        });
//            }else{
//                mStudyCollectAdapter.notifyDataSetChanged();
//            }
//        }
    }
//    private void NothingShow(int flag){
//        if(flag==0){//没数据
//            mNoData.setVisibility(View.VISIBLE);
//            mNoLogin.setVisibility(View.GONE);
//        }else if(flag==1){//没登录
//            mNoData.setVisibility(View.GONE);
//            mNoLogin.setVisibility(View.VISIBLE);
//        }else{
//            mNoData.setVisibility(View.GONE);
//            mNoLogin.setVisibility(View.GONE);
//        }
//    }
}
