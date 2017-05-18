package com.infrastructure.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 * Created by Administrator on 2016/12/6.
 */
public class MD5Util {


    public static String MD5(String text) {
        String md5String = DigestUtils.md5Hex(text);
        String res = DigestUtils.sha256Hex(md5String);
        return res.toUpperCase();
    }

    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    /**
     * Base64 encode
     */
    public static String base64Encode(String data) {
        return Base64.encodeBase64String(data.getBytes());
    }

    /**
     * Base64 decode
     *
     * @throws UnsupportedEncodingException
     */
    public static String base64Decode(String data) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(data.getBytes()), "utf-8");
    }

    /**
     * md5
     */
    public static String md5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * sha1
     * */
    /*public static String sha1Hex(String data){
        return DigestUtils.sha1Hex(data);
    }*/

    /**
     * sha256
     */
    public static String sha256Hex(String data) {
        return DigestUtils.sha256Hex(data);
    }
}
