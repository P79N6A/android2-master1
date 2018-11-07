package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.VideoDB;
import com.wh.wang.scroopclassproject.bean.DownVideoBean;
import com.wh.wang.scroopclassproject.bean.MineIdeaBean;
import com.wh.wang.scroopclassproject.bean.VedioDetailInfo;
import com.wh.wang.scroopclassproject.downloads.DataKeeper;
import com.wh.wang.scroopclassproject.downloads.FileHelper;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.wh.wang.scroopclassproject.newproject.ui.activity.NewMainActivity.manager;


/**
 * Created by wang on 2017/11/8.
 */

public class VideoDownAdapter extends BaseAdapter {
    private Context context;
    private List<VedioDetailInfo.DirBean> downbeanLists;
    private String fatharTitle;
    private String fatherImg;
    private final VideoDB videoDB;
    private String userId;
    private final String FILEPATH = FileHelper.getFileDefaultPath();

    public VideoDownAdapter(Context context, List downbeanLists, String fatharTitle, String fatherImg, String userId) {
        this.context = context;
        this.downbeanLists = downbeanLists;
        this.fatharTitle = fatharTitle;
        this.fatherImg = fatherImg;
        this.userId = userId;
        videoDB = VideoDB.getInstance(context);
    }

    @Override
    public int getCount() {
        return downbeanLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DViewHolder dviewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.course_down_video_item, null);
            dviewHolder = new DViewHolder();
            dviewHolder.course_down_item_rlayout_name = (RelativeLayout) convertView.findViewById(R.id.course_down_item_rlayout_name);
            dviewHolder.course_down_item_tv_name = (TextView) convertView.findViewById(R.id.course_down_item_tv_name);
            dviewHolder.course_down_item_iv_down = (ImageView) convertView.findViewById(R.id.course_down_item_iv_down);
            convertView.setTag(dviewHolder);

        } else {
            dviewHolder = (DViewHolder) convertView.getTag();
        }

        //设置数据
        dviewHolder.course_down_item_tv_name.setText(downbeanLists.get(position).getVideo_title());
        String video_id = downbeanLists.get(position).getVideo_id();
        int fatherId = Integer.parseInt(video_id);
        String id = downbeanLists.get(position).getId();
        int childId = Integer.parseInt(id);
        File downloadFile = new File(FILEPATH + "/" + "." + childId + ".mp4");
        DataKeeper dataKeeper = new DataKeeper(context);
        if (downloadFile.exists()) {
            if (dataKeeper.isHave(fatherId, childId)) {
                dviewHolder.course_down_item_iv_down.setImageResource(R.drawable.course_down_finished);
            }
        } else {

            if (dataKeeper.isHave(fatherId, childId)) {
                dviewHolder.course_down_item_iv_down.setImageResource(R.drawable.course_down_noclicl);
            } else {
                dviewHolder.course_down_item_iv_down.setImageResource(R.drawable.course_down_clicl);
                final int downPos = position;

                final DViewHolder finalDviewHolder = dviewHolder;
                dviewHolder.course_down_item_rlayout_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalDviewHolder.course_down_item_rlayout_name.setOnClickListener(null);
                        Toast.makeText(context, "已添加到下载列表中..", Toast.LENGTH_SHORT).show();
                        finalDviewHolder.course_down_item_iv_down.setImageResource(R.drawable.course_down_noclicl);
                        DownVideoBean downVideoBean = new DownVideoBean();
                        String video_id = downbeanLists.get(downPos).getVideo_id();
                        int fatherId = Integer.parseInt(video_id);
                        downVideoBean.setFatherId(fatherId);
                        downVideoBean.setFatherTitle(fatharTitle);
                        downVideoBean.setFatherImg(fatherImg);
                        String id = downbeanLists.get(downPos).getId();
                        int childId = Integer.parseInt(id);
                        downVideoBean.setChildId(childId);
                        String chileTitle = downbeanLists.get(downPos).getVideo_title();
                        downVideoBean.setChildTitle(chileTitle);
                        //String chileUrl = "http://shaozi-video.oss-cn-beijing.aliyuncs.com/" + downbeanLists.get(downPos).getDownload_url();
                        String chileUrl = "http://u.wimg.cc/" + downbeanLists.get(downPos).getDownload_url();
                        downVideoBean.setChildUrl(chileUrl);
                        downVideoBean.setChildStart(0);
                        downVideoBean.setChildFinished(0);
                        downVideoBean.setChildPrograss(0);
                        manager.addTask(fatherId, fatharTitle, fatherImg, childId, chileUrl, chileTitle);
                        setNetStuat(childId, 0, userId);
                    }
                });
            }
        }

        return convertView;
    }

    //video_id, file_id, status, type, user_id, down_id
    private void setNetStuat(int file_id, int status, String user_id) {
        HttpUserManager.getInstance().startDownload(file_id, status, user_id, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Log.e("whwh", "Object data" + data.toString());
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, MineIdeaBean.class);
    }

    private static class DViewHolder {
        private RelativeLayout course_down_item_rlayout_name;
        private TextView course_down_item_tv_name;
        private ImageView course_down_item_iv_down;
    }
}
