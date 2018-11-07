package com.wh.wang.scroopclassproject.newproject.ui.fragment.contact_frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.ContactMap;
import com.wh.wang.scroopclassproject.newproject.eventmodel.EventContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.model.ContactEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.CheckEventPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.CompanyContactAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyContactFragment extends Fragment implements View.OnClickListener {
    private TextView mSelectNum;
    private TextView mAllSelect;
    private RecyclerView mMemberList;
    private String mEvent_id;
    private ContactMap mContact_map;
    private Map<String, ContactEntity.InfoBean> map;
    private Set<String> mContactKey;
    private List<ContactEntity.InfoBean> mCompany_list;
    private CompanyContactAdapter mCompanyContactAdapter;
    private CheckEventPresenter mCheckEventPresenter = new CheckEventPresenter();
    private int mCurrentNum = 0;
    private String mUser_id;
    private String mMobile;

    public CompanyContactFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_contact, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }


    private void initData() {
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "mobile", "");
        mEvent_id = getArguments().getString("id");
        mContact_map = (ContactMap) getArguments().getSerializable("contact_map");
        mCompany_list = (List<ContactEntity.InfoBean>) getArguments().getSerializable("company_list");
        map = mContact_map.getInfoBeanMap();
        mContactKey = map.keySet();
        if(mCompany_list!=null&&mCompany_list.size()>0){
            if(mContactKey.size()>0){
                for (int i = 0; i < mCompany_list.size(); i++) {
                    if(mContactKey.contains(mCompany_list.get(i).getPhone())){
                        mCompany_list.get(i).setCheck(true);
                        mCompany_list.get(i).setOperation(true);
                        mCompany_list.get(i).setPrice(mContact_map.getInfoBeanMap().get(mCompany_list.get(i).getPhone()).getPrice());
                    }
                }
            }
            mCompanyContactAdapter = new CompanyContactAdapter(mCompany_list);
            mMemberList.setAdapter(mCompanyContactAdapter);
            mCompanyContactAdapter.setOnSelectClickListener(new CompanyContactAdapter.OnSelectClickListener() {
                @Override
                public void onSelectClick(int pos, final ContactEntity.InfoBean infoBean, final ImageView imageView) {
                    if(infoBean.isCheck()){
                        imageView.setImageResource(R.drawable.bm_icon_unselect);
                        infoBean.setCheck(false);
                        mCurrentNum--;
                    }else{
//                        //有价格代表曾经检查过，直接跳过检查
//                        if((infoBean.getPrice()!=null&&!"".equals(infoBean.getPhone()))||infoBean.getPhone().equals(mMobile)){
//                            imageView.setImageResource(R.drawable.bm_icon_select);
//                            infoBean.setCheck(true);
//                            mCurrentNum++;
//                            mSelectNum.setText("已选"+mCurrentNum+"人");
//                        }else{
//                            /**
//                             * TODO 添加更多检查
//                             */
//                            mCheckEventPresenter.checkMoreEvent(infoBean.getPhone(), mEvent_id, "", mUser_id, new OnResultListener() {
//
//                                @Override
//                                public void onSuccess(Object... value) {
//                                    CheckEventEntity eventEntity = (CheckEventEntity) value[0];
//                                    if(eventEntity.getStatus()==1){
//                                        imageView.setImageResource(R.drawable.bm_icon_select);
//                                        infoBean.setCheck(true);
//                                        infoBean.setPrice(eventEntity.getInfo());
//                                        mCurrentNum++;
//                                        mSelectNum.setText("已选"+mCurrentNum+"人");
//                                    }else{
//                                        Toast.makeText(getContext(), ""+eventEntity.getInfo(), Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//
//                                @Override
//                                public void onFault(String error) {
//                                    Toast.makeText(getContext(), "检测异常", Toast.LENGTH_SHORT).show();
//                                    Log.e("DH_CHECK_MORE", error);
//                                }
//                            });
//                        }
                        imageView.setImageResource(R.drawable.bm_icon_select);
                        infoBean.setCheck(true);
//                        infoBean.setPrice(eventEntity.getInfo());
                        mCurrentNum++;
                    }
                    mSelectNum.setText("已选"+mCurrentNum+"人");

                }
            });
        }

    }

    private void initView(View view) {
        mSelectNum = (TextView) view.findViewById(R.id.select_num);
        mAllSelect = (TextView) view.findViewById(R.id.all_select);
        mMemberList = (RecyclerView) view.findViewById(R.id.member_list);
        mMemberList.setLayoutManager(new LinearLayoutManager(MyApplication.mApplication));
    }

    public void finish() {
        if(mCompany_list!=null&&mCompany_list.size()>0){
            List<ContactEntity.InfoBean> list = new ArrayList<>();
            for (int i = 0; i < mCompany_list.size(); i++) {
                if(mCompany_list.get(i).isCheck()&&mCompany_list.get(i).getPhone()!=null&&!"".equals(mCompany_list.get(i))){
                    list.add(mCompany_list.get(i));
                }
                mCurrentNum = list.size();
            }
            if(mCurrentNum>0){
                EventBus.getDefault().post(new EventContactEntity(list));
                getActivity().finish();
            }else{
                Toast.makeText(getContext(), "请至少选择一位联系人", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "当前企业没有联系人", Toast.LENGTH_SHORT).show();
        }
    }


    private void initListener() {
        mAllSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_select:
                if(mCompany_list!=null&&mCompany_list.size()>0){
                    mCurrentNum = 0;
                    for (int i = 0; i < mCompany_list.size(); i++) {
                        if(mCompany_list.get(i).isJoinAccess()){
                            mCurrentNum++;
                            mCompany_list.get(i).setCheck(true);
                        }
                    }
                    if(mCompanyContactAdapter!=null){
                        mCompanyContactAdapter.notifyDataSetChanged();
                    }
                    mSelectNum.setText("已选"+mCurrentNum+"人");
                }
                break;
        }
    }
}
