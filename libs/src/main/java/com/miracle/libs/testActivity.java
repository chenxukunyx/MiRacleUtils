package com.miracle.libs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.miracle.libs.utils.AndroidVersionsUtils;
import com.miracle.libs.utils.AppApplicationMgr;
import com.miracle.libs.utils.AppCharacterParser;
import com.miracle.libs.utils.AppFileMgr;

/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-11
 * @time: 12:04
 * @age: 24
 */
public class TestActivity extends Activity {

    private static final String TAG = "TestActivity";

    String string = "向";
    TextView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String result = AppCharacterParser.getInstace().getSelling(string);
        Log.i(TAG, "----->>string: " + string);
        testView = (TextView) findViewById(R.id.test_view);

        testView.setText(AppFileMgr.getSdCardSize() + "");
    }
}
