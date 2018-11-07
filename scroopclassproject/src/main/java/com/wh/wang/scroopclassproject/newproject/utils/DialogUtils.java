package com.wh.wang.scroopclassproject.newproject.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.callback.OnAddressClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.Api;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ProvinceEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ResidueInviteEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.ResidueInvitePresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CasebookInviteActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.EnterpriseApplyActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.FunctionPager;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chdh on 2018/1/22.
 */

public class DialogUtils {

    private Context mContext;
    private DialogUtils mDialogUtils;
    private String mUser_Id;

    public DialogUtils(Context context) {
        mContext = context;
        mUser_Id = SharedPreferenceUtil.getStringFromSharedPreference(context,"user_id","");
    }

    public DialogUtils(){

    }

    private Dialog mInviteDialog;
    public void showInviteDialog(String user_id){
        if(mDialogUtils==null){
            mDialogUtils = new DialogUtils(mContext);
        }
        mDialogUtils.showLoading();
        mInviteDialog = new Dialog(mContext, R.style.MyDialog);

        new ResidueInvitePresenter().getResidueInvite(user_id, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                ResidueInviteEntity entity = (ResidueInviteEntity) value[0];
                mDialogUtils.hideLoading();
                if(entity.getCode()==200){
                    View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_invite_friend,null,false);
                    TextView infoTv = (TextView) dView.findViewById(R.id.info);
                    TextView cancelTv = (TextView) dView.findViewById(R.id.cancel);
                    TextView relieveTv = (TextView) dView.findViewById(R.id.relieve);
                    String info = "您的企业号一共可以有"+entity.getInfo().getCount_num()+"人\n剩余可邀请同事人数: "+entity.getInfo().getSub_num();
                    int f_index = info.indexOf("人");
                    int s_index = info.indexOf(":");
                    SpannableString spannableString = new SpannableString(info);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4894f2")), 10,f_index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4894f2")), s_index+1,info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(MyApplication.mApplication,19)), 10,f_index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(MyApplication.mApplication,19)), s_index+1,info.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    infoTv.setText(spannableString);
                    String sub_num = (entity.getInfo().getSub_num()==null||"".equals(entity.getInfo().getSub_num()==null))?"0":entity.getInfo().getSub_num();
                    if("0".equals(sub_num)){
                        relieveTv.setBackgroundResource(R.drawable.shape_free_bg);
                        relieveTv.setText("您的好友邀请剩余人数为0");
                        relieveTv.setOnClickListener(null);
                    }else{
                        relieveTv.setBackgroundResource(R.drawable.register_next_bg);
                        relieveTv.setText("邀请好友");
                        relieveTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(mInviteDialog!=null&&mInviteDialog.isShowing()){
                                    mInviteDialog.dismiss();
                                }
                                if(mOnShareInviteClickListener!=null){
                                    mOnShareInviteClickListener.onInviteClick();
                                }
                            }
                        });
                    }
                    mInviteDialog.setContentView(dView);
                    mInviteDialog.show();
                }else{
                    Toast.makeText(mContext, entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFault(String error) {
                mDialogUtils.hideLoading();
                Log.e("DH_RESIUDE",error);
            }
        });
    }

    public void showInviteDialog(String Count_num,String Sub_num){
        mInviteDialog = new Dialog(mContext, R.style.MyDialog);
        View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_invite_friend,null,false);
        TextView infoTv = (TextView) dView.findViewById(R.id.info);
        TextView cancelTv = (TextView) dView.findViewById(R.id.cancel);
        TextView relieveTv = (TextView) dView.findViewById(R.id.relieve);
        String info = "您一共可以邀请"+Count_num+"位同事加入\n剩余可邀请同事人数: "+Sub_num+"人";
        int f_index = info.indexOf("位");
        int s_index = info.indexOf(":");
        SpannableString spannableString = new SpannableString(info);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4894f2")), 7,f_index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4894f2")), s_index+1,info.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(MyApplication.mApplication,19)), 7,f_index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(MyApplication.mApplication,19)), s_index+1,info.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        infoTv.setText(spannableString);
        if("0".equals(Sub_num)){
            relieveTv.setBackgroundResource(R.drawable.shape_free_bg);
            relieveTv.setText("您的好友邀请剩余人数为0");
            relieveTv.setOnClickListener(null);
        }else{
            relieveTv.setBackgroundResource(R.drawable.register_next_bg);
            relieveTv.setText("邀请好友");
            relieveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnShareInviteClickListener!=null){
                        mOnShareInviteClickListener.onInviteClick();
                    }
                }
            });
        }
        mInviteDialog.setContentView(dView);
        mInviteDialog.show();
    }

    private Dialog mRelieveDialog;
    public void showRelieveDialog(String name){
        mRelieveDialog = new Dialog(mContext, R.style.MyDialog);
        View dView = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.dig_relieve_manager,null,false);
        TextView infoTv = (TextView) dView.findViewById(R.id.info);
        TextView cancelTv = (TextView) dView.findViewById(R.id.cancel);
        TextView relieveTv = (TextView) dView.findViewById(R.id.relieve);
        String info = "当前管理员为"+name+",设置新的管理员将解除旧管理员权限是否继续?";
        int index = info.indexOf(",");
        SpannableString spannableString = new SpannableString(info);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4894f2")), 6,index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(MyApplication.mApplication,14)), 6,index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        infoTv.setText(spannableString);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRelieveDialog!=null&&mRelieveDialog.isShowing()){
                    mRelieveDialog.dismiss();
                }
            }
        });
        relieveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRelieveDialog.setContentView(dView);
        mRelieveDialog.show();
    }

    private OnShareInviteClickListener mOnShareInviteClickListener;

    public void setOnShareInviteClickListener(OnShareInviteClickListener onShareInviteClickListener) {
        mOnShareInviteClickListener = onShareInviteClickListener;
    }

    public interface OnShareInviteClickListener{
        void onInviteClick();
    }

    /**
     * loading
     */
    private Dialog mLoadDialog;
    public void showLoading(){
        if(mLoadDialog==null){
            mLoadDialog = new Dialog(mContext,R.style.MyDialog);
            mLoadDialog.setContentView(R.layout.dig_loading);
            mLoadDialog.setCanceledOnTouchOutside(false);
            mLoadDialog.setCancelable(false);
        }
        mLoadDialog.show();
    }
    public void hideLoading(){
        if(mLoadDialog!=null&&mLoadDialog.isShowing()){
            mLoadDialog.dismiss();
        }
    }

    /**
     * 地址选择器
     */
    public static final int ONE_LEVEL = 1;
    public static final int TWO_LEVEL = 2;
    public static final int THREE_LEVEL = 3;
    //地区相关
    private List<String> mProvinceList = new ArrayList<>();
    private List<String> mCityList = new ArrayList<>();
    private List<String> mDistrictList = new ArrayList<>();
    private List<ProvinceEntity> mProvinceEntityList;
    private List<ProvinceEntity.CityBean> mCityBeanEntityList;
    private LoopView mProvinceLoop;
    private LoopView mCityLoop;
    private LoopView mDistrictLoop;

    private OnAddressClickListener mOnAddressClickListener;
    public Dialog initAddressDig(Activity activity, final int flag) {
        if(activity instanceof OnAddressClickListener){
            mOnAddressClickListener = (OnAddressClickListener) activity;
        }

        //分享
        final Dialog addressDialog = new Dialog(activity, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(MyApplication.mApplication).inflate(R.layout.layout_loop, null);

        mProvinceLoop = (LoopView) inflate.findViewById(R.id.province_loop);
        mCityLoop = (LoopView) inflate.findViewById(R.id.city_loop);
        mDistrictLoop = (LoopView) inflate.findViewById(R.id.district_loop);
        if(flag == THREE_LEVEL)
            mDistrictLoop.setVisibility(View.VISIBLE);
        else if(flag == ONE_LEVEL)
            mCityLoop.setVisibility(View.GONE);

        inflate.findViewById(R.id.key_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.key_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressDialog.dismiss();
                String address = "";
                String pro = mProvinceList.get(mProvinceLoop.getSelectedItem());
                String city = mCityList.get(mCityLoop.getSelectedItem());
                String dis = mDistrictList.get(mDistrictLoop.getSelectedItem());
                if ("北京市".equals(pro) || "上海市".equals(pro) || "重庆市".equals(pro) || "天津市".equals(pro) || "香港".equals(pro) || "澳门".equals(pro) || "台湾省".equals(pro)) {
                    if(flag == ONE_LEVEL)
                        address = pro;
                    else if(flag == TWO_LEVEL||flag == THREE_LEVEL)
                        address = city+" "+dis;

                } else {
                    if(flag == ONE_LEVEL)
                        address = pro;
                    else if(flag == TWO_LEVEL)
                        address = pro+" "+city;
                    else if(flag == THREE_LEVEL)
                        address = pro+" "+city+" "+dis;
                }
                if(mOnAddressClickListener!=null){
                    mOnAddressClickListener.onAddressClick(address);
                }

            }
        });
        //将布局设置给Dialog
        initAddress(activity);
        addressDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = addressDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        activity.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        addressDialog.setCancelable(true);
        addressDialog.setCanceledOnTouchOutside(true);

        return addressDialog;
    }
    private void initAddress(Activity activity) {

        mProvinceLoop.setNotLoop();
        mCityLoop.setNotLoop();
        mDistrictLoop.setNotLoop();

        mProvinceLoop.setTextSize(13);
        mCityLoop.setTextSize(13);
        mDistrictLoop.setTextSize(13);
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(MyApplication.mApplication, "province_data.json");
        //  解析json数据
        if (province_data_json != null && province_data_json.length() > 0) {
            Gson gson = new Gson();
            mProvinceEntityList = gson.fromJson(province_data_json, new TypeToken<List<ProvinceEntity>>() {
            }.getType());

            mProvinceLoop.setItems(getProvince());
            mProvinceLoop.setOuterTextColor(activity.getResources().getColor(R.color.white_color_aplha));
            mProvinceLoop.setCenterTextColor(activity.getResources().getColor(R.color.black));

            mCityLoop.setItems(getCity(0));
            mCityLoop.setOuterTextColor(activity.getResources().getColor(R.color.white_color_aplha));
            mCityLoop.setCenterTextColor(activity.getResources().getColor(R.color.black));

            mDistrictLoop.setItems(getDistrict(0));
            mDistrictLoop.setOuterTextColor(activity.getResources().getColor(R.color.white_color_aplha));
            mDistrictLoop.setCenterTextColor(activity.getResources().getColor(R.color.black));

            mProvinceLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mCityLoop.setItems(getCity(index));
                    mCityLoop.setCurrentPosition(0);
                    mDistrictLoop.setItems(getDistrict(0));
                    mDistrictLoop.setCurrentPosition(0);
                }
            });

            mCityLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mDistrictLoop.setItems(getDistrict(index));
                    mDistrictLoop.setCurrentPosition(0);
                }
            });

            mDistrictLoop.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {

                }
            });
        }

    }
    private List<String> getProvince() {
        for (int i = 0; i < mProvinceEntityList.size(); i++) {
            mProvinceList.add(mProvinceEntityList.get(i).getName());
        }
        return mProvinceList;
    }

    private List<String> getCity(int index) {
        mCityList.clear();
        mCityBeanEntityList = mProvinceEntityList.get(index).getCity();
        for (int i = 0; i < mCityBeanEntityList.size(); i++) {
            mCityList.add(mCityBeanEntityList.get(i).getName());
        }
        return mCityList;
    }

    private List<String> getDistrict(int index) {
        mDistrictList.clear();
        for (int i = 0; i < mCityBeanEntityList.get(index).getArea().size(); i++) {
            mDistrictList.add(mCityBeanEntityList.get(index).getArea().get(i));
        }
        return mDistrictList;
    }




    private Dialog mCompanyDig;
    private Dialog mALJDig;
    private Dialog mVIPDig;
    private Dialog mWEBDig;


    private void showWEBDig() {
        View dView = LayoutInflater.from(mContext).inflate(R.layout.dig_web_main, null, false);
        if (mWEBDig == null) {
            mWEBDig = new Dialog(mContext, R.style.MyDialog);
        }
        WebView web = (WebView) dView.findViewById(R.id.web);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setTextSize(WebSettings.TextSize.SMALLER);
        settings.setTextZoom(100);
        web.setWebViewClient(new WebViewClient() {
            //当加载页面完成的时候回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        web.loadUrl("http://admin.shaoziketang.com/?c=welcome&m=appdetail&type=3&id=997");
        Window dialogWindow = mWEBDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = lp.MATCH_PARENT;
        lp.height = DisplayUtil.dip2px(mContext, 400);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        mWEBDig.setContentView(dView);
        mWEBDig.show();

    }

    private void showVIPDig() {
        View dView = LayoutInflater.from(mContext).inflate(R.layout.dig_vip_main, null, false);
        if (mVIPDig == null) {
            mVIPDig = new Dialog(mContext, R.style.MyDialog);
        }
        mVIPDig.setContentView(dView);

        dView.findViewById(R.id.vip_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewEventDetailsActivity.class);
                intent.putExtra("event_id", "997");
                mContext.startActivity(intent);
                mVIPDig.dismiss();
            }
        });
        dView.findViewById(R.id.vip_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVIPDig.dismiss();
            }
        });
        mVIPDig.show();

    }

    private void showALJDig(final String alj_id) {
        View dView = LayoutInflater.from(mContext).inflate(R.layout.dig_alj_main, null, false);
        mALJDig.setContentView(dView);

        ImageView close = (ImageView) dView.findViewById(R.id.close);
        ImageView aljBg = (ImageView) dView.findViewById(R.id.alj_bg);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mALJDig.dismiss();
            }
        });

        aljBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetUtil.getRetrofit(Api.class)
                        .cancel_yd(mUser_Id, "2")
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                if (mALJDig != null) {
                    mALJDig.dismiss();
                }
                Intent intent = new Intent(mContext, CasebookInviteActivity.class);
                intent.putExtra("event_id", alj_id);
                mContext.startActivity(intent);
            }
        });
        mALJDig.show();
    }

    private void showCompanyDig(final OnCompanyBuyClickListener listener) {
        View dView = LayoutInflater.from(mContext).inflate(R.layout.dig_company_recommend, null, false);
        mCompanyDig.setContentView(dView);
        ImageView cancel = (ImageView) dView.findViewById(R.id.cancel);
        ImageView immeBuy = (ImageView) dView.findViewById(R.id.imme_buy);
        mCompanyDig.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e("DH_USER_ID", "id:" + mUser_Id);
                NetUtil.getRetrofit(Api.class)
                        .cancel_yd(mUser_Id, "")
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCompanyDig.dismiss();
            }
        });
        immeBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCompanyDig.dismiss();
                if (listener!=null) {
                    listener.onCompanyBuyClick();
                    //监听操作
//                    mShowFunction = 1;
//                    mRbs[4].setChecked(true);
                }

            }
        });
        mCompanyDig.show();
    }

    private void showFunctionDig() {
        View dView = LayoutInflater.from(mContext).inflate(R.layout.dig_company_function, null, false);
        mCompanyDig.setContentView(dView);
        ViewPager funVp = (ViewPager) dView.findViewById(R.id.fun_vp);
        Button openCompany = (Button) dView.findViewById(R.id.open_company);

        final RadioButton[] rbs = new RadioButton[4];
        int[] ids = {R.id.vp_1, R.id.vp_2, R.id.vp_3, R.id.vp_4};
        for (int i = 0; i < 4; i++) {
            rbs[i] = (RadioButton) dView.findViewById(ids[i]);
        }
        funVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rbs[position].setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        openCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCompanyDig.dismiss();
                if (StringUtils.isEmpty(mUser_Id)) {
                    mContext.startActivity(new Intent(mContext, LoginNewActivity.class));
                    return;
                }
                Intent intent = new Intent(mContext, EnterpriseApplyActivity.class);
                intent.putExtra("state", "0");
                mContext.startActivity(intent);
            }
        });
        funVp.setAdapter(new FunctionPager(mContext));
        mCompanyDig.show();
    }


    public interface OnCompanyBuyClickListener{
        void onCompanyBuyClick();
    }


    int giftDismiss = 0;//防止跳转后再次执行dismiss方法
    public void showGiftBagDig(final OnGiftBagClickListener listener) {

        final Dialog gifDig = new Dialog(mContext, R.style.MyDialog);
        View dView = LayoutInflater.from(mContext).inflate(R.layout.activity_gift_dialog, null, false);
        gifDig.setContentView(dView);
        ImageView content = (ImageView) gifDig.findViewById(R.id.content);
        TextView get = (TextView) gifDig.findViewById(R.id.get);
        gifDig.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (listener!=null&&giftDismiss==0) {
                    listener.onGiftBagDismissClick();
                }
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giftDismiss = 1;
                if (listener!=null) {
                    listener.onGetGiftBagClick();
                }
                gifDig.dismiss();
            }
        });
        Window dialogWindow = gifDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(mContext, 283);
        lp.height = DisplayUtil.dip2px(mContext, 402);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        gifDig.setContentView(dView);
        gifDig.show();

    }


    public interface OnGiftBagClickListener{
        void onGetGiftBagClick();
        void onGiftBagDismissClick();
    }


    public void showServiceTel(final String phone, final int type, final String wx, final String qr,
                               final OnTelResultClickListener onTelResultClickListener){//type: 0 打电话   1 反馈是否成功
        final Dialog telDig = new Dialog(mContext, R.style.MyDialog);
        View dView = LayoutInflater.from(mContext).inflate(R.layout.dig_call, null, false);
        telDig.setContentView(dView);

        TextView title = (TextView) telDig.findViewById(R.id.title);
        TextView fail = (TextView) telDig.findViewById(R.id.fail);
        TextView success = (TextView) telDig.findViewById(R.id.success);

        title.setText(phone);

        if(type==0){
            fail.setText("取消");
            success.setText("确定");
        }else{
            fail.setText("失败");
            success.setText("成功");
        }
        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telDig.dismiss();
                if(type==1){
                    //请求接口通知未解决,弹出微信窗
                    showFailWxDig(wx,qr);
                    if (onTelResultClickListener!=null) {
                        onTelResultClickListener.onTelResultClick("2");
                    }
                }
            }
        });
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telDig.dismiss();
                if(type==0){

                    showServiceTel("是否成功联系专属顾问",1,wx,qr,onTelResultClickListener);

                    Intent intentNO = new Intent(Intent.ACTION_DIAL, Uri
                            .parse("tel:" + phone));
                    mContext.startActivity(intentNO);
                }else{
                    //请求接口通知解决
                    if (onTelResultClickListener!=null) {
                        onTelResultClickListener.onTelResultClick("1");
                    }
                }
            }
        });
        Window dialogWindow = telDig.getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.y = 20;//设置Dialog距离底部的距离
        lp.width = DisplayUtil.dip2px(mContext, 283);
        lp.height = DisplayUtil.dip2px(mContext, 120);
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);

        telDig.setContentView(dView);
        telDig.show();
    }


    public void showFailWxDig(final String wx,String qr){
        Dialog failWxDig = new Dialog(mContext,R.style.MyDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dig_service_fail,null,false);
        failWxDig.setContentView(view);

        ImageView qrImg = (ImageView) failWxDig.findViewById(R.id.qr);
        TextView copyHint = (TextView) failWxDig.findViewById(R.id.copy_hint);

        Glide.with(mContext).load(qr).into(qrImg);
        TextView copy = (TextView) failWxDig.findViewById(R.id.copy);
        copyHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(wx);
                Toast.makeText(MyApplication.mApplication, "复制成功，即将打开微信", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    Toast.makeText(MyApplication.mApplication, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(wx);
                Toast.makeText(MyApplication.mApplication, "复制成功，即将打开微信", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    Toast.makeText(MyApplication.mApplication, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Window dialogWindow = failWxDig.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DisplayUtil.dip2px(mContext, 283);
        lp.height = DisplayUtil.dip2px(mContext, 402);
        dialogWindow.setAttributes(lp);

        failWxDig.setContentView(view);
        failWxDig.show();
    }


    public void showCopyWxDig(String avatar,final String wx,String name,String qr){
        Dialog copyWxDig = new Dialog(mContext,R.style.MyDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dig_copy_wx,null,false);
        copyWxDig.setContentView(view);
        RoundedImageView avatarImg = (RoundedImageView) copyWxDig.findViewById(R.id.avatar);
        TextView nameTv = (TextView) copyWxDig.findViewById(R.id.name);
        ImageView qrImg = (ImageView) copyWxDig.findViewById(R.id.qr);
        TextView copyHint = (TextView) copyWxDig.findViewById(R.id.copy_hint);

        TextView wxTv = (TextView) copyWxDig.findViewById(R.id.wx);
        wxTv.setText("微信号："+wx);
        Glide.with(mContext).load(avatar).into(avatarImg);
        Glide.with(mContext).load(qr).into(qrImg);
        nameTv.setText(name);
        TextView copy = (TextView) copyWxDig.findViewById(R.id.copy);
        copyHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(wx);
                Toast.makeText(MyApplication.mApplication, "复制成功，即将打开微信", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    Toast.makeText(MyApplication.mApplication, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(wx);
                Toast.makeText(MyApplication.mApplication, "复制成功，即将打开微信", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    Toast.makeText(MyApplication.mApplication, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Window dialogWindow = copyWxDig.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DisplayUtil.dip2px(mContext, 283);
        lp.height = DisplayUtil.dip2px(mContext, 402);
        dialogWindow.setAttributes(lp);

        copyWxDig.setContentView(view);
        copyWxDig.show();
    }


    public interface OnTelResultClickListener{
        void onTelResultClick(String state);
    }
}
