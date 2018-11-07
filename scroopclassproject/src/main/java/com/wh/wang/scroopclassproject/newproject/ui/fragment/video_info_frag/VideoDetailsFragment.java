package com.wh.wang.scroopclassproject.newproject.ui.fragment.video_info_frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CollectEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CollectPresenter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailsFragment extends Fragment implements View.OnClickListener {
    private SumUpEntity.InfoBean mInfoBean;
    private TextView mVideoTitle;
    private TextView mPlayGood;
    private TextView mPlayNum;
    private FrameLayout mCollectVideo;
    private ImageView mVideoCollect;
    private TextView mVideoFitpersonContent;
    private RoundedImageView mVideoTeacherAvatar;
    private TextView mVideoTeacherName;
    private TextView mVideoTeacherInfo;
    //    private WebView mVideoDetailWebview;
    private FrameLayout mCacheVideo;
    //    private MyXWalkView mVideoDetailXwalk;
    private TextView mSunUpFitperson;
    private View mFitpersonCut;
    private TextView mVideoTeacher;

    private boolean mIsCollect;
    private String mLogin_rand_str;
    private String mUserId;
    private String mCourseId;
    private CollectPresenter mCollectPresenter = new CollectPresenter();
    private List<SumUpEntity.DirBean> mDirBeanList;
    private boolean mIsShowCache;
    private FrameLayout mVideoDetailWebview;
    private FragmentActivity mActivity;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setWebviewData();
                    break;
                case 1:
                    setData();
                    break;
            }
        }
    };

    public VideoDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnCacheClickListener) {
            mOnCacheClickListener = (OnCacheClickListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_details, container, false);
        mVideoTitle = (TextView) view.findViewById(R.id.video_title);
        mPlayGood = (TextView) view.findViewById(R.id.play_good);
        mPlayNum = (TextView) view.findViewById(R.id.play_num);
        mCollectVideo = (FrameLayout) view.findViewById(R.id.collect_video);
        mVideoCollect = (ImageView) view.findViewById(R.id.video_collect);
        mVideoFitpersonContent = (TextView) view.findViewById(R.id.video_fitperson_content);
        mVideoTeacherAvatar = (RoundedImageView) view.findViewById(R.id.video_teacher_avatar);
        mVideoTeacherName = (TextView) view.findViewById(R.id.video_teacher_name);
        mVideoTeacherInfo = (TextView) view.findViewById(R.id.video_teacher_info);
//        mVideoDetailWebview = (WebView) view.findViewById(R.id.video_detail_webview);
        mSunUpFitperson = (TextView) view.findViewById(R.id.sun_up_fitperson);
        mFitpersonCut = (View) view.findViewById(R.id.fitperson_cut);
        mVideoTeacher = (TextView) view.findViewById(R.id.video_teacher);

        mCacheVideo = (FrameLayout) view.findViewById(R.id.cache_video);
        mVideoDetailWebview = (FrameLayout) view.findViewById(R.id.video_detail_webview);
        initListener();
        return view;
    }

    public void setDetailsInfo(SumUpEntity.InfoBean infoBean, List<SumUpEntity.DirBean> dirBeanList) {
        mDirBeanList = dirBeanList;
        mLogin_rand_str = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "login_rand_str", "");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mInfoBean = infoBean;
        mCourseId = mInfoBean.getId();
