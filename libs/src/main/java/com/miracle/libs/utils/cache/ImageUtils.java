package com.miracle.libs.utils.cache;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.miracle.libs.utils.MLog;

import java.io.IOException;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/31
 * @time: 上午9:53
 */

public class ImageUtils {

    private static ImageUtils mInstance;

    private NetCacheUtils mNetCacheUtils;
    private MemoriesCacheUtils mMemoriesCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private Cache mCache;


    private Bitmap mBitmap;

    public static ImageUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageUtils();
                }
            }
        }
        return mInstance;
    }

    public ImageUtils() {
        mLocalCacheUtils = new LocalCacheUtils();
        mMemoriesCacheUtils = new MemoriesCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoriesCacheUtils);
    }

    public void displayImage(ImageView iv, String url) {
        try {
            mBitmap = mMemoriesCacheUtils.get(url);
            if (mBitmap != null) {
                iv.setImageBitmap(mBitmap);
                MLog.i("cache", "从内存获取");
                return;
            }
            mBitmap = mLocalCacheUtils.get(url);
            if (mBitmap != null) {
                iv.setImageBitmap(mBitmap);
                MLog.i("cache", "从文件获取");
                return;
            }
            mNetCacheUtils.getBitmapFromNet(iv, url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
