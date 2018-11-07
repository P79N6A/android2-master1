package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.wxapi.ShareUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BannerFinishFragment extends Fragment {
    private TextView mFinish;
    private Button mAchCard;
    private String mComplete;
    private String mUrl;
    private String mStatus;
    private SGDetail2_0Entity.InfoBeanX.AchieveCardBean mAchieveCard;

    public BannerFinishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments()!=null) {
            Bundle bundle = getArguments();
            mComplete = bundle.getString("complete", "");
//            mUrl = bundle.getString("url", "");
            mStatus = bundle.getString("status");
            mAchieveCard = (SGDetail2_0Entity.InfoBeanX.AchieveCardBean) bundle.getSerializable("achieve");
        }
        View view = inflater.inflate(R.layout.fragment_sgfinsish, container, false);
        mFinish = (TextView) view.findViewById(R.id.finish);
        mAchCard = (Button) view.findViewById(R.id.ach_card);
        mAchCard.setVisibility(View.VISIBLE);
        if("0".equals(mComplete)||"1".equals(mComplete)){
            if ("0".equals(mComplete)) {
                mFinish.setText(R.string.task_success);
            }else{
                mFinish.setText(R.string.all_task_success);

            }
            if ("2".equals(mStatus)) {
                mAchCard.setVisibility(View.VISIBLE);
                mAchCard.setText("成就卡");
            }

        }else{
            mFinish.setText("很遗憾，未完成打卡任务");
            mAchCard.setVisibility(View.GONE);
        }
        mAchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAchieveCard!=null) {
                    showCardDig(); 
                }else{
                    Toast.makeText(MyApplication.mApplication, "获取成就卡数据异常", Toast.LENGTH_SHORT).show();
                }
               
            }
        });
        return view;
    }

    private Dialog mCardDig;
    private Bitmap mCJBitmap;

    private void showCardDig() {
        mCardDig = new Dialog(getContext(), R.style.MyDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dig_sg_achieve, null, false);
        mCardDig.setContentView(view);//

        final RelativeLayout content = (RelativeLayout) mCardDig.findViewById(R.id.content);
        ImageView wx = (ImageView) mCardDig.findViewById(R.id.wx);
        ImageView qr = (ImageView) mCardDig.findViewById(R.id.qr);
        ImageView avatarImg = (RoundedImageView) mCardDig.findViewById(R.id.avatar);
        TextView nickname = (TextView) mCardDig.findViewById(R.id.nickname);
        TextView title = (TextView) mCardDig.findViewById(R.id.title);
        TextView dayNum = (TextView) mCardDig.findViewById(R.id.day_num);
        TextView time = (TextView) mCardDig.findViewById(R.id.time);
        TextView todayTime = (TextView) mCardDig.findViewById(R.id.today_time);
        TextView allTime = (TextView) mCardDig.findViewById(R.id.all_time);
        ImageView pyq = (ImageView) mCardDig.findViewById(R.id.pyq);


        String avatar = SharedPreferenceUtil.getStringFromSharedPreference(getContext(),"avatar","");
        Glide.with(getContext()).load("https://img.shaoziketang.com/img_code20180725160823_100.h.png").into(qr);
        Glide.with(getContext()).load(avatar).centerCrop().into(avatarImg);
        nickname.setText(mAchieveCard.getUser_name());
        title.setText(mAchieveCard.getTitle());
        dayNum.setText("学习进度 "+mAchieveCard.getDay()+"/"+mAchieveCard.getTotalday()+" 天");
        time.setText(mAchieveCard.getS_time());
        todayTime.setText(mAchieveCard.getV_time());
        allTime.setText(mAchieveCard.getZ_time());

        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCJBitmap==null){
                    content.setDrawingCacheEnabled(true);
                    content.buildDrawingCache(true);
                    mCJBitmap=content.getDrawingCache();
                }

                ShareUtil.wechatShareImg(Constants.wx_api, mCJBitmap, 0, getContext(), false);
            }
        });

        pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCJBitmap==null){
                    content.setDrawingCacheEnabled(true);
                    content.buildDrawingCache(true);
                    mCJBitmap=content.getDrawingCache();
                }
                ShareUtil.wechatShareImg(Constants.wx_api, mCJBitmap, 1, getContext(), false);
            }
        });

        Window dialogWindow = mCardDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(MyApplication.mApplication, 263);
        lp.height = DisplayUtil.dip2px(MyApplication.mApplication, 521);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        mCardDig.show();
    }

}
