package com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.fragment;


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
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.adapter.CouponListAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponListEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.discount_coupon.net.CouponPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CouponListFragment extends Fragment {
    private RecyclerView mDiscountCouponList;
    private List<String> mList = new ArrayList<>();
    private String mStatus;
    private CouponPresenter mCouponPresenter = new CouponPresenter();
    private String mUserId;

    public CouponListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupon_list, container, false);
        mDiscountCouponList = (RecyclerView) view.findViewById(R.id.discount_coupon_list);
        mDiscountCouponList.setLayoutManager(new LinearLayoutManager(getContext()));
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        Bundle bundle = getArguments();
        if(bundle!=null){
            mStatus = bundle.getString("status");
        }
        accessNet();

        return view;
    }

    private void accessNet() {
        LoadingUtils.getInstance().showNetLoading(getContext());
        mCouponPresenter.getCouponList(mUserId, mStatus, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
                CouponListEntity couponListEntity = (CouponListEntity) value[0];
                if("200".equals(couponListEntity.getCode())){
                    CouponListAdapter couponListAdapter = new CouponListAdapter(getContext(), couponListEntity.getInfo(), mStatus);
                    mDiscountCouponList.setAdapter(new EmptyRecycleAdapter(couponListAdapter, R.layout.layout_coupon_empty));
                }else{
                    Toast.makeText(MyApplication.mApplication, couponListEntity.getMsg()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_ERROR_COUPON_LIST",error);
            }
        });
    }

}
