package com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.Constant;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewSearchActivity;
import com.wh.wang.scroopclassproject.newproject.ui.adapter.BoutiqueVPAdapter;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.boutique_frag.ChoicenessFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.boutique_frag.FreeCourseFragment;
import com.wh.wang.scroopclassproject.newproject.ui.fragment.boutique_frag.SystemCourseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils.getStatusBarHeight;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mBoutiqueVp;
    private FrameLayout mScrollBar;
    private RelativeLayout mTitlebarNew;

    private int mPhoneW;
    private TextView mChoiceness;
    private TextView mSystem;
    private TextView mFree;
    private TextView []mTitleTvs;
    private List<Fragment> mBoutiqueFrags = new ArrayList<>();
    private FragmentManager supportFragmentManager;
    public BoutiqueFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        initView(view);
        initSize();
        initListener();
        initFrag();
        return view;
    }


    private void initFrag() {
        supportFragmentManager = getChildFragmentManager();
        mBoutiqueFrags.add(new ChoicenessFragment());
        mBoutiqueFrags.add(new SystemCourseFragment());
        mBoutiqueFrags.add(new FreeCourseFragment());
        mBoutiqueVp.setAdapter(new BoutiqueVPAdapter(supportFragmentManager,mBoutiqueFrags));
        mBoutiqueVp.setOffscreenPageLimit(3);
    }

    private void initView(View view) {
        mBoutiqueVp = (ViewPager) view.findViewById(R.id.boutique_vp);
        mScrollBar = (FrameLayout) view.findViewById(R.id.scroll_bar);
        mChoiceness = (TextView) view.findViewById(R.id.choiceness);
        mSystem = (TextView) view.findViewById(R.id.system);
        mFree = (TextView) view.findViewById(R.id.free);
        mTitlebarNew = (RelativeLayout) view.findViewById(R.id.titlebar_new);

        mTitleTvs = new TextView[]{mChoiceness,mSystem,mFree};
        view.findViewById(R.id.content).setPadding(0, getStatusBarHeight(getContext()), 0, 0);
    }


    private void initListener() {
        mChoiceness.setOnClickListener(this);
        mSystem.setOnClickListener(this);
        mFree.setOnClickListener(this);
        mBoutiqueVp.addOnPageChangeListener(this);
        mTitlebarNew.setOnClickListener(this);
    }


    /**
     * 初始化滚动条大小
     */
    private void initSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mPhoneW = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = mScrollBar.getLayoutParams();
        layoutParams.width = mPhoneW/3;
        mScrollBar.setLayoutParams(layoutParams);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private int startX = 0;
    private void selectInfoAni(final int item) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startX, mPhoneW / 3 * item, 0, 0);
        translateAnimation.setDuration(200);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTitleTvs[item].setTextColor(getResources().getColor(R.color.t_select));
                for (int i = 0; i < mTitleTvs.length; i++) {
                    if (i != item) {
                        mTitleTvs[i].setTextColor(getResources().getColor(R.color.t_unselect));
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mScrollBar.startAnimation(translateAnimation);
        startX = mPhoneW / 3 * item;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choiceness:
                mBoutiqueVp.setCurrentItem(0);
                break;
            case R.id.system:
                mBoutiqueVp.setCurrentItem(1);
                break;
            case R.id.free:
                mBoutiqueVp.setCurrentItem(2);
                break;
            case R.id.titlebar_new:
                Intent intent = new Intent(getContext(), NewSearchActivity.class);
                startActivityForResult(intent, Constant.SEARCH_REQ);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectInfoAni(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
