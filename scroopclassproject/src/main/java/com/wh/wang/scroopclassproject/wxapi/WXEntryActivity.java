package com.wh.wang.scroopclassproject.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.mvp.OnResultListener;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.AljPresenter;
import com.wh.wang.scroopclassproject.newproject.mvp.presenter.ShareInfomPresenter;
import com.wh.wang.scroopclassproject.newproject.ui.activity.CasebookInfoActivity;
import com.wh.wang.scroopclassproject.newproject.ui.activity.ShareResultActivity;
import com.wh.wang.scroopclassproject.utils.Constants;
import com.wh.wang.scroopclassproject.utils.SharedPreferenceUtil;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;
import static com.wh.wang.scroopclassproject.newproject.Constant.isFree7Vip;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPayCourseOrAction;
import static com.wh.wang.scroopclassproject.newproject.Constant.isPublicCourseShare;
import static com.wh.wang.scroopclassproject.newproject.Constant.isShareInform;
import static com.wh.wang.scroopclassproject.newproject.Constant.temporaryEventNo;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private String WX_APP_ID = Constants.APP_ID;
    private String WX_APP_SECRET = Constants.APP_SECRET;
    private ShareInfomPresenter mShareInfomPresenter = new ShareInfomPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.wx_api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        Constants.wx_api.registerApp(WX_APP_ID);
        Constants.wx_api.handleIntent(getIntent(), this);
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        //登录返回
        if (resp instanceof SendAuth.Resp) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "成功登陆";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    String code = ((SendAuth.Resp) resp).code;
                    Constants.weichat_code = code;
                    Constants.loginCode = 0;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "取消登陆";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送被拒绝";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
                default:
                    result = "分享返回";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
            }
        } else {
            //分享返回
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "发送成功";
                    Toast.makeText(MyApplication.mApplication, result, Toast.LENGTH_LONG).show();
                    String user_id = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "user_id", "");
                    String is_vip = SharedPreferenceUtil.getStringFromSharedPreference(mContext, "is_vip", "");
                    if (isPublicCourseShare) {
                        Intent intent = new Intent(this, ShareResultActivity.class);
                        startActivity(intent);
                    }
                    if (isShareInform) {
                        Log.e("DH_SHARE_INOF", user_id + "  " + isPayCourseOrAction + "  " + temporaryEventNo);
                        mShareInfomPresenter.shareInform(user_id, String.valueOf(isPayCourseOrAction), temporaryEventNo, "0", new OnResultListener() {
                            @Override
                            public void onSuccess(Object... value) {
                                Log.e("DH_SHARE", "SUCCESS");
                            }

                            @Override
                            public void onFault(String error) {

                            }
                        });
                    }
                    if (isFree7Vip) {
                        new AljPresenter().getAljFree7Vip(user_id, temporaryEventNo, new OnResultListener() {
                            @Override
                            public void onSuccess(Object... value) {

                                Log.e("DH_ALJ_FREE7", "SUCCESS");
                            }

                            @Override
                            public void onFault(String error) {

                            }
                        });
                        Intent intent = new Intent(WXEntryActivity.this, CasebookInfoActivity.class);
                        intent.putExtra("event_id", temporaryEventNo);

//                        if (!"1".equals(is_vip)) {
//                            intent.putExtra("free_vip","1");
//                        }
                        startActivity(intent);

                    }
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "发送取消";
                    Toast.makeText(MyApplication.mApplication, result, Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送被拒绝";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
                default:
                    result = "发送返回";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
            }
        }
//        Constant.SHARE_STATUE = false;
        isPayCourseOrAction = -1;
        isShareInform = false;
        isPublicCourseShare = false;
        isFree7Vip = false;
        WXEntryActivity.this.finish();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Constants.wx_api.handleIntent(intent, this);
        finish();
    }
}
