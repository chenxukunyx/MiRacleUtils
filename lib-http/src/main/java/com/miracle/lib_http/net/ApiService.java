package com.miracle.lib_http.net;

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

    @GET
    Observable<ApiResponse<ResponseBody>> getData();
}
