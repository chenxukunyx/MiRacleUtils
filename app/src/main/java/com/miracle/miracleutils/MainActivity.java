package com.miracle.miracleutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.miracle.libhttp.TestEntity;
import com.miracle.libhttp.net.DefaultObserver;
import com.miracle.libhttp.net.HttpFactory;
import com.miracle.libhttp.net.OnResultCallback;
import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.utils.MLog;


/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-27
 * @time: 11:18
 * @age: 24
 */
public class MainActivity extends AppCompatActivity implements IGetImageView<TestEntity> {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ImageViewAdapter imageViewAdapter;
    ImagePresenter imagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagePresenter = new ImagePresenter();
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
        //add by chenxukun: 这是一段神奇的代码。。反正这么写就对了
        imagePresenter.getMeizi(this);
    }

    @Override
    public void success(TestEntity data) {
        for (TestEntity.ResultsBean resultsBean : data.getResults()) {
            MLog.i(TAG, resultsBean.toString());
        }
        imageViewAdapter = new ImageViewAdapter(MainActivity.this, data);
        imageViewAdapter.setOnItemClickListener(new ImageViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final String url) {
//              ToastUtils.show(getApplicationContext(), url);
                Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(imageViewAdapter);
        imageViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void failure(int code, String msg) {

    }

    @Override
    public void loading(boolean b) {
        MLog.i(TAG, b);
    }
}
