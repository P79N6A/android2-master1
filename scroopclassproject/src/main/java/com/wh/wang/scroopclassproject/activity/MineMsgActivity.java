package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.MineSecondMsgBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.ui.activity.LikeOrRemindActivity;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.StyleUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MineMsgActivity extends Activity implements View.OnClickListener {

    private RelativeLayout mine_rlayout_talk;
    private TextView mine_tv_talkContent;
    private TextView mine_tv_talkTime;
    private RelativeLayout mine_rlayout_repeat;
    private TextView mine_tv_repeatContent;
    private TextView mine_tv_repeatTime;
    private String userid;
    private TextView mine_tv_talk_nums;
    private TextView mine_tv_repeat_nums;
    private ImageView mine_msg_iv_empty;
    private RelativeLayout mLike;
    private TextView mLikeContent;
    private TextView mLikeTime;
    private TextView mLikeNum;
    private RelativeLayout mRemind;
    private TextView mRemindContent;
    private TextView mRemindTime;
    private TextView mRemindNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleUtil.customStyle2(this, R.layout.mine_msg, "消息通知");
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {

        //消息数据
        mine_rlayout_talk = (RelativeLayout) findViewById(R.id.mine_rlayout_talk);
        mine_rlayout_talk.setVisibility(View.GONE);
        mine_tv_talkContent = (TextView) findViewById(R.id.mine_tv_talkContent);
        mine_tv_talkTime = (TextView) findViewById(R.id.mine_tv_talkTime);
        mine_tv_talk_nums = (TextView) findViewById(R.id.mine_tv_talk_nums);
        mine_tv_talk_nums.setVisibility(View.GONE);


        //反馈数据
        mine_rlayout_repeat = (RelativeLayout) findViewById(R.id.mine_rlayout_repeat);
        mine_rlayout_repeat.setVisibility(View.GONE);
        mine_tv_repeatContent = (TextView) findViewById(R.id.mine_tv_repeatContent);
        mine_tv_repeatTime = (TextView) findViewById(R.id.mine_tv_repeatTime);
        mine_tv_repeat_nums = (TextView) findViewById(R.id.mine_tv_repeat_nums);
        mine_tv_repeat_nums.setVisibility(View.GONE);


        mLike = (RelativeLayout) findViewById(R.id.like);
        mLikeContent = (TextView) findViewById(R.id.like_content);
        mLikeTime = (TextView) findViewById(R.id.like_time);
        mLikeNum = (TextView) findViewById(R.id.like_num);
        mRemind = (RelativeLayout) findViewById(R.id.remind);
        mRemindContent = (TextView) findViewById(R.id.remind_content);
        mRemindTime = (TextView) findViewById(R.id.remind_time);
        mRemindNum = (TextView) findViewById(R.id.remind_num);

        //没有数据时
        mine_msg_iv_empty = (ImageView) findViewById(R.id.mine_msg_iv_empty);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSecondMsgFromHttp(userid);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(this, "user_id", "");
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {

        //消息点击
        mine_rlayout_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineMsgActivity.this, MineMsgDetailActivity.class);
                startActivity(intent);
            }
        });

        //反馈点击
        mine_rlayout_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineMsgActivity.this, MineFreebackctivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 网络请求
     */
    private void getSecondMsgFromHttp(String userid) {

        HttpUserManager.getInstance().updateSecondMsg(userid, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, MineSecondMsgBean.class);
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            try {
                Log.e("DH_MSG_JSON",obj.toString());
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                String msgStr = jsonObject.optString("msg");
                if (code == 200) {
                    mine_msg_iv_empty.setVisibility(View.GONE);
                    int notice_num = jsonObject.optInt("notice_num");
                    String new_notice = jsonObject.optString("new_notice");
                    JSONObject jsonNoticeObj = new JSONObject(new_notice);
                    String noticeId = jsonNoticeObj.optString("id");
                    String noticeContent = jsonNoticeObj.optString("content");
                    String noticeCreate_time = jsonNoticeObj.optString("create_time");

                    if (notice_num > 0) {
                        mine_tv_talk_nums.setVisibility(View.VISIBLE);
                        mine_tv_talk_nums.setClickable(true);
                        if (notice_num > 99) {
                            mine_tv_talk_nums.setText("99+");
                        } else {
                            mine_tv_talk_nums.setText(notice_num + "");
                        }

                    } else {
                        mine_tv_talk_nums.setVisibility(View.GONE);
                        mine_tv_talk_nums.setClickable(false);
                    }

                    if (StringUtils.isEmpty(noticeId)) {
                        mine_rlayout_talk.setVisibility(View.GONE);
                        mine_rlayout_talk.setClickable(false);
                        mine_msg_iv_empty.setVisibility(View.VISIBLE);
                    } else {
                        mine_rlayout_talk.setVisibility(View.VISIBLE);
                        mine_msg_iv_empty.setVisibility(View.GONE);
                        mine_rlayout_talk.setClickable(true);
                        mine_tv_talkContent.setText(noticeContent);
                        mine_tv_talkTime.setText(noticeCreate_time);

                    }

                    //反馈数据解析
                    int feedback_num = jsonObject.optInt("feedback_num");
                    String new_feedback = jsonObject.optString("new_feedback");
                    JSONObject jsonFeebackObj = new JSONObject(new_feedback);
                    String feebackId = jsonFeebackObj.optString("id");
                    String feebackContent = jsonFeebackObj.optString("content");
                    String feebackCreate_time = jsonFeebackObj.optString("create_time");

                    if (feedback_num > 0) {
                        mine_tv_repeat_nums.setVisibility(View.VISIBLE);
                        mine_tv_repeat_nums.setClickable(true);
                        if (feedback_num > 99) {
                            mine_tv_repeat_nums.setText("99+");
                        } else {
                            mine_tv_repeat_nums.setText(feedback_num + "");
                        }

                    } else {
                        mine_tv_repeat_nums.setVisibility(View.GONE);
                        mine_tv_repeat_nums.setClickable(false);
                    }

                    if (StringUtils.isEmpty(feebackId)) {
                        mine_rlayout_repeat.setVisibility(View.GONE);
                        mine_rlayout_repeat.setClickable(false);
                    } else {
                        //TODO WHWH 隐藏勺子官方反馈
                        mine_rlayout_repeat.setVisibility(View.VISIBLE);
                        //mine_rlayout_repeat.setVisibility(View.GONE);
                        mine_rlayout_repeat.setClickable(true);
                        mine_tv_repeatContent.setText(feebackContent);
                        mine_tv_repeatTime.setText(feebackCreate_time);
                    }

                    int dianzan_num = jsonObject.optInt("dianzan_num");
                    String dianzan_new = jsonObject.optString("dianzan_new");
                    JSONObject jsonDianZanObj = new JSONObject(dianzan_new);
                    String dianzanId = jsonDianZanObj.optString("id");
                    String dianzanContent = jsonDianZanObj.optString("content");
                    String dianzanCreate_time = jsonDianZanObj.optString("create_time");
                    if(StringUtils.isNotEmpty(dianzanId)){
                        mLike.setVisibility(View.VISIBLE);
                        mLike.setOnClickListener(MineMsgActivity.this);
                        if(dianzan_num>0){
                            mLikeNum.setVisibility(View.VISIBLE);
                            if(dianzan_num>99){
                                mLikeNum.setText("99+");
                            }else{
                                mLikeNum.setText(""+dianzan_num);
                            }
                        }else{
                            mLikeNum.setVisibility(View.GONE);
                        }
                        mLikeContent.setText(dianzanContent);
                        mLikeTime.setText(dianzanCreate_time);
                    }else{
                        mLike.setVisibility(View.GONE);
                    }


                    int tixing_num = jsonObject.optInt("tixing_num");
                    String tixing_new = jsonObject.optString("tixing_new");
                    JSONObject jsonTiXiangObj = new JSONObject(tixing_new);
                    String tixingId = jsonTiXiangObj.optString("id");
                    String tixingContent = jsonTiXiangObj.optString("content");
                    String tixingCreate_time = jsonTiXiangObj.optString("create_time");
                    if(StringUtils.isNotEmpty(tixingId)){
                        mRemind.setVisibility(View.VISIBLE);
                        mRemind.setOnClickListener(MineMsgActivity.this);
                        if(tixing_num>0){
                            mRemindNum.setVisibility(View.VISIBLE);
                            if(tixing_num>99){
                                mRemindNum.setText("99+");
                            }else{
                                mRemindNum.setText(""+tixing_num);
                            }
                        }else{
                            mRemindNum.setVisibility(View.GONE);
                        }
                        mRemindContent.setText(tixingContent);
                        mRemindTime.setText(tixingCreate_time);
                    }else{
                        mRemind.setVisibility(View.GONE);
                    }


                } else {
                    mine_msg_iv_empty.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.remind:
                intent = new Intent(this,LikeOrRemindActivity.class);
                intent.putExtra("type","5");
                startActivity(intent);
                break;
            case R.id.like:
                intent = new Intent(this, LikeOrRemindActivity.class);
                intent.putExtra("type","4");
                startActivity(intent);
                break;
        }
    }
}
