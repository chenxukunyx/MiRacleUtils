package com.miracle.libs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.miracle.libs.utils.AndroidVersionsUtils;
import com.miracle.libs.utils.AppApplicationMgr;
import com.miracle.libs.utils.AppCharacterParser;

/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-11
 * @time: 12:04
 * @age: 24
 */
public class test extends Activity {

    private static final String TAG = "test";

    String string = "向";
    TextView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String result = AppCharacterParser.getInstace().getSelling(string);
        Log.i(TAG, "----->>string: " + string);
        testView = (TextView) findViewById(R.id.test_view);
        testView.setText(AppApplicationMgr.getAppName(this) + " " + AppApplicationMgr.getPackageName(this) + " " + AppApplicationMgr.getVersionName(this) + " " +
                AppApplicationMgr.getVersionCode(this) + " " + AndroidVersionsUtils.getAndroidVersion());
    }
}
