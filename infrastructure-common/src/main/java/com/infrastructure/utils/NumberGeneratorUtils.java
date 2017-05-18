package com.infrastructure.utils;

import java.text.DecimalFormat;


public class NumberGeneratorUtils {

    private static DecimalFormat df = new DecimalFormat("0000");

    /**
     * @return String
     * @throws :
     * @Description(功能描述) :  获取商品单号
     * @author(作者) ：  suyuanliu
     * @date (开发日期)          :  2017年1月9日 下午17:33:52
     */
    public synchronized static String getGoodsNo() {
        return getNo("11");

    }

    /**
     * @return String
     * @throws :
     * @Description(功能描述) :  获取订单单号
     * @author(作者) ：  suyuanliu
     * @date (开发日期)          :  2017年1月9日 下午17:33:52
     */
    public synchronized static String getOrderNo() {
        return getNo("10");

    }

    public static void main(String[] args) {
        System.out.println(getOrderNo());
    }

    /**
     * 获取充值单号
     * 规则：年2位，月2位，日2位，时2位，分2位，秒2位，随机数6位的字符串
     *
     * @return
     */
    public static synchronized String getNo(String prefix) {
        String number = DateUtils.date2String(DateUtils.currentDateTime(), "yyMMddHHmmss");
        String str2 = df.format(Long.valueOf(DateUtils.date2String(DateUtils.currentDateTime(), "mmss")));
        return prefix + number + str2;
    }


}
