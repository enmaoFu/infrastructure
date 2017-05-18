package com.infrastructure.web.utils;

import java.net.URLEncoder;

/**
 * Created by tyq on 2016/01/26
 */
public class URLEncode {

    /**
     * 转地址栏中文
     * @param str
     * @return
     */
    public static String encodeString(String str){
        StringBuilder sb = new StringBuilder();
        char[] ab = str.toCharArray();
        for (char c : ab) {
            if(c > 32 && c <127) {
                sb.append(c);
            }else {
                try {
                    sb.append(URLEncoder.encode(String.valueOf(c), "utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
