package com.miracle.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.miracle.libhttp.TestEntity;
import com.miracle.libhttp.net.DefaultObserver;
import com.miracle.libhttp.net.HttpManager;
import com.miracle.libhttp.net.OnResultCallback;
import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.utils.MLog;
import com.miracle.miracleutils.R;


/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-27
 * @time: 11:18
 * @age: 24
 */
public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ImageViewAdapter imageViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        MLog.i(TAG, FileUtils.getSdCardAbsolutePath());
        MLog.i(TAG, FileUtils.getSdCardIsEnable());
        MLog.i(TAG, FileUtils.formatFileSize(FileUtils.getSdCardEnableSize()));
        MLog.i(TAG, FileUtils.getSdCardPath());
        MLog.i(TAG, FileUtils.formatFileSize(FileUtils.getSdCardSize()));
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        HttpManager.getHttpManager(this).request(HttpManager.getHttpManager(this).getApiService().getFuliData(),
                new DefaultObserver<TestEntity>(new OnResultCallback<TestEntity>() {
                    @Override
                    public void onSuccess(TestEntity entity) {
                        imageViewAdapter = new ImageViewAdapter(MainActivity.this, entity);
                        recyclerView.setAdapter(imageViewAdapter);
                        imageViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int code, String msg) {

                    }
                }));

        HttpManager.getHttpManager(this).request(HttpManager.getHttpManager(this).getApiService().getAndroidData(),
                new DefaultObserver<TestEntity>(new OnResultCallback<TestEntity>() {
                    @Override
                    public void onSuccess(TestEntity entity) {
                        for (TestEntity.ResultsBean resultsBean : entity.getResults()) {
                            MLog.i(TAG, resultsBean.getDesc());
                            MLog.i(TAG, "------------------");
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {

                    }
                }));
    }
}
