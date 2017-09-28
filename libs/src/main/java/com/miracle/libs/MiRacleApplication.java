package com.miracle.libs;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.miracle.libs.constant.MiracleConstant;
import com.miracle.libs.utils.CrashHandlerUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;


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

    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        initAVOSCloud();
        Logger.addLogAdapter(new AndroidLogAdapter());
        CrashHandlerUtils.getInstance().init(this, new CrashHandlerUtils.CrashListener() {
            @Override
            public void uploadExceptionToServer(File file) {

            }

            @Override
            public void crashAction() {
                Logger.e("uncaughtException");
            }
        }, "crashLog");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    private void initAVOSCloud() {
        AVOSCloud.initialize(this, MiracleConstant.APP_ID, MiracleConstant.APP_KEY);
        AVOSCloud.setDebugLogEnabled(true);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MiRacleApplication application = (MiRacleApplication) context.getApplicationContext();
        return application.refWatcher;
    }

}
