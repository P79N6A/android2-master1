package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.CommentResultBena;
import com.wh.wang.scroopclassproject.bean.ReplyBean;
import com.wh.wang.scroopclassproject.bean.VedioDetailInfo;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.BToast;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.NoScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private int resourceId;
    private Context context;
    private Handler handler;
    private List<VedioDetailInfo.CommentBean> list;
    private LayoutInflater inflater;
    private String courseid;
    private String user_id;
    private int pos;
    ReplyAdapter adapter;

    public CommentAdapter(Context context, List<VedioDetailInfo.CommentBean> list
            , int resourceId, Handler handler, String courseid, String user_id) {
        this.list = list;
        this.context = context;
        this.handler = handler;
        this.resourceId = resourceId;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        VedioDetailInfo.CommentBean bean = list.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(resourceId, null);
            holder.commentItemImg = (ImageView)
                    convertView.findViewById(R.id.commentItemImg);
            holder.commentNickname = (TextView)
                    convertView.findViewById(R.id.commentNickname);
            holder.replyText = (TextView)
                    convertView.findViewById(R.id.replyText);

            holder.deleteText = (TextView)
                    convertView.findViewById(R.id.deleteText);

            holder.mDeleteTextImg = (ImageView) convertView.findViewById(R.id.deleteText_img);
            holder.mReplyTextImg = (ImageView) convertView.findViewById(R.id.replyText_img);

            holder.commentItemTime = (TextView)
                    convertView.findViewById(R.id.commentItemTime);
            holder.commentItemContent = (TextView)
                    convertView.findViewById(R.id.commentItemContent);
            holder.replyList = (NoScrollListView)
                    convertView.findViewById(R.id.replyList);
            holder.replyList.setVisibility(View.INVISIBLE);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(bean.getAvator(), holder.commentItemImg, MyDisplayOptions.getOptions());

        holder.commentNickname.setText(bean.getNickname());
        holder.commentItemTime.setText(bean.getShijian());
        holder.commentItemContent.setText(bean.getContent());
        //rList
        if (bean.getReplyList() != null && bean.getReplyList().size() > 0) {
            holder.replyList.setVisibility(View.VISIBLE);
            adapter = new ReplyAdapter(context, position, bean.getReplyList(), R.layout.reply_item, handler, courseid,user_id);
            holder.replyList.setAdapter(adapter);
        } else {
            holder.replyList.setVisibility(View.GONE);
        }
        TextviewClickListener tcl = new TextviewClickListener(position);
        holder.replyText.setOnClickListener(tcl);
        if (bean.getUser_id().equals(user_id)) {
            holder.deleteText.setVisibility(View.VISIBLE);
            holder.mDeleteTextImg.setVisibility(View.VISIBLE);
            holder.deleteText.setOnClickListener(tcl);
        } else {
            holder.deleteText.setVisibility(View.GONE);
            holder.mDeleteTextImg.setVisibility(View.GONE);
        }

        return convertView;
    }

    /**
     * 获取回复评论
     */
    public void getReplyComment(ReplyBean bean, int position) {
        List<ReplyBean> rList = list.get(position).getReplyList();
        rList.add(rList.size(), bean);
    }

    private final class ViewHolder {
        public ImageView commentItemImg;            //评论人图片
        public TextView commentNickname;            //评论人昵称
        public TextView replyText;                    //回复
        public TextView deleteText;                    //删除
        public TextView commentItemTime;            //评论时间
        public TextView commentItemContent;            //评论内容
        public NoScrollListView replyList;            //评论回复列表
        public ImageView mDeleteTextImg;
        public ImageView mReplyTextImg;

    }

    /**
     * 事件点击监听器
     */
    class TextviewClickListener implements OnClickListener {
        private int position;

        public TextviewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.replyText:
                    Constants.isFatherClick = true;
                    String commentPosition = position + "|" + list.get(position).getReplyList().size();
                    handler.sendMessage(handler.obtainMessage(10, commentPosition));
                    break;

                case R.id.deleteText:
                    pos = position;
                    deleteComment(position);
                    break;
            }
        }
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

    private void deleteComment(int pos) {
        //tring id, String product_id, String type
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
