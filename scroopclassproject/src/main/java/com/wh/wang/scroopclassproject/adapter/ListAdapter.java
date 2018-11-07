package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.DownVideoBean;
import com.wh.wang.scroopclassproject.bean.MineIdeaBean;
import com.wh.wang.scroopclassproject.downloads.DownLoadListener;
import com.wh.wang.scroopclassproject.downloads.DownLoadManager;
import com.wh.wang.scroopclassproject.downloads.SQLDownLoadInfo;
import com.wh.wang.scroopclassproject.http.GetDataListener;
import com.wh.wang.scroopclassproject.http.HttpUserManager;
import com.wh.wang.scroopclassproject.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wh.wang.scroopclassproject.R.id.startDown;
import static com.wh.wang.scroopclassproject.R.id.stopDown;

public class ListAdapter extends BaseAdapter {
    public ArrayList<DownVideoBean> listdata = new ArrayList<DownVideoBean>();
    private Context mcontext;
    private DownLoadManager downLoadManager;
    public boolean showcheck = false;

    public ListAdapter(Context context, DownLoadManager downLoadManager) {
        this.mcontext = context;
        this.downLoadManager = downLoadManager;
        listdata = downLoadManager.getAllTask();
        downLoadManager.setAllTaskListener(new DownloadManagerListener());
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.list_item_layout, null);
            holder.item_cb = (CheckBox) convertView.findViewById(R.id.item_cb);
            holder.fileName = (TextView) convertView.findViewById(R.id.file_name);
            holder.file_size = (TextView) convertView.findViewById(R.id.file_size);
            //holder.textProgress = (TextView) convertView.findViewById(R.id.file_progerss);
            holder.fileProgress = (ProgressBar) convertView.findViewById(R.id.progressbar);
            //holder.downloadIcon = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.startDown = (Button) convertView.findViewById(startDown);
            holder.stopDown = (Button) convertView.findViewById(stopDown);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        listdata.get(position).getChildPrograss();

        if (showcheck) {
            holder.item_cb.setVisibility(View.VISIBLE);
        } else {
            holder.item_cb.setVisibility(View.GONE);
        }

        holder.item_cb.setClickable(true);
        holder.item_cb.setChecked(listdata.get(position).isCheck());
        if (listdata.get(position).isCheck()) {
            holder.item_cb.setBackgroundResource(R.drawable.right);
        } else {
            holder.item_cb.setBackgroundResource(R.drawable.none);
        }