//        setWebviewData();

        setData();
    }

    public void setData(){
        /**
         * 同步，防止数据设置时控件为null
         */
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mVideoTitle==null){
                    mHandler.sendEmptyMessageDelayed(1,200);
                    return;
                }
                mActivity = getActivity();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        if (mDirBeanList != null) {
//                            for (int i = 0; i < mDirBeanList.size(); i++) {
//                                SumUpEntity.DirBean dirBean = mDirBeanList.get(i);
//                                if ("1".equals(dirBean.getIs_live()) && "1".equals(dirBean.getLive_status())) {
//                                    break;
//                                }
//                                if (i == mDirBeanList.size() - 1) {
////                                    mHandler.sendEmptyMessage(0);
//                                }
//                            }
//                        } else {
//
//                            mHandler.sendEmptyMessage(0);
//                        }
//                    }
//                }.start();
                setWebviewData();
                mVideoTitle.setText(mInfoBean.getTitle());
                mPlayGood.setText("好评度" + mInfoBean.getGood());
                mPlayNum.setText(mInfoBean.getLearn() + "人已学");
                if(StringUtils.isNotEmpty(mInfoBean.getStudent())){
                    mSunUpFitperson.setVisibility(View.VISIBLE);
                    mFitpersonCut.setVisibility(View.VISIBLE);
                    mVideoFitpersonContent.setVisibility(View.VISIBLE);
                    mVideoFitpersonContent.setText(mInfoBean.getStudent());
                }else{
                    mSunUpFitperson.setVisibility(View.GONE);
                    mFitpersonCut.setVisibility(View.GONE);
                    mVideoFitpersonContent.setVisibility(View.GONE);
                }
                if(StringUtils.isNotEmpty(mInfoBean.getName())){
                    mVideoTeacherName.setVisibility(View.VISIBLE);
                    mVideoTeacherInfo.setVisibility(View.VISIBLE);
                    mVideoTeacherAvatar.setVisibility(View.VISIBLE);
                    mVideoTeacher.setVisibility(View.VISIBLE);
                    mVideoTeacherName.setText(mInfoBean.getName());
                    mVideoTeacherInfo.setText(mInfoBean.getDuan());
                    Glide.with(MyApplication.mApplication).load(mInfoBean.getHead()).centerCrop().into(mVideoTeacherAvatar);
                }else{
                    mVideoTeacherName.setVisibility(View.GONE);
                    mVideoTeacherInfo.setVisibility(View.GONE);
                    mVideoTeacherAvatar.setVisibility(View.GONE);
                    mVideoTeacher.setVisibility(View.GONE);
                }
                if ("".equals(mInfoBean.getCollect_status())) {
                    mIsCollect = false;
                    mVideoCollect.setImageResource(R.drawable.video_collect_nor);
                } else {
                    mIsCollect = true;
                    mVideoCollect.setImageResource(R.drawable.video_collect_press);
                }
//                mVideoDetailXwalk.load("http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=1&id=" + mCourseId,null);
            }
        },200);

    }
//    public void removeWebView(){
//        if(mVideoDetailWebview.getChildCount()>0){
//            webView.stopLoading();
//            webView.destroy();
//            mVideoDetailWebview.removeAllViews();
//            webView = null;
//            settings = null;
//        }
//    }
    /**
     * 加载H5页面
     */
    WebSettings settings;
    WebView webView;

    private void setWebviewData() {
        LayoutInflater inflater = LayoutInflater.from(MyApplication.mApplication);
        webView = (WebView) inflater.inflate(R.layout.video_web, mVideoDetailWebview, false);
//         webView = new WebView(getContext());
        settings = webView.getSettings();
        String url = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=1&id=" + mCourseId;
        settings.setDomStorageEnabled(false);
        //设置支持javaScript
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        //设置文字大小
        settings.setTextSize(WebSettings.TextSize.SMALLER);
        settings.setTextZoom(100);
        webView.loadUrl(url);
        mVideoDetailWebview.addView(webView);
    }

    private void initListener() {
        mCollectVideo.setOnClickListener(this);
        mCacheVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect_video:
                if (StringUtils.isNotEmpty(mUserId)) {
                    changeCollectState();
                } else {
                    Intent intent = new Intent(getContext(), LoginNewActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cache_video:
                if (mOnCacheClickListener != null) {
                    mOnCacheClickListener.onCacheClick();
                }
                break;
        }
    }

    /**
     * 点赞
     */
    private void changeCollectState() {
        if (mIsCollect) {
            mVideoCollect.setImageResource(R.drawable.video_collect_nor);
            mIsCollect = false;
        } else {
            mVideoCollect.setImageResource(R.drawable.video_collect_press);
            mIsCollect = true;
        }
        mCollectPresenter.changeCollect(mCourseId, mUserId, "1", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Log.e("DH_COLLECT", "Success");
                CollectEntity entity = (CollectEntity) value[0];
                if (entity.getErr() == 0) {
                    Toast.makeText(MyApplication.mApplication, "收藏成功", Toast.LENGTH_SHORT).show();
                } else if (entity.getErr() == -1) {
                    Toast.makeText(MyApplication.mApplication, "收藏取消", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.mApplication, "收藏失败", Toast.LENGTH_SHORT).show();
                    if (mIsCollect) {
                        mVideoCollect.setImageResource(R.drawable.video_collect_nor);
                        mIsCollect = false;
                    } else {
                        mVideoCollect.setImageResource(R.drawable.video_collect_press);
                        mIsCollect = true;
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_COLLECT", error);
            }
        });
    }

    private OnCacheClickListener mOnCacheClickListener;

    public interface OnCacheClickListener {
        void onCacheClick();
    }

    @Override
    public void onDestroyView() {
        if( webView!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();

        }
        super.onDestroyView();

    }


}
