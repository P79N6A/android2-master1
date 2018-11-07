package com.wh.wang.scroopclassproject.newproject.ui.fragment.scan_resule_frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OldResultFragment extends Fragment {


    private TextView mName;
    private TextView mTel;
    private TextView mCourse;
    public OldResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_old_result, container, false);
        mName = (TextView) view.findViewById(R.id.name);
        mTel = (TextView) view.findViewById(R.id.tel);
        mCourse = (TextView) view.findViewById(R.id.course);
        initData();
        return view;
    }

    private void initData() {
        String id = getArguments().getString("id");
        String name = getArguments().getString("name");
        String tel = getArguments().getString("tel");
        String title = getArguments().getString("title");
        Log.e("DH_SCAN","name:"+name);
        mName.setText(name);
        mTel.setText(tel);
        mCourse.setText(title);
    }

}
