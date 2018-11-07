package com.wh.wang.scroopclassproject.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wh.wang.scroopclassproject.R;
import com.wh.wang.scroopclassproject.base.MyApplication;
import com.wh.wang.scroopclassproject.newproject.utils.ScreenUtils;

import java.io.ByteArrayOutputStream;

import static com.wh.wang.scroopclassproject.wxapi.Util.bitmap2Bytes;

/**
 * Created by wang on 2017/12/5.
 */

public class ShareUtil {

    public static void weiChat(IWXAPI api, int flag, Context context, String shareUrl, String title, String des,int... imags) {
        int imgid = R.drawable.xiaologo;
        if(imags!=null&&imags.length>0&&imags[0]!=0){
            imgid = imags[0];
        }
        if(api==null){
            api = WXAPIFactory.createWXAPI(context, com.wh.wang.scroopclassproject.utils.Constants.APP_ID, true);
            api.registerApp(com.wh.wang.scroopclassproject.utils.Constants.APP_ID);
        }
        if (!api.isWXAppInstalled()) {
            return;
        }

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareUrl;
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = des;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), imgid);
        msg.thumbData = bmpToByteArray(thumb, 32);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = flag == 7 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    public static byte[] bmpToByteArray(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb&& options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    public static void wechatShareImg(IWXAPI api,Bitmap bitmap,int flag,Context context,boolean isRecycle) {
        if(flag==-1) return;
        if(api==null){
            api = WXAPIFactory.createWXAPI(context, com.wh.wang.scroopclassproject.utils.Constants.APP_ID, true);
            api.registerApp(com.wh.wang.scroopclassproject.utils.Constants.APP_ID);
        }
        if (!api.isWXAppInstalled()) {
            return;
        }
        WXImageObject wxImageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxImageObject;
        //设置缩略图
        Bitmap mBp = Bitmap.createScaledBitmap(bitmap,ScreenUtils.getScreenWidth(MyApplication.mApplication)/6, ScreenUtils.getScreenWidth(MyApplication.mApplication)/3, true);
        if(isRecycle)
            bitmap.recycle();

        msg.thumbData = bitmap2Bytes(mBp, 32);
//        msg.thumbData = bmpToByteArrayJPEG(mBp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//  transaction字段用
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
