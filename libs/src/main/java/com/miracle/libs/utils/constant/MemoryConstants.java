package com.miracle.libs.utils.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created with Android Studio
 *
 * @fuction:
 * @author: chenxukun
 * @data: 2017/11/1
 * @time: 上午11:21
 */

public class MemoryConstants {

    /**
     * BYTE与BYTE的倍数
     */
    public static final int BYTE = 1;

    /**
     * KB与BYTE的倍数
     */
    public static final int KB = 1024;

    /**
     * MB与BYTE的倍数
     */
    public static final int MB = 1048576;

    /**
     * GB与BYTE的倍数
     */
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit{
    }
}
