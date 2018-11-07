package com.wh.wang.scroopclassproject.newproject.ui.fragment.result_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EventDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EventDetailsPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplyResultFragment extends Fragment implements View.OnClickListener {
    private TextView mCourse;
    private TextView mTime;
    private TextView mAddress;
    private ImageView mMemberIcon;
    private String mEventId;
    private String mUserId;
    private EventDetailsPresenter mEventDetailsPresenter = new EventDetailsPresenter();
    private EventDetailEntity mEventDetailEntity;

    public ApplyResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_result, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        Bundle b = getArguments();
        mEventId = b.getString("order_no");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(getActivity(), "user_id", "");
        getSuccessInfo();
    }

    private void getSuccessInfo() {
        mEventDetailsPresenter.getEventDetails(mEventId, mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                mEventDetailEntity = (EventDetailEntity) value[0];
                if (mEventDetailEntity.getInfo() != null) {
                    EventDetailEntity.InfoBean info = mEventDetailEntity.getInfo();
                    mCourse.setText(info.getTitle());
                    mTime.setText(info.getStart_shijian());
                    mAddress.setText(info.getAddress());
                } else {
                    Toast.makeText(getContext(), "数据请求异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    private void initView(View view) {
        mCourse = (TextView) view.findViewById(R.id.course);
        mTime = (TextView) view.findViewById(R.id.time);
        mAddress = (TextView) view.findViewById(R.id.address);
        mMemberIcon = (ImageView) view.findViewById(R.id.member_icon);
    }

    private void initListener() {
        mMemberIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.member_icon:
                Intent intent = new Intent(getActivity(), NewEventDetailsActivity.class);
                intent.putExtra("event_id", "997");
                startActivity(intent);
                break;
        }
    }
}
