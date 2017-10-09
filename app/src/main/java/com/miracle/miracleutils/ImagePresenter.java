package com.miracle.miracleutils;

import android.content.Intent;

import com.miracle.libhttp.TestEntity;
import com.miracle.libhttp.net.DefaultObserver;
import com.miracle.libhttp.net.HttpFactory;
import com.miracle.libhttp.net.OnResultCallback;
import com.miracle.libs.utils.MLog;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-10-09
 * @time: 16:43
 * @age: 24
 */
public class ImagePresenter {
    private static final String TAG = "ImagePresenter";

    public void getAndroidData(final IGetImageView<TestEntity> iGetImageView) {
        HttpFactory.getHttpManager().request(HttpFactory.getHttpManager().getApiService().getAndroidData(),
                new DefaultObserver<TestEntity>(new OnResultCallback<TestEntity>() {
                    @Override
                    public void loading(boolean b) {
                        iGetImageView.loading(b);
                    }

                    @Override
                    public void onSuccess(TestEntity entity) {
                        iGetImageView.success(entity);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        iGetImageView.failure(code, msg);
                    }
                }));
    }

    public void getMeizi(final IGetImageView<TestEntity> iGetImageView) {
        HttpFactory.getHttpManager().request(HttpFactory.getHttpManager().getApiService().getFuliData(),
                new DefaultObserver<TestEntity>(new OnResultCallback<TestEntity>() {
                    @Override
                    public void loading(boolean b) {
                        iGetImageView.loading(b);
                    }

                    @Override
                    public void onSuccess(TestEntity entity) {
                        iGetImageView.success(entity);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        iGetImageView.failure(code, msg);
                    }
                }));
    }

}
