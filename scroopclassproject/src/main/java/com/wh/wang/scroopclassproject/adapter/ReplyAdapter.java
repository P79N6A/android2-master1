package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.CommentResultBena;
import com.wh.wang.scroopclassproject.bean.ReplyBean;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ReplyAdapter extends BaseAdapter {

    private int resourceId;
    private List<ReplyBean> list;
    private LayoutInflater inflater;
    private TextView replyContent;
    private TextView reply_delet;
    private TextView reply_add;
    private SpannableString ss;
    private Context context;
    private Handler handler;
    private int index;
    private String courseid;
    private int pos;
    private String user_id;

    public ReplyAdapter(Context context, int position, List<ReplyBean> list, int resourceId, Handler handler, String courseid, String user_id) {
        this.list = list;
        this.context = context;
        this.resourceId = resourceId;
        this.handler = handler;
        this.index = position;
        this.courseid = courseid;
        this.user_id = user_id;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ReplyBean bean = list.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, null);
            replyContent = (TextView) convertView.findViewById(R.id.replyContent);
            reply_delet = (TextView) convertView.findViewById(R.id.reply_delet);
            reply_add = (TextView) convertView.findViewById(R.id.reply_add);
            replyContent.setVisibility(View.VISIBLE);
            reply_delet.setVisibility(View.VISIBLE);
            reply_add.setVisibility(View.VISIBLE);

            Log.e("whwh", "reply====");
            final String replyNickName = bean.getNickname();
            final String commentNickName = bean.getRe_name();
            String replyContentStr = bean.getContent();
            //用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果
            //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
            //Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
            //Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
            //Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
            ss = new SpannableString(replyNickName + "回复" + commentNickName
                    + " : " + replyContentStr);
            ss.setSpan(new ForegroundColorSpan(Color.BLUE), replyNickName.length(),
                    replyNickName.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            replyContent.setText(ss);
            //添加点击事件时，必须设置
            replyContent.setMovementMethod(LinkMovementMethod.getInstance());

            pos = position;

            if (bean.getUser_id().equals(user_id)) {
                reply_delet.setVisibility(View.VISIBLE);
                reply_delet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //list.remove(position);
                        //notifyDataSetChanged();
                        deleteComment(position);
                    }
                });
            } else {
                reply_delet.setVisibility(View.GONE);
            }


            reply_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.isFatherClick = false;
                    String commentPosition = index + "|" + pos;
                    handler.sendMessage(handler.obtainMessage(10, commentPosition));
                }
            });
        }
        return convertView;
    }

    private Handler deleteHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    list.remove(pos);
                    notifyDataSetChanged();
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(context, msgStrs);

                } else {
                    String msgStrs = jsonObject.optString("msg");
                    BToast.showText(context, msgStrs);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void deleteComment(int position) {
        HttpUserManager.getInstance().deleteComment(list.get(pos).getId(), courseid, "1", new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                deleteHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, CommentResultBena.class);
    }
}
