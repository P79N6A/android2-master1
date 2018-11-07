package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkDetailsEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.WorkRemarkEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.WorkPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.WorkRemarkAdapter;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class HomeWorkDetailsActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener, WorkRemarkAdapter.OnReplayClickListener {
    private ImageView mTitlebarbackssImageViewback;
    private TextView mTitlebarbackssName;
    private TextView mWorkTitle;
    private RelativeLayout mWorkInfo;
    private RoundedImageView mWorkAvatar;
    private TextView mWorkName;
    private ImageView mWorkAttestation;
    private TextView mTime;
    private RelativeLayout mWorkRemarkTitle;

    private TextView mLookNum;
    private TextView mRemarkNum;
    private TextView mZanNum;
    private WebView mWorkContent;
    private RecyclerView mWorkRemarkList;
    private TextView mTitlebarbackssAction;
    private LinearLayout mGoodWork;
    private ScrollView mScroll;
    private WorkPresenter mWorkPresenter = new WorkPresenter();
    private String mUser_id;
    private WorkRemarkAdapter mWorkRemarkAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private WebSettings mSettings;
    private String mList_id;
    private List<WorkDetailsEntity.CommentBean> mComment;
    private TextView mRemarkTv;
    private WorkDetailsEntity.InfoBean mInfo;
    private TextView mZyTitle;
    private EditText mRemark;
    private boolean isLocationRemark = false; //是否定位到评论
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_details);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        mList_id = getIntent().getStringExtra("list_id");
        isLocationRemark = getIntent().getBooleanExtra("location_remark", false);
    }

    private void initListener() {
        mGoodWork.setOnClickListener(this);
        mTitlebarbackssImageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRemark.setOnEditorActionListener(this);
        mRemarkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.showKeyboard(HomeWorkDetailsActivity.this);
            }
        });
        final View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));

