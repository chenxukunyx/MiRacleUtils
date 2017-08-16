package com.miracle.libs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.miracle.libs.bean.JianshuBean;
import com.miracle.libs.utils.AppCharacterParser;
import com.miracle.libs.utils.AppFileMgr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-11
 * @time: 12:04
 * @age: 24
 */
public class MiRacleActivity extends Activity {

    private static final String TAG = "MiRacleActivity";

    String str = "向";
    TextView testView;
    Document document;
    private List<JianshuBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String result = AppCharacterParser.getInstace().getSelling(str);
        Log.i(TAG, "----->>str: " + str);
        testView = (TextView) findViewById(R.id.test_view);

        testView.setText(AppFileMgr.getSdCardSize() + "");
        mList = new ArrayList<>();

    }
}
