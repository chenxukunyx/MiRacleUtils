package com.miracle.libs.utils;

import android.util.Log;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-08-17
 * @time: 09:38
 * @age: 24
 */
public class MLog {

    private static final String TAG = "MLog----->>";

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.v(tag, msg);
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.d(tag, msg);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.i(tag, msg);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.w(tag, msg);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg){
        if (tag == null) {
            tag = TAG;
        }
        Log.e(tag, msg);
    }



}
