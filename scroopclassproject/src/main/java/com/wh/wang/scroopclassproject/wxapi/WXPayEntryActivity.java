package com.wh.wang.scroopclassproject.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.eventmodel.FinishTaskEntity;
import com.wh.wang.scroopclassproject.newproject.fun_class.study_group.net.SGDetail2_0Entity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CompanyResultActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.OrderActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.ResultActivity;
import com.wh.wang.scroopclassproject.newproject.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseType;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
//    private Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);
//         mHandler = new Handler();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            ToastUtils.showToast(this,isPayCourseOrAction+"");
            if(resp.errCode==0){
                Log.e("TAG","bbbbbb"+isPayCourseOrAction);
                if(isPayCourseOrAction!=-1){
                    if(isPayCourseOrAction==0){
                        final Intent intent = new Intent(this,ResultActivity.class);
                        intent.putExtra("type",String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no",temporaryEventNo);
                        startActivity(intent);
                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);

                            }
                        },300);

                    }else if(isPayCourseOrAction==1){

                        final Intent intent = new Intent(this,ResultActivity.class);
                        intent.putExtra("type",String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no",temporaryEventNo);
                        intent.putExtra("course_type",String.valueOf(isPayCourseType));
//                        startActivity(intent);
                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        },300);

                    }else if(isPayCourseOrAction==2){
                        final Intent intent = new Intent(this,CompanyResultActivity.class);
                        intent.putExtra("result_type","1");
//                        startActivity(intent);
                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        },300);

                    }else if(isPayCourseOrAction==3){
                        EventBus.getDefault().post(new SGDetail2_0Entity());
                    }else if(isPayCourseOrAction==1000){
                        Toast.makeText(MyApplication.mApplication, "购买成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new FinishTaskEntity());

                    }else if (isPayCourseOrAction==4){
//                        Intent intent = new Intent(WXPayEntryActivity.this,ResultActivity.class);
//                        intent.putExtra("type",String.valueOf(isPayCourseOrAction));
//                        intent.putExtra("order_no",temporaryEventNo);
//                        intent.putExtra("course_type",String.valueOf(isPayCourseType));
//                        startActivity(intent);
                        final Intent intent = new Intent(WXPayEntryActivity.this,ResultActivity.class);
                        intent.putExtra("type",String.valueOf(isPayCourseOrAction));
                        intent.putExtra("order_no",temporaryEventNo);
                        intent.putExtra("course_type",String.valueOf(isPayCourseType));
                        Handler mHandler = new Handler();
                         mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                startActivity(intent);
                            }
                        },300);


                    }
                }else{
                    Toast.makeText(this, "跳转结果页面异常", Toast.LENGTH_SHORT).show();
//                    sendRequestWithHttpURLConnection();
                }

            }else if(resp.errCode==-1){
                Toast.makeText(this, "支付发生错误", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
            }
            //重置支付类型状态
            temporaryEventNo = "";
            temporaryEventNo = "";
            isPayCourseType = "";
//            isPayCourseOrAction = -1;
            finish();
        }
}
    public static void sendRequestWithHttpURLConnection(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://api.dev.shaoziketang.com/?c=TestSpecial&m=tangZhiHui&shijian=1509677274&sign=bfd3b0564cf90a5fd4ac0f6afd53a993&uid=2363780");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mHandler.removeCallbacksAndMessages(null);
    }
}