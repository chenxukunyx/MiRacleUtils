package com.miracle.libs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miracle.libhttp.utils.Unicode2CharUtils;
import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.utils.MLog;
import com.miracle.libs.view.SuccessFailView;


/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-11
 * @time: 12:04
 * @age: 24
 */
public class MiRacleActivity extends AppCompatActivity implements View.OnClickListener{

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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MiRacleApplication.getRefWatcher(this).watch(this);
        MLog.i(TAG, "onDestory");
    }
}
