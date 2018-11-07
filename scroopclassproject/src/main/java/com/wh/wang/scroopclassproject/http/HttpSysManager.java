package com.wh.wang.scroopclassproject.http;


import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpSysManager {
    private static HttpSysManager manager = new HttpSysManager();
    OkHttpClient okHttp = new OkHttpClient();

    private HttpSysManager() {
    }

    public static HttpSysManager getInstance() {
        return manager;
    }


    /**
     * 请求初始化接口
     *
     * @param listener
     * @param clazz
     */
    public void init(GetDataListener listener, Class<?> clazz) {

        Request request = new Request.Builder().url(HttpUrl.superiorUrl).get().build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * 请求课程页面接口
     *
     * @param listener
     * @param clazz
     */
    public void initCourse(GetDataListener listener, Class<?> clazz) {

        Request request = new Request.Builder().url(HttpUrl.courseUrl).get().build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * 请求阅读页面接口
     *
     * @param listener
     * @param clazz
     */
    public void initRead(GetDataListener listener, Class<?> clazz) {

        Request request = new Request.Builder().url(HttpUrl.readUrl).get().build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * 请求我的页面接口之未完成课程接口
     *
     * @param listener
     * @param clazz
     */
    public void initMineUnfinished(String userid, GetDataListener listener, Class<?> clazz) {

        Request request = new Request.Builder().url(HttpUrl.mineunFinishedUrl + userid).get().build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }


    /**
     * 请求我的页面接口之我的收藏接口
     *
     * @param listener
     * @param clazz
     */
    public void initMineMyFavourite(String userid, GetDataListener listener, Class<?> clazz) {
        Request request = new Request.Builder().url(HttpUrl.minemyFavouriteUrl + userid).get().build();
        HttpCore.getInstance().httpGetServer2(okHttp, request, listener, clazz);
    }

    /**
     * 登录页面
     *
     * @param listener
     * @param clazz
     */

    public void get_login(String name, String password, GetDataListener listener, Class<?> clazz) {
        String loginUrl = "http://api.shaoziketang.com/index.php?c=base&m=login&mobile=" + name + "&password=" + password;
        Request request = new Request.Builder().url(loginUrl).get().build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * 注册页面
     *
     * @param listener
     * @param clazz    http://api.shaoziketang.com/index.php?c=base&m=reg
     */

    public void get_register(String name, String password, String repassword, GetDataListener listener, Class<?> clazz) {
        String loginUrl = "http://api.shaoziketang.com/index.php?c=base&m=reg&mobile=" + name + "&password=" + password + "&repassword=" + repassword;
        Request request = new Request.Builder().url(loginUrl).get().build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * 修改密码页面
     *
     * @param listener
     * @param clazz    手机号码：mobile
     *                 原密码：password
     *                 新密码：new_password
     *                 重复新密码：repasword
     *                 <p>
     *                 api.shaoziketang.com/index.php?c=base&m=upatePassword
     */

    public void get_changepassword(String mobile, String oldpassword, String newpassword, String newrepassword, String user_id,
                                   GetDataListener listener, Class<?> clazz) {
        String loginUrl = "http://api.shaoziketang.com/index.php?c=base&m=updatePassword&mobile=" + mobile
                + "&password=" + oldpassword + "&new_password=" + newpassword + "&repassword=" + newrepassword + "&user_id=" + user_id;
        Request request = new Request.Builder().url(loginUrl).get().build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }

    /**
     * 修改个人信息页面
     *
     * @param listener
     * @param clazz    name, place, nice, employ, phone,
     *                 <p>
     *                 http://api.shaoziketang.com/index.php?c=base&m=updateInfo
     */

    public void get_changeInfos(String name, String place, String nice, String employ, String phone, String user_id,
                                GetDataListener listener, Class<?> clazz) {
        String loginUrl = "http://api.shaoziketang.com/index.php?c=base&m=updateInfo&user_id=" + user_id + "&job=" + employ + "&brand=" + nice + "&contact=" + phone + "&area=" + place + "&username=" + name;
        Request request = new Request.Builder().url(loginUrl).get().build();
        HttpCore.getInstance().httpGetServer(okHttp, request, listener, clazz);
    }
}
