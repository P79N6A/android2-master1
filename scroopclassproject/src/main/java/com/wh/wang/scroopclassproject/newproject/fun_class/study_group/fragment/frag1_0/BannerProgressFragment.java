package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BannerProgressFragment extends Fragment {
    private TextView mProgress;
    private ProgressBar mProgressBar;
    private TextView mPrp1;
    private TextView mPro2;

    public BannerProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_banner_progress, container, false);
        mProgress = (TextView) view.findViewById(R.id.progress);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mPrp1 = (TextView) view.findViewById(R.id.prp_1);
        mPro2 = (TextView) view.findViewById(R.id.pro_2);

        mProgressBar.setMax(100);
        Bundle bundle = getArguments();
        if(bundle!=null){
            String status = bundle.getString("status","0");
            switch (status){
                case "0": //未看视频未评论 或 评论未看视频
                case "3":
                    mProgress.setText("0%");
                    mProgressBar.setProgress(0);
                    break;
                case "1": //看了视频未评论
                    mProgress.setText("50%");
                    mProgressBar.setProgress(50);
                    break;
                case "2": //看了视频也评论
                    mProgress.setText("已完成");
                    mProgressBar.setProgress(100);
                    break;
            }
        }
        return view;
    }

}
