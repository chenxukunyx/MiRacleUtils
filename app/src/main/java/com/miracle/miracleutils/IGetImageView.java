package com.miracle.miracleutils;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-10-09
 * @time: 16:42
 * @age: 24
 */
public interface IGetImageView<T> {
    void success(T data);
    void failure(int code, String msg);
    void loading(boolean b);
}
