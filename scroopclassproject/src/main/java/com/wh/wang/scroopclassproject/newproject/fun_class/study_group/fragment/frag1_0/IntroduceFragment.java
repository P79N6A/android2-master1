package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetailEntity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroduceFragment extends Fragment {

    private WebView mIntroduceWeb;
    private WebSettings mSettings;
    private SGDetailEntity.InfoBeanX.TeacherBean mTeacherBean;
    private RoundedImageView mTeacherHead;
    private TextView mTeacherName;
    private TextView mTeacherDuan;
    private String mVid;
    private Handler mHandler = new Handler();
    public IntroduceFragment() {
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

        Bundle arguments = getArguments();
        if (arguments !=null) {
            mVid = arguments.getString("vid");
            mTeacherBean = (SGDetailEntity.InfoBeanX.TeacherBean) arguments.getSerializable("teacher");
            if (mTeacherBean!=null) {
                setWebview(mVid);
                Glide.with(MyApplication.mApplication).load(mTeacherBean.getHead()).centerCrop().into(mTeacherHead);
                mTeacherName.setText(mTeacherBean.getTeacher_name());
                mTeacherDuan.setText(mTeacherBean.getDuan());
            }

        }



        return view;
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
                if(mOnWebViewHeightClickListener!=null){
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            mIntroduceWeb.measure(0, 0);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("Dh_H",mIntroduceWeb.getMeasuredHeight()+"  "+view.getContentHeight());
                                    int h = mIntroduceWeb.getMeasuredHeight()+ DisplayUtil.dip2px(MyApplication.mApplication,154);
                                    mOnWebViewHeightClickListener.onWebViewHeightClick(h);
                                }
                            },300);

                        }
                    });
                }

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
