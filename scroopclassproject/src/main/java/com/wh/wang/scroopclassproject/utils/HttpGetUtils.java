package com.wh.wang.scroopclassproject.utils;

import java.util.HashMap;

/**
 * 列表工具类
 *
 * @author xuliangbo
 */
public class HttpGetUtils {

    public static String getGetHttpUrl(HashMap<String, String> parameters, String urlString) {
        if (parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }
        return urlString;
    }
}
