package com.miracle.libhttp.callback;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-20
 * @time: 17:34
 * @age: 24
 */
public interface OnResultCallback<T> {
    void loading(boolean b);
    void onSuccess(T t);
    void onError(int code, String msg);
}
