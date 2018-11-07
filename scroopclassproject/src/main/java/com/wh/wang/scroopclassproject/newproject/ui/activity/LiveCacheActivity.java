package com.wh.wang.scroopclassproject.newproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.wh.wang.scroopclassproject.R;

public class LiveCacheActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_cache);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("servername", "");
//                map.put("userrole", 2);
//                map.put("host", RoomClient.webServer);
//                map.put("port", 80);  //端口
//                map.put("serial", "1301588545"); //课堂号
//                map.put("password", "123452"); //课堂号
//                map.put("nickname", "丁豪"); // 昵称
//                RoomClient.getInstance().joinRoom(LiveCacheActivity.this, map);
//            }
//        },1000);
    }
}
