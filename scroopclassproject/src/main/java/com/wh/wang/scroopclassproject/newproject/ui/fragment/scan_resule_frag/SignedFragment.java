package com.wh.wang.scroopclassproject.newproject.ui.fragment.scan_resule_frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignedFragment extends Fragment {

    private TextView mName;
    private TextView mTel;

    public SignedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signed, container, false);

        mName = (TextView) view.findViewById(R.id.name);
        mTel = (TextView) view.findViewById(R.id.tel);
        initData();
        return view;
    }

    private void initData() {
        if (getArguments()!=null) {
            String name = getArguments().getString("name");
            String tel = getArguments().getString("tel");
            mName.setText("姓名："+name);
            mTel.setText("手机号："+tel);
        }
    }

}
