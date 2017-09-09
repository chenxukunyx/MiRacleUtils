package com.miracle.libs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/31
 * @time: 上午9:03
 */

public class MD5Utils {

    public static String encoder(String psd) {
        try {
            //指定加密算法类型
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //将需要加密的字符串转换成byte类型的数组，然后进行随机哈希组合
            byte[] bs = digest.digest(psd.getBytes());
            //循环遍历bs，然后让其生成32位字符串，拼接字符串
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bs) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
