package com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag1_0;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.wh.wang.scroopclassproject.R;
public class ActionRuleFragment extends Fragment {

    private boolean isMeasured;
    private LinearLayout mContent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_action_rule, container, false);
        mContent = (LinearLayout) view.findViewById(R.id.content);
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!isMeasured) {
                    if(mOnRuleHeightClickListener!=null){
                        mOnRuleHeightClickListener.onRuleHeightClick(mContent.getMeasuredHeight());
                    }
                    isMeasured = true;
                }
                return true;
            }
        });
        return view;
    }

    private OnRuleHeightClickListener mOnRuleHeightClickListener;

    public void setOnRuleHeightClickListener(OnRuleHeightClickListener onRuleHeightClickListener) {
        mOnRuleHeightClickListener = onRuleHeightClickListener;
    }

    public interface OnRuleHeightClickListener{
        void onRuleHeightClick(int height);
    }

}
