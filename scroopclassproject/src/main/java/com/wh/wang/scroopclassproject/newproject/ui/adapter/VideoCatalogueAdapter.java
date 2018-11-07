package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.activity.LoginNewActivity;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.SumUpEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.LookHomeWorkActivity;
import com.wh.wang.scroopclassproject.newproject.utils.Formatter;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;
import com.wh.wang.scroopclassproject.utils.StringUtils;

import java.util.List;


/**
 * Created by chdh on 2018/2/23.
 */

public class VideoCatalogueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String mUser_id;
    private String mUserName;
    private String mMobile;
    private String mCate_id;
    private List<SumUpEntity.DirBean> mDirBeanList;
    private Context mContext;
    private LayoutInflater mInflater;

    public VideoCatalogueAdapter(List<SumUpEntity.DirBean> dirBeanList, Context context,String cate_id) {
        mDirBeanList = dirBeanList;
        mContext = context;
        mCate_id = cate_id;
        mInflater = LayoutInflater.from(MyApplication.mApplication);
        mUser_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
        mUserName = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "nickname", "");
        mMobile = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "mobile", "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            return new SecondTitleViewHolder(mInflater.inflate(R.layout.video_detail_item_two,parent,false));
        }else if(viewType==0){
            return new FirstTitleViewHolder(mInflater.inflate(R.layout.video_detail_item_one,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder!=null&&mDirBeanList.get(position)!=null){
            final SumUpEntity.DirBean dirBean = mDirBeanList.get(position);
            if(holder instanceof  FirstTitleViewHolder){
                FirstTitleViewHolder firstTitleViewHolder = (FirstTitleViewHolder) holder;
                firstTitleViewHolder.mVideoItemOneTv.setText(dirBean.getVideo_title());
            }else if(holder instanceof SecondTitleViewHolder){
                final SecondTitleViewHolder secondTitleViewHolder = (SecondTitleViewHolder) holder;
                secondTitleViewHolder.mVideoItemTwoTv.setText(dirBean.getVideo_title());
                Log.e("DH_CATE",mCate_id+"  "+dirBean.getId());
                if(mCate_id!=null&&!"".equals(mCate_id)&&!"-1".equals(mCate_id)&&dirBean.getId().equals(mCate_id)){
                    secondTitleViewHolder.view.setBackgroundColor(Color.parseColor("#16bc6e"));
                    secondTitleViewHolder.mCateTime.setTextColor(Color.parseColor("#ffffff"));
                    secondTitleViewHolder.mNoLine.setTextColor(Color.parseColor("#ffffff"));
                    if(mOnCatalogueClickListener!=null){
                        mOnCatalogueClickListener.onCateLocation(secondTitleViewHolder.view);
                    }
                }else{
                    secondTitleViewHolder.view.setBackgroundColor(Color.parseColor("#ffffff"));
                    secondTitleViewHolder.mCateTime.setTextColor(Color.parseColor("#9eadc3"));
                    secondTitleViewHolder.mNoLine.setTextColor(Color.parseColor("#8B8F97"));
                }
                secondTitleViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCate_id = dirBean.getId();
                        VideoCatalogueAdapter.this.notifyDataSetChanged();
                        if(mOnCatalogueClickListener!=null){
                            mOnCatalogueClickListener.onCatalogueClick(position,dirBean);
                        }
                    }
                });
                secondTitleViewHolder.mCateTime.setText("("+ Formatter.formatTime(Integer.parseInt(dirBean.getLength())*1000)+")");
                if("0".equals(dirBean.getCanshow())){ //小组是否上线
                    secondTitleViewHolder.mNoLine.setVisibility(View.VISIBLE);
                }else{
                    secondTitleViewHolder.mNoLine.setVisibility(View.GONE);
                    String ifnew = dirBean.getIfnew();
                    if("2".equals(ifnew)){  //是否为新课
                        secondTitleViewHolder.mTag.setVisibility(View.VISIBLE);
                    }else{
                        secondTitleViewHolder.mTag.setVisibility(View.GONE);
                    }
                    if("1".equals(dirBean.getIs_live())){ //是否为直播 （暂时取消）
                        secondTitleViewHolder.mWork.setVisibility(View.VISIBLE);
                        secondTitleViewHolder.mVideoItemTwoTv.setText("【直播】"+dirBean.getVideo_title());
                        if("1".equals(dirBean.getLive_status())){
                            secondTitleViewHolder.mWork.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.apply_line_shape_bg));
                            secondTitleViewHolder.mWork.setTextColor(Color.parseColor("#8DC63F"));
                            secondTitleViewHolder.mWork.setText("直播中");
                            secondTitleViewHolder.view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mCate_id = dirBean.getId();
                                    VideoCatalogueAdapter.this.notifyDataSetChanged();
                                    if(StringUtils.isNotEmpty(mUser_id)){
//                                        Map<String, Object> map = new HashMap<String, Object>();
//                                        map.put("servername", "");
//                                        map.put("userrole", 2);
//                                        map.put("host", RoomClient.webServer);
//                                        map.put("port", 80);  //端口
//                                        map.put("serial", dirBean.getLive_room()); //课堂号
//                                        map.put("password", dirBean.getLive_pass()); //课堂号
//                                        map.put("nickname", StringUtils.isEmpty(mUserName)?mMobile:mUserName); // 昵称
//                                        RoomClient.getInstance().joinRoom(mContext, map);
                                    }else{
                                        Intent intent = new Intent(mContext, LoginNewActivity.class);
                                        mContext.startActivity(intent);
                                    }
                                }
                            });
                        }else if("0".equals(dirBean.getLive_status())){
                            secondTitleViewHolder.mWork.setVisibility(View.VISIBLE);
                            if(dirBean.getZuoye_id()!=null&&!"0".equals(dirBean.getZuoye_id())){
                                secondTitleViewHolder.mWork.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.apply_examine_bg));
                                secondTitleViewHolder.mWork.setTextColor(Color.parseColor("#282828"));
                                secondTitleViewHolder.mWork.setText("作业");
                                secondTitleViewHolder.mWork.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(StringUtils.isNotEmpty(mUser_id)){
                                            Intent intent = new Intent(mContext, LookHomeWorkActivity.class);
                                            intent.putExtra("video_id",dirBean.getVideo_id());
                                            intent.putExtra("cate_id",dirBean.getId());
                                            mContext.startActivity(intent);
                                        }else{
                                            Intent intent = new Intent(mContext, LoginNewActivity.class);
                                            mContext.startActivity(intent);
                                        }
                                    }
                                });
                            }else{
                                secondTitleViewHolder.mWork.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.apply_examine_bg));
                                secondTitleViewHolder.mWork.setTextColor(Color.parseColor("#282828"));
                                secondTitleViewHolder.mWork.setText("直播结束");
                            }
                        }else{
                            secondTitleViewHolder.mWork.setBackgroundDrawable(null);
                            secondTitleViewHolder.mWork.setTextColor(Color.parseColor("#8DC63F"));
                            secondTitleViewHolder.mWork.setText(dirBean.getLive_status());
                        }
                    }else{
                        if(dirBean.getZuoye_id()!=null&&!"0".equals(dirBean.getZuoye_id())){ //作业
                            secondTitleViewHolder.mWork.setVisibility(View.VISIBLE);
                            secondTitleViewHolder.mWork.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.apply_examine_bg));
                            secondTitleViewHolder.mWork.setTextColor(Color.parseColor("#282828"));
                            secondTitleViewHolder.mWork.setText("作业");
                            secondTitleViewHolder.mWork.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(StringUtils.isNotEmpty(mUser_id)){
                                        Intent intent = new Intent(mContext, LookHomeWorkActivity.class);
                                        intent.putExtra("video_id",dirBean.getVideo_id());
                                        intent.putExtra("cate_id",dirBean.getId());
                                        mContext.startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(mContext, LoginNewActivity.class);
                                        mContext.startActivity(intent);
                                    }
                                }
                            });
                        }else{
                            secondTitleViewHolder.mWork.setVisibility(View.GONE);
                        }
                    }
                }

