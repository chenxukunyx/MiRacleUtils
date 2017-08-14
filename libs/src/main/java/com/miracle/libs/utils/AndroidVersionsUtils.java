package com.miracle.libs.utils;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-11
 * @time: 13:37
 * @age: 24
 */
public class AndroidVersionsUtils {

    /**
     * 功能：用于判断当前版本是否大于某个版本
     */

    public static final String BASE = "BASE";//1;
    public static final String BASE_1_1 = "BASE_1_1";//2;
    public static final String CUPCAKE = "CUPCAKE";//3;
    public static final String CUR_DEVELOPMENT = "CUR_DEVELOPMENT";//10000;
    public static final String DONUT = "DONUT";//4;
    public static final String ECLAIR = "ECLAIR";//5;
    public static final String ECLAIR_0_1 = "ECLAIR_0_1";//6;
    public static final String ECLAIR_MR1 = "ECLAIR_MR1";//7;
    public static final String FROYO = "FROYO";//8;
    public static final String GINGERBREAD = "GINGERBREAD";//9;
    public static final String GINGERBREAD_MR1 = "GINGERBREAD_MR1";//10;
    public static final String HONEYCOMB = "HONEYCOMB";//11;
    public static final String HONEYCOMB_MR1 = "HONEYCOMB_MR1";//12;
    public static final String HONEYCOMB_MR2 = "HONEYCOMB_MR2";//13;
    public static final String ICE_CREAM_SANDWICH = "ICE_CREAM_SANDWICH";//14;
    public static final String ICE_CREAM_SANDWICH_MR1 = "ICE_CREAM_SANDWICH_MR1";//15;
    public static final String JELLY_BEAN = "JELLY_BEAN";//16;
    public static final String JELLY_BEAN_MR1 = "JELLY_BEAN_MR1";//17;
    public static final String JELLY_BEAN_MR2 = "JELLY_BEAN_MR2";//18;
    public static final String KITKAT = "KITKAT";//19;
    public static final String KITKAT_WATCH = "KITKAT_WATCH";//20;
    public static final String LOLLIPOP = "LOLLIPOP";//21;
    public static final String LOLLIPOP_MR1 = "LOLLIPOP_MR1";//22;
    public static final String M = "M";//23;
    public static final String N = "N";//24;
    public static final String N_MR1 = "N_MR1";//25;


    private static final Map<Integer, String> mAndroidVer;

    static {
        mAndroidVer = new HashMap<>();
        mAndroidVer.put(1, BASE);
        mAndroidVer.put(2, BASE_1_1);
        mAndroidVer.put(3, CUPCAKE);
        mAndroidVer.put(10000, CUR_DEVELOPMENT);
        mAndroidVer.put(4, DONUT);
        mAndroidVer.put(5, ECLAIR);
        mAndroidVer.put(6, ECLAIR_0_1);
        mAndroidVer.put(7, ECLAIR_MR1);
        mAndroidVer.put(8, FROYO);
        mAndroidVer.put(9, GINGERBREAD);
        mAndroidVer.put(10, GINGERBREAD_MR1);
        mAndroidVer.put(11, HONEYCOMB);
        mAndroidVer.put(12, HONEYCOMB_MR1);
        mAndroidVer.put(13, HONEYCOMB_MR2);
        mAndroidVer.put(14, ICE_CREAM_SANDWICH);
        mAndroidVer.put(15, ICE_CREAM_SANDWICH_MR1);
        mAndroidVer.put(16, JELLY_BEAN);
        mAndroidVer.put(17, JELLY_BEAN_MR1);
        mAndroidVer.put(18, JELLY_BEAN_MR2);
        mAndroidVer.put(19, KITKAT);
        mAndroidVer.put(20, KITKAT_WATCH);
        mAndroidVer.put(21, LOLLIPOP);
        mAndroidVer.put(22, LOLLIPOP_MR1);
        mAndroidVer.put(23, M);
        mAndroidVer.put(24, N);
        mAndroidVer.put(24, N_MR1);
    }

    /**
     * 是否在2.2版本及以上
     * @return
     */
    public static boolean isFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 是否在2.3版本以上
     * @return
     */
    public static boolean isGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 是否在2.3.3版本以上
     * @return
     */
    public static boolean isGingerbreadMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * 是否在3.0版本以上
     * @return
     */
    public static boolean isHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 是否在3.1版本以上
     * @return
     */
    public static boolean isHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 是否在4.0版本以上
     * @return
     */
    public static boolean isIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 是否在4.0.3版本
     * @return
     */
    public static boolean isIceCreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * 是否在4.1版本以上
     * @return
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 是否在4.4.2版本以上
     * @return
     */
    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 是否在5.0版本以上
     * @return
     */
    public static boolean isLolliPop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 是否在6.0版本以上
     * @return
     */
    public static boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 是否在7.0版本以上
     * @return
     */
    public static boolean isN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static String getAndroidVersion() {
        return mAndroidVer.get(Build.VERSION.SDK_INT);
    }
}
