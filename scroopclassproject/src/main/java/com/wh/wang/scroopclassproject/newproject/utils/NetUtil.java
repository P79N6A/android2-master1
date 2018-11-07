package com.wh.wang.scroopclassproject.newproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wh.wang.scroopclassproject.newproject.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by dh on 2017/11/11.
 */

//这是一个工具类,接收一个类的对象,返回值也是这个类的类型
public class NetUtil {
    public static <T> T getRetrofit(Class<T> service){
        //这段代码的作用是接收一个类的对象
        //传进来的类中包含了一个子url和请求的字段,
        //访问成功后,就用我们的字段和服务器的字段进行比较,
        //但是服务器的字符串可能是普通字符串,也可能是Gson字符
        //串等,所以使用addConverterFactory()方法,就可以和指定
        //的字符串进行比较了,下面的就是能和普通和Gson字符串进行比较
        Retrofit retrofit=new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(Constant.BASE_URL)
                //用于字符串的转换,是字符串的转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                //用于Gson字符串的转换,是Gson字符串的转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return (T)retrofit.create(service);
    }

    public static <T> T getRetrofit2(Class<T> service){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(Constant.BASE_URL_2)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return (T)retrofit.create(service);
    }


    public static <T> T getRetrofit3(Class<T> service){
        Retrofit retrofit=new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(Constant.BASE_URL_3)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return (T)retrofit.create(service);
    }


    public static <T> T getTestRetrofit(Class<T> service){
        Retrofit retrofit=new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(Constant.BASE_URL_TEST)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return (T)retrofit.create(service);
    }



    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }
}
