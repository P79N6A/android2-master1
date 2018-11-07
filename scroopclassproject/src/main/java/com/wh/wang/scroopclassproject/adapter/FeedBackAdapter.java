package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.bean.MineFeedBackBean;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.MyDisplayOptions;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

import java.util.List;


public class FeedBackAdapter extends CommonAdapter {

    private Context context;
    private List<MineFeedBackBean.ListBean> mDatas;

    private RelativeLayout mine_item_order_rl;
    private ImageView mine_item_order_iv_pics;
    private TextView mine_item_order_tv_titles;
    private TextView mine_item_order_tv_money;


    public FeedBackAdapter(Context context, List<MineFeedBackBean.ListBean> mDatas) {
        super(context, mDatas);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent,
                R.layout.feedback_item, position);

        mine_item_order_rl = viewHolder.getView(R.id.mine_item_order_rl);
        mine_item_order_iv_pics = viewHolder.getView(R.id.mine_item_order_iv_pics);
        mine_item_order_tv_titles = viewHolder.getView(R.id.mine_item_order_tv_titles);
        mine_item_order_tv_money = viewHolder.getView(R.id.mine_item_order_tv_money);

        //右侧 客户的信息
        RelativeLayout mine_feedback_rlayout_right = viewHolder.getView(R.id.mine_feedback_rlayout_right);
        TextView mine_feedback_tv_right_content = viewHolder.getView(R.id.mine_feedback_tv_right_content);
        ImageView mine_feedback_iv_right = viewHolder.getView(R.id.mine_feedback_iv_right);

        //左侧 官方反馈信息
        RelativeLayout mine_feedback_rlayout_left = viewHolder.getView(R.id.mine_feedback_rlayout_left);
        TextView mine_feedback_tv_left_content = viewHolder.getView(R.id.mine_feedback_tv_left_content);
        ImageView mine_feedback_iv_left = viewHolder.getView(R.id.mine_feedback_iv_left);

        MineFeedBackBean.ListBean feedBackBean = mDatas.get(position);
        if (feedBackBean.getUser_type() == 0) {  //用户回复内容
            mine_feedback_rlayout_right.setVisibility(View.VISIBLE);
            mine_feedback_rlayout_left.setVisibility(View.GONE);
            mine_feedback_tv_right_content.setText(feedBackBean.getContent());
            ImageLoader.getInstance().displayImage(feedBackBean.getAvator(), mine_feedback_iv_right, MyDisplayOptions.getOptions());

        } else if (feedBackBean.getUser_type() == 1) {
            mine_feedback_rlayout_right.setVisibility(View.GONE);
            mine_feedback_rlayout_left.setVisibility(View.VISIBLE);
            mine_feedback_tv_left_content.setText(feedBackBean.getContent());
            //ImageLoader.getInstance().displayImage(feedBackBean.getAvator(), mine_feedback_iv_left, MyDisplayOptions.getOptions());
            mine_feedback_iv_left.setBackgroundResource(R.drawable.mine_repeat);
        }

        return viewHolder.getConvertView();
    }
}
