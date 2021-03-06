package com.miracle.libs.utils;/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-14
 * @time: 14:18
 * @age: 24
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @fuction：app数据清理类
 */
@SuppressLint("SdCardPath")
public class FileCleanUtils {

    /**
     * 清除本应用内部缓存数据(/data/data/com.xxx.xxx/cache)
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        FileUtils.deleteFilesByDirectory(context.getCacheDir());
        AppLogMessageMgr.i("FileCleanUtils->>cleanInternalCache", "清除本应用内部缓存(/data/data/" + context.getPackageName() + "/cache)");
    }

    /**
     * 清除本应用外部缓存数据(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.deleteFilesByDirectory(context.getExternalCacheDir());
            AppLogMessageMgr.i("FileCleanUtils->>cleanExternalCache", "清除本应用外部缓存数据(/mnt/sdcard/android/data/" + context.getPackageName() + "/cache)");
        }
    }

    /**
     * 清除本应用所有数据库
     * @param context
     */
    public static void cleanDataBase(Context context) {
        FileUtils.deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
        AppLogMessageMgr.i("FileCleanUtils->>cleanDatabases", "清除本应用所有数据库");
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * @param context 上下文
     * @return void
     */
    public static void cleanSharedPreference(Context context) {
        FileUtils.deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
        AppLogMessageMgr.i("FileCleanUtils->>cleanSharedPreference", "清除本应用cleanSharedPreference数据信息");
    }


    /**
     * 根据名字清除本应用数据库
     * @param context 上下文
     * @param dbName
     * @return void
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
        AppLogMessageMgr.i("FileCleanUtils->>cleanDatabaseByName", "根据名字清除本应用数据库");
    }


    /**
     * 清除本应用files文件(/data/data/com.xxx.xxx/files)
     * @param context 上下文
     * @return void
     */
    public static void cleanFiles(Context context) {
        FileUtils.deleteFilesByDirectory(context.getFilesDir());
        AppLogMessageMgr.i("FileCleanUtils->>cleanFiles", "清除data/data/" + context.getPackageName() + "/files下的内容信息");
    }


    /**
     * 清除本应用所有的数据
     * @param context 上下文
     * @return int
     */
    public static int cleanApplicationData(Context context) {
        //清除本应用内部缓存数据
        cleanInternalCache(context);
        //清除本应用外部缓存数据
        cleanExternalCache(context);
        //清除本应用SharedPreference
        cleanSharedPreference(context);
        //清除本应用files文件
        cleanFiles(context);
        AppLogMessageMgr.i("FileCleanUtils->>cleanApplicationData", "清除本应用所有的数据");
        return 1;
    }


    /**
     * 获取App应用缓存的大小
     * @param context 上下文
     * @return String
     */
    public static String getAppClearSize(Context context) {
        long clearSize = 0;
        String fileSizeStr = "";
        DecimalFormat df = new DecimalFormat("0.00");
        //获得应用内部缓存大小
        clearSize = FileUtils.getFileSize(context.getCacheDir());
        //获得应用SharedPreference缓存数据大小
        clearSize += FileUtils.getFileSize(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
        //获得应用data/data/com.xxx.xxx/files下的内容文件大小
        clearSize += FileUtils.getFileSize(context.getFilesDir());
        //获取应用外部缓存大小
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            clearSize += FileUtils.getFileSize(context.getExternalCacheDir());
        }
        if(clearSize > 5000)  {
            //转换缓存大小Byte为MB
            fileSizeStr = df.format((double) clearSize / 1048576) + "MB";
        }
        AppLogMessageMgr.i("FileCleanUtils->>getAppClearSize", "获取App应用缓存的大小");
        return fileSizeStr;
    }

    public static void cleanBitmapCache(Context context, File file) {
        delAllFiles(context, file);
    }

    public static void delAllFiles(Context context, File file) {
        if (file == null) {
            return;
        }
        //文件目录是否存在
        if (file.exists()) {
            //文件目录是否是文件
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    return;
                }
                for (File f: listFiles) {
                    delAllFiles(context, f);
                }
                file.delete();
            }
        }
    }
}
