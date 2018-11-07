package com.wh.wang.scroopclassproject.newproject.ui.fragment.mystudy_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MyStudyEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.MyStudyPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.OpenClassActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.StudyOrderAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlreadyBuyFragment extends Fragment {
    private RecyclerView mAlreadyBuyList;

    private MyStudyPresenter mMyStudyPresenter = new MyStudyPresenter();
    private String mUserId;
    private String mMobile;
    private StudyOrderAdapter mStudyOrderAdapter;

    public AlreadyBuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_already_buy,
                container, false);
        mAlreadyBuyList = (RecyclerView) view.findViewById(R.id.already_buy_list);
        mAlreadyBuyList.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    mStudyOrderAdapter = new StudyOrderAdapter(entity.getOrder());
                    mAlreadyBuyList.setAdapter(new EmptyRecycleAdapter(mStudyOrderAdapter, R.layout.layout_empty));
                    mStudyOrderAdapter.setOnOrderClickListener(new StudyOrderAdapter.OnOrderClickListener() {
                        @Override
                        public void onOrderClick(int pos, String id, String type) {
                            Intent intent = null;
                            switch (type) {
                                case "3":
                                    intent = new Intent(getContext(), NewEventDetailsActivity.class);
                                    intent.putExtra("event_id", id);
                                    break;
                                case "4":
                                    intent = new Intent(getContext(), SumUpActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("type", type);
                                    break;
                                case "5":
                                    intent = new Intent(getContext(), OpenClassActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("type", type);
                                    break;
                                case "1":
                                default:
                                    intent = new Intent(getContext(), NewVideoInfoActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("type", type);
                                    break;
                            }
                            if (intent != null) {
                                startActivity(intent);
                            }
                        }
                    });

                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_ORDER", error);
                    Toast.makeText(MyApplication.mApplication, "网络异常", Toast.LENGTH_SHORT).show();
                    mAlreadyBuyList.setAdapter(new EmptyRecycleAdapter(null, R.layout.layout_empty));
                }
            });
        } else {
            //未登录
            mAlreadyBuyList.setAdapter(new EmptyRecycleAdapter(null, R.layout.layout_no_login));
        }
    }
}
