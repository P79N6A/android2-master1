package com.wh.wang.scroopclassproject.viewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.ChangeInfoNewActivity;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.activity.MineIdeaActivity;
import com.wh.wang.scroopclassproject.activity.MineMsgActivity;
import com.wh.wang.scroopclassproject.activity.MineOrderActivity;
import com.wh.wang.scroopclassproject.activity.SettingActivity;
import com.wh.wang.scroopclassproject.base.BasePager;
import com.wh.wang.scroopclassproject.bean.MsgNumsBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.UserInfoEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.UserInfoPresenter;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Retrofit;


/**
 * Created by wang on 2017/8/14.
 * 我的页面
 */

public class MineViewPager extends BasePager {

    private String userid;
    private ImageView mine_iv_logo;
    private ImageView mine_iv_logo_bg;
    private RelativeLayout mine_rlayout_edit;
    private TextView mine_tv_names;
    private ImageView mine_iv_nice;
    private ImageView mine_iv_changeInfo;
    private RelativeLayout mine_rlayout_order;
    private RelativeLayout mine_rlayout_msg;
    private RelativeLayout mine_rlayout_idea;
    private RelativeLayout mine_rlayout_set;
    private RelativeLayout mine_rlayout_msg_nums;
    private TextView mine_tv_msg_nums;
    private TextView mUnloadTitle;
    private UserInfoPresenter mUserInfoPresenter = new UserInfoPresenter();
    private int mess_num;
    private String mobile;

    public MineViewPager(Context context) {
        super(context);
    }

    @Override
    public View baseView() {
        View view = View.inflate(context, R.layout.mine_viewpager, null);
        mine_iv_logo_bg = (ImageView) view.findViewById(R.id.mine_iv_logo_bg);//头像背景
        mine_iv_logo = (ImageView) view.findViewById(R.id.mine_iv_logo);//头像
        mine_rlayout_edit = (RelativeLayout) view.findViewById(R.id.mine_rlayout_edit);//编译信息父类
        mine_tv_names = (TextView) view.findViewById(R.id.mine_tv_names);//编译信息名字
        mine_iv_nice = (ImageView) view.findViewById(R.id.mine_iv_nice);//编译信息等级
        mine_iv_changeInfo = (ImageView) view.findViewById(R.id.mine_iv_changeInfo);//修改用户信息
        mine_rlayout_order = (RelativeLayout) view.findViewById(R.id.mine_rlayout_order);//我的订单

        mine_rlayout_msg = (RelativeLayout) view.findViewById(R.id.mine_rlayout_msg); //消息
        mine_rlayout_msg_nums = (RelativeLayout) view.findViewById(R.id.mine_rlayout_msg_nums); //消息数量父类
        mine_tv_msg_nums = (TextView) view.findViewById(R.id.mine_tv_msg_nums);//消息数量子类
        mine_rlayout_idea = (RelativeLayout) view.findViewById(R.id.mine_rlayout_idea);//意见
        mine_rlayout_set = (RelativeLayout) view.findViewById(R.id.mine_rlayout_set);//设置
        mUnloadTitle = (TextView) view.findViewById(R.id.unload_title);

        setDataInfo();
        setListener();
        return view;
    }


