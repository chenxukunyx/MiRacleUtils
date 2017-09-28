package com.miracle.libs.utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.miracle.libs.utils.MD5Utils;
import com.miracle.libs.utils.MLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/31
 * @time: 上午8:59
 *
 * 本地图片缓存
 */

public class LocalCacheUtils extends Cache{

    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bitmap_cache";

//    public void setLocalBitmapCache(String url, Bitmap bitmap) {
//        File dir = new File(CACHE_PATH);
//        if (!dir.exists() || !dir.isDirectory()) {
//            dir.mkdirs();
//        }
//        String fileName = MD5Utils.encoder(url);
//        File bitmapCacheFile = new File(dir, fileName);
//        try {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(bitmapCacheFile));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Bitmap getLocalBittmapCache(String url) {
//        File bitmapCacheFile = new File(CACHE_PATH, MD5Utils.encoder(url));
//        if (bitmapCacheFile.exists()) {
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(bitmapCacheFile));
//                return bitmap;
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    @Override
    Bitmap get(String url) throws IOException {
        File bitmapCacheFile = new File(CACHE_PATH, MD5Utils.encoder(url));
        if (bitmapCacheFile.exists()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(bitmapCacheFile));
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    void put(String url, Bitmap bitmap) {
        File dir = new File(CACHE_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        String fileName = MD5Utils.encoder(url);
        File bitmapCacheFile = new File(dir, fileName);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(bitmapCacheFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
