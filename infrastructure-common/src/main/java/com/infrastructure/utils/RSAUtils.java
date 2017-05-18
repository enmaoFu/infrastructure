package com.infrastructure.utils;

import net.iharder.base64.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcHPUn8ruTPyTF5StalKFnykWUCOsZNOmJpUB4NRiYolV2VOIGtQhc5rIpONbQQw1y+djx0ImvYwB+MfmrG7iNtuQWy7xC2w8JvpohLwdBRXumDY6jjXHkTjfyUijzkt+wIbGilpOXkUEasLu5eM+c/oHFnSjs8xlrq36PprmDCwIDAQAB";
    private static final String PRIVATE_KEY = "privateKey";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * @param publicKey
     * @return String
     * @Title: publicKeyToString
     * @Description: 得到字符串型的公钥 Java
     */
    public static String publicKeyToString(RSAPublicKey publicKey) {
        return Base64.encodeBytes(publicKey.getEncoded());
    }

    /**
     * @param privateKey
     * @return String
     * @Title: privateKeyToString
     * @Description: 得到字符串型的私钥 Java
     */
    public static String privateKeyToString(RSAPrivateKey privateKey) {
        return Base64.encodeBytes(privateKey.getEncoded());
    }

    /**
     * @param publicKey
     * @return PublicKey
     * @Title: getPublicKey
     * @Description: 将字符串型公钥转化为PublicKey Java
     */
    public static PublicKey getPublicKey(String publicKey) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
            KeyFactory factory;
            factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param privateKey
     * @return PrivateKey
     * @Title: getPrivateKey
     * @Description: 将字符串型私钥转化为 PrivateKey Java
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            //System.out.println(privateKey.length());
            return factory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return Map<String,Object>
     * @Title: generateKeyBytes
     * @Description: 初始化密钥对
     */
    public static Map<String, Object> generateKeyBytes() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<String, Object>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param key
     * @param plainText
     * @return String
     * @Title: RSAEncode
     * @Description: 将字符串加密
     */
    public static String RSAEncode(PublicKey key, String plainText) {
        try {
            byte[] b = plainText.getBytes("UTF-8");
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return Base64.encodeBytes(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param key
     * @param encodedText
     * @return String
     * @Title: RSADecode
     * @Description: 将字符串解密
     */
    public static String RSADecode(PrivateKey key, String encodedText) {
        try {
            byte[] b = Base64.decode(encodedText);
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data
     * @return byte[]
     * @Title: removeMSZero
     * @Description: JAVA和.Net密钥转化算法
     */
    private static byte[] removeMSZero(byte[] data) {
        byte[] temp;
        int len = data.length;
        if (data[0] == 0) {
            temp = new byte[data.length - 1];
            System.arraycopy(data, 1, temp, 0, len - 1);
        } else
            temp = data;

        return temp;
    }

    public String getSign() {
        String data = "{\"LoginName\":\"15823814954\",\"LoginType\":\"GR\",\"MerchantCode\":\"201701031445\",\"Password\":\"" + RSAEncode(getPublicKey(PUBLIC_KEY), "wsx999") + "\"}";
        String sign = RSAEncode(getPublicKey(PUBLIC_KEY), data);
        return sign;
    }
}