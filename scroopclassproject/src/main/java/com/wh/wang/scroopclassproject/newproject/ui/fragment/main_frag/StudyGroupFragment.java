package com.wh.wang.scroopclassproject.newproject.ui.fragment.main_frag;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0.StudyGroupInfo_2_0_Activity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.adapter.adapter2_0.SGHomeCourse_2_0_Adapter;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.SGHomeDataFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.fragment.frag2_0.SGHomeNoDataFragment;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGHome2_0Entity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.StudyGroupPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.utils.LoadingUtils;
import com.wh.wang.scroopclassproject.newproject.view.EmptyRecycleAdapter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.wh.wang.scroopclassproject.newproject.utils.StatusBarUtils.getStatusBarHeight;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudyGroupFragment extends Fragment implements SGHomeCourse_2_0_Adapter.OnJoinItemClickListener, OnResultListener {
    private TextView mStatusTitle;
    private RecyclerView mSgHotList;
    private String mUserId;
    private SGHomeDataFragment mSGHomeDataFragment;
    private SGHomeNoDataFragment mSGHomeNoDataFragment;
    private FragmentManager mChildFragmentManager;
    private SGHomeCourse_2_0_Adapter mSGHomeCourseAdapter;
    private ImageView mSgRule;
    private StudyGroupPresenter mStudyGroupPresenter = new StudyGroupPresenter();
    private EmptyRecycleAdapter mEmptyRecycleAdapter;


    public StudyGroupFragment() {
        // Required empty public constructor
    }

    private Handler mViewLoadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_group, container, false);
        mStatusTitle = (TextView) view.findViewById(R.id.status_title);
        mSgHotList = (RecyclerView) view.findViewById(R.id.sg_hot_list);
        mSgHotList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSgRule = (ImageView) view.findViewById(R.id.sg_rule);
        mSgHotList.setHasFixedSize(true);
        mSgHotList.setNestedScrollingEnabled(false);
        mStatusTitle.post(new Runnable() {
            @Override
            public void run() {
                int statusBarHeight = getStatusBarHeight(getContext());
                mStatusTitle.measure(0, 0);
                ViewGroup.LayoutParams layoutParams = mStatusTitle.getLayoutParams();
                layoutParams.height = mStatusTitle.getHeight() + statusBarHeight;
                mStatusTitle.setLayoutParams(layoutParams);
                mStatusTitle.setPadding(0, statusBarHeight, 0, 0);
            }
        });
        mChildFragmentManager = getChildFragmentManager();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        accessNet();
    }


