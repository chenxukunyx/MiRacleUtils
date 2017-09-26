package com.miracle.lib_http.utils;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-26
 * @time: 10:45
 * @age: 24
 */
public class Unicode2CharUtils {

    public static String decodeUnicode(String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        String s = buffer.toString();
        return s;
    }

    public static String unicode2Char(String unicode) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = unicode.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = unicode.substring(start + 2, unicode.length());
            } else {
                charStr = unicode.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        String s = buffer.toString();
        return s;
//        return buffer.toString();
    }

}
