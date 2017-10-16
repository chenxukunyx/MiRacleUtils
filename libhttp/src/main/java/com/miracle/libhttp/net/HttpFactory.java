package com.miracle.libhttp.net;

import android.content.Context;
import android.view.View;

import java.util.Map;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-28
 * @time: 15:05
 * @age: 24
 */
public class HttpFactory {

    static HttpManager httpManager;

    public static Context getmContext() {
        return mContext;
    }

    private static Context mContext;

    public static HttpManager getHttpManager() {
        if (httpManager == null) {
            synchronized (HttpFactory.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager(mContext);
                }
            }
        }
        return httpManager;
    }

    public static HttpManager getHttpManager(String url) {
        if (httpManager == null) {
            synchronized (HttpFactory.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager(mContext, url);
                }
            }
        }
        return httpManager;
    }

    public static HttpManager getHttpManager(String url, Map<String, String> headers) {
        if (httpManager == null) {
            synchronized (HttpFactory.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager(mContext, url, headers);
                }
            }
        }
        return httpManager;
    }

    public static void init(Context context) {
        mContext = context;
    }
}
