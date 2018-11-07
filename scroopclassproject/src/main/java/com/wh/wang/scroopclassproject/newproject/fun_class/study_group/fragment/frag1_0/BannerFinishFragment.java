package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity1_0.StudyGroupHomeActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BannerFinishFragment extends Fragment {
    private TextView mFinish;
    private Button mShowCoupon;
    private String mPid;
    private String mUserId;

    public BannerFinishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_banner_finish, container, false);
        mFinish = (TextView) view.findViewById(R.id.finish);
        mShowCoupon = (Button) view.findViewById(R.id.show_coupon);
        Bundle bundle = getArguments();
        if (bundle!=null) {
            String complete = bundle.getString("complete", "");
            final String url = bundle.getString("url","");
            mPid = bundle.getString("pid","");
            mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");

            if("1".equals(complete)){
                mFinish.setText(R.string.task_success);
                mShowCoupon.setText(R.string.my_award);
                mShowCoupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCoupon(url);
                    }
                });
            }else if("2".equals(complete)){
                mFinish.setText(R.string.task_defeated);
                mShowCoupon.setText("再接再厉，报名下一场");
                mShowCoupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), StudyGroupHomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        return view;
    }

    private Dialog mCouponDig;
    public void showCoupon(String url){
        if(mCouponDig==null){
            mCouponDig = new Dialog(getContext(),R.style.MyDialog) ;
        }
        View dView = LayoutInflater.from(getContext()).inflate(R.layout.dig_coupon, null, false);
        mCouponDig.setContentView(dView);

        ImageView coupon = (ImageView) dView.findViewById(R.id.coupon_img);
        ImageView close = (ImageView) dView.findViewById(R.id.close);
        if(StringUtils.isNotEmpty(url)){
            Glide.with(MyApplication.mApplication).load(url).into(coupon);
        }
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCouponDig.dismiss();
//            }
//        });
        mCouponDig.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                new StudyGroupPresenter().showCoupun(mUserId, mPid, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {

                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCouponDig.dismiss();
            }
        });
        mCouponDig.show();
    }
}
