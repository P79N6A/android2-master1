package com.wh.wang.scroopclassproject.newproject.ui.fragment.video_info_frag;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CommentEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CommentPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.VideoRemarkAdapter;
import com.wh.wang.scroopclassproject.utils.KeyBoardUtils;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoCommentFragment extends Fragment implements VideoRemarkAdapter.OnReplayClickListener, VideoRemarkAdapter.OnDeleteRemarkClickListener, VideoRemarkAdapter.OnDeleteReplayClickListener {
    private EditText mCommentEdit;
    private RelativeLayout mShareComment;
    private RelativeLayout mCommentInput;
    private RecyclerView mCommentList;
    private List<SumUpEntity.CommentBean> mCommentBeanList;
    private VideoRemarkAdapter mVideoRemarkAdapter;
    private CommentPresenter mCommentPresenter = new CommentPresenter();
    private Map<String,String> mMap = new HashMap<>();
    private Button mSendComment;
    private String mUserId;
    private String mCourseId;
    private int mIsReplay = 0;
    private List<SumUpEntity.CommentBean.ReplyBean> mReplyBeanList;
    private String mParentId = "0";
    private String mReplayName = "";

    public VideoCommentFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnCommentKeyboardClickListener){
            mOnCommentKeyboardClickListener = (OnCommentKeyboardClickListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_comment, container, false);
        mCommentEdit = (EditText) view.findViewById(R.id.comment_edit);
        mCommentInput = (RelativeLayout) view.findViewById(R.id.comment_input);
        mCommentList = (RecyclerView) view.findViewById(R.id.comment_list);

        mCommentList.setLayoutManager(new LinearLayoutManager(getContext()));
        View decorView = getActivity().getWindow().getDecorView();
        View contentView = getActivity().findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        view.findViewById(R.id.share_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsReplay = 0;
                mParentId = "0";
                mReplayName = "";
                showInput();
                if(mOnCommentKeyboardClickListener!=null){
                    mOnCommentKeyboardClickListener.onKeyboardClick();
                }
            }
        });
        view.findViewById(R.id.send_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment(mParentId,mReplayName,mIsReplay);
            }
        });
        return view;
    }

    private void addComment(String parentid, String re_name, final int isReplay) {
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");

        if(StringUtils.isEmpty(mUserId)){
            Intent intent = new Intent(getContext(), LoginNewActivity.class);
            startActivity(intent);
            return;
        }
        if(mCommentEdit.getText()==null|| StringUtils.isEmpty(mCommentEdit.getText().toString())){
            Toast.makeText(MyApplication.mApplication, "请输入评论内容", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = mCommentEdit.getText().toString();
        mMap.put("id",mCourseId);
        mMap.put("content",content);
        mMap.put("type","1");
        mMap.put("user_id",mUserId);
        mMap.put("parentid",parentid);
        mMap.put("re_name",re_name);
        mCommentPresenter.addComment(mMap, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                CommentEntity entity = (CommentEntity) value[0];
                if(entity.getCode()==200){
                    Log.e("DH_COMMENT","Comment Success");
                    if(entity.getInfo()!=null){
                        CommentEntity.InfoBean info = entity.getInfo();
                        if(isReplay==0){
                            SumUpEntity.CommentBean commentBean = new SumUpEntity.CommentBean();
                            commentBean.setAvator(info.getAvator());
                            commentBean.setContent(info.getContent());
                            commentBean.setNickname(info.getNickname());
                            commentBean.setId(info.getId());
                            commentBean.setShijian(info.getShijian());
                            commentBean.setUser_id(info.getUser_id());
                            commentBean.setUp_user(info.getUp_user());
                            commentBean.setParentid(info.getParentid());
                            commentBean.setRe_name(info.getRe_name());
                            commentBean.setChild(new ArrayList<SumUpEntity.CommentBean.ReplyBean>());
                            if(mCommentBeanList!=null){
                                mCommentBeanList.add(0,commentBean);
                            }
                        }else if (isReplay==1){
                            if(mReplyBeanList!=null){
                                SumUpEntity.CommentBean.ReplyBean replayBean = new SumUpEntity.CommentBean.ReplyBean();
                                replayBean.setAvator(info.getAvator());
                                replayBean.setContent(info.getContent());
                                replayBean.setNickname(info.getNickname());
                                replayBean.setId(info.getId());
                                replayBean.setShijian(info.getShijian());
                                replayBean.setUser_id(info.getUser_id());
                                replayBean.setUp_user(info.getUp_user());
                                replayBean.setParentid(info.getParentid());
                                replayBean.setRe_name(info.getRe_name());
                                mReplyBeanList.add(replayBean);
                            }
                        }
                        if(mVideoRemarkAdapter==null){
                            mVideoRemarkAdapter = new VideoRemarkAdapter(mCommentBeanList,getContext());
                            mCommentList.setAdapter(mVideoRemarkAdapter);
                        }
                        mVideoRemarkAdapter.notifyDataSetChanged();
                        mCommentEdit.setText("");
                    }
                }else{
                    Log.e("DH_COMMENT",entity.getMsg());
                    Toast.makeText(MyApplication.mApplication, ""+entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                Log.e("DH_COMMENT",error);
                Toast.makeText(MyApplication.mApplication, "数据发生错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initComment(List<SumUpEntity.CommentBean> commentBeanList,String course_id){
        mCommentBeanList = commentBeanList;
        mCourseId = course_id;
        mCommentList.post(new Runnable() {
            @Override
            public void run() {
                mVideoRemarkAdapter = new VideoRemarkAdapter(mCommentBeanList,getContext());
                mCommentList.setAdapter(mVideoRemarkAdapter);
                mVideoRemarkAdapter.setOnReplayClickListener(VideoCommentFragment.this);
                mVideoRemarkAdapter.setOnDeleteRemarkClickListener(VideoCommentFragment.this);
                mVideoRemarkAdapter.setOnDeleteReplayClickListener(VideoCommentFragment.this);
            }
        });
    }
    private int tempPadding = 0;
    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                //适配坚果全屏手机获取初始padding为负数的情况，键盘弹起时需要额外增加负数大小部分
                if(tempPadding==0&&diff<0){
                    tempPadding = Math.abs(diff);
                }
                if (diff > 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff+tempPadding);
                        Log.e("DH_KEY","UP");
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                        Log.e("DH_KEY","DOWN");
                    }
                }
            }
        };
    }

    private OnCommentKeyboardClickListener mOnCommentKeyboardClickListener;

    public void hideInput() {
        if(mCommentInput!=null){
            mCommentInput.setVisibility(View.GONE);
        }
    }

    /**
     * 一级回复监听
     * @param replyBeanList
     * @param p_id
     * @param r_name
     */
    @Override
    public void onReplayClick(List<SumUpEntity.CommentBean.ReplyBean> replyBeanList, String p_id, String r_name) {
        showInput();
        mIsReplay = 1;
        mReplyBeanList = replyBeanList;
        mParentId = p_id;
        mReplayName = r_name;
    }

    private void showInput() {
        mCommentInput.setVisibility(View.VISIBLE);
        mCommentEdit.setFocusable(true);
        mCommentEdit.setFocusableInTouchMode(true);
        mCommentEdit.requestFocus();
        KeyBoardUtils.showKeyboard(getActivity());
        if(mOnCommentKeyboardClickListener!=null){
            mOnCommentKeyboardClickListener.onKeyboardClick();
        }
    }

    /**
     * 删除评论
     * @param pos
     * @param id
     */
    @Override
    public void onDelRemark(final int pos, String id) {
        mCommentPresenter.deleteCommentJson(id, mCourseId, "1", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                mCommentBeanList.remove(pos);
                if(mVideoRemarkAdapter!=null){
                    mVideoRemarkAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    /**
     * 删除回复
     * @param pos
     * @param mReplyList
     * @param id
     */
    @Override
    public void onDelReplay(final int pos, final List<SumUpEntity.CommentBean.ReplyBean> mReplyList, String id) {
        mCommentPresenter.deleteCommentJson(id, mCourseId, "1", new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                mReplyList.remove(pos);
                if(mVideoRemarkAdapter!=null){
                    mVideoRemarkAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFault(String error) {

            }
        });
    }

    public interface OnCommentKeyboardClickListener{
        void onKeyboardClick();
    }


//    private void showInputManager(EditText editText) {
//        editText.setFocusable(true);
//        editText.setFocusableInTouchMode(true);
//        editText.requestFocus();
//
//        /** 目前测试来看，还是挺准的
//         * 原理：OnGlobalLayoutListener 每次布局变化时都会调用
//         * 界面view 显示消失都会调用，软键盘显示与消失时都调用
//         * */
//        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
//        InputMethodManager inputManager =
//                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
//
//    }
//
//    ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//            //判断窗口可见区域大小
//            Rect r = new Rect();
//            // getWindowVisibleDisplayFrame()会返回窗口的可见区域高度
//            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
//            //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
//            int heightDifference = WT.mScreenHeight - (r.bottom - r.top);
//            boolean isKeyboardShowing = heightDifference > WT.mScreenHeight / 3;
//            if(isKeyboardShowing){
//                // bottomView 需要跟随软键盘移动的布局
//                // setDuration(0) 默认300, 设置 0 ，表示动画执行时间为0，没有过程，只有动画结果了
//                bottomView.animate().translationY(-heightDifference).setDuration(0).start();
//            }else{
//                bottomView.animate().translationY(0).start();
//            }
//        }
//    };

}
