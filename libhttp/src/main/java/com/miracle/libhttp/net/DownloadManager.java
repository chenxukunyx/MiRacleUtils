package com.miracle.libhttp.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.miracle.libhttp.callback.Callback;
import com.miracle.libhttp.utils.MLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.ResponseBody;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-20
 * @time: 14:34
 * @age: 24
 */
public class DownloadManager {

    private static final String TAG = "DownloadManager";
    private Callback callback;

    private static String APK_CONNECTTYPE = "application/vnd.android.package-archive";

    private static String PNG_CONNECTTYPE = "image/png";

    private static String JPT_CONNECTTYPE = "image/jpg";

    private static String fileSuffix = "";

    private Handler handler;

    public DownloadManager(Callback c) {
        this.callback = c;
    }

    private static DownloadManager mInstance;

    public static DownloadManager getDownloadManager(Callback callback) {
        if (mInstance == null) {
            mInstance = new DownloadManager(callback);
        }
        return mInstance;
    }

    public boolean writeResponseBodtToDisk(Context context, ResponseBody body) {
        MLog.d(TAG, "connectType: " + body.contentType().toString());
        String type = body.contentType().toString();
        if (type.equals(APK_CONNECTTYPE)) {
            fileSuffix = ".apk";
        } else if (type.equals(PNG_CONNECTTYPE)) {
            fileSuffix = ".png";
        } else if (type.equals(JPT_CONNECTTYPE)) {
            fileSuffix = ".jpg";
        }
        final String name = System.currentTimeMillis() + fileSuffix;
        final String path = context.getExternalFilesDir(null) + File.separator + name;
        MLog.d(TAG, "path: " + path);
        try {
            File futureStudioIconFile = new File(path);
            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                MLog.d(TAG, "file length" + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    MLog.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    if (callback != null) {
                        handler = new Handler(Looper.getMainLooper());
                        final long finalFileSizeDownloaded = fileSizeDownloaded;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onProgress(finalFileSizeDownloaded);
                            }
                        });
                    }
                }

                outputStream.flush();
                MLog.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                if (callback != null) {
                    handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(path, name, fileSize);
                        }
                    });
                }
                return true;
            } catch (IOException e) {
                if (callback != null) {
                    callback.onError(e);
                }
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }catch (IOException e) {
            if (callback != null) {
                callback.onError(e);
            }
            return false;
        }
    }
}
