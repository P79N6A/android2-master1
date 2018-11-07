package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class Introduce_2_0_Fragment extends Fragment {

    private WebView mIntroduceWeb;
    private WebSettings mSettings;
    private SGDetail2_0Entity.InfoBeanX.TeacherBean mTeacherBean;
    private RoundedImageView mTeacherHead;
    private TextView mTeacherName;
    private TextView mTeacherDuan;
    private TextView mRule;

    private String mVid;
    private Handler mHandler = new Handler();
    private String mType;

    public Introduce_2_0_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduce, container, false);
        mIntroduceWeb = (WebView) view.findViewById(R.id.introduce_web);
        mTeacherHead = (RoundedImageView) view.findViewById(R.id.teacher_head);
        mTeacherName = (TextView) view.findViewById(R.id.teacher_name);
        mTeacherDuan = (TextView) view.findViewById(R.id.teacher_duan);
        mRule = (TextView) view.findViewById(R.id.rule);
        Bundle arguments = getArguments();
        if (arguments !=null) {
            mVid = arguments.getString("vid");
            mType = arguments.getString("type");
            mTeacherBean = (SGDetail2_0Entity.InfoBeanX.TeacherBean) arguments.getSerializable("teacher");
            if (mTeacherBean!=null) {
                setWebview(mVid);
                Glide.with(MyApplication.mApplication).load(mTeacherBean.getHead()).centerCrop().into(mTeacherHead);
                mTeacherName.setText(mTeacherBean.getTeacher_name());
                mTeacherDuan.setText(mTeacherBean.getDuan());
            }

        }

        initListener();

        return view;
    }

    private void initListener() {
        mRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRuleDig(mType);
            }
        });
    }

    private Dialog mRuleDig;
    private void showRuleDig(String type) {
        mRuleDig = new Dialog(getContext(),R.style.MyDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dig_sg_rule,null,false);
        mRuleDig.setContentView(view);
        ImageView ruleImg = (ImageView) mRuleDig.findViewById(R.id.rule_img);
        if ("1".equals(type)) {
            ruleImg.setImageResource(R.drawable.tiaozhanbanguize);
        }else{
            ruleImg.setImageResource(R.drawable.zixuebanguize);
        }
        ruleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRuleDig.dismiss();
            }
        });
        Window dialogWindow = mRuleDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(MyApplication.mApplication, 290);
        lp.height = DisplayUtil.dip2px(MyApplication.mApplication, 430);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        mRuleDig.show();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void setWebview(String id) {
        String url = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=1&id="+id;
        //设置支持javaScript
        mSettings = mIntroduceWeb.getSettings();
        mSettings.setDomStorageEnabled(false);
        //设置支持javaScript
        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        //设置文字大小
        mSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        mIntroduceWeb.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(final WebView view, String url) {
//                if(mOnWebViewHeightClickListener!=null){
//                    view.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mIntroduceWeb.measure(0, 0);
//                            mHandler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Log.e("Dh_H",mIntroduceWeb.getMeasuredHeight()+"  "+view.getContentHeight());
//                                    int h = mIntroduceWeb.getMeasuredHeight()+ DisplayUtil.dip2px(MyApplication.mApplication,154);
//                                    mOnWebViewHeightClickListener.onWebViewHeightClick(h);
//                                }
//                            },300);
//
//                        }
//                    });
//                }

            }
        });
        mIntroduceWeb.loadUrl(url);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mIntroduceWeb!=null){
            ViewParent parent = mIntroduceWeb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mIntroduceWeb);
            }

            mIntroduceWeb.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mIntroduceWeb.getSettings().setJavaScriptEnabled(false);
            mIntroduceWeb.clearHistory();
            mIntroduceWeb.clearView();
            mIntroduceWeb.removeAllViews();
            mIntroduceWeb.destroy();
        }
    }

    private OnWebViewHeightClickListener mOnWebViewHeightClickListener;

    public void setOnWebViewHeightClickListener(OnWebViewHeightClickListener onWebViewHeightClickListener) {
        mOnWebViewHeightClickListener = onWebViewHeightClickListener;
    }

    public interface OnWebViewHeightClickListener{
        void onWebViewHeightClick(int h);
    }
}
