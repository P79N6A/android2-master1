package com.wh.wang.scroopclassproject.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求核心类
 */
public class HttpCore {
    private static HttpCore core = new HttpCore();

    private HttpCore() {
    }

    public static HttpCore getInstance() {
        return core;
    }

    /**
     * post请求
     *
     * @param client
     * @param request
     * @param listener
     * @param clazz
     */
    public void httpPostServer(OkHttpClient client, Request request, final GetDataListener listener, final Class<?> clazz) {

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte buffer[] = new byte[1024];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.close();
                in.close();
                String data = baos.toString();
                Log.e("whwh", "httpPostServer--->data : " + data);
                Object o = new Gson().fromJson(data, clazz);
                listener.onSuccess(o);
            }

        });
    }

    /**
     * get请求
     *
     * @param client
     * @param request
     * @param listener
     * @param clazz
     */
    public void httpGetServer(OkHttpClient client, Request request, final GetDataListener listener, final Class<?> clazz) {
        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte buffer[] = new byte[1024];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.close();
                in.close();
                String data = baos.toString();
                ArrayList list = new ArrayList();
                Gson gson = new Gson();
                list = gson.fromJson(data, ArrayList.class);
                String s = gson.toJson(list.get(0));
                Log.e("whwh", "s=====" + s);
                listener.onSuccess(s);
            }
        });
    }


    /**
     * get请求
     *
     * @param client
     * @param request
     * @param listener
     * @param clazz    数据解析差距
     */
    public void httpGetServer2(OkHttpClient client, Request request, final GetDataListener listener, final Class<?> clazz) {
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte buffer[] = new byte[1024];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.close();
                in.close();
                String s = baos.toString();
                Log.e("DH_ISVIP", "s=====" + s);
                listener.onSuccess(s);
            }
        });
    }
}
