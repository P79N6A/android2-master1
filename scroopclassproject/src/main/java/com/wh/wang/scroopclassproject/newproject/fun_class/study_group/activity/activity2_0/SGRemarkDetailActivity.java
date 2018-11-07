package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.base.BaseActivity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0.SGRemarkAdapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRemarkBean;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGRemarkDetailEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CommentEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CommentPresenter;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SGRemarkDetailActivity extends BaseActivity implements SGRemarkAdapter.OnReplayClickListener, SGRemarkAdapter.OnRemarkItemClickListener {
    private RecyclerView mRemarkList;
    private SGRemarkAdapter mSGRemarkAdapter;
    private CommentPresenter mCommentPresenter = new CommentPresenter();
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private EditText mRemarkEdit;
    private TextView mAddRemark;
    private TextView mLikeNum;
    private ImageView mLikeIcon;
    private TextView mRemarkNum;
    private ImageView mRemarkIcon;
    private TextView mDelTv;
    private ImageView mDelIcon;


    private RoundedImageView mAvatar;
    private TextView mName;
    private TextView mTime;
    private TextView mContent;
    private String mId;
    private String mUserId;
    private String mVid;
    private List<SGRemarkBean> mReply;
    private String mParentId;
    private String mReName;
    private String mNickname;
    private Map<String, String> mMap = new HashMap<>();

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_sgremark_detail;
    }

    @Override
    protected boolean isUseTitle() {
        return true;
    }

    @Override
    public void initIntent() {
        mId = getIntent().getStringExtra("id");
        mVid = getIntent().getStringExtra("vid");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
        mParentId = mId;
    }

    @Override
    public void initView() {
        mRemarkList = (RecyclerView) findViewById(R.id.remark_list);
        mRemarkList.setLayoutManager(new LinearLayoutManager(this));
        mRemarkList.setHasFixedSize(true);
        mRemarkList.setNestedScrollingEnabled(false);
        mRemarkEdit = (EditText) findViewById(R.id.remark_edit);
        mAddRemark = (TextView) findViewById(R.id.add_remark);

        mAvatar = (RoundedImageView) findViewById(R.id.avatar);
        mName = (TextView) findViewById(R.id.name);
        mTime = (TextView) findViewById(R.id.time);
        mContent = (TextView) findViewById(R.id.content);

        mLikeNum = (TextView) findViewById(R.id.like_num);
        mLikeIcon = (ImageView) findViewById(R.id.like_icon);
        mRemarkNum = (TextView) findViewById(R.id.remark_num);
        mRemarkIcon = (ImageView) findViewById(R.id.remark_icon);
        mDelTv = (TextView) findViewById(R.id.del_tv);
        mDelIcon = (ImageView) findViewById(R.id.del_icon);

        mLikeNum.setVisibility(View.GONE);
        mLikeIcon.setVisibility(View.GONE);
        mRemarkNum.setVisibility(View.GONE);
        mRemarkIcon.setVisibility(View.GONE);
        mDelTv.setVisibility(View.GONE);
        mDelIcon.setVisibility(View.GONE);

        final View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
    }

    @Override
    public void initDatas() {
        mTitleC.setText("评论详情");
//        List<String> remarkList = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            remarkList.add("i:"+i);
//        }
//        mSGRemarkAdapter = new SGRemarkAdapter(this, remarkList,"1");
//        mRemarkList.setAdapter(mSGRemarkAdapter);

        Log.e("PARAM","vid:"+mVid+"  id:"+mId);
        LoadingUtils.getInstance().showNetLoading(this);
        mStudyGroupPresenter.getRemarkDetail(mVid, mId,mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                LoadingUtils.getInstance().hideNetLoading();
                SGRemarkDetailEntity sgRemarkEntity = (SGRemarkDetailEntity) value[0];
                if (sgRemarkEntity.getInfo()!=null) {
                    SGRemarkBean sgRemarkBean =  sgRemarkEntity.getInfo();

                    Glide.with(MyApplication.mApplication).load(sgRemarkBean.getAvator()).centerCrop().into(mAvatar);
                    mNickname = sgRemarkBean.getNickname();
                    mName.setText(mNickname);
                    mReName = mNickname;
                    mTime.setText(sgRemarkBean.getShijian());
                    mContent.setText(sgRemarkBean.getContent());

                    mReply = sgRemarkBean.getReply();
                    if(mReply!=null&&mReply.size()>0){
                        mSGRemarkAdapter = new SGRemarkAdapter(SGRemarkDetailActivity.this, mReply, "1",mNickname);
                        mRemarkList.setAdapter(mSGRemarkAdapter);
                        mSGRemarkAdapter.setOnReplayClickListener(SGRemarkDetailActivity.this);
                        mSGRemarkAdapter.setOnRemarkItemClickListener(SGRemarkDetailActivity.this);
                    }

                }
            }

            @Override
            public void onFault(String error) {
                LoadingUtils.getInstance().hideNetLoading();
                Log.e("DH_COMMIT_ERROR",error);
            }
        });