    /**
     * 设置基本数据
     */
    private void setDataInfo() {
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        mobile = SharedPreferenceUtil.getStringFromSharedPreference(context, "mobile", "");
        if (StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(mobile)) {
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(context, "avatar", "");
            if (!avatar.isEmpty()) {
                ImageLoader.getInstance().displayImage(avatar, mine_iv_logo, MyDisplayOptions.getOptions());
            } else {
                mine_iv_logo.setImageResource(R.drawable.mine_touxiang);
            }
            mUnloadTitle.setVisibility(View.GONE);
            mine_iv_nice.setVisibility(View.VISIBLE);
            String name = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
            if (StringUtils.isNotEmpty(name)) {
                mine_tv_names.setText(name);
            } else {
                mine_tv_names.setText("点击编辑个人资料");
            }
            
//            String is_vip = SharedPreferenceUtil.getStringFromSharedPreference(context, "is_vip", "");
            Log.e("DH_ISVIP",(mUserInfoPresenter==null)+"");
            if(mUserInfoPresenter==null){
                mUserInfoPresenter = new UserInfoPresenter();
            }
            mUserInfoPresenter.getUserInfo(userid, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    UserInfoEntity entity = (UserInfoEntity) value[0];
                    int isVip = entity.getInfo().getIs_vip();
                    Log.e("DH_ISVIP","success  vip:"+isVip);
                    SharedPreferenceUtil.putStringValueByKey(context, "is_vip", isVip+"");
                    if (isVip == 1) {
                        mine_iv_nice.setImageResource(R.drawable.vippic);
                    } else {
                        mine_iv_nice.setImageResource(R.drawable.normalpic);
                    }
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_ISVIP",error);
                }
            });

        } else {
            mine_iv_logo.setImageResource(R.drawable.mine_touxiang);
            mine_tv_names.setText("未登录");
            mine_iv_nice.setImageResource(R.drawable.normalpic);
            mUnloadTitle.setVisibility(View.VISIBLE);
            mine_iv_nice.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(mobile)) {

            getMsgNumsFromHttp(userid);
        }
    }

    @Override
    public void onResumeVisible() {
        super.onResumeVisible();
//        Log.e("DH_PAGER", "MineViewPager--->onResumeVisible");
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        mobile = SharedPreferenceUtil.getStringFromSharedPreference(context, "mobile", "");
        if (StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(mobile)) {
            Log.e("whwh", "isNotEmpty(userid)");
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(context, "avatar", "");
            if (!avatar.isEmpty()) {
                ImageLoader.getInstance().displayImage(avatar, mine_iv_logo, MyDisplayOptions.getOptions());
            } else {
                mine_iv_logo.setImageResource(R.drawable.mine_touxiang);
            }

            mUnloadTitle.setVisibility(View.GONE);
            mine_iv_nice.setVisibility(View.VISIBLE);
            String name = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
            if (StringUtils.isNotEmpty(name)) {
                mine_tv_names.setText(name);
            } else {
                mine_tv_names.setText("点击编辑个人资料");
            }

            //is_vip
//            String is_vip = SharedPreferenceUtil.getStringFromSharedPreference(context, "is_vip", "");
            mUserInfoPresenter.getUserInfo(userid, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    UserInfoEntity entity = (UserInfoEntity) value[0];
                    int isVip = entity.getInfo().getIs_vip();
                    Log.e("DH_ISVIP","success  vip:"+isVip);
                    SharedPreferenceUtil.putStringValueByKey(context, "is_vip", isVip+"");
                    if (isVip == 1) {
                        mine_iv_nice.setImageResource(R.drawable.vippic);
                    } else {
                        mine_iv_nice.setImageResource(R.drawable.normalpic);
                    }
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_ISVIP",error);
                }
            });


            getMsgNumsFromHttp(userid);

        } else {
            Log.e("whwh", "isEmpty(userid)");
            mine_iv_logo.setImageResource(R.drawable.mine_touxiang);
            mine_tv_names.setText("未登录");
            mine_iv_nice.setImageResource(R.drawable.normalpic);
            mUnloadTitle.setVisibility(View.VISIBLE);
            mine_iv_nice.setVisibility(View.GONE);
        }

        setListener();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        if (StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(mobile)) {
            mine_iv_changeInfo.setVisibility(View.VISIBLE);
            mine_iv_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChangeInfoNewActivity.class);
                    context.startActivity(intent);
                }
            });

            mine_rlayout_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChangeInfoNewActivity.class);
                    context.startActivity(intent);
                }
            });

            mine_iv_changeInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChangeInfoNewActivity.class);
                    context.startActivity(intent);
                }
            });

            mine_rlayout_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MineOrderActivity.class);
                    context.startActivity(intent);
                }
            });


            mine_rlayout_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MineMsgActivity.class);
                    context.startActivity(intent);
                }
            });

            mine_rlayout_idea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MineIdeaActivity.class);
                    context.startActivity(intent);
                }
            });

        } else {
            mine_iv_changeInfo.setVisibility(View.GONE);
            mine_iv_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });

            mine_rlayout_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });

            mine_rlayout_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });


            mine_rlayout_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });

            mine_rlayout_idea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginNewActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        mine_rlayout_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SettingActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public void baseData() {
        super.baseData();
    }

    /**
     * TODO
     * 获取消息的数量
     */
    private void getMsgNumsFromHttp(String userid) {
        HttpUserManager.getInstance().updateMsg(userid, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, MsgNumsBean.class);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                String msgStr = jsonObject.optString("msg");
                String infoStr = jsonObject.optString("info");
                ArrayList list = new ArrayList();
                Gson gson = new Gson();
                list = gson.fromJson(infoStr, ArrayList.class);
                String data = gson.toJson(list.get(0));
                JSONObject jsonObject2 = new JSONObject(data);
                mess_num = jsonObject2.optInt("mess_num");

                if (mess_num > 0) {
                    mine_rlayout_msg_nums.setVisibility(View.VISIBLE);
                    if (mess_num >= 99) {
                        mine_tv_msg_nums.setText("99 +");
                    } else {
                        mine_tv_msg_nums.setText(mess_num + "");
                    }
                } else {
                    mine_rlayout_msg_nums.setVisibility(View.GONE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void resumeData() {
        super.resumeData();
        Log.e("DH_PAGER", "MineViewPager--->resumeData");
        userid = SharedPreferenceUtil.getStringFromSharedPreference(context, "user_id", "");
        mobile = SharedPreferenceUtil.getStringFromSharedPreference(context, "mobile", "");
        if (StringUtils.isNotEmpty(userid)&&StringUtils.isNotEmpty(mobile)) {
            Log.e("whwh", "isNotEmpty(userid)");
            mUnloadTitle.setVisibility(View.GONE);
            mine_iv_nice.setVisibility(View.VISIBLE);
            String avatar = SharedPreferenceUtil.getStringFromSharedPreference(context, "avatar", "");
            if (!avatar.isEmpty()) {
                ImageLoader.getInstance().displayImage(avatar, mine_iv_logo, MyDisplayOptions.getOptions());
            } else {
                mine_iv_logo.setImageResource(R.drawable.mine_touxiang);
            }

            String name = SharedPreferenceUtil.getStringFromSharedPreference(context, "nickname", "");
            if (StringUtils.isNotEmpty(name)) {
                mine_tv_names.setText(name);
            } else {
                mine_tv_names.setText("点击编辑个人资料");
            }
            //is_vip
//            String is_vip = SharedPreferenceUtil.getStringFromSharedPreference(context, "is_vip", "");
            mUserInfoPresenter.getUserInfo(userid, new OnResultListener() {
                @Override
                public void onSuccess(Object... value) {
                    UserInfoEntity entity = (UserInfoEntity) value[0];
                    int isVip = entity.getInfo().getIs_vip();
                    Log.e("DH_ISVIP","success  vip:"+isVip);
                    SharedPreferenceUtil.putStringValueByKey(context, "is_vip", isVip+"");
                    if (isVip == 1) {
                        mine_iv_nice.setImageResource(R.drawable.vippic);
                    } else {
                        mine_iv_nice.setImageResource(R.drawable.normalpic);
                    }
                }

                @Override
                public void onFault(String error) {
                    Log.e("DH_ISVIP",error);
                }
            });


            getMsgNumsFromHttp(userid);

        } else {
            Log.e("whwh", "isEmpty(userid)");
            mine_iv_logo.setImageResource(R.drawable.mine_touxiang);
            mine_tv_names.setText("未登录");
            mine_iv_nice.setImageResource(R.drawable.normalpic);
            mUnloadTitle.setVisibility(View.VISIBLE);
            mine_iv_nice.setVisibility(View.GONE);
        }

        setListener();
    }
}
