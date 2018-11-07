package com.wh.wang.scroopclassproject.newproject.ui.fragment.result_frag;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.vip.activity.VipListActivity;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.CustomerEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.PaySuccessEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CustomerPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.EventDetailsPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.PaySuccessPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewOrderWebActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.ResultActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.UserInfoAdapter;
import com.wh.wang.scroopclassproject.newproject.utils.DialogUtils;
import com.wh.wang.scroopclassproject.newproject.utils.ToastUtils;
import com.wh.wang.scroopclassproject.utils.LogUtil;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewApplyResultFragment extends Fragment implements View.OnClickListener {
    private TextView mCourse;
    private TextView mTime;
    private TextView mAddress;
    private TextView mCommGroup;
    private TextView mApplyDetail;
//    private TextView mVipContent;
//    private TextView mOpenVip;
//    private RelativeLayout mVip;
    private TextView mApplyContinue;
    private RelativeLayout mApplyNoVipContent;

    private String mEventId;
    private String mUserId;
    private EventDetailsPresenter mEventDetailsPresenter = new EventDetailsPresenter();
    private PaySuccessPresenter mPaySuccessPresenter = new PaySuccessPresenter();
//    private EventDetailEntity mEventDetailEntity;
    private String mCode_hao;
    private String mWeixin_name;
    private String mImg_code;
    private PaySuccessEntity.InfoBean mInfo;
    private String mWeixin_hao;

    private RelativeLayout mServiceContent;
    private RoundedImageView mAvatar;
    private TextView mName;
    private ImageView mTel;
    private ImageView mWx;
    private DialogUtils mDialogUtils;
    private CustomerPresenter mCustomerPresenter = new CustomerPresenter();
    private CustomerEntity.InfoBean mCustomerInfo;
    private String mIsplay;
    private String mType;
    private LinearLayout menu;
    private TextView topapplydetail;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private UserInfoAdapter mAdapter;
    private List<ContactEntity.InfoBean> infoList = new ArrayList<>();


    public NewApplyResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_apply_result, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        Bundle b = getArguments();
        mEventId = b.getString("order_no");
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(getContext(), "user_id", "");
        mType = b.getString("type");
        if (mType.equals("4")){
            menu.setVisibility(View.GONE);
        }else if (mType.equals("1")){
            menu.setVisibility(View.VISIBLE);
            mApplyDetail.setVisibility(View.GONE);
            mApplyContinue.setBackgroundResource(R.drawable.apply_share_bg);
        }
        getSuccessInfo();

        mDialogUtils = new DialogUtils(getContext());
    }

    private void getSuccessInfo() {
        mCustomerPresenter.getCustomerInfo(mUserId, new OnResultListener() {
            @Override
            public void onSuccess(Object... value) {
                CustomerEntity customerEntity = (CustomerEntity) value[0];
                if (customerEntity.getInfo()!=null) {
                    mCustomerInfo = customerEntity.getInfo();
                    mIsplay = mCustomerInfo.getIsplay();
                    if ("2".equals(mIsplay)) {
                        mServiceContent.setVisibility(View.VISIBLE);
                        Glide.with(getContext()).load(mCustomerInfo.getAvator()).into(mAvatar);
                        mName.setText("专属顾问"+mCustomerInfo.getName());
                    }else{
                        mServiceContent.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFault(String error) {

            }
        });

        mPaySuccessPresenter.paySuccess(mUserId, mEventId, new OnResultListener() {

            @Override
            public void onSuccess(Object... value) {
                PaySuccessEntity entity = (PaySuccessEntity) value[0];
                if(entity.getCode()==200){
                    Gson gson  = new Gson();
                    Log.e("ddddddd",gson.toJson(entity));
                    mInfo = entity.getInfo();
                    mWeixin_name = mInfo.getWeixin_name();
                    mCode_hao = mInfo.getCode_hao();

                    mWeixin_hao = mInfo.getWeixin_hao();
                    mImg_code = mInfo.getImg_code();
                    mCourse.setText(mInfo.getTitle());
                    mTime.setText(mInfo.getStart_shijian());
                    mAddress.setText(mInfo.getAddress());

                    for (int i=0;i<mInfo.getBatch().size();i++){
                        ContactEntity.InfoBean infoBean = new ContactEntity.InfoBean();
                        infoBean.setName(mInfo.getBatch().get(i).getName());
                        infoBean.setPhone(mInfo.getBatch().get(i).getPhone());
                        infoList.add(infoBean);
                    }
                    mAdapter = new UserInfoAdapter(getActivity(), infoList,"0");
                    mRecyclerView.setAdapter(mAdapter);

                }else{
                    Toast.makeText(MyApplication.mApplication, ""+entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
                if("997".equals(mEventId)||"1407".equals(mEventId)){
                }else{
                    checkApply();
                    showGroupDialog(mWeixin_name,mWeixin_hao);
                }

            }

            @Override
            public void onFault(String error) {
                ToastUtils.showToast(getActivity(),mUserId+"+++++++++++"+mEventId);

                Log.e("DH_PAU_SUCCESS",error);
            }
        });
//        mEventDetailsPresenter.getEventDetails(mEventId, mUserId, new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//                mEventDetailEntity = (EventDetailEntity) value[0];
//                if (mEventDetailEntity.getInfo() != null){
//                    EventDetailEntity.InfoBean info = mEventDetailEntity.getInfo();
//                    showGroupDialog("勺布斯","7896");
//                    mCourse.setText(info.getTitle());
//                    mTime.setText(info.getStart_shijian());
//                    mAddress.setText(info.getAddress());
//                    double subPrice;
//                    try{
//                        subPrice = Double.parseDouble(info.getSub_price());
//                    }catch (Exception e){
//                        subPrice = 0;
//                    }
//                    if(info.getIs_vip()==0&&subPrice>0){
//                        mVip.setVisibility(View.VISIBLE);
//                        mVipContent.setText("此时成为会员,立即节省"+subPrice+"元");
//                    }else{
//                        mVip.setVisibility(View.GONE);
//                    }
//
//                } else {
//                    Toast.makeText(getContext(), "数据请求异常", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFault(String error) {
//
//            }
//        });
    }


    private void checkApply() {
        String isBao = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "isBao", "");
        String isRepeat = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "isRepeat", "");
        String isVip = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "isVip", "");
        String applyPayStatus = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "applyPayStatus", "");
        String eventPower = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "eventPower", "");
        if ("1".equals(isBao)) { //是否可以报名
            if ("1".equals(applyPayStatus)) { //支付状态
                if ("1".equals(isRepeat)) { //是否可重复报名
                    if ("1".equals(isVip)) { //是否是Vip可报名 否则 都可以报名
                        if (!"1".equals(mInfo.getIs_vip())) { //当前用户是否为vip
                            mApplyContinue.setText("非会员无法报名");
                            mApplyContinue.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
                            mApplyContinue.setOnClickListener(null);
                        }
                    }
                }
            } else {
                if ("1".equals(eventPower)) {
                    if (!"1".equals(mInfo.getIs_vip())) { //当前用户是否为vip
                        mApplyContinue.setText("非会员无法报名");
                        mApplyContinue.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
                        mApplyContinue.setOnClickListener(null);
                    }
                }
            }
        } else {
            mApplyContinue.setText("报名结束");
            mApplyContinue.setBackgroundResource(R.drawable.shape_no_apply_bt_bg);
            mApplyContinue.setOnClickListener(null);
        }
        if("997".equals(mEventId)||"1407".equals(mEventId)){
            mApplyContinue.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        mCourse = (TextView) view.findViewById(R.id.course);
        mTime = (TextView) view.findViewById(R.id.time);
        mAddress = (TextView) view.findViewById(R.id.address);
        mCommGroup = (TextView) view.findViewById(R.id.comm_group);
        mApplyDetail = (TextView) view.findViewById(R.id.apply_detail);
//        mVipContent = (TextView) view.findViewById(R.id.vip_content);
//        mOpenVip = (TextView) view.findViewById(R.id.open_vip);
//        mVip = (RelativeLayout) view.findViewById(R.id.vip);
        mApplyContinue = (TextView) view.findViewById(R.id.apply_continue);
        mApplyNoVipContent = (RelativeLayout) view.findViewById(R.id.apply_no_vip_content);


        mServiceContent = (RelativeLayout) view.findViewById(R.id.service_content);
        mAvatar = (RoundedImageView) view.findViewById(R.id.avatar);
        mName = (TextView) view.findViewById(R.id.name);
        mTel = (ImageView) view.findViewById(R.id.tel);
        mWx = (ImageView) view.findViewById(R.id.wx);
        menu = (LinearLayout) view.findViewById(R.id.menu);
        topapplydetail = (TextView) view.findViewById(R.id.topapply_detail);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_querenlist);

        //解决嵌套卡顿问题
        linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    private void initListener() {
        mCommGroup.setOnClickListener(this);
//        mOpenVip.setOnClickListener(this);
        mApplyContinue.setOnClickListener(this);
        mApplyDetail.setOnClickListener(this);
        mTel.setOnClickListener(this);
        mWx.setOnClickListener(this);
        topapplydetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comm_group:
                showGroupDialog(mWeixin_name,mWeixin_hao);
//                showGroupDialog();
                break;
            case R.id.apply_detail:
                Intent intent ;
                if("997".equals(mEventId)||"1407".equals(mEventId)){
                    intent = new Intent(getContext(), VipListActivity.class);
                    startActivity(intent);
                }else{
                    if(mInfo!=null){
                        if(StringUtils.isNotEmpty(mInfo.getRelative_video())){
                            if("4".equals(mInfo.getVideo_type())){
                                intent = new Intent(getContext(), SumUpActivity.class);
                            }else{
                                intent = new Intent(getContext(), NewVideoInfoActivity.class);
                            }
                            intent.putExtra("id",mInfo.getRelative_video());
                            intent.putExtra("type",mInfo.getVideo_type()+"");
                            startActivity(intent);
                        }else{
                            intent = new Intent(getContext(), NewOrderWebActivity.class);
                            intent.putExtra("pid",mInfo.getPid());
                            startActivity(intent);
                        }
                    }

                }

                break;
            case R.id.open_vip:
//                intent = new Intent(getContext(),VipListActivity.class);
//                intent.putExtra("event_id","997");
//                startActivity(intent);
                break;
            case R.id.apply_continue:
                if(mInfo!=null){
//                    if(!"1".equals(mInfo.getMy_bao())){
//                        intent = new Intent(getContext(), NewEventInfoActivity.class);
//                        EventDetailEntity.InfoBean bean = new EventDetailEntity.InfoBean();
//                        bean.setTitle(mInfo.getTitle());
//                        bean.setTicket_id("");
//                        bean.setId(mEventId);
//                        bean.setReal_price(mInfo.getReal_price());
//                        bean.setVip_price(mInfo.getVip_price());
//                        bean.setPrice(mInfo.getPrice());
//                        bean.setIs_vip(Integer.parseInt(mInfo.getIs_vip()));
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("eventDetailBean", bean);
//                        intent.putExtras(bundle);
//                    }else{
//                        intent = new Intent(getContext(), SelectApplyActivity.class);
//                        intent.putExtra("id",mEventId);
//                        intent.putExtra("title",mInfo.getTitle());
//                        intent.putExtra("original_price",mInfo.getPrice()+"");
//                        intent.putExtra("vip",Integer.parseInt(mInfo.getIs_vip()));
//                        intent.putExtra("apply_type",1);
//                    }
                    intent = new Intent(getContext(), NewEventInfoActivity.class);
//                    EventDetailEntity.InfoBean bean = new EventDetailEntity.InfoBean();
//                    bean.setTitle(mInfo.getTitle());
//                    bean.setTicket_id("");
//                    bean.setId(mEventId);
//                    bean.setReal_price(mInfo.getReal_price());
//                    bean.setVip_price(mInfo.getVip_price());
//                    bean.setPrice(mInfo.getPrice());
//                    bean.setIs_vip(Integer.parseInt(mInfo.getIs_vip()));
                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("eventDetailBean", bean);
                    bundle.putString("title",mInfo.getTitle());
                    bundle.putString("event_id",mEventId);
                    bundle.putString("real_price",mInfo.getReal_price());
                    bundle.putString("vip_price",mInfo.getVip_price());
                    bundle.putString("price",mInfo.getPrice());
                    bundle.putString("is_vip",mInfo.getIs_vip());
                    bundle.putString("isPayCourseOrAction","1");
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else{
                    Toast.makeText(MyApplication.mApplication, "请求数据错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.wx:
                mDialogUtils.showCopyWxDig(mCustomerInfo.getAvator(),mCustomerInfo.getWechat_number(),mCustomerInfo.getName(),mCustomerInfo.getQr_code());
                break;
            case R.id.tel:
                mDialogUtils.showServiceTel(mCustomerInfo.getPhone(), 0, mCustomerInfo.getWechat_number(), mCustomerInfo.getQr_code(),
                        new DialogUtils.OnTelResultClickListener() {
                            @Override
                            public void onTelResultClick(String state) {
                                mCustomerPresenter.telResult(mUserId,mCustomerInfo.getCustomer_id(),state);
                            }
                        });
                break;
            case R.id.topapply_detail:

                if("997".equals(mEventId)||"1407".equals(mEventId)){
                    intent = new Intent(getContext(), VipListActivity.class);
                    startActivity(intent);
                }else{
                    if(mInfo!=null){
                        if(StringUtils.isNotEmpty(mInfo.getRelative_video())){
                            if("4".equals(mInfo.getVideo_type())){
                                intent = new Intent(getContext(), SumUpActivity.class);
                            }else{
                                intent = new Intent(getContext(), NewVideoInfoActivity.class);
                            }
                            intent.putExtra("id",mInfo.getRelative_video());
                            intent.putExtra("type",mInfo.getVideo_type()+"");
                            startActivity(intent);
                        }else{
                            intent = new Intent(getContext(), NewOrderWebActivity.class);
                            intent.putExtra("pid",mInfo.getPid());
                            startActivity(intent);
                        }
                    }

                }
                break;

        }
    }

    private Dialog mGroupDig;
    private void showGroupDialog(String weichat_name,String id) {
        mGroupDig = new Dialog(getActivity(), R.style.MyDialog);
        mGroupDig.setContentView(R.layout.dig_in_group);
        TextView applyHint = (TextView) mGroupDig.findViewById(R.id.apply_hint);
        TextView wxNumber = (TextView) mGroupDig.findViewById(R.id.tv_wxnumber);
        ImageView qrImg = (ImageView) mGroupDig.findViewById(R.id.qr_img);
        Glide.with(getContext()).load(mImg_code).centerCrop().into(qrImg);
        String content = "添加客服微信："+weichat_name;
        String wxcontent = "微信客服号："+id;


        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#8dc63f")), 7,content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString spannableString1 = new SpannableString(wxcontent);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#8dc63f")), 6,wxcontent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        applyHint.setText(spannableString);
        wxNumber.setText(spannableString1);

       mGroupDig.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mGroupDig!=null&&mGroupDig.isShowing()){
                   mGroupDig.dismiss();
               }
           }
       });

        mGroupDig.show();
    }

    public void finish() {
        Intent intent = new Intent(getContext(),NewEventDetailsActivity.class);
        intent.putExtra("event_id",mEventId);
        startActivity(intent);
    }


    private void showGroupDialog() {
        final Dialog groupDig = new Dialog(getActivity(), R.style.MyDialog);
        groupDig.setContentView(R.layout.dig_apply_success);
        groupDig.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupDig!=null&&groupDig.isShowing()){
                    groupDig.dismiss();
                }
            }
        });

        groupDig.show();
    }
}
