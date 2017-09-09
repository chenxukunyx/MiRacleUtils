package com.miracle.libs.utils.cache;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/31
 * @time: 上午9:18
 */

public abstract class Cache {

    public static final String TAG = "Cache";

    abstract Bitmap get(String url) throws IOException;
    abstract void put(String url, Bitmap bitmap);
}
