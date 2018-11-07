package com.wh.wang.scroopclassproject.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;


/**
 * Created by wang on 2017/12/20.
 */

public class GotoNextActUtils {

    private static GotoNextActUtils mGotoNextActUtils = new GotoNextActUtils();

    private GotoNextActUtils() {
    }
    public static GotoNextActUtils getInstance(){
        if(mGotoNextActUtils==null){
            mGotoNextActUtils = new GotoNextActUtils();
        }
        return mGotoNextActUtils;
    }

    /**
     * @param context 当前页面
     * @param id      课程ID
     * @param type    课程类型 4是章节课程详情页  其他是
     */
    public void nextActivity(Context context, String id, String type,String... valses) {

//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("servername", "");
//        map.put("userrole", 2);
//        map.put("host", RoomClient.webServer);
//        map.put("port", 80);  //端口
//        map.put("serial", "226355672"); //课堂号
//        map.put("password", "123452"); //课堂号
//        map.put("nickname", "丁豪"); // 昵称
//        RoomClient.getInstance().joinRoom(context, map);
        Log.e("DH_COURSE","id:"+id);
        Intent intent;
        if ("4".equals(type)) {
            intent = new Intent(context, SumUpActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("type", type);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
//        else if("5".equals(type)){
//            intent = new Intent(context, OpenClassActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("id", id);
//            bundle.putString("type", type);
//            intent.putExtras(bundle);
//            context.startActivity(intent);
//        }
        else{//LookHomeWorkActivity
            intent = new Intent(context, NewVideoInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("type", type);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
