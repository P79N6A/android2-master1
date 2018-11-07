package com.wh.wang.scroopclassproject.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.activity.activity2_0.StudyGroupInfo_2_0_Activity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.AnswerActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CasebookInviteActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyDataActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyMemberActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEssayDetailActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.OpenClassActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "DH_REC";
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.e("DH_REC", "[MyReceiver] 用户点击打开了通知");
                Map<String,String> map = new HashMap<>();
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Log.e("DH_REC","json:"+json);
                    Iterator<String> it = json.keys();
                    while (it.hasNext()){
                        String key = it.next();
                        Log.e("DH_REC","key:"+key+"  value:"+json.optString(key));
                        map.put(key,json.optString(key));
                    }
                    if(map.size()>0){
                        Set<String> keySet = map.keySet();
                        if(keySet.contains("courseDetailId")){
                            String id = map.get("courseDetailId");
                            String type = "1";
                            if(keySet.contains("type")){
                                type = map.get("type");
                            }
                            if("4".equals(type)){
                                Intent i = new Intent(context, SumUpActivity.class);
                                bundle.putString("id", id);
                                bundle.putString("type", type);
                                i.putExtras(bundle);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }else{
                                Intent i = new Intent(context, NewVideoInfoActivity.class);
                                bundle.putString("id", id);
                                bundle.putString("type",type);
                                i.putExtras(bundle);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }
                        }else if(keySet.contains("signUpDetailId")){
                            String id = map.get("signUpDetailId");
                            String type = "1";
                            if(keySet.contains("type")){
                                type = map.get("type");
                            }
                            if("5".equals(type)){
                                Intent i = new Intent(context, OpenClassActivity.class);
                                bundle.putString("id", id);
                                bundle.putString("type",type);
                                i.putExtras(bundle);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }else{
                                Intent i = new Intent(context, NewEventDetailsActivity.class);
                                bundle.putString("event_id",id);
                                bundle.putString("type",type);
                                i.putExtras(bundle);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }
                        }else if(keySet.contains("staffList")){
                            String id = map.get("staffList");
                            int status = -1;
                            String name = "";
                            Intent i = new Intent(context, CompanyMemberActivity.class);
                            i.putExtra("parent_id",id);
                            if("0".equals(id)){
                                status = 0;
                            }else{
                                status = 1;
                            }
                            i.putExtra("status",status);
                            if(keySet.contains("boss_name")){
                                name = map.get("boss_name");
                            }
                            i.putExtra("parent_name",name);
                            context.startActivity(i);
                        }else if(keySet.contains("companyInfo")){
                            Intent i = new Intent(context, CompanyInfoActivity.class);
                            context.startActivity(i);
                        }else if(keySet.contains("weekData")){
                            if(keySet.contains("isBoss")){
                                Intent i = new Intent(context, CompanyDataActivity.class);
                                String id = map.get("weekData");
                                String is_boss = map.get("isBoss");
                                if("1".equals(is_boss)){
                                    i.putExtra("url_type","3");
                                    i.putExtra("parent_id",id);
                                }else{
                                    i.putExtra("url_type","2");
                                }
                                context.startActivity(i);
                            }
                        }else if(keySet.contains("url")){
                            Intent i = new Intent(context, AnswerActivity.class);
                            String url = map.get("url");
                            i.putExtra("exam_url",url);
                            context.startActivity(i);
                        }else if(keySet.contains("caseSetEventId")){
                            Intent i = new Intent(context, CasebookInviteActivity.class);
                            String event_id = map.get("caseSetEventId");
                            i.putExtra("event_id",event_id);
                            context.startActivity(i);
                        }else if(keySet.contains("studyDetail")){
                            String userId = SharedPreferenceUtil.getStringFromSharedPreference(MyApplication.mApplication, "user_id", "");
                            if(StringUtils.isNotEmpty(userId)){
                                Intent i = new Intent(context, StudyGroupInfo_2_0_Activity.class);
                                i.putExtra("pid",map.get("studyDetail"));
                                i.putExtra("vid",map.get("vid"));
                                i.putExtra("isbao",map.get("status"));
                                i.putExtra("type",map.get("type"));
                                context.startActivity(i);
                            }else{
                                processCustomMessage(context, bundle);
                            }

                        }else if(keySet.contains("newsDetailId")){
                            intent = new Intent(context, NewEssayDetailActivity.class);
                            intent.putExtra("id",map.get("newsDetailId"));
                            context.startActivity(intent);
                        }else{
                            processCustomMessage(context, bundle);
                        }
                    }
//                    if (it.hasNext()) {
//                        String myKey = it.next();
//                        String myValue = json.optString(myKey);
//                        String myKey2 = it.next();
//                        String myValue2 = json.optString(myKey2);
//                        if("courseDetailId".equals(myKey2)){
//                            if("4".equals(myValue)){
//                                Intent i = new Intent(context, SumUpActivity.class);
//                                bundle.putString("id", myValue2);
//                                bundle.putString("type", myValue);
//                                i.putExtras(bundle);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(i);
//                            }else{
//                                Intent i = new Intent(context, VideoInfosActivity.class);
//                                bundle.putString("id", myValue2);
//                                bundle.putString("type",myValue);
//                                i.putExtras(bundle);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(i);
//                            }
//                        }else if("signUpDetailId".equals(myKey2)){
//                            if("5".equals(myValue)){
//                                Intent i = new Intent(context, OpenClassActivity.class);
//                                bundle.putString("id", myValue2);
//                                bundle.putString("type",myValue);
//                                i.putExtras(bundle);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(i);
//                            }else{
//                                Intent i = new Intent(context, NewEventDetailsActivity.class);
//                                bundle.putString("event_id",myValue2);
//                                bundle.putString("type",myValue);
//                                i.putExtras(bundle);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(i);
//                            }
//                        }
//                        map.put(myKey,myValue);
//                        Logger.e("DH_REC", myKey+"  "+myValue+"  "+myKey2+"  "+myValue2);
//                    }
//                    if(map!=null&&map.size()>0){
//                        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//                        if (iterator.hasNext()){
//                            Map.Entry<String, String> next1 = iterator.next();
//                            Map.Entry<String, String> next2 = iterator.next();
//                            if("courseDetailId".equals(next2.getKey())){
//                                if("4".equals(next1.getValue())){
//                                    Intent i = new Intent(context, SumUpActivity.class);
//                                    bundle.putString("id", next2.getValue());
//                                    bundle.putString("type", next1.getValue());
//                                    i.putExtras(bundle);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(i);
//                                }else{
//                                    Intent i = new Intent(context, VideoInfosActivity.class);
//                                    bundle.putString("id", next2.getValue());
//                                    bundle.putString("type", next1.getValue());
//                                    i.putExtras(bundle);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(i);
//                                }
//                            }else if("signUpDetailId".equals(next2.getKey())){
//                                if("5".equals(next1.getValue())){
//                                    Intent i = new Intent(context, OpenClassActivity.class);
//                                    bundle.putString("id", next2.getValue());
//                                    bundle.putString("type", next1.getValue());
//                                    i.putExtras(bundle);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(i);
//                                }else{
//                                    Intent i = new Intent(context, EventDetailActivity.class);
//                                    bundle.putString("id", next2.getValue());
//                                    bundle.putString("type", next1.getValue());
//                                    i.putExtras(bundle);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(i);
//                                }
//                            }
//                        }
//                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    private void checkCourse(String id, Map<String, String> map, Set<String> keySet) {

    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (NewMainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(NewMainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(NewMainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(NewMainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }
}
