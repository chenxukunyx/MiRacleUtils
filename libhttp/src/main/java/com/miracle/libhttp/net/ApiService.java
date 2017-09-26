package com.miracle.libhttp.net;

import com.miracle.libhttp.TestEntity;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-20
 * @time: 17:46
 * @age: 24
 */
public interface ApiService {

//    @GET
//    Observable<ResponseBody> getAndroidData();

    @GET("data/Android/10/1")
    Observable<TestEntity> getAndroidData();

    @GET("data/福利/10/1")
    Observable<ResponseBody> getFuliData();
}
