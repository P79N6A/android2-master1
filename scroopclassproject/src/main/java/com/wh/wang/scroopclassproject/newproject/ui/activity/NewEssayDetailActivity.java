package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CommentEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayCommentBean;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayDetailEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.EssayLikeEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CommentPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EssayDetailPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.EssayRemarkAdapter;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewEssayDetailActivity extends BaseActivity implements OnResultListener, View.OnClickListener, TextView.OnEditorActionListener, EssayRemarkAdapter.OnRemarkClickListener {

    private String mEssayId;
    private TextView mReadTitle;
    private TextView mReadInfo;
    private WebView mReadContent;
    private ImageView mLike;
    private TextView mLikeTv;
    private RecyclerView mRemarkList;
    private EditText mRemark;
    private TextView mSendRemark;

    private EssayDetailPresenter mEssayDetailPresenter = new EssayDetailPresenter();
    private CommentPresenter mCommentPresenter = new CommentPresenter();
    private String mUserId;
    private WebSettings mSettings;
    private String mReName = "";
    private Map<String,String> mMap;
    private String mParentId = "0";
    private int mIsRepaly = 0;  //0 评论  1 回复
    private ScrollView mEssayScroll;
    private TextView mReadTv;

    private EssayRemarkAdapter mEssayRemarkAdapter;
    private EmptyRecycleAdapter mEmptyRecycleAdapter;
    private List<EssayCommentBean> mCommentList;
    private String mNickname;
    private Handler mHandler = new Handler();

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_new_essay_detail;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mEssayId = getIntent().getStringExtra("id");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
    }

    @Override
    public void initView() {
        mReadTitle = (TextView) findViewById(R.id.read_title);
        mReadInfo = (TextView) findViewById(R.id.read_info);
        mReadContent = (WebView) findViewById(R.id.read_content);
        mLike = (ImageView) findViewById(R.id.like);
        mLikeTv = (TextView) findViewById(R.id.like_tv);
        mRemark = (EditText) findViewById(R.id.remark);
        mEssayScroll = (ScrollView) findViewById(R.id.essay_scroll);
        mReadTv = (TextView) findViewById(R.id.read_tv);
        mRemarkList = (RecyclerView) findViewById(R.id.remark_list);
        mRemarkList.setNestedScrollingEnabled(false);
        mRemarkList.setHasFixedSize(false);
        mRemarkList.setLayoutManager(new LinearLayoutManager(this));
        mSendRemark = (TextView) findViewById(R.id.send_remark);

        final View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
    }

    @Override
    public void initDatas() {
        mTitleC.setText("文章详情");
        setEssayContent(mEssayId);
    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLike.setOnClickListener(this);
        mLikeTv.setOnClickListener(this);
        mSendRemark.setOnClickListener(this);
        mRemark.setOnEditorActionListener(this);


        mReadContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            // URL拦截
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("DH_ESSAY",url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void initOther() {

    }

    @Override
    public void onSuccess(Object... value) {
        EssayDetailEntity essayDetailEntity = (EssayDetailEntity) value[0];
        mReadTitle.setText(essayDetailEntity.getTitle());
        mReadInfo.setText("作者：" + essayDetailEntity.getAuthor() + "   " + essayDetailEntity.getPublish_shijian());
        if (StringUtils.isNotEmpty(essayDetailEntity.getCollect_status())) {
            mLike.setImageResource(R.drawable.dianzanshixin);
        } else {
            mLike.setImageResource(R.drawable.dianzankixin);
        }
        if (mEssayRemarkAdapter==null) {
            mCommentList = essayDetailEntity.getCommentList();
            mEssayRemarkAdapter = new EssayRemarkAdapter(this,mCommentList);
            mEssayRemarkAdapter.setOnRemarkClickListener(this);
        }
        mEmptyRecycleAdapter = new EmptyRecycleAdapter(mEssayRemarkAdapter, R.layout.layout_sg_remark_empty);
        mRemarkList.setAdapter(mEmptyRecycleAdapter);
    }

    @Override
    public void onFault(String error) {
    }


    /**
     * 设置数据
     */
    private void setEssayContent(String id) {
        String url = "http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=2&id=" + id;
        //设置支持javaScript
        mSettings = mReadContent.getSettings();
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
        mReadContent.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mReadContent.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        if (mReadContent != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mReadContent.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mReadContent);
            }

            mReadContent.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mReadContent.getSettings().setJavaScriptEnabled(false);
            mReadContent.clearHistory();
            mReadContent.clearView();
            mReadContent.removeAllViews();
            mReadContent.destroy();

        }
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");

        if (StringUtils.isNotEmpty(mUserId)) {
            mNickname = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "nickname", "");
            mEssayDetailPresenter.getEssayDetail(mEssayId, mUserId, this);
        }else{
            Intent intent = new Intent(this,LoginNewActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like:
            case R.id.like_tv:
                if(StringUtils.isEmpty(mUserId)){
                    Intent intent = new Intent(this, LoginNewActivity.class);
                    startActivity(intent);
                    return;
                }
                mEssayDetailPresenter.essayLike(mEssayId, mUserId, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        EssayLikeEntity essayLikeEntity = (EssayLikeEntity) value[0];
                        switch (essayLikeEntity.getErr()) {
                            case 0:
                                mLike.setImageResource(R.drawable.dianzanshixin);
                                Toast.makeText(MyApplication.mApplication, "成功收藏", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                mLike.setImageResource(R.drawable.dianzankixin);
                                Toast.makeText(MyApplication.mApplication, "取消收藏", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(MyApplication.mApplication, "收藏异常", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFault(String error) {

                    }
                });
                break;
            case R.id.send_remark:
                addEssayComment();
                break;
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (EditorInfo.IME_ACTION_SEND == actionId || KeyEvent.ACTION_DOWN == event.getAction()||
//                (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
//            addEssayComment();
//            return true;
//        }
        return false;
    }

    private void addEssayComment() {

        if(StringUtils.isEmpty(mUserId)){
            Intent intent = new Intent(this, LoginNewActivity.class);
            startActivity(intent);
            return;
        }
        if(mRemark.getText()==null|| StringUtils.isEmpty(mRemark.getText().toString())){
            Toast.makeText(MyApplication.mApplication, "请输入评论内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mMap==null) {
            mMap = new HashMap<>();
        }
        String content = mRemark.getText().toString();
        mMap.put("id",mEssayId);
        mMap.put("content",content);
        mMap.put("type","2");
        mMap.put("user_id",mUserId);
        mMap.put("parentid",mParentId);
        mMap.put("re_name",mReName);
        mCommentPresenter.addComment(mMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                KeyBoardUtils.hideKeyboard(NewEssayDetailActivity.this);
                Toast.makeText(MyApplication.mApplication, "评论成功", Toast.LENGTH_SHORT).show();
                CommentEntity commentEntity = (CommentEntity) value[0];
                mRemark.setText("");
                if (commentEntity.getInfo()!=null) {
                    CommentEntity.InfoBean info = commentEntity.getInfo();
                    EssayCommentBean essayCommentBean = new EssayCommentBean();
                    essayCommentBean.setAvator(info.getAvator());
                    essayCommentBean.setContent(info.getContent());
                    essayCommentBean.setId(info.getId());
                    essayCommentBean.setNickname(info.getNickname());
                    essayCommentBean.setParentid(info.getParentid());
                    essayCommentBean.setRe_name(info.getRe_name());
                    essayCommentBean.setShijian(info.getShijian());
                    essayCommentBean.setUp_user(info.getUp_user());
                    essayCommentBean.setUser_id(info.getUser_id());
                    List<EssayCommentBean> commentBeanList = new ArrayList<>();
                    essayCommentBean.setChild(commentBeanList);
                    if(mIsRepaly==0){
                        mCommentList.add(0,essayCommentBean);
                        if (mEmptyRecycleAdapter!=null) {
                            mEmptyRecycleAdapter.notifyDataSetChanged();
                        }
                    }else{
                        List<EssayCommentBean> child = mCommentList.get(mCurrentPos).getChild();
                        child.add(0,essayCommentBean);
                        if (mEmptyRecycleAdapter!=null) {
                            mEmptyRecycleAdapter.notifyItemChanged(mCurrentPos);
                        }
                    }


                }

//                if(mIsRepaly==0){
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mReadTv.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    int[] location = new int[2];
//                                    mReadTv.getLocationOnScreen(location);
//                                    int y = location[1]-mReadTv.getMeasuredHeight();
//                                    mEssayScroll.smoothScrollTo(0, y);
//                                }
//                            });
//                        }
//                    }, 500);
//                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }


    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;


                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        mRemark.requestFocus();
                    }
                    Log.e("DH_KEY", "UP" + diff);
                    mSendRemark.setVisibility(View.VISIBLE);
                } else {
                    Log.e("DH_KEY", "DOWN" + diff);
                    mRemark.setText("");
                    mRemark.clearFocus();
                    mParentId = "0";
                    mReName = "";
                    mIsRepaly = 0;
                    mSendRemark.setVisibility(View.GONE);
                    mRemark.setHint("我来说几句");
                }
            }
        };
    }

    private int mCurrentPos = 0;
    @Override
    public void onRemarkClick(int pos,String parentId,String reName) {
        mCurrentPos = pos;
        mParentId = parentId;
        mReName = reName;
        mIsRepaly = 1;
        mRemark.requestFocus();
        KeyBoardUtils.showKeyboard(this);
        mRemark.setHint(" 回复 "+mReName);
    }

    @Override
    public void onDelRemark(final int pos) {
        delComment(mCommentList.get(pos).getId(), new OnDelClickListener() {
            @Override
            public void onDelSuccess() {
                mCommentList.remove(pos);
                if(mEmptyRecycleAdapter!=null){
                    mEmptyRecycleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDelReply(final int pos, final List<EssayCommentBean> childs, final int child_pos) {
        delComment(childs.get(child_pos).getId(), new OnDelClickListener() {
            @Override
            public void onDelSuccess() {
                childs.remove(child_pos);
                if(mEmptyRecycleAdapter!=null){
                    mEmptyRecycleAdapter.notifyItemChanged(pos);
                }
            }
        });
    }

    public interface OnDelClickListener{
        void onDelSuccess();
    }

    public void delComment(String id,final OnDelClickListener onDelClickListener){
        mCommentPresenter.deleteCommentJson(id, mEssayId, "2", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                onDelClickListener.onDelSuccess();
            }

            @Override
            public void onFault(String error) {

            }
        });
    }
}
