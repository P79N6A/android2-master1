package com.wh.wang.scroopclassproject.newproject.ui.fragment.casebook_frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CasebookStateFragment extends Fragment {
    private FrameLayout mOrderState;
    private String mState;
    private TextView mName;
    private TextView mTel;
    private TextView mAddress;

    private FragmentManager mFragmentManager;
    private WaitFragment mWaitFragment;
    private OrderFragment mOrderFragment;
    private String order_id;

    public CasebookStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_casebook_state, container, false);
        mOrderState = (FrameLayout) view.findViewById(R.id.order_state);
        mName = (TextView) view.findViewById(R.id.name);
        mTel = (TextView) view.findViewById(R.id.tel);
        mAddress = (TextView) view.findViewById(R.id.address);

        mState = getArguments().getString("state");
        mName.setText(getArguments().getString("name")!=null?getArguments().getString("name"):"");
        mTel.setText(getArguments().getString("tel")!=null?getArguments().getString("tel"):"");
        mAddress.setText(getArguments().getString("address")!=null?getArguments().getString("address"):"");
        order_id = getArguments().getString("order_id");
        mFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mState.equals("1")){
            mWaitFragment = new WaitFragment();
            fragmentTransaction.add(R.id.order_state,mWaitFragment);
        }else{
            mOrderFragment = new OrderFragment();
            Bundle bundle = new Bundle();
            bundle.putString("order",mState.equals("2")?"0":"1");
            bundle.putString("order_id",order_id);
            mOrderFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.order_state,mOrderFragment);
        }
        fragmentTransaction.commit();
        return view;
    }

}
