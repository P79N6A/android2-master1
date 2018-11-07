package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

public class NewOrderActivity extends Activity {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private RecyclerView mOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        initView();
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mOrderList = (RecyclerView) findViewById(R.id.order_list);
    }
}
