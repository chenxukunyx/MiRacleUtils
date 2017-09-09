package com.miracle.libs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.view.SuccessFailView;

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

        testView.setText(FileUtils.getMobileEnableRAM() + "");

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
        } else {
            successFailView.failureStatus();
        }
    }
}
