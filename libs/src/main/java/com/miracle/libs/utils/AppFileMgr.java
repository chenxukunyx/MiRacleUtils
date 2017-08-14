package com.miracle.libs.utils;

import java.io.File;

/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-14
 * @time: 14:41
 * @age: 24
 */

public class AppFileMgr {

    public static void deleteFilesByDirectory(File dic) {
        if (dic != null && dic.exists() && dic.isDirectory()) {
            for (File file : dic.listFiles()) {
                if (file.isDirectory()) {
                    deleteFilesByDirectory(file);
                    file.delete();
                }
            }
            AppLogMessageMgr.i("AppFileMgr-->>deleteFilesByDirectory", "This directory is not file, execute delete");
        } else {
            AppLogMessageMgr.i("AppFileMgr-->>deleteFilesByDirectory", "This directory is file, not execute delete");
        }
    }

    /**
     * 递归取得文件夹大小
     * @param file
     * @return long
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file != null && file.exists() && file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()){
                    size = size + getFileSize(files[i]);
                }else {
                    size = size + files[i].length();
                }
            }
        }
        AppLogMessageMgr.i("AppFileMgr-->>getFileSize", "This file size: " + size);
        return size;
    }
}
