package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.GetAlipayEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BindZFBInfoFragment extends Fragment implements OnResultListener {
    private EditText mZfbNo;
    private ImageView mDelZfbNo;
    private EditText mZfbName;
    private ImageView mDelZfbName;
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private String mUserId;

    public BindZFBInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bind_zfbinfo, container, false);

        mZfbNo = (EditText) view.findViewById(R.id.zfb_no);
        mDelZfbNo = (ImageView) view.findViewById(R.id.del_zfb_no);
        mZfbName = (EditText) view.findViewById(R.id.zfb_name);
        mDelZfbName = (ImageView) view.findViewById(R.id.del_zfb_name);
        initListener();
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(getContext(),"user_id","");
        mStudyGroupPresenter.getAlipayInfo(mUserId,this);
        return view;
    }

    private void initListener() {

        mDelZfbNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZfbNo.setText("");
            }
        });

        mDelZfbName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZfbName.setText("");
            }
        });
    }

    public void checkInfo() {
        if (mZfbNo.getText()==null||mZfbNo.getText().toString()==null||
                StringUtils.isEmpty(mZfbNo.getText().toString().trim())) {
            Toast.makeText(MyApplication.mApplication, "请输入支付宝账号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mZfbName.getText()==null||mZfbName.getText().toString()==null||
                StringUtils.isEmpty(mZfbName.getText().toString().trim())) {
            Toast.makeText(MyApplication.mApplication, "请输入支付宝账号名", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mOnCommitZFBClickListener!=null) {
            mOnCommitZFBClickListener.onCommitClick(mZfbName.getText().toString(),mZfbNo.getText().toString());
        }
    }

    private OnCommitZFBClickListener mOnCommitZFBClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnCommitZFBClickListener){
            mOnCommitZFBClickListener = (OnCommitZFBClickListener) activity;
        }
    }

    @Override
    public void onSuccess(Object... value) {
        GetAlipayEntity getAlipayEntity = (GetAlipayEntity) value[0];
        if (getAlipayEntity.getInfo()!=null) {

            mZfbNo.setText(getAlipayEntity.getInfo().getAlipay_accout());
            mZfbName.setText(getAlipayEntity.getInfo().getAlipay_name());
        }

    }

    @Override
    public void onFault(String error) {

    }

    public interface OnCommitZFBClickListener{
        void onCommitClick(String name,String no);
    }
}
