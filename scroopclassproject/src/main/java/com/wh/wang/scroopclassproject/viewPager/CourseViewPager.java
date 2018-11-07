package com.wh.wang.scroopclassproject.viewPager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.CourseLableAdapter;
import com.wh.wang.scroopclassproject.adapter.CourseListAdapter;
import com.wh.wang.scroopclassproject.base.BasePager;
import com.wh.wang.scroopclassproject.bean.CourseBean;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewSearchActivity;
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
 * Created by wang on 2017/8/14.
 * 课程页面
 */

public class CourseViewPager extends BasePager {

    private PullToRefreshListView course_listview;
    private ListView lable_listview;
    private List<CourseBean.InfoBean.AllTypeBean> typeList;  //父类标签集合
    private List<CourseBean.InfoBean.CourseListBean> courseList;  //课程集合
    //全部的课程集合
    private List<CourseBean.InfoBean.CourseListBean> allList;
    private List<CourseBean.InfoBean.ScrollBean> scrollList;  //图片集合
    private CourseLableAdapter courseLableAdapter;
    private int firstHot;
    private CourseListAdapter courseListAdapter;
    private RelativeLayout titlebar_new;
    private ListView listview;
    int pageIndex = 0;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    public CourseViewPager(Context context) {
        super(context);
    }

