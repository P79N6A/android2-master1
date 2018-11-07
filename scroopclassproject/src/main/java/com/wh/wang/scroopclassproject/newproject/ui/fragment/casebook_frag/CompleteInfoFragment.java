package com.wh.wang.scroopclassproject.newproject.ui.fragment.casebook_frag;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.AljPresenter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteInfoFragment extends Fragment implements View.OnClickListener {
    private TextView mTvAddress;
    private TextView mSure;
    private EditText mEditName;
    private EditText mEditTel;
    private EditText mEditPlace;
    private AljPresenter mAljPresenter = new AljPresenter();
    private Map<String,String> map = new HashMap<>();
    private String mUser_id;
    private String mEvent_id;
    private LinearLayout mAddress;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnInfoClickListener){
            mOnInfoClickListener = (OnInfoClickListener) context;
        }
    }

    public CompleteInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_info, container, false);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mSure = (TextView) view.findViewById(R.id.sure);
        mAddress = (LinearLayout) view.findViewById(R.id.address);

        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditTel = (EditText) view.findViewById(R.id.edit_tel);
        mEditPlace = (EditText) view.findViewById(R.id.edit_place);
        mEvent_id = getArguments().getString("event_id");
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
        initListener();
        return view;
    }

    private void initListener() {
        mAddress.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    private OnInfoClickListener mOnInfoClickListener;
    Dialog dialog;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.address:
                if(mOnInfoClickListener!=null){
                    mOnInfoClickListener.onAddressClick();
                }
                break;
            case R.id.sure:
                if(!checkInfo()){
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.please_confirm_address)
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                map.put("address",mEditPlace.getText().toString());
                                map.put("area",mTvAddress.getText().toString().replace(" ",""));
                                map.put("user_id",mUser_id);
                                map.put("event_id","1363");
                                map.put("name",mEditName.getText().toString());
                                map.put("tel",mEditTel.getText().toString());
                                mAljPresenter.commitInfo(map, new OnResultListener() {
                                    @Override
                                    public void onSuccess(Object... value) {
                                        if(mOnInfoClickListener!=null){
                                            mOnInfoClickListener.onNextClick(1,mEditName.getText().toString(),mEditTel.getText().toString()
                                                    ,mTvAddress.getText().toString()+mEditPlace.getText().toString());
                                        }
                                    }

                                    @Override
                                    public void onFault(String error) {
                                        Log.e("DH_ALJ_COMMIT",error);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dialog!=null) {
                                    dialog.dismiss();
                                }

                            }
                        });
                dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();

                break;
        }
    }

    public boolean checkInfo() {
        if(StringUtils.isEmpty(mEditName.getText().toString())){
            Toast.makeText(MyApplication.mApplication, "请填写名字", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(mEditTel.getText().toString())){

            Toast.makeText(MyApplication.mApplication, "请填写手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(mTvAddress.getText().toString())){

            Toast.makeText(MyApplication.mApplication, "请填写地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StringUtils.isEmpty(mEditPlace.getText().toString())){

            Toast.makeText(MyApplication.mApplication, "请完善详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setAddress(String address) {
        mTvAddress.setText(address);
    }

    public interface OnInfoClickListener{
        void onAddressClick();
        void onNextClick(int type,String name,String tel,String address);
    }
}
