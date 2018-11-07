package com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.callback.OnRegisterNextClickListener;

public class SubmitResultFragment extends Fragment implements View.OnClickListener {
    private ImageView mResultIcon;
    private TextView mNext;
    private OnRegisterNextClickListener mOnRegisterNextClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnRegisterNextClickListener){
            mOnRegisterNextClickListener = (OnRegisterNextClickListener) activity;
        }else{
            Log.e("DH_NEXT_LISTENER","未绑定监听");
            return;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_result, container, false);
        initView(view);
        mNext.setOnClickListener(this);
        return view;
    }

    private void initView(View view) {
        mResultIcon = (ImageView) view.findViewById(R.id.result_icon);
        mNext = (TextView) view.findViewById(R.id.next);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                if(mOnRegisterNextClickListener!=null){
                    mOnRegisterNextClickListener.OnNextClick(-1);
                }
                break;
        }
    }
}
