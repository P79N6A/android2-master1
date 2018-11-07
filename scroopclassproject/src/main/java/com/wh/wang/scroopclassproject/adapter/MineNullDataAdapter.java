package com.wh.wang.scroopclassproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.utils.CommonAdapter;
import com.wh.wang.scroopclassproject.utils.ViewHolder;

/**
 * Created by wang on 2017/8/25.
 */

public class MineNullDataAdapter extends CommonAdapter {
    private Context context;
    private RelativeLayout mines_no_datas_rl;
    private ImageView mines_no_datas_iv;
    private TextView mines_no_datas_tv_mian;
    private TextView mines_no_datas_tv_branch;
    private int flag;

    public MineNullDataAdapter(Context context, int flag) {
        super(context);
        this.context = context;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, convertView, parent,
                R.layout.null_item, position);

        //没有数据输显示的控件
        mines_no_datas_rl = viewHolder.getView(R.id.mines_no_datas_rl);
        mines_no_datas_iv = viewHolder.getView(R.id.mines_no_datas_iv);
        mines_no_datas_tv_mian = viewHolder.getView(R.id.mines_no_datas_tv_mian);
        mines_no_datas_tv_branch = viewHolder.getView(R.id.mines_no_datas_tv_branch);
        if (flag == 1) {
            mines_no_datas_rl.setVisibility(View.VISIBLE);
            //mines_no_datas_iv.setBackgroundResource(R.drawable.error_one);
            mines_no_datas_iv.setBackgroundResource(R.drawable.login_empty);
            mines_no_datas_tv_mian.setVisibility(View.GONE);
            mines_no_datas_tv_branch.setVisibility(View.GONE);
            mines_no_datas_tv_mian.setText("你最中意的，便是收藏");
            mines_no_datas_tv_branch.setText("哦快看呐！他们的收藏夹已经开花结果了");

        } else if (flag == 2) {
            mines_no_datas_rl.setVisibility(View.VISIBLE);
            mines_no_datas_iv.setBackgroundResource(R.drawable.error_two);
            mines_no_datas_tv_mian.setText("不积硅步，无以至千里");
            mines_no_datas_tv_branch.setText("你的竞争对手已在学习的路上走了很远");

        } else if (flag == 3) {
            mines_no_datas_rl.setVisibility(View.VISIBLE);
            mines_no_datas_iv.setBackgroundResource(R.drawable.error_three);
            mines_no_datas_tv_mian.setText("孜孜不倦，水滴石穿");
            mines_no_datas_tv_branch.setText("常将有日思无日，莫待无时思有时");

        } else if (flag == 4) {
            mines_no_datas_rl.setVisibility(View.VISIBLE);
            mines_no_datas_iv.setBackgroundResource(R.drawable.error_one);
            mines_no_datas_tv_mian.setText("两粒种子，一片森林");
            mines_no_datas_tv_branch.setText("勇于开始，才能找到成功的路");
        } else if (flag == 5) {
            mines_no_datas_rl.setVisibility(View.VISIBLE);
            mines_no_datas_iv.setBackgroundResource(R.drawable.seach_null);
            mines_no_datas_tv_mian.setVisibility(View.INVISIBLE);
            mines_no_datas_tv_branch.setVisibility(View.INVISIBLE);
        }

        return viewHolder.getConvertView();
    }
}
