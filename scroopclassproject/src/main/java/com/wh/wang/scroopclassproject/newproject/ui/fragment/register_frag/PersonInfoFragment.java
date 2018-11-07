package com.wh.wang.scroopclassproject.newproject.ui.fragment.register_frag;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.callback.OnRegisterNextClickListener;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.model.RegisterEntity;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.RegisterNXPresenter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonInfoFragment extends Fragment implements View.OnClickListener {
    private TextView mNext;
    private OnRegisterNextClickListener mOnRegisterNextClickListener;
    private String mUser_id;
    private RegisterNXPresenter mRegisterNXPresenter = new RegisterNXPresenter();
    private EditText mName;
    private EditText mPosition;
    private EditText mEmail;
    private EditText mIdentity;

    public PersonInfoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnRegisterNextClickListener){
            mOnRegisterNextClickListener = (OnRegisterNextClickListener) activity;
        }else{
            Log.e("DH_NEXT_LISTENER","未绑定监听");
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);
        initView(view);
        initListener();
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        return view;
    }

    private void initView(View view) {
        mNext = (TextView) view.findViewById(R.id.next);
        mName = (EditText) view.findViewById(R.id.name);
        mPosition = (EditText) view.findViewById(R.id.position);
        mEmail = (EditText) view.findViewById(R.id.email);
        mIdentity = (EditText) view.findViewById(R.id.identity);
    }


    private void initListener() {
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                if (!checkInfo()) {
                    Toast.makeText(getContext(), "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                mRegisterNXPresenter.registerNx(mUser_id, getParamMap(), new OnResultListener() {
                    @Override
                    public void onSuccess(Object... value) {
                        RegisterEntity entity = (RegisterEntity) value[0];
                        if(entity.getCode()==200){
                            if (mOnRegisterNextClickListener != null) {
                                mOnRegisterNextClickListener.OnNextClick(2);
                            }else{
                                Toast.makeText(getContext(), ""+entity.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFault(String error) {
                        Toast.makeText(getContext(), "访问异常", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
    private boolean checkInfo() {
        if(mName==null||mName.getText()==null||mName.getText().toString()==null||"".equals(mName.getText().toString().trim())){
            return false;
        }
        if(mPosition==null||mPosition.getText()==null||mPosition.getText().toString()==null||"".equals(mPosition.getText().toString().trim())){
            return false;
        }
        if(mEmail==null||mEmail.getText()==null||mEmail.getText().toString()==null||"".equals(mEmail.getText().toString().trim())){
            return false;
        }
        if(mIdentity==null||mIdentity.getText()==null||mIdentity.getText().toString()==null||"".equals(mIdentity.getText().toString().trim())){
            return false;
        }
        return true;
    }

    private Map<String,String> getParamMap() {
        Map<String,String> map = new HashMap<>();
        map.put("name",mName.getText().toString());
        map.put("position",mPosition.getText().toString());
        map.put("email",mEmail.getText().toString());
        map.put("company_ide",mIdentity.getText().toString());
        return map;
    }
}