//        mStudyGroupPresenter.getRemarkDetailJSON(mVid, mId, new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//
//            }
//
//            @Override
//            public void onFault(String error) {
//
//            }
//        });

    }

    @Override
    public void initListener() {
        mTitleL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAddRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRemarkEdit.getText()==null|| mRemarkEdit.getText().toString()==null|| StringUtils.isEmpty(mRemarkEdit.getText().toString().trim())) {
                    Toast.makeText(MyApplication.mApplication, "请填写内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoadingUtils.getInstance().showNetLoading(SGRemarkDetailActivity.this);
                String content = mRemarkEdit.getText().toString();
                mMap.put("id",mVid);
                mMap.put("content",content);
                mMap.put("type","1");
                mMap.put("user_id",mUserId);
                mMap.put("parentid",mParentId);
                mMap.put("re_name",mReName+"");
//                Log.e("DH_COMMIT","vid:"+mVid);
                mCommentPresenter.addComment(mMap, new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        mRemarkEdit.setText("");
                        LoadingUtils.getInstance().hideNetLoading();
                        CommentEntity commentEntity = (CommentEntity) value[0];
                        if (commentEntity.getInfo()!=null) {
                            Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                            CommentEntity.InfoBean info = commentEntity.getInfo();
                            if(mReply==null){
                                mReply = new ArrayList<>();
                            }
                            SGRemarkBean sgRemarkBean = new SGRemarkBean();
                            sgRemarkBean.setAvator(info.getAvator());
                            sgRemarkBean.setContent(info.getContent());
                            sgRemarkBean.setId(info.getId());
                            sgRemarkBean.setRe_name(mReName);
                            sgRemarkBean.setNickname(info.getNickname());
                            sgRemarkBean.setNum("0");
                            sgRemarkBean.setThumbs_up_num("0");
                            sgRemarkBean.setThumbs_up_status("0");
                            sgRemarkBean.setUser_id(mUserId);
                            sgRemarkBean.setShijian(info.getShijian());
                            sgRemarkBean.setParentid(mId);
                            mReply.add(0,sgRemarkBean);
                            if(mSGRemarkAdapter==null){
                                mSGRemarkAdapter = new SGRemarkAdapter(SGRemarkDetailActivity.this, mReply, "1",mNickname);
                                mRemarkList.setAdapter(mSGRemarkAdapter);
                                mSGRemarkAdapter.setOnReplayClickListener(SGRemarkDetailActivity.this);
                                mSGRemarkAdapter.setOnRemarkItemClickListener(SGRemarkDetailActivity.this);
                            }else{
                                mSGRemarkAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onFault(String error) {
                        LoadingUtils.getInstance().hideNetLoading();
                        Toast.makeText(mContext, "提交异常"+error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void initOther() {

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
                        mRemarkEdit.requestFocus();
                    }
                } else {
                    mRemarkEdit.setText("");
                    mRemarkEdit.clearFocus();
                    mParentId = mId;
                    mReName = mNickname;
                    mRemarkEdit.setHint("回复");
                }
            }
        };
    }

    @Override
    public void onReplayClick(String parentid, String rename) {
        mParentId = parentid;
        mReName = rename;
        mRemarkEdit.setHint("回复:"+rename);
        KeyBoardUtils.showKeyboard(this);
    }

    @Override
    public void onRemarkClick(String id) {

    }

    @Override
    public void onDelClick(String id, final int pos) {
        mCommentPresenter.deleteCommentJson(id, mVid, "1", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                if (mReply!=null&&mReply.size()>0) {
                    mReply.remove(pos);

                    if (mSGRemarkAdapter!=null) {
                        mSGRemarkAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFault(String error) {
                Toast.makeText(mContext, "删除异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
