package com.wh.wang.scroopclassproject.newproject.utils;

import android.content.Context;
import android.content.Intent;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.newproject.ui.activity.AnswerActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CasebookInviteActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEssayDetailActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewEventDetailsActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewVideoInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.OpenClassActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.SumUpActivity;

/**
 * Created by teitsuyoshi on 2018/7/1.
 */

public class NewIntentUtils {

    public void IntentActivity(String sel_type, String type, String id, Context context) {
        Intent intent = null;
        switch (sel_type==null?"":sel_type) {
            case "1": //视频相关
                if("4".equals(type)){ //小结
                    intent = new Intent(context, SumUpActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("type",type);
                }else{ //普通课
                    intent = new Intent(context, NewVideoInfoActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("type",type);
                }
                break;
            case "2":
                if("5".equals(type)){ //公开课
                    intent = new Intent(context, OpenClassActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("type",type);
                }else if("8".equals(type)){ //案例集
                    intent = new Intent(context, CasebookInviteActivity.class);
                    intent.putExtra("event_id",id);
                }else{ //普通活动

                    MobclickAgent.onEvent(context,"indexshox");
                    intent = new Intent(context, NewEventDetailsActivity.class);
                    intent.putExtra("event_id",id);
                }
                break;
            case "3":
                //文章
                MobclickAgent.onEvent(context,"indexshox");
                intent = new Intent(context, NewEssayDetailActivity.class);
                intent.putExtra("id",id);
                break;
            case "4":
                //打卡列表
                break;
            case "5":
                //专题列表
                break;
            default:
                switch (type){
                    case "1": //普通视频
                        intent = new Intent(context, NewVideoInfoActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("type",type);
                        break;
                    case "2": //文章(下架)
                        intent = new Intent(context, NewEssayDetailActivity.class);
                        intent.putExtra("id", id);
                        MobclickAgent.onEvent(context,"indexshox");
                        break;
                    case "3": //活动报名帖
                        MobclickAgent.onEvent(context,"indexshox");
                        intent = new Intent(context, NewEventDetailsActivity.class);
                        intent.putExtra("event_id",id);
                        break;
                    case "4": //小结
                        intent = new Intent(context, SumUpActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("type",type);
                        break;
                    case "5": //公开课
                        intent = new Intent(context, OpenClassActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("type",type);
                        break;
                    case "6"://直播
                        intent = new Intent(context, AnswerActivity.class);
                        intent.putExtra("exam_url",id);
                        break;
                    case "8"://案例集
                        intent = new Intent(context, CasebookInviteActivity.class);
                        intent.putExtra("event_id",id);
                        break;
                }
                break;
        }
        if(intent!=null){
            context.startActivity(intent);
        }
    }
}