//    private Dialog mCardDig;
//
//    private void showCardDig() {
//        mCardDig = new Dialog(getContext(), R.style.MyDialog);
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.dig_sg_achieve, null, false);
//        mCardDig.setContentView(view);//
//
//        final RelativeLayout content = (RelativeLayout) mCardDig.findViewById(R.id.content);
//        ImageView wx = (ImageView) mCardDig.findViewById(R.id.wx);
//        ImageView qr = (ImageView) mCardDig.findViewById(R.id.qr);
//        ImageView avatarImg = (RoundedImageView) mCardDig.findViewById(R.id.avatar);
//
//        String avatar = SharedPreferenceUtil.getStringFromSharedPreference(getContext(),"avatar","");
//        Glide.with(getContext()).load("https://img.shaoziketang.com/img_code20180725160823_100.h.png").into(qr);
//        Glide.with(getContext()).load(avatar).centerCrop().into(avatarImg);
//        wx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                content.setDrawingCacheEnabled(true);
//                content.buildDrawingCache(true);
//                Bitmap bitmap=content.getDrawingCache();
//                ShareUtil.wechatShareImg(Constants.wx_api, bitmap, 0, getContext(), false);
//            }
//        });
//
//        Window dialogWindow = mCardDig.getWindow();
//        //获得窗体的属性
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        //lp.y = 20;//设置Dialog距离底部的距离
//        lp.width = DisplayUtil.dip2px(MyApplication.mApplication, 263);
//        lp.height = DisplayUtil.dip2px(MyApplication.mApplication, 521);
////        getWindow().getDecorView().setPadding(0, 0, 0, 0);
//        // 将属性设置给窗体
//        dialogWindow.setAttributes(lp);
//
//        mCardDig.show();
//    }

    public void accessNet() {
        mUserId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
        if (mChildFragmentManager == null) {
            return;
        }
        LoadingUtils.getInstance().showNetLoading(getContext());
        mStudyGroupPresenter.getSGHome2_0(mUserId,this);
//        mStudyGroupPresenter.getSGHome2_0Json(mUserId, new OnResultListener() {
//            @Override
//            public void onSuccess(Object... value) {
//
//            }
//
//            @Override
//            public void onFault(String error) {
//
//            }
//        });
    }

    @Override
    public void onJoinClick(String pid,String vid,String isbao,String type) {
        Intent intent;
        if(StringUtils.isEmpty(mUserId)){
            intent = new Intent(getContext(),LoginNewActivity.class);
        }else{
            intent = new Intent(getContext(),StudyGroupInfo_2_0_Activity.class);
            intent.putExtra("pid",pid);
            intent.putExtra("vid",vid);
            intent.putExtra("is_bao",isbao);
            intent.putExtra("type",type);
        }
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object... value) {
        LoadingUtils.getInstance().hideNetLoading();
        SGHome2_0Entity sgHome2_0Entity = (SGHome2_0Entity) value[0];
        if (sgHome2_0Entity.getInfo()!=null) {
            SGHome2_0Entity.InfoBean info = sgHome2_0Entity.getInfo();
            if (info.getList()!=null) {
                SGHome2_0Entity.InfoBean.ListBean list = info.getList();
                if (StringUtils.isNotEmpty(mUserId)&&list.getStatus()!=0) {
                    mSgRule.setVisibility(View.VISIBLE);
                    FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
                    if (mSGHomeNoDataFragment != null && mSGHomeNoDataFragment.isVisible()) {
                        fragmentTransaction.remove(mSGHomeNoDataFragment);
                    }
                    if (mSGHomeDataFragment == null) {
                        mSGHomeDataFragment = new SGHomeDataFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("punch_card",list.getTotalDay());
                        bundle.putSerializable("my_list", (Serializable) list.getMyJoin());
                        mSGHomeDataFragment.setArguments(bundle);
                    }

                    if(!mSGHomeDataFragment.isAdded()){

                        //设置数据TODO
                        fragmentTransaction.add(R.id.sg_home_info, mSGHomeDataFragment);
                    }else{
                        if(!mSGHomeDataFragment.isVisible()){
                            fragmentTransaction.show(mSGHomeDataFragment);
                        }
                        mSGHomeDataFragment.setData(list.getPunch_card(),list.getMyJoin()); //为了点击回来时数据的刷新
                    }
                    try {
                        fragmentTransaction.commitAllowingStateLoss();
                    }catch (Exception e){
                        Toast.makeText(MyApplication.mApplication, "状态异常", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    mSgRule.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();

                    if (mSGHomeDataFragment != null && mSGHomeDataFragment.isVisible()) {
                        fragmentTransaction.remove(mSGHomeDataFragment);
                    }
                    if (mSGHomeNoDataFragment == null) {
                        mSGHomeNoDataFragment = new SGHomeNoDataFragment();
                    }
                    if(!mSGHomeNoDataFragment.isAdded()){
                        fragmentTransaction.add(R.id.sg_home_info, mSGHomeNoDataFragment);

                    }else{
                        if(!mSGHomeNoDataFragment.isVisible()){
                            fragmentTransaction.show(mSGHomeNoDataFragment);
                        }
                    }
                    try {
                        fragmentTransaction.commitAllowingStateLoss();
                    }catch (Exception e){
                        Toast.makeText(MyApplication.mApplication, "状态异常", Toast.LENGTH_SHORT).show();
                    }
                }
                mSGHomeCourseAdapter = new SGHomeCourse_2_0_Adapter(getContext(),list.getHot());
                mEmptyRecycleAdapter = new EmptyRecycleAdapter(mSGHomeCourseAdapter, R.layout.layout_empty_mysg);
                mSgHotList.setAdapter(mEmptyRecycleAdapter);
                mSGHomeCourseAdapter.setOnJoinItemClickListener(this);
            }

        }


    }

    @Override
    public void onFault(String error) {
        LoadingUtils.getInstance().hideNetLoading();
        Log.e("DH_ERROR_SG_2_0",error);
    }



    /**
     *  针对fragment在back后保存完状态调用commit 跑出异常Can not perform this action after onSaveInstanceState解决办法
     *  通过反射手段在onSaveInstanceState 方法里调用 FragmentManagerImpl noteStateNotSaved方法将 mStateSaved 变量置为false
     */
    private Method noteStateNotSavedMethod;
    private Object fragmentMgr;
    private String[] activityClassName = {"Activity", "FragmentActivity"};

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ex) {
        }
    }

    private Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }


}
