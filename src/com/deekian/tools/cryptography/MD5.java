package com.deekian.tools.cryptography;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5计算
 */
public class MD5 {
    public static String makeMD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            //将加密后的数据转换为16进制数字
            StringBuilder md5code = new StringBuilder(new BigInteger(1, md.digest()).toString(16));// 16进制数字
            // 如果生成数字未满32位，需要前面补0
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code.insert(0, "0");
            }
            return md5code.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