    @Override
    public View baseView() {
        View view = View.inflate(context, R.layout.course_viewpager, null);
        titlebar_new = (RelativeLayout) view.findViewById(R.id.titlebar_new_rlayout);
        titlebar_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewSearchActivity.class);
                context.startActivity(intent);
            }
        });
        lable_listview = (ListView) view.findViewById(R.id.lable_listview);
        lable_listview.setSelection(0);

        course_listview = (PullToRefreshListView) view.findViewById(R.id.course_listview);
        listview = course_listview.getRefreshableView();
        allList = new ArrayList<CourseBean.InfoBean.CourseListBean>();
        return view;
    }

    @Override
    public void baseData() {
        super.baseData();
        getCourseLableFromNet();
    }

    /**
     * 获取课程标签
     */
    private void getCourseLableFromNet() {
        RequestParams params = new RequestParams(Constants.courseUrl2 + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getCourseLableFromNet---联网成功---result===" + result);
                //主线程
                lableProcessData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getCourseLableFromNet---联网失败---" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getCourseLableFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getCourseLableFromNet---onFinished---");
            }
        });
    }

    private void lableProcessData(String result) {
        firstHot = parseJsonHot(result);
        typeList = parseJsonType(result);
        courseList = parseJsonCourseList(result);
        scrollList = parseJsonscroll(result);
        setLableAdapter(firstHot);
        //TODO WHWH Changer
        //setCourseAdapter(typeList.get(0).getName(), 0, courseList, scrollList);
        getCourseFromNet(0, typeList.get(0).getId(), 0, typeList.get(0).getName());
        initListener(0, typeList.get(0).getId(), 0, typeList.get(0).getName());
    }

    private void setLableAdapter(int firstHot) {
        courseLableAdapter = new CourseLableAdapter(context, typeList, firstHot);
        lable_listview.setAdapter(courseLableAdapter);
        //courseLableAdapter.notifyDataSetChanged();

        lable_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                courseLableAdapter.changeSelected(position);//刷新
                String typeId = typeList.get(position).getId();
                String typeName = typeList.get(position).getName();
                //TODO WHWH
                courseList.clear();
                allList.clear();
                courseListAdapter = null;
                getCourseFromNet(position, typeId, 0, typeName);
                initListener(position, typeId, 0, typeName);
            }
        });

        lable_listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseLableAdapter.changeSelected(position);//刷新
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initListener(final int position, final String typeId, final int pageIndex, final String typeName) {
        course_listview.setMode(PullToRefreshBase.Mode.BOTH);
        course_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            private int page;

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = true;
                page = 0;
                getCourseFromNet(position, typeId, page, typeName);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isLoadMore = true;
                page++;
                getCourseFromNet(position, typeId, page, typeName);
            }
        });
    }

    private void getCourseFromNet(final int position, String typeId, int pageIndex, final String typeName) {
        RequestParams params = new RequestParams(Constants.courseUrl2 + typeId + "&page=" + pageIndex);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("whwh", "getCourseFromNet---联网成功---result===" + result);
                //主线程
                courseProcessData(position, typeName, result);
                course_listview.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("whwh", "getCourseFromNet---联网失败---" + ex.getMessage());
                course_listview.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("whwh", "getCourseFromNet---onCancelled---" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("whwh", "getCourseFromNet---onFinished---");
            }
        });
    }

    private void courseProcessData(int position, String typeName, String result) {
        courseList = parseJsonCourseList(result);
        scrollList = parseJsonscroll(result);
        //setJXKAdapter();
        if (isRefresh && courseList.size() > 0) {
            isRefresh = false;
            setCourseAdapter(typeName, position, allList, scrollList);
        } else if (isLoadMore && courseList.size() > 0) {
            isLoadMore = false;
            allList.addAll(courseList);
            setCourseAdapter(typeName, position, allList, scrollList);
        } else if (courseList.size() > 0) {
            allList.clear();
            allList.addAll(courseList);
            //superDetailAdapter.replaceAll(allCourseList);
            setCourseAdapter(typeName, position, allList, scrollList);
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.has_no_more), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 第一个视频详情布局和适配器
     */
    private void setCourseAdapter(String name, int flagg, List<CourseBean.InfoBean.CourseListBean> courseList,
                                  List<CourseBean.InfoBean.ScrollBean> scrollList) {
        if (flagg == 0) {
            //course_listview.setDividerHeight(0);
        } else {
            //course_listview.setDividerHeight(1);
        }

        //courseListAdapter = new CourseListAdapter(context, courseList, scrollList, name, flagg);
        // course_listview.setAdapter(courseListAdapter);
        // courseListAdapter.notifyDataSetChanged();

        if (courseListAdapter != null) {
            courseListAdapter.notifyDataSetChanged();
        } else {
            courseListAdapter = new CourseListAdapter(context, allList, scrollList, name, flagg);
            course_listview.setAdapter(courseListAdapter);
        }

    }


    /**
     * 获取是否有火图标
     */
    private int parseJsonHot(String result) {
        try {

            JSONObject infoObj = new JSONObject(result).getJSONObject("info");
            int status = new JSONObject(result).optInt("status");
            if (status == 1) {
                if (infoObj != null) {
                    firstHot = infoObj.optInt("firstHot");
                }

            } else {
                Log.e("whwh", "服务器接口数据有毛病!");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return firstHot;
    }


    /**
     * 标签数据解析
     */
    private List<CourseBean.InfoBean.AllTypeBean> parseJsonType(String result) {
        try {

            JSONObject infoObj = new JSONObject(result).getJSONObject("info");
            int status = new JSONObject(result).optInt("status");
            if (status == 1) {
                if (infoObj != null) {
                    JSONArray typeArray = infoObj.optJSONArray("allType");
                    typeList = new ArrayList<>();
                    if (typeArray != null && typeArray.length() > 0) {
                        for (int i = 0; i < typeArray.length(); i++) {
                            JSONObject typeObj = typeArray.optJSONObject(i);
                            if (typeObj != null) {

                                String type_id = typeObj.optString("id");
                                String type_name = typeObj.optString("name");
                                String type_logo = typeObj.optString("logo");
                                CourseBean.InfoBean.AllTypeBean typeBean = new CourseBean.InfoBean.AllTypeBean();
                                typeBean.setId(type_id);
                                typeBean.setName(type_name);
                                typeBean.setLogo(type_logo);
                                typeList.add(typeBean);
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

        return typeList;
    }

    /**
     * 课程数据解析
     */
    private List<CourseBean.InfoBean.CourseListBean> parseJsonCourseList(String result) {
        try {

            JSONObject infoObj = new JSONObject(result).getJSONObject("info");
            int status = new JSONObject(result).optInt("status");
            if (status == 1) {
                if (infoObj != null) {
                    JSONArray courseArray = infoObj.optJSONArray("courseList");
                    courseList = new ArrayList<>();
                    if (courseArray != null && courseArray.length() > 0) {
                        for (int i = 0; i < courseArray.length(); i++) {
                            JSONObject courseObj = courseArray.optJSONObject(i);
                            if (courseObj != null) {

                                String course_id = courseObj.optString("id");
                                String course_img = courseObj.optString("img");
                                Log.e("whwh", "course_img==" + course_img);
                                String course_new_price = courseObj.optString("new_price");
                                String course_teacher_id = courseObj.optString("teacher_id");
                                String course_teacher_name = courseObj.optString("teacher_name");
                                String course_title = courseObj.optString("title");
                                String type = courseObj.optString("type");

                                CourseBean.InfoBean.CourseListBean courseBean = new CourseBean.InfoBean.CourseListBean();
                                courseBean.setId(course_id);
                                courseBean.setImg(course_img);
                                courseBean.setNew_price(course_new_price);
                                courseBean.setTeacher_id(course_teacher_id);
                                courseBean.setTeacher_name(course_teacher_name);
                                courseBean.setTitle(course_title);
                                courseBean.setType(type);
                                courseList.add(courseBean);
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

        return courseList;
    }

    /**
     * 广告数据解析
     */
    private List<CourseBean.InfoBean.ScrollBean> parseJsonscroll(String result) {
        try {

            JSONObject infoObj = new JSONObject(result).getJSONObject("info");
            int status = new JSONObject(result).optInt("status");
            if (status == 1) {
                Log.e("whwh", "服务器接口数据没毛病!");
                if (infoObj != null) {
                    JSONArray scrollArray = infoObj.optJSONArray("scroll");
                    scrollList = new ArrayList<>();
                    if (scrollArray != null && scrollArray.length() > 0) {
                        for (int i = 0; i < scrollArray.length(); i++) {
                            JSONObject scrollObj = scrollArray.optJSONObject(i);
                            if (scrollObj != null) {

                                String scroll_courseId = scrollObj.optString("courseId");
                                Log.e("whwh", "scroll_courseId==" + scroll_courseId);
                                String scroll_fenlei_id = scrollObj.optString("fenlei_id");
                                String scroll_img = scrollObj.optString("img");
                                String scroll_title = scrollObj.optString("title");
                                String type = scrollObj.optString("type");


                                CourseBean.InfoBean.ScrollBean scrollBean = new CourseBean.InfoBean.ScrollBean();
                                scrollBean.setCourseId(scroll_courseId);
                                scrollBean.setFenlei_id(scroll_fenlei_id);
                                scrollBean.setImg(scroll_img);
                                scrollBean.setTitle(scroll_title);
                                scrollBean.setType(type);
                                scrollList.add(scrollBean);
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

        return scrollList;
    }

    @Override
    public void resumeData() {
        super.resumeData();
        if (Constants.couseLable > 0) {

        }
    }

}
