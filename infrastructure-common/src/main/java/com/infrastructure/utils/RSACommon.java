package com.infrastructure.utils;


/**
 * Created by Administrator on 2016/12/6.
 */
public class RSACommon {

    /**
     * 对数据串签名
     *
     * @param password 商户密码
     * @param data     要签名的字符串
     * @return 签名好的字符串
     * @throws Exception
     */
    public static String sign(String password, String data) throws Exception {
        return MD5Util.EncoderByMd5(data + password);
    }

    /**
     * 商户平台提交过来的数据验签
     *
     * @param privateKey 商户的私钥
     * @param data       明文数据
     * @param sign       加密数据
     * @return
     */
    public static boolean decSign(String privateKey, String data, String sign) {
        String data2 = RSAUtils.RSADecode(RSAUtils.getPrivateKey(privateKey), sign);
        if (data2.equals(data)) {
            return true;
        }
        return false;
    }
}
