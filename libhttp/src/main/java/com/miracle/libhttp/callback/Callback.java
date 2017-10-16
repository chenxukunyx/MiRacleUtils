package com.miracle.libhttp.callback;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-20
 * @time: 14:31
 * @age: 24
 */
public abstract class Callback {
    public void onStart() {}

    public void onCompleted() {}

    public abstract void onError(Throwable e);

    public void onProgress(long fileSizeDownload) {}

    public abstract void onSuccess(String path, String name, long fileSize);
}
