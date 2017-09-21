package com.miracle.libs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miracle.lib_http.*;
import com.miracle.lib_http.TestEntity;
import com.miracle.lib_http.net.DefaultObserver;
import com.miracle.lib_http.net.HttpManager;
import com.miracle.lib_http.net.OnResultCallback;
import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.utils.MLog;
import com.miracle.libs.view.SuccessFailView;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
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
        } else {
            successFailView.failureStatus();
            doSomeWork();
        }
    }

    private boolean test() {
        Integer a = new Integer(30);
        Integer b = new Integer(30);
        return a == b;
    }

    private void doSomeWork() {
//        Observable observable = Observable.create(new ObservableOnSubscribe() {
//            @Override
//            public void subscribe(ObservableEmitter e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext("3");
//                e.onComplete();
//            }
//        });
//
//        Observable observable1 = Observable.just(1, 2, 3, "4");
//
//        Observer observer = new Observer() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Logger.i(TAG, d.isDisposed());
////                MLog.i("onSubscribe : " + d.isDisposed());
//            }
//
//            @Override
//            public void onNext(Object value) {
//                if (value instanceof String) {
//                    MLog.i("onNext : " + value);
//                    Logger.i(TAG, "----->onNext : " + value);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                MLog.i("onComplete");
//            }
//        };

//        observable1.map(new Function() {
//            @Override
//            public Object apply(Object o) throws Exception {
//                return o.toString();
//            }
//        }).subscribe(observer);

        HttpManager.getInstance(this).request(HttpManager.getInstance(this).getApiService().getData(), new DefaultObserver<TestEntity>(new OnResultCallback<TestEntity>() {

            @Override
            public void onSuccess(TestEntity testEntity) {
                Logger.d(testEntity.getResults().get(0).getDesc());
            }

            @Override
            public void onError(int code, String msg) {
                Logger.d(msg);
            }
        }));
    }
}
