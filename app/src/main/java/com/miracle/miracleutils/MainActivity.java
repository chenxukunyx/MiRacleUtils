package com.miracle.miracleutils;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.miracle.libhttp.TestEntity;
import com.miracle.libhttp.net.DefaultObserver;
import com.miracle.libhttp.net.HttpFactory;
import com.miracle.libhttp.net.OnResultCallback;
import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.utils.MLog;
import com.miracle.libs.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;


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
        HttpFactory.getHttpManager().request(HttpFactory.getHttpManager().getApiService().getFuliData(),
                new DefaultObserver<TestEntity>(new OnResultCallback<TestEntity>() {
                    @Override
                    public void onSuccess(TestEntity entity) {
                        imageViewAdapter = new ImageViewAdapter(MainActivity.this, entity);
                        imageViewAdapter.setOnItemClickListener(new ImageViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(final String  url) {
//                                ToastUtils.show(getApplicationContext(), url);
                                Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
                                intent.putExtra("url", url);
                                startActivity(intent);

                            }
                        });
                        recyclerView.setAdapter(imageViewAdapter);
                        imageViewAdapter.notifyDataSetChanged();
                        MLog.i(TAG, entity.getResults().get(30).getDesc());
                    }

                    @Override
                    public void onError(int code, String msg) {
                        MLog.i(TAG, code + " : " + msg);
                    }
                }));
    }
}
