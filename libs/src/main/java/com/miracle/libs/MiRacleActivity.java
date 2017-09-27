package com.miracle.libs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miracle.libhttp.TestEntity;
import com.miracle.libhttp.net.DefaultObserver;
import com.miracle.libhttp.net.HttpManager;
import com.miracle.libhttp.net.OnResultCallback;
import com.miracle.libhttp.utils.Unicode2CharUtils;
import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.utils.MLog;
import com.miracle.libs.view.SuccessFailView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;


/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-11
 * @time: 12:04
 * @age: 24
 */
public class MiRacleActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "MiRacleActivity";

    TextView testView;
    private SuccessFailView successFailView;
    private Button loading, success, failure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        testView = (TextView) findViewById(R.id.test_view);

        testView.setText(FileUtils.formatFileSize(FileUtils.getMobileEnableRAM()));

        loading = (Button) findViewById(R.id.loading);
        loading.setOnClickListener(this);
        success = (Button) findViewById(R.id.success);
        success.setOnClickListener(this);
        failure = (Button) findViewById(R.id.failure);
        failure.setOnClickListener(this);
        successFailView = (SuccessFailView) findViewById(R.id.successfail_view);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loading) {
            successFailView.loadingStatus();
        } else if (R.id.success == v.getId()) {
            successFailView.successStatus();
            String s = Unicode2CharUtils.decodeUnicode("\\u4ee3\\u7801\\u5bb6");
            MLog.i(TAG, s);
        } else {
            successFailView.failureStatus();
            doSomeWork();
        }
    }

    private void doSomeWork() {
        HttpManager.getInstance(this).request(HttpManager.getInstance(this).getApiService().getAndroidData(), new DefaultObserver<TestEntity>(new OnResultCallback<TestEntity>() {

            @Override
            public void onSuccess(TestEntity testEntity) {
                MLog.i(TAG, testEntity.isError());
                MLog.i(TAG, "-------------------------------------------------\\n");
                List<TestEntity.ResultsBean> list = testEntity.getResults();
                for (TestEntity.ResultsBean result : list) {
                    MLog.i(TAG, "id: " + result.get_id());
                    MLog.i(TAG, "createAt: " + result.getCreatedAt());
                    MLog.i(TAG, "desc: " + result.getDesc());
                    MLog.i(TAG, "publishAt: " + result.getPublishedAt());
                    MLog.i(TAG, "source: " + result.getSource());
                    MLog.i(TAG, "type: " + result.getType());
                    MLog.i(TAG, "url: " + result.getUrl());
                    MLog.i(TAG, "who: " + result.getWho());
                    MLog.i(TAG, "-----------------------------\\n");
                }
            }

            @Override
            public void onError(int code, String msg) {
                MLog.d(TAG, msg);
            }
        }));

        HttpManager.getInstance(this).request(HttpManager.getInstance(this).getApiService().getFuliData(),
                        new DefaultObserver<ResponseBody>(new OnResultCallback<ResponseBody>() {
                            @Override
                            public void onSuccess(ResponseBody responseBody) {
                                try {
                                    String s = responseBody.string();
                                    MLog.i(TAG, s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(int code, String msg) {
                                MLog.i(TAG, msg);
                            }
                        }));
    }
}