        final int poss = position;
        final Holder finalHolder = holder;
        holder.item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listdata.get(poss).setCheck(isChecked);
                if (isChecked) {
                    finalHolder.item_cb.setBackgroundResource(R.drawable.right);
                } else {
                    finalHolder.item_cb.setBackgroundResource(R.drawable.none);
                }
            }
        });

        holder.fileName.setText(listdata.get(position).getChildTitle());
        holder.file_size.setText(FileUtils.getFormatSize(listdata.get(position).getChildSize()));
        holder.fileProgress.setProgress(listdata.get(position).getProgress());
        //holder.textProgress.setText(listdata.get(position).getProgress() + "%");
        //holder.downloadIcon.setOnCheckedChangeListener(new CheckedChangeListener(position));
        ////if (listdata.get(position).isChildDownloading()) {
        //   holder.downloadIcon.setChecked(true);
        // } else {
        //     holder.downloadIcon.setChecked(false);
        // }

        if (listdata.get(position).isChildDownloading()) {

            holder.startDown.setVisibility(View.VISIBLE);
            holder.stopDown.setVisibility(View.GONE);
            holder.startDown.setText(listdata.get(position).getProgress() + "%");

        } else {
            holder.startDown.setVisibility(View.GONE);
            holder.stopDown.setVisibility(View.VISIBLE);
        }

        //TODO
        final int pos = position;
        final Holder finalHolder1 = holder;
        if (listdata.get(position).isChildDownloading()) {
            holder.startDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder1.startDown.setVisibility(View.GONE);
                    finalHolder1.stopDown.setVisibility(View.VISIBLE);
                    listdata.get(pos).setOnDownloading(false);
                    downLoadManager.stopTask(listdata.get(pos).getChildId() + "");
                }
            });

        } else {

            holder.stopDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder1.startDown.setVisibility(View.VISIBLE);
                    finalHolder1.stopDown.setVisibility(View.GONE);
                    // 继续下载
                    listdata.get(pos).setOnDownloading(true);
                    downLoadManager.startTask(listdata.get(pos).getChildId() + "");
                }
            });
        }

        ListAdapter.this.notifyDataSetChanged();
        return convertView;
    }

    public List<DownVideoBean> getListTask() {
        return this.listdata;
    }

    public static class Holder {
        TextView fileName = null;
        TextView file_size = null;
        ProgressBar fileProgress = null;
        //CheckBox downloadIcon = null;
        CheckBox item_cb = null;
        Button startDown;
        Button stopDown;
    }

    public void addItem(DownVideoBean taskinfo) {
        this.listdata.add(taskinfo);
        this.notifyDataSetInvalidated();
    }

    public void setListdata(ArrayList<DownVideoBean> listdata) {
        this.listdata = listdata;
        this.notifyDataSetInvalidated();
    }

    class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        int position = 0;

        public CheckedChangeListener(int position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // 继续下载
                listdata.get(position).setOnDownloading(true);
                downLoadManager.startTask(listdata.get(position).getChildId() + "");
            } else {
                //停止下载
                listdata.get(position).setOnDownloading(false);
                downLoadManager.stopTask(listdata.get(position).getChildId() + "");
            }
            ListAdapter.this.notifyDataSetChanged();
        }

    }

    private class DownloadManagerListener implements DownLoadListener {

        @Override
        public void onStart(SQLDownLoadInfo sqlDownLoadInfo) {

        }

        @Override
        public void onProgress(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {
            //根据监听到的信息查找列表相对应的任务，更新相应任务的进度
            for (DownVideoBean taskInfo : listdata) {
                if (taskInfo.getChildId() == (sqlDownLoadInfo.getChildId())) {
                    taskInfo.setChildFinished(sqlDownLoadInfo.getChildDownSize());
                    taskInfo.setChildSize(sqlDownLoadInfo.getChildSize());
                    ListAdapter.this.notifyDataSetChanged();
                    break;
                }
            }
        }

        @Override
        public void onStop(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {

        }

        @Override
        public void onSuccess(SQLDownLoadInfo sqlDownLoadInfo) {
            //根据监听到的信息查找列表相对应的任务，删除对应的任务
            for (DownVideoBean taskInfo : listdata) {
                if (taskInfo.getChildId() == sqlDownLoadInfo.getChildId()) {
                    listdata.remove(taskInfo);
                    ListAdapter.this.notifyDataSetChanged();
                    setNetStuat(sqlDownLoadInfo.getChildId(), 1, downLoadManager.getUserID());
                    break;
                }
            }
        }

        @Override
        public void onError(SQLDownLoadInfo sqlDownLoadInfo) {
            //根据监听到的信息查找列表相对应的任务，停止该任务
            for (DownVideoBean taskInfo : listdata) {
                if (taskInfo.getChildId() == (sqlDownLoadInfo.getChildId())) {
                    taskInfo.setOnDownloading(false);
                    ListAdapter.this.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    private void setNetStuat(int file_id, int status, String user_id) {
        HttpUserManager.getInstance().startDownload(file_id, status, user_id, new GetDataListener() {
            @Override
            public void onSuccess(Object data) {
                Message obtain = Message.obtain();
                obtain.obj = data;
                mHandler.sendMessage(obtain);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, MineIdeaBean.class);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            Log.e("whwh", "obj===>" + obj.toString());
            try {
                JSONObject jsonObject = new JSONObject(obj.toString());
                int code = jsonObject.optInt("code");
                if (code == 200) {
                    String msgStrs = jsonObject.optString("msg");
                    Log.e("whwh", "msgStrs==" + msgStrs);
                } else {
                    String msgStrs = jsonObject.optString("msg");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
