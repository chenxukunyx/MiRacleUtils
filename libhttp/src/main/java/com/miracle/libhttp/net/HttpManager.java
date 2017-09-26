package com.miracle.libhttp.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miracle.libhttp.config.UrlConfig;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-20
 * @time: 17:41
 * @age: 24
 */
public class HttpManager {
    private static final String TAG = "HttpManager";

    private Cache cache;
    private File httpCacheDir;
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private ApiService mApiService;
    private static Context mContext;
    private static String base_url = UrlConfig.BASE_URL;

    private Scheduler schedulerObserable = Schedulers.io();

//    private static Retrofit.Builder builder = new Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .baseUrl(base_url);
//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
//            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

    private static class SingletonHolder {
        private static HttpManager INSTANCE = new HttpManager(mContext);
    }

    public static HttpManager getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public static HttpManager getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }

        return new HttpManager(context, url);
    }

    public static HttpManager getInstance(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new HttpManager(context, url, headers);
    }

    private HttpManager() {

    }

    private HttpManager(Context context) {

        this(context, base_url, null);
    }

    private HttpManager(Context context, String url) {

        this(context, url, null);
    }

    private HttpManager(Context context, String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = base_url;
        }

        if (httpCacheDir == null) {
            httpCacheDir = new File(mContext.getCacheDir(), "http_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDir, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new NovateCookieManager(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(context))
                .addNetworkInterceptor(new CacheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        mApiService = retrofit.create(ApiService.class);

    }

    private <T> void toSubscribe(Observable<T> o, Observer<T> s) {
        o.subscribeOn(schedulerObserable)
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public <T> void request(Observable<T> subscribe, Observer<T> subscriber) {
        toSubscribe(subscribe, subscriber);
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public Scheduler getSchedulerObserable() {
        return schedulerObserable;
    }

    public HttpManager setSchedulerObserable(Scheduler schedulerObserable) {
        this.schedulerObserable = schedulerObserable;
        return this;
    }
}
