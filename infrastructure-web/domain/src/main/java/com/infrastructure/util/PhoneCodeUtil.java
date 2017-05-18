package com.infrastructure.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @Filename PhoneCodeUtil.java
 * @Description
 * @Version 1.0
 * @Author suyl
 * @Email sushenyu2013@sina.cn
 * @History <li>Author: suyl<>
 * <li>Date: 2016/5/3<>
 * <li>Version: 1.0<>
 * <li>Content: create<>
 */
public class PhoneCodeUtil {

    public final static String CODE_NAME = "phone_code";
    /**
     * 短信验证码对象的长度
     */
    public final static int CODE_LENGTH = 4;
    /**
     * 短信验证码对象失效时间(秒)
     */
    public final static int CODE_OUT_TIME = 120;

    /**
     * 随机生成Code
     * @return
     */
    public static String randomCode(){
        StringBuffer code = new StringBuffer();
        //生成短信验证码
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    /**
     * 实例化验证码对象
     * @return
     */
    public static PhoneCode getInstance(String phone){
        return new PhoneCode(randomCode(),phone,Calendar.getInstance().getTime());
    }

    /**
     * 短信验证码是否有效
     * @return
     */
    public static boolean vaild(PhoneCode phoneCode){
        if(phoneCode == null){
            return false;
        }
        Date createTime = phoneCode.getCreateTime();
        Calendar cre = Calendar.getInstance();
        cre.setTime(createTime);

        Calendar cur = Calendar.getInstance();

        //当前时间减去validCode生成的时间
        long l = (cur.getTimeInMillis() - cre.getTimeInMillis()) / 1000;

        if(l < CODE_OUT_TIME){
            return true;
        }else{
            return false;
        }
    }
}
