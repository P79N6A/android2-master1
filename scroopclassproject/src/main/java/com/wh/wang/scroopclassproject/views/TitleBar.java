package com.wh.wang.scroopclassproject.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.newproject.ui.activity.NewSearchActivity;


/**
 * 自定义标题栏
 * wh
 */
public class TitleBar extends RelativeLayout {

    private Context context;

    /**
     * 在代码中实例化该类的时候使用这个方法
     *
     * @param context
     */
    public TitleBar(Context context) {
        this(context, null);
    }

    /**
     * 当在布局文件使用该类的时候，Android系统通过这个构造方法实例化该类
     *
     * @param context
     * @param attrs
     */
    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    /**
     * 当需要设置样式的时候，可以使用该方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TitleBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        ImageButton titlebar_ib_search = (ImageButton) findViewById(R.id.titlebar_ib_search);
        titlebar_ib_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewSearchActivity.class);
                context.startActivity(intent);
            }
        });

    }
}
