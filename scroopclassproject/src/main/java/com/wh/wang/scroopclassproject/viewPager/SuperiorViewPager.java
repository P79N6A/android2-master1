package com.wh.wang.scroopclassproject.viewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.MainActivity;
import com.wh.wang.scroopclassproject.activity.QuestionActivity;
import com.wh.wang.scroopclassproject.base.BasePager;
import com.wh.wang.scroopclassproject.bean.UpdateBean;
import com.wh.wang.scroopclassproject.fragments.FragmentAdapter;
import com.wh.wang.scroopclassproject.fragments.JXKFragment;
import com.wh.wang.scroopclassproject.fragments.XTKFragment;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewSearchActivity;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.boutique_frag.FreeCourseFragment;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/8/14.
 * 精品页面
 */


public class SuperiorViewPager extends BasePager implements View.OnClickListener {

    private ImageButton titlebar_ib_search;
    private List<Fragment> fragmentList;//数据源
    private RelativeLayout id_tab_bottom_jxk;
    private TextView bar_jxk;
    private View bar_view_one;
    private RelativeLayout id_tab_bottom_xtk;
    private TextView bar_xtk;
    private View bar_view_two;
    private RelativeLayout id_tab_bottom_mfk;
    private TextView bar_mfk;
    private View bar_view_three;
    private ViewPager sup_viewpager;
    private RelativeLayout titlebar_new;
    private AlertDialog dialog;
    private String mVersion_code;

    public SuperiorViewPager(Context context) {
        super(context);
    }


    @Override
    public View baseView() {

        View view = View.inflate(context, R.layout.superior_viewpager, null);
        //添加数据
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new JXKFragment());
        fragmentList.add(new XTKFragment());
//        fragmentList.add(new MFKFragment());
        fragmentList.add(new FreeCourseFragment());

        titlebar_new = (RelativeLayout) view.findViewById(R.id.titlebar_new_rlayout);
        titlebar_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewSearchActivity.class);
                context.startActivity(intent);
            }
        });
        sup_viewpager = (ViewPager) view.findViewById(R.id.sup_viewpager);

        //精选课
        id_tab_bottom_jxk = (RelativeLayout) view.findViewById(R.id.id_tab_bottom_jxk);
        bar_jxk = (TextView) view.findViewById(R.id.bar_jxk);
        bar_view_one = (View) view.findViewById(R.id.bar_view_one);

        //系统课
        id_tab_bottom_xtk = (RelativeLayout) view.findViewById(R.id.id_tab_bottom_xtk);
        bar_xtk = (TextView) view.findViewById(R.id.bar_xtk);
        bar_view_two = (View) view.findViewById(R.id.bar_view_two);

        //免费课
        id_tab_bottom_mfk = (RelativeLayout) view.findViewById(R.id.id_tab_bottom_mfk);
        bar_mfk = (TextView) view.findViewById(R.id.bar_mfk);
        bar_view_three = (View) view.findViewById(R.id.bar_view_three);

        //绑定监听事件
        id_tab_bottom_jxk.setOnClickListener(this);
        id_tab_bottom_xtk.setOnClickListener(this);
        id_tab_bottom_mfk.setOnClickListener(this);

        //初始化ViewPager
        //设置不进行预加载
        sup_viewpager.setOffscreenPageLimit(3);
        //绑定适配器
        FragmentManager childFragmentManager = ((MainActivity) context).getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(childFragmentManager, fragmentList);
        sup_viewpager.setAdapter(adapter);

        //设置viewpager切换监听
        sup_viewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                setTabSelection(arg0);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            @Override
            public void onPageScrollStateChanged(int arg0) {


            }
        });
        setTabSelection(0);

//        Constant.IMEI = PhoneStateUtils.getIMEI(context);
        return view;
    }

    private void setTabSelection(int i) {
        //清除状态
        clearBtn();
        switch (i) {

            case 0:
                bar_jxk.setTextColor(context.getResources().getColor(R.color.super_main_press));
                bar_view_one.setVisibility(View.VISIBLE);

                break;
            case 1:
                bar_xtk.setTextColor(context.getResources().getColor(R.color.super_main_press));
                bar_view_two.setVisibility(View.VISIBLE);
                break;
            case 2:
                bar_mfk.setTextColor(context.getResources().getColor(R.color.super_main_press));
                bar_view_three.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void clearBtn() {
        bar_jxk.setTextColor(context.getResources().getColor(R.color.super_main_normal));
        bar_view_one.setVisibility(View.INVISIBLE);
        bar_xtk.setTextColor(context.getResources().getColor(R.color.super_main_normal));
        bar_view_two.setVisibility(View.INVISIBLE);
        bar_mfk.setTextColor(context.getResources().getColor(R.color.super_main_normal));
        bar_view_three.setVisibility(View.INVISIBLE);
    }


    @Override
    public void baseData() {
        super.baseData();
        checkUpdate();
    }

    private void checkUpdate() {
        HttpUserManager.getInstance().updatess(new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                updataHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, UpdateBean.class);
    }

    private Handler updataHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;

            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                mVersion_code = jsonObject.getString("version");
                String mVersion_apk = jsonObject.getString("apk");
                if (isUpdate(mVersion_code)) {
                    showAlertDialog();
                    SharedPreferenceUtil.clearInfo(context);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void showAlertDialog() {

        dialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.DialogStyle)).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setView(LayoutInflater.from(context).inflate(R.layout.version_dialog, null));
        dialog.show();
        dialog.getWindow().setContentView(R.layout.version_dialog);
        Button btn_version_result = (Button) dialog.findViewById(R.id.btn_version_result);
        btn_version_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String sdPath = Environment.getExternalStorageDirectory() + "/";
                String mSavePath = sdPath + "jikedownload";
                File dir = new File(mSavePath);
                if (dir.exists()) {
                    new UpdateManager(context).checkUpdate();
                }
            }
        });

        TextView btn_version_question = (TextView) dialog.findViewById(R.id.btn_version_question);
        btn_version_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(context, QuestionActivity.class);
                context.startActivity(intent6);
            }
        });

    }

    private boolean isUpdate(String mVersion_code) {
        int serverVersion = Integer.parseInt(mVersion_code);
        int localVersion = 1;

        try {
            localVersion = context.getPackageManager().getPackageInfo("com.wh.wang.scroopclassproject", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (serverVersion > localVersion)
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tab_bottom_jxk:
                setTabSelection(0);
                //设置view显示的页面
                sup_viewpager.setCurrentItem(0);
                break;
            case R.id.id_tab_bottom_xtk:
                setTabSelection(1);
                sup_viewpager.setCurrentItem(1);
                break;
            case R.id.id_tab_bottom_mfk:
                setTabSelection(2);
                sup_viewpager.setCurrentItem(2);
                break;
        }

    }
}
