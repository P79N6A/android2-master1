package com.wh.wang.scroopclassproject.newproject.ui.fragment.casebook_frag;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {
    private TextView mTitle;
    private TextView mOtherInfo;
    private String order_id;
    private RelativeLayout mHintBody;
    private ImageView mClose;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        mTitle = (TextView) view.findViewById(R.id.title);
        mOtherInfo = (TextView) view.findViewById(R.id.other_info);
        mHintBody = (RelativeLayout) view.findViewById(R.id.hint_body);
        mClose = (ImageView) view.findViewById(R.id.close);
        String order = getArguments().getString("order");
        if("0".equals(order)){
            mTitle.setText(R.string.order_title_1);
            mOtherInfo.setText(R.string.order_hint_1);
            mHintBody.setVisibility(View.GONE);
        }else{
            order_id = getArguments().getString("order_id");
            mTitle.setText(R.string.order_title_2);
            mOtherInfo.setText(getResources().getString(R.string.order_hint_2)+order_id);
            mOtherInfo.setOnClickListener(this);
            mHintBody.setVisibility(View.VISIBLE);
            mClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHintBody.setVisibility(View.GONE);
                }
            });
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.other_info:
                Uri uri = Uri.parse("http://www.baidu.com/s?wd="+order_id);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                break;
        }
    }
}