//        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int heightDiff = decorView.getRootView().getHeight() - decorView.getHeight();
//                if (heightDiff > DisplayUtil.dip2px(MyApplication.mApplication,200)) {
//                    // 显示软键盘
//                }else{
//                    //隐藏软键盘
//                }
//            }
//        });
    }

    private void initView() {
        mTitlebarbackssImageViewback = (ImageView) findViewById(R.id.titlebarbackss_imageViewback);
        mTitlebarbackssName = (TextView) findViewById(R.id.titlebarbackss_name);
        mWorkTitle = (TextView) findViewById(R.id.work_title);
        mWorkInfo = (RelativeLayout) findViewById(R.id.work_info);
        mWorkAvatar = (RoundedImageView) findViewById(R.id.work_avatar);
        mWorkName = (TextView) findViewById(R.id.work_name);
        mWorkAttestation = (ImageView) findViewById(R.id.work_attestation);
        mTime = (TextView) findViewById(R.id.time);
        mLookNum = (TextView) findViewById(R.id.look_num);
        mRemarkNum = (TextView) findViewById(R.id.remark_num);
        mZanNum = (TextView) findViewById(R.id.zan_num);
        mWorkContent = (WebView) findViewById(R.id.work_content);
        mTitlebarbackssAction = (TextView) findViewById(R.id.titlebarbackss_action);
        mWorkRemarkList = (RecyclerView) findViewById(R.id.work_remark_list);
        mRemark = (EditText) findViewById(R.id.remark);
        mScroll = (ScrollView) findViewById(R.id.scroll);
        mRemarkTv = (TextView) findViewById(R.id.remark_tv);
        mZyTitle = (TextView) findViewById(R.id.zy_title);
        mWorkRemarkTitle = (RelativeLayout) findViewById(R.id.work_remark_title);

        mRemark.setHint("发表评论");
        mLinearLayoutManager = new LinearLayoutManager(this);
        mWorkRemarkList.setLayoutManager(mLinearLayoutManager);
        mWorkRemarkList.setHasFixedSize(true);
        mWorkRemarkList.setNestedScrollingEnabled(false);
        mGoodWork = (LinearLayout) findViewById(R.id.good_work);
        mTitlebarbackssName.setText("作业");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mWorkPresenter.getWorkDetails(mList_id, mUser_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                WorkDetailsEntity entity = (WorkDetailsEntity) value[0];
                if (entity.getCode() == 200) {
                    mInfo = entity.getInfo();
                    mComment = entity.getComment();
                    if (mInfo != null)
                        initInfo(mInfo);
                    if (mComment != null && mComment.size() > 0)
                        initComment(mComment);
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    private void initComment(List<WorkDetailsEntity.CommentBean> comment) {
        mWorkRemarkAdapter = new WorkRemarkAdapter(this, comment);
        mWorkRemarkList.setAdapter(mWorkRemarkAdapter);
        mWorkRemarkAdapter.setOnReplayClickListener(this);
    }

    private void initInfo(WorkDetailsEntity.InfoBean info) {
        Log.e("DH_DEL", info.getUser_id() + "   " + mUser_id);
        if (info.getUser_id().equals(mUser_id)) {
            mTitlebarbackssAction.setText("删除");
            mTitlebarbackssAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeWorkDetailsActivity.this);
                    builder.setTitle("是否删除作业?")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mWorkPresenter.deleteWork(mList_id, mUser_id, new OnResultListener() {
                                        @Override
                                        public void onSuccess(Object... value) {
                                            Log.e("DH_DELETE", "success");
                                            finish();
                                        }

                                        @Override
                                        public void onFault(String error) {
                                            Log.e("DH_DELETE", error);
                                        }
                                    });
                                }
                            });
                    Dialog d = builder.create();
                    d.show();
                }
            });
        } else {
            mTitlebarbackssAction.setOnClickListener(null);
        }
        if (info.getIfrenzheng() != 0) {
            mWorkAttestation.setVisibility(View.VISIBLE);
        }
        if (info.getIfteacherme() == 1 && !mUser_id.equals(info.getUser_id())) {
            if (info.getIs_youxiu() == 1) {
                mZyTitle.setText("取消优秀作业");
            } else if (info.getIs_youxiu() == 0) {
                mZyTitle.setText("评为优秀作业");
            }
            mGoodWork.setVisibility(View.VISIBLE);
        } else {
            mGoodWork.setVisibility(View.GONE);
        }

        setWebviewData(info.getUrl());
        mTime.setText(info.getShijian());
        mWorkTitle.setText(info.getTitle());
        mWorkName.setText(info.getUser_name());
        mLookNum.setText(info.getLiulan() + "人浏览");
        mZanNum.setText("赞 " + info.getZhan_sum());
        mRemarkNum.setText("评论(" + mInfo.getComment_sum() + ")");
        Glide.with(this).load(info.getUser_head()).centerCrop().into(mWorkAvatar);
        if (isLocationRemark) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLookNum.post(new Runnable() {
                        @Override
                        public void run() {
                            int[] location = new int[2];
                            mLookNum.getLocationOnScreen(location);
                            int y = location[1];
                            mScroll.smoothScrollTo(0, y);
                        }
                    });
                }
            }, 500);
        }
    }

    public void addCommentNum(int flag) {
        if (flag == 0) {
            mInfo.setComment_sum((Integer.parseInt(mInfo.getComment_sum()) + 1) + "");
        } else {
            mInfo.setComment_sum((Integer.parseInt(mInfo.getComment_sum()) - 1) + "");
        }
        mRemarkNum.setText("评论(" + mInfo.getComment_sum() + ")");
    }

    //TODO
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.good_work:
                mWorkPresenter.goodWork(mList_id, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
//                        if(mInfo.getIfrenzheng()==2){
//                            mZyTitle.setText("评为优秀作业");
//                        }else if(mInfo.getIfrenzheng()==1){
//                            mZyTitle.setText("取消优秀作业");
//                        }
                        WorkLikeEntity entity = (WorkLikeEntity) value[0];
                        if (entity.getCode() == 200) {
                            if (entity.getIs_youxiu() == 1) {
                                mZyTitle.setText("取消优秀作业");
                            } else {
                                mZyTitle.setText("评为优秀作业");
                            }
                        }
                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
                break;
        }
    }

    /**
     * 加载H5页面
     */
    private void setWebviewData(String url) {
        //设置支持javaScript
        mSettings = mWorkContent.getSettings();
        //设置支持javaScript
        mSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        mSettings.setUseWideViewPort(true);
        //增加缩放按钮
        mSettings.setBuiltInZoomControls(true);
        mSettings.setSupportZoom(true);
        //设置文字大小
        mSettings.setTextSize(WebSettings.TextSize.SMALLER);
        mSettings.setTextZoom(100);
        //不让从当前网页跳转到系统的浏览器中
        mWorkContent.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //pb_loading.setVisibility(View.GONE);
            }
        });
        mWorkContent.loadUrl(url);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_SEND == actionId || KeyEvent.ACTION_DOWN == event.getAction()) {
            remarkOrReplay(mPId, mRName, mIsReplay);
            return true;
        }
        return false;
    }

    private void remarkOrReplay(String pid, String rname, final int isReplay) {
        if (mRemark.getText() == null || StringUtils.isEmpty(mRemark.getText().toString())) {
            Toast.makeText(MyApplication.mApplication, "请输入评论内容", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = mRemark.getText().toString();
        mWorkPresenter.addWorkRemark(mList_id, mUser_id, content, pid, rname, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                WorkRemarkEntity entity = (WorkRemarkEntity) value[0];
                if (entity.getCode() == 200) {
                    WorkRemarkEntity.InfoBean info = entity.getInfo();
                    if (info != null) {
                        if (isReplay == 0) {
                            WorkDetailsEntity.CommentBean commBean = new WorkDetailsEntity.CommentBean();
                            commBean.setAvator(info.getAvator());
                            commBean.setChild(new ArrayList<WorkDetailsEntity.CommentBean.ChildBean>());
                            commBean.setContent(info.getContent());
                            commBean.setIfzhan(0);
                            commBean.setNickname(info.getNickname());
                            commBean.setRe_name(info.getRe_name());
                            commBean.setId(info.getId());
                            commBean.setUser_id(info.getUser_id());
                            commBean.setShijian(info.getShijian());
                            commBean.setUp_user(info.getUp_user());
                            mComment.add(commBean);
                            addCommentNum(0);
                        } else {
                            WorkDetailsEntity.CommentBean.ChildBean childBean = new WorkDetailsEntity.CommentBean.ChildBean();
                            childBean.setAvator(info.getAvator());
                            childBean.setContent(info.getContent());
                            childBean.setNickname(info.getNickname());
                            childBean.setRe_name(info.getRe_name());
                            childBean.setId(info.getId());
                            childBean.setUser_id(info.getUser_id());
                            childBean.setShijian(info.getShijian());
                            childBean.setUp_user(info.getUp_user());
                            mReplyBeanList.add(childBean);
                        }

                        if (mWorkRemarkAdapter == null) {
                            mWorkRemarkAdapter = new WorkRemarkAdapter(HomeWorkDetailsActivity.this, mComment);
                            mWorkRemarkList.setAdapter(mWorkRemarkAdapter);
                            mWorkRemarkAdapter.setOnReplayClickListener(HomeWorkDetailsActivity.this);
                        }
                        mWorkRemarkAdapter.notifyDataSetChanged();
                        mRemark.setText("");
                        KeyBoardUtils.hideKeyboard(HomeWorkDetailsActivity.this);
                        mScroll.post(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                } else {
                    Toast.makeText(MyApplication.mApplication, "" + entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    private List<WorkDetailsEntity.CommentBean.ChildBean> mReplyBeanList;
    private String mPId = "";
    private String mRName = "";
    private int mIsReplay = 0;

    @Override
    public void onReplayClick(List<WorkDetailsEntity.CommentBean.ChildBean> replyBeanList, String p_id, String r_name) {
        mReplyBeanList = replyBeanList;
        KeyBoardUtils.showKeyboard(this);
        mRemark.requestFocus();
        mPId = p_id;
        mRName = r_name;
        mIsReplay = 1;
        mRemark.setHint("回复:" + r_name);
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                Log.e("DH_KEY", "UP" + diff);


                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        mRemarkTv.setVisibility(View.GONE);
                        mRemark.requestFocus();
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mRemark.requestFocus();
//                            }
//                        },300);
                    }
                } else {
                    Log.e("DH_KEY", "DOWN" + diff+"  "+contentView.getPaddingBottom());
                    mRemarkTv.setVisibility(View.VISIBLE);
                    mRemark.setText("");
                    mRemark.clearFocus();
                    mPId = "";
                    mRName = "";
                    mIsReplay = 0;
                    mRemark.setHint("发表评论");
                }
            }
        };
    }
}
