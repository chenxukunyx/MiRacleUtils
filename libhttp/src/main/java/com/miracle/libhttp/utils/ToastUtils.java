package com.miracle.libhttp.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.miracle.libhttp.net.HttpFactory;

/**
 * Created with Android Studio
 *
 * @fuction:
 * @author: chenxukun
 * @data: 2017/9/11
 * @time: 下午5:50
 */

public class ToastUtils {

    private static int duration = 200;
    private Context mContext;
    private static ToastUtils toastUtils;

    public static ToastUtils getInsance() {
        if (toastUtils == null) {
            synchronized (ToastUtils.class) {
                if (toastUtils == null) {
                    toastUtils = new ToastUtils();
                }
            }
        }
        return toastUtils;
    }

    public void show(String msg) {
        ToastShortCenter(HttpFactory.getmContext(), msg);
    }


    /**
     * 默认Toast调用
     * @param context 上下文
     * @param message 显示文本
     */
    public static void Toast(final Context context, final String message) {
        Toast.makeText(context, message, duration).show();
    }

    /**
     * 将最长使用的显示方法单独提出来，方便使用。
     * 屏幕中心位置短时间显示Toast。
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        ToastShortCenter(context,message);
    }

    /**
     * 屏幕底部中间位置显示短时间Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortBottomCenter(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 屏幕底部左边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortBottomLeft(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕底部右边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortBottomRight(Context context, String message) {

        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕中心位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortCenter(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕中心左边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortCenterLeft(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕中心右边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortCenterRight(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕顶部中心位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortTopCenter(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕顶部左边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortTopLeft(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕顶部右边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastShortTopRight(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕底部中间位置显示长时间Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongBottomCenter(Context context, String message) {
        if (context != null) {

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 屏幕底部左边位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongBottomLeft(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕底部右边位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongBottomRight(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕中心位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongCenter(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕中心左边位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongCenterLeft(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕中心右边位置短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongCenterRight(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.RIGHT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕顶部中心位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongTopCenter(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕顶部左边位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongTopLeft(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.show();
        }
    }

    /**
     * 屏幕顶部右边位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongTopRight(Context context, String message) {
        if (context != null) {

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
            toast.show();
        }
    }

}
