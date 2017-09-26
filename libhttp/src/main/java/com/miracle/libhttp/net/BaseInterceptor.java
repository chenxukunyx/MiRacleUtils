package com.miracle.libhttp.net;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-20
 * @time: 13:59
 * @age: 24
 */
public class BaseInterceptor implements Interceptor {

    private Map<String, String> header;

    public BaseInterceptor(Map<String, String> header) {
        this.header = header;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (header != null && header.size() > 0) {
            Set<String> keys = header.keySet();
            for (String key : keys) {
                builder.addHeader(key, header.get(key)).build();
            }
        }
        return chain.proceed(builder.build());
    }
}
