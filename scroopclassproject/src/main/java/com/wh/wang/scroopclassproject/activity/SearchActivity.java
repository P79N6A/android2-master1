package com.wh.wang.scroopclassproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.adapter.SearchReadAdapter;
import com.wh.wang.scroopclassproject.adapter.SearchVideoAdapter;
import com.wh.wang.scroopclassproject.bean.SearchHistoryInfo;
import com.wh.wang.scroopclassproject.bean.SearchInfo;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.Md5Util;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;
import com.wh.wang.scroopclassproject.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    private ImageView search_back;
    private EditText search_et_input;
    private TextView search_tv;
    private LinearLayout search_end_no_result;
    private RelativeLayout search_rlayout_flag_one;
    private RelativeLayout search_rlayout_flag_two;
    private RelativeLayout search_rlayout_flag_three;
    private RelativeLayout search_rlayout_flag_four;
    private RelativeLayout search_rlayout_flag_five;
    private ListView search_listview;
    private RelativeLayout search_result_tilte_one;
    private TextView search_course;
    private RelativeLayout search_result_tilte_two;
    private TextView search_read;
    private List<SearchInfo.VideoBean> videoLists;
    private List<SearchInfo.NewBean> newsLists;
    private List<SearchHistoryInfo.HistoryBean> historyLists;
    private RelativeLayout search_start;
    private Button history_tag_one;
    private Button history_tag_two;
    private Button history_tag_three;
    private Button history_tag_four;
    private Button history_tag_five;
    private Button search_btn_hot_tag_one;
    private Button search_btn_hot_tag_two;
    private Button search_btn_hot_tag_three;
    private Button search_btn_hot_tag_four;
    private Button search_btn_hot_tag_five;
    private RelativeLayout search_end_have_result;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        InitView();
        InitData();
        InitListener();
        Log.e("DH_ID",SharedPreferenceUtil.getStringFromSharedPreference(SearchActivity.this, "user_id", ""));
    }

    /**
     * 初始化控件
     */
    private void InitView() {
        search_back = (ImageView) findViewById(R.id.search_back);//后退
        search_et_input = (EditText) findViewById(R.id.search_et_input);
        search_tv = (TextView) findViewById(R.id.search_tv);

        //开始页面
        search_start = (RelativeLayout) findViewById(R.id.search_start); //开始父类
        search_start.setVisibility(View.VISIBLE);

        //历史标签
        history_tag_one = (Button) findViewById(R.id.history_tag_one);
        history_tag_one.setVisibility(View.INVISIBLE);
        history_tag_two = (Button) findViewById(R.id.history_tag_two);
        history_tag_two.setVisibility(View.INVISIBLE);
        history_tag_three = (Button) findViewById(R.id.history_tag_three);
        history_tag_three.setVisibility(View.INVISIBLE);
        history_tag_four = (Button) findViewById(R.id.history_tag_four);
        history_tag_four.setVisibility(View.INVISIBLE);
        history_tag_five = (Button) findViewById(R.id.history_tag_five);
        history_tag_five.setVisibility(View.INVISIBLE);

        //热点标签
        search_btn_hot_tag_one = (Button) findViewById(R.id.search_btn_hot_tag_one);
        search_btn_hot_tag_two = (Button) findViewById(R.id.search_btn_hot_tag_two);
        search_btn_hot_tag_three = (Button) findViewById(R.id.search_btn_hot_tag_three);
        search_btn_hot_tag_four = (Button) findViewById(R.id.search_btn_hot_tag_four);
        search_btn_hot_tag_five = (Button) findViewById(R.id.search_btn_hot_tag_five);


        //点击搜索 有结果时
        search_end_have_result = (RelativeLayout) findViewById(R.id.search_end_have_result);
        search_end_have_result.setVisibility(View.GONE);

        search_result_tilte_one = (RelativeLayout) findViewById(R.id.search_result_tilte_one); //课程
        search_course = (TextView) findViewById(R.id.search_course);
        search_result_tilte_two = (RelativeLayout) findViewById(R.id.search_result_tilte_two); //阅读
        search_read = (TextView) findViewById(R.id.search_read);
        search_listview = (ListView) findViewById(R.id.search_listview);


        //点击搜索 没有结果时
        search_end_no_result = (LinearLayout) findViewById(R.id.search_end_no_result);
        search_end_no_result.setVisibility(View.GONE);

        search_rlayout_flag_one = (RelativeLayout) findViewById(R.id.search_rlayout_flag_one);
        search_rlayout_flag_two = (RelativeLayout) findViewById(R.id.search_rlayout_flag_two);
        search_rlayout_flag_three = (RelativeLayout) findViewById(R.id.search_rlayout_flag_three);
        search_rlayout_flag_four = (RelativeLayout) findViewById(R.id.search_rlayout_flag_four);
        search_rlayout_flag_five = (RelativeLayout) findViewById(R.id.search_rlayout_flag_five);

    }

    /**
     * 初始化数据
     */
    private void InitData() {
        user_id = SharedPreferenceUtil.getStringFromSharedPreference(SearchActivity.this, "user_id", "");
        HistoryDataFromNet();
    }

    private void HistoryDataFromNet() {
        RequestParams params = new RequestParams(Constants.searchHistotyUrl);
        params.addBodyParameter("id", user_id);
        String timeStr = StringUtils.getTimestamp();
        params.addBodyParameter("shijian", timeStr);
        params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processHistoryData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                // progressBar.setVisibility(View.GONE);
            }
        });

    }

    //解析数据
    //TODO WHWH
    private void processHistoryData(String result) {
        historyLists = parsedJsonHistory(result);
        Log.e("whwh", "historyLists==" + historyLists.size());
        showHistoryData();
    }

    //显示数据
    private void showHistoryData() {
        if (historyLists != null && historyLists.size() > 0) {
            history_tag_one.setVisibility(View.VISIBLE);
            String contentStr = historyLists.get(0).getContent();
            if (contentStr.length() > 3) {
                contentStr = contentStr.substring(0, 3);
                history_tag_one.setText(contentStr + "...");
            } else {
                history_tag_one.setText(contentStr);
            }
            history_tag_two.setVisibility(View.INVISIBLE);
            history_tag_three.setVisibility(View.INVISIBLE);
            history_tag_four.setVisibility(View.INVISIBLE);
            history_tag_five.setVisibility(View.INVISIBLE);
            history_tag_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchHistory(historyLists.get(0).getContent());
                }
            });


        }

        if (historyLists != null && historyLists.size() > 1) {

            history_tag_two.setVisibility(View.VISIBLE);
            String contentStr = historyLists.get(1).getContent();
            if (contentStr.length() > 3) {
                contentStr = contentStr.substring(0, 3);
                history_tag_two.setText(contentStr + "...");
            } else {
                history_tag_two.setText(contentStr);
            }
            history_tag_one.setVisibility(View.VISIBLE);
            history_tag_three.setVisibility(View.INVISIBLE);
            history_tag_four.setVisibility(View.INVISIBLE);
            history_tag_five.setVisibility(View.INVISIBLE);
            history_tag_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchHistory(historyLists.get(1).getContent());
                }
            });

        }


        if (historyLists != null && historyLists.size() > 2) {

            history_tag_three.setVisibility(View.VISIBLE);
            String contentStr = historyLists.get(2).getContent();
            if (contentStr.length() > 3) {
                contentStr = contentStr.substring(0, 3);
                history_tag_three.setText(contentStr + "...");
            } else {
                history_tag_three.setText(contentStr);
            }
            history_tag_one.setVisibility(View.VISIBLE);
            history_tag_two.setVisibility(View.VISIBLE);
            history_tag_four.setVisibility(View.INVISIBLE);
            history_tag_five.setVisibility(View.INVISIBLE);
            history_tag_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchHistory(historyLists.get(2).getContent());
                }
            });

        }
        if (historyLists != null && historyLists.size() > 3) {
            history_tag_four.setVisibility(View.VISIBLE);
            String contentStr = historyLists.get(3).getContent();
            if (contentStr.length() > 3) {
                contentStr = contentStr.substring(0, 3);
                history_tag_four.setText(contentStr + "...");
            } else {
                history_tag_four.setText(contentStr);
            }
            history_tag_one.setVisibility(View.VISIBLE);
            history_tag_two.setVisibility(View.VISIBLE);
            history_tag_three.setVisibility(View.VISIBLE);
            history_tag_five.setVisibility(View.INVISIBLE);
            history_tag_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchHistory(historyLists.get(3).getContent());
                }
            });

        }
        if (historyLists != null && historyLists.size() > 4) {
            history_tag_five.setVisibility(View.VISIBLE);
            history_tag_one.setVisibility(View.VISIBLE);
            history_tag_two.setVisibility(View.VISIBLE);
            history_tag_three.setVisibility(View.VISIBLE);
            history_tag_four.setVisibility(View.VISIBLE);
            String contentStr = historyLists.get(4).getContent();
            if (contentStr.length() > 3) {
                contentStr = contentStr.substring(0, 3);
                history_tag_five.setText(contentStr + "...");
            } else {
                history_tag_five.setText(contentStr);
            }

            history_tag_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchHistory(historyLists.get(4).getContent());
                }
            });
        }
    }


    /**
     * 初始化监听
     */
    private void InitListener() {
        //返回
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search_btn_hot_tag_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 1);
                finish();
            }
        });

        search_btn_hot_tag_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 7);
                finish();
            }
        });
        search_btn_hot_tag_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 13);
                finish();
            }
        });

        search_btn_hot_tag_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 19);
                finish();
            }
        });

        search_btn_hot_tag_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 26);
                finish();
            }
        });


        search_rlayout_flag_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 1);
                finish();
            }
        });

        search_rlayout_flag_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 26);
                finish();
            }
        });


        search_rlayout_flag_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 13);
                finish();
            }
        });


        search_rlayout_flag_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 19);
                finish();
            }
        });

        search_rlayout_flag_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.isFromSearch = true;
                SharedPreferenceUtil.putIntValueByKey(SearchActivity.this, "courseid", 7);
                finish();
            }
        });

        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(SearchActivity.this, "搜索点击...!");
                searchText();
            }
        });
    }

    private void searchHistory(String content) {
        //课程集合
        if (videoLists != null && videoLists.size() > 0) {
            videoLists.clear();
        }

        //阅读集合
        if (newsLists != null && newsLists.size() > 0) {
            newsLists.clear();
        }
        getDataFromNet(content);
    }

    /**
     * 搜索监听事件
     */
    private void searchText() {
        String search_input = search_et_input.getText().toString().trim();
        if (!TextUtils.isEmpty(search_input)) {

            //课程集合
            if (videoLists != null && videoLists.size() > 0) {
                videoLists.clear();
            }

            //阅读集合
            if (newsLists != null && newsLists.size() > 0) {
                newsLists.clear();
            }

            //search_input = URLEncoder.encode(search_input, "UTF-8");
            // url = Constants.searchUrl + search_input;
            getDataFromNet(search_input);
        }
    }

    //网络请求
    //TODO WHWH
    private void getDataFromNet(String search_input) {
        RequestParams params = new RequestParams(Constants.searchUrl);
        params.addBodyParameter("id", user_id);
        params.addBodyParameter("keyword", search_input);
        String timeStr = StringUtils.getTimestamp();
        params.addBodyParameter("shijian", timeStr);
        params.addBodyParameter("sign", Md5Util.md5("www.szkt.com" + timeStr));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                // progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 搜索历史
     */
    private void processData(String result) {


        search_start.setVisibility(View.GONE);
        search_end_have_result.setVisibility(View.VISIBLE);
        search_end_no_result.setVisibility(View.GONE);
        search_listview.setVisibility(View.VISIBLE);
        videoLists = parsedJsonVideo(result);
        newsLists = parsedJsonRead(result);
        Log.e("whwh", "videoLists==" + videoLists.size());
        Log.e("whwh", "newsLists==" + newsLists.size());

        if ((videoLists != null && videoLists.size() > 0) && (newsLists != null && newsLists.size() > 0)) {
            Log.e("whwh", "走了11111");
            search_end_have_result.setVisibility(View.VISIBLE);
            search_result_tilte_one.setVisibility(View.VISIBLE);
            search_result_tilte_two.setVisibility(View.VISIBLE);
            search_end_no_result.setVisibility(View.GONE);
            showVideoData();
        } else if (videoLists.size() > 0 && newsLists.size() == 0) {
            Log.e("whwh", "走了22222");
            search_end_have_result.setVisibility(View.VISIBLE);
            search_result_tilte_one.setVisibility(View.VISIBLE);
            search_result_tilte_two.setVisibility(View.GONE);
            search_end_no_result.setVisibility(View.GONE);
            showVideoData();
        } else if (videoLists.size() == 0 && newsLists.size() > 0) {
            Log.e("whwh", "走了333333");
            search_end_have_result.setVisibility(View.VISIBLE);
            search_result_tilte_one.setVisibility(View.GONE);
            search_result_tilte_two.setVisibility(View.VISIBLE);
            search_end_no_result.setVisibility(View.GONE);
            showReadData();
        } else if (videoLists.size() == 0 && newsLists.size() == 0) {
            Log.e("whwh", "走了4444");
            setNullData();
        }

        setListener();
    }

    private void setListener() {
        search_result_tilte_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideoData();
            }
        });

        search_result_tilte_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReadData();
            }
        });
    }


    //加载课程数据
    private void showVideoData() {
        SearchVideoAdapter mSearchVideoAdapter = new SearchVideoAdapter(this, videoLists);
        search_listview.setAdapter(mSearchVideoAdapter);
        mSearchVideoAdapter.notifyDataSetChanged();

    }


    //加载阅读数据
    private void showReadData() {
        SearchReadAdapter mSearchReadAdapter = new SearchReadAdapter(this, newsLists);
        search_listview.setAdapter(mSearchReadAdapter);
        mSearchReadAdapter.notifyDataSetChanged();
    }


    /**
     * 空集合
     */
    private void setNullData() {
        search_start.setVisibility(View.GONE);
        search_end_have_result.setVisibility(View.GONE);
        search_end_no_result.setVisibility(View.VISIBLE);
    }


    private List<SearchInfo.VideoBean> parsedJsonVideo(String result) {
        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray videoArray = jsonObject.optJSONArray("video");
            Log.e("whwh", "videoArray==" + videoArray.toString());
            videoLists = new ArrayList<>();
            if (videoArray != null && videoArray.length() > 0) {

                for (int i = 0; i < videoArray.length(); i++) {

                    JSONObject videoObj = videoArray.optJSONObject(i);

                    if (videoObj != null) {
                        String video_id = videoObj.optString("id");
                        String video_img = videoObj.optString("img");
                        String video_new_price = videoObj.optString("new_price");
                        String video_title = videoObj.optString("title");

                        SearchInfo.VideoBean videobean = new SearchInfo.VideoBean();
                        videobean.setId(video_id);
                        videobean.setImg(video_img);
                        videobean.setNew_price(video_new_price);
                        videobean.setTitle(video_title);
                        videoLists.add(videobean);
                    }
                }
            } else {
                setNullData();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videoLists;
    }

    private List<SearchInfo.NewBean> parsedJsonRead(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray newArray = jsonObject.optJSONArray("new");
            Log.e("whwh", "newArray==" + newArray.toString());
            newsLists = new ArrayList<>();
            if (newArray != null && newArray.length() > 0) {

                for (int i = 0; i < newArray.length(); i++) {

                    JSONObject newObj = newArray.optJSONObject(i);

                    if (newObj != null) {
                        String video_id = newObj.optString("id");
                        String video_img = newObj.optString("img");
                        String video_title = newObj.optString("title");

                        SearchInfo.NewBean newbean = new SearchInfo.NewBean();
                        newbean.setId(video_id);
                        newbean.setImg(video_img);
                        newbean.setTitle(video_title);
                        newsLists.add(newbean);
                    }
                }
            } else {
                //setNullData();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsLists;
    }

    private List<SearchHistoryInfo.HistoryBean> parsedJsonHistory(String result) {

        try {

            JSONObject jsonObject = new JSONObject(result);
            JSONArray historyArray = jsonObject.optJSONArray("history");
            Log.e("whwh", "historyArray==" + historyArray.toString());
            historyLists = new ArrayList<>();
            if (historyArray != null && historyArray.length() > 0) {

                for (int i = 0; i < historyArray.length(); i++) {

                    JSONObject hisObj = historyArray.optJSONObject(i);

                    if (hisObj != null) {
                        String history_content = hisObj.optString("content");
                        SearchHistoryInfo.HistoryBean historybean = new SearchHistoryInfo.HistoryBean();
                        historybean.setContent(history_content);
                        historyLists.add(historybean);
                    }
                }
            } else {
                //setNullData();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return historyLists;
    }
}
