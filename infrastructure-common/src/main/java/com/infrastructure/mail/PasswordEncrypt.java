package com.infrastructure.mail;

/**
 * 密码加密解密工具
 *
 * @author tyq
 * @data 2016/1/11
 */
public final class PasswordEncrypt {

    /**
     * 加密密码
     *
     * @param password 明文密码
     * @return
     */
    public static final String encrypt(String password) {
        StringBuilder sb = new StringBuilder(password.length());
        char[] arr = password.toCharArray();
        for (char c : arr) {
            c ^= 1;
            c ^= 0xa;
            sb.append(Integer.toHexString(c));
        }
        return sb.toString();
    }

    /**
     * 解密密码
     *
     * @param encrypt 密文
     * @return
     */
    public static final String decrypt(String encrypt) {
        String hex = encrypt.toLowerCase();
        StringBuilder sb = new StringBuilder();

        for (int i = 0, len = hex.length(); i < len; i += 2) {
            String sub = hex.substring(i, i + 2);
            int temp = Integer.parseInt(sub, 16);

            temp ^= 1;
            temp ^= 0xa;

            sb.append((char) temp);
        }

        return sb.toString();
    }

}
