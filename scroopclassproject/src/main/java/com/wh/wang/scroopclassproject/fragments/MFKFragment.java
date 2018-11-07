package com.wh.wang.scroopclassproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.MFKAdapter;
import com.wh.wang.scroopclassproject.base.BaseFragment;
import com.wh.wang.scroopclassproject.bean.MFKBean;
import com.wh.wang.scroopclassproject.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wang on 2017/11/21.
 */

public class MFKFragment extends BaseFragment {

    private PullToRefreshListView mfk_listview;
    private List<MFKBean.FreeCourseBean> mfkcList;  //课程父类
    int pageIndex = 0;
    //全部的课程集合
    private List<MFKBean.FreeCourseBean> allList;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private MFKAdapter mfkAdapter;
    private ListView listview;

    @Override
    public View initView(LayoutInflater inflater) {
        //添加页面布局
        View view = inflater.inflate(R.layout.fragment_mfk, null);
        mfk_listview = (PullToRefreshListView) view.findViewById(R.id.mfk_listview);
        listview = mfk_listview.getRefreshableView();
        allList = new ArrayList<MFKBean.FreeCourseBean>();
        initListener();
        return view;
    }

    private void initListener() {
        mfk_listview.setMode(PullToRefreshBase.Mode.BOTH);
        mfk_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = true;
                pageIndex = 0;
                initDatas();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isLoadMore = true;
                pageIndex++;
                initDatas();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initDatas();
    }

    private void initDatas() {
        getMFKDataFromNet();
    }


    private void getMFKDataFromNet() {
        RequestParams params = new RequestParams(Constants.supmfUrl + "&page=" + pageIndex);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getMFKDataFromNet---联网成功---result===" + result);
                //主线程
                processData(result);
                mfk_listview.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getMFKDataFromNet---联网失败---" + ex.getMessage());
                mfk_listview.onRefreshComplete();//
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getMFKDataFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getMFKDataFromNet---onFinished---");
            }
        });
    }

    private void processData(String result) {
        mfkcList = parseJsonMFKC(result);
        //setJXKAdapter();
        if (isRefresh && mfkcList.size() > 0) {
            isRefresh = false;
            //allList.clear();
            //allList.addAll(mfkcList);
            //superDetailAdapter.replaceAll(allCourseList);
            setJXKAdapter();
        } else if (isLoadMore && mfkcList.size() > 0) {
            isLoadMore = false;
            allList.addAll(mfkcList);
            //superDetailAdapter.replaceAll(allCourseList);
            setJXKAdapter();
        } else if (mfkcList.size() > 0) {
            allList.clear();
            allList.addAll(mfkcList);
            //superDetailAdapter.replaceAll(allCourseList);
            setJXKAdapter();
        }

    }

    private void setJXKAdapter() {
        if(mfkAdapter!=null){
            mfkAdapter.notifyDataSetChanged();
        }else{
            mfkAdapter = new MFKAdapter(mContext, allList);
            mfk_listview.setAdapter(mfkAdapter);
        }
    }


    private List<MFKBean.FreeCourseBean> parseJsonMFKC(String result) {
        try {

            JSONObject infoObj = new JSONObject(result).getJSONObject("info");
            int status = new JSONObject(result).optInt("status");
            if (status == 1) {
                if (infoObj != null) {
                    JSONArray mfArray = infoObj.optJSONArray("courseList");
                    mfkcList = new ArrayList<>();
                    if (mfArray != null && mfArray.length() > 0) {
                        for (int i = 0; i < mfArray.length(); i++) {
                            JSONObject mfObj = mfArray.optJSONObject(i);
                            if (mfObj != null) {
                                String mf_duan = mfObj.optString("duan");
                                String mf_id = mfObj.optString("id");
                                String mf_img = mfObj.optString("img");
                                String mf_learn = mfObj.optString("learn");
                                String mf_name = mfObj.optString("name");
                                String mf_new_price = mfObj.optString("new_price");
                                String mf_title = mfObj.optString("title");

                                MFKBean.FreeCourseBean freeCourseBean = new MFKBean.FreeCourseBean();
                                freeCourseBean.setDuan(mf_duan);
                                freeCourseBean.setId(mf_id);
                                freeCourseBean.setImg(mf_img);
                                freeCourseBean.setLearn(mf_learn);
                                freeCourseBean.setName(mf_name);
                                freeCourseBean.setNew_price(mf_new_price);
                                freeCourseBean.setTitle(mf_title);
                                mfkcList.add(freeCourseBean);
                            }
                        }
                    }
                }
            } else {
                Log.e("whwh", "服务器接口数据有毛病!");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mfkcList;
    }
}
