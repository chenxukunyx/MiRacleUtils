package com.miracle.libs;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.miracle.libs.constant.MiracleConstant;


/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-08-15
 * @time: 16:48
 * @age: 24
 */
public class MiRacleApplication extends Application {

    private static final String TAG = "MiRacleApplication";


    @Override
    public void onCreate() {
        super.onCreate();
        initAVOSCloud();
    }

    private void initAVOSCloud() {
        AVOSCloud.initialize(this, MiracleConstant.APP_ID, MiracleConstant.APP_KEY);
        AVOSCloud.setDebugLogEnabled(true);
    }

}
