package com.miracle.libs.utils.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.io.IOException;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/31
 * @time: 上午8:51
 *
 * 内存缓存
 */

public class MemoriesCacheUtils extends Cache{

    private LruCache<String, Bitmap> mBitmapLruCache;


    public MemoriesCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        mBitmapLruCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

//    public void setMemoryCache(String url, Bitmap bitmap) {
//        mBitmapLruCache.put(url, bitmap);
//    }
//
//    public Bitmap getMemoryCache(String url) {
//        return mBitmapLruCache.get(url);
//    }

    @Override
    Bitmap get(String url) throws IOException {
        return mBitmapLruCache.get(url);
    }

    @Override
    void put(String url, Bitmap bitmap) {
        mBitmapLruCache.put(url, bitmap);
    }
}
