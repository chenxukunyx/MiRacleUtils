package com.miracle.libs.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-19
 * @time: 10:43
 * @age: 24
 */
public class CrashHandlerUtils implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandlerUtils";

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandlerUtils mCrashHandlerUtils;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private Context context;
    private String mDirectory;
    private CrashListener mCrashListener;
    private File mCrashLogFile;

    private CrashHandlerUtils() {

    }

    public static CrashHandlerUtils getInstance() {
        if (mCrashHandlerUtils == null) {
            synchronized (CrashHandlerUtils.class) {
                if (mCrashHandlerUtils == null) {
                    mCrashHandlerUtils = new CrashHandlerUtils();
                }
            }
        }
        return mCrashHandlerUtils;
    }

    public void init(Context context, CrashListener crashListener, String directory) {
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.context = context.getApplicationContext();
        this.mCrashListener = crashListener;
        this.mDirectory = directory;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //导出异常信息到sdcard
        dumpExceptionToSDCard(e);
        //异常信息上传服务器
        uploadExceptionToServer();
        e.printStackTrace();

        if (!handleException(e) && mUncaughtExceptionHandler != null) {
            mUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                if (mCrashListener != null) {
                    mCrashListener.crashAction();
                }
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * 上传日志到服务器
     */
    private void uploadExceptionToServer() {
        if (mCrashListener != null && mCrashLogFile != null) {
            mCrashListener.uploadExceptionToServer(mCrashLogFile);
        }
    }

    private void dumpExceptionToSDCard(Throwable e) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            MLog.w(TAG, "sdcard unmounted, skip dump exception");
            return;
        }
        File dir = new File(PATH + mDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        MLog.i(TAG, "Path" + PATH);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        mCrashLogFile = new File(dir, FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(mCrashLogFile)));
            printWriter.println(time);
            dumpPhoneInfo(printWriter);
            printWriter.println();
            e.printStackTrace();
            printWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void dumpPhoneInfo(PrintWriter printWriter) {

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            printWriter.print("App version: ");
            printWriter.print(pi.versionName);//应用的版本名称
            printWriter.print('_');
            printWriter.println(pi.versionCode);//应用的版本号

            //android版本号
            printWriter.print("os version: ");
            printWriter.print(Build.VERSION.RELEASE);
            printWriter.print('_');
            printWriter.println(Build.VERSION.SDK_INT);

            //手机制造商
            printWriter.print("Vendor:");
            printWriter.println(Build.MANUFACTURER);

            //手机型号
            printWriter.print("Model:");
            printWriter.println(Build.MODEL);

            //cpu架构
            printWriter.print("CPU ABI:");
            printWriter.println(Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface CrashListener {
        /*上传文件到服务器*/
        public void uploadExceptionToServer(File file);

        /*出现异常下的处理*/
        public void crashAction();
    }
}