//                secondTitleViewHolder.mTag.setVisibility(View.VISIBLE);
//                secondTitleViewHolder.mWork.setVisibility(View.VISIBLE);
//                secondTitleViewHolder.mWork.setText("直播开始");

            }
        }
    }

    public void nofitPlayID(String cate_id){
        mCate_id = cate_id;
        VideoCatalogueAdapter.this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDirBeanList!=null?mDirBeanList.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mDirBeanList.get(position).getUrl()!=null&&!"null".equals(mDirBeanList.get(position).getUrl())&&!"".equals(mDirBeanList.get(position).getUrl())){
            return 1;
        }else{
            return 0;
        }
    }

    public class FirstTitleViewHolder extends RecyclerView.ViewHolder{
        private TextView mVideoItemOneTv;

        public FirstTitleViewHolder(View itemView) {
            super(itemView);
            mVideoItemOneTv = (TextView) itemView.findViewById(R.id.video_item_one_tv);
        }
    }

    public class SecondTitleViewHolder extends RecyclerView.ViewHolder{
        private TextView mVideoItemTwoTv;
        private TextView mWork;
        private TextView mTag;
        private LinearLayout mOther;
        private RelativeLayout mCateTitle;
        private TextView mCateTime;
        private View view ;
        private TextView mNoLine;

        public SecondTitleViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mTag = (TextView) itemView.findViewById(R.id.tag);
            mVideoItemTwoTv = (TextView) itemView.findViewById(R.id.video_item_two_tv);
            mWork = (TextView) itemView.findViewById(R.id.work);
            mOther = (LinearLayout) itemView.findViewById(R.id.other);
            mCateTitle = (RelativeLayout) itemView.findViewById(R.id.cate_title);
            mCateTime = (TextView) itemView.findViewById(R.id.cate_time);
            mNoLine = (TextView) itemView.findViewById(R.id.no_line);

        }
    }

    private OnCatalogueClickListener mOnCatalogueClickListener;

    public void setOnCatalogueClickListener(OnCatalogueClickListener onCatalogueClickListener) {
        mOnCatalogueClickListener = onCatalogueClickListener;
    }

    public interface OnCatalogueClickListener{
        void onCatalogueClick(int pos,SumUpEntity.DirBean dirBean);
        void onCateLocation(View view);
    }

}
