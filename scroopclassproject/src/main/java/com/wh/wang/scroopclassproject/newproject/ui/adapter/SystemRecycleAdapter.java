package com.wh.wang.scroopclassproject.newproject.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.model.MainCourseEntity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.MoreCourseActivity;
import com.wh.wang.scroopclassproject.newproject.utils.DisplayUtil;
import com.wh.wang.scroopclassproject.newproject.utils.GridSpacingItemDecoration;
import com.wh.wang.scroopclassproject.newproject.utils.MainSpaceItemDecoration;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class SystemRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private MainCourseEntity entity;
    private LayoutInflater mLayoutInflater;

    public SystemRecycleAdapter(Activity activity, MainCourseEntity entity) {
        this.activity = activity;
        this.entity = entity;
        mLayoutInflater = LayoutInflater.from(MyApplication.mApplication);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            return new WuDaLiViewHolder(mLayoutInflater.inflate(R.layout.item_system_wudali,parent,false));
        }else{
            return new SystemCourseViewHolder(mLayoutInflater.inflate(R.layout.item_choiceness_course,parent,false),viewType);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder!=null){
            if(holder instanceof WuDaLiViewHolder){
                WuDaLiViewHolder wuDaLiViewHolder = (WuDaLiViewHolder) holder;
                final List<MainCourseEntity.WudaliBean> wudali = entity.getWudali();
                String[] s = new String[]{"yunyingli","rencaili","zhichili_new","zhanlueli","yingxiaolue"};
                for (int i = 0; i < (wudali.size()>5?5:wudali.size()); i++) {
                    final String flag = s[i];
                    final MainCourseEntity.WudaliBean wudaliBean = wudali.get(i);
                    wuDaLiViewHolder.rs[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, MoreCourseActivity.class);
                            MobclickAgent.onEvent(activity,flag);
                            intent.putExtra("flag", "0");
                            intent.putExtra("jxid", wudaliBean.getId());
                            intent.putExtra("jxname", wudaliBean.getName());
                            activity.startActivity(intent);
                        }
                    });
                }
            }else if(holder instanceof SystemCourseViewHolder){
                SystemCourseViewHolder systemCourseViewHolder = (SystemCourseViewHolder) holder;
                if(position==1){
                    systemCourseViewHolder.mItemTitle.setVisibility(View.GONE);
                    systemCourseViewHolder.mItemCourseList.setAdapter(new ChoicenessEventAdapter(activity,entity.getEvents(),1));
                }else{
                    systemCourseViewHolder.mItemTitle.setVisibility(View.VISIBLE);
                    final MainCourseEntity.CourseBean.SystemCourseBean systemCourseBean
                            = entity.getCourse().getSystemCourse().get(position - 2);


                    systemCourseViewHolder.mItemTitleName.setText(systemCourseBean.getName());
                    systemCourseViewHolder.mItemMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, MoreCourseActivity.class);
                            int id = Integer.parseInt(systemCourseBean.getId());
                            intent.putExtra("flag","0");
                            intent.putExtra("jxid",id);
                            intent.putExtra("jxname", systemCourseBean.getName());
                            activity.startActivity(intent);
                        }
                    });
                    systemCourseViewHolder.mItemCourseList.setAdapter(new SystemCourseAdapter(activity,systemCourseBean.getCourse_detail()));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else if(position==1){
            return 1;
        }else{
            return 2;
        }
    }

    class WuDaLiViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mSystemWudali;
        private RelativeLayout mWudali1;
        private RelativeLayout mWudali2;
        private RelativeLayout mWudali3;
        private RelativeLayout mWudali4;
        private RelativeLayout mWudali5;
        private RelativeLayout[] rs;

        public WuDaLiViewHolder(View itemView) {
            super(itemView);
            mSystemWudali = (LinearLayout) itemView.findViewById(R.id.system_wudali);
            mWudali1 = (RelativeLayout) itemView.findViewById(R.id.wudali_1);
            mWudali2 = (RelativeLayout) itemView.findViewById(R.id.wudali_2);
            mWudali3 = (RelativeLayout) itemView.findViewById(R.id.wudali_3);
            mWudali4 = (RelativeLayout) itemView.findViewById(R.id.wudali_4);
            mWudali5 = (RelativeLayout) itemView.findViewById(R.id.wudali_5);
            rs = new RelativeLayout[]{mWudali1,mWudali2,mWudali3,mWudali4,mWudali5};
        }
    }

    class SystemCourseViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout mItemTitle;
        private TextView mItemTitleName;
        private TextView mItemMore;
        private RecyclerView mItemCourseList;

        public SystemCourseViewHolder(View itemView,int flag) {
            super(itemView);
            mItemTitle = (RelativeLayout) itemView.findViewById(R.id.item_title);
            mItemTitleName = (TextView) itemView.findViewById(R.id.item_title_name);
            mItemMore = (TextView) itemView.findViewById(R.id.item_more);
            mItemCourseList = (RecyclerView) itemView.findViewById(R.id.item_course_list);
            if(flag==1){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.mApplication);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mItemCourseList.setLayoutManager(linearLayoutManager);
                mItemCourseList.addItemDecoration(new MainSpaceItemDecoration(DisplayUtil.dip2px(MyApplication.mApplication,10)));
            }else{
                mItemCourseList.setLayoutManager(new GridLayoutManager(MyApplication.mApplication,2));
                mItemCourseList.addItemDecoration(new GridSpacingItemDecoration(2, DisplayUtil.dip2px(MyApplication.mApplication,10), true));
            }
        }
    }
}
