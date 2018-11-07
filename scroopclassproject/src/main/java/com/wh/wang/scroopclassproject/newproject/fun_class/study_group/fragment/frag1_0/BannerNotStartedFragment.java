package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BannerNotStartedFragment extends Fragment {
    private TextView mResidueTv;
    private TextView mResidueTimeDay;
    private TextView mResidueTimeHour;
    private TextView mResidueTimeMinute;
    private TextView mResidueTimeSecond;
//    private TextView mPublicTimeTv;
    private long mPublicTime;
    private long mResidueTime;// = 24 * 60 * 60 * 3;
    private CountDownTimer mCountDownTimer;


    public BannerNotStartedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner_not_started, container, false);
        mResidueTv = (TextView) view.findViewById(R.id.residue_tv);
        mResidueTimeDay = (TextView) view.findViewById(R.id.residue_time_day);
        mResidueTimeHour = (TextView) view.findViewById(R.id.residue_time_hour);
        mResidueTimeMinute = (TextView) view.findViewById(R.id.residue_time_minute);
        mResidueTimeSecond = (TextView) view.findViewById(R.id.residue_time_second);
//        mPublicTimeTv = (TextView) view.findViewById(R.id.public_time);
//        mPublicTime = new Date().getTime() /1000 + mResidueTime;
        initData();
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        long public_time = bundle.getLong("public_time",0);
        long now_time = bundle.getLong("now_time",0);
        mResidueTime = public_time - now_time;
        String start_time = bundle.getString("start_time");
//        mPublicTimeTv.setText(start_time + "开课");
        residueCountDown(mResidueTime);
    }

    private long DAT = 24 * 60 * 60;
    private long HOUR = 60 * 60;
    private long MINUTE = 60;

    private void residueCountDown(long residueTime) {
        if (mCountDownTimer == null) {
            starCountDown(residueTime);
            mCountDownTimer = new CountDownTimer(residueTime * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    starCountDown(l / 1000);
                }

                @Override
                public void onFinish() {
                    if(mOnActionStartClickListener!=null){
                        mOnActionStartClickListener.onActionStartClick();
                    }
                }
            };
            mCountDownTimer.start();
        }
    }

    /**
     * 时间更新
     *
     * @param time
     */
    private void starCountDown(long time) {
        long day = time / DAT;
        long hour = time % DAT / HOUR;
        long minute = time % DAT % HOUR / MINUTE;
        long second = time % DAT % HOUR % MINUTE;
        mResidueTimeDay.setText(day + "");
        mResidueTimeHour.setText(hour + "");
        mResidueTimeMinute.setText(minute + "");
        mResidueTimeSecond.setText(second + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    private OnActionStartClickListener mOnActionStartClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof  OnActionStartClickListener){
            mOnActionStartClickListener = (OnActionStartClickListener) activity;
        }
    }

    public interface OnActionStartClickListener{
        void onActionStartClick();
    }
}
