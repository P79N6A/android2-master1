package com.wh.wang.scroopclassproject.newproject.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

public abstract class BaseActivity extends FragmentActivity {

    private int layoutID;
    public Context mContext;
    public ImageView mTitleL;
    public TextView mTitleC;
    public ImageView mTitleR;
    public RelativeLayout mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        layoutID = getBaseLayoutId();
        if(layoutID==0){
            try {
                Log.e("DH_EXCEPTION",getString(R.string.no_Layout));
                throw new Exception(getString(R.string.no_Layout));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if(isUseTitle()){
            setTitleContentView(layoutID);
        }else{
            setContentView(layoutID);
        }

        initIntent();

        initView();

        initDatas();

        initListener();

        initOther();

    }

    private void setTitleContentView(int layoutID) {
        LinearLayout root = new LinearLayout(mContext);
        root.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View title = inflater.inflate(getBaseTitleId(),root,false);
        View content = inflater.inflate(layoutID,root,false);
        root.addView(title);
        root.addView(content);
        setContentView(root);
        mTitle = (RelativeLayout) title.findViewById(R.id.title);
        mTitleL = (ImageView) title.findViewById(R.id.title_l);
        mTitleC = (TextView) title.findViewById(R.id.title_c);

        mTitleR = (ImageView) findViewById(R.id.title_r);

    }

    //布局id
    abstract protected int getBaseLayoutId();

    //是否使用titleBar
    abstract protected boolean isUseTitle();

    //title id
    protected int getBaseTitleId(){
        return R.layout.layout_base_title;
    }

    //初始化Intent
    abstract public void initIntent();

    //初始化控件
    abstract public void initView();

    //初始化数据
    abstract public void initDatas();

    //初始化监听
    abstract public void initListener();

    //初始化其他
    abstract public void initOther();
}
