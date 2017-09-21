package com.miracle.lib_http.net;


import android.util.MalformedJsonException;

import com.miracle.lib_http.net.ApiException;
import com.miracle.lib_http.net.OnResultCallback;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;


/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-20
 * @time: 16:17
 * @age: 24
 */
public class DefaultObserver<T> implements Observer<T>{

    private OnResultCallback<T> mOnResultListener;
    private Disposable mDisposable;

    public DefaultObserver(OnResultCallback<T> mOnResultListener) {
        this.mOnResultListener = mOnResultListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
    }

    @Override
    public void onNext(T value) {
        if (mOnResultListener != null) {
            mOnResultListener.onSuccess(value);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof CompositeException) {
            CompositeException compositeE=(CompositeException)e;
            for (Throwable throwable : compositeE.getExceptions()) {
                if (throwable instanceof SocketTimeoutException) {
                    mOnResultListener.onError(ApiException.Code_TimeOut,ApiException.SOCKET_TIMEOUT_EXCEPTION);
                } else if (throwable instanceof ConnectException) {
                    mOnResultListener.onError(ApiException.Code_UnConnected,ApiException.CONNECT_EXCEPTION);
                } else if (throwable instanceof UnknownHostException) {
                    mOnResultListener.onError(ApiException.Code_UnConnected,ApiException.CONNECT_EXCEPTION);
//                } else if (throwable instanceof RxCacheException) {
                    //缓存异常暂时不做处理
                }  else if (throwable instanceof MalformedJsonException) {
                    mOnResultListener.onError(ApiException.Code_MalformedJson,ApiException.MALFORMED_JSON_EXCEPTION);
                }
            }
        }else {
            String msg = e.getMessage();
            int code;
            if (msg.contains("#")) {
                code = Integer.parseInt(msg.split("#")[0]);
                mOnResultListener.onError(code, msg.split("#")[1]);
            } else {
                code = ApiException.Code_Default;
                mOnResultListener.onError(code, msg);
            }
        }
    }

    @Override
    public void onComplete() {

    }

    public void unSubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
